/***************************************************************
 * <p>ProName��RenewalParaInput.js</p>
 * <p>Title�����ڲ�������</p>
 * <p>Description�����ڲ�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_renewal.RenewalParaSql");
	tSQLInfo.setSqlId("RenewalParaSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageComQ.value);
	tSQLInfo.addSubPara(fm.PayIntvQ.value);
	initResultInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(),ResultInfoGrid, 1 , 1);
	if (o=="1" && !turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}
function showGracePeriod(){
	if(fm.GracePeriodCheck.checked==true){
		DivGracePeriod.style.display=''
	}else{
		DivGracePeriod.style.display='none'
	}
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
		if("POLICYSAVE"==mOperate){
			queryCont();
		} else {
			initInpBox();
			queryClick(0);
		}
	}
}
/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=="conditioncomcode") {
	 
		var tSql = "1 and comgrade=#03# and comcode like #" + tManageCom + "%%# ";
		if (returnType=='0') {
			return showCodeList('conditioncomcode',value2,value3,null,tSql,'1','1',180);
		} else {
			return showCodeListKey('conditioncomcode',value2,value3,null,tSql,'1','1',180);
		}
	}
}

function addClick(){
	
	if(!verifyInput()){
		return false;
	}
	
	mOperate="PARAADD";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}

function updateClick(){
	
	var tSelNo = ResultInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	
	if(!verifyInput()){
		return false;
	}
	
	mOperate="PARAUPDATE";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}

function deleteClick(){
	
	var tSelNo = ResultInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	
	mOperate="PARADELETE";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}
/**
* չʾ��Ϣ
*/
function onShowInfo(){
	var tSelNo = ResultInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	fm.SerialNo.value = ResultInfoGrid.getRowColData(tSelNo, 1);
	fm.ManageCom.value = ResultInfoGrid.getRowColData(tSelNo, 2);
	fm.ManageComName.value = ResultInfoGrid.getRowColData(tSelNo, 3);
	fm.PayIntv.value = ResultInfoGrid.getRowColData(tSelNo, 4);
	fm.PayIntvName.value = ResultInfoGrid.getRowColData(tSelNo, 5);
	fm.PumpDays.value = ResultInfoGrid.getRowColData(tSelNo, 6);
	fm.GracePeriod.value = ResultInfoGrid.getRowColData(tSelNo, 7);
}

/**
* չʾ������Ϣ
*/
function onPolicyInfo(){
	var tSelNo = ContInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	fm.PSerialNo.value = ContInfoGrid.getRowColData(tSelNo, 1);
	fm.PPolicyNo.value = ContInfoGrid.getRowColData(tSelNo, 3);
	fm.PGracePeriod.value = ContInfoGrid.getRowColData(tSelNo, 13);
}


/**
 * ��ѯ
 */
function queryCont(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_renewal.RenewalParaSql");
	tSQLInfo.setSqlId("RenewalParaSql2");
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom2.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.SaleChnl.value);
	
	initContInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(),ContInfoGrid, 1 , 1);
	if (o=="1" && !turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

function saveClick(){
	
	var tSelNo = ContInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	if (fm.PGracePeriod.value=="" || !isInteger(fm.PGracePeriod.value)) {
		alert("������Ч��������");
		return false;
	}
	if (parseFloat(fm.PGracePeriod.value)<0) {
		alert("��¼�����0��������");
		return false;
	}
	mOperate="POLICYSAVE";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}
