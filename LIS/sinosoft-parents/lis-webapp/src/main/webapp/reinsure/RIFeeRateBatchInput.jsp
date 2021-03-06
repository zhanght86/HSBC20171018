<%@include file="/i18n/language.jsp"%>

<html>
<%
	//name :LRFeeRateBatchInput.jsp
	//function :LRFeeRateBatchInput.jsp
	//Creator :
	//date :2008-06-04
%>
<%@page contentType="text/html;charset=GBK"%>
<!--用户校验类-->
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
				<td class=titleImg>费率表批次</td>
			</tr>
		</table>
		<Div id="divLRFeeRateBatch" style="display: ''">
			<table class=common>
				<tr class=common>
					<td><span id="spanFeeRateBatchGrid"></span></td>
				</tr>
			</table>
			<input class="cssButton" type="button" style="display: ''"
				value="录入新批次" onclick="inputFeeRateBatch()"> <input
				class="cssButton" type="button" value="关  闭" onclick="ClosePage()">
		</Div>
		<br>
		<hr>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLRCont1);"></td>
				<td class=titleImg>费率表批次信息</td>
			</tr>
		</table>
		<Div id="divLRCont1" style="display: ''">
			<Table class=common>
				<TR class=common>
					<TD class=title5>费率表编号</TD>
					<TD class=input5><Input class=common name="FeeTableNo" readOnly
						elementtype=nacessary verify="费率表编号|NOTNULL&len<20"></TD>
					<TD class=title5>费率表名称</TD>
					<TD class=input5><Input class="common" name="FeeTableName"
						readOnly elementtype=nacessary verify="费率表名称|NOTNULL&len<50">
					</TD>
					<TD class=title5>费率表批次编号</TD>
					<TD class=input5><Input id="feeBatchNo" class="common"
						name="BatchNo" elementtype=nacessary
						verify="费率表批次编号|NOTNULL&len<20"></TD>
				</TR>
				<TR class=common>
					<TD class=title5>生效日期</TD>
					<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#InureDate'});" name="InureDate"
						 id="InureDate">		<span class="icon"><a onClick="laydate({elem: '#InureDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>失效日期</TD>
					<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#LapseDate'});" name="LapseDate"
						 id="LapseDate">		<span class="icon"><a onClick="laydate({elem: '#LapseDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>保存到</TD>
					<TD class=input5><Input class="codeno" readOnly
						name="SaveDataName"
						ondblClick="showCodeList('rifeetablelocate',[this,SaveDataNameName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('rifeetablelocate',[this,SaveDataNameName],[0,1],null,null,null,1);"
						verify="保存到|NOTNULL"><Input class=codename
						name='SaveDataNameName' readonly="readonly" elementtype=nacessary></TD>
				</TR>
				<TR class=common>
					<TD class=title5>费率表状态</TD>
					<TD class=input5><Input class=codeno readonly="readonly" NAME="State"
						ondblClick="showCodeList('ristate',[this,stateName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ristate',[this,stateName],[0,1],null,null,null,1);"
						verify="费率表状态|NOTNULL"><input class=codename
						name=stateName readonly="readonly" elementtype=nacessary></TD>

					<TD class=title5></TD>
					<TD class=input5></TD>

					<TD class=title5></TD>
					<td class="input5"></td>
				</TR>
			</Table>
			<Input name=BatchNoAbove style="display:none;"> <input
				class="cssButton" type="button" value="保  存" onclick="submitForm()">
			<input class="cssButton" type="button" value="修  改"
				onclick="updateClick()"> <input class="cssButton"
				type="button" value="删  除" onclick="deleteClick()"> <input
				class="cssButton" type="hidden" name="OperateType">
	</form>
	<br>
	<hr>
	<form action="" name=fmImport target="fraSubmit" method=post
		ENCTYPE="multipart/form-data">
		<!--  a href="./LRFeeRateImport.xls"> 导入模板下载 </a -->
		<!--  input class="cssButton" type="button" value="导入模板下载" onclick= "document.execCommand('saveas',false,'./LRFeeRateImport.xls');"  -->
		<input class="cssButton" type="button" value="导入模板下载"
			onclick="return alink();">
		<Table class=common>

			<TR class=common>
				<TD class=title5>选择导入的文件：</TD>
				<TD><Input type="file" name=FileName class=common> <INPUT
					VALUE="导  入" class=cssButton TYPE=button
					onclick="FeeRateTableImp();"></TD>

			</TR>
		</Table>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
