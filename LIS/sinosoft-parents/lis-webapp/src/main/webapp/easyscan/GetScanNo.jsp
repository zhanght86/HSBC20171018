<%
/**
* <p>Title: EasyScan影像系统</p>
* <p>Description: EasyScan从中心获取扫描批次号</p>
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
loggerDebug("GetScanNo","-------------GetScanNo.jsp Begin---------------------");
//返回数据对象
StringBuffer bufXML = new StringBuffer(1024);
String CON_QUERY_HIS_SCANNO="1";
try{
  VData nVData = new VData();
  nVData.setSize(10);
  GetScanNoUI tGetScanNoUI = new GetScanNoUI();
  String strOperate="";
  String strBussType="";
  GlobalInput nGlobalInput=new GlobalInput();
  //传入管理机构和操作员
  nGlobalInput.ManageCom=request.getParameter("ManageCom");
  nGlobalInput.Operator=request.getParameter("Operator");
  //操作方式:0- 申请新批次号;1- 查询历史批次号
  strOperate=request.getParameter("Operate");
  strBussType=request.getParameter("BussType");
  nVData.setElementAt(nGlobalInput,0);
  nVData.setElementAt(strOperate,1);
  if (strOperate.equals(CON_QUERY_HIS_SCANNO)){
    nVData.setElementAt(request.getParameter("ScanNo"),2);
    nVData.setElementAt(request.getParameter("BussNo"),3);
    nVData.setElementAt(request.getParameter("BussType"),4);
    nVData.setElementAt(request.getParameter("SubType"),5);
    nVData.setElementAt(request.getParameter("StartDate"),6);
    nVData.setElementAt(request.getParameter("EndDate"),7);
  }
  nVData.setElementAt(strBussType,8); //by zxm 06.04.10批次号需求
  tGetScanNoUI.submitData(nVData,"");
  //获得返回数据
  bufXML.append(tGetScanNoUI.getResult());
}
catch(Exception e){
  //如果出现异常，返回异常信息
  String strErr = "GetScanNo.jsp--获取扫描批次号失败！\n" + e.toString();

  bufXML.delete(0,bufXML.length());
  bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><RETURN>");
  bufXML.append("<NUMBER>-4500</NUMBER><MESSAGE>");
  bufXML.append(strErr);
  bufXML.append( "</MESSAGE></RETURN></DATA>");

  loggerDebug("GetScanNo",strErr);
}
finally{
  //输出返回数据
  out.println("<IndexXML>" + bufXML.toString() + "</IndexXML>");
  loggerDebug("GetScanNo","-------------GetScanNo.jsp End---------------------");
}
%>
<HTML>
  <title>获取扫描批次号</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
