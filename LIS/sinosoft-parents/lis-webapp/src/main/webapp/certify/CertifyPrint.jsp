<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  loggerDebug("CertifyPrint","****开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  try
  {           
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tStartDate = request.getParameter("StartDate");
    String tEndDate = request.getParameter("EndDate");
    loggerDebug("CertifyPrint","tStartDate="+tStartDate);
    loggerDebug("CertifyPrint","tEndDate="+tEndDate);
    //CertifyBatchList tCertifyBatchList = new CertifyBatchList();
    
    VData tVData = new VData();
    VData mResult = new VData();   

    tVData.addElement(tStartDate);
    tVData.addElement(tEndDate);
    tVData.addElement(tG);
    loggerDebug("CertifyPrint","Start CertifyBatchList...");      
    /*if(!tCertifyBatchList.submitData(tVData,""))
    {
      FlagStr="Fail";
      Content = " 处理失败，原因是:" +tCertifyBatchList.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tCertifyBatchList.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      loggerDebug("CertifyPrint","null");
      tError = tCertifyBatchList.mErrors;
      Content = "打印失败,原因是没有需要打印的数据信息！";
      FlagStr = "Fail";
    }
    else
    {
  	   session.putValue("PrintStream", txmlExport.getInputStream());
  	   loggerDebug("CertifyPrint","put session value");
  	   response.sendRedirect("../f1print/GetF1Print.jsp");
    }
   }*/
   String busiName="CertifyBatchList";
   String mDescType="打印";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,"",busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"失败";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
		  mResult = tBusinessDelegate.getResult();
		    XmlExport txmlExport = new XmlExport();
		    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		    if (txmlExport==null)
		    {
		      loggerDebug("CertifyPrint","null");
		      Content = "打印失败,原因是没有需要打印的数据信息！";
		      FlagStr = "Fail";
		    }
		    else
		    {
		  	   session.putValue("PrintStream", txmlExport.getInputStream());
		  	   loggerDebug("CertifyPrint","put session value");
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
