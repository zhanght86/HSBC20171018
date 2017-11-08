<%
/***************************************************************
 * <p>ProName��LCCoinsuranceSave.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-03
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LCCoinsuranceSchema"%>
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
			String tSerialNo = request.getParameter("SerialNo");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tMasterSlaveFlag = request.getParameter("MasterSlaveFlag");
			String tCoinComCode = request.getParameter("CoinComCode");
			String tAmntShareRate = request.getParameter("AmntShareRate");
			String tPremShareRate = request.getParameter("PremShareRate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			
			LCCoinsuranceSchema tLCCoinsuranceSchema = new LCCoinsuranceSchema();
			tLCCoinsuranceSchema.setSerialNo(tSerialNo);
			tLCCoinsuranceSchema.setGrpContNo(tGrpContNo);
			tLCCoinsuranceSchema.setMasterSlaveFlag(tMasterSlaveFlag);
			tLCCoinsuranceSchema.setCoinComCode(tCoinComCode);
			tLCCoinsuranceSchema.setAmntShareRate(tAmntShareRate);
			tLCCoinsuranceSchema.setPremShareRate(tPremShareRate);
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLCCoinsuranceSchema);
			
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCCoinsuranceUI")) {
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
