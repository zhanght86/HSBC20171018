<%
/***************************************************************
 * <p>ProName��GEdorRRInput.jsp</p>
 * <p>Title��������ʵ����</p>
 * <p>Description��������ʵ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-27
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
	var tEdorNo =  "<%=tEdorNo%>";
</script>

<html>
<head >
	<title>������ʵ����</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./EdorRRInput.js"></script>
	<%@include file="./EdorRRInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm method=post action="EdorRRSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo12);">
			</td>
			<td class=titleImg>��������ѯ����</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class="wid common" name=InsuredNameQ id=InsuredNameQ></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=IdNoQ id=IdNoQ></td>
				<td class=title>���շ���</td>
				<td class=input><Input class=codeno name=PlanCodeQ id=PlanCodeQ style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,planCodeNameQ,sysPlanCodeQ);" onkeyup="showContPlanCodeName(this,planCodeNameQ,sysPlanCodeQ);"><input class=codename name=planCodeNameQ ><input type=hidden name=sysPlanCodeQ ></td>
			</tr>
			<tr class=common>
				<td class=title>�������κ�</td>
				<td class=input><input class="wid common" name=ImpBatch id=ImpBatch></td>
				<td class=title></td>
				<td class=title  style="display: none">�������˿ͻ���</td>
				<td class=input><input class="wid common" name=InsuredNoQ id=InsuredNoQ type=hidden></td>
				<td class=title></td>
				<td class=input></td>
			</tr>			
		</table>
		<div id="divButton01" style="display: none">
			<input class=cssButton type=button value="��  ѯ" onclick="queryInsured(1);">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
			<input class=cssButton type=button value="ѡ��ɾ��" onclick="choseDelet();" >
			<input class=cssButton type=button value="����ɾ��" onclick="condDelete();">
			<input class=cssButton type=button value="ȫ��ɾ��" onclick="allDelete();">
		</div>
		<div id="divButton02" style="display: none">
			<input class=cssButton type=button value="��  ѯ" onclick="queryInsured(1);">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
		</div>
	</div>
	
	<!--�����������˲�ѯ��Ϣ-->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo4);">
			</td>
			<td class=titleImg>ʵ��������������Ϣ</td>
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
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<!--ҳ�������Ϣ-->
	<div id="divInfoNI" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfoNI1);">
				</td>
				<td class=titleImg>�������˻�����Ϣ</td>
			</tr>
		</table>
		<div id="divInfoNI1" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td id=td1 style="display: ''" class=title>��ȫ��Ч����</td>
				 	<td id=td2 style="display: ''" class=input><input class="coolDatePicker" dateFormat="short" verify="��ȫ��Ч����|notnull"  name=edorValDate elementtype=nacessary onClick="laydate({elem: '#edorValDate'});" id="edorValDate"><span class="icon"><a onClick="laydate({elem: '#edorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				 	<td class=title></td>
				 	<td class=input></td>
				 	<td class=title></td>
				 	<td class=input></td>
				</tr>
			</table>
		</div>	
		
		<div id="divQueryPage" style="display: ''">
			<table class=common>
				<tr class="common">
					<td class=title>�����������˹�ϵ</td>
					<td class=input><input class=codeno name=relatomain verify="�����������˹�ϵ|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('relation',[this,relatomainName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('relation',[this,relatomainName],[0,1],null,null,null,1,null);" readonly><input class=codename name=relatomainName readonly=true elementtype=nacessary></td>
					<td class=title id=mainname style="display: none">��������������</td>
					<td class=input id=mainname1 style="display: none"><input class="wid common" id=mainCustName name=mainCustName onkeydown="selectMainUser();"  elementtype=nacessary  ></td>
					<td class=title id=mainIDNo style="display: none">����������֤������</td>
					<td class=input id=mainCustNo1 style="display: none"><input class="wid common" id=mainIDNo1 name=mainIDNo></td>
				</tr>
				<tr class=common>
					<td class=title>������������</td>
					<td class=input><input class="wid common" name=InsuredName id=InsuredName  verify="������������|notnull&len<=40" onkeydown="selectUser();" onblur="checkMain();"  elementtype=nacessary></td>	
					<td class=title>֤������</td>
					<td class=input><input class=codeno name=IDType id=IDType verify="֤������|code:IDType&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="clearInput(IDNo,IDTypeName); showCodeList('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" onkeyup="clearInput(IDNo,IDTypeName); return showCodeListKey('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=IDTypeName readonly=true elementtype=nacessary></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=IDNo id=IDNo verify="֤������|notnull&len<=20" elementtype=nacessary onblur="checkidtype();"  ></td>
				</tr>
				<tr class=common>
					<td class=title>�Ա�</td>
					<td class=input><input class=codeno name=InsuredGender id=InsuredGender verify="�Ա�|notnull&len<=2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);"  readonly><input class=codename name="InsuredGenderName" readonly=true elementtype=nacessary></td>	
					<td class=title>��������</td>
					<td class= input><input class=coolDatePicker elementtype=nacessary dateFormat="short" name=InsuredBirthDay verify="��������|notnull&date" onClick="laydate({elem: '#InsuredBirthDay'});" id="InsuredBirthDay"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>����</td>
					<td class=input><input class="wid readonly" name=InsuredAppAge verify="����������|int"  readonly ></td>	
				</tr>
				<tr class=common>
					<td class=title>���շ���</td>
					<td class=input><Input class=codeno name=ContPlanCode id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);" readonly><input class=codename name=ContPlanCodeName elementtype=nacessary><input type=hidden name=sysPlanCode ></td>
					<td class=title>ְҵ����</td>
					<td class=input><input class=codeno name=OccupationCode id=OccupationCode verify="ְҵ����|notnull" readonly><input class=codename name=OccupationCodeName onkeydown="showOccupationCodeList(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" onkeyup="showOccupationCodeListKey(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" elementtype=nacessary></td>	          
					<td class=title>ְҵ���</td>
					<td class=input><input type=hidden name=OccupationType id=OccupationType verify="ְҵ���|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('occuptype',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('occuptype',[this,OccupationTypeName],[0,1]);" readonly><input class="wid readonly" name=OccupationTypeName readonly=true elementtype=nacessary></td>
 					<input type=hidden id="date" name=date value="<%=tCurrentDate%>">
 				</tr>
 				<tr class=common>
					<td class= title>�������� </td>
					<td class= input><Input class="wid common" name=ZipCode id=ZipCode  MAXLENGTH="6" verify="����������������|zipcode" ></td> 
					<td class= title>��������</td>
					<td class= input> <Input class="wid common" name=EMail id=EMail verify="�������˵�������|Email" ></td>
					<td class= title>΢�ź�</td>
					<td class= input> <Input class="wid common" name=MicroNo id=MicroNo ></td>
				</tr>
				<tr class=common>
					<td class= title>��ϵ�绰</td>
					<td class= input><input class="wid common" name=Phone id=Phone verify="��ϵ�绰|PHONE"   ></td>
					<td class= title>�ƶ��绰 </td>
					<td class= input><Input class="wid common" name=Mobile id=Mobile onblur="checkNumber(this);" verify="���������ƶ��绰|len<=11"  ></td>
					<td class= title></td>
					<td class= input></td> 
				</tr>
				<tr class=common>
					<td class=title>��ϸ��ַ</td>
					<td class=input colspan=5>
						<input class=codeno name=ProvinceName style="width:80px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode readonly>ʡ
						<input class=codeno name=CityName style="width:80px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0]);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0]);"><input type=hidden name=CityCode readonly>��
						<input class=codeno name=CountyName style="width:80px" readonlystyle="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0]);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0]);"><input type=hidden name=CountyCode readonly>��/��
						<input class="wid common" name=DetailAddress verify="��λ��ϸ��ַ|len<100" maxlength=100 style="width:300px">
					</td>
				</tr>
			</table>
			
		<!--�ǹ̶�����-->
		<div id="divQueryShow" style="display: none">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryAmnt);">
					</td>
					<td class=titleImg>�ǹ̶�����</td>
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
					<td class=input><input class=codeno name=ServerArea id=ServerArea style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=ServiceArName readonly=true></td>
					<td class=title>�Ƿ�α�׼��</td>
					<td class=input><input class=codeno name=Substandard id=Substandard style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=SubstandardName readonly=true></td>
					<td class=title>�籣���</td>
					<td class=input><input class=codeno name=Social id=Social value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=SocialName value="��" readonly=true></td>	
				</tr>
				<tr class=common>
					<td class=title>ְ��</td>
					<td class=input><input class=codeno name=Position id=Position style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick= "return initPosition(this,PositionName);" onkeyup="return initPosition(this,PositionName);"><input class=codename name=PositionName ></td>
					<td class=title>��˾ʱ��</td>
					<td class= input><input class="coolDatePicker"  dateFormat="short" name=JoinCompDate verify="��˾ʱ��|date"  onClick="laydate({elem: '#JoinCompDate'});" id="JoinCompDate"><span class="icon"><a onClick="laydate({elem: '#JoinCompDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>		
					<td class=title>����</td>
					<td class=input><input class="wid common" id=Seniority name=Seniority verify="����|int"  ></td>
				</tr>
				<tr class=common>	
					<td class=title>��н��Ԫ��</td>
					<td class=input><input class="wid common" id=Salary name=Salary  verify="��н|VALUE>=0&LEN<10"></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
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
					<td class=input><input class=codeno name=HeadBankCode id=HeadBankCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('headbank',[this,BankCodeName],[0,1],null, null, null, '1', null);" ><input class=codename name=BankCodeName></td>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=AccName id=AccName verify="�˻�����|LEN<=70" ></td>
					<td class=title>��  ��</td>
					<td class=input><input class="wid common" name=BankAccNo id=BankAccNo verify="��  ��|LEN<=25" onblur="checkNumber(this);"  ></td>		
				</tr>
				<tr class=common>
					<td class=title>����������ʡ</td>
					<td class=input><input class=codeno name=BankProvince id=BankProvince style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankprovince',[this, BankProvinceName],[0,1]);" onkeyup="return returnShowCodeListKey('bankprovince',[this, BankProvinceName],[0,1]);" ><input class=codename name="BankProvinceName"></td>
					<td class=title>������������</td>
					<td class=input><input class=codeno name=BankCity id=BankCity style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" onkeyup="return returnShowCodeList('bankcity',[this, BankCityName],[0,1]);" ><input class=codename name="BankCityName"></td>
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
				<tr class="wid common">
					<td class=title>Ա����</td>
					<td class=input><input class="wid common" name=WorkIDNo id=WorkIDNo verify="Ա����|len<=20" ></td>
					<td class=title>֤���Ƿ���</td>
					<td class=input><input class=codeno name=ISLongValid id=ISLongValid style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" readonly><input class=codename name=ISLongValidName readonly=true></td>
					<td class=title>֤����Ч��</td>
					<td class=input><Input name=LiscenceValidDate verify="֤����Ч��|date"  class="coolDatePicker" dateFormat="short" onClick="laydate({elem: '#LiscenceValidDate'});" id="LiscenceValidDate"><span class="icon"><a onClick="laydate({elem: '#LiscenceValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class="wid common">
					<td class= title>���ڷֹ�˾</td>
					<td class= input><Input class="wid common"  name=ComponyName id=ComponyName  ></td>
					<td class=title>���ڲ���</td>
					<td class=input><input class="wid common" name=DeptCode id=DeptCode ></td>
					<td class= title>�������˱���</td>
					<td class= input><Input class="wid common"  name=InsureCode id=InsureCode ></td>
				</tr>
				<tr class=common>
					<!--
					<td class= title>�����ͻ�Ⱥ</td>
					<td class= input><Input class=codeno name=SubCustomerNo ondblclick="return showCodeList('subcustomerno',[this,SubCustomerName],[0,1],null,'1 and a.customerno=(select appntno from lcgrpcont where grpcontno=#<%=tGrpcontNo%>#) and a.state=#1#','1',1);" onkeyup="return showCodeListKey('subcustomerno',[this,SubCustomerName],[0,1],null,'1 and a.customerno=(select appntno from lcgrpcont where grpcontno=#<%=tGrpcontNo%>#) and a.state=#1#','1',1);" readonly><input class=codename name=SubCustomerName readonly=true></td>
					-->
					<td class=title>������</td>
					<td class=input><input class="wid common" name=WorkAddress id=WorkAddress ></td>
					<td class=title>�籣��</td>
					<td class=input><input class="wid common" name=SocialAddress id=SocialAddress ></td>
					<td class=title></td>
					<td class=input>
				</tr> 
			</table>
		</div>
	</div>
		
		<!--��������Ϣ-->
		<div id="divQuerybnf" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
				</td>
				<td class=titleImg>��������Ϣ</td>
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
	<div id=divButton03 style="display: none">
		<input class=cssButton type=button value="��  ��" onclick="addRecord();">
		<input class=cssButton type=button value="��  ��" onclick="modifyRecord();">
	</div>	
	
	<!--������-->
	<input type=hidden name=Operate value="<%=tGI.Operator%>">	
	<input type=hidden name=CurrentDate value="<%=tCurrentDate%>">
	<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- ��������ID -->
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- �����ڵ�ID -->
	<input type=hidden name=EdorAppNo  value="<%=tEdorAppNo%>">
	<input type=hidden name=GrpPropNo value="<%=tGrpcontNo%>" >
	<input type=hidden name=EdorType value="<%=tEdorType%>">	
	<input type=hidden name=EdorNo value="<%=tEdorNo%>">	
	
	<input type=hidden name=mainCustNameTemp> <!--�����������Ʊ���-->
	<input type=hidden name=InsuredNameTemp>	
	<input type=hidden name=mainInsuredNo>
	<input type=hidden name=Serialno>
	<input type=hidden name=InsuredID>
	<input type=hidden name=Hidrelatomain>
	<input type=hidden name=BatchNo>
	<input type=hidden name=AmntFlag>
	<Br /><Br /><Br /><Br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
