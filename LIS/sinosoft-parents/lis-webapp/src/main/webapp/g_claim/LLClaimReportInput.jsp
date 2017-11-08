<%
/***************************************************************
 * <p>ProName：LLClaimReportInput.jsp</p>
 * <p>Title：报案录入界面</p>
 * <p>Description：报案录入界面</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mCurrentDate = PubFun.getCurrentDate();
	String mType = request.getParameter("Type");
	String mRptNo = request.getParameter("RptNo");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
	var mRptNo = "<%=mRptNo%>";		
	var mType = "<%=mType%>";
</script>
<html>
<head>
	<title>报案登记</title>
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
	<script src="./LLClaimReportInput.js"></script>
	<%@include file="./LLClaimReportInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm target=fraSubmit method=post action="./LLClaimReportSave.jsp">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divReportInfo);">
				</td>
				<td class=titleImg>报案信息</td>
			</tr>
		</table>
		<div id=divReportInfo class=maxbox1 style="display:''">
			<table class=common>
				<tr class=common>
					<td class=title>报案号</td>
					<td class=input><input class="wid readonly" name=RptNo readonly></td>
					<td class=title>投保人类型</td>
					<td class=input><input class=codeno name=RgtClass verify="投保人类型|NOTNULL&code:conttype" maxlength='6' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conttype',[this,RgtClassName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('conttype',[this,RgtClassName],[0,1],null,null,null,1);"><input class=codename name=RgtClassName readonly elementtype=nacessary></td>
					<td class=title id=appntinfo>投保人名称</td>
					<td class= input id=appntvalueinfo><input class=codeno name=AppntNo maxlength=20 ondblclick="showWarnInfo();"><input class=codename name=AppntName onkeydown="QueryOnKeyDown(this);" elementtype=nacessary></td>
				</tr>
	
				<tr class=common>
					<td class=title>报案人姓名</td>
					<td class=input><Input class="wid common" name=RptName verify="报案人姓名|NOTNULL" maxlength=200 elementtype=nacessary></td>
					<td class=title>报案人电话</td>
					<td class=input><input class="wid common" name=RptPhone verify="报案人电话|NOTNULL" maxlength=20 elementtype=nacessary></td>
					<td class=title>报案方式</td>					
					<td class=input><input class=codeno name=RptMode verify="报案方式|NOTNULL&code:rptmode" maxlength=6 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('rptmode',[this,RptModeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('rptmode',[this,RptModeName],[0,1],null,null,null,'1',null);" maxlength=50><input class=codename name=RptModeName elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>报案人和出险人关系</td>
					<td class=input><input class=codeno name=Relation verify="报案人和出险人关系|NOTNULL&code:relation" maxlength=6 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1],null,null,null,'1',null);" maxlength=50><input class=codename name=RelationName elementtype=nacessary></td>
					<td class=title>报案日期</td>
					<td class=Input ><Input class=coolDatePicker dateFormat="short" name=RptDate verify="报案日期|NOTNULL&DATE&LEN=10" elementtype=nacessary onblur="checkDateFormat(RptDate)" maxlength="10" onClick="laydate({elem: '#RptDate'});" id="RptDate"><span class="icon"><a onClick="laydate({elem: '#RptDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>关联状态</td>
					<td class=input><input class=codeno name=RgtFlag value='0' readonly><input class=codename name=RgtFlagName value='否' readonly></td>
				</tr>
					<tr class=common>
					<td class=title>报案登记人</td>
					<td class=input><Input class="wid readonly" name=RptInputOperator readonly></td>
					<td class=title>报案登记日期</td>
					<td class=input><input class="wid readonly" name=RptInputDate readonly></td>
					<td class=title>报案机构</td>					
					<td class=input><input class=codeno name=RptCom verify="报案机构|NOTNULL&code:managecom" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,RptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#03#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,RptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#03#','1',1);"><input class=codename name=RptComName readonly elementtype=nacessary></td>					
				</tr>
				<tr class=common>
					<td class=title>报案确认人</td>
					<td class=input><Input class="wid readonly" name=RptConfOperator readonly></td>
					<td class=title>报案确认日期</td>
					<td class=input><Input class="wid readonly" name=RptConfDate readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
		<input class=cssButton name=ReportSave value="保存报案" type=button onclick="saveReport();">
		<input class=cssButton name=ReportDelete value="删除报案" type=button onclick="deleteReport();">
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCustomerList);">
				</td>
				<td class=titleImg>客户信息列表</td>
			</tr>
		</table>	
		<div id=divCustomerList style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanCustomerListGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCustermorInfo);">
				</td>
				<td class=titleImg>客户信息</td>
			</tr>
		</table>	
		<div id=divCustermorInfo class=maxbox1 style="display:''">
			<input class=cssButton value="新  增" type=button name=CustomerAdd onclick="addCustomer();">
			<input class=cssButton value="修  改" type=button name=CustomerModify onclick="modifyCustomer();">
			<input class=cssButton value="删  除" type=button name=CustomerDelete onclick="deleteCustomer();">
			<input class=cssButton value="保单查询" type=button name=LCQueryBut onclick="showInsuredLCPol();">
			<input class=cssButton value="保全查询" type=button name=LPQueryBut onclick="showInsuredLCEdor();">
			<input class=cssButton value="既往赔案查询" type=button name=PastReport onclick="showOldCase();">
			<input class=cssButton value="索赔资料" type=button name=AffixBut onclick="ClaimData();">
			<!--
			<input class=cssButton value="前置调查" type=button name=PresurveyData onclick="ShowPresurveyData();">
			-->
			<input class=cssButton value="重大案件上报" type=button name=BigReport onclick="ShowReportBig();">
			<input class=cssButton value="清  空" type=button name=initCustomerInfo onclick="initInfo();">
			<br>
		  <font color='#ff0000'><b>被保险人检索条件：姓名+出生日期、姓名+员工号、证件号码，三种条件任选一种</b></font>
	    <table class=common> 
				<tr class=common>
					<td class=title>姓名</td>
					<td class=input><input class="wid common" name=CustomerName onkeydown="selectUser();" onblur="selectUser();" elementtype=nacessary maxlength=50></td>
					<td class=title>出生日期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=Birthday onkeydown="selectUser();" onblur="selectUser();" elementtype=nacessary></td>
					<td class=title>员工号</td>
					<td class=input><input class="wid common" name=EmpNo onkeydown="selectUser();" onblur="selectUser();"></td>
				</tr>
				<tr class=common>
					<td class=title>证件号码</td>
					<td class=input><Input class="wid common" name=IDNo onkeydown="selectUser();" onblur="selectUser();" elementtype=nacessary maxlength=20>
					<td class=title>证件类型</td>
					<td class=input><input class=codeno name=IDType readonly><input class=codename name=IDTypeName readonly elementtype=nacessary></td>
					<td class=title>性别</td>
					<td class=input><input class=codeno name=Gender readonly><input class=codename name=GenderName readonly elementtype=nacessary></td>  
				</tr>
				<tr class=common>
					<td class=title>备注</td>
					<td class=input colspan="5"><textarea class=common name=CustomerRemarks id=CustomerRemarks cols="50" rows="2" maxlength=200></textarea>
				</tr>
			</table>			
		</div>
	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAccidentList);">
				</td>
				<td class=titleImg>事件信息列表</td>
			</tr>
		</table>
		<div id= "divAccidentList" style= "display: ''">
			<table class= common>
				<tr class= common>
					<td colSpan=1><span id="spanAccidentListGrid" ></span></td>
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
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLLCaseReport);">
				</td>
				<td class=titleImg>事件信息</td>
			</tr>
		</table>
		<div id= "divLLCaseReport" class=maxbox1 style= "display: ''">
			<input class=cssButton name=AddCase value="新增事件"  type=button onclick="newCase()">
			<input class=cssButton name=ModifyCase value="修改事件"  type=button onclick="modifyCase()">
			<input class=cssButton name=DeleteCase value="删除事件"  type=button onclick="deleteCase()">
			<input class=cssButton value="清  空" type=button name=initCaseInfo onclick="initCustomerCaseInfo();initGrpClaimDutyGrid();queryReportCaseInfo();">						
		<table class=common>
			<tr class=common>
				<td class=title>出险原因</td>
				<td class=input><input class=codeno name=AccReason style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('accreason',[this,AccReasonName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('accreason',[this,AccReasonName],[0,1],null,null,null,1);" maxlength=6><input class=codename name=AccReasonName elementtype=nacessary></td>
				<td class=title>出险日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=AccDate elementtype=nacessary onClick="laydate({elem: '#AccDate'});" id="AccDate"><span class="icon"><a onClick="laydate({elem: '#AccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>就诊医院</td>
                <td class=input><input class=codeno name=HospitalCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('hospital1',[this,HospitalName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('hospital1',[this,HospitalName],[0,1],null,null,null,1);" maxlength=6><input class=codename onkeydown="QueryOnKeyDown(this)" onchange="fm.HospitalCode.value=''" name=HospitalName elementtype=nacessary></td>
                <td class= title></td>
				<td class= input></td>
           </tr>
			<tr class= common>
				<td class= title>索赔金额</td>
				<td class= input><input class="wid common" name=ClaimPay onchange="checkMoney(fm.ClaimPay);"></td>
				<td class=title>是否重大案件</td>
				<td class=input><input class=codeno readonly name=AccGrade value='0' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,AccGradeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,AccGradeName],[0,1],null,null,null,1);"><input class=codename name=AccGradeName value='否' readonly elementtype=nacessary></td>
				<td class= title></td>
				<td class= input></td>
			</tr>
			<tr class=common>
				<td class=title>出险地点<font color=red><b>*</b></font></td>
				<td class=input colspan=5>					
					<input class=common name=AccSite style="width:300px" maxlength=2000 >
				</td>			
			</tr>
			<tr class=common>
				<td class=title>出险类型<font color=red><b>*</b></font></td>
				<td class=input colspan=5>
					<input type=checkbox value="02" name=ClaimType>身故
					<input type=checkbox value="05" name=ClaimType>重大疾病
					<input type=checkbox value="01" name=ClaimType>伤残/全残
					<input type=checkbox value="06" name=ClaimType>烧烫伤
					<input type=checkbox value="00" name=ClaimType>医疗
					<input type=checkbox value="0A" name=ClaimType>津贴
				</td>
			</tr>
			<tr class=common>
				<td class=title>事故描述<font color=red><b>*</b></font></td>
				<td class=input colspan=5>
					<textarea class=common name=AccDesc id=AccDesc cols="50" rows="2" maxlength=1000 ></textarea>
				</td>
			</tr>
			<tr class=common>
				<td class=title>备注</td>
				<td class=input colspan=5>
					<textarea class=common name=CaseRemark id=CaseRemark cols="50" rows="2" maxlength=1000></textarea>
				</td>
			</tr>
		</table>
		<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanGrpClaimDutyGrid" ></span>
						</td>
					</tr>
		</table>	
	</div>
	
	<div id="divMajorInfo" style="display:none">	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSubmitInfo);">
				</td>
				<td class=titleImg>呈报信息</td>
			</tr>
		</table>
		<div id="divSubmitInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>经办人</td>
					<td class=input><input class="wid readonly" name=InputOperator></td>
					<td class=title>经办日期</td>
					<td class=input><input class="wid readonly" name=InputDate></td>  
					<td class=title>经办机构</td>
					<td class=input><input class=codeno readonly name=InputCom><input class=codename readonly name=InputComName></td>
				</tr>
				<tr class=common>
					<td class=title>机构意见(含调查计划)<font color=red>*</font></td>
					<td class=input colspan=5>
					<textarea class=readonly name=InputRemarks id=InputRemarks cols="60" rows="4" maxlength=2000 readonly></textarea>
					</td>
				</tr>
			</table>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRepMajorInfo);">
				</td>
				<td class=titleImg>回复信息</td>
			</tr>
		</table>
		<div id="divRepMajorInfo" style="display: ''">
		<div id="divReplyInfo" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>经办人</td>
					<td class=input><input class="wid common" name=RepInputOperator></td>
					<td class=title>经办日期</td>
					<td class=input><input class="wid common" name=RepInputDate></td>  
					<td class=title>经办机构</td>
					<td class=input><input class=codeno name=RepInputCom><input class=codename name=RepInputComName elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>分公司意见<font color=red>*</font></td>
					<td class=input colspan=5>
					<textarea class=common name=RepInputRemarks id=RepInputRemarks cols="60" rows="4" maxlength=200></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id="divReplyInfo1" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>经办人</td>
					<td class=input><input class="wid common" name=RepInputOperator1></td>
					<td class=title>经办日期</td>
					<td class=input><input class="wid common" name=RepInputDate1></td>  
					<td class=title>经办机构</td>
					<td class=input><input class=codeno name=RepInputCom1><input class=codename name=RepInputComName1 elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>总公司意见<font color=red>*</font></td>
					<td class=input colspan=5>
					<textarea class=common name=RepInputRemarks1 id=RepInputRemarks1 cols="60" rows="4" maxlength=200></textarea>
					</td>
				</tr>
			</table>
		</div>
	</div>				
		<input class=cssButton name=confirmBut value="审批确认" type=button onclick="majorApprove();">
		<input class=cssButton value="返  回" type=button onclick="goToBack1();">
	</div>	
	<div id=divReportConfirmButton style="display:''">
		<input class=cssButton name=ReportConfirm value="报案确认" type=button onclick="confirmReport();">
		<input class=cssButton value="返  回" type=button onclick="goToBack();">
	</div>		
	<input type=hidden name=MajorFlag>		<!--重大案件标识-->	
	<input type=hidden name=CustomerNo>		<!--客户号-->
	<input type=hidden name=AccNo>				<!--事件号-->
	<input type=hidden name=Operate>			<!--操作类型-->
	<input type=hidden name=AppntType>		<!--投保人类型-->
	<input type=hidden name=SelfRptCom>		<!--默认受理机构-->														
	<input type=hidden name=SelfAppntNo>	<!--所选投保人号码-->
	<input type=hidden name=SelfAppntName>	<!--所选投保人名称-->
	<input type=hidden name=PageIndex>		<!--记录页码-->
	<input type=hidden name=SelNo>				<!--记录行号-->			
	<input type=hidden name=SubRptNo>				<!--记录行号-->
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
