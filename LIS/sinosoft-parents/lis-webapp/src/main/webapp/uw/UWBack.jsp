<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ page import="com.sinosoft.lis.pubfun.GlobalInput" %>
<html>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //记录操作员
    var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <script src="../common/javascript/jquery.workpool.js"></script>
    <SCRIPT src="UWBack.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@ include file="UWBackInit.jsp" %>
    <title>核保订正</title>
</head>

<body onLoad="initForm();">
<form method=post name=fm id="fm" target="fraSubmit" action="./UWBackChk.jsp">
		<div id="WorkPool"></div>
		
        <input type="hidden" id="ProposalNoHide" name="ProposalNoHide" value="">
        <input type="hidden" id="InsuredName" name="InsuredName" value="">
        <input type="hidden" id="AppntName" name="AppntName" value="">
        <input type="hidden" id="MissionId" name="MissionId" value="">
        <input type="hidden" id="SubMissionId" name="SubMissionId" value="">
        <Input type="hidden" class=common id="PrtNo" name=PrtNo>
        <Input type="hidden" class=common id="AgentGroup" name=AgentGroup>
        <Input type="hidden" class=common id="ActivityID" name=ActivityID>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
