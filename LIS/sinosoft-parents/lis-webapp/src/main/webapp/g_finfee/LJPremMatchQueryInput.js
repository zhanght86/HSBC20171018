/***************************************************************
 * <p>ProName：LJPremMatchQueryInput.js</p>
 * <p>Title：保费匹配</p>
 * <p>Description：保费匹配</p>
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
function queryMatchInfo() {
	
	initMatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql8");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara("");
	
	turnPage1.queryModal(tSQLInfo.getString(), MatchInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 进入匹配
 */
function goToMatch() {

	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要进行操作的记录！");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	location.href = "./LJPremMatchInput.jsp?MatchSerialNo="+ tMatchSerialNo;
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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content,filepath,tfileName) {
	
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
	var filepath1 =filepath+tfileName;
	 window.location = "../common/jsp/download.jsp?FilePath="+filepath1+"&FileName="+tfileName;
	 showInfo.focus();
	
	}
	
	dealAfterSubmit(FlagStr);
}

function printMatch() {
	
	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	var tOperate = "PRINT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJPremMatchPrintSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm);
}

function dealAfterSubmit(cSuccFlag) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="PRINT") {
		
		if (cSuccFlag!="Fail") {
			
			var tSelNo = MatchInfoGrid.getSelNo()-1;
			var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
			fm.ManageCom.value = "";
			fm.QueryStartDate.value = "";
			fm.QueryEndDate.value = "";
			initMatchInfoGrid();
	
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_finfee.LJMatchSql");
			tSQLInfo.setSqlId("LJMatchSql8");
			tSQLInfo.addSubPara(tManageCom);
			tSQLInfo.addSubPara(fm.ManageCom.value);
			tSQLInfo.addSubPara(fm.QueryStartDate.value);
			tSQLInfo.addSubPara(fm.QueryEndDate.value);
			tSQLInfo.addSubPara(tMatchSerialNo);
			
			turnPage1.queryModal(tSQLInfo.getString(), MatchInfoGrid, 0, 1);
		}
	}
}

function downLoadClick(parm1) {
	
	var tFlag = document.all(parm1).all("MatchInfoGrid13").value;
	if (tFlag!='1') {
		return;
	}
	
	var tMatchSerialNo = document.all(parm1).all("MatchInfoGrid3").value;
	var tFilePath = document.all(parm1).all("MatchInfoGrid15").value;
	
	var tFileName = tMatchSerialNo+".pdf";
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
	
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "保费申请信息列表";
	
	var tTitle = "管理机构^|保费申请批次^|收费金额^|使用溢缴金额^|应收金额^|本次溢缴金额^|申请人";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql20");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara("");
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
