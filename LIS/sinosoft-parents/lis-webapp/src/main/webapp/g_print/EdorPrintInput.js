/***************************************************************
 * <p>ProName��EdorPrintInput.jsp</p>
 * <p>Title��������ӡ</p>
 * <p>Description��������ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-09-20
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �ύ�����水ť��Ӧ����
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
	fm.submit(); //�ύ
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content, patch, fileName1) {
	
	if (typeof(showInfo)=="object"&& typeof(showInfo)!="unknown") {
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
	
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
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
		}
	}	
}

/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}

/**
 * ��ӡ
 */
function print() {
	
	if(!beforeSubmit()){
		return false;
	}
	var tSelNo = QueryResultGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����ȫ������Ϣ");
		return false;
	}
	var tEdorAppNo = QueryResultGrid.getRowColData(tSelNo,2);
	mOperate = "PRINT";
	fm.action="./EdorPrintSave.jsp?Operate="+mOperate+"&EdorAppNo="+tEdorAppNo;
	submitForm();
}

/**
 * ��ѯ
 */
function queryClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.EdorPrintSql");
	tSQLInfo.setSqlId("EdorPrintSql1");
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.AppntNo.value);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.AcceptDATE.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.SaleChnl.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.EdorType.value);
	turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 1, 1);
	
	if(o=='1'){
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
** �ļ�����
**/
function downFile(patch,fileName1){
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
}