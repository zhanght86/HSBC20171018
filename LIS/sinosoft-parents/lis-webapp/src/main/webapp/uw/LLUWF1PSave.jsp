<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	LLOperatorNoticeF1PUI tLLOperatorNoticeF1PUI = new LLOperatorNoticeF1PUI();
	String Content = "";
	VData mResult;
	loggerDebug("LLUWF1PSave", "start");
	boolean operFlag = true;
	String FlagStr = "";

	String PrtSeq = request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tPrtNo = request.getParameter("PrtNo");
	String tContNo = request.getParameter("ContNo");
	String tNoticeType = request.getParameter("NoticeType");
	String tClmNo = request.getParameter("ClmNo");
	String tBatNo = request.getParameter("BatNo");
	loggerDebug("LLUWF1PSave", "tMissionID=" + tMissionID);
	loggerDebug("LLUWF1PSave", "tSubMissionID=" + tSubMissionID);

	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);

	loggerDebug("LLUWF1PSave", PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);

	XmlExportNew txmlExport = new XmlExportNew();
	//  tongmeng 2007-11-12 modify
	//  增加核保通知书的打印
	//  打印内容:理赔核保通知书
	if (tNoticeType.equals("LP90")) {
		if (!tLLOperatorNoticeF1PUI.submitData(tVData, "PRINT")) {
			Content = tLLOperatorNoticeF1PUI.mErrors.getFirstError()
			.toString();
			operFlag = false;
		} else {
			mResult = tLLOperatorNoticeF1PUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		Content = "没有得到要显示的数据文件";
		operFlag = false;
			}
		}
	}
	if (operFlag == true) {
//		session中的值用于工作流的跳转
		session.putValue("PrintNo",PrtSeq );
		session.putValue("MissionID",tMissionID );
		session.putValue("SubMissionID",tSubMissionID );
		session.putValue("Code",tNoticeType );	//核保通知书类别
		session.putValue("PrtNo",tPrtNo );
		session.putValue("ContNo",tContNo );	
		session.putValue("ClmNo",tClmNo );	
		session.putValue("BatNo",tBatNo );	

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
</script>
</html>
<%
}
%>
