
<%
	//��������:�������ⱨ���ӡ
	//������:�������ⱨ���ӡ
	//�������ڣ�2009-05-25
	//������  ��mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
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

	//��������CODE=claim3,claim12,claim13,claim14
	String Code = request.getParameter("Code");
	loggerDebug("ClaimReportF1Print","�������ʹ���:" + Code);
	//ʱ��
	String mDay[] = new String[2];
	mDay[0] = request.getParameter("StartDate");
	mDay[1] = request.getParameter("EndDate");
	//����
	String MngCom = request.getParameter("MngCom");
	
	//�˻���,ͳ������
	String StatType = request.getParameter("StatType");
	String StatTypeName = request.getParameter("StatTypeName");
	String DefaultOperator = request.getParameter("defaultOperator");
	String Risk = request.getParameter("Risk");
	
	//�����׶Σ�ͳ��ʱЧ����
	String CaseSection = request.getParameter("CaseSection");
	loggerDebug("ClaimReportF1Print","�����׶�"+CaseSection);
	String CommonDate = request.getParameter("CommonDate");
	loggerDebug("ClaimReportF1Print","ͳ��ʱЧ����"+CommonDate);
	
	VData tVData = new VData();
	VData mResult = new VData();
	tVData.addElement(Code);
	tVData.addElement(mDay);
	tVData.addElement(MngCom);	
	
	tVData.addElement(StatType);
	tVData.addElement(StatTypeName);
	tVData.addElement(DefaultOperator);
	tVData.addElement(Risk);
	
	tVData.addElement(CaseSection);
	tVData.addElement(CommonDate);
	tVData.addElement(tG);
	

	CError cError = new CError();
	CErrors mErrors = new CErrors();
	XmlExport txmlExport = new XmlExport();

	ClaimReportUI tClaimReportUI = new ClaimReportUI();
	if (!tClaimReportUI.submitData(tVData, "PRINTPAY")) {
		int n = tClaimReportUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			Content = Content + "ԭ����: " + tClaimReportUI.mErrors.getError(i).errorMessage;
			FlagStr = "FAIL";
		}
	} else {
		mResult = tClaimReportUI.getResult();
		txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
			loggerDebug("ClaimReportF1Print","û�еõ�Ҫ��ʾ�������ļ�");
		}
	}
	if (FlagStr.equals("Succ")) {
		ExeSQL tExeSQL = new ExeSQL();
		//��ȡ��ʱ�ļ���
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName() + ".vts";
		loggerDebug("ClaimReportF1Print","strVFFileName=" + strVFFileName);

		//��ȡ�����ʱ�ļ���·��
		String strRealPath = application.getRealPath("/").replace('\\', '/');
		loggerDebug("ClaimReportF1Print","strRealPath=" + strRealPath);

		String strVFPathName = strRealPath + strVFFileName;

		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(), strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//��dataStream�洢�������ļ�
		loggerDebug("ClaimReportF1Print","�洢�ļ���" + strVFPathName);
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
