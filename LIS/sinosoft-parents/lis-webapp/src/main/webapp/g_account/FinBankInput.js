/***************************************************************
 * <p>ProName��FinBankInput.js</p>
 * <p>Title����������ά��</p>
 * <p>Description����������ά��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬

var tSQLInfo = new SqlClass();

/**
 * �ύ����
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
	fm.Operate.value = mOperate;
	fm.action = "./FinBankSave.jsp";
	fm.submit(); //�ύ
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
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
		
		if (mOperate!="DELETE") {
			queryClick();
			clearInfo();
		} else {
			resetClick();
		}
	}
}

/**
 * ��ѯ����
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinBankSql");
	tSQLInfo.setSqlId("FinBankSql1");
	tSQLInfo.addSubPara(fm.FinBankCode.value);
	tSQLInfo.addSubPara(fm.FinBankName.value);
	tSQLInfo.addSubPara(fm.FinBankClass.value);
	tSQLInfo.addSubPara(fm.AccNo.value);
	tSQLInfo.addSubPara(fm.FinBankNature.value);
	tSQLInfo.addSubPara(fm.FinComCode.value);
	tSQLInfo.addSubPara(fm.State.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinBankGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}

function showFinBankInfo() {
	
	var tSelNo = FinBankGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�������б���Ϣ��");
		return false;
	}
	
	fm.FinBankCode.disabled = true;
	fm.FinBankCode.value = FinBankGrid.getRowColData(tSelNo-1, 1);
	fm.FinBankName.value = FinBankGrid.getRowColData(tSelNo-1, 2);
	fm.AccNo.value = FinBankGrid.getRowColData(tSelNo-1, 3);
	fm.FinBankClass.value = FinBankGrid.getRowColData(tSelNo-1, 4);
	fm.FinBankClassName.value = FinBankGrid.getRowColData(tSelNo-1, 5);
	fm.FinBankNature.value = FinBankGrid.getRowColData(tSelNo-1, 6);
	fm.FinBankNatureName.value = FinBankGrid.getRowColData(tSelNo-1, 7);
	fm.FinComCode.value = FinBankGrid.getRowColData(tSelNo-1, 8);
	fm.FinComName.value = FinBankGrid.getRowColData(tSelNo-1, 9);
	fm.State.value = FinBankGrid.getRowColData(tSelNo-1, 10);
	fm.StateName.value = FinBankGrid.getRowColData(tSelNo-1, 11);
}

/**
 * ��������
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinBankCode.value = fm.FinBankCode.value;
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * �޸�����
 */
function updateClick() {
	
	var tSelNo = FinBankGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ�����������Ϣ��");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinBankCode.value = FinBankGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	submitForm();
}

/**
 * ɾ������
 */
function deleteClick() {
	
	var tSelNo = FinBankGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ�����������Ϣ��");
		return false;
	}
	
	if (!confirm("ȷ��Ҫɾ����������Ϣ��")) {
		return false
	}
	
	fm.HiddenFinBankCode.value = FinBankGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * ��������
 */
function resetClick() {
	
	clearInfo();
	initFinBankGrid();
}

/**
 * ���������
 */
function clearInfo() {
	
	fm.FinBankCode.disabled = false;
	fm.FinBankCode.value = "";
	fm.FinBankName.value = "";
	fm.AccNo.value = "";
	fm.FinBankClass.value = "";
	fm.FinBankClassName.value = "";
	fm.FinBankNature.value = "";
	fm.FinBankNatureName.value = "";
	fm.FinComCode.value = "";
	fm.FinComName.value = "";
	fm.State.value = "";
	fm.StateName.value = "";
}

/**
 * ��������
 */
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "���б���^|��������^|�����˺�^|���д������^|���д�������^|�˻����ʱ���^|�˻���������^|�����������^|�����������^|״̬����^|״̬";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinBankSql");
	tSQLInfo.setSqlId("FinBankSql1");
	tSQLInfo.addSubPara(fm.FinBankCode.value);
	tSQLInfo.addSubPara(fm.FinBankName.value);
	tSQLInfo.addSubPara(fm.FinBankClass.value);
	tSQLInfo.addSubPara(fm.AccNo.value);
	tSQLInfo.addSubPara(fm.FinBankNature.value);
	tSQLInfo.addSubPara(fm.FinComCode.value);
	tSQLInfo.addSubPara(fm.State.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
