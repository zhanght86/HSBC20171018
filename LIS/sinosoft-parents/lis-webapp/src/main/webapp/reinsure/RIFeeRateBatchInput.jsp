<%@include file="/i18n/language.jsp"%>

<html>
<%
	//name :LRFeeRateBatchInput.jsp
	//function :LRFeeRateBatchInput.jsp
	//Creator :
	//date :2008-06-04
%>
<%@page contentType="text/html;charset=GBK"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="./RIFeeRateBatchInput.js"></SCRIPT>
<%@include file="./RIFeeRateBatchInit.jsp"%>
</head>
<body onload="initElementtype();initForm();">
	<form action="" method=post name=fm target="fraSubmit">
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLRFeeRateBatch);"></td>
				<td class=titleImg>���ʱ�����</td>
			</tr>
		</table>
		<Div id="divLRFeeRateBatch" style="display: ''">
			<table class=common>
				<tr class=common>
					<td><span id="spanFeeRateBatchGrid"></span></td>
				</tr>
			</table>
			<input class="cssButton" type="button" style="display: ''"
				value="¼��������" onclick="inputFeeRateBatch()"> <input
				class="cssButton" type="button" value="��  ��" onclick="ClosePage()">
		</Div>
		<br>
		<hr>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLRCont1);"></td>
				<td class=titleImg>���ʱ�������Ϣ</td>
			</tr>
		</table>
		<Div id="divLRCont1" style="display: ''">
			<Table class=common>
				<TR class=common>
					<TD class=title5>���ʱ���</TD>
					<TD class=input5><Input class=common name="FeeTableNo" readOnly
						elementtype=nacessary verify="���ʱ���|NOTNULL&len<20"></TD>
					<TD class=title5>���ʱ�����</TD>
					<TD class=input5><Input class="common" name="FeeTableName"
						readOnly elementtype=nacessary verify="���ʱ�����|NOTNULL&len<50">
					</TD>
					<TD class=title5>���ʱ����α��</TD>
					<TD class=input5><Input id="feeBatchNo" class="common"
						name="BatchNo" elementtype=nacessary
						verify="���ʱ����α��|NOTNULL&len<20"></TD>
				</TR>
				<TR class=common>
					<TD class=title5>��Ч����</TD>
					<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#InureDate'});" name="InureDate"
						 id="InureDate">		<span class="icon"><a onClick="laydate({elem: '#InureDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>ʧЧ����</TD>
					<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#LapseDate'});" name="LapseDate"
						 id="LapseDate">		<span class="icon"><a onClick="laydate({elem: '#LapseDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>���浽</TD>
					<TD class=input5><Input class="codeno" readOnly
						name="SaveDataName"
						ondblClick="showCodeList('rifeetablelocate',[this,SaveDataNameName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('rifeetablelocate',[this,SaveDataNameName],[0,1],null,null,null,1);"
						verify="���浽|NOTNULL"><Input class=codename
						name='SaveDataNameName' readonly="readonly" elementtype=nacessary></TD>
				</TR>
				<TR class=common>
					<TD class=title5>���ʱ�״̬</TD>
					<TD class=input5><Input class=codeno readonly="readonly" NAME="State"
						ondblClick="showCodeList('ristate',[this,stateName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ristate',[this,stateName],[0,1],null,null,null,1);"
						verify="���ʱ�״̬|NOTNULL"><input class=codename
						name=stateName readonly="readonly" elementtype=nacessary></TD>

					<TD class=title5></TD>
					<TD class=input5></TD>

					<TD class=title5></TD>
					<td class="input5"></td>
				</TR>
			</Table>
			<Input name=BatchNoAbove style="display:none;"> <input
				class="cssButton" type="button" value="��  ��" onclick="submitForm()">
			<input class="cssButton" type="button" value="��  ��"
				onclick="updateClick()"> <input class="cssButton"
				type="button" value="ɾ  ��" onclick="deleteClick()"> <input
				class="cssButton" type="hidden" name="OperateType">
	</form>
	<br>
	<hr>
	<form action="" name=fmImport target="fraSubmit" method=post
		ENCTYPE="multipart/form-data">
		<!--  a href="./LRFeeRateImport.xls"> ����ģ������ </a -->
		<!--  input class="cssButton" type="button" value="����ģ������" onclick= "document.execCommand('saveas',false,'./LRFeeRateImport.xls');"  -->
		<input class="cssButton" type="button" value="����ģ������"
			onclick="return alink();">
		<Table class=common>

			<TR class=common>
				<TD class=title5>ѡ������ļ���</TD>
				<TD><Input type="file" name=FileName class=common> <INPUT
					VALUE="��  ��" class=cssButton TYPE=button
					onclick="FeeRateTableImp();"></TD>

			</TR>
		</Table>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
