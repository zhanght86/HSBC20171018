<%
//�������ƣ�MenuShortSave.jsp
//�����ܣ���ݲ˵��޸Ĵ洢ҳ��
//�������ڣ�2002-09-23 08:08:52
//������  ����  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.logon.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="StrSplit.jsp"%>
  
<%
  //�������
  CErrors tError = null;
  
  //ִ�в���
  String transact = "";           
  String FlagStr = "";
  String Content = "";
  int MAXMENUSHORTNUM = Integer.parseInt(request.getParameter("MenuNum"));
  int i;

  //��ȡ����Ա����
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
  String strOperator = tGI.Operator;
  
  //��ֻ�в������
  transact = "insert";
  
  loggerDebug("MenuShortSave","\n---Start Save MenuShort---");
  loggerDebug("MenuShortSave","---MenuNum: " + MAXMENUSHORTNUM + " ---");
  
  LDMenuShortSchema tLDMenuShortSchema   = new LDMenuShortSchema();
  LDMenuShortUI tLDMenuShortUI   = new LDMenuShortUI();
  
  // ׼���������� VData
  tLDMenuShortSchema.setOperator(strOperator);
  VData tVData = new VData();
  tVData.addElement(tLDMenuShortSchema);
    
  //�����ݿ��ύ����
  try {
    //��ɾ��ȫ���ò���Ա�Ŀ�ݲ˵����¼���ٲ�����ε�ѡ��
    tLDMenuShortUI.deleteData(tVData, transact);  //delete ɾ����¼       
   
    for (i=0; i<MAXMENUSHORTNUM; i++) {
      //loggerDebug("MenuShortSave","selMenuName:" + request.getParameter("selMenuName" + i));
      String[] MenuShort = StrSplit(request.getParameter("selMenuName" + i), "|");

      //�ж��Ƿ�Բ˵������ѡ��
      if (MenuShort[0].compareTo("0") != 0) {
        tVData.clear();  
        tLDMenuShortSchema.setNodeCode(MenuShort[0]);
        tLDMenuShortSchema.setNodeName(MenuShort[1]);
        tLDMenuShortSchema.setRunScript(MenuShort[2]);
        tLDMenuShortSchema.setOperator(strOperator);
        tLDMenuShortSchema.setNodeOrder(i);
       	     	 
        tVData.addElement(tLDMenuShortSchema);     
        tLDMenuShortUI.submitData(tVData, transact);
      }
      else {
        tVData.clear();  
        tLDMenuShortSchema.setNodeCode("0");
        tLDMenuShortSchema.setNodeName("");
        tLDMenuShortSchema.setRunScript("");
        tLDMenuShortSchema.setOperator(strOperator);
        tLDMenuShortSchema.setNodeOrder(i);
       	     	 
        tVData.addElement(tLDMenuShortSchema);     
        tLDMenuShortUI.submitData(tVData, transact);
      }
  
    }      
  }
  catch(Exception ex) {
    Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }   
  
  loggerDebug("MenuShortSave","---End Save MenuShort---");
%>
                                      
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<link rel='stylesheet' type='text/css' href='../common/css/Project.css'>
</head>
��ݲ˵��޸��ύ�ɹ�,���µ�½����Ч��
</html>
