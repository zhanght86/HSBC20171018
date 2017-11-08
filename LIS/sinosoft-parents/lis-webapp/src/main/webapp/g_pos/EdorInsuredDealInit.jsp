<%
/***************************************************************
 * <p>ProName：EdorInsuredDealInit.jsp</p>
 * <p>Title：保全人员清单处理</p>
 * <p>Description：保全人员清单处理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
%>
<script language="JavaScript">
var tContPlanType = "";//00-普通保单，01-建工险，02-账户型险种，03-个人险种
var tUnFixedAmntFlag = false;//非固定保额标记，false-否，true-是
/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
		tSQLInfo.setSqlId("EdorInsuredDealSql6");
		tSQLInfo.addSubPara(tEdorAppNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			
			tContPlanType = strQueryResult[0][0];
		}
		
		//普通险种、建工险判断保险方案中是否含非固定保额
		if (tContPlanType=="00" || tContPlanType=="01") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
			tSQLInfo.setSqlId("EdorInsuredDealSql7");
			tSQLInfo.addSubPara(tEdorAppNo);
			
			var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (strQueryResult != null && strQueryResult[0][0]=="1") {
				
				tUnFixedAmntFlag = true;
				
				temp1.style.display = "none";
				temp2.style.display = "";
				temp3.style.display = "none";
			} else {
				
				temp1.style.display = "";
				temp2.style.display = "none";
				temp3.style.display = "none";
			}
		} else if (tContPlanType=="02") {
			
			temp1.style.display = "none";
			temp2.style.display = "none";
			temp3.style.display = "";
		}
		
		initInsuredListGrid();
		initBatchGrid();
		queryBatchInfo();
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

/**
 * 初始化列表
 */
function initInsuredListGrid() {
	
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
		iArray[i][0] = "流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人序号";
		iArray[i][1] = "28px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人姓名";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全生效日期";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "校验状态";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "校验结果描述";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "分组号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		InsuredListGrid = new MulLineEnter("fm", "InsuredListGrid");
		InsuredListGrid.mulLineCount = 0;
		InsuredListGrid.displayTitle = 1;
		InsuredListGrid.locked = 0;
		InsuredListGrid.canSel = 0;
		InsuredListGrid.canChk = 1;
		InsuredListGrid.hiddenPlus = 1;
		InsuredListGrid.hiddenSubtraction = 1;
		InsuredListGrid.selBoxEventFuncName = "";
		InsuredListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化InsuredListGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initBatchGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "批次号";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "导入人数";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "导入日期";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "导入时间";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		BatchGrid = new MulLineEnter("fmupload", "BatchGrid");
		BatchGrid.mulLineCount = 0;
		BatchGrid.displayTitle = 1;
		BatchGrid.locked = 0;
		BatchGrid.canSel = 1;
		BatchGrid.canChk = 0;
		BatchGrid.hiddenPlus = 1;
		BatchGrid.hiddenSubtraction = 1;
		BatchGrid.selBoxEventFuncName = "";
		BatchGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化BatchGrid时出错："+ ex);
	}
}
</script>
