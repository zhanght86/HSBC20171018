<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
%>

<script>
	var operator = "<%=tG.Operator%>";   //记录操作员
	var manageCom = "<%=tG.ManageCom%>"; //记录管理机构
	var comcode = "<%=tG.ComCode%>"; //记录登陆机构
</script>
<html>
<%
	//程序名称：NewPolFeeWithDrowInput.jsp
	//程序功能： 退还余额，即退还lcpol.levaingmoney
	//创建日期：2002-07-24 
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="NewPolFeeWithDrow.js"></SCRIPT>
<%@include file="NewPolFeeWithDrowInit.jsp"%>
</head>

<body onload="initForm();">
<form name=fm id="fm" action=./NewPolFeeWithDrowSave.jsp target=fraSubmit
	method=post>
<table class=common border=0 width=100%>
	<tr>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
		</td>
		<td class=titleImg align=center>请输入查询条件：</td>
	</tr>
</table>
<div class="maxbox1">
<div  id= "divFCDay" style= "display: ''"> 
<table class=common align=center>
	<TR class=common>		
		<TD class=title5>险种号码</TD>
		<TD class=input5><Input NAME=PolNo2 id="PolNo2" class="common wid"></TD>
		<TD class=title5>保单号码</TD>
		<TD class=input5><Input NAME=ContNo id="ContNo" class="common wid"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>险种编码</TD>
		<TD class=input5><Input NAME=RiskCode id="RiskCode" class="common wid"></TD>
		<TD class=title5>投保人姓名</TD>
		<TD class=input5><Input NAME=AppntName id="AppntName" class="common wid"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>银行代码</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  NAME=BankCode id="BankCode" class=code
			onclick="return showCodeList('FINAbank', [this]);" ondblclick="return showCodeList('FINAbank', [this]);"
			onkeyup="return showCodeListKey('FINAbank', [this]);"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
<!-- <INPUT VALUE=" 查 询 " TYPE=button class=cssButton onclick="easyQueryClick()"> -->

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divBank1);"></td>
		<td class=titleImg>保单信息</td>
	</tr>
</table>
<Div id="divBank1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanBankGrid"> </span></td>
	</tr>
</table>
<div id= "divPage" align=center style= "display: '' ">
  <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
  <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
  <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
  <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
</div>
</Div>
<hr class="line">

<Div id="divPayMoney" style="display: ''">
<table>
	<tr>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
		</td>
		<td class=titleImg>说明：修改退费金额可以部分退费</td>
	</tr>
</table>
<div class="maxbox1">
<div  id= "divFCDay1" style= "display: ''">
<table class=common align=center>
	<TR CLASS=common>
		<TD class=title5>险种号码</TD>
		<TD class=input5><Input class="common wid" name=PolNo id="PolNo"
			verify="险种号码|notnull"></TD>
		<TD class=title5>实退金额</TD>
		<TD class=input5><Input NAME=LeavingMoney id="LeavingMoney" class="common wid"
			verify="实退余额|notnull"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="submitForm();">退  费</a>
<!-- <INPUT VALUE=" 退 费 " class=cssButton TYPE=button onclick="submitForm()"> -->
</Div>

<Input type=hidden class=common name=ProPosalNo id="ProPosalNo" type=hidden>
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
