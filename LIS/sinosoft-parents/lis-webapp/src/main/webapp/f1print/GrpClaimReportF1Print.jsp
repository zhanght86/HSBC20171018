
<%
	//程序名称:团险理赔报表打印
	//程序功能:团险理赔报表打印
	//创建日期：2009-06-01
	//创建人  ：mw
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String FlagStr = "FAIL";
	String Content = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//报表种类CODE=claim3,claim12,claim13,claim14
	String Code = request.getParameter("Code");
	loggerDebug("GrpClaimReportF1Print","报表类型代码:" + Code);
	//时间
	String mDay[] = new String[2];
	mDay[0] = request.getParameter("StartDate");
	mDay[1] = request.getParameter("EndDate");
	//机构
	String MngCom = request.getParameter("MngCom");
	//退回率,统计类型
	String StatType = request.getParameter("StatType");
	String StatTypeName = request.getParameter("StatTypeName");
	String CaseType = request.getParameter("CaseType");
	String CaseResult = request.getParameter("CaseResult");
	VData tVData = new VData();
	VData mResult = new VData();
	tVData.addElement(Code);
	tVData.addElement(mDay);
	tVData.addElement(MngCom);	
	tVData.addElement(StatType);
	tVData.addElement(StatTypeName);
	tVData.addElement(CaseType);
	tVData.addElement(CaseResult);
	tVData.addElement(tG);

	CError cError = new CError();
	CErrors mErrors = new CErrors();
	XmlExport txmlExport = new XmlExport();

	ClaimReportUI tClaimReportUI = new ClaimReportUI();
	if (!tClaimReportUI.submitData(tVData, "PRINTPAY")) {
		int n = tClaimReportUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			Content = Content + "原因是: " + tClaimReportUI.mErrors.getError(i).errorMessage;
			FlagStr = "FAIL";
		}
	} else {
		mResult = tClaimReportUI.getResult();
		txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "没有得到要显示的数据文件";
			loggerDebug("GrpClaimReportF1Print","没有得到要显示的数据文件");
		}
	}
	if (FlagStr.equals("Succ")) {
		ExeSQL tExeSQL = new ExeSQL();
		//获取临时文件名
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName() + ".vts";
		loggerDebug("GrpClaimReportF1Print","strVFFileName=" + strVFFileName);

		//获取存放临时文件的路径
		String strRealPath = application.getRealPath("/").replace('\\', '/');
		loggerDebug("GrpClaimReportF1Print","strRealPath=" + strRealPath);

		String strVFPathName = strRealPath + strVFFileName;

		CombineVts tcombineVts = null;
		//合并VTS文件
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(), strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//把dataStream存储到磁盘文件
		loggerDebug("GrpClaimReportF1Print","存储文件到" + strVFPathName);
		AccessVtsFile.saveToFile(dataStream, strVFPathName);
		session.putValue("RealPath", strVFPathName);

		request.getRequestDispatcher("GetF1PrintJ1.jsp").forward(request, response);
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
