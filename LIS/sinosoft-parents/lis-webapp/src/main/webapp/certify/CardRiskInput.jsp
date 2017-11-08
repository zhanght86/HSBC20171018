<html>
<%
	//name :CardRiskInput.jsp
	//function :定额单险种定义
	//Creator :mw
	//date :2009-05-13
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGlobalInput = (GlobalInput) session.getValue("GI");
	String Branch = tGlobalInput.ComCode;
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CardRiskInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CardRiskInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./CardRiskSave.jsp" method=post name=fm id=fm target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>查询条件</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''" class="maxbox1">
<Table class=common>
	<TR class=common>
		<td class="title5">单证编码</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
            onclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyName id=CertifyName readonly></td>
		<TD class=title5>险种编码</TD>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="RiskCode" id="RiskCode"
			ondblclick="return showCodeList('RiskCode', [this,RiskName],[0,1],null,null,null,1);"
            onclick="return showCodeList('RiskCode', [this,RiskName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('RiskCode', [this,RiskName],[0,1],null,null,null,1);"><input
			class=codename name=RiskName id=RiskName readonly></td>
	</TR>
	<TR class=common>
	   <TD class=title5>产品代码</TD>
	     <td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="PlanCode" id="PlanCode"
			ondblclick="return showCodeList('portfolio', [this,PlanCodeName],[0,1],null,null,null,1);"
            onclick="return showCodeList('portfolio', [this,PlanCodeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('portfolio', [this,PlanCodeName],[0,1],null,null,null,1);"><input
			class=codename name=PlanCodeName id=PlanCodeName readonly></td>
		<Input class=common type=hidden name=OperateType>
	</TR>
</Table></Div>

<input class=cssButton type=button value="查  询" onclick="queryClick()">
<input class=cssButton type=button value="清  空" onclick="initForm()">

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardRisk);"></td>
		<td class=titleImg>查询结果列表</td>
	</tr>
</table>

<Div id="divCardRisk" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardRiskGrid">
		</span></td>
	</tr>
</table>


</Div>

<input class=cssButton type=button value="保  存" onclick="submitForm()"><br>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
