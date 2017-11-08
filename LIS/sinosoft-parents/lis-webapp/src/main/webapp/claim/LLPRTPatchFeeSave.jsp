
<%
	//**************************************************************************************************
	//Name��LLPRTPatchFeeSave.jsp
	//Function����������֪ͨ��
	//Author��wsz
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
	//ҳ����Ч
	if (tGI == null) {
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
		loggerDebug("LLPRTPatchFeeSave", "ҳ��ʧЧ,�����µ�½");
	}

	String transact = request.getParameter("fmtransact"); //��ȡ����
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
	loggerDebug("LLPRTPatchFeeSave", "tPath=" + tPath);

	//Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š��ͻ���
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("PrtSeq", request
			.getParameter("PrtSeq"));
	mTransferData.setNameAndValue("Path", tPath);

	VData tVData = new VData(); //׼������������ VData
	VData tResult = new VData(); //���ܺ�̨������
	XmlExportNew txmlExport = new XmlExportNew();
	tVData.add(tGI);
	tVData.add(mTransferData);
	String busiName = "LLPRTPatchFeeUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, transact, busiName)) {
		if (tBusinessDelegate.getCErrors() != null
		&& tBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = "�����ύʧ�ܣ�ԭ����:"
			+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "�����ύʧ��";
			FlagStr = "Fail";
		}
	} else {
		Content = " �����ύ�ɹ�! ";
		FlagStr = "Succ";
		tResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) tResult.getObjectByObjectName(
		"XmlExportNew", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
			loggerDebug("LLPRTPatchFeeSave", "û�еõ�Ҫ��ʾ�������ļ�");
		}
	}
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
