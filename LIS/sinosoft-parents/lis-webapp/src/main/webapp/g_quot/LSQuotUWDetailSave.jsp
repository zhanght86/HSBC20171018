<%
/***************************************************************
 * <p>ProName：LSQuotUWDetailSave.jsp</p>
 * <p>Title：询价核保明细</p>
 * <p>Description：询价核保明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-31
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotMainPointSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotationProcessSchema"%>
<%@page import="com.sinosoft.lis.schema.LCQutionReinsTotalSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tTranProdType = request.getParameter("TranProdType");
			
			String tUWMainPointRiskCode = request.getParameter("UWMainPointRiskCode");
			String tUWMainPointRuleCode = request.getParameter("UWMainPointRuleCode");
			String tSubUWAnaly = request.getParameter("SubUWAnaly");
			String tBranchUWAnaly = request.getParameter("BranchUWAnaly");
			
			String tSubUWOpinion = request.getParameter("SubUWOpinion");
			String tSubUWConclu = request.getParameter("SubUWConclu");
			
			String tBranchUWOpinion = request.getParameter("BranchUWOpinion");
			String tBranchUWConclu = request.getParameter("BranchUWConclu");
			String tBranchUrgentFlag = request.getParameter("BranchUrgentFlag");
			
			String tReinsArrange = request.getParameter("ReinsArrange");//再保安排
			String tFaculReason = request.getParameter("FaculReason");//临分原因
			String tFaculOtherDesc = request.getParameter("FaculOtherDesc");//临分时，其他描述
			
			String tUWOpinion = request.getParameter("UWOpinion");
			String tUWConclu = request.getParameter("UWConclu");
			
			String tUWManagerOpinion = request.getParameter("UWManagerOpinion");
			String tUWManagerConclu = request.getParameter("UWManagerConclu");
			
			String tBranchFinalOpinion = request.getParameter("BranchFinalOpinion");
			String tBranchFinalConclu = request.getParameter("BranchFinalConclu");
			
			String tSubFinalOpinion = request.getParameter("SubFinalOpinion");
			String tSubFinalConclu = request.getParameter("SubFinalConclu");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
			
			LSQuotMainPointSchema tLSQuotMainPointSchema = new LSQuotMainPointSchema();
			LSQuotationProcessSchema tLSQuotationProcessSchema = new LSQuotationProcessSchema();
			LCQutionReinsTotalSchema tLCQutionReinsTotalSchema = new LCQutionReinsTotalSchema();
			
			tLSQuotMainPointSchema.setQuotNo(tQuotNo);
			tLSQuotMainPointSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotMainPointSchema.setRiskCode(tUWMainPointRiskCode);
			tLSQuotMainPointSchema.setRuleCode(tUWMainPointRuleCode);
			tLSQuotMainPointSchema.setSubUWAnaly(tSubUWAnaly);
			tLSQuotMainPointSchema.setBranchUWAnaly(tBranchUWAnaly);
			
			tLSQuotationProcessSchema.setSubUWOpinion(tSubUWOpinion);
			tLSQuotationProcessSchema.setSubUWConclu(tSubUWConclu);
			tLSQuotationProcessSchema.setBranchUWOpinion(tBranchUWOpinion);
			tLSQuotationProcessSchema.setBranchUWConclu(tBranchUWConclu);
			tLSQuotationProcessSchema.setBranchUrgentFlag(tBranchUrgentFlag);
			tLSQuotationProcessSchema.setReinsArrange(tReinsArrange);
			tLSQuotationProcessSchema.setUWOpinion(tUWOpinion);
			tLSQuotationProcessSchema.setUWConclu(tUWConclu);
			tLSQuotationProcessSchema.setUWManagerOpinion(tUWManagerOpinion);
			tLSQuotationProcessSchema.setUWManagerConclu(tUWManagerConclu);
			tLSQuotationProcessSchema.setBranchFinalOpinion(tBranchFinalOpinion);
			tLSQuotationProcessSchema.setBranchFinalConclu(tBranchFinalConclu);
			tLSQuotationProcessSchema.setSubFinalOpinion(tSubFinalOpinion);
			tLSQuotationProcessSchema.setSubFinalConclu(tSubFinalConclu);
			
			tLCQutionReinsTotalSchema.setQuotNo(tQuotNo);
			tLCQutionReinsTotalSchema.setQuotBatNo(tQuotBatNo);
			tLCQutionReinsTotalSchema.setReinsType(tReinsArrange);
			tLCQutionReinsTotalSchema.setFaculReason(tFaculReason);
			tLCQutionReinsTotalSchema.setOtherDesc(tFaculOtherDesc);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotMainPointSchema);
			tVData.add(tLSQuotationProcessSchema);
			tVData.add(tLCQutionReinsTotalSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotUWDetailUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
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
