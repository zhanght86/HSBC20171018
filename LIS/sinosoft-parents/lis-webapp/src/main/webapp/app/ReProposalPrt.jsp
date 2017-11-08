<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
%>
<script>
var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="ReProposalPrt.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ReProposalPrtInit.jsp"%>
<title>重打个单</title>
</head>
<body onload="initForm();">
<form action="./ReProposalPrtSave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit"><!-- 保单信息部分 -->
<TABLE>
    <TR>
        <TD class=common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolContion);">
        </TD>
        <TD class = titleImg>
            请输入投保单查询条件：
        </TD>
    </TR>
</TABLE>	
<div class="maxbox1">
<Div id= "divLCPolContion" style= "display: ''">		
<table class=common align=center>
	<TR class=common>
		<TD class=title5>销售渠道</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno"  name=SaleChnl id="SaleChnl"
			verify="销售渠道|code:SaleChnl"
			onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);"
			onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class="codename" name="SaleChnlName" id="SaleChnlName"></TD>
		<TD class=title5>保单号码</TD>
		<TD class=input5><Input class="common wid" name=ContNo id="ContNo"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>管理机构</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=ManageCom id="ManageCom"
			 onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql);" ><input class="codename" name="ManageComName" id="ManageComName"></TD>
		<TD class= title5>印刷号码</TD>
		<TD class= input5> <Input class="common wid" name=PrtNo id="PrtNo"> </TD>
	</TR>
</table>
</div>
</div>
<input type='hidden' name='BranchGroup' id="BranchGroup">
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查询投保单</a>
<!-- <INPUT VALUE="查询投保单" class="cssButton" TYPE=button onclick="easyQueryClick();"> -->
<table> 
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor: hand;" OnClick="showPage(this,divLCPol1);"></td>
		<td class=titleImg>保单信息</td>
	</tr>
</table>
<Div id="divLCPol1" style="display: ''">
<table class=common>
	<TR class=common>
		<td text-align:  colSpan=1><span id="spanPolGrid"></span></td>
	</tr>
</table>
<INPUT VALUE="首  页" class="cssButton90" TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="上一页" class="cssButton91"
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="下一页"
	class="cssButton92" TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="尾  页" class="cssButton93" TYPE=button onclick="getLastPage();">
</div>
<TABLE>
    <TR>
        <TD class=common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolAdd);">
        </TD>
        <TD class = titleImg>
            请输入重打附属信息
        </TD>
    </TR>
</TABLE>
<div class="maxbox1">	
<Div id="divLCPolAdd" style="display: ''">
<table class=common align=center>
	<TR class=common>
		<TD class=title5>计 费</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"  type="text" class="codeno"  name=NeedPay id="NeedPay" value=""
			CodeData="0|^0|计费|^1|不计费"
			onClick="showCodeListEx('NeedPay',[this,NeedPayName],[0,1]);" ondblClick="showCodeListEx('NeedPay',[this,NeedPayName],[0,1]);"
			onkeyup="showCodeListKeyEx('NeedPay',[this,NeedPayName],[0,1]);"><input class="codename" name="NeedPayName" id="NeedPayName"></TD>

		<TD class=title5>重打原因</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"   type="text" class="codeno"  name=Reason id="Reason" value=""
			CodeData="0|^1|打印机故障|^2|操作失误|^3|数据错误|^4|遗失或毁损^5|其它"
			onClick="showCodeListEx('Reason',[this,ReasonName],[0,1]);" ondblClick="showCodeListEx('Reason',[this,ReasonName],[0,1]);"
			onkeyup="showCodeListKeyEx('Reason',[this,ReasonName],[0,1]);"><input class="codename" name="ReasonName" id="ReasonName"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>申请表编号</TD>
		<TD class=input5><Input class="common wid" name=BatchNo id="BatchNo" elementtype=nacessary></TD>
	</TR>
</table>	
</div>
</div>
<br>
<div>
	<a href="javascript:void(0)" class=button onclick="ApplyRePrint();">提交申请</a>
</div>
<br>
<!-- <INPUT VALUE="提 交 申 请" class="cssButton" TYPE=button onclick="ApplyRePrint();">  -->
<input type=hidden id="fmtransact" name="fmtransact">
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
</script>
