<%
/***************************************************************
 * <p>ProName：EdorNCDetailInit.jsp</p>
 * <p>Title： 方案明细一览</p>
 * <p>Description： 方案明细一览</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-07-16
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
		initButton();
		
		if(initContPlanDetailPageInfo(tPolicyNo,tEdorNo,tEdorType)){
			showContPlanDetail(fm, strQueryResult,StartNum, tContPlanType);
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
