<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�AutoDJPrint.jsp
//�����ܣ�ʵ���Զ��潻��ӡ�Ĺ���
//������  ��������
//�������ڣ�2004-5-20
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
  loggerDebug("AutoDJPrint","��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  String strEdorNo = request.getParameter("EdorNo");
  loggerDebug("AutoDJPrint","����֪ͨ�������"+strEdorNo);
  String strDate = request.getParameter("Date");
  loggerDebug("AutoDJPrint","���������"+strDate);
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  PrintAutoDJUI tPrintAutoDJUI = new PrintAutoDJUI();

  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.clear();
    tVData.addElement(strEdorNo);
    tVData.addElement(strDate);
    tVData.addElement(tG);
    tPrintAutoDJUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  mResult = tPrintAutoDJUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("AutoDJPrint","null");
     tError = tPrintAutoDJUI.mErrors;
    Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
    FlagStr = "Fail";
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	loggerDebug("AutoDJPrint","put session value");
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
