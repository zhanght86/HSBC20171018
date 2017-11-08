/***************************************************************
 * <p>ProName��LFGetPayInput.js</p>
 * <p>Title��������ȡ</p>
 * <p>Description��������ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-09-22
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * δ��ȡ�����ϸ��ѯ
 */
function queryClick(QueryFlag) {
	
	initUnGetGrid();
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_get.LFGetPaySql");
	tSQLInfo.setSqlId("LFGetPaySql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.ContNo.value);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.GetStartDate.value);
	tSQLInfo.addSubPara(fm.GetEndDate.value);
	tSQLInfo.addSubPara(tCurrenDate);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage1.queryModal(tSQLInfo.getString(), UnGetGrid, 1, 1);
	
	if (QueryFlag==1) {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
	
	getClick();
}

/**
 * ����ȡ�����ϸ��ѯ
 */
function getClick() {
	
	initGetGrid();
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_get.LFGetPaySql");
	tSQLInfo.setSqlId("LFGetPaySql2");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.ContNo.value);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.GetStartDate.value);
	tSQLInfo.addSubPara(fm.GetEndDate.value);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage2.queryModal(tSQLInfo.getString(), GetGrid, 1, 1);
}

/**
 * ѡ����ȡ
 */
function selGetClick() {
	
	var rowNum = UnGetGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (UnGetGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("������ѡ��һ����¼!");
		return false;
	}
	
	mOperate="SELGET";
	
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./LFGetPaySave.jsp?Operate="+mOperate;
	submitForm(fm);
}

/**
 * ����
 */
function cancelClick() {
	
	var rowNum = GetGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (GetGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("������ѡ��һ����¼!");
		return false;
	}
	
	mOperate="CANCEL";
	
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./LFGetPaySave.jsp?Operate="+mOperate;
	submitForm(fm);
}

/**
 * ����ȷ��
 */
function appConfClick() {
	
	var rowNum = GetGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (GetGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("������ѡ��һ����¼!");
		return false;
	}
	
	mOperate="APPCONF";
	
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./LFGetPaySave.jsp?Operate="+mOperate;
	submitForm(fm);
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
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		queryClick(2);
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
	
	return true;
}