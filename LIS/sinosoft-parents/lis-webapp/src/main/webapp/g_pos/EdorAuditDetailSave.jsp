<%
/***************************************************************
 * <p>ProName��EdorCheckDetailSave.jsp</p>
 * <p>Title����ȫ��˴���</p>
 * <p>Description����ȫ��˴���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-24
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
			
			String tOperate = request.getParameter("Operate");
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tAuditConclu = request.getParameter("AuditConclu");
			String tAuditOpinion = request.getParameter("AuditOpinion");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
			tTransferData.setNameAndValue("EdorAppNo", tEdorAppNo);
			tTransferData.setNameAndValue("EdorNo", tEdorNo);
			tTransferData.setNameAndValue("AuditConclu", tAuditConclu);
			tTransferData.setNameAndValue("AuditOpinion", tAuditOpinion);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorAuditDetailUI")) {
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
