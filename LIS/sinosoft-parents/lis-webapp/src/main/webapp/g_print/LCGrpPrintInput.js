/***************************************************************
 * <p>ProName��LCGrpPrintInput.js</p>
 * <p>Title�����屣����ӡ</p>
 * <p>Description�����屣����ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-01
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
	
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content,filepath,tfileName) {
	
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
		var filepath1 =filepath+tfileName;
		window.location = "../common/jsp/download.jsp?FilePath="+filepath1+"&FileName="+tfileName;
		showInfo.focus();
	}
	fm.printButton.disabled=false;
	queryClick(0);
}

/**
 * ��ѯ
 */
function queryClick(tFlag) {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.SaleChnl.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult && tFlag==1) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ������ӡ
 */
function print() {
	
	var i = ContInfoGrid.getSelNo();
	if (i<1) {
		alert("��ѡ�񱣵���Ϣ��");
		return false;
	}
	var tManageCom = ContInfoGrid.getRowColData(i-1,1);
	var tGrpContNo = ContInfoGrid.getRowColData(i-1,2);
	var tGrpPropNo = ContInfoGrid.getRowColData(i-1,3);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	var tRiskCode = easyExecSql(tSQLInfo.getString(), 1, 0, 1, 1);
	
	if(tRiskCode!=null){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
		tSQLInfo.setSqlId("LCGrpPrintSql3");
		tSQLInfo.addSubPara(tGrpContNo);
		var tPrintCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tPrintCount=='1'){
			alert("�ñ����Ѵ�ӡ!");
			return false;
		}
	}
	mOperate = "PRINT";
	
	fm.action="./LCGrpPrintSave.jsp?GrpContNo="+tGrpContNo+"&GrpPropNo="+tGrpPropNo+"&ManageCom="+tManageCom;
	submitForm();
	fm.Operate.value = mOperate;
	fm.printButton.disabled=true;
	fm.submit(); //�ύ
}

/**
 * �������˸���ƾ֤��ӡ
 */
function insuredPrint() {
	
	var i = ContInfoGrid.getSelNo();
	if (i<1) {
		alert("��ѡ�񱣵���Ϣ��");
		return false;
	}
	
	var tManageCom = ContInfoGrid.getRowColData(i-1,1);
	var tGrpContNo = ContInfoGrid.getRowColData(i-1,2);
	var tGrpPropNo = ContInfoGrid.getRowColData(i-1,3);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	var tRiskCode = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tRiskCode == null){
		alert("��������Ʒ,������˲���!");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	var tPrintCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPrintCount==null){
		alert("���ȴ�ӡ����!");
		return false;
	}
	mOperate = "NJPRINT";
	
	fm.action="./LCGrpPrintSave.jsp?GrpContNo="+tGrpContNo+"&GrpPropNo="+tGrpPropNo+"&ManageCom="+tManageCom;
	submitForm();
	fm.Operate.value = mOperate;
	fm.submit(); //�ύ
}
