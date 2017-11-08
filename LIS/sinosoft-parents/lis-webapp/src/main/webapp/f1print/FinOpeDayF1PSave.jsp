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
<%@page import="com.sinosoft.service.*" %>
<%
	loggerDebug("FinOpeDayF1PSave","start");
	String mDay[]=new String[2];
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	  //输出参数
	  CError cError = new CError( );
	  //后面要执行的动作：添加，修改，删除

	  String tRela  = "";
	  String FlagStr = "";
	  String Content = "";
	  String strOperation = "";
	  String toperator = "";
	strOperation = request.getParameter("fmtransact");
	mDay[0]=request.getParameter("StartDay");
	mDay[1]=request.getParameter("EndDay");
	loggerDebug("FinOpeDayF1PSave","start"+mDay[0]);
	loggerDebug("FinOpeDayF1PSave","end"+mDay[1]);
	toperator = tG.Operator;
	loggerDebug("FinOpeDayF1PSave",tG.Operator);
	loggerDebug("FinOpeDayF1PSave",tG.ManageCom);
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    	tVData.addElement(mDay);
    	tVData.addElement(tG);
    	 //20110512 modified by Cuizhe 代码报错，找不到BusinessDelegate类，找不到tOperator参数，tBusinessDelegate没有mErrors属性，改为getCErrors    	
    	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	
    	 if(!tBusinessDelegate.submitData(tVData,strOperation,"FinPayDayF1PUI")){
    	      mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
    	      cError.moduleName = "FinPayDayRiskF1PSave";
    	      cError.functionName = "submitData";
    	      cError.errorMessage = "FinPayDayF1PUI发生错误，但是没有提供详细的出错信息";
    	      mErrors.addOneError(cError);
      }
    	
    	//FinPayDayF1PUI tFinPayDayF1PUI = new FinPayDayF1PUI();
    	//if(!tFinPayDayF1PUI.submitData(tVData,"CONFIRM"))
    	//{
             //mErrors.copyAllErrors(tFinPayDayF1PUI.mErrors);
      	     //cError.moduleName = "FinPayDayRiskF1PSave";
             //cError.functionName = "submitData";
             //cError.errorMessage = "FinPayDayF1PUI发生错误，但是没有提供详细的出错信息";
             //mErrors.addOneError(cError);
        //}
	mResult = tBusinessDelegate.getResult();
	XmlExport txmlExport = new XmlExport();
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	if (txmlExport==null)
	{
	  loggerDebug("FinOpeDayF1PSave","null");
	}
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("FinOpeDayF1PSave","put session value");
	response.sendRedirect("GetF1Print.jsp");

%>
