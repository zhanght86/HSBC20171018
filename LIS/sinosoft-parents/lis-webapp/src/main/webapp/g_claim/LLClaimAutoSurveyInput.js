/***************************************************************
 * <p>ProName：LLClaimSpotCheckInput.js</p>
 * <p>Title：理赔抽检</p>
 * <p>Description：理赔抽检</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tSQLInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

/**
 * 查询理赔规则
 */
function queryAutoRule() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAutoSurveySql");
	tSQLInfo.setSqlId("LLClaimAutoSurveySql");
	
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryRiskcode.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryUWMoney.value);
	tSQLInfo.addSubPara(fm.QueryPayMoney.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimRuleListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}
/**
 * 展示规则明细
 */
function showClaimRuleDetail() {
	
	var i = LLClaimRuleListGrid.getSelNo()-1;
	var tRuleNo = LLClaimRuleListGrid.getRowColData(i,1);
	if (tRuleNo==null || tRuleNo=="") {
		alert("请先查询！");
		return;
	}
	
	fm.RuleNo.value = tRuleNo;
	fm.CheckManageCom.value = LLClaimRuleListGrid.getRowColData(i,2);
	fm.CheckManageComName.value = LLClaimRuleListGrid.getRowColData(i,3);	
	fm.CheckRiskCode.value = LLClaimRuleListGrid.getRowColData(i,4);
	fm.CheckRiskName.value = LLClaimRuleListGrid.getRowColData(i,5);
	fm.CheckAppPay.value = LLClaimRuleListGrid.getRowColData(i,6);
	fm.CheckClmPay.value = LLClaimRuleListGrid.getRowColData(i,7);
	fm.CheckStartDate.value = LLClaimRuleListGrid.getRowColData(i,8);
	fm.CheckEndDate.value = LLClaimRuleListGrid.getRowColData(i,9);	
}

/**
 * 新增规则
 */
function addRule() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	//校验抽检规则是否存在
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAutoSurveySql");
	tSQLInfo.setSqlId("LLClaimAutoSurveySql2");
	tSQLInfo.addSubPara(fm.CheckManageCom.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length>0) {
		alert("该分公司下，自动调查规则已维护，只能修改！");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action = "./LLClaimAutoSurveySave.jsp";
	submitForm();		
}
/**
 * 修改规则
 */
function modifyRule() {
	
	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条自动调查规则！");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="UPDATE";
	fm.action = "./LLClaimAutoSurveySave.jsp";
	submitForm();	
}
/**
 * 删除规则
 */
function delteRule() {

	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条自动调查规则！");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="DELETE";
	fm.action = "./LLClaimAutoSurveySave.jsp";
	submitForm();
}
/**
 * 重置规则
 */
function initRule() {
	initDetailInfo();
}

//展示提示信息
function showWarnInfo() {
		
	alert('请录入[投保人名称]后回车操作(支持模糊查询)！');
	fm.CheckAppntName.focus();
	fm.CheckAppntName.style.background = "#ffb900";
}

/**
 * 提交数据
 */
function submitForm() {
	
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
		initForm();
	}	
}
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "抽检规则序号^|抽检机构^|抽检机构名称^|"
							+"险种编码^|险种名称^|审核金额^|赔付金额^|生效起期^|生效止期";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAutoSurveySql");
	tSQLInfo.setSqlId("LLClaimAutoSurveySql");
	
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryRiskcode.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryUWMoney.value);
	tSQLInfo.addSubPara(fm.QueryPayMoney.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
	
	
}
