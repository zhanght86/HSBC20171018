/***************************************************************
 * <p>ProName：LCContCommonInput.js</p>
 * <p>Title：契约阶段共用域</p>
 * <p>Description：契约阶段共用域</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
 
var tActivityType = "1002";
var tPlanShowRows = 10;
/**
 * 获取产品类型
 */
function getContPlanType(cGrpPropNo) {
 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql5");
	tSQLInfo.addSubPara(cGrpPropNo);
 
	var tProdArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tProdArr==null) {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
		tSQLInfo.setSqlId("LCContPlanTradSql18");
		tSQLInfo.addSubPara(cGrpPropNo);
 		tSQLInfo.addSubPara(cGrpPropNo);
 		tSQLInfo.addSubPara(cGrpPropNo);
		var tPArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tPArr==null) {
			
		}else{
			return tPArr[0][0];
		}
	} else {
		return tProdArr[0][0];
	}
	
	return "";
}
/**
 * 获取产品类型
 */
function getQuotType(cGrpPropNo) {
 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContCommonSql");
	tSQLInfo.setSqlId("LCContCommonSql6");
	tSQLInfo.addSubPara(cGrpPropNo);
 
	var tQuotArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tQuotArr==null) {
	
	} else {
		return tQuotArr[0][0];
	}
	return "";
}

/**
 * 自定义codename赋值
 */
function auotContShowCodeName(cCodeType, cCode, cObj, cCodeName) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql12");
	tSQLInfo.addSubPara(cCodeType);
	tSQLInfo.addSubPara(cCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {

	} else {
		
		document.all(cCodeName).value = tArr[0][0];
	}
}


/**
 * 校验数据整数位于小数位长度
 */
function checkDecimalFormat(cValue, cLen1, cLen2) {
	
	if (cValue=='' || cValue==null) {//为空,
		return true;
	}
	
	var tLen =  (""+cValue+"").length;
	var tLen1 = (""+cValue+"").indexOf(".");
	var tLen2 = 0;
	if (tLen1==-1) {
		tLen1 = tLen;
	} else {
		tLen2 = tLen - tLen1 - 1;
	}
	
	if (Number(tLen1)>Number(cLen1)) {
		return false;
	}
	
	if (Number(tLen2)>Number(cLen2)) {
		return false;
	}
	
	return true;
}

/**
 * 产品参数维护跳转页 o-目标步骤
 */
function goToPordParamStep(o, p) {
	
	var tPath = "?ObjType=CONT&BussType=NB&ContPlanType="+ tContPlanType +"&BussNo="+ tBussNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID+"&QueryFlag="+tQueryFlag;
	if (o=="0") {//管理费维护
		location.href = "./LCContParamInput.jsp" + tPath;
	} else if (o=="1") {//保全退费算法维护
		location.href = "./LCEdorRefundCalInput.jsp" + tPath;
	}
}

/**
展示查询结果,赋值到Muline
**/
function showMulLineInfo(tResultStr, objGrid ,objPage){
	objPage.strQueryResult = tResultStr;
	if(objPage.strQueryResult==null||objPage.strQueryResult=="") {
		
		//initExeTrendsGrid();
		alert("未查询到符合条件的查询结果！");
		return false;
	}
	
	objPage.decodeEasyQueryResult(objPage.strQueryResult,'0');
	objPage.useSimulation = 1;
	objPage.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult, 0, 0, objPage);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	
	objPage.pageDisplayGrid = objGrid;
	//objGrid.SortPage = objPage;地址页标识
	//设置查询起始位置
	objPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	var arrDataSet = turnPage1.getData(objPage.arrDataCacheSet, objPage.pageIndex, 10);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, objPage.pageDisplayGrid, objPage);
	objGrid.setPageMark(objPage);
	 
	return true;
	
}

function getDutyElementArr(cRiskCode, cDutyCode) {

	var tCalFactor;//原因子编码
	var tFactorCode;//从表字段
	var tFactorName;//因子名称
	var tFieldType;//字段类型
	var tValueType;//值类型
	var tDefaultValue;//默认值
	var tFieldLength;//字段长度
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql13");
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
		
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	return tArr;
}

function checkDutyElement(cObj, tArr, cFlag) {
	
	var tRelaFactor = "";
	var tRelaFactorName = "";
	
	if (cFlag=="1") {
	
		tRelaFactor = "Relation";
		tRelaFactorName = "附属方案";
	}
	
	if (tArr==null) {
		//未查询出数据,表示无动态因子
	} else {
		
		for (var i=0; i<tArr.length; i++) {
				
			//tCalFactor = tArr[i][0];
			tFactorCode = tRelaFactor + tArr[i][1];
			tFactorName = tRelaFactorName + tArr[i][2];
			tFieldType = tArr[i][3];
			tValueType = tArr[i][4];
			//tDefaultValue = tArr[i][5];
			tFieldLength = tArr[i][6];
			tValueScope = tArr[i][7];
			
			if (tFieldType=="1") {//下拉框,仅校验是否为空
			
				var tValue = document.all(tFactorCode).value;
				if (tValue==null || tValue=="") {
					alert(tFactorName+"不能为空！");
					return false;
				}
			} else if (tFieldType=="0") {//录入框,取出类型valutype,根据类型及字段长度进行判断
			
				var tValue = document.all(tFactorCode).value;
				if (tValue==null || tValue=="") {
					alert(tFactorName+"不能为空！");
					return false;
				}
				
				if (tValueType=="INT" || tValueType=="NUM") {

					var tSplitArr = tValueScope.split(",");
					
					var tReg1 = tValueScope.substr(0, 1);
					var tReg2 = tSplitArr[0].substr(1);
					var tReg3 = tSplitArr[1].substr(0,tSplitArr[1].length-1);
					var tReg4 = tValueScope.substr(tValueScope.length-1);
					
					if (tReg1=="(") {//开区间
						
						if (Number(tValue)<=Number(tReg2)) {
							alert(tFactorName+"应大于"+ tReg2 +"！");
							return false;
						}
					} else if (tReg1=="[") {
						
						if (Number(tValue)<Number(tReg2)) {
							alert(tFactorName+"应不小于"+ tReg2 +"！");
							return false;
						}
					} else {
						alert(tFactorName+"校验异常！");
						return false;
					}
					
					if (tReg4==")") {//开区间
						
						if (Number(tValue)>=Number(tReg3)) {
							alert(tFactorName+"应小于"+ tReg3 +"！");
							return false;
						}
					} else if (tReg4=="]") {
						
						if (Number(tValue)>Number(tReg3)) {
							alert(tFactorName+"应不大于"+ tReg3 +"！");
							return false;
						}
					} else {
						alert(tFactorName+"校验异常！");
						return false;
					}
				}
				
				if (tValueType=="INT") {
					
					if (!isInteger(tValue)) {
						alert(tFactorName+"应为整数！");
						return false;
					}
				} else if (tValueType=="NUM") {
					
					if (!isNumeric(tValue)) {
						alert(tFactorName+"应为有效数字！");
						return false;
					}
					
					//拆分规则
					var tRegArr = tFieldLength.split(",");
					if (!checkDecimalFormat(tValue, tRegArr[0], tRegArr[1])) {
						alert(tFactorName+"整数位不应超过"+ tRegArr[0] +"位,小数位不应超过"+ tRegArr[1] +"位！");
						return false;
					}
				}
			}
		}
	}
	
	return true;
}
function getDutyElement(tArr, cFlag) {
	
	var tRelaFactor = "";
	var tRelaFactorName = "";
	
	if (cFlag=="1") {
	
		tRelaFactor = "Relation";
		tRelaFactorName = "附属方案";
	}

	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>";
	if (tArr==null) {
		//未查询出数据,表示无动态因子
	} else {
		
		tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>";
		var tCount = 3;
		for (var i=0; i<tArr.length; i++) {
		
			if (tCount==3) {
				tInnerHTML0 += "<tr class=common>";
			}
			
			tCalFactor = tArr[i][0];
			tFactorCode = tArr[i][1];
			tFactorName = tArr[i][2];
			tFieldType = tArr[i][3];
			tValueType = tArr[i][4];
			tDefaultValue = tArr[i][5];
			tFieldLength = tArr[i][6];
			tMandatoryFlag = tArr[i][8];
			tDefalutName = tArr[i][9];
			
			if (tFieldType=="0") {//根据字段类型进行处理,0-录入框,1-下拉框,2-日期
				
				if (tValueType=="NUM") {
					
					//modify by dianj 20150320 处理公共保额新增字段
					if (tCalFactor =="PerPrem") {
						tInnerHTML0 += "<td class=title id=idPerPrem1 name=idPerPrem1 style=\"display: ''\">"+ tRelaFactorName + tFactorName +"</td><td class=input id=idPerPrem2 name=idPerPrem2 style=\"display: ''\"><input class=common name=PerPrem id=PerPrem verify=\""+ tRelaFactorName + tFactorName +"|num\"";
					} else if (tCalFactor =="StandPerPrem")  {
						tInnerHTML0 += "<td class=title id=idStandPerPrem1 name=idStandPerPrem1 style=\"display: ''\">"+ tRelaFactorName + tFactorName +"</td><td class=input id=idStandPerPrem2 name=idStandPerPrem2 style=\"display: ''\"><input class=common name=StandPerPrem ";
					} else {
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|num\"";
					}
					
				} else if (tValueType=="INT") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|int\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode ;
				}
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += " readonly ";
				}
				//modify by dianj 20150320 处理公共保额新增字段
				if (tCalFactor =="PerPrem" ) {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red;display: 'none'\" id=idStarPerPrem name=idStarPerPrem >*</span></td><td class=title id =hidden1 name=hidden1 style=\"display: 'none'\"></td><td class=input id =hidden2 name=hidden2 style=\"display: 'none'\"></td>";
				} else if (tCalFactor =="StandPerPrem") {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> </td><td class=title id =hidden3 name=hidden3 style=\"display: 'none'\"></td><td class=input id =hidden4 name=hidden4 style=\"display: 'none'\"></td>";
				} else {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
				}
				
			} else if (tFieldType=="1") {
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\" readonly> <span style=\"color: red\">*</span></td>";
				} else {
					//add by dianj 
					if (tCalFactor =="PremCalWay") {
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name=PremCalWay id=PremCalWay  ondblclick=\"return showCodeList('"+ tValueType +"',[this,PremCalWayName],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,PremCalWayName],[0,1],null,null,null,1)\" readonly><input class=codename name=PremCalWayName id=PremCalWayName  readonly> <span style=\"color: red\">*</span></td>";
					} else{
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" ondblclick=\"return showCodeList('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1)\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\"  readonly> <span style=\"color: red\">*</span></td>";
					}
				}
			} else if (tFieldType=="2") {
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" readonly verify=\"'"+ tRelaFactorName + tFactorName +"'|date\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=\"coolDatePicker\" dateFormat=\"short\" name="+ tRelaFactor + tFactorCode +" verify=\"'"+ tRelaFactorName + tFactorName +"'|date\"";
				}
				
				tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
			}
			
			tCount--;
			if (tCount==0) {
				tInnerHTML0 += "</tr>";
				tCount = 3;
			}
		}
		
		if (tCount!=0 && tCount!=3) {//补上空白的字段
			
			for (var i=1; i<=tCount; i++) {
				tInnerHTML0 += "<td class=title></td><td class=input></td>";
			}
			
			tInnerHTML0 += "</tr>";
		}
		
		tInnerHTML0 += "</table>";
	}
		
	return tInnerHTML0;
}

/**
 * 获取责任动态域需赋值字段SQL
 */
function getDutySQLElement(tArr) {

	var tSQLElement = "";

	if (tArr==null) {
		//未查询出数据,表示无动态因子
	} else {

		for (var i=0; i<tArr.length; i++) {

			var tFactorCode = tArr[i][1];
			if (i==(tArr.length-1)) {
				tSQLElement += tFactorCode;
			} else {
				tSQLElement += tFactorCode + ",";
			}
		}
	}
		
	return tSQLElement;
}


/**
 * 不分页方法
 */
function noDiv(objPage, objGrid, tSql) {
	
	//为兼容已允许的程序，容错而增加
	objPage = new turnPageClass();
	objPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1, 1, 1);
	//判断是否查询成功
	if (!objPage.strQueryResult) {
		//清空MULTILINE，使用方法见MULTILINE使用说明
		objGrid.clearData();
		return false;
	}
	
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	objPage.arrDataCacheSet = clearArrayElements(objPage.arrDataCacheSet);
	//清空缓存
	objPage.allArrDataCacheSet = clearArrayElements(objPage.allArrDataCacheSet);
	objPage.allCacheSize = 0;
	//查询成功则拆分字符串，返回二维数组
	objPage.arrDataCacheSet = decodeEasyQueryResult(objPage.strQueryResult, 0, 0, objPage);
	objPage.pageLineNum = objPage.queryAllRecordCount;
	var tKey = 1;
	//cTurnPage.allCacheSize ++;
	objPage.allArrDataCacheSet[objPage.allCacheSize%objPage.allArrCacheSize] = {id:tKey,value:objPage.arrDataCacheSet};
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	objPage.pageDisplayGrid = objGrid;
	//保存SQL语句
	objPage.strQuerySql = tSql;
	//设置查询起始位置
	objPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	var arrDataSet = objPage.getData(objPage.arrDataCacheSet, objPage.pageIndex, objPage.pageLineNum);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, objPage.pageDisplayGrid, objPage);
	if (objPage.showTurnPageDiv==1) {
		try {
			objGrid.setPageMark(objPage);
		} catch(ex){}
	}
	
	return true;
}

/**
 * 去掉空格
 */
function trimBlank(cObjFm, Cobj, CobjType) {
	
	if(CobjType==null || CobjType ==""){
		CobjType ="common";
	}
	if(Cobj!=null){
		if(CobjType=="common"){alert(CobjType);
			Cobj.value = trim(Cobj.value);
		}
		return true;
	}
	//遍历FORM中的所有ELEMENT
	for (elementsNum=0;elementsNum<cObjFm.elements.length;elementsNum++) {
		//元素校验属性verify不为NULL
		if (cObjFm.elements[elementsNum].className!=null && cObjFm.elements[elementsNum].className!="") {
			//进行校验verifyElement
			if (cObjFm.elements[elementsNum].className == "common") {
				 cObjFm.elements[elementsNum].value = trim(cObjFm.elements[elementsNum].value);
			}
		}
	}
	return true;
}

/**
 * 影像扫描件查询
 */
function queryScanPage() {
	
	//如果保单已经签单，则使用传入的保单号查询投保书号，否则直接使用投保书号查询影像
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContCommonSql");
	tSQLInfo.setSqlId("LCContCommonSql8");
	tSQLInfo.addSubPara(tGrpPropNo);
	
	var tPrtNoArr = easyExecSql(tSQLInfo.getString());
	if (tPrtNoArr!=null) {
		
		tGrpPropNo = tPrtNoArr[0][0];
	}
	
	window.open("../g_easyscan/ScanPagesQueryMainInput.jsp?BussType=G_NB&BussNo="+tGrpPropNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 问题件管理
 */
function goToQuestion() {
	
	window.open("../g_busicommon/LDQuestionMain.jsp?OtherNoType=NB&OtherNo="+tGrpPropNo+"&ActivityID="+tActivityID+"&ShowStyle=2","问题件管理",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 附件管理
 */
function showAttachment() {
	
	window.open("../g_busicommon/LDAttachmentMain.jsp?OtherNoType=NB&OtherNo="+tGrpPropNo+"&UploadNode="+tActivityID,"附件管理",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function checkManageCom(sManageCom){
		
	//必须在3级机构下进行
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContCommonSql");
	tSQLInfo.setSqlId("LCContCommonSql2");
	tSQLInfo.addSubPara(sManageCom);
	
	var tComGradeArr = easyExecSql(tSQLInfo.getString());
	if (tComGradeArr==null) {
		alert("查询机构等级信息失败！");
		return false;
	} else {
		var tComGrade = tComGradeArr[0][0];
		if (tComGrade!="03") {
			alert("承保机构只能为3级机构！");
			return false;
		}
	}
	return true;
}

/**
 * 初始方案明细翻页信息
 */
function initContPlanDetailPageInfo(cPolicyNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContCommonSql");
	tSQLInfo.setSqlId("LCContCommonSql3");
	tSQLInfo.addSubPara(tPolicyNo);
	
	strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(strQueryResult==null){
		return false;
	}
	RowNum = strQueryResult.length;
	
	//计算出总的页数TotalPage
	if (RowNum%tPlanShowRows==0) {
		PageNum = parseInt(RowNum/tPlanShowRows);
	} else {
		PageNum = parseInt(RowNum/tPlanShowRows)+1;
	}
	return true;
}


/**
 * 方案明细
 */
function pubContPlanDetail(cObj, cArr, tStartNum, cContPlanType) {
	
	var tWidthArr = new Array();
	tWidthArr[0] = 30;
	tWidthArr[1] = 0;
	tWidthArr[2] = 200;
	tWidthArr[3] = 0;
	tWidthArr[4] = 130;
	tWidthArr[5] = 300;
	tWidthArr[6] = 0;
	tWidthArr[7] = 80;
	tWidthArr[8] = 150;
	tWidthArr[9] = 80;
	tWidthArr[10] = 80;

	var tInnerHTML1 = "";
	for (var i=0; i<tPlanShowRows; i++) {
		if ((tStartNum+i-1)>=RowNum) {
			//相等时,表示总记录数取尽
		} else {
			
			var tPolicyNo = cArr[tStartNum+i-1][0];
			var tSysPlanCode = cArr[tStartNum+i-1][1];
			var tPlanCode = cArr[tStartNum+i-1][2];
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContCommonSql");
			tSQLInfo.setSqlId("LCContCommonSql4");
			tSQLInfo.addSubPara(tPolicyNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);
			
			var tPlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPlanArr!=null) {
				var j = 0;
				var tSysPlanCode = tPlanArr[0][j++];
				var tPlanCode = tPlanArr[0][j++];
				var tPlanDesc = tPlanArr[0][j++];
				var tPlanType =  tPlanArr[0][j++];
				var tPlanTypeName =  tPlanArr[0][j++];
				var tPremCalType =  tPlanArr[0][j++];
				var tPremCalTypeName =  tPlanArr[0][j++];
				var tPlanFlag = tPlanArr[0][j++];
				var tPlanFlagName = tPlanArr[0][j++];
				var tOccupTypeFlag =  tPlanArr[0][j++];
				var tOccupTypeFlagName =  tPlanArr[0][j++];
				var tMinOccupType =  tPlanArr[0][j++];
				var tMinOccupTypeName =  tPlanArr[0][j++];
				var tMaxOccupType =  tPlanArr[0][j++];
				var tMaxOccupTypeName =  tPlanArr[0][j++];
				var tOccupType =  tPlanArr[0][j++];
				var tOccupTypeName =  tPlanArr[0][j++];
				var tOccupMidType =  tPlanArr[0][j++];
				var tOccupMidTypeName =  tPlanArr[0][j++];
				var tOccupCode =  tPlanArr[0][j++];
				var tOccupCodeName =  tPlanArr[0][j++];
				var tNumPeople =  tPlanArr[0][j++];
				var tMaleRate =  tPlanArr[0][j++];
				var tFemaleRate =  tPlanArr[0][j++];
				var tMinAge =  tPlanArr[0][j++];
				var tMaxAge =  tPlanArr[0][j++];
				var tAvgAge =  tPlanArr[0][j++];
				var tMinSalary =  tPlanArr[0][j++];
				var tMaxSalary =  tPlanArr[0][j++];
				var tAvgSalary =  tPlanArr[0][j++];
				var tSocialInsuRate =  tPlanArr[0][j++];//参加社保占比
				var tRetireRate =  tPlanArr[0][j++];//退休占比
				var tOtherDesc = tPlanArr[0][j++];//其他说明
				var tPolciyNo = tPlanArr[0][j++];//保单号

				tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
					+"<tr class=common>"
					+"	<td class=title colSpan=6><b>方案：</b>"+tPlanCode+"("+tPlanDesc+")</td>"
					+"</tr>";
				if (cContPlanType=="00" || cContPlanType=="02" || cContPlanType=="03") {//普通险种,账户型及个人险种
				
					if (tPlanType=="00" || tPlanType=="02") {
					
					//职业处理
					if (tOccupTypeFlag=="1") {//单职业
						
						tInnerHTML1 +="<tr class=common>"
												+"	<td class=title colSpan=6><b>职业类别：</b>"+tOccupTypeName+"&nbsp;&nbsp;<b>职业中分类：</b>"+tOccupMidTypeName+"&nbsp;&nbsp;<b>工种：</b>"+tOccupCodeName+"&nbsp;&nbsp;</td>"
												+"</tr>";
					} else if (tOccupTypeFlag=="2") {//多职业
						
						tInnerHTML1 +="<tr class=common>"
												 +"	<td class=title colSpan=6><b>职业类别：</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName;
												 +"</tr>";
					}
					tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6><b>人数：</b>"+ tNumPeople +"&nbsp;&nbsp;<b>男女比例：</b>"+ tMaleRate +":"+ tFemaleRate +"&nbsp;&nbsp;<b>最低年龄：</b>"+ tMinAge +"&nbsp;&nbsp;<b>最高年龄：</b>"+ tMaxAge +"&nbsp;&nbsp;<b>平均年龄：</b>"+ tAvgAge +"&nbsp;&nbsp;</td>"
											+"</tr>"
											+"<tr class=common>"
											+"	<td class=title colSpan=6><b>最低月薪：</b>"+ tMinSalary +"&nbsp;&nbsp;<b>最高月薪：</b>"+ tMaxSalary +"&nbsp;&nbsp;<b>平均月薪：</b>"+ tAvgSalary +"&nbsp;&nbsp;<b>参加社保占比：</b>"+ tSocialInsuRate +"&nbsp;&nbsp;<b>退休占比：</b>"+ tRetireRate +"&nbsp;&nbsp;</td>"
											+"</tr>";
					}
				}
				
				tInnerHTML1 +="<tr class=common>"
								+"	<td class=title colSpan=6><b>其他说明：</b>"+tOtherDesc+"</td>"
								+"</tr>"
								+"</table>";
				
				//查询出方案明细信息
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_app.LCContCommonSql");
				tSQLInfo.setSqlId("LCContCommonSql5");
				tSQLInfo.addSubPara(tPolicyNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);
				
				var tPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				
				if (tPlanDetailArr==null) {
				
				} else {
					
					tInnerHTML1 +="<div>"	
											+"	<table class=common>"
											+"	 <tr class=common>"
											+"	 	<td text-align: left colSpan=1>"
											+"	 		<table class=muline ALIGN=left border=0 CELLSPACING=0 CELLPADDING=0>"
											+"	 			<tr>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='序号' style='width: "+ tWidthArr[0] +"' readonly></td> "
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='险种名称编码' style='width: "+ tWidthArr[1] +"' readonly></td>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='险种名称' style='width: "+ tWidthArr[2] +"' readonly></td>"
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='责任编码' style='width: "+ tWidthArr[3] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='责任名称' style='width: "+ tWidthArr[4] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='保险责任描述' style='width: "+ tWidthArr[5] +"' readonly></td> ";
											
					if (cContPlanType=="00" || cContPlanType=="01" || cContPlanType=="03") {//普通险种,建工险及个人险种
					
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费类型编码' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='期望保费类型' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='期望保费(元)/期望费率/费率折扣' style='width: "+ tWidthArr[8] +"' readonly></td>";
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='初始保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望收益率' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";
					} else if (cContPlanType=="02") {//账户型
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费类型编码' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费类型' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费(元)/期望费率/费率折扣' style='width: "+ tWidthArr[8] +"' readonly></td>";
			
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='初始保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='期望收益率' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";					}
					
					for (var k=0; k<tPlanDetailArr.length; k++) {
						
						var k1 = 0;
						var tRiskCode = tPlanDetailArr[k][k1++];
						var tRiskName = tPlanDetailArr[k][k1++];
						var tDutyCode = tPlanDetailArr[k][k1++];
						var tDutyName = tPlanDetailArr[k][k1++];
						var tAmntType = tPlanDetailArr[k][k1++];
						var tAmntTypeName = tPlanDetailArr[k][k1++];
						var tFixedAmnt = tPlanDetailArr[k][k1++];
						var tSalaryMult = tPlanDetailArr[k][k1++];
						var tMinAmnt = tPlanDetailArr[k][k1++];
						var tMaxAmnt = tPlanDetailArr[k][k1++];
						var tExceptPremType = tPlanDetailArr[k][k1++];
						var tExceptPremTypeName = tPlanDetailArr[k][k1++];
						var tExceptPrem = tPlanDetailArr[k][k1++];
						var tInitPrem = tPlanDetailArr[k][k1++];
						var tExceptYield = tPlanDetailArr[k][k1++];
						var tRelaShareFlag = tPlanDetailArr[k][k1++];
						var tDetailDesc = "";
						
						if (cContPlanType=="02") {//账户型处理
							
							if (tExceptYield==null || tExceptYield=="") {
								tDetailDesc =  "预期收益率:无;";
							} else {
								tDetailDesc += "预期收益率:"+ tExceptYield +";";
							}
							
						} else {
							
							tDetailDesc += "保额类型:"+ tAmntTypeName +";";
							if (tAmntType=="01") {
								tDetailDesc += "固定保额(元):"+ tFixedAmnt +";";
							} else if (tAmntType=="02") {
								tDetailDesc += "月薪倍数:"+ tSalaryMult +";";
							} else if (tAmntType=="03") {
								tDetailDesc += "月薪倍数:"+ tSalaryMult +";最低保额(元):"+ tMinAmnt +";";
							} else if (tAmntType=="04") {
								tDetailDesc += "最低保额(元):"+ tMinAmnt +";最高保额(元):"+ tMaxAmnt +";";
							}
						}
						
						//获取险种责任动态域数据
						var tDutyArr = getDutyElementArr(tRiskCode, tDutyCode);
						if (tDutyArr==null) {//无责任动态域
						
						} else {
							
							var tSQLElement = getDutySQLElement(tDutyArr); 
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_app.LCContCommonSql");
							tSQLInfo.setSqlId("LCContCommonSql7");
							tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
							tSQLInfo.addSubPara(tPolicyNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tDutyDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

							tDetailDesc += getDutyDetailSub(tDutyArr, tDutyDetailSub,"0");
							
						}
						//保险责任描述 添加 主附共用配置信息
						var tRelaSub = "";
						if (tRelaShareFlag=="1") {
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_app.LCContCommonSql");
							tSQLInfo.setSqlId("LCContCommonSql16");
							tSQLInfo.addSubPara(tPolicyNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tRelaSubArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							
							tRelaSub = "主被保险人保额占比:"+ tRelaSubArr[0][0]+";附属被保人保额占比:"+tRelaSubArr[0][1]+";";
							tDetailDesc += tRelaSub;
						}
						
						tInnerHTML1		+=" <tr>"
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' value='"+ (k+1) +"' readonly></td>"// value='序号' 
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"' name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='险种名称编码'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"' name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='险种名称'
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"' name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='责任编码'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"' name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> "// value='责任名称'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> "// value='保险责任描述'
						
						if (cContPlanType=="00" || cContPlanType=="01" || cContPlanType=="03") {//普通险种,建工险及个人险种
						
							tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='期望保费类型'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费/费率/折扣'
							
							tInnerHTML1 +=" <td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='初始保费'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='期望收益率'
										+"	</tr>";
							
						} else if (cContPlanType=="02") {//账户型
							
							tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='期望保费类型'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费/费率/折扣'
							
							tInnerHTML1 +=" <td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='初始保费'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='期望收益率'
										+"	</tr>";
						}							
					}
					tInnerHTML1 += "				</table>"
												+"			</td>"
												+"		</tr>"
												+"	</table>";
					 
					tInnerHTML1 += "</div>";
				}
			}
		}
	}
	return tInnerHTML1;
}
/**
* 校验日期格式
**/
function isDateFormat(txt){ //是否为合法的日期格式:YYYY-MM-DD
	if(txt==null || txt == ""){
		return false;
	} else{
		var regex = /[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}/;   //可按具体格式修改
		if( regex.test(txt)){
			var noArr = txt.split("-");
			var year = eval(noArr[0]);
			var month = eval(noArr[1]);
			var day = eval(noArr[2]);
			if( year < 1 || month < 1 || month > 12 || day < 1 || day > 31) {
				return false;
			}
			if((month == 4 || month == 6 || month == 9 || month == 11) && day > 30){
				return false;
			}
			if(month == 2){
				if((year % 4 != 0) && day > 29){
					return false;
				}
				if(year % 4 == 0){
					if(year % 100 == 0 && year % 400 != 0 && day > 29){
						return false;
					} else if (day > 28){
						return false;
					}
				}
			}
			return true;
		}else{
			return false;
		}
	}
}

/**
 * 获取从表明细描述
 */
function getDutyDetailSub(tDutyArr, tDutyResultArr,tFlag) {

	var tDetailSubDesc = "";
	for (var i=0; i<tDutyArr.length; i++) {
		
		var tFactorCode = tDutyArr[i][1];
		var tFactorName = tDutyArr[i][2];
		var tFieldType = tDutyArr[i][3];
		var tValueType = tDutyArr[i][4];
		if (tFlag=="0") {//普通动态因子
			
			//modify by dianj 20150323 处理【保险责任描述】中公共保额的 保费计算方式、人均保费 的展示
			if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//公共保额保费计算方式为按保额
			} else if (tFactorCode == "P18") {
			} else {
				tDetailSubDesc += tFactorName+":";
			}
			
		} else if (tFlag=="1") {//主附共用配置动态因子
			tDetailSubDesc += "附属方案" + tFactorName+":";
		}
		//tDetailSubDesc += tFactorName+":";
		if (tFieldType=="1") {
		
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContCommonSql");
			tSQLInfo.setSqlId("LCContCommonSql1");
			tSQLInfo.addSubPara(tValueType);
			tSQLInfo.addSubPara(tDutyResultArr[0][i]);
			
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
			tDetailSubDesc += tArr[0][0];
		} else {
			
			if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//公共保额保费计算方式为按保额
			} else if (tFactorCode == "P18") {
			} else {
				tDetailSubDesc += tDutyResultArr[0][i];
			}
		}
		
		if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//公共保额保费计算方式为按保额
		} else if (tFactorCode == "P18") {
		} else {
			tDetailSubDesc += ";";
		}
	}
	
	return tDetailSubDesc;
}

/**
 * 检查身份证首位是否是省市代码
 */
function checkProvCode(cProvCode) {
	
	if(cProvCode==null || cProvCode ==""){
		return false;
	}
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_app.LCContCommonSql");
//	tSQLInfo.setSqlId("LCContCommonSql17");
//	tSQLInfo.addSubPara(cProvCode);
//	
//	var strResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	if(strResult==null || strResult == ""){
//		return false;
//	} else if(cProvCode!= strResult){
//		return false;
//	} 
	return true;
}
	
