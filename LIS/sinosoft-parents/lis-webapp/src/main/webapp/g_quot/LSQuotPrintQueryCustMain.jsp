<%
/***************************************************************
 * <p>ProName��LSQuotPrintQueryCustMain.jsp</p>
 * <p>Title�����۵���ӡ--��ѯ׼�ͻ�����</p>
 * <p>Description�����۵���ӡ--��ѯ׼�ͻ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>׼�ͻ����Ʋ�ѯ</title>
	<script language="javascript">
		var intPageWidth = screen.availWidth;
		var intPageHeight = screen.availHeight;
		window.resizeTo(intPageWidth,intPageHeight);
		window.focus();
	</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱�������,�����������-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ���������,�����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit" scrolling="yes" noresize src="about:blank">
	<frame name="fraTitle" scrolling="no" noresize src="about:blank">
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<%
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tSrc = "";

			tSrc = "./LSQuotPrintQueryCustInput.jsp";
			tSrc += "?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&QuotType="+tQuotType;
			
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
			<!--��һ��ҳ������-->
			<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
