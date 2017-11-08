<%
/***************************************************************
 * <p>ProName：LDUWUserSave.js</p>
 * <p>Title：核保用户管理</p>
 * <p>Description：核保用户管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LDUWUserSchema"%>
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
			
			String tUserCode = request.getParameter("UserCode");
			String tSupervisorFlag = request.getParameter("SupervisorFlag");
			String tPopedomLevel = request.getParameter("PopedomLevel");

			LDUWUserSchema tLDUWUserSchema = new LDUWUserSchema();
			tLDUWUserSchema.setUserCode(tUserCode);
			tLDUWUserSchema.setSupervisorFlag(tSupervisorFlag);
			tLDUWUserSchema.setUWPopedom(tPopedomLevel);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLDUWUserSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LDUWUserUI")) {
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
