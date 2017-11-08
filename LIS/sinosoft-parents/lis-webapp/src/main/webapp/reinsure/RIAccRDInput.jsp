<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RIAccRDInput.jsp
	//程序功能：分出责任定义
	//创建日期：2011/6/16
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
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
				<td class="titleImg">分出责任信息定义</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">分出责任编码</td>
				<td class="input5"><input class="common" name="AccumulateDefNO"
					value='' verify="分出责任编码|NOTNULL&len<20" elementtype=nacessary>
				</td>
				<td class="title5">分出责任名称</td>
				<td class="input5"><input class="common"
					name="AccumulateDefName" value='' elementtype=nacessary
					verify="分出责任名称|NOTNULL&len<100"></td>
			</tr>
			<tr class="common">
				<td class="title5">数据采集方案</td>
				<td class="input5"><input class="codeno" name="ArithmeticDefID"
					ondblclick="return showCodeList('riarithmetic',[this,ArithmeticDefName],[0,1]);"
					onkeyup="return showCodeListKey('riarithmetic',[this,ArithmeticDefName],[0,1]);"
					verify="数据采集方案|NOTNULL"><input
					class="codename" name="ArithmeticDefName" readonly
					elementtype=nacessary></td>
				<td class="title5">分出责任类型</td>
				<td class="input5"><input class="codeno" name="RIAccType"
					ondblclick="return showCodeList('riacctype',[this,RIAccTypeName],[0,1]);"
					onkeyup="return showCodeListKey('riacctype',[this,RIAccTypeName],[0,1]);"
					verify="分出责任类型|NOTNULL&CODE:riacctype"><input
					class="codename" name="RIAccTypeName" readonly
					elementtype=nacessary></td>
					</tr>
			<tr class="common">
				<td class="title5">分出责任状态</td>
				<td class="input5"><input class="codeno" name="RIAccState"
					ondblclick="return showCodeList('ristate',[this,RIAccStateName],[0,1]);"
					onkeyup="return showCodeListKey('ristate',[this,RIAccStateName],[0,1]);"
					verify="分出责任状态|NOTNULL&CODE:ristate"><input
					class="codename" name="RIAccStateName" elementtype=nacessary
					readonly></td>
				<td class="title5">数据采集周期</td>
				<td class="input5"><input class="codeno" name="DIntv"
					ondblClick="showCodeList('ridatacycle',[this,DIntvName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('ridatacycle',[this,DIntvName],[0,1],null,null,null,1);"
					verify="数据采集周期|NOTNULL&CODE:ridatacycle"><Input
					class=codename name='DIntvName' elementtype=nacessary readonly="readonly"></td>
				<!-- start  jintao 8.18
				<td class="title5"><div id="divName1" style="display:none;">分保特性</div>
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
				<td class="title5">业财标记</td>
				<td class="input5"><input class="codeno" name="BFFlag"
					verify="业财标记|code:ribfflag"
					ondblClick="showCodeList('ribfflag',[this,BFFlagName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('ribfflag',[this,BFFlagName],[0,1],null,null,null,1);"><Input
					class=codename name='BFFlagName'></td>

				<td class="title5">累计方式</td>
				<td class="input5"><input class="codeno" name="StandbyFlag"
					ondblClick="showCodeList('riaccflag',[this,StandbyFlagName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('riaccflag',[this,StandbyFlagName],[0,1],null,null,null,1);"
					verify="累计方式|NOTNULL&code:riaccflag"><Input class=codename
					name='StandbyFlagName' elementtype=nacessary readonly="readonly"></td>
				<td class="title" Style="display: none">幣種</td>
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
					<td class="titleImg">关联产品责任</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span
						id="spanRIAccRiskDutyGrid"> </span></td>
				</tr>
			</table>
		</div>
		<input class="cssButton" type="button" value="下一步"
			onclick="nextStep();">
		<!-- start  jintao 8.18
		<br> <input value="分保特性定义" onclick="button100()" name="caccspec"
			class="cssButton" type="button" style="display:none;"> <input
			value="共保费用定义" onclick="button99()" name="cacctype" class="cssButton"
			type="button" style="display:none;"> <br>
		end jintao 8.18-->
		<input type="hidden" name="OperateType">
		<input type="hidden" name="CalOrder" value="02">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
