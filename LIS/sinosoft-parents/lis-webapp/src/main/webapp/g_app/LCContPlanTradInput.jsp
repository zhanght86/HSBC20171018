<%
/***************************************************************
 * <p>ProName��LCContPlanTradInput.jsp</p>
 * <p>Title��Ͷ��������Ϣ¼��</p>
 * <p>Description��Ͷ��������Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-04-02 
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tContPlanType = request.getParameter("ContPlanType");
	String tQueryFlag = request.getParameter("QueryFlag");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tPrtNo = "<%=tPrtNo%>";
	var tContPlanType = "<%=tContPlanType%>";
	var tQueryFlag = "<%=tQueryFlag%>";
	//1-��ѯ�� 2-���۹����ģ�3-ѯ��δ�����ģ�4-ѯ���Ѿ�������
	
</script>
<html>
<head>
	<title>Ͷ������¼��</title>
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
	<script src="./LCContCommonInput.js"></script>
	<script src="./LCContPubPlan.js"></script>
	<script src="./LCContPlanInput.js"></script>
	<%@include file="./LCContPlanInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fmperiod id=fmperiod method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPeriod2);">
			</td>
			<td class=titleImg>Ͷ����Ϣ</td>
		</tr>
	</table>
	<div id="divPeriod2" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�б�����</td>
				<td class=input><input class=codeno name=AppManageCom id=AppManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center"  onblur="checkManagecom();" ondblclick=" returnShowCodeList('comcodeall',[this,ManageComName],[0,1]);" onkeyup="return returnShowCodeListKey('comcodeall',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName></td>
				<td class=title>�����ڼ�</td>
				<td class=input><input class=common style="width:60px" name=InsuPeriod id=InsuPeriod ><input class=codeno name=InsuPeriodName readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag',[this, InsuPeriodFlag],[1, 0],null,null,null,'1',null);" onkeyup="return showCodeListKey('InsuPeriodflag',[this, InsuPeriodFlag],[1,0],null,null,null,'1',null);"><input type=hidden name=InsuPeriodFlag readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=codeno value="0" name=PolicyFlag id=PolicyFlag readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('queryexp',[this, PolicyFlagName],[0, 1],null,'policytype0','concat(codetype,codeexp)','1',null);" onkeyup="return showCodeListKey('queryexp',[this, PolicyFlagName],[0,1],null,'policytype0','concat(codetype,codeexp)','1',null);"><input class=codename name=PolicyFlagName value="��ͨ����" readonly></td>
			</tr>
		</table>
		<input type=hidden name=Flag>
		<div id="divPeriod" style="display: none">
			<input class=cssButton type=button value="��  ��" onclick="periodSave();">
		</div>
</div>
</form>
<form name=fmrela id=fmrela method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuotInfo);">
			</td>
			<td class=titleImg>���۹���</td>
		</tr>
	</table>
	<div id="divQuotInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title5>Ͷ������</td>
				<td class=input5><input class="wid readonly" id=GrpPropNo name=GrpPropNo></td>
				<td class=title5>���۵���</td>
				<td class=input5><input class="wid common" id=OfferListNo name=OfferListNo></td>
				</div>
			</tr>
		</table>
		<input class=cssButton name="queryOffLis" style="display:none" type=button value="��  ѯ" onclick="queryOfferListNo();">
		<input type=hidden name=Flag>
		<div id="divRela" style="display: none">
			<input class=cssButton type=button value="���۹���" onclick="relaClick();">
			<input class=cssButton type=button value="�������" onclick="cancClick();">
		</div>
</div>
<hr class=line>
	<input class=cssButton type=button name=RequestButton value="ҵ������" onclick="showRequest();" >
	<input class=cssButton type=button name=GrpSpecButton value="�ر�Լ��" onclick="showGrpSpec();" >
</form>
<form name=fmEngin id=fmEngin method=post action="" target=fraSubmit>
		<div id=divEngin name=divEngin style="display: none">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEnginInfo);">
					</td>
					<td class=titleImg>������Ϣ</td>
				</tr>
			</table>
			<div id="divEnginInfo" class=maxbox1>
				<table class=common>
					<tr class=common>
						<td class=title>��������</td>
						<td class=input colspan=5><input class="wid common" name=EnginName style="width: 500px" elementtype=nacessary></td>
					</tr>
					<tr class=common>
						<td class=title>��������</td>
						<td class=input><input class=codeno name=EnginType id=EnginType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);"><input class=codename name=EnginTypeName readonly elementtype=nacessary></td>
						<td class=title>�������(ƽ����)</td>
						<td class=input><input class="wid common" name=EnginArea id=EnginArea></td>
						<td class=title>�������(Ԫ)</td>
						<td class=input><input class="wid common" name=EnginCost id=EnginCost></td>
					</tr>
				</table>
				<div id=divEnginFactor>
				</div>
				<table class=common>
					<tr class=common>
						<td class=title>���̵ص�</td>
						<td class=input><input class="wid common" name=EnginPlace id=EnginPlace elementtype=nacessary></td>
						<td class=title>��������</td>
						<td class=input><input class="coolDatePicker" dateFormat="short" name=EnginStartDate verify="��������|date" onClick="laydate({elem: '#EnginStartDate'});" id="EnginStartDate"><span class="icon"><a onClick="laydate({elem: '#EnginStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
						<td class=title>����ֹ��</td>
						<td class=input><input class="coolDatePicker" dateFormat="short" name=EnginEndDate verify="����ֹ��|date" onClick="laydate({elem: '#EnginEndDate'});" id="EnginEndDate"><span class="icon"><a onClick="laydate({elem: '#EnginEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					</tr>
					<tr class=common>
						<td class=title>���̸���</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc id=EnginDesc elementtype=nacessary></textarea></td>
					</tr>
					<tr class=common  id=trEnginCondition name=trEnginCondition style="display: none">
						<td class=title>����״��˵��</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition id=EnginCondition elementtype=nacessary></textarea><span style="color: red">(��˵������εĵ��ʡ�ˮ��������������Ȼ�ֺ�����ʧ���)</span></td>
					</tr>
					<tr class=common>
						<td class=title>��ע</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=Remark id=Remark></textarea></td>
					</tr>
					<tr class=common>
						<td class=title>�а�������</td>
						<td class=input><input class="wid common" name=InsurerName id=InsurerName></td>
						<td class=title>�а�������</td>
						<td class=input><input class=codeno name=InsurerType id=InsurerType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('insurertype', [this,InsurerTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListkey('insurertype', [this,InsurerTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=InsurerTypeName readonly></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
					<tr class=common>
						<td class=title>ʩ��������</td>
						<td class=input><input class="wid common" name=ContractorName id=ContractorName elementtype=nacessary></td>
						<td class=title>ʩ��������</td>
						<td class=input><input class=codeno name=ContractorType id=ContractorType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('contractortype', [this,ContractorTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('contractortype', [this,ContractorTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ContractorTypeName elementtype=nacessary></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
				<div id=divEnginSaveButton>
					<input class=cssButton type=button name=EnginSaveButton value="������Ϣ����" onclick="saveEngin();">
				</div>
			</div>
		</div>
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div id="divPlanTotal" name="divInfo1" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlanInfo);">
				</td>
				<td class=titleImg>������Ϣά��</td>
			</tr>
		</table>
	<div id="divPlanInfo" name="divPlanInfo" style="display: ''">
		<div id="divPlanList" name="divInfo1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPlanInfoGrid" ></span>
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
		
		<div id="divPlanTotal" style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="wid readonly" id=PlanCode name=PlanCode readonly></td>
					<td class=title style="display: none">ϵͳ��������</td>
					<td class=input style="display: none"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="wid common" name=PlanDesc verify="��������|len<15" elementtype=nacessary></td>
					<td class=title id=tdPlan5 name=tdPlan5 style="display: none">��������</td>
					<td class=input id=tdPlan6 name=tdPlan6 style="display: none"><input class=codeno name=PlanType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotplantype',[this, PlanTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('quotplantype',[this, PlanTypeName],[0,1]);" readonly><input class=codename name=PlanTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdPlan8 name=tdPlan8 style="display: none"><div id="divPlanFlag1" style="display: ''">������ʶ</div></td>
					<td class=input id=tdPlan9 name=tdPlan9 style="display: none"><div id="divPlanFlag2" style="display: ''"><input class=codeno name=PlanFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PlanFlagName readonly elementtype=nacessary></div></td>
				</tr>
				<tr class=common>
					<td class=title id=tdPlan10 name=tdPlan10 style="display: none">���Ѽ��㷽ʽ</td>
					<td class=input id=tdPlan11 name=tdPlan11 style="display: none"><input class=codeno name=PremCalType id=PremCalType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremCalTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdPlan12 name=tdPlan12 style="display: none">��������</td>
					<td class=input id=tdPlan13 name=tdPlan13 style="display: none"><input class="wid common" name=PlanPeople verify="��������|INT&value>0" elementtype=nacessary></td>
					<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
					<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
				<tr class=common>
					<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
					<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				<tr>
				<tr class=common id=trPlan1 name=trPlan1 style="display: none">
					<td class=title>ְҵ����<span style="color: red">*</span></td>
					<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio onclick="chooseOccupFlag('1');">��һְҵ���<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">��ְҵ���<input type=hidden name=OccupTypeFlag></td>
				</tr>
				<tr class=common id=trOccupType1 name=trOccupType1 style="display: none">
					<td class=title>ְҵ���</td>
					<td class=input><input class=codeno name=OccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype1',[this, OccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype1',[this, OccupTypeName],[0,1]);" readonly><input class=codename name=OccupTypeName readonly elementtype=nacessary></td>
					<td class=title>ְҵ�з���</td>
					<td class=input><input class=codeno name=OccupMidType style="background:url(../common/images/select--bg_03.png) no-repeat right center" readonly ondblclick="return returnShowCodeList('occupmidtype',[this, OccupMidTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupmidtype',[this, OccupMidTypeName],[0,1]);" ><input class=codename name=OccupMidTypeName readonly><span id=spanOccupMid name=spanOccupMid style="display: none;color: red">*</span></td>
					<td class=title>����</td>
					<td class=input><input class=codeno name=OccupCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occupcode',[this, OccupCodeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupcode',[this, OccupCodeName],[0,1]);" ><input class=codename name=OccupCodeName><span id=spanOccupCode name=spanOccupCode style="display: none;color: red">*</span></td>
				</tr>
				<tr class=common id=trOccupType2 name=trOccupType2 style="display: none">
					<td class=title>���ְҵ���</td>
					<td class=input><input class=codeno name=MinOccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype2',[this, MinOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MinOccupTypeName],[0,1]);" readonly><input class=codename name=MinOccupTypeName readonly elementtype=nacessary></td>
					<td class=title>���ְҵ���</td>
					<td class=input><input class=codeno name=MaxOccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype2',[this, MaxOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MaxOccupTypeName],[0,1]);" readonly><input class=codename name=MaxOccupTypeName readonly elementtype=nacessary></td>
					<td class=title>ְҵ����</td>
					<td class=input><input class="wid common" name=OccupRate elementtype=nacessary></td>
				</tr>
				<tr class=common id=trPlan2 name=trPlan2 style="display: none">
					<td class=title>�������(��)</td>
					<td class=input><input class="wid common" name=MinAge verify="�������(��)|INT&value>=0"></td>
					<td class=title>�������(��)</td>
					<td class=input><input class="wid common" name=MaxAge verify="�������(��)|INT&value>=0"></td>
					<td class=title>ƽ������(��)</td>
					<td class=input><input class="wid common" name=AvgAge verify="ƽ������(��)|INT&value>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common id=trPlan3 name=trPlan3 style="display: none">
					<td class=title>����</td>
					<td class=input><input class="wid common" name=NumPeople verify="����|INT&value>0" elementtype=nacessary></td>
					<td class=title>�μ��籣ռ��(С��)</td>
					<td class=input><input class="wid common" name=SocialInsuRate verify="�μ��籣ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
					<td class=title>��Ů����</td>
					<td class=input><input class="wid common" style="width:50px" name=MaleRate verify="��Ů����|INT&value>=0" maxlength=9><b>��</b><input class=common style="width:50px" name=FemaleRate verify="��Ů����|INT&value>=0" maxlength=9  elementtype=nacessary><font color=red> (�� 2:3)</font></td>
				</tr>
				<tr class=common id=trPlan4 name=trPlan4 style="display: none">
					<td class=title>����ռ��(С��)</td>
					<td class=input><input class="wid common" name=RetireRate verify="����ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
					<td class=title>���ѷ�̯��ʽ</td>
					<td class=input><input class=codeno name=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('premmode', [this,PremModeName], [0,1]);" onkeyup="return returnShowCodeListKey('premmode', [this,PremModeName], [0,1]);" readonly><input class=codename name=PremModeName readonly  elementtype=nacessary></td>
					<td class=title>��ҵ����ռ��(С��)</td>
					<td class=input><input class="wid common" name=EnterpriseRate verify="��ҵ����ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
				</tr>
				<tr class=common id=trPlan5 name=trPlan5 style="display: none">
					<td class=title>�����н(Ԫ)</td>
					<td class=input><input class="wid common" name=MinSalary verify="�����н(Ԫ)|num&value>=0"></td>
					<td class=title>�����н(Ԫ)</td>
					<td class=input><input class="wid common" name=MaxSalary verify="�����н(Ԫ)|num&value>=0"></td>
					<td class=title>ƽ����н(Ԫ)</td>
					<td class=input><input class="wid common" name=AvgSalary verify="ƽ����н(Ԫ)|num&value>=0"></td>
				</tr>
				<tr class=common>
					<td class=title>����˵��</td>
					<td class=input colspan=11><textarea class=common cols=70 rows=2 name=OtherDesc id=OtherDesc verify="����˵��|len<=1000"></textarea></td>
				</tr>
			</table>
			<div id="divAddPlanButton" style="display: none">
				<input class=cssButton type=button value="��  ��" onclick="addPlan();">
				<input class=cssButton type=button value="��  ��" onclick="modifyPlan();">
				<input class=cssButton type=button value="ɾ  ��" onclick="delPlan();">
			</div>
		</div>
	</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
				</td>
				<td class=titleImg>������ϸ��Ϣά��</td>
			</tr>
		</table>
		<div id="divInfo3" class=maxbox1>
			<input class=cssButton type=button value="������Ϣά��" onclick="planDetailOpen();">
			<input class=cssButton type=button value="������ϸһ��" onclick="showAllDetail();">
		<div id=divInfo5 name=divInfo5 style="display: none">
			<input class=cssButton type=button value="��Ʒ������Ϣά��" onclick="contParaOpen();">
			<input class=cssButton type=button value="��������ά��" onclick="scriptionRuleOpen();">
		</div>
	</div>
</div>
</form>
<form name=fmsub method=post action="" target=fraSubmit>
	<hr size="2" color="#D7E1F6" />
		<div id="divInfo4">
			<input class=cssButton type=button value="����¼�����" onclick="savePlanClick();">
			<input class=cssButton type=button value="��  ��" onclick="turnBack();">
			<input class=cssButton type=button value="���������" onclick="goToQuestion();">
		</div>
		<div id="divInfoClose"  style="display: none">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
		</div>
	</form>
	<form name=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
		<input type=hidden name=HiddenCodeType>
		<input type=hidden name=RelaFlag>
	</div>
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
