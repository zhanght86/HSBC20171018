/***************************************************************
 * <p>ProName��EdorInputQueryInput.js</p>
 * <p>Title����ȫ¼��</p>
 * <p>Description����ȫ¼��</p>
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
 * �����ز�ѯ
 */
function queryClick(QueryFlag) {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql3");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.AcceptOperator.value);
	tSQLInfo.addSubPara(fm.AcceptStartDate.value);
	tSQLInfo.addSubPara(fm.AcceptEndtDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), PublicGrid, 1, 1);
	
	if (QueryFlag==1) {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ���˳ز�ѯ
 */
function PersonalQuery() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql4");
	tSQLInfo.addSubPara(tOperator);
	
	turnPage2.queryModal(tSQLInfo.getString(), PersonalGrid, 1, 1);
}

/**
 * ���빫�����е����񵽸��˳�
 */
function appClick() {
	
	var tSelNo = PublicGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tMissionID = PublicGrid.getRowColData(tSelNo-1, 1);
	var tSubMissionID = PublicGrid.getRowColData(tSelNo-1, 2);
	var tActivityID = PublicGrid.getRowColData(tSelNo-1, 3);
	var tEdorAppNo = PublicGrid.getRowColData(tSelNo-1, 5);
	var tEdorNo = PublicGrid.getRowColData(tSelNo-1, 6);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql21");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult!=null) {
		
		if (strQueryResult[0][0]=="1") {
			alert("���ȴ����ñ�ȫ������ǰһ��������");
			return false;
		}
	}
	
	fm.action = "./EdorMissionSave.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&DefaultOperator="+tOperator+"&Operate=APPLY";
	submitForm();
}

/**
 * �����˳��е������˻ص�������
 */
function returnClick() {
	
	var tSelNo = PersonalGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tMissionID = PersonalGrid.getRowColData(tSelNo-1, 1);
	var tSubMissionID = PersonalGrid.getRowColData(tSelNo-1, 2);
	var tActivityID = PersonalGrid.getRowColData(tSelNo-1, 3);
	
	fm.action = "./EdorMissionSave.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate=REAPPLY";
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
	var iWidth=550;      //�������ڵĿ���; 
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
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	initForm();
	queryClick(2);
}

/**
 * ������˳�������
 */
function GotoDetail() {
	
	var tSelNo = PersonalGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tMissionID = PersonalGrid.getRowColData(tSelNo-1, 1);
	var tSubMissionID = PersonalGrid.getRowColData(tSelNo-1, 2);
	var tActivityID = PersonalGrid.getRowColData(tSelNo-1, 3);
	var tEdorAppNo = PersonalGrid.getRowColData(tSelNo-1, 5);
	var tEdorNo = PersonalGrid.getRowColData(tSelNo-1, 6);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql21");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult!=null) {
		
		if (strQueryResult[0][0]=="1") {
			alert("���ȴ����ñ�ȫ������ǰһ��������");
			return false;
		}
	}
	
	location.href = "./EdorInputDetailInput.jsp?MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&EdorAppNo="+ tEdorAppNo +"&EdorNo="+ tEdorNo;
	//var strUrl = "./EdorESMain.jsp?Pages=./EdorInputDetailInput.jsp&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&EdorAppNo="+ tEdorAppNo +"&EdorNo="+ tEdorNo +"&ScanFlag=1&BussNo="+tEdorAppNo+"&BussType=G_POS&SubType=22001";
	//window.open(strUrl,'EdorESMain','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}