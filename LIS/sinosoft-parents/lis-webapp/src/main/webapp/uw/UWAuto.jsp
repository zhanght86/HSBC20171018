<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var ComCode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="UWAuto.js"></SCRIPT>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWAutoInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  <title>�Զ��˱� </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./UWAutoChk.jsp">
          <INPUT type=hidden id="PrtNo" name= PrtNo value= "">
          <INPUT type=hidden id="AgentName" name= AgentName value= "">
          <INPUT type=hidden id="AppntName" name= AppntName  value= "">
          <INPUT type=hidden id="InsuredName" name= InsuredName  value= "">
          <INPUT type=hidden id="MissionID" name= MissionID  value= "">
          <INPUT type=hidden id="SubMissionID" name= SubMissionID  value= "">
          <INPUT type=hidden id="ActivityID" name= ActivityID  value= "">
          <INPUT type=hidden id="SubNoticeMissionID" name= SubNoticeMissionID  value= "">
          <input type=hidden id="ManageCom" name=ManageCom>
          <input type=hidden id="AgentCode" name=AgentCode>
          <input type=hidden id="AgentGroup" name=AgentGroup>
 	<div id="PublicPool"></div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
