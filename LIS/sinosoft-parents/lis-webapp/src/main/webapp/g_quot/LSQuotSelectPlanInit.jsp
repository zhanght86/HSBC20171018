<%
/***************************************************************
 * <p>ProName：LSQuotSelectPlanInit.js</p>
 * <p>Title：选择报价方案</p>
 * <p>Description：选择报价方案</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-19
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		initNoPlanCombiGrid();
		initQuotPlanGrid();
		initBJQuotPlanGrid();
		
		queryNoPlanCombi();
		queryQuotPlan();
		queryBJQuotPlan();
		
		initFixedValue();//项目询价建工险初始界面展示
		
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
		
		if (tQuotQuery == "Y") {//查询界面进入时，无论是已打印或未打印，都隐藏按钮
		
			document.all("divBindPlan").style.display = "none";
			document.all("divFixedValue").style.display = "none";
			fm.SelectButton.style.display = "none";
			fm.DelButton.style.display = "none";
		
		} else {
		
			var tPrintState = getPrintState();
			if (tPrintState!=null && tPrintState=="-1") {//未打印
				
				//仅产品类型为普通险种、个人险种可以进行捆绑
				if (tTranProdType=="00"|| tTranProdType=="03") {
					document.all("divBindPlan").style.display = "";		
				} else {
					document.all("divBindPlan").style.display = "none";	
				}
				fm.SelectButton.style.display = "";
				fm.DelButton.style.display = "";	
			} else {//已打印
			
				document.all("divBindPlan").style.display = "none";
				document.all("divFixedValue").style.display = "none";
				fm.SelectButton.style.display = "none";
				fm.DelButton.style.display = "none";	
			}
		}
		
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

/**
 * 不允许的方案组合
 */
function initNoPlanCombiGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "序号1";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案组合";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "限制范围";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "组合类型";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		NoPlanCombiGrid = new MulLineEnter("fm", "NoPlanCombiGrid");
		NoPlanCombiGrid.mulLineCount = 0;
		NoPlanCombiGrid.displayTitle = 1;
		NoPlanCombiGrid.locked = 1;
		NoPlanCombiGrid.canSel = 0;
		NoPlanCombiGrid.canChk = 0;
		NoPlanCombiGrid.hiddenPlus = 1;
		NoPlanCombiGrid.hiddenSubtraction = 1;
		//NoPlanCombiGrid.selBoxEventFuncName = "";
		NoPlanCombiGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}

/**
 * 询价方案
 */
function initQuotPlanGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案描述";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别标记";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "平均年龄(岁)";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "平均月薪(元)";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "保费计算方式";
		if (tTranProdType=='01') {//只有建工险时展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='01') {//只有建工险时展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "人数";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {//建工险也进行展示
			iArray[i][1] = "10px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "参加社保占比";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "男女比例";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "方案类型";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案标识";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业比例";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tQuotType=='01') {//项目询价  
		
			iArray[i] = new Array();
			iArray[i][0] = "最低工程造价/最低工程面积";
			if (tTranProdType=='01') {
				iArray[i][1] = "18px";
			} else {
				iArray[i][1] = "0px";
			}
			iArray[i][2] = 300;
			if (tTranProdType=='01') {
				iArray[i++][3] = 0;
			} else {
				iArray[i++][3] = 3;
			}
			
			iArray[i] = new Array();
			iArray[i][0] = "最高工程造价/最高工程面积";
			if (tTranProdType=='01') {
				iArray[i][1] = "18px";
			} else {
				iArray[i][1] = "0px";
			}
			iArray[i][2] = 300;
			if (tTranProdType=='01') {
				iArray[i++][3] = 0;
			} else {
				iArray[i++][3] = 3;
			}
		} 
		
		if (tQuotType=='00') {//一般询价
			
			iArray[i] = new Array();
			iArray[i][0] = "最低工程造价/最低工程面积";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "工程造价/工程面积";
			if (tTranProdType=='01') {
				iArray[i][1] = "18px";
			} else {
				iArray[i][1] = "0px";
			}
			iArray[i][2] = 300;
			if (tTranProdType=='01') {
				iArray[i++][3] = 0;
			} else {
				iArray[i++][3] = 3;
			}
		} 
		
		QuotPlanGrid = new MulLineEnter("fm", "QuotPlanGrid");
		QuotPlanGrid.mulLineCount = 0;
		QuotPlanGrid.displayTitle = 1;
		QuotPlanGrid.locked = 1;
		QuotPlanGrid.canSel = 0;
		QuotPlanGrid.canChk = 1;
		QuotPlanGrid.hiddenPlus = 1;
		QuotPlanGrid.hiddenSubtraction = 1;
		//QuotPlanGrid.selBoxEventFuncName = "";
		QuotPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}

/**
 * 报价所选方案
 */
function initBJQuotPlanGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "报价单号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案描述";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别标记";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "平均年龄(岁)";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "平均月薪(元)";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "保费计算方式编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费计算方式";
		if (tTranProdType=='01') {//只有建工险时展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='01') {//只有建工险时展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "人数";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {//建工险也进行展示
			iArray[i][1] = "10px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "参加社保占比";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "男女比例";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "工程造价/工程面积";
		if (tTranProdType=='01') {
			iArray[i][1] = "18px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='01') {
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		BJQuotPlanGrid = new MulLineEnter("fm", "BJQuotPlanGrid");
		BJQuotPlanGrid.mulLineCount = 0;
		BJQuotPlanGrid.displayTitle = 1;
		BJQuotPlanGrid.locked = 1;
		BJQuotPlanGrid.canSel = 0;
		BJQuotPlanGrid.canChk = 1;
		BJQuotPlanGrid.hiddenPlus = 1;
		BJQuotPlanGrid.hiddenSubtraction = 1;
		//BJQuotPlanGrid.selBoxEventFuncName = "";
		BJQuotPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}
</script>
