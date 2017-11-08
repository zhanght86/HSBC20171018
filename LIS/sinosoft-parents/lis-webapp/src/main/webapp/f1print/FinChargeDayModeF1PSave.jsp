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
  loggerDebug("FinChargeDayModeF1PSave","start");
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
  loggerDebug("FinChargeDayModeF1PSave","start"+mDay[0]);
  loggerDebug("FinChargeDayModeF1PSave","end"+mDay[1]);
  loggerDebug("FinChargeDayModeF1PSave",tG.Operator);
  loggerDebug("FinChargeDayModeF1PSave",tG.ManageCom);
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
      tVData.addElement(mDay);
      tVData.addElement(tG);
//  	20110512 modified by Cuizhe 代码报错，找不到BusinessDelegate类，找不到tOperator参数，tBusinessDelegate没有mErrors属性，改为getCErrors    	      
      BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	
    	 if(!tBusinessDelegate.submitData(tVData,strOperation,"FinChargeDayModeF1PUI")){
    	      mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
    	      cError.moduleName = "FinChargeDayRiskF1PSave.jsp";
    	      cError.functionName = "submitData";
    	      cError.errorMessage = "FinChargeDayModeF1PUI发生错误，但是没有提供详细的出错信息";
    	      mErrors.addOneError(cError);
      }
      
      
      //FinChargeDayModeF1PUI tFinChargeDayModeF1PUI = new FinChargeDayModeF1PUI();
      //if(!tFinChargeDayModeF1PUI.submitData(tVData,strOperation))
      //{
      //       mErrors.copyAllErrors(tFinChargeDayModeF1PUI.mErrors);
      //       cError.moduleName = "FinChargeDayModeF1PSave";
      //       cError.functionName = "submitData";
      //       cError.errorMessage = "FinChargeDayModeF1PUI发生错误，但是没有提供详细的出错信息";
      //       mErrors.addOneError(cError);
      //  }
  mResult = tBusinessDelegate.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("FinChargeDayModeF1PSave","null");
  }
  session.putValue("PrintStream", txmlExport.getInputStream());
  loggerDebug("FinChargeDayModeF1PSave","put session value");
  response.sendRedirect("GetF1Print.jsp");

  /*
  // 将XmlExport对象显示到客户端
  try {
     F1PrintParser fp = new F1PrintParser(txmlExport.getInputStream(), application.getRealPath("ui/f1print/template/"));
     //F1PrintParser fp = new F1PrintParser(new FileInputStream(application.getRealPath("f1print/2.xml")), application.getRealPath(""));
     fp.output(response.getOutputStream())
  }catch(java.net.MalformedURLException urlEx){
    urlEx.printStackTrace();
    cError.moduleName = "FinChargeDayRiskF1PSave";
    cError.functionName = "output";
    cError.errorMessage = urlEx.toString();
    mErrors.addOneError(cError);
  }catch(java.io.IOException ioEx){
    ioEx.printStackTrace();
    cError.moduleName = "FinChargeDayRiskF1PSave";
    cError.functionName = "output";
    cError.errorMessage = ioEx.toString();
    mErrors.addOneError(cError);
  }
  */
%>
