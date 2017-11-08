<%@ page language="java"%>
<%@page import="com.sinosoft.utility.*"%>
<%@ page import="com.sinosoft.lis.f1print.BankPayListBL"%>
<%
			String StartDate = StrTool.unicodeToGBK(
			request.getParameter("StartDate")).trim();
	String EndDate = StrTool.unicodeToGBK(
			request.getParameter("EndDate")).trim();
	String BankCode = StrTool.unicodeToGBK(
			request.getParameter("BankCode")).trim();
	String ManageCom = StrTool.unicodeToGBK(
			request.getParameter("ManageCom")).trim();

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("StartDate", StartDate);
	tTransferData.setNameAndValue("EndDate", EndDate);
	tTransferData.setNameAndValue("BankCode", BankCode);
	tTransferData.setNameAndValue("ManageCom", ManageCom);
	VData tVData = new VData();
	tVData.add(tTransferData);

	BankPayListBL bl = new BankPayListBL();

	if (bl.submitData(tVData)) {
		VData mResult = bl.getResult();
		XmlExport txmlExport = (XmlExport) mResult
		.getObjectByObjectName("XmlExport", 0);
		if (txmlExport == null) {
			loggerDebug("BankPayListSave","null");
		}
		session.putValue("PrintStream", txmlExport.getInputStream());
		loggerDebug("BankPayListSave","put session value");
		response.sendRedirect("../f1print/GetF1Print.jsp");
	} else {
%>
<html>
	<script language="javascript">
		alert('<%=bl.mErrors.getLastError()%>');
		window.close();
</script>
</html>
<%
}
%>
