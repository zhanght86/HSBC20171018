
<%@page import="com.sinosoft.ibrms.IbrmsTestService"%><%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.sinosoft.service.*"%>
<%
   /*JSONArray array = new JSONArray();
   
   Map map = new HashMap() ;
   map.put("name","111");
   map.put("age","222");
   array.put(map);
   
   map = new HashMap() ;
   map.put("name","222");
   map.put("age","333");
   array.put(map);
   
   JSONObject obj = new JSONObject();
   
   obj.put("startTime","2008-09-08");
   obj.put("endTime","2008-09-09 10:01:01");
   obj.put("result",array);*/
   
      
   
  /* String value = request.getParameter("json");
   loggerDebug("runRuleTest",value);
   String keyword = new String(value.getBytes("UTF-8"),"GBK");
   String mm =  java.net.URLDecoder.decode(value, "UTF-8");
   loggerDebug("runRuleTest",mm);*/
  // loggerDebug("runRuleTest","aaa");
  // IbrmsTestService testService = new IbrmsTestService();
   String tLan = (String) request.getParameter("Lan");
 //  testService.setLanguage(tLan);
  
 	VData sVData = new VData();
    sVData.add(0,request);
    sVData.add(1,tLan);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    tBusinessDelegate.submitData(sVData, "test", "IbrmsTestServiceUI");
   
    VData resVData = new VData();	
    resVData = tBusinessDelegate.getResult();
   
 
   //String json = testService.test(request);
   String json = (String)resVData.get(0);
   loggerDebug("runRuleTest",json);
   response.getWriter().write(json);
   
%>

