<%
//程序名称：FIDataBaseLinkSave.jsp
//程序功能：数据接口配置管理
//创建日期：2008-08-04	
//创建人  ：范昕
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

 
  loggerDebug("FIDataBaseLinkSave","开始执行Save页面");
  FIDataBaseLinkSchema mFIDataBaseLinkSchema = new FIDataBaseLinkSchema();
  //FIDataBaseLinkUI mFIDataBaseLinkUI = new FIDataBaseLinkUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("FIDataBaseLinkSave","操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  
  if((mOperateType.equals("INSERT||MAIN1"))||(mOperateType.equals("UPDATE||MAIN1"))||(mOperateType.equals("DELETE||MAIN1")))
  {
    loggerDebug("FIDataBaseLinkSave","开始进行获取数据的操作！！！");
  	mFIDataBaseLinkSchema.setInterfaceCode(request.getParameter("InterfaceCode"));
  	mFIDataBaseLinkSchema.setInterfaceName(request.getParameter("InterfaceName"));
  	mFIDataBaseLinkSchema.setDBType(request.getParameter("DBType"));
  	mFIDataBaseLinkSchema.setIP(request.getParameter("IP1"));
  	mFIDataBaseLinkSchema.setPort(request.getParameter("Port1"));
  	mFIDataBaseLinkSchema.setDBName(request.getParameter("DBName1"));  
  	mFIDataBaseLinkSchema.setServerName(request.getParameter("ServerName1"));
  	mFIDataBaseLinkSchema.setUserName(request.getParameter("UserName1"));  
  	mFIDataBaseLinkSchema.setPassWord(request.getParameter("PassWord1")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
    	tVData.addElement(mFIDataBaseLinkSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIDataBaseLinkUI");
  	}
  	catch(Exception ex)
  	{
    	Content = "失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
 	 	}
  }

	if((mOperateType.equals("INSERT||MAIN2"))||(mOperateType.equals("UPDATE||MAIN2"))||(mOperateType.equals("DELETE||MAIN2")))
  {
    loggerDebug("FIDataBaseLinkSave","开始进行获取数据的操作！！！");
  	mFIDataBaseLinkSchema.setInterfaceCode(request.getParameter("InterfaceCode"));
  	mFIDataBaseLinkSchema.setInterfaceName(request.getParameter("InterfaceName"));
  	mFIDataBaseLinkSchema.setDBType(request.getParameter("DBType"));
  	mFIDataBaseLinkSchema.setIP(request.getParameter("IP2"));
  	mFIDataBaseLinkSchema.setPort(request.getParameter("Port2"));
  	mFIDataBaseLinkSchema.setDBName(request.getParameter("DBName2"));  
  	mFIDataBaseLinkSchema.setServerName(request.getParameter("ServerName2"));
  	mFIDataBaseLinkSchema.setUserName(request.getParameter("UserName2"));  
  	mFIDataBaseLinkSchema.setPassWord(request.getParameter("PassWord2")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
    	tVData.addElement(mFIDataBaseLinkSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIDataBaseLinkUI");
  	}
  	catch(Exception ex)
  	{
    	Content = "失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
 	 	}
  }
  
  if((mOperateType.equals("INSERT||MAIN3"))||(mOperateType.equals("UPDATE||MAIN3"))||(mOperateType.equals("DELETE||MAIN3")))
  {
    loggerDebug("FIDataBaseLinkSave","开始进行获取数据的操作！！！");
  	mFIDataBaseLinkSchema.setInterfaceCode(request.getParameter("InterfaceCode"));
  	mFIDataBaseLinkSchema.setInterfaceName(request.getParameter("InterfaceName"));
  	mFIDataBaseLinkSchema.setDBType(request.getParameter("DBType"));
  	mFIDataBaseLinkSchema.setIP(request.getParameter("IP3"));
  	mFIDataBaseLinkSchema.setPort(request.getParameter("Port3"));
  	mFIDataBaseLinkSchema.setDBName(request.getParameter("DBName3"));  
  	mFIDataBaseLinkSchema.setServerName(request.getParameter("ServerName3"));
  	mFIDataBaseLinkSchema.setUserName(request.getParameter("UserName3"));  
  	mFIDataBaseLinkSchema.setPassWord(request.getParameter("PassWord3")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
    	tVData.addElement(mFIDataBaseLinkSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIDataBaseLinkUI");
  	}
  	catch(Exception ex)
  	{
    	Content = "失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
 	 	}
  }
  
  if((mOperateType.equals("INSERT||MAIN4"))||(mOperateType.equals("UPDATE||MAIN4"))||(mOperateType.equals("DELETE||MAIN4")))
  {
    loggerDebug("FIDataBaseLinkSave","开始进行获取数据的操作！！！");
  	mFIDataBaseLinkSchema.setInterfaceCode(request.getParameter("InterfaceCode"));
  	mFIDataBaseLinkSchema.setInterfaceName(request.getParameter("InterfaceName"));
  	mFIDataBaseLinkSchema.setDBType(request.getParameter("DBType"));
  	mFIDataBaseLinkSchema.setIP(request.getParameter("IP4"));
  	mFIDataBaseLinkSchema.setPort(request.getParameter("Port4"));
  	mFIDataBaseLinkSchema.setDBName(request.getParameter("DBName4"));  
  	mFIDataBaseLinkSchema.setServerName(request.getParameter("ServerName4"));
  	mFIDataBaseLinkSchema.setUserName(request.getParameter("UserName4"));  
  	mFIDataBaseLinkSchema.setPassWord(request.getParameter("PassWord4")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
    	tVData.addElement(mFIDataBaseLinkSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIDataBaseLinkUI");
  	}
  	catch(Exception ex)
  	{
    	Content = "失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
 	 	}
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
     tError = uiBusinessDelegate.getCErrors();
    	   if (!tError.needDealError())
    	  {  
    			Content = "操作已成功!";
    			FlagStr = "Succ";
    		}
   	  else
    		{
    			Content = " 操作失败，原因是:" + tError.getFirstError();
    			FlagStr = "Fail";
    		}
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
