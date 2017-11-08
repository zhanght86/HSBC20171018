/***************************************************************
 * <p>ProName：EdorCheckDetailInput.js</p>
 * <p>Title：保全复核</p>
 * <p>Description：保全复核</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 受理信息查询
 */
function queryEdorAppInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCheckSql");
	tSQLInfo.setSqlId("EdorCheckSql1");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.AppMode.value = strQueryResult[0][1];
		fm.AppDate.value = strQueryResult[0][2];
		fm.ReceiveDate.value = strQueryResult[0][3];
		fm.AcceptDate.value = strQueryResult[0][4];
		fm.PolicyNo.value = strQueryResult[0][5];
		fm.HidGrpContNo.value = strQueryResult[0][5];
		fm.AppntName.value = strQueryResult[0][6];
		fm.ValDate.value = strQueryResult[0][7];
		fm.PayIntv.value = strQueryResult[0][8];
		fm.BanlanceFlag.value = strQueryResult[0][9];
		fm.AfterClmRule.value = strQueryResult[0][10];
	} else {
		alert("查询保全受理信息失败！");
		return false;
	}
}

/**
 * 保全项目查询
 */
function queryEdorTypeInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCheckSql");
	tSQLInfo.setSqlId("EdorCheckSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorTypeGrid, 1, 1);
}

/**
 * 保全项目明细
 */
function detailClick() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一个保全项目！");
		return false;
	}
	
	var tGrpContNo = fm.HidGrpContNo.value;
	var tEdorType = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	
	window.open("./EdorTypeDetailMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&EdorType="+tEdorType,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content,patch,fileName1) {
	
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
		var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		var   showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		initForm();
		
		if("CONFIRM"==fm.Operate.value){
			returnClick();
		}
		if(mOperate=="PRINT"){
			downFile(patch,fileName1);
		}
	}
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}

/**
 * 复核完毕
 */
function confirmClick() {
	
	fm.Operate.value="CONFIRM";
	mOperate = "CONFIRM";
	var tGrpContNo = fm.HidGrpContNo.value;
	
	fm.action = "./EdorCheckDetailSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm);
}

/**
* 返回查询页面
*/
function returnC(){	
	top.close();
}

/**
 * 保单既往保全查询
 */
function showOldEdor(){
	
	var tGrpContNo = fm.PolicyNo.value;
		
	strUrl="../g_sysQuery/EdorQueryMain.jsp?GrpContNo="+tGrpContNo+"&Flag=Old" ;
	window.open(strUrl,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

