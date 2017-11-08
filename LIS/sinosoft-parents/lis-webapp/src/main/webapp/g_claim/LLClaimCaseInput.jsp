
<%
/***************************************************************
 * <p>ProName：LLClaimCaseInput.jsp</p>
 * <p>Title：普通立案录入</p>
 * <p>Description：普通立案录入</p>
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
	<title>普通立案录入界面</title>
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
	<script src="./LLClaimCaseSubInput.js"></script>
	<script src="./LLClaimCaseInput.js"></script>	
	<%@include file="./LLClaimCaseInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimCaseSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAcceptInfo);">
			</td>
			<td class=titleImg>受理信息</td>
		</tr>
	</table>
	<div id="divAcceptInfo" class=maxbox1 style="display: ''">
		<table class=common> 
			<tr class=common>
				<td class=title>批次号</td>
				<td class=input><input class="wid readOnly" name=GrpRgtNo maxlength='20' readOnly></td>
				<td class=title>保单类型</td>
				<td class=input><input class=codeno name=RgtClass maxlength='6' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conttype',[this,RgtClassName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('conttype',[this,RgtClassName],[0,1],null,null,null,1);" readonly><input class=codename name=RgtClassName readonly elementtype=nacessary></td>				
				<td class=title id='appntinfo'>投保人名称</td>
				<td class=input id='appntvalueinfo'><input class=codeno name=AppntNo style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showWarnInfo();" readonly><input class=codename name=AppntName onkeydown="QueryOnKeyDown(this);" onchange="fm.AppntNo.value='';" elementtype=nacessary maxlength='200'></td>
			</tr>
			<tr class=common>
				<td class=title>批次申请日期</td>
				<td class=input><input class=coolDatePicker name=AppDate maxlength='20' dateFormat=short elementtype=nacessary onClick="laydate({elem: '#AppDate'});" id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>受理日期</td>
				<td class=input><input class="wid readOnly" name=AcceptDate maxlength='20' dateFormat=short readOnly> </td>
				<td class=title>交接流转号</td>
				<td class=input><input class="common" name=PageNo maxlength='20' elementtype=nacessary><input class=cssButton type=button name=PageNoApply value="申 请" onclick="applyPageNo();"></td>
			</tr>
			<tr class=common>
				<td class=title>受理登记人</td>
				<td class=input><input class="wid readOnly" name=AcceptOperatorName readOnly></td>
				<td class=title>受理机构</td>
				<td class=input><input class=codeno name=AcceptCom verify="code:managecom" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,AcceptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#03#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,AcceptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#03#','1',1);" maxlength='20' readonly><input class=codename name=AcceptComName readonly elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<!-- <td class=title id='risklevel'>客户风险等级</td>
				<td class=input id='risklevelname'><input class=readOnly name=RiskLevel readOnly></td> -->
				
			</tr>
		</table>
		<input class=cssButton name=AcceptSave value="保  存" type=button onclick="saveAccept();">
		<input class=cssButton name=Acceptdelete value="删  除" type=button onclick="deleteAccept();">
	</div>
	<div id=CustomerListInfo style="display:''"> 	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerList);">
				</td>
				<td class=titleImg>客户信息列表</td>
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
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
				<input class=cssButton type=button value="导出数据" onclick="exportData();">
			</center>
		</div>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerInfo);">
			</td>
			<td class=titleImg>客户信息</td>
		</tr>
	</table>
	<div id="divCustomerInfo" class=maxbox1 style="display: ''">	
	
	<input class=cssButton name=CustomerAdd value="新  增" type=button onclick="addCustomer();">
	<input class=cssButton name=CustomerModify value="修  改" type=button onclick="modifyCustomer();">
	<input class=cssButton name=CustomerDelete value="删  除" type=button onclick="deleteCustomer();">
	<input class=cssButton name=noAccept value="未受理客户" type=button onclick="noAcceptInfo();">
	<input class=cssButton name=AcceptDelete value="删除全部已受理客户" type=button onclick="deleteAllAccept();">
	<input class=cssButton name=CustomerInit value="清  空" type=button onclick="initSelectCustomerInfo();">
	
	</br>
	<input class=cssButton name=PolicyQuery value="保单查询" type=button onclick="showInsuredLCPol();">
	<input class=cssButton name=EdorQuery value="保全查询" type=button onclick="showInsuredLCEdor();">
	<input class=cssButton name=ClaimQuery value="既往赔案查询" type=button onclick="showOldCase();">
	<input class=cssButton name=ScanQuery value="影像件查询" type=button onclick="queryEasyscan();">
	<input class=cssButton name=SecurityQuery value="社保信息查询" type=button onclick="querySecurity();">
	<input class=cssButton name=bnfMaintenance value="受益人信息" type=button onclick="benefitInfo();">
	<input class=cssButton name=bnfMaintenance value="账户查询" type=button onclick="queryAccInfo();">		
		
	<br>
	<font color='#ff0000'><b><span id="AcceptCountSpan">被保险人检索条件：姓名+出生日期、姓名+员工号、证件号码，三种条件任选一种</span></b></font>
	<table class=common> 
		<tr class=common>
			<td class=title>姓名</td>
			<td class=input><input class="wid common" name=CustomerName onkeydown="selectUser(this);" elementtype=nacessary maxlength=200></td>
			<td class=title>出生日期</td>
			<td class=input><input class=coolDatePicker dateFormat=short name=Birthday onkeydown="selectUser(this);" elementtype=nacessary></td>
			<td class=title>员工号</td>
			<td class=input><input class="wid common" name=EmployeNo onkeydown="selectUser(this);" maxlength=30></td>
		</tr>
		<tr class=common>
		   <td class=title>证件号码</td>
		   <td class=input><Input class="wid common" name=IDNo verify="证件号码|NOTNULL" onblur="analysisIDNo(this);" onkeydown="selectUser(this);" elementtype=nacessary maxlength=20>
			<td class=title>证件类型</td>
			<td class=input><input class=codeno name=IDType verify="证件类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" maxlength=50 readonly><input class=codename name=IDTypeName maxlength=2  elementtype=nacessary readonly></td>				
			<td class=title>性别</td>
				<td class=input><input class=codeno name=Gender verify="性别|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,GenderName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('sex',[this,GenderName],[0,1],null,null,null,'1',null);" maxlength=1 readonly><input class=codename name=GenderName  elementtype=nacessary readonly></td>  
	<!-- 		
			
			<td class=title>证件号码</td>
			<td class=input><Input class="wid common" name=IDNo onkeydown="selectUser(this);" elementtype=nacessary maxlength=30>
			<td class=title>证件类型</td>
			<td class=input><input class=codeno name=IDType readonly><input class=codename name=IDTypeName elementtype=nacessary readonly></td>
			<td class=title>性别</td>
			<td class=input><input class=codeno name=Gender readonly><input class=codename name=GenderName  elementtype=nacessary readonly></td>   -->
		</tr>
		<tr class=common>
			<td class=title>申请金额</td>
			<td class=input><input class="wid common" name=AppAmnt elementtype=nacessary onchange="checkMoney(fm.AppAmnt);" maxlength=20></td>
			<td class=title>发票数</td>
			<td class=input><input class="wid common" name=BillCount elementtype=nacessary onchange="checkInput(fm.BillCount);" maxlength=9></td>
			<td class=title>影像件数</td>
			<td class=input><input class="wid common" name=ScanCount onchange="checkInput(fm.ScanCount);" maxlength=9></td>
		</tr>
		<tr class=common>
			<td class=title>是否加急</td>
			<td class=input><input class=codeno name=IsUrgent value='0' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,IsUrgentName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,IsUrgentName],[0,1],null,null,null,1);" readonly><input class=codename name=IsUrgentName value='否' elementtype=nacessary readonly></td>
			<td class=title>是否开据分割单</td>
			<td class=input><input class=codeno name=IsOpenBillFlag value='0' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,IsOpenBillFlagName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,IsOpenBillFlagName],[0,1],null,null,null,1);" readonly><input class=codename name=IsOpenBillFlagName value='否' elementtype=nacessary readonly></td>			
			<td class=title>是否退回发票</td>
			<td class=input><input class=codeno name=IsBackBill value='0' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,IsBackBillName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,IsBackBillName],[0,1],null,null,null,1);" readonly><input class=codename name=IsBackBillName value='否' elementtype=nacessary readonly></td>			
		</tr>
		<tr class=common>
			<td class=title>开户银行</td>
			<td class=input><input class=codeno name=BankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null,null,null,1);" readonly><input class=codename name=BankCodeName onkeydown="QueryOnKeyDown(this)" onchange="fm.BankCode.value='';" maxlength=200></td>
			<td class=title>开户行所在省</td>
			<td class=input><input class=codeno name=Province style="background:url(../common/images/select--bg_03.png) no-repeat right center" onchange="initAreaData();" ondblclick="showCodeList('province',[this,ProvinceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('province',[this,ProvinceName],[0,1],null,null,null,1);" readonly><input class=codename name=ProvinceName readonly></td>
			<td class=title>开户行所在市</td>
			<td class=input><input class=codeno name=City style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="checkProvince();showCodeList('city', [this,CityName],[0,1],null,fm.Province.value,'upplacename',1);" onkeyup="checkProvince();showCodeListKey('city', [this,CityName],[0,1],null,fm.Province.value,'upplacename',1);" readonly><input class=codename name=CityName readonly></td>
		</tr>
		<tr class=common>
			<td class=title>账号</td>
			<td class=input><input class="wid common" name=BankAccNo maxlength="30"></td>
			<td class=title>账户名</td>
			<td class=input><input class="wid common" name=AccName maxlength=200></td>
			<td class=title>客户申请日期</td>
			<td class=input><input class=coolDatePicker dateFormat=short name=CustomerAppDate onClick="laydate({elem: '#CustomerAppDate'});" id="CustomerAppDate"><span class="icon"><a onClick="laydate({elem: '#CustomerAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
		</tr>
		<tr class=common>
			<td class=title>备注</td>
			<td class=input colspan="3"><textarea class=common name=Remark id=Remark cols="50" rows="2" maxlength=600></textarea>
			<td class=title>手机号</td>
			<td class=input><input class="wid common" name=TelPhone maxlength="11"></td>			
		</tr>
	</table>
	<input class=cssButton name=CustomerSave value="保  存" type=button onclick="modifyCustomer();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEventList);">
			</td>
			<td class=titleImg>事件信息列表</td>
		</tr>
	</table>
	<div id="divEventList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanEventlistGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCaseInfo);">
			</td>
			<td class=titleImg>事件信息</td>
		</tr>
	</table>
	
	<div id= "divCaseInfo" class=maxbox1 style= "display: ''">
		<input class=cssButton name=CaseAdd value="新增事件"  type=button onclick="addCase();">
		<input class=cssButton name=CaseModify value="修改事件"  type=button onclick="modifyCase();">
		<input class=cssButton name=CaseDelete value="删除事件"  type=button onclick="deleteCase();">
		<!-- <input class=cssButton value="非医疗账单"  type=button onclick="easyBill();">	-->				
		<input class=cssButton value="医疗账单"  type=button onclick="standBill();"> 
		<input class=cssButton value="清  空"  type=button onclick="initSelectCaseInfo();">
		<table class=common>
			<tr class=common>
				<td class=title>出险原因</td>				
				<td class=input><input class=codeno name=AccReason style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('accreason',[this,AccReasonName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('accreason',[this,AccReasonName],[0,1],null,null,null,1);" maxlength=6 readonly><input class=codename name=AccReasonName elementtype=nacessary readonly></td>
				<td class=title>出险日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=AccDate elementtype=nacessary onClick="laydate({elem: '#AccDate'});" id="AccDate"><span class="icon"><a onClick="laydate({elem: '#AccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>索赔金额</td>
				<td class=input><input class="wid common" name=ClaimPay onchange="checkMoney(fm.ClaimPay);" elementtype=nacessary maxlength=9></td>
			</tr>
			<tr class=common>
				<td class=title>就诊医院</td>				
				<td class=input><input class=codeno name=HospitalCode maxlength=20 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('hospital1', [this, HospitalName], [0, 1]);" onkeyup=" showCodeListKey('hospital1', [this, HospitalName] , [0, 1]);" readonly><input class=codename name=HospitalName onkeydown="QueryOnKeyDown(this)" onchange="fm.HospitalCode.value='';" elementtype=nacessary maxlength=600></td>
				<td class=title>主要诊断(ICD10)</td>
				<td class=input><input class=codeno name=AccResult1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showWarnInfo1();" onkeyup="showWarnInfo1();" maxlength=40><input class=codename name='AccResult1Name' onkeydown="QueryOnKeyDown(this)" onchange="fm.AccResult1.value='';" elementtype=nacessary maxlength=300></td>										
				<td class=title>诊断详情(ICD10)</td>
				<td class=input><input class=codeno name=AccResult2 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('diseasecode',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode',1);" onkeyup="showCodeListKey('accresult', [this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode',1);" maxlength=40 readonly><input class=codename name=AccResult2Name onkeydown="QueryOnKeyDown(this)" onchange="fm.AccResult2.value='';" maxlength=300></td>
			</tr>
			<tr class=common>
				<td class=title>伤残日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=DeformityDate onClick="laydate({elem: '#DeformityDate'});" id="DeformityDate"><span class="icon"><a onClick="laydate({elem: '#DeformityDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>身故日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=DeathDate onClick="laydate({elem: '#DeathDate'});" id="DeathDate"><span class="icon"><a onClick="laydate({elem: '#DeathDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title>治疗结果</td>				
				<td class=input><input class=codeno name=TreatResult style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('treatresult',[this,TreatResultName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('closereason',[this,TreatResultName],[0,1],null,null,null,1);" maxlength=10 readonly><input class=codename name=TreatResultName elementtype=nacessary readonly></td>
			</tr>
			<tr class=common>
				<td class=title>事件来源</td>
				<td class=input><input type=hidden name=CaseSource readonly><input class=readonly name=CaseSourceName elementtype=nacessary readonly></td>
				<td class=title>乐容回写事件号 </td>
				<td class=input><input class="wid common" name=LRCaseNo maxlength=20></td>
				<td class=title></td>				
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>出险地点</td>
				<td class=input colspan=5>
					<input class=common name=AccSite id=AccSite style="width:210px" elementtype=nacessary maxlength=200 >
				</td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>出险类型<font color=red><b>*</b></font></td>
				<td class=input colspan=5>
					<input type=checkbox value="02" name=ClaimType>身故
					<input type=checkbox value="04" name=ClaimType>重大疾病
					<input type=checkbox value="01" name=ClaimType>伤残/全残
					<input type=checkbox value="06" name=ClaimType>烧烫伤
					<input type=checkbox value="00" name=ClaimType>医疗
					<input type=checkbox value="0A" name=ClaimType>津贴
					<input type=checkbox value="05" name=ClaimType>失能
				</td>
			</tr>
			<tr class=common>
				<td class=title>事故描述</td>
				<td class=input colspan=5><textarea class=common name=AccDesc id=AccDesc cols="50" rows="2" maxlength=1000></textarea></td>
			</tr>
			<tr class=common>
				<td class=title>备注</td>
				<td class=input colspan=5><textarea class=common name=CaseRemark id=CaseRemark cols="50" rows="2" maxlength=1000></textarea></td>
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
	<input class=cssButton name=AssociateOff value="取消关联"  type=button onclick="offAssociate();">	
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
	<input class=cssButton name=AssociateOn value="关  联"  type=button onclick="onAssociate();">			
	<hr class=line>
	<table>
		<tr>
			<td>
				<input class=cssButton name=OverCase type=button value="立案完毕" onclick="caseOver();">
				<input class=cssButton name=ReturnBack type=button value="关  闭" onclick="goBack();">				
				<!-- <input class=cssButton name=ImportCustomer type=button value="客户信息批量导入" onclick="customerImport();"> -->
			</td>
		</tr>
	</table>
 	<input type=hidden name=SelNo>				<!--列表序号-->
 	<input type=hidden name=PageIndex>		<!--页码-->	
 	<input type=hidden name=PolNo>				<!--险种号-->
 	<input type=hidden name=DutyCode>			<!--责任编码-->
 	<input type=hidden name=GetDutyCode>	<!--给付项编码-->   	   	   	
 	<input type=hidden name=CaseNo>				<!--事件号-->
	<input type=hidden name=RegisterNo>		<!--个人案件号-->   
	<input type=hidden name=CustomerNo>		<!--客户号-->
	<input type=hidden name=Operate>			<!--操作类型-->
	<input type=hidden name=AppntType>		<!--投保人类型-->
	<input type=hidden name=SelfAcceptCom>		<!--默认受理机构-->					
	<input type=hidden name=SelfAppntNo>		<!--个人投保人-->		
	<input type=hidden name=SelfAppntName>		<!--个人投保人名称-->	
	<input type=hidden name=AcceptOperator>		<!--受理登记人编码-->
<br /><br /><br /><br />	
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
