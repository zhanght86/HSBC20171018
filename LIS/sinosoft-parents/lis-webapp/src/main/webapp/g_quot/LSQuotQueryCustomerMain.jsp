<%
/***************************************************************
 * <p>ProName��LSQuotQueryCustomerMain.jsp</p>
 * <p>Title��׼�ͻ�����ѯ��תҳ</p>
 * <p>Description��׼�ͻ�����ѯ��תҳ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-24
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
			String tSelNo = request.getParameter("SelNo");
			String tMark = request.getParameter("Mark");
			String tPreCustomerNo = request.getParameter("PreCustomerNo");
			String tPreCustomerName = new String(request.getParameter("PreCustomerName").getBytes("ISO8859_1"),"GBK");
			String tQuotNo = request.getParameter("QuotNo");
			String tSrc = "";

			tSrc = "./LSQuotQueryCustomerInput.jsp";
			tSrc += "?SelNo="+ tSelNo+"&Mark="+tMark +"&PreCustomerNo="+tPreCustomerNo +"&PreCustomerName="+tPreCustomerName +"&QuotNo="+tQuotNo;
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
