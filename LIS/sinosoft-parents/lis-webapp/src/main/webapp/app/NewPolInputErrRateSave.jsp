 <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="java.io.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
		loggerDebug("NewPolInputErrRateSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
				String FlagStr = "";
				String Content = "";
   
		   String tManageCom = request.getParameter("ManageCom");
		   String tOperatorNo = request.getParameter("OperatorNo");
		   //String tPrtNo = request.getParameter("PrtNo");
		   String tInputType = request.getParameter("InputType");
		   //String tRiskCode = request.getParameter("RiskCode");
		   //String tAgentCode = request.getParameter("AgentCode");
		   String tStartDate = request.getParameter("StartDate");
		   String tEndDate = request.getParameter("EndDate");
	 
	 
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();

  tVData.addElement(tG);
  
  //传递非SCHEMA信息                                                              
  TransferData tTransferData = new TransferData(); 
  tTransferData.setNameAndValue("ManageCom",tManageCom);                                                                                                                    
  tTransferData.setNameAndValue("StartDate",tStartDate); 
  tTransferData.setNameAndValue("EndDate",tEndDate);     
  tTransferData.setNameAndValue("OperatorNo",tOperatorNo);     
  //tTransferData.setNameAndValue("PrtNo",tPrtNo);     
  //tTransferData.setNameAndValue("InsuredName",tInsuredName);     
  //tTransferData.setNameAndValue("RiskCode",tRiskCode);     
  tTransferData.setNameAndValue("InputType",tInputType);     
  //tTransferData.setNameAndValue("PrintDate",tPrintDate);
  tVData.addElement(tTransferData);  

  loggerDebug("NewPolInputErrRateSave","***************************");  
  loggerDebug("NewPolInputErrRateSave","***************************");
  loggerDebug("NewPolInputErrRateSave","***************************");
  loggerDebug("NewPolInputErrRateSave","***************************");
  loggerDebug("NewPolInputErrRateSave","StartDate="+tStartDate);
  loggerDebug("NewPolInputErrRateSave","EndDate="+tEndDate);
  loggerDebug("NewPolInputErrRateSave","ManageCom="+tManageCom);
  loggerDebug("NewPolInputErrRateSave","OperatorNo="+tOperatorNo);
  //loggerDebug("NewPolInputErrRateSave","tPrtNo="+tPrtNo);
  //loggerDebug("NewPolInputErrRateSave","tInsuredName="+tInsuredName);
  //loggerDebug("NewPolInputErrRateSave","tRiskCode="+tRiskCode);
  loggerDebug("NewPolInputErrRateSave","InputType="+tInputType);
  //loggerDebug("NewPolInputErrRateSave","PrintDate="+tPrintDate);
  loggerDebug("NewPolInputErrRateSave","***************************");  
  loggerDebug("NewPolInputErrRateSave","***************************");
  loggerDebug("NewPolInputErrRateSave","***************************");
  loggerDebug("NewPolInputErrRateSave","***************************");
  loggerDebug("NewPolInputErrRateSave","***************************");  

  XmlExport txmlExport = new XmlExport();

					

  //NewPolInputErrRateUI tNewPolInputErrRateUI = new NewPolInputErrRateUI();
   String busiName="f1printNewPolInputErrRateUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if(!tBusinessDelegate.submitData(tVData,"PRINT",busiName))
  {
  	operFlag=false; 
  	Content=tBusinessDelegate.getCErrors().getFirstError().toString();
  	
  } 
  else
  {
  	mResult = tBusinessDelegate.getResult();
  	
  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  	
  	if(txmlExport==null)
  	{
  		operFlag=false;
  		Content="没有得到要显示的数据文件";
  	}
  
  }


	
	if (operFlag==true)
	{
	
	    String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		  loggerDebug("NewPolInputErrRateSave","strTemplatePath="+strTemplatePath);
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
      loggerDebug("NewPolInputErrRateSave","strVFFileName="+strVFFileName);
      loggerDebug("NewPolInputErrRateSave","strRealPath="+strRealPath);
      loggerDebug("NewPolInputErrRateSave","strVFPathName="+strVFPathName);
          
      AccessVtsFile.saveToFile(dataStream, strVFPathName);
      loggerDebug("NewPolInputErrRateSave","==> Write VTS file to disk ");

	    session.putValue("RealPath", strVFPathName);
	    loggerDebug("NewPolInputErrRateSave","put session value");
	        
			session.putValue("ManageCom",tManageCom);
			session.putValue("StartDate",tStartDate );
			session.putValue("EndDate",tEndDate);
			//session.putValue("PrintDate",tPrintDate);
			session.putValue("PrintStream", txmlExport.getInputStream());
			request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
			loggerDebug("NewPolInputErrRateSave","put session value");

	}
	else
	{
    	FlagStr = "Fail";

%>
<html>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">	
	alert("<%=Content%>");
	top.close();
	
</script>
</html>
<%
  }

%>
