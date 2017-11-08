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
		loggerDebug("BPOWorkAmountSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
				String FlagStr = "";
				String Content = "";
   
		   String tManageCom = request.getParameter("ManageCom");
		   String tBPOID = request.getParameter("BPOID");
		   String tStartDate = request.getParameter("StartDate");
		   String tEndDate = request.getParameter("EndDate");
		   String tRiskCode =request.getParameter("RiskCode");
	 
	 
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
  tTransferData.setNameAndValue("BPOID",tBPOID);     
  tTransferData.setNameAndValue("RiskCode",tRiskCode);   
  tTransferData.setNameAndValue("AppCode","app3");     
  //tTransferData.setNameAndValue("PrintDate",tPrintDate);
  tVData.addElement(tTransferData);  

  loggerDebug("BPOWorkAmountSave","***************************");  
  loggerDebug("BPOWorkAmountSave","***************************");
  loggerDebug("BPOWorkAmountSave","***************************");
  loggerDebug("BPOWorkAmountSave","***************************");
  loggerDebug("BPOWorkAmountSave","StartDate="+tStartDate);
  loggerDebug("BPOWorkAmountSave","EndDate="+tEndDate);
  loggerDebug("BPOWorkAmountSave","ManageCom="+tManageCom);
  loggerDebug("BPOWorkAmountSave","BPOID="+tBPOID);
  loggerDebug("BPOWorkAmountSave","RiskCode="+tRiskCode);
  //loggerDebug("BPOWorkAmountSave","PrintDate="+tPrintDate);
  loggerDebug("BPOWorkAmountSave","***************************");  
  loggerDebug("BPOWorkAmountSave","***************************");
  loggerDebug("BPOWorkAmountSave","***************************");
  loggerDebug("BPOWorkAmountSave","***************************");
  loggerDebug("BPOWorkAmountSave","***************************");  

  XmlExport txmlExport = new XmlExport();

					
  String busiName="f1printAppCheckUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 // AppCheckUI tAppCheckUI = new AppCheckUI();

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
	
	   
	    loggerDebug("BPOWorkAmountSave","put session value");
	        
			session.putValue("ManageCom",tManageCom);
			session.putValue("StartDate",tStartDate );
			session.putValue("EndDate",tEndDate);
			//session.putValue("PrintDate",tPrintDate);
			session.putValue("PrintStream", txmlExport.getInputStream());
			request.getRequestDispatcher("../f1print/GetF1Print.jsp").forward(request,response);
			loggerDebug("BPOWorkAmountSave","put session value");

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
