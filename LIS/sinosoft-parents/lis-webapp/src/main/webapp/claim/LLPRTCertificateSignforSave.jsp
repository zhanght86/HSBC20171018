
<%
	//**************************************************************************************************
	//Name：LLPRTCertificateSignforSave.jsp
	//Function：结论保存并理算
	//Author：wsz
	//Date:   2005-8-2
	//修改人:niuzj
	//修改日期:2005-09-20
	//修改原因:由原来打印一联改成打印两联
	//修改人:niuzj
	//修改日期:2005-10-04
	//修改原因:把打印两联重新修改为只打印一联(重新GET原来版本)
	//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	//准备通用参数
	CError cError = new CError();
	String FlagStr = "FAIL";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		loggerDebug("LLPRTCertificateSignforSave", "登录信息没有获取!!!");
		return;
	}

	//准备数据容器信息
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ClmNo", request
			.getParameter("ClmNo"));
	tTransferData.setNameAndValue("CustNo", request
			.getParameter("customerNo"));
	tTransferData.setNameAndValue("ClaimTag", "留存");

	// 准备传输数据 VData
	VData tVData = new VData();
	VData tResult = new VData();
	XmlExportNew txmlExport = new XmlExportNew();

	tVData.add(tG);
	tVData.add(tTransferData);

	String busiName = "LLPRTCertificateSignforUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();

	if (!tBusinessDelegate.submitData(tVData, "", busiName)) {
		if (tBusinessDelegate.getCErrors() != null
		&& tBusinessDelegate.getCErrors().getErrorCount() > 0) {
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++) {
		//loggerDebug("LLPRTCertificateSignforSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
		Content = Content
				+ "原因是: "
				+ tBusinessDelegate.getCErrors().getError(i).errorMessage;
			}
			FlagStr = "Fail";
		} else {
			Content = "处理失败";
			FlagStr = "Fail";
		}
	} else {
		tResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) tResult.getObjectByObjectName(
		"XmlExportNew", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "没有得到要显示的数据文件";
			loggerDebug("LLPRTCertificateSignforSave", "没有得到要显示的数据文件");
		}

	}

	if (FlagStr.equals("Succ")) {
		request
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
	} else {
%>
<html>
<script language="javascript">  
    alert("<%=Content%>");
    top.close();   
</script>
</html>
<%
}
%>
