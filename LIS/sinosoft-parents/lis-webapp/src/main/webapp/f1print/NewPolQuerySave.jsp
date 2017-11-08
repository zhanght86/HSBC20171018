<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//name : SRiskOutLisSave.jsp
//functon : Receive data and transfer data
//Creator  ��������
//Date ��2003-02-14
%>
<!--�û�У����-->
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
  loggerDebug("NewPolQuerySave","��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  loggerDebug("NewPolQuerySave","--------------------start------------------");
  
  GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

		
  String strMngCom    = request.getParameter("ManageCom");
  String strIssueStartDate = request.getParameter("IssueStartDate");
  String strIssueEndDate = request.getParameter("IssueEndDate");
  String strScanType = request.getParameter("ScanType");
  String strSignStartDate = request.getParameter("SignStartDate");
  String strsSignEndDate = request.getParameter("SignEndDate");
  String strSaleChnl = request.getParameter("SaleChnl");
  
  NewPolQueryUI tNewPolQueryUI=new NewPolQueryUI();
 
  VData tVData = new VData();
  VData mResult = new VData();
  TransferData tTransferData = new TransferData();
  
  try
  {
    tTransferData.setNameAndValue("ManageCom",strMngCom);
    tTransferData.setNameAndValue("ScanStartDate",strIssueStartDate);
    tTransferData.setNameAndValue("ScanEndDate",strIssueEndDate);
    tTransferData.setNameAndValue("ScanType",strScanType);
    tTransferData.setNameAndValue("SaleChnl",strSaleChnl);
    tTransferData.setNameAndValue("SignEndDate",strsSignEndDate);
    tTransferData.setNameAndValue("SignStartDate",strSignStartDate);
    tVData.addElement(tTransferData);
    tVData.addElement(tG);
    tNewPolQueryUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tNewPolQueryUI.getResult();
  //���д�ӡ����
  
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
 
  
  if (txmlExport==null)
  {
    tError = tNewPolQueryUI.mErrors;
    Content = "��ӡʧ��,ԭ���ǣ�"+tError.getFirstError();
    FlagStr = "Fail";
  }
  else
  {	 
	         //��dataStream�洢�������ļ�       
	         //��dataStream�洢�������ļ�       
	        //session.putValue("PrintStream", txmlExport.getInputStream());
	  	    //loggerDebug("NewPolQuerySave","put session value");
	  	   // response.sendRedirect("GetF1Print.jsp");
	  	   		//�ϲ�VTS�ļ�
		      String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
          CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	  	    ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
          tcombineVts.output(dataStream);
	  	   
	        //��ȡ��ʱ�ļ���
	        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
          String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
          LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);    
          LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
          String strFilePath = tLDSysVarSchema.getV("SysVarValue");
          String strVFFileName = strFilePath + FileQueue.getFileName()+".vts";

          String strRealPath = application.getRealPath("/").replace('\\','/');
          String strVFPathName = strRealPath + strVFFileName;
          
          loggerDebug("NewPolQuerySave","strVFFileName="+strVFFileName);
          loggerDebug("NewPolQuerySave","strRealPath="+strRealPath);
          loggerDebug("NewPolQuerySave","strVFPathName="+strVFPathName);
          
        	AccessVtsFile.saveToFile(dataStream, strVFPathName);
     			loggerDebug("NewPolQuerySave","==> Write VTS file to disk ");

	
	        session.putValue("RealPath", strVFPathName);
	        loggerDebug("NewPolQuerySave","put session value");
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
