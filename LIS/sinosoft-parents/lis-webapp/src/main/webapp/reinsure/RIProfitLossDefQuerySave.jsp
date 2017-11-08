<%@include file="/i18n/language.jsp"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：RIProfitLossDefQuerySave.jsp
//程序功能：盈余佣金定义查询
//创建日期：2011/8/20
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
 //接收信息，并作校验处理。
 
 //输入参数
 //××××Schema t××××Schema = new ××××Schema();
 RIProfitLossDefQueryUI tRIProfitLossDefQueryUI = new RIProfitLossDefQueryUI();
 
 //输出参数
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String transact = "";
 
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 transact = request.getParameter("fmtransact");
 
 //从url中取出参数付给相应的schema
 //t××××Schema.set××××(request.getParameter("××××"));
 
 try{
  //准备传输数据VData
  VData tVData = new VData();
  
  //传输schema
  //tVData.addElement(t××××Schema);
  
  tVData.add(tG);
  tRIProfitLossDefQueryUI.submitData(tVData,transact);
 }
 catch(Exception ex){
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }
 
 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr==""){
  tError = tRIProfitLossDefQueryUI.mErrors;
  if (!tError.needDealError()){                          
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           {
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
