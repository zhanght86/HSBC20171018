<%
/***************************************************************
 * <p>ProName：LSQuotCountRateSave.jsp</p>
 * <p>Title：长险测算</p>
 * <p>Description：费率测算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-10-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPolInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotCustInfoSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			tOperate = request.getParameter("Operate");
			
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			
			String tRiskCode = request.getParameter("RiskCode");
			String tDutyCode = request.getParameter("DutyCode");
			String tEffectivDate = request.getParameter("EffectivDate");
			String tCalDirection = request.getParameter("CalDirection");
			String tAmnt = request.getParameter("Amnt");
			String tPayIntv = request.getParameter("PayIntv");
			String tInsuPeriod = request.getParameter("InsuPeriod");
			String tInsuPeriodFlag = request.getParameter("InsuPeriodFlag");
			String tReceiveCode = request.getParameter("ReceiveCode");
			String tReceiveFlag = request.getParameter("ReceiveFlag");
			
			String tSerialNo = request.getParameter("SerialNo");
			String tInsuredName = request.getParameter("InsuredName");
			String tGender = request.getParameter("Gender");
			String tAge = request.getParameter("InsuredAge");
			String tBirthday = request.getParameter("Birthday");
			String tIDNo = request.getParameter("IDNo");
			
			LSQuotPolInfoSchema tLSQuotPolInfoSchema = new LSQuotPolInfoSchema();
			LSQuotCustInfoSchema tLSQuotCustInfoSchema = new LSQuotCustInfoSchema();
			
			tLSQuotPolInfoSchema.setQuotNo(tQuotNo);
			tLSQuotPolInfoSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPolInfoSchema.setRiskCode(tRiskCode);
			tLSQuotPolInfoSchema.setDutyCode(tDutyCode);
			tLSQuotPolInfoSchema.setEffectivDate(tEffectivDate);
			tLSQuotPolInfoSchema.setCalDirection(tCalDirection);
			tLSQuotPolInfoSchema.setAmnt(tAmnt);
			tLSQuotPolInfoSchema.setPayIntv(tPayIntv);
			tLSQuotPolInfoSchema.setInsuPeriod(tInsuPeriod);
			tLSQuotPolInfoSchema.setInsuPeriodFlag(tInsuPeriodFlag);
			tLSQuotPolInfoSchema.setReceiveCode(tReceiveCode);
			tLSQuotPolInfoSchema.setReceiveFlag(tReceiveFlag);
			
			tLSQuotCustInfoSchema.setSerialNo(tSerialNo);
			tLSQuotCustInfoSchema.setQuotNo(tQuotNo);
			tLSQuotCustInfoSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotCustInfoSchema.setInsuredName(tInsuredName);
			tLSQuotCustInfoSchema.setGender(tGender);
			tLSQuotCustInfoSchema.setBirthday(tBirthday);
			tLSQuotCustInfoSchema.setIDNo(tIDNo);
			
			if (tAge==null || "".equals(tAge)) {
				tLSQuotCustInfoSchema.setAge(-1);
			} else {
				tLSQuotCustInfoSchema.setAge(tAge);
			}

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLSQuotPolInfoSchema);
			tVData.add(tLSQuotCustInfoSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotCountRateUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
