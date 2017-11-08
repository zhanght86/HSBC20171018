<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RIBsnsBillUWInput.jsp
	//程序功能：账单审核
	//创建日期：2011/6/14
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="RIBsnsBillUW.js"></SCRIPT>
<%@include file="RIBsnsBillUWInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIBsnsBillUWSave.jsp" method=post name=fm
		target="fraSubmit">
		<table>
			<tr>
				<td class="titleImg">查询条件</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
								<td class=title5>账单</td>
				<td class=input5><Input class="codeno" name="BillNo" 
					ondblClick="showCodeList('ribillno',[this,BillName],[0,1],null,null,null,1,260);"
					onkeyup="showCodeListKey('ribillno',[this,BillName],[0,1],null,null,null,1,260);"><Input
					class=codename name='BillName'>
				</td>
				<td class=title5>再保合同</td>
				<td class=input5><Input class="codeno" name="ContNo" 
					ondblClick="showCodeList('ricontno',[this,ContName],[0,1],null,null,null,1,260);"
					onkeyup="showCodeListKey('ricontno',[this,ContName],[0,1],null,null,null,1,260);"><Input
					class=codename name='ContName'></td>
			</tr>
			<tr class="common">
				<td class=title5>再保公司</td>
				<td class=input5><Input class="codeno" name="RIcomCode" 
					ondblClick="showCodeList('riincompany',[this,RIcomName],[0,1],null,null,null,1,250);"
					onkeyup="showCodeListKey('riincompany',[this,RIcomName],[0,1],null,null,null,1,250);"><Input
					class=codename name='RIcomName'></td>
			<TD  class= title5 >幣種</TD>
		        <TD  class= input5 > 
		        	<Input class="codeno" name="Currency"  
		        	ondblClick="showCodeList('ribillcurrency',[this,CurrencyName],[0,1],null,fm.BillNo.value,'billno',1,250);"
				      onkeyup="showCodeListKe('ribillcurrency',[this,CurrencyName],[0,1],null,fm.BillNo.value,'billno',1,250);" readonly="readonly"><Input 
				      class= codename name="CurrencyName" readonly="readonly">
		        </TD>
			</tr>
			<tr class="common">
				<td class="title5">起始日期</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"
					dateFormat="short" name="StartDate" id="StartDate">
		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">
		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5">终止日期</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"
					dateFormat="short" name="EndDate" id="EndDate">
		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">
		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			</tr>
		</table>
		<input value="查询账单" onclick="queryBill( )" class="cssButton"
			type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">查询结果</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul10Grid">
				</span></td>
			</tr>
		</table>
		<br>
		<div id="divFeeItem1" style="display:none;">
			<table>
				<tr>
					<td class="titleImg">费用项</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span id="spanMul11Grid">
					</span></td>
				</tr>
			</table>
		</div>
		<div id="divFeeItem2" style="display:none;">
			<table>
				<tr>
					<td class="titleImg">费用项</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span id="spanMul12Grid">
					</span></td>
				</tr>
			</table>
		</div>
		<div id="divInfoItem" style="display:none;">
			<table>
				<tr>
					<td class="titleImg">信息项</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span id="spanMul13Grid">
					</span></td>
				</tr>
			</table>
		</div>
	    <br>
		<table class=common>
			<TR class=common>
				<TD class=title5>审核结论</TD>
				<TD class=input5><input class=codeno name="RIUWReport"
					verify="审核结论|NOTNULL&code:ribsnsuw"
					ondblclick="return showCodeList('ribsnsuw', [this,RIUWReportName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('ribsnsuw', [this,RIUWReportName],[0,1],null,null,null,1);"><input
					class=codename name=RIUWReportName readonly="readonly"
					elementtype=nacessary></TD>
				<TD class=title5></TD>
				<TD class=input5></TD>
				<TD class=title5></TD>
				<TD class=input5></TD>
			</TR>
		</table>
		<br> <input value="结论保存" onclick="button102( )" class="cssButton"
			type="button">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
