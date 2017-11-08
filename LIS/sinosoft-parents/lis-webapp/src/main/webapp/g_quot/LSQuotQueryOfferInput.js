/***************************************************************
 * <p>ProName：LSQuotQueryOfferInput.js</p>
 * <p>Title：报价查询</p>
 * <p>Description：报价查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-11-19
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询报价信息
 */
function queryQuotInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQueryOfferSql");
	tSQLInfo.setSqlId("LSQuotPrintSql1");
		
	tSQLInfo.addSubPara(fm.NameBJ.value);
	tSQLInfo.addSubPara(fm.QuotNoBJ.value);
	tSQLInfo.addSubPara(fm.QuotTypeBJ.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.OfferListNo.value);
	tSQLInfo.addSubPara(tManageCom);
	
	initOfferListGrid();
	turnPage1.queryModal(tSQLInfo.getString(), OfferListGrid, 2, 1);

	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 报价单下载
 */
function downloadQuot(parm1) {
	
	var tRow = OfferListGrid.getSelNo();
	//var tOfferListNo = document.all(parm1).all("OfferListGrid1").value;
	var tPrintState = document.all(parm1).all("OfferListGrid8").value;
	
	if (tPrintState=="0"){
		return false;
	} else {
		var tFileName = document.all(parm1).all("OfferListGrid11").value;
		var tFilePath = document.all(parm1).all("OfferListGrid12").value;
		window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
	}
}

/**
 * 报价信息查看
 */
function seeQuotation() {
	
	var tRow = OfferListGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条报价信息！");
		return false;	
	}

	var tOfferListNo = OfferListGrid.getRowColData(tRow-1,1);
	var tQuotNo = OfferListGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = OfferListGrid.getRowColData(tRow-1,3);
	var tQuotType = OfferListGrid.getRowColData(tRow-1,4);

	var tSrc = "";
	if (tQuotType == "00") {
		tSrc = "./LSQuotETSeeInput.jsp";
	} else if (tQuotType == "01") {
		tSrc = "./LSQuotProjSeeInput.jsp";
	}

	tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo+"&QuotType="+tQuotType+"&QuotQuery=Y&ReturnFlag=1";
	//location.href = tSrc;
	window.open(tSrc,"报价信息查看",'width=2000,height=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
