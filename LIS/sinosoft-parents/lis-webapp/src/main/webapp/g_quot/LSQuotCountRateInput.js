/***************************************************************
 * <p>ProName：LSQuotCountRateInput.js</p>
 * <p>Title：费率测算</p>
 * <p>Description：费率测算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-29
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		if (mOperate == "EXPRATE" || mOperate == "EXPCASHVALUE") {
			
			var tFileName = content.substring(content.lastIndexOf("/")+1);
			
			window.location = "../common/jsp/download.jsp?FilePath="+content+"&FileName="+tFileName;
			
		} else {
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		}
		initForm();
	}
	if (mOperate=="UPLOAD") {
		
		fm2.all('UploadPath').outerHTML = fm2.all('UploadPath').outerHTML;
	}
}

/**
* 下拉选择后展示界面要素
*/
function afterCodeSelect(o, p) {
	
	if(o=='quotprodrisk') {
		
		fm2.DutyCode.value = "";
		fm2.DutyName.value = "";
	}
	if(o=='cashvalueexptype') {
		
		if(p.value=='0') {
			
			fm2.all('expTD1').style.display = '';
			fm2.all('expTD2').style.display = '';
			fm2.all('expTD3').style.display = 'none';
			fm2.all('expTD4').style.display = 'none';
			fm2.all('expTD5').style.display = 'none';
			fm2.all('expTD6').style.display = 'none';
			fm2.all('expTD7').style.display = 'none';
			fm2.all('expTD8').style.display = 'none';
			fm2.all('expTD9').style.display = '';
			fm2.all('expTD10').style.display = '';
			
		} else if(p.value=='1') {
			
			fm2.all('expTD1').style.display = 'none';
			fm2.all('expTD2').style.display = 'none';
			fm2.all('expTD3').style.display = '';
			fm2.all('expTD4').style.display = '';
			fm2.all('expTD5').style.display = '';
			fm2.all('expTD6').style.display = '';
			fm2.all('expTD7').style.display = 'none';
			fm2.all('expTD8').style.display = 'none';
			fm2.all('expTD9').style.display = 'none';
			fm2.all('expTD10').style.display = 'none';
		} else {
			
			fm2.all('expTD1').style.display = 'none';
			fm2.all('expTD2').style.display = 'none';
			fm2.all('expTD3').style.display = 'none';
			fm2.all('expTD4').style.display = 'none';
			fm2.all('expTD5').style.display = 'none';
			fm2.all('expTD6').style.display = 'none';
			fm2.all('expTD7').style.display = '';
			fm2.all('expTD8').style.display = '';
			fm2.all('expTD9').style.display = '';
			fm2.all('expTD10').style.display = '';
		}
	}
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
	
	if (value1=='quotprodrisk') {
		
		var tSql = "1 and a.quotno = #"+ tQuotNo +"# and a.quotbatno = #"+ tQuotBatNo +"# and b.riskperiod=#L#";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}
	
	if (value1=='quotduty') {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("请先选择险种！");
			return false;
		}
		
		var tSql = "1 and a.riskcode=#"+ fm2.RiskCode.value +"#";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}
	
	if (value1=='payintv') {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("请先选择险种！");
			return false;
		}
		
		var tSql = "1 and b.riskcode=#"+ fm2.RiskCode.value +"#";
		
		if (returnType=='0') {
			return showCodeList('payintvbyrisk',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('payintvbyrisk',value2,value3,null,tSql,'1','1',null);
		}	
	}
}

/**
 * 保单信息保存
 */
function savePolInfo() {
	
	mOperate = "SAVEPOL";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
}

/**
 * 保单信息删除
 */
function delPolInfo() {
	
	mOperate = "DELETEPOL";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
}
/**
 * 增加
 */
function addSubmit() {
	
	mOperate = "INSERT";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
}

/**
* 修改
*/
function modSubmit() {
	
	mOperate = "UPDATE";
	
	var tRow = CustInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	
	if (!checkSubmit()) {
		return false;
	}
	
	var tSerialNo = CustInfoGrid.getRowColData(tRow-1,1);//流水号
	
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&SerialNo="+ tSerialNo;
	submitForm(fm2);
}

/**
 * 删除
 */
function delSubmit() {
	
	var tRow = CustInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	var tSerialNo = CustInfoGrid.getRowColData(tRow-1,1);//流水号
	
	mOperate = "DELETE";
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&SerialNo="+ tSerialNo;
	submitForm(fm2);
}

/**
 * 费率导出
 */
function expRate() {
	
	mOperate = "EXPRATE";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateExpSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo;
	submitForm(fm2);
}

/**
 * 导出现金价值
 */
function expCashValue() {
	
	mOperate = "EXPCASHVALUE";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateExpSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo;
	submitForm(fm2);
}

/**
* 提交前校验
*/
function checkSubmit() {
	
	//保单信息保存
	if (mOperate == "SAVEPOL") {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("险种不能为空！");
			return false;
		}
		
		if (isEmpty(fm2.DutyCode)) {
			alert("责任不能为空！");
			return false;
		}
		
		if (isEmpty(fm2.EffectivDate)) {
			alert("预计生效日期不能为空！");
			return false;
		} else {
			var tEffectivDate = fm2.EffectivDate.value;
			if (tCurrentDate > tEffectivDate) {
				alert("预计生效日期不能早于当前日期！");
				return false;
			}
		}
		
		if (isEmpty(fm2.CalDirection)) {
			alert("计算方向不能为空！");
			return false;
		}
		
		if (isEmpty(fm2.Amnt)) {
			alert("保额/保费不能为空！");
			return false;
		} else {
			if (!isNumeric(fm2.Amnt.value)) {
				alert("保额/保费不是有效数字！");
				return false;
			}
			if (Number(fm2.Amnt.value)<0) {
				alert("保额/保费应大于等于0！");
				return false;
			}
		}
		
		if (isEmpty(fm2.PayIntv)) {
			alert("缴费方式不能为空！");
			return false;
		}
		
		if (isEmpty(fm2.InsuPeriod)) {
			alert("缴费期间不能为空！");
			return false;
		} else {
			if (!isInteger(fm2.InsuPeriod.value) || Number(fm2.InsuPeriod.value)<0) {
				alert("缴费期间应为大于0的整数！");
				return false;
			}
		}
		
		if (isEmpty(fm2.InsuPeriodFlag)) {
			alert("缴费期间单位不能为空！");
			return false;
		}
		
		if (isEmpty(fm2.ReceiveCode)) {
			alert("领取方式不能为空！");
			return false;
		} else {
			if (!isInteger(fm2.ReceiveCode.value) || Number(fm2.ReceiveCode.value)<0) {
				alert("领取方式应为大于0的整数！");
				return false;
			}
		}
		
		if (isEmpty(fm2.ReceiveFlag)) {
			alert("缴费期间单位不能为空！");
			return false;
		}
		
		//校验是否录入保单信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tPolArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		var tSaveFlag = false;
		if (tPolArr != null) {
			if (fm2.RiskCode.value == tPolArr[0][0] && fm2.DutyCode.value == tPolArr[0][2]
				&& fm2.EffectivDate.value == tPolArr[0][4] && fm2.CalDirection.value == tPolArr[0][5] 
				&& fm2.Amnt.value == tPolArr[0][7] && fm2.PayIntv.value == tPolArr[0][9] 
				&& fm2.InsuPeriod.value == tPolArr[0][11] && fm2.InsuPeriodFlag.value == tPolArr[0][12]
				&& fm2.ReceiveCode.value == tPolArr[0][14] && fm2.ReceiveFlag.value == tPolArr[0][15]
			) {
				alert("保单信息未发生变动！");
				return false;
			} else {
				tSaveFlag = true;
			}
		}
		
		//校验是否录入被保人信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql4");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tCustCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tCustCount[0][0] != "0" && tSaveFlag) {
			if(!confirm('保单信息发生变动，将删除所有被保人信息，是否继续?')){
				return false;
			}
		}
	} 
	
	//保单信息删除
	if (mOperate == "DELETEPOL") {
		
		var tRiskCode = fm2.RiskCode.value;
		if (tRiskCode == "" || tRiskCode == null) {
			return false;
		} else {
			if(!confirm('将同时删除被保人信息，是否继续?')){
				return false;
			}
		}
	}
	
	//新增  修改
	if (mOperate == "INSERT" || mOperate == "UPDATE") {
		
		if (isEmpty(fm2.InsuredName)) {
			alert("姓名不能为空！");
			return false;
		}
		if (isEmpty(fm2.Gender)) {
			alert("性别不能为空！");
			return false;
		}
		if (isEmpty(fm2.InsuredAge) && isEmpty(fm2.Birthday) && isEmpty(fm2.IDNo)) {
			alert("年龄、出生日期、身份证号码至少录入一项！");
			return false;
		}
		if (!isEmpty(fm2.InsuredAge)) {
			
			if (!isInteger(fm2.InsuredAge.value)) {
				alert("年龄不是有效的整数！");
				return false;
			}
			if (Number(fm2.InsuredAge.value)<0) {
				alert("年龄应为不小于0的整数！");
				return false;
			}
		}
		
		//如录入了出生日期，反算年龄
		if (!isEmpty(fm2.Birthday)&&isEmpty(fm2.InsuredAge)) {
			
			var tInsuredAge = calAge(fm2.Birthday.value);
			if (tInsuredAge=="-1") {
				fm2.InsuredAge.value = "0";
			} else {
				fm2.InsuredAge.value = tInsuredAge;
			}
		}
		//如录入了 身份证号，反算年龄、出生日期
		if (!isEmpty(fm2.IDNo)&&isEmpty(fm2.InsuredAge)&&isEmpty(fm2.Birthday)) {
			
			fm2.Birthday.value = getBirthdatByIdNo(fm2.IDNo.value);
			
			var tInsuredAge = calAge(fm2.Birthday.value);
			if (tInsuredAge=="-1") {
				fm2.InsuredAge.value = "0";
			} else {
				fm2.InsuredAge.value = tInsuredAge;
			}
		}
		
		//如果录入身份证号，校验身份证号正确性
		if (!isEmpty(fm2.IDNo)) {
			
			if (!checkIdCard(fm2.IDNo.value)) {
				return false;
			}
		}
		
		if (!isEmpty(fm2.Gender) && !isEmpty(fm2.IDNo)) {
			
			var tGender = fm2.Gender.value;
			var tTempGender = getSex(fm2.IDNo.value);
			if (tGender != tTempGender ) {
				alert("性别与身份证号码不对应！");
				return false;
			}
		}
		
		if (!isEmpty(fm2.InsuredAge) && !isEmpty(fm2.Birthday)) {
			
			var tAge = fm2.InsuredAge.value;
			var tTempAge = calAge(fm2.Birthday.value);
			if (tAge != tTempAge ) {
				alert("年龄与出生日期不对应！");
				return false;
			}
		}
		
		if (!isEmpty(fm2.Birthday) && !isEmpty(fm2.IDNo)) {
			
			var tBirthday = fm2.Birthday.value;
			var tTempBirthday = getBirthdatByIdNo(fm2.IDNo.value);
			if (tBirthday != tTempBirthday ) {
				alert("出生日期与身份证号码不对应！");
				return false;
			}
		}
		
		if (!isEmpty(fm2.InsuredAge) && !isEmpty(fm2.IDNo)) {
			
			var tInsuredAge = fm2.InsuredAge.value;
			var tTempBirthday = getBirthdatByIdNo(fm2.IDNo.value);
			var tTempAge = calAge(tTempBirthday);
			if (tInsuredAge != tTempAge ) {
				alert("年龄与身份证号码不对应！");
				return false;
			}
		}
	}
	
	//费率导出
	if (mOperate == "EXPRATE") {
		
		if (tQuotNo=="" || tQuotNo == null) {
			alert("获取询价号失败！");
			return false;
		}
		if (tQuotBatNo=="" || tQuotBatNo == null) {
			alert("获取批次号失败！");
			return false;
		}
		
		//校验是否录入保单信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql3");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tPolCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tPolCount[0][0]=="0") {
			alert("请先维护保单信息！");
			return false;
		}
		
		//校验是否录入被保人信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql4");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tCustCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tCustCount[0][0]=="0") {
			alert("请先维护被保人信息！");
			return false;
		}
	}
	
	//导出现金价值
	if (mOperate == "EXPCASHVALUE") {
		
		if (isEmpty(fm2.ExpType)) {
			alert("导出方式不能为空！");
			return false;
		} else {
			if (fm2.ExpType.value == "0") {//导出方式：年龄
				
				if (isEmpty(fm2.Age)) {
					alert("年龄不能为空！");
					return false;
				} else {
					if (!isInteger(fm2.Age.value)) {
						alert("年龄不是有效的整数！");
						return false;
					}
					if (Number(fm2.Age.value)<0) {
						alert("年龄应为不小于0的整数！");
						return false;
					}
				}
			} else if (fm2.ExpType.value == "1") {//导出方式：年限区间
				if (isEmpty(fm2.YearStart)) {
					alert("年龄起不能为空！");
					return false;
				} else {
					if (!isInteger(fm2.YearStart.value)) {
						alert("年龄起不是有效的整数！");
						return false;
					}
					if (Number(fm2.YearStart.value)<=0) {
						alert("年龄起应为大于0的整数！");
						return false;
					}
				}
				if (isEmpty(fm2.YearEnd)) {
					alert("年限止不能为空！");
					return false;
				} else {
					if (!isInteger(fm2.YearEnd.value)) {
						alert("年限止不是有效的整数！");
						return false;
					}
					if (Number(fm2.YearEnd.value)<0) {
						alert("年限止应为不小于0的整数！");
						return false;
					}
					if (Number(fm2.YearEnd.value)>105) {
						alert("年限止应为小于105的整数！");
						return false;
					}
				}
				
				if (Number(fm2.YearStart.value) > Number(fm2.YearEnd.value)) {
					alert("年龄起不能大于年龄止！");
					return false;
				}
			}
		}
	}
	
	return true;
}

/**
 * 初始化查询保单信息
 */
function queryPolInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
	tSQLInfo.setSqlId("LSQuotCountRateSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tPolArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tPolArr != null) {
		
		fm2.RiskCode.value = tPolArr[0][0];
		fm2.RiskName.value = tPolArr[0][1];
		fm2.DutyCode.value = tPolArr[0][2];
		fm2.DutyName.value = tPolArr[0][3];
		fm2.EffectivDate.value = tPolArr[0][4];
		fm2.CalDirection.value = tPolArr[0][5];
		fm2.CalDirectionName.value = tPolArr[0][6];
		fm2.Amnt.value = tPolArr[0][7];
		fm2.PayIntv.value = tPolArr[0][9];
		fm2.PayIntvName.value = tPolArr[0][10];
		fm2.InsuPeriod.value = tPolArr[0][11];
		fm2.InsuPeriodFlag.value = tPolArr[0][12];
		fm2.InsuPeriodFlagName.value = tPolArr[0][13];
		fm2.ReceiveCode.value = tPolArr[0][14];
		fm2.ReceiveFlag.value = tPolArr[0][15];
		fm2.ReceiveName.value = tPolArr[0][16];
	}
}

/**
 * 初始化查询保单信息
 */
function queryCustInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
	tSQLInfo.setSqlId("LSQuotCountRateSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initCustInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), CustInfoGrid, 1, 1);//第三位表示使用大数据量
}

/**
 * 点击客户列表后，展示详细客户信息
 */
function showCustInfo() {
	
	var tSelNo = CustInfoGrid.getSelNo()-1;
	fm2.InsuredName.value = CustInfoGrid.getRowColData(tSelNo, 4);
	fm2.Gender.value = CustInfoGrid.getRowColData(tSelNo, 5);
	fm2.GenderName.value = CustInfoGrid.getRowColData(tSelNo, 6);
	fm2.InsuredAge.value = CustInfoGrid.getRowColData(tSelNo, 7);
	fm2.Birthday.value = CustInfoGrid.getRowColData(tSelNo, 8);
	fm2.IDNo.value = CustInfoGrid.getRowColData(tSelNo, 9);
	
}

/**
 * 被保人信息导入
 */
function impSubmit() {
	
	if (tQuotNo == null || tQuotNo == "") {
		alert("获取询价号失败！");
		return false;
	}
	if (tQuotBatNo == null || tQuotBatNo == "") {
		alert("获取批次号失败！");
		return false;
	}
	
	//校验是否维护保单信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
	tSQLInfo.setSqlId("LSQuotCountRateSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tCountArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCountArr[0][0]=="0") {
		alert("请先维护保单信息!");
		return false;
	}
	
	var tFilePath = fm2.UploadPath.value;
	if(tFilePath == null || tFilePath == ""){
		alert("请选择导入文件路径！");
		return false;
	}
	
	if (tFilePath!=null && tFilePath !="") {
		
		var tFileName = tFilePath.substring(tFilePath.lastIndexOf("\\")+1);
		var pattern2 = /^[^\s\+\&]+$/;
		if (!pattern2.test(tFileName)) {
			alert("上传的文件不能含有空格、‘+’，‘&’！");
			return false;
		}
		var tFileSuffix = tFilePath.substring(tFilePath.lastIndexOf("."));
		if (tFileSuffix==".xls" || tFileSuffix==".XLS" ) {
			
		} else {
			alert("不支持此文件类型上传！");
			return false;
		}
		
	}
	mOperate = "UPLOAD";
	fm2.encoding = "multipart/form-data";
	fm2.action="./LSQuotCustInfoImpSave.jsp?Operate=UPLOAD&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo;
	submitForm(fm2);
	
}

