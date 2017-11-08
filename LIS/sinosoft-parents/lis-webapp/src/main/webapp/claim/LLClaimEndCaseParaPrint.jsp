
<%
	//文件名称：LLClaimEndCaseParaPrint.jsp
	//文件功能：结案单证打印-----传入 “单证代码”、“赔案号、客户号”
	//创建人：yuejw
	//创建日期:2005-08-10
	//修改人:niuzj
	//修改日期:2005-09-17
	//修改原因:由原来打印一联改成打印两联
	//修改人:niuzj
	//修改日期:2005-10-04
	//修改原因:把打印两联重新修改为只打印一联(重新GET原来版本)
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
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	if (tGI == null) {
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
		loggerDebug("LLClaimEndCaseParaPrint", "页面失效,请重新登陆");
	}

	String transact = request.getParameter("fmtransact"); //获取操作
	String tPath = application.getRealPath("f1print/MStemplate") + "/"; //模版所在路径
	loggerDebug("LLClaimEndCaseParaPrint", "tPath=" + tPath);
	String tPrtType = request.getParameter("prtType1");
	loggerDebug("LLClaimEndCaseParaPrint", "prtType= " + tPrtType);

	//单证打印参数-----用于打印单个单证时传入“单证代码”
	LLParaPrintSchema tLLParaPrintSchema = new LLParaPrintSchema();
	tLLParaPrintSchema.setPrtCode(request.getParameter("PrtCode"));

	//String使用TransferData打包后提交-----用于传送 赔案号、客户号、模版路径
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("ClmNo", request
			.getParameter("ClmNo")); //赔案号
	mTransferData.setNameAndValue("CustNo", request
			.getParameter("CustomerNo")); //客户号
	mTransferData.setNameAndValue("Path", tPath);
	mTransferData.setNameAndValue("PrtSeq", request
			.getParameter("PrtSeq"));
	mTransferData.setNameAndValue("PrtType", tPrtType);

	VData tVData = new VData(); //准备往传输数据 VData
	VData tResult = new VData(); //接收后台的数据
	XmlExportNew txmlExport = new XmlExportNew();
	tVData.add(tGI);
	tVData.add(mTransferData);
	tVData.add(tLLParaPrintSchema);

	String busiName = "LLParaPrintUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, transact, busiName)) {
		if (tBusinessDelegate.getCErrors() != null
		&& tBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = "数据提交失败，原因是:"
			+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "数据提交失败";
			FlagStr = "Fail";
		}
	} else {
		Content = " 数据提交成功! ";
		FlagStr = "Succ";
		tResult = tBusinessDelegate.getResult();
		txmlExport = (XmlExportNew) tResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "没有得到要显示的数据文件";
			loggerDebug("LLClaimEndCaseParaPrint", "没有得到要显示的数据文件");
		}
	}

	if (FlagStr.equals("Succ")) {
//		session中的值用于工作流的跳转
		session.putValue("PrtSeq", request.getParameter("PrtSeq"));

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
