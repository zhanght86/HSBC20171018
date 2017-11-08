/***************************************************************
 * <p>ProName��LJGetManualConfirmSubInput.js</p>
 * <p>Title: �ֶ�����ȷ����ϸ</p>
 * <p>Description���ֶ�����ȷ����ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tOperate;

/**
 * ��ѯ
 */
function queryInfo() {
	
	initApplyGetInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql7");
	tSQLInfo.addSubPara(tApplyBatchNo);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInsuranceComName.value);
	tSQLInfo.addSubPara(fm.QueryAppntName.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), ApplyGetInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
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
	tSQLInfo.setSqlId("LJGetManualSql6");
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

function confirmClick() {
	
	document.all("ConfirmButton").disabled = true;
	
	if (!checkInfo()) {
		
		document.all("ConfirmButton").disabled = false;
		return false;
	}
	
	var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
	var tTransNo = ApplyGetInfoGrid.getRowColData(tSelNo, 1);
	var tGetDealType = ApplyGetInfoGrid.getRowColData(tSelNo, 2);
	
	tOperate = "CONFIRM";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualConfirmSubSave.jsp?Operate="+ tOperate +"&TransNo="+ tTransNo +"&GetDealType="+ tGetDealType;
	submitForm(fm);
}

function checkInfo() {

	var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("��ѡ��Ҫ���в��������ݣ�");
		return false;
	}
	
	var tConfirmConclusion = fm.ConfirmConclusion.value;
	if (tConfirmConclusion==null || tConfirmConclusion=="") {
		alert("���ѽ��۲���Ϊ�գ�");
		return false;
	}
	
	if (tConfirmConclusion=="00") {
		
		var tGetDealType = ApplyGetInfoGrid.getRowColData(tSelNo, 2);
		if (tGetDealType=="00") { 
			
			if (isEmpty(fm.GetType)) {
				alert("���ʽ����Ϊ�գ�");
				return false;
			}
			
			if (isEmpty(fm.OutBankCode)) {
				alert("�������в���Ϊ�գ�");
				return false;
			}
			
			if (isEmpty(fm.EnterAccDate)) {
				alert("�������ڲ���Ϊ�գ�");
				return false;
			}
			
			if (compareDate(tCurrentDate,fm.EnterAccDate.value)==2) {
				alert("�������ڲ��ܴ��ڵ�ǰ���ڣ�");
				return false;
			}
		}
	} else {
	
		var tConfirmDesc = fm.ConfirmDesc.value;
		if (tConfirmDesc==null ||tConfirmDesc=='' || tConfirmDesc.length>100) {
			alert("������������Ϊ���ҳ���Ӧ��100�ֳ��ڣ�");
			return false;
		}
	}
	
	
	return true;
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
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px")
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

function dealAfterSubmit(cSuccFlag, cBatchNo) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	
	if (tOperate=="CONFIRM") {
		
		document.all("ConfirmButton").disabled = false;
		
		if (cSuccFlag!='Fail') {
			
			queryInitInfo();
			initApplyGetInfoGrid();
			
			document.all("tr1").style.display = "none";
			document.all("tr2").style.display = "none";
			document.all("tr3").style.display = "none";
			
			fm.ConfirmConclusion.value = "";
			fm.ConfirmConclusionName.value = "";
			fm.ConfirmDesc.value = "";
			fm.GetType.value = "";
			fm.GetTypeName.value = "";
			fm.OutBankCode.value = "";
			fm.OutBankName.value = "";
			fm.OutBankAccNo.value = "";
			fm.EnterAccDate.value = "";
		}
	} else if (tOperate=="PRINT") {
		
		if ((cSuccFlag=="Succ")) {
			window.location = "../common/jsp/download.jsp?FilePath="+ cBatchNo +"&FileName=yjtf.pdf";
		}
	}
}

function backClick() {
	
	location.href = "./LJGetManualConfirmInput.jsp";
}


function afterCodeSelect(cCodeType, cField) {
	
	if (cCodeType=="succflag") {
		
		if (cField.value=="00") {//�ɹ�
			
			document.all("tr1").style.display = "none";
			
			var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
			if (tSelNo<0) {
				return;
			}
			var tGetDealType = ApplyGetInfoGrid.getRowColData(tSelNo, 2);
			if (tGetDealType=="01") {
				
				document.all("tr2").style.display = "none";
				document.all("tr3").style.display = "none";
				
				fm.GetType.value = "";
				fm.GetTypeName.value = "";
				fm.OutBankCode.value = "";
				fm.OutBankName.value = "";
				fm.OutBankAccNo.value = "";
				fm.EnterAccDate.value = "";
				fm.ConfirmDesc.value = "";
			} else {
				
				document.all("tr2").style.display = "";
				document.all("tr3").style.display = "";
				fm.GetType.value = "";
			}
		} else {//ʧ��
			
			document.all("tr1").style.display = "";
			document.all("tr2").style.display = "none";
			document.all("tr3").style.display = "none";

			fm.GetType.value = "";
			fm.GetTypeName.value = "";
			fm.OutBankCode.value = "";
			fm.OutBankName.value = "";
			fm.OutBankAccNo.value = "";
			fm.EnterAccDate.value = "";
		}
	}
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
		
		var tSql = "1 and codetype=#finbusstype# and codeexp=#get#";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='outfinbank') {
		
		var tSql = "1 and a.fincomcode in (select b.findb from ldcom b where b.comcode like #"+ tManageCom +"%#)";
		if (returnType=='0') {
			return showCodeList('outfinbank',value2,value3,null,tSql,'and 1','1',null);
		} else {
			return showCodeListKey('outfinbank',value2,value3,null,tSql,'and 1','1',null);
		}
	}
}

/**
 * ѡ��󽫽��涯̬չʾ���ʼ��
 */
function chooseClick() {
	
	document.all("tr1").style.display = "none";
	document.all("tr2").style.display = "none";
	document.all("tr3").style.display = "none";
	
	fm.ConfirmConclusion.value = "";
	fm.ConfirmConclusionName.value = "";
	fm.ConfirmDesc.value = "";
	fm.GetType.value = "";
	fm.GetTypeName.value = "";
	fm.OutBankCode.value = "";
	fm.OutBankName.value = "";
	fm.OutBankAccNo.value = "";
	fm.EnterAccDate.value = "";
}

/**
 * �˷�֪ͨ���ӡ
 */
function printClick() {

	var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ��ӡ������˷����ݣ�");
		return false;
	}
	
	var tTransNo = ApplyGetInfoGrid.getRowColData(tSelNo, 1);
	var tBussType = ApplyGetInfoGrid.getRowColData(tSelNo, 8);
	if (tBussType!="06") {
		alert("��ѡ��¼��������˷����ݣ�");
		return false;
	}
	
	tOperate = "PRINT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualPrintSave.jsp?Operate="+ tOperate +"&TransNo="+ tTransNo;
	submitForm(fm);
}
