<%
/***************************************************************
 * <p>ProName��LLClaimPreinvestInputMain.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-04-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
	<title>��������</title>
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
			String szSrc = "./LLClaimPreinvestInput.jsp";  
			szSrc += "?GrpRgtNo=" + request.getParameter("GrpRgtNo");  //���尸����
			szSrc += "&RgtNo=" + request.getParameter("RgtNo"); //������  
			szSrc += "&custNo=" + request.getParameter("custNo"); //�����˱���       
			szSrc += "&surveyType=" + request.getParameter("surveyType"); //��������
			szSrc += "&initPhase=" + request.getParameter("initPhase"); //����׶�
			szSrc += "&AppntNo=" + request.getParameter("AppntNo"); //Ͷ���˺���
			szSrc += "&ManageCom=" + request.getParameter("ManageCom"); //�������
			szSrc += "&Mode=" + request.getParameter("Mode"); //�������
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
