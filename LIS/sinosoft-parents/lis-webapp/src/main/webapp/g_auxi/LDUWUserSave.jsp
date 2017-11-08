<%
/***************************************************************
 * <p>ProName��LDUWUserSave.js</p>
 * <p>Title���˱��û�����</p>
 * <p>Description���˱��û�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LDUWUserSchema"%>
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
			
			String tUserCode = request.getParameter("UserCode");
			String tSupervisorFlag = request.getParameter("SupervisorFlag");
			String tPopedomLevel = request.getParameter("PopedomLevel");

			LDUWUserSchema tLDUWUserSchema = new LDUWUserSchema();
			tLDUWUserSchema.setUserCode(tUserCode);
			tLDUWUserSchema.setSupervisorFlag(tSupervisorFlag);
			tLDUWUserSchema.setUWPopedom(tPopedomLevel);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLDUWUserSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LDUWUserUI")) {
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
