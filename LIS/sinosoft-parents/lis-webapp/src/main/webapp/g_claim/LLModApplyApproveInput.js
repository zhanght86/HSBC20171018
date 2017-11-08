/***************************************************************
 * <p>ProName：LLModApplyApproveInput.js</p>
 * <p>Title：总公司理赔审批</p>
 * <p>Description：总公司理赔审批</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王超
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();



var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交数据
 */
function submitForm() {
	
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.action = "./LLModApplyApproveSave.jsp";
	fm.submit(); //提交
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		if( mOperate="SUBMITTHREE"){
		
			clearData();
			queryClick();
		}
	}
}

// 查询保项

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
		alert("未查询到符合条件的查询结果！");
		return false;
	}else{
		clearData();
	}
}

function showClmModApplyInfo() {

	var tSelNo = QueryModItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的保项信息！");
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

//清空数据
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



//已选择保单信息
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


//已选择保单方案信息
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


//保项审批
function Submit(){

	 var i = QueryModItemGrid.getSelNo();
	 if (i<1) {
		 alert("请选择一条保项信息！");
		 return false;
	 }
  
     if(fm.ApproveConclusion.value=="" || fm.ApproveConclusion.value==null){
         alert("审批结论为必录项");
         return false;
         
     }
     if(fm.ApproveIdea.value=="" || fm.ApproveIdea.value==null){
         alert("审批意见为必录项");
         return false;
     }
     
         mOperate="SUBMITTHREE";
         submitForm();
   	
}

