<%
/***************************************************************
 * <p>ProName��LSQuotFeeInput.jsp</p>
 * <p>Title��������Ϣ</p>
 * <p>Description��������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tQuotType = request.getParameter("QuotType");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tActivityID = request.getParameter("ActivityID");
%>
<script>
	var tQuotType = "<%=tQuotType%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head >
	<title>������Ϣ</title>
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
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotFeeInput.js"></script>
	<%@include file="./LSQuotFeeInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotFeeSave.jsp" target=fraSubmit>
	<div id="divFee" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRiskFee);">
				</td>
				<td class=titleImg>��������Ϣ<span style="color:red">(����Ӷ����������������ѱ���ֻ��Ϊ�������ݣ�����Ϊ���ű�׼)</span></td>
			</tr>
		</table>
		<div id="divRiskFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskFeeGrid"></span>
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
		<br>
		<!-- div id="divChargeFee" style="display: none">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanChargeFeeGrid"></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
			</center>
		</div -->
		
		<input class=cssButton type=button value="��������Ϣ����" name="SaveButton" id=SaveButton onclick="saveClick();">
		
		<div id="divWeightFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>��Ȩ�����ѱ���</td>
					<td class=input><input class="wid readonly" name=WeightChargeRate id=WeightChargeRate readonly></td>
					<td class=title>��Ȩҵ�������</td>
					<td class=input><input class="wid readonly" name=WeightBusiFeeRate id=WeightBusiFeeRate readonly></td>
					<td class=title>��ȨԤ��������</td>
					<td class=input><input class="wid readonly" name=WeightLossRatio id=WeightLossRatio readonly></td>
				</tr>
				<tr class=common>
					
					<td class=title>��Ȩ������̯</td>
					<td class=input><input class="wid readonly" name=WeightPoolRate id=WeightPoolRate readonly></td>
					<td class=title>��Ȩ˰��</td>
					<td class=input><input class="wid readonly" name=WeightTaxFeeRate id=WeightTaxFeeRate readonly></td>
					<td class=title>��Ȩ������</td>
					<td class=input><input class="wid readonly" name=WeightSumRate id=WeightSumRate readonly></td>
				</tr>
				<tr class=common>
					<td class=title>�ǽ����ձ���ռ��</td>
					<td class=input><input class="wid readonly" name=NonMedPremRate id=NonMedPremRate readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
	</div>
		
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOtherFee);">
			</td>
			<td class=titleImg>����������Ϣ</td>
		</tr>
	</table>
	<div id="divOtherFee" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOtherFeeGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage3.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage3.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage3.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage3.lastPage();">
		</center>
	</div>
	<br>
	<div id="divOtherFeeDetail" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=FeeType id=FeeType readonly verify="��������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('otherfeetype',[this, FeeTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('otherfeetype',[this, FeeTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=FeeTypeName id=FeeTypeName readonly elementtype=nacessary></td>
				<td class=title id="divOtherFeeDescTitle" style="display: none">������������</td>
				<td class=input id="divOtherFeeDescInput" style="display: none"><input class="wid common" name=OtherFeeDesc id=OtherFeeDesc verify="������������|len<=15" maxlength=15 elementtype=nacessary></td>
				<td class=title>����ֵ</td>
				<td class=input><input class="wid common" name=FeeValue id=FeeValue maxlength=10 elementtype=nacessary></td>
				<td class=title id="divTD1" style="display: ''"></td>
				<td class=input id="divTD2" style="display: ''"></td>
			</tr>
			<tr class=common>
				<td class=title>��ע</td>
				<td class=input colspan=5><textarea cols=76 rows=3 name=Remark id=Remark></textarea></td>
			</tr>
		</table>
	</div>
	
	<div>
		<input class=cssButton type=button value="����������Ϣ����" name="AddButton" onclick="addClick();">
		<input class=cssButton type=button value="����������Ϣ�޸�" name="ModifyButton" onclick="modifyClick();">
		<input class=cssButton type=button value="����������Ϣɾ��" name="DeleteButton" onclick="deleteClick();">
	</div>
	
	<div>
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=RiskCode id=RiskCode>
	<input type=hidden name=SerialNo id=SerialNo>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
