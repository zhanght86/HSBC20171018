/***************************************************************
 * <p>ProName：LCEdorRefundCalInput.js</p>
 * <p>Title：保全退费算法维护</p>
 * <p>Description：保全退费算法维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

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
	}
	querySubmit('JS');
	
	if (mOperate == "DELETE") {
		
		if (RefundListGrid.mulLineCount == 0) {
			fm2.RiskCode1.value = "";
			fm2.RiskName1.value = "";
			fm2.GetType1.value = "";
			fm2.GetTypeName1.value = "";
		}
	}
	//initForm();
}

/**
 * 查询退费信息列表
 */
function querySubmit(Obj) {
	
	clearRefundInfo();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContRefundCalSql");
	tSQLInfo.setSqlId("LCContRefundCalSql1");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm2.RiskCode1.value);
	tSQLInfo.addSubPara(fm2.GetType1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), RefundListGrid, 1, 1);
	
	if (Obj=='Page') {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	} 
}

/**
 * 展示退费信息维护信息
 */
function showRefundList() {
	
	var tRow = RefundListGrid.getSelNo();
	fm2.RiskCode2.value = RefundListGrid.getRowColData(tRow-1,1);
	fm2.RiskName2.value = RefundListGrid.getRowColData(tRow-1,2);
	fm2.GetType2.value = RefundListGrid.getRowColData(tRow-1,8);
	fm2.GetTypeName2.value = RefundListGrid.getRowColData(tRow-1,3);
	fm2.ValPeriod.value = RefundListGrid.getRowColData(tRow-1,9);
	fm2.ValPeriodName.value = RefundListGrid.getRowColData(tRow-1,6);
	fm2.ValStartDate.value = RefundListGrid.getRowColData(tRow-1,4);
	fm2.ValEndDate.value = RefundListGrid.getRowColData(tRow-1,5);
	fm2.FeeValue.value = RefundListGrid.getRowColData(tRow-1,7);
	
}

/**
 * 保全退费算法维护--增加
 */
function addSubmit() {
	
	mOperate = "INSERT";
	if (!verifyInput2()) {
		return false;
	}
	if(!beforeSub()){
		return false;
	}
	if (!checkValPeriod()) {
		return false;
	}
	showPages();
	fm2.action = "./LCEdorRefundCalSave.jsp?Operate="+ mOperate +"&ObjType="+ tObjType +"&BussType="+ tBussType +"&ContPlanType="+ tContPlanType +"&BussNo="+ tBussNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * 保全退费算法维护--修改
 */
function modSubmit() {
	
	mOperate = "UPDATE";
	var tRow = RefundListGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if (!verifyInput2()) {
		return false;
	}
	if(!beforeSub()){
		return false;
	}
	if (!checkValPeriod()) {
		return false;
	}
	var tSerialNo = RefundListGrid.getRowColData(tRow-1,10);//流水号
	fm2.SerialNo.value = tSerialNo;
	showPages();
	fm2.action = "./LCEdorRefundCalSave.jsp?Operate="+ mOperate +"&ObjType="+ tObjType +"&BussType="+ tBussType +"&ContPlanType="+ tContPlanType +"&BussNo="+ tBussNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * 保全退费算法维护--删除
 */
function delSubmit() {
	
	var tRow = RefundListGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	
	var tSerialNo = RefundListGrid.getRowColData(tRow-1,10);//流水号
	fm2.SerialNo.value = tSerialNo;
	
	mOperate = "DELETE";
	fm2.action = "./LCEdorRefundCalSave.jsp?Operate="+ mOperate +"&ObjType="+ tObjType +"&BussType="+ tBussType +"&ContPlanType="+ tContPlanType +"&BussNo="+ tBussNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * 校验单位，同一险种下，仅可选同一种单位
 */
function checkValPeriod() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContRefundCalSql");
	tSQLInfo.setSqlId("LCContRefundCalSql2");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm2.RiskCode2.value);
	
	var tNo = "";
	if (mOperate == "INSERT") {
		tSQLInfo.addSubPara(tNo);
	} else if (mOperate == "UPDATE") {
		var tRow = RefundListGrid.getSelNo();
		tNo = RefundListGrid.getRowColData(tRow-1,10);//流水号
		tSQLInfo.addSubPara(tNo);
	}
	tSQLInfo.addSubPara(fm2.GetType2.value);
	var arrResult = easyExecSql(tSQLInfo.getString());
	
	if (arrResult !== null && arrResult[0][0] !== "") {
		var tValPeriod = arrResult[0][0];
		if (tValPeriod != fm2.ValPeriod.value) {
			alert("对同一产品进行维护时，仅可选择同一种单位!");
			return false;
		}
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContRefundCalSql");
	tSQLInfo.setSqlId("LCContRefundCalSql4");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm2.RiskCode2.value);
	tSQLInfo.addSubPara(fm2.GetType2.value);
	tSQLInfo.addSubPara(fm2.ValPeriod.value);
	tSQLInfo.addSubPara(fm2.ValStartDate.value);
	tSQLInfo.addSubPara(fm2.ValEndDate.value);
	tSQLInfo.addSubPara(fm2.ValStartDate.value);
	tSQLInfo.addSubPara(fm2.ValEndDate.value);
	tSQLInfo.addSubPara(fm2.ValStartDate.value);
	tSQLInfo.addSubPara(fm2.ValEndDate.value);
	tSQLInfo.addSubPara(tNo);
	var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tRes!=null && tRes[0]=='1'){
		alert("该退费信息已经存在，或者区间交叉!");
		return false;
	}
	return true;
}

/**
 * 清除退费信息维护信息
 */
function clearRefundInfo() {
	
	fm2.RiskCode2.value = "";
	fm2.RiskName2.value = "";
	fm2.GetType2.value = "";
	fm2.GetTypeName2.value = "";
	fm2.ValPeriod.value = "";
	fm2.ValPeriodName.value = "";
	fm2.ValStartDate.value = "";
	fm2.ValEndDate.value = "";
	fm2.FeeValue.value = "";
}

/**
 * 新增、修改后界面展示
 */
function showPages() {
	
	if (fm2.RiskCode1.value!=null && fm2.RiskCode1.value != "") {
		fm2.RiskCode1.value = fm2.RiskCode2.value;
		fm2.RiskName1.value = fm2.RiskName2.value;
	}
	if (fm2.GetType1.value!=null && fm2.GetType1.value != "") {
		fm2.GetType1.value = fm2.GetType2.value;
		fm2.GetTypeName1.value = fm2.GetTypeName2.value;
	}
}

/**
 * 查询系统默认退费信息维护
 */
function querySysRefundInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContRefundCalSql");
	tSQLInfo.setSqlId("LCContRefundCalSql3");
	tSQLInfo.addSubPara(tBussNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), SysRefundInfoGrid, 1, 1);
	
}
function beforeSub(){

	if (parseInt(fm2.ValStartDate.value) >= parseInt(fm2.ValEndDate.value)) {
		alert("起始值应小于终止值！");
		return false;
	}
	if(parseInt(fm2.FeeValue.value)>1||parseInt(fm2.FeeValue.value)<0){
		alert("费用比例应为大于0且小于1的数字!");
		return false;
	}
	
	return true;
}

/**
 * 选择管理费类型后展示界面要素
 */
function afterCodeSelect(o, p) {
	
	if(o=="contplanrisk"){
		fm2.GetType2.value="";
		fm2.GetTypeName2.value="";
		fm2.ValPeriod.value="";
		fm2.ValPeriodName.value="";
		fm2.ValStartDate.value="";
		fm2.ValEndDate.value="";
		fm2.FeeValue.value="";
	} else if(o=="gettype"){
		fm2.ValPeriod.value="";
		fm2.ValPeriodName.value="";
		fm2.ValStartDate.value="";
		fm2.ValEndDate.value="";
		fm2.FeeValue.value="";
	} else if(o=="insuperiodflag"){
		fm2.ValStartDate.value="";
		fm2.ValEndDate.value="";
		fm2.FeeValue.value="";
	}
}
