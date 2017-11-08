/***************************************************************
 * <p>ProName：LCGrpApproveListSave.jsp</p>
 * <p>Title：复核任务池</p>
 * <p>Description：复核任务池</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-04-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage3 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询扫描列表信息
 */
function queryClick(o) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpApproveListSql");
	tSQLInfo.setSqlId("LCGrpApproveListSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	//tSQLInfo.addSubPara(tManageCom);//分公司及支公司只能操作建工险保单，总公司操作其他类型保单
	initGrpApproveListGrid();
	turnPage1.queryModal(tSQLInfo.getString(), GrpApproveListGrid, 1, 1);
	if (!turnPage1.strQueryResult&& o==1) {
		alert("未查询到符合条件的查询结果！");
	}

}

/**
 * 查询个人池列表信息
 */
function querySelfClick() {
	initSelfGrpApproveListGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpApproveListSql");
	tSQLInfo.setSqlId("LCGrpApproveListSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	turnPage2.queryModal(tSQLInfo.getString(), SelfGrpApproveListGrid, 1, 1);
}

/**
 * 进入录入
 */
function enterGrpApprove() {
	var tRow = SelfGrpApproveListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;
	}
	var tSelNo = SelfGrpApproveListGrid.getSelNo()-1;
	var tMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 1);
	var tSubMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 2);
	var tActivityID = SelfGrpApproveListGrid.getRowColData(tSelNo, 3);
	var tContPlanType = SelfGrpApproveListGrid.getRowColData(tSelNo, 4);
	var tGrpPropNo = SelfGrpApproveListGrid.getRowColData(tSelNo, 6);
	var tBussNo = SelfGrpApproveListGrid.getRowColData(tSelNo, 12);

		//window.location="./LCGrpContPolInput.jsp?GrpPropNo="+ tGrpPropNo+"&ContPlanType="+tContPlanType
	//+"&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag=1";
	var strUrl="./LCGrpContPolESMain.jsp?GrpPropNo="+ tGrpPropNo+"&ContPlanType="+tContPlanType
	+"&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag=1&ScanFlag=1&BussNo="+tBussNo+"&BussType=G_NB&SubType=21001,21002,21003,21004,21005,21006,21007,21008,21009";

	window.open(strUrl,'LCGrpContPolESMain','width='+screen.availWidth+',height='+screen.availHeight+',channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * 申请
 */
function applyClick() {
	var tRow = GrpApproveListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;
	}
	var tSelNo = GrpApproveListGrid.getSelNo()-1;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpApproveListSql");
	tSQLInfo.setSqlId("LCGrpApproveListSql3");
	tSQLInfo.addSubPara(GrpApproveListGrid.getRowColData(tSelNo, 5));

	var topera = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if(tOperator == topera){
		alert("复核人员不能与新单录入人员是同一人,请更换操作人员!");
		return false;
	}
	var tMissionID = GrpApproveListGrid.getRowColData(tSelNo, 1);
	var tActivityID = GrpApproveListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = GrpApproveListGrid.getRowColData(tSelNo, 2);
	fm.action ="./LCBussListSave.jsp?MissionID="+ tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Operate=APPLY";
	submitForm(fm);
}

/**
 * 退回
 */
function reApplyClick() {
	var tRow = SelfGrpApproveListGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;
	}
	var tSelNo = SelfGrpApproveListGrid.getSelNo()-1;
	var tMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 1);
	var tActivityID = SelfGrpApproveListGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = SelfGrpApproveListGrid.getRowColData(tSelNo, 2);
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
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var showInfo =  window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		 showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		var showInfo =    window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		 showInfo.focus();
		queryClick(0);
 		querySelfClick();
	}
}
