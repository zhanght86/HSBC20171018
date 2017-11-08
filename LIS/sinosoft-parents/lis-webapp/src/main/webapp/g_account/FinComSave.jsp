<%
/***************************************************************
 * <p>ProName：FinComSave.jsp</p>
 * <p>Title：财务机构维护</p>
 * <p>Description：财务机构维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
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
			
			TransferData tTransferData = new TransferData();
			
			if ("INSERT".equals(tOperate)) {
				
				tTransferData.setNameAndValue("FinComCode", request.getParameter("FinComCode"));
				tTransferData.setNameAndValue("FinComName", request.getParameter("FinComName"));
			} else if ("UPDATE".equals(tOperate)) {
				
				tTransferData.setNameAndValue("FinComCode", request.getParameter("HiddenFinComCode"));
				tTransferData.setNameAndValue("FinComName", request.getParameter("FinComName"));
			} else {
				tTransferData.setNameAndValue("FinComCode", request.getParameter("HiddenFinComCode"));
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "FinComUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "提交数据成功！";
			
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
