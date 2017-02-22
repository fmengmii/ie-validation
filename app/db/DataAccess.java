package db;

import play.Logger;
import play.db.DB;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringEscapeUtils;

import annotation.data.*;
import frames.CRFReader;

import com.google.gson.*;


public class DataAccess
{
	private Gson gson;
	private CRFReader crfReader;
	private String schema;
	private String docSchema;
	//private String rq;
	private List<Map<String, Object>> sectionList;
	private int crfID;
	private long currDocID;

	/*
	private String docTable = "";
	private String docKey = "";
	private String docTextCol = "";
	private int docID = -1;
	*/


	public DataAccess(String schema, String docSchema, List<Map<String, Object>> sectionList)
	{
		this.schema = schema;
		this.docSchema = docSchema;
		this.sectionList = sectionList;
		gson = new Gson();
		crfReader = new CRFReader(schema);
	}

	//new code
	public boolean authenticate(String username, String password) throws SQLException {
	    Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		
		String rq = getReservedQuote(conn);
		
		ResultSet rs = stmt.executeQuery("select pw from " + schema + rq + "user" + rq + " where user_name = '" + username + "'");
		// ResultSet rs = stmt.executeQuery("select pw from " + schema + "user where user_name = '" + username + "'");


	    if(rs.next()) {
	        if(password.equals(rs.getString(1))) {
	            conn.close();
	            return true;
	        }

	    }
	    conn.close();
	    return false;
	}

	/*public String getHistory() throws SQLException {
	    String mostRecentAction = "none";

	    Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select act from " + schema + "history");

		if(rs.next()) {
		    mostRecentAction = rs.getString(1);
		}
		System.out.println("DataAccess getHistory ran");
		System.out.println(mostRecentAction);

		conn.close();

	    return mostRecentAction;
	}*/

	public List<Map<String, String>> getHistory() throws SQLException {
		List<Map<String, String>> history = new ArrayList<Map<String, String>>();

		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + schema + "history");

		while(rs.next()) {
			String processID = rs.getString(1);
			String action = rs.getString(2);
			String htmlID = rs.getString(3);
			String extraInformation = rs.getString(4);
		  Map<String, String> map = new HashMap<String, String>();
			map.put("processID", processID);
			map.put("action", action);
			map.put("htmlID", htmlID);
			map.put("extraInfo", extraInformation);
		  history.add(map);
		}

		// System.out.println("DataAccess getHistory ran");

		conn.close();

	  return history;
	}

	public void actionOccurred(String action, String htmlID, String extraInfo) throws SQLException { // not sure if to use ints or longs
	  Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		stmt.execute("insert into " + schema + "history (act, html_id, extra_information) values ('" + action + "', '" + htmlID + "', '" + extraInfo + "')");
		conn.close();
	}

	public String getElementID(String htmlID) throws SQLException {
		String elementID = "";
		htmlID = htmlID.substring(0, htmlID.length() - 4);
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select element_id from " + schema + "element where html_id = '" + htmlID + "'");

		if(rs.next()) {
			elementID = rs.getString(1);
		}

		conn.close();

		elementID += "_0_0";

		return elementID;
	}

	public void clearHistory() throws SQLException { // do this at the beginning of each login...?
		/*
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		stmt.execute("drop table if exists history");
		stmt.execute("create table history (id int not null auto_increment, act varchar(255), html_id varchar(255), extra_information varchar(1000), primary key (id))");

		conn.close();
		*/
	}

	public void deleteMostRecentHistory() throws SQLException {
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		stmt.execute("delete from " + schema + "history order by id desc limit 1");

		conn.close();
	}


	//end of new code


	public int getCRFID(int projID) throws SQLException
	{
		int crfID = -1;
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select crf_id from " + schema + "crf_project where project_id = " + projID);
		if (rs.next()) {
			crfID = rs.getInt(1);
		}
		
		conn.close();

		return crfID;
	}

	public long getCurrDocID()
	{
		return currDocID;
	}

	public List<Map<String, String>> getProjects() throws SQLException
	{
		List<Map<String, String>> crfList = new ArrayList<Map<String, String>>();

		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select project_id, name from " + schema + "project");
		while (rs.next()) {
			String crfProjID = rs.getString(1);
			String name = rs.getString(2);
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", crfProjID);
			map.put("name", name);
			crfList.add(map);
 		}

		conn.close();

		return crfList;
	}

	public Map<String, String> getDocument(String docNamespace, String docTable, long docID) throws SQLException
	{
		// new code
		clearHistory(); // clears whenever you load a new document
		String docText = "";
		Connection conn = DB.getConnection("doc");
		Statement stmt = conn.createStatement();
		Connection conn2 = DB.getConnection();
		Statement stmt2 = conn2.createStatement();
		Map<String, String> docMap = new HashMap<String, String>();

		//get the key and text column names
		ResultSet rs = stmt2.executeQuery("select document_key, document_text_column, document_name, document_features, frame_instance_id from " + schema + "frame_instance_document "
			+ "where document_namespace = '" + docNamespace + "' and document_table = '" + docTable + "' and document_id = " + docID);

		String docKey = "";
		String docTextColumn = "";
		String docName = "";
		String docFeaturesStr = "";
		int frameInstanceID = 0;

		if (rs.next()) {
			docKey = rs.getString(1);
			docTextColumn = rs.getString(2);
			docName = rs.getString(3);
			docFeaturesStr = rs.getString(4);
			frameInstanceID = rs.getInt(5);
		}
		long crfID = frameInstanceID;

		rs = stmt2.executeQuery("select status from " + schema + "frame_instance_status " +
				"where frame_instance_id = " + crfID );
		if( rs.next() ) {
			docMap.put("frameInstanceStatus", String.valueOf(rs.getInt(1)));
		}
		Logger.info("getDocument: frameInstanceStatus=" + docMap.get("frameInstanceStatus"));

		System.out.println("select " + docTextColumn + " from " + docSchema + docTable + " where " + docKey + " = " + docID);

		//rs = stmt.executeQuery("select " + docTextColumn + " from " + schema + docTable + " where " + docKey + " = " + docID);
		rs = stmt.executeQuery("select " + docTextColumn + " from " + docSchema + docTable + " where " + docKey + " = " + docID);

		if (rs.next()) {
			docText = rs.getString(1);
			//docText = StringEscapeUtils.escapeHtml4(docText);
			docText = docText.replaceAll("<<", "[[");
			docText = docText.replaceAll(">>", "]]");
		}

		//docText = docText.replaceAll("\\n", "\\\\n");
		//docText = docText.replaceAll("\r\n", "\n");
		docText = docText.replaceAll("\r", "");

		rs.close();
		stmt.close();
		conn.close();
		stmt2.close();
		conn2.close();


		docMap.put("docName", docName);
		docMap.put("docText", docText + "\n\n");
		docMap.put("docFeatures", docFeaturesStr);

		//return "{\"docName\":\"" + docName + "\",\"docText\":\"" + docText.trim() + "\"}";
		//return gson.toJson(docMap);
		return docMap;
	}

	/*
	public String getAnnotations(List<Long> docIDList, String docNamespace) throws SQLException
	{

		//schema = (String) Cache.get("schemaName");

		StringBuilder strBlder = new StringBuilder();
		for (long docID : docIDList) {
			if (strBlder.length() > 0)
				strBlder.append(",");
			strBlder.append(docID);
		}

		List<Annotation> annotList = new ArrayList<Annotation>();
		Connection conn = DB.getConnection(docNamespace);
		String rq = getReservedQuote(conn);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select id, start, " + rq + "end" + rq + ", annotation_type, features from " + schema + "annotations where document_id in (" + strBlder.toString() +
			")");
		while (rs.next()) {
			int id = rs.getInt(1);
			int start = rs.getInt(2);
			int end = rs.getInt(3);
			String annotationType = rs.getString(4);
			String featuresStr = rs.getString(5);
			Map<String, Object> featuresMap = new HashMap<String, Object>();
			featuresMap = (Map<String, Object>) gson.fromJson(featuresStr, featuresMap.getClass());
			Annotation annot = new Annotation(id, annotationType, start, end, featuresStr, featuresMap);
			annotList.add(annot);
		}

		rs.close();
		stmt.close();
		conn.close();

		return gson.toJson(annotList);
	}
	 */

	public String loadProject(String username, int projID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		//schema = (String) Cache.get("schemaName");
		String rq = getReservedQuote(conn);

		List<Map<String, Object>> frameList = new ArrayList<Map<String, Object>>();

		int lastFrameInstanceID = 0;
		int lastFrameInstanceIndex = -1;
		ResultSet rs = stmt.executeQuery("select frame_instance_id from " + schema + rq + "user" + rq + " where user_name = '" + username + "'"); // changed from " where project_id = " + projID);
		if (rs.next()) {
			lastFrameInstanceID = rs.getInt(1);
		}

		rs = stmt.executeQuery("select a.frame_instance_id, b.name from " + schema + "project_frame_instance a, " + schema + "frame_instance b where a.frame_instance_id = b.frame_instance_id and "
			+ "a.project_id = " + projID + " order by frame_instance_id");

		int index = 1;
		while (rs.next()) {
			int frameInstanceID = rs.getInt(1);
			String name = rs.getString(2);

			if (frameInstanceID == lastFrameInstanceID)
				lastFrameInstanceIndex = index;

			Map<String, Object> frameMap = new HashMap<String, Object>();
			frameMap.put("frameInstanceID", frameInstanceID);
			frameMap.put("name", name);
			frameList.add(frameMap);

			index++;
		}

		if (lastFrameInstanceIndex == -1) {
			lastFrameInstanceIndex = 1;
			lastFrameInstanceID = (Integer) frameList.get(0).get("frameInstanceID");
		}


		//update user projectID
		int userCount = 0;
		rs = stmt.executeQuery("select count(*) from " + schema + rq + "user" + rq + " where user_name = '" + username + "'"); // this was updated from rs = stmt.executeQuery("select count(*) from " + schema + rq + "user" + rq + " where user_name = 'test'")
		if (rs.next()) {
			userCount = rs.getInt(1);
		}

		if (userCount == 0)
			stmt.execute("insert into " + schema + rq + "user" + rq + " (user_name, project_id, frame_instance_id) values ('test', " + projID + ", 1)");
		else
			stmt.execute("update " + schema + rq + "user" + rq + " set project_id = " + projID + " where user_name = '" + username + "'"); // this was updated from stmt.execute("update " + schema + rq + "user" + rq + " set project_id = " + projID + " where user_name = 'test'")

		conn.close();

		return gson.toJson(frameList) + ",{\"lastFrameInstanceID\":" + lastFrameInstanceID + ",\"lastFrameInstanceIndex\":" + lastFrameInstanceIndex + "}";
	}

	public String loadCRF(int projID, int frameInstanceID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		crfID = -1;
		//schema = (String) Cache.get("schemaName");

		//ResultSet rs = stmt.executeQuery("select crf_id from " + schema + "crf_project where crf_project_id = " + projID);
		//modify by wyu on Feb 4, 2017
		ResultSet rs = stmt.executeQuery("select crf_id from " + schema + "crf_project where project_id = " + projID);
		if (rs.next())
			crfID = rs.getInt(1);

		System.out.println("projID: " + projID + " crfID: " + crfID);

		//sectionList = new ArrayList<Map<String, Object>>();
		String crfStr = crfReader.readCRFDB(crfID, sectionList, frameInstanceID);

		conn.close();

		return crfStr;
	}

	public String loadFrameInstance(String username, int frameInstanceID, int projID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		String rq = getReservedQuote(conn);

		//schema = (String) Cache.get("schemaName");



		/*
		List<Map<String, Object>> frameList = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> highlightRangeMap = new HashMap<String, Map<String, Object>>();

		//Map<Integer, Integer> sectionNumMap = new HashMap<Integer, Integer>();
		//Map<String, Integer> elementNumMap = new HashMap<String, Integer>();
		//int currSectionNum = 0;
		//int currElementNum = 0;
		//String currElementID = "";

		ResultSet rs = stmt.executeQuery("select a.value, a.section_slot_number, a.element_slot_number, a.element_id, b.html_id, c.start, c." + rq + "end" + rq + ", "
			+ "a.document_namespace, a.document_table, a.document_id, c.features, d.html_id, e.element_type_name "
			+ "from " + schema + "frame_instance_data a, " + schema + "element b, " + schema + "annotation c, " + schema + "value d, " + schema + "element_type e "
			+ "where a.element_id = b.element_id and a.annotation_id = c.id and "
			+ "c.id = a.annotation_id and c.document_namespace = a.document_namespace and c.document_table = a.document_table and c.document_id = a.document_id and "
			+ "a.frame_instance_id = " + frameInstanceID + " and b.element_type = e.element_type_id and a.slot_id = d.slot_id "
			+ "order by a.element_id, a.section_slot_number, a.element_slot_number");

		while (rs.next()) {
			String value = rs.getString(1);
			int sectionSlotNum = rs.getInt(2);
			int elementSlotNum = rs.getInt(3);
			String elementID = rs.getString(4);
			String elementHTMLID = rs.getString(5);
			int start = rs.getInt(6);
			int end = rs.getInt(7);
			String docNamespace = rs.getString(8);
			String docTable = rs.getString(9);
			long docID = rs.getLong(10);
			String features = rs.getString(11);
			String valueHTMLID = rs.getString(12);
			String elementType = rs.getString(13);

			Map<String, Object> frameMap = new HashMap<String, Object>();
			frameMap.put("frameInstanceID", frameInstanceID);
			frameMap.put("value", value);
			frameMap.put("section_slot_number", sectionSlotNum);
			frameMap.put("element_slot_number", elementSlotNum);

			//if (slotNum > 1) {
			elementID += "_" + sectionSlotNum + "_" + elementSlotNum;
			elementHTMLID += "_" + sectionSlotNum + "_" + elementSlotNum;
			valueHTMLID += "_" + sectionSlotNum + "_" + elementSlotNum;
			//}

			frameMap.put("elementID", elementID);
			frameMap.put("elementHTMLID", elementHTMLID);
			frameMap.put("valueHTMLID", valueHTMLID);
			//frameMap.put("start", start);
			//frameMap.put("end", end);
			frameMap.put("docNamespace", docNamespace);
			frameMap.put("docTable", docTable);
			frameMap.put("docID", docID);
			frameMap.put("features", features);
			frameMap.put("elementType", elementType);

			frameList.add(frameMap);

			Map<String, Object> rangeMap = new HashMap<String, Object>();
			rangeMap.put("start", start);
			rangeMap.put("end", end);
			rangeMap.put("docNamespace", docNamespace);
			rangeMap.put("docTable", docTable);
			rangeMap.put("docID", docID);

			String htmlID = elementHTMLID;

			if (elementType.equals("checkbox"))
				htmlID = valueHTMLID;

			highlightRangeMap.put(htmlID, rangeMap);
		}
		*/





		String frameDataStr = getFrameData(frameInstanceID);


		long docID = -1;
		String docNamespace = "";
		String docTable = "";
		String docKey = "";
		String docTextCol = "";

		ResultSet rs = stmt.executeQuery("select document_namespace, document_table, document_key, document_text_column, document_id from "
			+ schema + "frame_instance_document "
			+ "where frame_instance_id = " + frameInstanceID);
		System.out.println("select document_namespace, document_table, document_key, document_text_column, document_id from "
			+ schema + "frame_instance_document "
			+ "where frame_instance_id = " + frameInstanceID);
		if (rs.next()) {
			docNamespace = rs.getString(1);
			docTable = rs.getString(2);
			docKey = rs.getString(3);
			docTextCol = rs.getString(4);
			docID = rs.getLong(5);

			currDocID = docID;
		}

		/*
		Cache.set("docTable", docTable);
		Cache.set("docKey", docKey);
		Cache.set("docTextCol", docTextCol);
		Cache.set("docID", docID);
		*/

		//String text = getDocument(docNamespace, docTable, docID);
		Map<String, String> docMap = getDocument(docNamespace, docTable, docID);

		//String frameListJSON = gson.toJson(frameList);
		String docJSON = gson.toJson(docMap);
		//String highlightRangeJSON = gson.toJson(highlightRangeMap);

		//frameListStr = "[" + frameListStr + ",\"" + text + "\"]";

		//List<Map<String, Object>> sectionList = (List<Map<String, Object>>) Cache.get("sectionList");
		//Integer projID = (Integer) Cache.get("projID");
		Map<Integer, Integer> sectionRepeatMap = new HashMap<Integer, Integer>();
		rs = stmt.executeQuery("select section_id, repeat_num from " + schema + "frame_instance_section_repeat where frame_instance_id = " + frameInstanceID);
		while (rs.next()) {
			int sectionID = rs.getInt(1);
			int repeatNum = rs.getInt(2);
			sectionRepeatMap.put(sectionID, repeatNum);

			System.out.println("sectionID: " + sectionID + " repeatNum: " + repeatNum);
		}

		for (Map<String, Object> sectionMap : sectionList) {
			int sectionID = ((Double) sectionMap.get("sectionID")).intValue();
			Integer repeatNum = sectionRepeatMap.get(sectionID);
			if (repeatNum == null)
				repeatNum = 0;
			sectionMap.put("repeatNumber", repeatNum);
		}

		String crfJSON = loadCRF(projID, frameInstanceID);
		String docListJSON = loadFrameInstanceDocuments(frameInstanceID);

		//update user
		stmt.execute("update " + schema + rq + "user" + rq + " set frame_instance_id = " + frameInstanceID + " where user_name = '" + username + "'");

		conn.close();

		String frameInstanceJSON = "[" + docJSON + "," + crfJSON + "," + docListJSON + "," + frameDataStr + "]";

		return frameInstanceJSON;
	}

	public String loadFrameInstanceDocuments(int frameInstanceID) throws SQLException
	{
		List<Map<String, Object>> docList = new ArrayList<Map<String, Object>>();
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("select document_namespace, document_table, document_id, document_key, document_text_column, document_name from " + schema + "frame_instance_document "
			+ "where frame_instance_id = " + frameInstanceID + " order by document_order");

		while (rs.next()) {
			String docNamespace = rs.getString(1);
			String docTable = rs.getString(2);
			long docID = rs.getLong(3);
			String docKey = rs.getString(4);
			String docTextColumn = rs.getString(5);
			String docName = rs.getString(6);

			Map<String, Object> docMap = new HashMap<String, Object>();
			docMap.put("docNamespace", docNamespace);
			docMap.put("docTable", docTable);
			docMap.put("docKey", docKey);
			docMap.put("docTextColumn", docTextColumn);
			docMap.put("docID", docID);
			docMap.put("docName", docName);
			docList.add(docMap);
		}

		conn.close();

		return gson.toJson(docList);
	}

    public String addAnnotation(int frameInstanceID, String htmlID, String value, int start, int end, String docNamespace, String docTable,
        	long docID, int crfID, String features, boolean add) throws SQLException
	{
    	String ret = "";

		System.out.println("htmlID: " + htmlID);

		String docName = docNamespace + "-" + docTable + "-" + docID;

		value = value.replaceAll("'", "''");

		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			String rq = getReservedQuote(conn);

			//separate slot number from html ID
			int sectionSlotNum = 1;
			int elementSlotNum = 1;
			int index = htmlID.lastIndexOf("_");
			if (index >= 0) {
				int index2 = htmlID.substring(0, index).lastIndexOf("_");
				String slotNumStr = htmlID.substring(index2+1);
				int index3 = slotNumStr.indexOf("_");

				try {
					sectionSlotNum = Integer.parseInt(slotNumStr.substring(0, index3));
					elementSlotNum = Integer.parseInt(slotNumStr.substring(index3+1));
					htmlID = htmlID.substring(0, index2);
				}
				catch(NumberFormatException e)
				{
					//e.printStackTrace();
				}
			}


			//get the slotID and element ID for this html ID and element type
			int slotID = -1;
			int elementID = -1;
			String elementType = "";
			ResultSet rs = stmt.executeQuery("select a.element_id, b.slot_id, d.element_type_name from " + schema + "element_value a, " + schema + "value b, "
				+ schema + "element c, " + schema + "element_type d "
				+ "where b.html_id = '" + htmlID + "' and b.value_id = a.value_id and a.element_id = c.element_id and c.element_type = d.element_type_id");
			if (rs.next()) {
				elementID = rs.getInt(1);
				slotID = rs.getInt(2);
				elementType = rs.getString(3);
			}


			//check if exact value for this element already exists
			int frameSectionSlotNum = -1;
			int frameElementSlotNum = -1;
			String frameDocNamespace = null;
			String frameDocTable = null;
			long frameDocID = -1;
			int frameAnnotID = -1;
			String provenance = null;

			/*
			rs = stmt.executeQuery("select a.section_slot_number, a.element_slot_number, a.document_namespace, a.document_table, a.document_id, a.annotation_id, e.provenance "
				+ "from " + schema + "frame_instance_data a, " + schema + "value b, " + schema + "crf_element c, " + schema + "element_value d, " + schema + "annotation e "
				+ "where c.crf_id = " + crfID + " and c.element_id = d.element_id and d.value_id = b.value_id and a.slot_id = b.slot_id and a.frame_instance_id = " + frameInstanceID + " and "
				+ "b.html_id = '" + htmlID + "' and a.annotation_id = e.id and a.document_namespace = e.document_namespace and a.document_table = e.document_table and a.document_id = e.document_id");
				*/

			boolean update = false;

			String query = "select a.document_namespace, a.document_table, a.document_id, a.annotation_id, b.provenance "
				+ "from " + schema + "frame_instance_data a, " + schema + "annotation b "
				+ "where a.element_id = " + elementID + " and a.frame_instance_id = " + frameInstanceID + " and a.section_slot_number = " + sectionSlotNum + " "
				+ "and a.element_slot_number = " + elementSlotNum + " "
				+ "and a.document_namespace = b.document_namespace "
				+ "and a.document_table = b.document_table and a.document_id = b.document_id and a.annotation_id = b.id";

			if (elementType.equals("checkbox")) {
				query = "select a.document_namespace, a.document_table, a.document_id, a.annotation_id, b.provenance "
					+ "from " + schema + "frame_instance_data a, " + schema + "annotation b "
					+ "where a.slot_id = " + slotID + " and a.frame_instance_id = " + frameInstanceID + " and a.section_slot_number = " + sectionSlotNum + " "
					+ "and a.element_slot_number = " + elementSlotNum + " "
					+ "and a.document_namespace = b.document_namespace "
					+ "and a.document_table = b.document_table and a.document_id = b.document_id and a.annotation_id = b.id";
			}


			rs = stmt.executeQuery(query);

			if (rs.next()) {
				frameDocNamespace = rs.getString(1);
				frameDocTable = rs.getString(2);
				frameDocID = rs.getLong(3);
				frameAnnotID = rs.getInt(4);
				provenance = rs.getString(5);
			}

			//if slot with same section and element slot numbers already has a value, this is an update

			if (frameAnnotID >= 0) {
				update = true;
			}


			//delete old values if this is an update
			if (update && !add) {
				//annot IDs to delete
				List<Map<String, String>> annotInfoList = new ArrayList<Map<String, String>>();

				if (elementType.equals("checkbox")) {
					//checkboxes must delete based on slotID
					rs = stmt.executeQuery("select document_namespace, document_table, document_id, annotation_id from " + schema + "frame_instance_data where frame_instance_id = "
						+ frameInstanceID + " and slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);
					while (rs.next()) {
						String docNamespace2 = rs.getString(1);
						String docTable2 = rs.getString(2);
						String docID2 = rs.getString(3);
						String annotID2 = rs.getString(4);
						Map<String, String> annotMap = new HashMap<String, String>();
						annotMap.put("docNamespace", docNamespace2);
						annotMap.put("docTable", docTable2);
						annotMap.put("docID", docID2);
						annotMap.put("annotID", annotID2);

						annotInfoList.add(annotMap);
					}

					stmt.execute("delete from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID + " and slot_id = " + slotID
						+ " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);
				}
				else {
					//delete based on elementID
					rs = stmt.executeQuery("select document_namespace, document_table, document_id, annotation_id from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID
						+ " and element_id = " + elementID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);
					while (rs.next()) {
						String docNamespace2 = rs.getString(1);
						String docTable2 = rs.getString(2);
						String docID2 = rs.getString(3);
						String annotID2 = rs.getString(4);
						Map<String, String> annotMap = new HashMap<String, String>();
						annotMap.put("docNamespace", docNamespace2);
						annotMap.put("docTable", docTable2);
						annotMap.put("docID", docID2);
						annotMap.put("annotID", annotID2);

						annotInfoList.add(annotMap);
					}

					stmt.execute("delete from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID + " and element_id = " + elementID
						+ " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);
				}

				for (Map<String, String> annotMap : annotInfoList) {
					stmt.execute("delete from " + schema + "annotation where document_namespace = '" + annotMap.get("docNamespace") + "' and document_table = '" + annotMap.get("docTable") + "' "
							+ "and document_id = " + annotMap.get("docID") + " and id = " + annotMap.get("annotID"));
				}
			}



			/*
			boolean radioUpdate = false;

			//not an update of an existing slot, but a radio button has been set to a new value
			if (elementType.equals("radio")) {
				rs = stmt.executeQuery("select annotation_id from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID + " and element_id = " + elementID);
				List<Integer> annotList = new ArrayList<Integer>();
				while (rs.next()) {
					annotList.add(rs.getInt(1));
				}

				if (annotList.size() > 0) {
					if (!add) {
						StringBuilder strBlder = new StringBuilder();
						for (int annotID : annotList) {
							if (strBlder.length() > 0)
								strBlder.append(",");
							strBlder.append(annotID);
						}
						//stmt.execute("delete from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID + " and element_id = " + elementID);
						stmt.execute("delete from " + schema + "annotation where id in (" + strBlder.toString() + ") and document_namespace = '" + docNamespace + "' and document_table = '" + docTable + "' "
							+ "and document_id = " + docID);
					}

					radioUpdate = true;
				}
			}
			*/



			//generate a new annotation ID
			int annotID = -1;
			rs = stmt.executeQuery("select max(id) from " + schema + "annotation where document_namespace = '" + docNamespace + "' "
				+ "and document_table = '" + docTable + "' and document_id = " + docID);
			System.out.println("\nselect max(id) from " + schema + "annotation where document_namespace = '" + docNamespace + "' "
				+ "and document_table = '" + docTable + "' and document_id = " + docID + "\n");
			if (rs.next())
				annotID = rs.getInt(1) + 1;

			System.out.println("new annot ID: " + annotID);

			//get annotation type
			String annotType = "";
			rs = stmt.executeQuery("select d.annotation_type "
				+ "from " + schema + "value a, " + schema + "crf_element b, " + schema + "element_value c, " + schema + "slot d "
				+ "where b.crf_id = " + crfID + " and b.element_id = c.element_id and c.value_id = a.value_id and "
				+ "a.html_id = '" + htmlID + "' "
				+ "and a.slot_id = d.slot_id");
			System.out.println("select d.annotation_type "
				+ "from " + schema + "value a, " + schema + "crf_element b, " + schema + "element_value c, " + schema + "slot d "
				+ "where b.crf_id = " + crfID + " and b.element_id = c.element_id and c.value_id = a.value_id and "
				+ "a.html_id = '" + htmlID + "' "
				+ "and a.slot_id = d.slot_id");
			if (rs.next()) {
				annotType = rs.getString(1);
			}


			/*
			if (update && !add) {
				stmt.execute("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
					+ "document_table = '" + docTable + "', document_id = " + docID
   					+ " where frame_instance_id = " + frameInstanceID + " and "
   					+ "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);

   				System.out.println("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
					+ "document_table = '" + docTable + "', document_id = " + docID
   					+ " where frame_instance_id = " + frameInstanceID + " and "
   					+ "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);

   				stmt.execute("update " + schema + "annotation set start = " + start + ", " + rq + "end" + rq + " = " + end + ", value = '" + value + "', "
   					+ "document_namespace = '" + docNamespace + "', "
   					+ "document_table = '" + docTable + "', document_id = " + docID + ", features = '" + features + "'"
   					+ " where document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
   					+ "document_id = " + frameDocID + " and id = " + frameAnnotID);

   				System.out.println("update " + schema + "annotation set start = " + start + ", " + rq + "end" + rq + " = " + end + ", value = '" + value + "', "
					+ "document_namespace = '" + docNamespace + "', "
					+ "document_table = '" + docTable + ", document_id = " + docID + ", features = '" + features + "'"
					+ " where document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
					+ "document_id = " + frameDocID + " and id = " + frameAnnotID);

   				stmt.execute("delete from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID + " and element_id = " + elementID);
				stmt.execute("delete from " + schema + "annotation where id in (" + strBlder.toString() + ") and document_namespace = '" + docNamespace + "' and document_table = '" + docTable + "' "
					+ "and document_id = " + docID);
			}
			else if (radioUpdate && !add) {
				stmt.execute("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
						+ "document_table = '" + docTable + "', document_id = " + docID + ", annotation_id = " + annotID
	   					+ " where frame_instance_id = " + frameInstanceID + " and "
	   					+ "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);

   				System.out.println("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
					+ "document_table = '" + docTable + "', document_id = " + docID
   					+ " where frame_instance_id = " + frameInstanceID + " and "
   					+ "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);

				stmt.execute("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, annotation_type, start, "
						+ rq + "end" + rq + ", value, features, provenance) "
	    				+ "values "
	    				+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + annotType + "'," + start + "," + end + ",'"
	    				+ value + "', '" + features + "', 'validation-tool')");

				System.out.println("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, annotation_type, start, "
    					+ rq + "end" + rq + ", value, features, provenance) "
        				+ "values "
        				+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + annotType + "'," + start + "," + end + ",'"
        				+ value + "', '" + features + "', 'validation-tool')");
			}
			else {
				stmt.execute("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, annotation_type, start, "
					+ rq + "end" + rq + ", value, features, provenance) "
    				+ "values "
    				+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + annotType + "'," + start + "," + end + ",'"
    				+ value + "', '" + features + "', 'validation-tool')");

				System.out.println("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, annotation_type, start, "
    					+ rq + "end" + rq + ", value, features, provenance) "
        				+ "values "
        				+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + annotType + "'," + start + "," + end + ",'"
        				+ value + "', '" + features + "', 'validation-tool')");

				stmt.execute("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, "
					+ "document_table, document_id, annotation_id, annotation_namespace, element_id) "
		    		+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "'," + sectionSlotNum + "," + elementSlotNum + ",'" + docNamespace + "', '" + docTable
		    		+ "', " + docID + "," + annotID + ",'namespace'," + elementID + ")");

		    	System.out.println("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, "
	    			+ "document_table, document_id, annotation_id, annotation_namespace, element_id) "
		    		+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "'," + sectionSlotNum + "," + elementSlotNum + ",'" + docNamespace + "', '" + docTable
		    		+ "', " + docID + "," + annotID + ",'namespace'," + elementID + ")");
			}
			*/



			stmt.execute("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, annotation_type, start, "
				+ rq + "end" + rq + ", value, features, provenance) "
				+ "values "
				+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + annotType + "'," + start + "," + end + ",'"
				+ value + "', '" + features + "', 'validation-tool')");

			System.out.println("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, annotation_type, start, "
					+ rq + "end" + rq + ", value, features, provenance) "
    				+ "values "
    				+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + annotType + "'," + start + "," + end + ",'"
    				+ value + "', '" + features + "', 'validation-tool')");

			stmt.execute("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, "
				+ "document_table, document_id, annotation_id, provenance, element_id) "
	    		+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "'," + sectionSlotNum + "," + elementSlotNum + ",'" + docNamespace + "', '" + docTable
	    		+ "', " + docID + "," + annotID + ",'namespace'," + elementID + ")");

	    	System.out.println("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, "
    			+ "document_table, document_id, annotation_id, provenance, element_id) "
	    		+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "'," + sectionSlotNum + "," + elementSlotNum + ",'" + docNamespace + "', '" + docTable
	    		+ "', " + docID + "," + annotID + ",'namespace'," + elementID + ")");

			ret = getFrameData(frameInstanceID);

			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "[" + ret + "]";
	}

    public String getFrameData(int frameInstanceID) throws SQLException
    {
		List<Map<String, Object>> frameList = new ArrayList<Map<String, Object>>();
		Map<String, List<Map<String, Object>>> highlightRangeMap = new HashMap<String, List<Map<String, Object>>>();

		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		String rq = getReservedQuote(conn);

		ResultSet rs = stmt.executeQuery("select a.value, a.section_slot_number, a.element_slot_number, a.element_id, b.html_id, c.id, "
				+ "c.start, c." + rq + "end" + rq + ", c.annotation_type, "
				+ "a.document_namespace, a.document_table, a.document_id, c.features, d.html_id, e.element_type_name "
				+ "from " + schema + "frame_instance_data a, " + schema + "element b, " + schema + "annotation c, " + schema + "value d, " + schema + "element_type e "
				+ "where a.element_id = b.element_id and a.annotation_id = c.id and a.document_namespace = c.document_namespace and a.document_table = c.document_table and a.document_id = c.document_id and "
				+ "a.frame_instance_id = " + frameInstanceID + " and b.element_type = e.element_type_id and a.slot_id = d.slot_id "
				+ "order by a.element_id, a.section_slot_number, a.element_slot_number");

		while (rs.next()) {
			String value = rs.getString(1);
			int sectionSlotNum = rs.getInt(2);
			int elementSlotNum = rs.getInt(3);
			int elementID = rs.getInt(4);
			String elementHTMLID = rs.getString(5);
			int annotID = rs.getInt(6);
			int start = rs.getInt(7);
			int end = rs.getInt(8);
			String annotType = rs.getString(9);
			String docNamespace = rs.getString(10);
			String docTable = rs.getString(11);
			long docID = rs.getLong(12);
			String features = rs.getString(13);
			String valueHTMLID = rs.getString(14);
			String elementType = rs.getString(15);

			Map<String, Object> frameMap = new HashMap<String, Object>();
			frameMap.put("frameInstanceID", frameInstanceID);
			frameMap.put("value", value);
			frameMap.put("section_slot_number", sectionSlotNum);
			frameMap.put("element_slot_number", elementSlotNum);

			//if (slotNum > 1) {
			String elementIDStr = elementID + "_" + sectionSlotNum + "_" + elementSlotNum;
			elementHTMLID += "_" + sectionSlotNum + "_" + elementSlotNum;
			valueHTMLID += "_" + sectionSlotNum + "_" + elementSlotNum;
			//}

			frameMap.put("elementID", elementIDStr);
			frameMap.put("elementHTMLID", elementHTMLID);
			frameMap.put("valueHTMLID", valueHTMLID);
			//frameMap.put("start", start);
			//frameMap.put("end", end);
			frameMap.put("docNamespace", docNamespace);
			frameMap.put("docTable", docTable);
			frameMap.put("docID", docID);
			frameMap.put("features", features);
			frameMap.put("elementType", elementType);

			frameList.add(frameMap);

			Map<String, Object> rangeMap = new HashMap<String, Object>();
			rangeMap.put("annotID", annotID);
			rangeMap.put("start", start);
			rangeMap.put("end", end);
			rangeMap.put("annotID", annotType);
			rangeMap.put("docNamespace", docNamespace);
			rangeMap.put("docTable", docTable);
			rangeMap.put("docID", docID);

			String htmlID = elementHTMLID;

			if (elementType.equals("checkbox"))
				htmlID = valueHTMLID;

			List<Map<String, Object>> rangeList = highlightRangeMap.get(htmlID);
			if (rangeList == null) {
				rangeList = new ArrayList<Map<String, Object>>();
				highlightRangeMap.put(htmlID, rangeList);
			}

			boolean inserted = false;
			int index = 0;
			for (Map<String, Object> rangeMap2 : rangeList) {
				if (((Integer) rangeMap2.get("start")) > ((Integer) rangeMap.get("start"))) {
					rangeList.add(index, rangeMap);
					inserted = true;
					break;
				}

				index++;
			}

			if (!inserted)
				rangeList.add(rangeMap);
		}

		String ret = gson.toJson(frameList) + "," + gson.toJson(highlightRangeMap);

		stmt.close();
		conn.close();

		return ret;
    }

	/*
    public void addAnnotation(int frameInstanceID, String htmlID, String value, int start, int end, String docNamespace, String docTable,
    	long docID, int crfID, String features) throws SQLException
	{
		System.out.println("htmlID: " + htmlID);

		String docName = docNamespace + "-" + docTable + "-" + docID;

		//schema = (String) Cache.get("schemaName");

		value = value.replaceAll("'", "''");

		try {
			//int docID = (Integer) Cache.get("docID");
			//int crfID = (Integer) Cache.get("crfID");

			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			String rq = getReservedQuote(conn);

			//separate slot number from html ID
			int sectionSlotNum = 1;
			int elementSlotNum = 1;
			int index = htmlID.lastIndexOf("_");
			if (index >= 0) {
				int index2 = htmlID.substring(0, index).lastIndexOf("_");
				String slotNumStr = htmlID.substring(index2+1);
				int index3 = slotNumStr.indexOf("_");

				try {
					sectionSlotNum = Integer.parseInt(slotNumStr.substring(0, index3));
					elementSlotNum = Integer.parseInt(slotNumStr.substring(index3+1));
					htmlID = htmlID.substring(0, index2);
				}
				catch(NumberFormatException e)
				{
					//e.printStackTrace();
				}
			}


			//get the slotID and element ID for this html ID
			int slotID = -1;
			int elementID = -1;
			ResultSet rs = stmt.executeQuery("select a.element_id, b.slot_id from " + schema + "element_value a, " + schema + "value b "
				+ "where b.html_id = '" + htmlID + "' and b.value_id = a.value_id");
			if (rs.next()) {
				elementID = rs.getInt(1);
				slotID = rs.getInt(2);
			}


			//check if exact value for this element already exists in the form
			//if so, get it's slot number, slotID, annotation ID, element ID, and provenance
			int frameSectionSlotNum = 1;
			int frameElementSlotNum = 1;
			String frameDocNamespace = "";
			String frameDocTable = "";
			long frameDocID = -1;
			int frameAnnotID = -1;
			rs = stmt.executeQuery("select a.section_slot_number, a.element_slot_number, a.document_namespace, a.document_table, a.document_id, a.annotation_id, e.provenance "
				+ "from " + schema + "frame_instance_data a, " + schema + "value b, " + schema + "crf_element c, " + schema + "element_value d, " + schema + "annotation e "
				+ "where c.crf_id = " + crfID + " and c.element_id = d.element_id and d.value_id = b.value_id and a.slot_id = b.slot_id and a.frame_instance_id = " + frameInstanceID + " and "
				+ "b.html_id = '" + htmlID + "' and a.annotation_id = e.id and a.document_namespace = e.document_namespace and a.document_table = e.document_table and a.document_id = e.document_id");

			//rs = stmt.executeQuery("select annotation_id, ");

			String provenance = null;
			while (rs.next()) {
				frameSectionSlotNum = rs.getInt(1);
				frameElementSlotNum = rs.getInt(2);

				//check if this is the same slot number (for repeated sections of the form)
				if (sectionSlotNum == frameSectionSlotNum && elementSlotNum == frameElementSlotNum) {
					//slotID = rs.getInt(1);
					frameDocNamespace = rs.getString(3);
					frameDocTable = rs.getString(4);
					frameDocID = rs.getLong(5);
					frameAnnotID = rs.getInt(6);
					//elementID = rs.getInt(4);
					provenance = rs.getString(7);
					break;
				}
			}


			//if element value is already being used (exists), slotID will be >= 0
			boolean update = false;
			boolean validUpdate = false;

			if (slotID >= 0 && sectionSlotNum == frameSectionSlotNum && elementSlotNum == frameElementSlotNum) {
				//check if the existing value has a provenance or if it was created by the validation tool
				//if no provenance or it was created by the tool, update the value (namespace, table, document id, start and end indexes, value, etc)
			    if (provenance == null || provenance.equals("validation-tool"))
			    	update = true;
			    //the existing value was created by another method, so override it because the tool has the last say
			    else
			    	validUpdate = true;
			}
			else {
				frameSectionSlotNum = 1;
				frameElementSlotNum = 1;
			}


			//generate an annotation ID if this is not an update
			int annotID = 0;
			if (!update && !validUpdate) {
				rs = stmt.executeQuery("select max(id) from " + schema + "annotation where document_id = " + docID);
				if (rs.next())
					annotID = rs.getInt(1) + 1;
			}


			//set the slot ID if it hasn't been set yet
			//get the annotation type for this element
			String annotType = "";
			rs = stmt.executeQuery("select a.slot_id, d.annotation_type "
				+ "from " + schema + "value a, " + schema + "crf_element b, " + schema + "element_value c, " + schema + "slot d "
				+ "where b.crf_id = " + crfID + " and b.element_id = c.element_id and c.value_id = a.value_id and "
				+ "a.html_id = '" + htmlID + "' "
				+ "and a.slot_id = d.slot_id");
			System.out.println("select a.slot_id, d.annotation_type "
				+ "from " + schema + "value a, " + schema + "crf_element b, " + schema + "element_value c, " + schema + "slot d "
				+ "where b.crf_id = " + crfID + " and b.element_id = c.element_id and c.value_id = a.value_id and "
				+ "a.html_id = '" + htmlID + "' "
				+ "and a.slot_id = d.slot_id");
			if (rs.next()) {
				if (slotID == -1)
					slotID = rs.getInt(1);
				annotType = rs.getString(2);
			}

			//check if this an update of the same slot but different annotation
			//a element's value is being changed
			boolean sameElementUpdate = false;

			rs = stmt.executeQuery("select a.element_id, a.section_slot_number, a.element_slot_number "
				+ "from " + schema + "frame_instance_data a, " + schema +"value b, " + schema + "element_value c, "
				//+ schema + "element_value b, " + schema + "element_value c, "
				//+ schema + "value d, " + schema + "value e, "
				+ schema + "annotation f "
				+ "where b.slot_id = " + slotID + " and a.frame_instance_id = " + frameInstanceID
				+ " and b.value_id = c.value_id  and c.element_id = a.element_id"
				//+ "a.slot_id = d.slot_id and b.value_id = d.value_id and b.element_id = c.element_id "
				+ " and a.annotation_id = f.id and a.document_namespace = f.document_namespace and a.document_table = f.document_table and "
				+ "a.document_id = f.document_id and f.provenance = 'validation-tool'");
			int frameElementID = -1;
			frameSectionSlotNum = -1;
			frameElementSlotNum = -1;
			if (rs.next()) {
				frameElementID = rs.getInt(1);
				frameSectionSlotNum = rs.getInt(2);
				frameElementSlotNum = rs.getInt(3);
			}

			//if this is an update of the same element, get the annotationID of the current value
			if (frameElementID >= 0 && sectionSlotNum == frameSectionSlotNum && elementSlotNum == frameElementSlotNum) {
				sameElementUpdate = true;

				rs = stmt.executeQuery("select document_namespace, document_table, document_id, annotation_id, slot_id from " + schema + "frame_instance_data "
					+ "where frame_instance_id = " + frameInstanceID + " and element_id = " + frameElementID);
				if (rs.next()) {
					frameDocNamespace = rs.getString(1);
					frameDocTable = rs.getString(2);
					frameDocID = rs.getLong(3);
					frameAnnotID = rs.getInt(4);
				}
			}

			//check if this is a new value overriding an existing value for the same element
			if (sameElementUpdate) {
				//update the frame instance data table
				//always update the document info since the new annotation may come from a different document
				stmt.execute("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
					+ "document_table = '" + docTable + "', document_id = " + docID + ", annotation_id = " + annotID + ", slot_id = " + slotID
					+ " where frame_instance_id = " + frameInstanceID + " and "
					+ "element_id = " + frameElementID + " and document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
					+ "document_id = " + frameDocID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);

				System.out.println("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
						+ "document_table = '" + docTable + "', document_id = " + docID + ", annotation_id = " + annotID + ", slot_id = " + slotID
						+ " where frame_instance_id = " + frameInstanceID + " and "
						+ "element_id = " + frameElementID + " and document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
						+ "document_id = " + frameDocID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum);

				//update the annotation with the new value
				//always use a new annotation ID since the annotation may come from a different document
				stmt.execute("update " + schema + "annotation set id = " + annotID + ", start = " + start + ", " + rq + "end" + rq + " = " + end + ", "
					+ "value = '" + value + "', document_namespace = '" + docNamespace + "', "
					+ "document_table = '" + docTable + "', document_id = " + docID + ", features = '" + features + "'"
					+ " where document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
					+ "document_id = " + frameDocID + " and id = " + frameAnnotID);

				System.out.println("update " + schema + "annotation set id = " + annotID + ", start = " + start + ", " + rq + "end" + rq + " = " + end + ", "
						+ "value = '" + value + "', document_namespace = '" + docNamespace + "', "
						+ "document_table = '" + docTable + "', document_id = " + docID + ", features = '" + features + "'"
						+ " where document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
						+ "document_id = " + frameDocID + " and id = " + frameAnnotID);
			}
			//this is not an update
			else if (!update) {
				//create a new annotation because none exist from the validation tool for this element
				stmt.execute("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, document_name, annotation_type, start, " + rq + "end" + rq + ", value, features, provenance) "
					+ "values "
					+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + docName + "', '" + annotType + "'," + start + "," + end + ",'" + value + "', '" + features + "', 'validation-tool')");

				System.out.println("insert into " + schema + "annotation (id, document_namespace, document_table, document_id, document_name, annotation_type, start, " + rq + "end" + rq + ", value, features, provenance) "
						+ "values "
						+ "(" + annotID + ",'" + docNamespace + "','" + docTable + "'," + docID + ",'" + docName + "', '" + annotType + "'," + start + "," + end + ",'" + value + "', '" + features + "', 'validation-tool')");

			    //update the frame_instance_data table because the tool is overriding another method
			    if (validUpdate) {
			    	stmt.execute("update " + schema + "frame_instance_data set value = '" + value + "', annotation_id = " + annotID + ", document_namespace = '" + docNamespace + "', "
			    		+ "document_table = '" + docTable + ", document_id = " + docID
					     + " where frame_instance_id = " + frameInstanceID + " and "
					     + "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum
					     + " and document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
					     + "document_id = " + frameDocID);
			    	System.out.println("update " + schema + "frame_instance_data set value = '" + value + "', annotation_id = " + annotID + ", document_namespace = '" + docNamespace + "', "
				    		+ "document_table = '" + docTable + ", document_id = " + docID
						     + " where frame_instance_id = " + frameInstanceID + " and "
						     + "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum
						     + " and document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
						     + "document_id = " + frameDocID);
			    }
			    //otherwise, create a new entry in the frame instance data table
			    else {
			    	stmt.execute("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, document_table, document_id, annotation_id, annotation_namespace, element_id) "
				    		+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "'," + sectionSlotNum + "," + elementSlotNum + ",'" + docNamespace + "', '" + docTable + "', " + docID + "," + annotID + ",'namespace'," + elementID + ")");
			    	System.out.println("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, document_table, document_id, annotation_id, annotation_namespace, element_id) "
				    		+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "'," + sectionSlotNum + "," + elementSlotNum + ",'" + docNamespace + "', '" + docTable + "', " + docID + "," + annotID + ",'namespace'," + elementID + ")");
			    }
			}
			//otherwise, this is an update
			else {
				stmt.execute("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
					     + "document_table = '" + docTable + "', document_id = " + docID
					+ " where frame_instance_id = " + frameInstanceID + " and "
					+ "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum
					+ " and document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
					+ "document_id = " + frameDocID);

				System.out.println("update " + schema + "frame_instance_data set value = '" + value + "', document_namespace = '" + docNamespace + "', "
					     + "document_table = '" + docTable + "', document_id = " + docID
					+ " where frame_instance_id = " + frameInstanceID + " and "
					+ "slot_id = " + slotID + " and section_slot_number = " + sectionSlotNum + " and element_slot_number = " + elementSlotNum
					+ " and document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
					+ "document_id = " + frameDocID);

				stmt.execute("update " + schema + "annotation set start = " + start + ", " + rq + "end" + rq + " = " + end + ", value = '" + value + "', "
					+ "document_namespace = '" + docNamespace + "', "
					+ "document_table = '" + docTable + ", document_id = " + docID + ", features = '" + features + "'"
					+ " where document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
					+ "document_id = " + frameDocID + " and id = " + frameAnnotID);

				System.out.println("update " + schema + "annotation set start = " + start + ", " + rq + "end" + rq + " = " + end + ", value = '" + value + "', "
						+ "document_namespace = '" + docNamespace + "', "
						+ "document_table = '" + docTable + ", document_id = " + docID + ", features = '" + features + "'"
						+ " where document_namespace = '" + frameDocNamespace + "' and document_table = '" + frameDocTable + "' and "
						+ "document_id = " + frameDocID + " and id = " + frameAnnotID);
			}

			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	*/

	private String getReservedQuote(Connection conn) throws SQLException
	{
		String rq = "`";
		String dbType = conn.getMetaData().getDatabaseProductName();
		if (dbType.equals("Microsoft SQL Server")) {
			rq = "\"";
		}

		return rq;
	}

	public String removeElement(int frameInstanceID, int elementID, String htmlID) throws SQLException
	{
		return clearElement(frameInstanceID, elementID, htmlID, true);
	}

	public String clearElement(int frameInstanceID, int elementID, String htmlID) throws SQLException
	{
		return clearElement(frameInstanceID, elementID, htmlID, false);
	}

	public String clearElement(int frameInstanceID, int elementID, String htmlID, boolean remove) throws SQLException
	{
		int index = htmlID.lastIndexOf("_");
		int index2 = htmlID.substring(0, index).lastIndexOf("_");

		int sectionSlotNum = Integer.parseInt(htmlID.substring(index2+1, index));
		int elementSlotNum = Integer.parseInt(htmlID.substring(index+1));
		htmlID = htmlID.substring(0, index2);

		System.out.println("clearing: " + elementID + " htmlID: " + htmlID + " sectionSlotNum: " + sectionSlotNum + " elementSlotNum: " + elementSlotNum);


		/*
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();


		int elementID = -1;
		ResultSet rs = stmt.executeQuery("select a.element_id from " + schema + "element where html_id = '" + htmlID + "'");
		if (rs.next())
			elementID = rs.getInt(1);
			*/

		return clearElement(frameInstanceID, elementID, sectionSlotNum, elementSlotNum, remove);

		//stmt.close();
		//conn.close();
	}

	public String clearElement(int frameInstanceID, int elementID, int sectionSlotNum, int elementSlotNum, boolean remove) throws SQLException
	{
		System.out.println(elementID);

   		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		System.out.println("frameInstanceID: " + frameInstanceID + " elementID: " + elementID + " sectionSlotNum: " + sectionSlotNum + " elementSlotNum: " + elementSlotNum);

		PreparedStatement pstmt = conn.prepareStatement("update " + schema + "annotation set score = 0.0 where document_namespace = ? and document_table = ? and document_id = ? and id = ?");
		PreparedStatement pstmt2 = conn.prepareStatement("delete from " + schema + "annotation where document_namespace = ? and document_table = ? and document_id = ? and id = ?");

		ResultSet rs = stmt.executeQuery("select a.document_namespace, a.document_table, a.document_id, a.annotation_id, b.provenance "
			+ "from " + schema + "frame_instance_data a, " + schema + "annotation b "
			+ "where a.frame_instance_id = " + frameInstanceID + " and a.element_id = " + elementID + " and a.section_slot_number = " + sectionSlotNum + " and a.element_slot_number = " + elementSlotNum
			+ " and b.id = a.annotation_id");

		while (rs.next()) {
			String docNamespace = rs.getString(1);
			String docTable = rs.getString(2);
			long docID = rs.getLong(3);
			int annotID = rs.getInt(4);
			String provenance = rs.getString(5);

			if (!provenance.equals("validation-tool")) {
				pstmt.setString(1, docNamespace);
				pstmt.setString(2, docTable);
				pstmt.setLong(3, docID);
				pstmt.setInt(4, annotID);
				pstmt.execute();
			}
			else {
				pstmt2.setString(1, docNamespace);
				pstmt2.setString(2, docTable);
				pstmt2.setLong(3, docID);
				pstmt2.setInt(4, annotID);
				pstmt2.execute();
			}
		}

		stmt.execute("delete from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID + " and section_slot_number = "
			+ sectionSlotNum + " and element_slot_number = " + elementSlotNum + " and element_id = " + elementID);

		if (remove)
			stmt.execute("update " + schema + "frame_instance_data set element_slot_number = element_slot_number - 1 "
				+ "where element_slot_number > " + elementSlotNum + " and frame_instance_id = " + frameInstanceID
				+ " and element_id = " + elementID + " and section_slot_number = " + sectionSlotNum);

		String ret = getFrameData(frameInstanceID);

		pstmt.close();
		pstmt2.close();
		stmt.close();
		conn.close();

		return "[" + ret + "]";
	}

	public String clearValue(int frameInstanceID, String htmlID) throws SQLException
	{
		System.out.println("clear value: " + htmlID);

   		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		//separate slot number from html ID
		int sectionSlotNum = 1;
		int elementSlotNum = 1;
		int index = htmlID.lastIndexOf("_");
		if (index >= 0) {
			int index2 = htmlID.substring(0, index).lastIndexOf("_");
			String slotNumStr = htmlID.substring(index2+1);
			int index3 = slotNumStr.indexOf("_");

			try {
				sectionSlotNum = Integer.parseInt(slotNumStr.substring(0, index3));
				elementSlotNum = Integer.parseInt(slotNumStr.substring(index3+1));
				htmlID = htmlID.substring(0, index2);
			}
			catch(NumberFormatException e)
			{
				//e.printStackTrace();
			}
		}

		//get the slotID and element ID for this html ID and element type
		int slotID = -1;
		int elementID = -1;
		String elementType = "";
		ResultSet rs = stmt.executeQuery("select a.element_id, b.slot_id, d.element_type_name from " + schema + "element_value a, " + schema + "value b, "
			+ schema + "element c, " + schema + "element_type d "
			+ "where b.html_id = '" + htmlID + "' and b.value_id = a.value_id and a.element_id = c.element_id and c.element_type = d.element_type_id");
		if (rs.next()) {
			elementID = rs.getInt(1);
			slotID = rs.getInt(2);
			elementType = rs.getString(3);
		}

		System.out.println("frameInstanceID: " + frameInstanceID + " elementID: " + elementID + " slotID: " + slotID + " sectionSlotNum: " + sectionSlotNum + " elementSlotNum: " + elementSlotNum);

		//PreparedStatement pstmt = conn.prepareStatement("update " + schema + "annotation set score = 0.0 where document_namespace = ? and document_table = ? and document_id = ? and id = ?");
		//PreparedStatement pstmt2 = conn.prepareStatement("delete from " + schema + "annotation where document_namespace = ? and document_table = ? and document_id = ? and id = ?");

		rs = stmt.executeQuery("select document_namespace, document_table, document_id, annotation_id "
			+ "from " + schema + "frame_instance_data "
			+ "where frame_instance_id = " + frameInstanceID + " and element_id = " + elementID + " and element_slot_number = " + elementSlotNum
			+ " and slot_id = " + slotID);

		String docNamespace = "";
		String docTable = "";
		long docID = -1;
		int annotID = -1;

		Statement stmt2 = conn.createStatement();

		while (rs.next()) {
			docNamespace = rs.getString(1);
			docTable = rs.getString(2);
			docID = rs.getLong(3);
			annotID = rs.getInt(4);

			stmt2.execute("delete from " + schema + "annotation where document_namespace = '" + docNamespace + "' and document_table = '"
				+ docTable + "' and document_id = " + docID + " and id = " + annotID);
		}

		stmt2.execute("delete from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID + " and section_slot_number = "
			+ sectionSlotNum + " and element_slot_number = " + elementSlotNum + " and element_id = " + elementID + " and slot_id  = " + slotID);

		String ret = getFrameData(frameInstanceID);

		//pstmt.close();
		//pstmt2.close();
		stmt.close();
		stmt2.close();
		conn.close();

		return "[" + ret + "]";
	}

	public void clearSection(int frameInstanceID, String sectionName, int sectionSlotNum) throws SQLException
	{
		System.out.println("clearsection: " + frameInstanceID + ", " + sectionName + ", " + sectionSlotNum);

		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		int sectionID = -1;
		ResultSet rs = stmt.executeQuery("select section_id from " + schema + "crf_section where name = '" + sectionName + "'");
		if (rs.next()) {
			sectionID = rs.getInt(1);
		}

		rs = stmt.executeQuery("select a.element_id, a.element_slot_number from " + schema + "frame_instance_data a, " + schema + "element b "
			+ "where b.section_id = " + sectionID + " and a.element_id = b.element_id");
		while (rs.next()) {
			int elementID = rs.getInt(1);
			int elementSlotNum = rs.getInt(2);
			clearElement(frameInstanceID, elementID, sectionSlotNum, elementSlotNum, true);
		}

		stmt.execute("update " + schema + "frame_instance_data set section_slot_number = section_slot_number - 1 "
			+ "where frame_instance_id = " + frameInstanceID + " and section_slot_number > " + sectionSlotNum);

		conn.close();
	}

	public void clearAll(int frameInstanceID) throws SQLException
	{
		System.out.println("clear all: " + frameInstanceID);

		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		List<String> docList = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery("select distinct document_namespace, document_table, document_id, annotation_id from " + schema + "frame_instance_data "
			+ "where frame_instance_id = " + frameInstanceID);
		while (rs.next()) {
			String docNamespace = rs.getString(1);
			String docTable = rs.getString(2);
			long docID = rs.getLong(3);
			int annotID = rs.getInt(4);
			docList.add("{\"docNamespace\":\"" + docNamespace + "\",\"docTable\":\"" + docTable + "\",\"docID\":" + docID + ",\"annotID\":" + annotID + "}");
		}


		for (String docStr : docList) {

			Map<String, Object> docMap = new HashMap<String, Object>();
			docMap = gson.fromJson(docStr, docMap.getClass());
			String docNamespace = (String) docMap.get("docNamespace");
			String docTable = (String) docMap.get("docTable");
			long docID = ((Double) docMap.get("docID")).longValue();
			int annotID = ((Double) docMap.get("annotID")).intValue();

			stmt.execute("delete from " + schema + "annotation where document_namespace = '" + docNamespace +
				"' and document_table = '" + docTable + "' and document_id = " + docID + " and id = " + annotID);
		}

		stmt.execute("delete from " + schema + "frame_instance_data where frame_instance_id = " + frameInstanceID);
		stmt.execute("delete from " + schema + "frame_instance_section_repeat where frame_instance_id = " + frameInstanceID);
		stmt.execute("delete from " + schema + "frame_instance_element_repeat where frame_instance_id = " + frameInstanceID);

		for (Map<String, Object> sectionMap : sectionList) {
			sectionMap.put("repeatNumber", 0);
		}

		stmt.close();
		conn.close();
	}

	public void addDocumentHistory(int frameInstanceID, String docNamespace, String docTable, long docID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("select count(*) from " + schema + "frame_instance_document_history "
		     + "where frame_instance_id = " + frameInstanceID + " and document_namespace = '" + docNamespace + "' and document_table = '" + docTable + "' and document_id = " + docID);

		int count = 0;

		if (rs.next()) {
		    count = rs.getInt(1);
		}

		if (count == 0)
		    stmt.execute("insert into " + schema + "frame_instance_document_history (frame_instance_id, document_namespace, document_table, document_id) "
		      + "values (" + frameInstanceID + ",'" + docNamespace + "','" + docTable + "'," + docID + ")");

		stmt.close();
		conn.close();
	}

	public Map<String, Boolean> getDocumentHistory(int frameInstanceID) throws SQLException
	{
		Map<String, Boolean> docMap = new HashMap<String, Boolean>();

		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("select document_namespace, document_table, document_id from " + schema + "frame_instance_document_history "
				+ "where frame_instance_id = " + frameInstanceID);

		while(rs.next()) {
			docMap.put("{\"docNamespace\":\"" + rs.getString(1) + "\",\"docTable\":\"" + rs.getString(2) + "\",\"docID\":" + rs.getLong(3) + "}", true);
		}

		stmt.close();
		conn.close();

		return docMap;
	}

	public void clearDocumentHistory(int frameInstanceID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		stmt.execute("delete from " + schema + "frame_instance_document_history where frame_instance_id = " + frameInstanceID);

		stmt.close();
		conn.close();
	}

	public List<Map<String, Object>> getDocumentAnnotations(String docNamespace, String docTable, long docID, double annotThreshold, int crfID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		String rq = getReservedQuote(conn);

		//get list of crf annotations
		List<String> crfAnnotList = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery("select distinct a.annotation_type from " + schema + "slot a, " + schema + "frame_slot b, " + schema + "crf c "
			+ "where c.crf_id = " + crfID + " and c.frame_id = b.frame_id and b.slot_id = a.slot_id");
		while (rs.next()) {
			crfAnnotList.add(rs.getString(1));
		}

		StringBuilder strBlder = new StringBuilder();
		for (String crfAnnot : crfAnnotList) {
			if (strBlder.length() > 0)
				strBlder.append(",");
			strBlder.append("'" + crfAnnot + "'");
		}

		strBlder.insert(0, "(");
		strBlder.append(")");
		System.out.println("getDocumentAnnotation: strBlder=" + strBlder.toString());
		//get annotations for these documents
		List<Map<String, Object>> annotList = new ArrayList<Map<String, Object>>();
		//rs = stmt.executeQuery("select distinct start, " + rq + "end" + rq + ", annotation_type from " + schema + "annotation where document_namespace = '" + docNamespace + "' and "
		rs = stmt.executeQuery("select start, " + rq + "end" + rq + ", annotation_type from "
				+ schema + "annotation where document_namespace = '" + docNamespace + "' and "
				+ "document_table = '" + docTable + "' and document_id = " + docID
				+ " and score > " + annotThreshold + " and annotation_type in "
				+ strBlder.toString() + " order by start");

		while (rs.next()) {
			long start = rs.getLong(1);
			long end = rs.getLong(2);
			String annotType = rs.getString(3);
			Map<String, Object> annot = new HashMap<String, Object>();
			annot.put("start", start);
			annot.put("end", end);
			annot.put("annotType", annotType);

			annotList.add(annot);
		}

		stmt.close();
		conn.close();

		return annotList;
	}

	public String fillSlot(int frameInstanceID, String docNamespace, String docTable, long docID, String slotName, int start, int end) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		//get slotID and elementID using annotation type
		int slotID = -1;
		int elementID = -1;
		String htmlID = "";
		String annotType = "";
		ResultSet rs = stmt.executeQuery("select a.slot_id, b.element_id, c.html_id, a.annotation_type from " + schema + "slot a, " + schema + "element_value b, " + schema + "value c "
			+ "where a.name = '" + slotName + "' and a.slot_id = c.slot_id and b.value_id = c.value_id");
		if (rs.next()) {
			slotID = rs.getInt(1);
			elementID = rs.getInt(2);
			htmlID = rs.getString(3) + "_0_0";
			annotType = rs.getString(4);
		}

		//get annotation ID and value
		int annotID = -1;
		String value = "";
		rs = stmt.executeQuery("select id, value from " + schema + "annotation where document_namespace = '" + docNamespace + "' and document_table = '" + docTable + "' "
			+ "and document_id = " + docID + " and annotation_type = '" + annotType + "' and start = " + start + " and end = " + end);
		if (rs.next()) {
			annotID = rs.getInt(1);
			value = rs.getString(2);
		}

		stmt.execute("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, document_table, document_id, annotation_id, provenance, element_id) "
			+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "',0,0,'" + docNamespace + "','" + docTable + "'," + docID + "," + annotID + ",'validation-tool'," + elementID + ")");
		System.out.println("insert into " + schema + "frame_instance_data (frame_instance_id, slot_id, value, section_slot_number, element_slot_number, document_namespace, document_table, document_id, annotation_id, provenance, element_id) "
				+ "values (" + frameInstanceID + "," + slotID + ",'" + value + "',0,0,'" + docNamespace + "','" + docTable + "'," + docID + "," + annotID + ",'namespace'," + elementID + ")");

		stmt.close();
		conn.close();

		return "{\"htmlID\":\"" + htmlID + "\",\"value\":\"" + value + "\"" + ",\"elementID\":" + elementID + ",\"sectionSlot\":0,\"elementSlot\":0}";
	}

	public List<Map<String, Object>> getSlotValues(int crfID, String annotType) throws SQLException
	{
		List<Map<String, Object>> slotValueList = new ArrayList<Map<String, Object>>();

		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("select a.slot_id, d.display_name, e.element_id, a.name from " + schema + "slot a, " + schema + "crf b, " + schema + "frame_slot c, " + schema
			+ "value d, " + schema + "element_value e "
			+ "where b.crf_id = " + crfID + " and a.annotation_type = '" + annotType + "' and a.slot_id = c.slot_id and c.frame_id = b.frame_id and "
			+ "a.slot_id = d.slot_id and e.value_id = d.value_id");
		while (rs.next()) {
			Map<String, Object> slotMap = new HashMap<String, Object>();
			slotMap.put("slotID", rs.getInt(1));
			slotMap.put("name", rs.getString(2));
			slotMap.put("elementID", rs.getInt(3));
			slotMap.put("htmlID", rs.getString(4));
			slotMap.put("sectionSlot", 0);
			slotMap.put("elementSlot", 0);
			slotValueList.add(slotMap);
		}
		
		conn.close();

		return slotValueList;
	}

	public String getFrameInstanceID(String entityIDStr, int projID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();

		gson = new Gson();

		List<String> colNamesList = new ArrayList<String>();
		List<Integer> colTypesList = new ArrayList<Integer>();

		ResultSet rs = stmt.executeQuery("select entity_columns, entity_types from crf_project where crf_project_id = " + projID);
		if (rs.next()) {
			colNamesList = gson.fromJson(rs.getString(1), colNamesList.getClass());
			colTypesList = gson.fromJson(rs.getString(2), colTypesList.getClass());
		}

		String[] colValues = entityIDStr.split(",");

		stmt.close();
		conn.close();

		String[] colNames = null;
		Integer[] colTypes = null;

		return getFrameInstanceID(colNamesList.toArray(colNames), colValues, projID);
	}

	public String getFrameInstanceID(String[] colNames, String[] colValues, int projID) throws SQLException
	{
		Connection conn = DB.getConnection();
		Connection docConn = DB.getConnection("doc");
		gson = new Gson();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select document_id_column, document_table, column_types_map from " + schema + "crf_project where crf_project_id = " + projID);

		String docIDCol = "";
		String docTable = "";
		String colTypesMapStr = "";

		if (rs.next()) {
			docIDCol = rs.getString(1);
			docTable = rs.getString(2);
			colTypesMapStr = rs.getString(3);
		}

		Map<String, Double> colTypesMap = new HashMap<String, Double>();
		colTypesMap = gson.fromJson(colTypesMapStr, colTypesMap.getClass());

		Statement stmt2 = docConn.createStatement();
		StringBuilder strBlder = new StringBuilder();
		for (int i=0; i<colNames.length; i++) {
			if (strBlder.length() > 0)
				strBlder.append(" and ");

			String apos = "";
			if (colTypesMap.get(colNames[i]).intValue() == 2)
				apos = "'";

			strBlder.append(colNames[i] + " = " + apos + colValues[i] + apos);
		}

		System.out.println("select " + docIDCol + " from " + docSchema + docTable + " where " + strBlder.toString());

		rs = stmt2.executeQuery("select " + docIDCol + " from " + docSchema + docTable + " where " + strBlder.toString());
		List<Long> docIDList = new ArrayList<Long>();
		while (rs.next()) {
			long docID = rs.getLong(1);
			docIDList.add(docID);
		}

		int frameInstanceID = -1;
		String frameInstanceName = "";
		while (frameInstanceID == -1) {
			for (long docID : docIDList) {
				rs = stmt.executeQuery("select frame_instance_id from " + schema + "frame_instance_document where document_id = " + docID);
				if (rs.next()) {
					frameInstanceID = rs.getInt(1);
					break;
				}
			}
		}

		rs = stmt.executeQuery("select name from " + schema + "frame_instance where frame_instance_id = " + frameInstanceID);
		if (rs.next()) {
			frameInstanceName = rs.getString(1);
		}

		conn.close();
		docConn.close();

		return "{\"frameInstanceID\":\"" + frameInstanceID + "\",\"frameInstanceName\":\"" + frameInstanceName + "\"}";
	}

	private int getLastID(Connection conn) throws SQLException
	{
		int lastIndex = -1;
		String dbType = conn.getMetaData().getDatabaseProductName();
		String queryStr = "select last_insert_id()";
		if (dbType.equals("Microsoft SQL Server"))
			queryStr = "select @@identity";

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		if (rs.next())
			lastIndex = rs.getInt(1);

		stmt.close();

		return lastIndex;
	}
	
	public boolean updateValidationStatus(int frameInstanceID)
	{
		try {
			Connection conn = DB.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update document_status set status = 1 where document_id = ? and status = 0");
			PreparedStatement pstmt2 = conn.prepareStatement("update annotation set provenance = 'validation-tool' where document_id = ? and provenance = 'msa-ie'");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select document_id from "
					+ schema + "frame_instance_document where frame_instance_id = " + frameInstanceID);
			
		
			while (rs.next()) {
				long docID = rs.getLong(1);
				pstmt.setLong(1, docID);
				pstmt.execute();
				
				pstmt2.setLong(1, docID);
				pstmt2.execute();
			}
		
			pstmt.close();
			stmt.close();
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			Logger.error("updateValidationStatus got error: " + e.toString() );
			return false;
		}
	}

	/*
	public boolean updateValidationStatus ( int docID ) {
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select frame_instance_id from "
					+ schema + "frame_instance_document where document_id = " + docID);
			int frameInstanceID = 0;
			if (rs.next()) {
				frameInstanceID = rs.getInt(1);
			}
			if( frameInstanceID == 0 ) {
				return false;
			}

			//update frame_instance_status table
			stmt.executeUpdate("update " + schema + "frame_instance_status set status = 1 "
					+ "where frame_instance_id = " + frameInstanceID);

			ResultSet rsFrameInstanceDocument = stmt.executeQuery("select "
					+ "document_namespace, document_table from " + schema
					+ "frame_instance_document where document_id = " + docID
					+ " and frame_instance_id = "  + frameInstanceID );

			if( rsFrameInstanceDocument.next() ) {
				//update document_status table
				String docNamespace = rsFrameInstanceDocument.getString(1);
				String docTable = rsFrameInstanceDocument.getString(2);
				ResultSet rsDocStatus = stmt.executeQuery("select status from "
					+ schema + "document_status where document_id = " + docID
					+ " and document_namespace = '" + docNamespace
						+ "' and document_table = '" + docTable + "'" );

				if( rsDocStatus.next() ) {
					int status = rsDocStatus.getInt(1);
					//Logger.info("docStatus return = " + status );
					if( status != 1 ) {
						stmt.executeUpdate("update " + schema + "document_status "
							+ "set status = 1 where document_id = " + docID
							+ " and document_namespace = " + docNamespace
							+ " and document_table = " + docTable );
					}
				} else {
					PreparedStatement preparedStat = conn.prepareStatement(
							"insert into " + schema + "document_status ("
							+ "document_namespace, document_table, document_id, status ) "
							+ "values(?, ?, ?, ?) " );
					preparedStat.setString(1, docNamespace);
					preparedStat.setString(2, docTable);
					preparedStat.setInt( 3, docID);
					preparedStat.setInt(4, 1);
					preparedStat.execute();
				}
			}

			conn.close();
			return true;
		} catch ( SQLException ex ) {
			Logger.error("updateValidationStatus got error: " + ex.toString() );
			return false;
		}
	}
	*/

}
