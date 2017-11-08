<%
/***************************************************************
 * <p>ProName��LJTempFeeOutApplySave.jsp</p>
 * <p>Title�������˷�����</p>
 * <p>Description�������˷�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJRefundConfirmSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String cTempFeeNo = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			String tApplyBatNo = request.getParameter("ApplyBatNo");
			String tConfirmDesc = request.getParameter("ConfirmDesc");
			String tConfirmConclusion = request.getParameter("ConfirmConclusion");
			
			LJRefundConfirmSchema tLJRefundConfirmSchema = new LJRefundConfirmSchema();
			if ("CONFIRMSUBMIT".equals(tOperate)) {
				
				tLJRefundConfirmSchema.setApplyBatNo(tApplyBatNo);
				tLJRefundConfirmSchema.setConfirmDesc(tConfirmDesc);
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ConfirmConclusion", tConfirmConclusion);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJRefundConfirmSchema);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJOutPayOutConfirmUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				tFlagStr = "Succ";
				tContent = "����ɹ���";
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
