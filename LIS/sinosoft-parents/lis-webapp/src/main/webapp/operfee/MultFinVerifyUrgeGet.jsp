 <%
//程序名称：IndiFinVerifyUrgeGet.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>    
  
<%@page contentType="text/html;charset=GBK" %>

<%
// 输出参数
   CErrors tErrors = null;          
   String FlagStr = "";
   String Content = "";

   // 准备传输数据 VData 
   VData tVData = new VData();
   
   GlobalInput tGI = (GlobalInput)session.getValue("GI");
   tVData.add(tGI);
   
   //String StartDate = request.getParameter("StartDate");
   //String EndDate   = request.getParameter("EndDate");
//System.out.println("EndDate="+EndDate);
   
   //LJTempFeeSchema qLJTempFeeSchema = new LJTempFeeSchema();
   //qLJTempFeeSchema.setEnterAccDate(StartDate); //到帐日期存放起始日期
   //qLJTempFeeSchema.setConfDate(EndDate);      //确认日期存放终止日期
   
  // tVData.add(qLJTempFeeSchema);
   MultFinVerifyUrgeGetBL tMultFinVerifyUrgeGetBL = new MultFinVerifyUrgeGetBL();
   tMultFinVerifyUrgeGetBL.submitData(tVData,"Verify");
   int failNum = tMultFinVerifyUrgeGetBL.failNum;
   int Count = tMultFinVerifyUrgeGetBL.Count;
   
   if(failNum != 0){
     int succNum = Count - failNum;
     FlagStr="Fail";
     Content="共查询到"+Count+"个纪录，成功处理"+succNum+"个纪录，失败"+failNum+"个纪录";
     tErrors = tMultFinVerifyUrgeGetBL.mErrors;
     if (tErrors.needDealError()){
      Content = Content+"\n 失败原因是:" + tErrors.getFirstError();
      }
   }
   else{
      FlagStr="批处理完成";
      Content="共查询到"+Count+"个纪录,全部处理成功!";
   }
%>
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>  