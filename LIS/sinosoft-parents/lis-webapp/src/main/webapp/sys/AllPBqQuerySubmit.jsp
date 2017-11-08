<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：AllPBqQuerySubmit.jsp
//程序功能：
//创建日期：2003-1-20
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
//      
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
<%
  //接收信息，并作校验处理。
  //输入参数
  LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
  LCPolSchema tLCPolSchema = new LCPolSchema();
  //AllPBqQueryUI tAllPBqQueryUI = new AllPBqQueryUI();
  String busiName="sysAllPBqQueryUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //输出参数
  String FlagStr = "";
  String Content = "";
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("AllPBqQuerySubmit","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
   String Operator  = tGI.Operator ;  //保存登陆管理员账号
   String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码
  
  CErrors tError = null;
  String tBmCert = "";
  
  //后面要执行的动作：添加，修改，删除
  String transact=request.getParameter("Transact");
loggerDebug("AllPBqQuerySubmit","transact:"+transact); 

    tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorMainSchema.setPolNo(request.getParameter("PolNo"));        
    tLPEdorMainSchema.setEdorType(request.getParameter("EdorType"));
    tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
    tLPEdorMainSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tLPEdorMainSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
    tLPEdorMainSchema.setManageCom(request.getParameter("ManageCom"));
        
 try
  {
  // 准备传输数据 VData
   VData tVData = new VData();
   tVData.addElement(tGI);
   tVData.addElement(tLPEdorMainSchema);
   tVData.addElement(tLCPolSchema);
    
   //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  //tAllPBqQueryUI.submitData(tVData,transact);
  if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "保存失败";
			FlagStr = "Fail";				
		}
	}
	else
	{
	   Content = "保存成功";
	   FlagStr = "Succ";				
	} 

  
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    //tError = tAllPBqQueryUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content ="保存成功！";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "保存失败，原因是:" + tError.getFirstError();
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

