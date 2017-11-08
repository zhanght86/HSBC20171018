/***************************************************************
 * <p>ProName：LCGrpPrintInput.js</p>
 * <p>Title：团体保单打印</p>
 * <p>Description：团体保单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-01
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交，保存按钮对应操作
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
	
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content,filepath,tfileName) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		var filepath1 =filepath+tfileName;
		window.location = "../common/jsp/download.jsp?FilePath="+filepath1+"&FileName="+tfileName;
		showInfo.focus();
	}
	fm.printButton.disabled=false;
	queryClick(0);
}

/**
 * 查询
 */
function queryClick(tFlag) {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.SaleChnl.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult && tFlag==1) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 保单打印
 */
function print() {
	
	var i = ContInfoGrid.getSelNo();
	if (i<1) {
		alert("请选择保单信息！");
		return false;
	}
	var tManageCom = ContInfoGrid.getRowColData(i-1,1);
	var tGrpContNo = ContInfoGrid.getRowColData(i-1,2);
	var tGrpPropNo = ContInfoGrid.getRowColData(i-1,3);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	var tRiskCode = easyExecSql(tSQLInfo.getString(), 1, 0, 1, 1);
	
	if(tRiskCode!=null){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
		tSQLInfo.setSqlId("LCGrpPrintSql3");
		tSQLInfo.addSubPara(tGrpContNo);
		var tPrintCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tPrintCount=='1'){
			alert("该保单已打印!");
			return false;
		}
	}
	mOperate = "PRINT";
	
	fm.action="./LCGrpPrintSave.jsp?GrpContNo="+tGrpContNo+"&GrpPropNo="+tGrpPropNo+"&ManageCom="+tManageCom;
	submitForm();
	fm.Operate.value = mOperate;
	fm.printButton.disabled=true;
	fm.submit(); //提交
}

/**
 * 被保险人个人凭证打印
 */
function insuredPrint() {
	
	var i = ContInfoGrid.getSelNo();
	if (i<1) {
		alert("请选择保单信息！");
		return false;
	}
	
	var tManageCom = ContInfoGrid.getRowColData(i-1,1);
	var tGrpContNo = ContInfoGrid.getRowColData(i-1,2);
	var tGrpPropNo = ContInfoGrid.getRowColData(i-1,3);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	var tRiskCode = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tRiskCode == null){
		alert("不是年金产品,不允许此操作!");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_print.LCGrpPrintSql");
	tSQLInfo.setSqlId("LCGrpPrintSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	var tPrintCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPrintCount==null){
		alert("请先打印保单!");
		return false;
	}
	mOperate = "NJPRINT";
	
	fm.action="./LCGrpPrintSave.jsp?GrpContNo="+tGrpContNo+"&GrpPropNo="+tGrpPropNo+"&ManageCom="+tManageCom;
	submitForm();
	fm.Operate.value = mOperate;
	fm.submit(); //提交
}
