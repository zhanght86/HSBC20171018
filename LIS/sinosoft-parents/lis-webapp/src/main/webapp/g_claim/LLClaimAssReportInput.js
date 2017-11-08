/***************************************************************
 * <p>ProName��LLClaimAssReportInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-18
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

/**
 * ��ѯ�����˴�����������Ϣ
 */
function queryOnReportInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAssReportSql");
	tSQLInfo.setSqlId("LLClaimAssReportSql");
	tSQLInfo.addSubPara(mCustomerNo);
	
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimReportGrid,2);		
}

/**
 * ��ѯ�������ѹ��������ı�����Ϣ
 */
function queryOffReportInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAssReportSql");
	tSQLInfo.setSqlId("LLClaimAssReportSql1");
	tSQLInfo.addSubPara(mCustomerNo);
	tSQLInfo.addSubPara(mRgtNo);
	turnPage2.queryModal(tSQLInfo.getString(),AssociatedReportGrid,2);
}

/**
 * ��������
 */
function associate() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ���������ı������ݣ�");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAssReportSql");
	tSQLInfo.setSqlId("LLClaimAssReportSql2");
	tSQLInfo.addSubPara(mRgtNo);
	var tIsRptNo = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tIsRptNo!=""){
		alert("�ð����Ѿ�����������Ϣ");
		return false;
	}
	
	fm.RptNo.value = tRptNo;
	fm.Operate.value = "ASSOCIATE";
	submitForm();
}
/**
 * ȡ������
 */
function removeAssociate() {
	
	var tSelNo = AssociatedReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ�����ѹ����ı������ݣ�");
		return false;
	}
	var tRptNo = AssociatedReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		return;
	}
	fm.RptNo.value = tRptNo;
	fm.Operate.value = "REMOVEASS";
	submitForm();	
}
/**
 * ������ϸ��ѯ
 */
function showReportDetail() {
	
	var tSelNo = AssociatedReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ���ѹ����ı������ݣ�");
		return false;
	}
	var tRptNo = AssociatedReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		return;
	}
	fm.RptNo.value = tRptNo;	
	window.open("LLClaimReportInput.jsp?Type=3&RptNo="+ tRptNo);
}
/**
 * ����
 */
function goBack() {
	
	top.close();
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
