/***************************************************************
 * <p>ProName��LJTempFeeOutApplyInput.js</p>
 * <p>Title�������˷�����</p>
 * <p>Description�������˷�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;


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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	   var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	dealAfterSubmit(FlagStr);
}


function dealAfterSubmit(o) {

	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	
	if (tOperate=="APPLYCLICK") {
		
		document.getElementById("ApplyClickButton").disabled = false;
		
		if (o!="Fail") {
		
			initTempFeeInfoGrid();
			queryApplyedInfo();
		}
	} else if (tOperate=="CANCELSUBMIT") {
		
		document.getElementById("CancelSubmitButton").disabled = false;
		
		if (o!="Fail") {
		
			queryApplyedInfo();
		}
	} else if (tOperate=="APPLYSUBMIT") {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		
		if (o!="Fail") {
		
			queryApplyedInfo();
		}
	} else if (tOperate=="MODIFYBANK") {
		
		document.getElementById("ModifyBankButton").disabled = false;
		
		if (o!="Fail") {
		
			queryApplyedInfo();
		}
	}
}

/**
 * ��ѯ���շ�����
 */
function queryInfo() {

	initTempFeeInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryInBankCode.value);
	tSQLInfo.addSubPara(fm.QueryInBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);

	turnPage1.queryModal(tSQLInfo.getString(), TempFeeInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ��ѯ����������շ�����
 */
function queryApplyedInfo() {
	
	initTempFeeOutInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql2");
	tSQLInfo.addSubPara(tOperator);
	
	turnPage2.queryModal(tSQLInfo.getString(), TempFeeOutInfoGrid, 0, 1);
}

/**
 * �˷�����
 */
function applyClick() {
	
	tOperate = "APPLYCLICK";
	document.getElementById("ApplyClickButton").disabled = true;
	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		document.getElementById("ApplyClickButton").disabled = false;
		alert("��ѡ��Ҫ����ļ�¼��");
		return false;
	}
	
	var tBussNo = TempFeeInfoGrid.getRowColData(tSelNo, 3);
	var tMoney = TempFeeInfoGrid.getRowColData(tSelNo, 6);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo +"&Money="+ tMoney;
	submitForm(fm);
}

/**
 * �����ύ
 */
function applySubmit() {

	tOperate = "APPLYSUBMIT";
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	document.getElementById("ApplySubmitButton").disabled = true;
	if (tSelNo<0) {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		alert("��ѡ��Ҫ�ύ�ļ�¼��");
		return false;
	}
	
	/*modify by songsz 20150120 ���θô�У��Ϊ��̨У��
	var tProvince = TempFeeOutInfoGrid.getRowColData(tSelNo, 11);
	var tCity = TempFeeOutInfoGrid.getRowColData(tSelNo, 13);
	if (tProvince==null || tProvince=="" || tCity==null || tCity=="") {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		alert("������Ϣ��ȫ�����Ƚ���������Ϣ�޸ģ�");
		return false;
	}*/
	
	
	var tBankCode = TempFeeOutInfoGrid.getRowColData(tSelNo, 23);
	if (tBankCode==null || tBankCode=="") {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		alert("������Ϣ��ȫ�����Ƚ���������Ϣ�޸ģ�");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}

/**
 * ��������
 */
function cancelSubmit() {
	
	tOperate = "CANCELSUBMIT";
	document.getElementById("CancelSubmitButton").disabled = true;
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		document.getElementById("CancelSubmitButton").disabled = false;
		alert("��ѡ��Ҫ�ύ�ļ�¼��");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}

function modifyBankInfo() {
	
	tOperate = "MODIFYBANK";
	document.getElementById("ModifyBankButton").disabled = true;
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		document.getElementById("ModifyBankButton").disabled = false;
		alert("��ѡ��Ҫ�ύ�ļ�¼��");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}


/**
 * ��������
 */
function expData() {
	
	fm.SheetName.value = "���շ���Ϣ�б�";
	
	var tTitle = "�������^|�ݽ��Ѻ�^|���ѷ�ʽ^|���^|��������^|�ͻ���������^|�ͻ������˺�^|�ͻ��˻���" +
			"^|���λ^|֧Ʊ��^|�տ�����^|�տ��˺�";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql5");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryInBankCode.value);
	tSQLInfo.addSubPara(fm.QueryInBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
