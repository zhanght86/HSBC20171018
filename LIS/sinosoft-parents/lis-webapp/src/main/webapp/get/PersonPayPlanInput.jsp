<html>
<%
	//�������ƣ�PersonPayPlanInput.jsp
	//�����ܣ�
	//�������ڣ�2002-07-24 08:38:43
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<!--<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>-->
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="PersonPayPlanInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="PersonPayPlanInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./PersonPayPlanSave.jsp" method=post name=fm id=fm target="fraSubmit"><!-- ��ʾ������PayPlan1����Ϣ -->
<table>
	<tr>
		<td class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divPayPlan1);"></td>
		<td class=titleImg>�߸��ƻ�������������</td>
	</tr>
</table>

<Div id="divPayPlan1" style="display: ''" class="maxbox">

<table class=common>
	<TR class=common>
		<TD class=title5>�������</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=wid class=code name=ManageCom value=""
			ondblclick="showCodeList('station',[this]);"
            onMouseDown="showCodeList('station',[this]);"
			onkeyup="return showCodeListKey('station',[this]);"></TD>
		<!--<TD class=title> ʱ�䷶Χ</TD>
        <td class="input" width="25%"><input class="coolDatePicker" dateFormat="short" id="timeStart" name="timeStart" verify="��ʼ����|NOTNULL&DATE" ></td>
        -->
		<TD class=title5>�߸�������</TD>
		<td class="input5"><!--<input class=wid class="coolDatePicker"
			dateFormat="short" id="timeEnd" name="timeEnd"
			verify="��ֹ����|NOTNULL&DATE">-->
            <Input  class="coolDatePicker" class="laydate-icon" onClick="laydate({elem: '#timeEnd'});" 	verify="��ֹ����|NOTNULL&DATE" dateFormat="short" name=timeEnd 	id="timeEnd"><span class="icon"><a onClick="laydate({elem: 	'#timeEnd'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
            </td>
	</TR>
	<TR class=common>
		<TD class=title5>��������</TD>
		<TD class=input5><Input class=wid name=ContNo></TD>
		<TD class=title5>�������ֺ���</TD>
		<TD class=input5><Input class=wid name=PolNo></TD>
	</TR>
	<TR class=common>
		<TD class=title5>�����˿ͻ�����</TD>
		<TD class=input5><Input class=wid name=InsuredNo></TD>
		<!--  <TD class=title>Ͷ���˿ͻ�����</TD>
		<TD class=input><Input class=common name=AppntNo></TD>-->
        <TD class=title5></TD>
        <TD class=input5></TD>
	</TR>
	
</table>
</div>
<!--<Input class=cssButton type=Button value="���ɴ߸�" onclick="submitForm()">-->
<a href="javascript:void(0);" class="button" onClick="submitForm();">���ɴ߸�</a>
<Div id="divBTquery" style="display :none">
			<!--<INPUT class=cssButton TYPE=button VALUE="��ѯ" OnClick="easyQueryClick()">-->
            <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
            </Div>
<!--δ���ʻ�������ȡ���б� -->
<table>
	<tr>
		
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,pallo);"></td>
		<td class=titleImg>δ��������ʻ�Ӧ�����һ��</td>
	</tr>
</table>
<Div  id= "pallo" style= "display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanGetGrid"> </span></td>
	</tr>
</table>
<center>
	<INPUT VALUE="��  ҳ" TYPE=button class="cssButton90" onclick="getFirstPage();"> 
	<INPUT VALUE="��һҳ" TYPE=button class="cssButton91" onclick="getPreviousPage();"> 
	<INPUT VALUE="��һҳ" TYPE=button class="cssButton92" onclick="getNextPage();"> 
	<INPUT VALUE="β  ҳ" TYPE=button class="cssButton93" onclick="getLastPage();"></center>

</div>

<!--<INPUT class=cssButton TYPE=button VALUE="��ѯ�Ѿ����ʻ�������ȡ��" OnClick="queryinsuracc()">-->
<a href="javascript:void(0);" class="button" onClick="queryinsuracc();">��ѯ�Ѿ����ʻ�������ȡ��</a>
</Div>
<!--�Ѿ����ʻ�������ȡ���б� -->
<table>
	<tr>
		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,bushii);"></td>
		<td class=titleImg>�Ѿ���������ʻ�Ӧ�����һ��</td>
	</tr>
</table>
<Div id="bushii" style="display:">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanGetGridInsurAcc"> </span></td>
	</tr>
</table>
<center>
	<INPUT VALUE="��  ҳ" TYPE=button class="cssButton90" onclick="getFirstPage();"> 
	<INPUT VALUE="��һҳ" TYPE=button class="cssButton91" onclick="getPreviousPage();"> 
	<INPUT VALUE="��һҳ" TYPE=button class="cssButton92" onclick="getNextPage();"> 
	<INPUT VALUE="β  ҳ" TYPE=button class="cssButton93" onclick="getLastPage();"></center>

</div>

<table class=common>
	<tr class=common>
		<!-- 
    	<td class = input width = 26%>
    		������&nbsp<Input class= readonly readonly name=getCount >&nbsp����¼��
    	</td>
   -->
		<input type=hidden id="SerialNo" name="SerialNo">
</table>

<!-- ��ʾ������LJSGet1����Ϣ --></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>

