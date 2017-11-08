<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	loggerDebug("FFChargeDayRiskF1PSave","start");
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
	strOperation = request.getParameter("fmtransact");
	mDay[0]=request.getParameter("StartDay");
	mDay[1]=request.getParameter("EndDay");
	loggerDebug("FFChargeDayRiskF1PSave","start"+mDay[0]);
	loggerDebug("FFChargeDayRiskF1PSave","end"+mDay[1]);
	loggerDebug("FFChargeDayRiskF1PSave",tG.Operator);
	loggerDebug("FFChargeDayRiskF1PSave",tG.ManageCom);
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    	tVData.addElement(mDay);
    	tVData.addElement(tG);
    	
    	  	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 //20110512 modified by Cuizhe 代码报错，找不到BusinessDelegate类，找不到tOperator参数，tBusinessDelegate没有mErrors属性，改为getCErrors
    	 if(!tBusinessDelegate.submitData(tVData,strOperation,"FFChargeDayRiskF1PUI")){
    		  mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
    	      cError.moduleName = "FinChargeDayRiskF1PSave.jsp";
    	      cError.functionName = "submitData";
    	      cError.errorMessage = "FFChargeDayRiskF1PUI发生错误，但是没有提供详细的出错信息";
    	      mErrors.addOneError(cError);
      }
    	
    	
    	//FFChargeDayRiskF1PUI tFFChargeDayRiskF1PUI = new FFChargeDayRiskF1PUI();
    	//if(!tFFChargeDayRiskF1PUI.submitData(tVData,strOperation))
    	//{
      //       mErrors.copyAllErrors(tFFChargeDayRiskF1PUI.mErrors);
      //	     cError.moduleName = "FFChargeDayRiskF1PSave";
      //       cError.functionName = "submitData";
      //       cError.errorMessage = "FFChargeDayRiskF1PUI发生错误，但是没有提供详细的出错信息";
      //       mErrors.addOneError(cError);
      //  }
	mResult = tBusinessDelegate.getResult();
	XmlExport txmlExport = new XmlExport();
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	if (txmlExport==null)
	{
	  loggerDebug("FFChargeDayRiskF1PSave","null");
	}
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("FFChargeDayRiskF1PSave","put session value");
	response.sendRedirect("GetF1Print.jsp");

%>
