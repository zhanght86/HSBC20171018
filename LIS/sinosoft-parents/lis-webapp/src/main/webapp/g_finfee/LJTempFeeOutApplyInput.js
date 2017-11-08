/***************************************************************
 * <p>ProName：LJTempFeeOutApplyInput.js</p>
 * <p>Title：暂收退费申请</p>
 * <p>Description：暂收退费申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;


/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
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
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	   var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	dealAfterSubmit(FlagStr);
}


function dealAfterSubmit(o) {

	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	
	if (tOperate=="APPLYCLICK") {
		
		document.getElementById("ApplyClickButton").disabled = false;
		
		if (o!="Fail") {
		
			initTempFeeInfoGrid();
			queryApplyedInfo();
		}
	} else if (tOperate=="CANCELSUBMIT") {
		
		document.getElementById("CancelSubmitButton").disabled = false;
		
		if (o!="Fail") {
		
			queryApplyedInfo();
		}
	} else if (tOperate=="APPLYSUBMIT") {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		
		if (o!="Fail") {
		
			queryApplyedInfo();
		}
	} else if (tOperate=="MODIFYBANK") {
		
		document.getElementById("ModifyBankButton").disabled = false;
		
		if (o!="Fail") {
		
			queryApplyedInfo();
		}
	}
}

/**
 * 查询暂收费数据
 */
function queryInfo() {

	initTempFeeInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryInBankCode.value);
	tSQLInfo.addSubPara(fm.QueryInBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);

	turnPage1.queryModal(tSQLInfo.getString(), TempFeeInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 查询已申请的暂收费数据
 */
function queryApplyedInfo() {
	
	initTempFeeOutInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql2");
	tSQLInfo.addSubPara(tOperator);
	
	turnPage2.queryModal(tSQLInfo.getString(), TempFeeOutInfoGrid, 0, 1);
}

/**
 * 退费申请
 */
function applyClick() {
	
	tOperate = "APPLYCLICK";
	document.getElementById("ApplyClickButton").disabled = true;
	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		document.getElementById("ApplyClickButton").disabled = false;
		alert("请选择要申请的记录！");
		return false;
	}
	
	var tBussNo = TempFeeInfoGrid.getRowColData(tSelNo, 3);
	var tMoney = TempFeeInfoGrid.getRowColData(tSelNo, 6);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo +"&Money="+ tMoney;
	submitForm(fm);
}

/**
 * 申请提交
 */
function applySubmit() {

	tOperate = "APPLYSUBMIT";
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	document.getElementById("ApplySubmitButton").disabled = true;
	if (tSelNo<0) {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		alert("请选择要提交的记录！");
		return false;
	}
	
	/*modify by songsz 20150120 屏蔽该处校验为后台校验
	var tProvince = TempFeeOutInfoGrid.getRowColData(tSelNo, 11);
	var tCity = TempFeeOutInfoGrid.getRowColData(tSelNo, 13);
	if (tProvince==null || tProvince=="" || tCity==null || tCity=="") {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		alert("银行信息不全，请先进行银行信息修改！");
		return false;
	}*/
	
	
	var tBankCode = TempFeeOutInfoGrid.getRowColData(tSelNo, 23);
	if (tBankCode==null || tBankCode=="") {
		
		document.getElementById("ApplySubmitButton").disabled = false;
		alert("银行信息不全，请先进行银行信息修改！");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}

/**
 * 撤销申请
 */
function cancelSubmit() {
	
	tOperate = "CANCELSUBMIT";
	document.getElementById("CancelSubmitButton").disabled = true;
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		document.getElementById("CancelSubmitButton").disabled = false;
		alert("请选择要提交的记录！");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}

function modifyBankInfo() {
	
	tOperate = "MODIFYBANK";
	document.getElementById("ModifyBankButton").disabled = true;
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		document.getElementById("ModifyBankButton").disabled = false;
		alert("请选择要提交的记录！");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutApplySave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}


/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "暂收费信息列表";
	
	var tTitle = "管理机构^|暂交费号^|交费方式^|金额^|到账日期^|客户开户银行^|客户银行账号^|客户账户名" +
			"^|付款单位^|支票号^|收款银行^|收款账号";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql5");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryInBankCode.value);
	tSQLInfo.addSubPara(fm.QueryInBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
