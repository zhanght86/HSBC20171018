<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLClaimScanInputSave.jsp
//程序功能：
//创建日期：2005-08-27 15:12:33
//创建人  ：zw创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //接收信息，并作校验处理。
  //输入参数
  Es_IssueDocSchema tEs_IssueDocSchema   = new Es_IssueDocSchema();
  LLClaimScanInputUI tLLClaimScanInputUI   = new LLClaimScanInputUI();

  //输出参数
  CErrors tError = null;
  String tOperate = "INSERT";
  tOperate = tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = " ";
  
  GlobalInput tG = new GlobalInput();
    //tG.Operator = "Admin";
    //tG.ComCode  = "001";
  //session.putValue("GI",tG);
  tG=(GlobalInput)session.getValue("GI");

  tEs_IssueDocSchema.setBussNo(request.getParameter("BussNo"));
  tEs_IssueDocSchema.setSubType(request.getParameter("SubType"));
  tEs_IssueDocSchema.setIssueDesc(request.getParameter("IssueDesc"));
  tEs_IssueDocSchema.setBussNoType(request.getParameter("BussNoType"));
  tEs_IssueDocSchema.setBussType(request.getParameter("BussType"));
  tEs_IssueDocSchema.setStatus(request.getParameter("Status"));
  //tLATrainSchema.setAClass(request.getParameter("AClass"));
  //tLATrainSchema.setTrainUnit(request.getParameter("TrainUnit"));
  //tLATrainSchema.setTrainName(request.getParameter("TrainName"));
 // tLATrainSchema.setCharger(request.getParameter("Charger"));
 // tLATrainSchema.setResult(request.getParameter("Result"));
 // tLATrainSchema.setResultLevel(request.getParameter("ResultLevel"));
 // tLATrainSchema.setTrainPassFlag(request.getParameter("TrainPassFlag"));
 // tLATrainSchema.setTrainStart(request.getParameter("TrainStart"));
 // tLATrainSchema.setTrainEnd(request.getParameter("TrainEnd"));
 // tLATrainSchema.setDoneDate(request.getParameter("DoneDate"));
 // tLATrainSchema.setNoti(request.getParameter("Noti"));
 // tLATrainSchema.setDoneFlag(request.getParameter("DoneFlag"));
//  tLATrainSchema.setOperator(tG.Operator);


  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
  tVData.addElement(tEs_IssueDocSchema);
  tVData.add(tG);
  try
  {
    tLLClaimScanInputUI.submitData(tVData,tOperate);
  }
  catch(Exception ex)
  {
    Content = "操作失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    tError = tLLClaimScanInputUI.mErrors;
    if (!tError.needDealError())
    {                          
      Content = "操作成功! ";
      FlagStr = "Succ";
    }
    else                                                                           
    {
      Content = "操作失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
  
  //添加各种预处理

%>                      
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

