
<%
	//程序名称：LLClaimUserBillInput.jsp
	//程序功能：个险理赔核赔人统计清单
	//创建日期：2009-04-15
	//创建人  ：mw
	//更新记录：
%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
%>
<title></title>
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
<script src="./LLClaimUserBill.js"></script>
<SCRIPT src="LLClaimPubFun.js"></SCRIPT>
<%@include file="LLClaimUserBillInit.jsp"%>
</head>
<body onLoad="initForm();">
<form action="./LLClaimUserBillSave.jsp" name=fm  id=fm target=fraSubmit
	method=post>
<table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,DivStatisticCondition);"></TD>
		<TD class=titleImg>统计条件</TD>
	</TR>
</table>
<Div id="DivStatisticCondition" style="display: ''">
<div class="maxbox1" >
<Table class=common>
	<TR class=common>
		<TD class=title5>统计机构</TD>
		<TD class=input5><Input class=codeno name="MngCom"
			verify="统计机构|NOTNULL"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,MngComName],[0,1]);"
			ondblclick="return showCodeList('station',[this,MngComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,MngComName],[0,1]);"><input
			class=codename name="MngComName" readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</table>
</Div>
</Div>
<br>
 <a href="javascript:void(0);" class="button"onclick="showLLClaimPrepayBill()">理赔核赔人统计清单</a>

<!--<table>
	<TR>
		<TD><Input class=cssButton VALUE="理赔核赔人统计清单" TYPE=button
			onclick="showLLClaimPrepayBill()"></TD>
	</TR>
</table>-->

</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
