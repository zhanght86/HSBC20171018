<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ִ�н����ѯ</title>

<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<!--<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">-->
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<script src="../common/javascript/MultiCom.js"></script>
<script type="text/javascript" src="./RuleResult.js"></script>
<%@include file="./RuleResultInit.jsp"%>
<script type="text/javascript">
	
</script>
<style>
input.cssButton92,input.cssButton90,input.cssButton91,input.cssButton93{padding:4px 12px 2px 15px;CURSOR: hand;}
a.button{border:1;padding:5px 12px;CURSOR: hand;}
</style>
</head>
<body onload="initForm();initElementtype();">
<form action="./ThPutStaSave.jsp" method=post name=fm id=fm target="fraSubmit">
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,RuleBasicInfor);"></TD>
		<TD class=titleImg>������Ϣ¼��</TD>
	</TR>
</Table>
<div id="RuleBasicInfor" style="display: ''" class="maxbox1">
<Table class="common">
	<TR class="common">
		<TD class=title5>����ṹ��</TD>
		<TD class=input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom
			ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
            onclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);" /><input
			class=codename name=ManageComName id=ManageComName onchange="return Clean();" /></TD>
		<TD class=title5>ҵ��ģ�飺</TD>
		<TD class=input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Business id=Business
			ondblclick="return showCodeList('ibrmsbusiness',[this,BusinessName],[0,1]);"
            onclick="return showCodeList('ibrmsbusiness',[this,BusinessName],[0,1]);"
			onkeyup="return showCodeListKey('ibrmsbusiness',[this,BusinessName],[0,1]);" /><input
			class=codename name=BusinessName id=BusinessName onchange="return Clean();" /></TD>


	</TR>
	<TR class="common">
		<TD class=title5>��ʼ���ڣ�</TD>
		<TD class=input5><!--<input class="multiDatePicker" dateFormat="short"
			name=RecordStartDate verify="��Ч����|NOTNULL&Date"> </input>-->
           <!-- <Input class="coolDatePicker" onClick="laydate({elem: '#RecordStartDate'});" verify="��Ч����|NOTNULL&Date" dateFormat="short" name=RecordStartDate id="RecordStartDate"><span class="icon"><a onClick="laydate({elem: '#RecordStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#RecordStartDate'});" verify="��Ч����|NOTNULL&Date" dateFormat="short" name=RecordStartDate id="RecordStartDate"><span class="icon"><a onClick="laydate({elem: '#RecordStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
		<TD class=title5>��ֹ���ڣ�</TD>
		<TD class=input5><!--<input class="multiDatePicker" dateFormat="short"
			name=RecordEndDate verify="ʧЧ����|NOTNULL&Date"> </input>-->
             <Input class="coolDatePicker" onClick="laydate({elem: '#RecordEndDate'});" verify="ʧЧ����|NOTNULL&Date" dateFormat="short" name=RecordEndDate id="RecordEndDate"><span class="icon"><a onClick="laydate({elem: '#RecordEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
	</TR>

</Table>
</div>
<!--<Div id=divCmdButton style="display: ''">--><!--<input class=cssButton
	type="button" value='��Ҫ��ѯ' onclick="queryMain()" />-->
    <a href="javascript:void(0);" class="button" onClick="queryMain();">��Ҫ��ѯ</a>
<!--</Div>
<p>-->
<Div id="divResultMain" style="display: ''">
<table>
<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
	<td class=titleImg>�������</td>
</table>
<Div  id= "divPayPlan1" style= "display: ''"">
<table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanMainGrid"></span>
		</td>
		<TD class=input style="display: none"></TD>
	</tr>

</table></Div>
<!--<INPUT VALUE="��  ҳ" TYPE=button class="cssButton"
	onclick="turnPage.firstPage()"> <INPUT VALUE="��һҳ" TYPE=button
	class="cssButton" onclick="turnPage.previousPage()"> <INPUT
	VALUE="��һҳ" TYPE=button class="cssButton" onclick="turnPage.nextPage()">
<INPUT VALUE="β  ҳ" TYPE=button class="cssButton"
	onclick="turnPage.lastPage()"> <br>-->

<!--<INPUT VALUE="ϸ���ѯ" TYPE=button class="cssButton"
	onclick="queryDetails()">
<INPUT VALUE="����EXCEL" TYPE=button class="cssButton"
	onclick="analyse_data_pol('Main')">-->
    <a href="javascript:void(0);" class="button" onClick="queryDetails();">ϸ���ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="analyse_data_pol('Main')">����EXCEL</a>
</Div>
<p>
<Div id="DivRusultDetail" style="display: ''">
<table>
<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
	<td class=titleImg>���ϸ���</td>
</table>
<Div  id= "divPayPlan1" style= "display: ''">
<table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanDetailGrid"></span>
		</td>
		<TD class=input style="display: none"></TD>
	</tr>

</table>
<!--<center>
<INPUT VALUE="��  ҳ" TYPE=button class="cssButton90"
	onclick="turnPage2.firstPage()"> <INPUT VALUE="��һҳ" TYPE=button
	class="cssButton91" onclick="turnPage2.previousPage()"> <INPUT
	VALUE="��һҳ" TYPE=button class="cssButton92" onclick="turnPage2.nextPage()">
<INPUT VALUE="β  ҳ" TYPE=button class="cssButton93"
	onclick="turnPage2.lastPage()"> </center>-->
     <center>
        <INPUT VALUE="��  ҳ" TYPE=button  class="cssButton90" onclick="turnPage.firstPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton91" onclick="turnPage.previousPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton92" onclick="turnPage.nextPage()">
        <INPUT VALUE="β  ҳ" TYPE=button  class="cssButton93" onclick="turnPage.lastPage()"></center> </Div>


</Div>
<!--<INPUT VALUE="����EXCEL" TYPE=button class="cssButton"
	onclick="analyse_data_pol('Details')">-->
    <a href="javascript:void(0);" class="button" onClick="analyse_data_pol('Details')">����EXCEL</a>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
