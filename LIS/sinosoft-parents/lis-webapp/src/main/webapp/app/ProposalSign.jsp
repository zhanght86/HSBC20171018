<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="ProposalSign.js"></SCRIPT>
  <%@include file="ProposalSignInit.jsp"%>
  <title>ǩ������ </title>
</head>
<body  onload="initForm();" >
	<form action="./ProposalSignSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  		<Input type=hidden name=PrtNo >
  		<Input type=hidden name = LastCheckdeProposalNo >
  	<div id="PublicPool"></div>
  	</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
