<%
/***************************************************************
 * <p>ProName：LSQuotChangePremSave.jsp</p>
 * <p>Title：报价单打印--变更保费</p>
 * <p>Description：报价单打印--变更保费</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
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
		tContent = "页面失效,请重新登陆";
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
			
			if ("3".equals(tChangeType)) {//非固定保额转固定保额
				
				TransferData tTransferData = new TransferData();
				String tExceptPrem = request.getParameter("ExceptPrem");//原 期望保费(元)/期望费率/费率折扣
				String tMinAmnt = request.getParameter("MinAmnt");//原 最低保额
				String tMaxAmnt = request.getParameter("MaxAmnt");//原 最高保额
				
				String tExceptPremType = request.getParameter("ExceptPremType");//期望保费类型
				String tChangeAmnt = request.getParameter("FixedAmntChange1");
				String tChangePrem = request.getParameter("FixedPremChange1");
				
				tLSQuotOfferPlanDetailSchema.setFixedAmnt(tChangeAmnt);//变更后，保额
				tLSQuotOfferPlanDetailSchema.setExceptPrem(tChangePrem);//变更后，保费
				
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
				tContent = "处理成功！";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
