<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="ProposalApprove.js"></SCRIPT>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <script src="../common/javascript/MultiCom.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ProposalApproveInit.jsp"%>
  <SCRIPT src="../common/javascript/MulLineInit.js"></SCRIPT>
  <title>投保单复核 </title>
 </head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  var Operator = "<%=tGlobalInput.Operator%>";
  var ComCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();initElementtype();" >
<form action="./ProposalApproveSave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<div id="WorkPool"></div>
	<Input type=hidden name="MissionID" id="MissionID">
	<Input type=hidden name="SubMissionID" id="SubMissionID">
	<Input type=hidden name="ActivityID" id="ActivityID">  
	<input type=hidden name=AgentCode id="AgentCode"> 	 
	<input type=hidden name=ManageCom id="ManageCom">
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
