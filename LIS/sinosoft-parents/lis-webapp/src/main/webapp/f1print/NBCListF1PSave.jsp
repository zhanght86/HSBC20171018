<%@include file="../common/jsp/UsrCheck.jsp"%>
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
<%
	loggerDebug("NBCListF1PSave","start");
	String mDay[]=new String[2];
	String SaleChnl = "";
	String mManageCom ="";
	String mRiskCode ="";
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
	SaleChnl=request.getParameter("SaleChnl");
	mManageCom=request.getParameter("ManageCom");
	mRiskCode=request.getParameter("RiskCode");
	loggerDebug("NBCListF1PSave","start"+mDay[0]);
	loggerDebug("NBCListF1PSave","end"+mDay[1]);
	loggerDebug("NBCListF1PSave",tG.Operator);
	loggerDebug("NBCListF1PSave",tG.ManageCom);
	loggerDebug("NBCListF1PSave","RiskCode:"+mRiskCode);
	LCPolSchema tLCPolSchema = new LCPolSchema();
	tLCPolSchema.setSaleChnl(SaleChnl);
	tLCPolSchema.setManageCom(mManageCom);
	tLCPolSchema.setRiskCode(mRiskCode);
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
  tVData.addElement(mDay);
  tVData.addElement(tG);
  tVData.addElement(tLCPolSchema);
  NBCListUI tNBCListUI = new NBCListUI();
  if(!tNBCListUI.submitData(tVData,""))
  {
    mErrors.copyAllErrors(tNBCListUI.mErrors);
    cError.moduleName = "NBCListUI";
    cError.functionName = "submitData";
    cError.errorMessage = "NBCListUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
    mErrors.addOneError(cError);
   }
	mResult = tNBCListUI.getResult();
	XmlExport txmlExport = new XmlExport();
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	if (txmlExport==null)
	{
	  loggerDebug("NBCListF1PSave","null");
	}
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("NBCListF1PSave","put session value");
	response.sendRedirect("GetF1Print.jsp");

%>
