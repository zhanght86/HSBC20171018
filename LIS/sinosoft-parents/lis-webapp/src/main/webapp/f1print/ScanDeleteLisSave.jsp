<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：ScanDeleteLisSave.jsp
//程序功能：综合打印—承保报表清单--单证删除清单
//创建日期：2010-06-02
//创建人  ：hanabin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

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
			Content = " 处理失败，原因是:"
					+ tScanDeleteLisBL.mErrors.getFirstError();
		} else {
			mResult = tScanDeleteLisBL.getResult();
			txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
			if (txmlExport == null) {
				System.out.println("null");
				Content = "打印失败,原因是没有需要打印的数据信息！"
						+ tScanDeleteLisBL.mErrors;
				FlagStr = "Fail";
			} else {
				session.putValue("PrintStream", txmlExport.getInputStream());
				System.out.println("put session value");
				response.sendRedirect("../f1print/GetF1Print.jsp");
			}
		}
	} catch (Exception ex) {
		Content = "PRINT" + "失败，原因是:" + ex.toString();
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