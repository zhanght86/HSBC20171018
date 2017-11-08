<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRiskAccPaySave.jsp
//程序功能：账户型险种定义
//创建日期：2009-3-14
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>  
<%
 //接收信息，并作校验处理。
 //输入参数
             
 String FlagStr = "Success";
 String Content = ""+"提交成功！"+"";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
operator = request.getParameter("operator");
PD_LMRiskAccFundSchema   tPD_LMRiskAccFundSchema=new PD_LMRiskAccFundSchema();
tPD_LMRiskAccFundSchema.setRiskCode(request.getParameter("RISKCODE"));
tPD_LMRiskAccFundSchema.setPayPlanCode(request.getParameter("PayPlanCode000"));
tPD_LMRiskAccFundSchema.setAccountCode(request.getParameter("AccountCode000"));
tPD_LMRiskAccFundSchema.setAccountName(request.getParameter("AccountCodeName000"));
tPD_LMRiskAccFundSchema.setAccountType(request.getParameter("AccountType000"));
tPD_LMRiskAccFundSchema.setOperator(request.getParameter("operator"));
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(tPD_LMRiskAccFundSchema);
  tVData.add(transferData);
 PD_LMRiskAccFundBL tPD_LMRiskAccFundBL=new PD_LMRiskAccFundBL();
 if(!tPD_LMRiskAccFundBL.submitData(tVData,operator)){
 FlagStr="Fail";
 System.out.println("提交失败！原因是："+tPD_LMRiskAccFundBL.getErrorMessage());
 Content=""+"提交失败！原因是："+""+tPD_LMRiskAccFundBL.getErrorMessage();
 }
%>                      

<html>
<script type="text/javascript">
<%if((operator.equals("delete")||operator.equals("del"))&&FlagStr.equals("Success")){
%>parent.fraInterface.reset();<%
}%>
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>

