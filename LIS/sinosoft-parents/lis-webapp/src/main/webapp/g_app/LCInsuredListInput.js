/***************************************************************
 * <p>ProName��LCInsuredListSave.jsp</p>
 * <p>Title����Ա�嵥�����</p>
 * <p>Description����Ա�嵥�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �龰
 * @version  : 8.0
 * @date     : 2014-04-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯɨ���б���Ϣ
 */
function queryClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredListSql");
	tSQLInfo.setSqlId("LCInsuredListSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.EscanCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	initInsuredListGrid();
	turnPage1.queryModal(tSQLInfo.getString(), InsuredListGrid, 1, 1);
	if (!turnPage1.strQueryResult && o==1) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
	
}

/**
 * ��ѯ���˳��б���Ϣ
 */
function querySelfClick() {
	initSelfInsuredListGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredListSql");
	tSQLInfo.setSqlId("LCInsuredListSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.queryModal(tSQLInfo.getString(), SelfInsuredListGrid, 1, 1);
} 

/**
 * ����¼��
 */
function enterInsured() {
	var tRow = SelfInsuredListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = SelfInsuredListGrid.getSelNo()-1;
	var tMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 2);
	var tActivityID = SelfInsuredListGrid.getRowColData(tSelNo, 3);
	var tContPlanType = SelfInsuredListGrid.getRowColData(tSelNo, 4);
	var tGrpPropNo = SelfInsuredListGrid.getRowColData(tSelNo, 6);
	window.location = "./LCInsuredInfoInput.jsp?ContPlanType="+ tContPlanType +"&GrpPropNo="+ tGrpPropNo + "&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	
}

/**
 * ����
 */
function applyClick() {
	var tRow = InsuredListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = InsuredListGrid.getSelNo()-1;
	var tMissionID = InsuredListGrid.getRowColData(tSelNo, 1);
	var tActivityID = InsuredListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = InsuredListGrid.getRowColData(tSelNo, 2);  
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=APPLY";
	submitForm(fm);
}

/**
 * �˻�
 */
function reApplyClick() {
	var tRow = SelfInsuredListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = SelfInsuredListGrid.getSelNo()-1;
	var tMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 1);
	var tActivityID = SelfInsuredListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 2);  
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
 		querySelfClick();
	}	
}
