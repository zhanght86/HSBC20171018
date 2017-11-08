/***************************************************************
 * <p>ProName：BalanceApproveInput.js</p>
 * <p>Title：结算审批</p>
 * <p>Description：结算审批</p>
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
function queryClick() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceApproveSql");
	if(_DBT==_DBM){
		tSQLInfo.setSqlId("BalanceApproveSql1");
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.StartBalanceDate.value);
		tSQLInfo.addSubPara(fm.EndBalanceDate.value);
		tSQLInfo.addSubPara(fm.Days.value);
		tSQLInfo.addSubPara(fm.BalancePeriod.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(tManageCom);
   }else if(_DBT==_DBO)
   {
	   tSQLInfo.setSqlId("BalanceApproveSql3");
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.StartBalanceDate.value);
		tSQLInfo.addSubPara(fm.EndBalanceDate.value);
		tSQLInfo.addSubPara(fm.Days.value);
		tSQLInfo.addSubPara(fm.BalancePeriod.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(fm.Managecom.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(tManageCom);
   }
		
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 0, 1);
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的查询结果!");
		return false;
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
	initContInfoGrid();
	initInpBox();
	DivPosInfo.style.display='none';
}
/**
*展示保全信息
*/

function showPosInfo(){
	DivPosInfo.style.display='';
	var tRow = ContInfoGrid.getSelNo()-1;
	var tBalanceAppNo=ContInfoGrid.getRowColData(tRow,2);
	var tGrpContNo=ContInfoGrid.getRowColData(tRow,3);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_balance.BalanceApproveSql");
	tSQLInfo.setSqlId("BalanceApproveSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tBalanceAppNo);
	turnPage2.queryModal(tSQLInfo.getString(), PosInfoGrid, 0, 1);
	var rowNum = PosInfoGrid.mulLineCount;
	 
	fm.SumMoney.value=ContInfoGrid.getRowColData(tRow,8);;
}

/**
*保存审批结论
*/
function saveApprove(){

	var rowNum = ContInfoGrid.getSelNo()-1;

	if(rowNum<0){	
		alert("请至少选择一条记录!");
		return false;
	}	
	if(fm.ApproveFlag.value==null||fm.ApproveFlag.value==''){
		alert("请录入审批结论!");
		return false;
	}
	if(fm.ApproveDesc.value==null||fm.ApproveDesc.value==''){
		alert("请录入审批描述!");
		return false;
	}
	fm.action = "./BalanceApproveSave.jsp?Operate=APPROVE";
	submitForm(fm);
}
