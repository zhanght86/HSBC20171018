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

function queryInfo() {
	
	initOutPayInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	tSQLInfo.setSqlId("LJOutPayOutSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), OutPayInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

function queryApplyInfo() {
	
	initOutPayOutInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	tSQLInfo.setSqlId("LJOutPayOutSql2");
	tSQLInfo.addSubPara(tOperator);
	turnPage2.queryModal(tSQLInfo.getString(), OutPayOutInfoGrid, 0, 1);
}

/**
 * �˷�����
 */
function applyClick() {
	
	document.all("ApplyClickButton").disabled = true;
	var tSelNo = OutPayInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		document.all("ApplyClickButton").disabled = false;
		alert("��ѡ��Ҫ����ļ�¼��");
		return false;
	}
	
	var tHeadBankCode = OutPayInfoGrid.getRowColData(tSelNo, 7);
	var tProvinceCode = OutPayInfoGrid.getRowColData(tSelNo, 9);
	var tCityCode = OutPayInfoGrid.getRowColData(tSelNo, 11);
	var tAccNo = OutPayInfoGrid.getRowColData(tSelNo, 14);
	var tAccName = OutPayInfoGrid.getRowColData(tSelNo, 15);
	
	if (tHeadBankCode==null || tHeadBankCode=="" || tProvinceCode==null || tProvinceCode=="" || tCityCode==null || tCityCode=="" || tAccNo==null || tAccNo=="" || tAccName==null || tAccName=="") {
		
		document.all("ApplyClickButton").disabled = false;
		alert("��ѡ��¼������Ϣ��ȫ�����ܽ��иò�����");
		return false;
	}
	
	tOperate = "APPLYCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJOutPayOutApplySave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * ��������
 */
function cancelSubmit() {
	
	document.all("CancelButton").disabled = true;
	var tSelNo = OutPayOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���г����ļ�¼��");
		return false;
	}
	
	var tApplyBatNo = OutPayOutInfoGrid.getRowColData(tSelNo, 1);
	
	tOperate = "CANCELSUBMIT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJOutPayOutApplySave.jsp?Operate="+ tOperate +"&ApplyBatNo="+ tApplyBatNo;
	submitForm(fm);
}

/**
 * �����ύ
 */
function applySubmit() {
	
	document.all("ApplyButton").disabled = true;
	var tSelNo = OutPayOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ύ�ļ�¼��");
		return false;
	}
	
	var tApplyBatNo = OutPayOutInfoGrid.getRowColData(tSelNo, 1);
	
	tOperate = "APPLYSUBMIT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJOutPayOutApplySave.jsp?Operate="+ tOperate +"&ApplyBatNo="+ tApplyBatNo;
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

/**
 * �ύ���غ���
 */
function dealAfterSubmit(cFlag) {

	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="APPLYCLICK") {
		
		document.all("ApplyClickButton").disabled = false;
		
		if (cFlag!='Fail') {
			
			initOutPayInfoGrid();
			queryApplyInfo();
		}
	} else if (tOperate=="APPLYSUBMIT") {
		
		document.all("ApplyButton").disabled = false;
		if (cFlag!='Fail') {
			queryApplyInfo();
		}
	} else if (tOperate=="CANCELSUBMIT") {
		
		document.all("CancelButton").disabled = false;
		if (cFlag!='Fail') {
			queryApplyInfo();
		}
	}
}
/**
 * ��������
 */
function expData() {
	
	fm.SheetName.value = "�����Ϣ�б�";
	
	var tTitle = "�������^|���屣����^|Ͷ��������^|��ɽ��(Ԫ)^|�ͻ���������^|�ͻ�����������ʡ^|�ͻ�������������^|�ͻ������˺�^|�ͻ��˻���";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	tSQLInfo.setSqlId("LJOutPayOutSql6");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
