<%
/***************************************************************
 * <p>ProName��LLClaimCaseReviewInput.jsp</p>
 * <p>Title���������¼��</p>
 * <p>Description���������¼��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mCurrentDate = PubFun.getCurrentDate();
	String mGrpRegisterNo = request.getParameter("GrpRgtNo");
	String mMode = request.getParameter("Mode");	
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����	
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
	var mGrpRegisterNo = "<%=mGrpRegisterNo%>";	
	var mMode = "<%=mMode%>";
</script>
<html>
<head>
	<title>�������¼�����</title>
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
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimCaseReviewInput.js"></script>
	<%@include file="./LLClaimCaseReviewInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimCaseReviewSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAcceptInfo);">
			</td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divAcceptInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>���κ�</td>
				<td class=input><input class="wid readonly" name=GrpRgtNo readonly></td>
				<td class=title>Ͷ��������</td>				
				<td class= input><input class="wid readonly" name=AppntName readonly></td>
				<td class=title>������ת��</td>
				<td class=input><input class="wid readonly" name=PageNo readonly></td>			
			</tr>
			<tr class=common>
				<td class=title>����ȷ������</td>
				<td class=input><input class="wid readonly" name=ConfirmDate readonly></td>
				<td class=title>����ȷ����</td>
				<td class=input><input class="wid readonly" name=ConfirmOperator readonly></td>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=AcceptCom readonly><input class=codename name=AcceptComName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>������⸶�ܽ��</td>
				<td class=input><input class="wid readonly" name=ReviewMoney readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=CaseType readonly><input class=codename name=CaseTypeName readonly></td>
				<td class=title>������ʽ</td>
				<td class=input><input type=checkbox name=PayType onclick=''>�������</td>
			</tr>
		</table>
	</div>
	<input class=cssButton name=AllCal type=button value="ȫ������" onclick="CalPayAll();">
	<input class=cssButton name=PerCal value="��������" type=button onclick="calPay();">
	<input class=cssButton type=button value="δ����ͻ�" onclick="noAcceptInfo();">
	<!-- <input class=cssButton name=BatchPay type=button value="����ȡ������" onclick="batchNoPay();"> -->
	<input class=cssButton type=button value="��  ��" onclick="goToBack();">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerList);">
			</td>
			<td class=titleImg>�ͻ���Ϣ�б�&nbsp;&nbsp;<span id="BatchInfo" style="display: ''"></span></td>
		</tr>
	</table>
	
	<div id="divCustomerList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCustomerListGrid"></span>
				</td>
			</tr>
		</table>
		
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="firstPage(turnPage1,CustomerListGrid);">
			<input class=cssButton91 type=button value="��һҳ" onclick="previousPage(turnPage1,CustomerListGrid);">
			<input class=cssButton92 type=button value="��һҳ" onclick="nextPage(turnPage1,CustomerListGrid);">
			<input class=cssButton93 type=button value="β  ҳ" onclick="lastPage(turnPage1,CustomerListGrid);">			
		</center>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCustomerInfo);">
			</td>
			<td class=titleImg>�ͻ���Ϣ</td>
		</tr>
	</table>
				
	<div id="divCustomerInfo" class=maxbox1 style="display:''">
		<input class=cssButton name=PolicyQuery value="������ѯ" type=button onclick="showInsuredLCPol();">
		<input class=cssButton name=EdorQuery value="��ȫ��ѯ" type=button onclick="showInsuredLCEdor();">
		<input class=cssButton name=ClaimQuery value="�����ⰸ��ѯ" type=button onclick="showOldCase();">
		<input class=cssButton name=ScanQuery value="Ӱ�����ѯ" type=button onclick="queryEasyscan();">
		<input class=cssButton name=SecurityQuery value="�籣��Ϣ��ѯ" type=button onclick="querySecurity();">
		<input class=cssButton name=bnfMaintenance value="��������Ϣ" type=button onclick="benefitInfo();">
		<input class=cssButton name=bnfMaintenance value="�˻���ѯ" type=button onclick="queryAccInfo();">
		<input class=cssButton name=ListBlack value="����������" type=button onclick="blackList();"> 
		<input class=cssButton name=ReportRela value="��������" type=button onclick="reportRelate();">
		<input class=cssButton name=DeleteCase value="�������" type=button onclick="showSurvey();">
		<input class=cssButton name=stopLLClaimValue value="��ͣ/��������" type=button onclick="stopORstartCase();"> 
		<br>
		<!-- <input class=cssButton name=BPOCheckInfo value="BPOУ������ѯ" type=button onclick="BPOCheck();">
		<input class=cssButton name=QueryAllBill value="���ȫ���˵�" type=button onclick="reviewClmBills();">
		<input class=cssButton name=QuestionQuery value="�������Ϣ" type=button onclick="question();"> -->
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=BankName readonly></td>
				<td class=title>������������ʡ</td>
				<td class=input><input class="wid readonly" name=BankProvince readonly></td>
				<td class=title>��������������</td>
				<td class=input><input class="wid readonly" name=BankCity readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�˺�</td>
				<td class=input><input class="wid readonly" name=AccNo readonly></td>
				<td class=title>�˻���</td>
				<td class=input><input class="wid readonly" name=AccName readonly></td>
				<td class=title>�����ⰸ-ԭ������</td>
				<td class=input><input class="wid readonly" name=OldClmNo readonly></td>
			</tr>
		</table>
	<div id="" style="display: ''">
		<span id="spanPayInfoListGrid" ></span>
	</div>		
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAccidentList);">
			</td>
			<td class=titleImg>�¼���Ϣ�б�</td>
		</tr>
	</table>
	<div id="divAccidentList" style="display: ''">
		<span id="spanAccidentListGrid" ></span>
		<center>
		<input value="��  ҳ" class=cssButton90 type=button onclick="turnPage2.firstPage();">
		<input value="��һҳ" class=cssButton91 type=button onclick="turnPage2.previousPage();">
		<input value="��һҳ" class=cssButton92 type=button onclick="turnPage2.nextPage();">
		<input value="β  ҳ" class=cssButton93 type=button onclick="turnPage2.lastPage();">
		</center>
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAccidentInfo);">
			</td>
			<td class=titleImg>�¼���Ϣ</td>
		</tr>
	</table>
	<div id="divAccidentInfo" class=maxbox1 style="display:''">
		<input class=cssButton name=AccidentAdd value="�����¼�" type=button onclick="addAccident();">
		<input class=cssButton name=AccidentModify value="�޸��¼�" type=button onclick="modifyAccident();">
		<input class=cssButton name=AccidentDelete value="ɾ���¼�" type=button onclick="deleteAccident();">
		<input class=cssButton name=AccidentClose value="�ر��¼�" type=button onclick="closeAccident();">
		<input class=cssButton name=AccidentOpen value="�����¼�" type=button onclick="openAccident();">
		<!-- <input class=cssButton name=CloseCase value="�ϲ��¼�" type=button onclick="combineAccident();"> -->
		<input class=cssButton name=CreateNote1 value="��ҽ���˵�" type=button onclick="noMedicalBill();">
		<input class=cssButton name=CreateNote1 value="ҽ���˵�" type=button onclick="MedicalBill();">
					
		<table class=common>
			<tr class=common>
				<td class=title>����ԭ��</td>				
				<td class=input><input class=codeno name=AccReason style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('accreason',[this,AccReasonName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('accreason',[this,AccReasonName],[0,1],null,null,null,1);" maxlength=6 readonly><input class=codename name=AccReasonName elementtype=nacessary readonly></td>
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short"  verify="��������|notnull&DATE verifyorder="1""   name=AccDate elementtype=nacessary onClick="laydate({elem: '#AccDate'});" id="AccDate"><span class="icon"><a onClick="laydate({elem: '#AccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>������</td>
				<td class=input><input class="wid common" name=ClaimPay onchange="checkMoney(fm.ClaimPay);" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>����ҽԺ</td>				
				<td class=input><input class=codeno name=HospitalCode readonly><input class=codename name=HospitalName onkeydown="QueryOnKeyDown(this)" elementtype=nacessary></td>
				<td class=title>��Ҫ���(ICD10)</td>
				<td class=input><input class=codeno name=AccResult1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('', [this,AccResult1Name],[0,1],null,null,null,1);" onkeyup="showCodeListKey('', [this,AccResult1Name],[0,1],null,null,null,1);" maxlength=10 readonly><input class=codename name='AccResult1Name' onkeydown="QueryOnKeyDown(this)" elementtype=nacessary maxlength=300></td>
				<td class=title>�������(ICD10)</td>
				<td class=input><input class=codeno name=AccResult2 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('diseasecode',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode',1);" onkeyup="showCodeListKey('accresult', [this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode',1);" maxlength=10 readonly><input class=codename name=AccResult2Name onkeydown="QueryOnKeyDown(this)" maxlength=300></td>
			</tr>
			<tr class=common>
				<td class=title>�˲�����</td>
				<td class=input><input class="coolDatePicker"  dateFormat="short" verify="�˲�����|notnull&DATE verifyorder="1""    name=DeformityDate onClick="laydate({elem: '#DeformityDate'});" id="DeformityDate"><span class="icon"><a onClick="laydate({elem: '#DeformityDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>�������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short"  verify="�������|notnull&DATE verifyorder="1""   name=DeathDate  onClick="laydate({elem: '#DeathDate'});" id="DeathDate"><span class="icon"><a onClick="laydate({elem: '#DeathDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>���ƽ��</td>				
				<td class=input><input class=codeno name=TreatResult style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('treatresult',[this,TreatResultName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('closereason',[this,TreatResultName],[0,1],null,null,null,1);"><input class=codename name=TreatResultName elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>�¼���Դ</td>
				<td class=input><input type=hidden name=CaseSource style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('casesource',[this,CaseSourceName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('casesource',[this,CaseSourceName],[0,1],null,null,null,1);"><input class="wid readonly" name=CaseSourceName readonly elementtype=nacessary></td>
				<td class=title>���ݻ�д�¼��� </td>
				<td class=input><input class="wid common" name=LRCaseNo ></td>
				<td class=title></td>				
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>���յص�</td>
				<td class=input colspan=3>
					<!-- <input class=codeno name=ProvinceName style="width:60px" readonly ondblclick="return showCodeList('province',[this, Province],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, Province],[1,0],null,null,null,'1',180);"><input type=hidden name=Province readonly maxlength=30>ʡ
					<input class=codeno name=CityName style="width:60px" readonly ondblclick="return showCodeList('city',[this, City],[1,0],null,fm.Province.value,'provincecode',1);" onkeyup="return showCodeListKey('city',[this, City],[1,0],null,fm.Province.value,'provincecode',1);"><input type=hidden name=City readonly maxlength=30>��
					<input class=codeno name=CountyName style="width:60px" readonly ondblclick="return showCodeList('county',[this, County],[1,0],null,fm.City.value,'citycode',1);" onkeyup="return showCodeListKey('county',[this, County],[1,0],null,fm.City.value,'citycode',1);"><input type=hidden name=County readonly maxlength=30>��/��						 -->
					
					<input class=common name=AccidentPlace style="width:300px" elementtype=nacessary maxlength=200 >
				</td>
				<td class=title></td>				
				<td class=input></td>
				<td class=title></td>				
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��������<font color=red><b>*</b></font></td>
				<td class=input colspan=3>
					<input type=checkbox value="02" name=ClaimType>���
					<input type=checkbox value="04" name=ClaimType>�ش󼲲�
					<input type=checkbox value="01" name=ClaimType>�˲�/ȫ��
					<input type=checkbox value="06" name=ClaimType>������
					<input type=checkbox value="00" name=ClaimType>ҽ��
					<input type=checkbox value="0A" name=ClaimType>����
					<input type=checkbox value="05" name=ClaimType>ʧ��
				</td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�¹�����</td>
				<td class=input colspan=5><textarea class=common name=AccidentRemarks id=AccidentRemarks cols="50" rows="2" maxlength=2000></textarea></td>
			</tr>
			<tr class=common>
				<td class=title>��ע</td>
				<td class=input colspan=5><textarea class=common name=Remarks id=Remarks cols="50" rows="2" maxlength=2000></textarea></td>
			</tr>
		</table>
	</div>	

	<div id=divCloseAccident style="display:none">
		<table class=common>
			<tr>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title><font color="red" size="2">�ر��¼�ԭ��</font></td>
				<td class=input><input class=codeno name=CloseReasonDesc style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('closereason',[this,CloseReasonDescName],[0,1]);" onkeyup="return showCodeListKey('closereason',[this,CloseReasonDescName],[0,1]);"><input class=codename name=CloseReasonDescName readonly></td>
				<td class=title><font color="red" size="2">�ر��¼���ע</font></td>
				<td class=input colspan="3"><Input name=CloseRemarkDesc style="background-color: #F7F7F7;border: 1px #799AE1 solid;height: 20px;width: 100%;behavior: url(../common/behaviors/filterInput.htc);"></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOnEventDutyList);">
			</td>
			<td class=titleImg>�ѹ����¼��ĸ���������Ϣ</td>
		</tr>
	</table>
	<div id="divOnEventDutyList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOnEventDutyListGrid"></span>
				</td>
			</tr>
		</table>
		<input class=cssButton name=AssociateOff value="ȡ������"  type=button onclick="offAssociate();">	
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOffEventDutyList);">
			</td>
			<td class=titleImg>�������¼��ĸ���������Ϣ</td>
		</tr>
	</table>
	<div id="divOffEventDutyList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOffEventDutyListGrid"></span>
				</td>
			</tr>
		</table>
		<input class=cssButton name=AssociateOn value="��  ��"  type=button onclick="onAssociate();">
	</div>
	<hr size="2" color="#D7E1F6">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLLCasePay);">
			</td>
			<td class=titleImg>�¼��⸶��Ϣ</td>
		</tr>
	</table>
	<div id="divLLCasePay" style="display: ''">
		<table class=common>
			<tr class=common>
				<td colSpan=1><span id="spanCaseDutyPayGrid" ></span></td>
			</tr>
		</table>
	</div>

	<div id=divPayModify style="display:none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divBaseUnit1);">
				</td>
				<td class=titleImg>�����⸶����</td>
			</tr>
		</table>
		
		<div id="divBaseUnit1" class=maxbox1 style="display:''">
			<table class=common>
				<tr class=common>
					<td class=title>�⸶����</td>
					<td class=input><input class=codeno name=GiveType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('givetype',[this,GiveTypeName],[0,1],null,conditionCode1,'1',1);" onkeyup="showCodeListKeyEx('givetype',[this,GiveTypeName],[0,1],null,conditionCode1,'1',1);"><input class=codename name=GiveTypeName elementtype=nacessary></td>					
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>	      			
				</tr>												
				<tr class=common id=AdjustInfo style="display:''">
					<td class=title>����ԭ��</td>
					<td class=input><input class=codeno name=AdjReason style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showSelectMod(AdjReason,AdjReasonName);" ><input class=codename name=AdjReasonName readonly></td>				
					<!-- 
					<td class=title>����ԭ��</td>
					<td class=input><input class=codeno name=AdjReason ondblclick="return showCodeList('adjreason',[this,AdjReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('adjreason',[this,AdjReasonName],[0,1],null,null,null,1);"><input class=codename name=AdjReasonName readonly></td>
					-->
					<td class=title>�������</td>
					<td class=input><input class="wid common" name=RealPay></td>
					<td class=title></td>
					<td class=input></td>						      			
				</tr>
				<tr class=common id=NoPayInfo style="display:none">
					<td class=title>�ܸ�ԭ��</td>
					<td class=input><input class=codeno name=NoGiveReason style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('closereason',[this,NoGiveReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('closereason',[this,NoGiveReasonName],[0,1],null,null,null,1);"><input class=codename name=NoGiveReasonName readonly></td>
					<td class=title>���ⱸע</td>
					<td class=input><input class="wid common" name=SpecialRemark></td>
					<td class=title></td>
					<td class=input></td>					
				</tr>
				<tr class=common>
					<td class=title>�⸶����˵��</td>
					<td class=input colspan="5"><textarea name=AdjRemark id=AdjRemark cols="60" rows="3" class=common></textarea></td>
				</tr>
				</table>
		 		<input class=cssButton name=PayInfoSave value="�����޸�ȷ��" type=button onclick="savePayInfo();" >
			</div>
	</div>
		
	<div id=divCheckConclusion style="display:none">
		
		<table>
			<tr>
				<td class=common id=divCheck style="display:none">
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divCheck);">
				</td>
				<td class=titleImg>���˽���</td>
			</tr>
		</table>	
        <div class=maxbox1 id=divCheck>
		<table class=common>
			<tr class=common>
				<td class=title>���˽���</td>
				<td class=input><input class=codeno name=CheckConclusion readonly><input class=codename name=CheckConclusionName readonly></td>
				<td class=title></td>
		   		<td class=input></td>
		   		<td class=title></td>
		   		<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�������<font color="#FF0000">*</font></td>
				<td class=input colspan="5"><textarea name=CheckAdvice id=CheckAdvice cols="60" rows="3" class=common maxlength=1000 readonly></textarea></td>
			</tr>
		</table>
		</div>
	</div>	
		
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAuditConclusion);">
			</td>
			<td class=titleImg>��˽���</td>
		</tr>
	</table>
			
	<div id=divAuditConclusion class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>��˽���</td>
				<td class=input><input class=codeno name=Conclusion style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('rgtconclusion',[this,ConclusionName],[0,1],null,conditionCode,'1',1);" onkeyup="return showCodeListKey('rgtconclusion',[this,ConclusionName],[0,1],null,conditionCode,'1',1);"><input class=codename name=ConclusionName readonly></td>
				<td class=title></td>
		   		<td class=input></td>
		   		<td class=title></td>
		   		<td class=input></td>
			</tr>
			<tr class=common id=ReviewAdvice style="display:''">
				<td class=title>������<font color="#FF0000">*</font></td>
				<td class=input colspan="5"><textarea name=ReviewAdvice id=ReviewAdvice cols="60" rows="3" class=common maxlength=1000></textarea></td>
			</tr>
			<tr class=common id=AgainReviewAdvice style="display:none">
				<td class=title>�������</td>
				<td class=input colspan="5"><textarea name=AgainReviewAdvice id=AgainReviewAdvice cols="60" rows="3" class=common maxlength=1000></textarea>
			</tr>
		</table>
	</div>
	
	<input class=cssButton name=ConfirmCase value="���ȷ��" type=button onclick="caseConfirm();">
	<!-- <input class=cssButton name=BatchConfirmCase value="�����ύ" type=button onclick="batchCaseConfirm();"> -->
	<font color="red">***��ʾ���������ύ������ʹ�ù����������˽���Ϊ��5-�����������6-��ͣ���İ������������ύ��</font> 
			
 	<input type=hidden name=SelNo>				<!--�б����-->
 	<input type=hidden name=PageIndex>		<!--ҳ��-->
 	<input type=hidden name=PolNo>				<!--���ֺ�-->
 	<input type=hidden name=DutyCode>			<!--���α���-->
 	<input type=hidden name=GetDutyCode>	<!--���������-->
	<input type=hidden name=GetDutyKind>	<!--��������-->
 	<input type=hidden name=CaseNo>				<!--�¼���-->
	<input type=hidden name=RegisterNo>		<!--���˰�����-->
	<input type=hidden name=CustomerNo>		<!--�ͻ���-->
	<input type=hidden name=Operate>			<!--��������-->
	<input type=hidden name=AppntType>		<!--Ͷ��������-->
	<input type=hidden name=MissionID>		<!--����ID-->
	<input type=hidden name=SubMissionID>	<!--������ID-->
	<input type=hidden name=ActivityID>		<!--������ID-->
	<input type=hidden name=AppntNo>			<!--Ͷ���˱���-->	
	<input type=hidden name=UWFlag>			<!--���˱�ʶ-->
	<input type=hidden name=PublicFlag>			<!--�Ƿ�ʹ�ù��������ʶ-->
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>