/***************************************************************
 * <p>ProName：LLClaimReportMainInput.js</p>
 * <p>Title：报案申请界面</p>
 * <p>Description：报案申请界面</p>
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
 * 查询公共池
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportAppSql");
	tSQLInfo.setSqlId("LLClaimReportAppSql");
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IdNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.QueryRptNo.value);
	tSQLInfo.addSubPara(fm.RptDateStart.value);
	tSQLInfo.addSubPara(fm.RptDateEnd.value);
	tSQLInfo.addSubPara(fm.RptState.value);
	tSQLInfo.addSubPara(fm.OperatorDateStart.value);
	tSQLInfo.addSubPara(fm.OperatorDateEnd.value);
	tSQLInfo.addSubPara(fm.RptCom.value);
	tSQLInfo.addSubPara(fm.RptFlag.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.rptstate.value);
		
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimReportGrid,2);
		
}
/**
 * 查询个人池案件
 */
function querySelfClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportAppSql");
	tSQLInfo.setSqlId("LLClaimReportAppSql1");
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(mOperator);	
	
	turnPage2.queryModal(tSQLInfo.getString(),SelfLLClaimReportGrid,2);		
}
/**
 * 查看报案明细
 */
function showReportDetail() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择公共池中一条报案信息");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	fm.RptNo.value = tRptNo;
	if (tRptNo==null || tRptNo=="") {
		alert("请先查询出险人事件信息！");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=3&RptNo="+ tRptNo;
}
/**
 * 申请
 */
function applyReport() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择公共池下一条报案信息");
		return false;
	}
	fm.RptNo.value = LLClaimReportGrid.getRowColData(tSelNo,1);
	
	var tRptState = LLClaimReportGrid.getRowColData(tSelNo,14);
	if (tRptState!=null && tRptState=="1") {
		alert("该报案已确认，不能申请！");
		return false;
	}	
	
	fm.Operate.value="APPLY";
	submitForm();
}
/**
 * 进入报案
 */
function enterReport(parm1) {
	
	var tSelNo;
	if (parm1!=null && parm1!="") {
		tSelNo = document.all(parm1).all("SelfLLClaimReportGridNo").value;
		tSelNo = tSelNo - turnPage2.pageIndex*turnPage2.pageLineNum;
		SelfLLClaimReportGrid.radioSel(tSelNo);
		tSelNo = tSelNo - 1;
	} else {
		tSelNo = SelfLLClaimReportGrid.getSelNo() - 1;
	}
	
	if (tSelNo<0) {
		alert("请选择个人池中一条报案信息");
		return false;
	}
	var tRptNo = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	fm.RptNo.value = tRptNo;
	if (tRptNo==null || tRptNo=="") {
		alert("请先查询报案人信息！");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=2&RptNo="+ tRptNo;
}
/**
 * 释放报案
 */
function releaseReport() {
	
	var tSelNo = SelfLLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择个人池下一条报案信息");
		return false;
	}
	fm.RptNo.value = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	fm.Operate.value="RELEASE";
	submitForm();	
}
/**
 * 新增报案
 */
function newReport() {
	window.location.href="LLClaimReportInput.jsp?Type=1";
}
/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	fm.submit();
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
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		initForm();
	}	
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
/**
 * 选择报案启动按钮
 */
function selectReportButton(){
	//公共池情况
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if(tSelNo<0){
		alert("请选择公共池池下的一条报案信息！");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	var tRptState = LLClaimReportGrid.getRowColData(tSelNo,14);
	if(tRptState ==2){
		fm.reportOpen.style.display = "";
		fm.reportClose.style.display = "none";
	}else{
		fm.reportOpen.style.display = "none";
		fm.reportClose.style.display = "";
	}
}

//个人池情况
function selectSelfReportButton(){
	
	var tSelNo1 = SelfLLClaimReportGrid.getSelNo()-1;
	if(tSelNo1<0){
		alert("请选择个人池下的一条报案信息！");
		return false;
	}
	var tRptNo1 = SelfLLClaimReportGrid.getRowColData(tSelNo1,1);
	var tRptState1 = SelfLLClaimReportGrid.getRowColData(tSelNo1,14);
	if(tRptState1==2){
		fm.reportOpen.style.display = "";
		fm.reportClose.style.display = "none";
	}else{
		fm.reportOpen.style.display = "none";
		fm.reportClose.style.display = "";
	}
}
/**
 * 报案关闭
 */
function closeReport() {
	
	var tSelNo = SelfLLClaimReportGrid.getSelNo()-1;
	if(tSelNo<0){
		alert("请选择个人池下的一条报案信息！");
		return false;
	}
	fm.RptNo.value = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	
	fm.Operate.value="CLOSE";
	submitForm();	
	
}
/**
 * 案件启动
 */
function openReport(){
	var tSelNo = SelfLLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择个人池下一条报案信息");
		return false;
	}
	fm.RptNo.value = SelfLLClaimReportGrid.getRowColData(tSelNo,1);
	
	fm.Operate.value="OPEN";
	submitForm();	
	
}
