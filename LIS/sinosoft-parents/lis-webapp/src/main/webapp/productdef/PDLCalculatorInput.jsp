<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�PDLCalculatorInput.jsp
	//�����ܣ��ۼ�������
	//�������ڣ�2016-5-16
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5><input class="common wid" name="RiskCode"
			id=RiskCode readonly></td>
		<td class=title5>��������</td>
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
		<td class="titleImg">�Ѷ�����ۼ�����Ϣ<font size=1 color='#ff0000'><b>�����������β㼶�������е��ۼ�����</b></font></td>
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
		<td class="titleImg">�Ѷ�����ۼ�����Ϣ<font size=1 color='#ff0000'><b>�����������β㼶���е��ۼ�����</b></font></td>
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
		<td class="titleImg">�ۼ�����ϸ������Ϣ</td>
	</tr>
</table>
</div>
<div class=maxbox1>
<div id="divInvAssBuildInfo4">
<table classs=common>
	<tr class=common>
		<!-- PD_LCalculatorMain �ۼ���������Ϣ-->
		<td class=tittle normal>�ۼ�������</td>
		<td class=input normal><Input class="common wid"
			name=CalculatorName id=CalculatorName verify="�ۼ�������|NOTNUlL"><font
			size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>�ۼ����㼶</td>
		<td class=input normal><Input class="codeno" name=CtrlLevel
			id=CtrlLevel readonly=true verify="�ۼ����㼶|NOTNUlL&code:pd_ctrllevel"
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_ctrllevel',[this,CtrlLevelName],[0,1]);"
			onDblClick="return showCodeList('pd_ctrllevel',[this,CtrlLevelName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_ctrllevel',[this,CtrlLevelName],[0,1]);"><input
			class=codename name=CtrlLevelName id=CtrlLevelName readonly=true><font
			size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
			<td class=tittle normal>�ۼ�������</td>
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
		<td class=tittle normal>Ҫ������</td>
		<td class=input normal><Input class="codeno" name=CtrlFactorType
			id=CtrlFactorType readonly=true
			verify="�ۼ���Ҫ������|NOTNUlL&code:pd_ctrlFactorType"
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_ctrlfactortype',[this,CtrlFactorTypeName],[0,1]);"
			onDblClick="return showCodeList('pd_ctrlfactortype',[this,CtrlFactorTypeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_ctrlfactortype',[this,CtrlFactorTypeName],[0,1]);"><input
			class=codename name=CtrlFactorTypeName id=CtrlFactorTypeName
			readonly=true><font size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>Ҫ��ֵ</td>
		<td class=input normal><input class="common wid"
			name=CtrlFactorValue verify="�ۼ���Ҫ��ֵ|NOTNULL" id=CtrlFactorValue><font
			size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
			<td class=tittle normal>Ҫ�ص�λ</td>
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
		<td class=tittle normal>Ҫ��ֵ���㷽ʽ</td>
		<td class=input normal><Input class="codeno" name=CalMode
			id=CalMode readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_calmodetype',[this,CalModeName],[0,1]);"
			onDblClick="return showCodeList('pd_calmodetype',[this,CalModeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_calmodetype',[this,CalModeName],[0,1]);"><input
			class=codename name=CalModeName id=CalModeName readonly=true></td>
			<td class=tittle normal>Ĭ��ֵ</td>
		<td class=input normal><input class="common wid" onfocus="checkCalMode();"
			name=DefaultValue id=DefaultValue><font size=2 color='#ff0000'><b><br/>��Ҫ��ֵ�ļ��㷽ʽΪ1ʱ��Ĭ��ֵ��Ҫ��ֵһ�¡�</b></font></td>
		<td class=tittle normal>Ҫ��ֵ���㹫ʽ</td>
		<td class=input normal><input class="common wid" name=CalCode
			id=CalCode></td>
			
	</tr>
	<tr class=common>
	
		<!-- �ۼ���ά����Ϣ -->
		<!-- PD_LCalculatorNatureTime  ��Ȼʱ��ά�� -->
		<td class=tittle normal>��Ч����</td>
		<td class=input normal><input class="coolDatePicker"
			name="StartDate" verify="�ۼ�����Ч����|NOTNULL" readonly
			onClick="laydate({elem: '#StartDate'});" id="StartDate"><span
			class="icon"><a onClick="laydate({elem: '#StartDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>��Чֹ��</td>
		<td class=input normal><input class="coolDatePicker"
			name="EndDate" verify="�ۼ�����Чֹ��|NOTNULL" readonly
			onClick="laydate({elem: '#EndDate'});" id="EndDate"><span
			class="icon"><a onClick="laydate({elem: '#EndDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
	</tr>
	<tr class=common>
		<!-- PD_LCalculatorInsuranceTime ����ʱ��ά�� -->
		<td class=tittle normal>��Чʱ��</td>
		<td class=input normal><input class="common wid" name=ValidPeriod
			verify="�ۼ�����Чʱ��|NOTNULL" id=ValidPeriod><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>��Чʱ����λ</td>
		<td class=input normal><Input class="codeno" name=ValidPeriodUnit
			verify="�ۼ�����Чʱ����λ|NOTNULL&code:pd_validPeriodUnit" id=ValidPeriodUnit
			readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('insuperiodflag',[this,ValidPeriodUnitName],[0,1]);"
			onDblClick="return showCodeList('insuperiodflag',[this,ValidPeriodUnitName],[0,1]);"
			onKeyUp="return showCodeListKey('insuperiodflag',[this,ValidPeriodUnitName],[0,1]);"><input
			class=codename name=ValidPeriodUnitName id=ValidPeriodUnitName
			readonly=true><font size=1 color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
	</tr>
	<tr class=common>
	<!-- PD_LCalculatorOrder �ۼ������� -->
		<td class=tittle normal>�ۼӲ���</td>
		<td class=input normal><input class="common wid" name=CalOrder
			verify="�ۼ�������|NOTNULL" id=CalOrder><font size=1
			color='#ff0000'><b>&nbsp;&nbsp;*</b></font></td>
		<td class=tittle normal>�����ۼ�����ţ���ѡ���㷨</td>
		<td class=input normal><input class="common wid"
			name=CalculatorCodeAfter id=CalculatorCodeAfter><font size=1
			color='#ff0000'><b>&nbsp;(��ѡ)</b></font></td>
	</tr>
	<tr class=common>
		<td class=tittle normal>��������</td>
		<td class=input normal><Input class="codeno" name=FeeType id=FeeType
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('llfeetype',[this,FeeTypeName],[0,1]);"
			onDblClick="return showCodeList('llfeetype',[this,FeeTypeName],[0,1]);"
			onKeyUp="return showCodeListKey('llfeetype',[this,FeeTypeName],[0,1]);"><input
			class=codename name=FeeTypeName id=FeeTypeName readonly=true></td>
		<td class=tittle normal>�˵�����</td>
		<td class=input normal><Input class="codeno" name=FeeCode id=FeeCode
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('pd_llmedfeetype',[this,FeeCodeName],[0,1]);"
			onDblClick="return showCodeList('pd_llmedfeetype',[this,FeeCodeName],[0,1]);"
			onKeyUp="return showCodeListKey('pd_llmedfeetype',[this,FeeCodeName],[0,1]);"><input
			class=codename name=FeeCodeName id=FeeCodeName readonly=true><font size=1 color='#ff0000'><b><br/>�������β㼶���ϣ�����Ҫ�����˵���Ϣ</b></font></td>
	</tr>
</table>
<!-- PD_LCalculatorFeeCode �ۼ����˵���������ά�ȱ� -->
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
		<td class="titleImg">�˵�������Ϣ</td>
	</tr>
</table>
</div>-->
<div class=maxbox1>
<div id="divFeeCodeBuildInfo1">
<table class=common>
	<tr class=commmon>
		<td class=tittle normal>�Ƿ�۳��Ը�����</td>
		<td class=input normal><Input class="codeno"
			name=ifselfPayPercent id=ifselfPayPercent readonly=true
			style="background: url(../common/images/select--bg_03.png) no-repeat right center"
			onclick="return showCodeList('yesno',[this,ifselfPayPercentName],[0,1]);"
			onDblClick="return showCodeList('yesno',[this,ifselfPayPercentName],[0,1]);"
			onKeyUp="return showCodeListKry('yesno',[this,ifselfPayPercentName],[0,1]);"><input
			class=codename name=ifselfPayPercentName id=ifselfPayPercentName
			readonly=true></td>
		<td class=tittle normal>�Ը�����</td>
		<td class=input normal><input class="common wid"
			name=selfPayPercent id=selfPayPercent></td>
			<td class=tittle normal>�����</td>
		<td class=input normal><Input class="common wid" name=IfPayMoney
			id=IfPayMoney></td>
	</tr>
	<!--  <tr class=common>
			<td class=tittle normal>�ȴ���</td>
		<td class=input normal><input class="common wid" name=ObsPeriod
			id=ObsPeriod></td>
		<td class=tittle normal>�ȴ��ڵ�λ</td>
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
<!--�㷨��������ҳ--> <jsp:include page="CalCodeDefMain.jsp" /><input
	value="��  ��" type=button onclick="save()" class="cssButton"
	type="button"> <input value="��  ��" type=button id="buttionId"
	onclick="update()" class="cssButton" type="button"><input
	value="ɾ ��" type=button onclick="del()" class="cssButton" type="button">
	<!--<input
	value="�˵���Ϣ����" type=button onclick="button1()" class="cssButton" type="button">  -->
<input type=hidden name="operator" id=operator> <input
	type=hidden name=IsReadOnly id=IsReadOnly>  <input
	type=hidden name=SerialNo id=SerialNo><input
	type=hidden name=CalculatorCode id=CalculatorCode><input
	type=hidden name=FlagStr id=FlagStr></form>
	<!-- �������� -->
<div align=left id=savebuttonid>
<form action="./PDLCalculatorInputULSave.jsp" method=post name=fm1
	target="fraSubmit" ENCTYPE="multipart/form-data">
<table>
	<tr>
		<td><input type=file name="FileName">&nbsp;&nbsp;<INPUT
			VALUE="��������" TYPE="button" class=cssButton onclick="clickUpload();">
		</td>
	</tr>
</table>
</form>
</div>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
