/***************************************************************
 * <p>ProName��EdorCancelInput.js</p>
 * <p>Title����ȫ����</p>
 * <p>Description����ȫ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-07-14
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ȫ������Ϣ��ѯ
 */
function queryClick(QueryFlag) {
	
	initEdorAppGrid();
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCancelSql");
	tSQLInfo.setSqlId("EdorCancelSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.ScanOperator.value);
	tSQLInfo.addSubPara(fm.ScanStartDate.value);
	tSQLInfo.addSubPara(fm.ScanEndtDate.value);
	tSQLInfo.addSubPara(fm.AcceptOperator.value);
	tSQLInfo.addSubPara(fm.AcceptStartDate.value);
	tSQLInfo.addSubPara(fm.AcceptEndtDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorAppGrid, 1, 1);
	
	if (QueryFlag==1) {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ��ȫ������Ϣ��ѯ
 */
function showEdorInfo() {
	
	var tSelNo = EdorAppGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo-1, 2);
	
	divEdorMain.style.display = "";
	divEdorItem.style.display = "none";
	initEdorGrid();
	initEdorItemGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCancelSql");
	tSQLInfo.setSqlId("EdorCancelSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), EdorGrid, 1, 1);
}

/**
 * ��ȫ��Ŀ��Ϣ��ѯ
 */
function showEdorItemInfo() {
	
	var tSelNo = EdorGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tEdorNo = EdorGrid.getRowColData(tSelNo-1, 1);
	
	divEdorItem.style.display = "";
	initEdorItemGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCancelSql");
	tSQLInfo.setSqlId("EdorCancelSql3");
	tSQLInfo.addSubPara(tEdorNo);
	
	turnPage3.queryModal(tSQLInfo.getString(), EdorItemGrid, 1, 1);
}

/**
 * ��ȫ������
 */
function edorAppCancel() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("��ѡ��һ����ȫ������Ϣ��");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tCancelReason = EdorAppGrid.getRowColData(tAppSelNo-1, 10);
	
	if (tCancelReason=="") {
		alert("����ԭ����Ϊ�գ�");
		return false;
	}
	
	fm.Operate.value = "EDORAPPCANCEL";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo;
	submitForm(fm);
}

/**
 * ��������ȫ����
 */
function cancelToAccept() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("��ѡ��һ����ȫ������Ϣ��");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tCancelReason = EdorAppGrid.getRowColData(tAppSelNo-1, 10);
	
	if (tCancelReason=="") {
		alert("����ԭ����Ϊ�գ�");
		return false;
	}
	
	fm.Operate.value = "CANCELTOACCEPT";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo;
	submitForm(fm);
}

/**
 * ��ȫ��������
 */
function edorMainCancel() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("��ѡ��һ����ȫ������Ϣ��");
		return false;
	}
	
	var tEdorMainSelNo = EdorGrid.getSelNo();
	if (tEdorMainSelNo==0) {
		alert("��ѡ��һ����ȫ������Ϣ��");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tEdorNo = EdorGrid.getRowColData(tEdorMainSelNo-1, 1);
	var tCancelReason = EdorGrid.getRowColData(tEdorMainSelNo-1, 6);
	
	if (tCancelReason=="") {
		alert("����ԭ����Ϊ�գ�");
		return false;
	}
	
	fm.Operate.value = "EDORMAINCANCEL";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo;
	submitForm(fm);
}

/**
 * ��ȫ��Ŀ����
 */
function edorItemCancel() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("��ѡ��һ����ȫ������Ϣ��");
		return false;
	}
	
	var tEdorMainSelNo = EdorGrid.getSelNo();
	if (tEdorMainSelNo==0) {
		alert("��ѡ��һ����ȫ������Ϣ��");
		return false;
	}
	
	var tEdorItemSelNo = EdorItemGrid.getSelNo();
	if (tEdorItemSelNo==0) {
		alert("��ѡ��һ����ȫ��Ŀ��Ϣ��");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tEdorNo = EdorGrid.getRowColData(tEdorMainSelNo-1, 1);
	var tEdorType = EdorItemGrid.getRowColData(tEdorItemSelNo-1, 1);
	var tCancelReason = EdorItemGrid.getRowColData(tEdorItemSelNo-1, 4);
	
	if (tCancelReason=="") {
		alert("����ԭ����Ϊ�գ�");
		return false;
	}
	
	fm.Operate.value = "EDORITEMCANCEL";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType;
	submitForm(fm);
}

/**
 * �ύ����
 */
function submitForm(obj) {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		if (fm.Operate.value == "EDORITEMCANCEL") {
			showEdorItemInfo();
		} else if (fm.Operate.value == "EDORMAINCANCEL") {
			showEdorInfo();
		} else {
			queryClick(2);
			initEdorGrid();
			initEdorItemGrid();
			divEdorMain.style.display = "none";
			divEdorItem.style.display = "none";
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
	
	return true;
}