<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRequRiskSave.jsp
//程序功能：产品申请与查询
//创建日期：2009-3-12
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.proddef.*"%>
  
<%
 //接收信息，并作校验处理。
 //输入参数
 session.setAttribute("IsReadOnly", "0");
 ProdDefWorkFlowBL tProdDefWorkFlowBL= new ProdDefWorkFlowBL();

 String operator = "Start";
 
 CErrors tError = new CErrors();
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String transact = "";
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 TransferData trans = new TransferData();
 trans.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
 trans.setNameAndValue("RequDate",request.getParameter("RequDate"));
 trans.setNameAndValue("Operator",tG.Operator);
 System.out.println("here");
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(trans);
  tProdDefWorkFlowBL.submitData(tVData,operator);
  
	//初始化页面展示
	new RiskState().init(request.getParameter("RiskCode"));
	System.out.println("初始化成功");
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr.equals(""))
 {
  tError = tProdDefWorkFlowBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //添加各种预处理
%>                      
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

