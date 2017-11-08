<%
/**
* <p>Title: EasyScanӰ��ϵͳ</p>
* <p>Description: EasyScan��ѯ���ĵ������</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: Sinosoft</p>
* @author wellhi
* @version 1.0
* @Date 2005-10-21
*/
%>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
loggerDebug("QueryIssueDoc","-------------QueryIssueDoc.jsp Begin---------------------");
DiskFileUpload nDiskFileUpload = new DiskFileUpload();
// ��������ļ��ߴ磬������4*1024*1024=4MB
nDiskFileUpload.setSizeMax(4194304);
// ���û�������С��������4*1024=4kb
nDiskFileUpload.setSizeThreshold(4096);
// ������ʱĿ¼
//nDiskFileUpload.setRepositoryPath("D:\\lis_hx\\ui\\xerox\\EasyScan\\temp");
//�������ݶ���
StringBuffer bufXML = new StringBuffer(1024);
try{
  List nFileItems = nDiskFileUpload.parseRequest(request);
  // �õ����е��ļ�
  Iterator nIterator = nFileItems.iterator();
  Iterator nIterator1 = nFileItems.iterator();
  // ���δ���ÿһ���ļ�
  StringBuffer xmlBuffer=new StringBuffer(1024);
  String CON_KEY="IndexXML";
  String strKey=CON_KEY;
  int i=0;
  //�������ݵĲ���
  while(nIterator.hasNext()){
    FileItem nFileItem = (FileItem)nIterator.next();
    if(nFileItem.getFieldName().equalsIgnoreCase(strKey)){
      xmlBuffer.append(nFileItem.getString());
    }
    i++;
    strKey =CON_KEY + String.valueOf(i);
  }
  loggerDebug("QueryIssueDoc",xmlBuffer);

  //�ַ�������ת��ΪvData
  String strXML=xmlBuffer.toString();
  ParameterDataConvert convert = new ParameterDataConvert();
  VData nVData = new VData();
  convert.xMLToVData(strXML,nVData);
  GlobalInput tGI = new GlobalInput();
  String strOperator = request.getParameter("Operator");
  String strManageCom = request.getParameter("ManageCom");
  tGI.Operator = strOperator;
  tGI.ManageCom = strManageCom;
  //  nVData.add(tGI);
  nVData.setElementAt(tGI,5);

  //�ύUI����
  //VData mData = new VData();
  QueryIssueDocUI tQueryIssueDocUI   = new QueryIssueDocUI();
  tQueryIssueDocUI.submitData(nVData,"");

  //��÷�������
  VData resultData = new VData();
  resultData = tQueryIssueDocUI.getResult();

  //��÷�������,��nVDataת��Ϊ�ַ�������
  convert.vDataToXML(bufXML,resultData);
}
catch(Exception e){
  //��������쳣�������쳣��Ϣ
  String strErr = "QueryIssueDoc.jsp--��ѯ���ĵ������ʧ�ܣ�\n" + e.toString();

  bufXML.delete(0,bufXML.length());
  bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><RETURN>");
  bufXML.append("<NUMBER>-4500</NUMBER><MESSAGE>");
  bufXML.append(strErr);
  bufXML.append( "</MESSAGE></RETURN></DATA>");

  loggerDebug("QueryIssueDoc",strErr);
}
finally{
  //�����������
  out.println("<IndexXML>" + bufXML.toString() + "</IndexXML>");
  loggerDebug("QueryIssueDoc","-------------QueryIssueDoc.jsp End---------------------");
}
%>
<HTML>
  <title>��ѯ���ĵ������</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
