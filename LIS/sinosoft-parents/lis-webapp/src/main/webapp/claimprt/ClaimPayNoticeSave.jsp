
<%
	//**************************************************************************************************
	//Name��ClaimRgtConclusionSave.jsp
	//Function���������۴�ӡ����������֪ͨ��
	//Author��mw
	//Date:   2009-04-22
	//**************************************************************************************************
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
		loggerDebug("ClaimPayNoticeSave","δ��ȡ����¼��Ϣ!");
		return;
	}

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("RgtNo", request.getParameter("RgtNo")); //������ 
	tTransferData.setNameAndValue("CustomerNo", request.getParameter("InsuredNo")); //������

	// ׼���������� VData
	VData tVData = new VData();
	VData tResult = new VData();
	XmlExport txmlExport = new XmlExport();

	tVData.add(tG);
	tVData.add(tTransferData);
	LLGrpClaimPrtUI tLLGrpClaimPrtUI = new LLGrpClaimPrtUI();
	if (tLLGrpClaimPrtUI.submitData(tVData, "PayNotice") == false) {
		int n = tLLGrpClaimPrtUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			Content = Content + "ԭ����: " + tLLGrpClaimPrtUI.mErrors.getError(i).errorMessage;
			FlagStr = "FAIL";
		}
	} else {
		tResult = tLLGrpClaimPrtUI.getResult();
		txmlExport = (XmlExport) tResult.getObjectByObjectName("XmlExport", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "û�еõ�Ҫ��ʾ�������ļ�";
			loggerDebug("ClaimPayNoticeSave","û�еõ�Ҫ��ʾ�������ļ�");
		}
	}

	if (FlagStr.equals("Succ")) {
		ExeSQL tExeSQL = new ExeSQL();
		//��ȡ��ʱ�ļ���
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName() + ".vts";
		loggerDebug("ClaimPayNoticeSave","strVFFileName=" + strVFFileName);

		//��ȡ�����ʱ�ļ���·��
		String strRealPath = application.getRealPath("/").replace('\\', '/');
		loggerDebug("ClaimPayNoticeSave","strRealPath=" + strRealPath);

		String strVFPathName = strRealPath + strVFFileName;

		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(), strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//��dataStream�洢�������ļ�
		loggerDebug("ClaimPayNoticeSave","�洢�ļ���" + strVFPathName);
		AccessVtsFile.saveToFile(dataStream, strVFPathName);
		session.putValue("RealPath", strVFPathName);

		request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request, response);
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
