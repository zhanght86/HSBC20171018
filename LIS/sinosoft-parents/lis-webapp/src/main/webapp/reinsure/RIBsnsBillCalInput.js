//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug = "0";
var DealWithNam;
var sqlresourcename = "reinsure.RIBsnsBillCalInputSql";

function verifyDate() {
	if (fm.StartDate.value > fm.EndDate.value) {
		myAlert(""+"生效日期不能大于失效日期！"+"");
		return false;
	}
	if (fm.BillType.value == "B1") {
		if ((fm.StartDate.value).substring(5, 10) == "01-01"
				&& fm.EndDate.value == ((fm.StartDate.value).substring(0, 5) + "03-31")) {
			fm.BillTimes.value = (fm.StartDate.value).substring(0, 4) + "Q1";
		} else if ((fm.StartDate.value).substring(5, 10) == "04-01"
				&& fm.EndDate.value == ((fm.StartDate.value).substring(0, 5) + "06-30")) {
			fm.BillTimes.value = (fm.StartDate.value).substring(0, 4) + "Q2";
		} else if ((fm.StartDate.value).substring(5, 10) == "07-01"
				&& fm.EndDate.value == ((fm.StartDate.value).substring(0, 5) + "09-30")) {
			fm.BillTimes.value = (fm.StartDate.value).substring(0, 4) + "Q3";
		} else if ((fm.StartDate.value).substring(5, 10) == "10-01"
				&& fm.EndDate.value == ((fm.StartDate.value).substring(0, 5) + "12-31")) {
			fm.BillTimes.value = (fm.StartDate.value).substring(0, 4) + "Q4";
		}
	} else if (fm.BillType.value == "B2") {
		if (fm.StartDate.value.substring(8, 10) == "01"
				&& fm.EndDate.value == (addDate(4, -1, addDate("5", 1,
						fm.StartDate.value)))) {
			fm.BillTimes.value = fm.StartDate.value.substring(0, 4)
					+ fm.StartDate.value.substring(5, 7);
		}
	}
	if (fm.BillTimes.value == "") {
		myAlert(""+"输入日期不合法！"+"");
		return false;
	}

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillCalInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIComCode.value);
	mySql.addSubPara(fm.BillTimes.value);
	mySql.addSubPara(fm.BillType.value);
	var SQL = mySql.getString();
	result = easyExecSql(SQL);
	if (result != null) {
		myAlert(""+"本期次账单已经计算！"+"");
		return false;
	}
	return true;
}

// 批量计算
function StatisticData() {
	try {
		if (verifyInput() && verifyDate()) {
			var showStr = ""+"正在统计数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "RIBsnsBillCalSave.jsp";
			fm.submit();
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

// 重 置
function ResetForm() {
	fm.RIComCode.value = '';
	fm.RIComName.value = '';
	fm.BillType.value = '';
	fm.BillTypeName.value = '';
	fm.StartDate.value = '';
	fm.EndDate.value = '';
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
	}
}
