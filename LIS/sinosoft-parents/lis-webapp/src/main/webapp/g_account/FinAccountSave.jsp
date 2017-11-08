<%
/***************************************************************
 * <p>ProName：FinAccountSave.jsp</p>
 * <p>Title：会计科目维护界面</p>
 * <p>Description：维护财务中涉及的会计科目</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.FinAccountSchema"%>
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
			
			String tFinCode = request.getParameter("HiddenFinCode");
			String tFinName = request.getParameter("FinName");
			String tFinType = request.getParameter("FinType");
			String tRemark = request.getParameter("Remark");
			
			FinAccountSchema tFinAccountSchema = new FinAccountSchema();
			tFinAccountSchema.setFinCode(tFinCode);
			tFinAccountSchema.setFinName(tFinName);
			tFinAccountSchema.setFinType(tFinType);
			tFinAccountSchema.setRemark(tRemark);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tFinAccountSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "FinAccountUI")) {
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
