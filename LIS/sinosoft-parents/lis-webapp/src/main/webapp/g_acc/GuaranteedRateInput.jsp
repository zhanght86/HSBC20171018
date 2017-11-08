<%
/***************************************************************
 * <p>ProName：GuaranteedRateInput.jsp</p>
 * <p>Title：保证利率公布</p>
 * <p>Description：保证利率公布</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-07-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>保证利率公布</title>
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
	<script src="./GuaranteedRateInput.js"></script>
	<%@include file="./GuaranteedRateInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./GuaranteedRateSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRiskInfo);">
			</td>
			<td class=titleImg>保证利率信息</td>
		</tr>
	</table>
	
	<div id="divRiskInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>险种</td>
				<td class=input><input class=codeno name=QueryRiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('ulriskcode',[this,QueryRiskName],[0,1],null,null,null,1,300)" onkeyup="return showCodeListKey('ulriskcode',[this,QueryRiskName],[0,1],null,null,null,1,300)"><input class=codename name=QueryRiskName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="查  询" onclick="queryClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGuaranteedRateGrid);">
			</td>
			<td class=titleImg>保证利率列表</td>
		</tr>
	</table>
	<div id=divGuaranteedRateGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanGuaranteedRateGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="导出数据" onclick="exportData();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGuaranteedRateInfo);">
			</td>
			<td class=titleImg>保证利率信息</td>
		</tr>
	</table>
	
	<div id="divGuaranteedRateInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>险种</td>
				<td class=input><input class=codeno name=RiskCode verify="险种|NOTNULL&CODE:ulriskcode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('ulriskcode',[this,RiskName],[0,1],null,null,null,1,300)" onkeyup="return showCodeListKey('ulriskcode',[this,RiskName],[0,1],null,null,null,1,300)"><input class=codename name=RiskName elementtype=nacessary></td>
				<td class=title>账户</td>
				<td class=input><input class=codeno name=InsuAccNo verify="账户|NOTNULL&CODE:fundcode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onclick="return beforQueryCode(this, RiskCode)" ondblclick="return showCodeList('fundcode',[this,InsuAccName],[0,1],null,'1 and a.riskcode = #'+fm.RiskCode.value+'#','1',1)" onkeyup="return showCodeListKey('fundcode',[this,InsuAccName],[0,1],null,'1 and a.riskcode = #'+fm.RiskCode.value+'#','1',1)"><input class=codename name=InsuAccName elementtype=nacessary></td>
				<td class=title></td>
				<td class=title></td>
			</tr>
			<tr class=common>
				<td class=title>应公布日期</td>
				<td class=input><input class=coolDatePicker name=SRateDate dateFormat="short" verify="应公布日期|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#SRateDate'});" id="SRateDate"><span class="icon"><a onClick="laydate({elem: '#SRateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>实际公布日期</td>
				<td class=input><input class=coolDatePicker name=ARateDate dateFormat="short" verify="实际公布日期|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#nacessary'});" id="nacessary"><span class="icon"><a onClick="laydate({elem: '#nacessary'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>保证年度（2005）</td>
				<td class=input><input class="wid common" name=RateYear verify="保证年度|INT&LEN=4" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>利率类型</td>
				<td class=input><input class=codeno name=RateIntv verify="利率类型|NOTNULL&CODE:rateintv" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('rateintv',[this,RateIntvName],[0,1],null,null,null,1)" onkeyup="return showCodeListKey('rateintv',[this,RateIntvName],[0,1],null,null,null,1)"><input class=codename name=RateIntvName elementtype=nacessary></td>
				<td class=title>保证利率</td>
				<td class=input><input class="wid common" name=Rate verify="保证利率|NUM&VALUE>=0&VALUE<1" elementtype=nacessary></td>
				<td class=title></td>
				<td class=title></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="新  增" onclick="insertClick();">
		<input class=cssButton type=button value="修  改" onclick="updateClick();">
		<input class=cssButton type=button value="重  置" onclick="resetClick();">
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=StartDate>
	<input type=hidden name=EndDate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
