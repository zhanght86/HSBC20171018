<%
/***************************************************************
 * <p>ProName��EdorNCDetailInput.jsp</p>
 * <p>Title��������ϸ��Ϣ¼��</p>
 * <p>Description��������ϸ��Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tPolicyNo = request.getParameter("PolicyNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	String tContPlanType = request.getParameter("ContPlanType");
	String tQueryFlag = request.getParameter("QueryFlag");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tPolicyNo = "<%=tPolicyNo%>";
	var tEdorNo = "<%=tEdorNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tQueryFlag = "<%=tQueryFlag%>";
	var tContPlanType = "<%=tContPlanType%>";//��Ϊҳ���ʼ��
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>������ϸ¼��</title>
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
	<script src="./EdorNCDetailInput.js"></script>
	<%@include file="./EdorNCDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>������ϸ��Ϣ</td>
		</tr>
	</table>
	<div id="divInfo1" name="divInfo1" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPlanDetailInfoGrid" ></span>
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
	
	<div id="divInfo2" style="display: ''">
		<br/>
		<table class=common>
			<tr class=common style="display: none">
				<td class=title colspan=6>
					<input type=hidden name=SysPlanCode id=SysPlanCode>
					<input class=common name=PlanType id=PlanType>
					<input class=common name=PremCalType id=PremCalType>
					<input class=common name=PlanFlag id=PlanFlag>
					<input class=common name=OccupTypeFlag id=OccupTypeFlag>
					<input class=common name=OldPlanType id=OldPlanType>
					<input class=common name=OldPremCalType id=OldPremCalType>
					<input class=common name=OldOccupTypeFlag id=OldOccupTypeFlag>
				</td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=PlanCode id=PlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('edorplan', [this,PlanDesc,SysPlanCode,PlanType,PremCalType,PlanFlag,OccupTypeFlag], [0,1,2,3,4,5,6]);" onkeyup="return returnShowCodeListKey('edorplan', [this,PlanDesc,SysPlanCode,PlanType,PremCalType,PlanFlag,OccupTypeFlag], [0,1,2,3,4,5,6]);"><input class=codename name=PlanDesc id=PlanDesc elementtype=nacessary></td>
				<td class=title>����</td>
				<td class=input><input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotrisk',[this,RiskName],[0,1]);" onkeyup="return returnShowCodeListKey('quotrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName id=RiskName elementtype=nacessary></td>
				<td class=title>����</td>
				<td class=input><input class=codeno name=DutyCode id=DutyCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotduty',[this,DutyName],[0,1]);" onkeyup="return returnShowCodeListKey('quotduty',[this,DutyName],[0,1]);"><input class=codename name=DutyName id=DutyName elementtype=nacessary></td>
			</tr>
		</table>
		<!--����Ҫ�ض�̬��3-->
		<div id="divDutyFactor">
			<table class=common>
				<tr class=common>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
		<!--��������¼��̶���-->
		<table class=common>
			<tr class=common id=idAmnt name=idAmnt style="display: none">
				<td class=title>��������</td>
				<td class=input><input class=codeno name=AmntType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('amnttype',[this, AmntTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('amnttype',[this, AmntTypeName],[0,1]);" readonly><input class=codename name=AmntTypeName id=AmntTypeName readonly elementtype=nacessary></td>
				<td class=title id=idFixedAmnt00 name=idFixedAmnt00 style="display: none">�̶�����(Ԫ)</td>
				<td class=input id=idFixedAmnt01 name=idFixedAmnt01 style="display: none"><input class="wid common" name=FixedAmnt id=FixedAmnt verify="�̶�����|num&value>0" elementtype=nacessary></td>
				<td class=title id=idSalaryMult00 name=idSalaryMult00 style="display: none">��н����</td>
				<td class=input id=idSalaryMult01 name=idSalaryMult01 style="display: none"><input class="wid common" name=SalaryMult id=SalaryMult verify="��н����|num&value>0" elementtype=nacessary></td>
				<td class=title id=idMinAmnt00 name=idMinAmnt00 style="display: none">��ͱ���(Ԫ)</td>
				<td class=input id=idMinAmnt01 name=idMinAmnt01 style="display: none"><input class="wid common" name=MinAmnt id=MinAmnt verify="��ͱ���|num&value>0" elementtype=nacessary></td>
				<td class=title id=idMaxAmnt00 name=idMaxAmnt00 style="display: none">��߱���(Ԫ)</td>
				<td class=input id=idMaxAmnt01 name=idMaxAmnt01 style="display: none"><input class="wid common" name=MaxAmnt id=MaxAmnt verify="��߱���|num&value>0" elementtype=nacessary></td>
				<td class=title id=idAmnt00 name=idAmnt00 style="display: ''"></td>
				<td class=input id=idAmnt01 name=idAmnt01 style="display: ''"></td>
				<td class=title id=idAmnt02 name=idAmnt02 style="display: ''"></td>
				<td class=input id=idAmnt03 name=idAmnt03 style="display: ''"></td>
			</tr>
			<tr class=common id=idPrem name=idPrem style="display: none">
				<td class=title>������������</td>
				<td class=input><input class=codeno name=ExceptPremType id=ExceptPremType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('exceptpremtype',[this, ExceptPremTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('exceptpremtype',[this, ExceptPremTypeName],[0,1]);" readonly><input class=codename name=ExceptPremTypeName readonly elementtype=nacessary></td>
				<td class=title>��������(Ԫ)/��������/�����ۿ�</td>
				<td class=input colspan=3><input class=common name=ExceptPrem id=ExceptPrem verify="��������/��������/�����ۿ�|num&value>=0" elementtype=nacessary> <input class=cssButton type=button id=tryCalButton name=tryCalButton style="display: none" value="��  ��" onclick="tryCal();"></td>
			</tr>
			<tr class=common id=idFeeRate name=idFeeRate style="display: none">
				<td class=title>��ʼ����</td>
				<td class=input><input class="wid common" name=InitPrem verify="��ʼ����|num&value>=0" elementtype=nacessary></td>
				<td class=title>����������</td>
				<td class=input><input class="wid common" name=ExceptYield verify="����������|num&value>=0"></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common id=trRelation name=trRelation style="display: none">
				<td class=input colspan=6><input class=checkbox type=checkbox id=SetRelation name=SetRelation onclick="setRelationFlag();">������������<input type=hidden name=RelaShareFlag value="0"></td>
			</tr>
			<tr class=common id=trRelationRate name=trRelationRate style="display: none">
				<td class=title style="display: none">�����������˹�ϵ</td>
				<td class=input style="display: none"><input class=codeno name=RelaToMain id=RelaToMain style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('relation', [this, RelaToMainName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('relation', [this, RelaToMainName], [0,1], null, null, null, 1);" readonly><input class=codename name=RelaToMainName readonly elementtype=nacessary></td>
				<td class=title>���������˱���ռ��</td>
				<td class=input><input class="wid common" name=MainAmntRate id=MainAmntRate verify="���������˱���ռ��|num&value>0&value<=1" elementtype=nacessary></td>
				<td class=title>���������˱���ռ��</td>
				<td class=input><input class="wid common" name=RelaAmntRate id=RelaAmntRate verify="���������˱���ռ��|num&value>0&value<=1" elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<div id="divDutyFactorRelation" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
		<table class=common>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title colspan=6>��ע</td>
			</tr>
			<tr class=common>
				<td class=title colspan=6><textarea cols=80 rows=3 name=Remark id=Remark></textarea><span style="color: red">(���ж�������ҵ�������뵽ҵ�����󴦽���¼��)</span></td>
			</tr>
		</table>
			<div id="divInfoButton" style="display: none">
				<input class=cssButton type=button value="��  ��" onclick="addPlanDetail();">
				<input class=cssButton type=button value="��  ��" onclick="modifyPlanDetail();">
				<input class=cssButton type=button value="ɾ  ��" onclick="delPlanDetail();">
			</div>
			<div id="divInfoClose" style="display: ''">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
			</div>
	</div>
	<div id="divInfo4" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
				</td>
				<td class=titleImg>��������ʹ�ù���</td>
			</tr>
		</table>
		<div id="divInfo3" name="divInfo1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPubAmntRelaPlanGrid" ></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
			</center>
		</div>
		<br/>
		<div id="divInfo5" name="divInfo1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPubAmntRelaDutyGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		<div id="divInfoButton2" style="display: none">
			<input class=cssButton type=button value="��  ��" onclick="saveClick();">
			<input class=cssButton type=button value="��  ��" onclick="cancelClick();">
			<!-- input class=cssButton type=button value="��  ��" onclick="top.close();" -->
		</div>
		
	</div>
	<div id=divInfo6 style="display:none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo7);">
				</td>
				<td class=titleImg>�ɷ�����Ϣ</td>
			</tr>
		</table>
		<div id=divInfo7 style="style:none">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPayFeeGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		<div id="divInfoButton3" style="display: none">
			<input class=cssButton type=button value="��  ��" onclick="saveInfoClick();">
		</div>
	</div>
	<div id=divInfo8 style="display:none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo9);">
				</td>
				<td class=titleImg>Ͷ���˻���Ϣ</td>
			</tr>
		</table>
		<div id=divInfo9 style="style:none">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanTZFeeGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		<div id="divInfoButton4" style="display: none">
			<input class=cssButton type=button value="��  ��" onclick="saveAccClick();">
		</div>
	</div>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
		<input type=hidden name=HiddenCodeType>
	</div>
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
