<%
/***************************************************************
 * <p>ProName：LCInsuredAddInput.jsp</p>
 * <p>Title：添加被保人详细信息</p>
 * <p>Description：添加被保人详细信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate = PubFun.getCurrentDate();
	String tCurrentTime = PubFun.getCurrentTime();

	String tFlag = request.getParameter("Flag");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tContNo = request.getParameter("ContNo");
	String tContPlanType = request.getParameter("ContPlanType");
%>
<script>
	var	tCurrentDate   = "<%=tCurrentDate%>";
	var	tCurrentTime   = "<%=tCurrentTime%>";
	
	var tFlag = "<%=tFlag%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tInsuredNo = "<%=tInsuredNo%>";
	var tContNo = "<%=tContNo%>";
	var tContPlanType = "<%=tContPlanType%>";
</script>
<html>
<head >
	<title>添加被保人信息</title>
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
	<script src="./LCInsuredAddInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCInsuredAddInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LCInsuredAddSave.jsp" target=fraSubmit>
	<!--被保人基本信息-->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryPage);">
			</td>
			<td class=titleImg>被保险人基本信息</td>
		</tr>
	</table>
	<div class=maxbox1>
	<table class=common>
			<tr>
				<td class=title>被保险人类型</td>
				<td class=input>
					<input class=codeno name=InsuredType id=InsuredType  readonly verify="被保险人类型|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('insuredtype',[this,InsuredTypeName],[0,1],null, '1 and othersign=#1# ', '1', '1', null);" onkeyup="return showCodeListKey('insuredtype',[this,InsuredTypeName],[0,1],null, '1 and othersign=#1# ', '1', '1', null);"  ><input class=codename name="InsuredTypeName" readonly=true elementtype=nacessary>
				</td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
	</table>
	<!--方案信息-->	
	<div id="shownewPage" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=title>保险方案<td>
				<td class=input><Input class=codeno name=JGContPlanCode id=JGContPlanCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,JGContPlanCodeName,JGsysContPlanCode);" onkeyup="showContPlanCodeName(this,JGContPlanCodeName,JGsysContPlanCode);"><input class=codename name=JGContPlanCodeName elementtype=nacessary><input type=hidden name=JGsysContPlanCode></td>
				<td class=title>被保险人人数</td>
				<td class=input><Input class="wid common" id=InsuredNum name=InsuredPeoples value="" verify="被保险人人数|num"></td>
				<td class=title></td>
 				<td class=input></td>
			</tr>
		</table>
	</div>
	<div id="divQueryPage" style="display: ''">
		<table class=common>
			<tr class= common>
				<td class=title>与主被保险人关系</td>
				<td class=input><input class=codeno name=relatomain id=relatomain readonly verify="与主被保险人关系|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('relation',[this,relatomainName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('relation',[this,relatomainName],[0,1],null,null,null,1,null);" ><input class=codename name=relatomainName readonly elementtype=nacessary></td>
				<td class=title><div id=mainname style="display: none">主被保险人姓名</div></td>
				<td class=input><div id=mainname1 style="display: none"><input class=common name=mainCustName onkeydown="selectMainUser();" onblur="selectMainUserInfo();" elementtype=nacessary ></div></td>
				<td class=title><div id=maincustno style="display: none">主被保险人客户号</div></td>
				<td class=input><div id=mainCustNo1 style="display: none"><input class="wid readonly" name=mainCustNo readonly></div></td>
			</tr>	
			<tr class=common>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" name=InsuredName verify="被保险人姓名|notnull&len<=40" onkeydown="selectUser();" onblur="checkMain();" elementtype=nacessary></td>	
				<td class=title>证件类型</td>
				<td class=input><input class=codeno name=IDType readonly verify="证件类型|code:IDType&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="clearInput(IDNo,IDTypeName); showCodeList('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" onkeyup="clearInput(IDNo,IDTypeName); return showCodeListKey('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" ><input class=codename name=IDTypeName readonly=true elementtype=nacessary></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=IDNo  verify="证件号码|notnull&len<=20" elementtype=nacessary onblur="checkidtype();"  ></td>
			</tr>
			<tr>
				<td class=title>性别</td>
				<td class=input><input class=codeno name=InsuredGender readonly  verify="性别|notnull&len<=2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);"  ><input class=codename name="InsuredGenderName" readonly=true elementtype=nacessary></td>	
				<td class=title>出生日期</td>
				<td class= input><input class=coolDatePicker name=InsuredBirthDay elementtype=nacessary dateFormat="short" verify="出生日期|notnull&date" onClick="laydate({elem: '#InsuredBirthDay'});" id="InsuredBirthDay"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>年龄</td>
				<td class=input><input class="wid readonly" name=InsuredAppAge readonly verify="被保人年龄|num"></td>	
			</tr>
			<tr class= common>
				<td class=title>保险方案</td>
				<td class=input><Input class=codeno name=ContPlanCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode,PlanType,PremcalType,PlanFlag);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode,PlanType,PremcalType,PlanFlag);"><input class=codename name=ContPlanCodeName elementtype=nacessary><input type=hidden name=sysPlanCode ></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
 			</tr>
 			<tr class= common>
				<td class=title>职业代码</td>
				<td class=input><input class=codeno name=OccupationCode readonly verify="职业代码|notnull"><input class=codename name=OccupationCodeName onkeydown="showOccupationCodeList(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" onkeyup="showOccupationCodeListKey(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" elementtype=nacessary></td>	          
				<td class=title colspan=2><span style="color: red">（录入职业代码名称敲回车模糊查询）</span></td>
				<!--<input class=codeno name="OccupationCode" verify="职业代码|notnull" ondblclick="showCodeList('occupationcode',[this,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,'1 and a.occuplevel=#3#','1',1);" onkeyup="return showCodeListKey('occupationcode',[this,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,'1 and a.occuplevel=#3#','1',1);" ><input class=codename name=OccupationCodeName onkeydown="showOccupationCodeList(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" onkeyup="showOccupationCodeListKey(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" elementtype=nacessary>-->
				<td class=title >职业类别</td>
				<td class=input><input type=hidden name=OccupationType verify="职业类别|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('occuptype',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('occuptype',[this,OccupationTypeName],[0,1]);" ><input class="wid readonly" name=OccupationTypeName readonly=true elementtype=nacessary></td>
				<!--<Input class="codeno" name="OccupationType"  verify="被保险人职业类别|code:OccupationType" ondblclick="return showCodeList('occuptype',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('occuptype',[this,OccupationTypeName],[0,1]);"><input class=codename name=OccupationTypeName readonly=true>-->
				<input type=hidden id="date" name=date value="<%=tCurrentDate%>">
			</tr>
			<tr class= common>
				<td class= title>邮政编码 </td>
				<td class= input><Input class="wid common" name=ZipCode  MAXLENGTH="6" verify="邮政编码|zipcode" ></td> 
				<td class= title>电子邮箱</td>
				<td class= input> <Input class="wid common" name=EMail verify="电子邮箱|Email" ></td>
				<td class= title>微信号</td>
				<td class= input> <Input class="wid common" name=MicroNo verify="微信号|len<30"></td>
			</tr>
			<tr class= common>
				<td class= title>联系电话</td>
				<td class= input><input class="wid common" name=Phone verify="联系电话|PHONE"></td>
				<td class= title>移动电话 </td>
				<td class= input><Input class="wid common" name=Mobile onblur="checkNumber(this);" verify="移动电话|len<=13"  ></td>
				<td class= title></td>
				<td class= input></td> 
			</tr>
			<tr class= common>
				<td class=title>详细地址</td>
				<td class=input colspan=5>
					<input class=codeno name=ProvinceName readonly style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode readonly>省
					<input class=codeno name=CityName readonly style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0]);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0]);"><input type=hidden name=CityCode readonly>市
					<input class=codeno name=CountyName readonly style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0]);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0]);"><input type=hidden name=CountyCode readonly>区/县
					<input class=common name=DetailAddress verify="单位详细地址|len<100" maxlength=100 style="width:300px">
				</td>
			</tr>		
		</table>
		
	</div>
		<!--附属被保人信息-->
		<div id=divQuerySpe style="display: none">
			<table class=common>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryFuSh);">
					</td>
					<td class=titleImg>附属被保险人信息</td>
				</tr>
			</table>
			<div id="divQueryFuSh" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanQueryInfoGrid" ></span>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<!--特殊信息-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuerySPE);">
				</td>
			<td class=titleImg>特殊信息</td>
			</tr>
		</table>
		<div id="divQuerySPE" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>服务区域</td>
					<td class=input><input class=codeno name=ServerArea readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" ><input class=codename name=ServiceArName readonly=true></td>
					<td class=title>是否次标准体</td>
					<td class=input><input class=codeno name=Substandard  readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" ><input class=codename name=SubstandardName readonly=true></td>
					<td class=title>社保标记</td>
					<td class=input><input class=codeno name=Social value="0" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" ><input class=codename name=SocialName value="无" readonly=true></td>	
				</tr>
				<tr class=common>
					<td class=title>月薪（元）</td>
					<td class=input><input class="wid common" name=Salary verify="月薪|VALUE>=0&LEN<10"></td>
					<td class=title>入司时间</td>
					<td class= input><input class=coolDatePicker  dateFormat="short" name="JoinCompDate" verify="入司时间|date"  onClick="laydate({elem: '#JoinCompDate'});" id="JoinCompDate"><span class="icon"><a onClick="laydate({elem: '#JoinCompDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>		
					<td class=title>工龄</td>
					<td class=input><input class="wid common" name=Seniority verify="工龄|int" ></td>
				</tr>
				<tr class=common>	
					<td class=title>职级</td>
					<td class=input><input class=codeno name=Position readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick= "return initPosition(this,PositionName);" onkeyup="return initPosition(this,PositionName);"><input class=codename name="PositionName" ></td>
					<td class=title>领取年龄</td>
					<td class=input><input class="wid common" name=GetYear verify="领取年龄|int" ></td>
					<td class=title>起领日期计算类型</td>
					<td class=input><input class=codeno name=GetStartType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('getstarttype',[this,GetStartTypeName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('getstarttype',[this,GetStartTypeName],[0,1],null, null, null, '1', null);" ><input class=codename name=GetStartTypeName readonly=true></td>
				</tr>
			</table>
		</div>
		<!--银行信息-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryBank);">
				</td>
				<td class=titleImg>被保险人银行信息</td>
			</tr>
		</table>
		<div id="divQueryBank" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>开户银行</td>
					<td class=input><input class=codeno name=HeadBankCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" ><input class=codename name="BankCodeName"></td>
					<td class=title>开户名</td>
					<td class=input><input class="wid common" name=AccName verify="开户名|LEN<=70" ></td>
					<td class=title>账  号</td>
					<td class=input><input class="wid common" name=BankAccNo verify="账  号|LEN<=25" onKeyPress="FormatString(BankAccNo);"  ></td>
				</tr>
				<tr class=common>
					<td class=title>开户行所在省</td>
					<td class=input><input class=codeno name=BankProvince readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1]);" onkeyup="return returnShowCodeListKey('bankprovince',[this, BankProvinceName],[0,1]);" ><input class=codename name="BankProvinceName"></td>
					<td class=title>开户行所在市</td>
					<td class=input><input class=codeno name=BankCity readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" onkeyup="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" ><input class=codename name="BankCityName"></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
		
		<!--其他信息-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryOther);">
				</td>
				<td class=titleImg>其他信息</td>
			</tr>
		</table>
		<div id="divQueryOther" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class= common>
					<td class=title>员工号</td>
					<td class=input><input class="wid common" name=WorkIDNo verify="员工号|len<=20" ></td>
					<td class=title>证件是否长期</td>
					<td class=input><input class=codeno name=ISLongValid readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" ><input class=codename name=ISLongValidName readonly=true></td>
					<td class=title>证件有效期</td>
					<td class=input><Input name=LiscenceValidDate verify="证件有效期|date"  class="coolDatePicker" dateFormat="short" onClick="laydate({elem: '#LiscenceValidDate'});" id="LiscenceValidDate"><span class="icon"><a onClick="laydate({elem: '#LiscenceValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class= common>
					<td class= title>所在分公司</td>
					<td class= input><Input class="wid common"  name=ComponyName verify="所在分公司|len<50" ></td>
					<td class=title>所在部门</td>
					<td class=input><input class="wid common" name=DeptCode verify="所在部门|len<40"></td>
					<td class= title>被保险人编码</td>
					<td class= input><Input class="wid common"  name=InsureCode verify="被保险人编码|len<20" ></td>
				</tr>
				<tr class=common>
					<!---
					<td class= title>所属客户群</td>
					<td class= input><Input class=codeno name=SubCustomerNo readonly ondblclick="return showCodeList('SubCustomerNo',[this,SubCustomerName],[0,1],null,fm.all('GrpContNo').value,'b.GrpcontNo',null);" onkeyup="return showCodeListKey('SubCustomerNo',[this,SubCustomerName],[0,1],null,fm.all('GrpContNo').value,'b.GrpcontNo',null);"><input class=codename name=SubCustomerName readonly=true></td>
					-->
					<td class=title>工作地</td>
					<td class=input><input class="wid common" name=WorkAddress verify="工作地|len<50" ></td>
					<td class=title>社保地</td>
					<td class=input><input class="wid common" name=SocialAddress verify="社保地|len<50"></td>
				</tr> 
			</table>
		</div>
	</div>
	<div id= "divAddDelButton" style= "display: ''" align=right>
		<input type =button class=cssButton name=addButton value="添加被保险人" onclick="addRecord();">
		<input type =button class=cssButton name=deleteButton value="删除被保险人" onclick="deleteRecord();">
		<input type =button class=cssButton name=modifyButton value="修改被保险人" onclick="modifyRecord();">
	</div>
	
	<!--被保人险种信息-->
	<div id="divPol" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
				</td>
				<td class=titleImg>被保险人险种信息</td>
			</tr>
		</table>
		<div id="divQueryInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanQueryScanGrid" ></span>
					</td>
				</tr>
			</table>
			<div id="divQueryInfoPage" style="display: none">
				<center>
					<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
					<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
					<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
					<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
				</center>
			</div>
		</div>
		
		<div id=divInsuredInfo3 style="display: none"><input class=cssButton type=button id=savaPOL value="责任信息保存" onclick="polSave();"></div>
	</div>
	
	<!--缴费项信息-->
	<div id="divPayPlan" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPayPlanInfo);">
				</td>
				<td class=titleImg>缴费项信息</td>
			</tr>
		</table>
		<div id="divPayPlanInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPayPlanGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		
		<div id=divPayPlanSaveButton style="display: none"><input class=cssButton type=button value="缴费信息保存" onclick="payPlanSave();"></div>
	</div>
	
	<!--投资信息-->
	<div id="divInvest" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInvestInfo);">
				</td>
				<td class=titleImg>投资账户信息</td>
			</tr>
		</table>
		<div id="divInvestInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanInvestGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		
		<div id=divInvestSaveButton style="display: none"><input class=cssButton type=button value="投资信息保存" onclick="investSave();"></div>
	</div>
	
	<input class=cssButton name="bnfAdd" type=button value="受益人维护" onclick="bnfaddRecord();">
	<input class=cssButton type=button value="关  闭" onclick="returnBack();">
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden id=InsuredNameTemp name=InsuredNameTemp>
	<input type=hidden id=mainCustNameTemp name=mainCustNameTemp>
	<input type=hidden id=GrpPrtno name="GrpPropNo" value="<%=tGrpPropNo%>"> 
	<input type=hidden id=ContType name="ContType">  
	<input type=hidden name=MissionID> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID> <!-- 工作节点ID -->
	<input type=hidden name=RowNo value="0"> <!-- 行号-差异展示使用 -->
	<input type=hidden name=LoadFlag value="01"> <!-- 操作页面类型 01-人员清单录入 02--录入  03--复核 -->
	<input type=hidden name=InsuredSeqNo value="<%=tInsuredNo%>"> <!-- 当前操作的个人客户号 -->
	<input type=hidden name=mainContNo> <!-- 当前操作的主被保人客户号 -->	
	<input type=hidden name=mContNo value="<%=tContNo%>"> <!-- 当前操作的个人保单号 -->	
	<!--添加保全信息-->
	<input type=hidden name=BQFlag>
	<input type=hidden name=EdorType>
	<input type=hidden name=EdorTypeCal>
	<input type=hidden name=EdorValiDate>
	<!--获取当前时间信息-->
	<input type=hidden name=tCurrentDate value="<%=tCurrentDate%>">
	<input type=hidden name=tCurrentTime value="<%=tCurrentTime%>">
	<input type=hidden name=tManageCom value="<%=tGI.ManageCom%>">
	<input type=hidden name=Flag value="<%=tFlag%>"> <!--复核时01-->
	<input type=hidden name=AmntFlag>
	<input type=hidden name=PlanFlag>
	<input type=hidden name=PremcalType>
	<input type=hidden name=PlanType>
	
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
