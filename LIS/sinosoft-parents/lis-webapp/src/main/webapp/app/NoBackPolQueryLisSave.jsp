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
		loggerDebug("NoBackPolQueryLisSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
				String FlagStr = "";
				String Content = "";
   
		   String tManageCom = request.getParameter("ManageCom");
		   String tBPOID = request.getParameter("BPOID");
		   String tStartDate = request.getParameter("StartDate");
		   String tEndDate = request.getParameter("EndDate");
	 
	 
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();

  tVData.addElement(tG);
  
  //���ݷ�SCHEMA��Ϣ                                                              
  TransferData tTransferData = new TransferData(); 
  tTransferData.setNameAndValue("ManageCom",tManageCom);                                                                                                                    
  tTransferData.setNameAndValue("StartDate",tStartDate); 
  tTransferData.setNameAndValue("EndDate",tEndDate);     
  tTransferData.setNameAndValue("BPOID",tBPOID);     
  //tTransferData.setNameAndValue("PrintDate",tPrintDate);
  tVData.addElement(tTransferData);  

  loggerDebug("NoBackPolQueryLisSave","***************************");  
  loggerDebug("NoBackPolQueryLisSave","***************************");
  loggerDebug("NoBackPolQueryLisSave","***************************");
  loggerDebug("NoBackPolQueryLisSave","***************************");
  loggerDebug("NoBackPolQueryLisSave","StartDate="+tStartDate);
  loggerDebug("NoBackPolQueryLisSave","EndDate="+tEndDate);
  loggerDebug("NoBackPolQueryLisSave","ManageCom="+tManageCom);
  loggerDebug("NoBackPolQueryLisSave","BPOID="+tBPOID);
  //loggerDebug("NoBackPolQueryLisSave","PrintDate="+tPrintDate);
  loggerDebug("NoBackPolQueryLisSave","***************************");  
  loggerDebug("NoBackPolQueryLisSave","***************************");
  loggerDebug("NoBackPolQueryLisSave","***************************");
  loggerDebug("NoBackPolQueryLisSave","***************************");
  loggerDebug("NoBackPolQueryLisSave","***************************");  

  XmlExport txmlExport = new XmlExport();

					

 // NoBackPolQueryLisUI tNoBackPolQueryLisUI = new NoBackPolQueryLisUI();
   String busiName="f1printNoBackPolQueryLisUI";
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
  		Content="û�еõ�Ҫ��ʾ�������ļ�";
  	}
  
  }


	
	if (operFlag==true)
	{
	
	   
	    loggerDebug("NoBackPolQueryLisSave","put session value");
	        
			session.putValue("ManageCom",tManageCom);
			session.putValue("StartDate",tStartDate );
			session.putValue("EndDate",tEndDate);
			//session.putValue("PrintDate",tPrintDate);
			session.putValue("PrintStream", txmlExport.getInputStream());
			request.getRequestDispatcher("../f1print/GetF1Print.jsp").forward(request,response);
			loggerDebug("NoBackPolQueryLisSave","put session value");

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
