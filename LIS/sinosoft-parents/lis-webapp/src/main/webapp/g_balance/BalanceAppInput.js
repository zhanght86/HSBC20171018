/***************************************************************
 * <p>ProName��BalanceAppInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
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
var turnPage3 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	initPosInfoGrid();
	initContInfoGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceAppSql");
	tSQLInfo.setSqlId("BalanceAppSql1");
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.StartBalanceDate.value);
	tSQLInfo.addSubPara(fm.EndBalanceDate.value);
	tSQLInfo.addSubPara(fm.BalancePeriod.value);
	tSQLInfo.addSubPara(tManageCom);
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
		initForm();
		fm.SumMoney.value='';
		fm.SelectPosMoney.value='';
	}
	
}
/**
*չʾ��ȫ��Ϣ
*/
function showPosInfo(){
	initPosInfoGrid();
	DivPosInfo.style.display='';
	var tRow = ContInfoGrid.getSelNo()-1;
	fm.SumMoney.value=ContInfoGrid.getRowColData(tRow,7);
	var tGrpContNo=ContInfoGrid.getRowColData(tRow,2);
	fm.GrpContNoQ.value=tGrpContNo;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceAppSql");
	tSQLInfo.setSqlId("BalanceAppSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tGrpContNo);
	noDiv(turnPage2, PosInfoGrid, tSQLInfo.getString());
	//turnPage2.queryModal(tSQLInfo.getString(), PosInfoGrid, 0, 1);
}

/**
չʾ��ѯ���,��ֵ��Muline
**/
function showMulLineInfo(tResultStr, objGrid ,objPage){
	objPage.strQueryResult = tResultStr;
	if(objPage.strQueryResult==null||objPage.strQueryResult=="") {
		
		//initExeTrendsGrid();
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
	
	objPage.decodeEasyQueryResult(objPage.strQueryResult,'0');
	objPage.useSimulation = 1;
	objPage.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult, 0, 0, objPage);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	
	objPage.pageDisplayGrid = objGrid;
	//objGrid.SortPage = objPage;��ַҳ��ʶ
	//���ò�ѯ��ʼλ��
	objPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	var arrDataSet = turnPage1.getData(objPage.arrDataCacheSet, objPage.pageIndex, 10);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, objPage.pageDisplayGrid, objPage);
	objGrid.setPageMark(objPage);
	 
	return true;
	
}


function showMoney(){
	var rowNum = PosInfoGrid.mulLineCount;
	var vMoney=0;
	for(var i=0;i < rowNum;i++){
		if(PosInfoGrid.getChkNo(i)){
			var vpay = new Number(PosInfoGrid.getRowColData(i,5));
			vMoney=Arithmetic(vMoney,'+',vpay,2);
		}
	}
	fm.SelectPosMoney.value=vMoney;
}
/**
*�ֹ�����
*/
function handDeal(){
	var kRow = ContInfoGrid.getSelNo()-1;
	var tGrpContNo=ContInfoGrid.getRowColData(kRow,2);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceAppSql");
	tSQLInfo.setSqlId("BalanceAppSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry>0){
		alert("�ñ����»���δ�����Ľ��㵥!");
		return false;
	}	
	var rowNum = PosInfoGrid.mulLineCount ;
	var tRow = 0;
	for(var index=0;index<rowNum;index++){
		if(PosInfoGrid.getChkNo(index)){
			tRow=1;
		}
	}
	if(tRow==0){
		alert("������ѡ��һ����¼!");
		return false;
	}	
	
	fm.action = "./BalanceAppSave.jsp?Operate=INSERT";
	submitForm(fm,"INSERT");
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
