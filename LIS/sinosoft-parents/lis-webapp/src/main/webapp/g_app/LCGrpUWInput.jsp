<%
/***************************************************************
 * <p>ProName��LCGrpUWInput.jsp</p>
 * <p>Title���˹��˱�</p>
 * <p>Description���˹��˱�<</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

	String tFlag = request.getParameter("Flag");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tContPlanType = request.getParameter("ContPlanType");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tContPlanType = "<%=tContPlanType%>";
</script>
<html>
<head >
	<title>�˹��˱�</title>
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
	<script src="./LCGrpUWInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCGrpUWInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action=" " target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpContNews);">
			</td>
			<td class=titleImg>����Ͷ������Ϣ</td>
		</tr>
	</table>
	<div id="divGrpContNews" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�б�����</td>
				<td class=input><input class="wid readonly" name=ManageCom id=ManageCom value="" readonly></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid readonly" name=GrpName id=GrpName value="" readonly></td>
				<td class=title>������������</td>
				<td class=input><input class="wid readonly" name=SaleChnl id=SaleChnl value="" readonly></td>
			</tr>
			<tr class=common>
				<td class=title>Ͷ����������</td>
				<td class=input><Input class="wid readonly" name=ApplyDate id=ApplyDate value="" readonly></td>
				<td class=title>��Ч����</td>
				<td class=input><div id="divVal" style="display: ''"><input class="coolDatePicker" dateFormat="short" verify="��Ч����|DATE" name=ValDate elementtype=nacessary onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></div>
					<div id="divValS" style="display: ''">���ѵ��˴���</div>
					</td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid readonly" name=AppntNum id=AppntNum value="" readonly></td>
			</tr>
			<tr class=common>
				<td class=title>Ӧ���ܱ���(Ԫ)</td>
				<td class=input><input class="wid readonly" name=ShouldPrem id=ShouldPrem value="" readonly></td>
				<td class=title>Ͷ������</td>
				<td class=input><input class="wid readonly" name=tGrpPropNo id=tGrpPropNo value="<%=tGrpPropNo%>" readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
	<input class=cssButton value="������ϸ" type=button onclick="policyQuery();">
	<input class=cssButton value="��Ա�嵥��ѯ" type=button onclick="insuredQuery();">
	<input class=cssButton value="���շ�����ѯ" type=button onclick="showPlanQuery();">
	<input class=cssButton value="��Ա��Ϣ�ֲ�" type=button onclick="insuredStatistic();">
	<input class=cssButton value="Ӱ�����ѯ" type=button onclick="queryScanPage();">
	<input class=cssButton value="���������" type=button onclick="goToQuestion();">
	<input class=cssButton type=button value="��������" onclick="showAttachment();">
	<br>
	<br>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divContDeal);">
			</td>
			<td class=titleImg>���屣���˱���Ϣ</td>
		</tr>
	</table>
	<div id="divContDeal" class=maxbox1 style="display: ''">
		<input class=cssButton value="�˱�����" type=button onclick="goToGErr();">
		<input class=cssButton value="�������ο���" type=button onclick="claimDutyControl();">
		<br/>
		<input class=cssButton value="�����˱�ȷ��" type=button onclick="uwConfirm();">
		<input class=cssButton value="��  ��" type=button onclick="goBack();">
	</div>
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- ��������ID -->
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- �����ڵ�ID -->
	<input type=hidden name=GrpPropNo value="<%=tGrpPropNo%>">
	<input type=hidden name=ContPlanType value="<%=tContPlanType%>">
	<input type=hidden name=ValDateType>
	<input type=hidden name=ReinsFlag value="0">

<br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
