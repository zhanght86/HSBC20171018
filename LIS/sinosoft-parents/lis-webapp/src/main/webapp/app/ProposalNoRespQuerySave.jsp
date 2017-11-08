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
		loggerDebug("ProposalNoRespQuerySave","start");
        CError cError = new CError( );
        boolean operFlag=true;
				String FlagStr = "";
				String Content = "";
   
		   String tManageCom = request.getParameter("ManageCom");
		   String tSaleChnl = request.getParameter("SaleChnl");
		   //String tPrtNo = request.getParameter("PrtNo");
		   String tInsuredName = request.getParameter("InsuredName");
		   String tRiskCode = request.getParameter("RiskCode");
		   String tAgentCode = request.getParameter("AgentCode");
		   String tStartDate = request.getParameter("StartDate");
		   String tEndDate = request.getParameter("EndDate");
	 
	 	   String tScanStartDate = request.getParameter("ScanStartDate");
	 	   String tScanEndDate = request.getParameter("ScanEndDate");
	 	   String tIssueStartDate = request.getParameter("IssueStartDate");
	 	   String tIssueEndDate = request.getParameter("IssueEndDate");
	 	   String tIssueNoticeType = request.getParameter("IssueNoticeType");
	 	   String tOperatePos = request.getParameter("OperatePos");
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
  tTransferData.setNameAndValue("SaleChnl",tSaleChnl);     
  //tTransferData.setNameAndValue("PrtNo",tPrtNo);     
  tTransferData.setNameAndValue("InsuredName",tInsuredName);     
  tTransferData.setNameAndValue("RiskCode",tRiskCode);     
  tTransferData.setNameAndValue("AgentCode",tAgentCode); 
  
  tTransferData.setNameAndValue("ScanStartDate",tScanStartDate);     
  //tTransferData.setNameAndValue("PrtNo",tPrtNo);     
  tTransferData.setNameAndValue("ScanEndDate",tScanStartDate);     
  tTransferData.setNameAndValue("IssueStartDate",tIssueStartDate);     
  tTransferData.setNameAndValue("IssueEndDate",tIssueEndDate); 
  tTransferData.setNameAndValue("IssueNoticeType",tIssueNoticeType); 
  tTransferData.setNameAndValue("OperatePos",tOperatePos);
  tVData.addElement(tTransferData);  


 

  XmlExport txmlExport = new XmlExport();

					

  //ProposalNoRespQueryUI tProposalNoRespQueryUI = new ProposalNoRespQueryUI();
  String busiName="f1printProposalNoRespQueryUI";
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
	
	   /* String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		  loggerDebug("ProposalNoRespQuerySave","strTemplatePath="+strTemplatePath);
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
      loggerDebug("ProposalNoRespQuerySave","strVFFileName="+strVFFileName);
      loggerDebug("ProposalNoRespQuerySave","strRealPath="+strRealPath);
      loggerDebug("ProposalNoRespQuerySave","strVFPathName="+strVFPathName);
          
      AccessVtsFile.saveToFile(dataStream, strVFPathName);
      loggerDebug("ProposalNoRespQuerySave","==> Write VTS file to disk ");
*/
	 //   session.putValue("RealPath", strVFPathName);
	    loggerDebug("ProposalNoRespQuerySave","put session value");
	        
			session.putValue("ManageCom",tManageCom);
			session.putValue("StartDate",tStartDate );
			session.putValue("EndDate",tEndDate);
			//session.putValue("PrintDate",tPrintDate);
			session.putValue("PrintStream", txmlExport.getInputStream());
			request.getRequestDispatcher("../f1print/GetF1Print.jsp").forward(request,response);
			loggerDebug("ProposalNoRespQuerySave","put session value");

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
