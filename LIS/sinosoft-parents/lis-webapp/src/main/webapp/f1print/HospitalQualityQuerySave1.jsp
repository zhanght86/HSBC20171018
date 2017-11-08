<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
  loggerDebug("HospitalQualityQuerySave1","****开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  try
  {
    String mOperate = "PRINT";  //传入后台的操作类型
    //初始化全局变量，从前台承接数据
    String StartDate=request.getParameter("StartDate");             //开始日期
    String EndDate=request.getParameter("EndDate");                 //结束日期
    String AppSalechnl=request.getParameter("AppSalechnl");                 //销售渠道
    String HQReportType = request.getParameter("HQReportType");         //统计报表类型
    
    loggerDebug("HospitalQualityQuerySave1","****开始日期: "+StartDate); 
    loggerDebug("HospitalQualityQuerySave1","****结束日期: "+EndDate);   
    loggerDebug("HospitalQualityQuerySave1","****统计报表类型: "+HQReportType);          
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    HospitalQualityQuery1UI tHospitalQualityQuery1UI = new HospitalQualityQuery1UI();
    
    VData tVData = new VData();
    VData mResult = new VData();

    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
    tVData.addElement(AppSalechnl);
    tVData.addElement(HQReportType);
    tVData.addElement(tG);
    loggerDebug("HospitalQualityQuerySave1","Start PolAppStatUI...");      
    if(!tHospitalQualityQuery1UI.submitData(tVData,mOperate))
    {
      FlagStr="Fail";
      Content = " 处理失败，原因是:" +tHospitalQualityQuery1UI.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tHospitalQualityQuery1UI.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      loggerDebug("HospitalQualityQuerySave1","null");
      tError = tHospitalQualityQuery1UI.mErrors;
      Content = "打印失败,原因是没有需要打印的数据信息！";
      FlagStr = "Fail";
    }
    else
    {
  	   session.putValue("PrintStream", txmlExport.getInputStream());
  	   loggerDebug("HospitalQualityQuerySave1","put session value");
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
