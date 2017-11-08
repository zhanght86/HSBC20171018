<%
/***************************************************************
 * <p>ProName��LSQuotSelectPlanSave.js</p>
 * <p>Title��ѡ�񱨼۷���</p>
 * <p>Description��ѡ�񱨼۷���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-19
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOfferPlanSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotOfferPlanSet"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOfferBindPlanSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotOfferBindPlanSet"%>
<%@page import="com.sinosoft.lis.g_quot.LSQuotPubFun"%>
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
			
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tOfferListNo = request.getParameter("OfferListNo");
			String tQuotType = request.getParameter("QuotType");
			String tTranProdType = request.getParameter("TranProdType");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			
			LSQuotOfferPlanSet tLSQuotOfferPlanSet = new LSQuotOfferPlanSet();
			LSQuotOfferBindPlanSet tLSQuotOfferBindPlanSet = new LSQuotOfferBindPlanSet();
			//ѡ�� ����ѡ�� ��Ҫ��ȡ
			if ("INSERT".equals(tOperate) || "BIND".equals(tOperate)) {
			
				String tChk [] = request.getParameterValues("InpQuotPlanGridChk"); //����е�����ֵ
				String tSysPlanCode [] = request.getParameterValues("QuotPlanGrid1"); 
				String tPlanCode [] = request.getParameterValues("QuotPlanGrid2"); 
				for(int index=0;index<tChk.length;index++) {
			
					LSQuotOfferPlanSchema tLSQuotOfferPlanSchema = new LSQuotOfferPlanSchema();
					if(tChk[index].equals("1")) {
					
						tLSQuotOfferPlanSchema.setOfferListNo(tOfferListNo);
						tLSQuotOfferPlanSchema.setSysPlanCode(tSysPlanCode[index]);
						tLSQuotOfferPlanSchema.setPlanCode(tPlanCode[index]);
						tLSQuotOfferPlanSet.add(tLSQuotOfferPlanSchema);
					}
				}
				
			} 
			//ɾ��
			if ("DELETE".equals(tOperate)){
				
				String tChk [] = request.getParameterValues("InpBJQuotPlanGridChk"); //����е�����ֵ
				String tQSysPlanCode [] = request.getParameterValues("BJQuotPlanGrid2"); 
				String tQPlanCode [] = request.getParameterValues("BJQuotPlanGrid3"); 
				
				for(int index=0;index<tChk.length;index++) {
			
					LSQuotOfferBindPlanSchema tLSQuotOfferBindPlanSchema = new LSQuotOfferBindPlanSchema();
					if(tChk[index].equals("1")) {
					
						tLSQuotOfferBindPlanSchema.setOfferListNo(tOfferListNo);
						tLSQuotOfferBindPlanSchema.setQSysPlanCode(tQSysPlanCode[index]);
						tLSQuotOfferBindPlanSchema.setQPlanCode(tQPlanCode[index]);
						tLSQuotOfferBindPlanSet.add(tLSQuotOfferBindPlanSchema);
					}
				}
			}
			//����ѡ��
			if ("BIND".equals(tOperate)){
			
				String tBindPlanDesc = request.getParameter("BindPlanDesc");
				tTransferData.setNameAndValue("BindPlanDesc", tBindPlanDesc);
				tTransferData.setNameAndValue("TranProdType", tTranProdType);
				tTransferData.setNameAndValue("QuotType", tQuotType);
			}
			
			//��Ŀѯ�۽�����ʱ�����湤����� �������
			if ("SAVE".equals(tOperate)){
				
				LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
				
				String tSysPlanCode = request.getParameter("SysPlanCode");//����ϵͳ��������
				String tPlanCode = request.getParameter("PlanCode");//���۷�������
				String tPremCalType = request.getParameter("PremCalType");//���Ѽ��㷽ʽ
				String tEnginArea = tLSQuotPubFun.isNumNull(request.getParameter("EnginArea"));//��͹������
				String tEnginCost = tLSQuotPubFun.isNumNull(request.getParameter("EnginCost"));//��͹������
				
				tTransferData.setNameAndValue("OfferListNo", tOfferListNo);
				tTransferData.setNameAndValue("SysPlanCode", tSysPlanCode);
				tTransferData.setNameAndValue("PlanCode", tPlanCode);
				tTransferData.setNameAndValue("PremCalType", tPremCalType);
				tTransferData.setNameAndValue("EnginArea", tEnginArea);
				tTransferData.setNameAndValue("EnginCost", tEnginCost);
			
			}
			
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
				
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotOfferPlanSet);
			tVData.add(tLSQuotOfferBindPlanSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotSelectPlanUI")) {
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
