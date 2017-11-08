<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>
<%
	loggerDebug("UWF1PSave", "start");
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";

	String PrtSeq = request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tPrtNo = request.getParameter("PrtNo");
	String tContNo = request.getParameter("ContNo");
	String tNoticeType = request.getParameter("NoticeType");
	loggerDebug("UWF1PSave", "tMissionID=" + tMissionID);
	loggerDebug("UWF1PSave", "tSubMissionID=" + tSubMissionID);

	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);

	//��LCContSchema ����tVData
	LCContSchema tLCContSchema = new LCContSchema();
	tLCContSchema.setContNo(tContNo);
	tLCContSchema.setPrtNo(tPrtNo);

	loggerDebug("UWF1PSave", PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);
	tVData.addElement(tLCContSchema);

	XmlExportNew txmlExport = new XmlExportNew();

	if (tNoticeType.equals("81") || tNoticeType.equals("84")
			|| tNoticeType.equals("86")) {
		UWPartCancelPrintUI tUWPartCancelPrintUI = new UWPartCancelPrintUI();
		if (!tUWPartCancelPrintUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tUWPartCancelPrintUI.mErrors.getFirstError()
			.toString();
		} else {
			mResult = tUWPartCancelPrintUI.getResult();
			loggerDebug("UWF1PSave", "mResult=" + mResult);
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);

			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}
	//tongmeng 2007-11-12 modify
	//���Ӻ˱�֪ͨ��Ĵ�ӡ
	//��ӡ����:1 �˱�֪ͨ��(��ӡ��)��ӡ 2 �˱�֪ͨ��(�Ǵ�ӡ��)��ӡ 3 ҵ��Ա֪ͨ��
	if (tNoticeType.equals("TB89") || tNoticeType.equals("14")
			|| tNoticeType.equals("TB90")) {
		OperatorNoticeF1PUI tOperatorNoticeF1PUI = new OperatorNoticeF1PUI();
		if (!tOperatorNoticeF1PUI.submitData(tVData, "PRINT")) {
			Content = tOperatorNoticeF1PUI.mErrors.getFirstError()
			.toString();
		}
		mResult = tOperatorNoticeF1PUI.getResult();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			operFlag = false;
			Content = "û�еõ�Ҫ��ʾ�������ļ�";

		}
	}
	//tongmeng 2007-11-28 add
	//���Ӿܱ�֪ͨ���ӡ
	if (tNoticeType.equals("00")) {
		RANF1PUI_MS tRANF1PUI = new RANF1PUI_MS();
		if (!tRANF1PUI.submitData(tVData, "CONFIRM")) {
			operFlag = false;
			Content = tRANF1PUI.mErrors.getFirstError().toString();
		} else {
			mResult = tRANF1PUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}
	//����֪ͨ��
	if (tNoticeType.equals("06")) {
		DANF1PUI_MS tDANF1PUI = new DANF1PUI_MS();
		if (!tDANF1PUI.submitData(tVData, "CONFIRM")) {
			operFlag = false;
			Content = tDANF1PUI.mErrors.getFirstError().toString();
		} else {
			mResult = tDANF1PUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}
	//����֪ͨ��
	if (tNoticeType.equals("09")) {
		AppCancelF1PUI_MS tAppCancelF1PUI = new AppCancelF1PUI_MS();
		if (!tAppCancelF1PUI.submitData(tVData, "CONFIRM")) {
			operFlag = false;
			Content = tAppCancelF1PUI.mErrors.getFirstError()
			.toString();
		} else {
			mResult = tAppCancelF1PUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}
	//���ڽ���֪ͨ��
	if (tNoticeType.equals("07")) {
		FirstPayF1PUI_MS tFirstPayF1PUI = new FirstPayF1PUI_MS();
		if (!tFirstPayF1PUI.submitData(tVData, "CONFIRM")) {
			operFlag = false;
			Content = tFirstPayF1PUI.mErrors.getFirstError().toString();
		} else {
			mResult = tFirstPayF1PUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	//��ӡת�˲��ɹ�֪ͨ�� hanbin 2010-05-07
	if (tNoticeType.equals("21")) {
		BankF1PUI tBankF1PUI = new BankF1PUI();
		if (!tBankF1PUI.submitData(tVData, "CONFIRM")) {
			operFlag = false;
			Content = tBankF1PUI.mErrors.getFirstError().toString();
		} else {
			mResult = tBankF1PUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}
	if (tNoticeType.equals("83")) {
		AddPremPrintUI tAddPremPrintUI = new AddPremPrintUI();
		if (!tAddPremPrintUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tAddPremPrintUI.mErrors.getFirstError()
			.toString();
		} else {
			mResult = tAddPremPrintUI.getResult();

			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);

			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (tNoticeType.equals("82")) {
		SpecPrintUI tSpecPrintUI = new SpecPrintUI();
		if (!tSpecPrintUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tSpecPrintUI.mErrors.getFirstError().toString();
		} else {
			mResult = tSpecPrintUI.getResult();

			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);

			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (tNoticeType.equals("85")) {
		QuotaPrintUI tQuotaPrintUI = new QuotaPrintUI();
		if (!tQuotaPrintUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tQuotaPrintUI.mErrors.getFirstError().toString();
		} else {
			mResult = tQuotaPrintUI.getResult();

			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);

			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (tNoticeType.equals("87")) {
		AddRefundmentUI tAddRefundmentUI = new AddRefundmentUI();
		if (!tAddRefundmentUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tAddRefundmentUI.mErrors.getFirstError()
			.toString();
		} else {
			mResult = tAddRefundmentUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (tNoticeType.equals("88")) {
		FBCGUI tFBCGUI = new FBCGUI();
		if (!tFBCGUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tFBCGUI.mErrors.getFirstError().toString();
		} else {
			mResult = tFBCGUI.getResult();

			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);

			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (operFlag == true) {
//		session�е�ֵ���ڹ���������ת
		session.putValue("PrintNo", PrtSeq);
		session.putValue("MissionID", tMissionID);
		session.putValue("SubMissionID", tSubMissionID);
		session.putValue("Code", tNoticeType); //�˱�֪ͨ�����
		session.putValue("PrtNo", tPrtNo);
		session.putValue("ContNo", tContNo);

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
</script>
</html>
<%
}
%>
