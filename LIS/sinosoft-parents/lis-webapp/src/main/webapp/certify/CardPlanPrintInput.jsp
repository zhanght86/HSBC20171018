<html>
<%
	//name :CardPlanCollectInput.jsp
	//function :ӡˢ�ƻ�����
	//Creator :mw
	//date :2009-02-16
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
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CardPlanPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CardPlanPrintInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./CardPlanPrintSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>��ѯ����</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''">
<div class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title5>��֤����</TD>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyCode id=CertifyCode
			ondblclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('certifycode', [this,CertifyName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyName id=CertifyName readonly=true></td>
		<TD class=title5>�ƻ�����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			verify="�ƻ�����|NOTNULL"
			CodeData="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" readonly
			name="PrintOperator" id="PrintOperator"></td>
		<td class="title5">��������</td>
		<td class="input5"><!--<input class="coolDatePicker" readonly
			dateFormat="short" name="PrintDate" verify="��������|NotNull">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#PrintDate'});" verify="��������|NotNull" dateFormat="short" name=PrintDate id="PrintDate"><span class="icon"><a onClick="laydate({elem: '#PrintDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
	<tr class="common">
		<td class="title5">��¼����</td>
		<td class="input5"><input class="wid" class="readonly" readonly
			name="managecom" id="managecom"></td>
	</TR>
</Table>
</Div>
</Div>
<input class=cssButton type=button value="��ӡˢ��ѯ" onclick="queryClick()">
<input class=cssButton type=button value="��ӡˢ��ѯ" onclick="queryClick2()">
<input class=cssButton type=button value="��  ��" onclick="clearData()">
<!--<a href="javascript:void(0);" class="button" onClick="queryClick();">��ӡˢ��ѯ</a>
<a href="javascript:void(0);" class="button" onClick="queryClick2();">��ӡˢ��ѯ</a>
<a href="javascript:void(0);" class="button" onClick="clearData();">��    ��</a>-->

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardPrintQuery);"></td>
		<td class=titleImg>��ѯ����б�</td>
	</tr>
</table>
<Div id="divCardPrintQuery" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardPrintQueryGrid">
		</span></td>
	</tr>
</table>
<!--<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>-->
</div>

<input class=cssButton type=button name=buttonP value="ӡ  ˢ" onclick="submitForm()">
<!--<a href="javascript:void(0);" name=buttonP class="button" onClick="submitForm();">ӡ    ˢ</a>-->
<input type="hidden" name="OperateType" value=""></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
