<%
/***************************************************************
 * <p>ProName：LSQuotSelectPlanSave.js</p>
 * <p>Title：选择报价方案</p>
 * <p>Description：选择报价方案</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
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
		tContent = "页面失效,请重新登陆";
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
			//选择 捆绑选择 都要获取
			if ("INSERT".equals(tOperate) || "BIND".equals(tOperate)) {
			
				String tChk [] = request.getParameterValues("InpQuotPlanGridChk"); //序号列的所有值
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
			//删除
			if ("DELETE".equals(tOperate)){
				
				String tChk [] = request.getParameterValues("InpBJQuotPlanGridChk"); //序号列的所有值
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
			//捆绑选择
			if ("BIND".equals(tOperate)){
			
				String tBindPlanDesc = request.getParameter("BindPlanDesc");
				tTransferData.setNameAndValue("BindPlanDesc", tBindPlanDesc);
				tTransferData.setNameAndValue("TranProdType", tTranProdType);
				tTransferData.setNameAndValue("QuotType", tQuotType);
			}
			
			//项目询价建工险时，保存工程造价 工程面积
			if ("SAVE".equals(tOperate)){
				
				LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
				
				String tSysPlanCode = request.getParameter("SysPlanCode");//报价系统方案编码
				String tPlanCode = request.getParameter("PlanCode");//报价方案编码
				String tPremCalType = request.getParameter("PremCalType");//保费计算方式
				String tEnginArea = tLSQuotPubFun.isNumNull(request.getParameter("EnginArea"));//最低工程面积
				String tEnginCost = tLSQuotPubFun.isNumNull(request.getParameter("EnginCost"));//最低工程造价
				
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
