/***************************************************************
 * <p>ProName��LSQuotQueryAllCalInput.js</p>
 * <p>Title�����ܼ����ѯ</p>
 * <p>Description�����ܼ����ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-08-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
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
	}
}

/**
 * ��ѯ����������Ϣ�б�
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.CValiDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	
	initExpCalTotalGrid();
	initExpMonthCMCalGrid();
	initExpCMCalGrid();
	turnPage1.queryModal(tSQLInfo.getString(), ExpCalTotalGrid, 0, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ��ѯ��ϸ��Ϣ
 */
function showDetail() {
	
	var tSelNo = ExpCalTotalGrid.getSelNo();
	var tPolicyNo = ExpCalTotalGrid.getRowColData(tSelNo-1, 1);
	
	queryExpMonthCMCal(tPolicyNo);
	queryExpCMCal(tPolicyNo);
}

/**
 * ��ѯ���½᰸������б�
 */
function queryExpMonthCMCal(cPolicyNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(cPolicyNo);
	
	initExpMonthCMCalGrid();
	turnPage2.queryModal(tSQLInfo.getString(), ExpMonthCMCalGrid, 0, 1);
}

/**
 * ��ѯ���½᰸������б�
 */
function queryExpCMCal(cPolicyNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(cPolicyNo);

	initExpCMCalGrid();
	turnPage3.queryModal(tSQLInfo.getString(), ExpCMCalGrid, 0, 1);
}

/**
 * ����
 */
function expAllInfo() {
	
	fm.SheetName[0].value = "����������Ϣ";
	fm.SheetName[1].value = "���½᰸�����";
	fm.SheetName[2].value = "Ԥ���⸶��";
	
	//����������Ϣ�б�
	var tTitle1 = "������^|�ͻ�����^|��Ч����^|��Чֹ��^|����Լ����^|����Լ����^|ҽ���ձ���/�ܱ���^|"
			+ "����ͬ�ڳ�������^|����ͬ�ڱ���^|����ͬ��ҽ�������^|����ͬ�ڷ�ҽ�������^|����ͬ���⸶��^|"
			+ "��ֹĿǰ����������᰸����ƽ��ʱ����^|��ֹĿǰ��������^|��ֹĿǰ����^|��ֹĿǰҽ�������^|��ֹĿǰ��ҽ�������^|��ֹĿǰ�⸶��";

	//���½᰸������б�
	var tTitle2 = "������^|�ͻ�����^|��Ч����^|��Чֹ��^|��N����^|��N�������";
	
	//Ԥ���⸶���б�
	var tTitle3 = "������^|�ͻ�����^|��Ч����^|��Чֹ��^|��������^|����^|δ�����^|�᰸���^|���ֽ᰸�⸶��^|����Ԥ���⸶��"; 
	
	fm.SheetTitle[0].value = tTitle1;
	fm.SheetTitle[1].value = tTitle2;
	fm.SheetTitle[2].value = tTitle3;
	
	//����������Ϣ�б�
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	fm.SheetSql[0].value = tSQLInfo.getString();
	
	//���½᰸������б�
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	fm.SheetSql[1].value = tSQLInfo.getString();
	
	//Ԥ���⸶���б�
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql6");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	fm.SheetSql[2].value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
