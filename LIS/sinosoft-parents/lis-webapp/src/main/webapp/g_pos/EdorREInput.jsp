<%
/***************************************************************
 * <p>ProName:EdorREInput.jsp</p>
 * <p>Title:  ������Ч</p>
 * <p>Description:������Ч</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-24
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
	<title>������Ч</title>
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
	<script src="./EdorCommonInput.js"></script>
	<script src="./EdorREInput.js"></script>
	<%@include file="./EdorREInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>������Ч</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��Ч����</td>
				<td class=input><input class=codeno name=REtype id=REtype readonly  verify="������Ч����|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return returnShowCodeList('queryexp',[this,REtypeName],[0, 1]);"
					onkeyup="return returnShowCodeList('queryexp',[this,REtypeName],[0, 1]);"><input class=codename name=REtypeName id=REtypeName elementtype=nacessary></td>
				<td class=title>
					<div id=tdGetMoneyName style="display: none">Ӧ�ձ���(Ԫ)</div>
					<div id=tdValDateName style="display: none">��Ч����</div>
				</td>
				<td class=input>
					<div id=tdGetMoney style="display: none"><input class="wid readonly" name=GetMoney readonly></div>
					<div id=tdValDate style="display: none"><input class=coolDatePicker dateFormat=short name=EdorValDate verify="��Ч����|DATE" elementtype=nacessary onClick="laydate({elem: '#EdorValDate'});" id="EdorValDate"><span class="icon"><a onClick="laydate({elem: '#EdorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></div>
				</td>
				<td class=title>
					<div id=tdInterestName style="display: none">��Ϣ(Ԫ)</div>
					<div id=tdDaysName style="display: none">ʣ�๤������(��)</div>
				</td>
				<td class=input>
					<div id=tdInterest style="display: none"><input class="wid common" id=GetInterest name=GetInterest verify="��Ϣ|notnull&NUM"></div>
					<div id=tdDays style="display: none"><input class="wid readonly" name=Days id=Days></div>
					</td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
	<div id="divButton01" name=Button01 style="display: none">
		<input class=cssButton type=button value="��  ��" onclick="saveClick();"></div>
	<div id="divButton02" name=Button02 style="display: ''">
		<input class=cssButton type=button value="��  ��" onclick="top.close();"></div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID> <!-- �����ڵ�ID -->
<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
