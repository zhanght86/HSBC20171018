<%
//程序名称：TempFinFeeCrTempNo.jsp
//程序功能：
//创建日期：2010-04-15
//创建人  ：朱伟
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.finfee.*"%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  
  loggerDebug("TempFinFeeCrTempNo","开始执行Save页面");
  String tLimit="" ;
  String ManageCom = "";
  ManageCom = request.getParameter("ManageCom");
  tLimit = PubFun.getNoLimit(ManageCom);
  String tTempNo = "TS"+PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// 系统产生暂收据号

%>
<html>
<script language="javascript">
	parent.fraInterface.AfterCreateTempNo("<%=tTempNo%>");
</script>
</html>
