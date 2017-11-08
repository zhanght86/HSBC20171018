/***************************************************************
 * <p>ProName:EdorRDInput.js</p>
 * <p>Title:  部分领取</p>
 * <p>Description:部分领取</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-22
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	tSQLInfo.setSqlId("EdorRDSql1");
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	tSQLInfo.setSqlId("EdorRDSql2");
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
	fm.action = "./EdorRDSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	fm.action = "./EdorRDSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	if(_DBT==_DBM){
		tSQLInfo.setSqlId("EdorRDSql5");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tInsuredName);
		tSQLInfo.addSubPara(tInsuredIDNo);
		tSQLInfo.addSubPara(fm.EdorAppNo.value);
		tSQLInfo.addSubPara(tBatchNo);
		tSQLInfo.addSubPara(tInsuredID);
		tSQLInfo.addSubPara(tCurrenDate);
   }else if(_DBT==_DBO)
   {
	   tSQLInfo.setSqlId("EdorRDSql3");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tInsuredName);
		tSQLInfo.addSubPara(tInsuredIDNo);
		tSQLInfo.addSubPara(fm.EdorAppNo.value);
		tSQLInfo.addSubPara(tBatchNo);
		tSQLInfo.addSubPara(tInsuredID);
		tSQLInfo.addSubPara(tCurrenDate);
   }
		
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	tSQLInfo.setSqlId("EdorRDSql4");
	tSQLInfo.addSubPara(tPolNo);
	tSQLInfo.addSubPara(tDutyCode);
	tSQLInfo.addSubPara(tPayPlanCode);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(tInsuredID);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage4.queryModal(tSQLInfo.getString(), InvestGrid, 1, 1);
	
	if (InvestGrid.mulLineCount>0) {
		divInvest.style.display = "";
	} else {
		divInvest.style.display = "none";
	}
}

/**
 * 领取信息保存
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
	var tInsuAccBala = PayPlanGrid.getRowColData(tSelNo-1, 6);
	var tGetAmount = PayPlanGrid.getRowColData(tSelNo-1, 7);
	
	if (tGetAmount =="") {
		alert("缴费项领取金额不能为空！");
		return false;
	}
	
	if (!isNumeric(tGetAmount)) {
		alert("缴费项领取金额需要录入大于等于0的数字！");
		return false;
	}
	
	if (tGetAmount<0) {
		alert("缴费项领取金额需要录入大于等于0的数字！");
		return false;
	}
	
	if (parseFloat(tGetAmount)>parseFloat(tInsuAccBala)) {
		alert("领取金额不能大于账户本息和！");
		return false;
	}
	
	if (tGetAmount>0 && InvestGrid.mulLineCount>1) {
		
		var tInvestMoneyFlag = false;//投资金额录入标志
		var tSumInvestGetAmount = 0;
		for (var i=0;i < InvestGrid.mulLineCount;i++) {
			
			var tInvestGetAmount = InvestGrid.getRowColData(i,7);
			
			if (tInvestGetAmount=="") {
				alert("第【"+(i+1)+"】行的账户领取金额不能为空！");
				return false;
			}
			
			if (!isNumeric(tInvestGetAmount)) {
				alert("第【"+(i+1)+"】行的账户领取金额需要录入大于等于0的数字！");
				return false;
			}
				
			if (tInvestGetAmount<0) {
				alert("第【"+(i+1)+"】行的账户领取金额需要录入大于等于0的数字！");
				return false;
			}
				
			tSumInvestGetAmount = tSumInvestGetAmount + parseFloat(tInvestGetAmount);
		}
		
		if (tSumInvestGetAmount!=tGetAmount) {
			alert("账户领取金额总和需要与缴费项领取金额相等！");
			return false;
		}
	}
	
	mOperate = "PayPlanSave";
	fm.action = "./EdorRDSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
