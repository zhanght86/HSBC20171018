/***************************************************************
 * <p>ProName：LSQuotCountProfitInit.js</p>
 * <p>Title：收益测算</p>
 * <p>Description：收益测算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-29
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交数据
 */
function submitForm(obj) {

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
	obj.submit();
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
//		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		getQueryResult(content);             
	}
}

/**
 * 模拟下拉操作
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	//险种
	if (value1=='quotrisk') {
		
		var tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case b.enddate when ## then #9999-12-31# else b.enddate end) >#"+tCurrentDate+"# and a.insuaccflag=#Y# and b.riskprop!=#I# ";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1','300');
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1','300');
		}
	}
	//责任
	if (value1=='quotduty') {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("请先选择险种！");
			return false;
		}
		
		var tSql = "1 and a.riskcode=#"+ fm2.RiskCode.value +"#";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	} 
	//管理费扣缴方式
	if(value1=='deducttype') {
		
		var tSql = "deducttype|0";
		
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'codetype|codeexp','1',180);
		} else {
			return showCodeList('queryexp',value2,value3,null,tSql,'codetype|codeexp','1',180);
		}
	}
	//月度管理费类型
	if(value1=='deducttype1') {
		
		var tSql = "1 and code in (#100#,#101#)";
		
		if (returnType=='0') {
			return showCodeList('deducttype',value2,value3,null,tSql,1,'1',180);
		} else {
			return showCodeListKey('deducttype',value2,value3,null,tSql,1,'1',180);
		}
	}
}

/**
* 下拉选择后展示界面要素
*/
function afterCodeSelect(o, p) {
	
	if(o=='quotrisk') {
		
		fm2.DutyCode.value = "";
		fm2.DutyName.value = "";
	}
}

/**
 * 收益测算
 */
function profitClick() {
	
	initProfitInfoGrid();
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (!checkDecimalFormat(fm2.InitPrem.value, '12', '2')) {
		alert("[预计初始保费]整数位不应超过12位,小数位不应超过2位！");
		return false;
	}
	
	var tMangFeeType = fm2.MangFeeType.value;
	var tInitMangFee = fm2.InitMangFee.value;
	if (tMangFeeType == "000" || tMangFeeType == "002" ) {
		
		if (!checkDecimalFormat(tInitMangFee, '12', '2')) {
			alert("[初始管理费(元)/比例]整数位不应超过12位,小数位不应超过2位！");
			return false;
		}
	}
	
	if (tMangFeeType == "001" || tMangFeeType == "003" ) {
		
		if (Number(tInitMangFee)<0 || Number(tInitMangFee)>1) {
			alert("[初始管理费(元)/比例]应大于等于0、小于等于1！");
			return false;
		}
	}
	
	var tMonthFeeType = fm2.MonthFeeType.value;
	var tMonthFee = fm2.MonthFee.value;
	if (tMonthFeeType == "100") {
		
		if (!checkDecimalFormat(tMonthFee, '12', '2')) {
			alert("[月度管理费(元)/比例]整数位不应超过12位,小数位不应超过2位！");
			return false;
		}
	}
	
	if (tMonthFeeType == "101") {
		
		if (Number(tMonthFee)<0 || Number(tMonthFee)>1) {
			alert("[月度管理费(元)/比例]应大于等于0、小于等于1！");
			return false;
		}
	}
	if (tMangFeeType == "000") {
		
		if (Number(tInitMangFee)>Number(fm2.InitPrem.value)) {
			alert("[初始管理费(元)/比例]应小于[预计初始保费]！");
			return false;
		}
	}
	
	mOperate = "TRYCAL";
	
	fm2.action = "./LSQuotCountProfitSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
	
}

/**
 * 展示Multiline
 */
function getQueryResult(strQueryResult) {
	
	var iArray;
	turnPage1 = new turnPageClass();
	//保存查询结果字符串	
	turnPage1.strQueryResult  = strQueryResult;
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage1.arrDataCacheSet = clearArrayElements(turnPage1.arrDataCacheSet);
	turnPage1.allArrDataCacheSet = clearArrayElements(turnPage1.allArrDataCacheSet);
	turnPage1.allCacheSize = 0;

	//使用模拟数据源，必须写在拆分之前
	turnPage1.useSimulation   = 1; 

	//查询成功则拆分字符串，返回二维数组
	turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult, 0, 0, turnPage1);
	var tKey = 1;

	//cTurnPage.allCacheSize ++;
	turnPage1.allArrDataCacheSet[turnPage1.allCacheSize%turnPage1.allArrCacheSize] = {id:tKey,value:turnPage1.arrDataCacheSet};			
	turnPage1.pageDisplayGrid = ProfitInfoGrid;
	turnPage1.pageIndex = 0;
	var arrDataSet = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, turnPage1.pageLineNum);
	displayMultiline(arrDataSet, turnPage1.pageDisplayGrid, turnPage1);

		if (turnPage1.showTurnPageDiv==1) {
		try {
			ProfitInfoGrid.setPageMark(turnPage1);
		} catch(ex){}
	}
}

