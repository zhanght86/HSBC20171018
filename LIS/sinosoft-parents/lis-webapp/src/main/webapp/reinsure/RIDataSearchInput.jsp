<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//�������ƣ�LRSearchInput.jsp
	//�����ܣ�
	//�������ڣ�2008-2-7
	//������  ��zhangbin
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="RIDataSearchInput.js"></SCRIPT>
<%@include file="RIDataSearchInit.jsp"%>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
</head>

<body onload="initElementtype();initForm();">
	<form action="" method=post name=fm target="fraSubmit">
		<div style="width: 200">
			<table class="common">
				<tr class="common">
					<td class="common"><img src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divCessGetData);">
					</td>
					<td class="titleImg">�ٱ����ݲ�ѯ</td>
					<td class="common"></td>
					<td class="common"></td>	
					<td class="common"></td>
					<td class="common"></td>	
				</tr>
			</table>
		</div>
		<Div id="divCessGetData" style="display: ''">
			<Table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title5>��ʼ����</TD>
					<TD class=input5><Input name=StartDate class="coolDatePicker" 
						dateFormat='short' id="StartDate">		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>��ֹ����</TD>
					<TD class=input5><Input name=EndDate class="coolDatePicker" 
						dateFormat='short' id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>ҵ������</TD>
					<TD class=input5><input class=codeno readonly="readonly" name="EventType"
						ondblclick="return showCodeList('rieventtype', [this,EventTypeName],[0,1],null,null,null,1);"
						onkeyup="return showCodeListKey('rieventtype', [this,EventTypeName],[0,1],null,null,null,1);"><input
						class=codename name=EventTypeName readonly="readonly"></TD>
				</TR>
				<TR>
					
					<TD class=title5>�ٱ���ͬ</TD>
					<TD class=input5><Input class="codeno" name="ContNo"
						ondblClick="showCodeList('ricontno',[this,ContName],[0,1],null,null,null,1,260);"
						onkeyup="showCodeListKey('ricontno',[this,ContName],[0,1],null,null,null,1,260);"><Input
						class=codename name='ContName'></TD>
					<TD class=title5>��ͬ����</TD>
					<TD class=input5><Input class="codeno" readOnly
						name="RIContType"
						ondblClick="showCodeList('riconttype',[this,RIContTypeName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('riconttype',[this,RIContTypeName],[0,1],null,null,null,1); "><Input
						class=codename name='RIContTypeName' readonly="readonly">
					</TD>	
					<td class="title5">�����˱���</td>
					<td class="input5"><Input class="common" name="InsuredNo">
					</td>
						
				</TR>
				<TR>
					<td class="title5">�ֳ����α��</td>
					<td class="input5"><Input class="codeno" name="AccumulateDefNO"
						ondblClick="showCodeList('riaccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('riaccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);"><Input
						class=codename name='AccumulateDefName'></td>
					<td class="title5">ҵ�Ʊ��</td>
					<td class="input5"><input class="codeno" name="BFFlag"
						readonly="readonly"
						ondblClick="showCodeList('ribfflag',[this,BFFlagName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ribfflag',[this,BFFlagName],[0,1],null,null,null,1);"
						verify="ҵ�Ʊ��|NOTNULL"><Input class=codename
						name='BFFlagName' elementtype=nacessary readonly="readonly"></td>
					<TD class=title5>����������</TD>
					<TD class=input5><Input class="common" name="InsuredName">
					</TD>
					</TR>
			</Table>
		</Div>

		<br> <INPUT class=cssButton VALUE="��  ѯ" TYPE=button
			onClick="queryClick();"> <INPUT class=cssButton VALUE="��  ��"
			TYPE=button onClick="resetPage();"> <br>
		<br>
		<div id='divTable3' style="display: ''">
			<table class="common">
				<tr class="common">
					<td style="text-align:left;" colSpan=1><span id="spanRIDataGrid"></span></td>
				</tr>
			</table>
		</div>
		<br>
		<div id='divUCoDetail' style="display:none;">
			<table class="common">
				<tr class="common">
					<td style="text-align:left;" colSpan=1><span
						id="spanRIDataUCoDetailGrid"></span></td>
				</tr>
			</table>
		</div>
		<div id='divCessDetail' style="display:none;">
			<table class="common">
				<tr class="common">
					<td style="text-align:left;" colSpan=1><span id="spanRIDataDetailGrid"></span></td>
				</tr>
			</table>
		</div>

		<INPUT class=cssButton VALUE="��ϸ��������" TYPE=button
			onClick="DownLoadExcel();" style="display: ''"> <input
			type="hidden" name=OperateType value=""> <input type="hidden"
			name="Operator" value="<%=Operator%>">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
