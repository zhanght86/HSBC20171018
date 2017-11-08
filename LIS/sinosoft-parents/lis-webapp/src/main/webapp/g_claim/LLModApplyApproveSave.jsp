<%
/***************************************************************
 * <p>ProName��LLClaimModApplySave.jsp</p>
 * <p>Title���ܹ�˾��������</p>
 * <p>Description���ܹ�˾��������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ����
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLModifyApplySchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
	try {
			
LLModifyApplySchema tLLModifyApplySchema = new LLModifyApplySchema();

			String mOperate = request.getParameter("Operate");
			String tApplyNo = request.getParameter("ApplyNo");
			String tApplyBatchNo = request.getParameter("ApplyBatchNo");			
			tLLModifyApplySchema.setReasonNo(request.getParameter("ReasonType"));
			tLLModifyApplySchema.setRuleType(request.getParameter("RuleType"));
			tLLModifyApplySchema.setRiskCode(request.getParameter("RiskCode"));
			tLLModifyApplySchema.setAdjustDirection(request.getParameter("AdjustDirection"));			
			tLLModifyApplySchema.setUpAdjustRule(request.getParameter("UpAdjustRule"));
			tLLModifyApplySchema.setUpAdjustRate(request.getParameter("UpAdjustRate"));
			tLLModifyApplySchema.setStartDate(request.getParameter("StartDate"));
			tLLModifyApplySchema.setEndDate(request.getParameter("EndDate"));
			tLLModifyApplySchema.setRemark(request.getParameter("Remark"));
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLModifyApplySchema);
			if ("SUBMITTHREE".equals(mOperate)) {
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
				tLLModifyApplySchema.setCheckConclusion(request.getParameter("CheckConclusion"));
				tLLModifyApplySchema.setCheckIdea(request.getParameter("CheckIdea"));
				tLLModifyApplySchema.setApproveConclusion(request.getParameter("ApproveConclusion"));
				tLLModifyApplySchema.setApproveIdea(request.getParameter("ApproveIdea"));
							
			}
						
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, mOperate, "LLClaimModApplyUI")) {
		tContent = tBusinessDelegate.getCErrors().getFirstError();
		tFlagStr = "Fail";		
		} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				}
	} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>                      
<html>
<script language="javascript"> 
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
