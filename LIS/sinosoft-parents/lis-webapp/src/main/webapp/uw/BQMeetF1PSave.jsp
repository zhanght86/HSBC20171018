<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>

<%
			loggerDebug("BQMeetF1PSave",
			"--------------------start------------------");
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";
	String PrtSeq = request.getParameter("PrtSeq");
	String OldPrtSeq = request.getParameter("OldPrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tContNo = request.getParameter("ContNo");
	loggerDebug("BQMeetF1PSave", "��ͬ��===" + tContNo);
	loggerDebug("BQMeetF1PSave", "OldPrtSeq==" + OldPrtSeq);

	String tCode = request.getParameter("NoticeType");
	if (tCode == null) {
		tCode = "24";
	}
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	tLOPRTManagerSchema.setCode(tCode);
	loggerDebug("BQMeetF1PSave", PrtSeq);
	loggerDebug("BQMeetF1PSave", "tCode:" + tCode);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);
	//BQMeetF1PUI tBQMeetF1PUI = new BQMeetF1PUI();
	 String busiName="BQMeetF1PUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	XmlExportNew txmlExport = new XmlExportNew();
	if (!tBusinessDelegate.submitData(tVData, "PRINT3",busiName)) {
		operFlag = false;
		Content = tBusinessDelegate.getCErrors().getFirstError().toString();
	} else {
		mResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			operFlag = false;
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
		}
	}

	if (operFlag == true) {
//		session�е�ֵ���ڹ���������ת
		session.putValue("PrintNo", PrtSeq);
		session.putValue("MissionID", tMissionID);
		session.putValue("SubMissionID", tSubMissionID);
		session.putValue("Code", tCode); //�˱�֪ͨ�����
		session.putValue("ContNo", tContNo);
		session.putValue("PrtNo", tContNo);
		session.putValue("ActivityID", tActivityID);

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

