
<%
	//**************************************************************************************************
	//Name��LLPRTCertificatePutOutSave.jsp
	//Function�����۱��沢����
	//Author������
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
	//׼��ͨ�ò���
	CError cError = new CError();
	String FlagStr = "FAIL";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		loggerDebug("LLPRTCertificatePutOutSave", "��¼��Ϣû�л�ȡ!!!");
		return;
	}
	loggerDebug("LLPRTCertificatePutOutSave",
			"############################");
	//׼������������Ϣ
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

	// ׼���������� VData
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
				+ "ԭ����: "
				+ tBusinessDelegate.getCErrors().getError(i).errorMessage;
			}
			FlagStr = "Fail";
		} else {
			Content = "����ʧ��";
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
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
			loggerDebug("LLPRTCertificatePutOutSave", "û�еõ�Ҫ��ʾ�������ļ�");
		}
	}

	loggerDebug("LLPRTCertificatePutOutSave", "1111111111111111111"
			+ FlagStr);
	if (FlagStr.equals("Succ")) {
//		session�е�ֵ���ڹ���������ת
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
