/***************************************************************
 * <p>ProName：RenewalPrintInput.js</p>
 * <p>Title：催缴通知书打印</p>
 * <p>Description：催缴通知书打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_renewal.RenewalPrintSql");
	tSQLInfo.setSqlId("RenewalPrintSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.StartOperDate.value);
	tSQLInfo.addSubPara(fm.EndOperDate.value);
	tSQLInfo.addSubPara(fm.StartPayDate.value);
	tSQLInfo.addSubPara(fm.EndPayDate.value);
	tSQLInfo.addSubPara(fm.PayIntv.value);
	tSQLInfo.addSubPara(fm.PrintState.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	initContInfoGrid();
	initRiskInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(),ContInfoGrid, 0 , 1);
	if (o=="1" && !turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
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
		queryClick(0);
		if(mOperate=="PRINT"){
			downFile(patch,fileName1);
		}
	}
}

/**
** 文件下载
**/
function downFile(patch,fileName1){
	
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
	
}

/**
*展示险种信息
*/
function showRiskDivInfo(){
	DivRiskInfo.style.display='';
}

/**
 * 查询
 */
function showRiskInfo() {
	
	showRiskDivInfo();
	var tSelNo = ContInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	var getNoticeNo = ContInfoGrid.getRowColData(tSelNo, 1);
	var payCount = ContInfoGrid.getRowColData(tSelNo, 10);
	var grpContNo = ContInfoGrid.getRowColData(tSelNo, 3);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_renewal.RenewalCancleSql");
	tSQLInfo.setSqlId("RenewalCancleSql2");
	tSQLInfo.addSubPara(payCount);
	tSQLInfo.addSubPara(grpContNo);
	
	initRiskInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(),RiskInfoGrid, 1 , 1);
}

function PrintRenewal(){
	var tSelNo = ContInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	var payCount = ContInfoGrid.getRowColData(tSelNo, 10);
	var grpContNo = ContInfoGrid.getRowColData(tSelNo, 3);
	var renewalNo = ContInfoGrid.getRowColData(tSelNo, 1);
	fm.action = "./RenewalPrintSave.jsp?Operate=PRINT&GrpContNo="+grpContNo+"&PayCount="+payCount+"&RenewNo="+renewalNo;
	mOperate = "PRINT";
	submitForm();
}
