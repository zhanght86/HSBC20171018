/***************************************************************
 * <p>ProName：LCInsuredUploadInput.js</p>
 * <p>Title：人员清单导入</p>
 * <p>Description：人员清单导入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-27
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 批次信息查询
 */
function queryBatchInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql1");
	tSQLInfo.addSubPara(tGrpPropNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), BatchGrid,1,1);
}

/**
 * 批次明细信息查询
 */
function queryDetailInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	if (fm.State.value=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("1");
	} else if (fm.State.value=="1") {
		tSQLInfo.addSubPara("1");
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), BatchDetailGrid,1,1);
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 导入人员清单
 */
function importClick() {
	
	if (tContPlanType=="02") {//账户型产品导入
		
		tOperate = "UPLOAD3";
	} else {
		
		if (tUnFixedAmntFlag) {//非固定保额导入
			
			tOperate = "UPLOAD2";
		} else {//一般导入
			
			tOperate = "UPLOAD1";
		}
	}
	
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
	var tGrpPropIndex = fileName.lastIndexOf("-");
	if (tGrpPropIndex < 0) {
		alert("文件命名格式不正确，请重新命名！");
		return false;
	}
	if (tGrpPropNo != fileName.substring(0,tGrpPropIndex)) {
		alert("文件命名必须以投保书号开始，请重新命名！");
		return false;
	}
	
	var tSubOtherNo = fileName.substring(tGrpPropIndex+1, indexLast);
	if (isNaN(tSubOtherNo)) {
		
		alert("文件命名格式不正确！文件命名以投保书号开始，加上横杠，再加数字流水序列！");
		return false;
	}
	
	if (fileName.length>=30) {
		
		alert("文件名长度不能超过30！");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql3");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(fileName);
	
	var havaFile = easyExecSql(tSQLInfo.getString(),1,0);
	if (havaFile == null) {
		alert("文件是否已导入校验失败！");
		return false;
	} else if (havaFile[0][0] > 0) {
		alert("该批次文件已经导入,请重新命名导入文件！");
		return false;
	}
	
	fmupload.action="./LCInsuredUploadSave.jsp?OtherNoType=LIST&OtherNo="+tGrpPropNo+"&SubOtherNo="+tSubOtherNo+"&UploadNode="+tActivityID+"&Operate="+tOperate+"&AttachType=09";
	submitForm(fmupload);
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
	
	var tBatchNo = BatchGrid.getRowColData(tSelNo-1, 2);
	
	fm.SheetName[0].value = "被保险人清单";
	fm.SheetName[1].value = "受益人清单";
	
	//被保险人清单标题
	var tInsuredTitle = "序号^|与主被保险人关系^|主被保险人序号^|被保险人姓名^|证件类型^|证件号码^|性别^|出生日期^|"
										+ "保险方案^|职业代码^|开户银行^|开户名^|账号^|开户行所在省^|开户行所在市^|服务区域^|"
										+ "是否次标准体^|社保标记^|职级名称^|入司时间^|工龄^|月薪^|员工号^|证件是否长期^|证件有效期^|"
										+ "所在分公司^|所在部门^|被保险人编码^|工作地^|社保地^|邮政编码^|电子邮箱^|"
										+ "微信号^|联系电话^|移动电话^|联系地址(省)^|联系地址(市)^|联系地址(区/县)^|详细地址^|导入错误信息";
	
	//受益人清单标题
	var tBnfTitle = "被保险人序号^|受益人顺序^|姓名^|证件类型^|证件号码^|性别^|出生日期^|"
								+ "与被保险人关系^|受益比例(小数)^|导入错误信息";
							
	fm.SheetTitle[0].value = tInsuredTitle;
	fm.SheetTitle[1].value = tBnfTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql4");
	tSQLInfo.addSubPara(tBatchNo);
	
	fm.SheetSql[0].value = tSQLInfo.getString();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql5");
	tSQLInfo.addSubPara(tBatchNo);
	
	fm.SheetSql[1].value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}

/**
 * 提交数据
 */
function submitForm(tForm) {
	
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
	tForm.submit();
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

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}
