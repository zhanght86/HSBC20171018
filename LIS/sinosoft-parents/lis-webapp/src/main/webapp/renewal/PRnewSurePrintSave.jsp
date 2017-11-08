<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
		loggerDebug("PRnewSurePrintSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";
    
    String cOperate = request.getParameter("fmtransact");
    if(cOperate.equals("PRINT"))
    {
        String PrtSeq=request.getParameter("PrtSeq");	
        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(PrtSeq);
		loggerDebug("PRnewSurePrintSave",PrtSeq);
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput)session.getValue("GI");

		VData tVData = new VData();
		VData mResult = new VData();
		CErrors mErrors = new CErrors();
    	tVData.addElement(tLOPRTManagerSchema);
    	tVData.addElement(tG);
       
    	PRnewSurePrintUI tPRnewSurePrintUI = new PRnewSurePrintUI();
		XmlExport txmlExport = new XmlExport();    
    	if(!tPRnewSurePrintUI.submitData(tVData,"PRINT"))
   		 {
     		operFlag=false;
   		    Content=tPRnewSurePrintUI.mErrors.getFirstError().toString();                 
    	 }
    	else
   		 {    
	 	 	mResult = tPRnewSurePrintUI.getResult();			
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
			loggerDebug("PRnewSurePrintSave","put session value");
			response.sendRedirect("GetF1Print.jsp");
		}
		else
		{
    		FlagStr = "Fail";

%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
<%
  		}
    }
    else if(cOperate.equals("PRINTBATCH"))
    {
    	String Ip = request.getRemoteAddr();
		loggerDebug("PRnewSurePrintSave","$$$$$$$$$$IP: "+Ip);
		String Path= application.getRealPath("sys")+File.separator;
  		String FileName = Path+"AppConfig.properties";
  		loggerDebug("PRnewSurePrintSave","$$$$$$$Config File: "+FileName);
    	String Sql = request.getParameter("Sql");
    	loggerDebug("PRnewSurePrintSave","$$$$$$$SQL: "+Sql);
    	GlobalInput tG = new GlobalInput();
		tG = (GlobalInput)session.getValue("GI");
		VData tVData = new VData();
		PRnewSurePrintUI tPRnewSurePrintUI = new PRnewSurePrintUI();
		
		try
		{
			tVData.addElement(tG);
   			tVData.addElement(Sql);
   			tVData.addElement(Ip);
   			tVData.addElement(FileName); 		
   			tPRnewSurePrintUI.submitData(tVData,"PRINTBATCH");
   		}
   		catch(Exception ex)
   		{
   			Content = "打印失败，原因是:" + ex.toString();
    		FlagStr = "Fail";
    	}
    	CErrors tError = tPRnewSurePrintUI.mErrors;
    	if (!tError.needDealError())
    	{
    		Content = " 保存成功";
    		FlagStr = "Succ";
    	}
    	else
		{
    		Content = " 保存失败，原因是:" + tError.getFirstError();
    		FlagStr = "Fail";
    	}
    }

%>
<html>
  <script language="javascript">
  parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
  </script>
</html>
