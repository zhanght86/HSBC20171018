<%
/***************************************************************
 * <p>ProName��LCinsuredQueryMain.jsp</p>
 * <p>Title����ѯ��������Ϣ</p>
 * <p>Description����ѯ��������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<script language="javascript">
function focusMe() {
	window.focus();
}
</script>
<head>
<%
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tInsuredName = new String(StrTool.unicodeToGBK(request.getParameter("InsuredName")));
	String tManageCom = request.getParameter("ManageCom");
	String tSrc = "";

	tSrc = "./LCinsuredQueryInput.jsp";
	tSrc += "?GrpPropNo="+ tGrpPropNo +"&InsuredName="+tInsuredName +"&ManageCom="+ tManageCom+"&Flag=01";
%>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
		
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
		<!--��һ��ҳ������-->
		<frame name="fraNext" src="about:blank" scrolling="auto">
	</frameset>
	
</frameset>

<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();">
	</body>
</noframes>

</html>
