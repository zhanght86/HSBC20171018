/***************************************************************
 * <p>ProName��LSQuotExpandInput.js</p>
 * <p>Title��������չ</p>
 * <p>Description��������չ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

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
		
		showDetailInfo();
		dealBox('0');
	}
}

/**
 * ������̬չʾ
 */
function afterCodeSelect(o, p) {
	
	if(o=='quotprodrisk') {
		
		fm.DutyCode.value = "";
		fm.DutyName.value = "";
		fm.GetDutyCode.value = "";
		fm.GetDutyName.value = "";
		fm.ExpandType.value = "";
		fm.ExpandTypeName.value = "";
		fm.ExpandDuty.value = "";
		fm.ExpandDutyName.value = "";
		
		document.all("allTD1").style.display = 'none';
		document.all("allTD2").style.display = 'none';
		document.all("allTD3").style.display = 'none';
		document.all("allTD4").style.display = 'none';
		document.all("allTD5").style.display = 'none';
		document.all("allTD6").style.display = 'none';
		document.all("allTD7").style.display = 'none';
		document.all("allTD8").style.display = 'none';
		
	} else if(o=='quotplanduty') {
		
		fm.GetDutyCode.value = "";
		fm.GetDutyName.value = "";
		fm.ExpandType.value = "";
		fm.ExpandTypeName.value = "";
		fm.ExpandDuty.value = "";
		fm.ExpandDutyName.value = "";
		
		document.all("allTD1").style.display = 'none';
		document.all("allTD2").style.display = 'none';
		document.all("allTD3").style.display = 'none';
		document.all("allTD4").style.display = 'none';
		document.all("allTD5").style.display = 'none';
		document.all("allTD6").style.display = 'none';
		document.all("allTD7").style.display = 'none';
		document.all("allTD8").style.display = 'none';
		
	} else if(o=='quotgetduty') {
		
		document.all("allTD1").style.display = '';
		document.all("allTD2").style.display = '';
		document.all("allTD3").style.display = 'none';
		document.all("allTD4").style.display = 'none';
		document.all("allTD5").style.display = '';
		document.all("allTD6").style.display = '';
		document.all("allTD7").style.display = '';
		document.all("allTD8").style.display = '';
		
		fm.ExpandType.value = "";
		fm.ExpandTypeName.value = "";
		fm.ExpandDuty.value = "";
		fm.ExpandDutyName.value = "";
		
	} else if (o=='qoutexpandtype') {
		
		if (p.value=='0') {
			document.all("allTD3").style.display = '';
			document.all("allTD4").style.display = '';
			document.all("allTD5").style.display = '';
			document.all("allTD6").style.display = '';
			document.all("allTD7").style.display = 'none';
			document.all("allTD8").style.display = 'none';
		} else if (p.value=='1') {
			document.all("allTD3").style.display = 'none';
			document.all("allTD4").style.display = 'none';
			document.all("allTD5").style.display = '';
			document.all("allTD6").style.display = '';
			document.all("allTD7").style.display = '';
			document.all("allTD8").style.display = '';
		}
		
		fm.ExpandDuty.value = "";
		fm.ExpandDutyName.value = "";
		document.all("divDutyFactor").innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	} else if (o=='expandduty') {
		
		var tExpandDuty = fm.ExpandDuty.value;
		var tFlag = 0;
		var tObj = fm;
		var tDivID = "divDutyFactor";
		dealDynamicElements(tExpandDuty, tFlag, tObj, tDivID);
		divDutyFactor.style.display = '';
	}
}

/**
 * �����Ʒ����Ҫ��չʾcSysPlanCode,cPlanCode,cRiskCode,cDutyCode,cGetDutyCode,cExpandType,
 */
function dealDynamicElements(cExpandDuty, cFlag, cObj, cDivID){
	
	document.all(cDivID).innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	
	//��ѯ����չʾ�����Ӳ�չʾ�ڽ�����
	var tArr = getDutyArr(cExpandDuty);
	
	if (tArr!=null && tArr.length!=0) {
		
		document.all(cDivID).innerHTML = getDutyElement(tArr);	
	} 
	
	if (cFlag=="0") {
		return;
	}
	if (tArr==null || tArr.length==0) {
		
	} else {
		
		var tSQLElement = getDutySQLElement(tArr);
		
		var tRow = ExpandInfoGrid.getSelNo();
		var tSysPlanCode = ExpandInfoGrid.getRowColData(tRow-1,1);
		var tPlanCode = ExpandInfoGrid.getRowColData(tRow-1,2);
		var tRiskCode = ExpandInfoGrid.getRowColData(tRow-1,3);
		var tDutyCode = ExpandInfoGrid.getRowColData(tRow-1,5);
		var tGetDutyCode = ExpandInfoGrid.getRowColData(tRow-1,7);
		var tExpandDutyCode = ExpandInfoGrid.getRowColData(tRow-1,11);
		
		//���ݲ�ѯ�ֶν��дӱ��ѯ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotExpandSql");
		tSQLInfo.setSqlId("LSQuotExpandSql4");
		tSQLInfo.addSubPara(tSQLElement);//SQL��ѯ�ֶ�
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tSysPlanCode);
		tSQLInfo.addSubPara(tPlanCode);
		tSQLInfo.addSubPara(tRiskCode);
		tSQLInfo.addSubPara(tDutyCode);
		tSQLInfo.addSubPara(tGetDutyCode);
		tSQLInfo.addSubPara(tExpandDutyCode);
		
		var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr1==null) {
			
		} else {//ѭ���Խ���Ҫ�ظ�ֵ
			
			for (var i=0; i<tArr.length; i++) {
			
				tFactorCode = tArr[i][1];//�ӱ��ֶ�
				tFieldType = tArr[i][3];//�ֶ�����
				tValueType = tArr[i][4];//ֵ����
				
				if (tFieldType=="1") {
					
					document.all(tFactorCode).value = tArr1[0][i];
					auotQuotShowCodeName(tValueType, tArr1[0][i], cObj, tFactorCode+"Name");
				} else {
					document.all(tFactorCode).value = tArr1[0][i];
				}
			}
		}
	}
}

/**
 * ��ȡ��̬Ҫ������
 */
function getDutyArr(cDutyCode) {

	var tCalFactor;//ԭ���ӱ���
	var tFactorCode;//�ӱ��ֶ�
	var tFactorName;//��������
	var tFieldType;//�ֶ�����
	var tValueType;//ֵ����
	var tDefaultValue;//Ĭ��ֵ
	var tFieldLength;//�ֶγ���
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotExpandSql");
	tSQLInfo.setSqlId("LSQuotExpandSql3");
	tSQLInfo.addSubPara(cDutyCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	return tArr;
}

/**
 * ������Ϣ
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotExpandSql");
	tSQLInfo.setSqlId("LSQuotExpandSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 2, 1);
}

/**
 * ���������Ϣ��չʾ��չ��Ϣ
 */
function showDetailInfo() {
	
	var tRow = PlanInfoGrid.getSelNo();
	var tPlanCode = PlanInfoGrid.getRowColData(tRow-1,2);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotExpandSql");
	tSQLInfo.setSqlId("LSQuotExpandSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tPlanCode);
	
	var tRiskCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tRiskCount[0][0]==0) {
		divAllExpandInfo.style.display = 'none';
		alert("�÷����޿ɽ���������չ������!");
		return false;
	} else {
		
		divAllExpandInfo.style.display = '';
		queryExpandInfo();
		initInpBox();
		document.all("allTD1").style.display = 'none';
		document.all("allTD2").style.display = 'none';
		document.all("allTD3").style.display = 'none';
		document.all("allTD4").style.display = 'none';
		document.all("allTD5").style.display = 'none';
		document.all("allTD6").style.display = 'none';
		document.all("allTD7").style.display = 'none';
		document.all("allTD8").style.display = 'none';
		
		document.all("divDutyFactor").innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
		
		dealBox('0');
	}
}

/**
 * ��ѯ��չ��Ϣ
 */
function queryExpandInfo() {
	
	var tRow = PlanInfoGrid.getSelNo();
	var tSysPlanCode = PlanInfoGrid.getRowColData(tRow-1,1);
	var tPlanCode = PlanInfoGrid.getRowColData(tRow-1,2);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotExpandSql");
	tSQLInfo.setSqlId("LSQuotExpandSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	
	turnPage2.queryModal(tSQLInfo.getString(), ExpandInfoGrid, 0, 1);

}

/**
 * �����չ��Ϣ
 */
function showExpandInfo() {
	
	initInpBox();
	var tRow = ExpandInfoGrid.getSelNo();
	fm.RiskCode.value = ExpandInfoGrid.getRowColData(tRow-1,3);
	fm.RiskName.value = ExpandInfoGrid.getRowColData(tRow-1,4);
	fm.DutyCode.value = ExpandInfoGrid.getRowColData(tRow-1,5);
	fm.DutyName.value = ExpandInfoGrid.getRowColData(tRow-1,6);
	fm.GetDutyCode.value = ExpandInfoGrid.getRowColData(tRow-1,7);
	fm.GetDutyName.value = ExpandInfoGrid.getRowColData(tRow-1,8);
	fm.ExpandType.value = ExpandInfoGrid.getRowColData(tRow-1,9);
	fm.ExpandTypeName.value = ExpandInfoGrid.getRowColData(tRow-1,10);
	
	if (fm.ExpandType.value =="0") {
		
		document.all("allTD1").style.display = '';
		document.all("allTD2").style.display = '';
		document.all("allTD3").style.display = '';
		document.all("allTD4").style.display = '';
		document.all("allTD5").style.display = '';
		document.all("allTD6").style.display = '';
		document.all("allTD7").style.display = 'none';
		document.all("allTD8").style.display = 'none';
		divDutyFactor.style.display = '';
		fm.ExpandDuty.value = ExpandInfoGrid.getRowColData(tRow-1,11);
		fm.ExpandDutyName.value = ExpandInfoGrid.getRowColData(tRow-1,12);
		
		var tExpandDuty = fm.ExpandDuty.value;
		var tFlag = 1;
		var tObj = fm;
		var tDivID = "divDutyFactor";
		
		dealDynamicElements(tExpandDuty, tFlag, tObj, tDivID);
		
	} else {
		
		document.all("allTD1").style.display = '';
		document.all("allTD2").style.display = '';
		document.all("allTD3").style.display = 'none';
		document.all("allTD4").style.display = 'none';
		document.all("allTD5").style.display = 'none';
		document.all("allTD6").style.display = 'none';
		document.all("allTD7").style.display = 'none';
		document.all("allTD8").style.display = 'none';
		divDutyFactor.style.display = 'none';
	}
	dealBox('1');
}

/**
 * �ύǰ��У��
 */
function checkBeforSubmit() {
	
	var tExpandDuty = fm.ExpandDuty.value;
	
	var tArr = getDutyArr(tExpandDuty);
	
	if (!checkDutyElement(fm,tArr)) {
		return false;
	}
	
	return true;
}

/**
 * ����
 */
function addClick() {

	if (!verifyInput2()) {
		return false;
	}
	
	if (fm.ExpandType.value =="0") {
		
		if (fm.ExpandDuty.value==null||fm.ExpandDuty.value=="") {
			alert("�������β���Ϊ�գ�");
			return false;
		}
		if (!checkBeforSubmit()) {
			return false;
		}
	}
	
	var tRow = PlanInfoGrid.getSelNo();
	var tSysPlanCode = PlanInfoGrid.getRowColData(tRow-1,1);
	var tPlanCode = PlanInfoGrid.getRowColData(tRow-1,2);
	
	mOperate = "INSERT";
	fm.action = "./LSQuotExpandSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * �޸�
 */
function modifyClick() {
	
	var tRow = ExpandInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����ά����չ��Ϣ��");
		return false;	
	}
	if (!verifyInput2()) {
		return false;
	}
	if (fm.ExpandType.value =="0") {
		
		if (fm.ExpandDuty.value==null||fm.ExpandDuty.value=="") {
			alert("�������β���Ϊ�գ�");
			return false;
		}
		if (!checkBeforSubmit()) {
			return false;
		}
	}
	
	var tRow = ExpandInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����ά����չ��Ϣ��");
		return false;	
	}
	
	var tSysPlanCode = ExpandInfoGrid.getRowColData(tRow-1,1);
	var tPlanCode = ExpandInfoGrid.getRowColData(tRow-1,2);
	var tRiskCode = ExpandInfoGrid.getRowColData(tRow-1,3);
	var tDutyCode = ExpandInfoGrid.getRowColData(tRow-1,5);
	var tGetDutyCode = ExpandInfoGrid.getRowColData(tRow-1,7);
	var tExpandType = ExpandInfoGrid.getRowColData(tRow-1,9);
	var tExpandDutyCode = ExpandInfoGrid.getRowColData(tRow-1,11);
	
	mOperate = "UPDATE";
	fm.action = "./LSQuotExpandSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&RiskCode="+ tRiskCode+"&DutyCode="+ tDutyCode+"&GetDutyCode="+ tGetDutyCode +"&ExpandType="+ tExpandType+"&ExpandDuty="+ tExpandDutyCode  +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * ɾ��
 */
function delClick() {
	
	var tRow = ExpandInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����ά����չ��Ϣ��");
		return false;	
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	var tRow = ExpandInfoGrid.getSelNo();
	var tSysPlanCode = ExpandInfoGrid.getRowColData(tRow-1,1);
	var tPlanCode = ExpandInfoGrid.getRowColData(tRow-1,2);
	var tRiskCode = ExpandInfoGrid.getRowColData(tRow-1,3);
	var tDutyCode = ExpandInfoGrid.getRowColData(tRow-1,5);
	var tGetDutyCode = ExpandInfoGrid.getRowColData(tRow-1,7);
	var tExpandType = ExpandInfoGrid.getRowColData(tRow-1,9);
	var tExpandDutyCode = ExpandInfoGrid.getRowColData(tRow-1,11);
	
	mOperate = "DELETE";
	fm.action = "./LSQuotExpandSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&RiskCode="+ tRiskCode+"&DutyCode="+ tDutyCode+"&GetDutyCode="+ tGetDutyCode +"&ExpandType="+ tExpandType+"&ExpandDuty="+ tExpandDutyCode+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * ������Ƿ�ֻ������
 */
function dealBox(o) {
	
	if (o=='1') {
		
		var tRow = ExpandInfoGrid.getSelNo();
		var tExpandType = ExpandInfoGrid.getRowColData(tRow-1,9);
		
		if (tExpandType =='0') {
			
			fm.ModButton.disabled = false;
		} else if (tExpandType =='1'){
			fm.ModButton.disabled = true;
		}
		
		fm.RiskCode.disabled = true;
		fm.RiskName.disabled = true;
		fm.DutyCode.disabled = true;
		fm.DutyName.disabled = true;
		fm.GetDutyCode.disabled = true;
		fm.GetDutyName.disabled = true;
		fm.ExpandType.disabled = true;
		fm.ExpandTypeName.disabled = true;
		fm.ExpandDuty.disabled = true;
		fm.ExpandDutyName.disabled = true;
		fm.AddButton.disabled = true;
	} else if (o=='0') {
		
		fm.RiskCode.disabled = false;
		fm.RiskName.disabled = false;
		fm.DutyCode.disabled = false;
		fm.DutyName.disabled = false;
		fm.GetDutyCode.disabled = false;
		fm.GetDutyName.disabled = false;
		fm.ExpandType.disabled = false;
		fm.ExpandTypeName.disabled = false;
		fm.ExpandDuty.disabled = false;
		fm.ExpandDutyName.disabled = false;
		fm.AddButton.disabled = false;
		fm.ModButton.disabled = false;
	}
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
	
	if (value1=='quotprodriskexpand') {//����
		
		var tRow = PlanInfoGrid.getSelNo();
		var tPlanCode = PlanInfoGrid.getRowColData(tRow-1,2);
		var conditionRiskCode = "1 and a.quotno= #"+tQuotNo+"# and a.quotbatno = #"+tQuotBatNo+"# and a.plancode = #"+tPlanCode+"# ";
		
		if (returnType=='0') {
			
			return showCodeList('quotprodriskexpand',value2,value3,null,conditionRiskCode,'1','1',null);
		} else {
			
			return showCodeListKey('quotprodriskexpand',value2,value3,null,conditionRiskCode,'1','1',null);
		}
	} else if (value1=='quotplanduty') {//����
		
		if (isEmpty(fm.RiskCode)) {
			alert("����ѡ������ ��");
			return false;
		}
		var tRow = PlanInfoGrid.getSelNo();
		var tPlanCode = PlanInfoGrid.getRowColData(tRow-1,2);
		var tRiskCode = fm.RiskCode.value;
		var conditionDutyCode = "1 and a.quotno= #"+tQuotNo+"# and a.quotbatno = #"+tQuotBatNo+"# and a.plancode = #"+tPlanCode+"# and a.riskcode = #"+tRiskCode+"# ";
		
		if (returnType=='0') {
			
			return showCodeList('quotplanduty',value2,value3,null,conditionDutyCode,'1','1',null);
		} else {
			
			return showCodeListKey('quotplanduty',value2,value3,null,conditionDutyCode,'1','1',null);
		}
	} else if (value1=='quotgetduty') {//������
		
		if (isEmpty(fm.RiskCode)) {
			alert("����ѡ������ ��");
			return false;
		}
		if (isEmpty(fm.DutyCode)) {
			alert("����ѡ������!");
			return false;
		}
		var tRiskCode = fm.RiskCode.value;
		var tDutyCode = fm.DutyCode.value;
		var conditionGetDutyCode = "1 and b.riskcode = #"+tRiskCode+"# and a.dutycode= #"+tDutyCode+"# ";
		
		if (returnType=='0') {
			
			return showCodeList('quotgetduty',value2,value3,null,conditionGetDutyCode,'1','1',null);
		} else {
			
			return showCodeListKey('quotgetduty',value2,value3,null,conditionGetDutyCode,'1','1',null);
		}
	} else if (value1=='qoutexpandtype') {//��չ����
		
		if (isEmpty(fm.GetDutyCode)) {
			alert("����ѡ������!");
			return false;
		}
		var tRiskCode = fm.RiskCode.value;
		var tDutyCode = fm.DutyCode.value;
		var tGetDutyCode = fm.GetDutyCode.value;
		
		var conditionExpandType = "1 and a.riskcode= #"+tRiskCode+"# and a.dutycode= #"+tDutyCode+"# and a.getdutycode= #"+tGetDutyCode+"# ";
		if (returnType=='0') {
			
			return showCodeList('qoutexpandtype',value2,value3,null,conditionExpandType,'1','1',null);
		} else {
			
			return showCodeListKey('qoutexpandtype',value2,value3,null,conditionExpandType,'1','1',null);
		}
	}  else if (value1=='expandduty') {//��������
		
		if (isEmpty(fm.ExpandType)) {
			alert("����ѡ����չ����!");
			return false;
		}
		var tRiskCode = fm.RiskCode.value;
		var tDutyCode = fm.DutyCode.value;
		var tGetDutyCode = fm.GetDutyCode.value;
		var tExpandType = fm.ExpandType.value;
		
		var conditionExpandDuty = "1 and a.riskcode= #"+tRiskCode+"# and a.dutycode= #"+tDutyCode+"# and a.getdutycode= #"+tGetDutyCode+"# and a.expandtype= #"+tExpandType+"#";
		if (returnType=='0') {
			
			return showCodeList('expandduty',value2,value3,null,conditionExpandDuty,'1','1',null);
		} else {
			
			return showCodeListKey('expandduty',value2,value3,null,conditionExpandDuty,'1','1',null);
		}
	}
}
