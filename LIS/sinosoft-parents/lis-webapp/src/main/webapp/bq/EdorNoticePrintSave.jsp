<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//�������ƣ�EdorNoticePrintSave.jsp
	//�����ܣ���ȫ֪ͨ�����ߴ�ӡ����̨
	//�������ڣ�2005-08-02 16:20:22
	//������  ��liurx
	//���¼�¼��  ������    ��������      ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	loggerDebug("EdorNoticePrintSave", "��ʼִ�д�ӡ����");
	String Content = "";
	CErrors tError = null;
	String FlagStr = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	VData tVData = new VData();
	String PrtSeq = request.getParameter("PrtSeq");
	//���֪ͨ������ liuzhao 2007-08-21
	String NoticeType = request.getParameter("NoticeType");

	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	TransferData tTransferData = new TransferData();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	//���֪ͨ������ liuzhao 2007-08-21
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
			Content = "����ʧ�ܣ�ԭ����:"
			+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = "����ʧ��";
			FlagStr = "Fail";
		}
	} else {
		mResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			FlagStr = "Fail";
			tError = tBusinessDelegate.getCErrors();
			Content = "��ӡʧ��,ԭ����XMLExportNew����Ϊ��"
			+ tError.getFirstError();
		} else {
//			session�е�ֵ���ڹ���������ת
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