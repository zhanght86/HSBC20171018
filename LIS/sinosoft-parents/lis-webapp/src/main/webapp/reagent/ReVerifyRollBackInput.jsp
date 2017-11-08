
<html>
<%
	//程序名称：IndiDueFeeInput.jsp
	//程序功能：个人保费催收，实现数据从保费项表到应收个人表和应收总表的流转
	//创建日期：2002-07-24 
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	loggerDebug("ReVerifyRollBackInput","tGI.Operator=" + tGI.Operator);
	loggerDebug("ReVerifyRollBackInput","tGI.ManageCom" + tGI.ManageCom);
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var ComCode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="ReVerifyRollBack.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ReVerifyRollBackInit.jsp"%>
</head>

<body onload="initForm();">
<form name=fm id="fm" action="./ReVerifyRollBackSave.jsp" target=fraSubmit method=post>
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
		<TD class=title5>交费收据号</TD>
		<TD class=input5><Input class="common wid" name=GetNoticeNo id="GetNoticeNo"></TD>
		<TD class=title5>保单号码</TD>
		<TD class=input5><Input class="common wid" name=ContNo id="ContNo"></TD>
	</TR>
	
	<TR class=common>
		<TD class=title5>印刷号</TD>
		<TD class=input5><Input class="common wid" name=PrtNo id="PrtNo"></TD>
		<TD class=title5>代理人编码</TD>
		<TD class=input5><Input class="common wid" name=AgentCode id="AgentCode"></TD>
		
	</TR>
	
	<TR class=common>
		<TD class=title5>投保人编码</TD>
		<TD class=input5><Input class="common wid" name=AppntNo id="AppntNo"></TD>
		<TD class=title5>被保人编码</TD>
		<TD class=input5><Input class="common wid" name=InsuredNo id="InsuredNo"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
<!-- <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick()"> -->


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLCPol1);"></td>
		<td class=titleImg>保单信息</td>
	</tr>
</table>
<Div id="divLCPol1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanPolGrid"> </span></td>
	</tr>
</table>

<center>
	<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
	<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 
	<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="getNextPage();"> 
	<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</Div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLCPol2);"></td>
		<td class=titleImg>回退操作信息</td>
	</tr>
</table>
<div class="maxbox1">
<div  id= "divLCPol2" style= "display: ''"> 
<table class=common align=center>
	<TR class=common>
		<TD class=title5>回退类型</TD>
		<TD class=input5><Input class="common wid" name=ReaSonType id="ReaSonType"></TD>
		<TD class=title5>备注信息</TD>
		<TD class=input5><Input class="common wid" name=ReMark id="ReMark"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="submitForm();">核销回退</a>
	<!-- <INPUT VALUE="核销回退" TYPE=button class=cssButton	onclick="submitForm()">  -->
	<INPUT type="hidden" name="Operator" id="Operator" value=""> 

</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
