/***************************************************************
 * <p>ProName��LLClaimReportMainInput.js</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();


/**
 * ��ѯ������
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportAppSql");
	tSQLInfo.setSqlId("LLClaimReportAppSql");
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IdNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.QueryRptNo.value);
	tSQLInfo.addSubPara(fm.RptDateStart.value);
	tSQLInfo.addSubPara(fm.RptDateEnd.value);
	tSQLInfo.addSubPara(fm.RptState.value);
	tSQLInfo.addSubPara(fm.OperatorDateStart.value);
	tSQLInfo.addSubPara(fm.OperatorDateEnd.value);
	tSQLInfo.addSubPara(fm.RptCom.value);
	tSQLInfo.addSubPara(fm.RptFlag.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.rptstate.value);
		
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimReportGrid,2);
		
}
/**
 * ��ѯ���˳ذ���
 */
function querySelfClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportAppSql");
	tSQLInfo.setSqlId("LLClaimReportAppSql1");
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(mOperator);	
	
	turnPage2.queryModal(tSQLInfo.getString(),SelfLLClaimReportGrid,2);		
}
/**
 * �鿴������ϸ
 */
function showReportDetail() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ�񹫹�����һ��������Ϣ");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	fm.RptNo.value = tRptNo;
	if (tRptNo==null || tRptNo=="") {
		alert("���Ȳ�ѯ�������¼���Ϣ��");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=3&RptNo="+ tRptNo;
}
/**
 * ����
 */
function applyReport() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ�񹫹�����һ��������Ϣ");
		return false;
	}
	fm.RptNo.value = LLClaimReportGrid.getRowColData(tSelNo,1);
	
	var tRptState = LLClaimReportGrid.getRowColData(tSelNo,14);
	if (tRptState!=null && tRptState=="1") {
		alert("�ñ�����ȷ�ϣ��������룡");
		return false;
	}	
	
	fm.Operate.value="APPLY";
	submitForm();
}
/**
 * ���뱨��
 */
function enterReport(parm1) {
	
	var tSelNo;
	if (parm1!=null && parm1!="") {
		tSelNo = document.all(parm1).all("SelfLLClaimReportGridNo").value;
		tSelNo = tSelNo - turnPage2.pageIndex*turnPage2.pageLineNum;
		SelfLLClaimReportGrid.radioSel(tSelNo);
		tSelNo = tSelNo - 1;
	} else {
		tSelNo = SelfLLClaimReportGrid.getSelNo() - 1;
	}
	
	if (tSelNo<0) {
		alert("��ѡ����˳���һ��������Ϣ");
		return false;
	}
	var tRptNo = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	fm.RptNo.value = tRptNo;
	if (tRptNo==null || tRptNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=2&RptNo="+ tRptNo;
}
/**
 * �ͷű���
 */
function releaseReport() {
	
	var tSelNo = SelfLLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ����˳���һ��������Ϣ");
		return false;
	}
	fm.RptNo.value = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	fm.Operate.value="RELEASE";
	submitForm();	
}
/**
 * ��������
 */
function newReport() {
	window.location.href="LLClaimReportInput.jsp?Type=1";
}
/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		initForm();
	}	
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
/**
 * ѡ�񱨰�������ť
 */
function selectReportButton(){
	//���������
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if(tSelNo<0){
		alert("��ѡ�񹫹��س��µ�һ��������Ϣ��");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	var tRptState = LLClaimReportGrid.getRowColData(tSelNo,14);
	if(tRptState ==2){
		fm.reportOpen.style.display = "";
		fm.reportClose.style.display = "none";
	}else{
		fm.reportOpen.style.display = "none";
		fm.reportClose.style.display = "";
	}
}

//���˳����
function selectSelfReportButton(){
	
	var tSelNo1 = SelfLLClaimReportGrid.getSelNo()-1;
	if(tSelNo1<0){
		alert("��ѡ����˳��µ�һ��������Ϣ��");
		return false;
	}
	var tRptNo1 = SelfLLClaimReportGrid.getRowColData(tSelNo1,1);
	var tRptState1 = SelfLLClaimReportGrid.getRowColData(tSelNo1,14);
	if(tRptState1==2){
		fm.reportOpen.style.display = "";
		fm.reportClose.style.display = "none";
	}else{
		fm.reportOpen.style.display = "none";
		fm.reportClose.style.display = "";
	}
}
/**
 * �����ر�
 */
function closeReport() {
	
	var tSelNo = SelfLLClaimReportGrid.getSelNo()-1;
	if(tSelNo<0){
		alert("��ѡ����˳��µ�һ��������Ϣ��");
		return false;
	}
	fm.RptNo.value = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	
	fm.Operate.value="CLOSE";
	submitForm();	
	
}
/**
 * ��������
 */
function openReport(){
	var tSelNo = SelfLLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ����˳���һ��������Ϣ");
		return false;
	}
	fm.RptNo.value = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	
	fm.Operate.value="OPEN";
	submitForm();	
	
}
