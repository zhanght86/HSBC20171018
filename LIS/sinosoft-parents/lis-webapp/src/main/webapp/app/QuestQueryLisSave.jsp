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
		loggerDebug("QuestQueryLisSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
				String FlagStr = "";
				String Content = "";
   
		   String tStartDate = request.getParameter("StartDate");
		   String tEndDate = request.getParameter("EndDate");
		   String tPolStartDate = request.getParameter("PolStartDate");
		   String tPolEndDate = request.getParameter("PolEndDate");
		   String tPrtNo = request.getParameter("PrtNo");
		   String tOperatePos =request.getParameter("OperatePos");
		   String tBackObj = request.getParameter("BackObj");
		   String tOperator = request.getParameter("Operator");
		   String tIfReply = request.getParameter("IfReply");
		   String tIssueType = request.getParameter("IssueType");
		   String tIsueManageCom = request.getParameter("IsueManageCom");
		   //根据通知书号查询
		   String tPrtSeq = request.getParameter("SerialNo");
		   String tPrintType = request.getParameter("PrintType");
		   String tErrorFlag = request.getParameter("ErrorFlag");
	 
	 
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();

  tVData.addElement(tG);
  
  //传递非SCHEMA信息                                                              
  TransferData tTransferData = new TransferData(); 
  tTransferData.setNameAndValue("ManageCom",tIsueManageCom);                                                                                                                    
  tTransferData.setNameAndValue("StartDate",tStartDate); 
  tTransferData.setNameAndValue("EndDate",tEndDate);     
  tTransferData.setNameAndValue("PolStartDate",tPolStartDate); 
  tTransferData.setNameAndValue("PolEndDate",tPolEndDate);     
  tTransferData.setNameAndValue("OperatePos",tOperatePos);     
  tTransferData.setNameAndValue("PrtNo",tPrtNo);     
  tTransferData.setNameAndValue("BackObj",tBackObj);     
  tTransferData.setNameAndValue("BackOperator",tOperator);     
  tTransferData.setNameAndValue("IssueType",tIssueType);     
  tTransferData.setNameAndValue("IfReply",tIfReply);     
  tTransferData.setNameAndValue("PrtSeq",tPrtSeq);     
  tTransferData.setNameAndValue("PrintType",tPrintType);     
  tTransferData.setNameAndValue("ErrorFlag",tErrorFlag);     
  //tTransferData.setNameAndValue("PrintDate",tPrintDate);
  tVData.addElement(tTransferData);  

  loggerDebug("QuestQueryLisSave","***************************");  
  loggerDebug("QuestQueryLisSave","***************************");
  loggerDebug("QuestQueryLisSave","***************************");
  loggerDebug("QuestQueryLisSave","***************************");
  loggerDebug("QuestQueryLisSave","StartDate="+tStartDate);
  loggerDebug("QuestQueryLisSave","EndDate="+tEndDate);
  loggerDebug("QuestQueryLisSave","PolStartDate="+tPolStartDate);
  loggerDebug("QuestQueryLisSave","PolEndDate="+tPolEndDate);
  loggerDebug("QuestQueryLisSave","ManageCom="+tIsueManageCom);
  loggerDebug("QuestQueryLisSave","tSaleChnl="+tOperatePos);
  loggerDebug("QuestQueryLisSave","tPrtNo="+tPrtNo);
  loggerDebug("QuestQueryLisSave","tInsuredName="+tBackObj);
  loggerDebug("QuestQueryLisSave","tRiskCode="+tOperator);
  loggerDebug("QuestQueryLisSave","tAgentCode="+tIssueType);
  loggerDebug("QuestQueryLisSave","tAgentCode="+tIfReply);
  loggerDebug("QuestQueryLisSave","tPrtSeq="+tPrtSeq);
  //loggerDebug("QuestQueryLisSave","PrintDate="+tPrintDate);
  loggerDebug("QuestQueryLisSave","***************************");  
  loggerDebug("QuestQueryLisSave","***************************");
  loggerDebug("QuestQueryLisSave","***************************");
  loggerDebug("QuestQueryLisSave","***************************");
  loggerDebug("QuestQueryLisSave","***************************");  

  XmlExport txmlExport = new XmlExport();

					

   //QuestQueryLisUI tQuestQueryLisUI = new QuestQueryLisUI();
   String busiName="f1printQuestQueryLisUI";
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
	
	    loggerDebug("QuestQueryLisSave","put session value");
	        
			session.putValue("ManageCom",tIsueManageCom);
			session.putValue("StartDate",tStartDate );
			session.putValue("EndDate",tEndDate);
			//session.putValue("PrintDate",tPrintDate);
			session.putValue("PrintStream", txmlExport.getInputStream());
			request.getRequestDispatcher("../f1print/GetF1Print.jsp").forward(request,response);
			loggerDebug("QuestQueryLisSave","put session value");

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
