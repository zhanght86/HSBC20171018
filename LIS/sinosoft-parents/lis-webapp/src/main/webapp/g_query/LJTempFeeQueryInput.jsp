<%
/***************************************************************
 * <p>ProName��LJTempFeeQueryInput.jsp</p>
 * <p>Title�����˲�ѯ</p>
 * <p>Description�����˲�ѯ</p>
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
	<title>���˲�ѯ</title>
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
	<script src="./LJTempFeeQueryInput.js"></script>
	<%@include file="./LJTempFeeQueryInit.jsp"%>
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
				<td class=title>���ѷ�ʽ</td>
				<td class=input><input class=codeno name=QueryPayType id=QueryPayType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paytype', [this,QueryPayTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('paytype', [this,QueryPayTypeName], [0,1], null, null, null, 1);"><input class=codename name=QueryPayTypeName></td>
				<td class=title>�ͻ�������</td>
				<td class=input><input class=codeno name=QueryCustBankCode id=QueryCustBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('headbank', [this,QueryCustBankName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('headbank', [this,QueryCustBankName], [0,1], null, null, null, 1);"><input class=codename name=QueryCustBankName></td>
				<td class=title>�ͻ������˺�</td>
				<td class=input><input class="wid common" name=QueryCustBankAccNo id=QueryCustBankAccNo></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ��˻���/���������</td>
				<td class=input><input class="wid common" name=QueryCustAccName id=QueryCustAccName></td>
				<td class=title>Ͷ����λ����</td>
				<td class=input><input class="wid common" name=QueryGrpName id=QueryGrpName></td>
				<td class=title>�ͻ���������</td>
				<td class=input><input class="wid common" name=QueryAgentName id=QueryAgentName></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryApplyStartDate dateFormat="short" onClick="laydate({elem: '#QueryApplyStartDate'});" id="QueryApplyStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryApplyStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryApplyEndDate dateFormat="short" onClick="laydate({elem: '#QueryApplyEndDate'});" id="QueryApplyEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryApplyEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>¼������</td>
				<td class=input><input class=coolDatePicker name=QueryInputStartDate dateFormat="short" onClick="laydate({elem: '#QueryInputStartDate'});" id="QueryInputStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryInputStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>¼��ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryInputEndDate dateFormat="short" onClick="laydate({elem: '#QueryInputEndDate'});" id="QueryInputEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryInputEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�������</td>
				<td class=input><input class=coolDatePicker name=QueryConfirmStartDate dateFormat="short" onClick="laydate({elem: '#QueryConfirmStartDate'});" id="QueryConfirmStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryConfirmStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>���ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryConfirmEndDate dateFormat="short" onClick="laydate({elem: '#QueryConfirmEndDate'});" id="QueryConfirmEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryConfirmEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>����״̬</td>
				<td class=input><input class=codeno name=State style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('tempfeestate', [this,StateName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('tempfeestate', [this,StateName], [0,1], null, null, null, 1);" readonly><input class=codename name=StateName readonly></td>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('managecom', [this,QueryManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('managecom', [this,QueryManageComName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryManageComName readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryApplyTempFee();">
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
					<span id="spanTempFeeInfoGrid" ></span>
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
			<td class=titleImg>��ϸ��Ϣ�б�</td>
		</tr>
	</table>
	<div id="divInfo3" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanTempFeeDetailInfoGrid" ></span>
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
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
