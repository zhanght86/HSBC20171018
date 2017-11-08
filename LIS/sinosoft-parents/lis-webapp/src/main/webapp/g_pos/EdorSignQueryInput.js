/***************************************************************
 * <p>ProName：EdorSignQueryInput.js</p>
 * <p>Title：保全签发</p>
 * <p>Description：保全签发</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 保全受理信息查询
 */
function queryClick(QueryFlag) {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql11");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.ScanOperator.value);
	tSQLInfo.addSubPara(fm.ScanStartDate.value);
	tSQLInfo.addSubPara(fm.ScanEndtDate.value);
	tSQLInfo.addSubPara(fm.AcceptOperator.value);
	tSQLInfo.addSubPara(fm.AcceptStartDate.value);
	tSQLInfo.addSubPara(fm.AcceptEndtDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorAppGrid, 1, 1);
	
	if (QueryFlag==1) {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/**
 * 保全批单信息查询
 */
function showEdorInfo() {
	
	var tSelNo = EdorAppGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo-1, 2);
	
	divEdorGrid.style.display = "";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql12");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), EdorGrid, 1, 1);
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
	fm.submit();
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content, patch, fileName1) {
	
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
		
		if(mOperate=="PRINT"){
			downFile(patch,fileName1);
		}else {
			initForm();
			divEdorGrid.style.display = "none";
			queryClick(2);
		}
	}
}

/**
 * 保全签发
 */
function edorSign() {
	
	var tSelNo = EdorAppGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tSelNo = EdorAppGrid.getSelNo()-1;
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo, 2);
	var tGrpContNo = EdorAppGrid.getRowColData(tSelNo, 3);
	
	fm.Operate.value="EDORSIGN";
	mOperate = "EDORSIGN";
	fm.action = "./EdorSignSave.jsp?EdorAppNo="+ tEdorAppNo +"&GrpContNo="+ tGrpContNo;
	submitForm(fm);
}

/**
 * 打印
 */
function printY() {
	
	var tSelNo = EdorAppGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条保全受理信息");
		return false;
	}
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo,2);
	
	mOperate = "PRINT";
	fm.action="../g_print/EdorPrintSave.jsp?Operate="+mOperate+"&EdorAppNo="+tEdorAppNo;
	submitForm();
}

/**
** 文件下载
**/
function downFile(patch,fileName1){
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
}
