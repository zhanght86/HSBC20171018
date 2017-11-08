<%
 loggerDebug("ActivateSave","Start ActivateSave.jsp Submit...1");
//程序名称：ActivateInput.jsp
//程序功能：卡单激活功能
//创建日期：2008-03-05 
//创建人  ：张征
//更新记录：  更新人    更新日期     更新原因/内容
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.lis.ebusiness.*"%>
  <%@page import="java.util.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

  //接收信息，并作校验处理。
  //输入参数
  //输出参数

  String mInputNo=request.getParameter("CertifyNo"); //卡号
  String mPassword = request.getParameter("Password"); //密码
  String Content="";
  String FlagStr="";
  CErrors tError = null;
  
  loggerDebug("ActivateSave","开始进行获取数据的操作！！！");
 
  //操作类型是插入，查询，更新
  loggerDebug("ActivateSave","页面录入的卡号是"+mInputNo);
  loggerDebug("ActivateSave","页面录入的密码是"+mPassword);

  VData tResult=new VData();
  //将数据提交给后台方法进行卡号和密码校验,参数放在Vector中
  try
  {
	    //将操作类型，管理机构，操作员添加到容器中
	    Vector testVector=new Vector();
	    testVector.add("Check");
    	testVector.add(mInputNo);
    	testVector.add(mPassword);
	    
    	//激活处理类
    	ActivateBL tActivateBL=new ActivateBL();
    	tResult=(VData)tActivateBL.submitData(testVector);
    	
    	loggerDebug("ActivateSave","返回的标识位:"+tResult.get(0));
    	loggerDebug("ActivateSave","返回的提示信息:"+tResult.get(1));
  }
  catch(Exception ex)
  {
       Content = "校验失败，原因是:" + ex.toString();
       FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    if (tResult.get(0).equals("success"))
    {
        Content = (String)tResult.get(1);
    	FlagStr = "Succ";
    }
    else
    {
    	Content = (String)tResult.get(1);
    	FlagStr = "Fail";
    }
  }
   loggerDebug("ActivateSave","Flag : " + FlagStr + " -- Content : " + Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

