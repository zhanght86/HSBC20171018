
<%
	//�������ƣ�CallbackReceiptReportSave.jsp
	//�����ܣ� δ���ջ�ִ��ѯ��ӡ
	//�������ڣ�2010-05-11
	//������  ��hanbin
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
	String Content = "";
	CErrors tError = null;
	String FlagStr = "Fail";
	try {

		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		String tManageCom = request.getParameter("ManageCom");
		String tAgentCode = request.getParameter("AgentCode");
		String tAgentCom = request.getParameter("AgentCom");
		String tSaleChnl = request.getParameter("SaleChnl");

		String tSignDateStart = request.getParameter("SignDateStart");
		loggerDebug("CallbackReceiptReportSave","SignDateStart��" + tSignDateStart);
		String tSignDateEnd = request.getParameter("SignDateEnd");
		loggerDebug("CallbackReceiptReportSave","SignDateEnd��" + tSignDateEnd);
		String tCleanPolFlag = request.getParameter("CleanPolFlag");
		String tStatType = request.getParameter("StatType");
		
		String tComGrade = request.getParameter("ComGrade");
		

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", tManageCom);
		tTransferData.setNameAndValue("AgentCode", tAgentCode);
		tTransferData.setNameAndValue("AgentCom", tAgentCom);
		tTransferData.setNameAndValue("SaleChnl", tSaleChnl);
		tTransferData.setNameAndValue("SignDateStart", tSignDateStart);
		tTransferData.setNameAndValue("SignDateEnd", tSignDateEnd);
		tTransferData.setNameAndValue("CleanPolFlag", tCleanPolFlag);
		tTransferData.setNameAndValue("StatType", tStatType);
		tTransferData.setNameAndValue("ComGrade", tComGrade);
		
		VData tVData = new VData();
		VData mResult = new VData();

		tVData.add(tTransferData);
		tVData.addElement(tG);

		CError cError = new CError();
		CErrors mErrors = new CErrors();
		XmlExport txmlExport = new XmlExport();

		CallbackReceiptReportBL tCallbackReceiptReportBL = new CallbackReceiptReportBL();
		if (!tCallbackReceiptReportBL.submitData(tVData, "PRINT")) {
			FlagStr = "Fail";
			Content = " ����ʧ�ܣ�ԭ����:"
					+ tCallbackReceiptReportBL.mErrors.getFirstError();
		} else {
			mResult = tCallbackReceiptReportBL.getResult();
			txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
			if (txmlExport == null) {
				loggerDebug("CallbackReceiptReportSave","null");
				Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��"
						+ tCallbackReceiptReportBL.mErrors;
				FlagStr = "Fail";
			} else {
				session.putValue("PrintStream", txmlExport.getInputStream());
				loggerDebug("CallbackReceiptReportSave","put session value");
				response.sendRedirect("../f1print/GetF1Print.jsp");
			}
		}
	} catch (Exception ex) {
		Content = "PRINT" + "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
%>
<html>
<%
	if ("Fail".equals(FlagStr)) {
%>
<script language="javascript">
			alert("<%=Content%>");    
			window.close();
		   </script>
<%
	}
%>
</html>
