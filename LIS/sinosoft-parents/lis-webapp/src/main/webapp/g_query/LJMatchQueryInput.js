/***************************************************************
 * <p>ProName：LJMatchQueryInput.jsp</p>
 * <p>Title：保费查询</p>
 * <p>Description：保费查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-08-11
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
function queryInfo() {
	
	initMatchInfoGrid();
	divInfo3.style.display = "none";
	divInfo4.style.display = "none";
	divInfo5.style.display = "none";
	initChoosedDataGrid();
	initBusinessDataGrid();
	initUploadFileGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql16");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryMatchState.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryStartDate4.value);
	tSQLInfo.addSubPara(fm.QueryEndDate4.value);
	tSQLInfo.addSubPara(fm.QueryStartDate5.value);
	tSQLInfo.addSubPara(fm.QueryEndDate5.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), MatchInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function showMatchDetail() {

	var tSelNo = MatchInfoGrid.getSelNo()-1;
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	
	divInfo3.style.display = "none";
	divInfo4.style.display = "none";
	divInfo5.style.display = "none";
	
	queryMatchFeeInfo(tMatchSerialNo);
	queryMatchPayInfo(tMatchSerialNo);
	queryAttachmentInfo(tMatchSerialNo);
}

/**
 * 查询所选匹配记录收费数据
 */
function queryMatchFeeInfo(o) {
	
	initChoosedDataGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql17");
	tSQLInfo.addSubPara(o);
	
	turnPage2.queryModal(tSQLInfo.getString(), ChoosedDataGrid, 0, 1);
	
	if (turnPage2.strQueryResult) {
		divInfo3.style.display = "";
	} else {
		divInfo3.style.display = "none";
	}
}

/**
 * 查询所选匹配记录应收数据
 */
function queryMatchPayInfo(o) {
	
	initBusinessDataGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LJMatchSql18");
	}else if(_DBT==_DBM){
		tSQLInfo.setSqlId("LJMatchSql26");
	}
	
	tSQLInfo.addSubPara(o);
	
	turnPage3.queryModal(tSQLInfo.getString(), BusinessDataGrid, 0, 1);
	
	if (turnPage3.strQueryResult) {
		divInfo4.style.display = "";
	} else {
		divInfo4.style.display = "none";
	}
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
	
	turnPage4.queryModal(tSQLInfo.getString(), UploadFileGrid, 0, 1);
	
	if (turnPage4.strQueryResult) {
		divInfo5.style.display = "";
	} else {
		divInfo5.style.display = "none";
	}
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
	
	fm.SheetName.value = "保费信息列表";
	
	var tTitle = "管理机构^|保费申请批次^|收费金额^|使用溢缴金额^|应收金额^|本次溢缴金额^|保费状态^|申请人" +
			"^|申请日期^|申请提交日期^|匹配操作人^|匹配日期^|匹配提交日期^|匹配结论^|结论描述^|审核操作^|审核日期^|审核结论^|审核描述";
			

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql19");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryMatchState.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryStartDate4.value);
	tSQLInfo.addSubPara(fm.QueryEndDate4.value);
	tSQLInfo.addSubPara(fm.QueryStartDate5.value);
	tSQLInfo.addSubPara(fm.QueryEndDate5.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
