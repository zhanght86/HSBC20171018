<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：EdorNoticePrintSave.jsp
//程序功能：保全通知书在线打印控制台
//创建日期：2005-08-02 16:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容 
%>
<!--用户校验类-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="java.io.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.report.f1report.*"%>
<%
  loggerDebug("OmniAccQuerySave","开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";

  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  VData tVData = new VData();
  String tStartDate=request.getParameter("StartDate");	
  String tEndDate=request.getParameter("EndDate");	
  String tContNo=request.getParameter("ContNo");	
  String tPolNo=request.getParameter("PolNo");	

  TransferData tTransferData = new TransferData();

	tTransferData.setNameAndValue("StartDate",tStartDate);
	tTransferData.setNameAndValue("EndDate",tEndDate);
	tTransferData.setNameAndValue("ContNo",tContNo);
	tTransferData.setNameAndValue("PolNo",tPolNo);
	
  tVData.add(tG);
  tVData.add(tTransferData);
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
	    
  XmlExport txmlExport = new XmlExport();  
  BqAccReportBL tBqAccReportBL = new BqAccReportBL();
  if(!tBqAccReportBL.submitData(tVData,"PRINT"))
  {
      FlagStr = "Fail";
      Content=tBqAccReportBL.mErrors.getFirstError().toString();                 
  } 
  else
  { 
      ExeSQL tExeSQL = new ExeSQL();
      //获取临时文件名
      String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
      String strFilePath = tExeSQL.getOneValue(strSql);
      String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
      //获取存放临时文件的路径
      //strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
      //String strRealPath = tExeSQL.getOneValue(strSql);
      String strRealPath = application.getRealPath("/").replace('\\','/');
      String strVFPathName = strRealPath + strVFFileName;

      CombineVts tcombineVts = null;

	  mResult = tBqAccReportBL.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	     FlagStr = "Fail";
	     tError = tBqAccReportBL.mErrors;
         Content = "打印失败,原因是＝＝"+tError.getFirstError();  
	  }
      else
      {
	     //合并VTS文件
         String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	     tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	     ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	     tcombineVts.output(dataStream);

	     //把dataStream存储到磁盘文件
	     loggerDebug("OmniAccQuerySave","存储文件到"+strVFPathName);
	     AccessVtsFile.saveToFile(dataStream,strVFPathName);
	     loggerDebug("OmniAccQuerySave","==> Write VTS file to disk ");

	     loggerDebug("OmniAccQuerySave","===strVFFileName : "+strVFFileName);
	     session.putValue("RealPath", strVFPathName);
	     
	      // session.putValue("PrintStream", txmlExport.getInputStream());
	      loggerDebug("OmniAccQuerySave","put session value");

	      //response.sendRedirect("../uw/GetF1Print.jsp");
	      request.getRequestDispatcher("../uw/GetF1PrintJ1.jsp").forward(request,response);
	      loggerDebug("OmniAccQuerySave","after calling forward()");
      }
   }
   
	if( !Content.equals("") ) {
		loggerDebug("OmniAccQuerySave","outputStream object has been open");
%>
<html>
<script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
	}
%>
