/***************************************************************
 * <p>ProName��LSQuotProjUWDetailInput.js</p>
 * <p>Title����Ŀѯ�ۺ˱���ϸ</p>
 * <p>Description����Ŀѯ�ۺ˱���ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-31
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();
var tTranPremMode;//���ѷ�̯��ʽ
var tPlanDetailOpen;

/**
 * ѯ�ۻ�����Ϣ��ѯ
 */
function queryQuotBasic() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm3.ProjQuotNo.value = tQuotNo;
		fm3.ProjQuotBatNo.value = tQuotBatNo;
		fm3.ProjName.value = strQueryResult[0][0];
		fm3.TargetCust.value = strQueryResult[0][1];
		fm3.NumPeople.value = strQueryResult[0][2];
		fm3.ProjPrePrem.value = strQueryResult[0][3];
		fm3.PreLossRatio.value = strQueryResult[0][4];
		fm3.Partner.value = strQueryResult[0][5];
		fm3.ExpiryDate.value = strQueryResult[0][6];
		tTranProdType = strQueryResult[0][7];
		fm3.ProjProdTypeName.value = strQueryResult[0][8];
		fm3.ProjBlanketFlagName.value = strQueryResult[0][10];
		fm3.ProjDesc.value = strQueryResult[0][11];
		fm3.ElasticFlag.value = strQueryResult[0][12];
		fm3.ElasticFlagName.value = strQueryResult[0][13];
		
		if (tTranProdType=="00") {
			
			document.getElementById("tdElasticFlag1").style.display = '';			
			document.getElementById("tdElasticFlag2").style.display = '';
			document.getElementById("tdElasticFlag3").style.display = '';
			document.getElementById("tdElasticFlag4").style.display = '';
			document.getElementById("tdElasticFlag5").style.display = 'none';
			document.getElementById("tdElasticFlag6").style.display = 'none';
		} else {
			document.getElementById("tdElasticFlag1").style.display = 'none';			
			document.getElementById("tdElasticFlag2").style.display = 'none';
			document.getElementById("tdElasticFlag3").style.display = '';
			document.getElementById("tdElasticFlag4").style.display = '';
			document.getElementById("tdElasticFlag5").style.display = '';
			document.getElementById("tdElasticFlag6").style.display = '';
		}
	}
}

/**
 * ѯ���������Ͳ�ѯ
 */
function querySaleChannel() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara("");
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var tElementCode;//���ӱ���
	var tElementName;//��������
	var tIsSelected;//�Ƿ�ѡ��
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>��������</td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][2];
		tIsSelected = tArr[i][3];
		
		tInnerHTML1 += "<input type=checkbox disabled name="+ tElementCode;
		if (tIsSelected=='0') {//ѯ�۱���û�б��������

			tInnerHTML1 += ">"+ tElementName;
		} else {//ѯ���б����˸�����

			tInnerHTML1 += " checked>"+ tElementName;
		}
		
		tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	document.getElementById("divSaleChannel").innerHTML = tInnerHTML0;
}

/**
 * ѯ���н������ѯ
 */
function queryQuotAgency() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage4, AgencyListGrid, tSQLInfo.getString())) {
		initAgencyListGrid();
		return false;
	}
	
	if (AgencyListGrid.mulLineCount>0) {
	
		document.getElementById('divAgencyInfo').style.display = '';
	}
}

/**
 * ѯ�۱��ϲ㼶���ֱ�׼��ѯ
 */
function queryPlanDiv() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var tElementCode;//���ӱ���
	var tElementName;//��������
	var tControlFlag;//�Ƿ���¼���
	var tIsSelected;//�Ƿ�ѡ��
	var tOElementValue;//ԭʼֵ
	var tNElementValue;//ѯ��ֵ
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>���ϲ㼶���ֱ�׼</td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];
		tOElementValue = tArr[i][4];
		tNElementValue = tArr[i][5];
		
		tInnerHTML1 += "<input type=checkbox disabled name="+ tElementCode;
		if (tIsSelected=='0') {//ѯ�۱���û�б��������

			tInnerHTML1 += ">"+ tElementName;
		} else {//ѯ���б����˸�����

			tInnerHTML1 += " checked>"+ tElementName;
		}
		
		if (tControlFlag=='1') {//����¼���

			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tControlFlag +"><input style='width:50px' class=readonly name="+ tElementCode +"Value value=\""+ tNElementValue +"\" readonly>";//����¼����,��Ϊֻ��ģʽ
		} else {
			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";	
		}
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	document.getElementById("divPlanDiv").innerHTML = tInnerHTML0;
}

/**
 * ��Ŀ��ѯ�۽ɷѷ�ʽ��ѯ
 */
function queryPayIntv() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql7");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrPayIntv = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var tElementCode;//���ӱ���
	var tElementName;//��������
	var tIsSelected;//�Ƿ�ѡ��
	var tPayIntv;//�ɷѷ�ʽ
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>�ɷѷ�ʽ<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArrPayIntv.length; i++) {
	
		tElementCode = tArrPayIntv[i][0];
		tElementName = tArrPayIntv[i][1];
		tIsSelected = tArrPayIntv[i][2];
		tPayIntv = tArrPayIntv[i][3];
		
		tInnerHTML1 += "<input type=checkbox disabled name="+ tElementCode;
		if (tIsSelected=='0') {//ѯ�۱���û�б��������
			tInnerHTML1 += ">"+ tElementName;
		} else {//ѯ���б����˸�����

			tInnerHTML1 += " checked>"+ tElementName;
		}
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	document.getElementById("divPayIntvDiv").innerHTML = tInnerHTML0;
}

/**
 * ��Ŀ��ѯ�����û�����ѯ
 */
function queryCom() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql8");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage3, ComListGrid, tSQLInfo.getString())) {
		initComListGrid();
		return false;
	}
	
	if (ComListGrid.mulLineCount>0) {
	
		document.getElementById('divComInfo').style.display = '';
	}
}

/**
 * ��Ŀ��ѯ�۹���ѯ�ۺŲ�ѯ
 */
function queryRelaQuot() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql9");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage5, RelaQuotListGrid, tSQLInfo.getString())) {
		initRelaQuotListGrid();
		return false;
	}
	
	if (RelaQuotListGrid.mulLineCount>0) {
	
		document.getElementById('RelaQuotFlag').checked = true;
		document.getElementById('divRelaQuotInfo').style.display = '';
	}
}

/**
 * ��ʼ���ڶ�����Ϣ
 */
function initQuotStep2() {
	
	queryPlanInfo();
	pubInitQuotStep2(fmPlan, tQuotType, tTranProdType, tTranPremMode, '');
}

/**
 * ��ʼ��������Ϣ��ѯ
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql25");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanInfoGrid();
	turnPage6.queryModal(tSQLInfo.getString(), PlanInfoGrid, 2, 1);
}

/**
 * ѡ�����
 */
function showPlanInfo() {
	
	pubShowPlanInfo(fmPlan, tQuotType, tTranProdType);
}

/**
 * ѡ�񵥶�ְҵ���
 */
function chooseOccupFlag(cQuotFlag) {

	pubChooseOccupFlag(fmPlan, cQuotFlag);
}

/**
 * ���ӷ���
 */
function addPlan() {
	
	dealRedundant(fmPlan, tQuotType, tTranProdType);
	if (!verifyForm('fmPlan')) {
		return false;
	}
	
	if (!pubBeforeSubmit(fmPlan, tQuotType, tTranProdType)) {//�ύǰ�Ĺ���У��
		return false;
	}
	
	fmPub.Operate.value = "ADDPLAN";
	fmPlan.action = "./LSQuotProjPlanSave.jsp?Operate=ADDPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmPlan);
}

/**
 * �޸ķ���
 */
function modifyPlan() {
	
	var tSelNo = PlanInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
	
		alert("��ѡ��Ҫ�޸ĵķ�����¼��");
		return false;
	}
	
	dealRedundant(fmPlan, tQuotType, tTranProdType);
	if (!verifyForm('fmPlan')) {
		return false;
	}
	
	if (!pubBeforeModifyPlan(fmPlan, tTranProdType)) {
		return false;
	}
	
	if (!pubBeforeSubmit(fmPlan, tQuotType, tTranProdType)) {//�ύǰ�Ĺ���У��
		return false;
	}
	
	fmPub.Operate.value = "MODIFYPLAN";
	fmPlan.action = "./LSQuotProjPlanSave.jsp?Operate=MODIFYPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmPlan);
}

/**
 * ɾ������
 */
function delPlan() {
	
	var tSelNo = PlanInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
	
		alert("��ѡ��Ҫɾ���ķ�����¼��");
		return false;
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fmPub.Operate.value = "DELPLAN";
	fmPlan.action = "./LSQuotProjPlanSave.jsp?Operate=DELPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmPlan);
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

	document.getElementById("HiddenCodeType").value = value1;
	if (value1=='plantype') {
		
		//var tSql = "plantype|"+tTranProdType;
		var tSql = "1 and codetype=#quotplantype# and codeexp=#"+ tTranProdType +"#";
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='occuptype1') {//��ְҵ��������
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=='occupmidtype') {
		
		var tOccupType = fmPlan.OccupType.value;
		var conditionOccupMidType = "1 and b.occupationtype= #"+tOccupType+"# ";
		if (returnType=='0') {
			
			if (isEmpty(fmPlan.OccupType)) {
				alert("����ѡ��ְҵ���");
				return false;
			}
			
			return showCodeList('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		} else {
			
			if (isEmpty(fmPlan.OccupType)) {
				alert("����ѡ��ְҵ���");
				return false;
			}
			return showCodeListKey('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		}
	} else if (value1=='occupcode') {
		
		var tOccupType = fmPlan.OccupType.value;
		var tOccupMidType = fmPlan.OccupMidType.value;
		var conditionOccupCode = "1 and a.occupationtype= #"+tOccupType+"# and a.occupmidcode = #"+tOccupMidType+"#";
		if (returnType=='0') {
			
			if (isEmpty(fmPlan.OccupMidType)) {
				alert("����ѡ��ְҵ�з��࣡");
				return false;
			}
			return showCodeList('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
		} else {
			
			if (isEmpty(fmPlan.OccupMidType)) {
				alert("����ѡ��ְҵ�з��࣡");
				return false;
			}
			return showCodeListKey('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
		}
	} else if (value1=='occuptype2') {//��ְҵ��������
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	}
}

/**
 * ��������
 */
function afterCodeSelect(cCodeType, Field) {
	
	var tCodeType = fmPub.HiddenCodeType.value;
	fmPub.HiddenCodeType.value = "";
	if (cCodeType=="queryexp") {//ѡ���Զ�������
		
		if (tCodeType=="plantype") {
			
			pubPlanAfterCodeSelect(fmPlan, tQuotType, tCodeType, Field.value);
		}
	} else if (cCodeType=="engincaltype") {//ѡ�񱣷Ѽ��㷽ʽ
		
		pubPlanAfterCodeSelect(fmPlan, tQuotType, cCodeType, Field.value);
	} else	if (cCodeType=="occuptype") {
		
		if (tCodeType=="occuptype1") {
			
			fmPlan.OccupMidType.value = "";
			fmPlan.OccupMidTypeName.value = "";
			fmPlan.OccupCode.value = "";
			fmPlan.OccupCodeName.value = "";
		}
	} else if (cCodeType=="premmode") {
			
		var tPremMode = fm2.PremMode.value;
		if (tPremMode=="1") {//��ҵ����
			
			fmPlan.EnterpriseRate.readOnly = true;
			fmPlan.EnterpriseRate.value = "1";
		} else if (tPremMode=="2") {//���˸���
		
			fmPlan.EnterpriseRate.readOnly = true;
			fmPlan.EnterpriseRate.value = "0";
		} else {
			
			fmPlan.EnterpriseRate.readOnly = false;
		}
	}  else if (cCodeType=="occupmidtype") {
		
		fmPlan.OccupCode.value = "";
		fmPlan.OccupCodeName.value = "";
	} else if (cCodeType=="reinsarrange") {
		
		fm3.FaculReason.value = "";
		fm3.FaculReasonName.value = "";
		document.getElementById("FaculOtherDesc").value = "";
		document.getElementById("tFaculOtherDesc").style.display = "none";
		
		if (Field.value == "2" && ( tActivityID=="0800100004" || tActivityID=="0800100005")) {
			
			document.getElementById("tdFacul1").style.display = "";
			document.getElementById("tdFacul2").style.display = "";
			document.getElementById("tdFacul3").style.display = "";
			document.getElementById("tdFacul4").style.display = "";
			document.getElementById("tdFacul5").style.display = "none";
			document.getElementById("tdFacul6").style.display = "none";
			document.getElementById("tdFacul7").style.display = "none";
			document.getElementById("tdFacul8").style.display = "none";
			
		} else {
			document.getElementById("tdFacul1").style.display = "none";
			document.getElementById("tdFacul2").style.display = "none";
			document.getElementById("tdFacul3").style.display = "none";
			document.getElementById("tdFacul4").style.display = "none";
			document.getElementById("tdFacul5").style.display = "";
			document.getElementById("tdFacul6").style.display = "";
			document.getElementById("tdFacul7").style.display = "";
			document.getElementById("tdFacul8").style.display = "";
		}
	} else if (cCodeType=="faculreason") {
		
		document.getElementById("FaculOtherDesc").value = "";
		if (Field.value == "15") {
			document.getElementById("tFaculOtherDesc").style.display = "";
		} else {
			document.getElementById("tFaculOtherDesc").style.display = "none";
		}
	} else if (cCodeType=="occupcode") {
		
		var tOccupCode = fmPlan.OccupCode.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql45");
		tSQLInfo.addSubPara(tOccupCode);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		fmPlan.OccupType.value = tArr[0][0];
		fmPlan.OccupTypeName.value = tArr[0][1];
		fmPlan.OccupMidType.value = tArr[0][2];
		fmPlan.OccupMidTypeName.value = tArr[0][3];
	}
}

/**
 * ������ϸά��
 */
function planDetailOpen() {
	
	tPlanDetailOpen = window.open("./LSQuotETPlanDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * ������ϸһ��
 */
function showAllDetail() {
	
	tAllDetailOpen = window.open("./LSQuotPlanAllDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * ��Ʒ����ά��
 */
function showProdParam() {
	
	window.open("./LSProdParamMain.jsp?ObjType=QUOT&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"��Ʒ����ά��",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �˱�Ҫ���ѯ
 */
function queryUWMainPoint() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql21");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWMainPointGrid, 2,1);
}

/**
 * �˱�Ҫ��չʾ
 */
function showUWMainPoint() {
	
	divUWMainPointDetail.style.display = "";
	
	if (tActivityID=="0800100002") {
		
		divSubUWAnaly.style.display = "";
		divBranchUWAnaly.style.display = "none";
	} else {
		
		if (tSubUWFlag==1) {
			divSubUWAnaly.style.display = "";
		} else {
			divSubUWAnaly.style.display = "none";
		}
		divBranchUWAnaly.style.display = "";
	}
	
	var tSelNo = UWMainPointGrid.getSelNo()-1;
	
	fm3.SubUWAnaly.value = UWMainPointGrid.getRowColData(tSelNo, 5);
	fm3.BranchUWAnaly.value = UWMainPointGrid.getRowColData(tSelNo, 6);
}

/**
 * Ҫ���������
 */
function analySaveClick() {
	
	var tSelNo = UWMainPointGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm3.UWMainPointRiskCode.value = UWMainPointGrid.getRowColData(tSelNo-1, 1);
	fm3.UWMainPointRuleCode.value = UWMainPointGrid.getRowColData(tSelNo-1, 3);
	
	mOperate = "ANALYSAVE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm3.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate;
	submitForm(fm3);
}

/**
 * Ҫ������ر�
 */
function analyCloseClick() {
	
	initUWMainPointGrid();
	queryUWMainPoint();
	divUWMainPointDetail.style.display = "none";
}

/**
 * �������������ѯ
 */
function queryOtherOpinion() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql22");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), OtherOpinionGrid, 0,1);
	var rowNum = turnPage2.queryAllRecordCount;//������
	if (parseInt(rowNum,10) > 10) {
		document.getElementById("divTurnPage").style.display = '';
	} else {
		document.getElementById("divTurnPage").style.display = 'none';
	}
}

/**
 * �����������չʾ
 */
function showOtherOpinion() {
	
	var tSelNo = OtherOpinionGrid.getSelNo()-1;
	
	fm2.OpinionType.value = OtherOpinionGrid.getRowColData(tSelNo, 2);
	fm2.OpinionTypeName.value = OtherOpinionGrid.getRowColData(tSelNo, 3);
	fm2.OpinionDesc.value = OtherOpinionGrid.getRowColData(tSelNo, 4);
}

/**
 * �������������������
 */
function downFile(parm1,parm2) {
	
	var tFilePath = fm2.all(parm1).all('OtherOpinionGrid6').value;
	var tFileName = fm2.all(parm1).all('OtherOpinionGrid7').value;
	
	if (tFilePath==null || tFilePath=="") {
		return false;
	}
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * ���������������
 */
function OtherOpinionAdd() {
	
	mOperate = "OPINIONADD";
	if (!beforeSubmit()) {
		return false;
	}
	
	if (!checkFileSuffix()) {
		return false;
	}
	
	fm2.action = "./LSQuotOtherOpinionSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachType=03&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * �޸������������
 */
function OtherOpinionModify() {
	
	var tSelNo = OtherOpinionGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm2.OtherOpinionSerialNo.value = OtherOpinionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "OPINIONMODIFY";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm2.action = "./LSQuotOtherOpinionSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachType=03&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * ɾ�������������
 */
function OtherOpinionDelete() {
	
	var tSelNo = OtherOpinionGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm2.OtherOpinionSerialNo.value = OtherOpinionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "OPINIONDELETE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm2.action = "./LSQuotOtherOpinionSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachType=03&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * ��������������۲�ѯ
 */
function queryProcess() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql19");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm3.SubUWOpinion.value = strQueryResult[0][0];
		fm3.SubUWConclu.value = strQueryResult[0][1];
		fm3.SubUWConcluName.value = strQueryResult[0][2];
		fm3.BranchUWOpinion.value = strQueryResult[0][3];
		fm3.BranchUWConclu.value = strQueryResult[0][4];
		fm3.BranchUWConcluName.value = strQueryResult[0][5];
		
		if (strQueryResult[0][5]=="1") {
			
			fm3.BranchUrgentFlag.value = "1";
			fm3.BranchUrgentFlag_CK.checked = true;
		} else {
			
			fm3.BranchUrgentFlag.value = "0";
			fm3.BranchUrgentFlag_CK.checked = false;
		}
		
		fm3.ReinsArrange.value = strQueryResult[0][7];
		fm3.ReinsArrangeName.value = strQueryResult[0][8];
		fm3.UWOpinion.value = strQueryResult[0][9];
		fm3.UWConclu.value = strQueryResult[0][10];
		fm3.UWConcluName.value = strQueryResult[0][11];
		fm1.UWManagerOpinion.value = strQueryResult[0][12];
		fm1.UWManagerConclu.value = strQueryResult[0][13];
		fm1.UWManagerConcluName.value = strQueryResult[0][14];
		fm1.BranchFinalOpinion.value = strQueryResult[0][15];
		fm1.BranchFinalConclu.value = strQueryResult[0][16];
		fm1.BranchFinalConcluName.value = strQueryResult[0][17];
		fm1.SubFinalOpinion.value = strQueryResult[0][18];
		fm1.SubFinalConclu.value = strQueryResult[0][19];
		fm1.SubFinalConcluName.value = strQueryResult[0][20];
	}
}

/**
 * �ۺ��������
 */
function UWOpinionSave() {
	
	mOperate = "UWOPINIONSAVE";
	if (!beforeSubmit()) {
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql24");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tResult != null) {
		var tReinsType = tResult[0][0];
		var tState = tResult[0][1];
		var tReinsArrange = fm3.ReinsArrange.value;
		if (tReinsType != tReinsArrange && tState != '0'&& tReinsArrange != '2') {
			if(!confirm('�ٱ����ŷ����ı䣬��ɾ���ٱ��ٷַ������ã��Ƿ����?')){
				return false;
			}
		}
	}
	
	fm3.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&TranProdType="+ tTranProdType;
	submitForm(fm3);
}

/**
 * ѯ�ۺ˱��ύ
 */
function UWSubmit() {
	
	mOperate = "UWSUBMIT";
	if (!beforeSubmit()) {
		return false;
	}
	if (tActivityID=="0800100002" || tActivityID=="0800100003" || tActivityID=="0800100004") {
		
		if (tActivityID=="0800100003" || tActivityID=="0800100004") {
			
			if(!confirm('ȷ��Ҫ�˱��ύ��?')){
				return false;
			}
		}

		fm3.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&TranProdType="+tTranProdType;
		submitForm(fm3);
	} else {
		
		fm1.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&TranProdType="+tTranProdType;
		submitForm(fm1);
	}
}

/**
 * ��Ŀ¼
 */
function ReturnList() {
	
	if (tActivityID=="0800100002") {
		
		location.href = "./LSQuotSubUWQueryInput.jsp";
	} else if (tActivityID=="0800100003") {
		
		location.href = "./LSQuotBranchUWQueryInput.jsp";
	} else if (tActivityID=="0800100004") {
		
		location.href = "./LSQuotUWQueryInput.jsp";
	} else if (tActivityID=="0800100005") {
		
		location.href = "./LSQuotUWManagerQueryInput.jsp";
	} else if (tActivityID=="0800100006") {
		
		location.href = "./LSQuotBranchFinalQueryInput.jsp";
	} else if (tActivityID=="0800100007") {
		
		location.href = "./LSQuotSubFinalQueryInput.jsp";
	}
}

/**
 * �Ƿ�Ӽ���ѡ��
 */
function FlagCheck() {
	
	if (fm3.BranchUrgentFlag_CK.checked) {
		
		fm3.BranchUrgentFlag.value='1';
	} else {
		
		fm3.BranchUrgentFlag.value='0';
	}
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
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		 var   showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		if (mOperate == "PRINT") {
			
			var tFileName = content.substring(content.lastIndexOf('/')+1,content.length); 
			var tFilePath = content;
			
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
		} else {
			
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		    var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			
			if (mOperate=="OPINIONDELETE" || mOperate=="OPINIONMODIFY" || mOperate=="OPINIONADD") {
				
				fm2.OpinionType.value = "";
				fm2.OpinionTypeName.value = "";
				fm2.OpinionDesc.value = "";
				fm2.all("UploadPath").outerHTML = fm2.all("UploadPath").outerHTML;
				queryOtherOpinion();
			} else if (mOperate=="UWOPINIONSAVE") {
				
			} else if (mOperate == "UWSUBMIT") {
				ReturnList();
			} else {
				initForm();
			}
		}
	}
}

/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="ANALYSAVE") {
		
		if (tActivityID=="0800100002" && fm3.SubUWAnaly.value=="") {
			alert("��֧��˾Ҫ���������Ϊ�գ�");
			return false;
		} else if (tActivityID=="0800100003" && fm3.BranchUWAnaly.value=="") {
			alert("�ֹ�˾Ҫ���������Ϊ�գ�");
			return false;
		}
	}
	
	if (mOperate=="OPINIONADD") {
		
		if (fm2.OpinionType.value=="") {
			alert("������Ͳ���Ϊ�գ�");
			return false;
		}
		
		if (fm2.UploadPath.value=="" && fm2.OpinionDesc.value=="") {
			alert("���������������������ͬʱΪ�գ�");
			return false;
		}
	}
	
	if (mOperate=="UWOPINIONSAVE") {
		
		if (tActivityID=="0800100002" && fm3.SubUWOpinion.value=="") {
			alert("��֧��˾�ۺ��������Ϊ�գ�");
			return false;
		} else if (tActivityID=="0800100003" && fm3.BranchUWOpinion.value=="") {
			alert("�ֹ�˾�ۺ��������Ϊ�գ�");
			return false;
		} else if (tActivityID=="0800100004") {
			
			if (fm3.ReinsArrange.value=="") {
				alert("�ٱ����Ų���Ϊ�գ�");
				return false;
			} else {
				if (fm3.ReinsArrange.value=="2" && fm3.FaculReason.value=="") {
					alert("�ٷ�ԭ����Ϊ�գ�");
					return false;
				} 
				if (fm3.FaculReason.value=="15" && fm3.FaculOtherDesc.value=="") {
					alert("�ٷ�������������Ϊ�գ�");
					return false;
				} 
			}
			
			if (fm3.UWOpinion.value=="") {
				alert("�ܹ�˾�ۺ��������Ϊ�գ�");
				return false;
			}
		}
	}
	
	if (mOperate=="UWSUBMIT") {
		
		if (tActivityID=="0800100002") {
			
			//У��˱�Ҫ���Ƿ�����дҪ�����(��֧��˾)
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
			tSQLInfo.setSqlId("LSQuotUWSql25");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			
			var tPointAll = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPointAll != null) {
				if (tPointAll[0][0] != '0') {
					alert("����δ��д[��֧��˾Ҫ�����]�ĺ˱�Ҫ�㣡");
					return false;
				}
			}
			
			if (fm3.SubUWOpinion.value=="") {
				alert("��֧��˾�ۺ��������Ϊ�գ�");
				return false;
			}
			
			if (fm3.SubUWConclu.value=="") {
				alert("��֧��˾�˱����۲���Ϊ�գ�");
				return false;
			}
		} else if (tActivityID=="0800100003") {
			
			//У��˱�Ҫ���Ƿ�����дҪ�����(�ֹ�˾)
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
			tSQLInfo.setSqlId("LSQuotUWSql26");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			
			var tPointAll = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPointAll != null) {
				if (tPointAll[0][0] != '0') {
					alert("����δ��д[�ֹ�˾Ҫ�����]�ĺ˱�Ҫ�㣡");
					return false;
				}
			}
			
			if (fm3.BranchUWOpinion.value=="") {
				alert("�ֹ�˾�ۺ��������Ϊ�գ�");
				return false;
			}
			
			if (fm3.BranchUWConclu.value=="") {
				alert("�ֹ�˾�˱����۲���Ϊ�գ�");
				return false;
			}
		} else if (tActivityID=="0800100004") {//�����ô�˳��,����������У��
			
			if (fm3.UWConclu.value=="") {
				alert("�ܹ�˾�˱����۲���Ϊ�գ�");
				return false;
			}
			
			var tUWConclu = fm3.UWConclu.value;
			if (tUWConclu=="0") {//ͬ��
				
				if (fm3.ReinsArrange.value=="") {
					alert("�ٱ����Ų���Ϊ�գ�");
					return false;
				} else {
					if (fm3.ReinsArrange.value=="2" && fm3.FaculReason.value=="") {
						alert("�ٷ�ԭ����Ϊ�գ�");
						return false;
					} 
					if (fm3.FaculReason.value=="15" && fm3.FaculOtherDesc.value=="") {
						alert("�ٷ�������������Ϊ�գ�");
						return false;
					} 
				}
			
				if (fm3.UWOpinion.value=="") {
					alert("�ܹ�˾�ۺ��������Ϊ�գ�");
					return false;
				}
				
				//У���ٷַ�����Ϣ
				if (!checkFaculPlan()) {
					return false;
				}
				
			} else if (tUWConclu=="1") {//�ϱ�
				
				if (fm3.ReinsArrange.value=="") {
					alert("�ٱ����Ų���Ϊ�գ�");
					return false;
				} else {
					if (fm3.ReinsArrange.value=="2" && fm3.FaculReason.value=="") {
						alert("�ٷ�ԭ����Ϊ�գ�");
						return false;
					} 
					if (fm3.FaculReason.value=="15" && fm3.FaculOtherDesc.value=="") {
						alert("�ٷ�������������Ϊ�գ�");
						return false;
					} 
				}
				
				if (fm3.UWOpinion.value=="") {
					alert("�ܹ�˾�ۺ��������Ϊ�գ�");
					return false;
				}
				
				//У���ٷַ�����Ϣ
				if (!checkFaculPlan()) {
					return false;
				}
				
			} else {//���������ͬ��ʱ,�����������Ϊ��
				
				if (fm3.UWOpinion.value=="") {
					alert("�ܹ�˾�ۺ��������Ϊ�գ�");
					return false;
				}
			}
		
		} else if (tActivityID=="0800100005") {
			
			if (fm1.UWManagerOpinion.value=="") {
				alert("�˱������������Ϊ�գ�");
				return false;
			}
			
			if (fm1.UWManagerConclu.value=="") {
				alert("�˱�������۲���Ϊ�գ�");
				return false;
			}
		} else if (tActivityID=="0800100006") {
			
			if (fm1.BranchFinalOpinion.value=="") {
				alert("�ֹ�˾�����������Ϊ�գ�");
				return false;
			}
			
			if (fm1.BranchFinalConclu.value=="") {
				alert("�ֹ�˾���ս��۲���Ϊ�գ�");
				return false;
			}
		} else if (tActivityID=="0800100007") {
			
			if (fm1.SubFinalOpinion.value=="") {
				alert("��֧��˾�����������Ϊ�գ�");
				return false;
			}
			
			if (fm1.SubFinalConclu.value=="") {
				alert("��֧��˾���ս��۲���Ϊ�գ�");
				return false;
			}
		}
	}
	
	return true;
}

/**
 * ģ����ѯ����
 */
function fuzzyQueryOccupCode(Filed,FildName) {
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		var objCodeName = FildName.value;
		if (objCodeName=="") {
			return false;
		}
		
		var tOccupType = fmPlan.OccupType.value;
		var tOccupMidType = fmPlan.OccupMidType.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql44");
		tSQLInfo.addSubPara(objCodeName);
		
		if (tOccupType==null || tOccupType=="") {
			tSQLInfo.addSubPara("");
		} else {
			tSQLInfo.addSubPara(tOccupType);
		}
		if (tOccupMidType==null || tOccupMidType=="") {
			tSQLInfo.addSubPara("");
		} else {
			tSQLInfo.addSubPara(tOccupMidType);
		}
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("�����ڸù��֣�");
			return false;
		} else {
			if (tArr.length == 1) {
				
				fmPlan.OccupType.value = tArr[0][0];
				fmPlan.OccupTypeName.value = tArr[0][1];
				fmPlan.OccupMidType.value = tArr[0][2];
				fmPlan.OccupMidTypeName.value = tArr[0][3];
				fmPlan.OccupCode.value = tArr[0][4];
				fmPlan.OccupCodeName.value = tArr[0][5];
			} else {
				var conditionOccupCode = "1 and a.occupationname like #%"+objCodeName+"%#";
				
				if (tOccupType==null || tOccupType=="") {
				} else {
					conditionOccupCode += " and a.occupationtype= #"+tOccupType+"# ";
				}
				if (tOccupMidType==null || tOccupMidType=="") {
				} else {
					conditionOccupCode += " and a.occupmidcode = #"+tOccupMidType+"# ";
				}
				
				showCodeList('occupcode', [Filed, FildName], [0,1], null,conditionOccupCode, '1', '1',null);
			}
		}
	}
}

/**
 * չʾ�ٱ���Ϣ
 */
function showReinsInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql23");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tResult != null ) {
		var tReinsType = tResult[0][0];
		if (tReinsType == "2") {
			document.getElementById("tdFacul1").style.display = "";
			document.getElementById("tdFacul2").style.display = "";
			document.getElementById("tdFacul3").style.display = "";
			document.getElementById("tdFacul4").style.display = "";
			document.getElementById("tdFacul5").style.display = "none";
			document.getElementById("tdFacul6").style.display = "none";
			document.getElementById("tdFacul7").style.display = "none";
			document.getElementById("tdFacul8").style.display = "none";
			
			fm3.FaculReason.value = tResult[0][1];
			fm3.FaculReasonName.value = tResult[0][2];
			
			if (tResult[0][1] == "15") {
				document.getElementById("FaculOtherDesc").value = tResult[0][3];
				document.getElementById("tFaculOtherDesc").style.display = "";
			}
			
		} else {
			document.getElementById("tdFacul1").style.display = "none";
			document.getElementById("tdFacul2").style.display = "none";
			document.getElementById("tdFacul3").style.display = "none";
			document.getElementById("tdFacul4").style.display = "none";
			document.getElementById("tdFacul5").style.display = "";
			document.getElementById("tdFacul6").style.display = "";
			document.getElementById("tdFacul7").style.display = "";
			document.getElementById("tdFacul8").style.display = "";
			
			fm3.FaculReason.value = "";
			fm3.FaculReasonName.value = "";
			document.getElementById("FaculOtherDesc").value = "";
			document.getElementById("tFaculOtherDesc").style.display = "none";
		}
		
	}
}

/**
 * �ٷַ���¼��
 */
function faculPlanInput() {
	
	if (fm3.ReinsArrange.value != "2") {
		alert("�ٱ����ŷ��ٷ֣�����¼���ٷַ�����");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql24");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tResult==null) {
		alert("���ȱ����ۺ������Ϣ!");
		return false;
	} else {
		var tReinsType = tResult[0][0];
		if (tReinsType!='2') {
			alert("�ٱ����ŷ��ٷ֣������±����ۺ������Ϣ!");
			return false;
		}
	}
	
	var tFlag = "";
	if (tActivityID=="0800100004") {
		tFlag = "1";
	} else {
		tFlag = "0";
	}
	window.open("../reinsure/RIFaculPreceptMain.jsp?QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo+ "&Flag="+ tFlag,"�ٱ��ٷַ�������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


/**
 * �ܹ�˾�˱����ڣ�������ٷ��ٱ����ύ���ʱУ���ٷַ�����Ϣ
 */
function checkFaculPlan() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql23");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);

	var tArrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArrResult==null) {
		
		alert("���ȱ����ۺ������Ϣ��");
		return false;
	} else {
		
		var tReinsArrange = tArrResult[0][0];
		if (fm3.ReinsArrange.value  != tReinsArrange) {
			alert("�ٱ������ѷ����ı䣬�뱣���ۺ������Ϣ��");
			return false;
		}
		if (tReinsArrange=="2") {
			
			var tState = tArrResult[0][4];
			if (tState == "1") {//0--δ¼�룬1--¼��δ��ϣ�2--¼�����
				alert("�ٷַ���δ¼����ϣ������ƣ�");
				return false;
			}
		}
	}
	return true;
}

/**
 * У�鸽����ʽ�Ƿ�Ϸ�
 */
function checkFileSuffix() {
	
	var tFilePath = fm2.UploadPath.value;
	
	if (tFilePath!=null && tFilePath !="") {
		
		var tFileName = tFilePath.substring(tFilePath.lastIndexOf("\\")+1);
		var pattern2 = /^[^\s\+\&]+$/;
		if (!pattern2.test(tFileName)) {
			alert("�ϴ����ļ����ܺ��пո񡢡�+������&����");
			return false;
		}		
		
		var tFileSuffix = tFilePath.substring(tFilePath.lastIndexOf("."));
		if (tFileSuffix==".eml" || tFileSuffix==".EML" || tFileSuffix==".txt" || tFileSuffix==".TXT" ||
			 tFileSuffix==".doc" || tFileSuffix==".DOC" || tFileSuffix==".docx"  || tFileSuffix==".DOCX" ||
		     tFileSuffix==".xls" || tFileSuffix==".XLS" || tFileSuffix==".xlsx"|| tFileSuffix==".XLSX" || 
		     tFileSuffix==".pdf" || tFileSuffix==".PDF" || tFileSuffix==".zip" || tFileSuffix==".ZIP" || 
		     tFileSuffix==".rar" || tFileSuffix==".RAR" || tFileSuffix==".gif" || tFileSuffix==".GIF") {
		} else {
			alert("��֧�ִ��ļ������ϴ���");
			return false;
		}
	} 
	return true;
	
}

/**
 * ��ѯ�����Ƿ񾭹���֧��˾�˱���������֧��Ϣչʾ
 */
function querySubUWFlag() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql27");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	return tResult[0][0];
	
}

/**
 * ��ѯ�����Ƿ񾭹��ܹ�˾�˱��������ܹ�˾�˱���Ϣչʾ
 */
function queryTotalFlag() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql28");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	return tResult[0][0];
	
}

/**
 * ��ӡѯ�۵�
 */
function printInquiry(o) {
	
	mOperate = "PRINT";
	
	var tPrintType = "";
	if (o == "pdf") {
		tPrintType = "pdf";
	} else if (o == "doc") {
		tPrintType = "doc";
	}
	
	fm3.action = "./LSQuotPrintInquirySave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&ProdType="+ tTranProdType+"&PrintType="+tPrintType
	submitForm(fm3);
} 
