/***************************************************************
 * <p>ProName��LJTempFeeOutConfirmInput.js</p>
 * <p>Title�������˷����</p>
 * <p>Description�������˷����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
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
	
	dealAfterSubmit(FlagStr);
}

function dealAfterSubmit(o) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="CONFIRMSUBMIT") {
		
		fm.ConfirmSubmitButton.disabled = false;
		if (o!="Fail") {
			
			initTempFeeOutInfoGrid();
			fm.ConfirmConclusion.value = "";
			fm.ConfirmConclusionName.value = "";
			fm.ConfirmDesc.value = "";
		}
	}
}

function queryInfo() {
	
	initTempFeeOutInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql3");
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
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);

	turnPage2.queryModal(tSQLInfo.getString(), TempFeeOutInfoGrid, 0, 1);
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

function confirmSubmit() {
	
	fm.ConfirmSubmitButton.disabled = true;
	if (!checkConfirm()) {
		fm.ConfirmSubmitButton.disabled = false;
		return false;
	}
	
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���в��������ݣ�");
		return false;
	}
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	tOperate = "CONFIRMSUBMIT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutConfirmSave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}

function checkConfirm() {
	
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���в��������ݣ�");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	
	var tConfirmConclusion = fm.ConfirmConclusion.value;
	var tConfirmDesc = fm.ConfirmDesc.value;
	
	if (tConfirmConclusion==null || tConfirmConclusion=="") {
		alert("��˽��۲���Ϊ�գ�");
		return false;
	}
	
	if (tConfirmConclusion=="01") {
		
		if (tConfirmDesc==null || tConfirmDesc=="") {
			alert("��˽���Ϊ��ͨ��ʱ��������������Ϊ�գ�");
			return false;
		}
	}
	
	if (tConfirmDesc.length>300) {
		alert("��������Ӧ��300�����ڣ�");
		return false;
	}
	
	return true;
}

function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=='infinbank') {
		
		var tSql = "1 and a.fincomcode in (select b.findb from ldcom b where b.comcode like #"+ tManageCom +"%#)";
		if (returnType=='0') {
			return showCodeList('infinbank',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('infinbank',value2,value3,null,tSql,'1','1',null);
		}
	}
}
