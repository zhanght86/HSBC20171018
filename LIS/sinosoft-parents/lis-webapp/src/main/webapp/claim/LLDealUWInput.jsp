<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
%>
<title>二核结论</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="LLClaimPubFun.js"></SCRIPT>
<script src="LLDealUW.js"></script>
<%@include file="LLDealUWInit.jsp"%>
</head>

<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divUnfinishedCont);"></TD>
		<TD class=titleImg>发起二核但未核保完成的案件列表</TD>
	</TR>
</Table>
<Div id="divUnfinishedCont" style="display: ''">
<Table class=common>
	<TR class=common>
		<TD text-align: left colSpan=1><span
			id="spanLLUnfinishedContGrid"></span></TD>
	</TR>
</Table>
</Div>

<br>
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCont);"></TD>
		<TD class=titleImg>已经二次核保完成的案件列表</TD>
	</TR>
</Table>



<Div id="divCont" style="display: ''">
<Table class=common>
	<TR class=common>
		<TD text-align: left colSpan=1><span id="spanLLContGrid"></span></TD>
	</TR>
</Table>
</Div><br>
<div class="maxbox1">
<table class=common>
	<TR class=common>
		<TD class=title5>立案号</TD>
		<TD class=input5><Input class="wid" class=common name=RptNo id=RptNo></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>

	</TR>
</table>
</div>
<!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();">-->
<a href="javascript:void(0);" class="button" onClick="queryGrid();">查    询</a>
<Input type="hidden" name="Operator"> <span id="spanCode"
	style="display: none; position:absolute; slategray"></span></Form><br><br><br><br>
</body>
</html>
