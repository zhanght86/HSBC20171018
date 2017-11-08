 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
 
//创建人  jw
 
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%> 
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String Content = "";
  String FlagStr = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  loggerDebug("FICertificateRBSave","开始执行Save页面");

  FIDistillRollBack tFIDistillRollBack = new FIDistillRollBack();
     
  String AppNo = request.getParameter("AppNo");


  
  try
  {
  
     VData tVData = new VData();
     tVData.add(tG);
     tVData.add(AppNo);
  
     if(!tFIDistillRollBack.dealProcess(tVData))
     {
        Content =  "失败，原因是:" + tFIDistillRollBack.mErrors.getFirstError();
        FlagStr = "Fail";         
     }
     else
     {
         ExeSQL tExeSQL = new ExeSQL();
         String tSql = "update FIDataFeeBackApp set AppState = '05' where AppNo = '" + AppNo + "'";
         tExeSQL.execUpdateSQL(tSql);
         if(tExeSQL.mErrors.needDealError())
         {    
               Content =  "更新状态失败";
               FlagStr = "Fail";                 
         }    
     }
    
  }
  catch(Exception ex)
  {
    Content =  "失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }


  if (FlagStr=="")
  {
      Content =  "申请确认成功";
      FlagStr = "";
  }
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
