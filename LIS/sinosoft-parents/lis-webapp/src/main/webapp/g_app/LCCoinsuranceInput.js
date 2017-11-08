/***************************************************************
 * <p>ProName��LCCoinsuranceInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-03
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryCoinsuranceInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCCoinsuranceSql");
	tSQLInfo.setSqlId("LCCoinsuranceSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), CoinsuranceGrid, 1, 1);
}


/**
 * չʾ��ϸ��Ϣ
 */
function showCoinInfo() {
	
	var tRow = CoinsuranceGrid.getSelNo();
	fm.MasterSlaveFlag.value = CoinsuranceGrid.getRowColData(tRow-1,2);
	fm.MasterSlaveName.value = CoinsuranceGrid.getRowColData(tRow-1,3);
	fm.CoinComCode.value = CoinsuranceGrid.getRowColData(tRow-1,4);
	fm.CoinComName.value = CoinsuranceGrid.getRowColData(tRow-1,5);
	fm.AmntShareRate.value = CoinsuranceGrid.getRowColData(tRow-1,6);
	fm.PremShareRate.value = CoinsuranceGrid.getRowColData(tRow-1,7);
	
}

/**
 * ����
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!verifyInput2()) {
		return false;
	}
	
	if (!checkSubmit()) {
		return false;
	}
	
	fm.action = "./LCCoinsuranceSave.jsp?Operate="+ mOperate +"&GrpContNo="+tGrpContNo;
	submitForm();
}

/**
 * �޸�
 */
function modifyClick() {
	
	mOperate = "UPDATE";
	var tRow = CoinsuranceGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if (!verifyInput2()) {
		return false;
	}
	if (!checkSubmit()) {
		return false;
	}
	var tSerialNo = CoinsuranceGrid.getRowColData(tRow-1,1);//��ˮ��
	
	fm.action = "./LCCoinsuranceSave.jsp?Operate="+ mOperate +"&GrpContNo="+tGrpContNo +"&SerialNo="+ tSerialNo;
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tRow = CoinsuranceGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	var tSerialNo = CoinsuranceGrid.getRowColData(tRow-1,1);//��ˮ��
	mOperate = "DELETE";
	fm.action = "./LCCoinsuranceSave.jsp?Operate="+ mOperate +"&GrpContNo="+tGrpContNo +"&SerialNo="+ tSerialNo;
	submitForm();

}

/**
 * �ύǰУ��
 */
function checkSubmit() {
	
	var tAmntShareRate = fm.AmntShareRate.value;
	if (!checkDecimalFormat(tAmntShareRate, 1, 2)) {
		alert("�����̯����С��λ��Ӧ����2λ��");
		return false;
	}
	var tPremShareRate = fm.PremShareRate.value;
	if (!checkDecimalFormat(tPremShareRate, 1, 2)) {
		alert("���ѷ�̯����С��λ��Ӧ����2λ��");
		return false;
	}
	//У��ֻ����һ�������չ�˾
	if (fm.MasterSlaveFlag.value =="0") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCCoinsuranceSql");
		tSQLInfo.setSqlId("LCCoinsuranceSql2");
		tSQLInfo.addSubPara(tGrpContNo);
		if (mOperate == "UPDATE") {
			var tRow1 = CoinsuranceGrid.getSelNo();
			var tSerialNo1 = CoinsuranceGrid.getRowColData(tRow1-1,1);//��ˮ��
			tSQLInfo.addSubPara(tSerialNo1);
		} else {
			tSQLInfo.addSubPara("");
		}
		var tCountArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tCountArr!=null) {
			if (tCountArr[0][0]>=1) {
				alert("ֻ����һ�����ٱ���˾��");
				return false;
			}
		}
	}
	//�����ı������Ѿ����ڵĹ�����˾�ķ�̯����֮��Ϊ1
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCCoinsuranceSql");
	tSQLInfo.setSqlId("LCCoinsuranceSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	if (mOperate == "UPDATE") {
		var tRow1 = CoinsuranceGrid.getSelNo();
		var tSerialNo1 = CoinsuranceGrid.getRowColData(tRow1-1,1);//��ˮ��
		tSQLInfo.addSubPara(tSerialNo1);
	} else {
		tSQLInfo.addSubPara("");
	}
	
	var tSumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	var tSumAmntShareRate=0;
	var tSumPremShareRate=0;
	if(tSumArr !=null ){
	 tSumAmntShareRate = tSumArr[0][0];//���ݿ��Ѵ��ڱ����̯����֮��
	 tSumPremShareRate = tSumArr[0][1];
	}
	var tAmntShareRate = fm.AmntShareRate.value;//ҳ�����������̯����
	var tPremShareRate = fm.PremShareRate.value;
	
	if (Number(tSumAmntShareRate)+Number(tAmntShareRate)>1) {
		
		alert("���������̯�������Ѿ����ڵĹ���\r��˾�����̯����֮��ӦС�ڵ���1��");
		return false;
	}
	if (Number(tSumPremShareRate)+Number(tPremShareRate)>1) {
		
		alert("�������ѷ�̯�������Ѿ����ڵĹ���\r��˾���ѷ�̯����֮��ӦС�ڵ���1��");
		return false;
	}
	
	return true;
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
		queryCoinsuranceInfo();
		initInpBox();
	}
}


/**
 * У����������λ��С��λ����
 */
function checkDecimalFormat(cValue, cLen1, cLen2) {
	
	if (cValue=='' || cValue==null) {//Ϊ��,
		return true;
	}
	
	var tLen =  (""+cValue+"").length;
	var tLen1 = (""+cValue+"").indexOf(".");
	var tLen2 = 0;
	if (tLen1==-1) {
		tLen1 = tLen;
	} else {
		tLen2 = tLen - tLen1 - 1;
	}
	
	if (Number(tLen1)>Number(cLen1)) {
		return false;
	}
	
	if (Number(tLen2)>Number(cLen2)) {
		return false;
	}
	
	return true;
}
