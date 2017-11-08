<%
/***************************************************************
 * <p>ProName��EdorMissionSave.jsp</p>
 * <p>Title����ȫ������</p>
 * <p>Description����ȫ������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tDefaultOperator = request.getParameter("DefaultOperator");
			String tOperate = request.getParameter("Operate");
			
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			
			tTransferData.setNameAndValue("ActivityID",tActivityID);
			tTransferData.setNameAndValue("MissionID",tMissionID);
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			tTransferData.setNameAndValue("DefaultOperator",tDefaultOperator);
			tTransferData.setNameAndValue("Operate",tOperate);
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorMissionUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "�����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = "�������쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
