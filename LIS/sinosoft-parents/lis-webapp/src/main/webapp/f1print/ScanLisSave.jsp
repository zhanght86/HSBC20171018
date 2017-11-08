<%@page contentType="text/html;charset=GBK" %>
<%
//name : SRiskOutLisSave.jsp
//functon : Receive data and transfer data
//Creator  ：刘岩松
//Date ：2003-02-14
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
  String strIssueStartDate = request.getParameter("IssueStartDate");
  String strIssueEndDate = request.getParameter("IssueEndDate");
  String strScanType = request.getParameter("ScanType");
  String strSubType = request.getParameter("SubType");
  String strOperaterNo = request.getParameter("OperaterNo");
  String strBusiType="";
  if(request.getParameter("BusiType")==null
		  ||request.getParameter("BusiType")==""){
	  strBusiType="";
  }else{
	  strBusiType =request.getParameter("BusiType");
  }
  	
  ScanLisUI tScanLisUI=new ScanLisUI();
 
  VData tVData = new VData();
  VData mResult = new VData();
  TransferData tTransferData = new TransferData();
  
  try
  {
    tTransferData.setNameAndValue("ManageCom",strMngCom);
    tTransferData.setNameAndValue("ScanStartDate",strIssueStartDate);
    tTransferData.setNameAndValue("ScanEndDate",strIssueEndDate);
    tTransferData.setNameAndValue("ScanType",strScanType);
    tTransferData.setNameAndValue("SubType",strSubType);
    tTransferData.setNameAndValue("OperaterNo",strOperaterNo);
    tTransferData.setNameAndValue("BusiType",strBusiType);
    tVData.addElement(tTransferData);
    tScanLisUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tScanLisUI.getResult();
  //进行打印连接
  
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
 
  
  if (txmlExport==null)
  {
    tError = tScanLisUI.mErrors;
    Content = "打印失败,原因是＝"+tError.getFirstError();
    FlagStr = "Fail";
  }
  else
  {	 
	         //把dataStream存储到磁盘文件       
	         //把dataStream存储到磁盘文件       
	        //session.putValue("PrintStream", txmlExport.getInputStream());
	  	    //System.out.println("put session value");
	  	   // response.sendRedirect("GetF1Print.jsp");
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
	        System.out.println("put session value");
	        request.getRequestDispatcher("./GetF1PrintJ1.jsp").forward(request,response);
	        //response.sendRedirect("./GetF1PrintJ1.jsp");
	        //response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
  }
  
  if( !Content.equals("") )
     { 
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