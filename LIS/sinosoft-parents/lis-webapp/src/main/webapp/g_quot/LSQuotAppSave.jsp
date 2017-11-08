<%
/***************************************************************
 * <p>ProName��LSQuotAppSave.jsp</p>
 * <p>Title��ѯ����������</p>
 * <p>Description��ѯ����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-04-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			
			tTransferData.setNameAndValue("ActivityID",tActivityID);
			tTransferData.setNameAndValue("MissionID",tMissionID);
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			tTransferData.setNameAndValue("DefaultOperator",tGI.Operator);
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			if (!"RETURN".equals(tOperate)) {
				tOperate = "operator";
			}
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotAppUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				if ("RETURN".equals(tOperate)) {
					tContent = "�˻ع����سɹ���";
				} else {
					tContent = "��������ɹ���";
				}
				
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
