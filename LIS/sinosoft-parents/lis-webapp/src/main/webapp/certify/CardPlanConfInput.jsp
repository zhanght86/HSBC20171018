<html>
<%
	//name :CertifyDescInput.jsp
	//function :�ƻ������ύ
	//Creator :mw
	//date :2008-12-29
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
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CardPlanConf.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CardPlanConfInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./CardPlanConfSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>�������ƻ���ѯ</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''">
<div class="maxbox1">
<Table class=common>
	<TR class=common>
		<td class="title5">��¼����</td>
		<td class="input5"><input class="wid" class="readonly" readonly
			name="managecom" id="managecom"></td>
	</TR>
	<tr class="common">
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" readonly
			name="ConOperator" id="ConOperator"></td>
		<td class="title5">��������</td>
		<td class="input5"><!--<input class="coolDatePicker" readonly
			dateFormat="short" name="MakeDate" verify="��������|NotNull">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��������|NotNull" dateFormat="short" name=ValidStartDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
</Table>
</div></Div>
<!--<input class=cssButton type=button value="�������ƻ���ѯ"
	onclick="queryClick()">-->
    
    <a href="javascript:void(0);" class="button" onClick="queryClick();">�������ƻ���ѯ</a>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardPlanQuery);"></td>
		<td class=titleImg>��ѯ����б�</td>
	</tr>
</table>
<Div id="divCardPlanQuery" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardPlanQueryGrid">
		</span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="turnPage.firstPage();"> <INPUT VALUE="��һҳ"
	class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button
	onclick="turnPage.nextPage();"> <INPUT VALUE="β  ҳ"
	class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>

</div>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardPlanQueryDetail);"></td>
		<td class=titleImg>��ϸ�б�</td>
	</tr>
</table>
<Div id="divCardPlanQueryDetail" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span
			id="spanCardPlanQueryDetailGrid"> </span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="turnPage2.firstPage();"> <INPUT VALUE="��һҳ"
	class=cssButton91 TYPE=button onclick="turnPage2.previousPage();">
<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button
	onclick="turnPage2.nextPage();"> <INPUT VALUE="β  ҳ"
	class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"></center>
</div>



<!--<input class=cssButton type=button value="��������" onclick="updateClick()">
<input class=cssButton type=button value="��  ӡ"
	onclick="CardPlanDetailPrint()">-->
    
    <a href="javascript:void(0);" class="button" onClick="updateClick();">��������</a>
    <a href="javascript:void(0);" class="button" onClick="CardPlanDetailPrint();">��    ӡ</a>




<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divrdPlanPk);"></td>
		<td class=titleImg>ע�⣺����֧��˾�ƻ�������Ϻ�ſ��Ի����ύ</td>
	</tr>
</table>
<Div id="divrdPlanPk" style="display: ''" class="maxbox1">
<table class=common>
	<tr>
		<TD class=title5>�ƻ�����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			verify="�ƻ�����|NOTNULL"
			CodeData="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true><font
			color=red>*</font></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</tr>
</table></Div>
<!--<input class=cssButton type=button value="�����ܼƻ���ѯ" onclick="queryPack()">-->
<a href="javascript:void(0);" class="button" onClick="queryPack();">�����ܼƻ���ѯ</a>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardPlanPack);"></td>
		<td class=titleImg>���ܽ��</td>
	</tr>
</table>
</div>
<Div id="divCardPlanPack" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardPlanPackGrid">
		</span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="turnPage3.firstPage();"> <INPUT VALUE="��һҳ"
	class=cssButton91 TYPE=button onclick="turnPage3.previousPage();">
<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button
	onclick="turnPage3.nextPage();"> <INPUT VALUE="β  ҳ"
	class=cssButton93 TYPE=button onclick="turnPage3.lastPage();"></center></div>

<!--<input class=cssButton type=button value="�����ύ" onclick="submitForm()">
<input class=cssButton type=button value="��  ӡ"
	onclick="CardPlanPackPrint()">-->
    <a href="javascript:void(0);" class="button" onClick="submitForm();">�����ύ</a>
    <a href="javascript:void(0);" class="button" onClick="CardPlanPackPrint();">��    ӡ</a>
     <input type="hidden"
	name="OperateType" value=""></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br />
<br />
<br />
<br />
<br />
</body>
</html>
