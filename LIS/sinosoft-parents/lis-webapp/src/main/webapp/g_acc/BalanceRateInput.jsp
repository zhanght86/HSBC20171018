<%
/***************************************************************
 * <p>ProName��BalanceRateInput.jsp</p>
 * <p>Title���������ʹ���</p>
 * <p>Description���������ʹ���</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2014-07-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>�������ʹ���</title>
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
	<script src="./BalanceRateInput.js"></script>
	<%@include file="./BalanceRateInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./BalanceRateSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRiskInfo);">
			</td>
			<td class=titleImg>����������Ϣ</td>
		</tr>
	</table>
	
	<div id="divRiskInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>����</td>
				<td class=input><input class=codeno name=QueryRiskCode id=QueryRiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('ulriskcode',[this,QueryRiskName],[0,1],null,null,null,1,300)" onkeyup="return showCodeListKey('ulriskcode',[this,QueryRiskName],[0,1],null,null,null,1,300)">
				<input class=codename name=QueryRiskName id=QueryRiskName></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short" onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divBalanceRateGrid);">
			</td>
			<td class=titleImg>���������б�</td>
		</tr>
	</table>
	<div id=divBalanceRateGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanBalanceRateGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="��������" onclick="exportData();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBalanceRateInfo);">
			</td>
			<td class=titleImg>����������Ϣ</td>
		</tr>
	</table>
	
	<div id="divBalanceRateInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>����</td>
				<td class=input><input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="����|NOTNULL&CODE:ulriskcode" ondblclick="return showCodeList('ulriskcode',[this,RiskName],[0,1],null,null,null,1,300)" onkeyup="return showCodeListKey('ulriskcode',[this,RiskName],[0,1],null,null,null,1,300)"><input class=codename name=RiskName elementtype=nacessary></td>
				<td class=title>�˻�</td>
				<td class=input><input class=codeno name=InsuAccNo id=InsuAccNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="�˻�|NOTNULL&CODE:fundcode" onclick="return beforQueryCode(this, RiskCode)" ondblclick="return showCodeList('fundcode',[this,InsuAccName],[0,1],null,'1 and a.riskcode = #'+fm.RiskCode.value+'#','1',1)" onkeyup="return showCodeListKey('fundcode',[this,InsuAccName],[0,1],null,'1 and a.riskcode = #'+fm.RiskCode.value+'#','1',1)">
				<input class=codename name=InsuAccName id=InsuAccName elementtype=nacessary></td>
				<td class=title></td>
				<td class=title></td>
			</tr>
			<tr class=common>
				<td class=title>Ӧ��������</td>
				<td class=input><input class=coolDatePicker name=SRateDate dateFormat="short" verify="Ӧ��������|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#SRateDate'});" id="SRateDate"><span class="icon"><a onClick="laydate({elem: '#SRateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>ʵ�ʹ�������</td>
				<td class=input><input class=coolDatePicker name=ARateDate dateFormat="short" verify="ʵ�ʹ�������|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#ARateDate'});" id="ARateDate"><span class="icon"><a onClick="laydate({elem: '#ARateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>���ʿ�ʼ����</td>
				<td class=input><input class=coolDatePicker name=StartDate dateFormat="short" verify="���ʿ�ʼ����|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=RateIntv id=RateIntv verify="��������|NOTNULL&CODE:rateintv" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('rateintv',[this,RateIntvName],[0,1],null,null,null,1)" onkeyup="return showCodeListKey('rateintv',[this,RateIntvName],[0,1],null,null,null,1)"><input class=codename name=RateIntvName elementtype=nacessary></td>
				<td class=title>��֤����</td>
				<td class=input><input class="wid common" name=Rate id=Rate verify="��֤����|NUM&VALUE>=0&VALUE<1" elementtype=nacessary></td>
				<td class=title></td>
				<td class=title></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="��  ��" onclick="insertClick();">
		<input class=cssButton type=button value="��  ��" onclick="updateClick();">
		<input class=cssButton type=button value="��  ��" onclick="resetClick();">
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=RateYear>
	<input type=hidden name=EndDate>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
