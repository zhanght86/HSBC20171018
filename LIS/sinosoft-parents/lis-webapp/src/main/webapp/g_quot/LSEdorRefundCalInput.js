/***************************************************************
 * <p>ProName��LSEdorRefundCalInput.js</p>
 * <p>Title����ȫ�˷��㷨ά��</p>
 * <p>Description����ȫ�˷��㷨ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

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
		querySubmit('JS');
		
		if (mOperate == "DELETE") {
			
			if (RefundListGrid.mulLineCount == 0) {
				fm2.RiskCode1.value = "";
				fm2.RiskName1.value = "";
				fm2.GetType1.value = "";
				fm2.GetTypeName1.value = "";
			}
		}
	}
}

/**
 * ��ѯ�˷���Ϣ�б�
 */
function querySubmit(Obj) {
	
	clearRefundInfo();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSEdorRefundCalSql");
	tSQLInfo.setSqlId("LSEdorRefundCalSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm2.RiskCode1.value);
	tSQLInfo.addSubPara(fm2.GetType1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), RefundListGrid, 0, 1);
	
	if (Obj=='Page') {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	} 
}

/**
 * չʾ�˷���Ϣά����Ϣ
 */
function showRefundList() {
	
	var tRow = RefundListGrid.getSelNo();
	fm2.RiskCode2.value = RefundListGrid.getRowColData(tRow-1,1);
	fm2.RiskName2.value = RefundListGrid.getRowColData(tRow-1,2);
	fm2.GetType2.value = RefundListGrid.getRowColData(tRow-1,8);
	fm2.GetTypeName2.value = RefundListGrid.getRowColData(tRow-1,3);
	fm2.ValPeriod.value = RefundListGrid.getRowColData(tRow-1,9);
	fm2.ValPeriodName.value = RefundListGrid.getRowColData(tRow-1,6);
	fm2.ValStartDate.value = RefundListGrid.getRowColData(tRow-1,4);
	fm2.ValEndDate.value = RefundListGrid.getRowColData(tRow-1,5);
	fm2.FeeValue.value = RefundListGrid.getRowColData(tRow-1,7);
	
}

/**
 * ��ȫ�˷��㷨ά��--����
 */
function addSubmit() {
	
	mOperate = "INSERT";
	if (!verifyInput2()) {
		return false;
	}
	if (!checkBeforSubmit()) {
		return false;
	}
	if (!checkValPeriod()) {
		return false;
	}
	showPage();
	fm2.action = "./LSEdorRefundCalSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&ObjType="+ tObjType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * ��ȫ�˷��㷨ά��--�޸�
 */
function modSubmit() {
	
	mOperate = "UPDATE";
	var tRow = RefundListGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if (!verifyInput2()) {
		return false;
	}
	if (!checkBeforSubmit()) {
		return false;
	}
	if (!checkValPeriod()) {
		return false;
	}
	var tSerialNo = RefundListGrid.getRowColData(tRow-1,10);//��ˮ��
	fm2.SerialNo.value = tSerialNo;
	showPage();
	fm2.action = "./LSEdorRefundCalSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&ObjType="+ tObjType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * ��ȫ�˷��㷨ά��--ɾ��
 */
function delSubmit() {
	
	var tRow = RefundListGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	var tSerialNo = RefundListGrid.getRowColData(tRow-1,10);//��ˮ��
	fm2.SerialNo.value = tSerialNo;
	
	mOperate = "DELETE";
	fm2.action = "./LSEdorRefundCalSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&ObjType="+ tObjType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * �ύǰУ��
 */
function checkBeforSubmit() {
	
	var tValStartDate = fm2.ValStartDate.value;
	var tValEndDate = fm2.ValEndDate.value;
	var tFeeValue = fm2.FeeValue.value;
	
	if (Number(tValStartDate)<0) {
		alert("��ʼֵӦ���ڵ���0��");
		return false;
	}
	if (Number(tValEndDate)<0) {
		alert("��ֵֹӦ���ڵ���0��");
		return false;
	}
	if (Number(tValStartDate)>=Number(tValEndDate)) {
		alert("��ʼֵӦС����ֵֹ��");
		return false;
	}
	if (Number(tFeeValue)<0) {
		alert("���ñ���Ӧ���ڵ���0��");
		return false;
	}
	if (Number(tFeeValue)>1) {
		alert("���ñ���ӦС�ڵ���1��");
		return false;
	}
	return true;
}


/**
 * У�鵥λ��ͬһ�����£�����ѡͬһ�ֵ�λ
 */
function checkValPeriod() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSEdorRefundCalSql");
	tSQLInfo.setSqlId("LSEdorRefundCalSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm2.RiskCode2.value);
	
	var tNo = "";
	if (mOperate == "INSERT") {
		tSQLInfo.addSubPara(tNo);
	} else if (mOperate == "UPDATE") {
		var tRow = RefundListGrid.getSelNo();
		tNo = RefundListGrid.getRowColData(tRow-1,10);//��ˮ��
		tSQLInfo.addSubPara(tNo);
	}
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult !== null && arrResult !== "") {
		var tValPeriod = arrResult[0][0];
		if (tValPeriod != fm2.ValPeriod.value) {
			alert("��ͬһ��Ʒ����ά��ʱ������ѡ��ͬһ�ֵ�λ!");
			return false;
		}
	}
	return true;
}

/**
 * ����˷���Ϣά����Ϣ
 */
function clearRefundInfo() {
	
	fm2.RiskCode2.value = "";
	fm2.RiskName2.value = "";
	fm2.GetType2.value = "";
	fm2.GetTypeName2.value = "";
	fm2.ValPeriod.value = "";
	fm2.ValPeriodName.value = "";
	fm2.ValStartDate.value = "";
	fm2.ValEndDate.value = "";
	fm2.FeeValue.value = "";
}

/**
 * �������޸ĺ����չʾ
 */
function showPage() {
	
	if (fm2.RiskCode1.value!=null && fm2.RiskCode1.value != "") {
		fm2.RiskCode1.value = fm2.RiskCode2.value;
		fm2.RiskName1.value = fm2.RiskName2.value;
	}
	if (fm2.GetType1.value!=null && fm2.GetType1.value != "") {
		fm2.GetType1.value = fm2.GetType2.value;
		fm2.GetTypeName1.value = fm2.GetTypeName2.value;
	}
}

/**
 * ��ѯϵͳĬ���˷���Ϣά��
 */
function querySysRefundInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSEdorRefundCalSql");
	tSQLInfo.setSqlId("LSEdorRefundCalSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), SysRefundInfoGrid, 0, 1);
	
}
