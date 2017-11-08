<%
/***************************************************************
 * <p>ProName��LCAgentToUserSave.jsp</p>
 * <p>Title���ͻ��������</p>
 * <p>Description���ͻ��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-07-29
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LAAgentToUserSchema"%>
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
		
			LAAgentToUserSchema tLAAgentToUserSchema = null;
			
			//��ǰ̨��ȡ��Ϣ
			String tAgentCode = request.getParameter("AgentCode");
			String tUserCode = request.getParameter("UserCode");
			String tOperate = request.getParameter("Operate");
		
						
			if("INSERT".equals(tOperate)){
				tLAAgentToUserSchema = new LAAgentToUserSchema();
				tLAAgentToUserSchema.setAgentCode(tAgentCode);
				tLAAgentToUserSchema.setUserCode(tUserCode);
				tLAAgentToUserSchema.setState("1");
			}else if("DELETE".equals(tOperate)){
				tLAAgentToUserSchema = new LAAgentToUserSchema();
				tLAAgentToUserSchema.setAgentCode(tAgentCode);
				tLAAgentToUserSchema.setUserCode(tUserCode);	
			}
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLAAgentToUserSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCAgentToUserUI")) {
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
