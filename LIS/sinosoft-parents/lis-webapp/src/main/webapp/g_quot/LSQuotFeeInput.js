/***************************************************************
 * <p>ProName：LSQuotFeeInput.js</p>
 * <p>Title：费用信息</p>
 * <p>Description：费用信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 费用率信息查询
 */
function queryRiskFee() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
	tSQLInfo.setSqlId("LSQuotFeeSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), RiskFeeGrid, 0, 1);
}

/**
 * 加权费用率信息查询
 */
function queryWeightFee() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
	tSQLInfo.setSqlId("LSQuotFeeSql8");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		var tSumPrem = strQueryResult[0][0];//保费合计
		var tSum1 = strQueryResult[0][1];//手续费
		var tSum2 = strQueryResult[0][2];//业务费用率
		var tSum3 = strQueryResult[0][3];//理赔率
		var tSum4 = strQueryResult[0][4];//渠道公摊
		var tSum5 = strQueryResult[0][5];//税费
		var tSum6 = strQueryResult[0][6];//费用率
		var tSumNonMedPrem = strQueryResult[0][7];//健康险保费
		
		if (tSumPrem==0) {
			
			fm.WeightChargeRate.value = "";
			fm.WeightBusiFeeRate.value = "";
			fm.WeightLossRatio.value = "";
			fm.WeightPoolRate.value = "";
			fm.WeightTaxFeeRate.value = "";
			fm.WeightSumRate.value = "";
			fm.NonMedPremRate.value = "";
		} else {
			
			fm.WeightChargeRate.value = mathRound(tSum1/tSumPrem);
			fm.WeightBusiFeeRate.value = mathRound(tSum2/tSumPrem);
			fm.WeightLossRatio.value = mathRound(tSum3/tSumPrem);
			fm.WeightPoolRate.value = mathRound(tSum4/tSumPrem);
			fm.WeightTaxFeeRate.value = mathRound(tSum5/tSumPrem);
			fm.WeightSumRate.value = mathRound(tSum6/tSumPrem);
			fm.NonMedPremRate.value = mathRound(tSumNonMedPrem/tSumPrem);
		}
	}
}

/**
 * 询价中介机构手续费查询
 */
//function showRiskFeeDetail() {
//	
//	var tSelNo = RiskFeeGrid.getSelNo();
//	var tRiskCode = RiskFeeGrid.getRowColData(tSelNo-1, 1);
//	
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
//	tSQLInfo.setSqlId("LSQuotFeeSql5");
//	tSQLInfo.addSubPara(tQuotNo);
//	tSQLInfo.addSubPara(tQuotBatNo);
//	tSQLInfo.addSubPara(tRiskCode);
//	
//	initChargeFeeGrid();
//	noDiv(turnPage2, ChargeFeeGrid, tSQLInfo.getString());
//	if (ChargeFeeGrid.mulLineCount==0) {
//		divChargeFee.style.display = "none";
//	} else {
//		divChargeFee.style.display = "";
//	}
//}

/**
 * 其他费用信息查询
 */
function queryOtherFee() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
	tSQLInfo.setSqlId("LSQuotFeeSql7");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage3.queryModal(tSQLInfo.getString(), OtherFeeGrid, 0, 1);
}

/**
 * 保存
 */
function saveClick() {
	
	var tSelNo = RiskFeeGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
		
	var tExpectChargeRate = RiskFeeGrid.getRowColData(tSelNo,6);
	var tExpectBusiFeeRate = RiskFeeGrid.getRowColData(tSelNo,8);
	var tPreLossRatio = RiskFeeGrid.getRowColData(tSelNo,9);
	var tPoolRate = RiskFeeGrid.getRowColData(tSelNo,10);
	var tTaxFeeRate = RiskFeeGrid.getRowColData(tSelNo,11);
	var tManageFeeRate = RiskFeeGrid.getRowColData(tSelNo,12);
	
	if (tActivityID=="0800100001" || tActivityID=="0800100002" || tActivityID=="0800100003") {//录入环节校验手续费
		
		if (tAgencyFlag==1) {//进行校验,数字0-1之间
			
			if (tExpectChargeRate==null || tExpectChargeRate=="") {
				alert("第"+(tSelNo+1)+"行期望手续费比例不能为空！");
				return false;
			}
			
			if (!isNumeric(tExpectChargeRate)) {
				alert("第"+(tSelNo+1)+"行期望手续费比例不是有效的数字！");
				return false;
			}
			
			if (Number(tExpectChargeRate)<0 || Number(tExpectChargeRate)>1) {
				alert("第"+(tSelNo+1)+"行期望手续费比例应为不小于0且不大于1的有效数字！");
				return false;
			}

			if (!checkDecimalFormat(tExpectChargeRate, 1, 6)) {
				alert("第"+(tSelNo+1)+"期望手续费比例整数位不应超过1位,小数位不应超过6位！");
				return false;
			}
			
//			var tSumAgencyRate = 0;
//			if (ChargeFeeGrid.mulLineCount==0) {
//				
//			} else {
//				
//				for(var i=0;i<ChargeFeeGrid.mulLineCount;i++){
//					
//					var tAgencyRate = ChargeFeeGrid.getRowColData(i,2);
//					if (tAgencyRate==null || tAgencyRate=="") {
//						alert("第"+(i+1)+"行中介期望手续费比例不能为空！");
//						return false;
//					}
//					
//					if (!isNumeric(tAgencyRate)) {
//						alert("第"+(i+1)+"行中介期望手续费比例不是有效的数字！");
//						return false;
//					}
//					
//					if (Number(tAgencyRate)<0 || Number(tAgencyRate)>1) {
//						alert("第"+(i+1)+"行中介期望手续费比例应为不小于0且不大于1的有效数字！");
//						return false;
//					}
//					
//					if (!checkDecimalFormat(tAgencyRate, 1, 2)) {
//						alert("第"+(i+1)+"中介期望手续费比例整数位不应超过1位,小数位不应超过2位！");
//						return false;
//					}
//					
//					tSumAgencyRate += tAgencyRate*1000000;
//				}
//			}
//			
//			if (ChargeFeeGrid.mulLineCount==0) {
//			} else {
//				
//				if (tExpectChargeRate*1000000!=tSumAgencyRate) {
//					alert("所选险种期望手续费率必须等于各中介期望手续费比例之和！");
//					return false;
//				}
//			}
		}
	}

	if (tActivityID=="0800100002" || tActivityID=="0800100003") {
		
		if (tExpectBusiFeeRate==null || tExpectBusiFeeRate=="") {
			alert("第"+(tSelNo+1)+"行期望业务费用率不能为空！");
			return false;
		}
		
		if (!isNumeric(tExpectBusiFeeRate)) {
			alert("第"+(tSelNo+1)+"行期望业务费用率不是有效的数字！");
			return false;
		}

		if (tPreLossRatio==null || tPreLossRatio=="") {
			alert("第"+(tSelNo+1)+"行预期理赔率不能为空！");
			return false;
		}
		
		if (!isNumeric(tPreLossRatio)) {
			alert("第"+(tSelNo+1)+"行预期理赔率不是有效的数字！");
			return false;
		}
	}

	mOperate = "SAVE";
	fm.action = "./LSQuotFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * 新增
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	fm.action = "./LSQuotOtherFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * 修改
 */
function modifyClick() {
	
	var tSelNo = OtherFeeGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm.SerialNo.value = OtherFeeGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}
	fm.action = "./LSQuotOtherFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tSelNo = OtherFeeGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	fm.SerialNo.value = OtherFeeGrid.getRowColData(tSelNo-1, 1);
	mOperate = "DELETE";
	fm.action = "./LSQuotOtherFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * 展示其他费用明细
 */
function showOtherFeeDetail() {
	
	var tSelNo = OtherFeeGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm.FeeType.value = OtherFeeGrid.getRowColData(tSelNo-1, 2);
	fm.FeeTypeName.value = OtherFeeGrid.getRowColData(tSelNo-1, 3);
	fm.OtherFeeDesc.value = OtherFeeGrid.getRowColData(tSelNo-1, 4);
	fm.FeeValue.value = OtherFeeGrid.getRowColData(tSelNo-1, 5);
	fm.Remark.value = OtherFeeGrid.getRowColData(tSelNo-1, 6);
	
	if (fm.FeeType.value == "09") {
		
		divOtherFeeDescTitle.style.display = "";
		divOtherFeeDescInput.style.display = "";
		divTD1.style.display = "none";
		divTD2.style.display = "none";
	} else {
		
		divOtherFeeDescTitle.style.display = "none";
		divOtherFeeDescInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
	}
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
	fm.Operate.value = mOperate;
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
		
		initForm();
		if (mOperate == "INSERT" || mOperate == "UPDATE" || mOperate == "DELETE") {
			fm.FeeType.value = "";
			fm.FeeTypeName.value = "";
			fm.OtherFeeDesc.value = "";
			fm.FeeValue.value = "";
			fm.Remark.value = "";
		}
	}
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="INSERT" || mOperate=="UPDATE") {
		
		if (fm.FeeType.value=="09") {
			
			if (fm.OtherFeeDesc.value=="") {
				
				alert("其他费用名称不能为空！");
				return false;
			}
		}
		if (fm.FeeValue.value=="") {
			
			alert("费用值不能为空！");
			return false;
		}
		if (fm.Remark.value.length>500) {
			
			alert("业务需求内容过长！");
			return false;
		}
	}
	
	return true;
}

/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tSelectValue, tObj) {

	if (tSelectValue=='otherfeetype') {
		
		if (tObj.value == "09") {
			
			divOtherFeeDescTitle.style.display = "";
			divOtherFeeDescInput.style.display = "";
			fm.OtherFeeDesc.value = "";
			fm.FeeValue.value = "";
			divTD1.style.display = "none";
			divTD2.style.display = "none";
		} else {
			
			divOtherFeeDescTitle.style.display = "none";
			divOtherFeeDescInput.style.display = "none";
			fm.OtherFeeDesc.value = "";
			fm.FeeValue.value = "";
			divTD1.style.display = "";
			divTD2.style.display = "";
		}
	}
}
