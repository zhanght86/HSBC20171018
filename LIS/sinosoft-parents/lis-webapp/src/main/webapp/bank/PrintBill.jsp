<%@page contentType="text/html;charset=GBK" %>
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
loggerDebug("PrintBill.jsp","��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";

  	String strBillNo = request.getParameter("BillNo");
  	String selBankCode = request.getParameter("selBankCode");
  	String strTFFlag = request.getParameter("TFFlag");
  	String strSXFlag = request.getParameter("SXFlag");
  	String strStation = request.getParameter("Station");
  	
  	PrintBillUI tPrintBillUI = new PrintBillUI();
  	loggerDebug("PrintBill.jsp","Ҫ��ӡ�����������ǣ�������"+strBillNo);
		loggerDebug("PrintBill.jsp","��ȷ����ı�־�ǣ�������"+strTFFlag);
		loggerDebug("PrintBill.jsp","�������ڵı�־�ǣ�������"+strSXFlag);
		loggerDebug("PrintBill.jsp","���б����ǣ�������"+selBankCode);

  	
  	//����һ��ȫ�ֱ����洢�ⰸ��

  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.addElement(strBillNo);
    tVData.addElement(strTFFlag);
    tVData.addElement(strSXFlag);
    tVData.addElement(selBankCode);
    tVData.addElement(strStation);
    
    //����������ӡ����
    tPrintBillUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tPrintBillUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("PrintBill.jsp","null");
     tError = tPrintBillUI.mErrors;
    Content = "��ӡʧ��,ԭ���ǣ���"+tError.getFirstError();
   
    FlagStr = "Fail";
    
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	loggerDebug("PrintBill.jsp","put session value");
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