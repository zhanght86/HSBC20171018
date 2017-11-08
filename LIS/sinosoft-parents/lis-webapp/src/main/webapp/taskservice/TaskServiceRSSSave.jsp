<%--
    RSS订阅 2011-12-06 HuangLiang
    根据请求生成订阅地址，或者输出订阅内�?
--%><%@page contentType="text/html;charset=UTF-8" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%	         
	String FlagStr="";      //操作结果
	String Content = "";    //控制台信�?
	String tAction = "";    //操作类型:获取地址，获取RSS
	String tOperate = "";   //操作代码�? AddressRSS OrderRSS

	tAction= request.getParameter("fmRSS");	
	if("AddressRSS".equalsIgnoreCase(tAction)){
		%><%@include file="../common/jsp/UsrCheck.jsp"%><%
		GlobalInput tGI = new GlobalInput();
		VData tData = new VData();
		String busiName = "TaskServiceRssUI";
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		
		tGI = (GlobalInput)session.getValue("GI");
		loggerDebug("TaskServiceRSSSave",tGI.Operator);
		tOperate = "AddressRSS";
    	tData.add(tGI.Operator);// 准备传输数据 VData
				
		if(!FlagStr.equals("Fail"))
		{
			if (!tBusinessDelegate.submitData(tData, tOperate,busiName))
			{
				Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
				loggerDebug("TaskServiceRSSSave",Content);
			}
			else
			{
				Content = " 操作成功! ";
				FlagStr = "Succ";
				String t = (String)tBusinessDelegate.getResult().get(0);
				String tAddress = "http://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI();
				tAddress = tAddress + "?RSSID=" + t;
				out.clear();loggerDebug("TaskServiceRSSSave",tAddress);
				//输出订阅地址
				%>
<html>
<script>
	prompt('URL',"<%=tAddress%>");
</script>
</html>
<% 				loggerDebug("TaskServiceRSSSave",t);//输出RSS订阅地址后缀
				tData.clear();
			}
		}
	}else
	{
		String tRSSID =	request.getParameter("RSSID");
		if(tRSSID != null){

			//查看订阅的RSS内容
			loggerDebug("TaskServiceRSSSave",tRSSID);
			VData tData = new VData();
			tOperate = "OrderRSS";
			String busiName = "TaskServiceRssUI";
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
//			 准备传输数据 VData
			tData.add(tRSSID);
			if(!FlagStr.equals("Fail"))
			{
				if (!tBusinessDelegate.submitData(tData, tOperate,busiName))
				{
					Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
					out.println(Content);
				}
				else
				{
					Content = " 操作成功! ";
					FlagStr = "Succ";
					String t = (String)tBusinessDelegate.getResult().get(0);
					out.clear();out.println(t);//输出RSS文件
					loggerDebug("TaskServiceRSSSave",t);
					tData.clear();
				}
			}
		}
	}
%>                      
