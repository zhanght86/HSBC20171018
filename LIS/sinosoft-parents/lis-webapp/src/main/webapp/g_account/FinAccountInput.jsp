<%
/***************************************************************
 * <p>ProName��FinAccountInput.jsp</p>
 * <p>Title����ƿ�Ŀά������</p>
 * <p>Description��ά���������漰�Ļ�ƿ�Ŀ</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ʯȫ��
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>��ƿ�Ŀά��</title>
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
	<script src="./FinAccountInput.js"></script>
	<%@include file="./FinAccountInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinAccountSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinAccountInfo);">
			</td>
			<td class=titleImg>��ƿ�Ŀ��Ϣ</td>
		</tr>
	</table>
	
	<div id="divFinAccountInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>��ƿ�Ŀ����</td>
				<td class=input><input class="wid common" name=FinCode verify="��ƿ�Ŀ����|NOTNULL&LEN=10" maxlength=10 elementtype=nacessary></td>
				<td class=title>��ƿ�Ŀ����</td>
				<td class=input colspan="3"><input class=common style="width:300px" name=FinName verify="��ƿ�Ŀ����|NOTNULL" maxlength=60 elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>��ƿ�Ŀ����</td>
				<td class=input><input class=codeno name=FinType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fincode1',[this,FinTypeName],[0,1],null,'1 and codetype=#fintype#','1',1)" onkeyup="return showCodeListKey('fincode1',[this,FinTypeName],[0,1],null,'1 and codetype=#fintype#','1',1)"><input class=codename name=FinTypeName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��ע</td>
				<td class=input colspan="5"><textarea class=common name=Remark id=Remark cols="60" rows="3"></textarea></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
		<input class=cssButton type=button value="��  ��" onclick="insertClick();">
		<input class=cssButton type=button value="��  ��" onclick="updateClick();">
		<input class=cssButton type=button value="ɾ  ��" onclick="deleteClick();">
		<input class=cssButton type=button value="��  ��" onclick="resetClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinAccountGrid);">
			</td>
			<td class=titleImg>��ƿ�Ŀ��Ϣ�б�</td>
		</tr>
	</table>
	<div id=divFinAccountGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinAccountGrid"></span></td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="��������" onclick="exportData();">
		</center>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=HiddenFinCode>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
