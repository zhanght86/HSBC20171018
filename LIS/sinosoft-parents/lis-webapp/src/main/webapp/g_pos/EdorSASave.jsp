<%
/***************************************************************
* <p>ProName��EdorSASave.jsp</p>
 * <p>Title�������ձ�������</p>
 * <p>Description�������ձ�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-25
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
	TransferData vTransferData = new TransferData();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
		
			String tOperate = request.getParameter("Operate");	
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");
	
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");
			
			String tStopDate = request.getParameter("StopDate");
			String tGetMoney = request.getParameter("GetMoney");
			String tReason = request.getParameter("Reason");
			String tReasonDesc = request.getParameter("ReasonDesc");
						
			if("SAVE".equals(tOperate)){
				vTransferData.setNameAndValue("MissionID",tMissionID);
				vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
				vTransferData.setNameAndValue("ActivityID",tActivityID);
				vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
				vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
				vTransferData.setNameAndValue("EdorType",tEdorType);
				vTransferData.setNameAndValue("EdorNo",tEdorNo);
				vTransferData.setNameAndValue("StopDate",tStopDate);
				vTransferData.setNameAndValue("GetMoney",tGetMoney);
				vTransferData.setNameAndValue("Reason",tReason);
				vTransferData.setNameAndValue("ReasonDesc",tReasonDesc);
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(vTransferData);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorSAUI")) {
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
