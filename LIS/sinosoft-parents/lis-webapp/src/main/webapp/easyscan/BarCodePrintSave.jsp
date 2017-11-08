
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
	String doccode = request.getParameter("DocCode");
	String subtype = request.getParameter("SubType");
	GlobalInput tG = (GlobalInput) session.getValue("GI");

	CErrors tError = null;

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("DocCode", doccode);
	tTransferData.setNameAndValue("SubType", subtype);
	VData tVData = new VData();
	tVData.add(tTransferData);
	tVData.add(tG);
	
	//BarCodePrintBL tBarCodePrintBL = new BarCodePrintBL();
	String busiName="BarCodePrintBL";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	try{
//	if (!tBarCodePrintBL.submitData(tVData, "")) {
//		tError = tBarCodePrintBL.mErrors;
//		loggerDebug("BarCodePrintSave","fail to get BPOReSend data");
//		Content = " ����ʧ�ܣ�ԭ���ǣ�" + tError.getFirstError();
//		FlagStr = "Fail";
//	} 
	  if(!tBusinessDelegate.submitData(tVData,"",busiName))
	   {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
					   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
					   FlagStr = "Fail";
			}
			else
			{
					   Content = "����ʧ��";
					   FlagStr = "Fail";				
			}
	   }


	else {
		Content = " �����ɹ�";
		FlagStr = "Succ";
	}
	}catch(Exception ex){
		ex.printStackTrace();
		Content = " ����ʧ�ܣ�ԭ���ǣ�" + ex.getMessage();
		FlagStr = "Fail";
	}
	if (FlagStr.equals("Succ")) {
		//VData mResult = tBarCodePrintBL.getResult();
		VData mResult = tBusinessDelegate.getResult();
		XmlExport txmlExport = (XmlExport) mResult
		.getObjectByObjectName("XmlExport", 0);
		if (txmlExport == null) {
			loggerDebug("BarCodePrintSave","null");
		}
		session
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		loggerDebug("BarCodePrintSave","put session value");
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
	
