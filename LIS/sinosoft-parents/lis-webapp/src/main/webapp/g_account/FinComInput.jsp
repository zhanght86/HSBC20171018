<%
/***************************************************************
 * <p>ProName��FinComInput.jsp</p>
 * <p>Title���������ά��</p>
 * <p>Description���������ά��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>�������ά��</title>
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
	<script src="./FinComInput.js"></script>
	<%@include file="./FinComInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinComSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinComInfo);">
			</td>
			<td class=titleImg>���������Ϣ</td>
		</tr>
	</table>
	<div id="divFinComInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>�����������</td>
				<td class=input><input class="wid common" name=FinComCode id=FinComCode verify="�����������|NOTNULL&LEN=9" maxlength=9 elementtype=nacessary></td>
				<td class=title>�����������</td>
				<td class=input><input class="wid common" name=FinComName id=FinComName verify="�����������|NOTNULL" maxlength=60 elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinComGridInfo);">
			</td>
			<td class=titleImg>��������б�</td>
		</tr>
	</table>
	<div id=divFinComGridInfo style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinComGrid"></span></td>
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
	<input type=hidden name=HiddenFinComCode>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
