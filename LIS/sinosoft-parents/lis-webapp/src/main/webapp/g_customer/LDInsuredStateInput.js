/***************************************************************
 * <p>ProName：LDInsuredStateInput.js</p>
 * <p>Title：被保险人身故维护</p>
 * <p>Description：被保险人身故维护</p>
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
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LDInsuredStateSql");
	tSQLInfo.setSqlId("LDInsuredStateSql1");
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.InsuredNo.value);
	turnPage1.queryModal(tSQLInfo.getString(), InsuredInfoGrid,1,1);
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的信息!");
		return false;
	}
}

/**
 * 提交
 */
function submitFunc(){
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
	}

	fmupload.all('ImportPath').outerHTML = fmupload.all('ImportPath').outerHTML;
}

/**
 * 导入
 */
function importClick(){
	mOperate = "UPLOAD";
	
	var filePath = fmupload.ImportPath.value;
	if(filePath == null || filePath == ""){
		alert("请选择导入文件路径！");
		return false;
	}
	
	var indexFirst = filePath.lastIndexOf("\\");
	var indexLast = filePath.lastIndexOf(".xlsx");
	if(indexFirst < 0 || indexLast < 0 || indexLast <= indexFirst) {
		alert("文件路径不合法或选择的文件格式不正确，请重新选择！");
		return false;
	}
	
	var fileName = filePath.substring(indexFirst+1,indexLast);
	if (fileName.length>=30) {
		
		alert("文件名长度不能超过30！");
		return false;
	}
	/**
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_cs.LCInsuredServiceSql");
	tSQLInfo.setSqlId("LCInsuredServiceSql3");
	tSQLInfo.addSubPara(fileName);
	
	var havaFile = easyExecSql(tSQLInfo.getString(),1,0);
	if (havaFile == null) {
		alert("文件是否已导入校验失败！");
		return false;
	} 
	*/
	fmupload.action="./LDInsuredStateSave.jsp?Operate="+mOperate;
	submitFunc();
	fmupload.submit();
	
}

function errorExport(){
	window.open("./LCErrorExportMain.jsp","错误信息导出",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
