<%
/**
* <p>Title: EasyScanӰ��ϵͳ</p>
* <p>Description: EasyScan�����Ļ�ȡɨ�����κ�</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: Sinosoft</p>
* @author wellhi
* @version 1.0
* @Date 2005-10-11
*/
%>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="java.io.*"%>

<%
loggerDebug("GetScanNo","-------------GetScanNo.jsp Begin---------------------");
//�������ݶ���
StringBuffer bufXML = new StringBuffer(1024);
String CON_QUERY_HIS_SCANNO="1";
try{
  VData nVData = new VData();
  nVData.setSize(10);
  GetScanNoUI tGetScanNoUI = new GetScanNoUI();
  String strOperate="";
  String strBussType="";
  GlobalInput nGlobalInput=new GlobalInput();
  //�����������Ͳ���Ա
  nGlobalInput.ManageCom=request.getParameter("ManageCom");
  nGlobalInput.Operator=request.getParameter("Operator");
  //������ʽ:0- ���������κ�;1- ��ѯ��ʷ���κ�
  strOperate=request.getParameter("Operate");
  strBussType=request.getParameter("BussType");
  nVData.setElementAt(nGlobalInput,0);
  nVData.setElementAt(strOperate,1);
  if (strOperate.equals(CON_QUERY_HIS_SCANNO)){
    nVData.setElementAt(request.getParameter("ScanNo"),2);
    nVData.setElementAt(request.getParameter("BussNo"),3);
    nVData.setElementAt(request.getParameter("BussType"),4);
    nVData.setElementAt(request.getParameter("SubType"),5);
    nVData.setElementAt(request.getParameter("StartDate"),6);
    nVData.setElementAt(request.getParameter("EndDate"),7);
  }
  nVData.setElementAt(strBussType,8); //by zxm 06.04.10���κ�����
  tGetScanNoUI.submitData(nVData,"");
  //��÷�������
  bufXML.append(tGetScanNoUI.getResult());
}
catch(Exception e){
  //��������쳣�������쳣��Ϣ
  String strErr = "GetScanNo.jsp--��ȡɨ�����κ�ʧ�ܣ�\n" + e.toString();

  bufXML.delete(0,bufXML.length());
  bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><RETURN>");
  bufXML.append("<NUMBER>-4500</NUMBER><MESSAGE>");
  bufXML.append(strErr);
  bufXML.append( "</MESSAGE></RETURN></DATA>");

  loggerDebug("GetScanNo",strErr);
}
finally{
  //�����������
  out.println("<IndexXML>" + bufXML.toString() + "</IndexXML>");
  loggerDebug("GetScanNo","-------------GetScanNo.jsp End---------------------");
}
%>
<HTML>
  <title>��ȡɨ�����κ�</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
