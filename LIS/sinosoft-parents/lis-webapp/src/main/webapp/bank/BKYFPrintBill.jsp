<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�PLPsqs.jsp--��ӡ�������������
//�����ܣ�
//������  ��������
//�������ڣ�2003-02-14
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
 <%@page import="com.sinosoft.lis.f1print.*"%>
<%
  loggerDebug("BKYFPrintBill","��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";

  	String strBillNo = request.getParameter("BillNo");
  	String strBankCode = request.getParameter("selBankCode");
  	String Station = request.getParameter("Station");
  	
  	PrintBillYFUI tPrintBillYFUI = new PrintBillYFUI();
  	loggerDebug("BKYFPrintBill","Ҫ��ӡ�����������ǣ�������"+strBillNo);
  	loggerDebug("BKYFPrintBill","Ҫ��ӡ�����д����ǣ�������"+strBankCode);
  	
  	//����һ��ȫ�ֱ����洢�ⰸ��

  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.addElement(strBillNo);
    tVData.addElement(strBankCode);
    tVData.addElement(Station);
    
    //����������ӡ����
    tPrintBillYFUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tPrintBillYFUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("BKYFPrintBill","null");
     tError = tPrintBillYFUI.mErrors;
    Content = "��ӡʧ��,ԭ���ǣ���"+tError.getFirstError();
   
    FlagStr = "Fail";
    
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	loggerDebug("BKYFPrintBill","put session value");
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
