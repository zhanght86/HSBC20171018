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
  loggerDebug("FinChargeDayModeF1PSave","start"+mDay[0]);
  loggerDebug("FinChargeDayModeF1PSave","end"+mDay[1]);
  loggerDebug("FinChargeDayModeF1PSave",tG.Operator);
  loggerDebug("FinChargeDayModeF1PSave",tG.ManageCom);
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
      tVData.addElement(mDay);
      tVData.addElement(tG);
//  	20110512 modified by Cuizhe ���뱨���Ҳ���BusinessDelegate�࣬�Ҳ���tOperator������tBusinessDelegateû��mErrors���ԣ���ΪgetCErrors    	      
      BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	
    	 if(!tBusinessDelegate.submitData(tVData,strOperation,"FinChargeDayModeF1PUI")){
    	      mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
    	      cError.moduleName = "FinChargeDayRiskF1PSave.jsp";
    	      cError.functionName = "submitData";
    	      cError.errorMessage = "FinChargeDayModeF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
    	      mErrors.addOneError(cError);
      }
      
      
      //FinChargeDayModeF1PUI tFinChargeDayModeF1PUI = new FinChargeDayModeF1PUI();
      //if(!tFinChargeDayModeF1PUI.submitData(tVData,strOperation))
      //{
      //       mErrors.copyAllErrors(tFinChargeDayModeF1PUI.mErrors);
      //       cError.moduleName = "FinChargeDayModeF1PSave";
      //       cError.functionName = "submitData";
      //       cError.errorMessage = "FinChargeDayModeF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
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
  // ��XmlExport������ʾ���ͻ���
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
