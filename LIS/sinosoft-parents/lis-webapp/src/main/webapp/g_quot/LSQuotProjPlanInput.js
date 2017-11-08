/***************************************************************
 * <p>ProName��LSQuotProjPlanInput.js</p>
 * <p>Title����Ŀѯ�۷�����Ϣ¼��</p>
 * <p>Description����Ŀѯ�۷�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//������Ϣ
var tSQLInfo = new SqlClass();
var tTranPremMode;//���ѷ�̯��ʽ
var tPlanDetailOpen;
var tAllDetailOpen;

function initQuotStep2() {
	
	queryPlanInfo();
	pubInitQuotStep2(fm2, tQuotType, tTranProdType, tTranPremMode, '');
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
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 2, 1);
}

/**
 * ѡ�����
 */
function showPlanInfo() {
	
	pubShowPlanInfo(fm2, tQuotType, tTranProdType);
}

/**
 * ѡ�񵥶�ְҵ���
 */
function chooseOccupFlag(cQuotFlag) {

	pubChooseOccupFlag(fm2, cQuotFlag);
}

/**
 * ���ӷ���
 */
function addPlan() {
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("��Ʒ�����ѷ����ı䣬���ɽ��иò�����");
		return false;
	}
	
	dealRedundant(fm2, tQuotType, tTranProdType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tQuotType, tTranProdType)) {//�ύǰ�Ĺ���У��
		return false;
	}
	
	fmPub.Operate.value = "ADDPLAN";
	fm2.action = "./LSQuotProjPlanSave.jsp?Operate=ADDPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
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
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("��Ʒ�����ѷ����ı䣬���ɽ��иò�����");
		return false;
	}
	
	dealRedundant(fm2, tQuotType, tTranProdType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeModifyPlan(fm2, tTranProdType)) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tQuotType, tTranProdType)) {//�ύǰ�Ĺ���У��
		return false;
	}

	fmPub.Operate.value = "MODIFYPLAN";
	fm2.action = "./LSQuotProjPlanSave.jsp?Operate=MODIFYPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
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
	fm2.action = "./LSQuotProjPlanSave.jsp?Operate=DELPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
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
	} else if (value1=='occuptype2') {//��ְҵ��������
	
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=='occupmidtype') {
		
		var tOccupType = fm2.OccupType.value;
		var conditionOccupMidType = "1 and b.occupationtype= #"+tOccupType+"# ";
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupType)) {
				alert("����ѡ��ְҵ���");
				return false;
			}
			
			return showCodeList('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		} else {
			
			if (isEmpty(fm2.OccupType)) {
				alert("����ѡ��ְҵ���");
				return false;
			}
			return showCodeListKey('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		}
	} else if (value1=='occupcode') {
		
		var tOccupType = fm2.OccupType.value;
		var tOccupMidType = fm2.OccupMidType.value;
		var conditionOccupCode = "1 and a.occupationtype= #"+tOccupType+"# and a.occupmidcode = #"+tOccupMidType+"#";
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("����ѡ��ְҵ�з��࣡");
				return false;
			}
			return showCodeList('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
		} else {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("����ѡ��ְҵ�з��࣡");
				return false;
			}
			return showCodeListKey('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
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
			
			pubPlanAfterCodeSelect(fm2, tQuotType, tCodeType, Field.value);
		}
	} else if (cCodeType=="engincaltype") {//ѡ�񱣷Ѽ��㷽ʽ
		
		pubPlanAfterCodeSelect(fm2, tQuotType, cCodeType, Field.value);
	} else	if (cCodeType=="occuptype") {
		
		if (tCodeType=="occuptype1") {
			
			fm2.OccupMidType.value = "";
			fm2.OccupMidTypeName.value = "";
			fm2.OccupCode.value = "";
			fm2.OccupCodeName.value = "";
		}
	} else if (cCodeType=="premmode") {
			
		var tPremMode = fm2.PremMode.value;
		if (tPremMode=="1") {//��ҵ����
			
			fm2.EnterpriseRate.readOnly = true;
			fm2.EnterpriseRate.value = "1";
		} else if (tPremMode=="2") {//���˸���
		
			fm2.EnterpriseRate.readOnly = true;
			fm2.EnterpriseRate.value = "0";
		} else {
			
			fm2.EnterpriseRate.readOnly = false;
		}
	}  else if (cCodeType=="occupmidtype") {
		
		fm2.OccupCode.value = "";
		fm2.OccupCodeName.value = "";
		
	} else if (cCodeType=="occupcode") {
		
		var tOccupCode = fm2.OccupCode.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql45");
		tSQLInfo.addSubPara(tOccupCode);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		fm2.OccupType.value = tArr[0][0];
		fm2.OccupTypeName.value = tArr[0][1];
		fm2.OccupMidType.value = tArr[0][2];
		fm2.OccupMidTypeName.value = tArr[0][3];
	}
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
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
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		dealAfterSubmit(content);
	}
	if (fmPub.Operate.value=="UPLOAD") {
		fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
	}
}

/**
 * �ύ���غ���
 */
function dealAfterSubmit(o) {
	
	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="ADDPLAN" || tOperate=="MODIFYPLAN" || tOperate=="DELPLAN" || tOperate=="UPLOAD") {
		
		queryPlanInfo();
		pubInitQuotStep2(fm2, tQuotType, tTranProdType, tTranPremMode, "");
		
		if (tTranProdType == "00" || tTranProdType == "03") {
			
			fm2.PlanType.value = "00";
			fm2.PlanTypeName.value = "��ͨ����";
			pubPlanAfterCodeSelect(fm2, tQuotType, "plantype", "00");
			chooseOccupFlag("2");
		} 
	}
	if (tOperate=="UPLOAD") {
		fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
	}
}

/**
 * ��һ��
 */
function nextStep() {
	
	goToStep(3);
}

/**
 * ������ϸά��
 */
function planDetailOpen() {
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("��Ʒ�����ѷ����ı䣬���ɽ��иò�����");
		return false;
	}
	
	tPlanDetailOpen = window.open("./LSQuotETPlanDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * ��һ��
 */
function previousStep() {
	
	goToStep(1);
}
/**
 * ��Ŀ¼
 */
function returnLR() {
	
	goToStep(0);
}

/**
 * �������
 */
function showConditionCheck() {

	pubShowConditionCheck(fm2);
}

function showAllDetail() {
	
	tAllDetailOpen = window.open("./LSQuotPlanAllDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
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
		
		var tOccupType = fm2.OccupType.value;
		var tOccupMidType = fm2.OccupMidType.value;
		
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
				
				fm2.OccupType.value = tArr[0][0];
				fm2.OccupTypeName.value = tArr[0][1];
				fm2.OccupMidType.value = tArr[0][2];
				fm2.OccupMidTypeName.value = tArr[0][3];
				fm2.OccupCode.value = tArr[0][4];
				fm2.OccupCodeName.value = tArr[0][5];
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
 * ��������
 */
function impPlanSubmit() {
	
	if (tQuotNo == null || tQuotNo == "") {
		alert("��ȡѯ�ۺ�ʧ�ܣ�");
		return false;
	}
	if (tQuotBatNo == null || tQuotBatNo == "") {
		alert("��ȡ���κ�ʧ�ܣ�");
		return false;
	}
	if (tQuotType == null || tQuotType == "") {
		alert("��ȡѯ������ʧ�ܣ�");
		return false;
	}
	if (tTranProdType == null || tTranProdType == "") {
		alert("��ȡ��Ʒ����ʧ�ܣ�");
		return false;
	}
	var filePath = fmupload.UploadPath.value;
	if(filePath == null || filePath == ""){
		alert("��ѡ�����ļ�·����");
		return false;
	}
	
	var indexFirst = filePath.lastIndexOf("\\");
	var indexLast = filePath.lastIndexOf(".xlsx");
	if(indexFirst < 0 || indexLast < 0 || indexLast <= indexFirst) {
		alert("�ļ�·�����Ϸ���ѡ����ļ���ʽ����ȷ��������ѡ��");
		return false;
	}

	fmPub.Operate.value = "UPLOAD";
	fmupload.action="./LQuotPlanImpSave.jsp?Operate=UPLOAD&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ProdType="+tTranProdType+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fmupload);
}

/**
 * ����ѯ�����ͣ���Ʒ����ѡ������ģ��
 */
function downTemplate() {
	
	if (tQuotType=="01" && tTranProdType=="00") {
		location.href="../template/quot/QuotProjPlanImport_00.xlsx";
	} else if (tQuotType=="01" && tTranProdType=="01") {
		location.href="../template/quot/QuotProjPlanImport_01.xlsx";
	} else if (tQuotType=="01" && tTranProdType=="03") {
		location.href="../template/quot/QuotProjPlanImport_03.xlsx";
	}
}

/**
 * չʾ����������Ϣ
 */
function showPlanSubmit() {
	
	window.open("./LSQuotPlanImpInfoMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo + "&TranProdType="+ tTranProdType ,"ѯ�۷���������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}