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
  loggerDebug("FFChargeDayModeF1PSave","start");
  String mDay[]=new String[2];
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
    //�������
    CError cError = new CError( );
    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��

    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String strOperation = "";
  strOperation = request.getParameter("fmtransact");
  mDay[0]=request.getParameter("StartDay");
  mDay[1]=request.getParameter("EndDay");
  loggerDebug("FFChargeDayModeF1PSave","start"+mDay[0]);
  loggerDebug("FFChargeDayModeF1PSave","end"+mDay[1]);
  loggerDebug("FFChargeDayModeF1PSave",tG.Operator);
  loggerDebug("FFChargeDayModeF1PSave",tG.ManageCom);
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
      tVData.addElement(mDay);
      tVData.addElement(tG);
//    20110512 modified by Cuizhe ���뱨���Ҳ���BusinessDelegate�࣬�Ҳ���tOperator������tBusinessDelegateû��mErrors���ԣ���ΪgetCErrors
       BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	
    	 if(!tBusinessDelegate.submitData(tVData,strOperation,"FFChargeDayModeF1PUI")){
    	      mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
    	      cError.moduleName = "FinChargeDayRiskF1PSave.jsp";
    	      cError.functionName = "submitData";
    	      cError.errorMessage = "FFChargeDayRiskF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
    	      mErrors.addOneError(cError);
      }
      
      
      //FFChargeDayModeF1PUI tFFChargeDayModeF1PUI = new FFChargeDayModeF1PUI();
      //if(!tFFChargeDayModeF1PUI.submitData(tVData,strOperation))
      //{
      //       mErrors.copyAllErrors(tFFChargeDayModeF1PUI.mErrors);
      //       cError.moduleName = "FFChargeDayModeF1PSave";
      //       cError.functionName = "submitData";
      //       cError.errorMessage = "FFChargeDayModeF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
      //       mErrors.addOneError(cError);
      //  }
  mResult = tBusinessDelegate.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("FFChargeDayModeF1PSave","null");
  }
  session.putValue("PrintStream", txmlExport.getInputStream());
  loggerDebug("FFChargeDayModeF1PSave","put session value");
  response.sendRedirect("GetF1Print.jsp");
%>
