<%
/***************************************************************
 * <p>ProName��LLClaimQueryCustMain.jsp</p>
 * <p>Title����ҽ���˵�</p>
 * <p>Description����ҽ���˵�</p>
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
	<title>��ҽ���˵�</title>
	<script language="javascript">
		function focusMe(){
			window.focus();
		}
	</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,100%,*,0">	
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<%
			String szSrc = "./LLClaimNoMedicalInput.jsp";  
			szSrc += "?RgtNo=" + request.getParameter("RgtNo");  //������
			szSrc += "&CaseNo=" + request.getParameter("CaseNo"); //������  
			szSrc += "&CustomerNo=" + request.getParameter("CustomerNo"); //�����˱���       
			szSrc += "&AccidentDate=" + request.getParameter("AccidentDate"); //�����˱���       
			szSrc += "&Mode=" + request.getParameter("Mode");
     %>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= szSrc %>">
	<!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
