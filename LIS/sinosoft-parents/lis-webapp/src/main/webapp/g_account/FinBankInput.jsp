<%
/***************************************************************
 * <p>ProName��FinBankInput.jsp</p>
 * <p>Title���������ά��</p>
 * <p>Description����������ά��</p>
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
	<title>��������ά��</title>
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
	<script src="./FinBankInput.js"></script>
	<%@include file="./FinBankInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinBankSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinBankInfo);">
			</td>
			<td class=titleImg>����������Ϣ</td>
		</tr>
	</table>
	<div id="divFinBankInfo" class=maxbox1 style="display:''">
		
		<table class=common>
			<tr class=common>
				<td class=title>���б���</td>
				<td class=input><input class="wid common" name=FinBankCode id=FinBankCode verify="�����������|NOTNULL" maxlength=13 elementtype=nacessary></td>
				<td class=title>��������</td>
				<td class=input colspan="3"><input class=common style="width:554px" name=FinBankName id=FinBankName verify="�����������|NOTNULL" maxlength=60 elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>���д���</td>
				<td class=input><input class=codeno name=FinBankClass id=FinBankClass style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('finheadbank',[this, FinBankClassName],[0, 1],null,null,null,1);" onkeyup="return showCodeListKey('finheadbank',[this,FinBankClassName],[0,1],null,null,null,1);"><input class=codename name=FinBankClassName></td>
				<td class=title>�˻�����</td>
				<td class=input><input class=codeno name=FinBankNature style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('finbanknature',[this, FinBankNatureName],[0, 1],null,null,null,1);" onkeyup="return showCodeListKey('finbanknature',[this,FinBankNatureName],[0,1],null,null,null,1);"><input class=codename name=FinBankNatureName></td>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=FinComCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fincom',[this,FinComName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('fincom',[this,FinComName],[0,1],null,null,null,'1',null);"><input class=codename name=FinComName></td>
			</tr>
			<tr class=common>
				<td class=title>�����˺�</td>
				<td class=input><input class="wid common" name=AccNo verify="�����˺�|NOTNULL" maxlength=60 elementtype=nacessary></td>
				<td class=title>�Ƿ���Ч</td>
				<td class=input><input class=codeno name=State verify="�Ƿ���Ч|code:trueflag" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag',[this,StateName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('trueflag',[this,StateName],[0,1],null,null,null,'1',null);"><input class=codename name=StateName></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinBankGridInfo);">
			</td>
			<td class=titleImg>��������б�</td>
		</tr>
	</table>
	<div id=divFinBankGridInfo style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinBankGrid"></span></td>
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
	<input type=hidden name=HiddenFinBankCode>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
