 <%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <%@page import="com.sinosoft.lis.fininterface.*"%>
 <%@page import="com.sinosoft.lis.db.*"%>
 <%@page import="com.sinosoft.lis.vdb.*"%>
 <%@page import = "com.sinosoft.lis.pubfun.*"%>
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import="java.net.*"%>
 <%@page import="java.util.*"%>
 <%@page import="java.io.*"%>

<%
  CErrors tError = new CErrors();
  String FlagStr = "";
  String Content = "";
  String flag = "true";
  String sBatchNo = request.getParameter("sBatchNo");//��ˮ����

  loggerDebug("MoveDataCommit",sBatchNo);
 
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");

  if(flag.equals("true"))
  {
       loggerDebug("MoveDataCommit","��ʼ����");

       try 
       {  
           FIMoveDataFromGRPToPRO oMoveDataFromGRPToPRO = new FIMoveDataFromGRPToPRO();
           if(!oMoveDataFromGRPToPRO.moveData(sBatchNo,tGI.Operator))
           {
             FlagStr = "Fail";
             Content = "��ȡ����ʧ�ܣ�ԭ���ǣ�" + oMoveDataFromGRPToPRO.mErrors.getFirstError();
             loggerDebug("MoveDataCommit","����ʧ��");
           }
          else
          {
             FlagStr = "Succ";
             Content = "�����ɹ�"; 
             loggerDebug("MoveDataCommit","�����ɹ�");
          }
          	                     
       }
       catch(Exception ex) 
       {
       
             FlagStr = "Fail";
             Content="����ʧ�ܣ�ԭ����: " + ex.getMessage();   
             loggerDebug("MoveDataCommit",Content);             
       }  	   
      loggerDebug("MoveDataCommit","��������");
  	  
  }


%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initForm();
</script>
</html>

