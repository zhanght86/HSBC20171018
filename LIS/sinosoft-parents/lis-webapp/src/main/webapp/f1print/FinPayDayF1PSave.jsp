<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<% 
//�������ƣ������ִ�ӡ����Ա�ս�
//�����ܣ�
//�������ڣ�2002-12-12 
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
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
	loggerDebug("FinPayDayF1PSave","start");
	String mDay[]=new String[2];
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	  //�������
	  CError cError = new CError( );
	  //����Ҫִ�еĶ��������ӣ��޸ģ�ɾ��

	  String tRela  = "";
	  String FlagStr = "";
	  String Content = "";
	  String strOperation = "";
	strOperation = request.getParameter("fmtransact");
	mDay[0]=request.getParameter("StartDay");
	mDay[1]=request.getParameter("EndDay");
	loggerDebug("FinPayDayF1PSave","start"+mDay[0]);
	loggerDebug("FinPayDayF1PSave","end"+mDay[1]);
	loggerDebug("FinPayDayF1PSave",tG.Operator);
	loggerDebug("FinPayDayF1PSave",tG.ManageCom);
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    	tVData.addElement(mDay);
    	tVData.addElement(tG);
    	
    	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	
    	 if(!tBusinessDelegate.submitData(tVData,strOperation,"FinPayDayF1PUI")){
    	      mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
    	      cError.moduleName = "FinChargeDayRiskF1PSave.jsp";
    	      cError.functionName = "submitData";
    	      cError.errorMessage = "FinPayDayF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
    	      mErrors.addOneError(cError);
      }
    	
    	
    	//FinPayDayF1PUI tFinPayDayF1PUI = new FinPayDayF1PUI();
    	//if(!tFinPayDayF1PUI.submitData(tVData,"PRINT"))
    	//{
      //       mErrors.copyAllErrors(tFinPayDayF1PUI.mErrors);
      //	     cError.moduleName = "FinPayDayRiskF1PSave";
      //       cError.functionName = "submitData";
      //       cError.errorMessage = "FinPayDayF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
      //       mErrors.addOneError(cError);
      //  }
	mResult = tBusinessDelegate.getResult();
	XmlExport txmlExport = new XmlExport();
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	if (txmlExport==null)
	{
	  loggerDebug("FinPayDayF1PSave","null");
	}
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("FinPayDayF1PSave","put session value");
	response.sendRedirect("GetF1Print.jsp");

%>