<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  loggerDebug("CertifyPrint","****��ʼִ�д�ӡ����");
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
      Content = " ����ʧ�ܣ�ԭ����:" +tCertifyBatchList.mErrors.getFirstError();
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
      Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
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
   String mDescType="��ӡ";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,"",busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"ʧ��";
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
		      Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
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
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
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
