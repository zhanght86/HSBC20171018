<%
//�������ƣ�BPOOperatorWorkQueryInput.jsp
//�����ܣ�¼�����쳣��������ͳ��
//�������ڣ�2007-09-19 16:53
//������  ��JL
//���¼�¼��  ������    ��������     ����ԭ��/����
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
      Content = "BPOOperatorWorkQueryUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
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
