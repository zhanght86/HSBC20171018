/***************************************************************
 * <p>ProName：EdorCommonInput.js</p>
 * <p>Title：保全公共方法</p>
 * <p>Description：保全公共方法</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
 
var tPlanShowRows = 10;
/**
 * 影像件查询
 */
function queryScanPage() {
	window.open("../g_easyscan/ScanPagesQueryMainInput.jsp?BussType=G_POS&BussNo="+tEdorAppNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 问题件管理
 */
function goToQuestion() {
	
	window.open("../g_busicommon/LDQuestionMain.jsp?OtherNoType=POS&OtherNo="+tEdorAppNo+"&ActivityID="+tActivityID+"&ShowStyle=2","问题件管理",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 附件管理
 */
function showAttachment() {
	
	window.open("../g_busicommon/LDAttachmentMain.jsp?OtherNoType=POS&OtherNo="+tEdorAppNo+"&UploadNode="+tActivityID,"附件管理",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 人员清单处理
 */
function edorInsuredList() {
	
	window.open("./EdorInsuredDealMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorAppNo="+tEdorAppNo,"人员清单处理",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 特约信息
 */
function queryGrpSpec() {
	
	var tPolicyNo = fm.PolicyNo.value;
	window.open("./GrpSpecMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&GrpContNo="+tPolicyNo,"特约信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 保险方案查询
 */
function gradeQuery() {
	window.open("../g_app/LCContPlanQueryMain.jsp?PolicyNo="+tPolicyNo ,"方案查询",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * 返回
 */
function returnClick() {
	
	if (tActivityID=="1800401001") {
		location.href = "./EdorAcceptQueryInput.jsp";
	} else if (tActivityID=="1800401002") {
		location.href = "./EdorInputQueryInput.jsp";
	} else if (tActivityID=="1800401003") {
		location.href = "./EdorCheckQueryInput.jsp";
	} else if (tActivityID=="1800401004") {
		
		location.href = "./EdorUWQueryInput.jsp";
	} else if (tActivityID=="1800401005") {
		
		location.href = "./EdorAuditQueryInput.jsp";
	}
}

/**
 * 自定义codename赋值
 */
function auotContShowCodeName(cCodeType, cCode, cObj, cCodeName) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCommonSql");
	tSQLInfo.setSqlId("EdorCommonSql3");
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
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|num\"";
				} else if (tValueType=="INT") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|int\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode ;
				}
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += " readonly ";
				}
				
				tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
			} else if (tFieldType=="1") {
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\" readonly> <span style=\"color: red\">*</span></td>";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" ondblclick=\"return showCodeList('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1)\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\"  readonly> <span style=\"color: red\">*</span></td>";
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
 * 获取产品类型
 */
function getContPlanType(cGrpPropNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCommonSql");
	tSQLInfo.setSqlId("EdorCommonSql2");
	tSQLInfo.addSubPara(cGrpPropNo);
	var tProdArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tProdArr==null) {
		return "";
	} else {
		return tProdArr[0][0];
	}
}
/**
 * 初始方案明细翻页信息
 */
function initContPlanDetailPageInfo(cPolicyNo,cEdorNo,cEdorType) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCommonSql");
	tSQLInfo.setSqlId("EdorCommonSql4");
	tSQLInfo.addSubPara(cPolicyNo);
	tSQLInfo.addSubPara(cEdorNo);
	tSQLInfo.addSubPara(cEdorType);
	
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
	tWidthArr[2] = 150;
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
			var tEdorNo = cArr[tStartNum+i-1][3];
			var tEdorType = cArr[tStartNum+i-1][4];
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorCommonSql");
			tSQLInfo.setSqlId("EdorCommonSql5");
			tSQLInfo.addSubPara(tPolicyNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);
			tSQLInfo.addSubPara(tEdorNo);
			tSQLInfo.addSubPara(tEdorType);

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
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql6");
				tSQLInfo.addSubPara(tPolicyNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);
				
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
									+"	</tr>";}
					
					for (var k=0; k<tPlanDetailArr.length; k++) {
						
						var k1 = 0;
						var tRiskCode = tPlanDetailArr[k][k1++];
						var tRiskName = tPlanDetailArr[k][k1++];
						var tDutyCode = tPlanDetailArr[k][k1++];
						var tDutyName = tPlanDetailArr[k][k1++];
						var tDetailDesc = tPlanDetailArr[k][k1++];
						var tExceptPremType = tPlanDetailArr[k][k1++];
						var tExceptPremTypeName = tPlanDetailArr[k][k1++];
						var tExceptPrem = tPlanDetailArr[k][k1++];
						var tInitPrem = tPlanDetailArr[k][k1++];
						var tExceptYield = tPlanDetailArr[k][k1++];
						
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
** 文件下载
**/
function downFile(patch,fileName1){
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
}

/**
 * 打印
 */
function print() {
	
	if (tEdorAppNo==null || tEdorAppNo=="") {
		alert("未传入受理号");
		return false;
	}
	mOperate = "PRINT";
	fm.action="../g_print/EdorPrintSave.jsp?Operate="+mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo;
	submitForm(fm);
}
