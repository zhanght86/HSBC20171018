/***************************************************************
 * <p>ProName：LSQuotPlanImpInfoInput.js</p>
 * <p>Title：询价方案导入信息</p>
 * <p>Description：询价方案导入信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-07-16
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询方案导入信息列表
 */
function queryPlanImpList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
	tSQLInfo.setSqlId("LSQuotPlanImpInfoSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm.BacthNo.value);
	tSQLInfo.addSubPara(fm.ImpState.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), PlanImpInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 下载人员清单信息
 */
function downloadClick() {
	
	var tSelNo = PlanImpInfoGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条批次信息！");
		return false;
	}
	
	var tImpState = PlanImpInfoGrid.getRowColData(tSelNo-1, 5);
	if (tImpState=="1") {
		alert("该批次导入方案全部正确，无批次错误信息！");
		return false;
	}
	var tBatchNo = PlanImpInfoGrid.getRowColData(tSelNo-1, 1);
	if (tQuotType=="00" && tTranProdType == "00") {//一般询价	普通险种
		
		fm.SheetName[0].value = "方案信息";
		fm.SheetName[1].value = "方案明细信息";
		fm.SheetName[2].value = "主附共用配置";
		
		//方案信息
		var tPlanTitle = "方案序号^|方案描述^|方案类型^|方案标识^|职业类型^|最低职业类别^|最高职业类别^|职业比例^|"
				+ "职业类别^|职业中分类^|工种^|最低年龄^|最高年龄^|平均年龄^|人数^|参加社保占比^|"
				+ "男女比例^|退休占比^|保费分摊方式^|企业负担占比^|最低月薪^|最高月薪^|平均月薪^|其他说明^|导入错误信息";

		// 方案明细信息
		var tPlanDetailTitle = "方案序号^|险种编码^|责任编码^|保额类型^|固定保额^|月薪倍数^|最低保额^|最高保额^|期望保费类型^|期望保费/费率/折扣^|主附共用配置^|备注^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		// 主附共用配置
		var tRelaShareITitle = "方案序号^|险种编码^|责任编码^|主被保险人保额占比^|附属被保险人保额占比^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		fm.SheetTitle[2].value = tRelaShareITitle;
		
		//方案
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql2");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//方案明细
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql3");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//主附共用配置
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql4");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="00" && tTranProdType == "01") {//一般询价	建工险

		fm.SheetName[0].value = "方案信息";
		fm.SheetName[1].value = "方案明细信息";
		fm.SheetName[2].value = "Sheet1";//无主附配置Sheet页
		
		//方案信息
		var tPlanTitle = "方案序号^|方案描述^|保费计算方式^|人数^|其他说明^|导入错误信息";

		// 方案明细信息
		var tPlanDetailTitle = "方案序号^|险种编码^|责任编码^|保额类型^|固定保额^|最低保额^|最高保额^|期望保费类型^|期望保费/费率/折扣^|备注^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//方案
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql5");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//方案明细
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql6");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//无主附共用配置，虚拟空数据
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="00" && tTranProdType == "02") {//一般询价	账户险种

		fm.SheetName[0].value = "方案信息";
		fm.SheetName[1].value = "方案明细信息";
		fm.SheetName[2].value = "Sheet1";//无主附配置Sheet页
		
		//方案信息
		var tPlanTitle = "方案序号^|方案描述^|方案类型^|职业类型^|最低职业类别^|最高职业类别^|职业比例^|"
				+ "职业类别^|职业中分类^|工种^|最低年龄^|最高年龄^|平均年龄^|人数^|参加社保占比^|"
				+ "男女比例^|退休占比^|保费分摊方式^|企业负担占比^|最低月薪^|最高月薪^|平均月薪^|其他说明^|导入错误信息";

		// 方案明细信息
		var tPlanDetailTitle = "方案序号^|险种编码^|责任编码^|初始保费^|期望收益率^|备注^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//方案
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql8");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//方案明细
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql9");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//无主附共用配置，虚拟空数据
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="01" && tTranProdType == "00") {//项目询价	普通险种
		
		fm.SheetName[0].value = "方案信息";
		fm.SheetName[1].value = "方案明细信息";
		fm.SheetName[2].value = "主附共用配置";
		
		//方案信息
		var tPlanTitle = "方案序号^|方案描述^|保险期间^|保险期间单位^|方案类型^|方案标识^|职业类型^|最低职业类别^|最高职业类别^|职业比例^|"
				+ "职业类别^|职业中分类^|工种^|最低年龄^|最高年龄^|平均年龄^|人数^|参加社保占比^|"
				+ "男女比例^|退休占比^|保费分摊方式^|企业负担占比^|最低月薪^|最高月薪^|平均月薪^|其他说明^|导入错误信息";

		// 方案明细信息
		var tPlanDetailTitle = "方案序号^|险种编码^|责任编码^|保额类型^|固定保额^|月薪倍数^|最低保额^|最高保额^|期望保费类型^|期望保费/费率/折扣^|主附共用配置^|备注^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		// 主附共用配置
		var tRelaShareITitle = "方案序号^|险种编码^|责任编码^|主被保险人保额占比^|附属被保险人保额占比^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		fm.SheetTitle[2].value = tRelaShareITitle;
		
		//方案
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql10");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//方案明细
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql11");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//主附共用配置
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql12");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="01" && tTranProdType == "01") {//项目询价  建工险

		fm.SheetName[0].value = "方案信息";
		fm.SheetName[1].value = "方案明细信息";
		fm.SheetName[2].value = "Sheet1";//无主附配置Sheet页
		
		//方案信息
		var tPlanTitle = "方案序号^|方案描述^|保险期间^|保险期间单位^|保费计算方式^|人数^|工程造价^|工程面积^|工程类型^|施工天数^工程明细^|"
				+ "工程概述^|工程状况说明^|其他说明^|导入错误信息";

		// 方案明细信息
		var tPlanDetailTitle = "方案序号^|险种编码^|责任编码^|保额类型^|固定保额^|最低保额^|最高保额^|期望保费类型^|期望保费/费率/折扣^|备注^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//方案
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql13");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//方案明细
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql14");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//无主附共用配置，虚拟空数据
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="01" && tTranProdType == "03") {//项目询价	个人险种
		
		fm.SheetName[0].value = "方案信息";
		fm.SheetName[1].value = "方案明细信息";
		fm.SheetName[2].value = "Sheet1";//无主附配置Sheet页
		
		//方案信息
		var tPlanTitle = "方案序号^|方案描述^|保险期间^|保险期间单位^|方案类型^|方案标识^|职业类型^|最低职业类别^|最高职业类别^|职业比例^|"
				+ "职业类别^|职业中分类^|工种^|最低年龄^|最高年龄^|平均年龄^|人数^|参加社保占比^|"
				+ "男女比例^|退休占比^|保费分摊方式^|企业负担占比^|最低月薪^|最高月薪^|平均月薪^|其他说明^|导入错误信息";

		// 方案明细信息
		var tPlanDetailTitle = "方案序号^|险种编码^|责任编码^|保额类型^|固定保额^|月薪倍数^|最低保额^|最高保额^|期望保费类型^|期望保费/费率/折扣^|备注^|" 
				+"免赔方式^|免赔额^|赔付比例^|免赔天数^|每日津贴额^|等待期^|使用团体账户限额^|适用人群^|次限额^|团体人数^|服务区域^|是否次标准体^|"
				+ "缴费期间^|缴费单位^|住院赔付天数限制^|导入错误信息";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//方案
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql15");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//方案明细
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql16");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//无主附共用配置，虚拟空数据
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} 
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}


/**
 * 提交数据
 */
function submitForm(tForm) {
	
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
	tForm.submit();
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
		initForm();
	}
}
