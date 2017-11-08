<%@ page language="java" contentType="text/html; charset=GBK"  isELIgnored="false" pageEncoding="GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>EasyScan 简单例子</title>

<!--样色表，需要后台调整颜色#8CAAE7; -->
<style type="text/css">
body
{
    margin: 0px;
	padding: 0px;
	border: 1px solid #8CAAE7;
	clear: none;
	font-size:12px;
}
a:visited,a{color:blue;}
<%
  GlobalInput tG1 = (GlobalInput)session.getValue("GI");
  String clientURL=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
  System.out.println("getcentersetting------------id1="+session.getId());
  String channel = request.getParameter("channel");
  String channelName = request.getParameter("channelName"); 
%>
</style>


</head>
<body >

	<div id="installInfo" style="display:none">
	<h1>您的扫描客户端未安装或未正确配置。</h1>
	如果您未安装，请<a href="" onclick="javascript:alert('管理员尚未配置下载地址,请通知管理员进行配置');return false;">点此下载</a>并安装。安装完毕后请参照<a href="">配置手册</a>对浏览器进行正确配置。
</div>
</body>
</html>


<!--调用方法样本，需要根据实际情况修改传入参数-->
<script language="javascript" type="text/javascript">


 var activex =null;
    try { 
        activex = new ActiveXObject("EasyScan.ActiveControl");
//        if (activex.RuntimeVersion<'2.0.0.0'){
//            //转到安装包的FWLink页面。
//        }
 
        activex.clear(); //清空所有参数
        activex.SystemId="";
        activex.ServerId="";
        activex.ServerUri = "<%=clientURL%>" + "easyscan5/EasyScanAdapter.jsp";
 		activex.ClientId = "";
        activex.ManageCom = "<%= tG1.ManageCom%>";
        activex.UserCode = "<%=tG1.Operator%>";               
        activex.Channel = "1";
        activex.Module = "0";  
        activex.InitParams = "json={'isUseBoxno':'true','isUseScanOrder':'true'}";

        activex.SourceMode = "Form"; //指定为弹出模式      
 		activex.UpdateMode = "";
        activex.AutoLoad = false; //设置为手动更新，采用load手动更新。
        activex.AutoUpdate = true;
        activex.AutoSettings = true;
        activex.AutoFeedback = false;
 
        //执行事件挂载
        //dispEvent();
 
        //初始化组件
        activex.load(); //开始更新
 
    }
    catch (ex) {
        alert("EasyScan影像系统启动失败，请重新安装,错误信息:\r\n" + ex.message);
				document.getElementById("installInfo").style.display="block";
    }
    finally {
        if(activex != null){
	 		 		activex.free();
          activex = null;
				}
    }   
</script>