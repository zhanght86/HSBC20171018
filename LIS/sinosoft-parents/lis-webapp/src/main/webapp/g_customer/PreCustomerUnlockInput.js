/***************************************************************
 * <p>ProName：PreCustomerUnlockInput.js</p>
 * <p>Title：准客户解锁界面</p>
 * <p>Description：准客户解锁界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询主客户经理
 */
function queryAgent(cObj, cObjName) {
	
	var keyCode = event.keyCode;
	if(keyCode=="13"||keyCode=="9") {
		
		window.event.keyCode = 0;
		if (cObjName.value==null || cObjName.value=="") {
			alert("主客户经理名称不能为空！");
			return false;
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.PreCustomerSql");
		tSQLInfo.setSqlId("PreCustomerSql12");
		tSQLInfo.addSubPara(cObjName.value);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("不存在该客户经理！");
			cObj.value = "";
			cObjName.value = "";
			return false;
		} else {
			if (tArr.length == 1) {
				cObj.value = tArr[0][0];
				cObjName.value = tArr[0][1];
			} else {
				showCodeList('agentcode',[cObj, cObjName],[0, 1],null,"1 and agentname like #%%"+cObjName.value+"%%#","1",'1',null);
			}
		}
	}
}

/**
 * 解锁
 */
function unlockClick() {
	
	if (!confirm("解锁将会删除该准客户信息，是否确认？")) {
		return false;
	}
	mOperate = "UNLOCK";
	submitForm();
}

/**
 * 客户经理信息修改
 */
function modifyClick() {
	
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/*
*	返回
*/
function returnBack() {
	
	if (isTraceFlag==0) {
		
		window.location="./PreCustomerQueryInput.jsp?Flag=2";
	} else if (isTraceFlag==1) {
		
		window.open("./PreCustomerTraceQueryMain.jsp?PreCustomerNo="+tPreCustomerNo,"修改轨迹查询",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
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
	fm.Operate.value = mOperate;
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
		//window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	if (mOperate == "UNLOCK") {
		window.location = "./PreCustomerQueryInput.jsp?Flag=2";
	}
}

/**
 * 查询准客户详细信息
 */
function queryDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	
	if (isTraceFlag==0) {
		
		tSQLInfo.setSqlId("PreCustomerSql2");
		tSQLInfo.addSubPara(tPreCustomerNo);
	} else if (isTraceFlag==1) {
		
		tSQLInfo.setSqlId("PreCustomerSql7");
		tSQLInfo.addSubPara(tTraceID);
	}
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		fm.PreCustomerNo.value = strQueryResult[0][0];
		fm.PreCustomerName.value = strQueryResult[0][1];
		fm.IDTypeName.value = strQueryResult[0][3];
		fm.IDNo.value = strQueryResult[0][4];
		fm.GrpNatureName.value = strQueryResult[0][6];
		fm.BusiCategoryName.value = strQueryResult[0][8];
		fm.SumNumPeople.value = strQueryResult[0][9];
		fm.PreSumPeople.value = strQueryResult[0][10];
		fm.PreSumPrem.value = strQueryResult[0][11];
		fm.UpCustomerName.value = strQueryResult[0][13];
		fm.SaleChannelName.value = strQueryResult[0][15];
		
		fm.DetailAddress.value = strQueryResult[0][17]+strQueryResult[0][19]+strQueryResult[0][21]+strQueryResult[0][22];
		fm.CustomerIntro.value = strQueryResult[0][23];
	}
	
	//查询主要联系人信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	
	if (isTraceFlag==0) {
		
		tSQLInfo.setSqlId("PreCustomerSql3");
		tSQLInfo.addSubPara(tPreCustomerNo);
	} else if (isTraceFlag==1) {
		
		tSQLInfo.setSqlId("PreCustomerSql8");
		tSQLInfo.addSubPara(tTraceID);
	}
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.LinkMan.value = strQueryResult[0][0];
		fm.Mobile.value = strQueryResult[0][1];
		fm.Phone.value = strQueryResult[0][2];
		fm.Depart.value = strQueryResult[0][3];
		fm.Post.value = strQueryResult[0][4];
		fm.Email.value = strQueryResult[0][5];
	}
	
	//查询主客户经理信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql4");
	tSQLInfo.addSubPara(tPreCustomerNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.SalerCode.value = strQueryResult[0][0];
		fm.SalerName.value = strQueryResult[0][1];
	}
	
	//查询附属客户经理信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	
	if (isTraceFlag==0) {
		
		tSQLInfo.setSqlId("PreCustomerSql5");
		tSQLInfo.addSubPara(tPreCustomerNo);
	} else if (isTraceFlag==1) {
		
		tSQLInfo.setSqlId("PreCustomerSql9");
		tSQLInfo.addSubPara(tTraceID);
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), SalerGrid, 0, 1);
	
	//轨迹明细信息查询时，如果没有附属客户经理信息，则将其隐藏
	if (SalerGrid.mulLineCount==0) {
		if (isTraceFlag==1) {
			divSaler.style.display = "none";
		}
	}
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="UPDATE") {
		
		for(var i=0;i < SalerGrid.mulLineCount;i++){
				
			var tSalerCode = SalerGrid.getRowColData(i,1);
			if(tSalerCode == null || tSalerCode.trim() == "") {
				
				alert("第["+(i+1)+"]行客户经理代码不能为空！");
				return false;
			}
			
			var tMainSalerCode = fm.SalerCode.value;
			if (tMainSalerCode==tSalerCode) {
				alert("第["+(i+1)+"]行客户经理代码不能与主客户经理代码相同！");
				return false;
			}
				
			//人员重复性校验
			for(var j=0;j < i;j++){
				
				var mSalerCode = SalerGrid.getRowColData(j,1);
				if (mSalerCode==tSalerCode) {
					alert("第["+(i+1)+"]行客户经理代码与第["+(j+1)+"]行重复！");
					return false;
				}
			}
		}
	}
	return true;
}
//查询客户经理
function queryManager() {
	
	var tSelNo = SalerGrid.lastFocusRowNo;//行号从0开始
	var strUrl = "PreCustomerManagerQueryMain.jsp?SelNo="+tSelNo;
	window.open(strUrl,"客户经理查询",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}
//查询主客户经理
function queryManagerInfo() {
	
	var strUrl = "MainManagerQueryMain.jsp";
	window.open(strUrl,"主客户经理查询",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}
