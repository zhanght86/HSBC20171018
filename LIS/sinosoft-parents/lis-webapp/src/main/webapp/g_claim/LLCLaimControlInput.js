/***************************************************************
 * <p>ProName��LLCLaimControlInput.js</p>
 * <p>Title���������ο���</p>
 * <p>Description���������ο���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2014-05-05
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var turnPage9 = new turnPageClass();
var turnPage10 = new turnPageClass();
var mAction = "";//����ģ��
var mOperate = "";//����״̬

var tSQLInfo = new SqlClass();

var tFlag = 0;

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	 var   showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		if (mAction=="DUTY") {
			
			showPlan1Info();
			
			if (mOperate=="DELETE") {
				initShareControlGrid();
				initConfShareControlGrid();
				initConfDutyShareControlGrid();
			}
		}
		
		if (mAction=="SHARE") {
			showPlan2Info();
		}
		
		if (mAction=="GET") {
			showPlan3Info();
		}
	}
}


/**
 * �ύ����
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Action.value = mAction;
	fm.Operate.value = mOperate;
	fm.action = "./LLCLaimControlSave.jsp";
	fm.submit(); //�ύ
}

/*******************************���ο���****************************************/
function showPlan1Info() {
	
	var tSelNo = Plan1Grid.getSelNo();
	fm.DutySysPlanCode.value = Plan1Grid.getRowColData(tSelNo-1, 1);
	fm.DutyPlanCode.value = Plan1Grid.getRowColData(tSelNo-1, 2);
	
	fm.DutyFactorType.value = "";
	fm.DutyFactorTypeName.value = "";
	fm.RiskCode.value = "";
	fm.RiskName.value = "";
	fm.DutyCode.value = "";
	fm.DutyName.value = "";
	fm.FactorCode.value = "";
	fm.FactorName.value = "";
	fm.CalRemark.value = "";
	fm.Params.value = "";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql10");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql11");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
	}
	
	turnPage3.pageLineNum = 100;
	turnPage3.queryModal(tSQLInfo.getString(), ConfDutyControlGrid, 2, 1);
}

function showConfDutyControlInfo() {
	
	var tSelNo = ConfDutyControlGrid.getSelNo();
	
	fm.DutyFactorType.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 5);
	fm.DutyFactorTypeName.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 6);
	fm.RiskCode.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 1);
	fm.RiskName.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 2);
	fm.DutyCode.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 3);
	fm.DutyName.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 4);
	fm.FactorCode.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 7);
	fm.FactorName.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 8);
	fm.CalRemark.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 10);
	fm.Params.value = ConfDutyControlGrid.getRowColData(tSelNo-1, 11);
}

function getDutyFactorType(Field, FieldName) {
	
	var tSelNo = Plan1Grid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ���շ�����Ϣ��");
		return false;
	}
	
	/*
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql3");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql4");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
	}
	
	var strResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strResult==null) {
		
		alert("�÷���û����Ҫ���ƵĿ���Ҫ�أ�");
		document.getElementById('DutyFactorType').CodeData = "";
		return false;
	} else {
		
		var returnStr = "0|";
		for (i=0;i<strResult.length;i++) {
			returnStr = returnStr + "^" + strResult[i][0] + "|" + strResult[i][1];
		}
		
		document.getElementById('DutyFactorType').CodeData = returnStr;
	}
	*/
	
	if (tBussType=="NB") {
		
		var queryCondition = "1 and c.policyno=#"+ tBussNo +"# and c.sysplancode=#"+ fm.DutySysPlanCode.value +"# and c.plancode=#"+ fm.DutyPlanCode.value +"#";
		showCodeList('nbdutyfactortype', [Field,FieldName], [0,1], null, queryCondition, '1', '1', null);
	} else {
		
		var queryCondition = "1 and c.quotno=#"+ tBussNo +"# and c.quotbatno=#"+ tSubBussNo +"# and c.sysplancode=#"+ fm.DutySysPlanCode.value +"# and c.plancode=#"+ fm.DutyPlanCode.value +"#";
		showCodeList('quotdutyfactortype', [Field,FieldName], [0,1], null, queryCondition, '1', '1', null);
	}
}

function getRisk(Field, FieldName) {
	
	var tSelNo = Plan1Grid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ���շ�����Ϣ��");
		return false;
	}
	
	if (fm.DutyFactorType.value=="") {
		alert("����ѡ��Ҫ�����ͣ�");
		return false;
	}
	
	/*
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql5");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyFactorType.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql6");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyFactorType.value);
	}
	
	var strResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strResult==null) {
		document.getElementById('RiskCode').CodeData = "";
		return false;
	} else {
		
		var returnStr = "0|";
		for (i=0;i<strResult.length;i++) {
			returnStr = returnStr + "^" + strResult[i][0] + "|" + strResult[i][1];
		}
		
		document.getElementById('RiskCode').CodeData = returnStr;
	}
	*/
	
	if (tBussType=="NB") {
		
		var queryCondition = "1 and a.policyno=#"+ tBussNo +"# and a.sysplancode=#"+ fm.DutySysPlanCode.value +"# and a.plancode=#"+ fm.DutyPlanCode.value +"# and exists(select 1 from lmclaimdutyfactorrela c where a.riskcode=c.riskcode and a.dutycode=c.dutycode and c.factortype=#"+ fm.DutyFactorType.value +"#)";
		showCodeList('nbriskcode', [Field,FieldName], [0,1], null, queryCondition, '1', '1', null);
	} else {
		
		var queryCondition = "1 and a.quotno=#"+ tBussNo +"# and a.quotbatno=#"+ tSubBussNo +"# and a.sysplancode=#"+ fm.DutySysPlanCode.value +"# and a.plancode=#"+ fm.DutyPlanCode.value +"# and exists(select 1 from lmclaimdutyfactorrela c where a.riskcode=c.riskcode and a.dutycode=c.dutycode and c.factortype=#"+ fm.DutyFactorType.value +"#)";
		showCodeList('quotriskcode', [Field,FieldName], [0,1], null, queryCondition, '1', '1', null);
	}
}

function getDuty(Field, FieldName) {
	
	var tSelNo = Plan1Grid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ���շ�����Ϣ��");
		return false;
	}
	
	if (fm.DutyFactorType.value=="") {
		alert("����ѡ��Ҫ�����ͣ�");
		return false;
	}
	
	if (fm.RiskCode.value=="") {
		alert("����ѡ�����֣�");
		return false;
	}
	
	/*
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql7");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
		tSQLInfo.addSubPara(fm.RiskCode.value);
		tSQLInfo.addSubPara(fm.DutyFactorType.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql8");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.DutySysPlanCode.value);
		tSQLInfo.addSubPara(fm.DutyPlanCode.value);
		tSQLInfo.addSubPara(fm.RiskCode.value);
		tSQLInfo.addSubPara(fm.DutyFactorType.value);
	}
	
	var strResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strResult==null) {
		document.getElementById('DutyCode').CodeData = "";
		return false;
	} else {
		
		var returnStr = "0|";
		for (i=0;i<strResult.length;i++) {
			returnStr = returnStr + "^" + strResult[i][0] + "|" + strResult[i][1];
		}
		
		document.getElementById('DutyCode').CodeData = returnStr;
	}
	*/
	
	if (tBussType=="NB") {
		
		var queryCondition = "1 and a.policyno=#"+ tBussNo +"# and a.sysplancode=#"+ fm.DutySysPlanCode.value +"# and a.plancode=#"+ fm.DutyPlanCode.value +"# and a.riskcode=#"+ fm.RiskCode.value +"# and exists(select 1 from lmclaimdutyfactorrela c where a.riskcode=c.riskcode and a.dutycode=c.dutycode and c.factortype=#"+ fm.DutyFactorType.value +"#)";
		showCodeList('nbdutycode', [Field,FieldName], [0,1], null, queryCondition, '1', '1', null);
	} else {
		
		var queryCondition = "1 and a.quotno=#"+ tBussNo +"# and a.quotbatno=#"+ tSubBussNo +"# and a.sysplancode=#"+ fm.DutySysPlanCode.value +"# and a.plancode=#"+ fm.DutyPlanCode.value +"# and a.riskcode=#"+ fm.RiskCode.value +"# and exists(select 1 from lmclaimdutyfactorrela c where a.riskcode=c.riskcode and a.dutycode=c.dutycode and c.factortype=#"+ fm.DutyFactorType.value +"#)";
		showCodeList('quotdutycode', [Field,FieldName], [0,1], null, queryCondition, '1', '1', null);
	}
}

function getFactor(Field, FieldName1, FieldName2, FieldName3) {
	
	var tSelNo = Plan1Grid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ���շ�����Ϣ��");
		return false;
	}
	
	if (fm.DutyFactorType.value=="") {
		alert("����ѡ��Ҫ�����ͣ�");
		return false;
	}
	
	if (fm.RiskCode.value=="") {
		alert("����ѡ�����֣�");
		return false;
	}
	
	if (fm.DutyCode.value=="") {
		alert("����ѡ�����Σ�");
		return false;
	}
	
	/*
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	tSQLInfo.setSqlId("LLCLaimControlSql9");
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(fm.DutyCode.value);
	tSQLInfo.addSubPara(fm.DutyFactorType.value);
	
	var strResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strResult==null) {
		document.getElementById('FactorCode').CodeData = "";
		return false;
	} else {
		
		var returnStr = "0|";
		for (i=0;i<strResult.length;i++) {
			returnStr = returnStr + "^" + strResult[i][0] + "|" + strResult[i][1] + "|" + strResult[i][2] + "|" + strResult[i][3];
		}
		
		document.getElementById('FactorCode').CodeData = returnStr;
	}
	*/
	
	var queryCondition = "1 and b.riskcode=#"+ fm.RiskCode.value +"# and b.dutycode=#"+ fm.DutyCode.value +"# and b.factortype=#"+ fm.DutyFactorType.value +"#";
	showCodeList('factorcode', [Field,FieldName1,FieldName2,FieldName3], [0,1,2,3], null, queryCondition, '1', '1', null);
}

function insertDutyClick() {
	
	var tSelNo = Plan1Grid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���շ�����Ϣ��");
		return false;
	}
	
	if (fm.DutyFactorType.value=="") {
		alert("��ѡ��Ҫ�����ͣ�");
		return false;
	}
	
	if (fm.RiskCode.value=="") {
		alert("��ѡ�����֣�");
		return false;
	}
	
	if (fm.DutyCode.value=="") {
		alert("��ѡ�����Σ�");
		return false;
	}
	
	if (fm.FactorCode.value=="") {
		alert("��ѡ��Ҫ�أ�");
		return false;
	}
	
	if (fm.Params.value=="") {
		alert("��¼��Ҫ��ֵ��");
		return false;
	}
	
	mAction = "DUTY";
	mOperate = "INSERT";
	submitForm();
}

function updateDutyClick() {
	
	var tSelNo = Plan1Grid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���շ�����Ϣ��");
		return false;
	}
	
	tSelNo = ConfDutyControlGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���Ѷ�������������Ϣ��");
		return false;
	}
	
	var tParams = ConfDutyControlGrid.getRowColData(tSelNo-1, 11);
	if (tParams=="") {
		alert("��¼��Ҫ��ֵ��");
		return false;
	}
	
	mAction = "DUTY";
	mOperate = "UPDATE";
	submitForm();
}

function deleteDutyClick() {
	
	var tSelNo = Plan1Grid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���շ�����Ϣ��");
		return false;
	}
	
	tSelNo = ConfDutyControlGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���Ѷ�������������Ϣ��");
		return false;
	}
	
	mAction = "DUTY";
	mOperate = "DELETE";
	submitForm();
}

/*******************************���ÿ���****************************************/
function showPlan2Info() {
	
	var tSelNo = Plan2Grid.getSelNo();
	fm.ShareSysPlanCode.value = Plan2Grid.getRowColData(tSelNo-1, 1);
	fm.SharePlanCode.value = Plan2Grid.getRowColData(tSelNo-1, 2);
	
	fm.ShareFactorType.value = "";
	fm.ShareFactorTypeName.value = "";
	initShareControlGrid();
	initConfDutyShareControlGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql18");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
		tSQLInfo.addSubPara(fm.SharePlanCode.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql19");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
		tSQLInfo.addSubPara(fm.SharePlanCode.value);
	}
	
	turnPage6.pageLineNum = 100;
	turnPage6.queryModal(tSQLInfo.getString(), ConfShareControlGrid, 2, 1);
}

function showConfShareControlInfo() {
	
	var tSelNo = ConfShareControlGrid.getSelNo();
	
	var tFactorType = ConfShareControlGrid.getRowColData(tSelNo-1, 1);
	var tFactorCode = ConfShareControlGrid.getRowColData(tSelNo-1, 3);
	var tInerSerialNo = ConfShareControlGrid.getRowColData(tSelNo-1, 5);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql20");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
		tSQLInfo.addSubPara(fm.SharePlanCode.value);
		tSQLInfo.addSubPara(tFactorType);
		tSQLInfo.addSubPara(tFactorCode);
		tSQLInfo.addSubPara(tInerSerialNo);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql21");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
		tSQLInfo.addSubPara(fm.SharePlanCode.value);
		tSQLInfo.addSubPara(tFactorType);
		tSQLInfo.addSubPara(tFactorCode);
		tSQLInfo.addSubPara(tInerSerialNo);
	}
	
	turnPage7.pageLineNum = 100;
	turnPage7.queryModal(tSQLInfo.getString(), ConfDutyShareControlGrid, 2, 1);
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

	if (value1=='ShareFactorType') {
		
		var tSelNo = Plan2Grid.getSelNo();
		if (tSelNo==0) {
			alert("����ѡ���շ�����Ϣ��");
			return false;
		}
		
		var tCodeType;
		var tSql = "";
		if (tBussType=="NB") {
			
			tCodeType = "ShareFactorTypeNB";
			tSql = "1 and c.policyno=#"+ tBussNo +"# and c.sysplancode =#"+ fm.ShareSysPlanCode.value +"# and c.plancode =#"+ fm.SharePlanCode.value +"#";
		} else {
			
			tCodeType = "ShareFactorTypeQUOT";
			tSql = "1 and c.quotno = #"+ tBussNo +"# and c.quotbatno =#"+  tSubBussNo+"# and c.sysplancode =#"+ fm.ShareSysPlanCode.value +"# and c.plancode =#"+ fm.SharePlanCode.value +"#";
		}
		
		
		if (returnType=='0') {
			return showCodeList(tCodeType,value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey(tCodeType,value2,value3,null,tSql,'1','1',null);
		}
	}
}

function getShareFactorType() {
	
	
	var tSelNo = Plan2Grid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ���շ�����Ϣ��");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql12");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
		tSQLInfo.addSubPara(fm.SharePlanCode.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql13");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
		tSQLInfo.addSubPara(fm.SharePlanCode.value);
	}
	
	var strResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strResult==null) {
		
		alert("�÷���û����Ҫ���ƵĿ���Ҫ�أ�");
		document.getElementById('ShareFactorType').CodeData = "";
		return false;
	} else {
		
		var returnStr = "0|";
		for (i=0;i<strResult.length;i++) {
			returnStr = returnStr + "^" + strResult[i][0] + "|" + strResult[i][1] + "|" + strResult[i][2];
		}
		
		document.getElementById('ShareFactorType').CodeData = returnStr;
	}
}

function insertShareClick() {
	
	var tSelNo = Plan2Grid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���շ�����Ϣ��");
		return false;
	}
	
	if (fm.ShareFactorType.value=="") {
		alert("��ѡ��Ҫ�����ͣ�");
		return false;
	}
	
	mAction = "SHARE";
	mOperate = "INSERT";
	submitForm();
}

function deleteShareClick() {
	
	var tSelNo = Plan2Grid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���շ�����Ϣ��");
		return false;
	}
	
	tSelNo = ConfShareControlGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���ѹ�����Ϣ�б�");
		return false;
	}
	
	mAction = "SHARE";
	mOperate = "DELETE";
	submitForm();
}

/*******************************��������****************************************/
function showPlan3Info() {
	
	var tSelNo = Plan3Grid.getSelNo();
	fm.GetSysPlanCode.value = Plan3Grid.getRowColData(tSelNo-1, 1);
	fm.GetPlanCode.value = Plan3Grid.getRowColData(tSelNo-1, 2);
	
	initGetControlGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql22");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.GetSysPlanCode.value);
		tSQLInfo.addSubPara(fm.GetPlanCode.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql23");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.GetSysPlanCode.value);
		tSQLInfo.addSubPara(fm.GetPlanCode.value);
	}
	
	turnPage9.pageLineNum = 100;
	turnPage9.queryModal(tSQLInfo.getString(), RiskDutyGrid, 2, 1);
}

function showGetControlInfo() {
	
	var tSelNo = RiskDutyGrid.getSelNo();
	
	fm.GetRiskCode.value = RiskDutyGrid.getRowColData(tSelNo-1, 1);
	fm.GetDutyCode.value = RiskDutyGrid.getRowColData(tSelNo-1, 3);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
		
		tSQLInfo.setSqlId("LLCLaimControlSql24");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm.GetSysPlanCode.value);
		tSQLInfo.addSubPara(fm.GetPlanCode.value);
		tSQLInfo.addSubPara(fm.GetRiskCode.value);
		tSQLInfo.addSubPara(fm.GetDutyCode.value);
	} else {
		
		tSQLInfo.setSqlId("LLCLaimControlSql25");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
		tSQLInfo.addSubPara(fm.GetSysPlanCode.value);
		tSQLInfo.addSubPara(fm.GetPlanCode.value);
		tSQLInfo.addSubPara(fm.GetRiskCode.value);
		tSQLInfo.addSubPara(fm.GetDutyCode.value);
	}
	
	turnPage10.pageLineNum = 100;
	turnPage10.queryModal(tSQLInfo.getString(), GetControlGrid, 2, 1);
}

function saveGetClick() {
	
	var tSelNo = Plan3Grid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ���շ�����Ϣ��");
		return false;
	}
	
	tSelNo = RiskDutyGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ������������Ϣ��");
		return false;
	}
	
	mAction = "GET";
	mOperate = "SAVE";
	submitForm();
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {
	
	if (cCodeName=="nbdutyfactortype" || cCodeName=="quotdutyfactortype") {
		
		fm.RiskCode.value = "";
		fm.RiskName.value = "";
		fm.DutyCode.value = "";
		fm.DutyName.value = "";
		fm.FactorCode.value = "";
		fm.FactorName.value = "";
		fm.CalRemark.value = "";
		fm.Params.value = "";
	}
	
	if (cCodeName=="nbriskcode" || cCodeName=="quotriskcode") {
		
		fm.DutyCode.value = "";
		fm.DutyName.value = "";
		fm.FactorCode.value = "";
		fm.FactorName.value = "";
		fm.CalRemark.value = "";
		fm.Params.value = "";
	}
	
	if (cCodeName=="nbdutycode" || cCodeName=="quotdutycode") {
		
		fm.FactorCode.value = "";
		fm.FactorName.value = "";
		fm.CalRemark.value = "";
		fm.Params.value = "";
	}
	
	if (cCodeName=="ShareFactorTypeNB" || cCodeName=="ShareFactorTypeQUOT") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
		
		if (tBussType=="NB") {
			
			if (fm.ShareFactorAttribute.value=="0") {
				
				tSQLInfo.setSqlId("LLCLaimControlSql14");
				tSQLInfo.addSubPara(tBussNo);
				tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
				tSQLInfo.addSubPara(fm.SharePlanCode.value);
				tSQLInfo.addSubPara(fm.ShareFactorType.value);
			} else {
				
				tSQLInfo.setSqlId("LLCLaimControlSql15");
				tSQLInfo.addSubPara(tBussNo);
				tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
				tSQLInfo.addSubPara(fm.SharePlanCode.value);
				tSQLInfo.addSubPara(fm.ShareFactorType.value);
			}
		} else {
			
			if (fm.ShareFactorAttribute.value=="0") {
				
				tSQLInfo.setSqlId("LLCLaimControlSql16");
				tSQLInfo.addSubPara(tBussNo);
				tSQLInfo.addSubPara(tSubBussNo);
				tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
				tSQLInfo.addSubPara(fm.SharePlanCode.value);
				tSQLInfo.addSubPara(fm.ShareFactorType.value);
			} else {
				
				tSQLInfo.setSqlId("LLCLaimControlSql17");
				tSQLInfo.addSubPara(tBussNo);
				tSQLInfo.addSubPara(tSubBussNo);
				tSQLInfo.addSubPara(fm.ShareSysPlanCode.value);
				tSQLInfo.addSubPara(fm.SharePlanCode.value);
				tSQLInfo.addSubPara(fm.ShareFactorType.value);
			}
		}
		
		turnPage5.pageLineNum = 100;
		turnPage5.queryModal(tSQLInfo.getString(), ShareControlGrid, 2, 1);
	}
}

function setTab(m, n) {
		
	var menu = document.getElementById("tab"+m).getElementsByTagName("li");
	
	for (i=0;i<3;i++) {
		
		menu[i].className = i==n?"now":"";
		
		if (i==n) {
			document.getElementById("tablistdiv"+i).style.display = "block";
		} else {
			document.getElementById("tablistdiv"+i).style.display = "none";
		}
	}
	
	tFlag = n;
}

function setTabOver(m, n) {
	
	if (tFlag==n) {
		return;
	}
	
	var menu = document.getElementById("tab"+m).getElementsByTagName("li");
	menu[tFlag].className = "";
	menu[n].className = "now";
}
	
function setTabOut(m, n) {
	
	if (tFlag==n) {
		return;
	}
	
	var menu = document.getElementById("tab"+m).getElementsByTagName("li");
	menu[tFlag].className = "now";
	menu[n].className = "";
}
/**
 * �������ο�����Ϣ����
 */
function ImportClick() {
		
	var strUrl="./LLClaimControlImpMain.jsp?GrpContNo="+tBussNo+"&Mode=0";
	var tWidth=1200;
	var tHeight=800;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�������ο�����Ϣ����",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * �������ο�����Ϣ����
 */
function ExportClick() {
	
	var tGrpContNo = tBussNo;
	
	fm.SheetName[0].value = "ģ��˵��";
	fm.SheetName[1].value = "Ҫ�ؿ���";
	fm.SheetName[2].value = "���ÿ���";
	fm.SheetName[3].value = "��������";
	
	var tExportTitle = "������";
	//Ҫ�ؿ���
	var tParamTitle = "���շ�������^|����^|����^|Ҫ������^|Ҫ��^|Ҫ��ֵ";
	
	//���ÿ���
	var tBillTitle = "���շ�������^|����^|����^|Ҫ������^|Ҫ��^|Ҫ��ֵ";
	
	//��������	
	var tDuceTitle = "���շ�������^|����^|����^|��������^|��������^|�ȴ���^|���ⷽʽ^|�����^|��������^|�⸶����^|���޶�^|���޶�^|��λ����^|�����޶��/�Σ�";
	
	fm.SheetTitle[0].value = tExportTitle;
	fm.SheetTitle[1].value = tParamTitle;
	fm.SheetTitle[2].value = tBillTitle;
	fm.SheetTitle[3].value = tDuceTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	tSQLInfo.setSqlId("LLClaimControlSql29");
	tSQLInfo.addSubPara(tGrpContNo);
	fm.SheetSql[0].value = tSQLInfo.getString();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	tSQLInfo.setSqlId("LLClaimControlSql26");
	tSQLInfo.addSubPara(tGrpContNo);
		
	fm.SheetSql[1].value = tSQLInfo.getString();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	tSQLInfo.setSqlId("LLClaimControlSql27");
	tSQLInfo.addSubPara(tGrpContNo);
	
	fm.SheetSql[2].value = tSQLInfo.getString();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	tSQLInfo.setSqlId("LLClaimControlSql28");
	tSQLInfo.addSubPara(tGrpContNo);	
	
	fm.SheetSql[3].value = tSQLInfo.getString();	
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}


function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlSql");
	
	if (tBussType=="NB") {
	
		tSQLInfo.setSqlId("LLCLaimControlSql1");
		tSQLInfo.addSubPara(tBussNo);
	} else {
	
		tSQLInfo.setSqlId("LLCLaimControlSql2");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(tSubBussNo);
	}
	
	turnPage1.pageLineNum = 5;
	turnPage4.pageLineNum = 5;
	turnPage8.pageLineNum = 5;
	
	turnPage1.queryModal(tSQLInfo.getString(), Plan1Grid, 2);
	turnPage4.queryModal(tSQLInfo.getString(), Plan2Grid, 2);
	turnPage8.queryModal(tSQLInfo.getString(), Plan3Grid, 2);
}
