<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.CashValueTablePrintBL"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	loggerDebug("CashValuePrintSave", "--------start-----------");
	String FlagStr = ""; //�������	
	String tOperate = ""; //��������
	String Content = "";

	String mContNo = request.getParameter("ContNo");
	XmlExportNew txmlExport = new XmlExportNew();;
	VData tData = new VData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	// ׼���������� VData
	tData.addElement(mContNo);
	tData.addElement(tG);

	loggerDebug("CashValuePrintSave", "--------ready-----------");
	CashValueTablePrintBL tCashValueTablePrintBL = new CashValueTablePrintBL();
	if (!tCashValueTablePrintBL.submitData(tData, tOperate)) {
		Content = " ����ʧ�ܣ�ԭ����: "
		+ tCashValueTablePrintBL.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	} else {
		loggerDebug("CashValuePrintSave", "-------succes---------");
		VData mResult = new VData();
		mResult = tCashValueTablePrintBL.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			loggerDebug("CashValuePrintSave",
			"-----------result-null---------------");
			FlagStr = "Fail";
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
		}
	}
	if (!FlagStr.equalsIgnoreCase("Fail")) {

		request.setAttribute("PrintStream", txmlExport.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
	} else {
%>
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//top.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
}
%>
