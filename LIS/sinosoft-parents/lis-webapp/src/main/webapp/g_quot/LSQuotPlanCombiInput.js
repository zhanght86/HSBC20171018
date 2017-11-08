/***************************************************************
 * <p>ProName：LSQuotPlanCombiInput.js</p>
 * <p>Title：方案组合配置</p>
 * <p>Description：方案组合配置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-02
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();


/**
 * 查询方案列表
 */
function queryPlanList() {
	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPlanCombiSql");
	tSQLInfo.setSqlId("LSQuotPlanCombiSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	//turnPage2.pageLineNum=2;
	
	//PlanListGrid.mulLineCount = 2;
	//turnPage2.queryModal(tSQLInfo.getString(), PlanListGrid, 1, 1);
	
	if (!noDiv(turnPage2, PlanListGrid, tSQLInfo.getString())) {
		initPlanListGrid();
		return false;
	}
}


/**
 * 查询不允许的方案组合
 */
function queryNoPlanCombi() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPlanCombiSql");
	tSQLInfo.setSqlId("LSQuotPlanCombiSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), NoPlanCombiGrid, 1, 1);
//	var rowNum = turnPage1.queryAllRecordCount;//总行数
//	if (parseInt(rowNum,10) > 10) {
//		document.getElementById("divTurnPage").style.display = '';
//	} else {
//		document.getElementById("divTurnPage").style.display = 'none';
//	}
}

/**
 * 新增
 */
function addClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	//判断是否选中了方案编码
	var chkFlag=false;
	for ( var i=0;i<PlanListGrid.mulLineCount; i++ ) {
	
		if( PlanListGrid.getChkNo(i)) {	
			chkFlag = true;
			break;
		}
	}
	if(!chkFlag) {
		alert("请选择方案信息！");
		return false;
	}
	
	mOperate = "INSERT";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotPlanCombiSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tRow = NoPlanCombiGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	var tSerialNo = NoPlanCombiGrid.getRowColData(tRow-1,1);
	mOperate = "DELETE";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotPlanCombiSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&SerialNo="+ tSerialNo;
	submitForm();

}

/**
 * 展示方案明细
 */
function showPlanDetail(parm1) {
	
	 var tPlanCode = document.all(parm1).all("PlanListGrid1").value;
	 var tSysPlanCode = document.all(parm1).all("PlanListGrid3").value;
	 var tMark = "1";//只查询一个方案的方案明细信息
	 var tActivityID = "";//只查询一个方案时，设置为空
	 window.open("./LSQuotPlanAllDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode +"&Mark="+ tMark,"方案明细",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
		initForm();
	}
}
