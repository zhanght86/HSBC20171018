<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>

<%
	//�������ƣ�PDRiskDefiInput.jsp
	//�����ܣ���Ʒ������Ϣ¼��
	//�������ڣ�2009-3-12
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
		<td class="titleImg">��Ʒ�ӿ����ݶ��壺</td>
</table>
<table class=common>
	<tr class=common>
		<td class="title">���ִ���</td>
		<td class=input5><input class=common name="QProductCode"></td>
		<td class="title">PlanCode</td>
		<td class=input5><input class="common" name="QIProductCode"></td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class="title">���Ҫ�أ�</td>
	</tr>
	<tr class=common>
		<td class =title ><input type="checkbox" value="Y" name="CIsEffectiveDate" />��Ч����</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsSpouseCode" />��ż����</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsStaffCode" />Ա���ۿ�</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsPremPayPeriod" />�����ڼ�</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsBenefitPeriod" />�����ڼ�</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsBenefitOption" />����</td>
	</tr>
	<tr class=common>
		<td class =title ><input type="checkbox" value="Y" name="CIsChannel" />����</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsCurrency" />����</td>
		<td class =title ><input type="checkbox" value="Y" name="CIsPar" />�ֺ�</td>	
		<td class =title ></td>
		<td class =title ></td>
		<td class =title ></td>
	</tr>
	<tr>
	</tr>
</table>
</br>
<div align=left><input value="��  ѯ" type=button onclick="query();"
	class="cssButton" type="button">
<table>
	<tr>
		<td class="titleImg">���ֱ�����PlanCode��Ӧ��Ϣ</td>
	 </tr>
</table>
<div>

<table class=common>
	<tr>
		<td><span id="spanMullineGrid"> </span></td>
	</tr>
</table>

</div>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
	onclick="turnPage.firstPage();"> <INPUT CLASS=cssbutton
	VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button
	onclick="turnPage.lastPage();"> <BR />
<BR />
<table>
	</BR>
	</BR>
	<table>
		<tr>
			<td class="titleImg">���ֱ�����PlanCode��Ӧ��ϸ��Ϣ</td>
	</table>
	<table class=common>
		<tr class=common>
			<td class="title">���ֱ���</td>
			<td class=input5><input class="codeNo" name="ProductCode"
				verify="���ֱ���|notnull"
				ondblclick="return showCodeList('pd_lmriskinfo',[this,ProductCodeS],[0,1],null,'1','1',1);"
				onkeyup="return showCodeList('pd_lmriskinfo',[this,ProductCodeS],[0,1],null,'1','1',1);"><input
				class="codename" name="ProductCodeS" elementtype=nacessary></td>
		</tr>
		<tr class=common>
			<td class="title">����</td>
			<td class=input5><input class=common name="BatchNo" verify="����|notnull" elementtype=nacessary></td>
			<td class="title">������������</td>
			<td class=input5><input class=common name="ProductChName"
				verify="������������|notnull"></td>
			<td class="title">����Ӣ������</td>
			<td class=input5><input class=common name="ProductEnName"
				verify="����Ӣ������|notnull"></td>
		</tr>
		<tr class=common>
			<td class="title">�ӿ����ֱ���</td>
			<td class=input5><input class=common name="IProductCode"
				verify="�ӿ����ֱ���|notnull" elementtype=nacessary></td>
			<td class="title">�ӿ�������������</td>
			<td class=input5><input class=common name="IProductChName"
				verify="�ӿ�������������"></td>
			<td class="title">�ӿ�����Ӣ������</td>
			<td class=input5><input class=common name="IProductEnName"
				verify="�ӿ�����Ӣ������"></td>
		</tr>
		<tr class=common>
			<td class="title">�Ƿ��뽻���ڼ����</td>
			<td class=input5><input class="codeNo" name="IsPremPayPeriod"
				readonly verify="�Ƿ��뽻���ڼ����|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsPremPayPeriodS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsPremPayPeriodS],[0,1]);"><input
				class="codename" name="IsPremPayPeriodS" elementtype=nacessary></td>
			<td class="title">�����ڼ䵥λ</td>
			<td class=input5><input class="codeNo" name="PremPayPeriodType"
				readonly verify="�����ڼ䵥λ|notnull"
				ondblclick="return showCodeList('riskperiodunit',[this,PremPayPeriodTypeS],[0,1]);"
				onkeyup="return showCodeListKey('riskperiodunit',[this,PremPayPeriodTypeS],[0,1]);"><input
				class="codename" name="PremPayPeriodTypeS" elementtype=nacessary></td>
			
			<td class="title">�����ڼ�</td>
			<td class=input5><input class=common name="PremPayPeriod" verify="�����ڼ�|notnull"
				validchar="0123456789" onkeypress="return blockChar(this)"
				elementtype=nacessary></td>
		</tr>
		<tr class=common>
			<td class="title">�Ƿ��뱣�ϼƻ����</td>
			<td class=input5><input class="codeNo" name="IsBenefitOption"
				readonly	verify="�Ƿ��뱣�ϼƻ����|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsBenefitOptionS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsBenefitOptionS],[0,1]);"><input
				class="codename" name="IsBenefitOptionS" elementtype=nacessary></td>
			<td class="title">���ϼƻ�����</td>
			<td class=input5><input class=codeNo name="BenefitOptionType"
				ondblclick="return showCodeList('benefitoptiontype',[this,BenefitOptionTypeS],[0,1]);"
				onkeyup="return showCodeListKey('benefitoptiontype',[this,BenefitOptionTypeS],[0,1]);"><input
				class="codename" name="BenefitOptionTypeS"></td>
			<td class="title">���ϼƻ����ֵ</td>
			<td class=input5><input class=common name="BenefitOption"></td>
		</tr>
		<tr class=common>
			<td class="title">�Ƿ��뱣���ڼ����</td>
			<td class=input5><input class="codeNo" name="IsBenefitPeriod"
				readonly  verify="�Ƿ��뱣���ڼ����|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsBenefitPeriodS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsBenefitPeriodS],[0,1]);"><input
				class="codename" name="IsBenefitPeriodS" elementtype=nacessary></td>
			<td class="title">�����ڼ䵥λ</td>
			<td class=input5><input class=codeNo name="BenefitPeriodType"
				ondblclick="return showCodeList('riskperiodunit',[this,BenefitPeriodTypeS],[0,1]);"
				onkeyup="return showCodeListKey('riskperiodunit',[this,BenefitPeriodTypeS],[0,1]);"><input
				class="codename" name="BenefitPeriodTypeS">
			</td>
			<td class="title">�����ڼ�</td>
			<td class=input5><input class=common name="BenefitPeriod"	
				validchar="0123456789" onkeypress="return blockChar(this)"></td>
		</tr>
		<tr class=common>
			<td class="title">�Ƿ��������������</td>
			<td class=input5><input class="codeNo" name="IsEffectiveDate"
				readonly verify="�Ƿ��������������"
				ondblclick="return false;return showCodeList('yesorno',[this,IsEffectiveDateS],[0,1]);"
				onkeyup="return false;return showCodeListKey('yesorno',[this,IsEffectiveDateS],[0,1]);" value="N"><input
				class="codename" name="IsEffectiveDateS" value="��" elementtype=nacessary></td>
			<td class="title">��������</td>
			<td class=input5><input class="multiDatePicker" dateFormat="short" verify="��������"
				name="EffectiveDate"/></td>
			<td class="title">ͣ������</td>
			<td class=input5><input class="multiDatePicker" dateFormat="short" verify="ͣ������"
				name="EffectiveEndDate"/></td>
		</tr>

		<tr class=common>
			<td class="title">�Ƿ�����ż�������</td>
			<td class=input5><input class="codeNo" name="IsSpouseCode"
				readonly 	verify="�Ƿ�����ż�������|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsSpouseCodeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsSpouseCodeS],[0,1]);"><input
				class="codename" name="IsSpouseCodeS" elementtype=nacessary></td>
			<td class="title">�Ƿ���ż����</td>
			<td class=input5><input class="codeNo" name="SpouseCode"
				ondblclick="return showCodeList('needflag',[this,SpouseCodeS],[0,1]);"
				onkeyup="return showCodeListKey('needflag',[this,SpouseCodeS],[0,1]);" ><input
				class='codename' name='SpouseCodeS'></td>
			<td class="title">�Ƿ���Ա�������</td>
			<td class=input5><input class="codeNo" name="IsStaffCode"
				readonly 	verify="�Ƿ���Ա�������|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsStaffCodeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsStaffCodeS],[0,1]);"><input
				class="codename" name="IsStaffCodeS" elementtype=nacessary></td>
		</tr>


		<tr class=common>
			<td class="title">�Ƿ�Ա����</td>
			<td class=input5><input class="codeNo" name="StaffCode"
				ondblclick="return showCodeList('needflag',[this,StaffCodeS],[0,1]);"
				onkeyup="return showCodeListKey('needflag',[this,StaffCodeS],[0,1]);"><input
				class="codename" name="StaffCodeS"></td>
			<td class="title">�Ƿ����������</td>
			<td class=input5><input class="codeNo" name="IsJoinCode" readonly	verify="�Ƿ����������|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsJoinCodeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsJoinCodeS],[0,1]);"><input
				class="codename" name="IsJoinCodeS" elementtype=nacessary></td>
			<td class="title">�Ƿ�����</td>
			<td class=input5><input class="codeNo" name="JoinCode"
				ondblclick="return showCodeList('needflag',[this,JoinCodeS],[0,1]);"
				onkeyup="return showCodeListKey('needflag',[this,JoinCodeS],[0,1]);"><input
				class="codename" name="JoinCodeS"></td>
		</tr>
		<tr class=common>
			<td class="title">�Ƿ���˱��������</td>
			<td class=input5><input class="codeNo" name="IsUreUWType"
				readonly	verify="�Ƿ���˱��������|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsUreUWTypeS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsUreUWTypeS],[0,1]);"><input
				class="codename" name="IsUreUWTypeS" elementtype=nacessary></td>
			<td class="title">�˱�����</td>
			<td class=input5><input class="codeNo" name="UreUWType"
				ondblclick="return showCodeList('uwtype3',[this,UreUWTypeS],[0,1]);"
				onkeyup="return showCodeListKey('uwtype3',[this,UreUWTypeS],[0,1]);"><input
				class="codename" name="UreUWTypeS"></td>
			<td class="title">�Ƿ������ۻ������</td>
			<td class=input5><input class="codeNo" name="IsChannel" readonly		verify="�Ƿ������ۻ������|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsChannelS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsChannelS],[0,1]);"><input
				class="codename" name="IsChannelS" elementtype=nacessary></td>
		</tr>
		<tr class=common>
			<td class="title">���ۻ���</td>
			<td class=input5><input class=codeNo name="Channel"
				ondblclick="return showCodeList('pd_salechnl',[this,ChannelS],[0,1]);"
				onkeyup="return showCodeListKey('pd_salechnl',[this,ChannelS],[0,1]);"><input
				class="codename" name="ChannelS" /></td>
			<td class="title">�Ƿ���������</td>
			<td class=input5><input class="codeNo" name="IsCurrency" readonly	verify="�Ƿ���������|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsCurrencyS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsCurrencyS],[0,1]);"><input
				class="codename" name="IsCurrencyS" elementtype=nacessary></td>
			<td class="title">����</td>
			<td class=input5>
			<input class="codeNo" name="Currency"	verify="�Ƿ���������"
				ondblclick="return showCodeList('pd_currency',[this,CurrencyName],[0,1]);"
				onkeyup="return showCodeListKey('pd_currency',[this,CurrencyName],[0,1]);"><input
				class="codename" name="CurrencyName">
			</td>
		</tr>
		<tr class=common>
			<td class="title">�Ƿ���ֺ��������</td>
			<td class=input5><input class="codeNo" name="IsPar" readonly		verify="�Ƿ���ֺ��������|notnull"
				ondblclick="return showCodeList('yesorno',[this,IsParS],[0,1]);"
				onkeyup="return showCodeListKey('yesorno',[this,IsParS],[0,1]);"><input
				class="codename" name="IsParS" elementtype=nacessary></td>
			<td class="title">�ֺ���</td>

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
		<td class="titleImg">��/������PlanCode��Ӧ��ϵ</td>
	 </tr>
	</table>
	<table class=common>
		<tr class=common>
			<td text-align:left colSpan=1><span id="spanSubRiskMullineGrid"></span>
			</td>
		</tr>

	</table>
	</div>
	
	<div align=left><input value="��  ��" type=button onclick="save();"
		class="cssButton" type="button"> <input value="��  ��"
		type=button onclick="modify();" class="cssButton" type="button">
	<input value="ɾ  ��" type=button onclick="del();" class="cssButton">
	<input value="��  ��" type=reset class="cssButton"></div>
</table>
<input type=hidden name="Operator"> <!--<input type=hidden name="ProductChName">
<input type=hidden name="ProductEnName">
-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
