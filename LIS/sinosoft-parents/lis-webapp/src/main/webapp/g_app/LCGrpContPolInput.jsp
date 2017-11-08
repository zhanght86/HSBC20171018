<%
/***************************************************************
 * <p>ProName：LCContPolInput.jsp</p>
 * <p>Title：新单录入</p>
 * <p>Description：新单录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-28
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tFlag = request.getParameter("Flag");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tContPlanType = request.getParameter("ContPlanType");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");

	String tCurrentDate = PubFun.getCurrentDate();
	String tCurrentTime = PubFun.getCurrentTime();

%>
<style type="text/css">
	#MessageTable{ position:absolute; background-color:blue;}
</style>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//记录登陆机构
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tContPlanType = "<%=tContPlanType%>";
	var	tActivityID = "<%=tActivityID%>";
	var	tCurrentDate = "<%=tCurrentDate%>";
	var	tCurrentTime = "<%=tCurrentTime%>";
	var scantype = "<%=request.getParameter("ScanFlag")%>";//判断是否做随动 1-随动，2-随动定制
	if(scantype==null||scantype=="") {
		scantype = "0";
	}
	//如果scantype = 2为随动定制，不加载界面查询，需要对该界面做处理
</script>
<html>
<head>
	<title>新单录入</title>
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
	<script src="./LCGrpContPolInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCGrpContPolInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">

<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>基本投保信息</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom readonly style="display: none" verify="承保机构|notnull" ondblclick=" returnShowCodeList('comcodeall',[this,ManageComName],[0,1],null,'1 and comgrade=#03# ',1,'1',180);" onkeyup="return returnShowCodeListKey('comcodeall',[this,ManageComName],[0,1],null,'1 and comgrade=#03#',1,'1',180);"><input class="wid readonly" readonly name=ManageComName elementtype=nacessary></td>
				<td class=title>投保单号</td>
				<td class=input><input class="wid readonly" name=GrpPropNo id=GrpPropNo></td>
				<td class=title>销售部门</td>
				<td class=input><input class=codeno name=SaleDepart id=SaleDepart readonly verify="销售部门|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('salebranch',[this,SaleDepartName],[0,1],null,'1 and othersign=#1# ','1','1',null);" onkeyup="return showCodeListKey('salebranch',[this,SaleDepartName],[0,1],null,'1 and othersign=#1# ','1','1',null);"><input class=codename name=SaleDepartName elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>渠道类型</td>
				<td class=input><input class=codeno name=ChnlType id=ChnlType readonly verify="渠道类型|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ChnlTypeName elementtype=nacessary></td>
				<td class=title>销售渠道</td>
				<td class=input><input class=codeno name=SaleChnl id=SaleChnl readonly verify="销售渠道|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);"><input class=codename name=SaleChnlName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>投保申请日期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=PolicyAppDate verify="投保申请日期|notnull&date" elementtype=nacessary onClick="laydate({elem: '#PolicyAppDate'});" id="PolicyAppDate"><span class="icon"><a onClick="laydate({elem: '#PolicyAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>生效日期类型</td>
				<td class=input><input class=codeno name=ValDateType verify="生效日期类型|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ValdateTypeName readonly=true elementtype=nacessary></td>
				<td class=title><div id=valDatename style="display: ''">生效日期</div></td>
				<TD class=input><div id=valDatename1 style="display: ''"><Input class="coolDatePicker" dateFormat="short"  name=ValDate verify="生效日期|date" onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color="#ff0000">*</font></div></TD>
			</tr>
			<tr class=common>
				<td class=title>是否续保</td>
				<td class=input><input class=codeno name=RenewFlag readonly value="0"style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick=" showCodeList('trueflag',[this,RenewFlagName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('trueflag',[this,RenewFlagName],[0,1],null,null,null,'1',null);"><input class=codename name=RenewFlagName  value="否" readonly=true ></td>
				<td class=title>缴费方式</td>
				<td class=input><input class=codeno name=PayintvType readonly verify="缴费方式|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('payintv',[this,payintvName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('payintv',[this,payintvName],[0,1],null,null,null,'1',null);"><input class=codename name=payintvName readonly=true elementtype=nacessary></td>
				<td class=title>保险期间</td>
				<td class=input><input class="readonly" style="width:60px" name=InsuPeriod readonly verify="保险期间|notnull&int&value>0" ><input class="readonly" name=InsuPeriodFlagName verify="保险期间单位|notnull" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag',[this, InsuPeriodFlag],[1, 0],null,null,null,'1',null);" onkeyup="return showCodeListKey('insuperiodflag',[this, InsuPeriodFlag],[1,0],null,null,null,'1',null);"><input type=hidden name=InsuPeriodFlag  elementtype=nacessary readonly></td>
			</tr>
			<tr class=common>
				<td class=title>是否共保</td>
				<td class=input><input class=codeno name=Coinsurance value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('trueflag',[this,CoinsuranceName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('trueflag',[this,CoinsuranceName],[0,1],null,null,null,'1',null);"><input class=codename name=CoinsuranceName  value="否" readonly=true ></td>
				<td class=title><input class=cssButton type=button value="共保设置" onclick="showCoinsurance();"></td>
				<td class=input></td>
				<td class=title>保单类型</td>
				<td class=input><input class="wid readonly" name=PolicyFlagName id=PolicyFlagName></td>
			</tr>
		</table>
		<table class=common id=pricingmode style="display: none">
			<tr class=common>
				<td class=title>计价方式</td>
				<td class=input><input class=codeno name=PricingMode id=PricingMode readonly=true style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('pricingmode',[this,PricingModeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('pricingmode',[this,PricingModeName],[0,1],null,null,null,'1',null);"><input class=codename name=PricingModeName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title colspan=6><input class=checkbox name=BusinessArea type=checkbox onclick="showBusinessArea();">增加业绩归属地请勾选<input class=checkbox name=AgentCom type=checkbox onclick="showAgentCom();">中介机构请勾选
			</tr>
		</table>
	<div id="divAgentDetailInfo" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAgentDetail);">
				</td>
				<td class=titleImg>承保地客户经理</td>
			</tr>
		</table>
		<div id="divAgentDetail" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanAgentDetailGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="divBusinessAreaInfo" name=divBusinessAreaInfo style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBusinessAreaList);">
				</td>
				<td class=titleImg>业绩归属地</td>
			</tr>
		</table>
		<div id="divBusinessAreaList"  style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanBusinessAreaGrid" ></span>
						</td>
					</tr>
				</table>
			</div>
	</div>
	<div id="divAgentComInfo" name=divAgentComInfo style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAgentComList);">
				</td>
				<td class=titleImg>中介机构</td>
			</tr>
		</table>
		<div id="divAgentComList"  style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanAgentComGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="divSalerInfo" name=divSalerInfo style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSalerList);">
				</td>
				<td class=titleImg>代理人</td>
			</tr>
		</table>
		<div id="divSalerList"  style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSalerInfoGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInsuredInfo);">
				</td>
				<td class=titleImg>投保人资料</td>
			</tr>
		</table>
		<div id="divInsuredInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>投保人编码</td>
					<td class=input><input class="wid readonly" name=AppntNo></td>
					<td class=title>投保人名称</td>
					<td class=input colspan=3><input class="wid common"  name=GrpName verify="投保人名称|notnull&len<60" style="width:554px"  elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>单位证件类型</td>
					<td class=input><input class=codeno name=GrpIDType readonly verify="单位证件类型|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename readonly name=GrpIDTypeName elementtype=nacessary></td>
					<td class=title>单位证件号码</td>
					<td class=input><input class="wid common" name=GrpID verify="单位证件号码|notnull" elementtype=nacessary></td>
					<td class=title>单位证件有效止期</td>
					<td class=input><Input class="coolDatePicker" verify="单位有效止期|date" dateFormat="short" name=GrpIDExpiryDate onClick="laydate({elem: '#GrpIDExpiryDate'});" id="GrpIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#GrpIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class=common>
					<td class=title>员工总数</td>
					<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="员工总数|notnull&int" elementtype=nacessary></td>
					<td class=title>经营范围</td>
					<td class=input colspan=3><input class="wid common" name=MainBusiness verify="经营范围|notnull&len<500" style="width:554px"  elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>单位性质</td>
					<td class=input><input class=codeno name=GrpNature verify="单位性质|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);"><input class=codename name=GrpNatureName elementtype=nacessary></td>
					<td class=title>行业分类</td>
					<td class=input><input class=codeno name=BusiCategory verify="行业分类|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);"><input class=codename name=BusiCategoryName elementtype=nacessary></td>
					<td class=title>社会保险登记证号</td>
					<td class=input><input class="wid common" name=SocialInsuCode verify="社会保险登记证号|len<30" ></td>
				</tr>
				<tr class=common>
					<td class=title>单位联系电话</td>
					<td class=input><input class="wid common" name=Phone1 verify="单位联系电话|PHONE&notnull&len<20" elementtype=nacessary></td>
					<td class=title>单位传真</td>
					<td class=input><input class="wid common" name=Fax verify="单位传真|PHONE&len<20"></td>
					<td class=title>法人代表</td>
					<td class=input><input class="wid common" name=Corporation verify="法人代表|len<40"></td>
				</tr>
				<tr class=common>
					<td class=title>法人代表证件类型</td>
					<td class=input><input class=codeno name=CorIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=CorIDTypeName></td>
					<td class=title>法人代表证件号码</td>
					<td class=input><input class="wid common" name=CorID id=CorID onblur="checkCoridtype();"></td>
					<td class=title>法人代表证件有效止期</td>
					<td class=input><Input class="coolDatePicker" verify="法人有效止期|date" dateFormat="short"  name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class=common>
					<td class=title>单位地址</td>
					<td class=input colspan=3>
						<input class=codeno name=ProvinceName style="width:60px"  onChange="clearCityAndCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode readonly>省
						<input class=codeno name=CityName style="width:60px" onChange="clearCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CityCode readonly>市
						<input class=codeno name=CountyName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CountyCode readonly>区/县
						<input class=common name=DetailAddress verify="单位详细地址|len<100" maxlength=100 style="width:300px"  elementtype=nacessary>
					</td>
					<td class=title>邮政编码</td>
					<td class=input><input class="wid common" name=ZipCode verify="邮政编码|zipcode&notnull"   MAXLENGTH="6"  elementtype=nacessary></td>
				</tr>
		</table>
			<!--单位勾选-->
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>单位多证件信息设置请勾选<input class=checkbox name=IDInfo type=checkbox onclick="selectIDInfo();"></td>
			</tr>
		</table>
		<div id="divIDInfo" name=divIDInfo style="display: none">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanIDInfoGrid" ></span>
					</td>
				</tr>
			</table>
			<br/>
		</div>
		<table class=common>
			<tr class=common>
				<td class=title>联系人姓名</td>
				<td class=input><input class="wid common" name=LinkMan verify="联系人姓名|notnull&len<20" elementtype=nacessary></td>
				<td class=title>联系人电话</td>
				<td class=input><input class="wid common" name=Phone2 verify="联系人电话|PHONE&notnull" elementtype=nacessary></td>
				<td class=title>联系人E-MAIL</td>
				<td class=input><input class="wid common" name=EMail verify="联系人E-mail|EMAIL"></td>
			</tr>
			<tr class=common>
				<td class=title>联系人手机号</td>
				<td class=input><input class="wid common" name=MobilePhone verify="联系人电话|num&len<=11"></td>
				<td class=title>联系人部门</td>
				<td class=input><input class="wid common" name=Department></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>联系人证件类型</td>
				<td class=input><input class=codeno name=IDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=IDTypeName></td>
				<td class=title>联系人证件号码</td>
				<td class=input><input class="wid common" name=IDNo onblur="checkidtype();"></td>
				<td class=title>联系人证件有效止期</td>
				<td class=input><input class="coolDatePicker" verify="联系人证件有效止期|date" dateFormat="short" name=IDEndDate ></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>多联系人请勾选<input class=checkbox name=TooContect type=checkbox onclick="selectPeopleInfo();"></td>
			</tr>
		</table>
		<div id="divLinkPeopleInfo" name=divLinkPeopleInfo style="display: none">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanTooContectGrid" ></span>
					</td>
				</tr>
			</table>
			<br/>
		</div>
		<table class=common>
				<tr class=common>
					<td class=title>开户银行</td>
					<td class=input><input class=codeno name="BankCode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" ><input class=codename name="BankCodeName"></td>
					<td class=title>开户行所在省</td>
					<td class=input><input class=codeno name="BankProvince" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onChange="clearBankCity();" ondblclick="clearInput(BankCity,BankCityName);returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1],null,null,null,'1',null);"><input class=codename name=BankProvinceName></td>
					<td class=title>开户行所在市</td>
					<td class=input><input class=codeno name="BankCity" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="checkBankProvince();returnShowCodeList('bankcity',[this, BankCityName],[0,1],null,null,null,'1',null);" onkeyup="checkBankProvince();returnShowCodeList('bankcity',[this, BankCityName],[0,1],null,null,null,'1',null);;"><input class=codename name=BankCityName></td>
				</tr>
				<tr class=common>
					<td class=title>开户名</td>
					<td class=input><input class="wid common" name="AccName" verify="账户名称|LEN<=70"></td>
					<td class=title>银行账号</td>
					<td class=input><input class="wid common" name="BankAccNo" verify="账  号|LEN<=25"></td>
					<td class=title>付款方式</td>
					<td class=input><input class=codeno name=PayType  verify="付款方式|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=PayTypeName   elementtype=nacessary></td>
				</tr>
		</table>
	</div>
	<!-- 工程基本信息 -->
	<div id="divBasicJGInfo" style="display: ''">
	<table id=EngTable>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBasicInfo);">
			</td>
			<td class=titleImg>工程基本信息</td>
		</tr>
	</table>
	<div id="divBasicInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>工程名称</td>
				<td class=input><input class="wid common" name=EnginName  elementtype=nacessary></td>
				<td class=title>施工方名称</td>
				<td class=input><input class="wid common" name=ContractorName   elementtype=nacessary></td>
				<td class=title>施工方资质</td>
				<td class=input><input class=codeno name=ContractorType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('contractortype',[this,ContractorTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('contractortype',[this,ContractorTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ContractorTypeName   elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>工程地点</td>
				<td class=input><input class="wid common" name=EnginPlace  elementtype=nacessary></td>
				<td class=title colspan=2>涉及海上、水下、高空、爆破及井下作业,请勾选 <input type=checkbox name=EnginCondition></td>
				<td class=title>详细描述</td>
				<td class=input><input class="wid common" name=DetailDes></td>
			</tr>
			<tr class=common>
				<td class=title>工程起期</td>
				<td class=input><input class="coolDatePicker"   dateFormat="short"  name="EnginStartDate" onClick="laydate({elem: '#EnginStartDate'});" id="EnginStartDate"><span class="icon"><a onClick="laydate({elem: '#EnginStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>工程止期</td>
				<td class=input><input class="coolDatePicker"   dateFormat="short"  name="EnginEndDate" onClick="laydate({elem: '#EnginEndDate'});" id="EnginEndDate"><span class="icon"><a onClick="laydate({elem: '#EnginEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>工程造价(元)</td>
				<td class=input><input class="wid common" name=EnginCost></td>
			</tr>
				<tr class=common>
				<td class=title>建筑面积(平方米)</td>
				<td class=input><input class="wid common"  name=EnginArea id=EnginArea ></td>
				<td class=title>工程类型</td>
				<td class=input><input class=codeno name=EnginType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);"><input class=codename name=EnginTypeName readonly elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
	<div id=divEnginFactor>
	</div>
	<table id=InsuredTable>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfok);">
			</td>
			<td class=titleImg>投保资料及保益信息</td>
		</tr>
	</table>
	<div id="divInfok" class=maxbox1 style="display: ''">
		<table class=common id=PremTable>
			<tr class=common>
				<td class=title>保费计算方式</td>
				<td class=input><input type=hidden name=PremCalMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engincaltype',[this,PremCalModeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('engincaltype',[this,PremCalModeName],[0,1],null,null,null,'1',null);"><input class="wid readonly" name=PremCalModeName ></td>
				<td class=title>保险费率</td>
				<td class=input><input class="wid common" name=PremFeeRate></td>
				<td class=title>趸交保费</td>
				<td class=input><input class="wid common" name=FirstPrem elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>施工人数</td>
				<td class=input><input class="wid common" name=ContractorPeoples elementtype=nacessary></td>
				<td class=title colspan=4>如理赔不能提供当地安监证明,请勾选<input type=checkbox  name=SafeProve></td>
			</tr>
		</table>
		<table class=common>
			<tr class= common>
				<td class= title> 特别约定<font color="red">&nbsp;&nbsp;(注意：若无特别约定请注明：无特别约定；若有，请在特别约定结束处注明： 除此之外无其他特别约定)</font></td>
			</tr>
			<tr  class= common>
				<td  class=title>
					<textarea name="GrpSpec" id=GrpSpec cols="110" rows="3" class="common" ></textarea>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<!--建工险种 END-->
	<!--关联交易 END-->
	<div align="left" id="divRelaPay" style="display: ''">
		<span id='spanRelaPay'></span>
	</div>
	<div align="right">
		<input class=cssButton name=SaveCont  style="display: none" type=button value="保  存" onclick="saveClick();">
	</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRiskInfo);">
				</td>
				<td class=titleImg>投保险种信息</td>
			</tr>
		</table>
		<div id="divRiskInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskGrid" ></span>
					</td>
				</tr>
			</table>
			<div id="divQueryInfoPage" style="display: none">
				<center>
					<input class=cssButton90 type=button value="首  页" onclick="turnPage7.firstPage();">
					<input class=cssButton91 type=button value="上一页" onclick="turnPage7.previousPage();">
					<input class=cssButton92 type=button value="下一页" onclick="turnPage7.nextPage();">
					<input class=cssButton93 type=button value="尾  页" onclick="turnPage7.lastPage();">
				</center>
			</div>
		</div>
		<div align="right">
			<table class=common>
				<tr class=common>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title>总保费</td>
					<td class=input><input class="wid readonly" name=SumPrems id=SumPrems readonly></td>
				</tr>
			</table>
		</div>
	<hr class=line />
		<input class=cssButton type=button value="被保险人清单核对" onclick="goToInsuredList();">
		<input class=cssButton type=button value="中介手续费率维护" onclick="goToChargeFee();">
		<input class=cssButton type=button value="定期结算维护" onclick="goToBalance();">
		<input class=cssButton type=button value="附件管理" onclick="showAttachment();">
		<div id="divContPlanQuery1" style="display: '';">
			<input class=cssButton id=qutButton type=button value="投保方案查询" onclick="showPlanQuery();">
			<input class=cssButton name=ClaimDuty  style="display: none" value="理赔责任控制" type=button onclick="claimDutyControl();">
		</div>
	<hr class=line />
		<div align="right">
			<input class=cssButton name=Question style="display: none" type=button value="问题件处理" onclick="goToQuestion();">
			<input class=cssButton name=Approve style="display: none" type=button value="复核完毕" onclick="saveApproveClick();">
			<input class=cssButton name=GrpCont style="display: none" type=button value="录入完毕" onclick="savePolicyClick();">
			<input class=cssButton type=button value="关  闭" onclick="top.close();">
		</div>
		<input type=hidden name=Operate>
		<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- 工作任务ID -->
		<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- 子工作任务ID -->
		<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- 工作节点ID -->
		<input type=hidden name=ContPlanType value="<%=tContPlanType%>">

		<!--获取当前时间信息-->
		<input type=hidden name=tCurrentDate value="<%=tCurrentDate%>">
		<input type=hidden name=tCurrentTime value="<%=tCurrentTime%>">
		<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
