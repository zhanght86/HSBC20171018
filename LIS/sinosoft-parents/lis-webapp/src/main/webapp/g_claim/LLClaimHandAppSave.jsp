<%
/***************************************************************
 * <p>ProName：LLClaimHandAppSave.jsp</p>
 * <p>Title：交接流转号申请页面</p>
 * <p>Description：交接流转号申请页面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLClaimPageNoSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
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
			
			LLClaimPageNoSchema tLLClaimPageNoSchema = new LLClaimPageNoSchema();
			tLLClaimPageNoSchema.setPageNo(request.getParameter("PageNo"));
			tLLClaimPageNoSchema.setSumNum(request.getParameter("SumNum"));
			tLLClaimPageNoSchema.setRemark(request.getParameter("Remark"));
			System.out.println("---------------------");
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLClaimPageNoSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimPageNoUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
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
