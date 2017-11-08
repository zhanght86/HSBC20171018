/***************************************************************
 * <p>ProName��LCContPlanListSave.jsp</p>
 * <p>Title��Ͷ������¼�������</p>
 * <p>Description��Ͷ������¼�������</p>
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
function queryPlanClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanListSql");
	tSQLInfo.setSqlId("LCContPlanListSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.EscanCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	
	initContPlanListGrid();
	turnPage1.queryModal(tSQLInfo.getString(), ContPlanListGrid, 1, 1);
	if (!turnPage1.strQueryResult && o==1) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
	
}

/**
 * ��ѯɨ���б���Ϣ
 */
function querySelfPlanClick() {
	initSelfContPlanListGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanListSql");
	tSQLInfo.setSqlId("LCContPlanListSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.queryModal(tSQLInfo.getString(), SelfContPlanListGrid, 1, 1);
} 

/**
 * ���뷽��¼��
 */
function enterContPlan() {
	var tRow = SelfContPlanListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = SelfContPlanListGrid.getSelNo()-1;
	var tMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 2);
	var tActivityID = SelfContPlanListGrid.getRowColData(tSelNo, 3);
	var tContPlanType = SelfContPlanListGrid.getRowColData(tSelNo, 4);
	var tGrpPropNo = SelfContPlanListGrid.getRowColData(tSelNo, 6);
	window.location = "./LCContPlanTradInput.jsp?ContPlanType="+ tContPlanType +"&GrpPropNo="+ tGrpPropNo + "&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
}


/**
 * ����
 */
function applyClick() {
	var tRow = ContPlanListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = ContPlanListGrid.getSelNo()-1;
	var tMissionID = ContPlanListGrid.getRowColData(tSelNo, 1);
	var tActivityID = ContPlanListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = ContPlanListGrid.getRowColData(tSelNo, 2);  
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=APPLY";
	submitForm(fm);
}

/**
 * �˻�
 */
function reApplyClick() {
	var tRow = SelfContPlanListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tSelNo = SelfContPlanListGrid.getSelNo()-1;
	var tMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 1);
	var tActivityID = SelfContPlanListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 2);  
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
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	    var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		queryPlanClick(0);
 		querySelfPlanClick();
	}	
}
