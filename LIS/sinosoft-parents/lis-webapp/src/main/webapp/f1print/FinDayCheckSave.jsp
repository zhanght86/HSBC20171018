<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//�������ƣ������ִ�ӡ����Ա�ս�
	//�����ܣ�
	//�������ڣ�2002-12-12
	//������  ��lh
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	loggerDebug("FinDayCheckSave", "start");
	String mDay[] = new String[2];
	GlobalInput tG1 = new GlobalInput();
	tG1 = (GlobalInput) session.getValue("GI");
	//�������
	CError cError = new CError();
	//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = ""; //�����ж����շѻ��Ǹ���
	String strOpt = ""; //LYS ������¼�����ջ���Ԥ��
	strOperation = request.getParameter("fmtransact");
	strOpt = request.getParameter("Opt");
	loggerDebug("FinDayCheckSave", "�շ�������" + strOpt);
	mDay[0] = request.getParameter("StartDay");
	mDay[1] = request.getParameter("EndDay");
	String managecom = request.getParameter("ManageCom");

	GlobalInput tG = new GlobalInput();
	tG.setSchema(tG1);
	if (managecom != null && !"".equals(managecom))
		tG.ManageCom = managecom;

	loggerDebug("FinDayCheckSave", "start" + mDay[0]);
	loggerDebug("FinDayCheckSave", "end" + mDay[1]);
	loggerDebug("FinDayCheckSave", tG.Operator);
	loggerDebug("FinDayCheckSave", tG.ManageCom);
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(mDay);
	tVData.addElement(strOpt); //�����շѵ����� ����ORԤ��
	tVData.addElement(tG);
	XmlExportNew txmlExport = new XmlExportNew();
	//20110512 modified by Cuizhe ���뱨���Ҳ���BusinessDelegate�࣬�Ҳ���tOperator������tBusinessDelegateû��mErrors���ԣ���ΪgetCErrors  
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();

	if (!tBusinessDelegate.submitData(tVData, strOperation,
			"FinDayCheckUI")) {
		mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
		cError.moduleName = "FinChargeDayRiskF1PSave.jsp";
		cError.functionName = "submitData";
		cError.errorMessage = "FinDayCheckUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
		mErrors.addOneError(cError);
	}

	//FinDayCheckUI tFinDayCheckUI = new FinDayCheckUI();
	//if(!tFinDayCheckUI.submitData(tVData,strOperation))
	//{
	//  mErrors.copyAllErrors(tFinDayCheckUI.mErrors);
	//  cError.moduleName = "FinDayCheckSave";
	//  cError.functionName = "submitData";
	//  cError.errorMessage = "FinDayCheckUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
	//  mErrors.addOneError(cError);
	//}
	mResult = tBusinessDelegate.getResult();
	//XmlExport txmlExport = new XmlExport();
	txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
	if (txmlExport == null) {
		loggerDebug("FinDayCheckSave", "null");
		FlagStr = "Fail";
		Content = "û�л����Ӧ������׼����";
	}
	if (!"Fail".equals(FlagStr)) {
		//session.putValue("PrintStream", txmlExport.getInputStream());
		//loggerDebug("FinDayCheckSave","put session value");
		//response.sendRedirect("GetF1Print.jsp");
		request
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
		//session.putValue("PrintStream", txmlExport.getInputStream());
		loggerDebug("FinDayCheckSave", "put session value");

	} else {
%>
<html>
<script language="javascript">
    			top.opener.afterSubmit('<%=FlagStr%>', '<%=Content%>');
    		  //  window.open("../f1print/GetF1Print.jsp");
    	</script>
</html>
<%
}
%>
