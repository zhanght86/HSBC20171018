<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=UTF-8" %>

<%
//程序名称：PDCheckRuleSave.jsp
//程序功能：检验规则库
//创建日期：2009-3-17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 //接收信息，并作校验处理。
 //输入参数
 

 PDCheckRuleBL pDCheckRuleBL = new PDCheckRuleBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getValue("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");

 PD_CheckRule_LibSchema tPD_CheckRule_LibSchema = new PD_CheckRule_LibSchema();
 
 
 String type = request.getParameter("Type");
 String checkRuleCode = "";
 
 if(operator.equals("save"))
 {
 	PDGetMaxNo getMaxNo = new PDGetMaxNo();
 	String maxNo = getMaxNo.getMaxNo("CHECKRULE",type);
 	checkRuleCode = type + maxNo;
 }
 else
 {
	 checkRuleCode = request.getParameter("CheckRuleCode");
 }
 
 tPD_CheckRule_LibSchema.setCode(checkRuleCode);
 tPD_CheckRule_LibSchema.setType(type);
 tPD_CheckRule_LibSchema.setName(request.getParameter("CheckRuleName"));
 tPD_CheckRule_LibSchema.setAlgo(request.getParameter("Algo"));
 tPD_CheckRule_LibSchema.setDescription(request.getParameter("Description"));
 
 transferData.setNameAndValue("PD_CheckRule_LibSchema",tPD_CheckRule_LibSchema);
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDCheckRuleBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDCheckRuleBL.mErrors;
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
<%=Content%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

