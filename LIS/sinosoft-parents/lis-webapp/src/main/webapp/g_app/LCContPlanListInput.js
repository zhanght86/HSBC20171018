/***************************************************************
 * <p>ProName：LCContPlanListSave.jsp</p>
 * <p>Title：投保方案录入任务池</p>
 * <p>Description：投保方案录入任务池</p>
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
function queryPlanClick(o) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanListSql");
	tSQLInfo.setSqlId("LCContPlanListSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.EscanCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	
	initContPlanListGrid();
	turnPage1.queryModal(tSQLInfo.getString(), ContPlanListGrid, 1, 1);
	if (!turnPage1.strQueryResult && o==1) {
		alert("未查询到符合条件的查询结果！");
	}
	
}

/**
 * 查询扫描列表信息
 */
function querySelfPlanClick() {
	initSelfContPlanListGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanListSql");
	tSQLInfo.setSqlId("LCContPlanListSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.queryModal(tSQLInfo.getString(), SelfContPlanListGrid, 1, 1);
} 

/**
 * 进入方案录入
 */
function enterContPlan() {
	var tRow = SelfContPlanListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;	
	}
	var tSelNo = SelfContPlanListGrid.getSelNo()-1;
	var tMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 2);
	var tActivityID = SelfContPlanListGrid.getRowColData(tSelNo, 3);
	var tContPlanType = SelfContPlanListGrid.getRowColData(tSelNo, 4);
	var tGrpPropNo = SelfContPlanListGrid.getRowColData(tSelNo, 6);
	window.location = "./LCContPlanTradInput.jsp?ContPlanType="+ tContPlanType +"&GrpPropNo="+ tGrpPropNo + "&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
}


/**
 * 申请
 */
function applyClick() {
	var tRow = ContPlanListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;	
	}
	var tSelNo = ContPlanListGrid.getSelNo()-1;
	var tMissionID = ContPlanListGrid.getRowColData(tSelNo, 1);
	var tActivityID = ContPlanListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = ContPlanListGrid.getRowColData(tSelNo, 2);  
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=APPLY";
	submitForm(fm);
}

/**
 * 退回
 */
function reApplyClick() {
	var tRow = SelfContPlanListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;	
	}
	var tSelNo = SelfContPlanListGrid.getSelNo()-1;
	var tMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 1);
	var tActivityID = SelfContPlanListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = SelfContPlanListGrid.getRowColData(tSelNo, 2);  
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
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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

		showInfo.focus();
		queryPlanClick(0);
 		querySelfPlanClick();
	}	
}
