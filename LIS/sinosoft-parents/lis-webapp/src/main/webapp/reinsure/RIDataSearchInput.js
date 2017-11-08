//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug = "0";
var DealWithNam;
var turnPage = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var eventType;
var sqlresourcename = "reinsure.RIDataSearchInputSql";
function queryClick() {
	dataQuery();

}

function dataQuery() {
	if (!verifyInput()) {
		return false;
	}
	// var tSQL = "select a.nodestate from riwflog a where a.batchno='" +
	// fm.BatchNo.value+"'";
	// var result = easyExecSql(tSQL);
	// if(result[0][0]=='00')
	// {
	// alert("该批次没有数据");
	// return false;
	// }
	// else
	// if(result[0][0]=='99')
	// {
	// tSQL = "select a.contno,a.insuredno,a.insuredname,(case when
	// a.ripreceptno is null then '' else a.ripreceptno end),(case when
	// a.accamnt is null then 0 else a.accamnt end) from ripolrecordbake a where
	// a.batchno='"+fm.BatchNo.value+"' ";
	// }else
	// {
	// tSQL = "select a.contno,a.insuredno,a.insuredname,(case when
	// a.ripreceptno is null then '' else a.ripreceptno end),(case when
	// a.accamnt is null then 0 else a.accamnt end) from ripolrecord a where
	// a.batchno='"+fm.BatchNo.value+"' ";
	// }
	// alert(111);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIDataSearchInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.InsuredNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.InsuredName.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.EventType.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.BFFlag.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIContType.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.EndDate.value);// 指定传入的参数，多个参数顺序添加
	var d = new Date();
	var years = "2017";
	var month = d.getMonth()+1;
	var days = d.getDate();
	var ndate = years+"-"+month+"-"+days;
	mySql.addSubPara(ndate);
	var tSQL = mySql.getString();
	RIDataDetailGrid.clearData();
	result = easyExecSql(tSQL);
	if (result == null) {
		RIDataGrid.clearData();
		myAlert(""+"查询结果为空"+"");
		return false;
	}
	turnPage1.queryModal(tSQL, RIDataGrid);
}

// 分保明细数据查询
function queryDetail() {
	var num = RIDataGrid.getSelNo();
	var EventNo = RIDataGrid.getRowColData(num - 1, 1);
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	if (RIDataGrid.getRowColData(num - 1, 11) == 'L01010060010') {
		mySql.setSqlId("RIDataSearchInputSql3");// 指定使用的Sql的id
		mySql.addSubPara(EventNo);// 指定传入的参数，多个参数顺序添加

		var tSQL = mySql.getString();
		turnPage2.queryModal(tSQL, RIDataUCoDetailGrid);
		RIDataDetailGrid.clearData();
		divUCoDetail.style.display = "";
		divCessDetail.style.display = "none";
	} else {
		mySql.setSqlId("RIDataSearchInputSql2");// 指定使用的Sql的id
		mySql.addSubPara(EventNo);// 指定传入的参数，多个参数顺序添加

		var tSQL = mySql.getString();
		turnPage2.queryModal(tSQL, RIDataDetailGrid);
		RIDataUCoDetailGrid.clearData();
		divUCoDetail.style.display = "none";
		divCessDetail.style.display = "";
	}
}

function verifyInput1() {

	return true;
}

function resetPage() {
	fm.reset();
	fm.all("BFFlag").value = "01";
	fm.all("BFFlagName").value = ""+"业务标记"+"";
	RIDataGrid.clearData();
	RIDataUCoDetailGrid.clearData();
	RIDataDetailGrid.clearData();
}

// 明细分保数据下载
function DownLoadExcel() {
	fm.OperateType.value = "DOWNLOAD";

	fm.target = "fraSubmit";
	fm.action = "./RIDataSearchSave.jsp";
	fm.submit(); // 提交

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

function afterCodeSelect(codeName, Field) {
	if (codeName == "riaccmucode") {
		RIDataGrid.clearData();
		RIDataUCoDetailGrid.clearData();
		RIDataDetailGrid.clearData();
		divUCoDetail.style.display = "none";
		divCessDetail.style.display = "none";
	}
}
