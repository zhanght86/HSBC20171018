<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="Claim.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ClaimInit.jsp"%>
<title>����������ӡ</title>
</head>

<body onLoad="initForm();">
<form method=post name=fm id="fm" target="fraSubmit">
<table class=common border=0 width=100%>
	<tr>
     <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
		<td class=titleImg align=center>�ŵ��ⰸ��ѯ����</td>
	</tr>
</table>
<div id="divInvAssBuildInfo">
       <div class="maxbox1" >
<table class=common>
	<TR class=common>
		<TD class=title5>������:</TD>
		<TD class=input5><Input class="common wid" name=RgtNo></TD>
		<TD class=title5>�������</TD>
		<TD class=input5><Input class="common wid" name=ManageCom
         style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this]);"
			ondblclick="return showCodeList('station',[this]);"
			onkeyup="return showCodeListKey('station',[this]);"></TD>
	</TR>
	<TR>
		<TD class=title5>ͳ������</TD>
		<TD class=input5>
            <input class=" multiDatePicker laydate-icon" dateFormat="short" name=StartDate  id=StartDate onClick="laydate

({elem:'#StartDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
		<TD class=title5>ͳ��ֹ��</TD>
		<TD class=input5>
             <input class=" multiDatePicker laydate-icon" dateFormat="short" name=EndDate  id=EndDate onClick="laydate

({elem:'#EndDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</TR>
</table>
</div>
</div>
<!--<INPUT VALUE=" �� ѯ " class=cssButton TYPE=button onclick="easyQueryClick()">-->
<a href="javascript:void(0);" class="button"onclick="easyQueryClick()">��   ѯ</a>
<br><br>
<Div id="divLPGrpEdorMain1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanPolGrid"> </span></td>
	</tr>
</table>
<!--<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton"> <input
	VALUE="��һҳ" TYPE="button" onClick="turnPage.previousPage();"
	class="cssButton"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton"> <input
	VALUE="β  ҳ" TYPE="button" onClick="turnPage.lastPage();"
	class="cssButton"></center>-->
</div>
<!--

<tr class=common>
	<td class=input align="center"><INPUT VALUE=" �������� "
		class=cssButton TYPE=button onClick="PrintClaimBatch()"></td>
	<td class=input align="center"><INPUT VALUE=" ֧���嵥 "
		class=cssButton TYPE=button onClick="PrintClaimPayBill()"></td>
	<td class=input align="center"><INPUT VALUE=" ����嵥 "
		class=cssButton TYPE=button onClick="PrintClaimGetBill()"></td>
	<td class=input align="center"><INPUT VALUE="�������֪ͨ"
		class=cssButton TYPE=button onClick="PrintClaimPayTR()"></td>
	<td class=input align="center"><INPUT 
		class=cssButton TYPE=hidden name=OperateFlag></td>
</tr>-->
 <a href="javascript:void(0);" class="button"onClick="PrintClaimBatch()">��������</a>
  <a href="javascript:void(0);" class="button"onClick="PrintClaimPayBill()">֧���嵥</a>
   <a href="javascript:void(0);" class="button"onClick="PrintClaimGetBill()">����嵥</a>
    <a href="javascript:void(0);" class="button"onClick="PrintClaimPayTR()">�������֪ͨ</a>
 <INPUT class=cssButton TYPE=hidden name=OperateFlag>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>

