<%
/***************************************************************
 * <p>ProName：GEdorNIInput.jsp</p>
 * <p>Title：新增被保人</p>
 * <p>Description：新增被保人</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo"); 
	String tGrpcontNo = request.getParameter("GrpContNo");
	String tEdorType = request.getParameter("EdorType"); 
	String tEdorNo = request.getParameter("EdorNo");
	
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tCurrentDate = "<%=tCurrentDate%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpcontNo = "<%=tGrpcontNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>"; 
	
</script>

<html>
<head >
	<title>新增被保人</title>
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
	<script src="./EdorNIInput.js"></script>
	<%@include file="./EdorNIInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="EdorNISave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo12);">
			</td>
			<td class=titleImg>新增被保险人查询条件</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" name=InsuredNameQ id=InsuredNameQ></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=IdNoQ id=IdNoQ></td>
				<td class=title>保险方案</td>
				<td class=input><Input class=codeno name=PlanCodeQ id=PlanCodeQ style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,planCodeNameQ,sysPlanCodeQ);" onkeyup="showContPlanCodeName(this,planCodeNameQ,sysPlanCodeQ);" ><input class=codename name=planCodeNameQ ><input type=hidden name=sysPlanCodeQ ></td>
			</tr>
			<tr class=common style="display: none">
				<td class=title>导入批次号</td>
				<td class=input><input class="wid common" name=ImpBatch id=ImpBatch></td>
				<td class=title></td>
				<td class=title style="display: none">被保险人客户号</td>
				<td class=input><input class="wid common" name=InsuredNoQ type=hidden></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<div id="divButton01" style="display: none">	
			<input class=cssButton type=button value="查  询" onclick="queryInsured(1);">
			<input class=cssButton type=button value="返  回" onclick="top.close();">
			<input class=cssButton type=button value="选择删除" onclick="choseDelet();" >
			<input class=cssButton type=button value="条件删除" onclick="condDelete();">
			<input class=cssButton type=button value="全部删除" onclick="allDelete();">
		</div>
		<div id="divButton02" style="display: none">	
			<input class=cssButton type=button value="查  询" onclick="queryInsured();">
			<input class=cssButton type=button value="返  回" onclick="top.close();">
		</div>
	</div>
	
	<!--新增被保险人查询信息-->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo4);">
			</td>
			<td class=titleImg>新增被保险人信息</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>			
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanEdorDetailGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<!--页面基本信息-->
	<div id="divInfoNI" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfoNI1);">
				</td>
				<td class=titleImg>被保险人基本信息</td>
			</tr>
		</table>
		<div id="divInfoNI1" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td id=td1 style="display: ''" class=title>保全生效日期</td>
				 	<td id=td2 style="display: ''" class=input><input class="coolDatePicker" dateFormat="short" verify="保全生效日期|notnull"  name=edorValDate elementtype=nacessary onClick="laydate({elem: '#edorValDate'});" id="edorValDate"><span class="icon"><a onClick="laydate({elem: '#edorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				 	<td class=title></td>
				 	<td class=input></td>
				 	<td class=title></td>
				 	<td class=input></td>
				</tr>
				<tr class=common  style="display: none">
					<td class=title>被保险人类型</td>
					<td class=input>
						<input class=codeno name=InsuredType id=InsuredType  verify="被保险人类型|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('insuredtype',[this,InsuredTypeName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('insuredtype',[this,InsuredTypeName],[0,1],null, null, null, '1', null);"  readonly><input class=codename name=InsuredTypeName id=InsuredTypeName readonly elementtype=nacessary>
					</td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>	
				</tr>
			</table>
		</div>	
		
		
		<!--方案信息-->	
		<div id="shownewPage" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>保险方案<td>
					<td class=input><Input class=codeno name=JGContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,JGContPlanCodeName,JGsysContPlanCode);" onkeyup="showContPlanCodeName(this,JGContPlanCodeName,JGsysContPlanCode);" readonly><input class=codename name=JGContPlanCodeName elementtype=nacessary><input type=hidden name=JGsysContPlanCode></td>
					<td class=title>被保险人人数</td>
					<td class=input><Input class="wid common"  id="InsuredNum" name=InsuredPeoples  verify="被保险人人数|int" elementtype=nacessary></td>
					<td class=title></td>
 					<td class=input></td>
				</tr>
			</table>
		</div>
		
		<div id="divQueryPage" style="display: ''">
			<table class=common>
				<tr class= common>
					<td class=title>与主被保险人关系</td> 
					<td class=input><input class=codeno name=relatomain verify="与主被保险人关系|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('relation',[this,relatomainName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('relation',[this,relatomainName],[0,1],null,null,null,1,null);" readonly><input class=codename name=relatomainName readonly=true elementtype=nacessary></td>
					<td class=title id=mainname style="display: none">主被保险人姓名</td>
					<td class=input id=mainname1 style="display: none"><input class="wid common" name=mainCustName onkeydown="selectMainUser();"  elementtype=nacessary  ></td>
					<td class=title id=mainIDNo style="display: none">主被保险人证件号码</td>
					<td class=input id=mainCustNo1 style="display: none"><input class="wid common" name=mainCustIDNo></td>
					<td class=title id=id1 style="display: none"></td>
					<td class=input id=id2 style="display: none"></td>
					<td class=title id=id3 style="display: none"></td>
					<td class=input id=id4 style="display: none"></td>
				</tr>
				<tr class=common>
					<td class=title>被保险人姓名</td>
					<td class=input><input class="wid common" name=InsuredName id=InsuredName  verify="被保险人姓名|notnull&len<=40" onkeydown="selectUser();" onblur="checkMain();"  elementtype=nacessary></td>	
					<td class=title>证件类型</td>
					<td class=input><input class=codeno name=IDType id=IDType verify="证件类型|code:IDType&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="clearInput(IDNo,IDTypeName); showCodeList('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" onkeyup="clearInput(IDNo,IDTypeName); return showCodeListKey('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=IDTypeName readonly=true elementtype=nacessary></td>
					<td class=title>证件号码</td>
					<td class=input><input class="wid common" name=IDNo id=IDNo  verify="证件号码|notnull&len<=20" elementtype=nacessary onblur="checkidtype();"  ></td>
				</tr>
				<tr>
					<td class=title>性别</td>
					<td class=input><input class=codeno name=InsuredGender id=InsuredGender  verify="性别|notnull&len<=2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);"  readonly><input class=codename name="InsuredGenderName" readonly=true elementtype=nacessary></td>	
					<td class=title>出生日期</td>
					<td class= input><input class=coolDatePicker elementtype=nacessary dateFormat="short" name=InsuredBirthDay verify="出生日期|notnull&date" onClick="laydate({elem: '#InsuredBirthDay'});" id="InsuredBirthDay"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>年龄</td>
					<td class=input><input class="wid readonly" name=InsuredAppAge verify="被保人年龄|int"  readonly ></td>	
				</tr>
				<tr class= common>
					<td class=title>保险方案</td>
					<td class=input><Input class=codeno name=ContPlanCode id=ContPlanCode ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);" readonly><input class=codename name=ContPlanCodeName elementtype=nacessary><input type=hidden name=sysPlanCode ></td>
					<td class=title>职业代码</td>
					<td class=input><input class=codeno name=OccupationCode id=OccupationCode verify="职业代码|notnull" readonly><input class=codename name=OccupationCodeName onkeydown="showOccupationCodeList(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" onkeyup="showOccupationCodeListKey(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" elementtype=nacessary></td>	          
					<td class=title>职业类别</td>
					<td class=input><input type=hidden name=OccupationType verify="职业类别|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('occuptype',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('occuptype',[this,OccupationTypeName],[0,1]);" readonly><input class="wid readonly" name=OccupationTypeName readonly=true elementtype=nacessary></td>
 					<input type=hidden id="date" name=date value="<%=tCurrentDate%>">
 				</tr>
 				<tr class= common>
					<td class= title>邮政编码 </td>
					<td class= input><Input class="wid common" name=ZipCode id=ZipCode  MAXLENGTH="6" verify="被保险人邮政编码|zipcode" ></td> 
					<td class= title>电子邮箱</td>
					<td class= input> <Input class="wid common" name=EMail id=EMail verify="被保险人电子邮箱|Email" ></td>
					<td class= title>微信号</td>
					<td class= input> <Input class="wid common" name=MicroNo id=MicroNo ></td>
				</tr>
				<tr class= common>
					<td class= title>联系电话</td>
					<td class= input><input class="wid common" name=Phone id=Phone verify="联系电话|PHONE"   ></td>
					<td class= title>移动电话 </td>
					<td class= input><Input class="wid common" name=Mobile id=Mobile onblur="checkNumber(this);" verify="被保险人移动电话|len<=11"  ></td>
					<td class= title></td>
					<td class= input></td> 
				</tr>
				<tr class= common>
					<td class=title>详细地址</td>
					<td class=input colspan=5>
						<input class=codeno name=ProvinceName id=ProvinceName style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode readonly>省
						<input class=codeno name=CityName id=CityName style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0]);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0]);"><input type=hidden name=CityCode readonly>市
						<input class=codeno name=CountyName id=CountyName style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0]);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0]);"><input type=hidden name=CountyCode readonly>区/县
						<input class=common name=DetailAddress id=DetailAddress verify="单位详细地址|len<100" maxlength=100 style="width:300px">
					</td>
				</tr>
			</table>
			
			<!--非固定保额-->
		<div id="divQueryShow" style="display: none">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryAmnt);">
					</td>
					<td class=titleImg>非固定保额</td>
				</tr>
			</table>
		<div id="divQueryAmnt" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanAmntGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
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
					<td class=input><input class=codeno name=ServerArea style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=ServiceArName readonly=true></td>
					<td class=title>是否次标准体</td>
					<td class=input><input class=codeno name=Substandard style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=SubstandardName readonly=true></td>
					<td class=title>社保标记</td>
					<td class=input><input class=codeno name=Social value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=SocialName value="无" readonly=true></td>	
				</tr>
				<tr class=common>
					<td class=title>职级</td>
					<td class=input><input class=codeno name=Position readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick= "return initPosition(this,PositionName);" onkeyup="return initPosition(this,PositionName);"><input class=codename name=PositionName readonly></td>
					<td class=title>入司时间</td>
					<td class= input><input class="coolDatePicker"  dateFormat="short" name=JoinCompDate verify="入司时间|date" onClick="laydate({elem: '#JoinCompDate'});" id="JoinCompDate"><span class="icon"><a onClick="laydate({elem: '#JoinCompDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>		
					<td class=title>工龄</td>
					<td class=input><input class="wid common" name=Seniority verify="工龄|int" ></td>
				</tr>
				<tr class=common>	
					<td class=title>月薪（元）</td>
					<td class=input><input class="wid common" name=Salary  verify="月薪|VALUE>=0&LEN<10"></td>
					<td class=title>领取年龄</td>
					<td class=input><input class="wid common" name=GetYear verify="领取年龄|int"></td>
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
					<td class=input><input class=codeno name=HeadBankCode id=HeadBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" ><input class=codename name=BankCodeName></td>
					<td class=title>开户名</td>
					<td class=input><input class="wid common" name=AccName id=AccName verify="账户名称|LEN<=70" ></td>
					<td class=title>账  号</td>
					<td class=input><input class="wid common" name=BankAccNo id=BankAccNo verify="账  号|LEN<=25" onblur="checkNumber(this);"  ></td>		
				</tr>
				<tr class=common>
					<td class=title>开户行所在省</td>
					<td class=input><input class=codeno name=BankProvince id=BankProvince style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1]);" onkeyup="return returnShowCodeListKey('bankprovince',[this, BankProvinceName],[0,1]);" ><input class=codename name="BankProvinceName"></td>
					<td class=title>开户行所在市</td>
					<td class=input><input class=codeno name=BankCity id=BankCity style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" onkeyup="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" ><input class=codename name="BankCityName"></td>
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
		<div id="divQueryOther" class=maxbox style="display: ''">
			<table class=common>
				<tr class= common>
					<td class=title>员工号</td>
					<td class=input><input class="wid common" id=WorkIDNo name=WorkIDNo verify="员工号|len<=20" ></td>
					<td class=title>证件是否长期</td>
					<td class=input><input class=codeno name=ISLongValid style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=ISLongValidName readonly=true></td>
					<td class=title>证件有效期</td>
					<td class=input><Input name=LiscenceValidDate verify="证件有效期|date"  class="coolDatePicker" dateFormat="short" onClick="laydate({elem: '#LiscenceValidDate'});" id="LiscenceValidDate"><span class="icon"><a onClick="laydate({elem: '#LiscenceValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class= common>
					<td class= title>所在分公司</td>
					<td class= input><Input class="common wid"  name=ComponyName id=ComponyName ></td>
					<td class=title>所在部门</td>
					<td class=input><input class="common wid"  name=DeptCode id=DeptCode ></td>
					<td class= title>被保险人编码</td>
					<td class= input><Input class="common wid"  name=InsureCode ></td>
				</tr>
				<tr class=common>
					<!--
					<td class= title>所属客户群</td>
					<td class= input><Input class=codeno name=SubCustomerNo ondblclick="return showCodeList('subcustomerno',[this,SubCustomerName],[0,1],null,'1 and a.customerno=(select appntno from lcgrpcont where grpcontno=#<%=tGrpcontNo%>#) and a.state=#1#','1',1);" onkeyup="return showCodeListKey('subcustomerno',[this,SubCustomerName],[0,1],null,'1 and a.customerno=(select appntno from lcgrpcont where grpcontno=#<%=tGrpcontNo%>#) and a.state=#1#','1',1);" readonly><input class=codename name=SubCustomerName readonly=true></td>
					-->
					<td class=title>工作地</td>
					<td class=input><input class="common wid" name=WorkAddress id=WorkAddress ></td>
					<td class=title>社保地</td>
					<td class=input><input class="common wid" name=SocialAddress id=SocialAddress ></td>
				</tr> 
			</table>
		</div>
	</div>
		
		<!--受益人信息-->
	<div id="divQuerybnf" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
				</td>
				<td class=titleImg>受益人信息</td>
			</tr>
		</table>
		<div id="divQueryInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanBnfGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id=divInsuredInfo2 style="display: none">
		<input class=cssButton type=button value="新  增" onclick="addRecord();">
		<input class=cssButton type=button value="修  改" onclick="modifyRecord();">
	</div>	
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate value="<%=tGI.Operator%>">	
	<input type=hidden name=CurrentDate value="<%=tCurrentDate%>">
	<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- 工作节点ID -->
	<input type=hidden name=EdorAppNo  value="<%=tEdorAppNo%>">
	<input type=hidden name=GrpPropNo value="<%=tGrpcontNo%>" >
	<input type=hidden name=EdorType value="<%=tEdorType%>">	
	<input type=hidden name=EdorNo value= "<%=tEdorNo%>">
	<input type=hidden name=BatchNo>
	<input type=hidden name=AmntFlag>
	
	<input type=hidden name=mainCustNameTemp> <!--主被保人名称备份-->
	<input type=hidden name=InsuredNameTemp>	
	<input type=hidden name=mainInsuredNo>
	<input type=hidden name=Serialno>
	<input type=hidden name=InsuredID>
	<Br /><Br /><Br /><Br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
