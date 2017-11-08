/***************************************************************
 * <p>ProName：LSQuotSelectPlanInput.js</p>
 * <p>Title：选择报价方案</p>
 * <p>Description：选择报价方案</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询不允许的方案组合
 */
function queryNoPlanCombi() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), NoPlanCombiGrid, 1, 1);
}

/**
 * 询价方案
 */
function queryQuotPlan() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	if (tQuotType == '01') {
		tSQLInfo.addSubPara('and a.sysplancode = b.sysplancode and a.plancode = b.plancode');	
	} else if (tQuotType == '00') {
		tSQLInfo.addSubPara('');	
	}
	
	//turnPage2.queryModal(tSQLInfo.getString(), QuotPlanGrid, 1, 1);
	
	if (!noDiv(turnPage2, QuotPlanGrid, tSQLInfo.getString())) {
		initQuotPlanGrid();
		return false;
	}
}

/**
 * 报价选择方案
 */
function queryBJQuotPlan() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql3");
	tSQLInfo.addSubPara(tOfferListNo);
	
	if (tQuotType == '01') {
		tSQLInfo.addSubPara('and a.sysplancode = b.sysplancode and a.plancode = b.plancode');	
	} else if (tQuotType == '00') {
		tSQLInfo.addSubPara('');	
	}
	
	turnPage3.queryModal(tSQLInfo.getString(), BJQuotPlanGrid, 1, 1);
}

/**
 * 选择
 */
function selectClick() {
	
	var tPrint = getPrintState();
	if (tPrint!=null && tPrint=="1") {//已打印
		alert("该报价单已打印，不可进行该操作！");
		return false;
	}
	//判断是否选中了方案编码
	var chkNum=0;
	var tGrpNum=0;//账户险团体账户个数
	var tSingeNum=0;//账户险个人账户个数
	for ( var i=0;i<QuotPlanGrid.mulLineCount; i++ ) {
	
		if( QuotPlanGrid.getChkNo(i)) {	
			chkNum++;
			if (tTranProdType == "02") {
				var tType = QuotPlanGrid.getRowColData(i,13);
				if (tType == "02") {
					tSingeNum++;
				} else if (tType == "03") {
					tGrpNum++;
				}
			}
		}
	}
	if(chkNum==0) {
		alert("请选择询价方案信息！");
		return false;
	}
	
	//校验建工险时，只可选择一个方案
	if (tTranProdType=="01") {
		
		if (chkNum>1) {
			alert("建工险只可以选择一个方案！");
			return false;
		}
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
		tSQLInfo.setSqlId("LSQuotSelectPlanSql5");
		tSQLInfo.addSubPara(tOfferListNo);
		var tPlanNumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tPlanNumArr!=null && tPlanNumArr[0][0]>0) {
			alert("建工险只可以选择一个方案！");
			return false;
		}
	}
	//账户险时，校验必须有且只有一个团体账户，必须有个人账户（个人账户可多个）
	if (tTranProdType=="02") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
		tSQLInfo.setSqlId("LSQuotSelectPlanSql6");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tGrpNumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tGrpNumArr!=null && tGrpNumArr[0][0]==0) {
			if (tGrpNum==0 || tGrpNum>1) {
				alert("账户险必须有且只有一个方案的方案类型为团体账户！");
				return false;
			}
		} else if (tGrpNumArr!=null && tGrpNumArr[0][0]>0 && tGrpNum>0){
			alert("账户险必须有且只有一个方案的方案类型为团体账户！");
			return false;
		}
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
		tSQLInfo.setSqlId("LSQuotSelectPlanSql7");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tSingeNumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tSingeNumArr != null && tSingeNumArr[0][0]==0) {
			if (tSingeNum==0) {
				alert("账户险必须要有方案的方案类型为个人账户！");
				return false;
			}
		}
	}
	
	mOperate = "INSERT";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&OfferListNo="+ tOfferListNo +"&QuotType="+ tQuotType +"&TranProdType="+ tTranProdType;
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tPrint = getPrintState();
	if (tPrint!=null && tPrint=="1") {//已打印
		alert("该报价单已打印，不可进行该操作！");
		return false;
	}
	
	//判断是否选中了方案编码
	var chkFlag=false;
	for ( var i=0;i<BJQuotPlanGrid.mulLineCount; i++ ) {
	
		if( BJQuotPlanGrid.getChkNo(i)) {	
			chkFlag = true;
			break;
		}
	}
	if(!chkFlag) {
		alert("请选择报价所选方案信息！");
		return false;
	}
	
	mOperate = "DELETE";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&OfferListNo="+ tOfferListNo+"&QuotType="+ tQuotType +"&TranProdType="+ tTranProdType;
	submitForm();
}

/**
 * 捆绑选择
 */
function bindSelectClick() {
	
	var tPrint = getPrintState();
	if (tPrint!=null && tPrint=="1") {//已打印
		alert("该报价单已打印，不可进行该操作！");
		return false;
	}
	
	if (!checkBindSelect()) {
		return false;
	}
	
	mOperate = "BIND";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&OfferListNo="+ tOfferListNo +"&TranProdType="+ tTranProdType +"&QuotType="+ tQuotType;
	submitForm();

}

/**
 * 校验捆绑选择
 */
function checkBindSelect() {
	
	//勾选两个或两个以上询价方案
//	var chkNum=0;
//	for ( var i=0;i<QuotPlanGrid.mulLineCount; i++ ) {
//
//		if( QuotPlanGrid.getChkNo(i)) {	
//			chkNum++;
//		}
//	}
//	if (Number(chkNum)<2) {
//		alert("请勾选两个或两个以上询价方案!");
//		return false;
//	}
	
	//校验必须至少有一个方案为核心方案
	var tPlanFlagArr = [];
//	var tOccupTypeFlagArr = [];//职业类别标记数组
//	var tOccupTypeArr = [];//职业类别数组
//	var tOccupRateArr = [];//职业比例数组
	var tPlanCodeAdd="";//拼接方案编码字符串，用于查询险种责任
	var tNum = 0;
	for ( var i=0;i<QuotPlanGrid.mulLineCount; i++ ) {

		if( QuotPlanGrid.getChkNo(i)) {	
			var tPlanCode=QuotPlanGrid. getRowColData(i,2);//方案编码
			var tPlanType=QuotPlanGrid. getRowColData(i,13);//方案类型
			var tPlanFlag=QuotPlanGrid. getRowColData(i,14);//方案标识
//			var tOccupTypeFlag=QuotPlanGrid. getRowColData(i,4);//职业类别标记
//			var tOccupType=QuotPlanGrid. getRowColData(i,5);//职业类别
//			var tOccupRate=QuotPlanGrid. getRowColData(i,14);//职业比例
			
			if (tPlanType=="00") {
				
				//add by ZhangC 20140830 职业类别必须相等才能捆绑 
//modify by ZhangC	20141204	职业类型相关捆绑规则变动，所有校验放到后台处理				
//				if (tNum>0) {
//					if (!in_Array(tOccupTypeFlag,tOccupTypeFlagArr)) {//职业类型标记不相等
//						alert("职业类型相等时才能进行捆绑!");
//						return false;
//					} else {//职业类型标记相等
//						if (tOccupTypeFlag=="2") {//多职业时，校验最低职业类别相等，最高职业类别相等，职业比例相等
//							
//							if (!in_Array(tOccupType,tOccupTypeArr)) {//校验职业类别是否相等
//								alert("当为多职业类别时，所选方案最低职业类别相等且最高职业类别相等时，才能进行捆绑!");
//								return false;
//							}
//							
//							if (!in_Array(tOccupRate,tOccupRateArr)) {//校验职业比例是否相等
//								alert("当为多职业类别时，所选方案职业比例都相等时才能进行捆绑!");
//								return false;
//							}
//						}
//					}
//				}
//				
//				tOccupTypeFlagArr.push(tOccupTypeFlag);
//				tOccupTypeArr.push(tOccupType);
//				tOccupRateArr.push(tOccupRate);
//				tNum++;
				
				tPlanFlagArr.push(tPlanFlag);
				tPlanCodeAdd += "'"+tPlanCode + "',";
				
			} else {
				alert("第["+(i+1)+"]行公共保额方案不可捆绑！");
				return false;
			}
		}
	}
	
	if (!in_Array("00",tPlanFlagArr)) {//核心方案--00
		alert("至少需要有一个方案为核心方案!");
		return false;
	}

	//捆绑的方案中不能含有相同的险种责任
	tPlanCodeAdd = tPlanCodeAdd.substring(0,tPlanCodeAdd.length -1);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tPlanCodeAdd);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr != null) {
		alert("捆绑的方案中不能含有相同的险种责任!");
		return false;
	}
	
	var tBindPlanDesc = fm.BindPlanDesc.value;
	if (tBindPlanDesc==null || tBindPlanDesc=="") {
		alert("捆绑方案描述不能为空！");
		return false;
	}
	return true;
}

/**
 * 判断一个数是否在数组中
 */
function in_Array(search,array){
	
    for(var i in array){
        if(array[i]==search){
            return true;
        }
    }
    return false;
}

/**
 * 提交
 */
function submitForm() {
	
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
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
		//QuotPlanGrid.checkBoxAllNot();//清空所有勾选
		queryQuotPlan();
		queryBJQuotPlan();
		fm.BindPlanDesc.value = "";
		
		showFixedValue();
	}
}

/**
 * 获取该报价单打印状态
 */
function getPrintState() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql8");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tStateArr!=null) {
		return tStateArr[0][0];
	} else {
		return null;
	}
}

/**
 * 项目询价建工险时，处理工程造价、工程面积录入框展示
 */
function showFixedValue() {
	
	if (mOperate == "INSERT" || mOperate == "SAVE") {
		
		if (tQuotType =="01" && tTranProdType == "01") {
			showFixedValueSub();
		}
	} else {
		document.all("divFixedValue").style.display = "none";
		document.all("tdFixed1").style.display = "none";
		document.all("tdFixed2").style.display = "none";
		document.all("tdFixed3").style.display = "none";
		document.all("tdFixed4").style.display = "none";
		fm.EnginCost.value = "";
		fm.EnginArea.value = "";
	}
}

/**
 * 根据保费计算方式处理 工程造价、工程面积录入框展示
 */
function showFixedValueSub() {
	
	var tPremCalType=BJQuotPlanGrid.getRowColData(0,10);//保费计算方式
	
	if (tPremCalType == "2") {//按造价
		
		document.all("divFixedValue").style.display = "";
		document.all("tdFixed1").style.display = "";
		document.all("tdFixed2").style.display = "";
		document.all("tdFixed3").style.display = "none";
		document.all("tdFixed4").style.display = "none";
		fm.EnginArea.value = "";
	} else if (tPremCalType == "3") {//按面积
		
		document.all("divFixedValue").style.display = "";
		document.all("tdFixed1").style.display = "none";
		document.all("tdFixed2").style.display = "none";
		document.all("tdFixed3").style.display = "";
		document.all("tdFixed4").style.display = "";
		fm.EnginCost.value = "";
	} else { //按人数
		
		document.all("divFixedValue").style.display = "none";
		document.all("tdFixed1").style.display = "none";
		document.all("tdFixed2").style.display = "none";
		document.all("tdFixed3").style.display = "none";
		document.all("tdFixed4").style.display = "none";
		fm.EnginCost.value = "";
		fm.EnginArea.value = "";
	}
	if (mOperate == "SAVE") {
		fm.EnginCost.value = "";
		fm.EnginArea.value = "";
	}
}
/**
 * 刷新界面时，初始化 项目询价 建工险 界面
 */
function initFixedValue() {
	
	if (tQuotType =="01" && tTranProdType == "01") {//项目询价建工险时
		
		var tOfferCout = BJQuotPlanGrid.mulLineCount;
		if (Number(tOfferCout)==0) {//无报价方案记录时，隐藏录入框
			hiddenFixedValue();
		} else {
			
			var tPrintState = getPrintState();
			if (tPrintState!=null && tPrintState=="-1") {//未打印
				
				if (tQuotQuery=="Y") {//询价查询界面进入
					hiddenFixedValue();
				} else {
					showFixedValueSub();
				}
			} else {
				hiddenFixedValue();
			}
		}
		
	} else {//其他情况全隐藏
		hiddenFixedValue();
	}
}

/**
 * 隐藏工程造价 工程面积 录入框
 */
function hiddenFixedValue() {
	
	document.all("divFixedValue").style.display = "none";
	document.all("tdFixed1").style.display = "none";
	document.all("tdFixed2").style.display = "none";
	document.all("tdFixed3").style.display = "none";
	document.all("tdFixed4").style.display = "none";
	fm.EnginCost.value = "";
	fm.EnginArea.value = "";
}

/**
 * 保存项目询价建工险时，工程造价、工程面积数据前校验
 */
function checkFixedValue() {
	
	var tOfferCout = BJQuotPlanGrid.mulLineCount;
	if (Number(tOfferCout)==0) {
		alert("请先选择报价方案！");
		return false;
	}
	var tPremCalType=BJQuotPlanGrid.getRowColData(0,10);//保费计算方式
	
	if (tPremCalType == "2") {//按造价
		
		//校验工程造价
		var tEnginCost = fm.EnginCost.value;
		if (tEnginCost==null || tEnginCost=="") {
			alert("工程造价不能为空！");
			return false;
		} else {
			
			//工程造价,应为大于0的有效数字
			if (!isNumeric(tEnginCost) || Number(tEnginCost)<0) {
				alert("工程造价应为大于0的有效数字！");
				return false;
			}
			
			if (!checkDecimalFormat(tEnginCost, 18, 2)) {
				alert("工程造价整数位不应超过18位,小数位不应超过2位！");
				return false;
			}
		}
		
	}  else if (tPremCalType == "3") {//按面积
		
		//校验工程面积
		var tEnginArea = fm.EnginArea.value;
		if (tEnginArea==null || tEnginArea=="") {
			alert("工程面积不能为空！");
			return false;
		} else {
			
			//工程面积,应为大于0的有效数字
			if (!isNumeric(tEnginArea) || Number(tEnginArea)<0) {
				alert("工程面积应为大于0的有效数字！");
				return false;
			}
			
			if (!checkDecimalFormat(tEnginArea, 18, 2)) {
				alert("工程面积整数位不应超过18位,小数位不应超过2位！");
				return false;
			}
		}
	}
	
	//校验 工程造价/工程面积是否在询价最低、最高之间
	var tSysPlanCode = BJQuotPlanGrid.getRowColData(0,2);//报价系统方案编码
	var tPlanCode = BJQuotPlanGrid.getRowColData(0,3);//方案编码
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql9");
	tSQLInfo.addSubPara(tPremCalType);
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tOfferListNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	
	var tMinValue = "";
	var tMaxValue = "";
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr == null) {
		alert("查询询价相关信息失败！");
		return false;
	} else {
		tMinValue = tArr[0][0];
		tMaxValue = tArr[0][1];
	}
	
	if (tPremCalType == "2") {//按造价
		
		var tValue = fm.EnginCost.value;
		if ((Number(tValue) < Number(tMinValue)) || (Number(tValue) > Number(tMaxValue)) ) {
			alert("工程造价应在"+tMinValue+"(元)到"+tMaxValue+"(元)之间！");
			return false;
		}
	}
	if (tPremCalType == "3") {//按面积
		
		var tValue = fm.EnginArea.value;
		if ((Number(tValue) < Number(tMinValue)) || (Number(tValue) > Number(tMaxValue)) ) {
			alert("工程面积应在"+tMinValue+"(平方米)到"+tMaxValue+"(平方米)之间！");
			return false;
		}
	}
	return true;
}

/**
 * 保存项目询价建工险时，工程造价、工程面积数据
 */
function saveFixedValue() {
	
	if (!checkFixedValue()) {
		return false;
	}
	var tOfferListNo=BJQuotPlanGrid.getRowColData(0,1);//报价号
	var tSysPlanCode=BJQuotPlanGrid.getRowColData(0,2);//报价系统方案编码
	var tPlanCode=BJQuotPlanGrid.getRowColData(0,3);//报价方案编码
	var tPremCalType=BJQuotPlanGrid.getRowColData(0,10);//保费计算方式
	
	mOperate = "SAVE";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?OfferListNo="+tOfferListNo+"&SysPlanCode="+tSysPlanCode+"&PlanCode="+tPlanCode+"&PremCalType="+tPremCalType+"&TranProdType="+ tTranProdType +"&QuotType="+ tQuotType;
	submitForm();
}
