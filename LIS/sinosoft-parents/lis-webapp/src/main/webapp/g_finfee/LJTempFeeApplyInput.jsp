<%
/***************************************************************
 * <p>ProName��LJTempFeeApplyInput.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
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
	ExeSQL	tExeSQL = new ExeSQL();
	//--LJL
	String tSQL = "select a.comgrade from ldcom a where 1=1 and a.comcode='"+"?tGIManageCom?"+"'";
	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    sqlbv.sql(tSQL);
    sqlbv.put("tGIManageCom", tGI.ManageCom);
    //--LJL
    String tComGrade = tExeSQL.getOneValue(sqlbv);
%>
<script>
	var tOperator = "<%=tGI.Operator%>";
	var tComGrade = "<%=tComGrade%>";
</script>

<%@page import="com.sinosoft.utility.SQLwithBindVariables"%><html>
<head>
	<title>��������</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LJTempFeeApplyInput.js"></script>
	<script src="./LJFinfeeCommon.js"></script>
	<%@include file="./LJTempFeeApplyInit.jsp"%>
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
				<td class=title>���ѷ�ʽ</td>
				<td class=input><input class=codeno name=QueryPayType id=QueryPayType style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 ondblclick="return showCodeList('paymode', [this,QueryPayTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('paymode', [this,QueryPayTypeName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryPayTypeName readonly></td>
				<td class=title>�ͻ�������</td>
				<td class=input><input class=codeno name=QueryCustBankCode id=QueryCustBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 ondblclick="return showCodeList('headbank', [this,QueryCustBankName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('headbank', [this,QueryCustBankName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryCustBankName readonly></td>
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
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short" onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate dateFormat="short" onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input><input type=hidden name=QueryTempFeeNo id=QueryTempFeeNo readonly></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryApplyTempFee('0');">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>����������Ϣ�б�</td>
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
			<td class=titleImg>����������Ϣά��</td>
		</tr>
	</table>
	<div id="divInfo3" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>���ѷ�ʽ</td>
				<td class=input><input class=codeno name=PayType id=PayType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode', [this,PayTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('paymode', [this,PayTypeName], [0,1], null, null, null, 1);" readonly><input class=codename name=PayTypeName id=PayTypeName readonly elementtype=nacessary></td>
				<td class=title>�ͻ�������</td>
				<td class=input><input class=codeno name=CustBankCode id=CustBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('headbank', [this,CustBankName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('headbank', [this,CustBankName], [0,1], null, null, null, 1);" readonly><input class=codename name=CustBankName id=CustBankName onkeydown="fuzzyQueryHeadBank(CustBankCode,CustBankName)"><span id=N1 name=N1 style="display:none;color:red"> *</span></td>
				<td class=title>�ͻ������˺�</td>
				<td class=input><input class="wid common" name=CustBankAccNo id=CustBankAccNo style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="removeSpecChar(this);"><span id=N2 name=N2 style="display:none;color:red"> *</span></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ��˻���/���������</td>
				<td class=input><input class="wid common" name=CustAccName id=CustAccName maxlength=60 elementtype=nacessary></td>
				<td class=title>���</td>
				<td class=input><input class="wid common" name=Money id=Money elementtype=nacessary></td>
				<td class=title>Ͷ����λ����</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName maxlength=60 elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ���������</td>
				<td class=input><input class="wid common" name=AgentName id=AgentName maxlength=60 elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ��" onclick="addTempFee();">
		<input class=cssButton type=button value="��  ��" onclick="modifyTempFee();">
		<input class=cssButton type=button value="ɾ  ��" onclick="deleteTempFee();">
		<input class=cssButton type=button value="��ӡ���ӵ�" onclick="printTempFee();">
		<input class=cssButton type=button value="�ύ¼��" onclick="toInpTempFee();">
	</div>
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetSql>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate id=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br /><br /><br /><br />
</body>
</html>
