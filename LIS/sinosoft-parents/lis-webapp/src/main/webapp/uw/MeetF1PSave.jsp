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
			loggerDebug("MeetF1PSave",
			"--------------------start------------------");
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";
	String PrtSeq = request.getParameter("PrtSeq");
	String OldPrtSeq = request.getParameter("OldPrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContNo = request.getParameter("ContNo");
	loggerDebug("MeetF1PSave", "合同号===" + tContNo);
	loggerDebug("MeetF1PSave", "OldPrtSeq==" + OldPrtSeq);

	String tCode = request.getParameter("NoticeType");
	if (tCode == null) {
		tCode = "04";
	}
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	tLOPRTManagerSchema.setCode(tCode);
	loggerDebug("MeetF1PSave", PrtSeq);
	loggerDebug("MeetF1PSave", "tCode:" + tCode);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);
	MeetF1PUI tMeetF1PUI = new MeetF1PUI();
	//改造2012-04-18，引入xmlExportNew，不使用写文件的方法。
	if (tMeetF1PUI.submitData(tVData, "PRINT3")) {

		mResult = tMeetF1PUI.getResult();
		XmlExportNew txmlExport = (XmlExportNew) mResult
		.getObjectByObjectName("XmlExportNew", 0);

		//session中的值用于工作流的跳转
		session.putValue("PrintNo", PrtSeq);
		session.putValue("MissionID", tMissionID);
		session.putValue("SubMissionID", tSubMissionID);
		session.putValue("Code", tCode); //核保通知书类别
		session.putValue("ContNo", tContNo);
		session.putValue("PrtNo", tContNo);

		request
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
	} else {
		Content = tMeetF1PUI.mErrors.getFirstError().toString();
		FlagStr = "Fail";
%>
<html>
<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<script language="javascript">
      alert("<%=Content%>");
      top.close();
</script>
</html>
<%
}
%>
