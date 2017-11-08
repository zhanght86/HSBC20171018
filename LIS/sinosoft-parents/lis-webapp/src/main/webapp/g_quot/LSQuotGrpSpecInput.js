/***************************************************************
 * <p>ProName：LSQuotGrpSpecInput.js</p>
 * <p>Title：特别约定</p>
 * <p>Description：特别约定</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-01
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();


/**
 * 查询已保存特别约定
 */
function quryGrpSpec() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotGrpSpecSql");
	tSQLInfo.setSqlId("LSQuotGrpSpecSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult !== null && arrResult !== "") {
		fm.GrpSpec.value = arrResult[0][0];
	}
}

/**
 * 初始化查询特别约定标准信息
 */
function queryGrpSpecInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotGrpSpecSql");
	tSQLInfo.setSqlId("LSQuotGrpSpecSql1");
	tSQLInfo.addSubPara("1");
	
	//turnPage1.queryModal(tSQLInfo.getString(), GrpSpecInfoGrid, 2);
	if (!noDiv(turnPage1, GrpSpecInfoGrid, tSQLInfo.getString())) {
		initGrpSpecInfoGrid();
		return false;
	}
}


/**
 * 选择按钮
 */
function selectClick() {
	
	//判断是否选中了标准特别约定信息
	var chkFlag=false;
	for( var i=0;i<GrpSpecInfoGrid.mulLineCount; i++ ) {
	
		if( GrpSpecInfoGrid.getChkNo(i)) {	
			
			chkFlag = true;
			break;
		}
	}
	if(!chkFlag) {
		alert("请选择标准特别约定信息！");
		return false;
	}
	//获取选中信息
	var tGrpSpecApp = "";
	for( var mRow=0;mRow<GrpSpecInfoGrid.mulLineCount; mRow++ ) {
		if( GrpSpecInfoGrid.getChkNo(mRow)) {	
			var tGrpSpec   = GrpSpecInfoGrid.getRowColData(mRow , 1); 
			tGrpSpecApp += tGrpSpec+"；";
		}
	}
	var tempGrpSpec = fm.GrpSpec.value;//页面当前信息
	fm.GrpSpec.value = tGrpSpecApp + tempGrpSpec;
	GrpSpecInfoGrid.checkBoxAllNot();//清空所有勾选
	
}

/**
 * 保存
 */
function saveClick() {
	
	var tGrpSpec = fm.GrpSpec.value;
	if (tGrpSpec == null || tGrpSpec == "") {
		alert("请选择标准特别约定信息！");
		return false;
	}
	if (parseInt(tGrpSpec.length,10) >1500) {
		alert("特别约定信息应小于1500字！");
		return false;
	}
	mOperate = "SAVE";
	submitForm();
}

/**
 * 提交
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
	fm.action = "./LSQuotGrpSpecSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	fm.submit();
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
	
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

		showInfo.focus();
	}
}

