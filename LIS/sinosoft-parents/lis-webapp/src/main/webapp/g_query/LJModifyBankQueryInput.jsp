<%
/***************************************************************
 * <p>ProName��LJModifyBankQueryInput.jsp</p>
 * <p>Title��������Ϣ�޸Ĳ�ѯ</p>
 * <p>Description��������Ϣ�޸Ĳ�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-09-02
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
	<title>������Ϣ�޸Ĳ�ѯ</title>
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
	<script src="./LJModifyBankQueryInput.js"></script>
	<%@include file="./LJModifyBankQueryInit.jsp"%>
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
				<td class=input><input class=codeno name=QueryManageCom id=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" value="<%=tGI.ManageCom%>" ondblclick="return showCodeList('managecom', [this,QueryManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('managecom', [this,QueryManageComName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryManageComName readonly></td>
				<td class=title>ҵ������</td>
				<td class=input><input class=codeno name=QueryBussType id=QueryBussType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('finbusstype', [this,QueryBussTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('finbusstype', [this,QueryBussTypeName], [0,1]);" readonly><input class=codename name=QueryBussTypeName readonly></td>
				<td class=title>ҵ�����</td>
				<td class=input><input class="wid common" name=QueryBussNo id=QueryBussNo></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ���������</td>
				<td class=input><input class=codeno name=QueryHeadBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('headbank', [this,QueryHeadBankName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('headbank', [this,QueryHeadBankName],[0,1],null,null,null,1);" readonly><input class=codename name=QueryHeadBankName readonly></td>
				<td class=title>�ͻ������˺�</td>
				<td class=input><input class="wid common" name=QueryBankAccNo id=QueryBankAccNo></td>
				<td class=title>�ͻ��˻���</td>
				<td class=input><input class="wid common" name=QueryAccName id=QueryAccName></td>
			</tr>
			<tr class=common>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=QueryGrpName id=QueryGrpName></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate1 dateFormat="short" onClick="laydate({elem: '#QueryStartDate1'});" id="QueryStartDate1"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate1 dateFormat="short" onClick="laydate({elem: '#QueryEndDate1'});" id="QueryEndDate1"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>���״̬</td>
				<td class=input><input class=codeno name=QueryState id=QueryState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('modifybankstate', [this,QueryStateName], [0,1],null,null,null,1);" onkeyup="return showCodeListKey('modifybankstate', [this,QueryStateName], [0,1],null,null,null,1);" readonly><input class=codename name=QueryStateName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
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
					<span id="spanQueryInfoGrid" ></span>
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
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
			</td>
			<td class=titleImg>��ʷ������Ϣ</td>
		</tr>
	</table>
	<div id="divInfo3" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryInfo1Grid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
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
<Br /><Br /><Br /><Br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
