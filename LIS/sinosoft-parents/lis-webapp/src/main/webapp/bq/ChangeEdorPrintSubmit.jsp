<%
//程序名称：
//程序功能：
//创建日期：2003-1-20
//创建人  ：yz
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
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page contentType="text/html;charset=UTF-8" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //输出参数
  String FlagStr = "";
  String Content = "";
  
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  

  //后面要执行的动作：添加，修改，删除
  String transact=request.getParameter("Transact");
  loggerDebug("ChangeEdorPrintSubmit","transact:"+transact);
  String tFileName = request.getParameter("FileName");
  loggerDebug("ChangeEdorPrintSubmit",tFileName);
  String tEdorNo = request.getParameter("EdorNo1");
  
  String result = "";
  if (transact.equals("DisplayToXML")) {
    try 
    {   
       result = changeXml.getBqPrintToXml(tEdorNo);
       String Path = application.getRealPath("config//Conversion.config");
       result = StrTool.Conversion(result, Path);
       //loggerDebug("ChangeEdorPrintSubmit",result);
    }
    catch (Exception e)
    {
      loggerDebug("ChangeEdorPrintSubmit","DisplayToXML 失败");
      Content = "DisplayToXML失败";
      FlagStr = "Fail";
    }
  }
  else if (transact.equals("DisplayXMLToPrint")) {
    try 
    {   
      //loggerDebug("ChangeEdorPrintSubmit","aaa: " + request.getParameter("EdorXml"));
       changeXml.setXmlToBqPrint2(request.getParameter("EdorXml"), tEdorNo, tGI.ComCode, tGI.Operator);
       Content = "保存成功";
       FlagStr = "true";
    }
    catch (Exception e)
    {
      loggerDebug("ChangeEdorPrintSubmit","setXmlToBqPrint2 失败");
      Content = "setXmlToBqPrint2 失败";
      FlagStr = "Fail";
    }
  }
  else if (transact.equals("PrintToXML")) {
    try 
    {   
       changeXml.getBqPrintToXml(tFileName, tEdorNo);
       loggerDebug("ChangeEdorPrintSubmit","getBqPrintToXml成功");
       Content = "保存成功";
       FlagStr = "true";
    }
    catch (Exception e)
    {
       Content = "保存失败";
       FlagStr = "Fail";
    }
  }
  else {
    try 
    {   
     changeXml.setXmlToBqPrint(tFileName, tEdorNo, tGI.ComCode, tGI.Operator);
     loggerDebug("ChangeEdorPrintSubmit","setXmlToBqPrint成功");
     Content = "保存成功";
     FlagStr = "true";
    }
    catch (Exception e)
    {
     Content = "保存失败";
     FlagStr = "Fail";
    }      
  } 
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=result%>");
</script>
</html>
