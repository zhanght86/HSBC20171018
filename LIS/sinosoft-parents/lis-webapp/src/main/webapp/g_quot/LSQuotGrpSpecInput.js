/***************************************************************
 * <p>ProName��LSQuotGrpSpecInput.js</p>
 * <p>Title���ر�Լ��</p>
 * <p>Description���ر�Լ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-01
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


/**
 * ��ѯ�ѱ����ر�Լ��
 */
function quryGrpSpec() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotGrpSpecSql");
	tSQLInfo.setSqlId("LSQuotGrpSpecSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult !== null && arrResult !== "") {
		fm.GrpSpec.value = arrResult[0][0];
	}
}

/**
 * ��ʼ����ѯ�ر�Լ����׼��Ϣ
 */
function queryGrpSpecInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotGrpSpecSql");
	tSQLInfo.setSqlId("LSQuotGrpSpecSql1");
	tSQLInfo.addSubPara("1");
	
	//turnPage1.queryModal(tSQLInfo.getString(), GrpSpecInfoGrid, 2);
	if (!noDiv(turnPage1, GrpSpecInfoGrid, tSQLInfo.getString())) {
		initGrpSpecInfoGrid();
		return false;
	}
}


/**
 * ѡ��ť
 */
function selectClick() {
	
	//�ж��Ƿ�ѡ���˱�׼�ر�Լ����Ϣ
	var chkFlag=false;
	for( var i=0;i<GrpSpecInfoGrid.mulLineCount; i++ ) {
	
		if( GrpSpecInfoGrid.getChkNo(i)) {	
			
			chkFlag = true;
			break;
		}
	}
	if(!chkFlag) {
		alert("��ѡ���׼�ر�Լ����Ϣ��");
		return false;
	}
	//��ȡѡ����Ϣ
	var tGrpSpecApp = "";
	for( var mRow=0;mRow<GrpSpecInfoGrid.mulLineCount; mRow++ ) {
		if( GrpSpecInfoGrid.getChkNo(mRow)) {	
			var tGrpSpec   = GrpSpecInfoGrid.getRowColData(mRow , 1); 
			tGrpSpecApp += tGrpSpec+"��";
		}
	}
	var tempGrpSpec = fm.GrpSpec.value;//ҳ�浱ǰ��Ϣ
	fm.GrpSpec.value = tGrpSpecApp + tempGrpSpec;
	GrpSpecInfoGrid.checkBoxAllNot();//������й�ѡ
	
}

/**
 * ����
 */
function saveClick() {
	
	var tGrpSpec = fm.GrpSpec.value;
	if (tGrpSpec == null || tGrpSpec == "") {
		alert("��ѡ���׼�ر�Լ����Ϣ��");
		return false;
	}
	if (parseInt(tGrpSpec.length,10) >1500) {
		alert("�ر�Լ����ϢӦС��1500�֣�");
		return false;
	}
	mOperate = "SAVE";
	submitForm();
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
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotGrpSpecSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	fm.submit();
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
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
	}
}

