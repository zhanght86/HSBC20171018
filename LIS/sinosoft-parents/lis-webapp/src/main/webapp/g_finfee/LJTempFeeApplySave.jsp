<%
/***************************************************************
 * <p>ProName：LJTempFeeApplySave.jsp</p>
 * <p>Title：进账申请</p>
 * <p>Description：进账申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJTempFeeSchema"%>
<%@page import="com.sinosoft.lis.schema.LJTempFeeClassSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	String tFilePath = "";
	String tFileName = "";
	String strVFFileName = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String cTempFeeNo = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			String tTempFeeNo = request.getParameter("TempFeeNo");
			String tPayType = request.getParameter("PayType");
			String tCustBankCode = request.getParameter("CustBankCode");
			String tCustBankAccNo = request.getParameter("CustBankAccNo");
			String tCustAccName = request.getParameter("CustAccName");
			String tMoney = request.getParameter("Money");
			String tGrpName = request.getParameter("GrpName");
			String tAgentName = request.getParameter("AgentName");
			
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
			if ("ADDTEMPFEE".equals(tOperate) || "MODIFYTEMPFEE".equals(tOperate)) {
				
				tLJTempFeeSchema.setTempFeeNo(tTempFeeNo);
				tLJTempFeeSchema.setTempFeeType("0");
				tLJTempFeeSchema.setRiskCode("0000");
				tLJTempFeeSchema.setConfFlag("0");
				tLJTempFeeSchema.setCurrency("01");
				tLJTempFeeSchema.setPayMoney(tMoney);
				tLJTempFeeClassSchema.setTempFeeNo(tTempFeeNo);
				tLJTempFeeClassSchema.setPayMode(tPayType);
				tLJTempFeeClassSchema.setChequeNo("-");
				tLJTempFeeClassSchema.setCurrency("01");
				tLJTempFeeClassSchema.setCustBankCode(tCustBankCode);
				tLJTempFeeClassSchema.setBankAccNo(tCustBankAccNo);
				tLJTempFeeClassSchema.setAccName(tCustAccName);
				tLJTempFeeClassSchema.setAppntName(tGrpName);
				tLJTempFeeClassSchema.setAgentName(tAgentName);
			} else if ("DELETETEMPFEE".equals(tOperate) || "PRINTTEMPFEE".equals(tOperate) || "TOINPTEMPFEE".equals(tOperate)) {
				
				tLJTempFeeSchema.setTempFeeNo(tTempFeeNo);
			}

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJTempFeeSchema);
			tVData.add(tLJTempFeeClassSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJTempFeeApplyUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				tFlagStr = "Succ";
				tContent = "处理成功！";
				/* cTempFeeNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1); */
				 tVData = tBusinessDelegate.getResult();
				 tFilePath=tVData.get(1).toString();
				long longst= Long.parseLong(tVData.get(0).toString())+1;
				 cTempFeeNo=tVData.get(2).toString();
				 
				 tFileName="000000"+longst+".pdf";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
<%-- 	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=cTempFeeNo%>"); --%>
parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tFilePath%>", "<%=tFileName%>","<%=cTempFeeNo%>","<%=tOperate%>");
</script>
</html>
