package controllers;

import play.*;
import play.data.*;
import play.db.Database;
import play.mvc.*;
import views.html.*;

//import play.data.FormFactory;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.Request;

import com.google.gson.Gson;

import db.DataAccess;
import frames.CRFReader;

@SuppressWarnings("deprecation")
public class Application extends Controller
{
	private static Gson gson;

    //start of additional code - Bell Wu 6/10/2016
    /*public static class Login {

        public String un;
        public String pw;

        public String validate() {
            Application ap= new Application();
            if (ap.authenticate(un, pw) == null) {
                return "Failed";
            }
            return null;
        }

    }*/

    //private String un;
    //private String pw;
	private int crfID;


    /*public String getUsername() {
        return un;
    }

    public String getPassword() {
        return pw;
    }*/

    public Result login() {

        return ok(login.render(true, true));

    }

    /*public Result createUser() {
        return ok(login.render(true, false));

    }

    public Result submitUser() {
        System.out.println("reg submit worked");
        return login();
    }*/


    //@Security.Authenticated(Secured.class)
    public Result logout() {
		try {
			List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
			sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
			DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
			String username = session("userName");
			//Logger.info("Logout: username="+username);
			da.removeAllFrameInstanceLockForUser(session("userName"));

		}
		catch (Exception e){
			e.printStackTrace();
		}
        session().clear();
        // flash("success", "youve.been.logged.out");
        // noCache(response());
        return redirect(routes.Application.login());
    }

    public Result authenticate() {
        boolean loggedIn = false;
        
        String schema = Play.application().configuration().getString("schema") + ".";
    	session("schemaName", schema);

    	String docSchema = Play.application().configuration().getString("docSchema") + ".";
    	session("docSchemaName", docSchema);
    	
    	String orderTable = Play.application().configuration().getString("orderTable");
    	session("orderTable", orderTable);
    	
    	String annotTable = Play.application().configuration().getString("annotTable");
    	session("annotTable", annotTable);
    	
    	String loadStatus = Play.application().configuration().getString("loadStatus");
    	if (loadStatus == null)
    		loadStatus = "false";
    	session("loadStatus", loadStatus);
    	
    	session("undoNum", "0");
    	
    	String docEntityColumn = Play.application().configuration().getString("docEntityColumn");
    	if (docEntityColumn == null)
    		docEntityColumn = "";
    	session("docEntityColumn", docEntityColumn);
    	
        try {

            DynamicForm df = Form.form().bindFromRequest();
            String un = df.get("username");
            String pw = df.get("password");


            gson = new Gson();
			List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
			sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
            DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);


            //System.out.println(un + " " + pw);

            if (da.authenticate(un, pw)) {
                //System.out.println("try block executed");
                //return index();
                loggedIn = true;
				session("userName", un);
				da.clearUndoHistoryDoc(un);
            } else {

                //return ok(login.render(false));
                loggedIn = false;

            }
        } catch (Exception e) {
						System.out.println(e);
            System.out.println("catch block executed");

        }
        //return badRequest("failed login");
        if(loggedIn) {
            //System.out.println("if statement worked");
            return index();
        } else {
            //System.out.println("else worked");
            return ok(login.render(false, true));
        }

    }

    public Result getHistory() {
    	List<Map<String, String>> history = new ArrayList<Map<String, String>>();


        // System.out.println("Application getHistory ran");
        try {
            DynamicForm df = Form.form().bindFromRequest();
            // System.out.println(df);

            double annotThreshold = Double.parseDouble(session("annotThreshold"));

            gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
	    	
	    	
	    	
            history = da.getHistory();
            
            if(history.size() != 0) {
                Map<String, String> mostRecentAction = history.get(history.size() - 1);
                
                String processID = mostRecentAction.get("processID");
                String action = mostRecentAction.get("action");
                String htmlID = mostRecentAction.get("htmlID");
                String extraInfo = mostRecentAction.get("extraInfo");
                int sectionID = Integer.parseInt(mostRecentAction.get("sectionID"));
                
                // System.out.println(action + " " + htmlID);
                
                if(action.equals("add")) {
                    String elementID = da.getElementID(htmlID, sectionID);
                    clearElement(elementID, htmlID);
                    //da.deleteMostRecentHistory();
                }
            }

        } catch (Exception e) {
			System.out.println("catch block");
			System.out.println(e);
        }
        
        return ok(gson.toJson(history));
    }



    // end of additional code




	public Result index()
	{
		return loadApp("", "");
	}

	public Result loadApp(String entityIDStr)
	{
		return loadApp("", entityIDStr);
	}

    public Result loadApp(String colNames, String colValues)
    {
    	List<Map<String, String>> crfList = new ArrayList<Map<String, String>>();
    	//String schema = Play.application().configuration().getString("schema") + ".";
    	//session("schemaName", schema);

    	String annotThreshold = Play.application().configuration().getString("annotThreshold");
    	if (annotThreshold == null)
    		annotThreshold = "0.5";
    	session("annotThreshold", annotThreshold);
    	
    	String userName = session("userName");

    	//session("schemaName", "validator.");
    	//String schema = "validator.";

    	try {
    		/*
    		Connection conn = DB.getConnection();
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select crf_project_id, name from " + schema + "crf_project");
    		while (rs.next()) {
    			String crfProjID = rs.getString(1);
    			String name = rs.getString(2);
    			Map<String, String> map = new HashMap<String, String>();
    			map.put("id", crfProjID);
    			map.put("name", name);
    			crfList.add(map);
     		}

    		conn.close();
    		*/

    		gson = new Gson();
    		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		crfList = da.getProjects();
    		
    		// new code
    		//da.clearHistory();

    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	Map[] array = new Map[crfList.size()];
    	for (int i=0; i<crfList.size(); i++) {
    		array[i] = crfList.get(i);
    	}
    	
    	//launch frame filler thread
    	//FrameFillerThread ffThread = new FrameFillerThread();
    	

        return ok(viewer.render(array, colNames, colValues, userName));
    }

    public Result annotIndex()
    {
    	List<Map<String, String>> crfList = new ArrayList<Map<String, String>>();
    	String schema = Play.application().configuration().getString("schema") + ".";
    	session("schemaName", schema);

    	String docSchema = Play.application().configuration().getString("docSchema") + ".";
    	session("docSchemaName", docSchema);

    	String annotThreshold = Play.application().configuration().getString("annotThreshold");
    	if (annotThreshold == null)
    		annotThreshold = "0.5";
    	session("annotThreshold", annotThreshold);

    	try {
    		gson = new Gson();
    		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		crfList = da.getProjects();

    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	Map[] array = new Map[crfList.size()];
    	for (int i=0; i<crfList.size(); i++) {
    		array[i] = crfList.get(i);
    	}

        return ok(annotViewer.render(array), request().getHeader());
    }

    public Result getDocument()
    {
    	DynamicForm form = Form.form().bindFromRequest();
    	String docNamespace = form.get("docNamespace");
    	String docTable = form.get("docTable");
    	String docID = form.get("docID");
    	//String docKey = form.get("docKey");
    	//String docTextColumn = form.get("docTextColumn");

    	double annotThreshold = Double.parseDouble(session("annotThreshold"));
    	String un = session("userName");

    	System.out.println("docNamespace: " + docNamespace + " docTable: " + docTable + " docID: " + docID);
    	String docText = "";
    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	try {
	    	System.out.println("docID: " + docID);
	    	//session("docID", Integer.toString(docID));
	    	
	    	String currDocIDStr = session("docID");
	    	long currDocID = -1;
	    	if (currDocIDStr != null)
	    		currDocID = Long.parseLong(session("docID"));
	    	String currDocNamespace = session("docNamespace");
	    	String currDocTable = session("docTable");
	    	
	    	//set current doc info
	    	session("docID", docID);
	    	session("docNamespace", docNamespace);
	    	session("docTable", docTable);
	    	
	    	
	    	gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
	    	da.setCurrDocID(Long.parseLong(docID));
	    	da.setUserName(un);
	    	
	    	
	    	//update validation status and clear undo history
	    	if (currDocID >= 0) {
		    	da.updateValidationStatusDoc(currDocNamespace, currDocTable, currDocID, un);
				da.clearUndoHistoryDoc(un); // clears whenever you load a new document
	    	}
	    	
	    	Map<String, String> docMap = da.getDocument(docNamespace, docTable, Long.parseLong(docID), session("docEntityColumn"));
	    	session("undoNum", "0");

	    	int crfID =  Integer.parseInt(session("crfID"));
	    	int projID = Integer.parseInt(session("projID"));
	    	List<Map<String, Object>> annotList = da.getDocumentAnnotations(docNamespace, docTable, Long.parseLong(docID), annotThreshold, crfID, projID);

	    	resultMap.put("docName", docMap.get("docName"));
	    	resultMap.put("docText", docMap.get("docText"));
	    	resultMap.put("docFeatures", docMap.get("docFeatures"));
	    	resultMap.put("annotList", annotList);
			//resultMap.put("docStatus", docMap.get("docStatus"));
			resultMap.put("frameInstanceStatus", docMap.get("frameInstanceStatus"));

	    	String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	//return ok(docText);
    	return ok(gson.toJson(resultMap));
    }

    public Result getDocumentAnnotations()
    {
    	List<Map<String, Object>> annotList = new ArrayList<Map<String, Object>>();

    	try {
    		DynamicForm form = Form.form().bindFromRequest();
        	String docNamespace = form.get("docNamespace");
        	String docTable = form.get("docTable");
        	String docID = form.get("docID");
        	double annotThreshold = Double.parseDouble(session("annotThreshold"));
        	int crfID = Integer.parseInt(session("crfID"));
        	int projID = Integer.parseInt(session("projID"));

    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		annotList = da.getDocumentAnnotations(docNamespace, docTable, Long.parseLong(docID), annotThreshold, crfID, projID);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(gson.toJson(annotList));
    }

    public Result getFrameData(int frameInstanceID)
    {
    	String ret = "";

    	try {
    		DynamicForm form = Form.form().bindFromRequest();
        	String docNamespace = form.get("docNamespace");
        	String docTable = form.get("docTable");
        	String docID = form.get("docID");
        	double annotThreshold = Double.parseDouble(session("annotThreshold"));
        	int crfID = Integer.parseInt(session("crfID"));

    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		ret = da.getFrameData(frameInstanceID);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(ret);
    }

    public Result getCRF(int crfID)
    {
    	String crfStr = "";
    	try {
    		session("crfID", Integer.toString(crfID));
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		CRFReader reader = new CRFReader(session("schemaName"));
    		//crfStr = reader.readCRFFile("/Users/frankmeng/Documents/Projects/nlp-workspace/ie-validation/public/crfs/lung-cancer-screening.json");
    		crfStr = reader.readCRFDB(crfID, sectionList);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(crfStr);
    }

    public Result addSection(String sectionName)
    {
    	String crfStr = "";
    	int frameInstanceID = Integer.parseInt(session("frameInstanceID"));

    	if (frameInstanceID > -1) {
	    	int crfID = Integer.parseInt(session("crfID"));

	    	try {
	    		gson = new Gson();
		    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
		    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    		CRFReader crfProc = new CRFReader(session("schemaName"));
	    		crfStr = crfProc.addRemoveSection(crfID, frameInstanceID, sectionName, 1, sectionList);

	    		String sectionListStr = gson.toJson(sectionList);
	    		session("sectionList", sectionListStr);
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
    	}

    	return ok(crfStr);
    }

    public Result removeSection(String sectionName, int repeatIndex)
    {
    	String crfStr = "";
    	int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    	if (frameInstanceID > -1) {
	    	try {
	    		int crfID = Integer.parseInt(session("crfID"));
	    		gson = new Gson();
		    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
		    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    		CRFReader crfProc = new CRFReader(session("schemaName"));
	    		crfStr = crfProc.addRemoveSection(crfID, frameInstanceID, sectionName, -1, sectionList);
	    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
	    		da.clearSection(frameInstanceID, sectionName, repeatIndex);
	    		String sectionListStr = gson.toJson(sectionList);
	    		session("sectionList", sectionListStr);
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
    	}

    	return ok(crfStr);
    }

    public Result setDocNamespaceTable()
    {
    	DynamicForm form = Form.form().bindFromRequest();
    	String docNamespace = form.get("docNamespace");
    	String docTable = form.get("docTable");
    	session("docNamespace", docNamespace);
    	session("docTable", docTable);

    	return ok();
    }

    public Result loadProject(int projID, boolean annotatedDocs)
    {
    	String frameList = "";
    	session("projID", Integer.toString(projID));
		String resultStr = "";

    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	//sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	String un = session("userName");
	    	
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		boolean orderTable = Boolean.parseBoolean(session("orderTable"));
    		
    		System.out.println("orderTable: " + orderTable + " annotatedDocs: " + annotatedDocs);
    		
    		frameList = da.loadProject(un, projID, orderTable, annotatedDocs); // was changed

			// **** add by wyu for repeat number ****
			String queryPatternString = "\\{\"lastFrameInstanceID\":(\\d+),\"lastFrameInstanceIndex\":(\\d+)\\}$";
			//Logger.info("Application.loadProject: queryPatternString=" + queryPatternString);
			Pattern pattern = Pattern.compile(queryPatternString);
			Matcher matcher = pattern.matcher(frameList);
			int lastFrameInstanceID = -1;
			int lastFrameInstanceIndex = -1;
			if( matcher.find() ) {
				lastFrameInstanceID = Integer.parseInt(matcher.group(1));
				lastFrameInstanceIndex = Integer.parseInt(matcher.group(2));
			}
			// **** end of add
			
			System.out.println("lastFrameInstanceID: " + lastFrameInstanceID);
			
			
    		int crfID = da.getCRFID(projID);
    		session("crfID", Integer.toString(crfID));

    		//Load the CRF into the view
    		//String crfStr = da.loadCRF(projID, -1);
			String crfStr = da.loadCRF(projID, lastFrameInstanceID); //modify by wyu for repeat number

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    		session("frameInstanceID", "-1");


    		resultStr = "[" + frameList + "," + crfStr + "]";
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(resultStr);
    }

    public Result loadFrameInstance(int frameInstanceID, boolean annotatedDocs, int userActions)
    {
    	String frameInstanceStr = "";
    	int oldFrameInstanceID = Integer.parseInt(session("frameInstanceID"));    	
    	session("frameInstanceID", Integer.toString(frameInstanceID));
    	
    	System.out.println("userActions: " + userActions);

    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	String un = session("userName");
	    	Boolean loadStatus = false;
	    	String loadStatusStr = session("loadStatus");
	    	if (loadStatusStr != null)
	    		loadStatus = Boolean.parseBoolean(loadStatusStr);
	    	
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		if (oldFrameInstanceID != frameInstanceID) {
    			//da.clearUndoHistory(un, oldFrameInstanceID, userActions);
    			da.updateValidationStatusFrameInstance(oldFrameInstanceID, un);
    			//session("undoNum", "0");
    			
    			String currDocNamespace = session("docNamespace");
    			String currDocTable = session("docTable");
    			String currDocIDStr = session("docID");
    			long currDocID = -1;
    			if (currDocIDStr != null)
    				currDocID = Long.parseLong(currDocIDStr);
    			
    			if (currDocID >= 0) {
    				da.updateValidationStatusDoc(currDocNamespace, currDocTable, currDocID, un);
    				da.clearUndoHistoryDoc(un); // clears whenever you load a new document
    			}
    		}
    		
    		int projID = Integer.parseInt(session("projID"));
    		frameInstanceStr = da.loadFrameInstance(un, frameInstanceID, projID, loadStatus, session("docEntityColumn"), annotatedDocs); // was changed
    		String currDocNamespace = da.getCurrDocNamespace();
    		String currDocTable = da.getCurrDocTable();
    		long docID = da.getCurrDocID();
    		
    		session("docNamespace", currDocNamespace);
    		session("docTable", currDocTable);
    		session("docID", Long.toString(docID));  		

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(frameInstanceStr);
    }

    public Result addAnnotation()
    {

    	int frameInstanceID = Integer.parseInt(session("frameInstanceID"));

    	String ret = "";

    	try {
    		DynamicForm form = Form.form().bindFromRequest();
    		String htmlID = form.get("htmlID");
    		String value = form.get("value");
        	int start = Integer.parseInt(form.get("start"));
        	int end = Integer.parseInt(form.get("end"));
        	String docNamespace = form.get("docNamespace");
        	String docTable = form.get("docTable");
        	boolean add = Boolean.parseBoolean(form.get("add"));

        	System.out.println(docNamespace + ", " + docTable);

        	long docID = Long.parseLong(form.get("docID"));
        	//int vScrollPos = Integer.parseInt(form.get("vScrollPos"));
        	//int scrollHeight = Integer.parseInt(form.get("scrollHeight"));
        	//int scrollWidth = Integer.parseInt(form.get("scrollWidth"));
        	String features = form.get("features");

        	gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		int undoNum = Integer.parseInt(session("undoNum"));
    		da.setUndoNum(undoNum);
    		da.setUserName(session("userName"));
    		//int docID = Integer.parseInt(session("docID"));
    		int crfID = Integer.parseInt(session("crfID"));
    		ret = da.addAnnotation(frameInstanceID, htmlID, value, start, end, docNamespace, docTable, docID, crfID, features, add);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    		
    		session("undoNum", Integer.toString(undoNum + 1));
    		
    		// new code
    		//String information = "start:" + start + "; end:" + end;
    		//da.actionOccurred("add", htmlID, information);

    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}


    	return ok(ret);
    }

    public Result clearElement(String elementIDStr, String htmlID)
    {
        System.out.println("elementIDStr: " + elementIDStr + " ;htmlID: " + htmlID);
    	String ret = "";

    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		int undoNum = Integer.parseInt(session("undoNum"));
    		da.setUndoNum(undoNum);
    		da.setUserName(session("userName"));

    		int index = elementIDStr.indexOf("_");
    		System.out.println("index: " + index);
    		elementIDStr = elementIDStr.substring(0, index);

    		int elementID = Integer.parseInt(elementIDStr);

    		ret = da.clearElement(frameInstanceID, elementID, htmlID);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    		
    		session("undoNum", Integer.toString(undoNum + 1));
    		
    		System.out.println("done clearing element");
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(ret);
    }

    public Result clearAll()
    {
    	List<Map<String, Object>> annotList = new ArrayList<Map<String, Object>>();
    	
    	try {
    		gson = new Gson();
    		DynamicForm form = Form.form().bindFromRequest();
        	String docNamespace = form.get("docNamespace");
        	String docTable = form.get("docTable");
        	String docID = form.get("docID");
        	int crfID = Integer.parseInt(session("crfID"));
        	
        	double annotThreshold = Double.parseDouble(session("annotThreshold"));
        	int projID = Integer.parseInt(session("projID"));
        	
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		da.clearAll(frameInstanceID);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    		
    		annotList = da.getDocumentAnnotations(docNamespace, docTable, Long.parseLong(docID), annotThreshold, crfID, projID);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}


    	return ok(gson.toJson(annotList));
    }

    public Result clearValue(String htmlID)
    {
    	String ret = "";

    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		int undoNum = Integer.parseInt(session("undoNum"));
    		da.setUndoNum(undoNum);
    		da.setUserName(session("userName"));
    		
    		ret = da.clearValue(frameInstanceID, htmlID);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(ret);
    }

    public Result addElement(String htmlID)
    {
    	System.out.println("add element htmlID: " + htmlID);

    	String crfStr = "";
    	int frameInstanceID = Integer.parseInt(session("frameInstanceID"));

    	if (frameInstanceID > -1) {
	    	int crfID = Integer.parseInt(session("crfID"));

	    	try {
	    		gson = new Gson();
		    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
		    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());

	    		CRFReader crfProc = new CRFReader(session("schemaName"));
	    		crfStr = crfProc.addRemoveElement(crfID, frameInstanceID, htmlID, 1, sectionList);
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
    	}


    	return ok(crfStr);
    }

    public Result removeElement(String elementIDStr, String htmlID)
    {
    	String ret = "";
    	int frameInstanceID = Integer.parseInt(session("frameInstanceID"));

    	if (frameInstanceID > -1) {
	    	int crfID = Integer.parseInt(session("crfID"));

	    	try {
	    		gson = new Gson();
		    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
		    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());

	    		CRFReader crfProc = new CRFReader(session("schemaName"));
	    		String crfStr = crfProc.addRemoveElement(crfID, frameInstanceID, htmlID, -1, sectionList);
	    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
	    		int undoNum = Integer.parseInt(session("undoNum"));
	    		da.setUndoNum(undoNum);
	    		da.setUserName(session("userName"));
	    		
	    		int index = elementIDStr.indexOf("_");
	    		elementIDStr = elementIDStr.substring(0, index);

	    		int elementID = Integer.parseInt(elementIDStr);
	    		String frameStr = da.removeElement(frameInstanceID, elementID, htmlID);
	    		
	    		session("undoNum", Integer.toString(undoNum + 1));

	    		ret = "[" + crfStr + "," + frameStr + "]";
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
    	}


    	return ok(ret);
    }

    public Result addDocumentHistory()
    {
    	try {
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    		DynamicForm form = Form.form().bindFromRequest();
    		String docNamespace = form.get("docNamespace");
    		String docTable = form.get("docTable");
    		long docID = Long.parseLong(form.get("docID"));

    		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		da.addDocumentHistory(frameInstanceID, docNamespace, docTable, docID);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok();
    }

    public Result getDocumentHistory()
    {
    	String docHistStr = "";

    	try {
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		Map<String, Boolean> docMap = da.getDocumentHistory(frameInstanceID);
    		docHistStr = gson.toJson(docMap);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(docHistStr);
    }

    public Result clearDocumentHistory()
    {
    	try {
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));

    		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		da.clearDocumentHistory(frameInstanceID);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok();
    }

    public Result fillSlot()
    {
    	String valueStr = "";
    	try {
	    	int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
	    	DynamicForm form = Form.form().bindFromRequest();
    		String slotName = form.get("slotName");
    		//String value = form.get("value");
        	int start = Integer.parseInt(form.get("start"));
        	int end = Integer.parseInt(form.get("end"));
        	String docNamespace = form.get("docNamespace");
        	String docTable = form.get("docTable");
        	long docID = Long.parseLong(form.get("docID"));

	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
	    	valueStr = da.fillSlot(frameInstanceID, docNamespace, docTable, docID, slotName, start, end);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(valueStr);
    }

    public Result getSlotValues(String annotType)
    {
    	String slotValuesStr = "";
    	try {
    		int crfID = Integer.parseInt(session("crfID"));
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
	    	List<Map<String, Object>> slotValues = da.getSlotValues(crfID, annotType);
	    	slotValuesStr = gson.toJson(slotValues);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(slotValuesStr);
    }

    public Result getFrameInstanceID(String colNamesStr, String colValuesStr)
    {
    	String ret = "";
    	try {
    		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
    		int projID = Integer.parseInt(session("projID"));

    		String[] colNames = colNamesStr.split(",");
    		String[] colValues = colValuesStr.split(",");

    		ret = da.getFrameInstanceID(colNames, colValues, projID);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

    	return ok(ret);
    }

	public Result frameInstanceValidated() {
		if( session("docID") == null ) {
			return ok("Error:The document didn't exist.");
		}

		long docID = Long.parseLong(session("docID"));
		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));

		Logger.info("frameInstanceValidated: frameInstanceID=" + frameInstanceID);
		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
		sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
		DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
		String un = session("userName");

		if( frameInstanceID == 0 ) {
			return ok("Error: The frameInstanceID is 0.");
		}
		//return ok("Success: The document(s) has been validated successfully.");

		if( da.updateValidationStatus(docID, un)) {
			int projID = Integer.parseInt(session("projID"));
			try {
				//String frameList = da.loadProject(un, projID);
				Logger.info("frameInstanceValidated: return ok.");
				//return ok("[" + frameList + "]");
				return ok();
			}catch(Exception e) {
    			e.printStackTrace();
				return ok("Error:There is an error during updating validation status.");
    		}
			//return ok("Success:This document has been validated successfully.");
		} else {
			Logger.info("frameInstanceValidated: DataAccess return false.");
			return ok("Error:There is an error during updating validation status.");
		}

	}

	public Result frameInstanceLocked(int frameInstanceID) {
		try {


			List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
			System.out.println("sectionList: " + session("sectionList"));
			sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
			DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
			int timeout = Play.application().configuration().getInt("frameInstanceLockTimeout");
			if (frameInstanceID == 0) {
				return ok("{Error: The frameInstanceID is 0.}");
			}
			return ok("{\"Success\": \"" + (da.isInstanceLockedByOthers(frameInstanceID, session("userName"), timeout) ? "true" : "false")+"\"}");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return ok("Error");
	}
	
	public Result undo()
    {
		String ret = "";
		
		try {
			List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
			int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
			sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
			DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
			
    		int undoNum = Integer.parseInt(session("undoNum"));
			if (undoNum > 0)
				undoNum--;
			
    		da.setUndoNum(undoNum);
    		da.setUserName(session("userName"));
    		ret = da.undo(frameInstanceID);
    		session("undoNum", Integer.toString(undoNum));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return ok(ret);
    }
	
	public Result redo()
    {
		String ret = "";
		
		try {
			List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
			int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
			sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
			DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
			
    		int undoNum = Integer.parseInt(session("undoNum"));
			
    		da.setUndoNum(undoNum);
    		da.setUserName(session("userName"));
    		ret = da.redo(frameInstanceID);
    		
    		if (ret.length() > 2)
    			undoNum++;
    		session("undoNum", Integer.toString(undoNum));

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return ok(ret);
    }
	
	public Result search(String searchTerm)
	{
		String ret = "";
		try {
			List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
			int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
			DataAccess da = new DataAccess(session("schemaName"), session("docSchemaName"), session("annotTable"), sectionList);
			ret = da.getSearchAnnotations(frameInstanceID, searchTerm);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return ok(ret);
	}
}
