<%
/***************************************************************
 * <p>ProName��LCInsuredAddInput.jsp</p>
 * <p>Title����ӱ�������ϸ��Ϣ</p>
 * <p>Description����ӱ�������ϸ��Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
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
	<title>��ӱ�������Ϣ</title>
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
	<!--�����˻�����Ϣ-->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryPage);">
			</td>
			<td class=titleImg>�������˻�����Ϣ</td>
		</tr>
	</table>
	<div class=maxbox1>
	<table class=common>
			<tr>
				<td class=title>������������</td>
				<td class=input>
					<input class=codeno name=InsuredType id=InsuredType  readonly verify="������������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('insuredtype',[this,InsuredTypeName],[0,1],null, '1 and othersign=#1# ', '1', '1', null);" onkeyup="return showCodeListKey('insuredtype',[this,InsuredTypeName],[0,1],null, '1 and othersign=#1# ', '1', '1', null);"  ><input class=codename name="InsuredTypeName" readonly=true elementtype=nacessary>
				</td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
	</table>
	<!--������Ϣ-->	
	<div id="shownewPage" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=title>���շ���<td>
				<td class=input><Input class=codeno name=JGContPlanCode id=JGContPlanCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,JGContPlanCodeName,JGsysContPlanCode);" onkeyup="showContPlanCodeName(this,JGContPlanCodeName,JGsysContPlanCode);"><input class=codename name=JGContPlanCodeName elementtype=nacessary><input type=hidden name=JGsysContPlanCode></td>
				<td class=title>������������</td>
				<td class=input><Input class="wid common" id=InsuredNum name=InsuredPeoples value="" verify="������������|num"></td>
				<td class=title></td>
 				<td class=input></td>
			</tr>
		</table>
	</div>
	<div id="divQueryPage" style="display: ''">
		<table class=common>
			<tr class= common>
				<td class=title>�����������˹�ϵ</td>
				<td class=input><input class=codeno name=relatomain id=relatomain readonly verify="�����������˹�ϵ|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('relation',[this,relatomainName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('relation',[this,relatomainName],[0,1],null,null,null,1,null);" ><input class=codename name=relatomainName readonly elementtype=nacessary></td>
				<td class=title><div id=mainname style="display: none">��������������</div></td>
				<td class=input><div id=mainname1 style="display: none"><input class=common name=mainCustName onkeydown="selectMainUser();" onblur="selectMainUserInfo();" elementtype=nacessary ></div></td>
				<td class=title><div id=maincustno style="display: none">���������˿ͻ���</div></td>
				<td class=input><div id=mainCustNo1 style="display: none"><input class="wid readonly" name=mainCustNo readonly></div></td>
			</tr>	
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class="wid common" name=InsuredName verify="������������|notnull&len<=40" onkeydown="selectUser();" onblur="checkMain();" elementtype=nacessary></td>	
				<td class=title>֤������</td>
				<td class=input><input class=codeno name=IDType readonly verify="֤������|code:IDType&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="clearInput(IDNo,IDTypeName); showCodeList('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" onkeyup="clearInput(IDNo,IDTypeName); return showCodeListKey('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" ><input class=codename name=IDTypeName readonly=true elementtype=nacessary></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=IDNo  verify="֤������|notnull&len<=20" elementtype=nacessary onblur="checkidtype();"  ></td>
			</tr>
			<tr>
				<td class=title>�Ա�</td>
				<td class=input><input class=codeno name=InsuredGender readonly  verify="�Ա�|notnull&len<=2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);"  ><input class=codename name="InsuredGenderName" readonly=true elementtype=nacessary></td>	
				<td class=title>��������</td>
				<td class= input><input class=coolDatePicker name=InsuredBirthDay elementtype=nacessary dateFormat="short" verify="��������|notnull&date" onClick="laydate({elem: '#InsuredBirthDay'});" id="InsuredBirthDay"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����</td>
				<td class=input><input class="wid readonly" name=InsuredAppAge readonly verify="����������|num"></td>	
			</tr>
			<tr class= common>
				<td class=title>���շ���</td>
				<td class=input><Input class=codeno name=ContPlanCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode,PlanType,PremcalType,PlanFlag);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode,PlanType,PremcalType,PlanFlag);"><input class=codename name=ContPlanCodeName elementtype=nacessary><input type=hidden name=sysPlanCode ></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
 			</tr>
 			<tr class= common>
				<td class=title>ְҵ����</td>
				<td class=input><input class=codeno name=OccupationCode readonly verify="ְҵ����|notnull"><input class=codename name=OccupationCodeName onkeydown="showOccupationCodeList(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" onkeyup="showOccupationCodeListKey(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" elementtype=nacessary></td>	          
				<td class=title colspan=2><span style="color: red">��¼��ְҵ���������ûس�ģ����ѯ��</span></td>
				<!--<input class=codeno name="OccupationCode" verify="ְҵ����|notnull" ondblclick="showCodeList('occupationcode',[this,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,'1 and a.occuplevel=#3#','1',1);" onkeyup="return showCodeListKey('occupationcode',[this,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,'1 and a.occuplevel=#3#','1',1);" ><input class=codename name=OccupationCodeName onkeydown="showOccupationCodeList(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" onkeyup="showOccupationCodeListKey(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" elementtype=nacessary>-->
				<td class=title >ְҵ���</td>
				<td class=input><input type=hidden name=OccupationType verify="ְҵ���|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('occuptype',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('occuptype',[this,OccupationTypeName],[0,1]);" ><input class="wid readonly" name=OccupationTypeName readonly=true elementtype=nacessary></td>
				<!--<Input class="codeno" name="OccupationType"  verify="��������ְҵ���|code:OccupationType" ondblclick="return showCodeList('occuptype',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('occuptype',[this,OccupationTypeName],[0,1]);"><input class=codename name=OccupationTypeName readonly=true>-->
				<input type=hidden id="date" name=date value="<%=tCurrentDate%>">
			</tr>
			<tr class= common>
				<td class= title>�������� </td>
				<td class= input><Input class="wid common" name=ZipCode  MAXLENGTH="6" verify="��������|zipcode" ></td> 
				<td class= title>��������</td>
				<td class= input> <Input class="wid common" name=EMail verify="��������|Email" ></td>
				<td class= title>΢�ź�</td>
				<td class= input> <Input class="wid common" name=MicroNo verify="΢�ź�|len<30"></td>
			</tr>
			<tr class= common>
				<td class= title>��ϵ�绰</td>
				<td class= input><input class="wid common" name=Phone verify="��ϵ�绰|PHONE"></td>
				<td class= title>�ƶ��绰 </td>
				<td class= input><Input class="wid common" name=Mobile onblur="checkNumber(this);" verify="�ƶ��绰|len<=13"  ></td>
				<td class= title></td>
				<td class= input></td> 
			</tr>
			<tr class= common>
				<td class=title>��ϸ��ַ</td>
				<td class=input colspan=5>
					<input class=codeno name=ProvinceName readonly style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode readonly>ʡ
					<input class=codeno name=CityName readonly style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0]);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0]);"><input type=hidden name=CityCode readonly>��
					<input class=codeno name=CountyName readonly style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0]);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0]);"><input type=hidden name=CountyCode readonly>��/��
					<input class=common name=DetailAddress verify="��λ��ϸ��ַ|len<100" maxlength=100 style="width:300px">
				</td>
			</tr>		
		</table>
		
	</div>
		<!--������������Ϣ-->
		<div id=divQuerySpe style="display: none">
			<table class=common>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryFuSh);">
					</td>
					<td class=titleImg>��������������Ϣ</td>
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
		
		<!--������Ϣ-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuerySPE);">
				</td>
			<td class=titleImg>������Ϣ</td>
			</tr>
		</table>
		<div id="divQuerySPE" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class=codeno name=ServerArea readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" ><input class=codename name=ServiceArName readonly=true></td>
					<td class=title>�Ƿ�α�׼��</td>
					<td class=input><input class=codeno name=Substandard  readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" ><input class=codename name=SubstandardName readonly=true></td>
					<td class=title>�籣���</td>
					<td class=input><input class=codeno name=Social value="0" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" ><input class=codename name=SocialName value="��" readonly=true></td>	
				</tr>
				<tr class=common>
					<td class=title>��н��Ԫ��</td>
					<td class=input><input class="wid common" name=Salary verify="��н|VALUE>=0&LEN<10"></td>
					<td class=title>��˾ʱ��</td>
					<td class= input><input class=coolDatePicker  dateFormat="short" name="JoinCompDate" verify="��˾ʱ��|date"  onClick="laydate({elem: '#JoinCompDate'});" id="JoinCompDate"><span class="icon"><a onClick="laydate({elem: '#JoinCompDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>		
					<td class=title>����</td>
					<td class=input><input class="wid common" name=Seniority verify="����|int" ></td>
				</tr>
				<tr class=common>	
					<td class=title>ְ��</td>
					<td class=input><input class=codeno name=Position readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick= "return initPosition(this,PositionName);" onkeyup="return initPosition(this,PositionName);"><input class=codename name="PositionName" ></td>
					<td class=title>��ȡ����</td>
					<td class=input><input class="wid common" name=GetYear verify="��ȡ����|int" ></td>
					<td class=title>�������ڼ�������</td>
					<td class=input><input class=codeno name=GetStartType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('getstarttype',[this,GetStartTypeName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('getstarttype',[this,GetStartTypeName],[0,1],null, null, null, '1', null);" ><input class=codename name=GetStartTypeName readonly=true></td>
				</tr>
			</table>
		</div>
		<!--������Ϣ-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryBank);">
				</td>
				<td class=titleImg>��������������Ϣ</td>
			</tr>
		</table>
		<div id="divQueryBank" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class=codeno name=HeadBankCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" ><input class=codename name="BankCodeName"></td>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=AccName verify="������|LEN<=70" ></td>
					<td class=title>��  ��</td>
					<td class=input><input class="wid common" name=BankAccNo verify="��  ��|LEN<=25" onKeyPress="FormatString(BankAccNo);"  ></td>
				</tr>
				<tr class=common>
					<td class=title>����������ʡ</td>
					<td class=input><input class=codeno name=BankProvince readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1]);" onkeyup="return returnShowCodeListKey('bankprovince',[this, BankProvinceName],[0,1]);" ><input class=codename name="BankProvinceName"></td>
					<td class=title>������������</td>
					<td class=input><input class=codeno name=BankCity readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" onkeyup="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" ><input class=codename name="BankCityName"></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
		
		<!--������Ϣ-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryOther);">
				</td>
				<td class=titleImg>������Ϣ</td>
			</tr>
		</table>
		<div id="divQueryOther" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class= common>
					<td class=title>Ա����</td>
					<td class=input><input class="wid common" name=WorkIDNo verify="Ա����|len<=20" ></td>
					<td class=title>֤���Ƿ���</td>
					<td class=input><input class=codeno name=ISLongValid readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" ><input class=codename name=ISLongValidName readonly=true></td>
					<td class=title>֤����Ч��</td>
					<td class=input><Input name=LiscenceValidDate verify="֤����Ч��|date"  class="coolDatePicker" dateFormat="short" onClick="laydate({elem: '#LiscenceValidDate'});" id="LiscenceValidDate"><span class="icon"><a onClick="laydate({elem: '#LiscenceValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class= common>
					<td class= title>���ڷֹ�˾</td>
					<td class= input><Input class="wid common"  name=ComponyName verify="���ڷֹ�˾|len<50" ></td>
					<td class=title>���ڲ���</td>
					<td class=input><input class="wid common" name=DeptCode verify="���ڲ���|len<40"></td>
					<td class= title>�������˱���</td>
					<td class= input><Input class="wid common"  name=InsureCode verify="�������˱���|len<20" ></td>
				</tr>
				<tr class=common>
					<!---
					<td class= title>�����ͻ�Ⱥ</td>
					<td class= input><Input class=codeno name=SubCustomerNo readonly ondblclick="return showCodeList('SubCustomerNo',[this,SubCustomerName],[0,1],null,fm.all('GrpContNo').value,'b.GrpcontNo',null);" onkeyup="return showCodeListKey('SubCustomerNo',[this,SubCustomerName],[0,1],null,fm.all('GrpContNo').value,'b.GrpcontNo',null);"><input class=codename name=SubCustomerName readonly=true></td>
					-->
					<td class=title>������</td>
					<td class=input><input class="wid common" name=WorkAddress verify="������|len<50" ></td>
					<td class=title>�籣��</td>
					<td class=input><input class="wid common" name=SocialAddress verify="�籣��|len<50"></td>
				</tr> 
			</table>
		</div>
	</div>
	<div id= "divAddDelButton" style= "display: ''" align=right>
		<input type =button class=cssButton name=addButton value="��ӱ�������" onclick="addRecord();">
		<input type =button class=cssButton name=deleteButton value="ɾ����������" onclick="deleteRecord();">
		<input type =button class=cssButton name=modifyButton value="�޸ı�������" onclick="modifyRecord();">
	</div>
	
	<!--������������Ϣ-->
	<div id="divPol" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
				</td>
				<td class=titleImg>��������������Ϣ</td>
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
					<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
					<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
					<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
					<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
				</center>
			</div>
		</div>
		
		<div id=divInsuredInfo3 style="display: none"><input class=cssButton type=button id=savaPOL value="������Ϣ����" onclick="polSave();"></div>
	</div>
	
	<!--�ɷ�����Ϣ-->
	<div id="divPayPlan" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPayPlanInfo);">
				</td>
				<td class=titleImg>�ɷ�����Ϣ</td>
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
		
		<div id=divPayPlanSaveButton style="display: none"><input class=cssButton type=button value="�ɷ���Ϣ����" onclick="payPlanSave();"></div>
	</div>
	
	<!--Ͷ����Ϣ-->
	<div id="divInvest" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInvestInfo);">
				</td>
				<td class=titleImg>Ͷ���˻���Ϣ</td>
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
		
		<div id=divInvestSaveButton style="display: none"><input class=cssButton type=button value="Ͷ����Ϣ����" onclick="investSave();"></div>
	</div>
	
	<input class=cssButton name="bnfAdd" type=button value="������ά��" onclick="bnfaddRecord();">
	<input class=cssButton type=button value="��  ��" onclick="returnBack();">
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden id=InsuredNameTemp name=InsuredNameTemp>
	<input type=hidden id=mainCustNameTemp name=mainCustNameTemp>
	<input type=hidden id=GrpPrtno name="GrpPropNo" value="<%=tGrpPropNo%>"> 
	<input type=hidden id=ContType name="ContType">  
	<input type=hidden name=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID> <!-- �����ڵ�ID -->
	<input type=hidden name=RowNo value="0"> <!-- �к�-����չʾʹ�� -->
	<input type=hidden name=LoadFlag value="01"> <!-- ����ҳ������ 01-��Ա�嵥¼�� 02--¼��  03--���� -->
	<input type=hidden name=InsuredSeqNo value="<%=tInsuredNo%>"> <!-- ��ǰ�����ĸ��˿ͻ��� -->
	<input type=hidden name=mainContNo> <!-- ��ǰ�������������˿ͻ��� -->	
	<input type=hidden name=mContNo value="<%=tContNo%>"> <!-- ��ǰ�����ĸ��˱����� -->	
	<!--��ӱ�ȫ��Ϣ-->
	<input type=hidden name=BQFlag>
	<input type=hidden name=EdorType>
	<input type=hidden name=EdorTypeCal>
	<input type=hidden name=EdorValiDate>
	<!--��ȡ��ǰʱ����Ϣ-->
	<input type=hidden name=tCurrentDate value="<%=tCurrentDate%>">
	<input type=hidden name=tCurrentTime value="<%=tCurrentTime%>">
	<input type=hidden name=tManageCom value="<%=tGI.ManageCom%>">
	<input type=hidden name=Flag value="<%=tFlag%>"> <!--����ʱ01-->
	<input type=hidden name=AmntFlag>
	<input type=hidden name=PlanFlag>
	<input type=hidden name=PremcalType>
	<input type=hidden name=PlanType>
	
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
