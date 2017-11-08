/***************************************************************
 * <p>ProName：RenewalParaInput.js</p>
 * <p>Title：续期参数配置</p>
 * <p>Description：续期参数配置</p>
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
	tSQLInfo.setResourceName("g_renewal.RenewalParaSql");
	tSQLInfo.setSqlId("RenewalParaSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageComQ.value);
	tSQLInfo.addSubPara(fm.PayIntvQ.value);
	initResultInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(),ResultInfoGrid, 1 , 1);
	if (o=="1" && !turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}
function showGracePeriod(){
	if(fm.GracePeriodCheck.checked==true){
		DivGracePeriod.style.display=''
	}else{
		DivGracePeriod.style.display='none'
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
		if("POLICYSAVE"==mOperate){
			queryCont();
		} else {
			initInpBox();
			queryClick(0);
		}
	}
}
/**
 * 模拟下拉操作
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=="conditioncomcode") {
	 
		var tSql = "1 and comgrade=#03# and comcode like #" + tManageCom + "%%# ";
		if (returnType=='0') {
			return showCodeList('conditioncomcode',value2,value3,null,tSql,'1','1',180);
		} else {
			return showCodeListKey('conditioncomcode',value2,value3,null,tSql,'1','1',180);
		}
	}
}

function addClick(){
	
	if(!verifyInput()){
		return false;
	}
	
	mOperate="PARAADD";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}

function updateClick(){
	
	var tSelNo = ResultInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	
	if(!verifyInput()){
		return false;
	}
	
	mOperate="PARAUPDATE";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}

function deleteClick(){
	
	var tSelNo = ResultInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	
	mOperate="PARADELETE";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}
/**
* 展示信息
*/
function onShowInfo(){
	var tSelNo = ResultInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	fm.SerialNo.value = ResultInfoGrid.getRowColData(tSelNo, 1);
	fm.ManageCom.value = ResultInfoGrid.getRowColData(tSelNo, 2);
	fm.ManageComName.value = ResultInfoGrid.getRowColData(tSelNo, 3);
	fm.PayIntv.value = ResultInfoGrid.getRowColData(tSelNo, 4);
	fm.PayIntvName.value = ResultInfoGrid.getRowColData(tSelNo, 5);
	fm.PumpDays.value = ResultInfoGrid.getRowColData(tSelNo, 6);
	fm.GracePeriod.value = ResultInfoGrid.getRowColData(tSelNo, 7);
}

/**
* 展示保单信息
*/
function onPolicyInfo(){
	var tSelNo = ContInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	fm.PSerialNo.value = ContInfoGrid.getRowColData(tSelNo, 1);
	fm.PPolicyNo.value = ContInfoGrid.getRowColData(tSelNo, 3);
	fm.PGracePeriod.value = ContInfoGrid.getRowColData(tSelNo, 13);
}


/**
 * 查询
 */
function queryCont(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_renewal.RenewalParaSql");
	tSQLInfo.setSqlId("RenewalParaSql2");
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom2.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.SaleChnl.value);
	
	initContInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(),ContInfoGrid, 1 , 1);
	if (o=="1" && !turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function saveClick(){
	
	var tSelNo = ContInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	if (fm.PGracePeriod.value=="" || !isInteger(fm.PGracePeriod.value)) {
		alert("不是有效的整数！");
		return false;
	}
	if (parseFloat(fm.PGracePeriod.value)<0) {
		alert("请录入大于0的整数！");
		return false;
	}
	mOperate="POLICYSAVE";
	fm.action = "./RenewalParaSave.jsp?Operate="+ mOperate;
	submitForm();
}
