<%
/***************************************************************
 * <p>ProName��EdorUWDetailInput.jsp</p>
 * <p>Title����ȫ�˱�</p>
 * <p>Description����ȫ�˱�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorNo = request.getParameter("EdorNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tEdorNo = "<%=tEdorNo%>";
</script>
<html>
<head >
	<title>��ȫ�˱�</title>
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
	<script src="./EdorUWDetailInput.js"></script>
	<script src="./EdorCommonInput.js"></script>
	<%@include file="./EdorUWDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorAppInfo);">
			</td>
		 	<td class=titleImg>��ȫ������Ϣ</td>
		</tr>
	</table>
	<div id="divEdorAppInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��ȫ�����</td>
				<td class=input><input class="wid readonly" name=EdorAppNo id=EdorAppNo value="<%=tEdorAppNo%>" readonly></td>
				<td class=title>��ȫ������</td>
				<td class=input><input class="wid readonly" name=EdorNo id=EdorNo value="<%=tEdorNo%>" readonly></td>
				<td class=title>��ȫ���뷽ʽ</td>
				<td class=input><input class="wid readonly" name=AppMode id=AppMode readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ���������</td>
				<td class=input><input class="wid readonly" name=AppDate id=AppDate readonly></td>
				<td class=title>��˾��������</td>
				<td class=input><input class="wid readonly" name=ReceiveDate id=ReceiveDate readonly></td>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=AcceptDate id=AcceptDate readonly></td>
			</tr>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid readonly" name=PolicyNo id=PolicyNo readonly></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid readonly" name=AppntName id=AppntName readonly></td>
				<td class=title>������Ч����</td>
				<td class=input><input class="wid readonly" name=ValDate id=ValDate readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�ɷѷ�ʽ</td>
				<td class=input><input class="wid readonly" name=PayIntv id=PayIntv readonly></td>
				<td class=title>�Ƿ񶨽�</td>
				<td class=input><input class="wid readonly" name=BanlanceFlag id=BanlanceFlag readonly></td>
				<td class=title>������˷ѹ���</td>
				<td class=input><input class="wid readonly" name=AfterClmRule id=AfterClmRule readonly></td>
			</tr>
		</table>
		<input class=cssButton value="������ϸ��ѯ" type=button onclick="policyDetClick();">
		<input class=cssButton value="��Ա�嵥��ѯ" type=button onclick="insuredQuery();">
		<input class=cssButton value="���շ�����ѯ" type=button onclick="showPlanQuery();">
		<input class=cssButton value="��Ա��Ϣ�ֲ�" type=button onclick="insuredStatistic();">
		<input class=cssButton value="��Լ��Ϣ" type=button onclick="queryGrpSpec();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorTypeGrid);">
			</td>
			<td class=titleImg>��ȫ��Ŀ��Ϣ</td>
		</tr>
	</table>
	<div id="divEdorTypeGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanEdorTypeGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divEdorTypeGridPage" style= "display: ''">
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</center>
		</div>

		<input class=cssButton type=button value="��ȫ��Ŀ��ϸ" onclick="detailClick();">
		<input class=cssButton type=button value="Ӱ�����ѯ" onclick="queryScanPage();">
		<input class=cssButton type=button value="���������" onclick="goToQuestion();">
		<input class=cssButton type=button value="��������" onclick="showAttachment();">
		<input class=cssButton type=button value="����Ԥ��" onclick="print();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorUW);">
			</td>
			<td class=titleImg>��ȫ�˱���Ϣ</td>
		</tr>
	</table>
	<div id="divEdorUW" class=maxbox1 style="display: ''">
		<!-- �ٱ����ٽ����ж�
		<input class=cssButton value="�ٱ�����" type=button onclick="gotoReins();">
		<br>--->
		<input class=cssButton value="�˱�����" type=button onclick="goToGErr();">
		<br>
		<input class=cssButton value="�����˱�ȷ��" type=button onclick="uwConfirm();">
		<input class=cssButton value="��  ��" type=button onclick="returnClick();">
	</div>

	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=HidGrpContNo>
	<input type=hidden name=MissionID value="<%=tMissionID%>">
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>">
	<input type=hidden name=ActivityID value="<%=tActivityID%>">
	<input type=hidden name=ReinsFlag value="0">
	<input type=hidden name=GrpPropNo >
	<br /><br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
