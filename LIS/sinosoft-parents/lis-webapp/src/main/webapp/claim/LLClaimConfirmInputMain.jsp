<!--Root="../../" -->
<html>
    <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<head>
<title>��ͬ����</title>
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
        
        <%
              String tSrc = "LLClaimConfirmInput.jsp";
              tSrc += "?claimNo=" + request.getParameter("claimNo");
              tSrc += "&MissionID=" + request.getParameter("MissionID");              
              tSrc += "&SubMissionID=" + request.getParameter("SubMissionID");
              tSrc += "&BudgetFlag=" + request.getParameter("BudgetFlag");
              tSrc += "&AuditPopedom=" + request.getParameter("AuditPopedom");
              tSrc += "&Auditer=" + request.getParameter("Auditer");
              tSrc += "&ActivityID=" + request.getParameter("ActivityID");
              tSrc += "&GrpRptNo=" + request.getParameter("GrpRptNo");
              tSrc += "&RgtClass=" + request.getParameter("RgtClass");
              tSrc += "&RgtObj=" + request.getParameter("RgtObj");
              loggerDebug("LLClaimConfirmInputMain",tSrc);
        %>        
        
        <frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">                 
        <!--��һ��ҳ������-->
        <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
    </frameset>
</frameset>
<noframes>
    <body bgcolor="#ffffff" onblur="focusMe();>
    </body>
</noframes>
</html>
