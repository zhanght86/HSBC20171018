<%
/**
* <p>Title: EasyScan影像系统</p>
* <p>Description: EasyScan从中心获取客户端控件的设置参数</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: Sinosoft</p>
* @author wellhi
* @version 1.0
* @Date 2005-10-11
*/
%>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="java.io.*"%>

<%
loggerDebug("GetCenterSetting","-------------GetCenterSetting.jsp Begin---------------------");
try{
  GlobalInput tGI = new GlobalInput();
  String strManageCom;
  strManageCom = request.getParameter("ManageCom");
  VData vData = new VData();
  tGI.ManageCom = strManageCom;
  vData.add(tGI);
  GetCenterSettingUI tGetCenterSettingUI = new GetCenterSettingUI();
  tGetCenterSettingUI.submitData(vData,"");
  //获得返回数据
  out.println("<IndexXML>" + tGetCenterSettingUI.getResult() + "</IndexXML>");
}
catch (Exception e) {
  loggerDebug("GetCenterSetting","GetCenterSetting--从中心获取客户端控件的设置参数失败!");
  loggerDebug("GetCenterSetting",e.toString());
}
loggerDebug("GetCenterSetting","-------------GetCenterSetting.jsp End ---------------------");
%>
<HTML>
  <title>影像上载预处理</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
