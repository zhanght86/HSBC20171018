/***************************************************************
 * <p>ProName��FinAccountInput.js</p>
 * <p>Title����ƿ�Ŀά������</p>
 * <p>Description��ά���������漰�Ļ�ƿ�Ŀ</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ʯȫ��
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
	fm.action = "./FinAccountSave.jsp";
	fm.submit(); //�ύ
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
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
 * ��ѯ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccountSql");
	tSQLInfo.setSqlId("FinAccountSql1");
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(fm.FinName.value);
	tSQLInfo.addSubPara(fm.FinType.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinAccountGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}

/**
 * ��ʾ��ƿ�Ŀ��Ϣ
 */
function showFinAccountInfo() {
	
	var tSelNo = FinAccountGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�������б���Ϣ��");
		return false;
	}
	
	fm.FinCode.disabled = true;
	fm.FinCode.value = FinAccountGrid.getRowColData(tSelNo-1, 1);
	fm.FinName.value = FinAccountGrid.getRowColData(tSelNo-1, 2);
	fm.FinType.value = FinAccountGrid.getRowColData(tSelNo-1, 3);
	fm.FinTypeName.value = FinAccountGrid.getRowColData(tSelNo-1, 4);
	fm.Remark.value = FinAccountGrid.getRowColData(tSelNo-1, 5);
}

/**
 * ����
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinCode.value = fm.FinCode.value;
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * �޸�
 */
function updateClick() {
	
	var tSelNo = FinAccountGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ����ƿ�Ŀ��Ϣ��");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinCode.value = FinAccountGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tSelNo = FinAccountGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ����ƿ�Ŀ��Ϣ��");
		return false;
	}
	
	if (!confirm("ȷ��Ҫɾ���û�ƿ�Ŀ��")) {
		return false
	}
	
	fm.HiddenFinCode.value = FinAccountGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * ����
 */
function resetClick() {
	
	clearInfo();
	initFinAccountGrid();
}

/**
 * ���������
 */
function clearInfo() {
	
	fm.FinCode.disabled = false;
	fm.FinCode.value = "";
	fm.FinName.value = "";
	fm.FinType.value = "";
	fm.FinTypeName.value = "";
	fm.Remark.value = "";
}

/**
 * ��������
 */
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "��ƿ�Ŀ����^|��ƿ�Ŀ����^|��ƿ�Ŀ���ͱ���^|��ƿ�Ŀ����^|��ע^|¼����^|¼������^|����޸Ĳ�����^|����޸�����";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccountSql");
	tSQLInfo.setSqlId("FinAccountSql1");
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(fm.FinName.value);
	tSQLInfo.addSubPara(fm.FinType.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
