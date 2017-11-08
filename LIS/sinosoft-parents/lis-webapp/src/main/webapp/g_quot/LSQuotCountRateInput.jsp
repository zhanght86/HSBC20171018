<%
/***************************************************************
 * <p>ProName：LSQuotCountRateInput.jsp</p>
 * <p>Title：费率测算</p>
 * <p>Description：费率测算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-29
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>长期险测算</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCountRateInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotCountRateInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li onclick="goToLongRiskStep(0)" class="now">费率测算</li>
		   <li onclick="goToLongRiskStep(1)">收益测算</li>
		</ul>
	</div>
</form>
<form name=fm2  id=fm2 method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPolInfo);">
				</td>
				<td class=titleImg>保单信息</td>
			</tr>
		</table>
		<div id="divPolInfo" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>险种</td>
					<td class=input><input class=codeno name=RiskCode id=RiskCode verify="险种|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotprodrisk',[this,RiskName],[0,1]);" onkeyup="return returnShowCodeListKey('quotprodrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName id=RiskName elementtype=nacessary></td>
					<td class=title>责任</td>
					<td class=input><input class=codeno name=DutyCode id=DutyCode verify="责任|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotduty',[this,DutyName],[0,1]);" onkeyup="return returnShowCodeListKey('quotduty',[this,DutyName],[0,1]);"><input class=codename name=DutyName id=DutyName elementtype=nacessary></td>
					<td class=title>预计生效日期</td>
					<td class=input><input class="coolDatePicker" dateFormat="short" name=EffectivDate id=EffectivDate  verify="预计生效日期|NOTNULL&DATE" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>计算方向</td>
					<td class=input><input class=codeno name=CalDirection id=CalDirection verify="计算方向|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('caldirection', [this,CalDirectionName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('caldirection', [this,CalDirectionName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=CalDirectionName id=CalDirectionName readonly elementtype=nacessary></td>
					<td class=title>保额</td>
					<td class=input><input class="wid common" name=Amnt id=Amnt verify="保额|NOTNULL&NUM" elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>缴费方式</td>
					<td class=input><input class=codeno name=PayIntv id=PayIntv verify="险种|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('payintv',[this,PayIntvName],[0,1]);" onkeyup="return returnShowCodeListKey('payintv',[this,PayIntvName],[0,1]);"><input class=codename name=PayIntvName id=PayIntvName elementtype=nacessary></td>
					<td class=title>缴费期间</td>
					<td class=input><input class="wid common" style="width:60px" name=InsuPeriod id=InsuPeriod verify="缴费期间|INT&VALUE>0&NOTNULL"><input type=hidden name=InsuPeriodFlag><input class=codeno name=InsuPeriodFlagName id=InsuPeriodFlagName verify="缴费期间单位|NOTNULL" ondblclick="return showCodeList('insuperiodflag', [this,InsuPeriodFlag], [1,0],null,'1 and code=#Y#','1','1',180);" onkeyup="return showCodeListKey('insuperiodflag', [this,InsuPeriodFlag], [1,0],null,'1 and code=#Y#','1','1',180);" elementtype=nacessary readonly></td>
					<td class=title>领取方式</td>
					<td class=input><input class="wid common" style="width:60px" name=ReceiveCode id=ReceiveCode verify="领取方式|INT&VALUE>0&NOTNULL"><input type=hidden name=ReceiveFlag><input class=codeno name=ReceiveName id=ReceiveName verify="领取方式单位|NOTNULL" ondblclick="return showCodeList('payunit',[this, ReceiveFlag], [1, 0], null, null, null, '1', null);" onkeyup="return showCodeListKey('payunit',[this, ReceiveFlag], [1, 0], null, null, null, '1', null);" elementtype=nacessary ></td>
				</tr>
			</table>
			<input class=cssButton type=button value="保  存" name="SavePolInfo" onclick="savePolInfo();">
			<input class=cssButton type=button value="删  除" name="DelPolInfo" onclick="delPolInfo();">
			<input class=cssButton type=button value="费率导出" name="ExpRate" onclick="expRate();">
		</div>
		<!-- 被保人信息 -->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCustInfo);">
				</td>
				<td class=titleImg>被保人信息</td>
			</tr>
		</table>
		<div id="divCustInfo"  style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanCustInfoGrid" ></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</center>
			<table class=common>
				<tr class=common>
					<td class=title>姓名</td>
					<td class=input><input class="wid common" name=InsuredName id=InsuredName verify="姓名|NOTNULL" elementtype=nacessary></td>
					<td class=title>性别</td>
					<td class=input><input class=codeno name=Gender id=Gender readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('gender',[this, GenderName],[0, 1],null,'1 and code!=#2#','1','1',180);" onkeyup="return showCodeListKey('gender',[this, GenderName],[0, 1],null,'1 and code!=#2#','1','1',180);"><input class=codename name=GenderName id=GenderName readonly elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>年龄</td>
					<td class=input><input class="wid common" name=InsuredAge id=InsuredAge ></td>
					<td class=title>出生日期</td>
					<td class=input><input class="coolDatePicker" dateFormat="short" name=Birthday id=Birthday></td>
					<td class=title>身份证号码</td>
					<td class=input><input class="wid common" name=IDNo id=IDNo></td>
				</tr>
			</table>
			<input class=cssButton type=button value="增  加" name="AddButton" onclick="addSubmit();">
			<input class=cssButton type=button value="修  改" name="ModButton" onclick="modSubmit();">
			<input class=cssButton type=button value="删  除" name="DelButton" onclick="delSubmit();">
		</div>
		<!-- 现金价值导出 -->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCashValue);">
				</td>
				<td class=titleImg>现金价值导出</td>
			</tr>
		</table>
		<div id="divCashValue" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>导出方式</td>
					<td class=input><input class=codeno name=ExpType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('cashvalueexptype', [this,ExpTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('cashvalueexptype', [this,ExpTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ExpTypeName id=ExpTypeName readonly elementtype=nacessary></td>
					<td class=title id=expTD1 style="display: 'none'">年龄</td>
					<td class=input id=expTD2 style="display: 'none'"><input class="wid common" name=Age id=Age elementtype=nacessary></td>
					<td class=title id=expTD3 style="display: 'none'">年限起</td>
					<td class=input id=expTD4 style="display: 'none'"><input class="wid common" name=YearStart id=YearStart elementtype=nacessary></td>
				</tr>
                <tr class=common>
                    <td class=title id=expTD5 style="display: 'none'">年限止</td>
					<td class=input id=expTD6 style="display: 'none'"><input class="wid common" name=YearEnd id=YearEnd elementtype=nacessary></td>
					<td class=title id=expTD7 style="display: ''"></td>
					<td class=input id=expTD8 style="display: ''"></td>
					<td class=title id=expTD9 style="display: ''"></td>
					<td class=input id=expTD10 style="display: ''"></td>
				</tr>
			</table>
			<input class=cssButton type=button value="现金价值导出" name="ExpCashValue" id=ExpCashValue onclick="expCashValue();">
		</div>
		<br/>
		<div id="divCustImp" style="display: ''">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInp);">
					</td>
					<td class=titleImg>被保人信息导入</td>
				</tr>
			</table>
			<div id="divInp" class=maxbox1>
				<table class=common>
					<tr class=common>
						<td class=title>导入文件</td>
						<td class=input colspan=5><input class="wid common" type=file id=UploadPath name=UploadPath style="width:400px"  elementtype=nacessary><input class=cssButton type=button value="导  入" onclick="impSubmit();">&nbsp;&nbsp;<a href="../template/quot/QuotCustInfoImp.xls">下载导入模板</href></td>
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
			</div>
		</div>
	</div>
    <br/><br/><br/><br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<input class=cssButton type=button value="关  闭" onclick="top.close();">
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
