<%
/***************************************************************
 * <p>ProName:EdorSAInput.jsp</p>
 * <p>Title:  �����ձ�������</p>
 * <p>Description:�����ձ�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorType = request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	String tGrpContNo = request.getParameter("GrpContNo");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
</script>
<html>
<head>
	<title>�����ձ�������</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./EdorSAInput.js"></script>
	<%@include file="./EdorSAInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>��������</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>���Ѽ��㷽ʽ</td>
				<td class=input><input type=hidden name=PremCalMode id=PremCalMode><input class="wid readonly" name=PremCalName id=PremCalName ></td>
				<td class=title>�����(ƽ����)/�����(Ԫ)</td>
				<td class=input>
					<div id=divEnginCost style="display: none"><input class="wid readonly" name=EnginCost id=EnginCost ></div>
					<div id=divEnginArea style="display: none"><input class="wid readonly" name=EnginArea id=EnginArea ></div>
				</td>
				<td class=title>�ܱ���(Ԫ)</td>
				<td class=input><input class="wid readonly" name=FirstPrem id=FirstPrem></td>
			</tr>
			<tr class=common>
				<td class=title>�ѷ������ڴ���</td>
				<td class=input><input class="wid readonly" name=Mtime id=Mtime ></td>
				<td class=title>��������</td>
				<td class=input><input class="wid common" id=DeferDays name=DeferDays verify="��������|NOTNULL&INT&>=1" onblur="getStopDate();" elementtype=nacessary></td>
				<td class=title>���ں󱣵���ֹ����</td>
				<td class=input>
					<input class="coolDatePicker" dateFormat="short" name=StopDate verify="���ں󱣵���ֹ����|NOTNULL&DATE" onblur="getDeferDays();" elementtype=nacessary onClick="laydate({elem: '#StopDate'});" id="StopDate"><span class="icon"><a onClick="laydate({elem: '#StopDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					<input type=hidden name=EndDate id=EndDate>
				</td>
			</tr>
			<tr class=common>
				<td class=title>���µ�����ϵͳ���㱣��</td>
				<td class=input><input class="wid readonly" name=InitPrem id=InitPrem ></td>
				<td class=title>ʵ������(Ԫ)</td>
				<td class=input><input class="wid common" id=GetMoney name=GetMoney verify="ʵ������|NOTNULL&NUM" elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<table class=common>
			<tr class=common>
				<td class=title>����ԭ��</td>
				<td class=input><input class=codeno name=Reason id=Reason readonly  verify="��������ԭ��|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, ReasonName],[0, 1],null,'surrenderSA','concat(codetype,codeexp)','1',180);" 
					onkeyup="return showCodeListKey('queryexp',[this, ReasonName],[0, 1],null,'surrenderSA','concat(codetype,codeexp)','1',180);"><input class=codename name=ReasonName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title>����ԭ������</td>
				<td class=input><textarea cols=70 rows=3 name=ReasonDesc id=ReasonDesc verify="������ֹ����|NOTNULL" elementtype=nacessary></textarea></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
			<div id="divButton01" name=Button01 style="display: none">
				<input class=cssButton type=button value="��  ��" onclick="saveClick();"></div>
			<div id="divButton02" name=Button02 style="display: ''">
				<input class=cssButton type=button value="��  ��" onclick="top.close();"></div>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID> <!-- �����ڵ�ID -->
<Br /><Br /><Br /><Br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
