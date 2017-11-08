<%
//程序名称：
//程序功能：分红险系数校验界面
//创建日期：2008-11-09 17:55:57
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
  <%@page import="com.sinosoft.service.*" %>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  String busiName="sysBonusCheckBL";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //接收信息，并作校验处理。
  //输入参数
  LOBonusMainSchema tLOBonusMainSchema   = new LOBonusMainSchema();

  

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
  
  String tChecks[] = request.getParameterValues("InpLOBonusMainGridChk"); 
  String transact=request.getParameter("transact");
  loggerDebug("BonusRateCheckSave","执行 "+ transact + "操作");
  GlobalInput tG = new GlobalInput();      
	tG = (GlobalInput)session.getValue("GI");

 
	tLOBonusMainSchema.setFiscalYear(request.getParameter("FiscalYear"));
	tLOBonusMainSchema.setGroupID("1");	
	tLOBonusMainSchema.setBonusCoefSum( request.getParameter("BonusCoefSum"));
	tLOBonusMainSchema.setFiscalYear(request.getParameter("FiscalYear"));
	tLOBonusMainSchema.setDistributeRate(request.getParameter("DistributeRate"));
	tLOBonusMainSchema.setDistributeValue(request.getParameter("DistributeValue"));


    
	  try
	  {
		   tVData.add(tLOBonusMainSchema);	 
		   tVData.add(tG);  
	    
	   //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	   tBusinessDelegate.submitData(tVData,transact,busiName);
	  }
	  catch(Exception ex)
	  {
	    Content = transact+"失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	  }
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
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

