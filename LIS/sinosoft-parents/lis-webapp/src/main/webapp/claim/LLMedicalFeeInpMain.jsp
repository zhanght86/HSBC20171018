<!--Root="../../" -->
<html>
<head>
<title>ҽ�Ƶ�֤¼��</title>
<script language="javascript">

function focusMe()
{
    window.focus();
}
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
        <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLMedicalFeeInpInput.jsp?claimNo=<%=request.getParameter("claimNo")%>&caseNo=<%=request.getParameter("caseNo")%>&custNo=<%=request.getParameter("custNo")%>&accDate=<%=request.getParameter("accDate")%>&medAccDate=<%=request.getParameter("medAccDate")%>&otherAccDate=<%=request.getParameter("otherAccDate")%>&GrpFlag=<%=request.getParameter("GrpFlag") %>">
        <!--��һ��ҳ������-->
        <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
    </frameset>
</frameset>
<noframes>
    <body bgcolor="#ffffff" onblur="focusMe();">
    </body>
</noframes>
</html>
