<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-1-20
//������  ��yz
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
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
  //�������
  String FlagStr = "";
  String Content = "";
  
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  

  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
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
      loggerDebug("ChangeEdorPrintSubmit","DisplayToXML ʧ��");
      Content = "DisplayToXMLʧ��";
      FlagStr = "Fail";
    }
  }
  else if (transact.equals("DisplayXMLToPrint")) {
    try 
    {   
      //loggerDebug("ChangeEdorPrintSubmit","aaa: " + request.getParameter("EdorXml"));
       changeXml.setXmlToBqPrint2(request.getParameter("EdorXml"), tEdorNo, tGI.ComCode, tGI.Operator);
       Content = "����ɹ�";
       FlagStr = "true";
    }
    catch (Exception e)
    {
      loggerDebug("ChangeEdorPrintSubmit","setXmlToBqPrint2 ʧ��");
      Content = "setXmlToBqPrint2 ʧ��";
      FlagStr = "Fail";
    }
  }
  else if (transact.equals("PrintToXML")) {
    try 
    {   
       changeXml.getBqPrintToXml(tFileName, tEdorNo);
       loggerDebug("ChangeEdorPrintSubmit","getBqPrintToXml�ɹ�");
       Content = "����ɹ�";
       FlagStr = "true";
    }
    catch (Exception e)
    {
       Content = "����ʧ��";
       FlagStr = "Fail";
    }
  }
  else {
    try 
    {   
     changeXml.setXmlToBqPrint(tFileName, tEdorNo, tGI.ComCode, tGI.Operator);
     loggerDebug("ChangeEdorPrintSubmit","setXmlToBqPrint�ɹ�");
     Content = "����ɹ�";
     FlagStr = "true";
    }
    catch (Exception e)
    {
     Content = "����ʧ��";
     FlagStr = "Fail";
    }      
  } 
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=result%>");
</script>
</html>
