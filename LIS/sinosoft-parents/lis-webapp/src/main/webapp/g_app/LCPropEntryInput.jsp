<%
/***************************************************************
 * <p>ProName��LCPropEntryInput.jsp</p>
 * <p>Title��Ͷ������Ϣ¼��</p>
 * <p>Description��Ͷ������Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tFlag = request.getParameter("Flag");
	
	String tCurrentDate = PubFun.getCurrentDate();
	String tCurrentTime = PubFun.getCurrentTime();

%>

<script>
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";	
	
	var	tCurrentDate   = "<%=tCurrentDate%>";
	var	tCurrentTime   = "<%=tCurrentTime%>";

</script>
<html>
<head>
	<title>Ͷ������Ϣ¼��</title>
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
	<script src="./LCPropEntryInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCPropEntryInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">

<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>Ͷ��������</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>Ͷ��������</td>
				<td class=input colspan=5><input class=common name=GrpName verify="׼�ͻ�����|notnull&len<60" maxlength=60 style="width:554px" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>��Ӫ��Χ</td>
				<td class=input colspan=3><input class=common name=MainBusiness verify="��Ӫ��Χ|notnull&len<200" maxlength=200 style="width:554px" elementtype=nacessary></td>
				<td class=title>���˴���</td>
				<td class=input><input class="wid common" name=Corporation verify="����|notnull&len<60" maxlength=60 elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>���˴���֤������</td>
				<td class=input><input class=codeno name=CorIDType ondblclick="return showCodeList('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=CorIDTypeName></td>
				<td class=title>���˴���֤������</td>
				<td class=input><input class="wid common" name=CorID onblur="checkCoridtype();" ></td>
				<td class=title>���˴���֤����Чֹ��</td>
				<TD class=input><input class="coolDatePicker" verify="����֤����Чֹ��|date" dateFormat="short" name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 
			</tr>
			<tr class=common>
				<td class=title>��ҵ����</td>
				<td class=input><input type=hidden name=BusiCategoryCode><input class="wid readonly" name=BusiCategory id=BusiCategory ></td>
				<td class=title>����ʱ��</td>
				<td class=input><input class=coolDatePicker verify="����ʱ��|date" dateFormat="short" name=FoundDate onClick="laydate({elem: '#FoundDate'});" id="FoundDate"><span class="icon"><a onClick="laydate({elem: '#FoundDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�籣���յǼ�֤��</td>
				<td class=input><input class="wid common" id=SocialInsuCode  name=SocialInsuCode></td>
			</tr>
			<tr class=common>
				<td class=title>��λ����</td>
				<td class=input><input class=codeno name=GrpNature verify="��λ����|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);"><input class=codename name=GrpNatureName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��λ֤������</td>
				<td class=input><input class=codeno readonly name=GrpIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);"><input class=codename  name=GrpIDTypeName  elementtype=nacessary></td>
				<td class=title>��λ֤������</td>
				<td class=input><input class="wid common" name=GrpIDNo id=GrpIDNo  maxlength=30  elementtype=nacessary></td>
				<td class=title>��λ֤����Чֹ��</td>
				<TD class=input><Input class=coolDatePicker verify="��λ֤����Чֹ��|date" dateFormat="short" name=GrpIDExpiryDate onClick="laydate({elem: '#GrpIDExpiryDate'});" id="GrpIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#GrpIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 
			</tr>
			<!--��λ��ѡ-->
			<tr class=common>
				<td class=title colspan=6>��λ֤����Ϣ�����빴ѡ<input class=checkbox type=checkbox name='IDInfoCheck' onclick="selectIDInfo();"></td>
			</tr>
			<tr class=common>
				<td class=title colspan=6>
					<div id="divIDInfoGrid" style="display: none">
						<table class=common>
							<tr class=common>
								<td text-align: left colSpan=1>
									<span id="spanIDInfoGrid" ></span>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>	
			<tr class=common>
				<td class=title>��λ��ַ</td>
				<td class=input colspan=3>
					<input class=codeno name=ProvinceName style="width:60px" onChange="clearCityAndCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, Province],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, Province],[1,0],null,null,null,'1',180);"><input type=hidden name=Province readonly>ʡ
					<input class=codeno name=CityName style="width:60px" onChange="clearCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeLis('city',[this, City],[1,0],null,null,null,1,null);" onkeyup="return returnShowCodeLisKey('city',[this, City],[1,0],null,null,null,1,null);"><input type=hidden name=City readonly>��
					<input class=codeno name=CountyName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeLis('district',[this, County],[1,0],null,null,null,1,null);" onkeyup="return returnShowCodeLisKey('district',[this, County],[1,0],null,null,null,1,null);"><input type=hidden name=County readonly>��/��
					<input class=common name=Address verify="��λ��ϸ��ַ|len<100" maxlength=100 style="width:300px">
				</td>
				<td class=title>��������</td>
				<td class=input><input class="wid common" name=ZipCode id=ZipCode verify="��������|zipcode"  MAXLENGTH="6"></td>
			</tr>
			<tr class=common>
				<td class=title>��λ��ϵ�绰</td>
				<td class=input><input class="wid common" name=Phone id=Phone></td>
				<td class=title>��λ����</td>
				<td class=input><input class="wid common" name=Fax id=Fax></td>
				<td class=title>��λE-mail��ַ</td>
				<td class=input><input class="wid common" name=EMail id=EMail verify="��λE-mail|EMAIL"></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ������</td>
				<td class=input><input class="wid common" name=LinkMan id=LinkMan></td>
				<td class=title>��ϵ�˵绰</td>
				<td class=input><input class="wid common" name=LinkPhone id=LinkPhone></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ��֤������</td>
				<td class=input><input class=codeno name=LinkIDType id=LinkIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,LinkIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,LinkIDTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=LinkIDTypeName></td>
				<td class=title>��ϵ��֤������</td>
				<td class=input><input class="wid common" name=LinkID id=LinkID onblur="checkidtype();" ></td>
				<td class=title>��ϵ��֤����Чֹ��</td>
				<td class=input><input class="coolDatePicker" verify="��ϵ��֤����Чֹ��|date" dateFormat="short" name=LinkIDExpiryDate ></td>
			</tr>
			<tr class=common>
				<td class=title>��λ������</td>
				<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="��λ������|INT"></td>
				<td class=title>������������</td>
				<td class=input><input class="wid common" name=MainContNumber id=MainContNumber verify="������������|INT"></td>
				<td class=title>��������������</td>
				<td class=input><input class="wid common" name=RelatedContNumber id=RelatedContNumber verify="��������������|INT"></td>
			</tr>
		</table>
		<div align="right" style="display:none" id=savePrint>
			<input class=cssButton type=button  value="��  ��" onclick="saveClick();">
		</div>
	</div>
	<!-- ����Ͷ����Ϣ -->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBasicInfo);">
			</td>
			<td class=titleImg>����Ͷ����Ϣ</td>
		</tr>
	</table>
	<div id="divBasicInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��Լ��Ч������</td>
				<td class=input><input class=codeno name=ValDateType id=ValDateType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,1,null);"><input class=codename name=ValdateTypeName id=ValdateTypeName readonly=true><font color="#ff0000">*</font></td>
				<td class=title><div id=valDatename style="display: ''">��Ч����</div></td>
				<TD class=input><div id=valDatename1 style="display: ''"><Input class="coolDatePicker" dateFormat="short"  name=ValDate onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color="#ff0000">*</font></div></TD> 
				<td class=title>��������</td>
				<td class=input><input class="codename"  name=InsuPeriod id=InsuPeriod ><input class=codeno name=InsuPeriodName id=InsuPeriodName style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('insuperiodflag',[this, InsuPeriodFlag],[1, 0],null,null,null,'1',null);" onkeyup="return showCodeListKey('InsuPeriodflag',[this, InsuPeriodFlag],[1,0],null,null,null,'1',null);"><font color="#ff0000">*</font><input type=hidden name=InsuPeriodFlag readonly>
			</tr>
			<tr class=common>
				<td class=title>�ɷѷ�ʽ</td>
				<td class=input><input class=codeno name=PayIntv id=PayIntv style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('payintv',[this,PayIntvName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeListKey('payintv',[this,PayIntvName],[0,1],null,null,null,'1',null);"><input class=codename name=PayIntvName id=PayIntvName ><font color="#ff0000">*</font></td>
				<td class=title>���ѷ�̯��ʽ</td>
				<td class=input><input class=codeno name=PremMode id=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('premmode',[this,PremModeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('premmode',[this,PremModeName],[0,1],null,null,null,'1',null);"><input class=codename name=PremModeName id=PremModeName ><font color="#ff0000">*</font></td>
				<td class=title>���ʽ</td>
				<td class=input><input class=codeno name=PayType id=PayType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=PayTypeName id=PayTypeName ><font color="#ff0000">*</font></td>
			</tr>
		</table>
		<div class=common id=divPlanDiv>
		</div>
		<table class=common>
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class=codeno name=ChnlType id=ChnlType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeList('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ChnlTypeName ><font color="#ff0000">*</font></td>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=SaleChnl id=SaleChnl readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);" ><input class=codename name=SaleChnlName ><font color="#ff0000">*</font></td>
				<td class=title>�б�����</td>
				<td class=input><input class=codeno name=AppManageCom id=AppManageCom readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" returnShowCodeList('comcodeall',[this,ManageComName],[0,1]);" onkeyup="return returnShowCodeListKey('comcodeall',[this,ManageComName],[0,1],null,'1 and comgrade=#03#',1,'1',180);"><input class=codename name=ManageComName readonly ><font color="#ff0000">*</font></td>
			</tr>
		</table>
	
		<div id="divZJInfo" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanZJGrid" ></span>
						</td>
					</tr>
				</table>
		</div>
	<hr class=line />
	<div id="divGrpSpecInfo" style="display: ''">
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
		<div align="right" style="display:none" id=saveProp>
			<input class=cssButton type=button value="��  ��" onclick="savesClick();">
		</div>
	</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFYXZInfo);">
				</td>
				<td class=titleImg>��ҳѡ��</td>
			</tr>
		</table>
		<div id="divFYXZInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title><input type=checkbox name=AddService>���ӷ���<input type=checkbox name=PersonProp>ҵ��Ա������</td>
				</tr>
			</table>
		</div>
		<input class=cssButton type=button value="�н���������ά��" onclick="goToChargeFee();">
		<input class=cssButton type=button value="��Ա�嵥ά��" onclick="goToInsuredInfo();">
		<input class=cssButton type=button value="��ӡͶ����" name=PrintProp disabled=true onclick="printPrtProp();"> 
		<input class=cssButton type=button value="������񱣷�ȷ����" onclick="printPremConf();">
		<input class=cssButton type=button value="��  ��" onclick="turnBack();">
		<input type=hidden name=Operate>
		<input type=hidden name=GrpPropNo>
		<input type=hidden name=Flag>
		<input type=hidden name=QuotNo>
		<input type=hidden name=QuotBatNo>
		<input type=hidden name=QuotType>
		<input type=hidden name=PolicyType>
		<input type=hidden name=ContPlanType>
		
		<!--��ȡ��ǰʱ����Ϣ-->
		<input type=hidden name=tCurrentDate value="<%=tCurrentDate%>">
		<input type=hidden name=tCurrentTime value="<%=tCurrentTime%>">
		<input type=hidden name=ManageCom value="<%=tGI.ManageCom%>">
	<Br /><Br />	<Br />	<Br />		
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
