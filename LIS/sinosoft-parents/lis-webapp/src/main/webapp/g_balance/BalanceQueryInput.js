/***************************************************************
 * <p>ProName��BalanceQueryInput.js</p>
 * <p>Title�����㵥��ѯ</p>
 * <p>Description�����㵥��ѯ</p>
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
	
	initContInfoGrid();
	initPosInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceQuerySql");
	tSQLInfo.setSqlId("BalanceQuerySql1");
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.BalanceAllNo.value);
	tSQLInfo.addSubPara(fm.StartBalanceDate.value);
	tSQLInfo.addSubPara(fm.EndBalanceDate.value);
	tSQLInfo.addSubPara(fm.BalanceType.value);
	tSQLInfo.addSubPara(fm.PrintState.value);
	tSQLInfo.addSubPara(tManageCom);
	if(fm.BalanceState.value=="00"){
		tSQLInfo.addSubPara("00");
		tSQLInfo.addSubPara("");
	}else if(fm.BalanceState.value=="01"){
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("01");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.StartConfDate.value);
	tSQLInfo.addSubPara(fm.EndConfDate.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(tManageCom);
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
	var tEdorAppNo= ContInfoGrid.getRowColData(tRow,4);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceQuerySql");
	tSQLInfo.setSqlId("BalanceQuerySql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	turnPage2.queryModal(tSQLInfo.getString(), PosInfoGrid, 0, 1);
}
function balanceExport(){
	var tRow = ContInfoGrid.getSelNo()-1;
	if(tRow<0){
		alert("��ѡ��һ�����㵥��Ϣ!");
		return false;
	}
	//�������
	var tTitle = "�б�����^|������^|���㵥����^|��ȫ�����^|��ȫǩ������^|�ܱ�����(Ԫ)^|Ӧ��������";
	
	var tGrpContNo=ContInfoGrid.getRowColData(tRow,2);
	var tEdorAppNo= ContInfoGrid.getRowColData(tRow,4);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceQuerySql");
	tSQLInfo.setSqlId("BalanceQuerySql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	fm.submit();
}

/**
 * ��������
 */
function exportData(filetype) {

	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "�б�����^|������^|Ͷ��������^|���㵥����^|Ӧ��������^|ʵ�ʽ�������^|������(Ԫ)^|����ȷ������^|���㵥״̬^|��ӡ״̬^|��ӡ����^|�������^|���㱸ע";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceQuerySql");
	tSQLInfo.setSqlId("BalanceQuerySql1");
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.BalanceAllNo.value);
	tSQLInfo.addSubPara(fm.StartBalanceDate.value);
	tSQLInfo.addSubPara(fm.EndBalanceDate.value);
	tSQLInfo.addSubPara(fm.BalanceType.value);
	tSQLInfo.addSubPara(fm.PrintState.value);
	tSQLInfo.addSubPara(tManageCom);
	if(fm.BalanceState.value=="00"){
		tSQLInfo.addSubPara("00");
		tSQLInfo.addSubPara("");
	}else if(fm.BalanceState.value=="01"){
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("01");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.StartConfDate.value);
	tSQLInfo.addSubPara(fm.EndConfDate.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryResultExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL+"&FileType="+filetype+"&Prefix=BalanceQuery";
	
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
