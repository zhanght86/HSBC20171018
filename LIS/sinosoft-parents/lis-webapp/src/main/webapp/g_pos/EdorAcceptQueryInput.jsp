<%
/***************************************************************
 * <p>ProName��EdorAcceptQueryInput.jsp</p>
 * <p>Title����ȫ����</p>
 * <p>Description����ȫ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-11
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
	<title>��ȫ����</title>
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
	<script src="./EdorAcceptQueryInput.js"></script>
	<%@include file="./EdorAcceptQueryInit.jsp"%>
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
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>ɨ�����</td>
				<td class=input><input class=codeno name=ScanManagecom id=ScanManagecom readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('comcodeall',[this, ScanManagecomName],[0, 1],null,'1 and comgrade=#03#','1','1',180);" 
					onkeyup="return showCodeListKey('comcodeall',[this, ScanManagecomName],[0, 1],null,'1 and comgrade=#03#','1','1',180);"><input class=codename name=ScanManagecomName readonly></td>
				<td class=title>��ȫ�����</td>
				<td class=input><input class="wid common" name=EdorAppNo id=EdorAppNo></td>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=PolicyNo id=PolicyNo></td>
			</tr>
			<tr class=common>
				<td class=title>ɨ����Ա</td>
				<td class=input><input class=codeno name=ScanOperator id=ScanOperator readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('usercode',[this, ScanOperatorName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('usercode',[this, ScanOperatorName],[0, 1],null,null,null,'1',180);"><input class=codename name=ScanOperatorName readonly></td>
				<td class=title>ɨ������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=ScanStartDate onClick="laydate({elem: '#ScanStartDate'});" id="ScanStartDate"><span class="icon"><a onClick="laydate({elem: '#ScanStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ɨ��ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=ScanEndtDate onClick="laydate({elem: '#ScanEndtDate'});" id="ScanEndtDate"><span class="icon"><a onClick="laydate({elem: '#ScanEndtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="��  ѯ" onclick="queryClick(1);">
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
		
		<input class=cssButton name=AppButton type=button value="��  ��" onclick="appClick();">
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
		<div id= "divPersonalGridPage" style= "display: none">
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
			</center>
		</div>
		
		<input class=cssButton type=button value="��ȫ����" onclick="GotoDetail();">
		<input class=cssButton type=button value="�˻ع�����" onclick="returnClick();">
	</div>
	
	<!--������-->
	<input type=hidden name=Operate id=Operate>
	<Br /><Br /><Br /><Br /><Br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
