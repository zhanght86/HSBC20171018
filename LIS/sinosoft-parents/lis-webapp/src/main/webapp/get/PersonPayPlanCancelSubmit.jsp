<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：PersonPayPlanCancelSubmit.jsp
//程序功能：
//创建日期：2005-7-6 16:43
//创建人  ：JL
//更新记录：  更新人    更新日期     更新原因/内容      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.service.*"%>

<%
  //接收信息，并作校验处理。
  //输入参数
                                               
  loggerDebug("PersonPayPlanCancelSubmit","==> Begin to Cancel LFGET");         
  
  LJSGetSchema tLJSGetSchema = new LJSGetSchema();
 // PersonPayPlanCancelUI tPersonPayPlanCancelUI = new PersonPayPlanCancelUI();
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //输出参数
  String FlagStr = "";
  String Content = "";
  CErrors tError = null; 
  
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("PersonPayPlanCancelSubmit","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
    //后面要执行的动作：添加，修改，删除
    String transact =request.getParameter("fmtransact");
    loggerDebug("PersonPayPlanCancelSubmit","transact:"+transact);   
    
    tLJSGetSchema.setGetNoticeNo(request.getParameter("OutGetNoticeNo"));
        
    try
    {
      // 准备传输数据 VData
      VData tVData = new VData();
      tVData.addElement(tGI);
      tVData.addElement(tLJSGetSchema);
    
      //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
      //tPersonPayPlanCancelUI.submitData(tVData,transact);
      tBusinessDelegate.submitData(tVData,transact,"PersonPayPlanCancelUI");
    }
    catch(Exception ex)
    {
      Content = "撤销失败，原因是:" + ex.toString();
      FlagStr = "Fail";
    }
  
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr=="")
    {
      //tError = tPersonPayPlanCancelUI.mErrors;
      tError = tBusinessDelegate.getCErrors();
      if (!tError.needDealError())
      {                          
        Content ="撤销成功！";
    	FlagStr = "Succ";
      }
      else                                                                           
      {
    	Content = "撤销失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
      }
    }
  } //页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

