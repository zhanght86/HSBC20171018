<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDTestPointClewSave.jsp
//程序功能：测试要点提示
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
 

 PDTestPlanClewBL tPDTestPlanClewBL = new PDTestPlanClewBL();
 
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
 
 PD_TestPlanClew_LibSchema tPD_TestPlanClew_LibSchema = new PD_TestPlanClew_LibSchema();
 String tPlanKind = request.getParameter("planKind");
 String tRiskKind = request.getParameter("riskkind");
 String tClewContent = request.getParameter("ClewContent");
 String tRemark = request.getParameter("Remark");
 String tClewcontentcode = request.getParameter("clewcontentcode");
 
 if(tRiskKind == null || tRiskKind.trim().equals("")){
	 tRiskKind = "9";
 }
 
 System.out.println("前面接收的数据为： tPlanKind = " + tPlanKind + " tRiskKind = " + tRiskKind + " tClewContentCode = " + tClewContent + " tRemark = " + tRemark + " tClewcontentcode = " + tClewcontentcode);
 tPD_TestPlanClew_LibSchema.setTESTPLANKIND(tPlanKind);
 tPD_TestPlanClew_LibSchema.setTESTPLANRISKKIND(tRiskKind);
 tPD_TestPlanClew_LibSchema.setClewContent(tClewContent);
 tPD_TestPlanClew_LibSchema.setRemark(tRemark);
 tPD_TestPlanClew_LibSchema.setClewContentCode(tClewcontentcode);

 transferData.setNameAndValue("PD_TestPlanClew_LibSchema",tPD_TestPlanClew_LibSchema);
 
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  tPDTestPlanClewBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = tPDTestPlanClewBL.mErrors;
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

