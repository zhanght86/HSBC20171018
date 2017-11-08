<%
/***************************************************************
 * <p>ProName：LJDebtsPayApplySave.jsp</p>
 * <p>Title：坏账申请</p>
 * <p>Description：坏账申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-09-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJDebtsPaySchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
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
			String tDebtsPayNo = request.getParameter("DebtsPayNo");
			String tConfirmConlusion = request.getParameter("ConfirmConlusion");
			String tConfirmDesc = request.getParameter("ConfirmDesc");
			
			LJDebtsPaySchema tLJDebtsPaySchema = new LJDebtsPaySchema();
			tLJDebtsPaySchema.setDebtsNo(tDebtsPayNo);
			tLJDebtsPaySchema.setConfirmConclusion(tConfirmConlusion);
			tLJDebtsPaySchema.setConfirmDesc(tConfirmDesc);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJDebtsPaySchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJDebtsPayConfirmUI")) {
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
