<%
//程序名称
//程序功能：
//创建日期：
//创建人  ：jw
//更新记录：
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.fininterface.FIDistillRollBack"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%

  CErrors tError = null;
  String Content = "";
  String FlagStr = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");

  FIDistillRollBack tFIDistillRollBack = new FIDistillRollBack();
  int tIndex = 0;
  String tRBResultGrids[] = request.getParameterValues("InpRBResultGridSel");
  String tBatchNo[] = request.getParameterValues("RBResultGrid1");
  String BatchNo = "";
 
 

  for(tIndex = 0; tIndex < tRBResultGrids.length; tIndex++ )
  {
     if(tRBResultGrids[tIndex].equals("1"))
     {
          BatchNo = tBatchNo[tIndex];
          break;
     }
  }
  
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(BatchNo);
  

  try
  {
     if(!tFIDistillRollBack.dealProcess(tVData))
     {
        Content =  "失败，原因是:" + tFIDistillRollBack.mErrors.getFirstError();
        FlagStr = "Fail";         
     }
  }
  catch(Exception ex)
  {
    Content =  "失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    Content = "数据回退成功";
    FlagStr = "Succ";
  }
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
