/***************************************************************
 * <p>ProName��EdorNCDetailInput.js</p>
 * <p>Title��������ϸ��Ϣ¼��</p>
 * <p>Description��������ϸ��Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-07-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//������Ϣ
var tSQLInfo = new SqlClass();

function queryPlanDetail() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql2");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);

	initPlanDetailInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanDetailInfoGrid, 1, 1);//����λ��ʾʹ�ô�������
}
/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	fmPub.all("HiddenCodeType").value = value1;
	if (value1=='edorplan') {

		var tSql = "1";
		tSql = "1 and a.grpcontno=#"+ tPolicyNo + "# and a.edorno=#"+ tEdorNo + "# ";
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}

	if (value1=='quotrisk') {

		if (isEmpty(fm2.PlanCode)) {
			alert("����ѡ�񷽰�");
			return false;
		}

		var tPlanType = fm2.PlanType.value;

		var tSql = "1";

		if (tContPlanType=='00') {//�������ͨ����,�������Ƿ�Ϊ���������,�����,��ô����������������,�������,��ô�������������� �����������ͨ����

			if (tPlanType=="00") {//�ǹ�������

				tSql = "1 and b.startdate<=#"+tCurrentDate+"# and ((case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# or exists (select 1 from ldcode where codetype=#exprisk# and code=#"+tPolicyNo+"# and state=#2#) or exists (select 1 from lcgrppol l where b.riskcode=l.riskcode and l.grpcontno=#"+tPolicyNo+"# )) and a.insuaccflag!=#Y# and b.riskprop!=#I# and not exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#2# and t.code=a.riskcode and t.othersign = #1#)";
				tSql += " and not exists (select 1 from ldcodeexp t where t.codetype = #quotriskexp# and t.code = a.riskcode and t.codeexp =#1# )";//ȥ����������
			} else if (tPlanType=="01") {

				tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#1# and t.code=a.riskcode)";
			} else {
				return null;
			}
		} else if (tContPlanType=='01') {//������

			tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#2# and t.code=a.riskcode)";
		} else if (tContPlanType=='02') {//�˻���
		
			tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and a.insuaccflag=#Y# and b.riskprop!=#I# ";
		} else if (tContPlanType=='03') {//���˲�Ʒ
		
			tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and b.riskprop=#I# ";
		}

		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}

	if (value1=='quotduty') {

		if (isEmpty(fm2.RiskCode)) {
			alert("����ѡ������");
			return false;
		}

		var tSql = "1 and a.riskcode=#"+ fm2.RiskCode.value +"#";

		if (fm2.PlanType.value=="02") {//�����˻�
			tSql += " and a.specflag=#0#";
		} else if (fm2.PlanType.value=="03") {//�����˻�
			tSql += " and a.specflag!=#0#";
		}

		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}

	if (value1=='amnttype') {

		if (tContPlanType=="00") {//��ͨ����,��չ����Ϊ��Ʒ����+��������

			var tPlanType = fm2.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("��ѡ�񷽰���");
				return false;
			}

			var tSql = "amnttype"+ tContPlanType+tPlanType;
			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		} else if (tContPlanType=="01") {//������,��չ����Ϊ��Ʒ����+���Ѽ��㷽ʽ

			var tPremCalType = fm2.PremCalType.value;
			if (tPremCalType==null || tPremCalType=="") {
				alert("��ѡ�񷽰���");
				return false;
			}

			var tSql = "amnttype"+ tContPlanType+tPremCalType;

			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}

		} else if (tContPlanType=="03") {//��������,��չ����Ϊ��Ʒ����+��������

			var tPlanType = fm2.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("��ѡ�񷽰���");
				return false;
			}

			var tSql = "amnttype"+ tContPlanType+tPlanType;

			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		}
	}

	if (value1=='exceptpremtype') {

		var tSql = "exceptpremtype0";
		var tOccupTypeFlag = fm2.OccupTypeFlag.value;
		
		if (returnType=='0') {
		
			if (tOccupTypeFlag=="2") {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		} else {
		
			if (tOccupTypeFlag=="2") {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		}
	}
}

function afterCodeSelect(cCodeType, Field) {

	var tCodeType = fmPub.HiddenCodeType.value;
	fmPub.HiddenCodeType.value = "";

	if (cCodeType=="edorplan") {//����������,���������Ϣ����ѡ������Ϣ��һ��,��ô���÷�����Ϣ

		var tPlanCode = fm2.PlanCode.value;
		var tPlanDesc = fm2.PlanDesc.value;
		var tSysPlanCode = fm2.SysPlanCode.value;
		var tPlanType = fm2.PlanType.value;
		var tPremCalType = fm2.PremCalType.value;
		var tPlanFlag = fm2.PlanFlag.value;
		var tOccupTypeFlag = fm2.OccupTypeFlag.value;

		var tOldPlanType = fm2.OldPlanType.value;
		var tOldPremCalType = fm2.OldPremCalType.value;
		var tOldOccupTypeFlag = fm2.OldOccupTypeFlag.value;

		fm2.OldPlanType.value = tPlanType;
		fm2.OldPremCalType.value = tPremCalType;
		fm2.OldOccupTypeFlag.value = tOccupTypeFlag;

		if (tContPlanType=="01") {

			if (tOldPremCalType==null || tOldPremCalType=="") {
				return;
			}
			if (tPremCalType!=tOldPremCalType) {

				fm2.RiskCode.value = "";
				fm2.RiskName.value = "";
				fm2.DutyCode.value = "";
				fm2.DutyName.value = "";

				dealShowDuty(fm2, "divDutyFactor");

				document.getElementById("divInfo4").style.display = 'none';
			}
		} else {

			if (tOldPlanType==null || tOldPlanType=="") {
				return;
			}

			if (tPlanType!=tOldPlanType) {

				fm2.RiskCode.value = "";
				fm2.RiskName.value = "";
				fm2.DutyCode.value = "";
				fm2.DutyName.value = "";

				dealShowDuty(fm2, "divDutyFactor");

				document.getElementById("divInfo4").style.display = 'none';
			}

			if (tContPlanType!="02") {//�˻��Ͳ�Ʒ�������������Ѵ���

				if (tOldOccupTypeFlag=="1" && tOccupTypeFlag=="2") {//ֻ�е���ְҵ������ְҵ���ת��ʱ,�Ž��вŴ���

						fm2.ExceptPremType.value = "";
						fm2.ExceptPremTypeName.value = "";
						fm2.ExceptPrem.value = "";
				}
			}
		}
	} else if (cCodeType=="quotrisk") {//ѡ�����ֺ�,������μ����ζ�̬��Ϣ

		dealShowDuty(fm2, "divDutyFactor");
	} else if (cCodeType=="quotduty") {//ѡ�����κ�,�������ζ�̬��

		var tRiskCode = fm2.RiskCode.value;
		var tDutyCode = fm2.DutyCode.value;
		var tFlag = 0;
		var tObj = fm2;
		var tDivID = "divDutyFactor";
		var tSysPlanCode = fm2.SysPlanCode.value;
		var tPlanCode = fm2.PlanCode.value;
		var tPlanType = fm2.PlanType.value;

		dealRiskDuty(tRiskCode, tDutyCode, tFlag, tObj, tDivID, tSysPlanCode, tPlanCode, tPlanType);//�ٴ����Ӹ�����
	} else if (cCodeType=="queryexp") {//ѡ�񱣶����ͺ�,�����̬չʾ

		if (tCodeType=="amnttype") {
			dealAmntShow(fm2, Field.value);
		}
	}  else if (cCodeType=="trueflag") {
		dealDutyLimitAmnt('Page');
	}
}

/**
 * ѡ�񱣶����
 */
function dealAmntShow(cObj, tFieldValue) {

	if (tFieldValue=="01") {

		document.getElementById("idFixedAmnt00").style.display = "";
		document.getElementById("idFixedAmnt01").style.display = "";
		document.getElementById("idSalaryMult00").style.display = "none";
		document.getElementById("idSalaryMult01").style.display = "none";
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "none";
		document.getElementById("idMinAmnt01").style.display = "none";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "";
		document.getElementById("idAmnt03").style.display = "";
	} else if (tFieldValue=="02") {

		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "";
		document.getElementById("idSalaryMult01").style.display = "";
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "none";
		document.getElementById("idMinAmnt01").style.display = "none";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "";
		document.getElementById("idAmnt03").style.display = "";
	} else if (tFieldValue=="03") {

		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "";
		document.getElementById("idSalaryMult01").style.display = "";
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "";
		document.getElementById("idMinAmnt01").style.display = "";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "none";
		document.getElementById("idAmnt03").style.display = "none";
	} else if (tFieldValue=="04") {

		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "none";
		document.getElementById("idSalaryMult01").style.display = "none";
		document.getElementById("idMaxAmnt00").style.display = "";
		document.getElementById("idMaxAmnt01").style.display = "";
		document.getElementById("idMinAmnt00").style.display = "";
		document.getElementById("idMinAmnt01").style.display = "";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "none";
		document.getElementById("idAmnt03").style.display = "none";
	}
}

/**
 * ���������������ʱ����
 */
function setRelationFlag() {

	if (document.getElementById("divDutyFactorRelation").style.display=="") {

		fm2.RelaShareFlag.value = "0";
		dealRelationShow(fm2, "0");
	} else {
		fm2.RelaShareFlag.value = "1";
		dealRelationShow(fm2, "1");
	}
}

/**
 * ���������������չʾ������
 */
function dealRelationShow(cObj, tFieldValue) {

	if (tFieldValue=="1") {

		document.getElementById("SetRelation").checked = true;
		document.getElementById("trRelationRate").style.display = "";
		document.getElementById("divDutyFactorRelation").style.display = "";
	} else {

		document.getElementById("SetRelation").checked = false;
		document.getElementById("trRelationRate").style.display = "none";
		document.getElementById("divDutyFactorRelation").style.display = "none";
	}
}

/**
 * ���������ϸ,չʾ��Ϣ
 */
function showPlanDetailInfo() {

	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	var tFlag = 1;
	var tObj = fm2;
	var tDivID = "divDutyFactor";

	var tPlanDesc = PlanDetailInfoGrid.getRowColData(tSelNo, 3);
	var tPlanType = PlanDetailInfoGrid.getRowColData(tSelNo, 4);
	var tPremCalType = PlanDetailInfoGrid.getRowColData(tSelNo, 6);
	var tPlanFlag = PlanDetailInfoGrid.getRowColData(tSelNo, 8);
	var tOccupTypeFlag = PlanDetailInfoGrid.getRowColData(tSelNo, 10);
	var tRiskName = PlanDetailInfoGrid.getRowColData(tSelNo, 13);
	var tDutyName = PlanDetailInfoGrid.getRowColData(tSelNo, 15);

	document.getElementById("SysPlanCode").value = tSysPlanCode;
	document.getElementById("PlanCode").value = tPlanCode;
	document.getElementById("PlanType").value = tPlanType;
	document.getElementById("PremCalType").value = tPremCalType;
	document.getElementById("PlanFlag").value = tPlanFlag;
	document.getElementById("OccupTypeFlag").value = tOccupTypeFlag;
	document.getElementById("OldOccupTypeFlag").value = tOccupTypeFlag;
	document.getElementById("PlanDesc").value = tPlanDesc;
	document.getElementById("RiskCode").value = tRiskCode;
	document.getElementById("RiskName").value = tRiskName;
	document.getElementById("DutyCode").value = tDutyCode;
	document.getElementById("DutyName").value = tDutyName;

	document.getElementById("OldPlanType").value = tPlanType;
	document.getElementById("OldPremCalType").value = tPremCalType;

	dealRiskDuty(tRiskCode, tDutyCode, tFlag, tObj, tDivID, tSysPlanCode, tPlanCode, tPlanType);

	if (tPlanType=="01") {

		document.getElementById("divInfo4").style.display = "";
		queryPubAmntRelaPlanInfo();
		initPubAmntRelaDutyGrid();
	} else {

		document.getElementById("divInfo4").style.display = "none";
	}
	if(tContPlanType=='02'){
		divInfo6.style.display='';
		initPayFeeGrid();
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorNCSql");
		tSQLInfo.setSqlId("EdorNCSql15");
		tSQLInfo.addSubPara(tRiskCode);
		tSQLInfo.addSubPara(tDutyCode);
		tSQLInfo.addSubPara(tPolicyNo);
		tSQLInfo.addSubPara(tSysPlanCode);
		tSQLInfo.addSubPara(tPlanCode);
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);

		turnPage4.queryModal(tSQLInfo.getString(), PayFeeGrid, 1, 1);
	}
}

/**
 * ���ӷ�����ϸ
 */
function addPlanDetail() {

	if (!beforeSubmit(fm2, tContPlanType)) {
		return false;
	}

	dealRedundant(fm2, tContPlanType);
	if (!verifyForm('fm2')) {
		return false;
	}

	fmPub.Operate.value = "ADDPLANDETAIL";
	fm2.action = "./EdorNCDetailSave.jsp?Operate=ADDPLANDETAIL&PolicyNo="+ tPolicyNo +"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&ContPlanType="+ tContPlanType;
	submitForm(fm2);
}

/**
 * �޸ķ�����ϸ
 */
function modifyPlanDetail() {
	fmPub.Operate.value = "MODIFYPLANDETAIL";
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}

	if (!checkSame()) {
		return false;
	}

	dealRedundant(fm2, tContPlanType);

	if (!verifyForm('fm2')) {
		return false;
	}

	if (!beforeSubmit(fm2, tContPlanType)) {
		return false;
	}

	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);

	fm2.action = "./EdorNCDetailSave.jsp?Operate=MODIFYPLANDETAIL&PolicyNo="+ tPolicyNo+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&OSysPlanCode="+ tSysPlanCode +"&OPlanCode="+ tPlanCode +"&ORiskCode="+ tRiskCode +"&ODutyCode="+ tDutyCode +"&ContPlanType="+ tContPlanType;
	submitForm(fm2);
}

/**
 * ɾ��������ϸ,ɾ��ʱ,��У����ѡ����,����,�����Ƿ�һ��
 */
function delPlanDetail() {
	fmPub.Operate.value = "DELPLANDETAIL";
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	if (!checkSame()) {
		return false;
	}

	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);


	fm2.action = "./EdorNCDetailSave.jsp?Operate=DELPLANDETAIL&PolicyNo=" + tPolicyNo + "&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&OSysPlanCode="+ tSysPlanCode +"&OPlanCode="+ tPlanCode +"&ORiskCode="+ tRiskCode +"&ODutyCode="+ tDutyCode +"&ContPlanType="+ tContPlanType;
	submitForm(fm2);
}

/**
 * У���ύ�������Ƿ�Ϊѡ�еļ�¼
 */
function checkSame() {

	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ķ�����ϸ��¼��");
		return false;
	}

	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);

	var tSysPlanCode1 = document.getElementById("SysPlanCode").value;
	var tPlanCode1 = document.getElementById("PlanCode").value;
	var tRiskCode1 = document.getElementById("RiskCode").value;
	var tDutyCode1 = document.getElementById("DutyCode").value;

	if (tSysPlanCode!=tSysPlanCode1 || tPlanCode!=tPlanCode1 || tRiskCode!=tRiskCode1 || tDutyCode!=tDutyCode1) {
		alert("���иò���ʱ�����ɸı䷽�������ּ�������Ϣ��");
		return false;
	}

	return true;
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {

	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}

	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		dealAfterSubmit(content);
	}
}
/**
 * ����������ʾ
 */
function dealShowDuty(cObj, cDivID) {

	document.getElementById(cDivID).innerHTML= "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	document.getElementById("DutyCode").value = "";
	document.getElementById("DutyName").value = "";
	document.getElementById("idAmnt").style.display = "none";
	document.getElementById("idPrem").style.display = "none";
	document.getElementById("idFeeRate").style.display = "none";
	document.getElementById("tryCalButton").style.display = "none";
	document.getElementById("AmntType").value = "";
	document.getElementById("AmntTypeName").value = "";
	document.getElementById("FixedAmnt").value = "";
	document.getElementById("MinAmnt").value = "";
	document.getElementById("ExceptPremType").value = "";
	document.getElementById("ExceptPremTypeName").value = "";
	document.getElementById("ExceptPrem").value = "";
	document.getElementById("InitPrem").value = "";
	document.getElementById("ExceptYield").value = "";
	document.getElementById("Remark").value = "";

	document.getElementById("trRelation").style.display = "none";
	document.getElementById("SetRelation").checked = false;
	document.getElementById("RelaShareFlag").value = "0";
	document.getElementById("trRelationRate").style.display = "none";
	document.getElementById("RelaToMain").value = "";
	document.getElementById("RelaToMainName").value = "";
	document.getElementById("MainAmntRate").value = "";
	document.getElementById("RelaAmntRate").value = "";
	document.getElementById("divDutyFactorRelation").style.display = "none";
	document.getElementById("divDutyFactorRelation").innerHTML= "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
}

/**
 * �����Ʒ����Ҫ��չʾ
 * cRiskCode:����;cDutyCode:����;cFlag:��ʶ(0-��������,1-�����¼);obj:��Ӧ��FM;cDivID:��Ҫչʾ����;
 */
function dealRiskDuty(cRiskCode, cDutyCode, cFlag, cObj, cDivID, cSysPlanCode, cPlanCode, cPlanType) {

	document.getElementById(cDivID).innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	document.getElementById(cDivID+"Relation").innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";

	document.getElementById("AmntType").value = "";
	document.getElementById("AmntTypeName").value = "";
	document.getElementById("FixedAmnt").value = "";
	document.getElementById("SalaryMult").value = "";
	document.getElementById("MaxAmnt").value = "";
	document.getElementById("MinAmnt").value = "";
	document.getElementById("ExceptPremType").value = "";
	document.getElementById("ExceptPremTypeName").value = "";
	document.getElementById("ExceptPrem").value = "";
	document.getElementById("InitPrem").value = "";
	document.getElementById("ExceptYield").value = "";
	document.getElementById("trRelation").style.display = "none";
	document.getElementById("SetRelation").checked = false;
	document.getElementById("RelaShareFlag").value = "0";
	document.getElementById("trRelationRate").style.display = "none";
	document.getElementById("RelaToMain").value = "";
	document.getElementById("RelaToMainName").value = "";
	document.getElementById("MainAmntRate").value = "";
	document.getElementById("RelaAmntRate").value = "";
	document.getElementById("divDutyFactorRelation").style.display = "none";

	if (cFlag=="0") {

		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "none";
		document.getElementById("idSalaryMult01").style.display = "none";
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "none";
		document.getElementById("idMinAmnt01").style.display = "none";
		document.getElementById("idAmnt00").style.display = "";
		document.getElementById("idAmnt01").style.display = "";
		document.getElementById("idAmnt02").style.display = "";
		document.getElementById("idAmnt03").style.display = "";
	}

	//1.���ݲ�Ʒ���ͽ���չʾ����
	if (tContPlanType=="01") {//������

		document.getElementById("idAmnt").style.display = "";
		document.getElementById("idPrem").style.display = "";
		document.getElementById("idFeeRate").style.display = "none";
		document.getElementById("tryCalButton").style.display = "";
		document.getElementById("trRelation").style.display = "none";
	} else if (tContPlanType=="02") {//�˻�������

		document.getElementById("idAmnt").style.display = "none";
		document.getElementById("idPrem").style.display = "none";
		document.getElementById("idFeeRate").style.display = "";
		document.getElementById("tryCalButton").style.display = "none"
		document.getElementById("trRelation").style.display = "none";
	} else if (tContPlanType=="03") {//��������

		document.getElementById("idAmnt").style.display = "";
		document.getElementById("idPrem").style.display = "";
		document.getElementById("idFeeRate").style.display = "none";
		document.getElementById("tryCalButton").style.display = "";
		document.getElementById("trRelation").style.display = "none";
	} else {//���඼Ĭ��Ϊһ������

		document.getElementById("idAmnt").style.display = "";
		document.getElementById("idPrem").style.display = "";
		document.getElementById("idFeeRate").style.display = "none";
		document.getElementById("tryCalButton").style.display = "";

		if (cPlanType=="00") {
			document.getElementById("trRelation").style.display = "";
		} else {
			document.getElementById("trRelation").style.display = "none";
		}
	}

	//2.��ѯ����չʾ�����Ӳ�չʾ�ڽ�����
	var tArr = getDutyElementArr(cRiskCode, cDutyCode);
	if (tArr==null || tArr.length==0) {

	} else {
		document.getElementById(cDivID).innerHTML = getDutyElement(tArr, "0");
		if (document.getElementById("trRelation").style.display=="") {
			document.getElementById(cDivID+"Relation").innerHTML = getDutyElement(tArr, "1");
		}
	}

	if (cFlag=="0") {
		return;
	}

	// �Ƚ��й̶���ĸ�ֵ�Ա㴦�����������˷���
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql7");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	//
	var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr2==null) {
		alert("��ȡ��ѡ��¼��Ϣʧ�ܣ�");
		return false;
	} else {//��ֵ

		var tFixedPlanInfo = new Array();
		var i = 0;

		tFixedPlanInfo[i++] = "AmntType";
		tFixedPlanInfo[i++] = "AmntTypeName";
		tFixedPlanInfo[i++] = "FixedAmnt";
		tFixedPlanInfo[i++] = "SalaryMult";
		tFixedPlanInfo[i++] = "MaxAmnt";
		tFixedPlanInfo[i++] = "MinAmnt";
		tFixedPlanInfo[i++] = "ExceptPremType";

		tFixedPlanInfo[i++] = "ExceptPremTypeName";
		tFixedPlanInfo[i++] = "ExceptPrem";
		tFixedPlanInfo[i++] = "InitPrem";
		tFixedPlanInfo[i++] = "ExceptYield";

		tFixedPlanInfo[i++] = "RelaShareFlag";

		tFixedPlanInfo[i++] = "Remark";

		for(var t=0; t<i; t++) {
			document.getElementById(tFixedPlanInfo[t]).value = tArr2[0][t];
		}

		dealAmntShow(fm2, fm2.AmntType.value);
		dealRelationShow(fm2, fm2.RelaShareFlag.value);
	}
	var tSQLElement;

	if (tArr==null || tArr.length==0) {

	} else {

		tSQLElement = getDutySQLElement(tArr);

		//3.���ݲ�ѯ�ֶν��дӱ��ѯ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorNCSql");
		tSQLInfo.setSqlId("EdorNCSql8");
		tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
		tSQLInfo.addSubPara(tPolicyNo);
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
		tSQLInfo.addSubPara(cRiskCode);
		tSQLInfo.addSubPara(cDutyCode);
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);

		var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		//
		if (tArr1==null) {

		} else {//ѭ���Խ���Ҫ�ظ�ֵ

			for (var i=0; i<tArr.length; i++) {

				tFactorCode = tArr[i][1];//�ӱ��ֶ�
				tFieldType = tArr[i][3];//�ֶ�����
				tValueType = tArr[i][4];//ֵ����

				if (tFieldType=="1") {

					document.all(tFactorCode).value = tArr1[0][i];
					auotContShowCodeName1(tValueType, tArr1[0][i], cObj, tFactorCode+"Name");
				} else {
					document.all(tFactorCode).value = tArr1[0][i];
				}
			}
		}
	}
		if (document.getElementById("trRelation").style.display=="" && document.getElementById("RelaShareFlag").value=="1") {//ֻ�е�������������չʾ�ұ���ѡ��,�Ų�ѯ������Ϣ

			//3.���ݲ�ѯ�ֶν��дӱ��ѯ
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNCSql");
			tSQLInfo.setSqlId("EdorNCSql20");
			if (tSQLElement==null || tSQLElement=="") {
				tSQLInfo.addSubPara("");//SQL��ѯ�ֶ�
			} else {
				tSQLInfo.addSubPara(","+tSQLElement);//SQL��ѯ�ֶ�
			}
			tSQLInfo.addSubPara(tPolicyNo);
			tSQLInfo.addSubPara(cSysPlanCode);
			tSQLInfo.addSubPara(cPlanCode);
			tSQLInfo.addSubPara(cRiskCode);
			tSQLInfo.addSubPara(cDutyCode);
			tSQLInfo.addSubPara(tEdorNo);
			tSQLInfo.addSubPara(tEdorType);

			var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			//
			if (tArr2==null) {

			} else {//ѭ���Խ���Ҫ�ظ�ֵ

				var tRelaFactor = "Relation";
				var tRelaFactorName = "��������";
				document.getElementById("RelaToMain").value = tArr2[0][0];
				document.getElementById("RelaToMainName").value = tArr2[0][1];
				document.getElementById("MainAmntRate").value = tArr2[0][2];
				document.getElementById("RelaAmntRate").value = tArr2[0][3];

			}
			if (tArr==null) {

			} else {

			for (var i=0; i<tArr.length; i++) {

				tFactorCode = tRelaFactor + tArr[i][1];//�ӱ��ֶ�
				tFieldType = tArr[i][3];//�ֶ�����
				tValueType = tArr[i][4];//ֵ����
				var j = i+4;
				if (tFieldType=="1") {

					document.all(tFactorCode).value = tArr2[0][j];
					auotContShowCodeName1(tValueType, tArr2[0][j], cObj, tFactorCode+"Name");
				} else {
					document.all(tFactorCode).value = tArr2[0][j];
				}
			}
		}
	}
}
function auotContShowCodeName1(cCodeType, cCode, cObj, cCodeName) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql12");
	tSQLInfo.addSubPara(cCodeType);
	tSQLInfo.addSubPara(cCode);

	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {

	} else {
		document.all(cCodeName).value=tArr[0][0];
	}
}
/**
 * �ύʱ������������
 */
function dealRedundant(cObj, cContPlanType) {

	if (cContPlanType=="00") {//��ͨ����

		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType=="01") {//�̶�����

			document.getElementById("SalaryMult").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="02") {//��н����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="03") {//��н��������ͱ���ȡ����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
		} else if (tAmntType=="04") {//�ǹ̶�����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("SalaryMult").value = "";
		}

		//ֻ����ͨ����+�ǹ��������+δѡ������ʱ,�Ž������´���
		if (document.getElementById("PlanType").value=="00" && document.getElementById("divDutyFactorRelation").style.display=="none") {

			document.getElementById("MainAmntRate").value = "";
			document.getElementById("RelaAmntRate").value = "";
			document.getElementById("RelaToMain").value = "";
			document.getElementById("RelaToMainName").value = "";

			var tRiskCode = document.getElementById("RiskCode").value;
			var tDutyCode = document.getElementById("DutyCode").value;
			if (tRiskCode==null || tRiskCode =="" || tDutyCode==null || tDutyCode=="") {
				return ;
			}
			var tArr = getDutyElementArr(tRiskCode, tDutyCode);
			dealRedundantRelaElement(cObj, tArr);
		}
	} else if (cContPlanType=="01") {//����������

		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType=="01") {//�̶�����

			document.getElementById("SalaryMult").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="02") {//��н����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="03") {//��н��������ͱ���ȡ����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
		} else if (tAmntType=="04") {//�ǹ̶�����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("SalaryMult").value = "";
		}
	} else if (cContPlanType=="02") {//�˻�������

		document.getElementById("AmntType").value = "";
		document.getElementById("FixedAmnt").value = "";
		document.getElementById("SalaryMult").value = "";
		document.getElementById("MaxAmnt").value = "";
		document.getElementById("MinAmnt").value = "";
	} else if (cContPlanType=="03") {//��������

		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType=="01") {//�̶�����

			document.getElementById("SalaryMult").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="02") {//��н����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="03") {//��н��������ͱ���ȡ����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
		} else if (tAmntType=="04") {//�ǹ̶�����

			document.getElementById("FixedAmnt").value = "";
			document.getElementById("SalaryMult").value = "";
		}
	}
}

/**
 * �ύǰУ��
 */
function beforeSubmit(cObj, cContPlanType) {

	/**
	 * У�鷶Χ:
	 * 1.���ݲ�Ʒ���ͽ��б�������У��
	 * 2.���ݱ������ͽ��б�������ֶ�У��
	 * 3.��̬չʾ���У��
	 * 4.�̶���У��
	 */
	if (isEmpty(cObj.PlanCode)) {

		alert("�������벻��Ϊ�գ�");
		return false;
	}

	if (isEmpty(cObj.RiskCode)) {

		alert("���ֲ���Ϊ�գ�");
		return false;
	}

	if (isEmpty(cObj.DutyCode)) {

		alert("���β���Ϊ�գ�");
		return false;
	}

	if (cContPlanType=="02") {//�����˻��Ͳ�Ʒ,�ޱ������͵��������,У��������ʼ���Ѽ�����������

		var tInitPrem = document.getElementById("InitPrem").value;
		if (tInitPrem==null || tInitPrem=="") {
			alert("��ʼ���Ѳ���Ϊ�գ�");
			return false;
		}

		if (!checkDecimalFormat(tInitPrem, 12, 2)) {
			alert("��ʼ��������λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
			return false;
		}

		var tExceptYield = document.getElementById("ExceptYield").value;
		if (tExceptYield==null || tExceptYield=="") {

		} else {

			if (!checkDecimalFormat(tExceptYield, 1, 6)) {
				alert("��������������λ��Ӧ����1λ,С��λ��Ӧ����6λ��");
				return false;
			}
		}
	} else {

		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType==null || tAmntType=="") {
			alert("��ѡ�񱣶����ͣ�");
			return false;
		}

		if (tAmntType=="01") {//�̶�����

			var tFixedAmnt = document.getElementById("FixedAmnt").value;
			if (tFixedAmnt==null || tFixedAmnt=="") {
				alert("�̶������Ϊ�գ�");
				return false;
			}

			if (!checkDecimalFormat(tFixedAmnt, 12, 2)) {
				alert("�̶���������λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
				return false;
			}
		} else if (tAmntType=="02") {//��н����

			var tSalaryMult = document.getElementById("SalaryMult").value;
			if (tSalaryMult==null || tSalaryMult=="") {
				alert("��н��������Ϊ�գ�");
				return false;
			}

			if (!checkDecimalFormat(tSalaryMult, 5, 2)) {
				alert("��н��������λ��Ӧ����4λ,С��λ��Ӧ����2λ��");
				return false;
			}
		} else if (tAmntType=="03") {//��н��������ͱ���ȡ����

			var tSalaryMult = document.getElementById("SalaryMult").value;
			if (tSalaryMult==null || tSalaryMult=="") {
				alert("��н��������Ϊ�գ�");
				return false;
			}

			if (!checkDecimalFormat(tSalaryMult, 5, 2)) {
				alert("��н��������λ��Ӧ����4λ,С��λ��Ӧ����2λ��");
				return false;
			}

			var tMinAmnt = document.getElementById("MinAmnt").value;
			if (tMinAmnt==null || tMinAmnt=="") {
				alert("��ͱ����Ϊ�գ�");
				return false;
			}

			if (!checkDecimalFormat(tMinAmnt, 12, 2)) {
				alert("��ͱ�������λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
				return false;
			}
		} else if (tAmntType=="04") {//�ǹ̶�����

			var tMaxAmnt = document.getElementById("MaxAmnt").value;
			if (tMaxAmnt==null || tMaxAmnt=="") {
				alert("��߱����Ϊ�գ�");
				return false;
			}

			if (!checkDecimalFormat(tMaxAmnt, 12, 2)) {
				alert("��߱�������λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
				return false;
			}

			var tMinAmnt = document.getElementById("MinAmnt").value;
			if (tMinAmnt==null || tMinAmnt=="") {
				alert("��ͱ����Ϊ�գ�");
				return false;
			}

			if (!checkDecimalFormat(tMinAmnt, 12, 2)) {
				alert("��ͱ�������λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
				return false;
			}

			if (Number(tMinAmnt)>Number(tMaxAmnt)) {
				alert("��ͱ��Ӧ������߱��");
				return false;
			}
		}
		var tExceptPremType = cObj.ExceptPremType.value;
		if (tExceptPremType==null || tExceptPremType=="") {
			alert("�����������Ͳ���Ϊ�գ�");
			return false;
		}

		if (fmPub.Operate.value!="TRYCAL") {
			var tExceptPrem = cObj.ExceptPrem.value;
			if (tExceptPrem==null || tExceptPrem=="") {
				alert("��������/��������/�����ۿ۲���Ϊ�գ�");
				return false;
			}

			if (tExceptPremType=="01") {//����,12,2

				if (!checkDecimalFormat(tExceptPrem, 12, 2)) {

					alert("��������������Ϊ��������ʱ����������/��������/�����ۿ�����λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
					return false;
				}
			} else if (tExceptPremType=="02") {//����

				if (!checkDecimalFormat(tExceptPrem, 4, 8)) {

					alert("��������������Ϊ��������ʱ����������/��������/�����ۿ�����λ��Ӧ����4λ,С��λ��Ӧ����6λ��");
					return false;
				}
			} else if (tExceptPremType=="03") {//�ۿ�

				if (!checkDecimalFormat(tExceptPrem, 2, 2)) {

					alert("��������������Ϊ�����ۿ�ʱ����������/��������/�����ۿ�����λ��Ӧ����2λ,С��λ��Ӧ����2λ��");
					return false;
				}
			}
		}
	}
	var tRiskCode = cObj.RiskCode.value;
	var tDutyCode = cObj.DutyCode.value;
	var tArr = getDutyElementArr(tRiskCode, tDutyCode);
	if (!checkDutyElement(cObj, tArr, "0")) {
		return false;
	}

	if (cContPlanType=="00" && fm2.RelaShareFlag.value=="1") {

		var tMainAmntRate = fm2.MainAmntRate.value;
		var tRelaAmntRate = fm2.RelaAmntRate.value;
		if (tMainAmntRate==null || tMainAmntRate=="") {
			alert("���������˱���ռ�Ȳ���Ϊ�գ�");
			return false;
		} else {

			if (!checkDecimalFormat(tMainAmntRate, 1, 2)) {
				alert("���������˱���ռ������λ��Ӧ����1λ,С��λ��Ӧ����2λ��");
				return false;
			}
		}

		if (tRelaAmntRate==null || tRelaAmntRate=="") {
			alert("���������˱���ռ�Ȳ���Ϊ�գ�");
			return false;
		} else {

			if (!checkDecimalFormat(tRelaAmntRate, 1, 2)) {
				alert("���������˱���ռ������λ��Ӧ����1λ,С��λ��Ӧ����2λ��");
				return false;
			}
		}

		if (!checkDutyElement(cObj, tArr, "1")) {
			return false;
		}
	}

	return true;
}

/**
 * ��ѯ�����������Ϣ
 */
function queryPubAmntRelaPlanInfo(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql10");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);

	initPubAmntRelaPlanGrid();
	turnPage2.queryModal(tSQLInfo.getString(), PubAmntRelaPlanGrid, 1, 1);//����λ��ʾʹ�ô�������
}

/**
 * ��ѯ������������������Ϣ
 */
function showRelaDuty(){

	var tRow = PubAmntRelaPlanGrid.getSelNo();
	var tSysPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,1);
	var tPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,2);

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql11");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);

	initPubAmntRelaDutyGrid();
	//
	noDiv(turnPage3, PubAmntRelaDutyGrid, tSQLInfo.getString());

	var arrResult = turnPage3.arrDataCacheSet;//��MulLine��ֵת��Ϊ��ά����
	for(var i=0;i<PubAmntRelaDutyGrid.mulLineCount ;i++){

		var tPubFlag = PubAmntRelaDutyGrid.getRowColData(i,5);
		dealDutyLimitAmnt('Query',i,tPubFlag);
	}
}

/**
 * ��������ʹ�ù���--����
 */
function saveClick() {

	var tRow = PubAmntRelaPlanGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ�������������Ϣ��");
		return false;
	}
	if (!checkPubAmnt()){
		return false;
	}
	var tSysPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,1);//ϵͳ��������
	var tPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,2);//��������
	var tLimitAmnt = PubAmntRelaPlanGrid.getRowColData(tRow-1,5);//�����޶�

	fmPub.Operate.value = "SAVEPUBAMNT";
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=SAVEPUBAMNT&PolicyNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&LimitAmnt="+ tLimitAmnt+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType;;
	submitForm(fm2);
}
/**
 * �ɷѽ��--����
 */
function saveInfoClick(){
	var tRow = PayFeeGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ���ɷ�����Ϣ��");
		return false;
	}
	if(PayFeeGrid.getRowColData(tRow-1,5)==null||PayFeeGrid.getRowColData(tRow-1,5)==''){
		alert("��¼��ɷѽ��!");
		return false;
	}
	if((!isNumeric(PayFeeGrid.getRowColData(tRow-1,5)))||PayFeeGrid.getRowColData(tRow-1,5)<0){
		alert("�ɷѽ��ӦΪ���ڵ���0������!");
		return false;
	}
	if(!beforeSub()){
		return false;
	}
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,1);//ϵͳ��������
	var tPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,2);//��������
	var tRiskCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,12);//���ֱ���

	fmPub.Operate.value = "SAVEFEE";
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=SAVEFEE&PolicyNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&RiskCode="+ tRiskCode+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType;
	submitForm(fm2);
}
/**
 * ��������ʹ�ù���--ȡ��
 */
function cancelClick() {

	var tRow = PubAmntRelaPlanGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ�������������Ϣ��");
		return false;
	}

	var tSysPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,1);//ϵͳ��������
	var tPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,2);//��������

	fmPub.Operate.value = "DELETEPUBAMNT";
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=DELETEPUBAMNT&PolicyNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode;
	submitForm(fm2);
}


/**
 * У�鹫����������޶�����޶�֮��Ĺ�ϵ
 */
function checkPubAmnt() {

	var tPlanRow = PlanDetailInfoGrid.getSelNo();
	//var tAmntType = PlanDetailInfoGrid.getRowColData(tPlanRow-1,15);//��������
	var tFixedAmnt = PlanDetailInfoGrid.getRowColData(tPlanRow-1,18);//�̶�����

	var tPubAmntRow = PubAmntRelaPlanGrid.getSelNo();
	var tLimitAmnt = PubAmntRelaPlanGrid.getRowColData(tPubAmntRow-1,5);//�����޶�

	if (tLimitAmnt != null && tLimitAmnt != "") {

		if (!isNumeric(tLimitAmnt)) {
			alert("�����޶����Ч�����֣�");
			return false;
		}

		if (Number(tLimitAmnt)<=0) {
			alert("�����޶�Ӧ����0��");
			return false;
		}

		//У������޶�ܴ��ڹ�������
		if (Number(tLimitAmnt)>Number(tFixedAmnt)) {
			alert("�����޶�ܴ��ڹ������");
			return false;
		}
	}

	//У�������޶�
	for(var i=0;i < PubAmntRelaDutyGrid.mulLineCount;i++){

		var tPubFlag = PubAmntRelaDutyGrid.getRowColData(i,5);//��������ʹ�ñ�־
		var tDutyLimitAmnt = PubAmntRelaDutyGrid.getRowColData(i,7);//�����޶�

		if (tPubFlag==null || tPubFlag=="") {
			alert("��["+(i+1)+"]���Ƿ�ʹ�ù��������־��ʽ��Ч��");
			return false;
		}
		if (tPubFlag == "1") {

			if(tDutyLimitAmnt == null || tDutyLimitAmnt.trim() == "") {//δ¼�������޶�

				alert("��["+(i+1)+"]�������޶��Ϊ�գ�");
				return false;
			} else {//¼���������޶�

				if (!isNumeric(tDutyLimitAmnt)) {
					alert("��["+(i+1)+"]�������޶����Ч�����֣�");
					return false;
				}

				if (Number(tDutyLimitAmnt)<=0) {
					alert("��["+(i+1)+"]�������޶�Ӧ����0��");
					return false;
				}

				var tLimitAmntNew = PubAmntRelaPlanGrid.getRowColData(tPubAmntRow-1,5);//�����޶�

				if (tLimitAmntNew != null && tLimitAmntNew != "") {

					if (Number(tDutyLimitAmnt)>Number(tLimitAmntNew)) {
						alert("��["+(i+1)+"]�������޶�ܴ��ڸ����޶");
						return false;
					}
				} else {

					if (Number(tDutyLimitAmnt)>Number(tFixedAmnt)) {
						alert("��["+(i+1)+"]�������޶�ܴ��ڹ������");
						return false;
					}
				}
			}
		}
	}
	return true;
}

/**
 * �����޶��Ƿ�ֻ������
 */
function dealDutyLimitAmnt(cMark,cRow,cPubFlag) {

	var rowNumber;
	var tPubFlag;
	if (cMark == "Page") {
		rowNumber = PubAmntRelaDutyGrid.lastFocusRowNo;
		tPubFlag = PubAmntRelaDutyGrid.getRowColData(rowNumber, 5);
	} else if (cMark == "Query") {
		rowNumber = cRow;
		tPubFlag = cPubFlag;
	}
	if (tPubFlag == "1") {
		document.getElementById("spanPubAmntRelaDutyGrid"+rowNumber).all('PubAmntRelaDutyGrid7').readOnly = false;
	} else {
		document.getElementById("spanPubAmntRelaDutyGrid"+rowNumber).all('PubAmntRelaDutyGrid7').readOnly = true;
		PubAmntRelaDutyGrid.setRowColData(rowNumber,7,"");
	}
}


function dealRedundantRelaElement(cObj, tArr) {

	var tRelaFactor = "Relation";
	if (tArr==null) {
		//δ��ѯ������,��ʾ�޶�̬����
	} else {

		for (var i=0; i<tArr.length; i++) {

			var tCalFactor = tArr[i][0];
			var tFactorCode = tArr[i][1];
			var tFactorName = tArr[i][2];
			var tFieldType = tArr[i][3];
			var tValueType = tArr[i][4];
			var tDefaultValue = tArr[i][5];
			var tFieldLength = tArr[i][6];
			var tMandatoryFlag = tArr[i][8];
			var tDefalutName = tArr[i][9];

			if (tDefaultValue==null || tDefaultValue=="") {

				tFactorCode = tRelaFactor + tArr[i][1];
				tFieldType = tArr[i][3];
				document.all(tFactorCode).value = "";
				if (tFieldType=="1") {//������,�������codename

					document.all(tFactorCode+"Name").value = "";
				}
			} else {//��Ĭ��ֵ,��ԭֵ

				tFactorCode = tRelaFactor + tArr[i][1];
				tFieldType = tArr[i][3];
				document.all(tFactorCode).value = tDefaultValue;
				if (tFieldType=="1") {//������,�������codename

					document.all(tFactorCode+"Name").value = tDefalutName;
				}
			}
		}
	}

	return true;
}
function showTZInfo(){
	divInfo8.style.display='';
	var tRow = PayFeeGrid.getSelNo();
	var tPayPlanCode =PayFeeGrid.getRowColData(PayFeeGrid.getSelNo()-1, 3);
	var tRiskCode =PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 12);

	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 2);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 14);
	initTZFeeGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql16");
	tSQLInfo.addSubPara(tRiskCode);
	tSQLInfo.addSubPara(tPayPlanCode);
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	tSQLInfo.addSubPara(tDutyCode);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	turnPage5.queryModal(tSQLInfo.getString(), TZFeeGrid, 1, 1);

	divInfo8.style.display = "";
	if (TZFeeGrid.mulLineCount>1) {
		divInfoButton4.style.display = "";
	} else {
		divInfoButton4.style.display = "none";
	}

}
/**
 * Ͷ���˻���Ϣ--����
 */
function saveAccClick(){
	var tRow = TZFeeGrid.mulLineCount;
	for(var index=0;index<tRow;index++){
		if(TZFeeGrid.getRowColData(index,5)==null||TZFeeGrid.getRowColData(index,5)==''){
			alert("��¼�����Ͷ�ʽ��!");
			return false;
		}
		if((!isNumeric(TZFeeGrid.getRowColData(index,5)))||TZFeeGrid.getRowColData(index,5)<0){
			alert("����Ͷ�ʽ��ӦΪ���ڵ���0������!");
			return false;
		}
		if(TZFeeGrid.getRowColData(index,6)==null||TZFeeGrid.getRowColData(index,6)==''){
			alert("��¼�����Ͷ�ʷ������!");
			return false;
		}
		if((!isNumeric(TZFeeGrid.getRowColData(index,6)))||TZFeeGrid.getRowColData(index,6)<0||TZFeeGrid.getRowColData(index,6)>1){
			alert("����Ͷ�ʷ������ӦΪ���ڵ���0��С�ڵ���1������!");
			return false;
		}
		if(!beforeSub()){
			return false;
		}
	}
	var num = PayFeeGrid.getSelNo();
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,1);//ϵͳ��������
	var tPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,2);//��������
	var tRiskCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,12);//���ֱ���
	var tDutyCode = PayFeeGrid.getRowColData(num-1,1);
	var tPayPlanCode = PayFeeGrid.getRowColData(num-1,3);

	fmPub.Operate.value = "SAVEACC";
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=SAVEACC&GrpPropNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&RiskCode="+ tRiskCode+"&DutyCode="+tDutyCode+"&PayPlanCode="+tPayPlanCode;
	submitForm(fm2);
}
//У��ɷѽ������ڳ�ʼ����
function beforeSub(){
	var tInitPrem = fm2.InitPrem.value;
	var rowNum = PayFeeGrid.mulLineCount;
	var index=0;
	for(var i=0;i < rowNum;i++){
		var k = new Number(PayFeeGrid.getRowColData(i,5));
		index=index+k;
	}
	if(!(index==tInitPrem)){
		alert("�ɷѽ��֮��Ӧ���ڳ�ʼ����!");
		return false;
	}
	return true;
}
//У�����Ͷ�ʽ��֮�͵��ڽɷѽ��
function beforeSubmiUl(){
	var rowNum = PayFeeGrid.getSelNo();
	var index = PayFeeGrid.getRowColData(rowNum-1,5);
	var tRowNum = TZFeeGrid.mulLineCount;
	var index1=0;
	for(var i=0;i < tRowNum;i++){
		var k = new Number(TZFeeGrid.getRowColData(i,5));
		index1=index1+k;
	}
	if(!(index==index1)){
		alert("����Ͷ�ʽ��֮��Ӧ���ڽɷѽ��!");
		return false;
	}
	var index2=0;
	for(var i=0;i < tRowNum;i++){
		var k = new Number(TZFeeGrid.getRowColData(i,6));
		index2=index2+k;
	}
	if(!(index2==1)){
		alert("����Ͷ�ʷ������֮��Ӧ����1!");
		return false;
	}
	return true;
}
/**
 * ����
 */
function tryCal() {

	fmPub.Operate.value = "TRYCAL";

	var tExceptPremType = fm2.ExceptPremType.value;
	if (tExceptPremType==null || tExceptPremType=="") {
		alert("����ѡ�������������ͣ�");
		return false;
	}

	if (tExceptPremType=="03") {
		alert("������������Ϊ�����ۿ�ʱ���޲ο�ֵ��");
		return false;
	}

	//�������ӷ�������ʽ
	dealRedundant(fm2, tContPlanType);

	if (!verifyForm('fm2')) {
		return false;
	}

	if (!beforeSubmit(fm2, tContPlanType)) {
		return false;
	}

	fm2.action = "./EdorNCTryCalSave.jsp?Operate=TRYCAL&PolicyNo="+ tPolicyNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&ContPlanType="+ tContPlanType+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType;
	submitForm(fm2);
}

function afterSubmitTryCal(FlagStr, content) {

	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}

	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	} else {

		dealAfterSubmit(content);
	}
}
/**
 * �ύ����
 */
function dealAfterSubmit(o) {

	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";

	if (tOperate=="ADDPLANDETAIL" || tOperate=="MODIFYPLANDETAIL" || tOperate=="DELPLANDETAIL") {

		fm2.PlanType.value = "";
		fm2.OldPlanType.value = "";
		fm2.PremCalType.value = "";
		fm2.OldPremCalType.value = "";
		fm2.PlanFlag.value = "";
		fm2.OccupTypeFlag.value = "";
		fm2.OldOccupTypeFlag.value = "";
		fm2.PlanCode.value = "";
		fm2.PlanDesc.value = "";
		fm2.SysPlanCode.value = "";
		fm2.RiskCode.value = "";
		fm2.RiskName.value = "";
		fm2.DutyCode.value = "";
		fm2.DutyName.value = "";

		dealShowDuty(fm2, "divDutyFactor");

		queryPlanDetail();
		document.getElementById("divInfo4").style.display = "none";
		document.getElementById("divInfo5").style.display = "none";
		document.getElementById("divInfo6").style.display = "none";
		document.getElementById("divInfo8").style.display = "none";

		initPubAmntRelaDutyGrid();
		initPubAmntRelaPlanGrid();
		initPayFeeGrid();
		initTZFeeGrid();

	} else if (tOperate=="SAVEPUBAMNT" || tOperate=="DELETEPUBAMNT"){

		queryPubAmntRelaPlanInfo();
		initPubAmntRelaDutyGrid();
	} else if (tOperate=="TRYCAL") {
		fm2.ExceptPrem.value = o;
	} else if(tOperate=="SAVEFEE" ){
		showTZInfo();
	} else if(tOperate=="SAVEACC"){
		showTZInfo();
	}
}
