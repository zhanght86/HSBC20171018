
<%
	//�������ƣ�LLClaimBackBillSave.jsp
	//�����ܣ�����������˰����嵥
	//�������ڣ�2009-04-15
	//������  ��mw
	//���¼�¼��
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>

<%
	//׼��ͨ�ò���
	CError cError = new CError();
	String FlagStr = "FAIL";
	String Content = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if (tG == null) {
		loggerDebug("LLClaimBackBillSave","δ��ȡ����¼��Ϣ!");
		return;
	}

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("MngCom", request.getParameter("MngCom")); //ͳ�ƻ���
	tTransferData.setNameAndValue("StartDate", request.getParameter("StartDate")); //��ʼ����
	tTransferData.setNameAndValue("EndDate", request.getParameter("EndDate")); //��������

	// ׼���������� VData
	VData tVData = new VData();
	VData tResult = new VData();
	XmlExport txmlExport = new XmlExport();

	tVData.add(tG);
	tVData.add(tTransferData);
	LLClaimBillUI tLLClaimBillUI = new LLClaimBillUI();
	if (tLLClaimBillUI.submitData(tVData, "BackBill") == false) {
		int n = tLLClaimBillUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			Content = Content + "ԭ����: " + tLLClaimBillUI.mErrors.getError(i).errorMessage;
			FlagStr = "FAIL";
		}
	} else {
		tResult = tLLClaimBillUI.getResult();
		txmlExport = (XmlExport) tResult.getObjectByObjectName("XmlExport", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
			loggerDebug("LLClaimBackBillSave","û�еõ�Ҫ��ʾ�������ļ�");
		}
	}

	if (FlagStr.equals("Succ")) {
		ExeSQL tExeSQL = new ExeSQL();
		//��ȡ��ʱ�ļ���
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName() + ".vts";
		loggerDebug("LLClaimBackBillSave","strVFFileName=" + strVFFileName);

		//��ȡ�����ʱ�ļ���·��
		String strRealPath = application.getRealPath("/").replace('\\', '/');
		loggerDebug("LLClaimBackBillSave","strRealPath=" + strRealPath);

		String strVFPathName = strRealPath + strVFFileName;

		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(), strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//��dataStream�洢�������ļ�
		loggerDebug("LLClaimBackBillSave","�洢�ļ���" + strVFPathName);
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
