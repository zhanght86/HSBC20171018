<%
/***************************************************************
 * <p>ProName��LJPremMatchConfirmInput.jsp</p>
 * <p>Title������ƥ��</p>
 * <p>Description������ƥ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
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
	<title>�������</title>
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
	<script src="./LJPremMatchConfirmInput.js"></script>
	<%@include file="./LJPremMatchConfirmInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox1 style="display: ''">	
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom ondblclick="return showCodeList('conditioncomcode', [this,ManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,ManageComName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=ManageComName id=ManageComName readonly></td>
				<td class=title>ƥ���ύ����</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short" onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ƥ���ύֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate dateFormat="short" onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryMatchInfo();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>�������Ϣ�б�</td>
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
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetSql>
</form>
<form name=fm3 id=fm3 method=post action="" target=fraSubmit>
	<table class=common>
		<tr class=common>
			<td class=title>��˽���</td>
			<td class=input><input class=codeno name=ConfirmConclusion id=ConfirmConclusion ondblclick="return showCodeList('inputconclusion', [this,ConfirmConclusionName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('inputconclusion', [this,ConfirmConclusionName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=ConfirmConclusionName id=ConfirmConclusionName readonly elementtype=nacessary></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
		<tr class=common>
			<td class=title>��������</td>
			<td class=input colspan=5><textarea cols=80 rows=3 name=ConfirmDesc></textarea></td>
		</tr>
	</table>
	<input class=cssButton type=button id=MatchConfirmButton name=MatchConfirmButton value="����ύ" onclick="matchConfirmClick();">
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div id=divMatchingConfirmGrid style="display:none">
		<div id="c1" style="display:''">
			<table class=common >
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanChoosedData1Grid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage6.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage6.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage6.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage6.lastPage();">
			</center>    
		</div>
		<br/>
		<div id="c2" style="display:''">
			<table class=common >
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanBusinessData1Grid"></span></td>
				</tr>
			</table>     
		</div>
	</div>
</form>
<div id=divUploadInfo style="display:none">
	<form name=fm4 id=fm4 method=post action="" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divUploadGrid);">
				</td>
				<td class=titleImg>�����ϴ�</td>
			</tr>
		</table>
		<div id=divUploadGrid style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanUploadFileGrid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage8.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage8.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage8.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage8.lastPage();">
			</center> 
		</div>
	</form>
</div>

<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>.
<br/><br/><br/><br/>
</body>
</html>
