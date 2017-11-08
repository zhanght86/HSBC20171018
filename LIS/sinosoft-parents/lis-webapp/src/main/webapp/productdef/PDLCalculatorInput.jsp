<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：PDLCalculatorInput.jsp
	//程序功能：累加器配置
	//创建日期：2016-5-16
	//创建人  ：
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
<SCRIPT src="PDLCalculatorInput.js"></SCRIPT>
<%@include file="PDLCalculatorInit.jsp"%>
</head>
<body onload="initForm();">
<form action="PDLCalculatorSave.jsp" method=post name=fm id="fm"
	target="fraSubmit">
<div class=maxbox1>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5><input class="common wid" name="RiskCode"
			id=RiskCode readonly></td>
		<td class=title5>给付代码</td>
		<td class=input5><input class="common wid" name="GetDutyCode"
			id=GetDutyCode readonly></td>
	</tr>
</table>
</div>
<div class=maxbox1>
<table>
	<tr>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divInvAssBuildInfo1);">
		</TD>
		<td class="titleImg">已定义的累加器信息<font size=1 color='#ff0000'><b>（本给付责任层级以上所有的累加器）</b></font></td>
	</tr>
</table>
</div>
<div id="divInvAssBuildInfo1">
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
			style="cursor: hand;" OnClick="showPage(this,divInvAssBuildInfo2);">
		</TD>
		<td class="titleImg">已定义的累加器信息<font size=1 color='#ff0000'><b>（本给付责任层级所有的累加器）</b></font></td>
	</tr>
</table>
</div>
<div id="divInvAssBuildInfo2">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanMulline2Grid">
		</span></td>
	</tr>
</table>
</div>
<div class=maxbox1>
<table>
	<tr>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divInvAssBuildInfo4);">
		</TD>
		<td class="titleImg">累加器详细配置信息</td>
	</tr>
</table>
</div>
<div class=maxbox1>
<div id="divInvAssBuildInfo4">
<table classs=common>
	<tr class=common>
		<!-- PD_LCalculatorMain 累加器主表信息-->
		<td class=tittle normal>累加器名称</td>
		<td class=input normal><Input class="common wid"
			name=CalculatorName id=CalculatorName verify="累加器名称|NOTNUlL"><font
			size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>累加器层级</td>
		<td class=input normal><Input class="codeno" name=CtrlLevel
			id=CtrlLevel readonly=true verify="累加器层级|NOTNUlL&code:pd_ctrllevel"
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_ctrllevel',[this,CtrlLevelName],[0,1]);"
			onDblClick="return showCodeList('pd_ctrllevel',[this,CtrlLevelName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_ctrllevel',[this,CtrlLevelName],[0,1]);"><input
			class=codename name=CtrlLevelName id=CtrlLevelName readonly=true><font
			size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
			<td class=tittle normal>累加器类型</td>
		<td class=input normal><Input class="codeno" name=CalculatorType
			id=CalculatorType readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_calculatortype',[this,CalculatorTypeName],[0,1]);"
			onDblClick="return showCodeList('pd_calculatortype',[this,CalculatorTypeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_calculatortype',[this,CalculatorTypeName],[0,1]);"
			onclick="showCalCodePart();"><input class=codename
			name=CalculatorTypeName id=CalculatorTypeName readonly=true></td>
			
	</tr>
	<tr class=common>
		<td class=tittle normal>要素类型</td>
		<td class=input normal><Input class="codeno" name=CtrlFactorType
			id=CtrlFactorType readonly=true
			verify="累加器要素类型|NOTNUlL&code:pd_ctrlFactorType"
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_ctrlfactortype',[this,CtrlFactorTypeName],[0,1]);"
			onDblClick="return showCodeList('pd_ctrlfactortype',[this,CtrlFactorTypeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_ctrlfactortype',[this,CtrlFactorTypeName],[0,1]);"><input
			class=codename name=CtrlFactorTypeName id=CtrlFactorTypeName
			readonly=true><font size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>要素值</td>
		<td class=input normal><input class="common wid"
			name=CtrlFactorValue verify="累加器要素值|NOTNULL" id=CtrlFactorValue><font
			size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
			<td class=tittle normal>要素单位</td>
		<td class=input normal><Input class="codeno" name=CtrlFactorUnit
			id=CtrlFactorUnit readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_ctrlfactorunit',[this,CtrlFactorUnitName],[0,1]);"
			onDblClick="return showCodeList('pd_ctrlfactorunit',[this,CtrlFactorUnitName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_ctrlfactorunit',[this,CtrlFactorUnitName],[0,1]);"><input
			class=codename name=CtrlFactorUnitName id=CtrlFactorUnitName
			readonly=true></td>
	</tr>
	<tr class=common>
		<td class=tittle normal>要素值计算方式</td>
		<td class=input normal><Input class="codeno" name=CalMode
			id=CalMode readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_calmodetype',[this,CalModeName],[0,1]);"
			onDblClick="return showCodeList('pd_calmodetype',[this,CalModeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_calmodetype',[this,CalModeName],[0,1]);"><input
			class=codename name=CalModeName id=CalModeName readonly=true></td>
			<td class=tittle normal>默认值</td>
		<td class=input normal><input class="common wid" onfocus="checkCalMode();"
			name=DefaultValue id=DefaultValue><font size=2 color='#ff0000'><b><br/>当要素值的计算方式为1时，默认值和要素值一致。</b></font></td>
		<td class=tittle normal>要素值计算公式</td>
		<td class=input normal><input class="common wid" name=CalCode
			id=CalCode></td>
			
	</tr>
	<tr class=common>
	
		<!-- 累加器维度信息 -->
		<!-- PD_LCalculatorNatureTime  自然时间维度 -->
		<td class=tittle normal>生效起期</td>
		<td class=input normal><input class="coolDatePicker"
			name="StartDate" verify="累加器生效起期|NOTNULL" readonly
			onClick="laydate({elem: '#StartDate'});" id="StartDate"><span
			class="icon"><a onClick="laydate({elem: '#StartDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>生效止期</td>
		<td class=input normal><input class="coolDatePicker"
			name="EndDate" verify="累加器生效止期|NOTNULL" readonly
			onClick="laydate({elem: '#EndDate'});" id="EndDate"><span
			class="icon"><a onClick="laydate({elem: '#EndDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
	</tr>
	<tr class=common>
		<!-- PD_LCalculatorInsuranceTime 保险时间维度 -->
		<td class=tittle normal>生效时长</td>
		<td class=input normal><input class="common wid" name=ValidPeriod
			verify="累加器生效时长|NOTNULL" id=ValidPeriod><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>生效时长单位</td>
		<td class=input normal><Input class="codeno" name=ValidPeriodUnit
			verify="累加器生效时长单位|NOTNULL&code:pd_validPeriodUnit" id=ValidPeriodUnit
			readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('insuperiodflag',[this,ValidPeriodUnitName],[0,1]);"
			onDblClick="return showCodeList('insuperiodflag',[this,ValidPeriodUnitName],[0,1]);"
			onKeyUp="return showCodeListKey('insuperiodflag',[this,ValidPeriodUnitName],[0,1]);"><input
			class=codename name=ValidPeriodUnitName id=ValidPeriodUnitName
			readonly=true><font size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
	</tr>
	<tr class=common>
	<!-- PD_LCalculatorOrder 累加器步骤 -->
		<td class=tittle normal>累加步骤</td>
		<td class=input normal><input class="common wid" name=CalOrder
			verify="累加器步骤|NOTNULL" id=CalOrder><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>后置累加器编号（可选）算法</td>
		<td class=input normal><input class="common wid"
			name=CalculatorCodeAfter id=CalculatorCodeAfter><font size=1
			color='#ff0000'><b>&nbsp;(可选)</b></font></td>
	</tr>
	<tr class=common>
		<td class=tittle normal>费用类型</td>
		<td class=input normal><Input class="codeno" name=FeeType id=FeeType
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('llfeetype',[this,FeeTypeName],[0,1]);"
			onDblClick="return showCodeList('llfeetype',[this,FeeTypeName],[0,1]);"
			onKeyUp="return showCodeListKey('llfeetype',[this,FeeTypeName],[0,1]);"><input
			class=codename name=FeeTypeName id=FeeTypeName readonly=true></td>
		<td class=tittle normal>账单编码</td>
		<td class=input normal><Input class="codeno" name=FeeCode id=FeeCode
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_llmedfeetype',[this,FeeCodeName],[0,1]);"
			onDblClick="return showCodeList('pd_llmedfeetype',[this,FeeCodeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_llmedfeetype',[this,FeeCodeName],[0,1]);"><input
			class=codename name=FeeCodeName id=FeeCodeName readonly=true><font size=1 color='#ff0000'><b><br/>给付责任层级以上，不需要关联账单信息</b></font></td>
	</tr>
</table>
<!-- PD_LCalculatorFeeCode 累加器账单费用类型维度表 -->
<!-- 
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanMulline3Grid">
		</span></td>
	</tr>
</table>
</div>
</div> -->
<!--  
<div class=maxbox1>
<table>
	<tr>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divFeeCodeBuildInfo1);">
		</TD>
		<td class="titleImg">账单配置信息</td>
	</tr>
</table>
</div>-->
<div class=maxbox1>
<div id="divFeeCodeBuildInfo1">
<table class=common>
	<tr class=commmon>
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
			<td class=tittle normal>免赔额</td>
		<td class=input normal><Input class="common wid" name=IfPayMoney
			id=IfPayMoney></td>
	</tr>
	<!--  <tr class=common>
			<td class=tittle normal>等待期</td>
		<td class=input normal><input class="common wid" name=ObsPeriod
			id=ObsPeriod></td>
		<td class=tittle normal>等待期单位</td>
		<td class=input normal><Input class="codeno" name=ObsPeriodUn
			id=ObsPeriodUn readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('insuperiodflag',[this,ObsPeriodUnName],[0,1]);"
			onDblClick="return showCodeList('insuperiodflag',[this,ObsPeriodUnName],[0,1]);"
			onKeyUp="return showCodeListKry('insuperiodflag',[this,ObsPeriodUnName],[0,1]);"><input
			class=codename name=ObsPeriodUnName id=ObsPeriodUnName readonly=true></td>
	</tr>-->
</table>
</div>
</div>
<!--算法定义引用页--> <jsp:include page="CalCodeDefMain.jsp" /><input
	value="保  存" type=button onclick="save()" class="cssButton"
	type="button"> <input value="修  改" type=button id="buttionId"
	onclick="update()" class="cssButton" type="button"><input
	value="删 除" type=button onclick="del()" class="cssButton" type="button">
	<!--<input
	value="账单信息定义" type=button onclick="button1()" class="cssButton" type="button">  -->
<input type=hidden name="operator" id=operator> <input
	type=hidden name=IsReadOnly id=IsReadOnly>  <input
	type=hidden name=SerialNo id=SerialNo><input
	type=hidden name=CalculatorCode id=CalculatorCode><input
	type=hidden name=FlagStr id=FlagStr></form>
	<!-- 增量导入 -->
<div align=left id=savebuttonid>
<form action="./PDLCalculatorInputULSave.jsp" method=post name=fm1
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
