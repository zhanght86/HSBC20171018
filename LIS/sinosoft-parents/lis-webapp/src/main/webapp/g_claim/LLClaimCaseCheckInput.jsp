<%
/***************************************************************
 * <p>ProName：LLClaimCaseCheckInput.jsp</p>
 * <p>Title：案件复核录入</p>
 * <p>Description：案件复核录入</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
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
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
	var mGrpRegisterNo = "<%=mGrpRegisterNo%>";
	var mMode = "<%=mMode%>";
</script>
<html>
<head>
	<title>案件审核录入界面</title>
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
			<td class=titleImg>受理信息</td>
		</tr>
	</table>
	<div id="divAcceptInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>批次号</td>
				<td class=input><input class="wid readonly" name=GrpRgtNo id=GrpRgtNo readonly></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid readonly" name=AppntName id=AppntName readonly></td>
				<td class=title>交接流转号</td>
				<td class=input><input class="wid readonly" name=PageNo id=PageNo readonly></td>			
			</tr>
			<tr class=common>
				<td class=title>受理确认日期</td>
				<td class=input><input class="wid readonly" name=ConfirmDate readonly></td>
				<td class=title>受理确认人</td>
				<td class=input><input class="wid readonly" name=ConfirmOperator></td>
				<td class=title>受理机构</td>
				<td class=input><input class=codeno name=AcceptCom><input class=codename name=AcceptComName></td>
			</tr>
			<tr class=common>
				<td class=title>待审核赔付总金额</td>
				<td class=input><input class="wid readonly" name=SumRealPay readonly></td>
				<td class=title>案件类型</td>
				<td class=input><input class=codeno name=CaseType readonly><input class=codename name=CaseTypeName readonly></td>
				<td class=title>给付方式</td>
				<td class=input><input type=checkbox name=PayType>团体给付</td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button value="未受理客户" onclick="showNoAccepted();">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerList);">
			</td>
			<td class=titleImg>客户信息列表&nbsp;&nbsp;<span id="BatchInfo" style="display: ''"></span></td>
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
			<input class=cssButton type=button value="首  页" onclick="firstPage(turnPage1,CustomerListGrid);">
			<input class=cssButton type=button value="上一页" onclick="previousPage(turnPage1,CustomerListGrid);">
			<input class=cssButton type=button value="下一页" onclick="nextPage(turnPage1,CustomerListGrid);">
			<input class=cssButton type=button value="尾  页" onclick="lastPage(turnPage1,CustomerListGrid);">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerInfo);">
			</td>
			<td class=titleImg>客户信息</td>
		</tr>
	</table>	

				
	<div id="divCustomerInfo" style="display:''">
		<input class=cssButton name=QueryCont3 value="保单查询" type=button onclick="showInsuredLCPol();">
		<input class=cssButton name=QueryCont3 value="保全查询" type=button onclick="showInsuredLCEdor();">
		<input class=cssButton name=QueryCont3 value="既往赔案查询" type=button onclick="showOldCase();">
		<input class=cssButton name=bnfMaintenance value="受益人信息" type=button onclick="benefitInfo();">		
		<input class=cssButton name=ViewScan value="影像件查询" type=button onclick="queryEasyscan();">
		<input class=cssButton name=BPOCheckInfo value="BPO校验结果查询" type=button onclick="BPOCheck();">
		<input class=cssButton name=QuesRecord value="社保信息查询" type=button onclick="querySheBao();">
		<input class=cssButton name=DeleteCase value="调查管理" type=button onclick="showSurvey();">
		<input class=cssButton name=QuestionQuery value="问题件信息" type=button onclick="question();">
		<input class=cssButton name=BlackListInfo value="黑名单管理" type=button onclick="blackList();">
		<input class=cssButton name=ShowReportInfo value="报案查看" type=button onclick="showReport();">
		<input class=cssButton name=bnfMaintenance value="账户查询" type=button onclick="queryAccInfo();">
		<input class=cssButton name=QueryAllBill value="审核全部账单" type=button onclick="reviewClmBills();">	
				
		<table class=common>
			<tr class=common>
				<td class=title>开户银行</td>
				<td class=input><input class="wid readonly" name=BankName></td>
				<td class=title>开户银行所在省</td>
				<td class=input><input class="wid readonly" name=BankProvince></td>
				<td class=title>开户银行所在市</td>
				<td class=input><input class="wid readonly" name=BankCity></td>
			</tr>
			<tr class=common>
				<td class=title>账号</td>
				<td class=input><input class="wid readonly" name=AccNo readonly></td>
				<td class=title>账户名</td>
				<td class=input><input class="wid readonly" name=AccName readonly></td>
				<td class=title>特殊赔案-原案件号</td>
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
			<td class=titleImg>事件信息列表</td>
		</tr>
	</table>
	
	<div id="divAccidentList" style="display: ''">
		<span id="spanAccidentListGrid" ></span>
		<center>
		<input value="首  页" class=cssButton type=button onclick="turnPage2.firstPage();">
		<input value="上一页" class=cssButton type=button onclick="turnPage2.previousPage();">
		<input value="下一页" class=cssButton type=button onclick="turnPage2.nextPage();">
		<input value="尾  页" class=cssButton type=button onclick="turnPage2.lastPage();">
		</center>
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAccidentInfo);">
			</td>
			<td class=titleImg>事件信息</td>
		</tr>
	</table>
	<div id="divAccidentInfo" style="display:''">
		<input class=cssButton name=CreateNote1 value="非医疗账单" type=button onclick="noMedicalBill();">
		<input class=cssButton name=CreateNote1 value="医疗账单" type=button onclick="MedicalBill();">
					
		<table class=common>
			<tr class=common>
				<td class=title>出险原因</td>
				<td class=input><input class=codeno name=AccReason readonly><input class=codename name=AccReasonName elementtype=nacessary readonly></td>
				<td class=title>出险日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=AccDate elementtype=nacessary readonly></td> 
				<td class=title>索赔金额</td>
				<td class=input><input class="wid common" name=ClaimPay elementtype=nacessary readonly></td>
			</tr>
			<tr class= common>
				<td class=title>就诊医院</td>
				<td class=input><input class=codeno name=HospitalCode readonly><input class=codename name=HospitalName elementtype=nacessary readonly></td>
				<td class=title>主要诊断(ICD10)</td>
				<td class=input><input class=codeno name=AccResult1 readonly><input class=codename name=AccResult1Name elementtype=nacessary readonly></td>
				<td class=title>诊断详情(ICD10)</td>
				<td class=input><input class=codeno name=AccResult2 readonly><input class=codename name=AccResult2Name readonly></td>
			</tr>
			<tr class=common>
				<td class=title>伤残日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=DeformityDate></td> 
				<td class=title>身故日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=DeathDate></td> 
				<td class=title>治疗结果</td>
				<td class=input><input class=codeno name=TreatResult readonly><input class=codename name=TreatResultName value='痊愈' elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>事件来源</td>
				<td class=input><input class=codeno name=CaseSource readonly><input class=codename name=CaseSourceName elementtype=nacessary readonly></td>
				<td class=title>乐容回写事件号 </td>
				<td class=input><input class="wid common" name=LRCaseNo ></td>
				<td class=title></td>
				<td class=input></td>				
			</tr>
			<tr class=common>
				<td class=title>出险地点</td>
				<td class=input colspan=3>
					<!-- <input class=codeno name=ProvinceName style="width:60px"><input type=hidden name=Province readonly>&nbsp;&nbsp;省&nbsp;&nbsp;
					<input class=codeno name=CityName style="width:60px"><input type=hidden name=City readonly>&nbsp;&nbsp;市&nbsp;&nbsp;
					<input class=codeno name=CountyName style="width:60px"><input type=hidden name=County readonly>&nbsp;&nbsp;区/县&nbsp;&nbsp; -->
					<input class=common name=AccidentPlace style="width:280px">
				</td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>出险类型<font color=red><b>*</b></font></td>
				<td class=input colspan=3>
					<input type=checkbox value="02" name=ClaimType>身故
					<input type=checkbox value="05" name=ClaimType>重大疾病
					<input type=checkbox value="01" name=ClaimType>伤残/全残
					<input type=checkbox value="06" name=ClaimType>烧烫伤
					<input type=checkbox value="00" name=ClaimType>医疗
					<input type=checkbox value="0A" name=ClaimType>津贴
					<input type=checkbox value="05" name=ClaimType>失能									
				</td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>事故描述</td>
				<td class=input colspan=5><textarea class="common" name=AccidentRemarks cols="50" rows="2" maxlength=200></textarea></td>
			</tr>
			<tr class=common>
				<td class=title>备注</td>
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
				<td class=title>关闭事件原因</td>
				<td class=input><input class=codeno name=CloseReasonDesc readonly><input class=codename name=CloseReasonDescName readonly></td>
				<td class=title>关闭事件备注</td>
				<td class=input colspan="3"><Input name=CloseRemarkDesc style="background-color: #F7F7F7;border: 1px #799AE1 solid;height: 20px;width: 100%;behavior: url(../common/behaviors/filterInput.htc);"></td>
			</tr>
		</table>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOnEventDutyList);">
			</td>
			<td class=titleImg>已关联事件的给付责任信息</td>
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
			<td class=titleImg>待关联事件的给付责任信息</td>
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
		<td class=titleImg>事件赔付信息</td>
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
				<td class=titleImg>保项赔付结论</td>
			</tr>
		</table>

		<div id="divBaseUnit1" style="display:''">
			<table class=common>
				<tr class=common>
					<td class=title>赔付结论</td>
					<td class=input><input class=codeno name=GiveType ondblclick="showCodeList('givetype',[this,GiveTypeName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('givetype',[this,GiveTypeName],[0,1],null,null,null,1);"><input class=codename name=GiveTypeName elementtype=nacessary></td>					
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>	      			
				</tr>												
				<tr class=common id=AdjustInfo style="display:''">
					<td class=title>调整原因</td>
					<td class=input><input class=codeno name=AdjReason ondblclick="return showCodeList('adjreason',[this,AdjReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('adjreason',[this,AdjReasonName],[0,1],null,null,null,1);"><input class=codename name=AdjReasonName readonly></td>
					<td class=title>调整金额</td>
					<td class=input><input class="wid common" name=RealPay></td>
					<td class=title></td>
					<td class=input></td>						      			
				</tr>
				<tr class=common id=NoPayInfo style="display:none">
					<td class=title>拒付原因</td>
					<td class=input><input class=codeno name=NoGiveReason ondblclick="return showCodeList('closereason',[this,NoGiveReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('closereason',[this,NoGiveReasonName],[0,1],null,null,null,1);"><input class=codename name=NoGiveReasonName readonly></td>
					<td class=title>特殊备注</td>
					<td class=input><input class=common name=SpecialRemark></td>
					<td class=title></td>
					<td class=input></td>					
				</tr>
				<tr class=common>
					<td class=title>赔付结论说明</td>
					<td class=input colspan="5"><textarea name=AdjRemark cols="60" rows="3" class=common></textarea></td>
				</tr>
				</table>		 		
			</div>
	</div>
			
	<table>
		<tr>
			<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAuditConclusion);"></ td>
			<td class=titleImg>审核结论</td>
		</tr>
	</table>	
			
	<div id=divAuditConclusion style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>审核结论</td>
				<td class=input><input class=codeno name=Conclusion ondblclick="return showCodeList('rewconclusion',[this,ConclusionName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('rewconclusion',[this,ConclusionName],[0,1],null,null,null,1);" readonly><input class=codename name=ConclusionName readonly></td>
				<td class=title></td>
		   		<td class=input></td>
		   		<td class=title></td>
		   		<td class=input></td>
			</tr>
			<tr class=common id=ReviewAdvice style="display:''">
			<td class=title>审核意见<font color="#FF0000">*</font></td>
			<td class=input colspan="5"><textarea name=ReviewAdvice cols="60" rows="3" class=common maxlength=1000 readonly></textarea></td>
			</tr>
			<tr class=common id=AgainReviewAdvice style="display:none">
			<td class=title>再审意见</td>
			<td class=input colspan="5"><textarea name=AgainReviewAdvice cols="60" rows="3" class=common maxlength=1000 readonly></textarea>
			</tr>
		</table>
	</div>
	
	
	<div id=divApproveConclusion style="display:none">
		<table >
			<tr>
				<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divApprove);"></ td>
				<td class=titleImg>审批结论</td>				
			</tr>
		</table>			
		<table class=common id=divApprove>
			<tr class=common>
				<td class=title>审批结论</td>
				<td class=input><input class=codeno name=ApproveConclusion readonly><input class=codename name=ApproveConclusionName readonly></td>
				<td class=title></td>
		   		<td class=input></td>
		   		<td class=title></td>
		   		<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>审批意见<font color="#FF0000">*</font></td>
				<td class=input colspan="5"><textarea name=ApproveAdvice cols="60" rows="3" class=common maxlength=1000 readonly></textarea></td>
			</tr>
		</table>
	</div>	
	
	<table>
		<tr>
			<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divCehckConclusion);"></ td>
			<td class=titleImg>复核结论</td>
		</tr>
	</table>
			
	<div id=divCehckConclusion style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>复核结论<font color="#FF0000">*</font></td>
				<td class=input><input class=codeno name=ChkConclusion readonly ondblclick="return showCodeList('chkconclusion',[this,ChkConclusionName],[0,1],null,conditionCode,'1',1);" onkeyup="return showCodeListKey('chkconclusion',[this,ChkConclusionName],[0,1],null,conditionCode,'1',1);"><input class=codename name=ChkConclusionName readonly></td>
				<td class=title></td>	   		
	   		<td class=input><input type=hidden class=codeno name=ChkAdvice readonly ondblclick="return showCodeList('chkadvice',[this,ChkAdviceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('chkadvice',[this,ChkAdviceName],[0,1],null,null,null,1);"><input type=hidden class=codename name=ChkAdviceName readonly></td>
	   		<td class=title></td>
	   		<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>复核意见<font color="#FF0000">*</font></td>
				<td class=input colspan="5"><textarea name=CheckAdvice cols="60" rows="3" class=common maxlength=2000></textarea></td>
			</tr>
		</table>
	</div>
	
	<input class=cssButton name=goBack value="返  回" type=button onclick="goToBack();">
<!-- 	<input class=cssButton name=UWTraceShow value="核赔履历查看" type=button onclick="showUWTrace();"> -->
	<input class=cssButton name=CaseConfirm value="复核确认" type=button onclick="checkConfirm();">
	<input class=cssButton name=BatchCaseConfirm value="批量复核确认" type=button onclick="batchCheckConfirm();">
	<font color="red" size="2">***提示：只能对下【0-给付或部分给付】、【5-案件回退】结论的案件进行批量复核确认！</font> 
			
 	<input type=hidden name=SelNo>				<!--列表序号-->
 	<input type=hidden name=PageIndex>		<!--页码-->
 	<input type=hidden name=PolNo>				<!--险种号-->
 	<input type=hidden name=DutyCode>			<!--责任编码-->
 	<input type=hidden name=GetDutyCode>	<!--给付项编码-->
	<input type=hidden name=GetDutyKind>	<!--理赔类型-->
 	<input type=hidden name=CaseNo>				<!--事件号-->
	<input type=hidden name=RegisterNo>		<!--个人案件号-->
	<input type=hidden name=CustomerNo>		<!--客户号-->
	<input type=hidden name=Operate>			<!--操作类型-->
	<input type=hidden name=AppntType>		<!--投保人类型-->
	<input type=hidden name=AppntNo>			<!--投保人编码-->	
	<input type=hidden name=UWFlag>			<!--投保人编码-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>