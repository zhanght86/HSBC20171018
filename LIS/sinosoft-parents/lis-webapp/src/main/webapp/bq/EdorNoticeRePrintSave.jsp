<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//程序名称：EdorNoticePrintSave.jsp
	//程序功能：保全通知书在线打印控制台
	//创建日期：2005-08-02 16:20:22
	//创建人  ：liurx
	//更新记录：  更新人    更新日期      更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.report.f1report.*"%>
<%
	loggerDebug("EdorNoticeRePrintSave", "开始执行打印操作");
	String Content = "";
	CErrors tError = null;
	String FlagStr = "Fail";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	VData tVData = new VData();
	String PrtSeq = request.getParameter("PrtSeq");
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	tVData.add(tG);
	tVData.add(tLOPRTManagerSchema);
	VData mResult = new VData();
	CErrors mErrors = new CErrors();

	XmlExportNew txmlExport = new XmlExportNew();
	//BqNoticeUI tBqNoticeUI = new BqNoticeUI();
	 String busiName="BqNoticeUI";
	 BusinessDelegate  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, "PRINT",busiName)) {
		FlagStr = "Fail";
		Content = tBusinessDelegate.getCErrors().getFirstError().toString();
	} else {
		mResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			FlagStr = "Fail";
			tError = tBusinessDelegate.getCErrors();
			Content = "打印失败,原因是＝＝" + tError.getFirstError();
		} else {
			request.setAttribute("PrintStream", txmlExport
			.getInputStream());
			request.getRequestDispatcher("../print/ShowPrint.jsp")
			.forward(request, response);
		}
	}
	if (!Content.equals("")) {
		loggerDebug("EdorNoticeRePrintSave",
		"outputStream object has been open");
%>
<html>
<script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
}
%>
