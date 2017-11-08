<%
/***************************************************************
 * <p>ProName��LLClaimAssReportSave.jsp</p>
 * <p>Title������������</p>
 * <p>Description������������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLClaimBlackListSchema"%>
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
			String tState = request.getParameter("State");
			
			LLClaimBlackListSchema tLLClaimBlackListSchema = new LLClaimBlackListSchema();
			tLLClaimBlackListSchema.setBussType("CM");
			tLLClaimBlackListSchema.setBussNo(request.getParameter("RgtNo"));
			tLLClaimBlackListSchema.setCustomerNo(request.getParameter("CustomerNo"));
			if (tState!=null && "0".equals(tState)) {
				tLLClaimBlackListSchema.setAdjustReason(request.getParameter("AdjustReason"));
			} else {
				tLLClaimBlackListSchema.setAdjustReason(request.getParameter("AdjustReason1"));
			}
			tLLClaimBlackListSchema.setAdjustRemark(request.getParameter("AdjustRemark"));			
						
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLClaimBlackListSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimBlackListUI")) {
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