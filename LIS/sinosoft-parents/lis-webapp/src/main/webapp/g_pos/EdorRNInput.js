/***************************************************************
 * <p>ProName：EdorRNInput.js</p>
 * <p>Title：不定期缴费</p>
 * <p>Description：不定期缴费</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

//原被保险人信息查询
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	tSQLInfo.setSqlId("EdorRNSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的查询结果!");
		return false;
	}
}

//修改过的被保险人信息查询
function queryUpClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	tSQLInfo.setSqlId("EdorRNSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(NullToEmpty(tEdorNo));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);
	
	if(o=='1'){
		if(!turnPage2.strQueryResult){
			alert("未查询到符合条件的查询结果!");
			return false;
		}
	}
}

//选择操作
function selectClick(){
	
	var rowNum = OldInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (OldInsuredInfoGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("请至少选择一条记录!");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate="UPDATE";
	fm.action = "./EdorRNSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

//撤销操作
function deleteOperate(){
	
	var tSelNo = UpdateInsuredInfoGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条记录！");
		return false;
	}
	
	mOperate="DELETE";
	fm.action = "./EdorRNSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

/**
 * 查询被保人缴费项信息
 **/
function queryPayPlan(){
	
	var tSelNo= UpdateInsuredInfoGrid.getSelNo();
	
	var tSerialNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tBatchNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,2);
	var tInsuredID = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,3);
	var tInsuredName = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,4);
	var tInsuredIDNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,5);
	
	initPayPlanGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("EdorRNSql3");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("EdorRNSql5");
	   }
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tInsuredName);
	tSQLInfo.addSubPara(tInsuredIDNo);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(tBatchNo);
	tSQLInfo.addSubPara(tInsuredID);
	
	turnPage3.queryModal(tSQLInfo.getString(), PayPlanGrid, 1, 1);
	
	divPayPlan.style.display = "";
	divInvest.style.display = "none";
}

/**
 * 查询投资账户信息
 **/
function queryInvest(){
	
	var tInsuredListSelNo= UpdateInsuredInfoGrid.getSelNo();
	var tInsuredID = UpdateInsuredInfoGrid.getRowColData(tInsuredListSelNo-1,3);
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条缴费项信息！");
		return false;
	}
	
	var tPolNo = PayPlanGrid.getRowColData(tSelNo-1, 1);
	var tDutyCode = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanCode = PayPlanGrid.getRowColData(tSelNo-1, 4);
	
	initInvestGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	tSQLInfo.setSqlId("EdorRNSql4");
	tSQLInfo.addSubPara(tPolNo);
	tSQLInfo.addSubPara(tDutyCode);
	tSQLInfo.addSubPara(tPayPlanCode);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(tInsuredID);
	
	turnPage4.queryModal(tSQLInfo.getString(), InvestGrid, 1, 1);
	
	if (InvestGrid.mulLineCount>0) {
		divInvest.style.display = "";
	} else {
		divInvest.style.display = "none";
	}
}

/**
 * 缴费信息保存
 **/
function payPlanSave(){
	
	var tInsuredListSelNo= UpdateInsuredInfoGrid.getSelNo();
	if (tInsuredListSelNo==0) {
		alert("请选择一条被保险人信息！");
		return false;
	}
	
	fm.InsuredID.value = UpdateInsuredInfoGrid.getRowColData(tInsuredListSelNo-1,3);
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条缴费项信息！");
		return false;
	}
	
	var tDutyCode = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanCode = PayPlanGrid.getRowColData(tSelNo-1, 4);
	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 6);
	
	if (tPayPlanPrem =="") {
		alert("缴费金额不能为空！");
		return false;
	}
	
	if (!isNumeric(tPayPlanPrem)) {
		alert("缴费金额需要录入大于等于0的数字！");
		return false;
	}
	
	if (tPayPlanPrem<0) {
		alert("缴费金额需要录入大于等于0的数字！");
		return false;
	}
	
	if (tPayPlanPrem>0 && InvestGrid.mulLineCount>1) {
		
		var tInvestMoneyFlag = false;//投资金额录入标志
		var tInvestRateFlag = false;//投资分配比例录入标志
		var tSumInvestMoney = 0;
		var tSumInvestRate = 0;
		for (var i=0;i < InvestGrid.mulLineCount;i++) {
			
			var tInvestMoney = InvestGrid.getRowColData(i,6);
			var tInvestRate = InvestGrid.getRowColData(i,7);
			
			if (tInvestMoney=="" && tInvestRate=="") {
				alert("第【"+(i+1)+"】行投资金额与投资分配比例不能同时为空！");
				return false;
			}
			
			if (tInvestMoney!="" && tInvestRate!="") {
				alert("第【"+(i+1)+"】行投资金额与投资分配比例不能同时录入！");
				return false;
			}
			
			if (tInvestMoney!="") {
				
				tInvestMoneyFlag = true;
				
				if (!isNumeric(tInvestMoney)) {
					alert("第【"+(i+1)+"】行的投资金额需要录入大于等于0的数字！");
					return false;
				}
				
				if (tInvestMoney<0) {
					alert("第【"+(i+1)+"】行的投资金额需要录入大于等于0的数字！");
					return false;
				}
				
				tSumInvestMoney = tSumInvestMoney + parseFloat(tInvestMoney);
			}
			
			if (tInvestRate!="") {
				
				tInvestRateFlag = true;
				
				if (!isNumeric(tInvestRate)) {
					alert("第【"+(i+1)+"】行的投资分配比例需要录入0-1之间的小数！");
					return false;
				}
				
				if (tInvestRate>1 || tInvestRate<0) {
					alert("第【"+(i+1)+"】行的投资分配比例需要录入0-1之间的小数！");
					return false;
				}
				
				tSumInvestRate = tSumInvestRate + parseFloat(tInvestRate);
			}
		}
		
		if (tInvestMoneyFlag && tInvestRateFlag) {
			alert("投资金额与投资分配比例不能同时录入！");
			return false;
		}
		
		if (tInvestMoneyFlag && tSumInvestMoney!=tPayPlanPrem) {
			alert("投资金额总和需要与缴费金额相等！");
			return false;
		}
		
		if (tInvestRateFlag && tSumInvestRate!=1) {
			alert("投资分配比例之和不等于1！");
			return false;
		}
	}
	
	mOperate = "PayPlanSave";
	fm.action = "./EdorRNSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

function submitFunc(){
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	divPayPlan.style.display = "none";
	divInvest.style.display = "none";
	queryOldClick();
	queryUpClick(2);
}
