/***************************************************************
 * <p>ProName：LLClaimBPOcheckInput.js</p>
 * <p>Title：黑名单管理</p>
 * <p>Description：黑名单管理</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : gaodh
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
 
/**
 * 查询已维护的客户信息列表
 */
function queryCustomerList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackListSql");
	tSQLInfo.setSqlId("LLClaimBlackListSql1");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	tSQLInfo.addSubPara(mOperator);
	tSQLInfo.addSubPara(mClmState);
	
	turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2);	
}

/**
 * 查询出险人的调整轨迹信息
 */
function showCustomerTrace() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一个出险人信息");
		return false;
	}
	fm.RgtNo.value = CustomerListGrid.getRowColData(tSelNo,1);	
	fm.CustomerNo.value = CustomerListGrid.getRowColData(tSelNo,2);
	var tState = CustomerListGrid.getRowColData(tSelNo,8);
	fm.State.value = tState;
	if (tState=="0") {
		document.getElementById("confirmReason").style.display = "";
		document.getElementById("releaseReason").style.display = "none";
		fm.blackConform.style.display = "";
		fm.blackRelase.style.display = "none";
	} else {
		document.getElementById("confirmReason").style.display = "none";
		document.getElementById("releaseReason").style.display = "";
		fm.blackConform.style.display = "none";
		fm.blackRelase.style.display = "";		
	}
	
	if (mMode==1) {
		
		document.getElementById("blackConform").disabled=true;	
		document.getElementById("blackRelase").disabled=true;
	} else {
		document.getElementById("blackConform").disabled=false;	
		document.getElementById("blackRelase").disabled=false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackListSql");
	tSQLInfo.setSqlId("LLClaimBlackListSql2");
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),CustomerStateListGrid,2);
	
}

/**
 * 黑名单确认
 */
function conformClick() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一个出险人信息");
		return false;
	}
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	if (fm.AdjustReason.value=="") {
		alert("调整原因不可为空！");
		fm.AdjustReason.focus();
		fm.AdjustReason.style.background="#ffb900";
	}
			
	fm.Operate.value = "CONFIRM";	
	submitForm();	
}

/**
 * 取消黑名单
 */
function releaseClick() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一个出险人信息");
		return false;
	}

	var tState = CustomerListGrid.getRowColData(tSelNo,8);	
	fm.State.value = tState;
	
	if (fm.AdjustReason1.value=="") {
		alert("调整原因不可为空！");
		fm.AdjustReason1.focus();
		fm.AdjustReason1.style.background="#ffb900";
	}		
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}	
			
	fm.Operate.value = "RELEASE";	
	submitForm();	
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
	}	
}