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

//输出参数
CError cError = new CError( );
//后面要执行的动作：添加，修改，删除
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
		cError.errorMessage = "PFeeInvoiceF1PUI发生错误，但是没有提供详细的出错信息";
		mErrors.addOneError(cError);
	}

	ExeSQL tExeSQL = new ExeSQL();
	//获取临时文件名
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath + tG.Operator + "_yanglhtest" +".vts";
	String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//获取存放临时文件的路径
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
		//合并VTS文件
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//把dataStream存储到磁盘文件
		//System.out.println("存储文件到"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		System.out.println("==> Write VTS file to disk ");

		System.out.println("===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//本来打算采用get方式来传递文件路径
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
	Content = strOperation+"失败，原因是:" + mErrors.getFirstError();
%>
<script language="javascript">
alert("<%=Content%>");
top.close();
</script>
<%
}
%>