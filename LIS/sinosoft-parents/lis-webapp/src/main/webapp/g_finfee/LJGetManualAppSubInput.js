/***************************************************************
 * <p>ProName��LJGetManualAppSubInput.js</p>
 * <p>Title: �ֶ�����������ϸ</p>
 * <p>Description���ֶ�����������ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

/**
 * ��ѯ
 */
function queryInfo(o) {
	
	initGetInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql3");
	tSQLInfo.addSubPara(tAppCom);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInsuranceComName.value);
	tSQLInfo.addSubPara(fm.QueryAppntName.value);
	
	
	turnPage1.queryModal(tSQLInfo.getString(), GetInfoGrid, 0, 1);
	if (o=="0") {
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

function queryInitInfo() {
	
	if (tApplyBatchNo==null || tApplyBatchNo=="") {
		alert("�������κ�Ϊ�գ�");
		backClick();
		return;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql2");
	tSQLInfo.addSubPara(tApplyBatchNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		
		fm.ApplyMoney.value = "0";
		fm.ApplyCount.value = "0";
	} else {
		
		fm.ApplyMoney.value = tArr[0][0];
		fm.ApplyCount.value = tArr[0][1];
	}
}


function queryApplyInfo() {

	if (tApplyBatchNo==null || tApplyBatchNo=="") {
		
		alert("�������κ�Ϊ�գ�");
		backClick();
		return;
	}
	
	initApplyGetInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql4");
	tSQLInfo.addSubPara(tApplyBatchNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), ApplyGetInfoGrid, 0, 1);
}

/**
 * ѡ��
 */
function chooseClick() {
	
	document.all("ChooseButton").disabled = true;
	if (GetInfoGrid.getChkCount()==0) {
		
		document.all("ChooseButton").disabled = false;
		alert("��ѡ���������Ϣ���ݣ�");
		return false;
	}
	
	if (isEmpty(fm.GetDealType)) {
		
		document.all("ChooseButton").disabled = false;
		alert("��ѡ����ʽ��");
		return false;
	}
	
	tOperate = "CHOOSECLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate +"&AppCom="+ tAppCom;
	submitForm(fm);
}

/**
 * ����
 */
function cancelClick() {
	
	document.all("CancelButton").disabled = true;
	if (ApplyGetInfoGrid.getChkCount()==0) {
		
		document.all("CancelButton").disabled = false;
		alert("��ѡ��Ҫ���������ݣ�");
		return false;
	}
	
	tOperate = "CANCELCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * �޸�����
 */
function modifyBankClick() {
	
	document.all("ModifyButton").disabled = true;
	if (ApplyGetInfoGrid.getChkCount()==0) {
		
		document.all("ModifyButton").disabled = false;
		alert("��ѡ��Ҫ�޸�������Ϣ�����ݣ�");
		return false;
	}

	tOperate = "MODIFYBANK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

function confirmClick() {
	
	document.all("ConfirmButton").disabled = true;
	if (ApplyGetInfoGrid.mullineCount==0) {
		
		document.all("ConfirmButton").disabled = false;
		alert("û�п��ύ�����ݣ�");
		return false;
	}
	
	/*
	for (var i=0; i<ApplyGetInfoGrid.mulLineCount; i++) {
			
		if (ApplyGetInfoGrid.getChkNo(i)) {
			
			var tBankCode = ApplyGetInfoGrid.getRowColData(i, 15);
			if (tBankCode==null || tBankCode=="") {
			
				document.all("ConfirmButton").disabled = false;
				alert("��ѡ��"+ (i+1) +"��������Ϣ��ȫ�����Ƚ����޸ģ�");
				return false;
			}
		}
	}*/
	
	tOperate = "CONFIRM";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
function afterSubmit(FlagStr, content, cBatchNo) {
	
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
	dealAfterSubmit(FlagStr, cBatchNo);
}

function dealAfterSubmit(FlagStr, cBatchNo) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	
	if (tOperate=="CHOOSECLICK") {
		
		document.all("ChooseButton").disabled = false;
		queryInfo("1");
		queryApplyInfo();
	} else if (tOperate=="CANCELCLICK") {
		
		document.all("CancelButton").disabled = false;
		queryApplyInfo();
	} else if (tOperate=="MODIFYBANK") {
		
		document.all("ModifyButton").disabled = false;
		queryApplyInfo();
	} else if (tOperate=="CONFIRM") {
		backClick();
	} else if (tOperate=="PRINT") {
		
		if ((FlagStr=="Succ")) {
			window.location = "../common/jsp/download.jsp?FilePath="+ cBatchNo +"&FileName=yjtf.pdf";
		}
	}
}

function backClick() {
	
	location.href = "./LJGetManualApplyInput.jsp";
}

/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	if (value1=='finbusstype') {
		
		var tSql = "finbusstypeget";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	}
}

/**
 * �˷�֪ͨ���ӡ
 */
function printClick() {
	
	if (ApplyGetInfoGrid.getChkCount()==0) {
		
		alert("��ѡ��Ҫ��ӡ������˷Ѽ�¼��");
		return false;
	}
	
	if (ApplyGetInfoGrid.getChkCount()>1) {
		alert("�ô�ӡ��֧�ֵ�����ӡ��");
		return false;
	}
	
	var tTransNo = "";
	for (var i=0; i<ApplyGetInfoGrid.mulLineCount; i++) {
			
		if (ApplyGetInfoGrid.getChkNo(i)) {
			
			var tBussType = ApplyGetInfoGrid.getRowColData(i, 8);
			tTransNo = ApplyGetInfoGrid.getRowColData(i, 1);
			if (tBussType!="06") {
			
				alert("��ѡ��¼��������˷����ݣ�");
				return false;
			}
		}
	}
	
	tOperate = "PRINT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualPrintSave.jsp?Operate="+ tOperate +"&TransNo="+ tTransNo;
	submitForm(fm);
}
