/***************************************************************
 * <p>ProName��EdorAuditDetailInput.js</p>
 * <p>Title����ȫ���</p>
 * <p>Description����ȫ���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ������Ϣ��ѯ
 */
function queryEdorAppInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAuditSql");
	tSQLInfo.setSqlId("EdorAuditSql1");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.AppMode.value = strQueryResult[0][1];
		fm.AppDate.value = strQueryResult[0][2];
		fm.ReceiveDate.value = strQueryResult[0][3];
		fm.AcceptDate.value = strQueryResult[0][4];
		fm.PolicyNo.value = strQueryResult[0][5];
		fm.HidGrpContNo.value = strQueryResult[0][5];
		fm.AppntName.value = strQueryResult[0][6];
		fm.ValDate.value = strQueryResult[0][7];
		fm.PayIntv.value = strQueryResult[0][8];
		fm.BanlanceFlag.value = strQueryResult[0][9];
		fm.AfterClmRule.value = strQueryResult[0][10];
	} else {
		alert("��ѯ��ȫ������Ϣʧ�ܣ�");
		return false;
	}
}

/**
 * ��ȫ��Ŀ��ѯ
 */
function queryEdorTypeInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAuditSql");
	tSQLInfo.setSqlId("EdorAuditSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorTypeGrid, 1, 1);
}

/**
 * ��ȫ��Ŀ��ϸ
 */
function detailClick() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ����ȫ��Ŀ��");
		return false;
	}
	
	var tGrpContNo = fm.HidGrpContNo.value;
	var tEdorType = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	
	window.open("./EdorTypeDetailMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&EdorType="+tEdorType,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content,patch,fileName1) {
	
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
		if("AUDITCONFIRM"==fm.Operate.value){
			returnClick();
		}
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
 * ���ͨ��
 */
function auditConfirm(){
	
	var tAuditConclu = fm.AuditConclu.value;
	var tAuditOpinion = fm.AuditOpinion.value;
	if(null==tAuditConclu || ""==tAuditConclu){
		alert("��ѡ����˽��� ��");
		return false;
	}
	if(null==tAuditOpinion || ""==tAuditOpinion){
		alert("��¼�������� ��");
		return false;
	}
	fm.Operate.value="AUDITCONFIRM";
	mOperate = "AUDITCONFIRM";
	var tGrpContNo = fm.HidGrpContNo.value;
	fm.action = "./EdorAuditDetailSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm);
}


/**
 * ����������ȫ��ѯ
 */
function showOldEdor(){
	
	var tGrpContNo = fm.PolicyNo.value;
		
	strUrl="../sysQuery/EdorQueryMain.jsp?GrpContNo="+tGrpContNo+"&Flag=Old" ;
	window.open(strUrl,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
