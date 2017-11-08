/***************************************************************
 * <p>ProName：LCCoinsuranceInput.js</p>
 * <p>Title：共保设置</p>
 * <p>Description：共保设置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-03
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryCoinsuranceInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCCoinsuranceSql");
	tSQLInfo.setSqlId("LCCoinsuranceSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), CoinsuranceGrid, 1, 1);
}


/**
 * 展示详细信息
 */
function showCoinInfo() {
	
	var tRow = CoinsuranceGrid.getSelNo();
	fm.MasterSlaveFlag.value = CoinsuranceGrid.getRowColData(tRow-1,2);
	fm.MasterSlaveName.value = CoinsuranceGrid.getRowColData(tRow-1,3);
	fm.CoinComCode.value = CoinsuranceGrid.getRowColData(tRow-1,4);
	fm.CoinComName.value = CoinsuranceGrid.getRowColData(tRow-1,5);
	fm.AmntShareRate.value = CoinsuranceGrid.getRowColData(tRow-1,6);
	fm.PremShareRate.value = CoinsuranceGrid.getRowColData(tRow-1,7);
	
}

/**
 * 新增
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!verifyInput2()) {
		return false;
	}
	
	if (!checkSubmit()) {
		return false;
	}
	
	fm.action = "./LCCoinsuranceSave.jsp?Operate="+ mOperate +"&GrpContNo="+tGrpContNo;
	submitForm();
}

/**
 * 修改
 */
function modifyClick() {
	
	mOperate = "UPDATE";
	var tRow = CoinsuranceGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if (!verifyInput2()) {
		return false;
	}
	if (!checkSubmit()) {
		return false;
	}
	var tSerialNo = CoinsuranceGrid.getRowColData(tRow-1,1);//流水号
	
	fm.action = "./LCCoinsuranceSave.jsp?Operate="+ mOperate +"&GrpContNo="+tGrpContNo +"&SerialNo="+ tSerialNo;
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tRow = CoinsuranceGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	var tSerialNo = CoinsuranceGrid.getRowColData(tRow-1,1);//流水号
	mOperate = "DELETE";
	fm.action = "./LCCoinsuranceSave.jsp?Operate="+ mOperate +"&GrpContNo="+tGrpContNo +"&SerialNo="+ tSerialNo;
	submitForm();

}

/**
 * 提交前校验
 */
function checkSubmit() {
	
	var tAmntShareRate = fm.AmntShareRate.value;
	if (!checkDecimalFormat(tAmntShareRate, 1, 2)) {
		alert("保额分摊比例小数位不应超过2位！");
		return false;
	}
	var tPremShareRate = fm.PremShareRate.value;
	if (!checkDecimalFormat(tPremShareRate, 1, 2)) {
		alert("保费分摊比例小数位不应超过2位！");
		return false;
	}
	//校验只能有一个主保险公司
	if (fm.MasterSlaveFlag.value =="0") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCCoinsuranceSql");
		tSQLInfo.setSqlId("LCCoinsuranceSql2");
		tSQLInfo.addSubPara(tGrpContNo);
		if (mOperate == "UPDATE") {
			var tRow1 = CoinsuranceGrid.getSelNo();
			var tSerialNo1 = CoinsuranceGrid.getRowColData(tRow1-1,1);//流水号
			tSQLInfo.addSubPara(tSerialNo1);
		} else {
			tSQLInfo.addSubPara("");
		}
		var tCountArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tCountArr!=null) {
			if (tCountArr[0][0]>=1) {
				alert("只能有一个主再保公司！");
				return false;
			}
		}
	}
	//新增的比例与已经存在的共保公司的分摊比例之和为1
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCCoinsuranceSql");
	tSQLInfo.setSqlId("LCCoinsuranceSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	if (mOperate == "UPDATE") {
		var tRow1 = CoinsuranceGrid.getSelNo();
		var tSerialNo1 = CoinsuranceGrid.getRowColData(tRow1-1,1);//流水号
		tSQLInfo.addSubPara(tSerialNo1);
	} else {
		tSQLInfo.addSubPara("");
	}
	
	var tSumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	var tSumAmntShareRate=0;
	var tSumPremShareRate=0;
	if(tSumArr !=null ){
	 tSumAmntShareRate = tSumArr[0][0];//数据库已存在保额分摊比例之和
	 tSumPremShareRate = tSumArr[0][1];
	}
	var tAmntShareRate = fm.AmntShareRate.value;//页面新增保额分摊比例
	var tPremShareRate = fm.PremShareRate.value;
	
	if (Number(tSumAmntShareRate)+Number(tAmntShareRate)>1) {
		
		alert("新增保额分摊比例与已经存在的共保\r公司保额分摊比例之和应小于等于1！");
		return false;
	}
	if (Number(tSumPremShareRate)+Number(tPremShareRate)>1) {
		
		alert("新增保费分摊比例与已经存在的共保\r公司保费分摊比例之和应小于等于1！");
		return false;
	}
	
	return true;
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
		queryCoinsuranceInfo();
		initInpBox();
	}
}


/**
 * 校验数据整数位于小数位长度
 */
function checkDecimalFormat(cValue, cLen1, cLen2) {
	
	if (cValue=='' || cValue==null) {//为空,
		return true;
	}
	
	var tLen =  (""+cValue+"").length;
	var tLen1 = (""+cValue+"").indexOf(".");
	var tLen2 = 0;
	if (tLen1==-1) {
		tLen1 = tLen;
	} else {
		tLen2 = tLen - tLen1 - 1;
	}
	
	if (Number(tLen1)>Number(cLen1)) {
		return false;
	}
	
	if (Number(tLen2)>Number(cLen2)) {
		return false;
	}
	
	return true;
}
