
<%
	//**************************************************************************************************
	//Name：LLPRTCertificatePutOutSave.jsp
	//Function：结论保存并理算
	//Author：续涛
	//Date:   2005-07-14
	//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.FileQueue"%>
<%@page import="com.sinosoft.lis.f1print.CombineVts"%>
<%@page import="com.sinosoft.lis.f1print.AccessVtsFile"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
			loggerDebug("LLPRTCertificatePutOutSave",
			"############################");
	loggerDebug("LLPRTCertificatePutOutSave",
			"############################");
	//准备通用参数
	CError cError = new CError();
	String FlagStr = "FAIL";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		loggerDebug("LLPRTCertificatePutOutSave", "登录信息没有获取!!!");
		return;
	}
	loggerDebug("LLPRTCertificatePutOutSave",
			"############################");
	//准备数据容器信息
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ClmNo", request
			.getParameter("ClmNo"));
	tTransferData.setNameAndValue("CustNo", request
			.getParameter("customerNo"));
	tTransferData.setNameAndValue("PrtSeq", request
			.getParameter("PrtSeq"));
	tTransferData.setNameAndValue("CustomerNo", request
			.getParameter("customerNo"));
	tTransferData.setNameAndValue("RgtClass", request
			.getParameter("RgtClass"));

	// 准备传输数据 VData
	VData tVData = new VData();
	VData tResult = new VData();
	XmlExportNew txmlExport = new XmlExportNew();

	tVData.add(tG);
	tVData.add(tTransferData);

	String busiName = "LLPRTCertificatePutOutUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();

	if (!tBusinessDelegate.submitData(tVData, "", busiName)) {
		if (tBusinessDelegate.getCErrors() != null
		&& tBusinessDelegate.getCErrors().getErrorCount() > 0) {
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++) {
		Content = Content
				+ "原因是: "
				+ tBusinessDelegate.getCErrors().getError(i).errorMessage;
			}
			FlagStr = "Fail";
		} else {
			Content = "处理失败";
			FlagStr = "Fail";
		}
	} else {
		loggerDebug("LLPRTCertificatePutOutSave", "2222222222222222222"
		+ FlagStr);
		tResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) tResult.getObjectByObjectName(
		"XmlExportNew", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "没有得到要显示的数据文件";
			loggerDebug("LLPRTCertificatePutOutSave", "没有得到要显示的数据文件");
		}
	}

	loggerDebug("LLPRTCertificatePutOutSave", "1111111111111111111"
			+ FlagStr);
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
