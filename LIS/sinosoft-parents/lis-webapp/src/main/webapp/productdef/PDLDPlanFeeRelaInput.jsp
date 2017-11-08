<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：PDLDPlanFeeRelaInput.jsp
	//程序功能：累加器关联账单配置
	//创建日期：2016-5-22
	//创建人  ：王海超
	//更新记录：  更新人    更新日期     更新原因/内容
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<link rel="stylesheet" type="text/css"
	href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>
<SCRIPT src="PDLDPlanFeeRelaInput.js"></SCRIPT> 
<%@include file="./PDLDPlanFeeRelaInit.jsp"%>
</head>
<body onload="initForm1()">
<form action="PDLDPlanFeeRelaSave.jsp" method=post name=fm1 id="fm1"
	target="fraSubmit">
<div class=maxbox1>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5><input class="common wid" name="RiskCode"
			id=RiskCode readonly></td>
		<td class=title5>给付代码</td>
		<td class=input5><input class="common wid" name="GetDutyCode"
			id=GetDutyCode></td>
	</tr>
</table>
</div>
 <a href="javascript:void(0);" class="button"onclick="queryFeeInfo()">查   询</a>
<div class=maxbox1>
<table>
	<tr>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divLcalculatorFeeInfo);">
		</TD>
		<td class="titleImg">已定义的账单信息<font size=1 color='#ff0000'><b>（本给付责任层级所有的账单信息）</b></font></td>
	</tr>
</table>
</div>
<div id="divLcalculatorFeeInfo">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanMulline1Grid">
		</span></td>
	</tr>
</table>
</div>
<div class=maxbox1>
<table>
	<tr>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divLcalculatorInfo2);">
		</TD>
		<td class="titleImg">累加器详细配置信息</td>
	</tr>
</table>
</div>
<div class=maxbox1>
<div id="divLcalculatorInfo2">
<table classs=common>
	<tr class=common>
		<!-- PD_LDPlanFeeRela  保障计划与账单费用关联表 -->
		<td class=tittle normal>保障计划代码</td>
		<td class=input normal><Input class="codeno" name=PlanCode
			id=PlanCode readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_plancode',[this,PlanCodeName],[0,1]);"
			onDblClick="return showCodeList('pd_plancode',[this,PlanCodeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_plancode',[this,PlanCodeName],[0,1]);">
		<input class=codename name=PlanCodeName id=PlanCodeName readonly=true></TD>
		<td class=tittle normal>是否扣除自付比例</td>
		<td class=input normal><Input class="codeno"
			name=ifselfPayPercent id=ifselfPayPercent readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('yesno',[this,ifselfPayPercentName],[0,1]);"
			onDblClick="return showCodeList('yesno',[this,ifselfPayPercentName],[0,1]);"
			onKeyUp="return showCodeListKry('yesno',[this,ifselfPayPercentName],[0,1]);"><input
			class=codename name=ifselfPayPercentName id=ifselfPayPercentName
			readonly=true></td>
		<td class=tittle normal>自付比例</td>
		<td class=input normal><input class="common wid"
			name=selfPayPercent id=selfPayPercent></td>
	</tr>
	<tr class=common>
		<td class=tittle normal>免赔额</td>
		<td class=input normal><Input class="common wid" name=IfPayMoney
			id=IfPayMoney></td>
		<td class=tittle normal>账单费用项代码</td>
		<td class=input normal><Input class="codeno" name=FeeCode id=FeeCode
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('tpd_llmedfeetype',[this,FeeCodeName],[0,1]);"
			onDblClick="return showCodeList('tpd_llmedfeetype',[this,FeeCodeName],[0,1]);"
			onKeyUp="return showCodeListKey('tpd_llmedfeetype',[this,FeeCodeName],[0,1]);"><input
			class=codename name=FeeCodeName id=FeeCodeName ></td>
		<td class=tittle normal>费用类型</td>
		<td class=input normal><Input class="codeno" name=FeeType id=FeeType
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('llfeetype',[this,FeeTypeName],[0,1]);"
			onDblClick="return showCodeList('llfeetype',[this,FeeTypeName],[0,1]);"
			onKeyUp="return showCodeListKey('llfeetype',[this,FeeTypeName],[0,1]);"><input
			class=codename name=FeeTypeName id=FeeTypeName readonly=true></td>
	</tr>
	<tr class=common>
		<td class=tittle normal>保单年度金额上限</td>
		<td class=input normal><input class="common wid"
			name=MoneyLimitAnnual id=MoneyLimitAnnual></td>
		<td class=tittle normal>保单年度次数上限</td>
		<td class=input normal><input class="common wid"
			name=CountLimitAnnual id=CountLimitAnnual></td>
		<td class=tittle normal>保单年度天数上限</td>
		<td class=input normal><input class="common wid"
			name=DaysLimitAnnual id=DaysLimitAnnual></td>
	</tr>
	<tr class=common>
		<td class=tittle normal>每次赔偿金额上限</td>
		<td class=input normal><input class="common wid"
			name=MoneyLimitEveryTime id=MoneyLimitEveryTime></td>
		<td class=tittle normal>每次赔偿天数上限</td>
		<td class=input normal><input class="common wid"
			name=DaysLimitEveryTime id=DaysLimitEveryTime></td>
		<td class=tittle normal>每天赔付金额上限</td>
		<td class=input normal><input class="common wid"
			name=MoneyLimitEveryDay id=MoneyLimitEveryDay></td>
	</tr>
	<tr class=common>
		<td class=tittle normal>每天赔偿固定金额</td>
		<td class=input normal><input class="common wid"
			name=PayMoneyEveryDay id=PayMoneyEveryDay></td>
		<td class=tittle normal>等待期</td>
		<td class=input normal><input class="common wid" name=ObsPeriod
			id=ObsPeriod></td>
		<td class=tittle normal>等待期单位</td>
		<td class=input normal><Input class="codeno" name=ObsPeriodUn
			id=ObsPeriodUn readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_validperiodunit',[this,ObsPeriodUnName],[0,1]);"
			onDblClick="return showCodeList('pd_validperiodunit',[this,ObsPeriodUnName],[0,1]);"
			onKeyUp="return showCodeListKry('pd_validperiodunit',[this,ObsPeriodUnName],[0,1]);"><input
			class=codename name=ObsPeriodUnName id=ObsPeriodUnName readonly=true></td>
	</tr>
	<tr class=common>
		<!--  <td class=tittle normal>层级</td>
		<td class=input normal><input class="codeno" name=Level CodeData="0|^1|账单^2|给付责任^3|责任"
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			ondblclick="return showCodeListEx('Level',[this,LevelName],[0,1]);"
			onkeyup="return showCodeListKeyEx('Level',[this,LevelName],[0,1]);" readonly=true><input
			class=codename name=LevelName id=LevelName readonly=true><font
			size=1 color='#ff0000'><b>*</b></font></td>-->
		<td class=tittle normal>产品险种代码</td>
		<td class=input normal><input class="common wid" name="RiskCode1"
			id=RiskCode1></td>
		<td class=tittle normal>给付代码</td>
		<td class=input normal><input class="common wid" name="GetDutyCode1"
			id=GetDutyCode1 verify="给付代码|NOTNUlL"></td>
	</tr>
	<tr class=common >
	<td class=input normal colSpan=4><font size=1 color='#ff0000'><b>注意：<br/>
																		1.责任级别上限(对应的给付责任编码为000，账单编码为0000);<br/>
																		2.给付责任级别上限(对应的账单编码为0000);<br/>
																		3.账单级别上限;<br/>
																		4.保障计划可以不选，系统默认为A;</b></font></td>
	</tr>
</table>
</div>
</div>
<input value="保  存" type=button onclick="save()" class="cssButton"
	type="button"> <input value="修  改" type=button
	onclick="update()" class="cssButton" type="button"><input
	value="删  除" type=button onclick="del()" class="cssButton"
	type="button"><input value="返  回" type=button
	onclick="returnParent( )" class="cssButton" type="button"><input type=hidden name="operator" id=operator><input type=hidden name="DutyCode" id=DutyCode>
<input type=hidden name=IsReadOnly id=IsReadOnly></form>
<!-- 增量导入 -->
<div align=left id=savebuttonid>
<form action="./PDLDPlanFeeRelaInputULSave.jsp" method=post name=fm2 id=fm2
	target="fraSubmit" ENCTYPE="multipart/form-data">
<table>
	<tr>
		<td><input type=file name="FileName">&nbsp;&nbsp;<INPUT
			VALUE="增量导入" TYPE="button" class=cssButton onclick="clickUpload();">
		</td>
	</tr>
</table>
</form>
</div>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
