<%
/***************************************************************
 * <p>ProName��LLClimCaseSave.jsp</p>
 * <p>Title������������������</p>
 * <p>Description������������������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
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
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			String tGrpRgtNo = 	request.getParameter("GrpRgtNo");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpRgtNo",tGrpRgtNo);
			
			//��¼�����Ϣ--LLClaimUWMain
			LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
			tLLClaimUWMainSchema.setClmNo(request.getParameter("RegisterNo"));
			tLLClaimUWMainSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLClaimUWMainSchema.setCheckType("1");
			tLLClaimUWMainSchema.setExamConclusion(request.getParameter("ChkConclusion"));			
			tLLClaimUWMainSchema.setRemark(request.getParameter("CheckAdvice"));
			tLLClaimUWMainSchema.setExamIdea(request.getParameter("ChkAdvice"));
														
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLClaimUWMainSchema);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimCaseCheckUI")) {
				tContent = tBusinessDelegate.getCErrors().getLastError();
				tFlagStr = "Fail";
			} else {

				tContent = "����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			
			ex.printStackTrace();
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
