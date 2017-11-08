/***************************************************************
 * <p>ProName：LCInsuredListSave.jsp</p>
 * <p>Title：人员清单任务池</p>
 * <p>Description：人员清单任务池</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 甸景
 * @version  : 8.0
 * @date     : 2014-04-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询扫描列表信息
 */
function queryClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredListSql");
	tSQLInfo.setSqlId("LCInsuredListSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.EscanCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	initInsuredListGrid();
	turnPage1.queryModal(tSQLInfo.getString(), InsuredListGrid, 1, 1);
	if (!turnPage1.strQueryResult && o==1) {
		alert("未查询到符合条件的查询结果！");
	}
	
}

/**
 * 查询个人池列表信息
 */
function querySelfClick() {
	initSelfInsuredListGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredListSql");
	tSQLInfo.setSqlId("LCInsuredListSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.queryModal(tSQLInfo.getString(), SelfInsuredListGrid, 1, 1);
} 

/**
 * 进入录入
 */
function enterInsured() {
	var tRow = SelfInsuredListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;	
	}
	var tSelNo = SelfInsuredListGrid.getSelNo()-1;
	var tMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 2);
	var tActivityID = SelfInsuredListGrid.getRowColData(tSelNo, 3);
	var tContPlanType = SelfInsuredListGrid.getRowColData(tSelNo, 4);
	var tGrpPropNo = SelfInsuredListGrid.getRowColData(tSelNo, 6);
	window.location = "./LCInsuredInfoInput.jsp?ContPlanType="+ tContPlanType +"&GrpPropNo="+ tGrpPropNo + "&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	
}

/**
 * 申请
 */
function applyClick() {
	var tRow = InsuredListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;	
	}
	var tSelNo = InsuredListGrid.getSelNo()-1;
	var tMissionID = InsuredListGrid.getRowColData(tSelNo, 1);
	var tActivityID = InsuredListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = InsuredListGrid.getRowColData(tSelNo, 2);  
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=APPLY";
	submitForm(fm);
}

/**
 * 退回
 */
function reApplyClick() {
	var tRow = SelfInsuredListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;	
	}
	var tSelNo = SelfInsuredListGrid.getSelNo()-1;
	var tMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 1);
	var tActivityID = SelfInsuredListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = SelfInsuredListGrid.getRowColData(tSelNo, 2);  
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=REAPPLY";
	submitForm(fm);
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
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
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
		queryClick(0);
 		querySelfClick();
	}	
}
