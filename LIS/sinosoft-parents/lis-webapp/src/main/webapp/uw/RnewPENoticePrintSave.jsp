<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
		loggerDebug("RnewPENoticePrintSave","--------------------start------------------");
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";
    String PrtSeq=request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContNo	 = request.getParameter("ContNo");
   String tPrtNo	 = request.getParameter("PrtNo");
   //添加一个数据检查的判断
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	loggerDebug("RnewPENoticePrintSave",PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
	  ExeSQL tExeSQL = new ExeSQL();
	  String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
      String strFilePath = tExeSQL.getOneValue(strSql);
      String strVFFileName = strFilePath + tG.Operator + "REPE_" + FileQueue.getFileName()+".vts";
      //获取存放临时文件的路径
      String strRealPath = application.getRealPath("/").replace('\\','/');
      String strVFPathName = strRealPath + strVFFileName;
      
      CombineVts tcombineVts = null;

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
       
   // RnewPENoticePrintUI tRnewPENoticePrintUI = new RnewPENoticePrintUI();
    	String busiName="RnewPENoticePrintUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	XmlExport txmlExport = new XmlExport(); 
	if(!tBusinessDelegate.submitData(tVData,"PRINT",busiName))
	{
		operFlag=false;
	    Content=tBusinessDelegate.getCErrors().getFirstError().toString(); 
	}else{
		 mResult = tBusinessDelegate.getResult();	
		 txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  if(txmlExport==null)
		  {
		   operFlag=false;
		   Content="没有得到要显示的数据文件";	  
		  }
	}	
	/*
    if(!tRnewPENoticePrintUI.submitData(tVData,"PRINT"))
    {
       operFlag=false;
       Content=tRnewPENoticePrintUI.mErrors.getFirstError().toString();                 
    }
    else
    {    
	  mResult = tRnewPENoticePrintUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="没有得到要显示的数据文件";	  
	  }
	}
	*/
	if (operFlag==true)
	{
		//合并VTS文件
        //String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
	    //tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	    //ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	    //tcombineVts.output(dataStream);
		
		//AccessVtsFile.saveToFile(dataStream,strVFPathName);
	    loggerDebug("RnewPENoticePrintSave","==> Write VTS file to disk ");
	    
	    
	session.putValue("PrintNo",PrtSeq );
	session.putValue("MissionID",tMissionID );
	session.putValue("SubMissionID",tSubMissionID );
	session.putValue("Code","43" );	//核保通知书类别
	session.putValue("ContNo",tContNo );
	session.putValue("PrtNo",tPrtNo );
	session.putValue("RealPath", strVFPathName);	
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("RnewPENoticePrintSave","put session value,ContNo:"+tContNo);
	loggerDebug("RnewPENoticePrintSave","put session value");
	//response.sendRedirect("../uw/GetF1Print.jsp");
	//request.getRequestDispatcher("../uw/GetF1PrintJ1.jsp").forward(request,response);
	request.getRequestDispatcher("../uw/GetF1Print.jsp").forward(request,response);
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
