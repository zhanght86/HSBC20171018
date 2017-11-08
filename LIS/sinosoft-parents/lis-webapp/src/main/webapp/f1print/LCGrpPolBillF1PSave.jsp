<%%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
	loggerDebug("LCGrpPolBillF1PSave","start");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	//输出参数
	CError cError = new CError();
	//后面要执行的动作：添加，修改，删除
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";
	strOperation = request.getParameter("fmtransact");
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("StartDay", request
			.getParameter("StartDay"));
	tTransferData.setNameAndValue("EndDay", request
			.getParameter("EndDay"));
	tTransferData.setNameAndValue("StartTime", request
			.getParameter("StartTime"));
	tTransferData.setNameAndValue("EndTime", request
			.getParameter("EndTime"));
	tTransferData.setNameAndValue("ManageCom", request
			.getParameter("ManageCom"));
	tTransferData.setNameAndValue("SaleChnl", request
			.getParameter("SaleChnl"));
	tTransferData.setNameAndValue("StartContNo", request
			.getParameter("StartContNo"));
	tTransferData.setNameAndValue("EndContNo", request
			.getParameter("EndContNo"));
	tTransferData.setNameAndValue("ManageGrade", request
			.getParameter("ManageGrade"));			

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.add(tTransferData);
	tVData.add(tG);
	LCGrpPolBillF1PUI tLCGrpPolBillF1PUI = new LCGrpPolBillF1PUI();
	try {
		if (!tLCGrpPolBillF1PUI.submitData(tVData, strOperation)) {
			mErrors.copyAllErrors(tLCGrpPolBillF1PUI.mErrors);
			cError.moduleName = "LCPolBillF1PSave";
			cError.functionName = "submitData";
			cError.errorMessage = "LCGrpPolBillF1PUI发生错误，但是没有提供详细的出错信息";
			mErrors.addOneError(cError);
		}
		mResult = tLCGrpPolBillF1PUI.getResult();
		XmlExport txmlExport = new XmlExport();
		txmlExport = (XmlExport) mResult.getObjectByObjectName(
		"XmlExport", 0);
		if (txmlExport == null) {
			loggerDebug("LCGrpPolBillF1PSave","null");
		}
		ExeSQL tExeSQL = new ExeSQL();
		//获取临时文件名
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath + tG.Operator + "_"
		+ FileQueue.getFileName() + ".vts";
		//获取存放临时文件的路径
		//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
		//String strRealPath = tExeSQL.getOneValue(strSql);
		String strRealPath = application.getRealPath("/").replace('\\',
		'/');
		String strVFPathName = strRealPath + strVFFileName;

		CombineVts tcombineVts = null;
		//合并VTS文件
		String strTemplatePath = application
		.getRealPath("f1print/MStemplate/")
		+ "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),
		strTemplatePath);

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//把dataStream存储到磁盘文件
		loggerDebug("LCGrpPolBillF1PSave","存储文件到" + strVFPathName);
		AccessVtsFile.saveToFile(dataStream, strVFPathName);
		loggerDebug("LCGrpPolBillF1PSave","==> Write VTS file to disk ");

		loggerDebug("LCGrpPolBillF1PSave","===strVFFileName : " + strVFFileName);
		session.putValue("RealPath", strVFPathName);
		// session.putValue("PrintStream", txmlExport.getInputStream());
		loggerDebug("LCGrpPolBillF1PSave","put session value");

		//response.sendRedirect("../uw/GetF1Print.jsp");
		request.getRequestDispatcher("../uw/GetF1PrintJ1.jsp").forward(
		request, response);
		loggerDebug("LCGrpPolBillF1PSave","after calling forward()");

	} catch (Exception ex) {
		Content = strOperation + "失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
%>
<%@page import="java.io.ByteArrayOutputStream"%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">	
	
</script>
</html>
<%
//   }
%>
