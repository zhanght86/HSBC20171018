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
  <%@page import="com.sinosoft.lis.fininterface.FIDistillMain"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  VData tVData  = new VData();
  String Content = "";
  String FlagStr = "";
  String StartDate = "";
  String EndDate = "";
  String CertificateID = "";
  FIDistillMain tFIDistillMain = new FIDistillMain();  
  GlobalInput tG = new GlobalInput();  
  String szTemplatePath = application.getRealPath("") + "/fininterface/log/";	//模板路径  


  tG=(GlobalInput)session.getValue("GI");
  StartDate = request.getParameter("Bdate");
  EndDate = request.getParameter("Edate");
  CertificateID = request.getParameter("CertificateId");
  tVData.add(tG);
  tVData.add(StartDate);
  tVData.add(EndDate);
  tVData.add(CertificateID);
  tVData.add(szTemplatePath);
  String strBatchno = "";

  String remark = "";
  try
  {
     if(!tFIDistillMain.dealProcess(tVData))
     {
        Content = "失败，原因是:" +tFIDistillMain.mErrors.getFirstError();
        FlagStr = "Fail";     
     }
  }
  catch(Exception ex)
  {
    Content = "执行异常，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
      Content = "数据采集成功";
      ExeSQL oExeSQL = new ExeSQL();
      if(tFIDistillMain.BatchNo != null && !tFIDistillMain.BatchNo.equals("")){
         String strSQL  = " select count(1) from firuledealerrlog  where datasourcebatchno = '" + tFIDistillMain.BatchNo.trim() + "'";
        int countNum = Integer.parseInt(oExeSQL.getOneValue(strSQL));
		if (countNum > 0) {
			Content = Content + ",但存在一些不规范数据,需要人工处理。";
		} 
      }
      FlagStr = "Succ";
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
