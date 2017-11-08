<%
/***************************************************************
 * <p>ProName��LSQuotChangePremSave.jsp</p>
 * <p>Title�����۵���ӡ--�������</p>
 * <p>Description�����۵���ӡ--�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-21
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOfferPlanDetailSchema"%>
<%@page import="com.sinosoft.lis.quot.LSQuotPubFun"%>
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
			
			String tOfferListNo = request.getParameter("OfferListNo");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tRiskCode = request.getParameter("RiskCode");
			String tDutyCode = request.getParameter("DutyCode");
			
			String tChangeType = request.getParameter("ChangeType");
			String tChangeAmntType = request.getParameter("AmntTypeChange");
			String tChangeFixedAmnt = request.getParameter("FixedAmntChange");
			String tChangeSalaryMult = request.getParameter("SalaryMultChange");
			String tChangeMaxAmnt = request.getParameter("MaxAmntChange");
			String tChangeMinAmnt = request.getParameter("MinAmntChange");
			String tChangeExceptPrem = request.getParameter("ExceptPremChange");
			
			LSQuotOfferPlanDetailSchema tLSQuotOfferPlanDetailSchema = new LSQuotOfferPlanDetailSchema();
			
			tLSQuotOfferPlanDetailSchema.setOfferListNo(tOfferListNo);
			tLSQuotOfferPlanDetailSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotOfferPlanDetailSchema.setPlanCode(tPlanCode);
			tLSQuotOfferPlanDetailSchema.setRiskCode(tRiskCode);
			tLSQuotOfferPlanDetailSchema.setDutyCode(tDutyCode);
			
			tLSQuotOfferPlanDetailSchema.setChangeType(tChangeType);
			tLSQuotOfferPlanDetailSchema.setFixedAmnt(tChangeFixedAmnt);
			tLSQuotOfferPlanDetailSchema.setSalaryMult(tChangeSalaryMult);
			tLSQuotOfferPlanDetailSchema.setMaxAmnt(tChangeMaxAmnt);
			tLSQuotOfferPlanDetailSchema.setMinAmnt(tChangeMinAmnt);
			tLSQuotOfferPlanDetailSchema.setExceptPrem(tChangeExceptPrem);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLSQuotOfferPlanDetailSchema);
			
			if ("3".equals(tChangeType)) {//�ǹ̶�����ת�̶�����
				
				TransferData tTransferData = new TransferData();
				String tExceptPrem = request.getParameter("ExceptPrem");//ԭ ��������(Ԫ)/��������/�����ۿ�
				String tMinAmnt = request.getParameter("MinAmnt");//ԭ ��ͱ���
				String tMaxAmnt = request.getParameter("MaxAmnt");//ԭ ��߱���
				
				String tExceptPremType = request.getParameter("ExceptPremType");//������������
				String tChangeAmnt = request.getParameter("FixedAmntChange1");
				String tChangePrem = request.getParameter("FixedPremChange1");
				
				tLSQuotOfferPlanDetailSchema.setFixedAmnt(tChangeAmnt);//����󣬱���
				tLSQuotOfferPlanDetailSchema.setExceptPrem(tChangePrem);//����󣬱���
				
				tTransferData.setNameAndValue("ExceptPrem", tExceptPrem);
				tTransferData.setNameAndValue("MinAmnt", tMinAmnt);
				tTransferData.setNameAndValue("MaxAmnt", tMaxAmnt);
				tTransferData.setNameAndValue("ExceptPremType", tExceptPremType);
				
				tVData.add(tTransferData);
			}
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotChangePremUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "����ɹ���";
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
