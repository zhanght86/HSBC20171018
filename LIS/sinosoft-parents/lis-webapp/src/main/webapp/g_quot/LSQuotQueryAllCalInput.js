/***************************************************************
 * <p>ProName：LSQuotQueryAllCalInput.js</p>
 * <p>Title：汇总计算查询</p>
 * <p>Description：汇总计算查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-08-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 提交数据
 */
function submitForm(obj) {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
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
}

/**
 * 查询保单汇总信息列表
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.CValiDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	
	initExpCalTotalGrid();
	initExpMonthCMCalGrid();
	initExpCMCalGrid();
	turnPage1.queryModal(tSQLInfo.getString(), ExpCalTotalGrid, 0, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 查询详细信息
 */
function showDetail() {
	
	var tSelNo = ExpCalTotalGrid.getSelNo();
	var tPolicyNo = ExpCalTotalGrid.getRowColData(tSelNo-1, 1);
	
	queryExpMonthCMCal(tPolicyNo);
	queryExpCMCal(tPolicyNo);
}

/**
 * 查询逐月结案赔款金额列表
 */
function queryExpMonthCMCal(cPolicyNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(cPolicyNo);
	
	initExpMonthCMCalGrid();
	turnPage2.queryModal(tSQLInfo.getString(), ExpMonthCMCalGrid, 0, 1);
}

/**
 * 查询逐月结案赔款金额列表
 */
function queryExpCMCal(cPolicyNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(cPolicyNo);

	initExpCMCalGrid();
	turnPage3.queryModal(tSQLInfo.getString(), ExpCMCalGrid, 0, 1);
}

/**
 * 导出
 */
function expAllInfo() {
	
	fm.SheetName[0].value = "保单汇总信息";
	fm.SheetName[1].value = "逐月结案赔款金额";
	fm.SheetName[2].value = "预期赔付率";
	
	//保单汇总信息列表
	var tTitle1 = "保单号^|客户名称^|生效起期^|生效止期^|新契约保费^|新契约人数^|医疗险保费/总保费^|"
			+ "当年同期出险人数^|当年同期保费^|当年同期医疗险赔款^|当年同期非医疗险赔款^|当年同期赔付率^|"
			+ "截止目前就诊日期与结案日期平均时间间隔^|截止目前出险人数^|截止目前保费^|截止目前医疗险赔款^|截止目前非医疗险赔款^|截止目前赔付率";

	//逐月结案赔款金额列表
	var tTitle2 = "保单号^|客户名称^|生效起期^|生效止期^|第N个月^|第N个月赔款";
	
	//预期赔付率列表
	var tTitle3 = "保单号^|客户名称^|生效起期^|生效止期^|险种名称^|保费^|未决赔款^|结案赔款^|险种结案赔付率^|险种预期赔付率"; 
	
	fm.SheetTitle[0].value = tTitle1;
	fm.SheetTitle[1].value = tTitle2;
	fm.SheetTitle[2].value = tTitle3;
	
	//保单汇总信息列表
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	fm.SheetSql[0].value = tSQLInfo.getString();
	
	//逐月结案赔款金额列表
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	fm.SheetSql[1].value = tSQLInfo.getString();
	
	//预期赔付率列表
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryAllCalSql");
	tSQLInfo.setSqlId("LSQuotQueryAllCalSql6");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	fm.SheetSql[2].value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
