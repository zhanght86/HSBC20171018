<%
/***************************************************************
 * <p>ProName：LSQuotPlanAllDetailInit.jsp</p>
 * <p>Title：方案明细一览</p>
 * <p>Description：方案明细一览</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
%>
<script language="JavaScript">
var OnPage = 1;//当前显示的页数
var RowNum = 0;//记录的总数
var PageNum = 0;//记录的总页数
var StartNum = 1;//页面显示起始记录数
var strQueryResult;

/**
 * 初始化界面
 */
function initForm() {

	try {
		
		initParam();
		initInpBox();
		
		if (tMark == "1") {
			document.all("divConfluence").style.display = "none";
			document.all("divTurnPage").style.display = "none";
		}
		tTranProdType = getProdType(tQuotNo, tQuotBatNo);
		initPubDetailPageInfo(tQuotNo, tQuotBatNo,tSysPlanCode,tPlanCode,tMark);
		showAllPlanDetail(fm, strQueryResult,StartNum, tQuotType, tTranProdType, tActivityID);
		
		if ((tActivityID=="0800100002" || tActivityID=="0800100003" || tActivityID=="0800100004") && tTranProdType!="02") {
			
			document.all("divImp").style.display = "";
		} else {
			document.all("divImp").style.display = "none";
		}
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
		tSQLInfo.setSqlId("LSQuotAllDetailSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tTotalArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tTotalArr != null) {
			
			fm.SumPrem.value = tTotalArr[0][0];
			fm.SumNonMedPrem.value = tTotalArr[0][1];
			fm.SumMedPrem.value = tTotalArr[0][2];
			fm.SumPerIllPrem.value = tTotalArr[0][3];
		}
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 把null的字符串转为空
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}
</script>
