<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LJAPaySchema"%>
<%@page import="com.sinosoft.lis.schema.LOPRTManager2Schema"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	System.out.println("start");

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//输出参数
	CError cError = new CError();
	String Content = "";

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	String FlagStr = "Fail";
	
	String tRadio[] = request.getParameterValues("InpPolGridSel");
	int number = 0;
	for (number = 0; number < tRadio.length; number++) {
		if (tRadio[number].equals("1"))
			break;
	}
	String tContNo[] = request.getParameterValues("PolGrid1");
	String tPrem[] = request.getParameterValues("PolGrid3");
	String tAppntName[] = request.getParameterValues("PolGrid2");
	String tGetNoticeNo[] = request.getParameterValues("PolGrid11");
	String tPolNo[] = request.getParameterValues("PolGrid12");

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ContNo", tContNo[number]);
	tTransferData.setNameAndValue("AppntName", tAppntName[number]);
	tTransferData.setNameAndValue("GetNoticeNo", tGetNoticeNo[number]);
	tTransferData.setNameAndValue("PolNo", tPolNo[number]);


	System.out.println("ContNo=" + tContNo[number]);
	System.out.println("AppntName=" + tAppntName[number]);
	System.out.println("GetNoticeNo=" + tGetNoticeNo[number]);
	System.out.println("PolNo=" + tPolNo[number]);

	tVData.addElement(tTransferData);
	tVData.addElement(tG);

	TempFeeInvoiceF1PUI tTempFeeInvoiceF1PUI = new TempFeeInvoiceF1PUI();
	try {
		if (!tTempFeeInvoiceF1PUI.submitData(tVData, "")) {
			mErrors.copyAllErrors(tTempFeeInvoiceF1PUI.mErrors);
			mErrors.addOneError(cError);
			Content = "失败，原因是:" + mErrors.getFirstError();
		}

		mResult = tTempFeeInvoiceF1PUI.getResult();
		XmlExport txmlExport = new XmlExport();
		txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
		if (txmlExport == null) {
			System.out.println("xmlExport is null");
		} else {
					FlagStr = "Succ";
					session.putValue("PrintStream", txmlExport.getInputStream());
					response.sendRedirect("GetF1Print.jsp");
		}
	} catch (Exception ex) {
		mErrors = tTempFeeInvoiceF1PUI.mErrors;
		Content = "失败，原因是:" + mErrors.getFirstError();
	}
	if("Fail".equals(FlagStr)){
%>
<script language="javascript">
alert("<%=Content%>");
top.close();
</script>
<%
}
%>
