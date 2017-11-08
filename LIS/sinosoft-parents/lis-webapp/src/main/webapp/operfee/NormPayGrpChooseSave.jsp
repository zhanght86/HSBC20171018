 <%
//程序名称：NormPayGrpChooseSave.jsp
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
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  <%@page import="com.sinosoft.service.*" %> 
  
<%@page contentType="text/html;charset=GBK" %> 
<%

//应收个人交费表      
  LJSPayPersonSet    tLJSPayPersonSet    ;
  LJSPayPersonSchema tLJSPayPersonSchema ;
  
  String tContno = "00000000000000000000";
  
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
  String GrpPolNo=request.getParameter("PolNo"); //集体保单号码   
  String PayDate=request.getParameter("PayDate"); //交费日期

//应收个人交费表中的多项数据                        
    String tTempClassNum[] = request.getParameterValues("NormPayGrpChooseGridNo"); //序号
    String tChk[] = request.getParameterValues("InpNormPayGrpChooseGridChk");    //选中标记   
    String tPolNo[] = request.getParameterValues("NormPayGrpChooseGrid1"); //个人保单号
    String tDutyCode[] = request.getParameterValues("NormPayGrpChooseGrid2"); //责任编码
    String tPayPlanCode[] = request.getParameterValues("NormPayGrpChooseGrid3"); //交费计划编码
    String tSumDuePayMoney[] = request.getParameterValues("NormPayGrpChooseGrid6"); //应交金额    
    String tSumActuPayMoney[] = request.getParameterValues("NormPayGrpChooseGrid7"); //实交金额
	
    int TempCount = tTempClassNum.length; //记录数      
  
    tLJSPayPersonSet    = new LJSPayPersonSet();
   
   for(int i=0;i<TempCount;i++)
   {
     tLJSPayPersonSchema = new LJSPayPersonSchema();
     tLJSPayPersonSchema.setPolNo(tPolNo[i]);
     tLJSPayPersonSchema.setDutyCode(tDutyCode[i]);
     tLJSPayPersonSchema.setPayPlanCode(tPayPlanCode[i]);
     tLJSPayPersonSchema.setSumDuePayMoney(tSumDuePayMoney[i]);
     tLJSPayPersonSchema.setSumActuPayMoney(tSumActuPayMoney[i]);
     tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
     tLJSPayPersonSchema.setContNo(tContno);
     tLJSPayPersonSchema.setPayAimClass("2"); //集体下的个人交费     
     tLJSPayPersonSchema.setOperator(Operator);
     tLJSPayPersonSchema.setPayType("ZC");           //交费方式：正常交费    
     if(tChk[i].equals("1")) //如果被选中，那么录入标记设为1
    	 tLJSPayPersonSchema.setInputFlag("1");
     else
         tLJSPayPersonSchema.setInputFlag("0"); 	 
     tLJSPayPersonSet.add(tLJSPayPersonSchema); 
   }

  //PreParePayPersonUI tPreParePayPersonUI = new PreParePayPersonUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
   VData tVData = new VData();
   tVData.addElement(tLJSPayPersonSet);
   
   tBusinessDelegate.submitData(tVData,"INSERT","PreParePayPersonUI");

   tError =  tBusinessDelegate.getCErrors();
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

