<%@page contentType="text/html;charset=GBK" %>
<%
//name : 
//functon : 
//Creator  ：路明
//Date ：2006-12-28
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.f1print.*"%>
 <%@page import="java.io.*"%>
<%
  System.out.println("开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  System.out.println("--------------------start------------------");
	
  	String strMngCom    = request.getParameter("ManageCom");
  	String strStartDate = request.getParameter("StartDate");
  	String strEndDate = request.getParameter("EndDate");
  	String strPrintDate = request.getParameter("PrintDate"); 
    
    System.out.println("管理机构是"+strMngCom);
    System.out.println("开始日期是"+strStartDate);
    System.out.println("结束日期是"+strEndDate);
    System.out.println("打印日期是"+strPrintDate);
     
  	
  	ReportLisUI tReportLisUI=new ReportLisUI();
  	
		  VData tVData = new VData();
		  VData mResult = new VData();
			 
			  try
			  {
			    tVData.addElement(strMngCom);
			    tVData.addElement(strStartDate); 
			    tVData.addElement(strEndDate);
			    tVData.addElement(strPrintDate);
			    
			    tReportLisUI.submitData(tVData,"PRINT");
			  }
			  catch(Exception ex)
			  {
			    Content = "PRINT"+"失败，原因是:" + ex.toString();
			    FlagStr = "Fail";
			  }
 
    mResult = tReportLisUI.getResult();
 
  //进行打印连接
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  
  
  if (txmlExport==null)
  {
    System.out.println("null");
     tError = tReportLisUI.mErrors;
    Content = "打印失败,原因是＝＝"+tError.getFirstError();
    FlagStr = "Fail";
  }
  else
  {
           //合并VTS文件
        String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
        CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
        
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        tcombineVts.output(dataStream);
        
        //获取临时文件名
        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
        String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
        LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);    
        LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
        String strFilePath = tLDSysVarSchema.getV("SysVarValue");
        String strVFFileName = strFilePath + FileQueue.getFileName()+".vts";
        
        String strRealPath = application.getRealPath("/").replace('\\','/');
        String strVFPathName = strRealPath + strVFFileName;
        
        System.out.println("strVFFileName="+strVFFileName);
        System.out.println("strRealPath="+strRealPath);
        System.out.println("strVFPathName="+strVFPathName);
        
        AccessVtsFile.saveToFile(dataStream, strVFPathName);
        System.out.println("==> Write VTS file to disk ");
        
        
        session.putValue("RealPath", strVFPathName);
        //session.putValue("PrintStream", txmlExport.getInputStream());
        System.out.println("put session value");
        //response.sendRedirect("../f1print/GetF1Print.jsp");
        request.getRequestDispatcher("./GetF1PrintJ1.jsp").forward(request,response);
  }
%>  
<html>
<script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>