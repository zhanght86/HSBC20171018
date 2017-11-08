/***************************************************************
 * <p>ProName��LSQuotBranchFinalQueryInput.js</p>
 * <p>Title���ֹ�˾��������</p>
 * <p>Description���ֹ�˾��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �����ز�ѯ
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProcessSql");
	tSQLInfo.setSqlId("LSQuotProcessSql6");
	tSQLInfo.addSubPara(fm.QuotName.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndtDate.value);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuotGrid, 0,1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ���˳ز�ѯ
 */
function perQueryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProcessSql");
	tSQLInfo.setSqlId("LSQuotProcessSql7");
	tSQLInfo.addSubPara(fm.QuotName.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tOperator);
	
	turnPage2.queryModal(tSQLInfo.getString(), PerQuotGrid, 0,1);
}

/**
 * ���빫�����е����ݵ����˳�
 */
function appClick() {
	
	var tSelNo = QuotGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tMissionID = QuotGrid.getRowColData(tSelNo-1, 3);
	var tSubMissionID = QuotGrid.getRowColData(tSelNo-1, 4);
	var tActivityID = QuotGrid.getRowColData(tSelNo-1, 5);
	
	fm.action = "./LSQuotAppSave.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
		initForm();
	}
	
}

/**
 * ����ѯ��
 */
function GotoDetail() {
	
	var tSelNo = PerQuotGrid.getSelNo()-1;
	var tMissionID = PerQuotGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = PerQuotGrid.getRowColData(tSelNo, 4);
	var tActivityID = PerQuotGrid.getRowColData(tSelNo, 5);
	var tQuotNo = PerQuotGrid.getRowColData(tSelNo, 6);
	var tQuotBatNo = PerQuotGrid.getRowColData(tSelNo, 7);
	var tQuotType = PerQuotGrid.getRowColData(tSelNo, 8);
	
	if (tQuotType=="00") {
		location.href = "./LSQuotUWDetailInput.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	} else if (tQuotType=="01") {
		location.href = "./LSQuotProjUWDetailInput.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	}
}
