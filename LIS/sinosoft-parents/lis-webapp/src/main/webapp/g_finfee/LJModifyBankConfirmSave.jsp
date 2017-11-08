<%
/***************************************************************
 * <p>ProName：LJMoidfyBankConfirmSave.jsp</p>
 * <p>Title: 客户银行信息修改审核</p>
 * <p>Description：客户银行信息修改审核</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-08-02
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LJModifyBankConfirmSchema"%>
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
			String tApplyBatNo = request.getParameter("ApplyBatNo");
			String tConfirmConclusion = request.getParameter("ConfirmConclusion");
			String tConfirmDesc = request.getParameter("ConfirmDesc");
			
			LJModifyBankConfirmSchema tLJModifyBankConfirmSchema = new LJModifyBankConfirmSchema();
			tLJModifyBankConfirmSchema.setApplyBatNo(tApplyBatNo);
			tLJModifyBankConfirmSchema.setConfirmConclusion(tConfirmConclusion);
			tLJModifyBankConfirmSchema.setConfirmDesc(tConfirmDesc);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJModifyBankConfirmSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJModifyBankConfirmUI")) {
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
