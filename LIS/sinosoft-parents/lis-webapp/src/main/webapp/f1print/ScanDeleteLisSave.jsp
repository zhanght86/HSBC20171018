<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�ScanDeleteLisSave.jsp
//�����ܣ��ۺϴ�ӡ���б������嵥--��֤ɾ���嵥
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
		String strPrtNo    = request.getParameter("PrtNo");
		String strStartDate = request.getParameter("StartDate");
		String strEndDate = request.getParameter("EndDate");


		TransferData tTransferData = new TransferData();
	    tTransferData.setNameAndValue("ManageCom",strMngCom);
	    tTransferData.setNameAndValue("PrtNo",strPrtNo);
	    tTransferData.setNameAndValue("StartDate",strStartDate);
	    tTransferData.setNameAndValue("EndDate",strEndDate);
		VData tVData = new VData();
		VData mResult = new VData();

		tVData.add(tTransferData);
		tVData.addElement(tG);

		CError cError = new CError();
		CErrors mErrors = new CErrors();
		XmlExport txmlExport = new XmlExport();

		ScanDeleteLisBL tScanDeleteLisBL = new ScanDeleteLisBL();
		if (!tScanDeleteLisBL.submitData(tVData, "PRINT")) {
			FlagStr = "Fail";
			Content = " ����ʧ�ܣ�ԭ����:"
					+ tScanDeleteLisBL.mErrors.getFirstError();
		} else {
			mResult = tScanDeleteLisBL.getResult();
			txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
			if (txmlExport == null) {
				System.out.println("null");
				Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��"
						+ tScanDeleteLisBL.mErrors;
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