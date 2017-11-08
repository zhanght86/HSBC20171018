
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.easyscan.BarCodePrintBL"%>
<%@page import="com.sinosoft.service.*" %>
<%
	String FlagStr = "";
	String Content = "";
	String doccode = request.getParameter("ClmNo");
	GlobalInput tG = (GlobalInput) session.getValue("GI");

	CErrors tError = null;

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("DocCode", doccode);
	VData tVData = new VData();
	tVData.add(tTransferData);
	tVData.add(tG);
	
	//BarCodePrintBL tBarCodePrintBL = new BarCodePrintBL();
	String busiName="BarCodePrintBL";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	

	try{
//	if (!tBarCodePrintBL.submitData(tVData, "")) {
//		tError = tBarCodePrintBL.mErrors;
//		loggerDebug("LLClaimBarCodePrintSave","fail to get BPOReSend data");
//		Content = " 提数失败，原因是：" + tError.getFirstError();
//		FlagStr = "Fail";
//	} 
	if(!tBusinessDelegate.submitData(tVData, "",busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
	    	loggerDebug("LLClaimBarCodePrintSave","fail to get BPOReSend data");
			Content = "提数失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
		else
		{
			Content = "提数失败";
			FlagStr = "Fail";				
		}
	}

	else {
		Content = " 提数成功";
		FlagStr = "Succ";
	}
	}catch(Exception ex){
		ex.printStackTrace();
		Content = " 提数失败，原因是：" + ex.getMessage();
		FlagStr = "Fail";
	}
	if (FlagStr.equals("Succ")) {
		//VData mResult = tBarCodePrintBL.getResult();
		VData mResult = tBusinessDelegate.getResult();
		XmlExport txmlExport = (XmlExport) mResult
		.getObjectByObjectName("XmlExport", 0);
		if (txmlExport == null) {
			loggerDebug("LLClaimBarCodePrintSave","null");
		}
		session
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		loggerDebug("LLClaimBarCodePrintSave","put session value");
		response.sendRedirect("../f1print/GetF1Print.jsp");
	} else {
		if (!Content.equals("")) {
%>

<html>
<script language="javascript" type="text/javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
	}
	}
%>
	
