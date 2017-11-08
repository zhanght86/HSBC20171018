/***************************************************************
 * <p>ProName：LSQuotETQueryInput.js</p>
 * <p>Title：一般询价录入查询</p>
 * <p>Description：一般询价录入查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询待录入询价信息
 */
function queryETClick(cFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql1");
	if (cFlag == '1') {//页面查询
		
		tSQLInfo.addSubPara(fm.ETPreCustomerName.value);
		tSQLInfo.addSubPara(fm.ETQuotNo.value);
		tSQLInfo.addSubPara(tOperator);
	} else if (cFlag == '2') {//申请点击否后处理界面展示
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.ETQuotNo.value);
		tSQLInfo.addSubPara(tOperator);
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.PreCustomerNo.value);
	
	initETQuotInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), ETQuotInfoGrid, 0, 1);
	if (cFlag == '1') {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}


/**
 * 申请新的询价
 */
function applyETClick() {
	
	fm.ETPreCustomerName.value = "";
	fm.ETQuotNo.value = "";
	
	//申请环节校验应该在3级机构下进行
	if (tComGrade!="03") {
		alert("该操作应该在3级机构下进行！");
		return false;
	}
	
	fmPub.Operate.value = "APPLYET";
	fm.action = "./LSQuotETQuerySave.jsp?Operate=APPLYET&QuotType="+tETQuotType;
	submitForm(fm);
}

/**
 * 查询已完成询价信息
 */
function queryFinishClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql3");
	tSQLInfo.addSubPara(fm1.FPreCustomerName.value);
	tSQLInfo.addSubPara(fm1.FQuotNo.value);
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	
	initFinishQuotInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(), FinishQuotInfoGrid, 1, 1);
	
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 再次询价
 */
function againETClick() {
	
	fm1.FPreCustomerName.value = "";
	fm1.FQuotNo.value = "";
	
	var tRow = FinishQuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条已完成询价信息！");
		return false;	
	}
	tQuotNo = FinishQuotInfoGrid.getRowColData(tRow-1,3);
	tQuotBatNo = FinishQuotInfoGrid.getRowColData(tRow-1,4);
	
	fmPub.Operate.value = "AGAINET";
	fm1.action = "./LSQuotAgainApplySave.jsp?Operate=AGAINET&QuotType=00&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo;
	submitForm(fm1);
}

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
		
		dealAfterSubmit(cQuotNo, cQuotBatNo);
	}	
}

function dealAfterSubmit(cQuotNo, cQuotBatNo) {

	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=='APPLYET'||tOperate=='AGAINET') {
		
		if (!confirm("是否现在进行询价录入？")) {
			fm.ETQuotNo.value = cQuotNo;
			queryETClick('2');
			return false;
		} else {
			fm.ETQuotNo.value = cQuotNo;
			queryETClick('1');
			//ETQuotInfoGrid.radioSel(1);
			enterQuot();
			fm.ETQuotNo.value =="";
			return true;
		}
	} 
}

/**
 * 进入询价
 */
function enterQuot() {
	
	tSelNo = ETQuotInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		tSelNo = 0;
	}
	tMissionID = ETQuotInfoGrid.getRowColData(tSelNo, 3);
	tSubMissionID = ETQuotInfoGrid.getRowColData(tSelNo, 4);
	tActivityID = ETQuotInfoGrid.getRowColData(tSelNo, 5);
	tQuotNo = ETQuotInfoGrid.getRowColData(tSelNo, 6);
	tQuotBatNo = ETQuotInfoGrid.getRowColData(tSelNo, 7);
	tQuotType = ETQuotInfoGrid.getRowColData(tSelNo, 8);
	
	goToStep(1);
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
	tQuotType = "00";
	showPalnDetailView();
}
