<%
/***************************************************************
	 * <p>ProName:EdorACInput.jsp</p>
	 * <p>Title:  Ͷ�������ϱ��</p>
	 * <p>Description:Ͷ�������ϱ��</p>
	 * <p>Copyright��Copyright (c) 2012</p>
	 * <p>Company��Sinosoft</p>
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
	<title>Ͷ�������ϱ��</title>
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
			<td class=titleImg>Ͷ��������</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox style="display: ''">
		<table class=common>
				<tr class=common>
					<td class=title>Ͷ���˱���</td>
					<td class=input><input class="wid readonly" name=AppntNo id=AppntNo></td>
					<td class=title>Ͷ��������</td>
					<td class=input colspan=3><input class="wid common"  name=GrpName id=GrpName verify="Ͷ��������|notnull&len<150" style="width:554px" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>��λ֤������</td>
					<td class=input><input class=codeno name=GrpIDType id=GrpIDType readonly verify="��λ֤������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" >
					<input class=codename name=GrpIDTypeName elementtype=nacessary></td>
					<td class=title>��λ֤������</td>
					<td class=input><input class="wid common" name=GrpID id=GrpID verify="��λ֤������|notnull"  elementtype=nacessary></td>
					<td class=title>��λ֤����Чֹ��</td>
					<td class=input><Input class="coolDatePicker" verify="��λ��Чֹ��|date" dateFormat="short" name=GrpIDExpiryDate></td> 
				</tr>
				<tr class=common>
					<td class=title>Ա������</td>
					<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="Ա������|notnull&int" elementtype=nacessary></td>
					<td class=title>��Ӫ��Χ</td>
					<td class=input colspan=3><input class="wid common" name=MainBusiness id=MainBusiness verify="��Ӫ��Χ|notnull" style="width:554px"  elementtype=nacessary></td>					
				</tr>
				<tr class=common>
					<td class=title>��λ����</td>
					<td class=input><input class=codeno name=GrpNature id=GrpNature verify="��λ����|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);"><input class=codename name=GrpNatureName id=GrpNatureName elementtype=nacessary></td>
					<td class=title>��ҵ����</td>
					<td class=input><input class=codeno name=BusiCategory id=BusiCategory verify="��ҵ����|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);"><input class=codename name=BusiCategoryName id=BusiCategoryName elementtype=nacessary></td>
					<td class=title>��ᱣ�յǼ�֤��</td>
					<td class=input><input class="wid common" name=SocialInsuCode id=SocialInsuCode></td>
				</tr>
				<tr class=common>
					<td class=title>��λ��ϵ�绰</td>
					<td class=input><input class="wid common" name=Phone1 id=Phone1 verify="��λ��ϵ�绰|notnull&PHONE" elementtype=nacessary></td>
					<td class=title>��λ����</td>
					<td class=input><input class="wid common" name=Fax id=Fax></td>
					<td class=title>����</td>
					<td class=input><input class="wid common" name=Corporation id=Corporation></td>
				</tr>
				<tr class=common>
					<td class=title>����֤������</td>
					<td class=input><input class=codeno name=CorIDType id=CorIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=CorIDTypeName id=CorIDTypeName></td>
					<td class=title>����֤������</td>
					<td class=input><input class="wid common" name=CorID id=CorID onblur="checkCoridtype();"></td>
					<td class=title>����֤����Чֹ��</td>
					<td class=input><Input class="coolDatePicker" verify="������Чֹ��|date" dateFormat="short"  name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				</tr>
				<tr class=common>
					<td class=title>��λ��ַ</td>
					<td class=input colspan=3>
						<input class=codeno name=ProvinceName id=ProvinceName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onChange="clearCityAndCounty();" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode id=ProvinceCode readonly>ʡ
						<input class=codeno name=CityName id=CityName style="width:60px"  style="background:url(../common/images/select--bg_03.png) no-repeat right center"ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CityCode id=CityCode readonly>��
						<input class=codeno name=CountyName id=CountyName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CountyCode id=CountyCode readonly>��/��
						<input class="wid common" name=DetailAddress id=DetailAddress verify="��λ��ϸ��ַ|len<100" maxlength=100 style="width:300px" elementtype=nacessary>
					</td>
					<td class=title>��������</td>
					<td class=input><input class="wid common" name=ZipCode id=ZipCode verify="��������|zipcode&notnull"   MAXLENGTH="6"  elementtype=nacessary></td>
				</tr>
		</table>
			<!--��λ��ѡ-->
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>��λ֤����Ϣ�����빴ѡ<input class=checkbox name=IDInfo id=IDInfo type=checkbox onclick="selectIDInfo();"></td>
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
				<td class=title>��ϵ������</td>
				<td class=input><input class="wid common" name=LinkMan id=LinkMan verify="��ϵ������|notnull" elementtype=nacessary></td>
				<td class=title>��ϵ�˵绰</td>
				<td class=input><input class="wid common" name=Phone2 id=Phone2 verify="��ϵ�˵绰|notnull&PHONE" elementtype=nacessary></td>
				<td class=title>��ϵ��E-MAIL</td>
				<td class=input><input class="wid common" name=EMail id=EMail verify="��ϵ��E-mail|EMAIL"></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ���ֻ���</td>
				<td class=input><input class="wid common" name=MobilePhone id=MobilePhone verify="��ϵ�˵绰|num&len<=11"></td>
				<td class=title>��ϵ�˲���</td>
				<td class=input><input class="wid common" name=Department id=Department></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ��֤������</td>
				<td class=input><input class=codeno name=IDType id=IDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=IDTypeName id=IDTypeName></td>
				<td class=title>��ϵ��֤������</td>
				<td class=input><input class="wid common" name=IDNo id=IDNo onblur="checkidtype();"></td>
				<td class=title>��ϵ��֤����Чֹ��</td>
				<td class=input><input class="coolDatePicker" verify="��ϵ��֤����Чֹ��|date" dateFormat="short" name=IDEndDate onClick="laydate({elem: '#IDEndDate'});" id="IDEndDate"><span class="icon"><a onClick="laydate({elem: '#IDEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>����ϵ���빴ѡ<input class=checkbox name=TooContect id=TooContect type=checkbox onclick="selectPeopleInfo();"></td>
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
				<td class=title>��������</td>
				<td class=input><input class=codeno name=HeadBankCode id=HeadBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" ><input class=codename name="BankCodeName" id=BankCodeName></td>
				<td class=title>����������ʡ</td>
					<td class=input><input class=codeno name=BankProvince id=BankProvince style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1]);" onkeyup="return returnShowCodeListKey('bankprovince',[this, BankProvinceName],[0,1]);" ><input class=codename name="BankProvinceName" id=BankProvinceName></td>
					<td class=title>������������</td>
					<td class=input><input class=codeno name=BankCity id=BankCity style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" onkeyup="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" ><input class=codename name="BankCityName" id=BankCityName></td>
			</tr>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=AccName id=AccName verify="�˻�����|LEN<=70"></td>
				<td class=title>�����˺�</td>
				<td class=input><input class="wid common" name=BankAccNo id=BankAccNo verify="��  ��|LEN<=25"></td>		
				<td class=title>���ʽ</td>
				<td class=input><input class=codeno name=PayType id=PayType  verify="���ʽ|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=PayTypeName id=PayTypeName   elementtype=nacessary></td>
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
			<td class=titleImg>Ͷ��������</td>
		</tr>
	</table>
	<div id="divAppntQuery" class=maxbox1 style="display: ''">
		<table class=common>
				<tr class=common>
					<td class=title>Ͷ��������</td>
					<td class=input>
						<input type=hidden name=IAppntNo>
						<input class="wid common" name=AppntName id=AppntName>
					</td>
					<td class=title>֤������</td>
					<td class=input><input class=codeno name=IDType id=IDType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=IDTypeName id=IDTypeName></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=IDNo id= onblur="checkidtype();"></td>
				</tr>
				<tr class=common>
					<td class=title>�Ա�</td>
					<td class=input><input class=codeno name=InsuredGender id=InsuredGender readonly verify="�Ա�|len<=2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('gender',[this,InsuredGenderName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('gender',[this,InsuredGenderName],[0,1],null, null, null, '1', null);"><input class=codename name="InsuredGenderName" id=InsuredGenderName ></td>	
					<td class=title>��������</td>
					<td class=input><input class=coolDatePicker name=InsuredBirthDay dateFormat="short" verify="��������|date" onClick="laydate({elem: '#InsuredBirthDay'});" id="InsuredBirthDay"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>֤����Чֹ��</td>
					<td class=input><Input class="coolDatePicker" verify="֤����Чֹ��|date" dateFormat="short" name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				</tr>
			</table>
		</div>
	</div>
	<div id="divButton01" name=Button01 style="display: none">
		<input class=cssButton type=button value="��  ��" onclick="saveClick();">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	<div id="divButton03" name=Button03 style="display: none">
		<input class=cssButton type=button value="��  ��" onclick="saveAppClick();">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	<div id="divButton02" name=Button02 style="display: none">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	</form>
<form name=fmfra id=fmfra method=post action="" target=fraSubmit>
		<!--������-->
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=GrpPropNo id=GrpPropNo>
	<input type=hidden name=CurrentDate id=CurrentDate>
	<input type=hidden name=EdorAppNo id=EdorAppNo>
	<input type=hidden name=MissionID id=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID id=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID id=ActivityID> <!-- �����ڵ�ID -->
	<input type=hidden name=IDGender id=IDGender>
	<input type=hidden name=IDBirthDay id=IDBirthDay>
	<input type=hidden name=IDFlag id=IDFlag>
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
