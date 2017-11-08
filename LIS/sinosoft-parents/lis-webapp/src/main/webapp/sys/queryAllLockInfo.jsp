<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%

%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="java.util.*"%>
  
<%
 

		String tResult =  PubConcurrencyLock.getAllLockData();
%>
	     
<html>
<script language="javascript">
	///alert("1112");
 parent.fraInterface.displayData('<%=tResult%>');
</script>
</html>
