/***************************************************************
 * <p>ProName：GuaranteedRateInput.js</p>
 * <p>Title：保证利率公布</p>
 * <p>Description：保证利率公布</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-07-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
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
	fm.action = "./GuaranteedRateSave.jsp";
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		queryClick();
	}
}

/**
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_acc.RateSql");
	tSQLInfo.setSqlId("RateSql1");
	tSQLInfo.addSubPara(fm.QueryRiskCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), GuaranteedRateGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}

/**
 * 显示利率
 */
function showGuaranteedRateInfo() {
	
	var tSelNo = GuaranteedRateGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的列表信息！");
		return false;
	}
	
	fm.RiskCode.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 1);
	fm.RiskName.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 2);
	fm.InsuAccNo.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 3);
	fm.InsuAccName.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 4);
	fm.SRateDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 5);
	fm.ARateDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 6);
	fm.RateYear.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 7);
	fm.RateIntv.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 10);
	fm.RateIntvName.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 11);
	fm.Rate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 12);
	
	fm.StartDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 8);
	fm.EndDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 9);
}

/**
 * 新增
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * 修改
 */
function updateClick() {
	
	var tSelNo = GuaranteedRateGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条利率信息！");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "UPDATE";
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tSelNo = GuaranteedRateGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条利率信息！");
		return false;
	}
	
	if (!confirm("确认要删除该分支科目？")) {
		return false
	}
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * 重置
 */
function resetClick() {
	
	clearInfo();
	initGuaranteedRateGrid();
}

/**
 * 清除表单数据
 */
function clearInfo() {
	
	fm.RiskCode.value = "";
	fm.RiskName.value = "";
	fm.InsuAccNo.value = "";
	fm.InsuAccName.value = "";
	fm.SRateDate.value = "";
	fm.ARateDate.value = "";
	fm.RateYear.value = "";
	fm.RateIntv.value = "";
	fm.RateIntvName.value = "";
	fm.Rate.value = "";
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {
	
	if (Field.name=="RiskCode") {
		
		fm.InsuAccNo.value = "";
		fm.InsuAccName.value = "";
	}
}

/**
 * 查询账户之前的校验
 */
function beforQueryCode(obj, Field) {
	
	var tRiskCode = Field.value;
	if (tRiskCode==null || tRiskCode=="") {
		
		alert("请先选择险种编码！");
		field.focus();
		return false;
	}
	
	return true;
}

/**
 * 导出数据
 */
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "险种编码^|险种名称^|账户编码^|账户名称^|应公布日期^|实际公布日期^|保证年度^|利率开始日期^|利率结束日期^|利率类型编码^|利率类型^|利率^|录入人^|录入日期";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_acc.RateSql");
	tSQLInfo.setSqlId("RateSql1");
	tSQLInfo.addSubPara(fm.QueryRiskCode.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
