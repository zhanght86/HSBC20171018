<%
/***************************************************************
 * <p>ProName��LJTempFeeOutQueryInput.jsp</p>
 * <p>Title: �����˷Ѳ�ѯ</p>
 * <p>Description�������˷Ѳ�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-08-31
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
	<title>�����˷Ѳ�ѯ</title>
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
	<script src="./LJTempFeeOutQueryInput.js"></script>
	<%@include file="./LJTempFeeOutQueryInit.jsp"%>
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
				<td class=input><input class=codeno name=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryManageComName readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate1 dateFormat="short" onClick="laydate({elem: '#QueryStartDate1'});" id="QueryStartDate1"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate1 dateFormat="short" onClick="laydate({elem: '#QueryEndDate1'});" id="QueryEndDate1"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>���շѺ�</td>
				<td class=input><input class="wid common" name=QueryTempFeeNo id=QueryTempFeeNo></td>
				<td class=title>�տ�����</td>
				<td class=input><input class=codeno name=QueryInBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('infinbank', [this,QueryInBankCodeName,QueryInBankAccNo], [0,1,2]);" onkeyup="return returnShowCodeListKey('infinbank', [this,QueryInBankCodeName,QueryInBankAccNo], [0,1,2]);" readonly><input class=codename name=QueryInBankCodeName readonly></td>
				<td class=title>�տ��˺�</td>
				<td class=input><input class="wid readonly" name=QueryInBankAccNo readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ���������</td>
				<td class=input><input class=codeno name=QueryHeadBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('headbank',[this,QueryHeadBankName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('headbank',[this,QueryHeadBankName],[0,1],null,null,null,1);" readonly><input class=codename name=QueryHeadBankName readonly></td>
				<td class=title>�ͻ������˺�</td>
				<td class=input><input class="wid common" name=QueryAccNo></td>
				<td class=title>�ͻ��˻���</td>
				<td class=input><input class="wid common" name=QueryAccName></td>
			</tr>
			<tr class=common>
				<td class=title>���ѷ�ʽ</td>
				<td class=input><input class=codeno name=QueryPayType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode',[this,QueryPayTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('paymode',[this,QueryPayTypeName],[0,1],null,null,null,1);" readonly><input class=codename name=QueryPayTypeName readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate2 dateFormat="short" onClick="laydate({elem: '#QueryStartDate2'});" id="QueryStartDate2"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate2 dateFormat="short" onClick="laydate({elem: '#QueryEndDate2'});" id="QueryEndDate2"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate3 dateFormat="short" onClick="laydate({elem: '#QueryStartDate3'});" id="QueryStartDate3"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate3 dateFormat="short" onClick="laydate({elem: '#QueryEndDate3'});" id="QueryEndDate3"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����״̬</td>
				<td class=input><input class=codeno name=QueryPayState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paystate',[this,QueryPayStateName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('paystate',[this,QueryPayStateName],[0,1],null,null,null,1);" readonly><input class=codename name=QueryPayStateName readonly></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryInfo();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>��ѯ����б�</td>
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
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetSql>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
	
</form>
<Br /><Br /><Br /><Br /><Br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
