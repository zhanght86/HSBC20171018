/***************************************************************
 * <p>ProName��LLClaimSpecialCasePrintInput.js</p>
 * <p>Title������������ӡ</p>
 * <p>Description������������ӡ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();


/**
 * ���β�ѯ
 */
function queryBatch() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCasePrintSql");
	tSQLInfo.setSqlId("LLClaimSpecialCasePrintSql1");		
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(fm.PrintState.value);
	tSQLInfo.addSubPara(fm.ConfirmStartDate.value);
	tSQLInfo.addSubPara(fm.ConfirmEndDate.value);	
	tSQLInfo.addSubPara(mManageCom);
	
	turnPage1.queryModal(tSQLInfo.getString(),BatchListGrid, 2);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
	
}


/**
 * ���δ�ӡ
 */
function batchPrint() {
	
	/*if(mManageCom.length<=2){
		alert("�������ܹ�˾��ӡ!");
		return false;
	}*/
	var i = BatchListGrid.getSelNo();
	
	if(i < 1){
		alert("��ѡ�д�ӡ������Ϣ�б��һ�м�¼��");
		return false;
	}
	var tBatchNo=BatchListGrid.getRowColData(i-1,1);
	if(tBatchNo===''||tBatchNo=="null"){
		alert("���Ȳ�ѯ��");
		return false;
	}
	fm.tGrpRgtNo.value=tBatchNo;
	fm.Operate.value="BatchPrint";
	submitForm();
}


/**
 * ���˲�ѯ
 */
function querySingle() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCasePrintSql");
	tSQLInfo.setSqlId("LLClaimSpecialCasePrintSql2");
	tSQLInfo.addSubPara(fm.SingleGrpName.value);
	tSQLInfo.addSubPara(fm.SingleCustomerName.value);
	tSQLInfo.addSubPara(fm.SingleIdNo.value);	
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara("");
				
	turnPage2.queryModal(tSQLInfo.getString(),CustomerListGrid, 2);
	
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}

/**
 * ��ѯѡ�еĿͻ���Ϣ
 */
function showSelectInfo () {
	
	var tCloseReason = document.getElementById("divRgtClose");
	tCloseReason.style.display = "";
	fm.NoRgtReason.value = "";
	fm.NoRgtReasonName.value = "";
}

/**
 * �رո��˰���
 */
/*function rgtClose () {
	
	var i = CustomerListGrid.getSelNo()-1;
	if (i<0) {
		alert("����ѡ��һ�����˰�����Ϣ��");
		return;
	}	
	var tRegisterNo = CustomerListGrid.getRowColData(i,2);
	fm.RgtNo.value = tRegisterNo;
	var tCustomerNo = CustomerListGrid.getRowColData(i,4);
	fm.CustomerNo.value = tCustomerNo;		
	
	if (fm.NoRgtReason.value==null || fm.NoRgtReason.value=="") {
		alert("��¼��ر�ԭ��");
		return false;
	}
	
	fm.Operate.value = "CLOSE";
	submitForm();
}*/

/**
 * ���˴�ӡ
 */
function singlePrint() {
	/*if(mManageCom.length<=2){
		alert("�������ܹ�˾��ӡ!");
		return false;
	}*/
	
	fm.Operate.value="Print";
	submitForm();
}

/**
 * �ύ����
 */
function submitForm() {
	
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
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content,patch,fileName1) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else if(fm.Operate.value=="CLOSE"){
		
			
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		}else if(fm.Operate.value=="BatchPrint"||fm.Operate.value=="Print"){
		
			window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1
	}
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
//������Ϣ������������
function showSelectPrintInfo(){
	
	
	var i = BatchListGrid.getSelNo()-1;		
	var tGrpRgtNo = BatchListGrid.getRowColData(i,1);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCasePrintSql");
	tSQLInfo.setSqlId("LLClaimSpecialCasePrintSql2");
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara("");	
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(tGrpRgtNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),CustomerListGrid, 2);
	
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}