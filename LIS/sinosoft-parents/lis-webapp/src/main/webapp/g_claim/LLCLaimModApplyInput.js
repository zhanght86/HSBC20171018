/***************************************************************
 * <p>ProName��LLCLaimModApplyInput.js</p>
 * <p>Title�������޸ķֹ�˾�����������</p>
 * <p>Description�������޸ķֹ�˾�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ����
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();


var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �ύ����
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
	fm.action = "./LLClaimModApplySave.jsp";
	fm.submit(); //�ύ
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}

	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
	
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
	if (mOperate=="INSERT" || mOperate=="UPDATE" || mOperate=="DELETE" || mOperate=="SUBMIT" ) {
			clearData();
			queryClick();
		}
		
		if (mOperate=="SELECT" || mOperate=="CANCEL") {
			
			queryGrp();
			
			if (fm.RuleType.value=="01") {
				queryAcceptGrpClick();
			} else if (fm.RuleType.value=="02") {
				queryAcceptGrpPlanClick();
			}
		}
		
		if (mOperate=="SAVEPLAN") {
			queryPlan();
		}
	}
	
	if(mOperate=="APPLYMODIFY") {
	
	    fm.QueryApplyNo.value = fm.ApplyNo.value;
		queryClick();
		fm.QueryApplyNo.value="";
	}
}



// ��ѯ����
function queryClick() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql1");	
	tSQLInfo.addSubPara(mManageCom);	
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryRuleType.value); 
	tSQLInfo.addSubPara(fm.QueryRiskCode.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.ClmmodifyState.value); 
	tSQLInfo.addSubPara(fm.QueryReasonType.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryApplyNo.value);

	turnPage1.queryModal(tSQLInfo.getString(), QueryModItemGrid, 2, 1);
	if (!turnPage1.strQueryResult) {                       
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	} else {
		clearData();
	}
}

//�������
function clearData() {
	
	fm.ReasonType.value = "";
	fm.ReasonTypeName.value = "";
	fm.RuleType.value = "";
	fm.RuleTypeName.value = "";
	fm.RiskCode.value = "";
	fm.RiskCodeName.value = "";
	fm.AdjustDirection.value = "";
	fm.AdjustDirectionName.value = "";
	fm.UpAdjustRule.value = "";
	fm.UpAdjustRuleName.value = "";
	fm.UpAdjustRate.value = "";
	fm.StartDate.value = "";
	fm.EndDate.value = "";
	fm.Remark.value = "";
	fm.ApplyNo.value = "";
	fm.ApplyBatchNo.value = "";
	fm.CheckConclusion.value = "";
	fm.CheckConclusionName.value = "";
	fm.CheckIdea.value = "";
	
	fm.all("divQueryGrpCont").style.display="none";
	fm.all("divGrpCont").style.display="none";
	fm.all("divGrpContPlan").style.display="none";
	fm.all("CheckInfo").style.display="none";
	
	initNotGrpGrid();
	initAcceptGrpGrid();
	initAcceptGrpPlanGrid();
	initPlanGrid();
}

//��ȡ�����޸Ĳ�ѯ����
function showClmModApplyInfo() {
	
	var tSelNo = QueryModItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	fm.ReasonType.value = QueryModItemGrid.getRowColData(tSelNo-1, 2);
	fm.ReasonTypeName.value = QueryModItemGrid.getRowColData(tSelNo-1, 3);
	fm.RuleType.value = QueryModItemGrid.getRowColData(tSelNo-1, 4);
	fm.RuleTypeName.value = QueryModItemGrid.getRowColData(tSelNo-1, 5);
	fm.RiskCode.value = QueryModItemGrid.getRowColData(tSelNo-1, 6);
	fm.RiskCodeName.value = QueryModItemGrid.getRowColData(tSelNo-1, 7);
	fm.AdjustDirection.value = QueryModItemGrid.getRowColData(tSelNo-1, 8);
	fm.AdjustDirectionName.value = QueryModItemGrid.getRowColData(tSelNo-1, 9);
	fm.UpAdjustRule.value = QueryModItemGrid.getRowColData(tSelNo-1, 10);
	fm.UpAdjustRuleName.value = QueryModItemGrid.getRowColData(tSelNo-1, 11);
	fm.UpAdjustRate.value = QueryModItemGrid.getRowColData(tSelNo-1, 12);
	fm.StartDate.value = QueryModItemGrid.getRowColData(tSelNo-1, 13);
	fm.EndDate.value = QueryModItemGrid.getRowColData(tSelNo-1, 14);
	fm.Remark.value = QueryModItemGrid.getRowColData(tSelNo-1, 15);
	fm.ApplyState.value = QueryModItemGrid.getRowColData(tSelNo-1, 17);
	fm.ApplyNo.value = QueryModItemGrid.getRowColData(tSelNo-1, 18);
	fm.ApplyBatchNo.value = QueryModItemGrid.getRowColData(tSelNo-1, 19);
	fm.CheckConclusion.value=QueryModItemGrid.getRowColData(tSelNo-1, 20);
	initNotGrpGrid();
	
	
	if (fm.AdjustDirection.value=="02") {
	   	fm.all("UpAdjustRuleTitle").style.display = "none";
		fm.all("UpAdjustRuleInput").style.display = "none";
		fm.all("UpAdjustRateTitle").style.display = "none";
		fm.all("UpAdjustRateInput").style.display = "none";
	} else {
		fm.all("UpAdjustRuleTitle").style.display = "";
		fm.all("UpAdjustRuleInput").style.display = "";
		fm.all("UpAdjustRateTitle").style.display = "";
		fm.all("UpAdjustRateInput").style.display = "";
	}	

	//����չʾ
	
	if (fm.RuleType.value=="00" || fm.RuleType.value=="") {
		fm.all("divQueryGrpCont").style.display="none";
		fm.all("divGrpCont").style.display="none";
		fm.all("divGrpContPlan").style.display="none";
		
		fm.all("StartDateTitle").style.display = "";
		fm.all("EndDateTitle").style.display = "";
		fm.all("StartDateInput").style.display = "";
		fm.all("EndDateInput").style.display = "";
	}
	
	if (fm.RuleType.value=="01") {
		fm.all("divQueryGrpCont").style.display = "";
		fm.all("divGrpCont").style.display = "";
		fm.all("divGrpContPlan").style.display = "none";
		
		fm.all("StartDateTitle").style.display = "none";
		fm.all("EndDateTitle").style.display = "none";
		fm.all("StartDateInput").style.display = "none";
		fm.all("EndDateInput").style.display = "none";
		queryAcceptGrpClick();
	}
	
	if (fm.RuleType.value=="02") {
		fm.all("divQueryGrpCont").style.display = "";
		fm.all("divGrpCont").style.display = "none";
		fm.all("divGrpContPlan").style.display = "";
		
		fm.all("StartDateTitle").style.display = "none";
		fm.all("EndDateTitle").style.display = "none";
		fm.all("StartDateInput").style.display = "none";
		fm.all("EndDateInput").style.display = "none";
		queryAcceptGrpPlanClick();
	}
	if (fm.CheckConclusion.value=="1"){
		fm.all("CheckInfo").style.display = "";
		queryCheckInfo();
	}
	if(fm.CheckConclusion.value=="0" || fm.CheckConclusion.value==null || fm.CheckConclusion.value==""){
		fm.all("CheckInfo").style.display = "none";
	}
	
}

//������Ϣ����
function showRiskInfo(){
	var tSelNo = AcceptGrpaGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����ѡ�񱣵���Ϣ��Ϣ��");
		return false;
	}
	
	queryPlan();
   
}

//�������У��
 
function beforQueryCode(obj, Field) {
	
	var tObjName = obj.name;
	var tFieldValue = Field.value;
	if (tFieldValue==null || tFieldValue=="") {
		
		if (tObjName=="RuleType" || tObjName=="RiskCode") {
			alert("����ѡ�����޸�ԭ��");
		}
		if(tObjName=="AdjustDirection"){		
			alert("����ѡ�����ֱ��룡");	
		}
		if(tObjName=="UpAdjustRule"){		
			alert("����ѡ���������");	
		}
	}
		Field.focus();
		if(!(fm.RiskCode.value=="2MT002" || fm.RiskCode.value=="2MT006")){
			conditionCode = "1 and comcode in (#1#,#2#)";
		}else{
			conditionCode = "1 and comcode=#1#";
		}
		return false;
}

//������ѡ���Ժ����
function afterCodeSelect(cCodeName, Field) {
	
	if (Field.name=="ReasonType") {
		
		fm.RuleType.value = "";
		fm.RuleTypeName.value="";
		fm.RiskCode.value = "";
		fm.RiskCodeName.value = "";
		fm.AdjustDirection.value = "";
		fm.AdjustDirectionName.value = "";		
		fm.UpAdjustRule.value = "";
		fm.UpAdjustRuleName.value = "";
		fm.UpAdjustRate.value = "";
		
		fm.all("UpAdjustRuleTitle").style.display = "none";
		fm.all("UpAdjustRuleInput").style.display = "none";
		fm.all("UpAdjustRateTitle").style.display = "none";
		fm.all("UpAdjustRateInput").style.display = "none";
		
		fm.all("StartDateTitle").style.display = "none";
		fm.all("EndDateTitle").style.display = "none";
		fm.all("StartDateInput").style.display = "none";
		fm.all("EndDateInput").style.display = "none";
	}
	
	if (Field.name=="AdjustDirection") {
		
		if (Field.value=="02") {
			
			fm.all("UpAdjustRuleTitle").style.display = "none";
			fm.all("UpAdjustRuleInput").style.display = "none";
			fm.all("UpAdjustRateTitle").style.display = "none";
			fm.all("UpAdjustRateInput").style.display = "none";
			
			fm.UpAdjustRule.value = "";
			fm.UpAdjustRuleName.value = "";
			fm.UpAdjustRate.value = "";
		} else {
			
			fm.all("UpAdjustRuleTitle").style.display = "";
			fm.all("UpAdjustRuleInput").style.display = "";
			fm.all("UpAdjustRateTitle").style.display = "";
			fm.all("UpAdjustRateInput").style.display = "";
		}
	}
	
	if(Field.name=="RuleType"){
		
		if(Field.value=="00"){
			fm.all("StartDateTitle").style.display = "";
			fm.all("EndDateTitle").style.display = "";
			fm.all("StartDateInput").style.display = "";
			fm.all("EndDateInput").style.display = "";
		} else {
			
			fm.StartDate.value = "";
			fm.EndDate.value = "";
			fm.all("StartDateTitle").style.display = "none";
			fm.all("EndDateTitle").style.display = "none";
			fm.all("StartDateInput").style.display = "none";
			fm.all("EndDateInput").style.display = "none";
		}
	}
	
}

function beforeCheck() {
	
	if (fm.AdjustDirection.value!="02") {
		
		if (fm.UpAdjustRule.value==null || fm.UpAdjustRule.value=="") {
			alert("��ѡ�����ϵ�������");
			return false;
		}
		
		if (fm.UpAdjustRate.value==null || fm.UpAdjustRate.value=="") {
			alert("��ѡ�����ϵ���������");
			return false;
		}
			
	}
	
	if (fm.RuleType.value=="00") {
		
		if (fm.StartDate.value==null || fm.StartDate.value=="") {
			alert("[��������]Ϊ���֣�����¼�뱣���޸���Ч���ڣ�");
			return false;
		}
		
		if (fm.EndDate.value==null || fm.EndDate.value=="") {
			alert("[��������]Ϊ���֣�����¼�뱣���޸���ֹ���ڣ�");
			return false;
		}
	}
	
	//��Ч��������Чֹ��У��
	
	if(fm.EndDate.value < fm.StartDate.value){
	    alert("¼�����Чֹ�ڱ��������Ч����");
	    return false;
	}
	
	
	return true;
}

//��������

function insertClick() {

	if((!(fm.UpAdjustRate.value<=1) || fm.UpAdjustRate.value<0) && (fm.AdjustDirection.value=="00" || fm.AdjustDirection.value=="01" )){
		if(fm.UpAdjustRule.value=="00" || fm.UpAdjustRule.value=="01"){
			alert("�����ϵ�������Ϊ�˵����������˽��ʱ�����ϵ������������Ǵ���0С��1��С����");
			return false;
		}

	}
	
	if(!(fm.UpAdjustRate.value>=1)&&(fm.AdjustDirection.value=="00" || fm.AdjustDirection.value=="01" )){
		if( fm.UpAdjustRule.value=="02"){
			alert("�����ϵ�������Ϊ����ʱ�����ϵ������������Ǵ���1��С����");
			return false;
		}
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (!beforeCheck()) {
		return false;
	}
		
	mOperate = "INSERT";
	submitForm();
}


//�޸ı���
function upateClick(){

	var tSelNo = QueryModItemGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (!beforeCheck()) {
		return false;
	}
	if(fm.ApplyState.value=="��Ч"){
		alert("�Ѿ���Ч����ͨ��[�����޸�]������޸�");
		return false;
	}else{
	
		mOperate="UPDATE";
		submitForm();
	}
}


//ɾ������
function deleteClick(){

	var tSelNo = QueryModItemGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	if(fm.ApplyState.value=="��Ч"){

		alert("�Ѿ���Ч����ͨ��[�����޸�]�����ɾ��");
		return false;
	}else{
	
		mOperate="DELETE";
		submitForm();
	}
}


//������ѯ--��������Ϣ
function queryGrp(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql2");
	
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.ValStartDate.value);
	tSQLInfo.addSubPara(fm.ValEndDate.value);
	
		
	turnPage2.queryModal(tSQLInfo.getString(), NotGrpGrid, 0, 1);
	if (!turnPage2.strQueryResult) {                       
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}

//�ѹ���������Ϣ��ѯ-������
function queryAcceptGrpClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql3");	
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	tSQLInfo.addSubPara(mManageCom);
	
	turnPage3.pageLineNum = 1000;
	turnPage3.queryModal(tSQLInfo.getString(), AcceptGrpGrid, 2, 1);	
	initAcceptGrpPlanGrid();
	initPlanGrid();
}

//�ѹ���������Ϣ��ѯ-����������
function queryAcceptGrpPlanClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql3");
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	tSQLInfo.addSubPara(mManageCom);
	
	turnPage4.pageLineNum = 1000;
	turnPage4.queryModal(tSQLInfo.getString(), AcceptGrpPlanGrid, 2, 1);
	
	initAcceptGrpGrid();
	initPlanGrid();
}


//������Ϣ��ѯ
function queryPlan() {
	
	var tSelNo = QueryModItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	
	var tRiskCode = QueryModItemGrid.getRowColData(tSelNo-1, 6);
	
	var tGrpSelNo = AcceptGrpPlanGrid.getSelNo();
	if (tGrpSelNo==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	var tGrpContNo = AcceptGrpPlanGrid.getRowColData(tGrpSelNo-1, 2);
	var tContType = AcceptGrpPlanGrid.getRowColData(tGrpSelNo-1, 8);
	
	if(tContType!="02"){	

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
		tSQLInfo.setSqlId("LLClaimModApplySql4");
		tSQLInfo.addSubPara(tRiskCode);
		tSQLInfo.addSubPara(fm.ApplyNo.value); 
		tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(fm.ApplyNo.value); 
		tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
		tSQLInfo.addSubPara(tGrpContNo);
		
		turnPage5.pageLineNum = 1000;
		turnPage5.queryModal(tSQLInfo.getString(), PlanGrid, 2, 1);
	}
}
 
//������ѡ��
function selectClick() {

	var tSelNo = QueryModItemGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	
	var rowNum = NotGrpGrid.mulLineCount;
	var flag = false;
	
	for (var i=0;i<rowNum;i++) {
	
		if (NotGrpGrid.getChkNo(i)) {
			flag = true;
		}
	}
	
	if (!flag) {
		alert("����ѡ��һ��������������Ϣ");
		return false;
	}
	if(fm.ApplyState.value=="��Ч"){	
		alert("����Ч�����ѡ�����������Ϣ��");
		return false;
	}else{

		mOperate="SELECT";
		submitForm();
	}	
}

//������ȡ��ѡ��
function cancelClick() {
	
	var tSelNo = QueryModItemGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	
	var flag = false;
	
	if (fm.RuleType.value=="01") {
		
		var rowNum = AcceptGrpGrid.mulLineCount;
		
		for (var i=0;i<rowNum;i++) {
			
			if (AcceptGrpGrid.getChkNo(i)) {
				flag = true;
			}
		}
	} else if (fm.RuleType.value=="02") {
		
		var rowNum = AcceptGrpPlanGrid.mulLineCount;
		for (var i=0;i<rowNum;i++) {
			
			if (AcceptGrpPlanGrid.getChkNo(i)) {
				flag = true;
			}
		}
	}
	
	if (!flag) {
		alert("����ѡ��һ���ѹ���������Ϣ");
		return false;
	}
	if(fm.ApplyState.value=="��Ч"){	
		alert("����Ч�����ȡ������������Ϣ��");
		return false;
	}else{
		
		mOperate="CANCEL";
		submitForm();
	}
}

//���淽����Ϣ
function savePlan() {
	var tSelNo = QueryModItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	
	var tGrpSelNo = AcceptGrpPlanGrid.getSelNo();
	if (tGrpSelNo==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}	
	fm.HiddenGrpContNo.value = AcceptGrpPlanGrid.getRowColData(tGrpSelNo-1, 2);
	
	mOperate="SAVEPLAN";
	submitForm();
}

//�����޸�
function applyModify(){

    var tSelNo = QueryModItemGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ���Ѿ���Ч�ı�����Ϣ��");
		return false;
	}
	if(fm.ApplyState.value=="��Ч"){	
		mOperate="APPLYMODIFY";
		submitForm();
	}else{		
		alert("�ù���δ��Ч������ֱ�ӽ���[�ύ����]������");
		return false;
	}
}


//�ύ����
function submitClick(){
	
    var tSelNo = QueryModItemGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;
	}
	if(fm.ApplyState.value=="��Ч"){
		alert("�ñ�������Ч����ͨ��[�����޸�]����ܽ���[�ύ����]��");
		return false;
	}else{
		mOperate="SUBMIT";
		submitForm();
	}
}



//������Ϣ��ѯ
function queryCheckInfo(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql8");
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	var tCheckInfo = document.getElementById("CheckInfo");
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length<=0) {
		tCheckInfo.style.display = "none";
		return false;
		
	} else {
		
		tCheckInfo.style.display = "";
		}
		
		if (tArr.length>=1) {
			fm.CheckConclusion.value=tArr[0][0];
			fm.CheckConclusionName.value=tArr[0][1];
			fm.CheckIdea.value=tArr[0][2];
		}
}

