<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//�������ƣ�LRSearchInput.jsp
	//�����ܣ�
	//�������ڣ�2007-2-7
	//������  ��zhangbin
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="RICalInput.js"></SCRIPT>
<%@include file="./RiCalInit.jsp"%>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
</head>
<%
	String currentdate = PubFun.getCurrentDate();
%>
<script type="text/javascript">
	var currentDate="<%=currentdate%>";
</script>
<body onload="initElementtype();initForm();">
	<form action="RiCalSave.jsp" method=post name=fm target="fraSubmit">
		<div style="width: 200">
			<table class="common">
				<tr class="common">
					<td class="common"><img src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divCessGetData);"></td>
					<td class="titleImg">�ٱ����ݴ���</td>
				</tr>
			</table>
		</div>
		<br>
		<Div id="divCessGetData" style="display: ''">
			<table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title5>��ʼ����</TD>
					<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"
						dateFormat="short" name=StartDate value='<%=CurrentDate%>'
						 id="StartDate">
		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">
		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class=title5>��ֹ����</TD>
					<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"
						dateFormat="short" name=EndDate value='<%=CurrentDate%>'
						 id="EndDate">
		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">
		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					</tr>
					<TR class=common>
					<TD class=title5>��������</TD>
					<TD class=input5><input class=codeno readOnly
						name="ProcessType"
						ondblclick="return showCodeList('riprocesstype', [this,ProcessTypeName],[0,1],null,null,null,1);"
						onkeyup="return showCodeListKey('riprocesstype', [this,ProcessTypeName],[0,1],null,null,null,1);"
						verify="��������|NOTNULL"><input class=codename
						name=ProcessTypeName readonly="readonly"></TD>
				</TR>
			</table>
			<br> <INPUT class=cssButton VALUE="���ݴ���" TYPE=Button
				onclick="SubmitForm();"> &nbsp;&nbsp;
			<Div id="divList" style="display: ''">
				<table>
					<tr>
						<td class=common><IMG src="../common/images/butExpand.gif"
							style="cursor: hand;" OnClick="showPage(this,divTaskList);"></td>
						<td class=titleImg>�����б�</td>
					</tr>
				</table>
				<Div id="divTaskList" style="display: ''">
					<table class="common">
						<tr class="common">
							<td style="text-align:left;" colSpan=1><span id="spanTaskListGrid"></span></td>
						</tr>
					</table>
				</Div>
			</Div>
			<input type="hidden" name=OperateType value=""> <input
				type="hidden" name="Operator" value="">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
