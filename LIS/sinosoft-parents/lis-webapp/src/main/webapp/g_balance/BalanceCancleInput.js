/***************************************************************
 * <p>ProName��BalanceCancleInput.js</p>
 * <p>Title�����㵥����</p>
 * <p>Description�����㵥����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceCancleSql");
	tSQLInfo.setSqlId("BalanceCancleSql1");
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.BalanceAllNo.value);
	tSQLInfo.addSubPara(fm.StartBalanceDate.value);
	tSQLInfo.addSubPara(fm.EndBalanceDate.value);
	tSQLInfo.addSubPara(fm.BalanceType.value);
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 0, 1);
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ�����������Ĳ�ѯ���!");
		return false;
	}
}



/**
 * �ύ
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
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
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
	}
	initForm();
}
/**
*չʾ��ȫ��Ϣ
*/
function showPosInfo(){
	DivPosInfo.style.display='';
	var tRow = ContInfoGrid.getSelNo()-1;
	var tGrpContNo=ContInfoGrid.getRowColData(tRow,2);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceCancleSql");
	tSQLInfo.setSqlId("BalanceCancleSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	noDiv(turnPage2, PosInfoGrid, tSQLInfo.getString());
	//turnPage2.queryModal(tSQLInfo.getString(), PosInfoGrid, 0, 1);
}


/**
 * ����ҳ����
 */
function noDiv(objPage, objGrid, tSql) {
	
	//Ϊ����������ĳ����ݴ������
	objPage = new turnPageClass();
	objPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!objPage.strQueryResult) {
		//���MULTILINE��ʹ�÷�����MULTILINEʹ��˵��
		objGrid.clearData();
		return false;
	}
	
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	objPage.arrDataCacheSet = clearArrayElements(objPage.arrDataCacheSet);
	//��ջ���
	objPage.allArrDataCacheSet = clearArrayElements(objPage.allArrDataCacheSet);
	objPage.allCacheSize = 0;
	//��ѯ�ɹ������ַ��������ض�ά����
	objPage.arrDataCacheSet = decodeEasyQueryResult(objPage.strQueryResult, 0, 0, objPage);
	objPage.pageLineNum = objPage.queryAllRecordCount;
	var tKey = 1;
	//cTurnPage.allCacheSize ++;
	objPage.allArrDataCacheSet[objPage.allCacheSize%objPage.allArrCacheSize] = {id:tKey,value:objPage.arrDataCacheSet};
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	objPage.pageDisplayGrid = objGrid;
	//����SQL���
	objPage.strQuerySql = tSql;
	//���ò�ѯ��ʼλ��
	objPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	var arrDataSet = objPage.getData(objPage.arrDataCacheSet, objPage.pageIndex, objPage.pageLineNum);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, objPage.pageDisplayGrid, objPage);
	if (objPage.showTurnPageDiv==1) {
		try {
			objGrid.setPageMark(objPage);
		} catch(ex){}
	}
	
	return true;
}
