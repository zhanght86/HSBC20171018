<html>
<%
	//name :CardPlanReturnInput.jsp
	//function :��֤�ƻ�����
	//Creator :mw
	//date :2009-02-10
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
<SCRIPT src="CardPlanReturn.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CardPlanReturnInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./CardPlanReturnSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>�����ƻ���ѯ</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''">
<div class="maxbox1">
<Table class=common>
	<TR class=common>
		<TD class=title5>��֤����</TD>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('certifycode', [this,CertifyName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyName id=CertifyName readonly=true></td>
		<TD class=title5>�ƻ�����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			CodeData="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true></TD>
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
		<td class="input5"><input class="wid" class="common" readonly
			name="RetOperator" id="RetOperator"></td>
		<td class="title5">��������</td>
		<td class="input5"><!--<input class="coolDatePicker" readonly
			dateFormat="short" name="MakeDate" verify="��������|NotNull">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��������|NotNull" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
	<TR class=common>
		<td class="title5">��¼����</td>
		<td class="input5"><input class="wid" class="readonly" readonly
			name="managecom" id="managecom"></td>
	</TR>
</Table></Div></Div>

<!--<input class=cssButton type=button name=button1 value="�¼��������ƻ���ѯ"
	onclick="queryClick('D')">--> <input style="display:none" class=cssButton type=button
	name=button2 value="�ϼ��������ƻ���ѯ" onclick="queryClick('Y')">
    
    <a href="javascript:void(0);" name=button1 class="button" onClick="queryClick('D')">�¼��������ƻ���ѯ</a>
    <a href="javascript:void(0);" name=button2 class="button" onClick="queryClick('Y')">�ϼ��������ƻ���ѯ</a>
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
<!--<center><INPUT VALUE="��  ҳ" class=cssButton TYPE=button
	onclick="turnPage.firstPage();"> <INPUT VALUE="��һҳ"
	class=cssButton TYPE=button onclick="turnPage.previousPage();">
<INPUT VALUE="��һҳ" class=cssButton TYPE=button
	onclick="turnPage.nextPage();"> <INPUT VALUE="β  ҳ"
	class=cssButton TYPE=button onclick="turnPage.lastPage();"></center>-->
</div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardPlanDetail);"></td>
		<td class=titleImg>��ϸ��Ϣ</td>
	</tr>
</table>
<Div id="divCardPlanDetail" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardPlanDetailGrid">
		</span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="turnPage2.firstPage();"> <INPUT VALUE="��һҳ"
	class=cssButton91 TYPE=button onclick="turnPage2.previousPage();">
<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button
	onclick="turnPage2.nextPage();"> <INPUT VALUE="β  ҳ"
	class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"></center>
</div>

<!--<input class=cssButton type=button name=PFbutton value="��  ��"
	onclick="submitForm()">-->
    
    <a href="javascript:void(0);" class="button" onClick="submitForm();">��    ��</a>
     <input type="hidden" name="OperateType"
	value=""><input type="hidden" name="PlanState" value="">
    </form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br />
<br />
<br /><br /><br />
</body>
</html>
