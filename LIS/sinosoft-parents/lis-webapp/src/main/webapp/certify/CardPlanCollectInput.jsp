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
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CardPlanCollect.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CardPlanCollectInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./CardPlanCollectSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>���ܼƻ���ѯ</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''">
<div class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title5>�ƻ�����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			verify="�ƻ�����|NOTNULL"
			CodeData="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true><font
			color=red>*</font></TD>
		<td class="title5">��¼����</td>
		<td class="input5"><input class="wid" class="readonly" readonly
			name="managecom" id="managecom"></td>
	</TR>
	<tr class="common">
		<td class="title5">��ʼ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="StartDate">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
		<td class="title5">��ֹ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="EndDate">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
	<tr class="common">
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" readonly name="InputMan" id="InputMan"></td>
		<td class="title5">��������</td>
		<td class="input5"><!--<input class="coolDatePicker" readonly
			dateFormat="short" name="InputDate">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
</Table>
</Div></Div>
<input class=cssButton type=button value="�����ܼƻ���ѯ"
	onclick="queryClickD()"> <input class=cssButton type=button
	value="�ѻ��ܼƻ���ѯ" onclick="queryClickY()">
   <!-- <a href="javascript:void(0);" class="button" onClick="queryClickD();">�����ܼƻ���ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="queryClickY();">�ѻ��ܼƻ���ѯ</a>-->
    </Div>

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
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>

<Div id="divCardPlanQuery2" style="display: none">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardPlanQueryGrid2">
		</span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>

<input class=cssButton type=button name=HZbutton value="��  ��"
	onclick="submitForm()"> <input class=cssButton type=button
	name=DYbutton value="��  ӡ" onclick="easyPrint()">
    <!--<a href="javascript:void(0);" name=HZbutton class="button" onClick="submitForm();">��    ��</a>
    <a href="javascript:void(0);" name=DYbutton class="button" onClick="easyPrint();">��    ӡ</a>-->
     <input
	type="hidden" name="OperateType" value=""></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
