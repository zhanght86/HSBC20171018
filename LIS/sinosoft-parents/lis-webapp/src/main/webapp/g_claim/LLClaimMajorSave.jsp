<%
/***************************************************************
 * <p>ProName：LLClaimMajorSave.jsp</p>
 * <p>Title：重大案件上报/录入</p>
 * <p>Description：重大案件上报/录入</p>
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
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tFilePath = "";
	String tFileName = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			LLCaseReportDetailSchema tLLCaseReportDetailSchema = new LLCaseReportDetailSchema();
			tLLCaseReportDetailSchema.setRptNo(request.getParameter("RptNo"));
			tLLCaseReportDetailSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLCaseReportDetailSchema.setRepOperator(request.getParameter("InputOperator"));
			tLLCaseReportDetailSchema.setRepDate(request.getParameter("InputDate"));
			tLLCaseReportDetailSchema.setRepManageCom(request.getParameter("InputCom"));
			tLLCaseReportDetailSchema.setRepOpinion(request.getParameter("InputRemarks"));
			tLLCaseReportDetailSchema.setRepType("1");
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLCaseReportDetailSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimMajorUI")) {				
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {				
				tContent = "处理成功！";
				tFlagStr = "Succ";
				TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				
				tFilePath = (String) trd.getValueByName("FilePath");
				tFileName = (String) trd.getValueByName("FileName");				
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tFilePath%>", "<%=tFileName%>");
</script>
</html>
