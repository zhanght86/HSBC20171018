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
  <%@page import="com.sinosoft.lis.fininterface.FICertificateRBProduceMain"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%

  String Content = "";
  String FlagStr = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  loggerDebug("FICertificateDataSave","开始执行Save页面");
  FICertificateRBProduceMain tFICertificateRBProduceMain = new FICertificateRBProduceMain();
     

  String AppNo = request.getParameter("AppNo");
  String CertificateID = request.getParameter("CertificateId");	

  //兼容中科软财务系统临时代码，其他项目实施可以去掉
  String tSql = "update FIAboriginalData  set TypeFlag06 = (select b.codealias from Ficodetrans b where b.codetype = 'RiskTrans' and b.code =  StringInfo05 ) where StringInfo05 is not null and BatchNo = '" + AppNo + "'";
  ExeSQL tExeSQL = new ExeSQL();
  tExeSQL.execUpdateSQL(tSql); 
  //中科软财务系统临时代码，其他项目实施可以去掉    
   
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(CertificateID);
  tVData.add(AppNo);


  
  try
  {
    if(!tFICertificateRBProduceMain.dealProcess(tVData))
    {
        Content =  "失败，原因是:" + tFICertificateRBProduceMain.mErrors.getFirstError();
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
      Content =  "红冲凭证提取成功";
      FlagStr = "";
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
