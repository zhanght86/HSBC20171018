<%
/***************************************************************
 * <p>ProName��ScanPagesQueryMainInput.jsp</p>
 * <p>Title��Ӱ�����ѯ</p>
 * <p>Description��Ӱ�����ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%
  String tBussNo = request.getParameter("BussNo");
  String tBussType = request.getParameter("BussType");
%>
<html>
<head>
<title>Ӱ�����ѯ </title>
<script language="javascript">
	var intPageWidth=100%;
	var intPageHeight=100%;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
	window.onunload = resetOperation;
	function resetOperation() {
    	opener.mOperate = 0;
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
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../g_easyscan/ScanPagesQueryInput.jsp?BussNo=<%=tBussNo%>&BussType=<%=tBussType%>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>

