<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>
<script>
  Operator = "<%=tGlobalInput.Operator%>";
  ComCode = "<%=tGlobalInput.ComCode%>";
  ManageCom = "<%=tGlobalInput.ManageCom%>";
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="MasterCenter.js"></SCRIPT>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="MasterCenterInit.jsp"%>
  <title>管理中心 </title>
 </head>

<body  onload="initForm();initElementtype();" >
<form action="./ProposalApproveSave.jsp" method=post name=fm id="fm" target="fraSubmit">	
    <div id ="ManPublicPool"></div>
    <br/>
    <div id="ManPrivatePool"></div>
	<!--#########################  隐藏表单区域   ##############################-->
	<Input type=hidden id="Operator" name="Operator" value="">
    <Input type=hidden id="ComCode" name="ComCode" value="">
	<Input type=hidden id="MissionID" name="MissionID" value="">
	<Input type=hidden id="SubMissionID" name="SubMissionID" value="">
	<Input type=hidden id="ActivityID" name="ActivityID" value=""> 
	<Input type=hidden id="hiddenContNo" name="hiddenContNo" value="">   
	<Input type=hidden id="PrtNo" name="PrtNo" value=""> 
	<Input type=hidden id="ManageCom" name="ManageCom" value="">   		 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


