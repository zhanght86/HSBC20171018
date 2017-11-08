<%
/***************************************************************
 * <p>ProName:EdorSFSave.jsp</p>
 * <p>Title:  ���������ո���</p>
 * <p>Description:���������ո���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-01-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LPGrpEdorItemSchema"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
		
			String tOperate = request.getParameter("Operate");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorType = request.getParameter("EdorType");
			
			if ("SAVE".equals(tOperate)) {
			
				String tGetType = request.getParameter("GetType");
				String tGetMoney = request.getParameter("GetMoney");
				
				tLPGrpEdorItemSchema.setDisplayType(tGetType);
				tLPGrpEdorItemSchema.setGetMoney(tGetMoney);
			}
			
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			vTransferData.setNameAndValue("EdorNo",tEdorNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(tLPGrpEdorItemSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorSFUI")) {
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
