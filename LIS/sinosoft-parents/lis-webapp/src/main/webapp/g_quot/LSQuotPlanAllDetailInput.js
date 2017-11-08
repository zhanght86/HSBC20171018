/***************************************************************
 * <p>ProName：LSQuotPlanAllDetailInput.js</p>
 * <p>Title：方案明细一览</p>
 * <p>Description：方案明细一览</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 保存方案费率信息
 */
function saveAllDetail(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cDetailRows) {

	//提交前校验
	if (!beforeSubmit(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cDetailRows)) {
		return false;
	}
	fm.encoding = "application/x-www-form-urlencoded";
	fm.action = "./LSQuotPlanAllDetailSave.jsp?Operate=ADDPLANDETAIL&QuotNo="+ cQuotNo +"&QuotBatNo="+ cQuotBatNo +"&QuotType="+ tQuotType +"&SysPlanCode="+ cSysPlanCode +"&PlanCode="+ cPlanCode +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType +"&DetailRows="+ cDetailRows;
	submitForm(fm);
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
		if (mOperate == "EXPTEMPLATE" || mOperate == "EXPDETAIL") {
			
			var tFileName = content.substring(content.lastIndexOf("/")+1);
			
			window.location = "../common/jsp/download.jsp?FilePath="+content+"&FileName="+tFileName;
			
		} else {
			
			if (mOperate=="UPLOAD") {
				
				document.all('UploadPath').outerHTML = document.all('UploadPath').outerHTML;
			}
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
		
		goToPage(OnPage);
	}	
}

function showAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID) {
	
	cObj.PageInfo.value = "第"+OnPage+"/"+PageNum+"页";
	document.all("divShowAllPlan").innerHTML = pubShowAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID);
}

/**
 * 展示指定页
 */
function goToPage(OnPage) {
	
	tStartNum = (OnPage-1)*tPlanShowRows+1;
	showAllPlanDetail(fm, strQueryResult,tStartNum, tQuotType, tTranProdType, tActivityID);
}

/**
 *
 */
function beforeSubmit(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cDetailRows) {
	
	/**
	 * 获取记录数与数据库当前记录数进行比对
	 * 获取所有值后根据工作流节点及期望值类型进行判断值是否正确
	 */
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
	tSQLInfo.setSqlId("LSQuotAllDetailSql8");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	
	var tPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPlanDetailArr==null) {
		alert("获取原方案明细失败！");
		return false;
	}
	
	if (tPlanDetailArr.length!=cDetailRows) {//比较数量是否一致
		alert("该方案明细信息发生改变，请刷新页面后再进行该操作！");
		return false;
	}
	
	for (var i=0; i<tPlanDetailArr.length; i++) {
		
		var tRiskCode = tPlanDetailArr[i][0];
		var tRiskName = tPlanDetailArr[i][1];
		var tDutyCode = tPlanDetailArr[i][2];
		var tDutyName = tPlanDetailArr[i][3];
		var tExceptPremType = tPlanDetailArr[i][4];
		var tExceptPremTypeName = tPlanDetailArr[i][5];
		var tStandValue = tPlanDetailArr[i][6];
		var tExceptValue = tPlanDetailArr[i][7];
		var tSubUWValue = tPlanDetailArr[i][8];
		var tBranchUWValue = tPlanDetailArr[i][9];
		var tUWValue = tPlanDetailArr[i][10];
		
		var tDutyCode1 = document.all("DutyCode"+ tRiskCode + tDutyCode + cSysPlanCode).value;
		if (tDutyCode1==null || tDutyCode1=="") {
			alert("该方案明细信息发生改变，请刷新页面后再进行该操作！");
			return false;
		}
		
		var tExceptPremType = document.all("ExceptPremType"+ tRiskCode + tDutyCode + cSysPlanCode).value;
		if (tExceptPremType==null || tExceptPremType=="") {
			alert("该方案明细信息发生改变，请刷新页面后再进行该操作！");
			return false;
		}
		var tValue = "";
		if (tActivityID=="0800100002") {//中核修改
			
			tValue = 	document.all("SubUWValue"+ tRiskCode + tDutyCode + cSysPlanCode).value;
			if (tValue==null || tValue=="") {
				
				alert("第"+ (i+1) +"行核保保费(中)不能为空！");
				return false;
			}
			
			if (!isNumeric(tValue) || Number(tValue)<0) {
				
				alert("第"+ (i+1) +"行核保保费(中)应为不小于0的有效数字！");
				return false;
			}
			
			//根据类型对值进行校验
			if (tExceptPremType=="01") {//保费,12,2
			
				if (!checkDecimalFormat(tValue, 12, 2)) {
					
					alert("当对期望保费进行询价时，核保保费(中)整数位不应超过12位,小数位不应超过2位,请修改第"+ (i+1) +"行核保保费(中)！");
					return false;
				}
			} else if (tExceptPremType=="02") {//费率
				
				if (!checkDecimalFormat(tValue, 4, 8)) {
					
					alert("当对期费率进行询价时，核保保费(中)整数位不应超过4位,小数位不应超过8位,请修改第"+ (i+1) +"行核保保费(中)！");
					return false;
				}
			} else if (tExceptPremType=="03") {//折扣
				
				if (!checkDecimalFormat(tValue, 2, 2)) {
					
					alert("当对费率折扣进行询价时，核保保费(中)整数位不应超过2位,小数位不应超过2位,请修改第"+ (i+1) +"行核保保费(中)！");
					return false;
				}
			}
		} else if (tActivityID=="0800100003") {//分核修改
		
			tValue = 	document.all("BranchUWValue"+ tRiskCode + tDutyCode + cSysPlanCode).value;
			if (tValue==null || tValue=="") {
				
				alert("第"+ (i+1) +"行核保保费(分)不能为空！");
				return false;
			}
			
			if (!isNumeric(tValue) || Number(tValue)<0) {
				
				alert("第"+ (i+1) +"行核保保费(分)应为不小于0的有效数字！");
				return false;
			}
			
			//根据类型对值进行校验
			if (tExceptPremType=="01") {//保费,12,2
			
				if (!checkDecimalFormat(tValue, 12, 2)) {
					
					alert("当对期望保费进行询价时，核保保费(分)整数位不应超过12位,小数位不应超过2位,请修改第"+ (i+1) +"行核保保费(分)！");
					return false;
				}
			} else if (tExceptPremType=="02") {//费率
				
				if (!checkDecimalFormat(tValue, 4, 8)) {
					
					alert("当对期望费率进行询价时，核保保费(分)整数位不应超过4位,小数位不应超过8位,请修改第"+ (i+1) +"行核保保费(分)！");
					return false;
				}
			} else if (tExceptPremType=="03") {//折扣
				
				if (!checkDecimalFormat(tValue, 2, 2)) {
					
					alert("当对费率折扣进行询价时，核保保费(分)整数位不应超过2位,小数位不应超过2位,请修改第"+ (i+1) +"行核保保费(分)！");
					return false;
				}
			}
		} else if (tActivityID=="0800100004") {//总核修改
			
			tValue = 	document.all("UWValue"+ tRiskCode + tDutyCode + cSysPlanCode).value;
			if (tValue==null || tValue=="") {
				
				alert("第"+ (i+1) +"行建议保费不能为空！");
				return false;
			}
			
			if (!isNumeric(tValue) || Number(tValue)<0) {
				
				alert("第"+ (i+1) +"行建议保费应为不小于0的有效数字！");
				return false;
			}
			
			//根据类型对值进行校验
			if (tExceptPremType=="01") {//保费,12,2
			
				if (!checkDecimalFormat(tValue, 12, 2)) {
					
					alert("当对期望保费进行询价时，建议保费整数位不应超过12位,小数位不应超过2位,请修改第"+ (i+1) +"行建议保费！");
					return false;
				}
			} else if (tExceptPremType=="02") {//费率
				
				if (!checkDecimalFormat(tValue, 4, 8)) {
					
					alert("当对期望费率进行询价时，建议保费整数位不应超过4位,小数位不应超过8位,请修改第"+ (i+1) +"行建议保费！");
					return false;
				}
			} else if (tExceptPremType=="03") {//折扣
				
				if (!checkDecimalFormat(tValue, 2, 2)) {
					
					alert("当对费率折扣进行询价时，建议保费整数位不应超过2位,小数位不应超过2位,请修改第"+ (i+1) +"行建议保费！");
					return false;
				}
			}
		}
	}
	
	return true;
}

/**
 * 展示批量操作信息
 */
function showImp() {
	
	if(divImpDetail.style.display=='none') {
		divImpDetail.style.display=''
	} else {
		divImpDetail.style.display='none'
	}
}

/**
 * 导出方案明细
 */
function expPlanDetail() {
	
	mOperate = "EXPDETAIL";
	fm.encoding = "application/x-www-form-urlencoded";
	fm.action = "./LSQuotExpDetailSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&ProdType="+ tTranProdType+"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * 导出模版
 */
function expImpTemplate() {
	
	mOperate = "EXPTEMPLATE";
	fm.encoding = "application/x-www-form-urlencoded";
	fm.action = "./LSQuotExpPremSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * 保费导入
 */
function impPrem() {
	
	if (tQuotNo == null || tQuotNo == "") {
		alert("获取询价号失败！");
		return false;
	}
	if (tQuotBatNo == null || tQuotBatNo == "") {
		alert("获取批次号失败！");
		return false;
	}
	if (tQuotType == null || tQuotType == "") {
		alert("获取询价类型失败！");
		return false;
	}
	if (tActivityID == null || tActivityID == "") {
		alert("获取工作流节点失败！");
		return false;
	}
	var tFilePath = fm.UploadPath.value;
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
	fm.encoding = "multipart/form-data";
	fm.action="./LQuotImpPremSave.jsp?Operate=UPLOAD&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&TranProdType="+tTranProdType+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm);
}

/**
 * 展示方案导入信息
 */
function showImpLog() {
	
	window.open("./LSQuotShowImpPremMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo+ "&ActivityID="+ tActivityID,"询价保费导入信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
