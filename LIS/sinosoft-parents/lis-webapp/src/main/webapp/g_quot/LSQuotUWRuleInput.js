/***************************************************************
 * <p>ProName��LSQuotUWRuleInput.js</p>
 * <p>Title���˱�����</p>
 * <p>Description���˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-04
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
		
		afterSubmitSelect();
	}
}

/**
 * �ύ�����չʾ����
 */
function afterSubmitSelect(){
	
	if (mOperate == "SAVE") {
		queryUWRuleInfo();
	} else {
		showPage('0');
		initInpBox();
		queryEdorRuleInfo();
	}
}

/**
 * ������̬չʾ
 */
function afterCodeSelect(o, p) {
	
	if(o=='edorruletype') {
		
		fm.EdorCode.value = "";
		fm.EdorCodeName.value = "";
		fm.CalCode.value = "";
		fm.CalCodeName.value = "";
		
		if(p.value=='1') {
			
			showPage('1');
		
		} else if (p.value=='2') {
			
			showPage('2');
		} else {
			
			showPage('0');
		}
	}
	if(o=='quotedorcode') {
		fm.CalCode.value = "";
		fm.CalCodeName.value = "";
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

	if (value1=="edorrulecode") {
		
		if (!isEmpty(fm.RuleType)) {
			var tRuleType = fm.RuleType.value;
			var vCodeType = fm.RuleType.value;
			if(tRuleType=="2"){
				vCodeType = fm.EdorCode.value;
			}
			
			var tSql = "1 and codetype=#edorrulecode# and codeexp=#"+ vCodeType +"#"
			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'1','1',180);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',180);
			}
		}
	}
}
/**
 * ��ѯ�˱�����
 */
function queryUWRuleInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWRuleSql");
	tSQLInfo.setSqlId("LSQuotUWRuleSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (tActivityID =="0800100004" || tActivityID =="0800100005") {
		tSQLInfo.addSubPara('1');
	} else {
		tSQLInfo.addSubPara('1');
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), UWRuleGrid, 2, 1);
}

/**
 * ��ѯ��ȫ����
 */
function queryEdorRuleInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWRuleSql");
	tSQLInfo.setSqlId("LSQuotUWRuleSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), EdorRuleGrid, 2, 1);
}

/**
 * չʾ��ȫ����
 */
function showEdorRuleInfo() {
	
	var tRow = EdorRuleGrid.getSelNo();
	var tRuleType = EdorRuleGrid.getRowColData(tRow-1,1);//��ȫ�������ͱ���
		
		showPage(tRuleType);
		
		fm.RuleType.value = EdorRuleGrid.getRowColData(tRow-1,1);
		fm.RuleTypeName.value = EdorRuleGrid.getRowColData(tRow-1,2);
		fm.EdorCode.value = EdorRuleGrid.getRowColData(tRow-1,3);
		fm.EdorCodeName.value = EdorRuleGrid.getRowColData(tRow-1,4);
		fm.CalCode.value = EdorRuleGrid.getRowColData(tRow-1,5);
		fm.CalCodeName.value = EdorRuleGrid.getRowColData(tRow-1,6);
	 
}


/**
 * ����
 */
function saveClick() {
	
	mOperate = "SAVE";
	var tRow = UWRuleGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	
	fm.RiskCode.value = UWRuleGrid.getRowColData(tRow-1,3);//��Ʒ����
	fm.Params.value = UWRuleGrid.getRowColData(tRow-1,6);//����
	fm.InputValues.value = UWRuleGrid.getRowColData(tRow-1,8);//����ֵ
	fm.RuleCode.value = UWRuleGrid.getRowColData(tRow-1,9);//�������
	
	if (!checkSave()) {
		return false;
	}
	
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * ����ǰУ��
 */
function checkSave() {
	
	var tInputValues = fm.InputValues.value;
	if (tInputValues==null || tInputValues=="") {
		alert("����ֵ����Ϊ�գ�");
		return false;
	}
	return true;
	
}

/**
 * ����
 */
function addClick() {
	
	mOperate = "INSERT";

	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * �޸�
 */
function modClick() {
	
	mOperate = "UPDATE";
	var tRow = EdorRuleGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	var tSerialNo = EdorRuleGrid.getRowColData(tRow-1,7);//��ˮ��
	fm.SerialNo.value = tSerialNo;
	if (!checkSubmit()) {
		return false;
	}
	
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * ɾ��
 */
function delClick() {
	
	var tRow = EdorRuleGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	var tSerialNo = EdorRuleGrid.getRowColData(tRow-1,7);//��ˮ��
	fm.SerialNo.value = tSerialNo;
	
	mOperate = "DELETE";
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * �ύǰ��У��
 */
function checkSubmit() {
	
	var tRuleType = fm.RuleType.value;
	if (tRuleType==null || tRuleType=="") {
		alert("��ȫ�������Ͳ���Ϊ�գ�");
		return false;
	}
	if (tRuleType=="2") {
		if (fm.EdorCode.value==null || fm.EdorCode.value=="") {
			alert("��ȫ��Ŀ����Ϊ�գ�");
			return false;
		}
	}
	if (fm.CalCode.value==null || fm.CalCode.value=="") {
		alert("��ȫ�㷨����Ϊ�գ�");
		return false;
	}
	//У��¼��ı�ȫ������Ϣ�Ƿ��Ѵ���
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWRuleSql");
	tSQLInfo.setSqlId("LSQuotUWRuleSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tRuleType);
	tSQLInfo.addSubPara(NullToEmpty(fm.EdorCode.value));
	if(mOperate == "INSERT"){
		tSQLInfo.addSubPara("");//modify JingDian ֻ����һ�����򱣴�һ��
		tSQLInfo.addSubPara("");
	}else if(mOperate == "UPDATE"){
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(NullToEmpty(fm.SerialNo.value));
	}
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult[0][0]!="0") {
		alert("�ñ�ȫ�����Ѵ���!");
		return false;
	}
	return true;
}

/**
 * ����Ҫ������/չʾ
 */
function showPage(tObj) {
	
	if (tObj == "2") {
		document.all("tdRule5").style.display = '';
		document.all("tdRule6").style.display = '';
		document.all("tdRule7").style.display = '';
		document.all("tdRule8").style.display = '';
		document.all("tdRule9").style.display = 'none';
		document.all("tdRule10").style.display = 'none';
		document.all("tdRule11").style.display = 'none';
		document.all("tdRule12").style.display = 'none';
	} else if (tObj == "1") {
		document.all("tdRule5").style.display = 'none';
		document.all("tdRule6").style.display = 'none';
		document.all("tdRule7").style.display = '';
		document.all("tdRule8").style.display = '';
		document.all("tdRule9").style.display = '';
		document.all("tdRule10").style.display = '';
		document.all("tdRule11").style.display = 'none';
		document.all("tdRule12").style.display = 'none';
	} else {
		document.all("tdRule5").style.display = 'none';
		document.all("tdRule6").style.display = 'none';
		document.all("tdRule7").style.display = 'none';
		document.all("tdRule8").style.display = 'none';
		document.all("tdRule9").style.display = '';
		document.all("tdRule10").style.display = '';
		document.all("tdRule11").style.display = '';
		document.all("tdRule12").style.display = '';
	}
}

