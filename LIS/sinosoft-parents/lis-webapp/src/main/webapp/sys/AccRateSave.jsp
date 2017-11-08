<%
//程序名称：
//程序功能：万能利率输入界面
//创建日期：2007-11-09 17:55:57
//创建人  ：彭送庭

%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
    <%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //接收信息，并作校验处理。
  //输入参数
  LMInsuAccRateSchema tLMInsuAccRateSchema   = new LMInsuAccRateSchema();
  LMAccGuratRateSchema tLMAccGuratRateSchema   = new LMAccGuratRateSchema();

  AccRateBL tAccRateBL = new AccRateBL();

  //输出参数
  CErrors tError = null;
  //后面要执行的动作：添加，修改，删除
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录

  
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String mCurrentTime = PubFun.getCurrentTime();
  String mCurrentDate = PubFun.getCurrentDate();
  VData tVData = new VData();
  
  String tChecks[] = request.getParameterValues("InpLMInsuAccRateGridChk"); 
  String tFlag=request.getParameter("AccType"); //获得是保证利率还是结算利率
  loggerDebug("AccRateSave","保证利率还是结算利率(0/1):"+ tFlag);
  String transact=request.getParameter("transact");
  loggerDebug("AccRateSave","执行 "+ transact + "操作");
  GlobalInput tG = new GlobalInput();      
	tG = (GlobalInput)session.getValue("GI");

  
	
  tLMInsuAccRateSchema.setRiskCode(request.getParameter("RiskCode"));
  tLMInsuAccRateSchema.setBalaDate(request.getParameter("BalaDate"));
  tLMInsuAccRateSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
  tLMInsuAccRateSchema.setSRateDate(request.getParameter("SRateDate"));
  tLMInsuAccRateSchema.setARateDate(request.getParameter("ARateDate"));
  tLMInsuAccRateSchema.setRate(request.getParameter("Rate"));
  tLMInsuAccRateSchema.setRateIntv("Y");
  tLMInsuAccRateSchema.setRateState("0");     
  tLMInsuAccRateSchema.setMakeDate(mCurrentDate);
  tLMInsuAccRateSchema.setMakeTime(mCurrentTime);


  tLMAccGuratRateSchema.setRiskCode(request.getParameter("RiskCode"));
  tLMAccGuratRateSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
  tLMAccGuratRateSchema.setRate(request.getParameter("GruRate"));
  tLMAccGuratRateSchema.setRateStartDate(request.getParameter("RateStartDate"));
  tLMAccGuratRateSchema.setRateEndDate(request.getParameter("RateEndDate"));
  tLMAccGuratRateSchema.setRateIntv("Y");
  tLMAccGuratRateSchema.setRateState("0"); 
  tLMAccGuratRateSchema.setMakeDate(mCurrentDate);
  tLMAccGuratRateSchema.setMakeTime(mCurrentTime);
  
  
  if("1".equals(tFlag)) //结算利率处理
  {
//    if("delete".equals(transact)) //删除记录
//    {
//  	  for(int nIndex = 0; nIndex < tChecks.length; nIndex++ ) 
//	  {
//	      // If this line isn't selected, continue
//	     if( !tChecks[nIndex].equals("1") ) 
//	     {
//	        continue;
//	     }
//
//	 }	
//    }
    
	  try
	  {
		   tVData.add(tLMInsuAccRateSchema);	 
		   tVData.add(tG);  
	    
	   //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	   tAccRateBL.submitData(tVData,transact);
	  }
	  catch(Exception ex)
	  {
	    Content = transact+"失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	  }
  }
  if("0".equals(tFlag)) //保证利率处理
  {
//	    if("delete".equals(transact))  //删除记录
//	    {
//	  	  for(int nIndex = 0; nIndex < tChecks.length; nIndex++ ) 
//		  {
//		      // If this line isn't selected, continue
//		     if( !tChecks[nIndex].equals("1") ) 
//		     {
//		        continue;
//		     }
//
//		 }	
//	    }
	  try
	  {
		   tVData.add(tLMAccGuratRateSchema);
		   tVData.add(tG);  
	   //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	   tAccRateBL.submitData(tVData,transact);
	  }
	  catch(Exception ex)
	  {
	    Content = transact+"失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	  } 

  }
  


  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tAccRateBL.mErrors;
    if (!tError.needDealError())
    {                          
      Content = " 处理成功";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 处理失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //添加各种预处理

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

