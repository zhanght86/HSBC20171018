/***************************************************************
 * <p>ProName��BalancePrintInput.js</p>
 * <p>Title�����㵥��ӡ</p>
 * <p>Description�����㵥��ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
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
	
	initContInfoGrid();
	initPosInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalancePrintSql");
	tSQLInfo.setSqlId("BalancePrintSql1");
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.BalanceAllNo.value);
	tSQLInfo.addSubPara(fm.StartBalanceDate.value);
	tSQLInfo.addSubPara(fm.EndBalanceDate.value);
	tSQLInfo.addSubPara(fm.BalanceType.value);
	tSQLInfo.addSubPara(fm.PrintState.value);
	tSQLInfo.addSubPara(tManageCom);
	if(fm.BalanceState.value=="00"){
		tSQLInfo.addSubPara("00");
		tSQLInfo.addSubPara("");
	}else if(fm.BalanceState.value=="01"){
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("01");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.StartConfDate.value);
	tSQLInfo.addSubPara(fm.EndConfDate.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 0, 1);
	if(o=="1" && !turnPage1.strQueryResult){
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
function afterSubmit(FlagStr, content, patch, fileName1) {
	
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
		queryClick(0);
		if(mOperate=="PRINT"){
			downFile(patch,fileName1);
		}
	}
}
/**
*չʾ��ȫ��Ϣ
*/
function showPosInfo(){
	DivPosInfo.style.display='';
	var tRow = ContInfoGrid.getSelNo()-1;
	var tGrpContNo=ContInfoGrid.getRowColData(tRow,2);
	var tBlanceNo=ContInfoGrid.getRowColData(tRow,4);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalancePrintSql");
	if(_DBT==_DBM){
		tSQLInfo.setSqlId("BalancePrintSql2");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tBlanceNo);
   }else if(_DBT==_DBO)
   {
	    tSQLInfo.setSqlId("BalancePrintSql3");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tBlanceNo);
   }
	turnPage2.queryModal(tSQLInfo.getString(), PosInfoGrid, 0, 1);
}

/**
 * ���ؽ����嵥��Ϣ
 */
function downloadClick() {
	
	var tSelNo = ContInfoGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	var tGrpContNo=ContInfoGrid.getRowColData(tSelNo-1,2);
	var tBlanceNo=ContInfoGrid.getRowColData(tSelNo-1,4);
	
	fm.SheetName.value = "��ȫ�����嵥";
	
	//���������嵥����
	var tInsuredTitle = "���^|������^|���㵥��^|��ȫ�����^|��ȫǩ������^|�ܱ�����(Ԫ)";
	
	fm.SheetTitle.value = tInsuredTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalancePrintSql");
	
	if(_DBT==_DBM){
		tSQLInfo.setSqlId("BalancePrintSql2");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tBlanceNo);
   }else if(_DBT==_DBO)
   {
	    tSQLInfo.setSqlId("BalancePrintSql3");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tBlanceNo);
   }
	
	fm.SheetSql.value = tSQLInfo.getString();
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
//���㵥��ӡ
function BalancePrint(){
	
	var tSelNo = ContInfoGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ�����㵥��Ϣ��");
		return false;
	}
	var tGrpContNo=ContInfoGrid.getRowColData(tSelNo-1,2);
	var tBalanceNo=ContInfoGrid.getRowColData(tSelNo-1,4);
	fm.action = "./BalancePrintSave.jsp?Operate=PRINT&GrpContNo="+tGrpContNo+"&BalanceNo="+tBalanceNo;
	mOperate = "PRINT";
	submitForm();
}

/**
** �ļ�����
**/
function downFile(patch,fileName1){
	
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
	
}
