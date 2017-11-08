<%@include file="../i18n/language.jsp"%>

<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>

<%
	String RiskCode = request.getParameter("RiskCode");
	String StandbyFlag1 = request.getParameter("StandbyFlag1");
	String tType = request.getParameter("Type");
	String busiName = "RiskFaceDefUI";
	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add(0, RiskCode);
	tVData.add(1, StandbyFlag1);
	
	JSONObject getItems = null;
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (tType != null) {
		System.out.println("111");
	}

	if (tType == null || tType.equals("")|| tType.equals("null")) {
		//RiskParamsBL mRiskParamsBL = new RiskParamsBL(RiskCode);
		//mRiskParamsBL.initRiskParamsBL();
		
		try {
			

			//String tDiscountCode = "";
			if (!tBusinessDelegate.submitData(tVData, "InitRiskParams",
					busiName)) {
				VData rVData = tBusinessDelegate.getResult();
				//Content = " 处理失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				//FlagStr = "Fail";
			} else {
				VData rVData = tBusinessDelegate.getResult();
				getItems = (JSONObject) rVData.get(0);

				//Content = " 处理成功! ";
				//FlagStr = "Succ";
				// new RiskState().setState(request.getParameter("RiskCode"), "契约业务控制->险种核保规则", "1");
				
				out.print(getItems);
			}

		} catch (Exception ex) {
			// Content = "保存失败，原因是:" + ex.toString();
			// FlagStr = "Fail";
		}
		
	} else if (tType.equals("DutyPay")) {
		
		if (!tBusinessDelegate.submitData(tVData, "InitRiskDutyParams",
				busiName)) {
			VData rVData = tBusinessDelegate.getResult();
		} else {
			VData rVData = tBusinessDelegate.getResult();
			getItems = (JSONObject) rVData.get(0);
			
			JSONArray array = getItems.getJSONArray("data");   

			System.out.println("getItems:"+array);
			out.print(array);
		}
	}
	
	 else if (tType.equals("Input")) {
		
		if (!tBusinessDelegate.submitData(tVData, "InitRiskInputParams",
				busiName)) {
			VData rVData = tBusinessDelegate.getResult();
		} else {
			VData rVData = tBusinessDelegate.getResult();
			JSONArray array =  (JSONArray)rVData.get(0);
			out.print(array);
		}
	}
	 else if (tType.equals("PayGrid")) {
			
			if (!tBusinessDelegate.submitData(tVData, "InitPayGrid",
					busiName)) {
				VData rVData = tBusinessDelegate.getResult();
			} else {
				VData rVData = tBusinessDelegate.getResult();
				getItems = (JSONObject) rVData.get(0);

				//Content = " 处理成功! ";
				//FlagStr = "Succ";
				// new RiskState().setState(request.getParameter("RiskCode"), "契约业务控制->险种核保规则", "1");
				
				out.print(getItems);
			}
		}
	 else if (tType.equals("DutyGrid")) {
			
			if (!tBusinessDelegate.submitData(tVData, "InitDutyGrid",
					busiName)) {
				VData rVData = tBusinessDelegate.getResult();
			} else {
				VData rVData = tBusinessDelegate.getResult();
				getItems = (JSONObject) rVData.get(0);

				//Content = " 处理成功! ";
				//FlagStr = "Succ";
				// new RiskState().setState(request.getParameter("RiskCode"), "契约业务控制->险种核保规则", "1");
				
				out.print(getItems);
			}
		}
%>
