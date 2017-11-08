/***************************************************************
 * <p>ProName��LSQuotETQueryInput.js</p>
 * <p>Title��һ��ѯ��¼���ѯ</p>
 * <p>Description��һ��ѯ��¼���ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ��¼��ѯ����Ϣ
 */
function queryETClick(cFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql1");
	if (cFlag == '1') {//ҳ���ѯ
		
		tSQLInfo.addSubPara(fm.ETPreCustomerName.value);
		tSQLInfo.addSubPara(fm.ETQuotNo.value);
		tSQLInfo.addSubPara(tOperator);
	} else if (cFlag == '2') {//��������������չʾ
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.ETQuotNo.value);
		tSQLInfo.addSubPara(tOperator);
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.PreCustomerNo.value);
	
	initETQuotInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), ETQuotInfoGrid, 0, 1);
	if (cFlag == '1') {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}


/**
 * �����µ�ѯ��
 */
function applyETClick() {
	
	fm.ETPreCustomerName.value = "";
	fm.ETQuotNo.value = "";
	
	//���뻷��У��Ӧ����3�������½���
	if (tComGrade!="03") {
		alert("�ò���Ӧ����3�������½��У�");
		return false;
	}
	
	fmPub.Operate.value = "APPLYET";
	fm.action = "./LSQuotETQuerySave.jsp?Operate=APPLYET&QuotType="+tETQuotType;
	submitForm(fm);
}

/**
 * ��ѯ�����ѯ����Ϣ
 */
function queryFinishClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql3");
	tSQLInfo.addSubPara(fm1.FPreCustomerName.value);
	tSQLInfo.addSubPara(fm1.FQuotNo.value);
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	
	initFinishQuotInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(), FinishQuotInfoGrid, 1, 1);
	
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * �ٴ�ѯ��
 */
function againETClick() {
	
	fm1.FPreCustomerName.value = "";
	fm1.FQuotNo.value = "";
	
	var tRow = FinishQuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ�������ѯ����Ϣ��");
		return false;	
	}
	tQuotNo = FinishQuotInfoGrid.getRowColData(tRow-1,3);
	tQuotBatNo = FinishQuotInfoGrid.getRowColData(tRow-1,4);
	
	fmPub.Operate.value = "AGAINET";
	fm1.action = "./LSQuotAgainApplySave.jsp?Operate=AGAINET&QuotType=00&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo;
	submitForm(fm1);
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
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content, cQuotNo, cQuotBatNo) {
	
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
		
		dealAfterSubmit(cQuotNo, cQuotBatNo);
	}	
}

function dealAfterSubmit(cQuotNo, cQuotBatNo) {

	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=='APPLYET'||tOperate=='AGAINET') {
		
		if (!confirm("�Ƿ����ڽ���ѯ��¼�룿")) {
			fm.ETQuotNo.value = cQuotNo;
			queryETClick('2');
			return false;
		} else {
			fm.ETQuotNo.value = cQuotNo;
			queryETClick('1');
			//ETQuotInfoGrid.radioSel(1);
			enterQuot();
			fm.ETQuotNo.value =="";
			return true;
		}
	} 
}

/**
 * ����ѯ��
 */
function enterQuot() {
	
	tSelNo = ETQuotInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		tSelNo = 0;
	}
	tMissionID = ETQuotInfoGrid.getRowColData(tSelNo, 3);
	tSubMissionID = ETQuotInfoGrid.getRowColData(tSelNo, 4);
	tActivityID = ETQuotInfoGrid.getRowColData(tSelNo, 5);
	tQuotNo = ETQuotInfoGrid.getRowColData(tSelNo, 6);
	tQuotBatNo = ETQuotInfoGrid.getRowColData(tSelNo, 7);
	tQuotType = ETQuotInfoGrid.getRowColData(tSelNo, 8);
	
	goToStep(1);
}

/**
 * ��ϸ�鿴
 */
function toPalnDetailView() {
	
	var tRow = FinishQuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ�������ѯ����Ϣ��");
		return false;	
	}
	tQuotNo = FinishQuotInfoGrid.getRowColData(tRow-1,3);
	tQuotBatNo = FinishQuotInfoGrid.getRowColData(tRow-1,4);
	tQuotType = "00";
	showPalnDetailView();
}
