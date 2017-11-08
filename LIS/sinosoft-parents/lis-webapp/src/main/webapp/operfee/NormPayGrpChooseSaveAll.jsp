 <%
//程序名称：NormPayGrpChooseSaveAll.jsp
//程序功能：
//创建日期：2005-07-05 08:49:52
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="java.lang.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.bl.*"%>  
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  
<%@page contentType="text/html;charset=gb2312" %> 
<%

// 输出参数
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  if(tGI==null)
  {
    System.out.println("页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
   String Operator  = tGI.Operator ;  //保存登陆管理员账号
   String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码

  
  //表单中的隐藏字段
  String GrpPolNo=request.getParameter("GrpPolNo1"); //集体保单号码  

  SaveAllPersonInput tSaveAllPersonInput = new SaveAllPersonInput();
  try
  {
   VData tVData = new VData();
   tVData.add(tGI);
   tVData.addElement(GrpPolNo);

   
   tSaveAllPersonInput.submitData(tVData,"GrpChoose_INSERT");

   tError = tSaveAllPersonInput.mErrors;
   if (tError.needDealError())        
    {
      Content = " 失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail"; 
    }
   else{
      Content = " 数据保存完毕";
      FlagStr = "Succ";   	 
      } 
  }
  catch(Exception ex)
  {
    Content = "失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
} //页面有效区
%>                                              
<html>
<body>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</body>
</html>

