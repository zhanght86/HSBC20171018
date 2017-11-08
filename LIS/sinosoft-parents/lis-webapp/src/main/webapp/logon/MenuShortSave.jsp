<%
//程序名称：MenuShortSave.jsp
//程序功能：快捷菜单修改存储页面
//创建日期：2002-09-23 08:08:52
//创建人  ：胡  博
//更新记录：  更新人    更新日期     更新原因/内容
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
  //输出参数
  CErrors tError = null;
  
  //执行插入
  String transact = "";           
  String FlagStr = "";
  String Content = "";
  int MAXMENUSHORTNUM = Integer.parseInt(request.getParameter("MenuNum"));
  int i;

  //获取操作员编码
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
  String strOperator = tGI.Operator;
  
  //现只有插入操作
  transact = "insert";
  
  loggerDebug("MenuShortSave","\n---Start Save MenuShort---");
  loggerDebug("MenuShortSave","---MenuNum: " + MAXMENUSHORTNUM + " ---");
  
  LDMenuShortSchema tLDMenuShortSchema   = new LDMenuShortSchema();
  LDMenuShortUI tLDMenuShortUI   = new LDMenuShortUI();
  
  // 准备传输数据 VData
  tLDMenuShortSchema.setOperator(strOperator);
  VData tVData = new VData();
  tVData.addElement(tLDMenuShortSchema);
    
  //向数据库提交数据
  try {
    //先删除全部该操作员的快捷菜单项记录，再插入这次的选择
    tLDMenuShortUI.deleteData(tVData, transact);  //delete 删除纪录       
   
    for (i=0; i<MAXMENUSHORTNUM; i++) {
      //loggerDebug("MenuShortSave","selMenuName:" + request.getParameter("selMenuName" + i));
      String[] MenuShort = StrSplit(request.getParameter("selMenuName" + i), "|");

      //判断是否对菜单项进行选择
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
    Content = transact+"失败，原因是:" + ex.toString();
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
快捷菜单修改提交成功,重新登陆后生效！
</html>
