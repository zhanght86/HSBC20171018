/***************************************************************
 * <p>ProName��LLModifyconfigInput.js</p>
 * <p>Title�������޸Ĺ������ý���</p>
 * <p>Description�������޸Ĺ�������ȷ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��־��
 * @version  : 8.0
 * @date     : 2014-06-01
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo1 = new SqlClass();
var tSQLInfo2 = new SqlClass();
var tSQLInfo3 = new SqlClass();

/**
 * �ύ�����水ť��Ӧ����
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
	fm.Operate.value = mOperate;
	fm.submit(); //�ύ
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
	} 
	else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	/*
	queryClick();*/
	if (mOperate=="DELETE1") {
				queryClick1();
	}else if(mOperate=="DELETE2"){
				queryClick2();
	}else if(mOperate=="DELETE3"){
				queryClick3();
	}else if(mOperate=="INSERT1"){
				queryClick1();
	}else if(mOperate=="INSERT2"){
				queryClick2();
	}else if(mOperate=="INSERT3"){
				queryClick3();
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


 /*����*/
 function reset(){
 
 	fm.RuleType.value = '';
 	fm.RuleTypeName.value = '';
 	fm.PolType.value = '';
 	fm.PolTypeName.value = '';
 	fm.TiaoZhengType.value = '';
 	fm.TiaoZhengName.value = '';
 
 }
 /**
 * �����޸Ĳ�ѯ
 */
function queryClick() {

	if (!beforeSubmit()) {
		return false;
	}
	
	tSQLInfo1 = new SqlClass();
	tSQLInfo1.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo1.setSqlId("LLModifyconfigInputSql1");
	tSQLInfo1.addSubPara(fm.ReasonNo.value);
	turnPage1.queryModal(tSQLInfo1.getString(), RuleTypeGrid, 2);
	/*if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
	*/
	tSQLInfo2 = new SqlClass();
	tSQLInfo2.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo2.setSqlId("LLModifyconfigInputSql2");
	tSQLInfo2.addSubPara(fm.ReasonNo.value);
	turnPage2.queryModal(tSQLInfo2.getString(), PolTypeGrid, 2);
	/*if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
	*/
	tSQLInfo3 = new SqlClass();
	tSQLInfo3.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo3.setSqlId("LLModifyconfigInputSql3");
	tSQLInfo3.addSubPara(fm.ReasonNo.value);
	turnPage3.queryModal(tSQLInfo3.getString(), TiaoZhengGrid, 2);
	/*if (!turnPage3.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
	*/
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������б��������ݣ�");
	}else if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�������б��������ݣ�");
	}else if (!turnPage3.strQueryResult) {
		alert("δ��ѯ�����������б��������ݣ�");
		return false;
	}
	reset();
}
/*
������������
*/
function addClick(tFlag){
	if (!beforeSubmit()) {
		return false;
	}

	if(tFlag==1){
		if(trim(fm.RuleType.value)==""){
			alert("�������Ͳ���Ϊ��");
			return false;
		}
		mOperate = "INSERT1";
	}
	else if(tFlag==2){
		if(trim(fm.PolType.value)==""){
			alert("���ֱ��벻��Ϊ��");
			return false;
		}
		mOperate = "INSERT2";
	}
	else if(tFlag==3){
		if(trim(fm.TiaoZhengType.value)==""){
			alert("����������Ϊ��");
			return false;
		}
		mOperate = "INSERT3";
	}
	submitForm();
} 


function deleteClick(tFlag){
	if (!beforeSubmit()) {
		return false;
	}
 	
   if(tFlag==1){
   		var tSelNo = RuleTypeGrid.getChkCount();
		if (tSelNo<1) {
			alert("��ѡ��һ�������������ݣ�");
			return false;
		}
   		mOperate = "DELETE1";
   }
   else if(tFlag==2){
   		var tSelNo = PolTypeGrid.getChkCount();
		if (tSelNo<1) {
			alert("��ѡ��һ���������ݣ�");
			return false;
		}
   		mOperate = "DELETE2";
   }
   else{
   		var tSelNo = TiaoZhengGrid.getChkCount();
		if (tSelNo<1) {
			alert("��ѡ��һ�������������ݣ�");
			return false;
		}
   		mOperate = "DELETE3";
   }
   if(!confirm("ȷ��Ҫɾ����")){
      return false;
   }
   submitForm();
}

function queryClick1(){

	tSQLInfo1 = new SqlClass();
	tSQLInfo1.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo1.setSqlId("LLModifyconfigInputSql1");
	tSQLInfo1.addSubPara(fm.ReasonNo.value);
	turnPage1.queryModal(tSQLInfo1.getString(), RuleTypeGrid, 2);
}

function queryClick2(){

	tSQLInfo2 = new SqlClass();
	tSQLInfo2.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo2.setSqlId("LLModifyconfigInputSql2");
	tSQLInfo2.addSubPara(fm.ReasonNo.value);
	turnPage2.queryModal(tSQLInfo2.getString(), PolTypeGrid, 2);
}

function queryClick3(){

	tSQLInfo3 = new SqlClass();
	tSQLInfo3.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo3.setSqlId("LLModifyconfigInputSql3");
	tSQLInfo3.addSubPara(fm.ReasonNo.value);
	turnPage3.queryModal(tSQLInfo3.getString(), TiaoZhengGrid, 2);
}









