<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
			loggerDebug("BqPENoticePrintSave",
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
	String tActivityID = request.getParameter("ActivityID");
	String ErrorMessage = "";
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("MissionID", tMissionID);
	tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	loggerDebug("BqPENoticePrintSave", PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tTransferData);
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);

	//BqPENoticePrintUI tBqPENoticePrintUI = new BqPENoticePrintUI();
	 String busiName="BqPENoticePrintUI";
	 BusinessDelegate  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	XmlExportNew txmlExport = new XmlExportNew();
	if (!tBusinessDelegate.submitData(tVData, "PRINT",busiName)) {
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+operFlag);
		operFlag = false;
		Content = tBusinessDelegate.getCErrors().getFirstError().toString();
	} else {
		mResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			operFlag = false;
			Content = "没有得到要显示的数据文件";
		}
	}
	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+operFlag);
	if (operFlag == true) {
//		session中的值用于工作流的跳转
		session.putValue("PrintNo",PrtSeq );
		session.putValue("MissionID",tMissionID );
		session.putValue("SubMissionID",tSubMissionID );
		session.putValue("ActivityID",tActivityID);
		session.putValue("Code","23" );	//核保通知书类别
		session.putValue("ContNo",tContNo );
		session.putValue("PrtNo",tPrtNo );
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
