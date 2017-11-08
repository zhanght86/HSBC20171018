/***************************************************************
 * <p>ProName：FinExtractInput.js</p>
 * <p>Title：会计分录提取</p>
 * <p>Description：会计分录提取</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态

var tSQLInfo = new SqlClass();

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
	fm.Operate.value = mOperate;
	fm.action = "./FinExtractSave.jsp";
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, tFilePath, tFileName) {
	
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		if (mOperate=="download") {
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName
		}
	}	
}

/**
 * 查询会计分录信息
 */
function queryData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	var tStartDate = fm.StartDate.value;
	var tEndDate = fm.EndDate.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinExtractSql");
	tSQLInfo.setSqlId("FinExtractSql1");
	tSQLInfo.addSubPara(tStartDate);
	tSQLInfo.addSubPara(tEndDate);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinExtractGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("没有查询到数据！");
		return false;
	}
	
}

//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	var tStartDate = fm.StartDate.value;
	var tEndDate = fm.EndDate.value;
	
	//报表标题
	var tTitle = "SourceBatchID^|AccountingDate^|CurrencyCode^|CurrencyConversionDate^|CurrencyConversionRate^|CurrencyConversionType^|EnteredDr^|EnteredCr^|AccountedDr^|AccountedCr^|ActualFlag^|Segment1^|Segment2^|Segment3^|Segment4^|Segment5^|Segment6^|Segment7^|Segment8^|Segment9^|LineDescription^|Attribute1^|Attribute2^|Attribute3^|Attribute4^|Attribute5^|Attribute6^|Attribute7^|Attribute8^|Attribute9^|Attribute10^|Attribute11^|Attribute12^|Attribute13^|Attribute14^|Attribute15";
	//报表提数SQL
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinExtractSql");
	tSQLInfo.setSqlId("FinExtractSql2");
	tSQLInfo.addSubPara(tStartDate);
	tSQLInfo.addSubPara(tEndDate);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

/**
 * 提取数据
 */
function extractData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "extract";
	submitForm();
}

/**
 * 删除数据
 */
function deleteData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "delete";
	submitForm();
}

/**
 * 下载财务接口文件
 */
function downloadData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "download";
	submitForm();
}
