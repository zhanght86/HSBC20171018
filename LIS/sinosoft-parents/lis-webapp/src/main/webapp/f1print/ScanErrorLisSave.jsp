<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�ScanErrorLisSave.jsp
//�����ܣ��ۺϴ�ӡ���б������嵥--ɨ������ͳ�Ʊ���
//�������ڣ�2010-06-02
//������  ��hanabin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
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

		String strMngCom    = request.getParameter("ManageCom");
		String strScanType    = request.getParameter("ScanType");
		String strStartDate = request.getParameter("StartDate");
		String strEndDate = request.getParameter("EndDate");
		String strComGrade = request.getParameter("ComGrade");


		TransferData tTransferData = new TransferData();
	    tTransferData.setNameAndValue("ManageCom",strMngCom);
	    tTransferData.setNameAndValue("ScanType",strScanType);
	    tTransferData.setNameAndValue("StartDate",strStartDate);
	    tTransferData.setNameAndValue("EndDate",strEndDate);
		VData tVData = new VData();
		VData mResult = new VData();

		tVData.add(tTransferData);
		tVData.addElement(tG);

		CError cError = new CError();
		CErrors mErrors = new CErrors();
		XmlExport txmlExport = new XmlExport();

		ScanErrorLisBL tScanErrorLisBL = new ScanErrorLisBL();
		if (!tScanErrorLisBL.submitData(tVData, "PRINT")) {
			FlagStr = "Fail";
			Content = " ����ʧ�ܣ�ԭ����:"
					+ tScanErrorLisBL.mErrors.getFirstError();
		} else {
			mResult = tScanErrorLisBL.getResult();
			txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
			if (txmlExport == null) {
				System.out.println("null");
				Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��"
						+ tScanErrorLisBL.mErrors;
				FlagStr = "Fail";
			} else {
				session.putValue("PrintStream", txmlExport.getInputStream());
				System.out.println("put session value");
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
%>