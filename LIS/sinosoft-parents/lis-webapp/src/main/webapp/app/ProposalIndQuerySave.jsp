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
		loggerDebug("ProposalIndQuerySave","start");
        CError cError = new CError( );
        boolean operFlag=true;
				String FlagStr = "";
				String Content = "";
   
		   String tManageCom = request.getParameter("ManageCom");
		   String tContNo = request.getParameter("ContNo");
		   String tPrtNo = request.getParameter("PrtNo");
		   String tInsuredName = request.getParameter("InsuredName");
		   String tInsuredNo = request.getParameter("InsuredNo");
		   String tAppntName = request.getParameter("AppntName");
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
  //tTransferData.setNameAndValue("SaleChnl",tSaleChnl);     
  tTransferData.setNameAndValue("PrtNo",tPrtNo); 
  tTransferData.setNameAndValue("ContNo",tContNo);        
  tTransferData.setNameAndValue("InsuredName",tInsuredName);
   tTransferData.setNameAndValue("InsuredNo",tInsuredNo);       
  tTransferData.setNameAndValue("AppntName",tAppntName);     

  tVData.addElement(tTransferData);  


  XmlExport txmlExport = new XmlExport();

					

   //ProposalIndQueryLisUI tProposalIndQueryLisUI = new ProposalIndQueryLisUI();
   String busiName="f1printProposalIndQueryLisUI";
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

	    loggerDebug("ProposalIndQuerySave","put session value");
	        
			//session.putValue("ManageCom",tManageCom);
			//session.putValue("StartDate",tStartDate );
			//session.putValue("EndDate",tEndDate);
			//session.putValue("PrintDate",tPrintDate);
			session.putValue("PrintStream", txmlExport.getInputStream());
			request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
			loggerDebug("ProposalIndQuerySave","put session value");

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
