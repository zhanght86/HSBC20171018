<%
/***************************************************************
 * <p>ProName��LDUWPopedomInput.jsp</p>
 * <p>Title���˱�Ȩ�޹���</p>
 * <p>Description���˱�Ȩ�޹���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tOperator = tGI.Operator;
	String tManageCom = tGI.ManageCom;
%>
<script>
	var tOperator = "<%=tOperator%>";
	var tManageCom = "<%=tManageCom%>";
</script>
<html>
<head>
	<title>�˱�Ȩ�޹���</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LDUWPopedomInput.js"></script>
	<%@include file="./LDUWPopedomInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWPopedom);">
				</td>
				<td class=titleImg>��ѯ����</td>
			</tr>
		</table>
		<div id="divUWPopedom" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>Ȩ�޼���</td>
					<td class=input><input class="wid common" name=PopedomLevel1 id=PopedomLevel1></td>
					<td class=title>Ȩ������</td>
					<td class=input><input class="wid common" name=PopedomName1 id=PopedomName1 ></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton type=button value="��  ѯ" onclick="querySubmit();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWPopedomList);">
				</td>
				<td class=titleImg>�˱�Ȩ���б�</td>
			</tr>
		</table>
		<div id="divUWPopedomList" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanUWPopedomGrid"></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</center>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWUserInfo);">
				</td>
				<td class=titleImg>�˱�Ȩ����Ϣ</td>
			</tr>
		</table>
		<div id="divUWUserInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>Ȩ�޼��� <span style="color: red">���磺L01��</span></td>
					<td class=input><input class="wid common" name=PopedomLevel verify="Ȩ�޼���|NOTNULL" elementtype=nacessary></td>
					<td class=title>Ȩ������</td>
					<td class=input><input class="wid common" name=PopedomName verify="Ȩ������|NOTNULL" elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>				
				</tr>
				<tr class=common>
					<td class=title>�������ձ���</td>
					<td class=input><input class="wid common" name=PerLifeAmnt verify="�������ձ���|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>	
					<td class=title>���������ձ���</td>
					<td class=input><input class="wid common" name=PerAcciAmnt verify="���������ձ���|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
					<td class=title>�����ؼ�����</td>
					<td class=input><input class="wid common" name=PerIllAmnt verify="�����ؼ�����|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>����ҽ���ձ���</td>
					<td class=input><input class="wid common" name=PerMedAmnt verify="����ҽ���ձ���|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>	
					<td class=title>���ѹ�ģ</td>
					<td class=input><input class="wid common" name=PremScale verify="���ѹ�ģ|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
					<td class=title>���շ��ʸ���</td>
					<td class=input><input class="wid common" name=MainPremRateFloat verify="���շ��ʸ���|NOTNULL&NUM" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>ҽ���շ��ʸ���</td>
					<td class=input><input class="wid common" name=MedPremRateFloat verify="ҽ���շ��ʸ���|NOTNULL&NUM" elementtype=nacessary></td>	
					<td class=title>��Ч����</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=ValDate verify="��Ч����|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>��ֹ����</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=EndDate verify="��ֹ����|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
			</table>
			<input class=cssButton type=button value="��  ��" name="AddButton" onclick="addSubmit();">
			<input class=cssButton type=button value="��  ��" name="ModButton" onclick="modSubmit();">
			<input class=cssButton type=button value="ɾ  ��" name="DelButton" onclick="delSubmit();">
		</div>
	</div>
	<input type=hidden name=Operate>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
