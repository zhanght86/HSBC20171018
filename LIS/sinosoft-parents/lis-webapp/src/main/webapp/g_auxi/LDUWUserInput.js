/***************************************************************
 * <p>ProName��LDUWUserInput.js</p>
 * <p>Title���˱��û�����</p>
 * <p>Description���˱��û�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-25
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
		
		initUWUserGrid();
		initInpBox();
	}
}

/**
 * ��ѯ
 */
function querySubmit() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_auxi.LDUWUserSql");
	tSQLInfo.setSqlId("LDUWUserSql1");
	tSQLInfo.addSubPara(fm.UserCode1.value);
	tSQLInfo.addSubPara(fm.UserName1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWUserGrid, 0, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}


/**
 * ��ѯ׼�ͻ���Ϣ
 */
function queryUser() {
	
	var strUrl = "LDQueryUserMain.jsp";
	window.open(strUrl,"�û���ѯ",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}

/**
 * չʾ�û���Ϣ
 */
function showUserInfo() {
	
	var tRow = UWUserGrid.getSelNo();
	fm.UserCode.value = UWUserGrid.getRowColData(tRow-1,1);
	fm.UserName.value = UWUserGrid.getRowColData(tRow-1,2);
	fm.SupervisorFlag.value = UWUserGrid.getRowColData(tRow-1,3);
	fm.SupervisorName.value = UWUserGrid.getRowColData(tRow-1,4);
	fm.PopedomLevel.value = UWUserGrid.getRowColData(tRow-1,5);
	fm.PopedomLevelName.value = UWUserGrid.getRowColData(tRow-1,6);
}

/**
 * ����
 */
function addSubmit() {
	
	mOperate = "INSERT";
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWUserSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * �޸�
 */
function modSubmit() {
	
	mOperate = "UPDATE";
	var tRow = UWUserGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWUserSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * ɾ��
 */
function delSubmit() {
	
	var tRow = UWUserGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fm.UserCode.value = UWUserGrid.getRowColData(tRow-1,1);
	mOperate = "DELETE";
	fm.action = "./LDUWUserSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * �ύǰУ��
 */
function checkSubmit() {
	
	if (fm.UserCode.value == null || fm.UserCode.value == "") {
		alert("�û����벻��Ϊ�գ�");
		return false;
	}
	if (fm.UserName.value == null || fm.UserName.value == "") {
		alert("�û���������Ϊ�գ�");
		return false;
	}
	if (fm.SupervisorFlag.value == null || fm.SupervisorFlag.value == "") {
		alert("�Ƿ�˱����ܲ���Ϊ�գ�");
		return false;
	}
	if (fm.PopedomLevel.value == null || fm.PopedomLevel.value == "") {
		alert("�˱�������Ϊ�գ�");
		return false;
	}
	
	if (mOperate == "INSERT") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_auxi.LDUWUserSql");
		tSQLInfo.setSqlId("LDUWUserSql2");
		tSQLInfo.addSubPara(fm.UserCode.value);

		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr[0][0] != 0) {
			alert("�ú˱��û��Ѵ��ڣ�");
			return false;
		}
	}
	if (mOperate == "UPDATE") {
		
		var tRow = UWUserGrid.getSelNo();
		var tUserCode = UWUserGrid.getRowColData(tRow-1,1);
		if (fm.UserCode.value  != tUserCode) {
			alert("�û����벻���޸ģ�");
			return false;
		}
	}
	
	return true;
}
