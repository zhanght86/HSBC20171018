<%
/***************************************************************
 * <p>ProName：LSQuotPersonUWSave.jsp</p>
 * <p>Title：个人核保信息处理</p>
 * <p>Description：个人核保信息处理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-21
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPersonUWSchema"%>
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
			
			String tOperate = request.getParameter("PerUWOperate");
			
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tActivityID = request.getParameter("ActivityID");
			
			String tSerialNo = request.getParameter("PerUWSerialNo");
			String tContNo = request.getParameter("ContNo");
			String tGrpName = request.getParameter("GrpName");
			String tInsuredName = request.getParameter("InsuredName");
			String tIDType = request.getParameter("IDType");
			String tIDNo = request.getParameter("IDNo");
			String tGender = request.getParameter("Gender");
			String tBirthday = request.getParameter("Birthday");
			String tAge = request.getParameter("Age");
			String tRiskName = request.getParameter("RiskName");
			String tLossAmount = request.getParameter("LossAmount");
			String tReason = request.getParameter("Reason");
			String tRemark = request.getParameter("Remark");
			String tUWOpinion = request.getParameter("UWOpinion");
			String tUWDesc = request.getParameter("UWDesc");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("UploadNode", tActivityID);
			
			LSQuotPersonUWSchema tLSQuotPersonUWSchema = new LSQuotPersonUWSchema();
			
			tLSQuotPersonUWSchema.setSerialNo(tSerialNo);
			tLSQuotPersonUWSchema.setQuotNo(tQuotNo);
			tLSQuotPersonUWSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPersonUWSchema.setContNo(tContNo);
			tLSQuotPersonUWSchema.setGrpName(tGrpName);
			tLSQuotPersonUWSchema.setInsuredName(tInsuredName);
			tLSQuotPersonUWSchema.setIDType(tIDType);
			tLSQuotPersonUWSchema.setIDNo(tIDNo);
			tLSQuotPersonUWSchema.setGender(tGender);
			tLSQuotPersonUWSchema.setBirthday(tBirthday);
			tLSQuotPersonUWSchema.setAge(tAge);
			tLSQuotPersonUWSchema.setRiskName(tRiskName);
			tLSQuotPersonUWSchema.setLossAmount(tLossAmount);
			tLSQuotPersonUWSchema.setReason(tReason);
			tLSQuotPersonUWSchema.setRemark(tRemark);
			tLSQuotPersonUWSchema.setUWOpinion(tUWOpinion);
			tLSQuotPersonUWSchema.setUWDesc(tUWDesc);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotPersonUWSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPersonUWUI")) {
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
