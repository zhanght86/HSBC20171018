<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@page import="com.sinosoft.utility.SSRS"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.utility.DBConnPool"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.ibrms.IbrmsTestService"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="org.apache.log4j.*" %>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>

<%
    String sql = request.getParameter("sql");
	loggerDebug("getComboxData","##################查询后台的SQL语句是#################");
	loggerDebug("getComboxData",sql);
	//tongmeng 2011-06-02 add 
	//支持多种调用
	if(sql.toUpperCase().indexOf("CODETYPE:")!=-1)
	{
		loggerDebug("getComboxData", sql.substring(sql.indexOf(":")+1));
		String strResult = "";
		String codeType = sql.substring(sql.indexOf(":")+1).toLowerCase();
		//Logger log = Logger.getRootLogger();      
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		VData tData=new VData();
		LDCodeSchema tLDCodeSchema =new LDCodeSchema();
		tLDCodeSchema.setCodeType(codeType);
		tData.add(tLDCodeSchema);
		
	  	GlobalInput tGI = new GlobalInput();
	  	tGI = (GlobalInput)session.getValue("GI");
	  	tData.add(tGI);
		tBusinessDelegate.submitData(tData,"QUERY||MAIN","CodeQueryUI");
		if (tBusinessDelegate.getCErrors().needDealError()) {
			loggerDebug("getComboxData",tBusinessDelegate.getCErrors().getFirstError()) ;
		}	else {
		  tData.clear();
		  tData=tBusinessDelegate.getResult();
		  strResult=(String)tData.get(0);
		  loggerDebug("getComboxData",strResult);
		  
		  String[][] tSplit = StrTool.decodeResult(strResult);
		 if(tSplit!=null)
		 {
		  JSONArray array = new JSONArray();
		  for(int i=0;i<tSplit.length;i++)
			 {
			  Map map = new HashMap() ;
	    		map.put("code",tSplit[i][0]);
	    		map.put("name",tSplit[i][0]+"-"+tSplit[i][1]);
	    		array.put(map);
			 }
		  
		  	JSONObject obj = new JSONObject();
		   
		  
	   		obj.put("rows",array);
	   		String json= obj.toString();
	   		loggerDebug("getComboxData",json);
	   		response.getWriter().write(json);
		  //strValue=StrTool.unicodeToGBK(strResult);
		  //log.debug(strResult);
		 }
		}
	}
	else
	{	
		  SSRS ssrs = new SSRS();
	      TransferData sTransferData=new TransferData();
	      sTransferData.setNameAndValue("SQL", sql);
	      VData sVData = new VData();
	      sVData.add(sTransferData);
	      BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	      if(tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI"))
	      {
	    	  ssrs = (SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
	      }   
	      
    	//ExeSQL   exeSql  = new ExeSQL();
    
	    JSONArray array = new JSONArray();
	   // SSRS ssrs = exeSql.execSQL(sql);
   		for(int i=1;i<=ssrs.getMaxRow();i++){
    		Map map = new HashMap() ;
    		map.put("code",ssrs.GetText(i,1));
    		map.put("name",ssrs.GetText(i,1)+"-"+ssrs.GetText(i,2));
    		array.put(map);
    	}
   
    	JSONObject obj = new JSONObject();
   
  
   		obj.put("rows",array);
   		String json= obj.toString();
   		loggerDebug("getComboxData",json);
   		response.getWriter().write(json);
	}
   
%>

