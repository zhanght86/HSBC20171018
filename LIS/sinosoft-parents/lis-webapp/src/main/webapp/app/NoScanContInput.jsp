<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ScanContInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
//�����¸���
String tContNo="";
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
tFlag=request.getParameter("type");
%>
<script>
var contNo="<%=tContNo%>";          //���˵��Ĳ�ѯ����.
var operator="<%=tGI.Operator%>";   //��¼����Ա
var manageCom="<%=tGI.ManageCom%>"; //��¼��½����
var type="<%=tFlag%>";
var comcode="<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="NoScanContInput.js"></SCRIPT>
<script src="../common/javascript/jquery.workpool.js"></script>
<script src="../common/javascript/MultiCom.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="NoScanContInit.jsp"%>
<title>ɨ��¼��</title>
</head>
<body  onload="initForm();initElementtype();" >
	<form action="./NoScanContInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<div id="SelfPool"></div>
		<a href="javascript:void(0)" class=button onClick="GoToInput();">��ʼ¼��</a>
		<!-- <input value="��ʼ¼��" class="cssButton" type="button" onclick="GoToInput();" /> -->
		<input type="hidden" name="tFlag" value="<%=tFlag%>" id="tFlag">
		<input type="hidden" name="Operator" id="Operator"> 
		<input type="hidden" name=MissionID id="MissionID" value=""> 
		<input type="hidden" name=SubMissionID id="SubMissionID" value="">
		<input type="hidden" name=PrtNo id="PrtNo" value="">
		<input type="hidden" name=ContType id="ContType" value="">
		<input type="hidden" name=ManageCom id="ManageCom" value="">
		<input type="hidden" name=InputDate id="InputDate" value="">
	</form>
    <br><br><br><br><br>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>

