/***************************************************************
 * <p>ProName��RenewalManualInput.js</p>
 * <p>Title�����ڴ���</p>
 * <p>Description�����ڴ���</p>
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

	initRiskInfoGrid();
	if (fm.Days.value!="" && !isInteger(fm.Days.value)) {
		alert("�����߽ɱ���������������Ч��������");
		return false;
	}

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_renewal.RenewalManualSql");
	tSQLInfo.setSqlId("RenewalManualSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.StartOperDate.value);
	tSQLInfo.addSubPara(fm.EndOperDate.value);
	tSQLInfo.addSubPara(fm.StartPayDate.value);
	tSQLInfo.addSubPara(fm.EndPayDate.value);
	tSQLInfo.addSubPara(fm.PayIntv.value);
	if(isInteger(fm.Days.value)){
		tSQLInfo.addSubPara(addDate(4,-fm.Days.value,tCurrentDate));
		tSQLInfo.addSubPara(addDate(4,fm.Days.value,tCurrentDate));
	}else{
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	//tSQLInfo.addSubPara(tCurrentDate);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	
	
	initContInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(),ContInfoGrid, 1 , 1);
	if (o=="1" && !turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
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
		queryClick("0");
	}
}
/**
*չʾ������Ϣ
*/
function showRiskDivInfo(){
	DivRiskGrid.style.display='';
}

/**
 * ��ѯ
 */
function showRiskInfo() {

	showRiskDivInfo();
	var tSelNo = ContInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	var getNoticeNo = ContInfoGrid.getRowColData(tSelNo, 1);
	var payCount = ContInfoGrid.getRowColData(tSelNo, 10);
	var grpContNo = ContInfoGrid.getRowColData(tSelNo, 3);

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_renewal.RenewalManualSql");
	tSQLInfo.setSqlId("RenewalManualSql2");
	tSQLInfo.addSubPara(payCount);
	tSQLInfo.addSubPara(grpContNo);

	initRiskInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(),RiskInfoGrid, 1 , 1);
}
/**
 * �ֹ��鵵
 */
function renewalClick(){

	if (ContInfoGrid.getSelNo()==0) {
		alert("�빴ѡ����ѡ��Ҫ�����ļ�¼��");
		return false;
	}

	mOperate="RENEWAL";
	fm.action = "./RenewalManualSave.jsp?Operate="+ mOperate +"&RenewalType=Manu";
	submitForm();
}