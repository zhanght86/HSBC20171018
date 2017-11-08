<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
		loggerDebug("RReportScorePrint","--------------------start------------------");
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";
	String tCustomerNo=request.getParameter("CustomerNo");
	String tContNo	 = request.getParameter("PrtNo");
	loggerDebug("RReportScorePrint","合同号==="+tContNo);
	loggerDebug("RReportScorePrint","CustomerNo=="+tCustomerNo);
	LCScoreRReportSchema tLCScoreRReportSchema = new LCScoreRReportSchema();
	tLCScoreRReportSchema.setContNo(tContNo );
	tLCScoreRReportSchema.setCustomerNo(tCustomerNo);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    tVData.addElement(tLCScoreRReportSchema);
    tVData.addElement(tG);
       
//    RReportScorePrintUI tRReportScorePrintUI = new RReportScorePrintUI();
	XmlExport txmlExport = new XmlExport();    
//    if(!tRReportScorePrintUI.submitData(tVData,"PRINT"))
//    {
//       operFlag=false;
//       Content=tRReportScorePrintUI.mErrors.getFirstError().toString();                 
//    }
String busiName="RReportScorePrintUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"PRINT",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = tBusinessDelegate.getCErrors().getFirstError().toString();
		//FlagStr = "Fail";
		operFlag=false;
	}
	else
	{
		//Content = "保存失败";
		//FlagStr = "Fail";	
		operFlag=false;			
	}
}

    else
    {    
	  //mResult = tRReportScorePrintUI.getResult();	
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
		session.putValue("PrintStream", txmlExport.getInputStream());
		loggerDebug("RReportScorePrint","put session value,ContNo:"+tContNo);
		loggerDebug("RReportScorePrint","put session value");
		request.getRequestDispatcher("./GetF1Print.jsp").forward(request,response);
		//response.sendRedirect("GetF1Print.jsp");
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
	
	//window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");	
	
</script>
</html>
<%
  }

%>
