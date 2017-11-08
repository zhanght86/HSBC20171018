/***************************************************************
 * <p>ProName：LJPremMatchConfirmInput.jsp</p>
 * <p>Title：保费匹配</p>
 * <p>Description：保费匹配</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryMatchInfo() {
	
	initMatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql15");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), MatchInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function matchConfirmClick() {
	
	fm3.all("MatchConfirmButton").disabled = true;
	if (!beforeMatchConfirmClick()) {
		fm3.all("MatchConfirmButton").disabled = false;
		return false;
	}
	
	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的数据！");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	
	tOperate = "MATCHCONFIRM";
	fmPub.Operate.value = tOperate;
	fm3.action = "./LJPremMatchConfirmSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm3);
}

function beforeMatchConfirmClick() {

	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的数据！");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	
	var tConfirmConclusion = fm3.ConfirmConclusion.value;
	var tConfirmDesc = fm3.ConfirmDesc.value;
	if (tConfirmConclusion==null || tConfirmConclusion=="") {
		alert("审核结论不能为空！");
		return false;
	}
	
	if (tConfirmConclusion=="01") {
		
		if (tConfirmDesc==null || tConfirmDesc=="") {
			alert("退回时结论描述不能为空！");
			return false;
		}
	}
	
	if (tConfirmDesc.length>300) {
		alert("结论描述应在300字内！");
		return false;
	}
	
	return true;
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
		
		fm3.all("MatchConfirmButton").disabled = false;
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
		
		fm3.all("MatchConfirmButton").disabled = false;
		dealAfterSubmit();
	}
}

function dealAfterSubmit() {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="MATCHCONFIRM") {
	
		fm3.ConfirmConclusion.value = "";
		fm3.ConfirmConclusionName.value = "";
		fm3.ConfirmDesc.value = "";
		initMatchInfoGrid();
		initChoosedData1Grid();
		initBusinessData1Grid();
		initUploadFileGrid();
		divMatchingConfirmGrid.style.display = "none";
		divUploadInfo.style.display = "none";
	}
}

/**
 * 查询所选匹配记录收费数据
 */
function queryMatchFeeInfo(o) {
	
	initChoosedData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql13");
	tSQLInfo.addSubPara(o);
	
	turnPage6.queryModal(tSQLInfo.getString(), ChoosedData1Grid, 0, 1);
}

/**
 * 查询所选匹配记录应收数据
 */
function queryMatchPayInfo(o) {
	
	initBusinessData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LJMatchSql14");
	}else if(_DBT==_DBM){
		tSQLInfo.setSqlId("LJMatchSql25");
	}
	tSQLInfo.addSubPara(o);
	
	turnPage7.queryModal(tSQLInfo.getString(), BusinessData1Grid, 0, 1);
}

/**
 * 查询该匹配上传的附件信息
 */
function queryAttachmentInfo(o) {

	initUploadFileGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql7");
	tSQLInfo.addSubPara(o);
	
	turnPage8.queryModal(tSQLInfo.getString(), UploadFileGrid, 0, 1);
	
	if (turnPage8.strQueryResult) {
		divUploadInfo.style.display = "";
	} else {
		divUploadInfo.style.display = "none";
	}
}

function showMatchDetail() {

	var tSelNo = MatchInfoGrid.getSelNo()-1;
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	
	queryMatchFeeInfo(tMatchSerialNo);
	queryMatchPayInfo(tMatchSerialNo);
	
	divMatchingConfirmGrid.style.display = "";
	queryAttachmentInfo(tMatchSerialNo);
}

/**
 * 下载附件
 */
function downLoadClick(parm1) {
	
	var tFileName = document.all("UploadFileGrid3").value;
	var tFilePath = document.all("UploadFileGrid4").value;
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "待审核信息列表";
	
	var tTitle = "管理机构^|保费申请批次^|收费金额^|使用溢缴金额^|应收金额^|本次溢缴金额^|申请人^|申请日期" +
			"^|申请提交日期^|匹配操作人^|匹配日期^|匹配提交日期";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql21");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
