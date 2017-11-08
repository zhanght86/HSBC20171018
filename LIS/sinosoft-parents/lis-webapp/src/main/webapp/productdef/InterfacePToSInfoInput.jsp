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
	String operator = tGI.Operator;
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="InterfacePToSInfo.js"></SCRIPT>
<%@include file="./InterfacePToSInfoInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body onload="initForm();initElementtype();">
<form action="./InterfacePToSInfoSave.jsp" method=post name=fm
	target="fraSubmit">
<table>
	<tr>
		<td class="titleImg">产品接口数据定义：</td>
</table>
<table class=common>
	<tr class=common>
		<td class="title">险种代码</td>
		<td class=input5><input class=common name="QProductCode"></td>
		<td class="title">PlanCode</td>
		<td class=input5><input class="common" name="QIProductCode"></td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class="title">相关要素：</td>
	</tr>
	<tr class=common>
		<td class =title ><input type="checkbox" value="Y" name="CIsEffectiveDate" />有效日期</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsSpouseCode" />配偶共购</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsStaffCode" />员工折扣</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsPremPayPeriod" />交费期间</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsBenefitPeriod" />保险期间</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsBenefitOption" />保障</td>
	</tr>
	<tr class=common>
		<td class =title ><input type="checkbox" value="Y" name="CIsChannel" />渠道</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsCurrency" />币种</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsPar" />分红</td>	
		<td class =title ></td>
		<td class =title ></td>
		<td class =title ></td>
	</tr>
	<tr>
	</tr>
</table>
</br>
<div align=left><input value="查  询" type=button onclick="query();"
	class="cssButton" type="button">
<table>
	<tr>
		<td class="titleImg">险种编码与PlanCode对应信息</td>
	 </tr>
</table>
<div>

<table class=common>
	<tr>
		<td><span id="spanMullineGrid"> </span></td>
	</tr>
</table>

</div>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
	onclick="turnPage.firstPage();"> <INPUT CLASS=cssbutton
	VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button
	onclick="turnPage.lastPage();"> <BR />
<BR />
<table>
	</BR>
	</BR>
	<table>
		<tr>
			<td class="titleImg">险种编码与PlanCode对应详细信息</td>
	</table>
	<table class=common>
		<tr class=common>
			<td class="title">险种编码</td>
			<td class=input5><input class="codeNo" name="ProductCode"
				verify="险种编码|notnull"
				ondblclick="return showCodeList('pd_lmriskinfo',[this,ProductCodeS],[0,1],null,'1','1',1);"
				onkeyup="return showCodeList('pd_lmriskinfo',[this,ProductCodeS],[0,1],null,'1','1',1);"><input
				class="codename" name="ProductCodeS" elementtype=nacessary></td>
		</tr>
		<tr class=common>
			<td class="title">批次</td>
			<td class=input5><input class=common name="BatchNo" verify="批次|notnull" elementtype=nacessary></td>
			<td class="title">险种中文名称</td>
			<td class=input5><input class=common name="ProductChName"
				verify="险种中文名称|notnull"></td>
			<td class="title">险种英文名称</td>
			<td class=input5><input class=common name="ProductEnName"
				verify="险种英文名称|notnull"></td>
		</tr>
		<tr class=common>
			<td class="title">接口险种编码</td>
			<td class=input5><input class=common name="IProductCode"
				verify="接口险种编码|notnull" elementtype=nacessary></td>
			<td class="title">接口险种中文名称</td>
			<td class=input5><input class=common name="IProductChName"
				verify="接口险种中文名称"></td>
			<td class="title">接口险种英文名称</td>
			<td class=input5><input class=common name="IProductEnName"
				verify="接口险种英文名称"></td>
		</tr>
		<tr class=common>
			<td class="title">是否与交费期间相关</td>
			<td class=input5><input class="codeNo" name="IsPremPayPeriod"
				readonly verify="是否与交费期间相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsPremPayPeriodS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsPremPayPeriodS],[0,1]);"><input
				class="codename" name="IsPremPayPeriodS" elementtype=nacessary></td>
			<td class="title">交费期间单位</td>
			<td class=input5><input class="codeNo" name="PremPayPeriodType"
				readonly verify="交费期间单位|notnull"
				ondblclick="return showCodeList('riskperiodunit',[this,PremPayPeriodTypeS],[0,1]);"
				onkeyup="return showCodeListKey('riskperiodunit',[this,PremPayPeriodTypeS],[0,1]);"><input
				class="codename" name="PremPayPeriodTypeS" elementtype=nacessary></td>
			
			<td class="title">交费期间</td>
			<td class=input5><input class=common name="PremPayPeriod" verify="交费期间|notnull"
				validchar="0123456789" onkeypress="return blockChar(this)"
				elementtype=nacessary></td>
		</tr>
		<tr class=common>
			<td class="title">是否与保障计划相关</td>
			<td class=input5><input class="codeNo" name="IsBenefitOption"
				readonly	verify="是否与保障计划相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsBenefitOptionS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsBenefitOptionS],[0,1]);"><input
				class="codename" name="IsBenefitOptionS" elementtype=nacessary></td>
			<td class="title">保障计划类型</td>
			<td class=input5><input class=codeNo name="BenefitOptionType"
				ondblclick="return showCodeList('benefitoptiontype',[this,BenefitOptionTypeS],[0,1]);"
				onkeyup="return showCodeListKey('benefitoptiontype',[this,BenefitOptionTypeS],[0,1]);"><input
				class="codename" name="BenefitOptionTypeS"></td>
			<td class="title">保障计划相关值</td>
			<td class=input5><input class=common name="BenefitOption"></td>
		</tr>
		<tr class=common>
			<td class="title">是否与保险期间相关</td>
			<td class=input5><input class="codeNo" name="IsBenefitPeriod"
				readonly  verify="是否与保险期间相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsBenefitPeriodS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsBenefitPeriodS],[0,1]);"><input
				class="codename" name="IsBenefitPeriodS" elementtype=nacessary></td>
			<td class="title">保险期间单位</td>
			<td class=input5><input class=codeNo name="BenefitPeriodType"
				ondblclick="return showCodeList('riskperiodunit',[this,BenefitPeriodTypeS],[0,1]);"
				onkeyup="return showCodeListKey('riskperiodunit',[this,BenefitPeriodTypeS],[0,1]);"><input
				class="codename" name="BenefitPeriodTypeS">
			</td>
			<td class="title">保险期间</td>
			<td class=input5><input class=common name="BenefitPeriod"	
				validchar="0123456789" onkeypress="return blockChar(this)"></td>
		</tr>
		<tr class=common>
			<td class="title">是否与销售日期相关</td>
			<td class=input5><input class="codeNo" name="IsEffectiveDate"
				readonly verify="是否与销售日期相关"
				ondblclick="return false;return showCodeList('yesorno',[this,IsEffectiveDateS],[0,1]);"
				onkeyup="return false;return showCodeListKey('yesorno',[this,IsEffectiveDateS],[0,1]);" value="N"><input
				class="codename" name="IsEffectiveDateS" value="否" elementtype=nacessary></td>
			<td class="title">开售日期</td>
			<td class=input5><input class="multiDatePicker" dateFormat="short" verify="开售日期"
				name="EffectiveDate"/></td>
			<td class="title">停售日期</td>
			<td class=input5><input class="multiDatePicker" dateFormat="short" verify="停售日期"
				name="EffectiveEndDate"/></td>
		</tr>

		<tr class=common>
			<td class="title">是否与配偶共购相关</td>
			<td class=input5><input class="codeNo" name="IsSpouseCode"
				readonly 	verify="是否与配偶共购相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsSpouseCodeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsSpouseCodeS],[0,1]);"><input
				class="codename" name="IsSpouseCodeS" elementtype=nacessary></td>
			<td class="title">是否配偶共购</td>
			<td class=input5><input class="codeNo" name="SpouseCode"
				ondblclick="return showCodeList('needflag',[this,SpouseCodeS],[0,1]);"
				onkeyup="return showCodeListKey('needflag',[this,SpouseCodeS],[0,1]);" ><input
				class='codename' name='SpouseCodeS'></td>
			<td class="title">是否与员工单相关</td>
			<td class=input5><input class="codeNo" name="IsStaffCode"
				readonly 	verify="是否与员工单相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsStaffCodeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsStaffCodeS],[0,1]);"><input
				class="codename" name="IsStaffCodeS" elementtype=nacessary></td>
		</tr>


		<tr class=common>
			<td class="title">是否员工单</td>
			<td class=input5><input class="codeNo" name="StaffCode"
				ondblclick="return showCodeList('needflag',[this,StaffCodeS],[0,1]);"
				onkeyup="return showCodeListKey('needflag',[this,StaffCodeS],[0,1]);"><input
				class="codename" name="StaffCodeS"></td>
			<td class="title">是否与联保相关</td>
			<td class=input5><input class="codeNo" name="IsJoinCode" readonly	verify="是否与联保相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsJoinCodeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsJoinCodeS],[0,1]);"><input
				class="codename" name="IsJoinCodeS" elementtype=nacessary></td>
			<td class="title">是否联保</td>
			<td class=input5><input class="codeNo" name="JoinCode"
				ondblclick="return showCodeList('needflag',[this,JoinCodeS],[0,1]);"
				onkeyup="return showCodeListKey('needflag',[this,JoinCodeS],[0,1]);"><input
				class="codename" name="JoinCodeS"></td>
		</tr>
		<tr class=common>
			<td class="title">是否与核保类型相关</td>
			<td class=input5><input class="codeNo" name="IsUreUWType"
				readonly	verify="是否与核保类型相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsUreUWTypeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsUreUWTypeS],[0,1]);"><input
				class="codename" name="IsUreUWTypeS" elementtype=nacessary></td>
			<td class="title">核保类型</td>
			<td class=input5><input class="codeNo" name="UreUWType"
				ondblclick="return showCodeList('uwtype3',[this,UreUWTypeS],[0,1]);"
				onkeyup="return showCodeListKey('uwtype3',[this,UreUWTypeS],[0,1]);"><input
				class="codename" name="UreUWTypeS"></td>
			<td class="title">是否与销售机构相关</td>
			<td class=input5><input class="codeNo" name="IsChannel" readonly		verify="是否与销售机构相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsChannelS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsChannelS],[0,1]);"><input
				class="codename" name="IsChannelS" elementtype=nacessary></td>
		</tr>
		<tr class=common>
			<td class="title">销售机构</td>
			<td class=input5><input class=codeNo name="Channel"
				ondblclick="return showCodeList('pd_salechnl',[this,ChannelS],[0,1]);"
				onkeyup="return showCodeListKey('pd_salechnl',[this,ChannelS],[0,1]);"><input
				class="codename" name="ChannelS" /></td>
			<td class="title">是否与币种相关</td>
			<td class=input5><input class="codeNo" name="IsCurrency" readonly	verify="是否与币种相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsCurrencyS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsCurrencyS],[0,1]);"><input
				class="codename" name="IsCurrencyS" elementtype=nacessary></td>
			<td class="title">币种</td>
			<td class=input5>
			<input class="codeNo" name="Currency"	verify="是否与币种相关"
				ondblclick="return showCodeList('pd_currency',[this,CurrencyName],[0,1]);"
				onkeyup="return showCodeListKey('pd_currency',[this,CurrencyName],[0,1]);"><input
				class="codename" name="CurrencyName">
			</td>
		</tr>
		<tr class=common>
			<td class="title">是否与分红类型相关</td>
			<td class=input5><input class="codeNo" name="IsPar" readonly		verify="是否与分红类型相关|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsParS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsParS],[0,1]);"><input
				class="codename" name="IsParS" elementtype=nacessary></td>
			<td class="title">分红标记</td>

			<td class=input5><input class="codeNo" name="Par"
				ondblclick="return showCodeList('pars',[this,ParS],[0,1]);"
				onkeyup="return showCodeListKey('pars',[this,ParS],[0,1]);"><input
				class="codename" name="ParS"></td>
		</tr>
		<tr class=common>
		</tr>
	</table>
	<div>
	<table>
	<tr>
		<td class="titleImg">主/附加险PlanCode对应关系</td>
	 </tr>
	</table>
	<table class=common>
		<tr class=common>
			<td text-align:left colSpan=1><span id="spanSubRiskMullineGrid"></span>
			</td>
		</tr>

	</table>
	</div>
	
	<div align=left><input value="增  加" type=button onclick="save();"
		class="cssButton" type="button"> <input value="修  改"
		type=button onclick="modify();" class="cssButton" type="button">
	<input value="删  除" type=button onclick="del();" class="cssButton">
	<input value="重  置" type=reset class="cssButton"></div>
</table>
<input type=hidden name="Operator"> <!--<input type=hidden name="ProductChName">
<input type=hidden name="ProductEnName">
-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
