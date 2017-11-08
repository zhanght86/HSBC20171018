<%
/***************************************************************
 * <p>ProName��RenewalPrintInput.jsp</p>
 * <p>Title���߽�֪ͨ���ӡ</p>
 * <p>Description���߽�֪ͨ���ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head >
	<title>�߽�֪ͨ���ӡ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./RenewalPrintInput.js"></script>
	<%@include file="./RenewalPrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="RenewalPrintSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
		 	<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�б�����</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom readonly  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('managecom',[this,ManageComName],[0,1],null,'1','1','1',180);" 
					onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,'1','1','1',180);"><input class=codename name=ManageComName readonly></td>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>�鵵����</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=StartOperDate onClick="laydate({elem: '#StartOperDate'});" id="StartOperDate"><span class="icon"><a onClick="laydate({elem: '#StartOperDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�鵵ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=EndOperDate onClick="laydate({elem: '#EndOperDate'});" id="EndOperDate"><span class="icon"><a onClick="laydate({elem: '#EndOperDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�ɷ�Ƶ��</td>
				<td class=input><input class=codeno  name=PayIntv id=PayIntv style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, PayIntvName],[0, 1],null,'1 and codetype=#payintv# and codeexp=#1#','1','1',180);" 
					onkeyup="return showCodeListKey('queryexp',[this, PayIntvName],[0, 1],null,'1 and codetype=#payintv# and codeexp=#1#','1','1',180);"><input class=codename name=PayIntvName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>Ӧ������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=StartPayDate onClick="laydate({elem: '#StartPayDate'});" id="StartPayDate"><span class="icon"><a onClick="laydate({elem: '#StartPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>Ӧ��ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=EndPayDate onClick="laydate({elem: '#EndPayDate'});" id="EndPayDate"><span class="icon"><a onClick="laydate({elem: '#EndPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��ӡ״̬</td>
				<td class=input><input class=codeno  name=PrintState style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('printflag',[this, PrintStateName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('printflag',[this, PrintStateName],[0, 1],null,null,null,'1',180);"><input class=codename name=PrintStateName readonly></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="��  ѯ" onclick="queryClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divContInfoGrid);">
			</td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divContInfoGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanContInfoGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
		<input class=cssButton type=button value="�߽�֪ͨ���ӡ" onclick="PrintRenewal()">	
	</div>
<div id=DivRiskInfo style="display:none">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRiskInfoGrid);">
			</td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divRiskInfoGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanRiskInfoGrid"></span>
				</td>
			</tr>
		</table>
	</div>
</div>
	<!--������-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
