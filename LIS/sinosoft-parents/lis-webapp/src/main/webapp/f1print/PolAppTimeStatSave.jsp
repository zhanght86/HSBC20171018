<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PolAppStatSave.jsp
//程序功能：承保报表统计
//创建日期：2007-04-05 15:01
//创建人  ：Fuqx
%>
<!--用户校验类-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
  System.out.println("****开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  try
  {
    String mOperate = "PRINT";  //传入后台的操作类型
    //初始化全局变量，从前台承接数据
    String StartDate=request.getParameter("StartDate");             //开始日期
    String EndDate=request.getParameter("EndDate");                 //结束日期
    String ReportType = request.getParameter("AppReportType");         //统计报表类型
    String StartTime=request.getParameter("StartTime");             //开始时间
    String EndTime=request.getParameter("EndTime");                 //结束时间
    
    System.out.println("****开始日期: "+StartDate); 
    System.out.println("****结束日期: "+EndDate);
    System.out.println("****开始时间: "+StartTime); 
    System.out.println("****结束时间: "+EndTime); 
    System.out.println("****统计报表类型: "+ReportType); 
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    PolAppTimeStatUI tPolAppTimeStatUI = new PolAppTimeStatUI();
    
    VData tVData = new VData();
    VData mResult = new VData();

    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
    tVData.addElement(ReportType);
    tVData.addElement(StartTime);
    tVData.addElement(EndTime);
    tVData.addElement(tG);
    
    System.out.println("Start PolAppTimeStatUI...");      
    if(!tPolAppTimeStatUI.submitData(tVData,mOperate))
    {
      FlagStr="Fail";
      Content = " 处理失败，原因是:" +tPolAppTimeStatUI.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tPolAppTimeStatUI.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      System.out.println("null");
      tError = tPolAppTimeStatUI.mErrors;
      Content = "打印失败,原因是没有需要打印的数据信息！";
      FlagStr = "Fail";
    }
    else
    {
  	   session.putValue("PrintStream", txmlExport.getInputStream());
  	   System.out.println("put session value");
  	   response.sendRedirect("../f1print/GetF1Print.jsp");
    }
   }
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  %>
  <html>
  <%if("Fail".equals(FlagStr))
  {
  %>
  <script language="javascript">
	alert("<%=Content%>");    
	window.close();
   </script>
   <%
   }
   %>
</html>