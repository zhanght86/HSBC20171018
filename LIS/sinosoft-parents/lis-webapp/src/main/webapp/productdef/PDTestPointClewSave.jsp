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
 

 PDTestPointClewBL pDTestPointClewBL = new PDTestPointClewBL();
 
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
 
 PD_TestPointClew_LibSchema tPD_TestPointClew_LibSchema = new PD_TestPointClew_LibSchema();
 tPD_TestPointClew_LibSchema.setFieldCode(request.getParameter("FieldCode"));
 tPD_TestPointClew_LibSchema.setTableCode(request.getParameter("TableCode"));
 tPD_TestPointClew_LibSchema.setClewContent(request.getParameter("ClewContent"));
 tPD_TestPointClew_LibSchema.setId(request.getParameter("Id")); 
 
 transferData.setNameAndValue("PD_TestPointClew_LibSchema",tPD_TestPointClew_LibSchema);
 
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDTestPointClewBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDTestPointClewBL.mErrors;
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

