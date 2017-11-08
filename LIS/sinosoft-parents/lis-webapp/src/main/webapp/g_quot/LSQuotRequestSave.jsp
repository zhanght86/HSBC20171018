<%
/***************************************************************
 * <p>ProName：LSQuotRequestSave.jsp</p>
 * <p>Title：业务需求</p>
 * <p>Description：业务需求</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotRequestSchema"%>
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
			String tActivityID = request.getParameter("ActivityID");
			String tSerialNo = request.getParameter("SerialNo");
			String tRequestType = request.getParameter("RequestType");
			String tOtherTypeDesc = request.getParameter("OtherTypeDesc");
			String tRiskCode = request.getParameter("RiskCode");
			String tRequestDesc = request.getParameter("RequestDesc");
			String tSubUWOpinion = request.getParameter("SubUWOpinion");
			String tBranchUWOpinion = request.getParameter("BranchUWOpinion");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			LSQuotRequestSchema tLSQuotRequestSchema = new LSQuotRequestSchema();
			
			tLSQuotRequestSchema.setSerialNo(tSerialNo);
			tLSQuotRequestSchema.setQuotNo(tQuotNo);
			tLSQuotRequestSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotRequestSchema.setRequestType(tRequestType);
			tLSQuotRequestSchema.setOtherTypeDesc(tOtherTypeDesc);
			tLSQuotRequestSchema.setRiskCode(tRiskCode);
			tLSQuotRequestSchema.setRequestDesc(tRequestDesc);
			tLSQuotRequestSchema.setSubUWOpinion(tSubUWOpinion);
			tLSQuotRequestSchema.setBranchUWOpinion(tBranchUWOpinion);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotRequestSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotRequestUI")) {
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
