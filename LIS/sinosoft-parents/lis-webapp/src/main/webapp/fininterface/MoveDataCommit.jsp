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
  String sBatchNo = request.getParameter("sBatchNo");//流水号码

  loggerDebug("MoveDataCommit",sBatchNo);
 
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");

  if(flag.equals("true"))
  {
       loggerDebug("MoveDataCommit","开始提数");

       try 
       {  
           FIMoveDataFromGRPToPRO oMoveDataFromGRPToPRO = new FIMoveDataFromGRPToPRO();
           if(!oMoveDataFromGRPToPRO.moveData(sBatchNo,tGI.Operator))
           {
             FlagStr = "Fail";
             Content = "提取数据失败，原因是：" + oMoveDataFromGRPToPRO.mErrors.getFirstError();
             loggerDebug("MoveDataCommit","操作失败");
           }
          else
          {
             FlagStr = "Succ";
             Content = "提数成功"; 
             loggerDebug("MoveDataCommit","操作成功");
          }
          	                     
       }
       catch(Exception ex) 
       {
       
             FlagStr = "Fail";
             Content="提数失败，原因是: " + ex.getMessage();   
             loggerDebug("MoveDataCommit",Content);             
       }  	   
      loggerDebug("MoveDataCommit","提数结束");
  	  
  }


%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initForm();
</script>
</html>

