/***************************************************************
 * <p>ProName��LDUWPopedomInput.js</p>
 * <p>Title���˱�Ȩ�޹���</p>
 * <p>Description���˱�Ȩ�޹���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
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
		
		initUWPopedomGrid();
		initInpBox();
	}
}

/**
 * ��ѯ
 */
function querySubmit() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_auxi.LDUWPopedomSql");
	tSQLInfo.setSqlId("LDUWPopedomSql1");
	tSQLInfo.addSubPara(fm.PopedomLevel1.value);
	tSQLInfo.addSubPara(fm.PopedomName1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWPopedomGrid, 0, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * չʾ�û���Ϣ
 */
function showUWPopedomInfo() {
	
	var tRow = UWPopedomGrid.getSelNo();
	fm.PopedomLevel.value = UWPopedomGrid.getRowColData(tRow-1,1);
	fm.PopedomName.value = UWPopedomGrid.getRowColData(tRow-1,2);
	fm.PerLifeAmnt.value = UWPopedomGrid.getRowColData(tRow-1,3);
	fm.PerAcciAmnt.value = UWPopedomGrid.getRowColData(tRow-1,4);
	fm.PerIllAmnt.value = UWPopedomGrid.getRowColData(tRow-1,5);
	fm.PerMedAmnt.value = UWPopedomGrid.getRowColData(tRow-1,6);
	fm.PremScale.value = UWPopedomGrid.getRowColData(tRow-1,7);
	fm.MainPremRateFloat.value = UWPopedomGrid.getRowColData(tRow-1,8);
	fm.MedPremRateFloat.value = UWPopedomGrid.getRowColData(tRow-1,9);
	fm.ValDate.value = UWPopedomGrid.getRowColData(tRow-1,10);
	fm.EndDate.value = UWPopedomGrid.getRowColData(tRow-1,11);
}

/**
 * ����
 */
function addSubmit() {
	
	mOperate = "INSERT";
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWPopedomSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * �޸�
 */
function modSubmit() {
	
	mOperate = "UPDATE";
	var tRow = UWPopedomGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWPopedomSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * ɾ��
 */
function delSubmit() {
	
	var tRow = UWPopedomGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fm.PopedomLevel.value = UWPopedomGrid.getRowColData(tRow-1,1);
	mOperate = "DELETE";
	fm.action = "./LDUWPopedomSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * �ύǰУ��
 */
function checkSubmit() {
	
	if (!verifyInput2()) {
		return false;
	}
	if (fm.ValDate.value > fm.EndDate.value) {
		alert("��Ч����ӦС����ֹ���ڣ�");
		return false;
	}
	if (mOperate == "INSERT") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_auxi.LDUWPopedomSql");
		tSQLInfo.setSqlId("LDUWPopedomSql2");
		tSQLInfo.addSubPara(fm.PopedomLevel.value);

		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr[0][0] != 0) {
			alert("�ú˱�Ȩ���Ѵ��ڣ�");
			return false;
		}
	}
	
	if (mOperate == "UPDATE") {
		
		var tRow = UWPopedomGrid.getSelNo();
		var tPopedomLevel = UWPopedomGrid.getRowColData(tRow-1,1);
		if (fm.PopedomLevel.value != tPopedomLevel) {
			alert("Ȩ�޼��𲻿��޸ģ�");
			return false;
		}
	}
	
	return true;
}
