/***************************************************************
 * <p>ProName��LSQuotPlanCombiInput.js</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-02
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


/**
 * ��ѯ�����б�
 */
function queryPlanList() {
	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPlanCombiSql");
	tSQLInfo.setSqlId("LSQuotPlanCombiSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	//turnPage2.pageLineNum=2;
	
	//PlanListGrid.mulLineCount = 2;
	//turnPage2.queryModal(tSQLInfo.getString(), PlanListGrid, 1, 1);
	
	if (!noDiv(turnPage2, PlanListGrid, tSQLInfo.getString())) {
		initPlanListGrid();
		return false;
	}
}


/**
 * ��ѯ������ķ������
 */
function queryNoPlanCombi() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPlanCombiSql");
	tSQLInfo.setSqlId("LSQuotPlanCombiSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), NoPlanCombiGrid, 1, 1);
//	var rowNum = turnPage1.queryAllRecordCount;//������
//	if (parseInt(rowNum,10) > 10) {
//		document.getElementById("divTurnPage").style.display = '';
//	} else {
//		document.getElementById("divTurnPage").style.display = 'none';
//	}
}

/**
 * ����
 */
function addClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	//�ж��Ƿ�ѡ���˷�������
	var chkFlag=false;
	for ( var i=0;i<PlanListGrid.mulLineCount; i++ ) {
	
		if( PlanListGrid.getChkNo(i)) {	
			chkFlag = true;
			break;
		}
	}
	if(!chkFlag) {
		alert("��ѡ�񷽰���Ϣ��");
		return false;
	}
	
	mOperate = "INSERT";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotPlanCombiSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tRow = NoPlanCombiGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	var tSerialNo = NoPlanCombiGrid.getRowColData(tRow-1,1);
	mOperate = "DELETE";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotPlanCombiSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&SerialNo="+ tSerialNo;
	submitForm();

}

/**
 * չʾ������ϸ
 */
function showPlanDetail(parm1) {
	
	 var tPlanCode = document.all(parm1).all("PlanListGrid1").value;
	 var tSysPlanCode = document.all(parm1).all("PlanListGrid3").value;
	 var tMark = "1";//ֻ��ѯһ�������ķ�����ϸ��Ϣ
	 var tActivityID = "";//ֻ��ѯһ������ʱ������Ϊ��
	 window.open("./LSQuotPlanAllDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode +"&Mark="+ tMark,"������ϸ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
