/***************************************************************
 * <p>ProName��LSQuotPrintInput.js</p>
 * <p>Title�����۵���ӡ</p>
 * <p>Description�����۵���ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
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
function afterSubmit(FlagStr, content,cOfferListNo) {
	
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
		
		if (fm.Operate.value == "CREATE") {
			
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
		
		if (fm.Operate.value == "PRINT") {
			
			var tFileName = content.substring(content.lastIndexOf('/')+1,content.length); 
			var tFilePath = content;
			
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
		}
		document.all('divPrintCust').style.display = 'none';
		cleanPreCustomer();
		fm.OfferListNo.value = "";
		fm.NameBJ.value = "";
		fm.QuotNoBJ.value = "";
		fm.QuotTypeBJ.value = "";
		fm.QuotTypeBJName.value = "";
		fm.State.value = "";
		fm.StateName.value = "";
		queryQuotInfo('JS',cOfferListNo);
	}
}

/**
 * ��ѯ
 */
function queryClick(tObj) {
	
	//�жϵ�ǰ��¼���Ƿ���������Ŀѯ����Ա����
	var tSpecial = "0";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql11");
	tSQLInfo.addSubPara(tOperator);
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult[0][0] == "0") {
		tSpecial = "0";
	} else {
		tSpecial = "1";
	}
	
	var tComStirng= aginManage();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tComStirng);
	tSQLInfo.addSubPara(fm.Name.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(tCurrentDate);
	
	if (tSpecial == "0") {
		tSQLInfo.addSubPara("1");
	} else {
		tSQLInfo.addSubPara("");
	}
	
	initQuotInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), QuotInfoGrid, 2, 1);
	document.all('divPrintCust').style.display = 'none';
	cleanPreCustomer();
	if (tObj == "Page") {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ���ݵ�¼������ѯʹ�û���
 * ����¼�룺861302 ���أ�'861302','8613','86'
 */
function aginManage(){
	
	var tempManageCom = "";
	var tempCom = "";
	var tFlag = true;
	var tArr = [];
	tArr.push(tManageCom);
	tempManageCom = tManageCom;
	
	while(tFlag){
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql1");
		tSQLInfo.addSubPara(tempManageCom);
		
		var arrResult = easyExecSql(tSQLInfo.getString());
		
		if (arrResult==null || arrResult[0][0] == "") {
			tFlag = false;
		} else {
			
			tempCom = arrResult[0][0];
			if (checkArray(tArr,tempCom)) {
				tFlag = false;
			} else {
				tArr.push(tempCom);
				tempManageCom = tempCom;
			}
		}
	}
	//ƴ���ַ���
	var tFinalCom = "";
	for (var m=0;m<tArr.length;m++) {
		
		if (m == tArr.length -1) {
			tFinalCom += "'"+tArr[m]+"'";
		} else {
			tFinalCom += "'"+tArr[m]+"',";
		}
	}
	return tFinalCom;
}

/**
 * �ж�Ԫ���Ƿ���������
 */
function checkArray(arr,ch){
	
	var j = arr.length;
	while(j--){
		if (arr[j]===ch) {
			return true;
		}
	}
	return false;
}

/**
 * չʾ�ͻ���Ϣ
 */
function showPreCustomer() {
	
	document.all('divPrintCust').style.display = '';
	
	var tRow = QuotInfoGrid.getSelNo();
	var tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	var tQuotbatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	var tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	
	if (tQuotType == "00") {//һ��ѯ��
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql3");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotbatNo);
		
		var arrResult = easyExecSql(tSQLInfo.getString());
		fm.PreCustomerNo.value = arrResult[0][0];
		fm.PreCustomerName.value = arrResult[0][1];
		fm.IDType.value = arrResult[0][2];
		fm.IDTypeName.value = arrResult[0][3];
		fm.IDNo.value = arrResult[0][4];
		fm.GrpNature.value = arrResult[0][5];
		fm.GrpNatureName.value = arrResult[0][6];
		fm.BusiCategory.value = arrResult[0][7];
		fm.BusiCategoryName.value = arrResult[0][8];
		fm.SumNumPeople.value = arrResult[0][9];
		fm.PreSumPeople.value = arrResult[0][10];
		fm.Address.value = arrResult[0][11];
		
	} else if (tQuotType == "01") {//��Ŀѯ��
		
		cleanPreCustomer();
	}
}

/**
 * ��տͻ���Ϣ
 */
function cleanPreCustomer() {
		
	fm.PreCustomerNo.value = "";
	fm.PreCustomerName.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.IDNo.value = "";
	fm.GrpNature.value = "";
	fm.GrpNatureName.value = "";
	fm.BusiCategory.value = "";
	fm.BusiCategoryName.value = "";
	fm.SumNumPeople.value = "";
	fm.PreSumPeople.value = "";
	fm.Address.value = "";
}

/**
 * ��ѯ׼�ͻ���Ϣ
 */
function queryCustomer() {
	
	var tRow = QuotInfoGrid.getSelNo();
	var tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	var tQuotBatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	var tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	
	var strUrl = "LSQuotPrintQueryCustMain.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&QuotType="+tQuotType;
	window.open(strUrl,"׼�ͻ����Ʋ�ѯ",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}

/**
 * ��ѯ׼�ͻ���ϸ��Ϣ��չʾ
 */
function queryCustInfo() {
	
	var tPreCustomerNo = fm.PreCustomerNo.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql4");
	tSQLInfo.addSubPara(tPreCustomerNo);
	
	var tResult = easyExecSql(tSQLInfo.getString());
	
	fm.IDType.value = tResult[0][0];
	fm.IDTypeName.value = tResult[0][1];
	fm.IDNo.value = tResult[0][2];
	fm.GrpNature.value = tResult[0][3];
	fm.GrpNatureName.value = tResult[0][4];
	fm.BusiCategory.value = tResult[0][5];
	fm.BusiCategoryName.value = tResult[0][6];
	fm.SumNumPeople.value = tResult[0][7];
	fm.PreSumPeople.value = tResult[0][8];
	fm.Address.value = tResult[0][9];
}

/**
 * ���ɱ��۵�
 */
function createQuotation() {
	
	//���ɱ��۵�������3�������½���
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql8");
	tSQLInfo.addSubPara(tManageCom);
	
	var tComGradeArr = easyExecSql(tSQLInfo.getString());
	if (tComGradeArr==null) {
		alert("��ѯ�����ȼ���Ϣʧ�ܣ�");
		return false;
	} else {
		var tComGrade = tComGradeArr[0][0];
		if (tComGrade!="03") {
			alert("�ò���Ӧ����3�������½��У�");
			return false;
		}
	} 
	
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��ѯ����Ϣ��");
		return false;	
	}
	var tQuotState = QuotInfoGrid.getRowColData(tRow-1,6);
	if (tQuotState=="04" || tQuotState =="05") {
		
		alert("��ѯ���ѹ鵵,�������ɱ��۵�!");
		return false;	
	}
	
	var tPreCustomerNo = fm.PreCustomerNo.value;
	if (tPreCustomerNo == null || tPreCustomerNo == "") {
		alert("��ѡ��׼�ͻ����ƣ�");
		return false;
	}
	if(!confirm('ȷ��Ҫ���ɱ��۵���?')){
		return false;
	}
	
	var tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	var tQuotBatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	var tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	var tProdType = QuotInfoGrid.getRowColData(tRow-1,8);
	var tQuery_QuotNo = fm.Query_QuotNo.value;
	var tQuery_QuotBatNo = fm.Query_QuotBatNo.value;
	
	if (tQuotType == "00") {//һ��ѯ��
		
		if (tQuery_QuotNo != null && tQuery_QuotNo != "" && tQuery_QuotBatNo != null && tQuery_QuotBatNo != "") {
			if (tQuotNo != tQuery_QuotNo || tQuotBatNo != tQuery_QuotBatNo) {
				alert("��ѡѯ����Ϣ�ѱ䶯��������ѡ��׼�ͻ����ƣ�");
				return false;	
			}
		}
	}
	
	fm.Operate.value = "CREATE";
	fm.action = "./LSQuotPrintSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&QuotType="+tQuotType+"&ProdType="+tProdType;
	submitForm(fm);
}

/**
 * ��ѯ������Ϣ
 */
function queryQuotInfo(cObj,cOfferNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql5");
	if (cObj == "Page") {//ҳ���ѯʱ������ѯ����Ϣ
		
		tSQLInfo.addSubPara(fm.NameBJ.value);
		tSQLInfo.addSubPara(fm.QuotNoBJ.value);
		tSQLInfo.addSubPara(fm.QuotTypeBJ.value);
		tSQLInfo.addSubPara(fm.State.value);
		tSQLInfo.addSubPara(fm.OfferListNo.value);
		tSQLInfo.addSubPara(tOperator);
	} else if (cObj == "JS") {//���ɱ�����Ϣ��ˢ�²�ѯ������Ϣ
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(cOfferNo);
		tSQLInfo.addSubPara(tOperator);
	}
	
	initOfferListGrid();
	turnPage2.queryModal(tSQLInfo.getString(), OfferListGrid, 2, 1);
	
	if (cObj == "Page") {
		if (!turnPage2.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ���۵�����
 */
function downloadQuot(parm1) {
	
	var tRow = OfferListGrid.getSelNo();
	//var tOfferListNo = document.all(parm1).all("OfferListGrid1").value;
	var tPrintState = document.all(parm1).all("OfferListGrid8").value;
	if (tPrintState=="0"){
	
	} else {
		var tFileName = document.all(parm1).all("OfferListGrid11").value;
		var tFilePath = document.all(parm1).all("OfferListGrid12").value;
		window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
	}
}

/**
 * �鿴���۵�
 */
function seeQuotation() {
	
	var tRow = OfferListGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;	
	}
	
	var tOfferListNo = OfferListGrid.getRowColData(tRow-1,1);
	var tQuotNo = OfferListGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = OfferListGrid.getRowColData(tRow-1,3);
	var tQuotType = OfferListGrid.getRowColData(tRow-1,4);
	
	var tSrc = "";
	if (tQuotType == "00") {
		tSrc = "./LSQuotETSeeInput.jsp";
	} else if (tQuotType == "01") {
		tSrc = "./LSQuotProjSeeInput.jsp";
	}

	tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo+"&QuotType="+tQuotType+"&QuotQuery=N";
	location.href = tSrc;
}

/**
 * ��ϸ�鿴
 */
function toPalnDetailView() {
	
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��ѯ����Ϣ��");
		return false;	
	}
	tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	tQuotBatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	showPalnDetailView();
}

/**
 * ��ӡ���۵�
 */
function printOfferList(o) {
	
	var tRow = OfferListGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;	
	}
	var tState = OfferListGrid.getRowColData(tRow-1,8);
	if (tState=="1") {
		alert("���۵��Ѵ�ӡ�������ٴδ�ӡ��");
		return false;
	}
	var tOfferListNo = OfferListGrid.getRowColData(tRow-1,1);
	var tQuotNo = OfferListGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = OfferListGrid.getRowColData(tRow-1,3);
	var tQuotType = OfferListGrid.getRowColData(tRow-1,4);
	
	//У��ûѡ�񷽰�ʱ���ɴ�ӡ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql7");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tPlanCountArr = easyExecSql(tSQLInfo.getString());
	
	if (tPlanCountArr[0][0]==0) {
		alert("����ѡ�񱨼۷�����");
		return false;
	}
	
	//һ��ѯ���˻�����ʱ��У�鱨�۷�������Ҫ�������˻��������˻�
	var tTranProdType = getTranProdType(tQuotType,tQuotNo, tQuotBatNo);
	
	if (tQuotType=="00" && tTranProdType=="02") {

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql9");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tArr = easyExecSql(tSQLInfo.getString());
		if (tArr.length < 2) {
			alert("�˻�����ѡ���۷��������������˻��͸����˻��������ͣ�");
			return false;
		}
	}
	
	//У����Ŀѯ�۽�����ʱ���Ƿ���ѡ�񱨼۷���ʱά���˹�����ۻ򹤳����
	if (tQuotType=="01" && tTranProdType=="01") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql10");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tEnginArr = easyExecSql(tSQLInfo.getString());
		if (tEnginArr == null) {
			alert("����ѡ�񱨼۷�����");
			return false;
		} else {
			if (tEnginArr[0][0] == "2" && tEnginArr[0][1] == "-1") {
				alert("��ѡ���۷���δά��������ۣ�");
				return false;
			}
			if (tEnginArr[0][0] == "3" && tEnginArr[0][2] == "-1") {
				alert("��ѡ���۷���δά�����������");
				return false;
			}
		} 
	}
	
	var tPrintType = "";
	if (o == "pdf") {
		tPrintType = "pdf";
	} else if (o == "doc") {
		tPrintType = "doc";
	}
	fm.Operate.value = "PRINT";
	fm.action = "./LSQuotOfferPrintSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&QuotType="+tQuotType +"&ProdType="+tTranProdType+"&OfferListNo="+tOfferListNo+"&PrintType="+tPrintType;
	submitForm(fm);
}


/**
 * ��ȡ��Ʒ����
 */
function getTranProdType(cQuotType,cQuotNo, cQuotBatNo) {
	
	if (cQuotType=="00") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql8");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
	} else if (cQuotType=="01") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotProjPlanSql");
		tSQLInfo.setSqlId("LSQuotProjPlanSql1");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
	}
	
	var tProdArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tProdArr==null) {
	
	} else {
		return tProdArr[0][0];
	}
	
	return null;
}
