<%
/***************************************************************
 * <p>ProName��PropPrintInput.jsp</p>
 * <p>Title��Ͷ�����ӡ</p>
 * <p>Description��Ͷ�����ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>Ͷ�����ӡ</title>
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
	<script src="./LCPropPrintInput.js"></script>
	<%@include file="./LCPropPrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- ��ѯ���� -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>׼�ͻ�����</td>
				<td class=input><input class="wid common" name=preCustomerName id=preCustomerName></td>
				<td class=title>���۵���</td>
				<td class=input><input class="wid common" name=OfferListNo id=OfferListNo></td>
				<td class=title>ѯ�ۺ�</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo> </td>
			</tr>
			<tr class=common>
				<td class=title>ѯ������</td>
				<td class=input><input class=codeno name=QuotType id=QuotType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', null);" readonly>
				<input class=codename name=QuotTypeName id=QuotTypeName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult);">
			</td>
			<td class=titleImg>��ѯ���</td>
		</tr>
	</table>
	<div id="divResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuotInfoGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
	
	<input class=cssButton type=button value="���۵���ϸ" onclick="showQuotation();">
	<input class=cssButton type=button value="����Ͷ����" onclick="createPrintInfo();">
	</div>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultQuery);">
			</td>
			<td class=titleImg>Ͷ������Ϣ</td>
		</tr>
	</table>
	<div id="divResultQuery" class=maxbox1 style="display: ''">
	<table class=common>
		<tr class=common>
			<td class=title>Ͷ������</td>
			<td class=input><input class="wid common" name=GrpPropNoQ id=GrpPropNoQ></td>
			<td class=title>���۵���</td>
			<td class=input><input class="wid common" name=OfferListNoQ id=OfferListNoQ></td>
			<td class=title>��λ����</td>
			<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>
		</tr>
		<tr class=common>
			<td class=title>Ͷ����״̬</td>
			<td class=input><input class=codeno name=State id=State style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('printstate', [this,StateName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('printstate', [this,StateName], [0,1], null, null, null, '1', null);" readonly>
			<input class=codename name=StateName id=StateName></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	<input class=cssButton type=button value="��  ѯ" onclick="queryPropInfo();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult1);">
			</td>
			<td class=titleImg>��ѯ���</td>
		</tr>
	</table>
	<div id="divResult1" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPropInfoGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
	<input class=cssButton type=button value="Ͷ������Ϣ" onclick="propInfo();">
	<input type=hidden name=Operate>
	</div>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
