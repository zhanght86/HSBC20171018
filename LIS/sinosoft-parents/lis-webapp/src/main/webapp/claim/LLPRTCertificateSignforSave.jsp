
<%
	//**************************************************************************************************
	//Name��LLPRTCertificateSignforSave.jsp
	//Function�����۱��沢����
	//Author��wsz
	//Date:   2005-8-2
	//�޸���:niuzj
	//�޸�����:2005-09-20
	//�޸�ԭ��:��ԭ����ӡһ���ĳɴ�ӡ����
	//�޸���:niuzj
	//�޸�����:2005-10-04
	//�޸�ԭ��:�Ѵ�ӡ���������޸�Ϊֻ��ӡһ��(����GETԭ���汾)
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
	//׼��ͨ�ò���
	CError cError = new CError();
	String FlagStr = "FAIL";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		loggerDebug("LLPRTCertificateSignforSave", "��¼��Ϣû�л�ȡ!!!");
		return;
	}

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ClmNo", request
			.getParameter("ClmNo"));
	tTransferData.setNameAndValue("CustNo", request
			.getParameter("customerNo"));
	tTransferData.setNameAndValue("ClaimTag", "����");

	// ׼���������� VData
	VData tVData = new VData();
	VData tResult = new VData();
	XmlExportNew txmlExport = new XmlExportNew();

	tVData.add(tG);
	tVData.add(tTransferData);

	String busiName = "LLPRTCertificateSignforUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();

	if (!tBusinessDelegate.submitData(tVData, "", busiName)) {
		if (tBusinessDelegate.getCErrors() != null
		&& tBusinessDelegate.getCErrors().getErrorCount() > 0) {
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++) {
		//loggerDebug("LLPRTCertificateSignforSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
		tResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) tResult.getObjectByObjectName(
		"XmlExportNew", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
			loggerDebug("LLPRTCertificateSignforSave", "û�еõ�Ҫ��ʾ�������ļ�");
		}

	}

	if (FlagStr.equals("Succ")) {
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
