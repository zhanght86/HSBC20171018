<%
/***************************************************************
 * <p>ProName��LSQuotPrintInput.jsp</p>
 * <p>Title�����۵���ӡ</p>
 * <p>Description�����۵���ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tManageCom = tGI.ManageCom;
%>
<script>
	var tOperator = "<%=tOperator%>";//��ǰ��¼��
	var tManageCom = "<%=tManageCom%>";//��ǰ��¼����
	var tQuotNo = "";
	var tQuotBatNo = "";
	var tQuotType = "";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>���۵���ӡ</title>
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
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotPrintInput.js"></script>
	<%@include file="./LSQuotPrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotPrintSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>׼�ͻ�����/��Ŀ����</td>
				<td class=input><input class="wid common" name=Name id=Name></td>
				<td class=title>ѯ�ۺ�</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo></td>
				<td class=title>ѯ������</td>
				<td class=input><input class=codeno name=QuotType id=QuotType ondblclick="return showCodeList('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', null);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QuotTypeName id=QuotTypeName></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick('Page');">
		<input class=cssButton type=button value="��ϸ�鿴" onclick="toPalnDetailView();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuotInfo);">
			</td>
			<td class=titleImg>ѯ����Ϣ</td>
		</tr>
	</table>
	<div id="divQuotInfo" style="display: ''">
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
	</div>
	<div id="divPrintCust" style="display: 'none'">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
				</td>
				<td class=titleImg>��ӡ�ͻ�</td>
			</tr>
		</table>
		<div id="divInfo" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>׼�ͻ����� </td>
					<td class=input colspan=3><input class=codeno name=PreCustomerNo id=PreCustomerNo verify="׼�ͻ�����|notnull" style="width:80px" ondblclick="queryCustomer();" style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=PreCustomerName id=PreCustomerName style="width:300px"  readonly elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>֤������</td>
					<td class=input><input class=common name=IDType style="display: none"><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid readonly" name=IDNo id=IDNo readonly></td>
					<td class=title>��λ����</td>
					<td class=input><input class=common name=GrpNature id=GrpNature style="display: none"><input class="wid readonly" name=GrpNatureName id=GrpNatureName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��ҵ����</td>
					<td class=input><input class=common name=BusiCategory id=BusiCategory style="display: none"><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
					<td class=title>��λ������</td>
					<td class=input><input class="wid readonly" name=SumNumPeople id=SumNumPeople readonly></td>
					<td class=title>Ԥ��Ͷ��������</td>
					<td class=input><input class="wid readonly" name=PreSumPeople id=PreSumPeople readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��ַ</td>
					<td class=input colspan=5><input class=readonly name=Address id=Address readonly></td>
				</tr>
			</table>
		</div>
	</div>
	<input class=cssButton type=button value="���ɱ��۵�" onclick="createQuotation();">
	<br/>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryBJInfo);">
			</td>
			<td class=titleImg>������Ϣ��ѯ����</td>
		</tr>
	</table>
	<div id="divQueryBJInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>���۵���</td>
				<td class=input><input class="wid common" name=OfferListNo id=OfferListNo></td>
				<td class=title>ѯ�ۺ�</td>
				<td class=input><input class="wid common" name=QuotNoBJ id=QuotNoBJ></td>
				<td class=title>׼�ͻ�����/��Ŀ����</td>
				<td class=input><input class="wid common" name=NameBJ id=NameBJ></td>
			</tr>
			<tr class=common>
				<td class=title>ѯ������</td>
				<td class=input><input class=codeno name=QuotTypeBJ id=QuotTypeBJ ondblclick="return showCodeList('quottype', [this,QuotTypeBJName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('quottype', [this,QuotTypeBJName], [0,1], null, null, null, '1', null);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QuotTypeBJName id=QuotTypeBJName></td>
				<td class=title>���۵�״̬</td>
				<td class=input><input class=codeno name=State id=State ondblclick="return showCodeList('printstate', [this,StateName], [0,1], null, '1 and code in (#-1#,#1#)', '1', '1', null);" onkeyup="return showCodeListKey('printstate', [this,StateName], [0,1], '1 and code in (#-1#,#1#)', '1', '1', '1', null);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=StateName id=StateName></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryQuotInfo('Page');">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBJInfo);">
			</td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divBJInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOfferListGrid"></span>
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
	<input class=cssButton type=button value="�鿴���۵�" onclick="seeQuotation();">
	<input class=cssButton type=button value="��ӡ���۵�" onclick="printOfferList('pdf');">
	<!-- input class=cssButton type=button value="��ӡ���۵�(word)" onclick="printOfferList('doc');" -->
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=Query_QuotNo id=Query_QuotNo>
	<input type=hidden name=Query_QuotBatNo id=Query_QuotBatNo>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
