<%
/***************************************************************
 * <p>ProName��LSQuotPlanAllDetailInput.jsp</p>
 * <p>Title��������ϸһ��</p>
 * <p>Description��������ϸһ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.utility.SSRS"%>
<%
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tTranProdType = request.getParameter("TranProdType");
	String tSysPlanCode = request.getParameter("SysPlanCode");
	String tPlanCode = request.getParameter("PlanCode");
	String tMark = request.getParameter("Mark");//1--ֻ��ѯһ����������ϸ
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tTranProdType = "";
	var tSysPlanCode = "<%=tSysPlanCode%>";
	var tPlanCode = "<%=tPlanCode%>";
	var tMark = "<%=tMark%>";
</script>
<html>
<head >
	<title>������ϸһ��</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotPlanAllDetailInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotPlanAllDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="divConfluence" style="display: none">
		<div id="divImp" style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=common><input class=checkbox type=checkbox name=ImpFlag id=ImpFlag onclick="showImp();">�Ƿ�ִ����������</td>
				</tr>
			</table>
			<div id="divImpDetail" style="display: none">
				<table class=common>
					<tr class=common>
						<td class=title>�����ļ�</td>
						<td class=input colspan=5><input class="wid common" type=file id=UploadPath name=UploadPath style="width:400px"  elementtype=nacessary></td>
					</tr>
					<tr class=common>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
				<input class=cssButton type=button value="����������ϸ" onclick="expPlanDetail();">
				<input class=cssButton type=button value="��������ģ��" onclick="expImpTemplate();">
				<input class=cssButton type=button value="�������ѵ���" onclick="impPrem();">
				<input class=cssButton type=button value="������Ϣ�鿴" onclick="showImpLog();">
			</div>
			<br/>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divTotal);">
				</td>
				<td class=titleImg>������Ϣ</td>
			</tr>
		</table>
		<div id="divTotal" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�ܱ���(Ԫ)</td>
					<td class=input><input class="wid readonly" name=SumPrem id=SumPrem readonly></td>
					<td class=title>�ǽ����ձ���(Ԫ)</td>
					<td class=input><input class="wid readonly" name=SumNonMedPrem id=SumNonMedPrem readonly></td>
					<td class=title nowrap>�����ձ���(�����ؼ�)(Ԫ)</td>
					<td class=input><input class="wid readonly" name=SumMedPrem id=SumMedPrem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>�ؼ�����(Ԫ)</td>
					<td class=input><input class="wid readonly" name=SumPerIllPrem id=SumPerIllPrem readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlanDetail);">
			</td>
			<td class=titleImg>������ϸ</td>
		</tr>
	</table>
	<div id="divShowAllPlan" class=maxbox1>
	</div>
	<div id="divTurnPage"  style="display: ''">
		<table align=center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="lastPage();">
			<input class=readonly name=PageInfo  id=PageInfo readonly value="">
		</table>
	</div>
	
	<input class=cssButton type=button value="��  ��" onclick="top.close();">
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
