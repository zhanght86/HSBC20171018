
<%
	//�ļ����ƣ�LLClaimEndCaseParaPrint.jsp
	//�ļ����ܣ��᰸��֤��ӡ-----���� ����֤���롱�����ⰸ�š��ͻ��š�
	//�����ˣ�yuejw
	//��������:2005-08-10
	//�޸���:niuzj
	//�޸�����:2005-09-17
	//�޸�ԭ��:��ԭ����ӡһ���ĳɴ�ӡ����
	//�޸���:niuzj
	//�޸�����:2005-10-04
	//�޸�ԭ��:�Ѵ�ӡ���������޸�Ϊֻ��ӡһ��(����GETԭ���汾)
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
	if (tGI == null) {
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
		loggerDebug("LLClaimEndCaseParaPrint", "ҳ��ʧЧ,�����µ�½");
	}

	String transact = request.getParameter("fmtransact"); //��ȡ����
	String tPath = application.getRealPath("f1print/MStemplate") + "/"; //ģ������·��
	loggerDebug("LLClaimEndCaseParaPrint", "tPath=" + tPath);
	String tPrtType = request.getParameter("prtType1");
	loggerDebug("LLClaimEndCaseParaPrint", "prtType= " + tPrtType);

	//��֤��ӡ����-----���ڴ�ӡ������֤ʱ���롰��֤���롱
	LLParaPrintSchema tLLParaPrintSchema = new LLParaPrintSchema();
	tLLParaPrintSchema.setPrtCode(request.getParameter("PrtCode"));

	//Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š��ͻ��š�ģ��·��
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("ClmNo", request
			.getParameter("ClmNo")); //�ⰸ��
	mTransferData.setNameAndValue("CustNo", request
			.getParameter("CustomerNo")); //�ͻ���
	mTransferData.setNameAndValue("Path", tPath);
	mTransferData.setNameAndValue("PrtSeq", request
			.getParameter("PrtSeq"));
	mTransferData.setNameAndValue("PrtType", tPrtType);

	VData tVData = new VData(); //׼������������ VData
	VData tResult = new VData(); //���պ�̨������
	XmlExportNew txmlExport = new XmlExportNew();
	tVData.add(tGI);
	tVData.add(mTransferData);
	tVData.add(tLLParaPrintSchema);

	String busiName = "LLParaPrintUI";
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
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
			loggerDebug("LLClaimEndCaseParaPrint", "û�еõ�Ҫ��ʾ�������ļ�");
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