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
		loggerDebug("PRnewApplyPrintSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";

	String PrtSeq=request.getParameter("PrtSeq");	
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	loggerDebug("PRnewApplyPrintSave",PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
       
    //PRnewNoticePrintUI tPRnewNoticePrintUI = new PRnewNoticePrintUI();
	XmlExport txmlExport = new XmlExport();    
    /*if(!tPRnewNoticePrintUI.submitData(tVData,"PRINT"))
    {
       operFlag=false;
       Content=tPRnewNoticePrintUI.mErrors.getFirstError().toString();                 
    }*/
    String busiName="PRnewNoticePrintUI";
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
	session.putValue("PrintNo",PrtSeq );
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("PRnewApplyPrintSave","put session value");
	response.sendRedirect("GetF1Print.jsp");
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
