<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRiskDefiSave.jsp
//程序功能：产品基础信息录入
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
 

 PDRiskDefiBL pDRiskDefiBL = new PDRiskDefiBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
	 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
	 transferData.setNameAndValue("RequDate",request.getParameter("RequDate"));
	 transferData.setNameAndValue("Operator",tG.Operator);
	 transferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
	 transferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
	 transferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	 transferData.setNameAndValue("IsBaseInfoDone","1");
	 
	 ProdDefWorkFlowBL tProdDefWorkFlowBL= new ProdDefWorkFlowBL();
	 
	 
	 try
	 {
	  // 准备传输数据 VData
	  VData tVData = new VData();

	  tVData.add(tG);
	  tVData.add(transferData);
	  tProdDefWorkFlowBL.submitData(tVData,operator);
	 }
	 catch(Exception ex)
	 {
	  Content = ""+"保存失败，原因是:"+"" + ex.toString();
	  FlagStr = "Fail";
	 }

	 //如果在Catch中发现异常，则不从错误类中提取错误信息
	 if (FlagStr=="")
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
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

