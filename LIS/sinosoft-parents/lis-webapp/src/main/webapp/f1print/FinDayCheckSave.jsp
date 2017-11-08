<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//程序名称：按险种打印操作员日结
	//程序功能：
	//创建日期：2002-12-12
	//创建人  ：lh
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	loggerDebug("FinDayCheckSave", "start");
	String mDay[] = new String[2];
	GlobalInput tG1 = new GlobalInput();
	tG1 = (GlobalInput) session.getValue("GI");
	//输出参数
	CError cError = new CError();
	//后面要执行的动作：添加，修改，删除
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = ""; //用来判断是收费还是付费
	String strOpt = ""; //LYS 用来记录是暂收还是预收
	strOperation = request.getParameter("fmtransact");
	strOpt = request.getParameter("Opt");
	loggerDebug("FinDayCheckSave", "收费类型是" + strOpt);
	mDay[0] = request.getParameter("StartDay");
	mDay[1] = request.getParameter("EndDay");
	String managecom = request.getParameter("ManageCom");

	GlobalInput tG = new GlobalInput();
	tG.setSchema(tG1);
	if (managecom != null && !"".equals(managecom))
		tG.ManageCom = managecom;

	loggerDebug("FinDayCheckSave", "start" + mDay[0]);
	loggerDebug("FinDayCheckSave", "end" + mDay[1]);
	loggerDebug("FinDayCheckSave", tG.Operator);
	loggerDebug("FinDayCheckSave", tG.ManageCom);
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(mDay);
	tVData.addElement(strOpt); //传递收费的类型 暂收OR预收
	tVData.addElement(tG);
	XmlExportNew txmlExport = new XmlExportNew();
	//20110512 modified by Cuizhe 代码报错，找不到BusinessDelegate类，找不到tOperator参数，tBusinessDelegate没有mErrors属性，改为getCErrors  
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();

	if (!tBusinessDelegate.submitData(tVData, strOperation,
			"FinDayCheckUI")) {
		mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
		cError.moduleName = "FinChargeDayRiskF1PSave.jsp";
		cError.functionName = "submitData";
		cError.errorMessage = "FinDayCheckUI发生错误，但是没有提供详细的出错信息";
		mErrors.addOneError(cError);
	}

	//FinDayCheckUI tFinDayCheckUI = new FinDayCheckUI();
	//if(!tFinDayCheckUI.submitData(tVData,strOperation))
	//{
	//  mErrors.copyAllErrors(tFinDayCheckUI.mErrors);
	//  cError.moduleName = "FinDayCheckSave";
	//  cError.functionName = "submitData";
	//  cError.errorMessage = "FinDayCheckUI发生错误，但是没有提供详细的出错信息";
	//  mErrors.addOneError(cError);
	//}
	mResult = tBusinessDelegate.getResult();
	//XmlExport txmlExport = new XmlExport();
	txmlExport = (XmlExportNew) mResult.getObjectByObjectName(
			"XmlExportNew", 0);
	if (txmlExport == null) {
		loggerDebug("FinDayCheckSave", "null");
		FlagStr = "Fail";
		Content = "没有获得相应的数据准备类";
	}
	if (!"Fail".equals(FlagStr)) {
		//session.putValue("PrintStream", txmlExport.getInputStream());
		//loggerDebug("FinDayCheckSave","put session value");
		//response.sendRedirect("GetF1Print.jsp");
		request
		.setAttribute("PrintStream", txmlExport
				.getInputStream());
		request.getRequestDispatcher("../print/ShowPrint.jsp").forward(
		request, response);
		//session.putValue("PrintStream", txmlExport.getInputStream());
		loggerDebug("FinDayCheckSave", "put session value");

	} else {
%>
<html>
<script language="javascript">
    			top.opener.afterSubmit('<%=FlagStr%>', '<%=Content%>');
    		  //  window.open("../f1print/GetF1Print.jsp");
    	</script>
</html>
<%
}
%>
