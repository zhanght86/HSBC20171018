<%
/***************************************************************
 * <p>ProName��LJGetManualAppSubInput.jsp</p>
 * <p>Title: �ֶ�����������ϸ</p>
 * <p>Description���ֶ�����������ϸ</p>
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
	String tAppCom = request.getParameter("AppCom");
	String tApplyBachNo = request.getParameter("ApplyBatchNo");
	String tAppDate = request.getParameter("AppDate");
%>
<script>
	var tApplyBatchNo = "<%=tApplyBachNo%>"
	var tAppCom = "<%=tAppCom%>";
	var tAppDate = "<%=tAppDate%>";
</script>
<html>
<head>
	<title>�ֶ���������</title>
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
	<script src="./LJGetManualAppSubInput.js"></script>
	<%@include file="./LJGetManualAppSubInit.jsp"%>
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
	<div id="divInfo1" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>���κ�</td>
				<td class=input><input class="wid readonly" name=ApplyBatchNo id=ApplyBatchNo readonly></td>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=ApplyDate id=ApplyDate readonly></td>
				<td class=title>�ܽ��</td>
				<td class=input><input class="wid readonly" name=ApplyMoney id=ApplyMoney readonly></td>
			</tr>
			<tr class=common>
				<td class=title>����</td>
				<td class=input><input class="wid readonly" name=ApplyCount id=ApplyCount readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button id=ConfirmButton name=ConfirmButton value="�����ύȷ��" onclick="confirmClick();">
		<input class=cssButton type=button value="��  ��" onclick="backClick();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divInfo2" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=QueryGrpContNo id=QueryGrpContNo></td>
				<td class=title>ҵ������</td>
				<td class=input><input class=codeno name=QueryBussType id=QueryBussType ondblclick="return returnShowCodeList('finbusstype', [this,BussTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('finbusstype', [this,BussTypeName], [0,1]);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=BussTypeName id=BussTypeName readonly></td>
				<td class=title>ҵ���</td>
				<td class=input><input class="wid common" name=QueryBussNo id=QueryBussNo></td>
			</tr>
			<tr class=title>
				<td class=title>Ӧ������</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short" onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>Ӧ��ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate dateFormat="short" onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>������˾����</td>
				<td class=input><input class="wid common" name=QueryInsuranceComName id=QueryInsuranceComName></td>
			</tr>
			<tr class=common>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=QueryAppntName id=QueryAppntName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryInfo('0');">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
			</td>
			<td class=titleImg>����˸�����Ϣ�б�</td>
		</tr>
	</table>
	<div id="divInfo3" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanGetInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<!-- add by songsz 20150306 ���Ӵ���ת��� -->
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>����ʽ</td>
				<td class=input><input class=codeno name=GetDealType id=GetDealType ondblclick="return showCodeList('fingetdealtype', [this,GetDealTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('fingetdealtype', [this,GetDealTypeName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=GetDealTypeName id=GetDealTypeName readonly elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button id=ChooseButton name=ChooseButton value="ѡ  ��" onclick="chooseClick();">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo4);">
			</td>
			<td class=titleImg>��ѡ���������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divInfo4" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanApplyGetInfoGrid" ></span>
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
	<input class=cssButton type=button id=CancelButton name=CancelButton value="ȡ��ѡ��" onclick="cancelClick();">
	<input class=cssButton type=button id=ModifyButton name=ModifyButton value="����ά������" onclick="modifyBankClick();">
	<input class=cssButton type=button id=PrintButton name=PrintButton value="����˷Ѵ�ӡ" onclick="printClick();">
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
