/***************************************************************
 * <p>ProName：LSQuotProjQueryInput.js</p>
 * <p>Title：项目询价录入查询</p>
 * <p>Description：项目询价录入查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, cQuotNo, cQuotBatNo) {
	
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
		
		dealAfterSubmit(cQuotNo, cQuotBatNo);
	}	
}

function dealAfterSubmit(cQuotNo, cQuotBatNo) {

	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=='APPLYET'||tOperate=='AGAINET') {
		
		if (!confirm("是否现在进行询价录入？")) {
			fm.PIQuotNo.value = cQuotNo;
			queryPIClick('2');
			return false;
		} else {
			fm.PIQuotNo.value = cQuotNo;
			queryPIClick('1');
			//PIQuotInfoGrid.radioSel(1);
			enterQuot();
			fm.PIQuotNo.value =="";
			return true;
		}
	}
}

/**
 * 查询待录入询价信息
 */
function queryPIClick(cFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjQuerySql");
	tSQLInfo.setSqlId("LSQuotProjQuerySql1");
	if (cFlag == '1') {//页面查询
		tSQLInfo.addSubPara(fm.ProjName.value);
		tSQLInfo.addSubPara(fm.PIQuotNo.value);
		tSQLInfo.addSubPara(tOperator);
	} else if (cFlag == '2') {//申请点击否后处理界面展示
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.PIQuotNo.value);
		tSQLInfo.addSubPara(tOperator);
	}
	tSQLInfo.addSubPara(tManageCom);
	
	initPIQuotInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PIQuotInfoGrid, 0, 1);
	
	if (cFlag == '1') {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}


/**
 * 进入询价项目询价基本信息
 */
function enterQuot() {
	
	tSelNo = PIQuotInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		tSelNo = 0;
	}
	tMissionID = PIQuotInfoGrid.getRowColData(tSelNo, 3);
	tSubMissionID = PIQuotInfoGrid.getRowColData(tSelNo, 4);
	tActivityID = PIQuotInfoGrid.getRowColData(tSelNo, 5);
	tQuotNo = PIQuotInfoGrid.getRowColData(tSelNo, 6);
	tQuotBatNo = PIQuotInfoGrid.getRowColData(tSelNo, 7);
	tQuotType = PIQuotInfoGrid.getRowColData(tSelNo, 8);

	goToStep(1);
}

/**
 * 申请新的询价
 */
function applyPIClick() {
	
	fm.ProjName.value = "";
	fm.PIQuotNo.value = "";
	
	//申请环节校验应该在3级机构下进行
	if (tComGrade!="03") {
		alert("该操作应该在3级机构下进行！");
		return false;
	}
	
	fmPub.Operate.value = "APPLYET";
	fm.action = "./LSQuotProjQuerySave.jsp?Operate=APPLYET&QuotType=01";
	submitForm(fm);
}

/**
 * 查询已完成询价信息
 */
function queryFinishClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjQuerySql");
	tSQLInfo.setSqlId("LSQuotProjQuerySql2");
	tSQLInfo.addSubPara(fm1.FProjName.value);
	tSQLInfo.addSubPara(fm1.FPIQuotNo.value);
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	
	initFinishQuotInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(), FinishQuotInfoGrid, 1, 1);
	
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 明细查看
 */
function toPalnDetailView() {
	
	var tRow = FinishQuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条已完成询价信息！");
		return false;	
	}
	tQuotNo = FinishQuotInfoGrid.getRowColData(tRow-1,3);
	tQuotBatNo = FinishQuotInfoGrid.getRowColData(tRow-1,4);
	tQuotType = "01";
	showPalnDetailView();
}

/**
 * 再次询价
 */
function againProjClick() {
	
	fm1.FProjName.value = "";
	fm1.FPIQuotNo.value = "";
	
	var tRow = FinishQuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条已完成询价信息！");
		return false;	
	}
	tQuotNo = FinishQuotInfoGrid.getRowColData(tRow-1,3);
	tQuotBatNo = FinishQuotInfoGrid.getRowColData(tRow-1,4);
	
	fmPub.Operate.value = "AGAINET";
	fm1.action = "./LSQuotAgainApplySave.jsp?Operate=AGAINET&QuotType=01&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo;
	submitForm(fm1);
}
