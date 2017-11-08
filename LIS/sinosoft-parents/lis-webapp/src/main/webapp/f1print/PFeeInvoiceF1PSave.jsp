<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LJAPaySchema"%>
<%@page import="com.sinosoft.lis.schema.LOPRTManager2Schema"%>
<%@page import="com.sinosoft.utility.*"%>
<%
System.out.println("start");
String managecom = request.getParameter("MngCom");
LJAPaySchema tLJAPaySchema = new LJAPaySchema();
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

//�������
CError cError = new CError( );
//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
String Content = "";
String strOperation = "";
strOperation = request.getParameter("fmtransact");
tLJAPaySchema.setPayNo(request.getParameter("PayNo"));
VData tVData = new VData();
VData mResult = new VData();
CErrors mErrors = new CErrors();
tVData.addElement(tLJAPaySchema);
tVData.addElement(tG);

PFeeInvoiceF1PUI tPFeeInvoiceF1PUI = new PFeeInvoiceF1PUI();
try
{
	if(!tPFeeInvoiceF1PUI.submitData(tVData,strOperation))
	{
		mErrors.copyAllErrors(tPFeeInvoiceF1PUI.mErrors);
		cError.moduleName = "PFeeInvoiceF1PSave";
		cError.functionName = "submitData";
		cError.errorMessage = "PFeeInvoiceF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
		mErrors.addOneError(cError);
	}

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

	mResult = tPFeeInvoiceF1PUI.getResult();

	XmlExport txmlExport = new XmlExport();
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	tLOPRTManager2Schema=(LOPRTManager2Schema)mResult.getObjectByObjectName("LOPRTManager2Schema",0);
	if (txmlExport==null)
	{
		System.out.println("null");
	}
	else
	{
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//��dataStream�洢�������ļ�
		//System.out.println("�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		System.out.println("==> Write VTS file to disk ");

		System.out.println("===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//�����������get��ʽ�������ļ�·��
		//response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
		response.sendRedirect("../f1print/GetF1PrintJ1.jsp");

//		session.putValue("PrintStream", txmlExport.getInputStream());
//		System.out.println("put session value");
//		session.putValue("PrintNo","00");
//		session.putValue("PrintQueue",tLOPRTManager2Schema);
//		response.sendRedirect("GetF1Print.jsp");
	}
}
catch(Exception ex)
{
	mErrors=tPFeeInvoiceF1PUI.mErrors;
	Content = strOperation+"ʧ�ܣ�ԭ����:" + mErrors.getFirstError();
%>
<script language="javascript">
alert("<%=Content%>");
top.close();
</script>
<%
}
%>