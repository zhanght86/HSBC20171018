<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ����󱣷����л��˳ɹ��嵥֪ͨ��
//�����ܣ�
//������  ��������
//�������ڣ�2004-5-28
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
  System.out.println("��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
	String tStartDate = request.getParameter("StartDate"); 	
	String tEndDate = request.getParameter("EndDate"); 	
	String tStation = request.getParameter("Station"); 	
	
	QueryPolPauseUI tQueryPolPauseUI = new QueryPolPauseUI();
  System.out.println("��ʼ��ӡ���б���Ч����ֹ��Ϣ��")	;
  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.addElement(tStartDate);
    tVData.addElement(tEndDate);
    tVData.addElement(tStation);
    
    tVData.addElement(tG);
    //����������ӡ����
    tQueryPolPauseUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tQueryPolPauseUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    System.out.println("null");
    tError = tQueryPolPauseUI.mErrors;
    Content = "��ӡʧ��,ԭ����û��Ҫ��ӡ������";
    FlagStr = "Fail";
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	System.out.println("put session value");
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