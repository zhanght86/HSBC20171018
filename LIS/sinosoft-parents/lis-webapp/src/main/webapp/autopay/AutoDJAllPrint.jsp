<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�AutoDJAllPrint.jsp
//�����ܣ�ʵ���Զ��潻������ӡ�����嵥�Ĵ�ӡ����
//������  ��������
//�������ڣ�2004-9-15
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.autopay.*"%>
<%
  loggerDebug("AutoDJAllPrint","��ʼִ��AutoDJAllPrint.jsp");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  String strStartDate = request.getParameter("StartDate");
  String strEndDate = request.getParameter("EndDate");
  String strManageCom = request.getParameter("Station");
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  PrintAutoDJAllUI tPrintAutoDJAllUI = new PrintAutoDJAllUI();

  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.clear();
    tVData.addElement(strStartDate);
    tVData.addElement(strEndDate);
    tVData.addElement(strManageCom);
    tVData.addElement(tG);
    tPrintAutoDJAllUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  mResult = tPrintAutoDJAllUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("AutoDJAllPrint","null");
     tError = tPrintAutoDJAllUI.mErrors;
    Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
    FlagStr = "Fail";
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	loggerDebug("AutoDJAllPrint","put session value");
  	response.sendRedirect("../f1print/GetF1Print.jsp");
  }
  %>
  <html>
  <script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>