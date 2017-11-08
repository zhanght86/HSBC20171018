<%
/***************************************************************
 * <p>ProName��LJDebtsPayApplySave.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-09-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJDebtsPaySchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			String tDebtsPayNo = request.getParameter("DebtsPayNo");
			String tConfirmConlusion = request.getParameter("ConfirmConlusion");
			String tConfirmDesc = request.getParameter("ConfirmDesc");
			
			LJDebtsPaySchema tLJDebtsPaySchema = new LJDebtsPaySchema();
			tLJDebtsPaySchema.setDebtsNo(tDebtsPayNo);
			tLJDebtsPaySchema.setConfirmConclusion(tConfirmConlusion);
			tLJDebtsPaySchema.setConfirmDesc(tConfirmDesc);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJDebtsPaySchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJDebtsPayConfirmUI")) {
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
