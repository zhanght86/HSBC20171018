<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIAccRDInput.jsp
	//�����ܣ��ֳ����ζ���
	//�������ڣ�2011/6/16
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="RIAccRD.js"></SCRIPT>
<%@include file="RIAccRDInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIAccRDSave.jsp" method=post name=fm target="fraSubmit">
		<%@include file="../common/jsp/OperateButton.jsp"%>
		<br>
		<table>
			<tr>
				<td class="titleImg">�ֳ�������Ϣ����</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">�ֳ����α���</td>
				<td class="input5"><input class="common" name="AccumulateDefNO"
					value='' verify="�ֳ����α���|NOTNULL&len<20" elementtype=nacessary>
				</td>
				<td class="title5">�ֳ���������</td>
				<td class="input5"><input class="common"
					name="AccumulateDefName" value='' elementtype=nacessary
					verify="�ֳ���������|NOTNULL&len<100"></td>
			</tr>
			<tr class="common">
				<td class="title5">���ݲɼ�����</td>
				<td class="input5"><input class="codeno" name="ArithmeticDefID"
					ondblclick="return showCodeList('riarithmetic',[this,ArithmeticDefName],[0,1]);"
					onkeyup="return showCodeListKey('riarithmetic',[this,ArithmeticDefName],[0,1]);"
					verify="���ݲɼ�����|NOTNULL"><input
					class="codename" name="ArithmeticDefName" readonly
					elementtype=nacessary></td>
				<td class="title5">�ֳ���������</td>
				<td class="input5"><input class="codeno" name="RIAccType"
					ondblclick="return showCodeList('riacctype',[this,RIAccTypeName],[0,1]);"
					onkeyup="return showCodeListKey('riacctype',[this,RIAccTypeName],[0,1]);"
					verify="�ֳ���������|NOTNULL&CODE:riacctype"><input
					class="codename" name="RIAccTypeName" readonly
					elementtype=nacessary></td>
					</tr>
			<tr class="common">
				<td class="title5">�ֳ�����״̬</td>
				<td class="input5"><input class="codeno" name="RIAccState"
					ondblclick="return showCodeList('ristate',[this,RIAccStateName],[0,1]);"
					onkeyup="return showCodeListKey('ristate',[this,RIAccStateName],[0,1]);"
					verify="�ֳ�����״̬|NOTNULL&CODE:ristate"><input
					class="codename" name="RIAccStateName" elementtype=nacessary
					readonly></td>
				<td class="title5">���ݲɼ�����</td>
				<td class="input5"><input class="codeno" name="DIntv"
					ondblClick="showCodeList('ridatacycle',[this,DIntvName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('ridatacycle',[this,DIntvName],[0,1],null,null,null,1);"
					verify="���ݲɼ�����|NOTNULL&CODE:ridatacycle"><Input
					class=codename name='DIntvName' elementtype=nacessary readonly="readonly"></td>
				<!-- start  jintao 8.18
				<td class="title5"><div id="divName1" style="display:none;">�ֱ�����</div>
				</td>
				<td class="input5">
					<div id="divCode1" style="display:none;">
						<input class="codeno" name="RIRiskFeature"
							ondblclick="return showCodeList('ririskfeature',[this,RIRiskFeatureName],[0,1],null,null,null,1);"
							onkeyup="return showCodeListKey('ririskfeature',[this,RIRiskFeatureName],[0,1],null,null,null,1);"
							nextcasing=><input class="codename"
							name="RIRiskFeatureName">
					</div></td>
					end  jintao 8.18 -->
			<tr class="common">
				<td class="title5">ҵ�Ʊ��</td>
				<td class="input5"><input class="codeno" name="BFFlag"
					verify="ҵ�Ʊ��|code:ribfflag"
					ondblClick="showCodeList('ribfflag',[this,BFFlagName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('ribfflag',[this,BFFlagName],[0,1],null,null,null,1);"><Input
					class=codename name='BFFlagName'></td>

				<td class="title5">�ۼƷ�ʽ</td>
				<td class="input5"><input class="codeno" name="StandbyFlag"
					ondblClick="showCodeList('riaccflag',[this,StandbyFlagName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('riaccflag',[this,StandbyFlagName],[0,1],null,null,null,1);"
					verify="�ۼƷ�ʽ|NOTNULL&code:riaccflag"><Input class=codename
					name='StandbyFlagName' elementtype=nacessary readonly="readonly"></td>
				<td class="title" Style="display: none">�ŷN</td>
				<td class="input" Style="display: none"><input class="codeno"
					name="moneyKind" readonly
					ondblclick="return showCodeList('currency',[this,moneyName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('currency',[this,moneyName],[0,1],null,null,null,1);"><input
					class="codename" name="moneyName" elementtype=nacessary readonly="readonly">
				</td>
			</tr>
			</tr>
		</table>
		<br>
		<div style="display: none">
			<table>
				<tr>
					<td class="titleImg">������Ʒ����</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span
						id="spanRIAccRiskDutyGrid"> </span></td>
				</tr>
			</table>
		</div>
		<input class="cssButton" type="button" value="��һ��"
			onclick="nextStep();">
		<!-- start  jintao 8.18
		<br> <input value="�ֱ����Զ���" onclick="button100()" name="caccspec"
			class="cssButton" type="button" style="display:none;"> <input
			value="�������ö���" onclick="button99()" name="cacctype" class="cssButton"
			type="button" style="display:none;"> <br>
		end jintao 8.18-->
		<input type="hidden" name="OperateType">
		<input type="hidden" name="CalOrder" value="02">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
