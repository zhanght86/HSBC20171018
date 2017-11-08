var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
window.onfocus = myonfocus;

function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "ricontno") {
		fm.all('RIContNo').value = fm.all('RIContNoPage').value;
		queryClick();
	}
}

function queryClick() {
	fm.OperateType.value = "QUERY";
	var strSQL = "select a.RIContNo A1,a.RIPreceptNo A2,a.RIPreceptName A3,a.AccumulateDefNO A4,"
			+ " (select AccumulateDefName from RIAccumulateDef where a.AccumulateDefNO= AccumulateDefNO) A5, "
			+ " a.PreceptType A6,'"+"临时分保方案"+"' A7,"
			+ " (select codename from ldcode where code=a.state and codetype='ristate') A8,a.state A9 "
			+ " from RIPrecept a where a.PreceptType='02' and a.RIContNo='"
			+ fm.RIContNo.value
			+ "' order by a.accumulatedefno ,a.ripreceptno ";
	turnPage.queryModal(strSQL, PreceptSearchGrid);
}

function accuRiskInfo() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0 || tSel == null) {
		myAlert(""+"请先选择再保方案！"+"");
		return false;
	}
	var accumulateNo = PreceptSearchGrid.getRowColData(tSel - 1, 4);
	window.open("./FrameAccRiskQuery.jsp?AccumulateNo=" + accumulateNo);
}

/**
 * 关联临分保单
 */
function RelaTempPol() {

	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0) {
		myAlert(""+"请选择再保方案"+"");
		return false;
	}
	var state = PreceptSearchGrid.getRowColData(tSel - 1, 9);
	var PreceptType = PreceptSearchGrid.getRowColData(tSel - 1, 6);
	if (state == '02' && PreceptType == '02') {
		myAlert(""+"该方案未生效"+","+"不能关联保单!"+"");
		return false;
	}
	var tRIContNo = PreceptSearchGrid.getRowColData(tSel - 1, 1);
	var tRIPreceptNo = PreceptSearchGrid.getRowColData(tSel - 1, 2);
	var tOperateNo = fm.OperateNo.value;
	var RIPolno = fm.RIPolno.value;
	window.open("./FrameRelaTempPol.jsp?RIContNo=" + tRIContNo
			+ "&RIPreceptNo=" + tRIPreceptNo + "&RIPolno=" + RIPolno, "Temp1");
}

/**
 * 关联团险临分保单
 */
function RelaGrpTempPol() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0) {
		myAlert(""+"请选择再保方案"+"");
		return false;
	}
	var tRIContNo = PreceptSearchGrid.getRowColData(tSel - 1, 1);
	var tRIPreceptNo = PreceptSearchGrid.getRowColData(tSel - 1, 2);
	var tCalFeeTerm = fm.CalFeeTerm.value;
	var tCalFeeType = fm.CalFeeType.value;
	var tOperateNo = fm.OperateNo.value;
	var tCodeType = "05";
	window.open("./FrameRelaTempPol.jsp?RIContNo=" + tRIContNo
			+ "&RIPreceptNo=" + tRIPreceptNo + "&CalFeeType=" + tCalFeeType
			+ "&CalFeeTerm=" + tCalFeeTerm + "&OperateNo=" + tOperateNo
			+ "&CodeType=" + tCodeType, "Temp1");
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, result, CertifyCode) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
}

// 临分方案详细信息
function showPrecept() {

	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0) {
		myAlert(""+"请选择再保方案"+"");
		return false;
	}
	var tRIPreceptNo = PreceptSearchGrid.getRowColData(tSel - 1, 2);
	var strSQL = "select X.A1,(select ComPanyName from RIComInfo where ComPanyNo=X.A2),d.upperLimit,d.PossessScale,d.premfeetableno,d.comfeetableno from (select a.RIPreceptName A1,b.IncomeCompanyNo A2,0 A3,a.ripreceptno A4 from RIPrecept a,RIIncomeCompany b where "
	strSQL += "a.ripreceptno='"
			+ tRIPreceptNo
			+ "' and b.ripreceptno=a.ripreceptno and b.incomecompanytype='01') X ,RIRiskDivide d where d.ripreceptno = X.A4 and d.incomecompanyno = X.A2 "
	// var strSQL = " select 'C000000003','China Life
	// Re','1600000','0.7','F00021','C00021' from dual " ;
	turnPage1.queryModal(strSQL, PreceptInfoGrid);
}

function addCessScale(result, flag) {
	// 重置
	strSQL = "select DivisionLineValue from RIDivisionLineDef where ripreceptno='"
			+ result
			+ "' and divisionlinetype<>'04' order by DivisionLineOrder ";
	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		x = 0;
	} else {
		x = strArray.length;
	}
	// 重置line
	line = new Array(x);
	if (strArray != null) {
		for ( var i = 0; i < strArray.length; i++) {
			line[i] = strArray[i];
		}
	}
	strSQL = " select (select companyname from RIComInfo where companyno=a.IncomeCompanyNo) from RIIncomeCompany a where ripreceptno='"
			+ result + "' order by a.IncomeCompanyOrder ";
	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		y = 0;
	} else {
		y = strArray.length;
	}

}

// 重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm() {
	try {
		queryClick();
		inputPrecept();
	} catch (re) {
		myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
	}
}

function ClosePage() {
	top.close();
}
