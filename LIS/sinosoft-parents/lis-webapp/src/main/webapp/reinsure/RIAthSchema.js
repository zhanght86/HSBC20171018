var showInfo;
var mDebug = "0";
var sqlresourcename = "reinsure.RIAthSchemaInputSql";
var DealWithNam;
var turnPage = new turnPageClass();

// 演算法定义增加
function submitForm() {
	if (verifyInput()) {
		var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

		fm.OperateType.value = 'add';
		fm.submit(); // 提交
	}
}

// 演算法定义查询
function queryClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAthSchemaInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.ArithmeticDefID.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ArithmeticDefName.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ArithSubType.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, ResultGrid);
}

// 演算法定义修改
function updateClick() {
	if (verifyInput()) {
		var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

		fm.OperateType.value = 'update';
		fm.submit(); // 提交
	}
}
// 演算法定义删除
function deleteClick() {
	if (fm.ArithmeticDefID.value == null || fm.ArithmeticDefID.value == "") {
		myAlert(""+"请录入演算法编码!"+"");
		return false;
	}
	if (!confirm(""+"您确定要修改该演算法吗？"+"")) {
		return false;
	}
	var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+showStr;// encodeURI(encodeURI(showStr));
	showInfo = window
			.showModelessDialog(urlStr, window,
					"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.OperateType.value = 'delete';
	fm.submit(); // 提交
}

function getDetail() {
	var tRow = ResultGrid.getSelNo();
	if (tRow > 0) {
		fm.ArithmeticDefID.value = ResultGrid.getRowColData(tRow - 1, 1);
		fm.ArithmeticDefName.value = ResultGrid.getRowColData(tRow - 1, 2);
		fm.AccumulateDefNO.value = ResultGrid.getRowColData(tRow - 1, 5);
		fm.ArithTypeName.value = ResultGrid.getRowColData(tRow - 1, 3);
		fm.ArithSubType.value = ResultGrid.getRowColData(tRow - 1, 7);
		fm.PreceptType.value = ResultGrid.getRowColData(tRow - 1, 6);
		if (fm.PreceptType.value != "") {
			fm.ArithContName.value = ResultGrid.getRowColData(tRow - 1, 4);
		}
		fm.AccumulateDefName.value = ResultGrid.getRowColData(tRow - 1, 8);
		if (fm.ArithSubType.value == "P") {
			divArithType1.style.display = '';
			divArithType2.style.display = '';
		} else {
			divArithType1.style.display = 'none';
			divArithType2.style.display = 'none';
		}
	}
}

// 明细演算法定义
function nextStep() {
	// 先选择一行
	var tRow = ResultGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请您先进行选择!"+"");
		return;
	}
	var varSrc = "&ArithmeticID='" + ResultGrid.getRowColData(tRow - 1, 1)
			+ "' ";
	window.open(
			"./FrameMainCalItemBatch.jsp?Interface=RIItemCalDetailInput.jsp"
					+ varSrc, "true1");
}

function afterSubmit(FlagStr, content) {
	showInfo.close();

	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		mOperate = "";
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	if(fm.OperateType.value == "delete"){
		initForm();
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "riatithtype") {
		// 资料提取演算法
		if (Field.value == "L") {
			divArithType1.style.display = 'none';
			divArithType2.style.display = 'none';
		}
		// 分保方案演算法
		if (Field.value == "P") {
			divArithType1.style.display = '';
			divArithType2.style.display = '';
		}
	}
}
