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
<SCRIPT src="ClaimPayNotice.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ClaimPayNoticeInit.jsp"%>
<title>理赔批单打印</title>
</head>

<body onLoad="initForm();">
<form method=post name=fm id="fm" target="fraSubmit">
<table class=common border=0 width=100%>
	<tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
		<td class=titleImg align=center>团单赔案查询条件</td>
	</tr>
</table>
<div id="divInvAssBuildInfo">
       <div class="maxbox1" >
<table class=common>
	<TR class=common>
		<TD class=title5>立案/赔案号:</TD>
		<TD class=input5><Input class="common wid" name=RgtNo></TD>
		<TD class=title5>管理机构</TD>
		<TD class=input5><Input class="codeno" name=ManageCom
        style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);"
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" readonly></TD>
	</TR>
	<TR>
		<TD class=title5>统计起期</TD>
		<TD class=input5>
            <input class=" multiDatePicker laydate-icon" dateFormat="short" name=StartDate  id=StartDate onClick="laydate({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
		<TD class=title5>统计止期</TD>
		<TD class=input5>
            <input class=" multiDatePicker laydate-icon" dateFormat="short" name=EndDate  id=EndDate onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
            
	</TR>
	<TR class=common>
		<TD class=title5>被保人客户号:</TD>
		<TD class=input5><Input class="common wid" name=InsuredNo></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</table>
</div></div>
<!--<INPUT VALUE=" 查 询 " class=cssButton TYPE=button  onclick="easyQueryClick()">-->
 <a href="javascript:void(0);" class="button"onclick="easyQueryClick()">查   询</a>
 <br><br>

<Div id="divLPGrpEdorMain1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanPolGrid"> </span></td>
	</tr>
</table>
<center><input VALUE="首  页" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="上一页" TYPE="button" onClick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="下一页" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="尾  页" TYPE="button" onClick="turnPage.lastPage();"
	class="cssButton93"></center>
</div>


<!--<td class=input align="center"><INPUT VALUE="理赔给付通知"
	class=cssButton TYPE=button onClick="PrintCaseReceiptClass()"></td>-->
     <a href="javascript:void(0);" class="button"onClick="PrintCaseReceiptClass()">理赔给付通知</a>
<br><br><br><br>


</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>

