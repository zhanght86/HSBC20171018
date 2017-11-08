<%@include file="../i18n/language.jsp"%>

<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<%
	String RiskCode = request.getParameter("RiskCode");
	String tType = request.getParameter("Type");
	String busiName = "InitDutyPayColSelUI";
	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add(0, RiskCode);
	
	JSONObject getItems = null;
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	

if (tType.equals("Duty")) {
		
		if (!tBusinessDelegate.submitData(tVData, "Duty",
				busiName)) {
			VData rVData = tBusinessDelegate.getResult();
		} else {
			VData rVData = tBusinessDelegate.getResult();
			//getItems = (JSONObject) rVData.get(0);
			JSONArray array =  (JSONArray)rVData.get(0);
			out.print(array);
			//JSONArray array = getItems.getJSONArray("data");   

		//	System.out.println("getItems:"+array);
		//	out.print(array);
		}
	}
else if (tType.equals("InitDuty")) {
	
	if (!tBusinessDelegate.submitData(tVData, "InitDuty",
			busiName)) {
		VData rVData = tBusinessDelegate.getResult();
	} else {
		VData rVData = tBusinessDelegate.getResult();
		//getItems = (JSONObject) rVData.get(0);
		JSONArray array =  (JSONArray)rVData.get(0);
		out.print(array);
		//JSONArray array = getItems.getJSONArray("data");   

	//	System.out.println("getItems:"+array);
	//	out.print(array);
	}
}

else if (tType.equals("InitPay")) {
	
	if (!tBusinessDelegate.submitData(tVData, "InitPay",
			busiName)) {
		VData rVData = tBusinessDelegate.getResult();
	} else {
		VData rVData = tBusinessDelegate.getResult();
		//getItems = (JSONObject) rVData.get(0);
		JSONArray array =  (JSONArray)rVData.get(0);
		out.print(array);
		//JSONArray array = getItems.getJSONArray("data");   

	//	System.out.println("getItems:"+array);
	//	out.print(array);
	}
}
	
	 
%>
