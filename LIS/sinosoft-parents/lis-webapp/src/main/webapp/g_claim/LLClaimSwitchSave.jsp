<%
/***************************************************************
 * <p>ProName��LLClaimSwitchSave.jsp</p>
 * <p>Title��������ͣ������</p>
 * <p>Description��δ�����ͻ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLRegisterTraceSchema"%>
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
			
			String tOperate = request.getParameter("Operate");
			
			LLRegisterTraceSchema tLLRegisterTraceSchema = new LLRegisterTraceSchema();

			tLLRegisterTraceSchema.setRgtNo(request.getParameter("RgtNo"));
			tLLRegisterTraceSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLRegisterTraceSchema.setPauseReason(request.getParameter("PauseReason"));
			tLLRegisterTraceSchema.setPauseRemark(request.getParameter("ReasonDesc"));
						
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLRegisterTraceSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimSwitchUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "�����ɹ���";
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