<%
/***************************************************************
 * <p>ProName��LLClaimLastCaseMain.jsp</p>
 * <p>Title����ȫ��ѯ</p>
 * <p>Description����ȫ��ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-04-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
	<title>��ȫ��ѯ</title>
	<script language="javascript">
		function focusMe(){
			window.focus();
		}
	</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,100%,*,0">	
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<%
		  String tCustomerNo	= request.getParameter("CustomerNo");		  
		  String tMode = request.getParameter("Mode");
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLClaimQueryEdorInput.jsp?CustomerNo=<%=tCustomerNo%>&Mode=<%=tMode%>">
		
	<!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
