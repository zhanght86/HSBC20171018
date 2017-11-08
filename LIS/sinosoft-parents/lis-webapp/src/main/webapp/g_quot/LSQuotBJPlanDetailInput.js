/***************************************************************
 * <p>ProName：LSQuotBJPlanDetailInput.js</p>
 * <p>Title：报价单打印--报价方案明细</p>
 * <p>Description：报价单打印--报价方案明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-20
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();
var tPrint;

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
	}
}

/**
 * 展示方案明细
 */
function showQuotPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cShow,cPrint,cQuotQuery) {
	
	cObj.PageInfo.value = "第"+OnPage+"/"+PageNum+"页";
	document.all("divBJPlanDetail").innerHTML = pubQuotPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cShow,cPrint,cQuotQuery);
}

/**
 * 展示指定页
 */
function goToPage(OnPage) {
	
	tStartNum = (OnPage-1)*tPlanShowRows+1;
	showQuotPlanDetail(fm, strQueryResult,tStartNum, tQuotType, tTranProdType, tShow,tPrint,tQuotQuery);
}

/**
 * 变更保费
 */
function changDetail(cOfferListNo,cQuotNo,cQuotBatNo,cSysPlanCode,cPlanCode) {
	
	window.open("./LSQuotChangePremMain.jsp?QuotNo="+ cQuotNo + "&QuotBatNo="+ cQuotBatNo +"&SysPlanCode="+ cSysPlanCode +"&PlanCode="+ cPlanCode+"&OfferListNo="+ cOfferListNo+"&PrintState="+ tPrint+"&QuotQuery="+tQuotQuery,"变更保费",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 获取该报价单打印状态
 */
function getPrintState() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql8");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tStateArr!=null) {
		return tStateArr[0][0];
	} else {
		return null;
	}
}
