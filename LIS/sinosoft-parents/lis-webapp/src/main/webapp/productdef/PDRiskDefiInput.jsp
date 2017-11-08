
<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>

<%
		//程序名称：PDRiskDefiInput.jsp
		//程序功能：产品基础信息录入
		//创建日期：2009-3-12
		//创建人  ：
		//更新记录：  更新人    更新日期     更新原因/内容
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput) session.getAttribute("GI");
		session.setAttribute("LoadFlagButton1",
				session.getAttribute("LoadFlagButton1"));
	
		System.out.println("LoadFlagButton1=="
				+ session.getAttribute("LoadFlagButton1"));
		try {
			String temp = request.getParameter("LoadFlagButton");
			System.out.println("传入的标记是：" + temp);
			if ("0".equals(temp)) {
				session.setAttribute("LoadFlagButton1", "0");
			}
		} catch (Exception e) {
		}
	%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
<SCRIPT src="DictionaryUtilities.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<script>
		
		var mOperator = "<%=tGI.Operator%>";   //记录操作员
		var LoadFlagButton = "<%=request.getParameter("LoadFlagButton")%>";
		var	mpdflag = "<%=request.getParameter("pdflag")%>";
	</script>
<%@include file="buttonshow.jsp"%>
<SCRIPT src="PDRiskDefi.js"></SCRIPT>
<%@include file="PDRiskDefiInit.jsp"%>


</head>
<body onload="initForm();initElementtype();">


<table class=common2>
	<TR class=title>
		<TD class=titleImg>险种产品信息：</TD>
	</TR>
</table>
<table class=common>
	<TR class=common>
		<TD class=title>险种代码</TD>
		<TD class=input><input class=common name="RiskCode1" id="RiskCode"
			readonly="readonly"></TD>
		<TD class=title>申请日期
		</TD>
		<TD class=input><input class="coolDatePicker" dateFormat="short"
			name="RequDate1" readonly id="RequDate"
			onClick="laydate({elem:'#RequDate'});"><span class="icon"><a
			onClick="laydate({elem: '#RequDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</Table>
<hr>
<table class=common2>
	<TR class=title>
		<TD class=titleImg>产品复制：</TD>
	</TR>
</table>
<table class=common>
	<TR class=common>
		<TD class=title>已上线产品</TD>
		<TD class=input><Input class=codeNo name=RiskCode2
			ondblclick="queryRisk();"><input class=codename
			name=RiskName2></TD>
		<TD class=title><input id=copyproductbutton
			value="产品复制"
			onclick="productCopy()" class="cssButton" type="button"
			name=productCopy></TD>
		<TD class=input></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</table>
<form action="./PDRiskDefiSave.jsp" method=post name=fmF
	target="fraSubmit">
<hr>
<table class=common2>
	<TR class=title>
		<TD class=titleImg>基础信息定义：</TD>
	</TR>
</table>
<table class=common2 border=0>
	<TR class=common>
		<TD class=title>险种中文名称</TD>
		<TD class=input><Input class=common name=RiskName
			verify="险种名称|NOTNULL&len<120" 
				
				
				
			elementtype="nacessary"></TD>
		<TD class=title>险种英文名称</TD>
		<TD class=input><Input class=common name=RiskEnName
			verify="险种英文名称|NOTNULL&len<120"
								
				
				
				
			elementtype="nacessary"></TD>
		<td class="title">险种类别</td>
		<td class="input"><input class="codeNo" name="riskProp" value=""
			ondblclick="return showCodeList('PD_RISKPROP',[this,riskPropS],[0,1]);"
			onkeyup="return showCodeListKey('PD_RISKPROP',[this,riskPropS],[0,1]);"><input
			class="codename" name="riskPropS"
			value="个险"
			elementtype="nacessary"
			verify="险种性质|NOTNULL"></td>
	</TR>
</table>
<table class=common2>
	<tr class=common>
		<TD class=title>险种中文简称</TD>
		<TD class=input><Input class=common name=RiskShortName
			verify="险种简称|len<120"></TD>
		<TD class=title>险种英文简称</TD>
		<TD class=input><Input class=common name=RiskEnShortName
			verify="险种英文简称|len<120"></TD>
		<td class="title">主/附险标记
		</td>
		<td class="input"><input class="codeNo" name="SubRiskFlag"
			<%--ondblclick="return showCodeList('PD_SUBRISKFLAG',[this,SubRiskFlagS],[0,1]);"
								onkeyup="return showCodeListKey('PD_SUBRISKFLAG',[this,SubRiskFlagS],[0,1]);"> --%>
				ondblclick="return showCodeList('PD_SUBRISKFLAG',[this,SubRiskFlagS],[0,1]);"
			onkeyup="return showCodeListKey('PD_SUBRISKFLAG',[this,SubRiskFlagS],[0,1]);"><input
			class="codename" name="SubRiskFlagS" elementtype="nacessary"
			verify="主附险标记|NOTNULL"></td>
	</tr>
	<tr class=common>
		<td class="title">险种分类</td>

		<td class="input"><input class="codeNo" name="riskType"
			ondblclick="return showCodeList('pd_kindcode',[this,riskTypeS],[0,1]);"
			onkeyup="return showCodeListKey('pd_kindcode',[this,riskTypeS],[0,1]);"><input
			class="codename" name="riskTypeS" elementtype="nacessary"
			verify="险种分类|NOTNULL"
			></td>
		<td class="title">险种期间</td>
		<td class="input"><input class="codeNo" name="RiskPeriod"
			<%-- ondblclick="return showCodeList('PD_RISKPERIOD',[this,RiskPeriodS],[0,1]);"
				onkeyup="return showCodeListKey('PD_RISKPERIOD',[this,RiskPeriodS],[0,1]);">--%>
				ondblclick="return showCodeList('PD_RISKPERIOD',[this,RiskPeriodS],[0,1]);"
			onkeyup="return showCodeListKey('PD_RISKPERIOD',[this,RiskPeriodS],[0,1]);"><input
			class="codename" name="RiskPeriodS" elementtype="nacessary"
			verify="险种期间|NOTNULL"></td>
		<td class="title">在/停售状态
		</td>
		<td class="input"><input class="codeNo" name="SaleFlag"
			ondblclick="return showCodeList('PD_SaleFlag',[this,SaleFlagS],[0,1]);"
			onkeyup="return showCodeListKey('PD_SaleFlag',[this,SaleFlagS],[0,1]);"><input
			class="codename" name="SaleFlagS"></td>
	</tr>
	<tr class=common>
		<td class="title">红利标记</td>
		<td class="input2"><input class="codeNo" name="BonusFlag"
			verify="红利标记|NOTNULL"
			<%--ondblclick="return showCodeList('PD_BonusFlag1',[this,BonusFlagS],[0,1]);"
				onkeyup="return showCodeListKey('PD_BonusFlag1',[this,BonusFlagS],[0,1]);">--%>
				ondblclick="return showCodeList('PD_BonusFlag',[this,BonusFlagS],[0,1]);"
			onkeyup="return showCodeListKey('PD_BonusFlag',[this,BonusFlagS],[0,1]);"><input
			class="codename" name="BonusFlagS" elementtype="nacessary"
			verify="红利标记|NOTNULL"></td>
		<!-- <td class="title" style='display: none'>核保权限
		</td>
		<td class="input2" style='display: none'><input class="codeNo"
			name="RiskType2"
			ondblclick="return showCodeList('PD_RiskType2',[this,RiskType2S],[0,1]);"
			onkeyup="return showCodeListKey('PD_RiskType2',[this,RiskType2S],[0,1]);"><input
			class="codename" name="RiskType2S" elementtype="nacessary"
			verify="核保权限|NOTNULL">
		</td> -->
		<TD class=title>原险种编码</TD>
		<TD class=input><Input class="common wid" name=OrigRiskCode id="OrigRiskCode" ></TD>
		<td class="title">特约打印类型</td>
		<td class="input2"><input class="codeNo"
			name="CancleForeGetSpecFlag"
			ondblclick="return showCodeList('PD_NForeGetSpecFlag1',[this,CancleForeGetSpecFlagS],[0,1]);"
			onkeyup="return showCodeListKey('PD_NForeGetSpecFlag1',[this,CancleForeGetSpecFlagS],[0,1]);"><input
			class="codename" name="CancleForeGetSpecFlagS"></td>
	</tr>
	<tr class=common>
		<td class="title">多被保险人标记</td>
		<td class="input2"><input class="codeNo" name="InsuredFlag"
			ondblclick="return showCodeList('PD_InsuredFlag',[this,InsuredFlagS],[0,1]);"
			onkeyup="return showCodeListKey('PD_InsuredFlag',[this,InsuredFlagS],[0,1]);"><input
			class="codename" name="InsuredFlagS" elementtype="nacessary"
			verify="多被保人标记|NOTNULL"></td>
		<td class="title">豁免对象标记</td>
		<td class="input"><input class="codeNo" name="RiskType7"
			<%--ondblclick="return showCodeList('pd_risktype71',[this,RiskType7S],[0,1]);"
				onkeyup="return showCodeListKey('pd_risktype71',[this,RiskType7S],[0,1]);"> --%>
				ondblclick="return showCodeList('pd_risktype7',[this,RiskType7S],[0,1]);"
			onkeyup="return showCodeListKey('pd_risktype7',[this,RiskType7S],[0,1]);"><input
			class="codename" name="RiskType7S"></td>
		<!--  <td class="title" style="display: none">豁免险种标记</td>
		<td class="input2" style="display: none"><input class="codeNo"
			name="RiskType9"
			ondblclick="return showCodeList('PD_RiskType91',[this,RiskType9S],[0,1]);"
			onkeyup="return showCodeListKey('PD_RiskType91',[this,RiskType9S],[0,1]);"><input
			class="codename" name="RiskType9S"></td>-->
			<td class="title">有无名单标记</td>
		<td class="input"><input class="codeNo" name="ListFlag" id="ListFlag" readonly
        style="background:url(../common/images/select--bg_03.png) no-repeat right center"
        onClick="return showCodeList('PD_ListFlag',[this,ListFlagS],[0,1]);"
			ondblclick="return showCodeList('PD_ListFlag',[this,ListFlagS],[0,1]);"
			onkeyup="return showCodeListKey('PD_ListFlag',[this,ListFlagS],[0,1]);"><input
			class="codename" name="ListFlagS" id="ListFlagS"></td>
	</tr>
	<tr class=common>
		<!--  <td class="title" style="display: none">自动垫缴类型</td>
			<td class="input2" style="display: none"><input class="codeNo" name="AutoPayType"
				ondblclick="return showCodeList('PD_AutoPayType',[this,AutoPayTypeS],[0,1]);"
				onkeyup="return showCodeListKey('PD_AutoPayType',[this,AutoPayTypeS],[0,1]);"><input
				class="codename" name="AutoPayTypeS"></td>-->
		<td class="title">管理部门</td>
		<td class="input"><input class="codeNo" name="MngCom" id="MngCom"
			readonly
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onClick="return showCodeList('PD_MngCom',[this,MngComS],[0,1]);"
			ondblclick="return showCodeList('PD_MngCom',[this,MngComS],[0,1]);"
			onkeyup="return showCodeListKey('PD_MngCom',[this,MngComS],[0,1]);"><input
			class="codename" name="MngComS" id="MngComS" elementtype="nacessary"><font
			color="red">*</font></td>
		<TD class=title>投保人最小年龄(岁)
		</TD>
		<TD class=input><Input class=common name=MinAppntAge></TD>
		<TD class=title>投保人最大年龄(岁)
		</TD>
		<TD class=input><Input class=common name=MaxAppntAge></TD>
	</tr>

	<tr class=common>
		<!--  <td class="title" style="display: none">自动展期类型</td>
			<td class="input2" style="display: none"><input class="codeNo" name="AutoETIType"
				ondblclick="return showCodeList('PD_AutoETIType',[this,AutoETITypeS],[0,1]);"
				onkeyup="return showCodeListKey('PD_AutoETIType',[this,AutoETITypeS],[0,1]);"><input
				class="codename" name="AutoETITypeS"></td>-->
				<td class="title">保单类型</td>
		<td class="input"><input class="codeNo" name="PolType" id="PolType" readonly
        style="background:url(../common/images/select--bg_03.png) no-repeat right center"
        onClick="return showCodeList('PD_POLTYPE',[this,PolTypeS],[0,1]);"
			ondblclick="return showCodeList('PD_POLTYPE',[this,PolTypeS],[0,1]);"
			onkeyup="return showCodeListKey('PD_POLTYPE',[this,PolTypeS],[0,1]);"><input
			class="codename" name="PolTypeS" id="PolTypeS" elementtype="nacessary"
			verify="保单类型|NOTNULL"><font color="red">*</font></td>
		<TD class=title>被保人最小年龄(岁)
		</TD>
		<TD class=input><Input class=common name=MinInsuredAge></TD>
		<TD class=title>被保人最大年龄(岁)
		</TD>
		<TD class=input><Input class=common name=MaxInsuredAge></TD>

	</tr>
	<tr class=common>
		<%-- td class="title">投连/万能标记
			</td--%>
		<td class="title">险种分类3</td>
		<td class="input2"><input class="codeNo" name="RiskType3"
			ondblclick="return showCodeList('PD_RiskType3',[this,RiskType3S],[0,1]);"
			onkeyup="return showCodeListKey('PD_RiskType3',[this,RiskType3S],[0,1]);"><input
			class="codename" name="RiskType3S"></td>
		<!--  <td class="title"><%--="IPA分类"--%></td>-->
		<td class="title">险种分类4</td>
		<td class="input2"><input class="codeNo" name="RiskType4"
			ondblclick="return showCodeList('PD_RiskType4',[this,RiskType4S],[0,1]);"
			onkeyup="return showCodeListKey('PD_RiskType4',[this,RiskType4S],[0,1]);"><input
			class="codename" name="RiskType4S"></td>
		<!--  <td class="title">Insurance Class</td>-->
		<td class="title">险种标记</td>
		<td class="input"><input class="codeNo" name="RiskFlag"
			ondblclick="return showCodeList('pd_riskflag',[this,RiskFlagS],[0,1]);"
			onkeyup="return showCodeListKey('pd_riskflag',[this,RiskFlagS],[0,1]);"><input
			class="codename" name="RiskFlagS"></td>
	</tr>
	<tr class=common>
		<!-- <td class="titleSize8">Product Group for ODS</td> -->
		<td class="title">险种细类</td>
		<td class="input"><input class="codeNo" name="RiskTypeDetail"
			ondblclick="return showCodeList('PD_RISKTYPEDETAIL',[this,RiskTypeDetailS],[0,1]);"
			onkeyup="return showCodeListKey('PD_RISKTYPEDETAIL',[this,RiskTypeDetailS],[0,1]);"><input
			class="codename" name="RiskTypeDetailS"></td>
		<td class="title">是否需要录入客户签收日期</td>
		<td class="input"><input class="codeNo" name="NeedGetPolDate" id="NeedGetPolDate" readonly
        style="background:url(../common/images/select--bg_03.png) no-repeat right center"
        onClick="return showCodeList('PD_NeedGetPolDate',[this,NeedGetPolDateS],[0,1]);"
			ondblclick="return showCodeList('PD_NeedGetPolDate',[this,NeedGetPolDateS],[0,1]);"
			onkeyup="return showCodeListKey('PD_NeedGetPolDate',[this,NeedGetPolDateS],[0,1]);"><input
			class="codename" name="NeedGetPolDateS" id="NeedGetPolDateS" ></td>
		<td class="title">险种分类5</td>
		<td class="input"><input class="codeNo" name="RiskType5" id="RiskType5" readonly
        style="background:url(../common/images/select--bg_03.png) no-repeat right center"
        onClick="return showCodeList('PD_RiskType5',[this,RiskType5S],[0,1]);"
			ondblclick="return showCodeList('PD_RiskType5',[this,RiskType5S],[0,1]);"
			onkeyup="return showCodeListKey('PD_RiskType5',[this,RiskType5S],[0,1]);"><input
			class="codename" name="RiskType5S" id="RiskType5S" ></td>
	</tr>
	<tr class=common>
   <td class="title">生效日算法</td>
		<td class="input"><input class="codeNo" name="SignDateCalMode" id="SignDateCalMode" readonly
        style="background:url(../common/images/select--bg_03.png) no-repeat right center"
        onClick="return showCodeList('PD_SignDateCalMode',[this,SignDateCalModeS],[0,1]);"
			ondblclick="return showCodeList('PD_SignDateCalMode',[this,SignDateCalModeS],[0,1]);"
			onkeyup="return showCodeListKey('PD_SignDateCalMode',[this,SignDateCalModeS],[0,1]);"><input
			class="codename" name="SignDateCalModeS" id="SignDateCalModeS" ></td>
	<TD class=title>开办日期</TD>
	 <TD class=input><Input class="coolDatePicker" dateFormat="short" name=StartDate verify="开办日期|NOTNULL" elementtype="nacessary" id="StartDate" onClick="laydate({elem:'#StartDate'});"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color="red">*</font></TD>
	<TD class=title>停办日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=EndDate id="EndDate" onClick="laydate({elem:'#EndDate'});"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</tr>
	<tr class=common>
	<td class="title">财务险种分类</td>
		<td class="input"><input class="codeNo" name="RiskTypeAcc" id="RiskTypeAcc" readonly
        style="background:url(../common/images/select--bg_03.png) no-repeat right center"
        onClick="return showCodeList('PD_RiskTypeAcc',[this,RiskTypeAccS],[0,1]);"
			ondblclick="return showCodeList('PD_RiskTypeAcc',[this,RiskTypeAccS],[0,1]);"
			onkeyup="return showCodeListKey('PD_RiskTypeAcc',[this,RiskTypeAccS],[0,1]);"><input
			class="codename" name="RiskTypeAccS" id="RiskTypeAccS" ></td>
			<td class="title">特殊险种标记</td>
		<td class="input"><input class="codeNo" name="SpecFlag" id="SpecFlag" readonly
        style="background:url(../common/images/select--bg_03.png) no-repeat right center"
        onClick="return showCodeList('PD_SpecFlag',[this,SpecFlagS],[0,1]);"
			ondblclick="return showCodeList('PD_SpecFlag',[this,SpecFlagS],[0,1]);"
			onkeyup="return showCodeListKey('PD_SpecFlag',[this,SpecFlagS],[0,1]);"><input
			class="codename" name="SpecFlagS" id="SpecFlagS" ></td>
			<td class="title">核保权限
		</td>
		<td class="input2"><input class="codeNo"
			name="RiskType2"
			ondblclick="return showCodeList('PD_RiskType2',[this,RiskType2S],[0,1]);"
			onkeyup="return showCodeListKey('PD_RiskType2',[this,RiskType2S],[0,1]);"><input
			class="codename" name="RiskType2S" elementtype="nacessary"
			verify="核保权限|NOTNULL">
		</td>
</tr>


</table>
<!-- 生效日算法 保持现有程序逻辑不变，因为中银签单生效日处理比较复杂，但是规则统一，所以增加中银特有描述为０ -->
<!--<td class="input2"><input type=hidden name="SignDateCalMode"
	value='0'> <input type=hidden name="SignDateCalModeS">
</td>-->
<table class=common2>
	<tr class='common' style='display: none'>
		<td class='title'>险种缴费间隔定义:</td>

		<td class='input80'><input type='checkbox' name='payintv'
			value=-1> 不定期缴费 <input
			type='checkbox' name='payintv' value=0> 趸缴
		<input type='checkbox' name='payintv' value=1> 月缴
		<input type='checkbox' name='payintv' value=3> 季缴
		<input type='checkbox' name='payintv' value=6> 半年缴
		<input type='checkbox' name='payintv' value=12> 年缴
		</td>
	</TR>

</table>
<table class=common id="MedicareDefi" style="display: none;">
	<TR class=common>
		<td class="title">医疗险给付类型</td>
		<td class="input"><input class="codeNo" name="RISKTYPE1"
			ondblclick="return showCodeList('PD_RISKTYPE1',[this,RISKTYPE1S],[0,1]);"
			onkeyup="return showCodeListKey('PD_RISKTYPE1',[this,RISKTYPE1S],[0,1]);"><input
			class="codename" name="RISKTYPE1S"></td>
		<td class="title"></td>
		<td class="input"></td>

		<td class="title"></td>
		<td class="input"></td>
	</TR>
</table>
<table class=common2 id="lifeDefi" style="display: ;">
	<TR class=common>
		<TD class=title>账户标记</TD>
		<td class="input2"><input type="radio" value="Y"
			name="InsuAccFlag" id='InsuAccFlag' onclick="showDivAccInsu();" /> 是
		<input type="radio" value="N" name="InsuAccFlag" id='InsuAccFlag'
			checked="checked" onclick="closeDiveAccInsu();" /> 否
		</td>
		<TD class=title>续保标记</TD>
		<td class=input2><input type="radio" value="Y" name="RnewFlag" />
		是 <input type="radio"
			value="N" name="RnewFlag" checked="checked" /> 否
		</td>
		<TD class=title>投资标记</TD>
		<td class=input><input type="radio" value="Y" name="InvestFlag" />
		是 <input type="radio"
			value="N" name="InvestFlag" checked="checked" /> 否
		</td>
	</TR>
	<TR class=common>
		<TD class=title>分保标记</TD>
		<td class=input2><input type="radio" value="Y" name="RinsFlag" />
		是 <input type="radio"
			value="N" name="RinsFlag" checked="checked" /> 否
		</td>
		<TD class=title>抵押标记</TD>
		<td class=input><input type="radio" value="Y"
			name="MortagageFlag" /> 是
		<input type="radio" value="N" name="MortagageFlag" checked="checked" />
		否</td>
		<TD class=title>借款标记</TD>
		<td class=input><input type="radio" value="Y" name="LoanFlag" />
		是 <input type="radio"
			value="N" name="LoanFlag" checked="checked" /> 否
		</td>
	</tr>
	<TR class=common>
		<TD class=title>产品回溯标记</TD>
		<td class=input><input type="radio" value="Y" name="BackDateFlag" />
		是 <input type="radio"
			value="N" name="BackDateFlag" checked="checked" /> 否
		</td>
		<TD class=title>责任可选标记</TD>
		<td class=input2><input type="radio" value="Y" name="ChoDutyFlag" />
		是 <input type="radio"
			value="N" name="ChoDutyFlag" checked="checked" /> 否
		</td>
		<TD class=title>生存给付标记</TD>
		<td class=input2><input type="radio" value="Y" name="GetFlag" />
		是 <input type="radio"
			value="N" name="GetFlag" checked="checked" /> 否
		</td>
	</TR>
	<TR class=common>
		<TD class=title>自动垫缴标记</TD>
		<td class=input><input type="radio" value="Y" name="AutoPayFlag" />
		是 <input type="radio"
			value="N" name="AutoPayFlag" checked="checked" /> 否
		</td>
		<TD class=title>自动退保标记</TD>
		<td class=input><input type="radio" value="Y" name="AutoCTFlag" />
		是 <input type="radio"
			value="N" name="AutoCTFlag" checked="checked" /> 否
		</td>
		<TD class=title>自动展期标记</TD>
		<td class=input><input type="radio" value="Y" name="AutoETIFlag" />
		是 <input type="radio"
			value="N" name="AutoETIFlag" checked="checked" /> 否
		</td>
	</TR>
	<TR class=common>
		<TD class=title>续期收费标记</TD>
		<td class=input2><input type="radio" value="Y" name="CPayFlag" />
		是 <input type="radio"
			value="N" name="CPayFlag" checked="checked" /> 否
		</td>
		<TD class=title>减额缴清标记</TD>
		<td class=input><input type="radio" value="Y"
			name="CutAmntStopPay" /> 是
		<input type="radio" value="N" name="CutAmntStopPay" checked="checked" />
		否</td>
		<TD class=title>清缴不分红标记</TD>
		<td class=input><input type="radio" value="Y" name="NonParFlag" />
		是 <input type="radio"
			value="N" name="NonParFlag" checked="checked" /> 否
		</td>
	</TR>
	<!--  <TR class=common>
	<TD class=title>产品折扣标记</TD>
		<td class="input">
			<input type = "radio" value = "Y"  name = "prodDisFlag" id = 'proDisFlag' onclick = "showDivProdDis();"/>是 
			<input type = "radio" value = "N"  name = "prodDisFlag" id = 'proDisFlag' onclick = "closeDivProdDis();" checked="checked"/>否
		</td>	
		<TD class=title></TD>
		<td class="input"></td>
		<TD class=title></TD>
		<td class="input"></td>
		</TR>-->
</table>

<div id="SubRiskFlagDiv" style="display: none;">
<table>
	<tr class=common>
		<td text-align:left colSpan=1><span id="spanMulline12Grid"></span>
		</td>
	</tr>
</table>
</div>

<div id="MainRiskFlagDiv" style="display: none;">
<table>
	<tr class=common>
		<td text-align:left colSpan=1><span id="spanMulline13Grid"></span>
		</td>
	</tr>
</table>
<hr>
<table class=common>
	<tr class='common'>
		<TD class=title style="display: none;">产品折扣标记
		</TD>
		<td class="input2" style="display: none;"><input type="radio"
			value="Y" name="prodDisFlag" id='proDisFlag'
			onclick="showDivProdDis();" /> 是
		<input type="radio" value="N" name="prodDisFlag" id='proDisFlag'
			onclick="closeDivProdDis();" checked="checked" /> 否
		</td>
		<TD class=title></td>
		<TD class=input></td>
		<TD class=title></td>
		<TD class=input></td>
	</TR>
	<tr class=common id="DivProdDis" style="display: none;">
		<td><input value="产品折扣定义"
			type=button id="btnRiskDisDefi" onclick="defiProdDis( )"
			class="cssButton" type="button"> <br>
		</td>
	</tr>
</table>
</div>
<div id="MainRiskFlagDiv" style="display: none;">
<TR class=common>
	<td class=title>寿险分类</td>
	<td class=input><input class="codeNo" name="LifeType"
		ondblclick="return showCodeList('LifeType',[this,LifeTypeS],[0,1]);"
		onkeyup="return showCodeListKey('LifeType',[this,LifeTypeS],[0,1]);">
	<input class="codename" name="LifeTypeS"></td>
</tr>
<!--<td class="title">特殊险种标记</td>
<td class="input2"><input class="codeNo" name="SpecFlag"
	ondblclick="return showCodeList('PD_SpecFlag',[this,SpecFlagS],[0,1]);"
	onkeyup="return showCodeListKey('PD_SpecFlag',[this,SpecFlagS],[0,1]);"><input
	class="codename" name="SpecFlagS"></td>
-->
<table class=common id="BonusDefi" style="display: none;">
	<TR class=common>
		<td class=title>红利领取方式</td>
		<td class=input><input class="codeNo" name="BonusMode"
			ondblclick="return showCodeList('bonusgetmode',[this,BonusModeS],[0,1]);"
			onkeyup="return showCodeListKey('bonusgetmode',[this,BonusModeS],[0,1]);"><input
			class="codename" name="BonusModeS"></td>
		<td class="title"></td>
		<td class="input"></td>

		<td class="title"></td>
		<td class="input"></td>
	</TR>
</table>
<TD class=title>原险种编码</TD>
<TD class=input><Input class=common name=OrigRiskCode
	verify="原险种编码|len<=8"></TD>
<!--<td class="title" style='display: none'>管理部门
</td>
<td class="input" style='display: none'><input class="codeNo"
	name="MngCom" value="PDD"
	ondblclick="return showCodeList('PD_MngCom',[this,MngComS],[0,1]);"
	onkeyup="return showCodeListKey('PD_MngCom',[this,MngComS],[0,1]);"><input
	class="codename" value="产品部"
	name="MngComS" style='display: none' elementtype="nacessary"></td>

-->


<!--  <td class="title" style='display: none'><%--="险种分类"--%></td>
<td class="input" style='display: none'><input class="codeNo"
	name="RISKTYPE1"
	ondblclick="return showCodeList('PD_RISKTYPE1',[this,RISKTYPE1S],[0,1]);"
	onkeyup="return showCodeListKey('PD_RISKTYPE1',[this,RISKTYPE1S],[0,1]);">
<input class="codename" name="RISKTYPE1S" elementtype="nacessary"
	verify="险种分类|NOTNULL"></td>

<td class="titleSize8" style='display: none'>保单类型
</td>
<td class="input2" style='display: none'><input class="codeNo"
	name="PolType" value="P"
	ondblclick="return showCodeList('PD_POLTYPE',[this,PolTypeS],[0,1]);"
	onkeyup="return showCodeListKey('PD_POLTYPE',[this,PolTypeS],[0,1]);"><input
	class="codename" name="PolTypeS" elementtype="nacessary"
	verify="保单类型|NOTNULL"
	value="保单"></td>
-->
<!--<td class="title" style='display: none'>有无名单标记
</td>
<td class="input2" style='display: none'><input class="codeNo"
	name="ListFlag" value="Y"
	ondblclick="return showCodeList('PD_ListFlag',[this,ListFlagS],[0,1]);"
	onkeyup="return showCodeListKey('PD_ListFlag',[this,ListFlagS],[0,1]);"
	verify="有无名单标记|NOTNULL"><input
	class="codename" name="ListFlagS"
	value="有名单"
	elementtype="nacessary"></td>
-->
<TD class=title style='display: none'>开办日期
</TD>
<TD class=input style='display: none'><Input
	class="multiDatePicker" dateFormat="short" name=StartDate
	verify="开办日期|NOTNULL"
	elementtype="nacessary" value="1900-01-01"></TD>
<TD class=title style='display: none'>停办日期
</TD>
<TD class=input style='display: none'><Input
	class="multiDatePicker" dateFormat="short" name=EndDate></TD>

<!--<td class="title" style='display: none'>是否需要录入客户签收日期
</td>
<td class="input2" style='display: none'><input class="codeNo"
	name="NeedGetPolDate" value="0"
	ondblclick="return showCodeList('PD_NeedGetPolDate',[this,NeedGetPolDateS],[0,1]);"
	onkeyup="return showCodeListKey('PD_NeedGetPolDate',[this,NeedGetPolDateS],[0,1]);"><input
	class="codename" name="NeedGetPolDateS"
	value="表示需要录入客户签收日期"></td>

<td class="title" style='display: none'>险种分类5
</td>
<td class="input2" style='display: none'><input class="codeNo"
	name="RiskType5"
	ondblclick="return showCodeList('PD_RiskType5',[this,RiskType5S],[0,1]);"
	onkeyup="return showCodeListKey('PD_RiskType5',[this,RiskType5S],[0,1]);"><input
	class="codename" name="RiskType5S"></td>
-->
</div>
<div align=left id=save1><input
	value="保  存" onclick="save()"
	class="cssButton" type="button"> <input
	value="修  改" onclick="update()"
	class="cssButton" type="button"> <input
	value="删  除" onclick="del()"
	class="cssButton" type="button"> <input class=cssButton
	VALUE="多语言信息" TYPE=button
	id="MultLanguageRisk" onclick="defineMultLanguageRisk();"></div>
<!--<input type=hidden name="RiskTypeAcc" value='0'> -->
<input
	type=hidden name="riskCodeF"> <input type=hidden
	name="operator"> <input type=hidden name="isSubRisk"> <input
	type=hidden name="RiskCode"> <input type=hidden name="RequDate">
<input type=hidden name="RiskCodem"> <input type=hidden
	name="RequDatem"> <input type=hidden name=MissionID> <input
	type=hidden name=SubMissionID> <input type=hidden
	name=ActivityID> <input type=hidden name=IsReadOnly>
<hr />
</form>

<form action="./PDRiskGraceSave.jsp" method=post name=fmP
	target="fraSubmit">
<table class=common>
	<TR class=title>
		<TD class=titleImg>宽限期定义：</TD>
	</TR>
</table>
<table class=common>
	<TR class=common>
		<TD class=title>催缴标记</TD>
		<td class="input"><input class="codeNo" name="UrgePayFlag"
			ondblclick="return showCodeList('pd_urgepayflag',[this,UrgePayFlagName],[0,1]);"
			onkeyup="return showCodeListKey('pd_urgepayflag',[this,UrgePayFlagName],[0,1]);"
			value="OOOO"><input class="codename" name="UrgePayFlagName"
			elementtype="nacessary"></td>
		<TD class=title>缴费宽限期</TD>
		<TD class=input><Input class=common name=GracePeriod2
			verify="缴费宽限期|NOTNULL&Integer">
		</TD>
		<TD class=title>缴费宽限期单位</TD>
		<td class="input"><input class="codeNo" name="GracePeriodUnit"
			ondblclick="return showCodeList('pd_graceperiodunit',[this,GPeriodUnitName],[0,1]);"
			onkeyup="return showCodeListKey('pd_graceperiodunit',[this,GPeriodUnitName],[0,1]);"><input
			class="codename" name="GPeriodUnitName"></td>
	</TR>
	<TR class=common>
		<TD class=title>逾期处理方式</TD>
		<td class="input"><input class="codeNo" name="OverdueDeal"
			ondblclick="return showCodeList('pd_overduedeal',[this,OverdueDealName],[0,1]);"
			onkeyup="return showCodeListKey('pd_overduedeal',[this,OverdueDealName],[0,1]);"><input
			class="codename" name="OverdueDealName"></td>
		<TD class=title></TD>
		<td class="input"></td>
		<TD class=title></TD>
		<td class="input"></td>
		<TD class=title STYLE="display: none;">宽限期算法
		</TD>
		<TD class=input STYLE="display: none;"><Input class=common
			name=GraceCalCode></TD>
		<TD class=title STYLE="display: none;">宽限期计算方式
		</TD>
		<td class="input" STYLE="display: none;"><input class="codeNo"
			name="GraceDateCalMode" value="0"
			ondblclick="return showCodeList('pd_gracedatecalmode',[this,GDateCalModeName],[0,1]);"
			onkeyup="return showCodeListKey('pd_gracedatecalmode',[this,GDateCalModeName],[0,1]);"><input
			class="codename" name="GDateCalModeName"
			value="以计算为准">
		</td>
	</TR>
</table>
<div STYLE="display: none;"><jsp:include
	page="DutyGraceCalCodeDefMain.jsp" /> <input id=savabutton1
	value="宽限期算法定义" type=button
	class="cssButton" type="button"
	onclick="InputDutyGraceCalCodeDefFace();"></div>
<div align=left id=saverp><input
	value="保  存"
	onclick="submitRiskPay('save')" class="cssButton" type="button">
<input value="修  改"
	onclick="submitRiskPay('update')" class="cssButton" type="button">
<input value="删  除"
	onclick="submitRiskPay('del')" class="cssButton" type="button">
</div>
<input type=hidden name=gOperator> <input type=hidden
	name=gRiskcode>
<hr />
</form>

<form action="./PDRiskDutySave.jsp" method=post name=fmA target="fraSubmit">
		<table>
			<tr>
				<td class=common><img src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divGroupPol1);">
				</td>
				<td class="titleImg">险种责任信息</td>
			</tr>
		</table>
		<DIV id=divGroupPol1 STYLE="display: ''">
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span id="spanMulline14Grid">
					</span></td>
				</tr>
			</table>
			<table>
				<tr>
					<td class="titleImg">责任属性定义：</td>
				</tr>
			</table>
			<table class=common>
				<tr class="common">
					<input type='hidden' name="DutyCode" maxlength=10">
					<input type=hidden name="riskCodeS">
					<input type=hidden name="operatorS">
					<td class="title">责任名称</td>
					<td class="input"><input class=common elementtype="nacessary"
						name="DutyName" maxlength=30" ></td>
					<TD class=title>责任可选标记</TD>
					<td class="input"><input class="codeNo" name="choFlag"
						CodeData="0|^M|必选^C|可选"
						ondblclick="return showCodeListEx('',[this,choFlagS],[0,1]);"
						onkeyup="return showCodeListKeyEx('',[this,choFlagS],[0,1]);"><input
						class="codename" name="choFlagS" elementtype="nacessary"
						verify="选择标记|NOTNULL"></td>
					<TD class=titleSize8></TD>
					<td class="input2"></td>
				</tr>
				<tr class="common">
					<td class="title">缴费终止期间</td>
					<td class="input"><input class=common name="DutyPayEndYear"
						maxlength=30" ></td>
					<TD class=title>缴费终止期间单位</TD>
					<td class="input"><input class="codeNo"
						name="DutyPayEndYearFlag"
						ondblclick="return showCodeList('pd_paystartyearflag',[this,DutyPayEndYearFlagS],[0,1]);"
						onkeyup="return showCodeListKey('pd_paystartyearflag',[this,DutyPayEndYearFlagS],[0,1]);"><input
						class="codename" name="DutyPayEndYearFlagS"></td>
					<TD class=titleSize8>录入保险期间</TD>
					<td class="input2"><input type="radio" value="Y"
						name="IsInsuYear" onclick="showDivInsuYear();" /> 是 <input
						type="radio" checked=true value="N" name="IsInsuYear"
						onclick="closeDivInsuYear();" /> 否 </td>
				</tr>

				<tr class="common">
					<TD class=title>缴费终止日期计算参照</TD>
					<td class=input><input class="codeNo" name="PayEndDateCalRef"
						ondblclick="return showCodeList('pd_PayEndDateCalRef',[this,PayEndDateCalRefS],[0,1]);"
						onkeyup="return showCodeListKey('pd_PayEndDateCalRef',[this,PayEndDateCalRefS],[0,1]);"><input
						class="codename" name="PayEndDateCalRefS"></td>
					<TD class=title>缴费终止日期计算方式</TD>
					<td class=input><input class="codeNo" name="PayEndDateCalMode"
						ondblclick="return showCodeList('pd_PayEndDateCalMode',[this,PayEndDateCalModeS],[0,1]);"
						onkeyup="return showCodeListKey('pd_PayEndDateCalMode',[this,PayEndDateCalModeS],[0,1]);"><input
						class="codename" name="PayEndDateCalModeS"></td>
					<TD class=title>缴费终止期间关系</TD>
					<td class=input><input class="codeNo" name="PayEndYearRela"
						ondblclick="return showCodeList('PayEndYearRela',[this,PayEndYearRelaS],[0,1]);"
						onkeyup="return showCodeListKey('PayEndYearRela',[this,PayEndYearRelaS],[0,1]);"><input
						class="codename" name="PayEndYearRelaS" elementtype="nacessary">
					</td>
				</tr>
				<tr class="common">
					<TD class=title>起领期间</TD>
					<td class="input"><input class=common name="GetYear">
					</td>
					<TD class=title>起领期间单位</TD>
					<td class=input><input class="codeNo" name="GetYearFlag"
						ondblclick="return showCodeList('pd_paystartyearflag',[this,GetYearFlagS],[0,1]);"
						onkeyup="return showCodeListKey('pd_paystartyearflag',[this,GetYearFlagS],[0,1]);"><input
						class="codename" name="GetYearFlagS"></td>
					<TD class=title>起领期间关系</TD>
					<td class=input><input class="codeNo" name="GetYearRela"
						ondblclick="return showCodeList('GetYearRela',[this,GetYearRelaS],[0,1]);"
						onkeyup="return showCodeListKey('GetYearRela',[this,GetYearRelaS],[0,1]);"
						verify="起领期间关系|NOTNULL"><input class="codename"
						name="GetYearRelaS" elementtype="nacessary"></td>
				</tr>
				<tr class="common">
					<TD class=title>计算方法</TD>
					<td class=input><input class="codeNo" name="CalMode"
						ondblclick="return showCodeList('pd_CalMode',[this,CalModeS],[0,1]);"
						onkeyup="return showCodeListKey('pd_CalMode',[this,CalModeS],[0,1]);"><input
						class="codename" name="CalModeS"></td>
					<TD class=title>建议书计算方法</TD>
					<td class=input><input class="codeNo" name="PCalMode"
						ondblclick="return showCodeList('pd_pcalmode',[this,PCalModeS],[0,1]);"
						onkeyup="return showCodeListKey('pd_pcalmode',[this,PCalModeS],[0,1]);"><input
						class="codename" name="PCalModeS"></td>
					<TD class=title></TD>
					<td class="input"></td>
					<TD class=title STYLE="display: none;">基本保额算法</TD>
					<td class="input" STYLE="display: none;"><input class=common
						name="BasicCalCode" value="000003"></td>
					<TD class=title STYLE="display: none;">保额标记</TD>
					<td class=input STYLE="display: none;"><input class="codeNo"
						name="AmntFlag"
						ondblclick="return showCodeList('pd_AmntFlag',[this,AmntFlagS],[0,1]);"
						onkeyup="return showCodeListKey('pd_AmntFlag',[this,AmntFlagS],[0,1]);"><input
						class="codename" name="AmntFlagS"></td>
					<TD class=title STYLE="display: none;">算入保额标记</TD>
					<TD class=title STYLE="display: none;"><input class=common
						name="AddAmntFlagDuty"></td>
				</tr>
				<tr class="common">
					<TD class=title STYLE="display: none;">单位保额</TD>
					<td class="input" STYLE="display: none;"><input class=common
						name="VPU"></td>
				</tr>
			</table>

			<table class=common style='display: none' id='InsuYearFlagId'>
				<tr class="common">
					<td class="title">保险期间</td>
					<td class="input"><input class=common name="InsuYear">
					</td>
					<td class="title">保险期间单位</td>
					<td class="input"><input class="codeNo" name="InsuYearFlag"
						ondblclick="return showCodeList('pd_paystartyearflag',[this,InsuYearFlagS],[0,1]);"
						onkeyup="return showCodeListKey('pd_paystartyearflag',[this,InsuYearFlagS],[0,1]);"><input
						class="codename" name="InsuYearFlagS"></td>
					<TD class="title">保险期间关系</TD>
					<td class="input"><input class="codeNo" name="InsuYearRela"
						ondblclick="return showCodeList('InsuYearRela',[this,InsuYearRelaS],[0,1]);"
						onkeyup="return showCodeListKey('InsuYearRela',[this,InsuYearRelaS],[0,1]);"><input
						class="codename" name="InsuYearRelaS"></td>
				</tr>
			</table>
			<div align=left id=save2>
				<input value="保  存" onclick="Dutysave()" class="cssButton" type="button">
				<input value="修  改" onclick="Dutyupdate()" class="cssButton" type="button">
				<input value="删  除" onclick="Dutydel()" class="cssButton" type="button">
				<INPUT class=cssButton VALUE="多语言信息" TYPE=button id="MultLanguageDuty" onclick="defineMultLanguageDuty();">
			</div>
	</form>

<form action="./PDRiskDutyRelaSave.jsp" method=post name=fm
	target="fraSubmit">
<div id="DutyShowPart" style="display: none">
<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,payDutyGroup);"></td>

		<td class="titleImg">险种缴费责任</td>
	</tr>
</table>
<DIV id=payDutyGroup STYLE="display: ''"><!-- <input	type=button class=cssbutton value="责任信息定义" onclick="DutyDefi()">  -->
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline9Grid"> </span></td>
	</tr>
</table>
</BR>
<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,payDutyGroup);"></td>
		<td class="titleImg">缴费属性定义</td>
	</tr>
</table>
<!--  
						<table>
							<tr>
								<td class="titleImg">缴费属性定义：</TD>
									<TD><input value="查询缴费库" onclick="QueryPayLib()"
									class="cssButton" type="button"></td>
								<TD width=200></TD>
								<TD></TD>
								<TD width=200></TD>
								<TD></TD>
								<TD width=200></TD>
							
							</tr>
						</table>
						-->
<table class=common>
	<input type='hidden' name='DutyCodeS'>
	<tr>
		<td class="title">缴费名称
		</td>
		<td class="input"><input class=common name="payPlanName"
			elementtype="nacessary" maxlength=30"></td>
		<TD class=title>保费值是否可以为零</TD>
		<td class="input"><input class="codeNo" name="zeroFlag"
			ondblclick="return showCodeList('pd_yesno',[this,ZeroFlagS],[0,1]);"
			onkeyup="return showCodeListKey('pd_yesno',[this,ZeroFlagS],[0,1]);"><input
			class="codename" name="ZeroFlagS" elementtype="nacessary"
			verify="保费值是否可以为零|NOTNULL"></td>

		<td class="title">是否和账户相关</td>
		<td class="input"><input class="codeNo" name="needAccPay"
			verify="是否和账户相关|NOTNULL"
			ondblclick="return showCodeList('pd_needacc',[this,needAccPayS],[0,1]);"
			onkeyup="return showCodeListKey('pd_needacc',[this,needAccPayS],[0,1]);"><input
			class="codename" name="needAccPayS" elementtype="nacessary">
		</td>
		<td class="title" style='display: none'>宽限期定义
		</td>
		<input type=hidden name=payPlanCode>
		<td class="input" style='display: none''><input class=common
			name="GracePeriod"></td>
	</tr>
	<tr class="common">
		<td class="title">算法类型</td>
		<td class="input"><input class="codeNo" name="payCalType"
			verify="算法类型|NOTNULL"
			ondblclick="return showCodeList('pd_paycaltype',[this,payCalTypeS],[0,1]);"
			onkeyup="return showCodeListKey('pd_paycaltype',[this,payCalTypeS],[0,1]);"><input
			class="codename" name="payCalTypeS" elementtype="nacessary">
		</td>
		<td class="title">算法编码</td>
		<td class="input"><input class=common name="payCalCode"
			maxlength=10"  ></td>
		<TD class=titleSize8>录入缴费起期</TD>
		<td class="input2"><input type="radio" value="Y"
			name="ISPayStartYear" onclick="showPayStartCtrl();" />是
		<input type="radio" value="N" name="ISPayStartYear"
			onclick="closePayStartCtrl();" />否
		</td>
	</tr>
	<tr class="common" style='display: none' id='isAccRela'>
		<td class="title">是否和账户关联</td>
		<td class="input"><input type="radio" value="Y" name="isAccRela" />是
		<input type="radio" value="N" name="isAccRela" />否
		</td>
	</tr>
	<tr class="common">
		<td class="title">缴费终止期间</td>
		<td class="input"><input class=common name="PayEndYear"
			verify="缴费终止期间|Integer"></td>
		<td class="title">缴费终止期间单位</td>
		<td class="input"><input class="codeNo" name="PayEndYearFlag"
			ondblclick="return showCodeList('pd_payendyearflag',[this,PayEndYearFlagS],[0,1]);"
			onkeyup="return showCodeListKey('pd_payendyearflag',[this,PayEndYearFlagS],[0,1]);"><input
			class="codename" name="PayEndYearFlagS"></td>
		<td class="title">缴费终止日期计算方式</td>
		<td class="input"><input class="codeNo" name="PayEndDateCalMode"
			ondblclick="return showCodeList('pd_payenddatecalmode',[this,PayEndDateCalModeS],[0,1]);"
			onkeyup="return showCodeListKey('pd_payenddatecalmode',[this,PayEndDateCalModeS],[0,1]);"><input
			class="codename" name="PayEndDateCalModeS"></td>
	</tr>
	<tr class="common">
		<td class="title" STYLE="display: none;">缴费间隔
		</td>
		<td class="input" STYLE="display: none;"><input class=common
			name="PayIntv"></td>
		<td class="title">催缴标记</td>
		<td class="input"><input class="codeNo" name="UrgePayFlag"
			ondblclick="return showCodeList('pd_urgepayflag',[this,UrgePayFlagS],[0,1]);"
			onkeyup="return showCodeListKey('pd_urgepayflag',[this,UrgePayFlagS],[0,1]);"
			value="XXXX"><input class="codename" elementtype="nacessary"
			name="UrgePayFlagS"></td>
		<td class="title">交费目的分类</td>
		<td class="input"><input class="codeNo" name="PayAimClass"
			ondblclick="return showCodeList('pd_payaimclass',[this,PayAimClassS],[0,1]);"
			onkeyup="return showCodeListKey('pd_payaimclass',[this,PayAimClassS],[0,1]);"><input
			class="codename" name="PayAimClassS"></td>
	</tr>
	<tr class="common">
		<TD class=title>投资分类</TD>
		<td class="input"><input class="codeNo" name="InvestType"
			ondblclick="return showCodeList('pd_InvestTypes',[this,InvestTypeS],[0,1]);"
			onkeyup="return showCodeListKey('pd_InvestTypes',[this,InvestTypeS],[0,1]);"><input
			class="codename" name="InvestTypeS"></td>
		<TD class=title>保费重算标记</TD>
		<td class="input"><input class="codeNo" name="RCalPremFlag"
			ondblclick="return showCodeList('yesorno',[this,RCalPremFlagS],[0,1]);"
			onkeyup="return showCodeListKey('yesorno',[this,RCalPremFlagS],[0,1]);"><input
			class="codename" name="RCalPremFlagS"></td>
	</tr>
</table>
<table class=common style='display: none' id='isPayStartCtrl'>
	<tr class="common">
		<td class="title">缴费起始期间</td>
		<td class="input"><input class=common name="PayStartYear"
			verify="缴费起始期间|Integer"></td>
		<td class="title">缴费起始期间单位</td>
		<td class="input"><input class="codeNo" name="PayStartYearFlag"
			ondblclick="return showCodeList('pd_paystartyearflag',[this,PayStartFlagName],[0,1]);"
			onkeyup="return showCodeListKey('pd_paystartyearflag',[this,PayStartFlagName],[0,1]);"><input
			class="codename" name="PayStartFlagName"></td>
		<td class="title">缴费起始日期计算参照</td>
		<td class="input"><input class="codeNo" name="PayStartDateCalRef"
			ondblclick="return showCodeList('pd_paystdatecalref',[this,PaySDCalRefName],[0,1]);"
			onkeyup="return showCodeListKey('pd_paystdatecalref',[this,PaySDCalRefName],[0,1]);"><input
			class="codename" name="PaySDCalRefName"></td>
	</tr>
	<tr class="common">
		<td class="title">缴费起始日期计算方式</td>
		<td class="input"><input class="codeNo"
			name="PayStartDateCalMode"
			ondblclick="return showCodeList('pd_paystdatecalmode',[this,PaySDCalModeName],[0,1]);"
			onkeyup="return showCodeListKey('pd_paystdatecalmode',[this,PaySDCalModeName],[0,1]);"><input
			class="codename" name="PaySDCalModeName"></td>
	</tr>
</table>
<input class="cssButton" id=savabutton2
	value="数据表定义" type=button
	onclick="button100( )" type="button">

<hr>
<jsp:include page="DutyPayCalCodeDefMain.jsp" /> <!-- <input value="险种录入界面约定" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" >  -->
<!-- <input value="险种缴费间隔定义" type=button  onclick="button109( )" class="cssButton" type="button" >  -->

<input id=savabutton3 value="保费算法定义"
	type=button class="cssButton" type="button"
	onclick="InputDutyPayCalCodeDefFace('99');">
<hr>
<table class=common>
	<tr class=common>
		<td class=title>反算算法编码</td>
		<td class="input"><input class=common name="payCalCodeBack"
			maxlength=10" ></td>
		<td class=title></td>
		<td class=input></td>
		<td class=title></td>
		<td class=input></td>
	</tr>
	<tr class=common>
		<jsp:include page="DutyPayCalCodeDefBackMain.jsp" />
	</tr>
	<tr class=common>
		<td class="input"><input id=savabutton4
			value="反算算法定义" type=button
			class="cssButton" type="button"
			onclick="InputDutyPayCalCodeDefFaceBack('99');"></td>
	</tr>
</table>
<hr>
<table class=common>
	<tr class=common>
		<TD class=title>保费重算时点算法</TD>
		<td class="input"><input class=common name="RCalPremCode">
		</td>
		<td class=title></td>
		<td class=input></td>
		<td class=title></td>
		<td class=input></td>
	</tr>
</table>
<table class=common border=0>
	<tr class=common>
		<td class=title>算法类型</td>
		<td class=common><input type="radio" value="Y"
			name="RCalPremCodeType"
			onclick="setRadioFlag('RCalPremCodeType','Y',0)" checked /> 规则引擎
		<input type="radio" value="N" name="RCalPremCodeType"
			onclick="setRadioFlag('RCalPremCodeType','N',1)" /> SQL</td>
	</tr>
</table>
<input id=savabutton8 value="保费重算时点算法定义"
	type=button class="cssButton" type="button"
	onclick="calCodedeFined('RCalPremCode','RCalPremCodeType');">
<hr>
<table class=common>
	<tr class=common>
		<TD class=title>建议书算法</TD>
		<td class="input"><input class=common name="PCalCode"></td>
		<td class=title></td>
		<td class=input></td>
		<td class=title></td>
		<td class=input></td>
	</tr>
</table>
<table class=common border=0>
	<tr class=common>
		<td class=title>算法类型</td>
		<td class=common><input type="radio" value="Y"
			name="PCalCodeType" onclick="setRadioFlag('PCalCodeType','Y',0)"
			checked /> 规则引擎 <input type="radio"
			value="N" name="PCalCodeType"
			onclick="setRadioFlag('PCalCodeType','N',1)" /> SQL</td>
	</tr>
</table>
<input id=savabutton7 value="建议书算法定义"
	type=button class="cssButton" type="button"
	onclick="calCodedeFined('PCalCode','PCalCodeType');">
<hr>
<div align=left id=save4><input
	value="重  置" onclick="Payinit()"
	class="cssButton" type="button"> <input
	value="保  存" onclick="Paysave()"
	class="cssButton" type="button"> <input
	value="修  改" onclick="Payupdate()"
	class="cssButton" type="button"> <input
	value="删  除" onclick="Paydel()"
	class="cssButton" type="button"> <INPUT class=cssButton
	VALUE="多语言信息" TYPE=button
	id="MultLanguagePay" onclick="defineMultLanguagePay();"></div>
</DIV>
<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,getDutyGroup);"></td>
		<td class="titleImg">给付责任信息</td>
	</tr>
</table>
<DIV id=getDutyGroup STYLE="display: ''"><!--input type=button class=cssbutton value="修  改" onclick="ModifyGet()"-->
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline11Grid"> </span></td>
	</tr>
</table>
</BR>
<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,getDutyGroup);"></td>
		<td class="titleImg">给付属性定义</td>
	</tr>
</table>
<!-- 
					<table>
						<tr>
							<td class="titleImg">给付属性定义：</TD>
					 
								<TD><input value="查询给付库" onclick="QueryGetLib()"
								class="cssButton" type="button"></td>
									<TD width=200></TD>
							<TD width=200></TD>
							<TD></TD>
							<TD width=200></TD>
							<TD></TD>
							<TD width=200></TD>
						
						</tr>
					</table>-->
<table class=common border=0>
	<tr class="common">
		<input type=hidden name="getDutyCode" maxlength=6">
		<td class="title">给付名称
		</td>
		<td class="input"><input class=common elementtype="nacessary" id=getDutyName
			name="getDutyName" maxlength=30"></td>
		<td class="title">算入保额标记</td>
		<td class="input"><input class="codeNo" name="AddAmntFlag"
			ondblclick="return showCodeList('pd_yesno',[this,AddAmntFlagS],[0,1]);"
			onkeyup="return showCodeListKey('pd_yesno',[this,AddAmntFlagS],[0,1]);"><input
			class="codename" name="AddAmntFlagS" elementtype="nacessary"
			verify="算入保额标记|NOTNULL"></td>
		<td class="title">是否和账户相关</td>
		<td class="input"><input class="codeNo" name="needAccGet"
			verify="是否和账户相关|NOTNULL"
			ondblclick="return showCodeList('pd_needacc',[this,needAccGetS],[0,1]);"
			onkeyup="return showCodeListKey('pd_needacc',[this,needAccGetS],[0,1]);"><input
			class="codename" name="needAccGetS" elementtype="nacessary">
		</td>
	</tr>
	<tr class="common">
		<td class="title">给付类型</td>
		<td class="input"><input class="codeNo" name="type"
			ondblclick="return showCodeList('pd_dutygettype',[this,typeS],[0,1]);"
			onkeyup="return showCodeListKey('pd_dutygettype',[this,typeS],[0,1]);"><input
			class="codename" name="typeS" elementtype="nacessary"
			verify="给付类型|NOTNULL"></td>
		<TD class=title>满期金/年金标记
		</TD>
		<td class="input"><input class="codeNo" name="GetType1"
			ondblclick="return showCodeList('pd_gettype1',[this,GetType1S],[0,1]);"
			onkeyup="return showCodeListKey('pd_gettype1',[this,GetType1S],[0,1]);"><input
			class="codename" name="GetType1S"></td>
		<TD class=title>给付类型</TD>
		<td class="input"><input class="codeNo" name="GetType3"
			ondblclick="return showCodeList('pd_gettype3',[this,GetType3S],[0,1]);"
			onkeyup="return showCodeListKey('pd_gettype3',[this,GetType3S],[0,1]);"><input
			class="codename" name="GetType3S"></td>
	</tr>
	<tr class="common">

		<td class="title">算法类型</td>
		<td class="input"><input class="codeNo" name="getCalType"
			verify="算法类型|NOTNULL"
			ondblclick="return showCodeList('pd_getcaltype',[this,getcaltypeS],[0,1]);"
			onkeyup="return showCodeListKey('pd_getcaltype',[this,getcaltypeS],[0,1]);"><input
			class="codename" name="getcaltypeS"></td>
		<td class="title">算法编码</td>
		<td class="input"><input class=common name="getCalCode"
			maxlength=10></td>
		<TD class=title>给付金是否可以为零</TD>
		<td class="input"><input class="codeNo" name="zeroGetFlag"
			ondblclick="return showCodeList('pd_yesno',[this,zeroGetFlagS],[0,1]);"
			onkeyup="return showCodeListKey('pd_yesno',[this,zeroGetFlagS],[0,1]);"><input
			class="codename" name="zeroGetFlagS"></td>
	</tr>
	<tr class="common">

		<td class="title">催付标记</td>
		<td class="input"><input class="codeNo" name="UrgeGetFlag"
			ondblclick="return showCodeList('pd_urgegetflag',[this,UrgeGetFlagS],[0,1]);"
			onkeyup="return showCodeListKey('pd_urgegetflag',[this,UrgeGetFlagS],[0,1]);"><input
			class="codename" name="UrgeGetFlagS"></td>
		<td class="title" STYLE="display: none;">是否是账户结清后才能申请
		</td>
		<td class="input" STYLE="display: none;"><input class="codeNo"
			name="NeedCancelAcc"
			ondblclick="return showCodeList('pd_NeedCancelAcc',[this,NeedCancelAccS],[0,1]);"
			onkeyup="return showCodeListKey('pd_NeedCancelAcc',[this,NeedCancelAccS],[0,1]);"><input
			class="codename" name="NeedCancelAccS"></td>
		<td class="title" STYLE="display: none;">默认申请标志
		</td>
		<td class="input" STYLE="display: none;"><input class="codeNo"
			name="CanGet"
			ondblclick="return showCodeList('pd_CanGet',[this,CanGetS],[0,1]);"
			onkeyup="return showCodeListKey('pd_CanGet',[this,CanGetS],[0,1]);"><input
			class="codename" name="CanGetS"></td>
		<td class="title"></td>
		<td class="input"></td>
	</tr>

	<tr class="common">
		<td class="title" id="getInvName" style='display: none'>给付间隔
		</td>
		<td class="input" id="getInvValue" style='display: none'><input
			class=common name="GetIntv" maxlength=6"></td>
		<TD class=titleSize6>起领期间是否限制</TD>
		<td class=input2><input type="radio" value="1"
			name="IsGetYearFlag" onclick='showGetYearPart();' /> 是
		<input type="radio" value="0" name="IsGetYearFlag"
			onclick='closeGetYearPart();' checked=true /> 否
		</td>

		<TD class=titleSize6>止领期间是否限制</TD>
		<td class=input2><input type="radio" value="1"
			name="IsGetEndPeriodFlag" onclick='showGetEndPeriodPart();' /> 是
		<input type="radio" value="0" name="IsGetEndPeriodFlag"
			onclick='closeGetEndPeriodPart();' checked=true /> 否
		</td>
	</tr>

</table>

<table class=common style='display: none' id='GetYearPartId'>
	<tr class="common">
		<td class="title">起领期间</td>
		<td class="input"><input class=common name="GetYear" maxlength=6">
		</td>
		<td class="title">起领期间单位</td>
		<td class="input"><input class="codeNo" name="GetYearFlag1"
			ondblclick="return showCodeList('pd_paystartyearflag',[this,GetYearFlag1S],[0,1]);"
			onkeyup="return showCodeListKey('pd_paystartyearflag',[this,GetYearFlag1S],[0,1]);"><input
			class="codename" name="GetYearFlag1S"></td>

		<td class="title">起领期间参照</td>
		<td class="input"><input class="codeNo" name="StartDateCalRef"
			ondblclick="return showCodeList('startdatecalref',[this,StartDateCalRefS],[0,1]);"
			onkeyup="return showCodeListKey('startdatecalref',[this,StartDateCalRefS],[0,1]);"><input
			class="codename" name="StartDateCalRefS"></td>
		<td></td>
		<td></td>
	</tr>

	<tr class="common">
		<td class="title">起领日期计算方式</td>
		<td class="input"><input class="codeNo" name="StartDateCalMode"
			ondblclick="return showCodeList('startdatecalmode',[this,StartDateCalModeS],[0,1]);"
			onkeyup="return showCodeListKey('startdatecalmode',[this,StartDateCalModeS],[0,1]);"><input
			class="codename" name="StartDateCalModeS"></td>
	</tr>
</table>

<table class=common style='display: none' id='GetEndPeriodPartId'>
	<tr class="common">
		<td class="title">止领期间</td>
		<td class="input"><input class=common name="GetEndPeriod"
			maxlength=6"></td>
		<td class="title">止领期间单位</td>
		<td class="input"><input class="codeNo" name="GetEndUnit"
			ondblclick="return showCodeList('pd_paystartyearflag',[this,GetEndUnitS],[0,1]);"
			onkeyup="return showCodeListKey('pd_paystartyearflag',[this,GetEndUnitS],[0,1]);"><input
			class="codename" name="GetEndUnitS"></td>

		<td class="title">止领期间参照</td>
		<td class="input"><input class="codeNo" name="EndDateCalRef"
			ondblclick="return showCodeList('startdatecalref',[this,EndDateCalRefS],[0,1]);"
			onkeyup="return showCodeListKey('startdatecalref',[this,EndDateCalRefS],[0,1]);"><input
			class="codename" name="EndDateCalRefS"></td>
		<td></td>
		<td></td>

	</tr>

	<tr class="common">
		<td class="title">止领日期计算方式</td>
		<td class="input"><input class="codeNo" name="EndDateCalMode"
			ondblclick="return showCodeList('enddatecalmode',[this,EndDateCalModeS],[0,1]);"
			onkeyup="return showCodeListKey('enddatecalmode',[this,EndDateCalModeS],[0,1]);"><input
			class="codename" name="EndDateCalModeS"></td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<TD class=title>保额重算标记</TD>
		<td class="input"><input class="codeNo" name="RCalAmntFlag"
			ondblclick="return showCodeList('yesorno',[this,RCalAmntFlagS],[0,1]);"
			onkeyup="return showCodeListKey('yesorno',[this,RCalAmntFlagS],[0,1]);"><input
			class="codename" name="RCalAmntFlagS"></td>
		<td class=title></td>
		<td class=input></td>
		<td class=title></td>
		<td class=input></td>
	</tr>
</table>
<jsp:include page="DutyGetCalCodeDefMain.jsp" />
<TABLE>

	<input id=savabutton6 value="保额算法定义"
		type=button class="cssButton" type="button"
		onclick="InputDutyGetCalCodeDefFace('99');">
</table>
<hr>
<table class=common>
	<tr class=common>
		<td class="title">反算算法编码</td>
		<td class="input"><input class=common name="getCalCodeBack"
			maxlength=10" ></td>
		<td class=title></td>
		<td class=input></td>
		<td class=title></td>
		<td class=input></td>
	</tr>
	<tr class=common>
		<jsp:include page="DutyGetCalCodeDefMainBack.jsp" />
	</tr>
	<tr class=common>
		<td class="input"><input id=savabutton5
			value="反算算法定义" type=button
			class="cssButton" type="button"
			onclick="InputDutyGetCalCodeDefFaceBack('99');"></td>
	</tr>
</table>
<hr>
<table class=common>
	<tr class=common>
		<TD class=title>保额重算时点算法</TD>
		<td class="input"><input class=common name="RCalAmntCode">
		</td>
		<td class=title></td>
		<td class=input></td>
		<td class=title></td>
		<td class=input></td>
	</tr>
</table>
<table class=common border=0>
	<tr class=common>
		<td class=title>算法类型</td>
		<td class=common><input type="radio" value="Y"
			name="RCalAmntCodeType"
			onclick="setRadioFlag('RCalAmntCodeType','Y',0)" checked /> 规则引擎
		<input type="radio" value="N" name="RCalAmntCodeType"
			onclick="setRadioFlag('RCalAmntCodeType','N',1)" /> SQL</td>
	</tr>
</table>
<input id=savabutton9 value="保额重算时点算法定义"
	type=button class="cssButton" type="button"
	onclick="calCodedeFined('RCalAmntCode','RCalAmntCodeType');">
<hr>
<table class=common>
	<tr class=common>
		<TD class=title>建议书算法</TD>
		<td class="input"><input class=common name="PCalCodeAmnt">
		</td>
		<td class=title></td>
		<td class=input></td>
		<td class=title></td>
		<td class=input></td>
	</tr>
</table>
<table class=common border=0>
	<tr class=common>
		<td class=title>算法类型</td>
		<td class=common><input type="radio" value="Y"
			name="PCalCodeAmntType"
			onclick="setRadioFlag('PCalCodeAmntType','Y',0)" checked /> 规则引擎
		<input type="radio" value="N" name="PCalCodeAmntType"
			onclick="setRadioFlag('PCalCodeAmntType','N',1)" /> SQL</td>
	</tr>
</table>
<input id=savabutton10 value="建议书算法定义"
	type=button class="cssButton" type="button"
	onclick="calCodedeFined('PCalCodeAmnt','PCalCodeAmntType');">
<hr>
<div align=left id=save3><input
	value="重  置" onclick="Getinit()"
	class="cssButton" type="button"> <input
	value="保  存" onclick="Getsave()"
	class="cssButton" type="button"> <input
	value="修  改" onclick="Getupdate()"
	class="cssButton" type="button"> <input
	value="删  除" onclick="Getdel()"
	class="cssButton" type="button"> <INPUT class=cssButton
	VALUE="多语言信息" TYPE=button
	id="MultLanguageGet" onclick="defineMultLanguageGet();"></div>
<div id=divGetAlive style="display: none;"><input
	value="责任生存给付" id="btnClmAlive"
	name="btnGetAlive" onclick="getAliveDif( )" class="cssButton"
	type="button"></div>


<input type=hidden name="riskCodeS"> <input type=hidden
	name=payOrGet> <input type=hidden name=operator></DIV>
</DIV>
<hr>
</form>



<!-- -------------------------------隐藏域------------------------------------->






<!-- 账户缴费和给付的定义 -->
<div id="DivAccInsu" style="display: none;">
<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,defRiskAccDivDetail);">
		</td>
		<td class="titleImg">账户缴费和给付定义</td>
	</tr>
</table>
<div id="defRiskAccDivDetail" style="display: ''"><input
	value="险种账户定义" type=button
	id="btnRiskAccountDefi" onclick="button84( )" class="cssButton"
	type="button"> <input
	value="账户缴费定义" type=button
	id="btnRiskAccPay" onclick="button116( )" class="cssButton"> <input
	value="账户给付定义" type=button
	id="btnRiskAccGet" onclick="button231( )" class="cssButton"> <input
	value="管理费定义" type=button
	id="btnRiskAccFee" onclick="button184( )" class="cssButton"></div>
<br>

</div>
<hr>
<input value="记事本"
	onclick="button85( )" class="cssButton" type="hidden">
<input value="问题件录入"
	onclick="IssueInput( )" class="cssButton" type="hidden">
<input value="问题件回复/查询" type=button
	id="btnIssueQuery" onclick="IssueQuery( )" class="cssButton"
	type="button">
<hr>
<input id=baseInfoDoneId value="基础信息录入完毕"
	type=button onclick="baseInfoDone()" class="cssButton" type="button"
	name=baseInfoDone>
<input value="返  回"
	onclick="returnParent( )" class="cssButton" type="button">
<br>
<hr>


<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
