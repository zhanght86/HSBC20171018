/***************************************************************
 * <p>ProName��EdorSignQueryInput.js</p>
 * <p>Title����ȫǩ��</p>
 * <p>Description����ȫǩ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ȫ������Ϣ��ѯ
 */
function queryClick(QueryFlag) {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql11");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.ScanOperator.value);
	tSQLInfo.addSubPara(fm.ScanStartDate.value);
	tSQLInfo.addSubPara(fm.ScanEndtDate.value);
	tSQLInfo.addSubPara(fm.AcceptOperator.value);
	tSQLInfo.addSubPara(fm.AcceptStartDate.value);
	tSQLInfo.addSubPara(fm.AcceptEndtDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorAppGrid, 1, 1);
	
	if (QueryFlag==1) {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ��ȫ������Ϣ��ѯ
 */
function showEdorInfo() {
	
	var tSelNo = EdorAppGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo-1, 2);
	
	divEdorGrid.style.display = "";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql12");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), EdorGrid, 1, 1);
}

/**
 * �ύ
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
	fm.submit();
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content, patch, fileName1) {
	
	if (typeof(showInfo)=="object") {
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
		
		if(mOperate=="PRINT"){
			downFile(patch,fileName1);
		}else {
			initForm();
			divEdorGrid.style.display = "none";
			queryClick(2);
		}
	}
}

/**
 * ��ȫǩ��
 */
function edorSign() {
	
	var tSelNo = EdorAppGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tSelNo = EdorAppGrid.getSelNo()-1;
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo, 2);
	var tGrpContNo = EdorAppGrid.getRowColData(tSelNo, 3);
	
	fm.Operate.value="EDORSIGN";
	mOperate = "EDORSIGN";
	fm.action = "./EdorSignSave.jsp?EdorAppNo="+ tEdorAppNo +"&GrpContNo="+ tGrpContNo;
	submitForm(fm);
}

/**
 * ��ӡ
 */
function printY() {
	
	var tSelNo = EdorAppGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����ȫ������Ϣ");
		return false;
	}
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo,2);
	
	mOperate = "PRINT";
	fm.action="../g_print/EdorPrintSave.jsp?Operate="+mOperate+"&EdorAppNo="+tEdorAppNo;
	submitForm();
}

/**
** �ļ�����
**/
function downFile(patch,fileName1){
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
}
