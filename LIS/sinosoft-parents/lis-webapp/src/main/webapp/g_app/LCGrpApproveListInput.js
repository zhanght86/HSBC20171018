/***************************************************************
 * <p>ProName��LCGrpApproveListSave.jsp</p>
 * <p>Title�����������</p>
 * <p>Description�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-04-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage3 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯɨ���б���Ϣ
 */
function queryClick(o) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpApproveListSql");
	tSQLInfo.setSqlId("LCGrpApproveListSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	//tSQLInfo.addSubPara(tManageCom);//�ֹ�˾��֧��˾ֻ�ܲ��������ձ������ܹ�˾�����������ͱ���
	initGrpApproveListGrid();
	turnPage1.queryModal(tSQLInfo.getString(), GrpApproveListGrid, 1, 1);
	if (!turnPage1.strQueryResult&& o==1) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}

}

/**
 * ��ѯ���˳��б���Ϣ
 */
function querySelfClick() {
	initSelfGrpApproveListGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpApproveListSql");
	tSQLInfo.setSqlId("LCGrpApproveListSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.queryModal(tSQLInfo.getString(), SelfGrpApproveListGrid, 1, 1);
}

/**
 * ����¼��
 */
function enterGrpApprove() {
	var tRow = SelfGrpApproveListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;
	}
	var tSelNo = SelfGrpApproveListGrid.getSelNo()-1;
	var tMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 2);
	var tActivityID = SelfGrpApproveListGrid.getRowColData(tSelNo, 3);
	var tContPlanType = SelfGrpApproveListGrid.getRowColData(tSelNo, 4);
	var tGrpPropNo = SelfGrpApproveListGrid.getRowColData(tSelNo, 6);
	var tBussNo = SelfGrpApproveListGrid.getRowColData(tSelNo, 12);

		//window.location="./LCGrpContPolInput.jsp?GrpPropNo="+ tGrpPropNo+"&ContPlanType="+tContPlanType
	//+"&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag=1";
	var strUrl="./LCGrpContPolESMain.jsp?GrpPropNo="+ tGrpPropNo+"&ContPlanType="+tContPlanType
	+"&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag=1&ScanFlag=1&BussNo="+tBussNo+"&BussType=G_NB&SubType=21001,21002,21003,21004,21005,21006,21007,21008,21009";

	window.open(strUrl,'LCGrpContPolESMain','width='+screen.availWidth+',height='+screen.availHeight+',channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * ����
 */
function applyClick() {
	var tRow = GrpApproveListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;
	}
	var tSelNo = GrpApproveListGrid.getSelNo()-1;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpApproveListSql");
	tSQLInfo.setSqlId("LCGrpApproveListSql3");
	tSQLInfo.addSubPara(GrpApproveListGrid.getRowColData(tSelNo, 5));

	var topera = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if(tOperator == topera){
		alert("������Ա�������µ�¼����Ա��ͬһ��,�����������Ա!");
		return false;
	}
	var tMissionID = GrpApproveListGrid.getRowColData(tSelNo, 1);
	var tActivityID = GrpApproveListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = GrpApproveListGrid.getRowColData(tSelNo, 2);
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=APPLY";
	submitForm(fm);
}

/**
 * �˻�
 */
function reApplyClick() {
	var tRow = SelfGrpApproveListGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;
	}
	var tSelNo = SelfGrpApproveListGrid.getSelNo()-1;
	var tMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 1);
	var tActivityID = SelfGrpApproveListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 2);
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		 var showInfo =  window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		 showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo =    window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		 showInfo.focus();
		queryClick(0);
 		querySelfClick();
	}
}
