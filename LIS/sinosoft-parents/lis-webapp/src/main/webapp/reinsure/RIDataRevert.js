//该文件中包含客户端需要处理的函数和事件

//程序名称：RIDataRevert.js
//程序功能：数据回滚
//创建日期：2011-07-30
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIDataRevertInputSql";
var str = getCurrentDate();
// 查询按钮
function button130() {
	if (verifyInput() && verifyInput1()) {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);	
		mySql.setSqlId("RIDataRevertInputSql1");// 指定使用的Sql的id
		mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql.getString();
		var arrResult = easyExecSql(strSQL);
		var tBFFlag = "";
		//IE11-Null
		if(arrResult!=null){
			tBFFlag = arrResult[0][0];
		}
		mySql = new SqlClass();
		if(tBFFlag==""){
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIDataRevertInputSql2");// 指定使用的Sql的id
			mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.InsuredNo.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(str);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.RiskCode.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.StartDate1.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.EndDate1.value);// 指定传入的参数，多个参数顺序添加
			
			var tSQL = mySql.getString();
			turnPage.queryModal(tSQL, Mul11Grid);
		}else{
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIDataRevertInputSql3");// 指定使用的Sql的id
			mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(str);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.InsuredNo.value);// 指定传入的参数，多个参数顺序添加
			
			
			mySql.addSubPara(fm.RiskCode.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.StartDate1.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.EndDate1.value);// 指定传入的参数，多个参数顺序添加
			
			var tSQL = mySql.getString();
			turnPage.queryModal(tSQL, Mul11Grid);
		}
		
	}
}

function verifyInput1() {
	if (fm.StartDate.value > str) {
		myAlert(""+I18NMsg(/*起始日期不能大于当天!*/"L0000014322")+"");
		return false;
	}
	return true;
}

// 数据回滚按钮
function button131() {
	fm.OperateType.value = "REVERT";
	
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);	
	mySql.setSqlId("RIDataRevertInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	var tBFFlag = "";
	//IE11-Null
	if(arrResult!=null){
		tBFFlag = arrResult[0][0];
	}
	mySql = new SqlClass();
	if(tBFFlag==""){
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIDataRevertInputSql4");// 指定使用的Sql的id
		mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.InsuredNo.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(str);// 指定传入的参数，多个参数顺序添加
		
		
		mySql.addSubPara(fm.RiskCode.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.StartDate1.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.EndDate1.value);// 指定传入的参数，多个参数顺序添加
	}else{
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIDataRevertInputSql5");// 指定使用的Sql的id
		mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(str);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.InsuredNo.value);// 指定传入的参数，多个参数顺序添加
		
		
		mySql.addSubPara(fm.RiskCode.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.StartDate1.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.EndDate1.value);// 指定传入的参数，多个参数顺序添加
	}

	var strSQL = mySql.getString();
	temp = easyExecSql(strSQL);
	
	if (temp == null) {
		myAlert(""+I18NMsg(/*回滚数据为空，请先查询！*/"L0000014323")+"");
		return false;
	}
	try {
		if (verifyInput() && verifyInput1()) {
			var showStr = ""+I18NMsg(/*正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面*/"M0000050069")+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIDataRevertSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加操作
}

// 使得从该窗口弹出的窗口能够聚焦
function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

		initForm();
		// 执行下一步操作
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
