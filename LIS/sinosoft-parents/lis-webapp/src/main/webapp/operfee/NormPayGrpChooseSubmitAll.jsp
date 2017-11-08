  <%
//程序名称：NormPayGrpChooseSubmitAll.jsp
//程序功能：
//创建日期：2002-10-11 08:49:52
//创建人  ：
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
  
<%@page contentType="text/html;charset=GBK" %> 
<%
  
//集体保单表-放置集体保单号，交费日期，操作员，管理机构      
  LCGrpPolSet    tLCGrpPolSet  = new LCGrpPolSet();  ;
  LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
  
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
  String GrpPolNo=request.getParameter("SubmitGrpPolNo"); //集体保单号码   
  String GrpContNo=request.getParameter("SubmitGrpContNo");
  String PayDate=request.getParameter("SubmitPayDate"); //交费日期
  String ManageFeeRate=request.getParameter("SubmitManageFeeRate"); //管理费比例
   
  tLCGrpPolSchema.setGrpPolNo(GrpPolNo);
  tLCGrpPolSchema.setGrpContNo(GrpContNo);
  tLCGrpPolSchema.setPaytoDate(PayDate); //交至日期字段保存交费日期
  tLCGrpPolSchema.setManageFeeRate(ManageFeeRate);
  tLCGrpPolSchema.setManageCom(ManageCom);
  tLCGrpPolSchema.setOperator(Operator);        
  
  NormPayGrpChooseOperUI tNormPayGrpChooseOperUI = new NormPayGrpChooseOperUI();   
  try
  {
   VData tVData = new VData();
   tVData.addElement(tGI);
   tVData.addElement(tLCGrpPolSchema);
   tNormPayGrpChooseOperUI.submitData(tVData,"VERIFY");
   tError = tNormPayGrpChooseOperUI.mErrors;
   
   if (tError.needDealError())        
    {
     Content = " 交费失败，原因是: " + tNormPayGrpChooseOperUI.mErrors.getError(0).errorMessage;
     FlagStr = "Fail";      	  
    }
    else
    {
     Content = " 数据处理完毕";
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
    if("<%=FlagStr%>"=="Succ")
      {
       parent.fraInterface.  NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid");  
      }
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</body>
</html>

