<%
/***************************************************************
 * <p>ProName��LCContPolInput.jsp</p>
 * <p>Title���µ�¼��</p>
 * <p>Description���µ�¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
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
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tContPlanType = "<%=tContPlanType%>";
	var	tActivityID = "<%=tActivityID%>";
	var	tCurrentDate = "<%=tCurrentDate%>";
	var	tCurrentTime = "<%=tCurrentTime%>";
	var scantype = "<%=request.getParameter("ScanFlag")%>";//�ж��Ƿ����涯 1-�涯��2-�涯����
	if(scantype==null||scantype=="") {
		scantype = "0";
	}
	//���scantype = 2Ϊ�涯���ƣ������ؽ����ѯ����Ҫ�Ըý���������
</script>
<html>
<head>
	<title>�µ�¼��</title>
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
			<td class=titleImg>����Ͷ����Ϣ</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�б�����</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom readonly style="display: none" verify="�б�����|notnull" ondblclick=" returnShowCodeList('comcodeall',[this,ManageComName],[0,1],null,'1 and comgrade=#03# ',1,'1',180);" onkeyup="return returnShowCodeListKey('comcodeall',[this,ManageComName],[0,1],null,'1 and comgrade=#03#',1,'1',180);"><input class="wid readonly" readonly name=ManageComName elementtype=nacessary></td>
				<td class=title>Ͷ������</td>
				<td class=input><input class="wid readonly" name=GrpPropNo id=GrpPropNo></td>
				<td class=title>���۲���</td>
				<td class=input><input class=codeno name=SaleDepart id=SaleDepart readonly verify="���۲���|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('salebranch',[this,SaleDepartName],[0,1],null,'1 and othersign=#1# ','1','1',null);" onkeyup="return showCodeListKey('salebranch',[this,SaleDepartName],[0,1],null,'1 and othersign=#1# ','1','1',null);"><input class=codename name=SaleDepartName elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=ChnlType id=ChnlType readonly verify="��������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ChnlTypeName elementtype=nacessary></td>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=SaleChnl id=SaleChnl readonly verify="��������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);"><input class=codename name=SaleChnlName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>Ͷ����������</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=PolicyAppDate verify="Ͷ����������|notnull&date" elementtype=nacessary onClick="laydate({elem: '#PolicyAppDate'});" id="PolicyAppDate"><span class="icon"><a onClick="laydate({elem: '#PolicyAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��Ч��������</td>
				<td class=input><input class=codeno name=ValDateType verify="��Ч��������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ValdateTypeName readonly=true elementtype=nacessary></td>
				<td class=title><div id=valDatename style="display: ''">��Ч����</div></td>
				<TD class=input><div id=valDatename1 style="display: ''"><Input class="coolDatePicker" dateFormat="short"  name=ValDate verify="��Ч����|date" onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color="#ff0000">*</font></div></TD>
			</tr>
			<tr class=common>
				<td class=title>�Ƿ�����</td>
				<td class=input><input class=codeno name=RenewFlag readonly value="0"style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick=" showCodeList('trueflag',[this,RenewFlagName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('trueflag',[this,RenewFlagName],[0,1],null,null,null,'1',null);"><input class=codename name=RenewFlagName  value="��" readonly=true ></td>
				<td class=title>�ɷѷ�ʽ</td>
				<td class=input><input class=codeno name=PayintvType readonly verify="�ɷѷ�ʽ|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('payintv',[this,payintvName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('payintv',[this,payintvName],[0,1],null,null,null,'1',null);"><input class=codename name=payintvName readonly=true elementtype=nacessary></td>
				<td class=title>�����ڼ�</td>
				<td class=input><input class="readonly" style="width:60px" name=InsuPeriod readonly verify="�����ڼ�|notnull&int&value>0" ><input class="readonly" name=InsuPeriodFlagName verify="�����ڼ䵥λ|notnull" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag',[this, InsuPeriodFlag],[1, 0],null,null,null,'1',null);" onkeyup="return showCodeListKey('insuperiodflag',[this, InsuPeriodFlag],[1,0],null,null,null,'1',null);"><input type=hidden name=InsuPeriodFlag  elementtype=nacessary readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�Ƿ񹲱�</td>
				<td class=input><input class=codeno name=Coinsurance value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('trueflag',[this,CoinsuranceName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('trueflag',[this,CoinsuranceName],[0,1],null,null,null,'1',null);"><input class=codename name=CoinsuranceName  value="��" readonly=true ></td>
				<td class=title><input class=cssButton type=button value="��������" onclick="showCoinsurance();"></td>
				<td class=input></td>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=PolicyFlagName id=PolicyFlagName></td>
			</tr>
		</table>
		<table class=common id=pricingmode style="display: none">
			<tr class=common>
				<td class=title>�Ƽ۷�ʽ</td>
				<td class=input><input class=codeno name=PricingMode id=PricingMode readonly=true style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('pricingmode',[this,PricingModeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('pricingmode',[this,PricingModeName],[0,1],null,null,null,'1',null);"><input class=codename name=PricingModeName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title colspan=6><input class=checkbox name=BusinessArea type=checkbox onclick="showBusinessArea();">����ҵ���������빴ѡ<input class=checkbox name=AgentCom type=checkbox onclick="showAgentCom();">�н�����빴ѡ
			</tr>
		</table>
	<div id="divAgentDetailInfo" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAgentDetail);">
				</td>
				<td class=titleImg>�б��ؿͻ�����</td>
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
				<td class=titleImg>ҵ��������</td>
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
				<td class=titleImg>�н����</td>
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
				<td class=titleImg>������</td>
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
				<td class=titleImg>Ͷ��������</td>
			</tr>
		</table>
		<div id="divInsuredInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>Ͷ���˱���</td>
					<td class=input><input class="wid readonly" name=AppntNo></td>
					<td class=title>Ͷ��������</td>
					<td class=input colspan=3><input class="wid common"  name=GrpName verify="Ͷ��������|notnull&len<60" style="width:554px"  elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>��λ֤������</td>
					<td class=input><input class=codeno name=GrpIDType readonly verify="��λ֤������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename readonly name=GrpIDTypeName elementtype=nacessary></td>
					<td class=title>��λ֤������</td>
					<td class=input><input class="wid common" name=GrpID verify="��λ֤������|notnull" elementtype=nacessary></td>
					<td class=title>��λ֤����Чֹ��</td>
					<td class=input><Input class="coolDatePicker" verify="��λ��Чֹ��|date" dateFormat="short" name=GrpIDExpiryDate onClick="laydate({elem: '#GrpIDExpiryDate'});" id="GrpIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#GrpIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class=common>
					<td class=title>Ա������</td>
					<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="Ա������|notnull&int" elementtype=nacessary></td>
					<td class=title>��Ӫ��Χ</td>
					<td class=input colspan=3><input class="wid common" name=MainBusiness verify="��Ӫ��Χ|notnull&len<500" style="width:554px"  elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>��λ����</td>
					<td class=input><input class=codeno name=GrpNature verify="��λ����|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);"><input class=codename name=GrpNatureName elementtype=nacessary></td>
					<td class=title>��ҵ����</td>
					<td class=input><input class=codeno name=BusiCategory verify="��ҵ����|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('category',[this,BusiCategoryName],[0,1],null,null,null,'1',null);"><input class=codename name=BusiCategoryName elementtype=nacessary></td>
					<td class=title>��ᱣ�յǼ�֤��</td>
					<td class=input><input class="wid common" name=SocialInsuCode verify="��ᱣ�յǼ�֤��|len<30" ></td>
				</tr>
				<tr class=common>
					<td class=title>��λ��ϵ�绰</td>
					<td class=input><input class="wid common" name=Phone1 verify="��λ��ϵ�绰|PHONE&notnull&len<20" elementtype=nacessary></td>
					<td class=title>��λ����</td>
					<td class=input><input class="wid common" name=Fax verify="��λ����|PHONE&len<20"></td>
					<td class=title>���˴���</td>
					<td class=input><input class="wid common" name=Corporation verify="���˴���|len<40"></td>
				</tr>
				<tr class=common>
					<td class=title>���˴���֤������</td>
					<td class=input><input class=codeno name=CorIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=CorIDTypeName></td>
					<td class=title>���˴���֤������</td>
					<td class=input><input class="wid common" name=CorID id=CorID onblur="checkCoridtype();"></td>
					<td class=title>���˴���֤����Чֹ��</td>
					<td class=input><Input class="coolDatePicker" verify="������Чֹ��|date" dateFormat="short"  name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class=common>
					<td class=title>��λ��ַ</td>
					<td class=input colspan=3>
						<input class=codeno name=ProvinceName style="width:60px"  onChange="clearCityAndCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode readonly>ʡ
						<input class=codeno name=CityName style="width:60px" onChange="clearCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CityCode readonly>��
						<input class=codeno name=CountyName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0],null,null,null,'1',null);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0],null,null,null,'1',null);"><input type=hidden name=CountyCode readonly>��/��
						<input class=common name=DetailAddress verify="��λ��ϸ��ַ|len<100" maxlength=100 style="width:300px"  elementtype=nacessary>
					</td>
					<td class=title>��������</td>
					<td class=input><input class="wid common" name=ZipCode verify="��������|zipcode&notnull"   MAXLENGTH="6"  elementtype=nacessary></td>
				</tr>
		</table>
			<!--��λ��ѡ-->
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>��λ��֤����Ϣ�����빴ѡ<input class=checkbox name=IDInfo type=checkbox onclick="selectIDInfo();"></td>
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
				<td class=input><input class="wid common" name=LinkMan verify="��ϵ������|notnull&len<20" elementtype=nacessary></td>
				<td class=title>��ϵ�˵绰</td>
				<td class=input><input class="wid common" name=Phone2 verify="��ϵ�˵绰|PHONE&notnull" elementtype=nacessary></td>
				<td class=title>��ϵ��E-MAIL</td>
				<td class=input><input class="wid common" name=EMail verify="��ϵ��E-mail|EMAIL"></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ���ֻ���</td>
				<td class=input><input class="wid common" name=MobilePhone verify="��ϵ�˵绰|num&len<=11"></td>
				<td class=title>��ϵ�˲���</td>
				<td class=input><input class="wid common" name=Department></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ��֤������</td>
				<td class=input><input class=codeno name=IDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=IDTypeName></td>
				<td class=title>��ϵ��֤������</td>
				<td class=input><input class="wid common" name=IDNo onblur="checkidtype();"></td>
				<td class=title>��ϵ��֤����Чֹ��</td>
				<td class=input><input class="coolDatePicker" verify="��ϵ��֤����Чֹ��|date" dateFormat="short" name=IDEndDate ></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>����ϵ���빴ѡ<input class=checkbox name=TooContect type=checkbox onclick="selectPeopleInfo();"></td>
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
					<td class=input><input class=codeno name="BankCode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null,null,null,'1',null);" ><input class=codename name="BankCodeName"></td>
					<td class=title>����������ʡ</td>
					<td class=input><input class=codeno name="BankProvince" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onChange="clearBankCity();" ondblclick="clearInput(BankCity,BankCityName);returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1],null,null,null,'1',null);"><input class=codename name=BankProvinceName></td>
					<td class=title>������������</td>
					<td class=input><input class=codeno name="BankCity" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="checkBankProvince();returnShowCodeList('bankcity',[this, BankCityName],[0,1],null,null,null,'1',null);" onkeyup="checkBankProvince();returnShowCodeList('bankcity',[this, BankCityName],[0,1],null,null,null,'1',null);;"><input class=codename name=BankCityName></td>
				</tr>
				<tr class=common>
					<td class=title>������</td>
					<td class=input><input class="wid common" name="AccName" verify="�˻�����|LEN<=70"></td>
					<td class=title>�����˺�</td>
					<td class=input><input class="wid common" name="BankAccNo" verify="��  ��|LEN<=25"></td>
					<td class=title>���ʽ</td>
					<td class=input><input class=codeno name=PayType  verify="���ʽ|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=PayTypeName   elementtype=nacessary></td>
				</tr>
		</table>
	</div>
	<!-- ���̻�����Ϣ -->
	<div id="divBasicJGInfo" style="display: ''">
	<table id=EngTable>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBasicInfo);">
			</td>
			<td class=titleImg>���̻�����Ϣ</td>
		</tr>
	</table>
	<div id="divBasicInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="wid common" name=EnginName  elementtype=nacessary></td>
				<td class=title>ʩ��������</td>
				<td class=input><input class="wid common" name=ContractorName   elementtype=nacessary></td>
				<td class=title>ʩ��������</td>
				<td class=input><input class=codeno name=ContractorType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('contractortype',[this,ContractorTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('contractortype',[this,ContractorTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ContractorTypeName   elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>���̵ص�</td>
				<td class=input><input class="wid common" name=EnginPlace  elementtype=nacessary></td>
				<td class=title colspan=2>�漰���ϡ�ˮ�¡��߿ա����Ƽ�������ҵ,�빴ѡ <input type=checkbox name=EnginCondition></td>
				<td class=title>��ϸ����</td>
				<td class=input><input class="wid common" name=DetailDes></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker"   dateFormat="short"  name="EnginStartDate" onClick="laydate({elem: '#EnginStartDate'});" id="EnginStartDate"><span class="icon"><a onClick="laydate({elem: '#EnginStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class="coolDatePicker"   dateFormat="short"  name="EnginEndDate" onClick="laydate({elem: '#EnginEndDate'});" id="EnginEndDate"><span class="icon"><a onClick="laydate({elem: '#EnginEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�������(Ԫ)</td>
				<td class=input><input class="wid common" name=EnginCost></td>
			</tr>
				<tr class=common>
				<td class=title>�������(ƽ����)</td>
				<td class=input><input class="wid common"  name=EnginArea id=EnginArea ></td>
				<td class=title>��������</td>
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
			<td class=titleImg>Ͷ�����ϼ�������Ϣ</td>
		</tr>
	</table>
	<div id="divInfok" class=maxbox1 style="display: ''">
		<table class=common id=PremTable>
			<tr class=common>
				<td class=title>���Ѽ��㷽ʽ</td>
				<td class=input><input type=hidden name=PremCalMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engincaltype',[this,PremCalModeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('engincaltype',[this,PremCalModeName],[0,1],null,null,null,'1',null);"><input class="wid readonly" name=PremCalModeName ></td>
				<td class=title>���շ���</td>
				<td class=input><input class="wid common" name=PremFeeRate></td>
				<td class=title>��������</td>
				<td class=input><input class="wid common" name=FirstPrem elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>ʩ������</td>
				<td class=input><input class="wid common" name=ContractorPeoples elementtype=nacessary></td>
				<td class=title colspan=4>�����ⲻ���ṩ���ذ���֤��,�빴ѡ<input type=checkbox  name=SafeProve></td>
			</tr>
		</table>
		<table class=common>
			<tr class= common>
				<td class= title> �ر�Լ��<font color="red">&nbsp;&nbsp;(ע�⣺�����ر�Լ����ע�������ر�Լ�������У������ر�Լ��������ע���� ����֮���������ر�Լ��)</font></td>
			</tr>
			<tr  class= common>
				<td  class=title>
					<textarea name="GrpSpec" id=GrpSpec cols="110" rows="3" class="common" ></textarea>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<!--�������� END-->
	<!--�������� END-->
	<div align="left" id="divRelaPay" style="display: ''">
		<span id='spanRelaPay'></span>
	</div>
	<div align="right">
		<input class=cssButton name=SaveCont  style="display: none" type=button value="��  ��" onclick="saveClick();">
	</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRiskInfo);">
				</td>
				<td class=titleImg>Ͷ��������Ϣ</td>
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
					<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage7.firstPage();">
					<input class=cssButton91 type=button value="��һҳ" onclick="turnPage7.previousPage();">
					<input class=cssButton92 type=button value="��һҳ" onclick="turnPage7.nextPage();">
					<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage7.lastPage();">
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
					<td class=title>�ܱ���</td>
					<td class=input><input class="wid readonly" name=SumPrems id=SumPrems readonly></td>
				</tr>
			</table>
		</div>
	<hr class=line />
		<input class=cssButton type=button value="���������嵥�˶�" onclick="goToInsuredList();">
		<input class=cssButton type=button value="�н���������ά��" onclick="goToChargeFee();">
		<input class=cssButton type=button value="���ڽ���ά��" onclick="goToBalance();">
		<input class=cssButton type=button value="��������" onclick="showAttachment();">
		<div id="divContPlanQuery1" style="display: '';">
			<input class=cssButton id=qutButton type=button value="Ͷ��������ѯ" onclick="showPlanQuery();">
			<input class=cssButton name=ClaimDuty  style="display: none" value="�������ο���" type=button onclick="claimDutyControl();">
		</div>
	<hr class=line />
		<div align="right">
			<input class=cssButton name=Question style="display: none" type=button value="���������" onclick="goToQuestion();">
			<input class=cssButton name=Approve style="display: none" type=button value="�������" onclick="saveApproveClick();">
			<input class=cssButton name=GrpCont style="display: none" type=button value="¼�����" onclick="savePolicyClick();">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
		</div>
		<input type=hidden name=Operate>
		<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- ��������ID -->
		<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- �ӹ�������ID -->
		<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- �����ڵ�ID -->
		<input type=hidden name=ContPlanType value="<%=tContPlanType%>">

		<!--��ȡ��ǰʱ����Ϣ-->
		<input type=hidden name=tCurrentDate value="<%=tCurrentDate%>">
		<input type=hidden name=tCurrentTime value="<%=tCurrentTime%>">
		<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
