<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/**
* <p>Title: EasyScan影像系统</p>
* <p>Description: EasyScan查询单证详细数据处理</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: Sinosoft</p>
* @author wellhi
* @version 1.0
* @Date 2005-11-09
*/
%>
<%@page contentType="text/html;charset=gb2312" %>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>EasyScan影像系统</title>
    
    <script src="../common/javascript/Common.js" type="text/javascript"></script>
    <script src="../common/cvar/CCodeOperate.js" type="text/javascript"></script>
    <!-- 页面样式  -->
    <link rel='stylesheet' type='text/css' href='../common/css/Project.css'>
	<link rel='stylesheet' type='text/css' href='../common/css/Project3.css'>
      <%
      try
      {
        //获取session
        GlobalInput tG1 = (GlobalInput)session.getValue("GI");
        if (tG1 == null) {
          session.putValue("GI",null);
          out.println("网页超时，请您重新登录！");
        }
        //获取URL
        String clientURL = (String)session.getValue("ClientURL");
        int iEnd = clientURL.indexOf("logon/main.jsp");
        if(iEnd == -1) iEnd = clientURL.indexOf("logon/mainNew.jsp");
        clientURL = clientURL.substring(0,iEnd);
        //获取影像保存类型
        String imageType = EasyScanConfig.getInstance().imageType;
        //其它的传入参数（预留）
        String otherConfig = "";
        %>

        <script language="JavaScript" type="text/javascript">
          var objWUpdate;
          var objEasyScan;
          var strRet;
          var strVersion;
          //EasyScan当前版本
          var CON_CURRENT_VERSION;
          CON_CURRENT_VERSION="V4.0.8.0";
          try {
            //创建在线更新程序对象
            objWUpdate = new ActiveXObject ("Update.WUpdate");
            //创建成功，则启动在线更新程序对象
            strRet=objWUpdate.sfUpdate("<%= clientURL%>", "EasyScan单证扫描系统");

            /*
            //不处理返回信息
            if(strRet=="") {
              alert("更新成功！");
            }
            else{
              alert("更新失败！" + strRet);
            }
            */
            //            objWUpdate=null;
            if(objWUpdate!=null){
              objWUpdate=null;
            }
          }
          catch(e) {
            alert( "自动更新程序启动失败！！\n" + e);
            if(objWUpdate!=null){
              objWUpdate=null;
            }
          }

          try {
            //创建EasyScan对象
            objEasyScan = new ActiveXObject ("EasyScan.SinoEasyScan");
            strVersion=objEasyScan.dfGetVersion();
            //如果客户端与服务器端的版本一致
            if(strVersion == CON_CURRENT_VERSION)
            {
              //创建成功，则初始化EasyScan
              objEasyScan.dfControlInit("<%=tG1.Operator%>", "<%=tG1.ManageCom%>", "<%=clientURL%>", "<%=imageType%>", "<%=otherConfig%>");
              //启动EasyScan
              objEasyScan.dfShowMain();
              //              alert(objEasyScan.dfGetVersion());
            }
            else
            {
              //如果客户端版本比服务器端的版本旧
              if(strVersion < CON_CURRENT_VERSION)
              {
                alert( "您使用的不是最新版本的EasyScan程序 [" + strVersion + "]，请下载安装最新版本的EasyScan程序 [" + CON_CURRENT_VERSION + "] ！");
              }
              //如果客户端版本比服务器端的版本新
              else if(strVersion > CON_CURRENT_VERSION)
              {
                alert( "您使用的EasyScan程序版本是 [" + strVersion + "] ，服务器端的版本是 [" + CON_CURRENT_VERSION + "] ，两个版本不匹配，请与系统管理员联系！");
              }
            }
            if(objEasyScan!=null){
              objEasyScan=null;
            }
          }
          catch(e) {
            alert( "EasyScan扫描影像系统启动失败，可能是您未安装EasyScan最新版本 [" + CON_CURRENT_VERSION + "] 的程序，或者没有正确安装。\n" + e);
            if(objEasyScan!=null){
              objEasyScan=null;
            }
          }
          </script>
          </head>
          <body>
            <%
            }
            catch(Exception exception)
            {
              out.println("网页超时，请您重新登录");
              return;
            }
            %>
            </body>
          </html>
