<%
/***************************************************************
 * <p>ProName：LJTempFeeEnterSave.jsp</p>
 * <p>Title：进账录入</p>
 * <p>Description：进账录入</p>
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
			String tInputConclusion = request.getParameter("InputConclusion");
			String tEnterAccDate = request.getParameter("EnterAccDate");
			String tInBankCode = request.getParameter("InBankCode");
			String tInBankAccNo = request.getParameter("InBankAccNo");
			String tDraweeName = request.getParameter("DraweeName");
			String tCheckNo = request.getParameter("CheckNo");
			String tInputDesc = request.getParameter("InputDesc");
			String tPayType = request.getParameter("PayType");
			
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
			if ("ENTERTEMPFEE".equals(tOperate)) {
				
				tLJTempFeeSchema.setTempFeeNo(tTempFeeNo);
				tLJTempFeeSchema.setInputConclusion(tInputConclusion);
				tLJTempFeeSchema.setInputDesc(tInputDesc);
				tLJTempFeeSchema.setEnterAccDate(tEnterAccDate);
				
				tLJTempFeeClassSchema.setTempFeeNo(tTempFeeNo);
				tLJTempFeeClassSchema.setPayMode(tPayType);
				tLJTempFeeClassSchema.setInBankCode(tInBankCode);
				tLJTempFeeClassSchema.setInBankAccNo(tInBankAccNo);
				tLJTempFeeClassSchema.setDraweeName(tDraweeName);
				tLJTempFeeClassSchema.setChequeNo(tCheckNo);
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJTempFeeSchema);
			tVData.add(tLJTempFeeClassSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJTempFeeEnterUI")) {
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
