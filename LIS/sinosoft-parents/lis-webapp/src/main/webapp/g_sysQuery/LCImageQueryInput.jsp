<%
/***************************************************************
 * <p>ProName��LCImageQueryInput.jsp</p>
 * <p>Title��Ӱ�����ѯ</p>
 * <p>Description��Ӱ�����ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%= tGI.ManageCom %>";
	var tComCode="<%=tGI.ComCode%>";
	var tOperator="<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>Ӱ�����ѯ</title>
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
	<script src="./LCImageQueryInput.js"></script>
	<%@include file="./LCImageQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQueryInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>ɨ�����</td>
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,'1','1','1',180);" 
				onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,'1','1','1',180);"><input class=codename name=ManageComName readonly></td>
				<td class=title>ҵ������</td>
				<td class=input><input class=codeno name=BussType style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return returnShowCodeList('esbusstype',[this, BussTypeName],[0,1]);" 
					onkeyup="return returnShowCodeList('esbusstype',[this, BussTypeName],[0,1]);"><input class=codename name=BussTypeName></td>
				<td class=title>ҵ���</td>
				<td class=input><input class="wid common" verify="ҵ���|notnull" name=BussNo elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>ɨ������</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=ScanStartDate onClick="laydate({elem: '#ScanStartDate'});" id="ScanStartDate"><span class="icon"><a onClick="laydate({elem: '#ScanStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ɨ��ֹ��</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=ScanEndDate onClick="laydate({elem: '#ScanEndDate'});" id="ScanEndDate"><span class="icon"><a onClick="laydate({elem: '#ScanEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>ɨ����Ա</td>
				<td class=input><input class="wid common" name=Operator id=Operator></td>	
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<br>
			<font color='#FF0000'><b><span id="AcceptCountSpan">ҵ��ţ�����Լʹ��Ͷ����ţ���ȫʹ������ţ�����ʹ�ð�����</span></b></font>
		<br>
		<input class=cssButton value="��  ѯ" type=button onclick="queryClick();">
		<input class=cssButton value="��  ��" type=button onclick="resetClick();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQueryResult);">
			</td>
			<td class=titleImg>��ѯ���</td>
		</tr>
	</table>
	<div id="divQueryResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryResultGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
		
		<input class=cssButton value="�鿴Ӱ�����Ϣ" type=button onclick="queryScanPage();">
	</div>
	
	<Input type=hidden name=Operate id=Operate>
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
