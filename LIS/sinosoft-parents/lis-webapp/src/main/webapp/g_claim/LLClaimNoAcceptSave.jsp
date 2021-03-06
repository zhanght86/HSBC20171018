<%
/***************************************************************
 * <p>ProName：LLClaimNoAcceptSave.jsp</p>
 * <p>Title：未受理客户管理</p>
 * <p>Description：未受理客户管理</p>
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
<%@page import="com.sinosoft.lis.schema.LLNotAcceptCustomerSchema"%>
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
			
			LLNotAcceptCustomerSchema tLLNotAcceptCustomerSchema = new LLNotAcceptCustomerSchema();
			tLLNotAcceptCustomerSchema.setGrpRgtNo(request.getParameter("GrpRgtNo"));
			tLLNotAcceptCustomerSchema.setRgtNo(request.getParameter("RgtNo"));
			tLLNotAcceptCustomerSchema.setCustomerName(request.getParameter("CustomerName"));
			tLLNotAcceptCustomerSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLNotAcceptCustomerSchema.setGender(request.getParameter("Gender"));
			tLLNotAcceptCustomerSchema.setBirthday(request.getParameter("Birthday"));
			System.out.println(request.getParameter("Birthday")+"-------");
			tLLNotAcceptCustomerSchema.setIDType(request.getParameter("IDType"));
			tLLNotAcceptCustomerSchema.setIDNo(request.getParameter("IDNo"));
			tLLNotAcceptCustomerSchema.setAppAmnt(request.getParameter("AppAmnt"));
			tLLNotAcceptCustomerSchema.setBillCount(request.getParameter("BillCount"));
			tLLNotAcceptCustomerSchema.setScanCount(request.getParameter("ScanCount"));
			tLLNotAcceptCustomerSchema.setRemark(request.getParameter("NoAcceptReasonName"));
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLNotAcceptCustomerSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLNoAcceptUI")) {
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
