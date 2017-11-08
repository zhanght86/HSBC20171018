/***************************************************************
 * <p>ProName：LCErrorExportInput.js</p>
 * <p>Title：错误信息导出</p>
 * <p>Description：错误信息导出</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-07-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 导出错误信息查询
 */
function queryClick() {
	if(fm.StartImportDate.value>fm.EndImportDate.value){
		alert("导入起期应晚于导入止期!");
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCErrorExportSql");
	tSQLInfo.setSqlId("LCErrorExportSql1");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(fm.StartImportDate.value);
	tSQLInfo.addSubPara(fm.EndImportDate.value);
	turnPage1.queryModal(tSQLInfo.getString(), BatchGrid,1,1);
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的信息!");
		return false;
	}
}

/**
 * 下载人员清单信息
 */
function downloadClick() {
	
	var tSelNo = BatchGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条批次信息！");
		return false;
	}
	
	var tBatchNo = BatchGrid.getRowColData(tSelNo-1, 1);
	
	fm.SheetName.value = "身份证清洗清单";
	
	//被保险人服务卡信息
	var tInsuredTitle = "序号^|被保险人姓名^|性别^|出生日期^|证件类型^|证件号码^|"
										+ "死亡日期^|导入失败原因";
							
	fm.SheetTitle.value = tInsuredTitle;
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCErrorExportSql");
	tSQLInfo.setSqlId("LCErrorExportSql2");
	tSQLInfo.addSubPara(tBatchNo);
	
	fm.SheetSql.value = tSQLInfo.getString();

	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}

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
		initForm();
	}
}
