
<%
	//**************************************************************************************************
	//Name：LLPRTPatchFeeSave.jsp
	//Function：补交保费通知书
	//Author：wsz
	//Date:   2005-8-8
	//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	//页面有效
	if (tGI == null) {
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
		loggerDebug("LLPRTPatchFeeSave", "页面失效,请重新登陆");
	}

	String transact = request.getParameter("fmtransact"); //获取操作
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
	loggerDebug("LLPRTPatchFeeSave", "tPath=" + tPath);

	//String使用TransferData打包后提交-----用于传送 赔案号、客户号
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("PrtSeq", request
			.getParameter("PrtSeq"));
	mTransferData.setNameAndValue("Path", tPath);

	VData tVData = new VData(); //准备往传输数据 VData
	VData tResult = new VData(); //接受后台的数据
	XmlExportNew txmlExport = new XmlExportNew();
	tVData.add(tGI);
	tVData.add(mTransferData);
	String busiName = "LLPRTPatchFeeUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, transact, busiName)) {
		if (tBusinessDelegate.getCErrors() != null
		&& tBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = "数据提交失败，原因是:"
			+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "数据提交失败";
			FlagStr = "Fail";
		}
	} else {
		Content = " 数据提交成功! ";
		FlagStr = "Succ";
		tResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) tResult.getObjectByObjectName(
		"XmlExportNew", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "没有得到要显示的数据文件";
			loggerDebug("LLPRTPatchFeeSave", "没有得到要显示的数据文件");
		}
	}
	if (FlagStr.equals("Succ")) {
//		session中的值用于工作流的跳转
		session.putValue("PrtSeq", request.getParameter("PrtSeq"));

		request
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
	} else {
%>
<html>
<script language="javascript">  
    alert("<%=Content%>");
    top.close();  
</script>
</html>
<%
}
%>
