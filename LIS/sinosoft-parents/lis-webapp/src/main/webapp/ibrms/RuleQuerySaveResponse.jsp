
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：BookAddSave.jsp
//程序功能：
//创建日期：2008-09-02 15:12:33
//创建人  ：dxy程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">

</head>
<script language="javascript">
   
		parent.fraInterface.afterSubmit('<%=request.getParameter("flag")%>','<%=request.getParameter("Content")%>');

</script>
</html>

