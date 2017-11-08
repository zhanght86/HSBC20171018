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
  <%@page import="com.sinosoft.lis.fininterface.FIReDistillMain"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%

  String Content = "";
  String FlagStr = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  loggerDebug("FIDistillDataSave","开始执行Save页面");
  FIReDistillMain tFIReDistillMain = new FIReDistillMain();
     

  String AppNo = request.getParameter("AppNo");
  String BusinessNo = request.getParameter("BusinessNo");
  String CertificateID = request.getParameter("CertificateId");	

    
   
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(CertificateID);
  tVData.add(BusinessNo);
  tVData.add(AppNo);


  
  try
  {
    if(!tFIReDistillMain.dealProcess(tVData))
    {
        Content =  "失败，原因是:" + tFIReDistillMain.mErrors.getFirstError();
        FlagStr = "Fail";        
    }
    
  }
  catch(Exception ex)
  {
    Content =  "失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  if (FlagStr=="")
  {
      Content =  "红冲数据采集成功";
      FlagStr = "";
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
