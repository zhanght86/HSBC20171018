<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
	loggerDebug("NBCF2PSave", "start");
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";

	String tPrtSeq = request.getParameter("PrtSeq");
	String tNoticeType = request.getParameter("noticetype");
	loggerDebug("NBCF2PSave", "noticetype:" + tNoticeType);
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(tPrtSeq);

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();

	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);

	MSNBCF1PUI tNBCF1PUI = new MSNBCF1PUI();
	XmlExportNew txmlExport = new XmlExportNew();
	if (tNoticeType.equals("08")) {//�����罻�˷�֪ͨ��
		if (!tNBCF1PUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tNBCF1PUI.mErrors.getFirstError().toString();
		} else {
			mResult = tNBCF1PUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}
	if (tNoticeType.equals("78")) {//�ŵ��罻�˷�֪ͨ��
		GrpRFPaybackNoticeUI tGrpRFPaybackNoticeUI = new GrpRFPaybackNoticeUI();

		if (!tGrpRFPaybackNoticeUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tGrpRFPaybackNoticeUI.mErrors.getFirstError()
			.toString();
		} else {
			mResult = tGrpRFPaybackNoticeUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
			operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (operFlag == true) {
//		session�е�ֵ���ڹ���������ת
		session.putValue("PrintNo",tPrtSeq );
		session.putValue("Code",tNoticeType);

		request
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
	} else {
		FlagStr = "Fail";
%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<script language="javascript">	
	alert("<%=Content%>");
	window.close();	
</script>
</html>
<%
}
%>