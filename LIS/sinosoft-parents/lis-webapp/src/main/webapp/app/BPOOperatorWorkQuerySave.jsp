<%
//程序名称：BPOOperatorWorkQueryInput.jsp
//程序功能：录单外异常件工作量统计
//创建日期：2007-09-19 16:53
//创建人  ：JL
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");

  CError cError = new CError();
  VData mResult = new VData();

  String FlagStr = "";
  String Content = "";
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("StartDate",request.getParameter("StartDate"));
  tTransferData.setNameAndValue("EndDate",request.getParameter("EndDate"));

  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
  String busiName="f1printBPOOperatorWorkQueryUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
 // BPOOperatorWorkQueryUI tBPOOperatorWorkQueryUI = new BPOOperatorWorkQueryUI();
  if(!tBusinessDelegate.submitData(tVData,"PRINT",busiName))
  {
    FlagStr = "Fail";
    if( tBusinessDelegate.getCErrors().needDealError() )
    {
      Content = tBusinessDelegate.getCErrors().getFirstError();
    }
    else 
    {
      Content = "BPOOperatorWorkQueryUI发生错误，但是没有提供详细的出错信息";
    }
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
      loggerDebug("BPOOperatorWorkQuerySave","null");
    }
    session.setAttribute("PrintStream", txmlExport.getInputStream());
    loggerDebug("BPOOperatorWorkQuerySave","put session value");
    response.sendRedirect("../f1print/GetF1Print.jsp");
  }
%>
