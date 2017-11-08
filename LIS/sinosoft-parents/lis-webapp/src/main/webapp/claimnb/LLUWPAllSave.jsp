<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimnb.*"%>
<%
	loggerDebug("LLUWPAllSave", "start");
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";

	String PrtSeq = request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	loggerDebug("LLUWPAllSave", "tMissionID=" + tMissionID);
	String tSubMissionID = request.getParameter("SubMissionID");
	loggerDebug("LLUWPAllSave", "tSubMissionID=" + tSubMissionID);
	String tPrtNo = request.getParameter("PrtNo");
	String tContNo = request.getParameter("ContNo");
	String tNoticeType = request.getParameter("NoticeType");

	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLOPRTManagerSchema);
	tVData.addElement(tG);
	XmlExportNew txmlExport = new XmlExportNew();

	if (tNoticeType.equals("LP00") || tNoticeType.equals("LP06")
			|| tNoticeType.equals("LP84") || tNoticeType.equals("LP86")||tNoticeType.equals("LP81")) {
		LLUWPartCancelPrintUI tUWPartCancelPrintUI = new LLUWPartCancelPrintUI();
		if (!tUWPartCancelPrintUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tUWPartCancelPrintUI.mErrors.getFirstError()
			.toString();
		} else {
			mResult = tUWPartCancelPrintUI.getResult();
			loggerDebug("LLUWPAllSave", "mResult=" + mResult);
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (tNoticeType.equals("LP83")) {
		LLUWPAddFeeUI tLLUWPAddFeeUI = new LLUWPAddFeeUI();
		if (!tLLUWPAddFeeUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tLLUWPAddFeeUI.mErrors.getFirstError().toString();
		} else {
			mResult = tLLUWPAddFeeUI.getResult();

			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);

			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (tNoticeType.equals("LP82")) {
		LLUWPSpecUI tLLUWPSpecUI = new LLUWPSpecUI();
		if (!tLLUWPSpecUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tLLUWPSpecUI.mErrors.getFirstError().toString();
		} else {
			mResult = tLLUWPSpecUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	if (tNoticeType.equals("LP87")) {
		LLUWPAddRefundmentUI tLLUWPAddRefundmentUI = new LLUWPAddRefundmentUI();
		if (!tLLUWPAddRefundmentUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tLLUWPAddRefundmentUI.mErrors.getFirstError()
			.toString();
		} else {
			mResult = tLLUWPAddRefundmentUI.getResult();
			txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
			if (txmlExport == null) {
		operFlag = false;
		Content = "û�еõ�Ҫ��ʾ�������ļ�";
			}
		}
	}

	/*===================================================================
	 * �޸�ԭ����ӡ����Զ�������֪ͨ���ӡ
	 * �޸��ˣ�  �����
	 * �޸�ʱ�䣺2005/12/12 14:15
	 ===================================================================
	 */
	if (tNoticeType.equals("LP88")) {
		LLUWPBXBUI tLLUWPBXBUI = new LLUWPBXBUI();
		if (!tLLUWPBXBUI.submitData(tVData, "PRINT")) {
			operFlag = false;
			Content = tLLUWPBXBUI.mErrors.getFirstError().toString();
		} else {
			mResult = tLLUWPBXBUI.getResult();
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
		session.putValue("PrintNo",PrtSeq );
		session.putValue("MissionID",tMissionID );
		session.putValue("SubMissionID",tSubMissionID );
		session.putValue("Code",tNoticeType );	//�˱�֪ͨ�����
		session.putValue("PrtNo",tPrtNo );
		session.putValue("ContNo",tContNo );	

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
	top.opener.focus();
</script>
</html>
<%
}
%>
