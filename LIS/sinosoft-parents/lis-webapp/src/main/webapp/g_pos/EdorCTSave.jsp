<%
/***************************************************************
* <p>ProName��EdorCTSave.jsp</p>
 * <p>Title�������˱�</p>
 * <p>Description�������˱�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredListSchema"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredListSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
		
			String tOperate = request.getParameter("Operate");	
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tBackType = request.getParameter("BackType");
			String tBackCal = request.getParameter("BackCal");
			String tReasonDesc = request.getParameter("ReasonDesc");

			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			vTransferData.setNameAndValue("EdorNo",tEdorNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);
			vTransferData.setNameAndValue("BackType",tBackType); // �˱���ʽ
			vTransferData.setNameAndValue("BackCal",tBackCal); // �˱��㷨
			vTransferData.setNameAndValue("ReasonDesc",tReasonDesc); // �˱�ԭ������
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(vTransferData);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorCTUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ�!";
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
