/***************************************************************
 * <p>ProName：LJTempFeeEnterInput.js</p>
 * <p>Title：进账录入</p>
 * <p>Description：进账录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryApplyTempFee() {
	
	initTempFeeInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql2");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInputStartDate.value);
	tSQLInfo.addSubPara(fm.QueryInputEndDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), TempFeeInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 选择时处理
 */
function showTempFeeInfo() {
	
	divInfo4.style.display = "";

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	fm.PayType.value = TempFeeInfoGrid.getRowColData(tSelNo, 2);
	fm.PayTypeName.value = TempFeeInfoGrid.getRowColData(tSelNo, 3);
	fm.CustBankCode.value = TempFeeInfoGrid.getRowColData(tSelNo, 4);
	fm.CustBankName.value = TempFeeInfoGrid.getRowColData(tSelNo, 5);
	fm.CustBankAccNo.value = TempFeeInfoGrid.getRowColData(tSelNo, 6);
	fm.CustAccName.value = TempFeeInfoGrid.getRowColData(tSelNo, 7);
	fm.Money.value = TempFeeInfoGrid.getRowColData(tSelNo, 8);
	fm.GrpName.value = TempFeeInfoGrid.getRowColData(tSelNo, 10);
	fm.AgentName.value = TempFeeInfoGrid.getRowColData(tSelNo, 12);
	
	var tInputConclusion = TempFeeInfoGrid.getRowColData(tSelNo, 15);
	fm.InputConclusion.value = tInputConclusion;
	fm.InputConclusionName.value = TempFeeInfoGrid.getRowColData(tSelNo, 16);
	fm.EnterAccDate.value = TempFeeInfoGrid.getRowColData(tSelNo, 17);
	fm.InBankCode.value = TempFeeInfoGrid.getRowColData(tSelNo, 18);
	fm.InBankCodeName.value = TempFeeInfoGrid.getRowColData(tSelNo, 19);
	fm.InBankAccNo.value = TempFeeInfoGrid.getRowColData(tSelNo, 20);
	fm.DraweeName.value = TempFeeInfoGrid.getRowColData(tSelNo, 21);
	fm.CheckNo.value = TempFeeInfoGrid.getRowColData(tSelNo, 22);
	showDisplay(tInputConclusion);
}

/**
 * 提交
 */
function confirmTempFee() {
	
	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择要进行操作的记录！");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);
	
	if (isEmpty(fm.InputConclusion)) {
		alert("请选择录入结论！");
		return false;
	}
	
	dealRefund();
	
	if (!checkData()) {
		return false;
	}
	
	tOperate = "ENTERTEMPFEE";
	fm.action = "./LJTempFeeEnterSave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

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
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		initQueryInfo();
		initTempFeeInfoGrid();
		initEnterInfo();
	}
}

function afterCodeSelect(cCodeType, Field) {

	if (cCodeType=="inputconclusion") {
		
		var tValue = Field.value;
		showDisplay(tValue);
	}
}

/**
 * 动态隐藏域展示
 */
function showDisplay(cFlag) {
	
	if (cFlag=="00") {
	
		document.all("tr1").style.display = "";
		document.all("tr3").style.display = "none";	
		var tPayType = fm.PayType.value;
		if (tPayType=="02") {
			document.all("tr2").style.display = "";
		} else {
			document.all("tr2").style.display = "none";
		}
	} else if (cFlag=="01") {
		
		document.all("tr1").style.display = "none";
		document.all("tr2").style.display = "none";
		document.all("tr3").style.display = "";	
	} else {
		
		document.all("tr1").style.display = "none";
		document.all("tr2").style.display = "none";
		document.all("tr3").style.display = "none";
	}
}

/**
 * 初始查询数据
 */
function initQueryInfo() {

	fm.QueryPayType.value = "";
	fm.QueryPayTypeName.value = "";
	fm.QueryCustBankCode.value = "";
	fm.QueryCustBankName.value = "";
	fm.QueryCustBankAccNo.value = "";
	fm.QueryCustAccName.value = "";
	fm.QueryGrpName.value = "";
	fm.QueryAgentName.value = "";
	fm.QueryApplyStartDate.value = "";
	fm.QueryApplyEndDate.value = "";
	fm.QueryInputStartDate.value = "";
	fm.QueryInputEndDate.value = "";
}

/**
 * 初始录入数据域
 */
function initEnterInfo() {
	
	divInfo4.style.display = "none";
	
	fm.PayType.value = "";
	fm.PayTypeName.value = "";
	fm.CustBankCode.value = "";
	fm.CustBankName.value = "";
	fm.CustBankAccNo.value = "";
	fm.CustAccName.value = "";
	fm.Money.value = "";
	fm.GrpName.value = "";
	fm.AgentName.value = "";
	
	fm.InputConclusion.value = "";
	fm.InputConclusionName.value = "";
	fm.EnterAccDate.value = "";
	fm.InBankCode.value = "";
	fm.InBankCodeName.value = "";
	fm.InBankAccNo.value = "";
	fm.DraweeName.value = "";
	fm.CheckNo.value = "";
	fm.InputDesc.value = "";

	document.all("tr1").style.display = "none";
	document.all("tr2").style.display = "none";
	document.all("tr3").style.display = "none";
}

/**
 * 处理冗余数据
 */
function dealRefund() {
	
	var tInputConclusion = fm.InputConclusion.value;
	if (tInputConclusion=="00") {
		
		fm.InputDesc.value = "";
	} else {
		
		fm.EnterAccDate.value = "";
		fm.InBankCode.value = "";
		fm.InBankCodeName.value = "";
		fm.InBankAccNo.value = "";
		fm.DraweeName.value = "";
		fm.CheckNo.value = "";
	}
}

/**
 * 校验数据
 */
function checkData() {

	var tInputConclusion = fm.InputConclusion.value;
	if (tInputConclusion=="00") {
		
		if (isEmpty(fm.EnterAccDate)) {
			alert("到账日期不能为空！");
			return false;
		}
		
		if (tCurrentDate<fm.EnterAccDate.value) {
			alert("到账日期应不晚于当前日期！");
			return false;
		}
		
		if (isEmpty(fm.InBankCode)) {
			alert("收款银行不能为空！");
			return false;
		}
		/**
		if (isEmpty(fm.InBankAccNo)) {
			alert("收款账号不能为空！");
			return false;
		}
		*/
		
		var tPayType = fm.PayType.value;
		if (tPayType=="02") {
			
			if (isEmpty(fm.DraweeName)) {
				alert("付款单位不能为空！");
				return false;
			}
			
			if (isEmpty(fm.CheckNo)) {
				alert("支票号不能为空！");
				return false;
			}
		}
	} else {
		
		var tInputDesc = trim(fm.InputDesc.value);
		if (tInputDesc==null || tInputDesc=="") {
			alert("结论描述不能为空！");
			return false;
		}
		
		if (tInputDesc.length>300) {
			alert("结论描述长度应在300字内！");
			return false;
		}
	}
	
	return true;
}

function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=='infinbank') {
		
		var tSql = "1 and a.fincomcode in (select b.findb from ldcom b where b.comcode like #"+ tManageCom +"%#)";
		if (returnType=='0') {
			return showCodeList('infinbank',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('infinbank',value2,value3,null,tSql,'1','1',null);
		}
	}
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "进账申请信息列表";
	
	var tTitle = "暂交费号^|交费方式^|客户开户行^|客户银行账号^|客户账户名^|金额^|投保单位名称^|客户经理" +
			"^|申请人^|申请日期^|收款银行^|收款账号^|付款单位^|支票号^|退回日期^|退回原因";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql10");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInputStartDate.value);
	tSQLInfo.addSubPara(fm.QueryInputEndDate.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
