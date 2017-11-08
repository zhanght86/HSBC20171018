/***************************************************************
 * <p>ProName：LLClaimCaseReviewAppInput.js</p>
 * <p>Title：案件审核申请</p>
 * <p>Description：案件审核申请</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询案件
 */
function queryMain(tFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewAppSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewAppSql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(fm.AcceptWorkdays.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.ActivityID.value);
				
	turnPage1.queryModal(tSQLInfo.getString(),MainCaseGrid, 2);
	if (!turnPage1.strQueryResult && tFlag==1) {
		alert("未查询到符合条件的查询结果！");
		return false;
	} else if (turnPage1.strQueryResult) {
		setMulLineColor(MainCaseGrid);
	}
}
//为MulLine设置颜色
function setMulLineColor(ObjGrid) {
	
	//为MulLine设置颜色
	for (var i=0;i<ObjGrid.mulLineCount;i++) {
		
		var tAcceptWorkdays = ObjGrid.getRowColData(i, 8);
		
		if (tAcceptWorkdays>=5 && tAcceptWorkdays<=10) {
			ObjGrid.setColor(i, "#43FA67");
		} else if (tAcceptWorkdays>10 && tAcceptWorkdays<=25) {
			ObjGrid.setColor(i, "#FCB97C");
		} else if (tAcceptWorkdays>25) {
			ObjGrid.setColor(i, "#FD8B73");
		}

	}	
}
function firstPage(tObject,tObjGrid) {
	
	tObject.firstPage();
	setMulLineColor(tObjGrid);
}

function previousPage(tObject,tObjGrid) {
	
	tObject.previousPage();
	setMulLineColor(tObjGrid);
}

function nextPage(tObject,tObjGrid) {
	
	tObject.nextPage();
	setMulLineColor(tObjGrid);
}

function lastPage(tObject,tObjGrid) {
	
	tObject.lastPage();
	setMulLineColor(tObjGrid);
}
/**
 * 查询案件
 */
function querySelf(type) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewAppSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewAppSql1");	
	tSQLInfo.addSubPara(fm.GrpRgtNo1.value);
	tSQLInfo.addSubPara(fm.GrpName1.value);
	tSQLInfo.addSubPara(fm.AcceptCom1.value);
	tSQLInfo.addSubPara(fm.StartDate1.value);
	tSQLInfo.addSubPara(fm.EndDate1.value);
	tSQLInfo.addSubPara(fm.AcceptWorkdays1.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.ActivityID.value);
	tSQLInfo.addSubPara(mOperator);
				
	turnPage2.queryModal(tSQLInfo.getString(),SelfCaseGrid, 2);
	if (turnPage2.strQueryResult) {
		setMulLineColor(SelfCaseGrid);
	}	
	if (!turnPage2.strQueryResult && type==1) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}
/**
 * 申请案件
 */
function applayClick() {
	
	var tSelNo = MainCaseGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择公共池中的数据！");
		return false;
	}
	var tGrpRgtNo = MainCaseGrid.getRowColData(tSelNo,1);
	fm.SelectGrpRgtNo.value = tGrpRgtNo;	
		
	fm.Operate.value="APPLY";
	submitForm();	
}
/**
 * 进入案件
 */
function enterCase(parm1) {
	
	var tSelNo;
	if (parm1!=null && parm1!="") {
		tSelNo = document.all(parm1).all("SelfCaseGridNo").value;
		tSelNo = tSelNo - turnPage2.pageIndex*turnPage2.pageLineNum;
		SelfCaseGrid.radioSel(tSelNo);
		tSelNo = tSelNo - 1;
	} else {
		tSelNo = SelfCaseGrid.getSelNo() - 1;
	}
	
	if (tSelNo<0) {
		alert("请先选择个人池中的数据！");
		return false;
	}
	
	var tGrpRgtNo = SelfCaseGrid.getRowColData(tSelNo,1);
	fm.SelectGrpRgtNo.value = tGrpRgtNo;
	window.location.href = "LLClaimCaseReviewInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=0";
}
/**
 * 释放案件
 */
function releaseCase() {
	
	var tSelNo = SelfCaseGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择个人池中的数据！");
		return false;
	}
	var tGrpRgtNo = SelfCaseGrid.getRowColData(tSelNo,1);
	fm.SelectGrpRgtNo.value = tGrpRgtNo;		
		
	fm.Operate.value="RELEASE";
	submitForm();		
}
/**
 * 新增案件
 */
function newCase() {
	
	window.location.href="LLClaimCaseReviewInput.jsp";	
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
		var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		initForm();
		queryMain();
	}	
}