<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
			loggerDebug("BodyCheckPrintSave",
			"--------------------start------------------");
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";
	String PrtSeq = request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	loggerDebug("BodyCheckPrintSave", PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);

	BodyCheckPrintUI tBodyCheckPrintUI = new BodyCheckPrintUI();
	XmlExportNew txmlExport = new XmlExportNew();
	if (!tBodyCheckPrintUI.submitData(tVData, "PRINT")) {
		operFlag = false;
		Content = tBodyCheckPrintUI.mErrors.getFirstError().toString();
	} else {
		mResult = tBodyCheckPrintUI.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			operFlag = false;
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
		}
	}
	if (operFlag == true) {
//		session�е�ֵ���ڹ���������ת
		session.putValue("PrintNo",PrtSeq );
		session.putValue("MissionID",tMissionID );
		session.putValue("SubMissionID",tSubMissionID );
		session.putValue("Code","03" );	//�˱�֪ͨ�����
		session.putValue("ContNo",tContNo );
		session.putValue("PrtNo",tPrtNo );	
		request
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
	} else {
		FlagStr = "Fail";
%>
<html>
<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<script language="javascript">	
	alert("<%=Content%>");
	top.close();
	
	//window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");	
	
</script>
</html>
<%
}
%>
