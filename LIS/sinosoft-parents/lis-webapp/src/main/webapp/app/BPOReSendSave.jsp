
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.tb.BPOReSendBL"%>
<%@page import="com.sinosoft.service.*" %>
<%
	String FlagStr = "";
	String Content = "";
	String prtno = request.getParameter("PrtNo");
	GlobalInput tG = (GlobalInput) session.getValue("GI");

	CErrors tError = null;

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("DocCode", prtno);
	VData tVData = new VData();
	tVData.add(tTransferData);
	tVData.add(tG);
	
	//BPOReSendBL tBPOReSendBL = new BPOReSendBL();
   String busiName="tbBPOReSendBL";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	try{
	if (!tBusinessDelegate.submitData(tVData, "",busiName)) {
		tError = tBusinessDelegate.getCErrors();
		loggerDebug("BPOReSendSave","fail to get BPOReSend data");
		Content = " 提数失败，原因是：" + tError.getFirstError();
		FlagStr = "Fail";
	} else {
		Content = " 提数成功";
		FlagStr = "Succ";
	}
	}catch(Exception ex){
		ex.printStackTrace();
		Content = " 提数失败，原因是：" + ex.getMessage();
		FlagStr = "Fail";
	}
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

