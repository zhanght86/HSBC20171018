/***************************************************************
 * <p>ProName��LCGrpContListInput.jsp</p>
 * <p>Title��¼�빤����</p>
 * <p>Description��¼�빤����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-04-28
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯѯ����Ϣ
 */
function queryClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpUWListSql");
	tSQLInfo.setSqlId("LCGrpUWListSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	turnPage1.queryModal(tSQLInfo.getString(), QueryScanGrid, 1, 1);
	if (!turnPage1.strQueryResult && o==1) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}
function queryScanMyInfo() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpUWListSql");
	tSQLInfo.setSqlId("LCGrpUWListSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.queryModal(tSQLInfo.getString(), ScanMyInfoGrid, 1, 1);
}
/**
 * ����¼��
 */
function scanMyInfo() {
	var tRow = ScanMyInfoGrid.getSelNo();
	
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = ScanMyInfoGrid.getSelNo()-1;
	var tMissionID = ScanMyInfoGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = ScanMyInfoGrid.getRowColData(tSelNo, 2);
	var tActivityID = ScanMyInfoGrid.getRowColData(tSelNo, 3);
	var tContPlanType = ScanMyInfoGrid.getRowColData(tSelNo, 4);
	var tGrpPropNo = ScanMyInfoGrid.getRowColData(tSelNo, 6);
	window.location="./LCGrpUWInput.jsp?GrpPropNo="+ tGrpPropNo+"&ContPlanType="+tContPlanType
	+"&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag=0";
}

/**
 * ����
 */
function applyClick() {
	var tRow = QueryScanGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = QueryScanGrid.getSelNo()-1;
	var tMissionID = QueryScanGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = QueryScanGrid.getRowColData(tSelNo, 2);  
	var tActivityID = QueryScanGrid.getRowColData(tSelNo, 3);	
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=APPLY";
	submitForm(fm);
}

/**
 * �˻�
 */
function reApplyClick() {
	var tRow = ScanMyInfoGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = ScanMyInfoGrid.getSelNo()-1;
	var tMissionID = ScanMyInfoGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = ScanMyInfoGrid.getRowColData(tSelNo, 2);  
	var tActivityID = ScanMyInfoGrid.getRowColData(tSelNo, 3);
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=REAPPLY";
	submitForm(fm);
}

/**
 * �ύ����
 */
function submitForm(obj) {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
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
		queryClick(0);
		queryScanMyInfo();
	}	
}
