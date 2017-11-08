<%
/***************************************************************
 * <p>ProName：LSQuotExpandInit.jsp</p>
 * <p>Title：责任拓展</p>
 * <p>Description：责任拓展</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-03
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
		tTranProdType = getProdType(tQuotNo, tQuotBatNo);
		
		initPlanInfoGrid();
		initExpandInfoGrid();
		queryPlanInfo();
		
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
		fm.RiskCode.value = "";
		fm.RiskName.value = "";
		fm.DutyCode.value = "";
		fm.DutyName.value = "";
		fm.GetDutyCode.value = "";
		fm.GetDutyName.value = "";
		fm.ExpandType.value = "";
		fm.ExpandTypeName.value = "";
		fm.ExpandDuty.value = "";
		fm.ExpandDutyName.value = "";
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		if (tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			fm.AddButton.style.display = "";
			fm.ModButton.style.display = "";
			fm.DelButton.style.display = "";
		} else {
			
			fm.AddButton.style.display = "none";
			fm.ModButton.style.display = "none";
			fm.DelButton.style.display = "none";
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
 * 方案信息
 */
function initPlanInfoGrid() {
	
	turnPage1 = new turnPageClass();
	
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
		iArray[i][1] = "00px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "方案描述";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
				
		iArray[i] = new Array();
		iArray[i][0] = "方案类型";
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "是否有拓展";
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "平均年龄";
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "平均月薪(元)";
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "人数";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "参加社保占比";
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "男女比例";
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02' || tTranProdType=='03') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "方案类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		PlanInfoGrid = new MulLineEnter("fm", "PlanInfoGrid");
		PlanInfoGrid.mulLineCount = 1;
		PlanInfoGrid.displayTitle = 1;
		PlanInfoGrid.locked = 0;
		PlanInfoGrid.canSel = 1;
		PlanInfoGrid.canChk = 0;
		PlanInfoGrid.hiddenSubtraction = 1;
		PlanInfoGrid.hiddenPlus = 1;
		PlanInfoGrid.selBoxEventFuncName = "showDetailInfo";
		PlanInfoGrid.loadMulLine(iArray);
		
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}


/**
 * 拓展信息
 */
function initExpandInfoGrid() {
	
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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
				
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "给付项编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "给付项";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "拓展类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "拓展类型";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "扩大责任编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "扩大责任";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		ExpandInfoGrid = new MulLineEnter("fm", "ExpandInfoGrid");
		ExpandInfoGrid.mulLineCount = 0;
		ExpandInfoGrid.displayTitle = 1;
		ExpandInfoGrid.locked = 0;
		ExpandInfoGrid.canSel = 1;
		ExpandInfoGrid.canChk = 0;
		ExpandInfoGrid.hiddenSubtraction = 1;
		ExpandInfoGrid.hiddenPlus = 1;
		ExpandInfoGrid.selBoxEventFuncName = "showExpandInfo";
		ExpandInfoGrid.loadMulLine(iArray);
			
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}
</script>
