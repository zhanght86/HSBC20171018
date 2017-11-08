<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");

  CError cError = new CError( );
  VData mResult = new VData();

  String FlagStr = "";
  String Content = "";
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("StartDate",request.getParameter("StartDate"));
  tTransferData.setNameAndValue("EndDate",request.getParameter("EndDate"));
  tTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom"));

  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);

//   BQBankTransferFailureStatBL tBQBankTransferFailureStatBL = new BQBankTransferFailureStatBL();
     String busiName="tBQBankTransferFailureStatBL";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//  if(!tBQBankTransferFailureStatBL.submitData(tVData,"PRINT"))
    if(!tBusinessDelegate.submitData(tVData,"PRINT",busiName))
  {
    FlagStr = "Fail";
//    if( tBQBankTransferFailureStatBL.mErrors.needDealError() )
    //if( tBusinessDelegate.getCErrors())
    //{
      Content = tBusinessDelegate.getCErrors().getContent();
    //}
    //else 
    //{
    //  Content = "GTMarginStatUI发生错误，但是没有提供详细的出错信息";
    //}
%>
<script language="javascript">
	 alert('<%= Content %>');
   window.opener = null;
   window.close();
</script>
<%
	}
	else
	{
	  mResult = tBusinessDelegate.getResult();
	  XmlExport txmlExport = (XmlExport)mResult.getObjectByObjectName("XmlExport",0);

	  if (txmlExport==null) {
	    loggerDebug("BQBankTransferFailureStatF1PSave","null");
	  }
	  session.setAttribute("PrintStream", txmlExport.getInputStream());
	  loggerDebug("BQBankTransferFailureStatF1PSave","put session value");
	  response.sendRedirect("./GetF1Print.jsp");
  }
%>
