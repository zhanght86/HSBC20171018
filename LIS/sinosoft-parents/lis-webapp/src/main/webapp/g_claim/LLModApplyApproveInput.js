/***************************************************************
 * <p>ProName��LLModApplyApproveInput.js</p>
 * <p>Title���ܹ�˾��������</p>
 * <p>Description���ܹ�˾��������</p>
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
	fm.action = "./LLModApplyApproveSave.jsp";
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
		
		if( mOperate="SUBMITTHREE"){
		
			clearData();
			queryClick();
		}
	}
}

// ��ѯ����

function queryClick() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql7");	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryRuleType.value); 
	tSQLInfo.addSubPara(fm.QueryRiskCode.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.ClmmodifyState.value); 
	tSQLInfo.addSubPara(fm.QueryReasonType.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryApplyDate.value);
	tSQLInfo.addSubPara(fm.QueryEndApplyDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryModItemGrid, 2, 1);
	if (!turnPage1.strQueryResult) {                    
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}else{
		clearData();
	}
}

function showClmModApplyInfo() {

	var tSelNo = QueryModItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�����ı�����Ϣ��");
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
	fm.ApplyNo.value = QueryModItemGrid.getRowColData(tSelNo-1, 18);
	fm.ApplyBatchNo.value = QueryModItemGrid.getRowColData(tSelNo-1, 19);
	fm.CheckConclusion.value = QueryModItemGrid.getRowColData(tSelNo-1, 20);
	fm.CheckConclusionName.value = QueryModItemGrid.getRowColData(tSelNo-1, 21);
	fm.CheckIdea.value = QueryModItemGrid.getRowColData(tSelNo-1, 22);
		
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
	
	if(fm.RuleType.value=='00' || fm.RuleType.value==""){
		document.all("divGrpCont").style.display="none";
		document.all("divGrpContPlan").style.display="none";
		queryCheckInfo();
	}else if(fm.RuleType.value=='01'){
		document.all("divGrpCont").style.display="";
		document.all("divGrpContPlan").style.display="none";
		queryAcceptGrpClick();
		queryCheckInfo();		
	}else{
		document.all("divGrpCont").style.display="none";
		document.all("divGrpContPlan").style.display="";		 	
		queryAcceptGrpaClick();
		queryCheckInfo();
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
	fm.CheckConclusion.value="";
	fm.CheckConclusionName.value="";	
	fm.CheckIdea.value="";
	fm.ApproveConclusion.value="";
	fm.ApproveConclusionName.value="";	
	fm.ApproveIdea.value="";
	
	initAcceptGrpGrid();
	initAcceptGrpaGrid();
}



//��ѡ�񱣵���Ϣ
function queryAcceptGrpClick(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql3");	
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.pageLineNum = 1000;
	turnPage2.queryModal(tSQLInfo.getString(), AcceptGrpGrid, 2, 1);	
}


//��ѡ�񱣵�������Ϣ
function queryAcceptGrpaClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql6");
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	
	turnPage3.pageLineNum = 1000;
	turnPage3.queryModal(tSQLInfo.getString(), AcceptGrpaGrid, 2, 1);
}


//��������
function Submit(){

	 var i = QueryModItemGrid.getSelNo();
	 if (i<1) {
		 alert("��ѡ��һ��������Ϣ��");
		 return false;
	 }
  
     if(fm.ApproveConclusion.value=="" || fm.ApproveConclusion.value==null){
         alert("��������Ϊ��¼��");
         return false;
         
     }
     if(fm.ApproveIdea.value=="" || fm.ApproveIdea.value==null){
         alert("�������Ϊ��¼��");
         return false;
     }
     
         mOperate="SUBMITTHREE";
         submitForm();
   	
}

