package controllers;

import play.*;
import play.data.*;
import play.db.DB;
import play.mvc.*;
import views.html.*;

//import play.data.FormFactory;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private String un;
    private String pw;


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


    @Security.Authenticated(Secured.class)
    public Result logout() {
        //System.out.println("logout test");
        session().clear();
        // flash("success", "youve.been.logged.out");
        // noCache(response());
        return redirect(routes.Application.login());
    }

    public Result authenticate() {
        boolean loggedIn = false;
        
        String schema = Play.application().configuration().getString("schema") + ".";
    	session("schemaName", schema);

        try {

            DynamicForm df = Form.form().bindFromRequest();
            un = df.get("username");
            pw = df.get("password");


            gson = new Gson();
			List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
			sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
            DataAccess da = new DataAccess(session("schemaName"), sectionList);


            //System.out.println(un + " " + pw);

            if (da.authenticate(un, pw)) {
                //System.out.println("try block executed");
                //return index();
                loggedIn = true;

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
	    	DataAccess da = new DataAccess(session("schemaName"), sectionList);
	    	
	    	
	    	
            history = da.getHistory();
            
            if(history.size() != 0) {
                Map<String, String> mostRecentAction = history.get(history.size() - 1);
                
                String processID = mostRecentAction.get("processID");
                String action = mostRecentAction.get("action");
                String htmlID = mostRecentAction.get("htmlID");
                String extraInfo = mostRecentAction.get("extraInfo");
                
                // System.out.println(action + " " + htmlID);
                
                if(action.equals("add")) {
                    String elementID = da.getElementID(htmlID);
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
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
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
    	

        return ok(viewer.render(array, colNames, colValues));
    }

    public Result annotIndex()
    {
    	List<Map<String, String>> crfList = new ArrayList<Map<String, String>>();
    	String schema = Play.application().configuration().getString("schema") + ".";
    	session("schemaName", schema);

    	String annotThreshold = Play.application().configuration().getString("annotThreshold");
    	if (annotThreshold == null)
    		annotThreshold = "0.5";
    	session("annotThreshold", annotThreshold);

    	try {
    		gson = new Gson();
    		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
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

        return ok(annotViewer.render(array));
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

    	System.out.println("docNamespace: " + docNamespace + " docTable: " + docTable + " docID: " + docID);
    	String docText = "";
    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	try {
	    	System.out.println("docID: " + docID);
	    	//session("docID", Integer.toString(docID));
	    	session("docID", docID);
	    	gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), sectionList);
	    	Map<String, String> docMap = da.getDocument(docNamespace, docTable, Long.parseLong(docID));

	    	int crfID = Integer.parseInt(session("crfID"));
	    	List<Map<String, Object>> annotList = da.getDocumentAnnotations(docNamespace, docTable, Long.parseLong(docID), annotThreshold, crfID);

	    	resultMap.put("docName", docMap.get("docName"));
	    	resultMap.put("docText", docMap.get("docText"));
	    	resultMap.put("docFeatures", docMap.get("docFeatures"));
	    	resultMap.put("annotList", annotList);
			resultMap.put("frameInstanceStatus", docMap.get("frameInstanceStatus")); // add by wyu
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

    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
	    	DataAccess da = new DataAccess(session("schemaName"), sectionList);
    		annotList = da.getDocumentAnnotations(docNamespace, docTable, Long.parseLong(docID), annotThreshold, crfID);
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
	    	DataAccess da = new DataAccess(session("schemaName"), sectionList);
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

	    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
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

    public Result loadProject(int projID)
    {
    	String frameList = "";
    	session("projID", Integer.toString(projID));
		String resultStr = "";

    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	//sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
    		frameList = da.loadProject(un, projID); // was changed

			// **** add by wyu for repeat number ****
			//,{"lastFrameInstanceID":" + lastFrameInstanceID + ",\"lastFrameInstanceIndex\":" + lastFrameInstanceIndex + "}
			String queryPatternString = "\\{\"lastFrameInstanceID\":(\\d+),\"lastFrameInstanceIndex\":(\\d+)\\}$";
			//Logger.info("Application.loadProject: queryPatternString=" + queryPatternString);
			Pattern pattern = Pattern.compile(queryPatternString);
			Matcher matcher = pattern.matcher(frameList);
			int lastFrameInstanceID = -1;
			int lastFrameInstanceIndex = -1;
			if( matcher.find() ) {
				lastFrameInstanceID = Integer.parseInt(matcher.group(1));
				lastFrameInstanceIndex = Integer.parseInt(matcher.group(2));
				Logger.info("Application.loadProject: lastFrameInstanceID=" + lastFrameInstanceID);
				Logger.info("Application.loadProject: lastFrameInstanceIndex=" + lastFrameInstanceIndex);
			}
			// **** end of add
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

    public Result loadFrameInstance(int frameInstanceID)
    {
    	String frameInstanceStr = "";
    	session("frameInstanceID", Integer.toString(frameInstanceID));

    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
    		int projID = Integer.parseInt(session("projID"));
    		frameInstanceStr = da.loadFrameInstance(un, frameInstanceID, projID); // was changed
    		long docID = da.getCurrDocID();
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
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
    		//int docID = Integer.parseInt(session("docID"));
    		int crfID = Integer.parseInt(session("crfID"));
    		ret = da.addAnnotation(frameInstanceID, htmlID, value, start, end, docNamespace, docTable, docID, crfID, features, add);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    		
    		
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
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);

    		int index = elementIDStr.indexOf("_");
    		System.out.println("index: " + index);
    		elementIDStr = elementIDStr.substring(0, index);

    		int elementID = Integer.parseInt(elementIDStr);

    		ret = da.clearElement(frameInstanceID, elementID, htmlID);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    		
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
    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
    		da.clearAll(frameInstanceID);

    		String sectionListStr = gson.toJson(sectionList);
    		session("sectionList", sectionListStr);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}


    	return ok();
    }

    public Result clearValue(String htmlID)
    {
    	String ret = "";

    	try {
    		gson = new Gson();
	    	List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
	    	sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
    		int frameInstanceID = Integer.parseInt(session("frameInstanceID"));
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);

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
    	System.out.println("htmlID: " + htmlID);

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
	    		DataAccess da = new DataAccess(session("schemaName"), sectionList);

	    		int index = elementIDStr.indexOf("_");
	    		elementIDStr = elementIDStr.substring(0, index);

	    		int elementID = Integer.parseInt(elementIDStr);
	    		String frameStr = da.removeElement(frameInstanceID, elementID, htmlID);

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
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
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
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
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
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
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
	    	DataAccess da = new DataAccess(session("schemaName"), sectionList);
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
	    	DataAccess da = new DataAccess(session("schemaName"), sectionList);
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
    		DataAccess da = new DataAccess(session("schemaName"), sectionList);
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

	public Result docValidated() {
		Logger.info("docValidated comming in...");
		if( session("docID") == null ) {
			return ok("Error:The document didn't exist.");
		}
		int docID = Integer.parseInt(session("docID"));

		Logger.info("docValidated: docID=" + docID);
		List<Map<String, Object>> sectionList = new ArrayList<Map<String, Object>>();
		sectionList = gson.fromJson(session("sectionList"), sectionList.getClass());
		DataAccess da = new DataAccess(session("schemaName"), sectionList);

		if( da.updateValidationStatus(docID)) {
			return ok("Success:This document has been validated successfully.");
		} else {
			return ok("Error:There is an error during updating validation status.");
		}

	}
}
