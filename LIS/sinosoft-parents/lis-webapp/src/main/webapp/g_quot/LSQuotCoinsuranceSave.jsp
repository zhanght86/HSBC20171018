<%
/***************************************************************
 * <p>ProName��LSQuotCoinsuranceSave.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-03
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotCoinsuranceSchema"%>
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
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tMasterSlaveFlag = request.getParameter("MasterSlaveFlag");
			String tCoinComCode = request.getParameter("CoinComCode");
			String tAmntShareRate = request.getParameter("AmntShareRate");
			String tPremShareRate = request.getParameter("PremShareRate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			
			LSQuotCoinsuranceSchema tLSQuotCoinsuranceSchema = new LSQuotCoinsuranceSchema();
			tLSQuotCoinsuranceSchema.setSerialNo(tSerialNo);
			tLSQuotCoinsuranceSchema.setQuotNo(tQuotNo);
			tLSQuotCoinsuranceSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotCoinsuranceSchema.setMasterSlaveFlag(tMasterSlaveFlag);
			tLSQuotCoinsuranceSchema.setCoinComCode(tCoinComCode);
			tLSQuotCoinsuranceSchema.setAmntShareRate(tAmntShareRate);
			tLSQuotCoinsuranceSchema.setPremShareRate(tPremShareRate);
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotCoinsuranceSchema);
			
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotCoinsuranceUI")) {
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
