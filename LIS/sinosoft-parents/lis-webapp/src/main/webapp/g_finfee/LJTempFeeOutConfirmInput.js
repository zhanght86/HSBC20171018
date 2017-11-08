/***************************************************************
 * <p>ProName：LJTempFeeOutConfirmInput.js</p>
 * <p>Title：暂收退费审核</p>
 * <p>Description：暂收退费审核</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
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
	}
	
	dealAfterSubmit(FlagStr);
}

function dealAfterSubmit(o) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="CONFIRMSUBMIT") {
		
		fm.ConfirmSubmitButton.disabled = false;
		if (o!="Fail") {
			
			initTempFeeOutInfoGrid();
			fm.ConfirmConclusion.value = "";
			fm.ConfirmConclusionName.value = "";
			fm.ConfirmDesc.value = "";
		}
	}
}

function queryInfo() {
	
	initTempFeeOutInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql3");
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
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);

	turnPage2.queryModal(tSQLInfo.getString(), TempFeeOutInfoGrid, 0, 1);
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function confirmSubmit() {
	
	fm.ConfirmSubmitButton.disabled = true;
	if (!checkConfirm()) {
		fm.ConfirmSubmitButton.disabled = false;
		return false;
	}
	
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要进行操作的数据！");
		return false;
	}
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	tOperate = "CONFIRMSUBMIT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJTempFeeOutConfirmSave.jsp?Operate="+ tOperate +"&BussNo="+ tBussNo;
	submitForm(fm);
}

function checkConfirm() {
	
	var tSelNo = TempFeeOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要进行操作的数据！");
		return false;
	}
	
	var tBussNo = TempFeeOutInfoGrid.getRowColData(tSelNo, 3);
	
	var tConfirmConclusion = fm.ConfirmConclusion.value;
	var tConfirmDesc = fm.ConfirmDesc.value;
	
	if (tConfirmConclusion==null || tConfirmConclusion=="") {
		alert("审核结论不能为空！");
		return false;
	}
	
	if (tConfirmConclusion=="01") {
		
		if (tConfirmDesc==null || tConfirmDesc=="") {
			alert("审核结论为不通过时，结论描述不能为空！");
			return false;
		}
	}
	
	if (tConfirmDesc.length>300) {
		alert("结论描述应在300字以内！");
		return false;
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
