<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
%>

<script>
	var operator = "<%=tG.Operator%>";   //��¼����Ա
	var manageCom = "<%=tG.ManageCom%>"; //��¼�������
	var comcode = "<%=tG.ComCode%>"; //��¼��½����
</script>
<html>
<%
	//�������ƣ�NewPolFeeWithDrowInput.jsp
	//�����ܣ� �˻������˻�lcpol.levaingmoney
	//�������ڣ�2002-07-24 
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
		<td class=titleImg align=center>�������ѯ������</td>
	</tr>
</table>
<div class="maxbox1">
<div  id= "divFCDay" style= "display: ''"> 
<table class=common align=center>
	<TR class=common>		
		<TD class=title5>���ֺ���</TD>
		<TD class=input5><Input NAME=PolNo2 id="PolNo2" class="common wid"></TD>
		<TD class=title5>��������</TD>
		<TD class=input5><Input NAME=ContNo id="ContNo" class="common wid"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>���ֱ���</TD>
		<TD class=input5><Input NAME=RiskCode id="RiskCode" class="common wid"></TD>
		<TD class=title5>Ͷ��������</TD>
		<TD class=input5><Input NAME=AppntName id="AppntName" class="common wid"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>���д���</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  NAME=BankCode id="BankCode" class=code
			onclick="return showCodeList('FINAbank', [this]);" ondblclick="return showCodeList('FINAbank', [this]);"
			onkeyup="return showCodeListKey('FINAbank', [this]);"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
<!-- <INPUT VALUE=" �� ѯ " TYPE=button class=cssButton onclick="easyQueryClick()"> -->

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divBank1);"></td>
		<td class=titleImg>������Ϣ</td>
	</tr>
</table>
<Div id="divBank1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanBankGrid"> </span></td>
	</tr>
</table>
<div id= "divPage" align=center style= "display: '' ">
  <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
  <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
  <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
  <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
</div>
</Div>
<hr class="line">

<Div id="divPayMoney" style="display: ''">
<table>
	<tr>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
		</td>
		<td class=titleImg>˵�����޸��˷ѽ����Բ����˷�</td>
	</tr>
</table>
<div class="maxbox1">
<div  id= "divFCDay1" style= "display: ''">
<table class=common align=center>
	<TR CLASS=common>
		<TD class=title5>���ֺ���</TD>
		<TD class=input5><Input class="common wid" name=PolNo id="PolNo"
			verify="���ֺ���|notnull"></TD>
		<TD class=title5>ʵ�˽��</TD>
		<TD class=input5><Input NAME=LeavingMoney id="LeavingMoney" class="common wid"
			verify="ʵ�����|notnull"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="submitForm();">��  ��</a>
<!-- <INPUT VALUE=" �� �� " class=cssButton TYPE=button onclick="submitForm()"> -->
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
