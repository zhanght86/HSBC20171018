<%
/**
* <p>Title: EasyScanӰ��ϵͳ</p>
* <p>Description: EasyScan�����Ļ�ȡ�ͻ��˿ؼ������ò���</p>
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
  //��÷�������
  out.println("<IndexXML>" + tGetCenterSettingUI.getResult() + "</IndexXML>");
}
catch (Exception e) {
  loggerDebug("GetCenterSetting","GetCenterSetting--�����Ļ�ȡ�ͻ��˿ؼ������ò���ʧ��!");
  loggerDebug("GetCenterSetting",e.toString());
}
loggerDebug("GetCenterSetting","-------------GetCenterSetting.jsp End ---------------------");
%>
<HTML>
  <title>Ӱ������Ԥ����</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
