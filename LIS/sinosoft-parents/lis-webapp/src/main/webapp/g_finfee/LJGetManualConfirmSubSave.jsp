<%
/***************************************************************
 * <p>ProName：LJGetManualConfirmSubSave.jsp</p>
 * <p>Title: 手动付费确认明细</p>
 * <p>Description：手动付费确认明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJGetManualSubSchema"%>
<%@page import="com.sinosoft.lis.vschema.LJGetManualSubSet"%>
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
			String tTransNo = request.getParameter("TransNo");
			String tConfirmConclusion = request.getParameter("ConfirmConclusion");
			String tConfirmDesc = request.getParameter("ConfirmDesc");
			String tGetType = request.getParameter("GetType");
			String tOutBankCode = request.getParameter("OutBankCode");
			String tOutBankAccNo = request.getParameter("OutBankAccNo");
			String tEnterAccDate = request.getParameter("EnterAccDate");
			String tGetDealType = request.getParameter("GetDealType");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("TransNo", tTransNo);
			tTransferData.setNameAndValue("ConfirmConclusion", tConfirmConclusion);
			tTransferData.setNameAndValue("ConfirmDesc", tConfirmDesc);
			tTransferData.setNameAndValue("GetType", tGetType);
			tTransferData.setNameAndValue("OutBankCode", tOutBankCode);
			tTransferData.setNameAndValue("OutBankAccNo", tOutBankAccNo);
			tTransferData.setNameAndValue("EnterAccDate", tEnterAccDate);
			tTransferData.setNameAndValue("GetDealType", tGetDealType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJGetManualConfirmSubUI")) {
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
