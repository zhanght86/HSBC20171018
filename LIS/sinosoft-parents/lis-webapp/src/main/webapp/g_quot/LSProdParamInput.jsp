<%
/***************************************************************
 * <p>ProName��LSProdParamInput.jsp</p>
 * <p>Title����Ʒ������Ϣά��--�����ά��</p>
 * <p>Description����Ʒ������Ϣά��--�����ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-04
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
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tObjType = request.getParameter("ObjType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tObjType = "<%=tObjType%>";
	var tOperator = "<%=tOperator%>";
</script>
<html>
<head>
	<title>��Ʒ������Ϣά��</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSProdParamInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSProdParamInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li onclick="goToPordParamStep(0)" class="now">�����ά��</li>
		   <li onclick="goToPordParamStep(1)">��ȫ�˷��㷨ά��</li>
		</ul>
	</div>
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divManageFee);">
				</td>
				<td class=titleImg>��ά���������Ϣ�б�</td>
			</tr>
		</table>
		<div id="divManageFee"  style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanManageFeeGrid"></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</center>
			<table class=common>
				<tr class=common>
					<td class=title>����</td>
					<td class=input><input class=codeno name=RiskCode id=RiskCode verify="����|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotprodrisk',[this,RiskName],[0,1],null,'1 and a.quotno = #<%=tQuotNo%># and a.quotbatno = #<%=tQuotBatNo%>#','1','1',null);" onkeyup="return showCodeListKey('quotprodrisk',[this,RiskName],[0,1],null,null,null,'1',null);" readonly><input class=codename name=RiskName id=RiskName elementtype=nacessary readonly></td>
					<td class=title>�˻�����</td>
					<td class=input><input class=codeno name=AccType id=AccType verify="�˻�����|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="returnShowCodeList('risktoacc',[this,AccTypeName],[0,1]);" onkeyup="returnShowCodeListKey('risktoacc',[this,AccTypeName],[0,1]);" readonly><input class=codename name=AccTypeName id=AccTypeName elementtype=nacessary readonly></td>
					<td class=title>���������</td>
					<td class=input><input class=codeno name=FeeType id=FeeType verify="���������|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('mafeetype',[this, FeeTypeName], [0,1],null,null,null,1);" onkeyup="return showCodeListKey('mafeetype',[this, FeeTypeName], [0,1],null,null,null,1);" readonly><input class=codename name=FeeTypeName elementtype=nacessary readonly></td>
				</tr>
				<tr id=allTR1 class=common style="display: none">
					<td class=title>��ʼ����ѿ۳���ʽ</td>
					<td class=input><input class=codeno name=DeductType id=DeductType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="returnShowCodeList('deducttype',[this,DeductTypeName],[0,1]);" onkeyup="returnShowCodeListKey('deducttype',[this,DeductTypeName],[0,1]);" readonly><input class=codename name=DeductTypeName id=DeductTypeName elementtype=nacessary readonly></td>
					<td class=title>��ʼ�����(Ԫ)/����</td>
					<td class=input><input class="wid common" name=FeeValue id=FeeValue maxlength=20 elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr id=allTR2 class=common style="display: none">
					<td class=title>�¶ȹ��������</td>
					<td class=input><input class=codeno name=MonthFeeType id=MonthFeeType ondblclick="returnShowCodeList('deducttype',[this,MonthFeeTypeName],[0,1]);" onkeyup="returnShowCodeListKey('deducttype',[this,MonthFeeTypeName],[0,1]);"><input class=codename name=MonthFeeTypeName elementtype=nacessary readonly></td>
					<td class=title>�¶ȹ����(Ԫ)/����</td>
					<td class=input><input class="wid common" name=MonthValue id=MonthValue maxlength=20 elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr id=allTR3 class=common style="display: none">
					<td class=title>��ȹ��������</td>
					<td class=input><input class=codeno name=YearFeeType id=YearFeeType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="returnShowCodeList('deducttype',[this,YearFeeTypeName],[0,1]);" onkeyup="returnShowCodeListKey('deducttype',[this,YearFeeTypeName],[0,1]);"><input class=codename name=YearFeeTypeName id=YearFeeTypeName elementtype=nacessary readonly></td>
					<td class=title>�����ʼֵ(��)</td>
					<td class=input><input class="wid common" name=YearStartValue id=YearStartValue maxlength=6 elementtype=nacessary></td>
					<td class=title>�����ֵֹ(<)</td>
					<td class=input><input class="wid common" name=YearEndValue id=YearEndValue maxlength=6 elementtype=nacessary></td>
				</tr>
				<tr id=allTR4 class=common style="display: none">
					<td class=title>��ȹ����(Ԫ)/����</td>
					<td class=input><input class="wid common" name=YearValue id=YearValue maxlength=20 elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton type=button value="��  ��" name="AddButton" onclick="addSubmit();">
			<input class=cssButton type=button value="��  ��" name="ModButton" onclick="modSubmit();">
			<input class=cssButton type=button value="ɾ  ��" name="DelButton" onclick="delSubmit();">
		</div>
		<hr class="line"/>
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=SerialNo id=SerialNo>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
