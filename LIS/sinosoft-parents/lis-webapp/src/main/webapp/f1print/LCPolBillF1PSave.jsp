<%%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
	loggerDebug("LCPolBillF1PSave","start");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	//�������
	CError cError = new CError();
	//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
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
	LCPolBillF1PUI tLCPolBillF1PUI = new LCPolBillF1PUI();
	try {
		if (!tLCPolBillF1PUI.submitData(tVData, strOperation)) {
			mErrors.copyAllErrors(tLCPolBillF1PUI.mErrors);
			cError.moduleName = "LCPolBillF1PSave";
			cError.functionName = "submitData";
			cError.errorMessage = "LCPolBillF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
			mErrors.addOneError(cError);
		}
		mResult = tLCPolBillF1PUI.getResult();
		XmlExport txmlExport = new XmlExport();
		txmlExport = (XmlExport) mResult.getObjectByObjectName(
		"XmlExport", 0);
		if (txmlExport == null) {
			loggerDebug("LCPolBillF1PSave","null");
		}
		ExeSQL tExeSQL = new ExeSQL();
		//��ȡ��ʱ�ļ���
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath + tG.Operator + "_"
		+ FileQueue.getFileName() + ".vts";
		//��ȡ�����ʱ�ļ���·��
		//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
		//String strRealPath = tExeSQL.getOneValue(strSql);
		String strRealPath = application.getRealPath("/").replace('\\',
		'/');
		String strVFPathName = strRealPath + strVFFileName;

		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application
		.getRealPath("f1print/MStemplate/")
		+ "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),
		strTemplatePath);

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//��dataStream�洢�������ļ�
		loggerDebug("LCPolBillF1PSave","�洢�ļ���" + strVFPathName);
		AccessVtsFile.saveToFile(dataStream, strVFPathName);
		loggerDebug("LCPolBillF1PSave","==> Write VTS file to disk ");

		loggerDebug("LCPolBillF1PSave","===strVFFileName : " + strVFFileName);
		session.putValue("RealPath", strVFPathName);
		// session.putValue("PrintStream", txmlExport.getInputStream());
		loggerDebug("LCPolBillF1PSave","put session value");

		//response.sendRedirect("../uw/GetF1Print.jsp");
		request.getRequestDispatcher("../uw/GetF1PrintJ1.jsp").forward(
		request, response);
		loggerDebug("LCPolBillF1PSave","after calling forward()");

	} catch (Exception ex) {
		Content = strOperation + "ʧ�ܣ�ԭ����:" + ex.toString();
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
