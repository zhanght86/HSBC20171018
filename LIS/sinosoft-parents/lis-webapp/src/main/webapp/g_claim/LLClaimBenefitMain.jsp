<%
/***************************************************************
 * <p>ProName：LLClaimNoAcceptMain.jsp</p>
 * <p>Title：受益人信息</p>
 * <p>Description：受益人信息</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-04-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
	<title>受益人信息</title>
	<script language="javascript">
		function focusMe(){
			window.focus();
		}
	</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,100%,*,0">	
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<%
		  String tGrpRgtNo	= request.getParameter("GrpRgtNo");
		  String tRgtNo	= request.getParameter("RgtNo");
		  String tCustomerNo	= request.getParameter("CustomerNo");
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLClaimBenefitInput.jsp?GrpRgtNo=<%=tGrpRgtNo%>&RgtNo=<%=tRgtNo%>&CustomerNo=<%=tCustomerNo%>">
		
	<!--下一步页面区域-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
