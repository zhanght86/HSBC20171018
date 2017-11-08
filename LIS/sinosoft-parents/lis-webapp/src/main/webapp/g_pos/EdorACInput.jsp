<%
/***************************************************************
	 * <p>ProName:EdorACInput.jsp</p>
	 * <p>Title:  投保人资料变更</p>
	 * <p>Description:投保人资料变更</p>
	 * <p>Copyright：Copyright (c) 2012</p>
	 * <p>Company：Sinosoft</p>
	 * @author   : caiyc
	 * @version  : 8.0
	 * @date     : 2014-06-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorType = request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	
%>
<script>
	var tCurrentDate = "<%=tCurrentDate%>";
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
	
</script>
<html>
<head>
	<title>投保人资料变更</title>
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
	<script src="./EdorACInput.js"></script>
	<%@include file="./EdorACInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<span id="spanCodeHelp" style="display:none;"></span>
<form name=fm id=fm method=post action="" target=fraSubmit>
<div id="divGrpInfo" style="display: none">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>投保人资料</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox style="display: ''">
		<table class=common>
				<tr class=common>
					<td class=title>投保人编码</td>
					<td class=input><input class="wid readonly" name=AppntNo id=AppntNo></td>
					<td class=title>投保人名称</td>
					<td class=input colspan=3><input class="wid common"  name=GrpName id=GrpName verify="投保人名称|notnull&len<150" style="width:554px" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>单位证件类型</td>
					<td class=input><input class=codeno name=GrpIDType id=GrpIDType readonly verify="单位证件类型|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" >
					<input class=codename name=GrpIDTypeName elementtype=nacessary></td>
					<td class=title>单位证件号码</td>
					<td class=input><input class="wid common" name=GrpID id=GrpID verify="单位证件号码|notnull"  elementtype=nacessary></td>
					<td class=title>单位证件有效止期</td>
					<td class=input><Input class="coolDatePicker" verify="单位有效止期|date" dateFormat="short" name=GrpIDExpiryDate></td> 
				</tr>
				<tr class=common>
					<td class=title>员工总数</td>
					<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="员工总数|notnull&int" elementtype=nacessary></td>
					<td class=title>经营范围</td>
					<td class=input colspan=3><input class="wid common" name=MainBusiness id=MainBusiness verify="经营范围|notnull" style="width:554px"  elementtype=nacessary></td>					
				</tr>
				<tr class=common>
					<td class=title>单位性质</td>
					<td class=input><input class=codeno name=GrpNature id=GrpNature verify="单位性质|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);"><input class=codename name=GrpNatureName id=GrpNatureName elementtype=nacessary></td>
					<td class=title>行业分类</td>
					<td class=input><input class=codeno name=BusiCategory id=BusiCategory verify="行业分类|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);"><input class=codename name=BusiCategoryName id=BusiCategoryName elementtype=nacessary></td>
					<td class=title>社会保险登记证号</td>
					<td class=input><input class="wid common" name=SocialInsuCode id=SocialInsuCode></td>
				</tr>
				<tr class=common>
					<td class=title>单位联系电话</td>
					<td class=input><input class="wid common" name=Phone1 id=Phone1 verify="单位联系电话|notnull&PHONE" elementtype=nacessary></td>
					<td class=title>单位传真</td>
					<td class=input><input class="wid common" name=Fax id=Fax></td>
					<td class=title>法人</td>
					<td class=input><input class="wid common" name=Corporation id=Corporation></td>
				</tr>
				<tr class=common>
					<td class=title>法人证件类型</td>
					<td class=input><input class=codeno name=CorIDType id=CorIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=CorIDTypeName id=CorIDTypeName></td>
					<td class=title>法人证件号码</td>
					<td class=input><input class="wid common" name=CorID id=CorID onblur="checkCoridtype();"></td>
					<td class=title>法人证件有效止期</td>
					<td class=input><Input class="coolDatePicker" verify="法人有效止期|date" dateFormat="short"  name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				</tr>
				<tr class=common>
					<td class=title>单位地址</td>
					<td class=input colspan=3>
						<input class=codeno name=ProvinceName id=ProvinceName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onChange="clearCityAndCounty();" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode id=ProvinceCode readonly>省
						<input class=codeno name=CityName id=CityName style="width:60px"  style="background:url(../common/images/select--bg_03.png) no-repeat right center"ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CityCode id=CityCode readonly>市
						<input class=codeno name=CountyName id=CountyName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CountyCode id=CountyCode readonly>区/县
						<input class="wid common" name=DetailAddress id=DetailAddress verify="单位详细地址|len<100" maxlength=100 style="width:300px" elementtype=nacessary>
					</td>
					<td class=title>邮政编码</td>
					<td class=input><input class="wid common" name=ZipCode id=ZipCode verify="邮政编码|zipcode&notnull"   MAXLENGTH="6"  elementtype=nacessary></td>
				</tr>
		</table>
			<!--单位勾选-->
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>单位证件信息设置请勾选<input class=checkbox name=IDInfo id=IDInfo type=checkbox onclick="selectIDInfo();"></td>
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
				<td class=input><input class="wid common" name=LinkMan id=LinkMan verify="联系人姓名|notnull" elementtype=nacessary></td>
				<td class=title>联系人电话</td>
				<td class=input><input class="wid common" name=Phone2 id=Phone2 verify="联系人电话|notnull&PHONE" elementtype=nacessary></td>
				<td class=title>联系人E-MAIL</td>
				<td class=input><input class="wid common" name=EMail id=EMail verify="联系人E-mail|EMAIL"></td>
			</tr>
			<tr class=common>
				<td class=title>联系人手机号</td>
				<td class=input><input class="wid common" name=MobilePhone id=MobilePhone verify="联系人电话|num&len<=11"></td>
				<td class=title>联系人部门</td>
				<td class=input><input class="wid common" name=Department id=Department></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>联系人证件类型</td>
				<td class=input><input class=codeno name=IDType id=IDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=IDTypeName id=IDTypeName></td>
				<td class=title>联系人证件号码</td>
				<td class=input><input class="wid common" name=IDNo id=IDNo onblur="checkidtype();"></td>
				<td class=title>联系人证件有效止期</td>
				<td class=input><input class="coolDatePicker" verify="联系人证件有效止期|date" dateFormat="short" name=IDEndDate onClick="laydate({elem: '#IDEndDate'});" id="IDEndDate"><span class="icon"><a onClick="laydate({elem: '#IDEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>多联系人请勾选<input class=checkbox name=TooContect id=TooContect type=checkbox onclick="selectPeopleInfo();"></td>
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
				<td class=input><input class=codeno name=HeadBankCode id=HeadBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" ><input class=codename name="BankCodeName" id=BankCodeName></td>
				<td class=title>开户行所在省</td>
					<td class=input><input class=codeno name=BankProvince id=BankProvince style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1]);" onkeyup="return returnShowCodeListKey('bankprovince',[this, BankProvinceName],[0,1]);" ><input class=codename name="BankProvinceName" id=BankProvinceName></td>
					<td class=title>开户行所在市</td>
					<td class=input><input class=codeno name=BankCity id=BankCity style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" onkeyup="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" ><input class=codename name="BankCityName" id=BankCityName></td>
			</tr>
			<tr class=common>
				<td class=title>开户名</td>
				<td class=input><input class="wid common" name=AccName id=AccName verify="账户名称|LEN<=70"></td>
				<td class=title>银行账号</td>
				<td class=input><input class="wid common" name=BankAccNo id=BankAccNo verify="账  号|LEN<=25"></td>		
				<td class=title>付款方式</td>
				<td class=input><input class=codeno name=PayType id=PayType  verify="付款方式|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=PayTypeName id=PayTypeName   elementtype=nacessary></td>
			</tr>
		</table>
	</div>
</div>
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
<div id="divAppInfo" style="display: ''">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAppntQuery);">
			</td>
			<td class=titleImg>投保人资料</td>
		</tr>
	</table>
	<div id="divAppntQuery" class=maxbox1 style="display: ''">
		<table class=common>
				<tr class=common>
					<td class=title>投保人名称</td>
					<td class=input>
						<input type=hidden name=IAppntNo>
						<input class="wid common" name=AppntName id=AppntName>
					</td>
					<td class=title>证件类型</td>
					<td class=input><input class=codeno name=IDType id=IDType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=IDTypeName id=IDTypeName></td>
					<td class=title>证件号码</td>
					<td class=input><input class="wid common" name=IDNo id= onblur="checkidtype();"></td>
				</tr>
				<tr class=common>
					<td class=title>性别</td>
					<td class=input><input class=codeno name=InsuredGender id=InsuredGender readonly verify="性别|len<=2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('gender',[this,InsuredGenderName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('gender',[this,InsuredGenderName],[0,1],null, null, null, '1', null);"><input class=codename name="InsuredGenderName" id=InsuredGenderName ></td>	
					<td class=title>出生日期</td>
					<td class=input><input class=coolDatePicker name=InsuredBirthDay dateFormat="short" verify="出生日期|date" onClick="laydate({elem: '#InsuredBirthDay'});" id="InsuredBirthDay"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>证件有效止期</td>
					<td class=input><Input class="coolDatePicker" verify="证件有效止期|date" dateFormat="short" name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				</tr>
			</table>
		</div>
	</div>
	<div id="divButton01" name=Button01 style="display: none">
		<input class=cssButton type=button value="保  存" onclick="saveClick();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	<div id="divButton03" name=Button03 style="display: none">
		<input class=cssButton type=button value="保  存" onclick="saveAppClick();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	<div id="divButton02" name=Button02 style="display: none">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	</form>
<form name=fmfra id=fmfra method=post action="" target=fraSubmit>
		<!--隐藏区-->
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=GrpPropNo id=GrpPropNo>
	<input type=hidden name=CurrentDate id=CurrentDate>
	<input type=hidden name=EdorAppNo id=EdorAppNo>
	<input type=hidden name=MissionID id=MissionID> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID id=SubMissionID> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID id=ActivityID> <!-- 工作节点ID -->
	<input type=hidden name=IDGender id=IDGender>
	<input type=hidden name=IDBirthDay id=IDBirthDay>
	<input type=hidden name=IDFlag id=IDFlag>
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
