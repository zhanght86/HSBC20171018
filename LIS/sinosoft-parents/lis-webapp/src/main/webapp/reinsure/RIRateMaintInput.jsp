<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIRateMaintInput.jsp
	//�����ܣ�����ά��
	//�������ڣ�2011/6/17
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="RIRateMaint.js"></SCRIPT>
<%@include file="RIRateMaintInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
<form action="./RIRateMaintSave.jsp" method=post name=fm
	target="fraSubmit">

<table>
	<tr>
		<td class="titleImg">����ά��</td>
	</tr>
</table>
<table class="common">
	<tr class="common">
		<td class="title5">����</td>
		<td class="input5"><input class="codeno" name="RiskCode" 
			ondblclick="return showCodeList('rilmrisk',[this,RiskName],[0,1]);"
			onkeyup="return showCodeListKey('rilmrisk',[this,RiskName],[0,1]);"
			nextcasing=""
			verify="����|NOTNULL&code:rilmrisk"><input
			class="codename" name="RiskName" elementtype=nacessary
			readonly="readonly"></td>
		<td class="title5">��������</td>
		<td class="input5"><input class="codeno" name="InterestType" 
			ondblclick="return showCodeList('riinteresttype', [this,InterestTypeName],[0,1]);"
			onkeyup="return showCodeListKey('riinteresttype', [this,InterestTypeName],[0,1]);"
			nextcasing=''
			verify="��������|NOTNULL&CODE:riinteresttype"><input
			class="codename" name="InterestTypeName" elementtype=nacessary
			readonly="readonly"></td>
	</tr>
	<tr class="common">
		<td class="title5">��Ч����</td>
		<td class="input5"><input class="coolDatePicker"
			onClick="laydate({elem: '#StartDate'});" dateFormat="short"
			name="StartDate" id="StartDate"> <span class="icon"><a
			onClick="laydate({elem: '#StartDate'});"> <img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<td class=title5>ʧЧ����</td>
		<td class=input5><input class="coolDatePicker"
			onClick="laydate({elem: '#EndDate'});" dateFormat="short"
			name="EndDate" id="EndDate"> <span class="icon"><a
			onClick="laydate({elem: '#EndDate'});"> <img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			</tr>
	<tr class="common">
		<td class="title5">����ֵ</td>
		<td class="input5"><input class="common" name="InterestRate"
			verify="����ֵ|NOTNULL&NUM&LEN<17" 
			elementtype=nacessary></td>
	</tr>

</table>




<input value="��  ��"
	onclick="button103()" class="cssButton" type="button"> <br>
<br>
<table>
	<tr>
		<td class="titleImg">���ʲ�ѯ</td>
	</tr>
</table>
<table class="common">
	<tr class="common">
	<td class="title5">����</td>
		<td class="input5"><input class="codeno" name="RiskCodeQuery"
			ondblclick="return showCodeList('rilmrisk',[this,RiskNameQuery],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('rilmrisk',[this,RiskNameQuery],[0,1],null,null,null,1);"
			nextcasing=""
			verify="����|CODE:rilmrisk"><input
			class="codename" name="RiskNameQuery" readonly="readonly"></td>
			<td class="title5">��������</td>
			<td class="input5"><input class="codeno" name="InterestTypeQuery"
			ondblclick="return showCodeList('riinteresttype', [this,InterestNameQuery],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('riinteresttype', [this,InterestNameQuery],[0,1],null,null,null,1);"
			nextcasing=''
			verify="��������|CODE:riinteresttype"><input
			class="codename" name="InterestNameQuery"></td>
<!--		<td class="title5"></td>-->
<!--		<td class="input5" ><input class="common"   name="InterestRate"-->
<!--			verify="����ֵ|NOTNULL&NUM&LEN<17" -->
<!--			elementtype=nacessary</td>-->
	</tr>
	<tr class="common">
	
			<td class="title5">��Ч����</td>
			<td class="input5"><input class="coolDatePicker"
			onClick="laydate({elem: '#StartDateQuery'});" dateFormat="short"
			name="StartDateQuery" id="StartDateQuery"> <span class="icon"><a onClick="laydate({elem: '#StartDateQuery'});"> <img src="../common/laydate/skins/default/icon.png" /></a>
			</span>
		</TD>
	
	<td class="title5"></td>
	<td class="input5"></td>
	<td class="title5"></td>
	<td class="input5"></td>
<!--	</tr>-->
	
	
</table>





<input value="��  ѯ"
	onclick="button104()" class="cssButton" type="button"> <input
	value="ɾ  ��" onclick="button105( )"
	class="cssButton" type="button">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divCertifyType);">
		</td>
		<td class=titleImg>��ʷ���ʹ켣��</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span id="spanMul10Grid">
		</span></td>
	</tr>
</table>
<input type="hidden" name="OperateType" /></form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
