/***************************************************************
 * <p>ProName��LJGetManualApplyInput.js</p>
 * <p>Title: �ֶ���������</p>
 * <p>Description���ֶ���������</p>
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
function queryInfo(o) {
	
	initApplyBatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql1");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(fm.QueryBatchNo.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), ApplyBatchInfoGrid, 0, 1);
	if (o=="0") {
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * �������κ�
 */
function applyClick() {

	if (tComGrade!="03") {
		alert("������֧�����½��иò�����");
		return false;
	}
	
	fm.QueryBatchNo.value = "";
	fm.QueryApplyStartDate.value = "";
	fm.QueryApplyEndDate.value = "";
	
	tOperate = "APPLYCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualApplySave.jsp?Operate="+ tOperate;
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
		
		dealAfterSubmit(cBatchNo);
	}
}

function dealAfterSubmit(cBatchNo) {
	
	fm.QueryBatchNo.value = cBatchNo;
	queryInfo('1');
	ApplyBatchInfoGrid.radioSel(1);
	enterClick();
}

function enterClick() {
	
	var tSelNo = ApplyBatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		return;
	}
	var tAppCom = ApplyBatchInfoGrid.getRowColData(tSelNo, 1);
	var tBatchNo = ApplyBatchInfoGrid.getRowColData(tSelNo, 3);
	var tAppDate = ApplyBatchInfoGrid.getRowColData(tSelNo, 4);
	
	location.href = "./LJGetManualAppSubInput.jsp?AppCom="+ tAppCom +"&ApplyBatchNo="+ tBatchNo +"&AppDate="+ tAppDate;
}
