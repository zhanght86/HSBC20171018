<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//程序名称：LRSearchInput.jsp
	//程序功能：
	//创建日期：2007-2-7
	//创建人  ：zhangbin
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="RISearchInput.js"></SCRIPT>
<%@include file="RISearchInit.jsp"%>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
</head>

<body onload="initElementtype();initForm();">
	<form action="" method=post name=fm target="fraSubmit">
		<div style="width: 200">
			<table class="common">
				<tr class="common">
					<td class="common"><img src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divCessGetData);"></td>
					<td class="titleImg">再保结果查询</td>
				</tr>
			</table>
		</div>
		<br>
		<Div id="divCessGetData" style="display: ''">
			<table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title5>起始日期</TD>
					<TD class=input5><Input name=StartDate class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"
						dateFormat="short" id="StartDate">		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>终止日期</TD>
					<TD class=input5><Input name=EndDate class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"
						dateFormat="short" id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>查询类型</TD>
					<TD class=input5><input class=codeno readonly="readonly" name="EventType"
						ondblclick="return showCodeList('risearchtype', [this,EventTypeName],[0,1],null,null,null,1);"
						onkeyup="return showCodeListKey('risearchtype', [this,EventTypeName],[0,1],null,null,null,1);"
						verify="查询类型|NOTNULL&code:risearchtype"><input
						class=codename name="EventTypeName" readonly="readonly"
						elementtype=nacessary></TD>
				</TR>
				<TR>
					<td class="title5">
						<Div id="divTitle1" style="display:none;">团体保单号</Div>
						<Div id="divTitle2" style="display: ''">保单号</Div>
					</td>
					<td class="input5">
						<Div id="divInput1" style="display:none;">
							<Input class=common name=GrpContNo>
						</Div>
						<Div id="divInput2" style="display: ''">
							<Input class=common name=ContNo>
						</Div>
					</td>
					<TD class=title5>被保人号码</TD>
					<TD class=input5><Input class=common name=InsuredNo></TD>
					<TD class=title5>被保人姓名</TD>
					<TD class=input5><Input class=common name=InsuredName>
					</TD>
				</TR>
				<TR>
					<TD class=title5>再保公司</TD>
					<TD class=input5><Input class="codeno" name="RIcomCode"
						ondblClick="showCodeList('riincompany',[this,RIcomName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('riincompany',[this,RIcomName],[0,1],null,null,null,1);"><Input
						class=codename name="RIcomName"></TD>
					<TD class=title5>分出责任</TD>
					<TD class=input5><Input class="codeno" name="AccumulateDefNO"
						verify="分出责任|code:riaccmucode"
						ondblClick="showCodeList('riaccmucode',[this,AccumulateDefNOName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('riaccmucode',[this,AccumulateDefNOName],[0,1],null,null,null,1);"><Input
						class=codename name='AccumulateDefNOName'></TD>
					<td class="title5">再保合同</td>
					<td class="input5"><input class="codeno" name="RIContNo"
						verify="再保合同|code:riaccmucode"
						ondblclick="return showCodeList('ricontno',[this,RIContName],[0,1]);"
						onkeyup="return showCodeListKey('ricontno',[this,RIContName],[0,1]);"><input
						class="codename" name="RIContName"></td>
				</TR>
				<tr>
					<TD class=title5>
						<Div id="divTitle3" style="display:none;">保单类型</Div>
					</TD>
					<TD class=input5>
						<Div id="divTitle4" style="display:none;">
							<input class=codeno readonly="readonly" name="TempType"
								ondblclick="return showCodeList('ritemptype', [this,TempTypeName],[0,1],null,null,null,1);"
								onkeyup="return showCodeListKey('ritemptype', [this,TempTypeName],[0,1],null,null,null,1);"><input
								class=codename name=TempTypeName readonly="readonly">
						</Div>
					</TD>
				</tr>
			</table>
			<br> <INPUT class=cssButton VALUE="查询" TYPE=button
				onClick="queryClick();"> <INPUT class=cssButton VALUE="重置"
				TYPE=button onClick="resetPage();"> <br> <br>
			<div id='divBusynessGrid' style="display: ''">
				<table class="common">
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanBusynessGrid"></span></td>
					</tr>
				</table>
			</div>

			<div id='divEdorGrid' style='display: none'>
				<table class="common">
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanEdorGrid"></span></td>
					</tr>
				</table>
			</div>

			<div id='divClaimGrid' style='display: none'>
				<table class="common">
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanClaimGrid"></span></td>
					</tr>
				</table>
			</div>

			<div id='divFeeGrid' style="display:none;">
				<table class="common">
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanFeeGrid"></span></td>
					</tr>
				</table>
			</div>

			<INPUT class=cssButton VALUE="数据下载" TYPE=button
				onClick="exportExcel();" style="display: ''">
				
		</Div>
		<input type="hidden" name=OperateType value=""> <input
			type="hidden" name="Operator" value="<%=Operator%>"> <input
			type="hidden" name=ContType value="">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
