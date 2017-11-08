<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//name : SRiskOutLisSave.jsp
//functon : Receive data and transfer data
//Creator  ：刘岩松
//Date ：2003-02-14
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.f1print.*"%>
 <%@page import="java.io.*"%>
<%
  loggerDebug("FaultyReportLisSave","开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  loggerDebug("FaultyReportLisSave","--------------------start------------------");
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
	String strTemplatePath=tPath;
	loggerDebug("FaultyReportLisSave","标准路径==="+tPath);
	
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
		
  	String strMngCom    = request.getParameter("ManageCom");
  	String mRiskCode    = request.getParameter("RiskCode");
  	String mSaleChnl    = request.getParameter("SaleChnl");
  	String mAgentCode    = request.getParameter("AgentCode");
  	String mOperatePos    = request.getParameter("OperatePos");
  	String mBackObj    = request.getParameter("BackObj");
  	String mStartTime    = request.getParameter("StartTime");
  	String mEndTime    = request.getParameter("EndTime");
  	String mIsRecord    = request.getParameter("IsRecord");
  	String strIssueDate = request.getParameter("IssueDate");
    String strSQL = "";
    String str = request.getParameter("StrSQL");

  	String strVFPathName = strTemplatePath+"FaultyReportLis.vts";
  	CombineVts tcombineVts = null;
  	FaultyReportLisUI tFaultyReportLisUI=new FaultyReportLisUI();
  	//loggerDebug("FaultyReportLisSave","管理机构是"+strMngCom);
  //	loggerDebug("FaultyReportLisSave","开始日期是"+strIssueDate);
   // loggerDebug("FaultyReportLisSave","查询语句为:"+strSQL); 
  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.addElement(strMngCom);
    tVData.addElement(strIssueDate); 
    tVData.addElement(strSQL);
    tVData.addElement(mRiskCode);
    tVData.addElement(mSaleChnl);
    tVData.addElement(mAgentCode);
    tVData.addElement(mOperatePos);
    tVData.addElement(mBackObj);
    tVData.addElement(mStartTime);
    tVData.addElement(mEndTime);
    tVData.addElement(mIsRecord);
	tVData.addElement(tG);
    tFaultyReportLisUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tFaultyReportLisUI.getResult();
  //进行打印连接
  
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  strVFPathName=(String)mResult.getObjectByObjectName("String",0);
  
  if (txmlExport==null)
  {
    loggerDebug("FaultyReportLisSave","null");
     tError = tFaultyReportLisUI.mErrors;
    Content = "打印失败,原因是＝＝"+tError.getFirstError();
    FlagStr = "Fail";
  }
  else
  {
  // tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
//
          ByteArrayOutputStream dataStream1 = new ByteArrayOutputStream();
     //     tcombineVts.output(dataStream1);

          //把dataStream存储到磁盘文件
       //   loggerDebug("FaultyReportLisSave","存储文件到"+strVFPathName);
     //    AccessVtsFile.saveToFile(dataStream1,strVFPathName);
     //     loggerDebug("FaultyReportLisSave","==> Write VTS file to disk ");

     
          session.putValue("RealPath", strVFPathName );
          
  	 //    loggerDebug("FaultyReportLisSave","put session value");
  	    //response.sendRedirect("../uw/GetF1Print.jsp");
  	    session.putValue("PrintStream", txmlExport.getInputStream());
  	    request.getRequestDispatcher("../f1print/GetF1Print.jsp").forward(request,response);
  	    	//response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
  }
  
  if( !Content.equals("") )
     {    
  %>
  <html>
  <script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
  }

%>
