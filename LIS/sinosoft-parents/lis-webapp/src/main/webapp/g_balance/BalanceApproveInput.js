/***************************************************************
 * <p>ProName��BalanceApproveInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
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
function queryClick() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceApproveSql");
	if(_DBT==_DBM){
		tSQLInfo.setSqlId("BalanceApproveSql1");
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.StartBalanceDate.value);
		tSQLInfo.addSubPara(fm.EndBalanceDate.value);
		tSQLInfo.addSubPara(fm.Days.value);
		tSQLInfo.addSubPara(fm.BalancePeriod.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(tManageCom);
   }else if(_DBT==_DBO)
   {
	   tSQLInfo.setSqlId("BalanceApproveSql3");
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.StartBalanceDate.value);
		tSQLInfo.addSubPara(fm.EndBalanceDate.value);
		tSQLInfo.addSubPara(fm.Days.value);
		tSQLInfo.addSubPara(fm.BalancePeriod.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(tManageCom);
   }
		
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 0, 1);
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ�����������Ĳ�ѯ���!");
		return false;
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
	}
	initContInfoGrid();
	initInpBox();
	DivPosInfo.style.display='none';
}
/**
*չʾ��ȫ��Ϣ
*/

function showPosInfo(){
	DivPosInfo.style.display='';
	var tRow = ContInfoGrid.getSelNo()-1;
	var tBalanceAppNo=ContInfoGrid.getRowColData(tRow,2);
	var tGrpContNo=ContInfoGrid.getRowColData(tRow,3);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceApproveSql");
	tSQLInfo.setSqlId("BalanceApproveSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tBalanceAppNo);
	turnPage2.queryModal(tSQLInfo.getString(), PosInfoGrid, 0, 1);
	var rowNum = PosInfoGrid.mulLineCount;
	 
	fm.SumMoney.value=ContInfoGrid.getRowColData(tRow,8);;
}

/**
*������������
*/
function saveApprove(){

	var rowNum = ContInfoGrid.getSelNo()-1;

	if(rowNum<0){	
		alert("������ѡ��һ����¼!");
		return false;
	}	
	if(fm.ApproveFlag.value==null||fm.ApproveFlag.value==''){
		alert("��¼����������!");
		return false;
	}
	if(fm.ApproveDesc.value==null||fm.ApproveDesc.value==''){
		alert("��¼����������!");
		return false;
	}
	fm.action = "./BalanceApproveSave.jsp?Operate=APPROVE";
	submitForm(fm);
}
