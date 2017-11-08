<%
/***************************************************************
 * <p>ProName：LDWorkFlowSave.jsp</p>
 * <p>Title：工作流驱动</p>
 * <p>Description：工作流驱动</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-11-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.schema.LDQuestionSchema"%>
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
			
			String tScanManageCom = request.getParameter("ScanManageCom");
			String tBussType = request.getParameter("BussType");
			String tSubType = request.getParameter("SubType");
			String tPropType = request.getParameter("PropType");
			String tDocCode = request.getParameter("DocCode");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ScanManageCom",tScanManageCom);
			tTransferData.setNameAndValue("BussType",tBussType);
			tTransferData.setNameAndValue("SubType",tSubType);
			tTransferData.setNameAndValue("PropType",tPropType);
			tTransferData.setNameAndValue("DocCode",tDocCode);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LDWorkFlowUI")) {
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
