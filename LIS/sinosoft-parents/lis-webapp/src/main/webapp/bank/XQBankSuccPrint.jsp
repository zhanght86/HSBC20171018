<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//�������ƣ�XQBankSuccPrint.jsp
	//�����ܣ�ʵ�ִ�ӡ�Ĺ���
	//������  ��������
	//�������ڣ�2004-4-29
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
	loggerDebug("XQBankSuccPrint", "��ʼִ�д�ӡ����");
	String Content = "����ɹ���";
	CErrors tError = null;
	String FlagStr = "Fail";
	//��ʼ��ȫ�ֱ�������ǰ̨�н�����
	String strStartDate = request.getParameter("StartDate"); //��ʼ����
	String strEndDate = request.getParameter("EndDate"); //��������
	String strAgentState = request.getParameter("AgentState"); //ҵ��Ա��״̬(1Ϊ��ְ����0Ϊ�¶���)
	String strPremType = request.getParameter("PremType"); //�����ڵı�־
	String strFlag = request.getParameter("Flag"); //S or F(SΪ���д��գ�FΪ���д���)
	String strStation = request.getParameter("Station");
	String strGetType = request.getParameter("GetType"); //��ȡ������ͣ�0��ǰ̨��ӡ��1�����ɾ�̬����
	//		��ȡģ��·��
	String templatepath = "";
	LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
	tLDSysVarDB2.setSysVar("XSEXCelTemplate");
	if (!tLDSysVarDB2.getInfo()) {
		loggerDebug("XQBankSuccPrint", "��ȡģ��·��ʧ��");
	}
	templatepath = tLDSysVarDB2.getSysVarValue();
	String strTemplatePath = application.getRealPath(templatepath)
			+ "/";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	XQPremBankSuccUI tXQPremBankSuccUI = new XQPremBankSuccUI();

	VData tVData = new VData();
	VData mResult = new VData();
	try {
		tVData.clear();
		tVData.addElement(strStartDate);
		tVData.addElement(strEndDate);
		tVData.addElement(strAgentState);
		tVData.addElement(strPremType);
		tVData.addElement(strFlag);
		tVData.addElement(strStation);
		tVData.addElement(strTemplatePath);
		tVData.addElement(tG);
		tXQPremBankSuccUI.submitData(tVData, "PRINT");
	} catch (Exception ex) {
		Content = "PRINT" + "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
	if (strGetType.equals("0")) {
		mResult = tXQPremBankSuccUI.getResult();
		XmlExportNew txmlExport = new XmlExportNew();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			loggerDebug("XQBankSuccPrint", "null");
			tError = tXQPremBankSuccUI.mErrors;
			Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
			FlagStr = "Fail";
		} else {
			loggerDebug("XQBankSuccPrint", "put session value");
			request.setAttribute("PrintStream", txmlExport
			.getInputStream());
			request.getRequestDispatcher("../print/ShowPrint.jsp")
			.forward(request, response);
		}
	}
%>
<html>
<script language="javascript">
	alert("<%=Content%>");
	if(<%=strGetType%> == '0')
   {
			top.opener.focus();
			top.close();
   }
</script>
</html>
