<%
/***************************************************************
 * <p>ProName：LLClimCaseSave.jsp</p>
 * <p>Title：理赔复核案件界面</p>
 * <p>Description：理赔复核案件管理</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLGrpRegisterSchema"%>
<%@page import="com.sinosoft.lis.schema.LLRegisterSchema"%>
<%@page import="com.sinosoft.lis.schema.LLCaseSchema"%>
<%@page import="com.sinosoft.lis.schema.LLAppClaimReasonSchema"%>
<%@page import="com.sinosoft.lis.schema.LLClaimDetailSchema"%>
<%@page import="com.sinosoft.lis.vschema.LLAppClaimReasonSet"%>
<%@page import="com.sinosoft.lis.schema.LLCaseDutySchema"%>
<%@page import="com.sinosoft.lis.vschema.LLCaseDutySet"%>
<%@page import="com.sinosoft.lis.schema.LDBankSchema"%>
<%@page import="com.sinosoft.lis.schema.LLClaimUWMainSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tGrpRegisterNo = "";
	String tRegisterNo = "";
	String tCaseNo = "";
	String tMissionID = "";
	String tSubMissionID = "";
	String tActivityID = "";
	String tGiveType = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			String tGrpRgtNo = 	request.getParameter("GrpRgtNo");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpRgtNo",tGrpRgtNo);
			
			//记录审核信息--LLClaimUWMain
			LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
			tLLClaimUWMainSchema.setClmNo(request.getParameter("RegisterNo"));
			tLLClaimUWMainSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLClaimUWMainSchema.setCheckType("2");
			tLLClaimUWMainSchema.setExamConclusion(request.getParameter("AppConclusion"));			
			tLLClaimUWMainSchema.setRemark(request.getParameter("ApproveAdvice"));
			tLLClaimUWMainSchema.setExamIdea(request.getParameter("AppAdvice"));
														
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLClaimUWMainSchema);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimCaseApproveUI")) {
				tContent = tBusinessDelegate.getCErrors().getLastError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			
			ex.printStackTrace();
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
