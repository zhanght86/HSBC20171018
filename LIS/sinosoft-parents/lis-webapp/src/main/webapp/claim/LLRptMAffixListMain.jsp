<%
//Name:LLMAffixListMain.jsp
//Function����֤���ɼ�����������
//Date��2005-05-21 17:44:28
//Author ����־��
%>
<html>
<head>
<title>���ⰸ������¼��</title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();	
</script>

</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
    <!--����ͻ��˱��������򣬸����������-->
    <frame name="VD" src="../common/cvar/CVarData.jsp">
  
    <!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
    <frame name="EX" src="../common/cvar/CExec.jsp">
  
    <frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
    <frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
    <frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
    <!--�˵�����-->
        <frame name="fraMenu" scrolling="yes" noresize src="about:blank">
        <!--��������-->
    
	<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLRptMAffixListInput.jsp?claimTypes=<%=request.getParameter("claimTypes")%>&CaseNo=<%=request.getParameter("CaseNo")%>&whoNo=<%=request.getParameter("whoNo")%>&Proc=<%=request.getParameter("Proc")%>&Reason=<%=request.getParameter("Reason")%>">        
        <!--��һ��ҳ������-->
        <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
    </frameset>
</frameset>
<noframes>
    <body bgcolor="#ffffff">
    </body>
</noframes>
</html>
