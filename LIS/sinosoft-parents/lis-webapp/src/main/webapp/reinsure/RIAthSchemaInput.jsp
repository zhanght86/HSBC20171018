<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	//��ʽ���ƣ�RIAthSchemaInput.jsp
	//��ʽ���ܣ��������㷨����
	//�������ڣ�2011/6/16
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="RIAthSchema.js"></SCRIPT>
<%@include file="RIAthSchemaInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIAthSchemaSave.jsp" method=post name=fm
		target="fraSubmit">

		<%@include file="../common/jsp/OperateButton.jsp"%>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divArithmetic);"></td>
				<td class=titleImg>���㷨����</td>
			</tr>
		</table>
		<Div id="divArithmetic" style="display: ''">
			<table class=common>
				<tr class=common>
					<TD class=title5>���㷨����</TD>
					<TD class=input5><Input class=common name=ArithmeticDefID verify="���㷨����|NOTNULL" elementtype=nacessary>
					</TD>
					<TD class=title5>���㷨����</TD>
					<TD class=input5><Input class=common name=ArithmeticDefName verify="���㷨����|NOTNULL" elementtype=nacessary>
					</TD>
				</tr>
				<tr>
					<TD class=title5>���㷨����</TD>
					<TD class=input5><Input class=codeno name=ArithSubType
						readonly="readonly"
						ondblClick="showCodeList('riatithtype',[this,ArithTypeName],[0,1],null,null,null,[1]);"
						onkeyup="showCodeListKey('riatithtype',[this,ArithTypeName],[0,1],null,null,null,[1]);" verify="���㷨����|NOTNULL&code:riatithtype"><input
						class=codename name=ArithTypeName readonly="readonly" elementtype=nacessary></TD>
					<TD class=title5>�ֳ�����</TD>
					<TD class=input5><Input class="codeno" name="AccumulateDefNO"
						style="width: 37.74%" verify="�ֳ�����|CODE:riaccmucode" 
						ondblClick="showCodeList('riaccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('riaccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);"><Input
						class=codename name='AccumulateDefName' style="width: 55.7%"
						readonly="readonly"></TD>
				</tr>
				<tr>
					<TD class=title5>
						<Div id="divArithType1" style="display:none;">��������</Div>
					</TD>
					<TD class=input5>
						<Div id="divArithType2" style="display:none;">
							<Input class=codeno name='PreceptType'
								ondblClick="showCodeList('risolutiontype',[this,ArithContName],[0,1],null,null,null,[1]);"
								onkeyup="showCodeListKey('risolutiontype',[this,ArithContName],[0,1],null,null,null,[1]);"><input
								class=codename name='ArithContName' readonly="readonly">
						</Div>
					</TD>
				</tr>
			</table>
		</div>
		<br>

		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divCalItemType);"></td>
				<td class=titleImg>���㷨����Ѷ</td>
			</tr>
		</table>
		<Div id="divCalItemType" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span id="spanResultGrid"></span>
					</td>
				</tr>
			</table>
		</Div>
		<br> <br> <input class="cssButton" type="button"
			value="��ϸ���㷨����" onclick="nextStep();"> <input type=hidden
			id="OperateType" name="OperateType">

	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
