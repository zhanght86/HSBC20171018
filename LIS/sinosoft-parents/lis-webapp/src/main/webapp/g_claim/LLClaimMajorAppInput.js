/***************************************************************
 * <p>ProName：LLClaimMajorMainInput.js</p>
 * <p>Title：重大案件上报公共池界面</p>
 * <p>Description：重大案件上报公共池界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询报案列表
 */
function queryClick() {
	
	//校验录入机构级别
	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMajorAppSql");
	tSQLInfo.setSqlId("LLClaimMajorAppSql2");
	tSQLInfo.addSubPara(mManageCom);
	tArr = null;
	tArr = easyExecSql(tSQLInfo.getString());
	//判断是否查询成功
	if (tArr==null || tArr.length==0) {
		alert("未查询到登录机构的机构级别!");
		return false;
	} else {
		tComGrade = tArr[0][0];
	}
			
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMajorAppSql");	
	if (tComGrade=="01") {
		
		tSQLInfo.setSqlId("LLClaimMajorAppSql1");		
	} else if (tComGrade=="02") {
		
		tSQLInfo.setSqlId("LLClaimMajorAppSql");
	}
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.RptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.RptDateStart.value);
	tSQLInfo.addSubPara(fm.RptDateEnd.value);
	tSQLInfo.addSubPara(mManageCom);	
	
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimReportGrid,2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return;
	}	
}

/**
 * 查询报案明细
 */
function showRptDetail() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择公共池中一条报案信息");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		alert("请先查询出险人事件信息！");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=3&RptNo="+ tRptNo;	
}

/**
 * 进入案件
 */
function enterReport(parm1) {
	
	var tSelNo;
	if (parm1!=null && parm1!="") {
		tSelNo = document.all(parm1).all("LLClaimReportGridNo").value;
		tSelNo = tSelNo - turnPage1.pageIndex*turnPage1.pageLineNum;
		LLClaimReportGrid.radioSel(tSelNo);
		tSelNo = tSelNo - 1;
	} else {
		tSelNo = LLClaimReportGrid.getSelNo() - 1;
	}	
	
	if (tSelNo<0) {
		alert("请选择公共池中一条报案信息");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		alert("请先查询出险人事件信息！");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=4&RptNo="+ tRptNo;	
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = showDialogWindow(urlStr, 0);
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
		//showDialogWindow(urlStr, 1);
		var name='提示';   //网页名称，可为空; 
		var iWidth=1;      //弹出窗口的宽度; 
		var iHeight=1;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.submit();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showDialogWindow(urlStr, 1);
		var name='提示';   //网页名称，可为空; 
		var iWidth=1;      //弹出窗口的宽度; 
		var iHeight=1;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.submit();
	}	
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
