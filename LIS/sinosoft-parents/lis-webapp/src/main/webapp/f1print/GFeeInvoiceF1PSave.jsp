<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
  loggerDebug("GFeeInvoiceF1PSave","start");
  
  LJAPaySchema tLJAPaySchema = new LJAPaySchema();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");


    //�������
    CError cError = new CError();
    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
    LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String strOperation = "";
  strOperation = request.getParameter("fmtransact");
  tLJAPaySchema.setPayNo(request.getParameter("PayNo"));
  
  loggerDebug("GFeeInvoiceF1PSave",":payno:"+tLJAPaySchema.getPayNo());
  loggerDebug("GFeeInvoiceF1PSave",":payno:"+strOperation);
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
      tVData.addElement(tLJAPaySchema);
      tVData.addElement(tG);
      GFeeInvoiceF1PUI tGFeeInvoiceF1PUI = new GFeeInvoiceF1PUI();
  try
  {    
      
      if(!tGFeeInvoiceF1PUI.submitData(tVData,strOperation))
      {
             mErrors.copyAllErrors(tGFeeInvoiceF1PUI.mErrors);
             cError.moduleName = "GFeeInvoiceF1PSave";
             cError.functionName = "submitData";
             cError.errorMessage = "GFeeInvoiceF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
             mErrors.addOneError(cError);
             throw new Exception();
        }
      else
      	{
   loggerDebug("GFeeInvoiceF1PSave","$$$$$Success Deal Print Data....");
  mResult = tGFeeInvoiceF1PUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  tLOPRTManager2Schema=(LOPRTManager2Schema)mResult.getObjectByObjectName("LOPRTManager2Schema",0);
  ExeSQL tExeSQL = new ExeSQL();
	//��ȡ��ʱ�ļ���
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath + tG.Operator + "_yanglhtest" +".vts";
	String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//��ȡ�����ʱ�ļ���·��
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("/").replace('\\','/');
	String strVFPathName = strRealPath + strVFFileName;

	CombineVts tcombineVts = null;
	if (txmlExport==null)
	{
		loggerDebug("GFeeInvoiceF1PSave","null");
	}
	else
	{
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//��dataStream�洢�������ļ�
		//loggerDebug("GFeeInvoiceF1PSave","�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("GFeeInvoiceF1PSave","==> Write VTS file to disk ");

		loggerDebug("GFeeInvoiceF1PSave","===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//�����������get��ʽ�������ļ�·��
		//response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
		response.sendRedirect("../f1print/GetF1PrintJ1.jsp");

//		session.putValue("PrintStream", txmlExport.getInputStream());
//		loggerDebug("GFeeInvoiceF1PSave","put session value");
//		session.putValue("PrintNo","00");
//		session.putValue("PrintQueue",tLOPRTManager2Schema);
//		response.sendRedirect("GetF1Print.jsp");
	}
      	}
}
catch(Exception ex)
{
	mErrors=tGFeeInvoiceF1PUI.mErrors;
	Content = strOperation+"ʧ�ܣ�ԭ����:" + mErrors.getFirstError();
%>
<script language="javascript">
alert("<%=Content%>");
top.close();
</script>
<%
}
%>
