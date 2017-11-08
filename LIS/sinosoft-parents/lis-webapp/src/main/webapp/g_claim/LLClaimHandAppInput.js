/***************************************************************
 * <p>ProName：LLClaimHandAppInput.js</p>
 * <p>Title：交接流转号申请页面</p>
 * <p>Description：交接流转号申请页面</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();
var tPageIndex = 0;
var tSelNo = 0;

/**
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPageNoSql");
	tSQLInfo.setSqlId("LLClaimPageNoSql");
	tSQLInfo.addSubPara(fm.QueryPageNo.value);
	tSQLInfo.addSubPara(fm.AppOperator.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.AppStartDate.value);
	tSQLInfo.addSubPara(fm.AppEndDate.value);	
	
	turnPage1.queryModal(tSQLInfo.getString(),HandNoListGrid,1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}	
}
/**
 * 返回选中的交接流转号
 */
function returnSelect() {
	
	var tSelNo = HandNoListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条理赔用户信息");
		return false;
	}
	try {
		
		var returnArr = new Array();
		returnArr = HandNoListGrid.getRowData(tSelNo);
		if (returnArr!=null) {
		
			if (returnArr[0]==null || returnArr[0]=="") {
				alert("请先查询！");
				return false;
			} else {
				var tAvailabeSum = returnArr[1]-returnArr[2];
				if (tAvailabeSum<=0) {
					alert("关联人数已达到上限，请选择其他交接流转号");
					return false;
				}
				top.opener.afterAppPageNo(returnArr);
			}		
		} else {
			return false;
		}
	} catch(ex) {
		alert("返回异常："+ ex);
	}
	top.close();	
}
/**
 * 增加交接流转信息
 */
function addPageNoClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	var tNum = fm.SumNum.value;
	if (tNum!=null && tNum<=0) {
		alert("关联人数必须大于0");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPageNoSql");
	tSQLInfo.setSqlId("LLClaimPageNoSql2");	
	tSQLInfo.addSubPara(fm.AppOperator.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var RowNum = 0;
	if (tArr==null || tArr.length==0) {
		RowNum = 0;
	} else {
		RowNum = tArr[0][0];
	}
	var mRowNum = Number(RowNum)+1;
	tPageIndex = Math.ceil(mRowNum/10)-1;
	tSelNo = mRowNum%10;
	if(mRowNum%10 == 0){
		tSelNo = 10;
	}
	
	fm.PageNo.value = "";
	fm.Operate.value = "INSERT";
	fm.action = "./LLClaimHandAppSave.jsp?Operate = INSERT";
	submitForm();
}
/**
 * 修改
 */
function modifyClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	var tNum = fm.SumNum.value;
	if (tNum!=null && tNum<=0) {
		alert("关联人数必须大于0");
		return false;
	}	
	fm.Operate.value = "UPDATE";
	submitForm();	
}
/**
 * 删除
 */
function deleteClick() {
	fm.Operate.value = "DELETE";
	submitForm();	
}
/**
 * 返回
 */
function goBack() {
	top.close();
}
/**
 * 选中一条交接流水号
 */
function selectPageNo() {
	
	var tSelNo = HandNoListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条理赔用户信息");
		return;
	}
	var tPageNo = HandNoListGrid.getRowColData(tSelNo,1);
	if (tPageNo==null || tPageNo=="") {
		alert("请先查询！");
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPageNoSql");
	tSQLInfo.setSqlId("LLClaimPageNoSql1");
	tSQLInfo.addSubPara(tPageNo);	
	var tArr = easyExecSql(tSQLInfo.getString());
	if (tArr!=null && tArr.length>0) {
		fm.PageNo.value = tArr[0][0];
		fm.SumNum.value = tArr[0][1];
		fm.Remark.value = tArr[0][2];
	}
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
	}	
}
/**
 * 默认选中刚操作的记录
 */
function setSelRow(ObjGrid,tTurnPage){			
	 	
	if (tPageIndex!=null && tPageIndex!="") {
		for (var i=0; i<tPageIndex; i++) {
			tTurnPage.nextPage();
		}
	}
	if(tSelNo==null || tSelNo ==""){
		tSelNo = 1;
	}	
	ObjGrid.radioSel(tSelNo);
	
}
