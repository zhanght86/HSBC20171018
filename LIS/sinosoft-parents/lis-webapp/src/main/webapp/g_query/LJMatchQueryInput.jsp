<%
/***************************************************************
 * <p>ProName��LJMatchQueryInput.jsp</p>
 * <p>Title�����Ѳ�ѯ</p>
 * <p>Description�����Ѳ�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-08-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
</script>
<html>
<head>
	<title>���Ѳ�ѯ</title>
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
	<script src="./LJMatchQueryInput.js"></script>
	<%@include file="./LJMatchQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div style="display: none">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanVirtualGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=QueryManageCom id=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryManageComName readonly></td>
				<td class=title>�����ύ����</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate1 dateFormat="short" onClick="laydate({elem: '#QueryStartDate1'});" id="QueryStartDate1"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�����ύֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate1 dateFormat="short" onClick="laydate({elem: '#QueryEndDate1'});" id="QueryEndDate1"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>����״̬</td>
				<td class=input><input class=codeno name=QueryMatchState id=QueryMatchState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('premmatchstate', [this,QueryMatchStateName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('premmatchstate', [this,QueryMatchStateName], null, null, null, 1);" readonly><input class=codename name=QueryMatchStateName readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate2 dateFormat="short" onClick="laydate({elem: '#QueryStartDate2'});" id="QueryStartDate2"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate2 dateFormat="short" onClick="laydate({elem: '#QueryEndDate2'});" id="QueryEndDate2"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>ƥ������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate3 dateFormat="short" onClick="laydate({elem: '#QueryStartDate3'});" id="QueryStartDate3"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ƥ��ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate3 dateFormat="short" onClick="laydate({elem: '#QueryEndDate3'});" id="QueryEndDate3"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ƥ���ύ����</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate4 dateFormat="short" onClick="laydate({elem: '#QueryStartDate4'});" id="QueryStartDate4"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate4'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>ƥ���ύֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate4 dateFormat="short" onClick="laydate({elem: '#QueryEndDate4'});" id="QueryEndDate4"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate4'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate5 dateFormat="short" onClick="laydate({elem: '#QueryStartDate5'});" id="QueryStartDate5"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate5'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>���ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate5 dateFormat="short" onClick="laydate({elem: '#QueryEndDate5'});" id="QueryEndDate5"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate5'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryInfo();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanMatchInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="��������" onclick="expData();">
		</center>
	</div>
	<div id="divInfo3" style="display:none">
		<br/>
		<table class=common >
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanChoosedDataGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>    
	</div>
	<div id="divInfo4" style="display:none">
		<br/>
		<table class=common >
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanBusinessDataGrid"></span></td>
			</tr>
		</table>
	</div>
	
	<div id="divInfo5" style="display:none">
		<br/>
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanUploadFileGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage4.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage4.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage4.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage4.lastPage();">
		</center> 
	</div>
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetSql>
</form>

<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
