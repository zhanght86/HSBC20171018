<%
/***************************************************************
 * <p>ProName��EdorNCInput.jsp</p>
 * <p>Title��������Ϣ¼��</p>
 * <p>Description��������Ϣ¼��</p>
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
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tPolicyNo = request.getParameter("GrpContNo");
	String tEdorType = request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	String tQueryFlag = request.getParameter("QueryFlag");
%>
<script>
	var tCurrentDate = "<%=tCurrentDate%>";
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tPolicyNo = "<%=tPolicyNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
	var tQueryFlag = "<%=tQueryFlag%>";
	//1-��ѯ�� 2-ά��
	
</script>
<html>
<head>
	<title>����¼��</title>
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
	<script src="./EdorCommonInput.js"></script>
	<script src="./EdorNCPub.js"></script>
	<script src="./EdorNCInput.js"></script>
	<%@include file="./EdorNCInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div id="divPlanTotal" name="divInfo1" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
				</td>
				<td class=titleImg>������Ϣά��</td>
			</tr>
		</table>
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
					<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
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
					<td class=input id=tdPlan6 name=tdPlan6 style="display: none"><input class=codeno name=PlanType id=PlanType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotplantype',[this, PlanTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('quotplantype',[this, PlanTypeName],[0,1]);" readonly><input class=codename name=PlanTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdPlan8 name=tdPlan8 style="display: none"><div id="divPlanFlag1" style="display: ''">������ʶ</div></td>
					<td class=input id=tdPlan9 name=tdPlan9 style="display: none"><div id="divPlanFlag2" style="display: ''"><input class=codeno type=hidden name=PlanFlag><input class="wid readonly" name=PlanFlagName></div></td>
						<!--input class=codeno readonly name=PlanFlag ondblclick="return showCodeList('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" -->
					<td class=title id=tdPlan10 name=tdPlan10 style="display: none">���Ѽ��㷽ʽ</td>
					<td class=input id=tdPlan11 name=tdPlan11 style="display: none"><input class=codeno name=PremCalType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremCalTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdPlan12 name=tdPlan12 style="display: none">��������</td>
					<td class=input id=tdPlan13 name=tdPlan13 style="display: none"><input class="wid common" name=PlanPeople id=PlanPeople verify="��������|INT&value>0" elementtype=nacessary></td>
					<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
					<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
					<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
					<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
				<tr>
				<tr class=common id=trPlan1 name=trPlan1 style="display: none">
					<td class=title>ְҵ����<span style="color: red">*</span></td>
					<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio onclick="chooseOccupFlag('1');">��һְҵ���<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">��ְҵ���<input type=hidden name=OccupTypeFlag></td>
				</tr>
				<tr class=common id=trOccupType1 name=trOccupType1 style="display: none">
					<td class=title>ְҵ���</td>
					<td class=input><input class=codeno name=OccupType id=OccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype1',[this, OccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype1',[this, OccupTypeName],[0,1]);" readonly><input class=codename name=OccupTypeName readonly elementtype=nacessary></td>
					<td class=title>ְҵ�з���</td>
					<td class=input><input class=codeno name=OccupMidType id=OccupMidType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occupmidtype',[this, OccupMidTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupmidtype',[this, OccupMidTypeName],[0,1]);" ><input class=codename name=OccupMidTypeName readonly><span id=spanOccupMid name=spanOccupMid style="display: none;color: red">*</span></td>
					<td class=title>����</td>
					<td class=input><input class=codeno name=OccupCode id=OccupCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occupcode',[this, OccupCodeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupcode',[this, OccupCodeName],[0,1]);" ><input class=codename name=OccupCodeName><span id=spanOccupCode name=spanOccupCode style="display: none;color: red">*</span></td>
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
					<td class=title>�μ��籣ռ��</td>
					<td class=input><input class="wid common" name=SocialInsuRate verify="�μ��籣ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
					<td class=title>��Ů����</td>
					<td class=input><input class="wid common" style="width:50px" name=MaleRate verify="��Ů����|INT&value>=0" maxlength=9><b>��</b><input class=common style="width:50px" name=FemaleRate verify="��Ů����|INT&value>=0" maxlength=9  elementtype=nacessary><font color=red> (�� 2:3)</font></td>
				</tr>
				<tr class=common id=trPlan4 name=trPlan4 style="display: none">
					<td class=title>����ռ��</td>
					<td class=input><input class="wid common" name=RetireRate verify="����ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
					<td class=title>���ѷ�̯��ʽ</td>
					<td class=input><input class=codeno name=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('premmode', [this,PremModeName], [0,1]);" onkeyup="return returnShowCodeListKey('premmode', [this,PremModeName], [0,1]);" readonly><input class=codename name=PremModeName readonly  elementtype=nacessary></td>
					<td class=title>��ҵ����ռ��</td>
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
		</div>
	</div>
	<hr class=line />
	<input class=cssButton type=button value="��  ��" onclick="top.close();">
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<input type=hidden name=Operate>
	<input type=hidden name=HiddenCodeType>
</form>
<Br /><Br /><Br /><Br /><Br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
