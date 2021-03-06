<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//程序名称：LRBsnsTabInput.jsp
	//程序功能：
	//创建日期：2007-2-8
	//创建人  ：zhangbin
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="RIBsnsBillPrintInput.js"></SCRIPT>
<%@include file="RIBsnsBillPrintInit.jsp"%>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
</head>

<body onload="initElementtype();initForm();">
	<form action="" method=post name=fm target="fraSubmit">

		<div style="width: 200" style="display:'';">
			<table class="common">
				<tr class="common">
					<td class="common"><img src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divBillPrint);"></td>
					<td class="titleImg">查询条件</td>
				</tr>
			</table>
		</div>

		<Div id="divCessGetData" style="display:'';">

			<table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title5>起始日期</TD>
					<TD class=input5><Input name=StartDate class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"
						dateFormat='short' id="StartDate">		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>终止日期</TD>
					<TD class=input5><Input name=EndDate class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"
						dateFormat='short' id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5></TD>
					<TD class=input5></TD>
				</TR>

			</table>
			<INPUT class=cssButton VALUE="查   询" TYPE=button
				onClick="queryBillPrint();"><INPUT
				class=cssButton VALUE="重   置" TYPE=button onClick="ResetForm();">

		</Div>

		<div style="width: 200" style="display:'';">
			<table class="common">
				<tr class="common">
					<td class="common"><img src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divBillPrint);"></td>
					<td class="titleImg">待打印账单</td>
				</tr>
			</table>
		</div>
	
		<Div id="divBillPrint" style="display:'';">
			<table class="common">
				<tr class="common">
					<td style="text-align:left;" colSpan=1><span
						id="spanBillPrintListGrid"></span></td>
				</tr>
			</table>
		</Div>
		<input type="hidden" name="Billno"> <input type="hidden"
			name="Ricontno"> <input type="hidden" name="Batchno">
		<input type="hidden" name="Currency"> <INPUT type="hidden"
			class=cssButton VALUE="账单打印" TYPE=button onClick="">

		<div style="width: 200">
			<table class="common">
				<tr class="common">
					<td class="common"><img src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divBillPrint);"></td>
					<td class="titleImg">待打印账单</td>
				</tr>
			</table>
		</div>
		<Table class=common>
			<TR class=common>
				
				<td class=title id="RIComCode" style="display: ''">分保公司</td>
				<td class=input id="RICoName" style="display: ''"><Input
					class="codeno" name="RIComCode"
					ondblClick="showCodeList('riincompany',[this,RIComName],[0,1],null,null,null,1,250);"
					onkeyup="showCodeListKey('riincompany',[this,RIComName],[0,1],null,null,null,1,250);"><Input
					class=codename name='RIComName' verify="分保公司|NOTNULL" elementtype=nacessary></td>

		
				<td class="title5"><Div id="otherCode" style="display: ''">年度</Div></td>
				<td class="input5"><Div id="printName" style="display: ''">
						<Input class="code" name=CalYear verify="年度|NOTNULL&len=4&num"
							ondblclick="showCodeList('riprofityear',[this],[0])"
							onkeyup="showCodeListKey('riprofityear',[this],[0])"
							elementtype=nacessary>
					</Div></td>

				<TD class=title id="seCode" style="display: ''">季度</TD>
				<TD class=input id="seName" style="display: ''"><Input
					class="codeno" name="seaType"
					ondblClick="showCodeList('ribillseason',[this,seaName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('ribillseason',[this,seaName],[0,1],null,null,null,1);"
					readonly value=""><Input class=codename name='seaName'
					elementtype=nacessary verify="季度|NOTNULL" readonly="readonly" value=""></TD>
			</TR>

		</Table>
		<hr>
		<input name="BillType"  type="hidden"  value="<%=request.getParameter("BillType")%>">
		 <INPUT class=cssButton VALUE="账单打印" TYPE=button
			onClick="billPrint();"> <input type="hidden" name=OperateType
			value=""> <input type="hidden" name="Operator"
			value="<%=Operator%>">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
