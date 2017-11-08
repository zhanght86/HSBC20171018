/***************************************************************
 * <p>ProName：LLClaimCasePrintInput.js</p>
 * <p>Title：立案打印</p>
 * <p>Description：立案打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();


/**
 * 批次查询
 */
function queryBatch() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCasePrintSql");
	tSQLInfo.setSqlId("LLClaimCasePrintSql");
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(fm.PrintState.value);
	tSQLInfo.addSubPara(fm.ConfirmStartDate.value);
	tSQLInfo.addSubPara(fm.ConfirmEndDate.value);	
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),BatchListGrid, 2);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
	
}


/**
 * 批次打印
 */
function batchPrint() {
	
	/*if(mManageCom.length<=2){
		alert("不允许总公司打印!");
		return false;
	}*/
	var i = BatchListGrid.getSelNo();
	
	if(i < 1){
		alert("请选中打印批次信息列表的一行记录！");
		return false;
	}
	var tBatchNo=BatchListGrid.getRowColData(i-1,1);
	if(tBatchNo===''||tBatchNo=="null"){
		alert("请先查询！");
		return false;
	}
	fm.tGrpRgtNo.value=tBatchNo;
	fm.Operate.value="BatchPrint";
	submitForm();
}


/**
 * 个人查询
 */
function querySingle() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCasePrintSql");
	tSQLInfo.setSqlId("LLClaimCasePrintSql1");
	tSQLInfo.addSubPara(fm.SingleGrpName.value);
	tSQLInfo.addSubPara(fm.SingleCustomerName.value);
	tSQLInfo.addSubPara(fm.SingleIdNo.value);	
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara("");
				
	turnPage2.queryModal(tSQLInfo.getString(),CustomerListGrid, 2);
	
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}

/**
 * 查询选中的客户信息
 */
function showSelectInfo () {
	
	var tCloseReason = document.getElementById("divRgtClose");
	tCloseReason.style.display = "";
	fm.NoRgtReason.value = "";
	fm.NoRgtReasonName.value = "";
}

/**
 * 关闭个人案件
 */
/*function rgtClose () {
	
	var i = CustomerListGrid.getSelNo()-1;
	if (i<0) {
		alert("请先选则一条个人案件信息！");
		return;
	}	
	var tRegisterNo = CustomerListGrid.getRowColData(i,2);
	fm.RgtNo.value = tRegisterNo;
	var tCustomerNo = CustomerListGrid.getRowColData(i,4);
	fm.CustomerNo.value = tCustomerNo;		
	
	if (fm.NoRgtReason.value==null || fm.NoRgtReason.value=="") {
		alert("请录入关闭原因！");
		return false;
	}
	
	fm.Operate.value = "CLOSE";
	submitForm();
}
*/
/**
 * 个人打印
 */
function singlePrint() {
	
/*	if(mManageCom.length<=2){
		alert("不允许总公司打印!");
		return false;
	}*/
	
	fm.Operate.value="Print";
	submitForm();
}

/**
 * 提交数据
 */
function submitForm() {
	
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
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content,patch,fileName1) {
	
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
	} else if(fm.Operate.value=="CLOSE") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}	else if(fm.Operate.value=="BatchPrint"||fm.Operate.value=="Print"){
		
		window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1
}
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
//批次信息单击触发方法
function showSelectPrintInfo(){
	
	
	var i = BatchListGrid.getSelNo()-1;		
	var tGrpRgtNo = BatchListGrid.getRowColData(i,1);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCasePrintSql");
	tSQLInfo.setSqlId("LLClaimCasePrintSql1");
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara("");	
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(tGrpRgtNo);
	turnPage2.queryModal(tSQLInfo.getString(),CustomerListGrid, 2);
	
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}