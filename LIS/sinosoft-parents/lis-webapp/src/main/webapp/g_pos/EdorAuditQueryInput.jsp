<%
/***************************************************************
 * <p>ProName��EdorAuditQueryInput.jsp</p>
 * <p>Title����ȫ���</p>
 * <p>Description����ȫ���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
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
	<title>��ȫ���</title>
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
	<script src="./EdorAuditQueryInput.js"></script>
	<%@include file="./EdorAuditQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
		 	<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=Managecom id=Managecom readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('comcodeall',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03#','1','1',180);" 
					onkeyup="return showCodeListKey('comcodeall',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03#','1','1',180);"><input class=codename name=ManagecomName id=ManagecomName readonly></td>
				<td class=title>��ȫ�����</td>
				<td class=input><input class="wid common" name=EdorAppNo id=EdorAppNo></td>
				<td class=title>��ȫ������</td>
				<td class=input><input class="wid common" name=EdorNo id=EdorNo></td>
			</tr>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=PolicyNo id=PolicyNo></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=AppntName id=AppntName></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>������Ա</td>
				<td class=input><input class=codeno name=AcceptOperator id=AcceptOperator readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('usercode',[this, AcceptOperatorName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('usercode',[this, AcceptOperatorName],[0, 1],null,null,null,'1',180);"><input class=codename name=AcceptOperatorName id=AcceptOperatorName readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptStartDate onClick="laydate({elem: '#AcceptStartDate'});" id="AcceptStartDate"><span class="icon"><a onClick="laydate({elem: '#AcceptStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptEndtDate onClick="laydate({elem: '#AcceptEndtDate'});" id="AcceptEndtDate"><span class="icon"><a onClick="laydate({elem: '#AcceptEndtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>������Ա</td> 
				<td class=input><input class=codeno name=CheckOperator id=CheckOperator readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('usercode',[this, CheckOperatorName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('usercode',[this, CheckOperatorName],[0, 1],null,null,null,'1',180);"><input class=codename name=CheckOperatorName id=CheckOperatorName readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=CheckStartDate onClick="laydate({elem: '#CheckStartDate'});" id="CheckStartDate"><span class="icon"><a onClick="laydate({elem: '#CheckStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=CheckEndtDate onClick="laydate({elem: '#CheckEndtDate'});" id="CheckEndtDate"><span class="icon"><a onClick="laydate({elem: '#CheckEndtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton id=QueryButton type=button value="��  ѯ" onclick="queryClick(1);">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPublicGrid);">
			</td>
			<td class=titleImg>������</td>
		</tr>
	</table>
	<div id="divPublicGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPublicGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divPublicGridPage" style= "display: ''">
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</center>
		</div>
		
		<input class=cssButton name=AppButton id=AppButton type=button value="��  ��" onclick="appClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPersonalGrid);">
			</td>
			<td class=titleImg>���˳�</td>
		</tr>
	</table>
	<div id="divPersonalGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPersonalGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divPersonalGridPage" style= "display: ''">
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
			</center>
		</div>
		
		<input class=cssButton type=button value="��ȫ���" onclick="GotoDetail();">
		<input class=cssButton type=button value="�˻ع�����" onclick="returnClick();">
	</div>
	
	<!--������-->
	<input type=hidden name=Operate id=Operate>
	<Br /><Br /><Br /><Br /><Br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
