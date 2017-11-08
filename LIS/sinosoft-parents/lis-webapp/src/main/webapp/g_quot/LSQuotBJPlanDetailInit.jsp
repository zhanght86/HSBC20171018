<%
/***************************************************************
 * <p>ProName：LSQuotBJPlanDetailInit.jsp</p>
 * <p>Title：报价单打印--报价方案明细</p>
 * <p>Description：报价单打印--报价方案明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-20
 ****************************************************************/
%>
<script language="JavaScript">
var OnPage = 1;//当前显示的页数
var RowNum = 0;//记录的总数
var PageNum = 0;//记录的总页数
var StartNum = 1;//页面显示起始记录数
var strQueryResult;
var tShow = 1;//变更保费按钮展示

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tPrint = getPrintState();//modify by songsz 20150210 修改该字段为全局变量
		initQuotPlanDetailPageInfo(tOfferListNo);
		showQuotPlanDetail(fm, strQueryResult,StartNum, tQuotType, tTranProdType, tShow,tPrint,tQuotQuery);
		
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
	
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
	} catch (ex) {
		alert("初始化按钮错误！");
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
