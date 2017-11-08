<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//程序名称：XQBankSuccPrint.jsp
	//程序功能：实现打印的功能
	//创建人  ：刘岩松
	//创建日期：2004-4-29
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
	loggerDebug("XQBankSuccPrint", "开始执行打印操作");
	String Content = "处理成功！";
	CErrors tError = null;
	String FlagStr = "Fail";
	//初始化全局变量，从前台承接数据
	String strStartDate = request.getParameter("StartDate"); //开始日期
	String strEndDate = request.getParameter("EndDate"); //结束日期
	String strAgentState = request.getParameter("AgentState"); //业务员的状态(1为在职单，0为孤儿单)
	String strPremType = request.getParameter("PremType"); //首续期的标志
	String strFlag = request.getParameter("Flag"); //S or F(S为银行代收，F为银行代付)
	String strStation = request.getParameter("Station");
	String strGetType = request.getParameter("GetType"); //获取结果类型，0，前台打印，1，生成静态报表
	//		获取模板路径
	String templatepath = "";
	LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
	tLDSysVarDB2.setSysVar("XSEXCelTemplate");
	if (!tLDSysVarDB2.getInfo()) {
		loggerDebug("XQBankSuccPrint", "获取模板路径失败");
	}
	templatepath = tLDSysVarDB2.getSysVarValue();
	String strTemplatePath = application.getRealPath(templatepath)
			+ "/";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	XQPremBankSuccUI tXQPremBankSuccUI = new XQPremBankSuccUI();

	VData tVData = new VData();
	VData mResult = new VData();
	try {
		tVData.clear();
		tVData.addElement(strStartDate);
		tVData.addElement(strEndDate);
		tVData.addElement(strAgentState);
		tVData.addElement(strPremType);
		tVData.addElement(strFlag);
		tVData.addElement(strStation);
		tVData.addElement(strTemplatePath);
		tVData.addElement(tG);
		tXQPremBankSuccUI.submitData(tVData, "PRINT");
	} catch (Exception ex) {
		Content = "PRINT" + "失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	if (strGetType.equals("0")) {
		mResult = tXQPremBankSuccUI.getResult();
		XmlExportNew txmlExport = new XmlExportNew();
		txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
		"XmlExportNew", 0);
		if (txmlExport == null) {
			loggerDebug("XQBankSuccPrint", "null");
			tError = tXQPremBankSuccUI.mErrors;
			Content = "打印失败,原因是没有需要打印的数据信息！";
			FlagStr = "Fail";
		} else {
			loggerDebug("XQBankSuccPrint", "put session value");
			request.setAttribute("PrintStream", txmlExport
			.getInputStream());
			request.getRequestDispatcher("../print/ShowPrint.jsp")
			.forward(request, response);
		}
	}
%>
<html>
<script language="javascript">
	alert("<%=Content%>");
	if(<%=strGetType%> == '0')
   {
			top.opener.focus();
			top.close();
   }
</script>
</html>
