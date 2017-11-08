<%
/***************************************************************
 * <p>ProName��LSQuotUWRuleSave.jsp</p>
 * <p>Title���˱�����</p>
 * <p>Description���˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotUWRuleSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotEdorRuleSchema"%>
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
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			
			String tSerialNo = request.getParameter("SerialNo");//��ˮ��
			String tRuleType = request.getParameter("RuleType");//��ȫ��������
			String tEdorCode = request.getParameter("EdorCode");//��ȫ��Ŀ
			String tCalCode = request.getParameter("CalCode");//��ȫ�㷨
			String tOtherCalCode = request.getParameter("OtherCalCode");//��������
			
			String tRiskCode = request.getParameter("RiskCode");//��Ʒ����
			String tRuleCode = request.getParameter("RuleCode");//�������
			String tParams = request.getParameter("Params");//����
			String tInputValues = request.getParameter("InputValues");//��������ֵ
			
			LSQuotUWRuleSchema tLSQuotUWRuleSchema = new LSQuotUWRuleSchema();
			LSQuotEdorRuleSchema tLSQuotEdorRuleSchema = new LSQuotEdorRuleSchema();
			
			if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate)) {
			
				tLSQuotEdorRuleSchema.setSerialNo(tSerialNo);
				tLSQuotEdorRuleSchema.setQuotNo(tQuotNo);
				tLSQuotEdorRuleSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotEdorRuleSchema.setRuleType(tRuleType);
				tLSQuotEdorRuleSchema.setEdorCode(tEdorCode);
				tLSQuotEdorRuleSchema.setCalCode(tCalCode);
				
			} else if ("DELETE".equals(tOperate)) {
				
				tLSQuotEdorRuleSchema.setSerialNo(tSerialNo);
			} else if ("SAVE".equals(tOperate)) {
			
				tLSQuotUWRuleSchema.setQuotNo(tQuotNo);
				tLSQuotUWRuleSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotUWRuleSchema.setRiskCode(tRiskCode);
				tLSQuotUWRuleSchema.setRuleCode(tRuleCode);
				tLSQuotUWRuleSchema.setParams(tParams);
				tLSQuotUWRuleSchema.setInputValues(tInputValues);
				
			}
				
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotUWRuleSchema);
			tVData.add(tLSQuotEdorRuleSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotUWRuleUI")) {
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
