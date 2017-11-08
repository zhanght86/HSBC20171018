<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDContDefiEntrySave.jsp
//程序功能：契约信息定义入口
//创建日期：2009-3-13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.proddef.*"%>
 <%@page import="com.sinosoft.service.*" %>  
<%
 //接收信息，并作校验处理。
 //输入参数
System.out.println("------------into PDContDefiEntrySave.jsp-------------------");

// PDContDefiEntryBL pDContDefiEntryBL = new PDContDefiEntryBL();
 //PDPrepAndDealWFBL tPDPrepAndDealWFBL = new PDPrepAndDealWFBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 operator = request.getParameter("operator");
 
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  
  transferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
  transferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
  transferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
  transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
  transferData.setNameAndValue("RequDate",request.getParameter("RequDate"));
  transferData.setNameAndValue("Operator",tG.Operator);

  transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
  

  
  if(operator.equals("simplecont"))
  {
	  transferData.setNameAndValue("SimpleContPara", request.getParameter("SimpleContPara"));
  }
  
  tVData.add(transferData);
  //tPDPrepAndDealWFBL.submitData(tVData,operator);
  String busiName="PDPrepAndDealWFBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " "+"处理成功!"+" ";
  	FlagStr = "Success";
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  /*
 }
 catch(Exception ex)
 {
  Content = "保存失败，原因是:" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = tPDPrepAndDealWFBL.mErrors;
  if (!tError.needDealError())
  {                          
   RiskState.setState(request.getParameter("RiskCode"),"监管信息设定->保监会报表的产品分类","1");
   Content = " 保存成功! ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " 保存失败，原因是:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }*/

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

