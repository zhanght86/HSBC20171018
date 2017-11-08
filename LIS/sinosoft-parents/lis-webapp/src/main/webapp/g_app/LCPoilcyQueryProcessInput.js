/***************************************************************
 * <p>ProName：LCPoilcyQueryProcessInput.js</p>
 * <p>Title：承保保单流程节点查询</p>
 * <p>Description：承保保单流程节点查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-08-09
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
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content,cOfferListNo) {
	
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
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPoilcyQueryProcessSql");
	tSQLInfo.setSqlId("LCPoilcyQueryProcessSql1");
	tSQLInfo.addSubPara(fm.GrpPrtNo.value);//投保单号
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(fm.GrpPrtNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);//保单号
	tSQLInfo.addSubPara(fm.GrpName.value);
	if(fm.ManageCom.value!=null&&fm.ManageCom.value!=''){
		tSQLInfo.addSubPara(fm.ManageCom.value);
	}else{
		tSQLInfo.addSubPara(tManageCom);
	}
	tSQLInfo.addSubPara(fm.NowAddress.value);
	tSQLInfo.addSubPara(fm.NowAddress.value);
	initProcessGrid();
	initTraceGrid();
	turnPage1.queryModal(tSQLInfo.getString(), ProcessGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}


/**
 * 查询轨迹 
 */
function queryTrace() {
	
	var tSelNo = ProcessGrid.getSelNo();
	var tGrppolNo = ProcessGrid.getRowColData(tSelNo-1, 2);
	var tGrpContNo= ProcessGrid.getRowColData(tSelNo-1, 3);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPoilcyQueryProcessSql");
	tSQLInfo.setSqlId("LCPoilcyQueryProcessSql2");
	tSQLInfo.addSubPara(tGrppolNo);

	initTraceGrid();
	turnPage2.queryModal(tSQLInfo.getString(), TraceGrid, 1, 1);
}
