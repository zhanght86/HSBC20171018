<%
/***************************************************************
 * <p>ProName：LSQuotCombiPrintSave.jsp</p>
 * <p>Title：套餐打印</p>
 * <p>Description：套餐打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-08-07
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOfferListSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOfferPlanSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOfferListNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			tOfferListNo  = request.getParameter("OfferListNo");
			String tPreCustomerNo = request.getParameter("PreCustomerNo");
			String tPreCustomerName = request.getParameter("PreCustomerName");
			String tPlanDesc = request.getParameter("PlanDesc");
			String tCombiMult = request.getParameter("CombiMult");
			String tCombiCode = request.getParameter("CombiCode");
			String tCombiRate = request.getParameter("CombiRate");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode= request.getParameter("PlanCode");
			
			LSQuotOfferListSchema tLSQuotOfferListSchema = new LSQuotOfferListSchema();
			LSQuotOfferPlanSchema tLSQuotOfferPlanSchema = new LSQuotOfferPlanSchema();
			
			if ("CREATE".equals(tOperate)) {
				
				tLSQuotOfferListSchema.setPreCustomerNo(tPreCustomerNo);
				tLSQuotOfferListSchema.setPreCustomerName(tPreCustomerName);
			}
			
			if ("INSERT".equals(tOperate)) {
				
				tLSQuotOfferPlanSchema.setOfferListNo(tOfferListNo);
				tLSQuotOfferPlanSchema.setPlanDesc(tPlanDesc);
				tLSQuotOfferPlanSchema.setCombiMult(tCombiMult);
				tLSQuotOfferPlanSchema.setCombiCode(tCombiCode);
				tLSQuotOfferPlanSchema.setCombiRate(tCombiRate);
			}
			if ("UPDATE".equals(tOperate)) {
				
				tLSQuotOfferPlanSchema.setOfferListNo(tOfferListNo);
				tLSQuotOfferPlanSchema.setSysPlanCode(tSysPlanCode);
				tLSQuotOfferPlanSchema.setPlanCode(tPlanCode);
				tLSQuotOfferPlanSchema.setPlanDesc(tPlanDesc);
				tLSQuotOfferPlanSchema.setCombiCode(tCombiCode);
				tLSQuotOfferPlanSchema.setCombiMult(tCombiMult);
				tLSQuotOfferPlanSchema.setCombiRate(tCombiRate);
			}
			if ("DELETE".equals(tOperate)) {
			
				tLSQuotOfferPlanSchema.setOfferListNo(tOfferListNo);
				tLSQuotOfferPlanSchema.setSysPlanCode(tSysPlanCode);
				tLSQuotOfferPlanSchema.setPlanCode(tPlanCode);
			}
			
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLSQuotOfferListSchema);
			tVData.add(tLSQuotOfferPlanSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotCombiPrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
				
				if ("CREATE".equals(tOperate)) {
				
					TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tOfferListNo =(String)tTransferData.getValueByName("OfferListNo");
				}
							}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tOfferListNo%>");
</script>
</html>
