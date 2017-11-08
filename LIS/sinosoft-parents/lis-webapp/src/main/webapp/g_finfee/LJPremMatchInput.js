/***************************************************************
 * <p>ProName：LJMatchApplyInput.js</p>
 * <p>Title：保费匹配界面</p>
 * <p>Description：保费匹配界面</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-09
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var tOperate;

/**
 * 查询进账数据
 */
function queryFee() {
	
	initFinDataGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql9");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.FeeManageCom.value);
	tSQLInfo.addSubPara(fm.FeeCustAccName.value);
	tSQLInfo.addSubPara(fm.FeeStartDate.value);
	tSQLInfo.addSubPara(fm.FeeEndDate.value);
	tSQLInfo.addSubPara(tMatchSerialNo);
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinDataGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 查询溢缴数据
 */
function queryOutPay() {
	
	initOverDataGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql10");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.OutPayGrpName.value);
	tSQLInfo.addSubPara(fm.OutPayGrpContNo.value);
	tSQLInfo.addSubPara(fm.OutPayBussType.value);
	tSQLInfo.addSubPara(fm.OutPayManageCom.value);
	tSQLInfo.addSubPara(fm.OutPayPrtNo.value);
	tSQLInfo.addSubPara(fm.OutPayBussNo.value);
	tSQLInfo.addSubPara(tMatchSerialNo);
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), OverDataGrid, 0, 1);
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 查询业务数据
 */
function queryBuss() {

	initBusinessDataGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LJMatchSql11");
	}else if(_DBT==_DBM){
		tSQLInfo.setSqlId("LJMatchSql24");
	}
	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm1.DuePayManageCom.value);
	tSQLInfo.addSubPara(fm1.DuePayGrpName.value);
	tSQLInfo.addSubPara(fm1.DuePayGrpContNo.value);
	tSQLInfo.addSubPara(fm1.DuePayBussType.value);
	tSQLInfo.addSubPara(fm1.DuePayBussNo.value);
	tSQLInfo.addSubPara(fm1.DuePayBussNo.value);
	tSQLInfo.addSubPara(fm1.DuePayBussStartDate.value);
	tSQLInfo.addSubPara(fm1.DuePayBussEndDate.value);
	tSQLInfo.addSubPara(fm1.DuePayAgencyName.value);
	tSQLInfo.addSubPara(fm1.DuePayCoinsurance.value);
	
	turnPage3.queryModal(tSQLInfo.getString(), BusinessDataGrid, 0, 1);
	if (!turnPage3.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 选择进账数据
 */
function optionFee() {
	
	if (FinDataGrid.getChkCount()==0) {
		alert("请选择收费数据！");
		return false;
	}
	
	document.getElementById("OptionFeeButton").disabled = true;
	if (ChoosedDataGrid.mulLineCount==0) {
	
	} else {
		
		for (var i=0; i<FinDataGrid.mulLineCount; i++) {
			
			if (FinDataGrid.getChkNo(i)) {
				
				var tPremSource = FinDataGrid.getRowColData(i, 1);//保费来源编码
				var tSourceNo = FinDataGrid.getRowColData(i, 5);//暂收费号
				
				for (var j=0; j<ChoosedDataGrid.mulLineCount; j++) {
				
					var tPremSource1 = ChoosedDataGrid.getRowColData(j, 1);
					var tSourceNo1 = ChoosedDataGrid.getRowColData(j, 3);
					
					if (tPremSource==tPremSource1 && tSourceNo==tSourceNo1) {
						
						alert("财务收费数据第"+ (i+1) +"行已存在已选择数据中！");
						document.getElementById("OptionFeeButton").disabled = false;
						return false;
					}
				}
			}
		}
	}
	
	var tCount; //记录当前
	for (var i=0; i<FinDataGrid.mulLineCount; i++) {

		if (FinDataGrid.getChkNo(i)) {
			 
			tCount = ChoosedDataGrid.mulLineCount;   
			ChoosedDataGrid.addOne("ChoosedDataGrid");
			
			var tPremSource = FinDataGrid.getRowColData(i, 1);//保费来源编码
			var tPremSourceName = FinDataGrid.getRowColData(i, 2);//保费来源编码
			var tBussType = FinDataGrid.getRowColData(i, 3);//业务来源编码
			var tBussTypeName = FinDataGrid.getRowColData(i, 4);//业务来源
			var tTempFeeNo = FinDataGrid.getRowColData(i, 5);//暂收费号
			var tCustBankCode = FinDataGrid.getRowColData(i, 6);//客户开户行编码
			var tCustBankName = FinDataGrid.getRowColData(i, 7);//客户开户行
			var tCustBankAccNo = FinDataGrid.getRowColData(i, 8);//客户账号
			var tCustAccName = FinDataGrid.getRowColData(i, 9);//客户账户名
			var tAppntNo = FinDataGrid.getRowColData(i, 10);//投保单位编码
			var tGrpName = FinDataGrid.getRowColData(i, 11);//投保单位名称
			var tInBankCode = FinDataGrid.getRowColData(i, 12);//收款银行编码
			var tInBankName = FinDataGrid.getRowColData(i, 13);//收款银行
			var tInBankAccNo = FinDataGrid.getRowColData(i, 14);//收款账号
			var tEnterAccDate = FinDataGrid.getRowColData(i, 15);//到账日期
			var tEnterMoney = FinDataGrid.getRowColData(i, 16);//到账金额
			
			ChoosedDataGrid.checkBoxSel(tCount+1);
			
			ChoosedDataGrid.setRowColData(tCount, 1, tPremSource);//保费来源编码
			ChoosedDataGrid.setRowColData(tCount, 2, tPremSourceName);//保费来源
			ChoosedDataGrid.setRowColData(tCount, 3, tTempFeeNo);//来源号码
			//ChoosedDataGrid.setRowColData(tCount, 4, );//保单号(空)
			ChoosedDataGrid.setRowColData(tCount, 5, tBussType);//业务类型编码
			ChoosedDataGrid.setRowColData(tCount, 6, tBussTypeName);//业务类型
			ChoosedDataGrid.setRowColData(tCount, 7, tTempFeeNo);//业务号码
			ChoosedDataGrid.setRowColData(tCount, 8, tCustBankCode);//客户开户行编码
			ChoosedDataGrid.setRowColData(tCount, 9, tCustBankName);//客户开户行
			ChoosedDataGrid.setRowColData(tCount, 10, tCustBankAccNo);//客户账号
			ChoosedDataGrid.setRowColData(tCount, 11, tCustAccName);//客户账户名
			ChoosedDataGrid.setRowColData(tCount, 12, tAppntNo);//投保单位编码
			ChoosedDataGrid.setRowColData(tCount, 13, tGrpName);//投保单位名称
			ChoosedDataGrid.setRowColData(tCount, 14, tInBankCode);//收费银行编码
			ChoosedDataGrid.setRowColData(tCount, 15, tInBankName);//收费银行
			ChoosedDataGrid.setRowColData(tCount, 16, tInBankAccNo);//收费账号
			ChoosedDataGrid.setRowColData(tCount, 17, tEnterAccDate);//到账日期
			ChoosedDataGrid.setRowColData(tCount, 18, tEnterMoney);//到账金额
		}
	}
	document.getElementById("OptionFeeButton").disabled = false;
}

/**
 * 选择溢缴数据
 */
function optionOutPay() {
	
	if (OverDataGrid.getChkCount()==0) {
		alert("请选择收费数据！");
		return false;
	}
	
	document.getElementById("OptionOutPayButton").disabled = true;
	if (ChoosedDataGrid.mulLineCount==0) {
	
	} else {
		
		for (var i=0; i<OverDataGrid.mulLineCount; i++) {
			
			if (OverDataGrid.getChkNo(i)) {
				
				var tPremSource = OverDataGrid.getRowColData(i, 1);//保费来源编码
				var tSourceNo = OverDataGrid.getRowColData(i, 3);//暂收费号
				
				for (var j=0; j<ChoosedDataGrid.mulLineCount; j++) {
				
					var tPremSource1 = ChoosedDataGrid.getRowColData(j, 1);
					var tSourceNo1 = ChoosedDataGrid.getRowColData(j, 3);
					
					if (tPremSource==tPremSource1 && tSourceNo==tSourceNo1) {
						
						alert("保单溢缴数据第"+ (i+1) +"行已存在已选择数据中！");
						document.getElementById("OptionOutPayButton").disabled = false;
						return false;
					}
				}
			}
		}
	}
	
	var tCount; //记录当前
	for (var i=0; i<OverDataGrid.mulLineCount; i++) {

		if (OverDataGrid.getChkNo(i)) {
			 
			tCount = ChoosedDataGrid.mulLineCount;   
			ChoosedDataGrid.addOne("ChoosedDataGrid");
			
			var tPremSource = OverDataGrid.getRowColData(i, 1);//保费来源
			var tPremSourceName = OverDataGrid.getRowColData(i, 2);//保费来源编码
			var tOverNo = OverDataGrid.getRowColData(i, 3);//溢缴流水号
			var tGrpContNo = OverDataGrid.getRowColData(i, 4);//保单号
			var tAppntNo = OverDataGrid.getRowColData(i, 5);//投保人编码
			var tGrpName = OverDataGrid.getRowColData(i, 6);//投保人名称
			var tBussType = OverDataGrid.getRowColData(i, 7);//业务类型编码
			var tBussTypeName = OverDataGrid.getRowColData(i, 8);//业务类型
			var tBussNo = OverDataGrid.getRowColData(i, 9);//业务号
			var tOverMoney = OverDataGrid.getRowColData(i, 10);//溢缴金额
			var tMoney = OverDataGrid.getRowColData(i, 11);//本次使用金额
			
			ChoosedDataGrid.checkBoxSel(tCount+1);
			
			ChoosedDataGrid.setRowColData(tCount, 1, tPremSource);//保费来源编码
			ChoosedDataGrid.setRowColData(tCount, 2, tPremSourceName);//保费来源
			ChoosedDataGrid.setRowColData(tCount, 3, tOverNo);//来源号码
			ChoosedDataGrid.setRowColData(tCount, 4, tGrpContNo);//保单号(空)
			ChoosedDataGrid.setRowColData(tCount, 5, tBussType);//业务类型编码
			ChoosedDataGrid.setRowColData(tCount, 6, tBussTypeName);//业务类型
			ChoosedDataGrid.setRowColData(tCount, 7, tBussNo);//业务号码
			//ChoosedDataGrid.setRowColData(tCount, 8, );//客户开户行编码
			//ChoosedDataGrid.setRowColData(tCount, 9, );//客户开户行
			//ChoosedDataGrid.setRowColData(tCount, 10, );//客户账户名
			//ChoosedDataGrid.setRowColData(tCount, 11, );//客户账号
			ChoosedDataGrid.setRowColData(tCount, 12, tAppntNo);//投保单位编码
			ChoosedDataGrid.setRowColData(tCount, 13, tGrpName);//投保单位名称
			//ChoosedDataGrid.setRowColData(tCount, 14, );//收费银行编码
			//ChoosedDataGrid.setRowColData(tCount, 15, );//收费银行
			//ChoosedDataGrid.setRowColData(tCount, 16, );//收费账号
			//ChoosedDataGrid.setRowColData(tCount, 17, );//到账日期
			ChoosedDataGrid.setRowColData(tCount, 18, tMoney);//到账金额
		}
	}
	
	document.getElementById("OptionOutPayButton").disabled = false;
}

/**
 * 查询申请待匹配数据
 */
function queryMatchData() {
	
	initMatchingConfirmGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql12");
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage5.queryModal(tSQLInfo.getString(), MatchingConfirmGrid, 0, 1);
	
	if (turnPage5.strQueryResult) {
		
		queryMatchPayInfo();
		queryMatchFeeInfo();
		c1.style.display = "";
		c2.style.display = "";
	} else {
		
		c1.style.display = "none";
		c2.style.display = "none";
		initChoosedData1Grid();
		initBusinessData1Grid();
	}
}

/**
 * 保费匹配
 */
function premMatchClick() {
	
	document.getElementById("PremMatchButton").disabled = true;
	if (!beforePremMatchClick()) {
		document.getElementById("PremMatchButton").disabled = false;
		return false;
	}
	
	tOperate = "PREMMATCH";
	fmPub.Operate.value = tOperate;
	fm1.action = "./LJPremMatchSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm1);
}

/**
 * 匹配操作前
 */
function beforePremMatchClick() {
	
	if (MatchingConfirmGrid.mulLineCount!=0) {

		alert("已进行匹配不可再次进行该操作！");
		return false;
	}
	
	//页面部分仅进行最基本的金额格式校验
	if (ChoosedDataGrid.getChkCount()==0) {
		alert("已选择数据不能为空！");
		return false;
	}
	
	var tCount = 0; //记录当前
	var tCount1 = 0;
	for (var i=0; i<ChoosedDataGrid.mulLineCount; i++) {

		if (ChoosedDataGrid.getChkNo(i)) {
			
			var tMoney = ChoosedDataGrid.getRowColData(i, 18);
			var tCurOutMoney = ChoosedDataGrid.getRowColData(i, 19);
			
			if (tCurOutMoney==null || tCurOutMoney=="") {
				//溢缴金额为空,不进行校验
			} else {
				
				if (!isNumeric(tCurOutMoney)) {
					alert("已选择数据第"+(i+1)+"行溢缴金额不是有效数字！");
					return false;
				}
				
				if (Number(tCurOutMoney)>Number(tMoney)) {
					alert("保单溢缴数据第"+(i+1)+"行本次使用金额应大于0且不大于到账金额！");
					return false;
				}
				
				if (!checkDecimalFormat(tCurOutMoney, 10, 2)) {
					alert("保单溢缴数据第"+(i+1)+"行本次使用金额整数位应不大于10字长，小数应不大于2字长！");
					return false;
				}
				tCount1++;
			}
			
			tCount++;
		}
	}
	
	if (tCount==0) {
		alert("请选择已选择数据！");
		return false;
	}
	
	if (tCount1!=1 && tCount1!=0) {
		alert("已选择数据处的溢缴金额仅能有一笔！");
		return false;
	}
	
	tCount = 0;
	for (var i=0; i<BusinessDataGrid.mulLineCount; i++) {

		if (BusinessDataGrid.getChkNo(i)) {
			
			var tCurOutMoney = BusinessDataGrid.getRowColData(i, 10);
			
			if (tCurOutMoney==null || tCurOutMoney=="") {
				//溢缴金额为空,不进行校验
			} else {
				
				if (!isNumeric(tCurOutMoney)) {
					alert("业务应收数据第"+(i+1)+"行溢缴金额不是有效数字！");
					return false;
				}
				
				if (!checkDecimalFormat(tCurOutMoney, 10, 2)) {
					alert("业务应收数据第"+(i+1)+"行溢缴金额整数位应不大于10字长，小数应不大于2字长！");
					return false;
				}
			}
			
			tCount++;
		}
	}
	
	if (tCount==0) {
		alert("请选择业务应收数据！");
		return false;
	}
	
	return true;
}

/**
 * 撤销匹配
 */
function cancelClick() {
	
	if (MatchingConfirmGrid.mulLineCount==0) {
		
		alert("没有可进行撤销的数据！");
		return false;
	}
	
	tOperate = "CANCELCLICK";
	fmPub.Operate.value = tOperate;
	fm2.action = "./LJPremMatchSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm2);
}

/**
 *
 */
function matchConfirmClick() {
	
	var tInputConclusion = fm3.InputConclusion.value;
	if (tInputConclusion==null || tInputConclusion=="") {
		alert("匹配结论不能为空！");
		return false;
	}
	
	if (tInputConclusion=="00") {
		
		if (MatchingConfirmGrid.mulLineCount!=1) {
			alert("请先进行匹配！");
			return false;
		}
		
		var tInputDesc = fm3.InputDesc.value;
		if (tInputDesc.length>300) {
			alert("结论描述应在300字内！");
			return false;
		}
	} else {
		
		var tInputDesc = fm3.InputDesc.value;
		if (tInputDesc==null || tInputDesc=="") {
			alert("退回时，结论描述不能为空！");
			return false;
		}
		
		if (tInputDesc.length>300) {
			alert("结论描述应在300字内！");
			return false;
		}
	}
	
	tOperate = "MATCHCONFIRM";
	fmPub.Operate.value = tOperate;
	fm3.action = "./LJPremMatchSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm3);
}

/**
 * 查询所选匹配记录收费数据
 */
function queryMatchFeeInfo() {
	
	initChoosedData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql13");
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage6.queryModal(tSQLInfo.getString(), ChoosedData1Grid, 0, 1);
}

/**
 * 查询所选匹配记录应收数据
 */
function queryMatchPayInfo() {
	
	initBusinessData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LJMatchSql14");
	}else if(_DBT==_DBM){
		tSQLInfo.setSqlId("LJMatchSql25");
	}
	
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage7.queryModal(tSQLInfo.getString(), BusinessData1Grid, 0, 1);
}

/**
 * 查询该匹配上传的附件信息
 */
function queryAttachmentInfo() {

	initUploadFileGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql7");
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage8.queryModal(tSQLInfo.getString(), UploadFileGrid, 0, 1);
	
	if (turnPage8.strQueryResult) {
		divUploadInfo.style.display = "";
	} else {
		divUploadInfo.style.display = "none";
	}
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
   var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		dealFailSubmit();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		dealAfterSubmit();
	}	
}

function dealFailSubmit() {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="PREMMATCH") {
		
		document.getElementById("PremMatchButton").disabled = false;
	}
}

/**
 * 提交成功后处理
 */
function dealAfterSubmit() {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="PREMMATCH") {
		
		document.getElementById("PremMatchButton").disabled = false;
		initQueryInfo();
		initQueryInfo();
		queryMatchData();
	} else if (tOperate=="CANCELCLICK") {
		
		initMatchingConfirmGrid();
		c1.style.display = "none";
		c2.style.display = "none";
	} else if (tOperate=="MATCHCONFIRM") {
		
		location.href = "./LJPremMatchQueryInput.jsp";
	}
}

/**
 * 返回操作
 */
function returnMenu() {
	
	location.href = "./LJPremMatchQueryInput.jsp";
}

/**
 * 初始化查询域
 */
function initQueryInfo() {
	
	fm.FeeManageCom.value = "";
	fm.FeeManageComName.value = "";
	fm.FeeStartDate.value = "";
	fm.FeeCustAccName.value = "";
	fm.FeeEndDate.value = "";
	fm.OutPayGrpName.value = "";
	fm.OutPayGrpContNo.value = "";
	fm.OutPayBussType.value = "";
	fm.OutPayBussTypeName.value = "";
	fm.OutPayManageCom.value = "";
	fm.OutPayManageComName.value = "";
	fm.OutPayPrtNo.value = "";
	fm.OutPayBussNo.value = "";
	initFinDataGrid();
	initOverDataGrid();
	
	initChoosedDataGrid();
	fm1.DuePayGrpName.value = "";
	fm1.DuePayGrpContNo.value = "";
	fm1.DuePayBussType.value = "";
	fm1.DuePayBussTypeName.value = "";
	fm1.DuePayBussNo.value = "";
	fm1.DuePayBussStartDate.value = "";
	fm1.DuePayBussEndDate.value = "";
	fm1.DuePayAgencyName.value = "";
	fm1.DuePayCoinsurance.value = "";
	
	initBusinessDataGrid();
}

/**
 * 下载附件
 */
function downLoadClick(parm1) {
	var tFileName = document.all("UploadFileGrid3").value;
	var tFilePath = document.all("UploadFileGrid4").value;
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}


/**
 * 模拟下拉操作
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	if (value1=='finbusstype') {
		
		var tSql = "finbusstypeoutpay";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	} else if (value1=='finbusstype1') {
		
		var tSql = "finbusstypepay";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	}
}
