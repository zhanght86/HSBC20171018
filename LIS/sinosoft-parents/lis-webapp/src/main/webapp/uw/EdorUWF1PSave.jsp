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
	loggerDebug("EdorUWF1PSave", "start");
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";

	String PrtSeq = request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tPrtNo = request.getParameter("PrtNo");
	String tContNo = request.getParameter("ContNo");
	String tActivityID = request.getParameter("ActivityID");
	String tNoticeType = request.getParameter("NoticeType");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	loggerDebug("EdorUWF1PSave", "tMissionID=" + tMissionID);
	loggerDebug("EdorUWF1PSave", "tSubMissionID=" + tSubMissionID);

	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);

	loggerDebug("EdorUWF1PSave", PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);

	XmlExportNew txmlExport = new XmlExportNew();
	//  tongmeng 2007-11-12 modify
	//  增加核保通知书的打印
	//  打印内容:1 核保通知书(打印类)打印 2 核保通知书(非打印类)打印 3 业务员通知书
	if (tNoticeType.equals("25") || tNoticeType.equals("27")
			|| tNoticeType.equals("BQ84") || tNoticeType.equals("14")
			|| tNoticeType.equals("TB90")) {
		//BQOperatorNoticeF1PUI tBQOperatorNoticeF1PUI = new BQOperatorNoticeF1PUI();
		String busiName="BQOperatorNoticeF1PUI";
  			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (!tBusinessDelegate.submitData(tVData, "PRINT",busiName)) {
			Content = tBusinessDelegate.getCErrors().getFirstError()
			.toString();
		}
		mResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			Content = "没有得到要显示的数据文件";
			operFlag = false;
		}
	} else {
		//EdorUWNoticeUI tEdorUWNoticeUI = new EdorUWNoticeUI();
		String busiName="EdorUWNoticeUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, "PRINT",busiName)) {
			operFlag = false;
			Content = tBusinessDelegate.getCErrors().getFirstError()
			.toString();

		} else {
			mResult = tBusinessDelegate.getResult();

			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);

			if (txmlExport == null) {
		operFlag = false;
		Content = "没有得到要显示的数据文件";
			}
		}
	}

	if (operFlag == true) {
//		session中的值用于工作流的跳转
		session.putValue("PrintNo",PrtSeq );
		session.putValue("MissionID",tMissionID );
		session.putValue("SubMissionID",tSubMissionID );
		session.putValue("ActivityID",tActivityID );
		session.putValue("Code",tNoticeType );	//核保通知书类别
		session.putValue("PrtNo",tPrtNo );
		session.putValue("ContNo",tContNo );	
		session.putValue("PrintStream", txmlExport.getInputStream());
		session.putValue("EdorNo",tEdorNo );	
		session.putValue("EdorType",tEdorType );	

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
