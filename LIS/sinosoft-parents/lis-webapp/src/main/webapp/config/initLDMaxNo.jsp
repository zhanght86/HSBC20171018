
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

	String tType = request.getParameter("Type");
	String tLimitType = request.getParameter("LimitType");
	String tNoCode = request.getParameter("NoCode");
	String busiName = "LDMaxNoConfigUI";
	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add(0, tType);
	tVData.add(1, tNoCode);
	tVData.add(2, tLimitType);
	JSONObject getItems = null;
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (tType != null) {
	}

	if (tType == null || tType.equals("")|| tType.equals("null")) {
		
		
	} else if (tType.equals("Element")) {
		
		if (!tBusinessDelegate.submitData(tVData, "InitElement",
				busiName)) {
			VData rVData = tBusinessDelegate.getResult();
		} else {
			VData rVData = tBusinessDelegate.getResult();
			//getItems = (JSONObject) rVData.get(0);
			JSONArray array =  (JSONArray)rVData.get(0);
			out.print(array);
			//JSONArray array = getItems.getJSONArray("data");   

			//loggerDebug("initAllRiskParams","getItems:"+array);
			//out.print(array);
		}
	}
	
	 else if (tType.equals("Rule")) {
		
		if (!tBusinessDelegate.submitData(tVData, "InitRule",
				busiName)) {
			VData rVData = tBusinessDelegate.getResult();
		} else {
			VData rVData = tBusinessDelegate.getResult();
			//getItems = (JSONObject) rVData.get(0);
			JSONArray array =  (JSONArray)rVData.get(0);
			out.print(array);
			//JSONArray array = getItems.getJSONArray("data");   

			//loggerDebug("initAllRiskParams","getItems:"+array);
			//out.print(array);
		}
	}
	 else if (tType.equals("previewRule")) {
			
			if (!tBusinessDelegate.submitData(tVData, "previewRule",
					busiName)) {
				VData rVData = tBusinessDelegate.getResult();
			} else {
				VData rVData = tBusinessDelegate.getResult();
				//getItems = (JSONObject) rVData.get(0);
				JSONArray array =  (JSONArray)rVData.get(0);
				System.out.print(array);
				out.print(array);
				//JSONArray array = getItems.getJSONArray("data");   

				//loggerDebug("initAllRiskParams","getItems:"+array);
				//out.print(array);
			}
		}
	 else if (tType.equals("testRule")) {
			
			if (!tBusinessDelegate.submitData(tVData, "testRule",
					busiName)) {
				VData rVData = tBusinessDelegate.getResult();
			} else {
				VData rVData = tBusinessDelegate.getResult();
				//getItems = (JSONObject) rVData.get(0);
				JSONArray array =  (JSONArray)rVData.get(0);
				System.out.print(array);
				out.print(array);
				//JSONArray array = getItems.getJSONArray("data");   

				//loggerDebug("initAllRiskParams","getItems:"+array);
				//out.print(array);
			}
		}
%>
