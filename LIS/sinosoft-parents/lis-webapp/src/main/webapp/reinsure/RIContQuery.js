var sqlresourcename = "reinsure.RIContQuerySql";
var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass(); // 使用翻页功能，必须建立为全局变量

// 提交，保存按钮对应操作
function submitForm() {
	if (fm.PageFlag.value == "CONT") {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIContQuerySql1");// 指定使用的Sql的id
		mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql.getString();
		turnPage.queryModal(strSQL, ReContGrid);
	}
	// else{
	// strSQL = "select ricontno,ricontname,conttype,(select codename from
	// ldcode where codetype = 'riconttype' and code = ContType),"
	// +"
	// ReInsuranceType,decode(ReInsuranceType,'01','成数分保','02','溢额分保','03','成数溢额分保'),cvalidate,enddate,"
	// +" State,(select codename from ldcode where code=state and
	// codetype='ristate'),gitype,decode(gitype,'1','月度','3','季度','12','年度')
	// from RIBarGainInfo where 1=1 "
	// + getWherePart("RIContNo","RIContNo")
	// ;
	// strSQL = strSQL +" order by RIContNo";
	// }
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	}
}

// 重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"在Proposal.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
	}
}

// 取消按钮对应操作
function cancelForm() {
	showDiv(operateButton, "true");
	showDiv(inputButton, "false");
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加操作
}

// 显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}

// 显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug) {
	if (cDebug == "1") {
		parent.fraMain.rows = "0,0,0,0,*";
	} else {
		parent.fraMain.rows = "0,0,0,82,*";
	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData() {
	if (fm.PageFlag.value == "CONT") {
		// 先选择一行
		var tRow = ReContGrid.getSelNo();
		if (tRow == 0) {
			myAlert(""+"请您先进行选择!"+"");
			return;
		}

		// var strSQL = "select RIContNo,RIContName,ContType,(select codename
		// from ldcode where codetype = 'riconttype' and code = ContType), "
		// +" ReInsuranceType,(case ReInsuranceType when '01' then '成数分保' when
		// '02' then '溢额分保' when '03' then '成数溢额分保' end), "
		// +"
		// CValiDate,EndDate,RISignDate,state,decode(State,'0','未生效','1','有效',''),gitype,decode(gitype,'1','月度','3','季度','12','年度')
		// "
		// +" from RIBarGainInfo "
		// + "where 1=1 and RIContNo='"+ReContGrid.getRowColData(tRow-1,1)+"'"
		// ;
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIContQuerySql2");// 指定使用的Sql的id
		mySql.addSubPara(ReContGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql.getString();
		strArray = easyExecSql(strSQL);

		// 如果查询结果为空，
		if (strArray == null) {
			myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
			return false;
		}

		// 如果查询结果不为空，则把查询的结果依次自动填充到主页面对应的对象中
		top.opener.fm.all('RIContNo').value = strArray[0][0];
		top.opener.fm.all('RIContName').value = strArray[0][1];
		// top.opener.fm.all('ContType').value =strArray[0][2];
		// top.opener.fm.all('ContTypeName').value =strArray[0][3];
		top.opener.fm.all('ReCountType').value = strArray[0][2];
		top.opener.fm.all('ReCountTypeName').value = strArray[0][3];
		top.opener.fm.all('RValidate').value = strArray[0][5];
		top.opener.fm.all('RInvalidate').value = strArray[0][6];
		top.opener.fm.all('RSignDate').value = strArray[0][4];
		top.opener.fm.all('ContState').value = strArray[0][7];
		top.opener.fm.all('ContStateName').value = strArray[0][8];
		top.opener.fm.all('UseType').value = strArray[0][9];
		top.opener.fm.all('UseTypeName').value = strArray[0][10];

		// 该语句用来从联系人表RIBarGainSigner中查询相应的记录：
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIContQuerySql3");// 指定使用的Sql的id
		mySql1.addSubPara(ReContGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql1.getString();
		strArray = easyExecSql(strSQL);
		top.opener.SignerGrid.clearData();
		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				top.opener.SignerGrid.addOne("SignerGrid");

				top.opener.SignerGrid.setRowColData(k, 1, strArray[k][0]);
				top.opener.SignerGrid.setRowColData(k, 2, strArray[k][1]);
				top.opener.SignerGrid.setRowColData(k, 3, strArray[k][2]);
				top.opener.SignerGrid.setRowColData(k, 4, strArray[k][3]);
				top.opener.SignerGrid.setRowColData(k, 5, strArray[k][4]);
				top.opener.SignerGrid.setRowColData(k, 6, strArray[k][5]);
				top.opener.SignerGrid.setRowColData(k, 7, strArray[k][6]);
				top.opener.SignerGrid.setRowColData(k, 8, strArray[k][7]);
				top.opener.SignerGrid.setRowColData(k, 9, strArray[k][8]);
			}
		}

		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RIContQuerySql4");// 指定使用的Sql的id
		mySql2.addSubPara(ReContGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql2.getString();
		strArray = easyExecSql(strSQL);
		top.opener.FactorGrid.clearData();
		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				top.opener.FactorGrid.addOne("FactorGrid");
				top.opener.FactorGrid.setRowColData(k, 1, strArray[k][0]);
				top.opener.FactorGrid.setRowColData(k, 2, strArray[k][1]);
				top.opener.FactorGrid.setRowColData(k, 3, strArray[k][2]);
			}
		}
	}
	top.close();
}

function ClosePage() {
	top.close();
}
