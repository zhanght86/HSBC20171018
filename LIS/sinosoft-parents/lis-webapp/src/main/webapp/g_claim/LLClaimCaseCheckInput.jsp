<%
/***************************************************************
 * <p>ProName��LLClaimCaseCheckInput.jsp</p>
 * <p>Title����������¼��</p>
 * <p>Description����������¼��</p>
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
	<script src="./LLClaimCaseCheckInput.js"></script>
	<%@include file="./LLClaimCaseCheckInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimCaseCheckSave.jsp" target=fraSubmit>
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
				<td class=input><input class="wid readonly" name=GrpRgtNo id=GrpRgtNo readonly></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid readonly" name=AppntName id=AppntName readonly></td>
				<td class=title>������ת��</td>
				<td class=input><input class="wid readonly" name=PageNo id=PageNo readonly></td>			
			</tr>
			<tr class=common>
				<td class=title>����ȷ������</td>
				<td class=input><input class="wid readonly" name=ConfirmDate readonly></td>
				<td class=title>����ȷ����</td>
				<td class=input><input class="wid readonly" name=ConfirmOperator></td>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=AcceptCom><input class=codename name=AcceptComName></td>
			</tr>
			<tr class=common>
				<td class=title>������⸶�ܽ��</td>
				<td class=input><input class="wid readonly" name=SumRealPay readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=CaseType readonly><input class=codename name=CaseTypeName readonly></td>
				<td class=title>������ʽ</td>
				<td class=input><input type=checkbox name=PayType>�������</td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button value="δ����ͻ�" onclick="showNoAccepted();">
	
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
			<input class=cssButton type=button value="��  ҳ" onclick="firstPage(turnPage1,CustomerListGrid);">
			<input class=cssButton type=button value="��һҳ" onclick="previousPage(turnPage1,CustomerListGrid);">
			<input class=cssButton type=button value="��һҳ" onclick="nextPage(turnPage1,CustomerListGrid);">
			<input class=cssButton type=button value="β  ҳ" onclick="lastPage(turnPage1,CustomerListGrid);">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerInfo);">
			</td>
			<td class=titleImg>�ͻ���Ϣ</td>
		</tr>
	</table>	

				
	<div id="divCustomerInfo" style="display:''">
		<input class=cssButton name=QueryCont3 value="������ѯ" type=button onclick="showInsuredLCPol();">
		<input class=cssButton name=QueryCont3 value="��ȫ��ѯ" type=button onclick="showInsuredLCEdor();">
		<input class=cssButton name=QueryCont3 value="�����ⰸ��ѯ" type=button onclick="showOldCase();">
		<input class=cssButton name=bnfMaintenance value="��������Ϣ" type=button onclick="benefitInfo();">		
		<input class=cssButton name=ViewScan value="Ӱ�����ѯ" type=button onclick="queryEasyscan();">
		<input class=cssButton name=BPOCheckInfo value="BPOУ������ѯ" type=button onclick="BPOCheck();">
		<input class=cssButton name=QuesRecord value="�籣��Ϣ��ѯ" type=button onclick="querySheBao();">
		<input class=cssButton name=DeleteCase value="�������" type=button onclick="showSurvey();">
		<input class=cssButton name=QuestionQuery value="�������Ϣ" type=button onclick="question();">
		<input class=cssButton name=BlackListInfo value="����������" type=button onclick="blackList();">
		<input class=cssButton name=ShowReportInfo value="�����鿴" type=button onclick="showReport();">
		<input class=cssButton name=bnfMaintenance value="�˻���ѯ" type=button onclick="queryAccInfo();">
		<input class=cssButton name=QueryAllBill value="���ȫ���˵�" type=button onclick="reviewClmBills();">	
				
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=BankName></td>
				<td class=title>������������ʡ</td>
				<td class=input><input class="wid readonly" name=BankProvince></td>
				<td class=title>��������������</td>
				<td class=input><input class="wid readonly" name=BankCity></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAccidentList);">
			</td>
			<td class=titleImg>�¼���Ϣ�б�</td>
		</tr>
	</table>
	
	<div id="divAccidentList" style="display: ''">
		<span id="spanAccidentListGrid" ></span>
		<center>
		<input value="��  ҳ" class=cssButton type=button onclick="turnPage2.firstPage();">
		<input value="��һҳ" class=cssButton type=button onclick="turnPage2.previousPage();">
		<input value="��һҳ" class=cssButton type=button onclick="turnPage2.nextPage();">
		<input value="β  ҳ" class=cssButton type=button onclick="turnPage2.lastPage();">
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
	<div id="divAccidentInfo" style="display:''">
		<input class=cssButton name=CreateNote1 value="��ҽ���˵�" type=button onclick="noMedicalBill();">
		<input class=cssButton name=CreateNote1 value="ҽ���˵�" type=button onclick="MedicalBill();">
					
		<table class=common>
			<tr class=common>
				<td class=title>����ԭ��</td>
				<td class=input><input class=codeno name=AccReason readonly><input class=codename name=AccReasonName elementtype=nacessary readonly></td>
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=AccDate elementtype=nacessary readonly></td> 
				<td class=title>������</td>
				<td class=input><input class="wid common" name=ClaimPay elementtype=nacessary readonly></td>
			</tr>
			<tr class= common>
				<td class=title>����ҽԺ</td>
				<td class=input><input class=codeno name=HospitalCode readonly><input class=codename name=HospitalName elementtype=nacessary readonly></td>
				<td class=title>��Ҫ���(ICD10)</td>
				<td class=input><input class=codeno name=AccResult1 readonly><input class=codename name=AccResult1Name elementtype=nacessary readonly></td>
				<td class=title>�������(ICD10)</td>
				<td class=input><input class=codeno name=AccResult2 readonly><input class=codename name=AccResult2Name readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�˲�����</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=DeformityDate></td> 
				<td class=title>�������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=DeathDate></td> 
				<td class=title>���ƽ��</td>
				<td class=input><input class=codeno name=TreatResult readonly><input class=codename name=TreatResultName value='Ȭ��' elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>�¼���Դ</td>
				<td class=input><input class=codeno name=CaseSource readonly><input class=codename name=CaseSourceName elementtype=nacessary readonly></td>
				<td class=title>���ݻ�д�¼��� </td>
				<td class=input><input class="wid common" name=LRCaseNo ></td>
				<td class=title></td>
				<td class=input></td>				
			</tr>
			<tr class=common>
				<td class=title>���յص�</td>
				<td class=input colspan=3>
					<!-- <input class=codeno name=ProvinceName style="width:60px"><input type=hidden name=Province readonly>&nbsp;&nbsp;ʡ&nbsp;&nbsp;
					<input class=codeno name=CityName style="width:60px"><input type=hidden name=City readonly>&nbsp;&nbsp;��&nbsp;&nbsp;
					<input class=codeno name=CountyName style="width:60px"><input type=hidden name=County readonly>&nbsp;&nbsp;��/��&nbsp;&nbsp; -->
					<input class=common name=AccidentPlace style="width:280px">
				</td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��������<font color=red><b>*</b></font></td>
				<td class=input colspan=3>
					<input type=checkbox value="02" name=ClaimType>���
					<input type=checkbox value="05" name=ClaimType>�ش󼲲�
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
				<td class=input colspan=5><textarea class="common" name=AccidentRemarks cols="50" rows="2" maxlength=200></textarea></td>
			</tr>
			<tr class=common>
				<td class=title>��ע</td>
				<td class=input colspan=5><textarea class="common" name=Remarks cols="50" rows="2" maxlength=200></textarea></td>
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
				<td class=title>�ر��¼�ԭ��</td>
				<td class=input><input class=codeno name=CloseReasonDesc readonly><input class=codename name=CloseReasonDescName readonly></td>
				<td class=title>�ر��¼���ע</td>
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
	</div>
			
			
	<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLLCasePay);"></ td>
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
				<td class=common><img src="../common/images/butCollapse.gif" style="cursor:hand;" OnClick="showPage(this,divPayModify);"></ td>
				<td class=titleImg>�����⸶����</td>
			</tr>
		</table>

		<div id="divBaseUnit1" style="display:''">
			<table class=common>
				<tr class=common>
					<td class=title>�⸶����</td>
					<td class=input><input class=codeno name=GiveType ondblclick="showCodeList('givetype',[this,GiveTypeName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('givetype',[this,GiveTypeName],[0,1],null,null,null,1);"><input class=codename name=GiveTypeName elementtype=nacessary></td>					
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>	      			
				</tr>												
				<tr class=common id=AdjustInfo style="display:''">
					<td class=title>����ԭ��</td>
					<td class=input><input class=codeno name=AdjReason ondblclick="return showCodeList('adjreason',[this,AdjReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('adjreason',[this,AdjReasonName],[0,1],null,null,null,1);"><input class=codename name=AdjReasonName readonly></td>
					<td class=title>�������</td>
					<td class=input><input class="wid common" name=RealPay></td>
					<td class=title></td>
					<td class=input></td>						      			
				</tr>
				<tr class=common id=NoPayInfo style="display:none">
					<td class=title>�ܸ�ԭ��</td>
					<td class=input><input class=codeno name=NoGiveReason ondblclick="return showCodeList('closereason',[this,NoGiveReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('closereason',[this,NoGiveReasonName],[0,1],null,null,null,1);"><input class=codename name=NoGiveReasonName readonly></td>
					<td class=title>���ⱸע</td>
					<td class=input><input class=common name=SpecialRemark></td>
					<td class=title></td>
					<td class=input></td>					
				</tr>
				<tr class=common>
					<td class=title>�⸶����˵��</td>
					<td class=input colspan="5"><textarea name=AdjRemark cols="60" rows="3" class=common></textarea></td>
				</tr>
				</table>		 		
			</div>
	</div>
			
	<table>
		<tr>
			<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAuditConclusion);"></ td>
			<td class=titleImg>��˽���</td>
		</tr>
	</table>	
			
	<div id=divAuditConclusion style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>��˽���</td>
				<td class=input><input class=codeno name=Conclusion ondblclick="return showCodeList('rewconclusion',[this,ConclusionName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('rewconclusion',[this,ConclusionName],[0,1],null,null,null,1);" readonly><input class=codename name=ConclusionName readonly></td>
				<td class=title></td>
		   		<td class=input></td>
		   		<td class=title></td>
		   		<td class=input></td>
			</tr>
			<tr class=common id=ReviewAdvice style="display:''">
			<td class=title>������<font color="#FF0000">*</font></td>
			<td class=input colspan="5"><textarea name=ReviewAdvice cols="60" rows="3" class=common maxlength=1000 readonly></textarea></td>
			</tr>
			<tr class=common id=AgainReviewAdvice style="display:none">
			<td class=title>�������</td>
			<td class=input colspan="5"><textarea name=AgainReviewAdvice cols="60" rows="3" class=common maxlength=1000 readonly></textarea>
			</tr>
		</table>
	</div>
	
	
	<div id=divApproveConclusion style="display:none">
		<table >
			<tr>
				<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divApprove);"></ td>
				<td class=titleImg>��������</td>				
			</tr>
		</table>			
		<table class=common id=divApprove>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=ApproveConclusion readonly><input class=codename name=ApproveConclusionName readonly></td>
				<td class=title></td>
		   		<td class=input></td>
		   		<td class=title></td>
		   		<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�������<font color="#FF0000">*</font></td>
				<td class=input colspan="5"><textarea name=ApproveAdvice cols="60" rows="3" class=common maxlength=1000 readonly></textarea></td>
			</tr>
		</table>
	</div>	
	
	<table>
		<tr>
			<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divCehckConclusion);"></ td>
			<td class=titleImg>���˽���</td>
		</tr>
	</table>
			
	<div id=divCehckConclusion style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>���˽���<font color="#FF0000">*</font></td>
				<td class=input><input class=codeno name=ChkConclusion readonly ondblclick="return showCodeList('chkconclusion',[this,ChkConclusionName],[0,1],null,conditionCode,'1',1);" onkeyup="return showCodeListKey('chkconclusion',[this,ChkConclusionName],[0,1],null,conditionCode,'1',1);"><input class=codename name=ChkConclusionName readonly></td>
				<td class=title></td>	   		
	   		<td class=input><input type=hidden class=codeno name=ChkAdvice readonly ondblclick="return showCodeList('chkadvice',[this,ChkAdviceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('chkadvice',[this,ChkAdviceName],[0,1],null,null,null,1);"><input type=hidden class=codename name=ChkAdviceName readonly></td>
	   		<td class=title></td>
	   		<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�������<font color="#FF0000">*</font></td>
				<td class=input colspan="5"><textarea name=CheckAdvice cols="60" rows="3" class=common maxlength=2000></textarea></td>
			</tr>
		</table>
	</div>
	
	<input class=cssButton name=goBack value="��  ��" type=button onclick="goToBack();">
<!-- 	<input class=cssButton name=UWTraceShow value="���������鿴" type=button onclick="showUWTrace();"> -->
	<input class=cssButton name=CaseConfirm value="����ȷ��" type=button onclick="checkConfirm();">
	<input class=cssButton name=BatchCaseConfirm value="��������ȷ��" type=button onclick="batchCheckConfirm();">
	<font color="red" size="2">***��ʾ��ֻ�ܶ��¡�0-�����򲿷ָ���������5-�������ˡ����۵İ���������������ȷ�ϣ�</font> 
			
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
	<input type=hidden name=AppntNo>			<!--Ͷ���˱���-->	
	<input type=hidden name=UWFlag>			<!--Ͷ���˱���-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>