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
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	loggerDebug("EdorNoticePrintSave", "开始执行打印操作");
	String Content = "";
	CErrors tError = null;
	String FlagStr = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	VData tVData = new VData();
	String PrtSeq = request.getParameter("PrtSeq");
	//获得通知书类型 liuzhao 2007-08-21
	String NoticeType = request.getParameter("NoticeType");

	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	TransferData tTransferData = new TransferData();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	//添加通知书类型 liuzhao 2007-08-21
	tTransferData.setNameAndValue("NoticeType", NoticeType);

	tVData.add(tG);
	tVData.add(tTransferData);
	tVData.add(tLOPRTManagerSchema);
	VData mResult = new VData();

	BusinessDelegate tBusinessDelegate;
	XmlExportNew txmlExport = new XmlExportNew();

	String busiName = "BqNoticeUI";
	tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, "PRINT", busiName)) {
		if (tBusinessDelegate.getCErrors() != null
		&& tBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = "申请失败，原因是:"
			+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = "操作失败";
			FlagStr = "Fail";
		}
	} else {
		mResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			FlagStr = "Fail";
			tError = tBusinessDelegate.getCErrors();
			Content = "打印失败,原因是XMLExportNew对象为空"
			+ tError.getFirstError();
		} else {
//			session中的值用于工作流的跳转
			session.putValue("PrintNo",PrtSeq );
			session.putValue("Code","bqnotice"); 

			request.setAttribute("PrintStream", txmlExport
			.getInputStream());
			request.getRequestDispatcher("../print/ShowPrint.jsp")
			.forward(request, response);
			loggerDebug("EdorNoticePrintSave",
			"after calling forward()");
		}
	}

	if (FlagStr.equals("")) {
		loggerDebug("EdorNoticePrintSave",
		"outputStream object has been open");
%>
<html>
<script language="javascript">
	top.opener.focus();

</script>
</html>
<%
}else{
%>
<html>
<script language="javascript">
	top.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");
	top.close();
</script>
</html>
<%} %>