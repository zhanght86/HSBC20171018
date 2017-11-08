/***************************************************************
 * <p>ProName：LSQuotChangePremInput.js</p>
 * <p>Title：报价单打印--变更保费</p>
 * <p>Description：报价单打印--变更保费</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-20
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

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
		queryPlanDetailInfo();
		document.all("divOfferPlanDetail").style.display = "none";
	}
}

/**
 * 查询方案类型
 */
function getQuotType(cQuotNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotChangePremSql");
	tSQLInfo.setSqlId("LSQuotChangePremSql1");
	tSQLInfo.addSubPara(tQuotNo);
	
	var arrResult = easyExecSql(tSQLInfo.getString());
	
	if (arrResult==null) {
		
	} else {
		return arrResult[0][0];
	}
	
	return null;
}

/**
 * 询价方案明细
 */
function queryPlanDetailInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotChangePremSql");
	tSQLInfo.setSqlId("LSQuotChangePremSql2");
	tSQLInfo.addSubPara(tOfferListNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	
	initPlanDetailInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanDetailInfoGrid, 1, 1);
}

/**
 * 点击方案明细,展示信息
 */
function showPlanDetailInfo() {
	
	document.all("divOfferPlanDetail").style.display = "";
	
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	var tFlag = 1;
	var tObj = fm;
	var tDivID = "divDutyFactor";
	
	var tPlanDesc = PlanDetailInfoGrid.getRowColData(tSelNo, 3);
	var tPlanType = PlanDetailInfoGrid.getRowColData(tSelNo, 4);
	var tPremCalType = PlanDetailInfoGrid.getRowColData(tSelNo, 6);
	var tPlanFlag = PlanDetailInfoGrid.getRowColData(tSelNo, 8);
	var tOccupTypeFlag = PlanDetailInfoGrid.getRowColData(tSelNo, 10);
	var tRiskName = PlanDetailInfoGrid.getRowColData(tSelNo, 13);
	var tDutyName = PlanDetailInfoGrid.getRowColData(tSelNo, 15);
	
	tObj.all("SysPlanCode").value = tSysPlanCode;
	tObj.all("PlanCode").value = tPlanCode;
	tObj.all("PlanType").value = tPlanType;
	tObj.all("PremCalType").value = tPremCalType;
	tObj.all("PlanFlag").value = tPlanFlag;
	tObj.all("OccupTypeFlag").value = tOccupTypeFlag;
	tObj.all("OldOccupTypeFlag").value = tOccupTypeFlag;
	tObj.all("PlanDesc").value = tPlanDesc;
	tObj.all("RiskCode").value = tRiskCode;
	tObj.all("RiskName").value = tRiskName;
	tObj.all("DutyCode").value = tDutyCode;
	tObj.all("DutyName").value = tDutyName;
	
	tObj.all("OldPlanType").value = tPlanType;
	tObj.all("OldPremCalType").value = tPremCalType;
	
	//获取原始系统方案编码，方案编码
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotChangePremSql");
	tSQLInfo.setSqlId("LSQuotChangePremSql3");
	tSQLInfo.addSubPara(tOfferListNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	tSQLInfo.addSubPara(tRiskCode);
	tSQLInfo.addSubPara(tDutyCode);
	
	var tPalnArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPalnArr==null) {
		alert("获取原始方案信息失败！");
		return false;
	} else {
		var tRSysPlanCode = tPalnArr[0][0];
		var tRPlanCode = tPalnArr[0][1];
	}
	dealRiskDuty(tRiskCode, tDutyCode, tFlag, tObj, tDivID, tRSysPlanCode, tRPlanCode, tPlanType);
	//展示变更信息
	var tChangeType = PlanDetailInfoGrid.getRowColData(tSelNo, 16);
	showChangeInfo(tChangeType);
	afterMulShowChangeInfo(fm,tChangeType,fm.AmntType.value);
	
	//add by ZhangC 20150402 添加应收保费录入
	var tAmntType = PlanDetailInfoGrid.getRowColData(tSelNo, 18);
	
	if (tQuotType == "01" && tTranProdType == "01" && tAmntType == "01") {
		document.all("divReceivPrem").style.display = "";
		var tReceivPrem = PlanDetailInfoGrid.getRowColData(tSelNo, 31);
		fm.ReceivPrem.value = tReceivPrem;
	} else {
		document.all("divReceivPrem").style.display = "none";
	}
	
}

/**
 * 处理产品责任要素展示
 * cRiskCode:险种;cDutyCode:责任;cFlag:标识(0-下拉后处理,1-点击记录);obj:对应的FM;cDivID:需要展示的域;
 */
function dealRiskDuty(cRiskCode, cDutyCode, cFlag, cObj, cDivID, cSysPlanCode, cPlanCode, cPlanType) {
	
	document.all(cDivID).innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	document.all(cDivID+"Relation").innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	
	document.all("AmntType").value = "";
	document.all("AmntTypeName").value = "";
	document.all("FixedAmnt").value = "";
	document.all("SalaryMult").value = "";
	document.all("MaxAmnt").value = "";
	document.all("MinAmnt").value = "";
	document.all("ExceptPremType").value = "";
	document.all("ExceptPremTypeName").value = "";
	document.all("ExceptPrem").value = "";
	document.all("InitPrem").value = "";
	document.all("ExceptYield").value = "";
	document.all("trRelation").style.display = "none";
	document.all("SetRelation").checked = false;
	document.all("RelaShareFlag").value = "0";
	document.all("trRelationRate").style.display = "none";
	document.all("RelaToMain").value = "";
	document.all("RelaToMainName").value = "";
	document.all("MainAmntRate").value = "";
	document.all("RelaAmntRate").value = "";
	document.all("divDutyFactorRelation").style.display = "none";
	
	//1.根据产品类型进行展示域处理
	if (tTranProdType=="01") {//建工险
	
		document.all("idAmnt").style.display = "";  
		document.all("idPrem").style.display = "";   
		document.all("idFeeRate").style.display = "none";
		document.all("tryCalButton").style.display = "none";
		document.all("trRelation").style.display = "none";
	} else if (tTranProdType=="02") {//账户型险种
		
		document.all("idAmnt").style.display = "none";
		document.all("idPrem").style.display = "none";
		document.all("idFeeRate").style.display = "";
		document.all("tryCalButton").style.display = "none"
		document.all("trRelation").style.display = "none";
	} else if (tTranProdType=="03") {//个人险种
		
		document.all("idAmnt").style.display = "";  
		document.all("idPrem").style.display = "";   
		document.all("idFeeRate").style.display = "none";
		document.all("tryCalButton").style.display = "none";
		document.all("trRelation").style.display = "none";
	} else {//其余都默认为一般险种
		
		document.all("idAmnt").style.display = "";  
		document.all("idPrem").style.display = "";   
		document.all("idFeeRate").style.display = "none";
		document.all("tryCalButton").style.display = "none";
		
		if (cPlanType=="00") {
			document.all("trRelation").style.display = "";
		} else {
			document.all("trRelation").style.display = "none";
		}
	}
	
	//2.查询出需展示的因子并展示在界面上
	var tArr = getDutyElementArr(cRiskCode, cDutyCode);
	if (tArr==null || tArr.length==0) {
	
	} else {
		
		document.all(cDivID).innerHTML = getDutyElementDomain(tArr, "0");
		if (document.all("trRelation").style.display=="") {
			document.all(cDivID+"Relation").innerHTML = getDutyElementDomain(tArr, "1");	
		}
	}
	
	// 先进行固定域的赋值以便处理附属被保险人方案
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql20");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
	
	var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr2==null) {
		alert("获取所选记录信息失败！");
		return false;
	} else {//赋值
		
		var tFixedPlanInfo = new Array();
		var i = 0;
		
		tFixedPlanInfo[i++] = "AmntType";
		tFixedPlanInfo[i++] = "AmntTypeName";
		tFixedPlanInfo[i++] = "FixedAmnt";
		tFixedPlanInfo[i++] = "SalaryMult";
		tFixedPlanInfo[i++] = "MaxAmnt";
		tFixedPlanInfo[i++] = "MinAmnt";
		tFixedPlanInfo[i++] = "ExceptPremType";
		
		tFixedPlanInfo[i++] = "ExceptPremTypeName";
		tFixedPlanInfo[i++] = "ExceptPrem";
		tFixedPlanInfo[i++] = "InitPrem";
		tFixedPlanInfo[i++] = "ExceptYield";
		
		tFixedPlanInfo[i++] = "RelaShareFlag";
		/*
		tFixedPlanInfo[i++] = "RelaToMain";
		tFixedPlanInfo[i++] = "RelaToMainName";
		tFixedPlanInfo[i++] = "MainAmntRate";
		tFixedPlanInfo[i++] = "RelaAmntRate";
		*/
		tFixedPlanInfo[i++] = "Remark";
			
		for(var t=0; t<i; t++) {
			document.all(tFixedPlanInfo[t]).value = tArr2[0][t];
		}
		
		dealAmntShow(cObj, cObj.AmntType.value);
		dealRelationShow(cObj, cObj.RelaShareFlag.value);
	}
	
	var tSQLElement;
	if (tArr==null || tArr.length==0) {
	
	} else {
		
		tSQLElement = getDutySQLElement(tArr);
		
		//3.传递查询字段进行从表查询
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql42");
		tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
		tSQLInfo.addSubPara(tOfferListNo);
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
		tSQLInfo.addSubPara(cRiskCode);
		tSQLInfo.addSubPara(cDutyCode);
		
		var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr1==null) {
			
		} else {//循环对界面要素赋值
			
			for (var i=0; i<tArr.length; i++) {
				
				tFactorCode = tArr[i][1];//从表字段
				tFieldType = tArr[i][3];//字段类型
				tValueType = tArr[i][4];//值类型
				
				if (tFieldType=="1") {
					
					document.all(tFactorCode).value = tArr1[0][i];
					auotQuotShowCodeName(tValueType, tArr1[0][i], cObj, tFactorCode+"Name");
				} else {
					if (tFactorCode == "P18") {//标准人均保费
					} else {
						document.all(tFactorCode).value = tArr1[0][i];
					}
				}
				
				//add by ZhangC 20150323 处理公共保额 保费计算方式 人均保费 展示
				if (tFactorCode== "P16" && document.all("P16").value == "1") {
					
					document.all("idPerPrem1").style.display = "";
					document.all("idPerPrem2").style.display = "";
					document.all("hidden1").style.display = "none";
					document.all("hidden2").style.display = "none";
				} else if (tFactorCode== "P16" &&  document.all("P16").value == "0") {
					
					document.all("idPerPrem1").style.display = "none";
					document.all("idPerPrem2").style.display = "none";
					document.all("hidden1").style.display = "";
					document.all("hidden2").style.display = "";
				}
			}
		}
	}

	if (document.all("trRelation").style.display=="" && document.all("RelaShareFlag").value=="1") {//只有当附属方案配置展示且被勾选了,才查询附属信息
		
		//3.传递查询字段进行从表查询
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql30");
		if (tSQLElement==null || tSQLElement=="") {
			tSQLInfo.addSubPara("");//SQL查询字段
		} else {
			tSQLInfo.addSubPara(","+tSQLElement);//SQL查询字段
		}
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
		tSQLInfo.addSubPara(cRiskCode);
		tSQLInfo.addSubPara(cDutyCode);
		
		var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr2==null) {
			
		} else {//循环对界面要素赋值
			
			var tRelaFactor = "Relation";
			var tRelaFactorName = "附属方案";
			
			document.all("RelaToMain").value = tArr2[0][0];
			document.all("RelaToMainName").value = tArr2[0][1];
			document.all("MainAmntRate").value = tArr2[0][2];
			document.all("RelaAmntRate").value = tArr2[0][3];
				
			if (tArr==null) {
			
			} else { 
				
				for (var i=0; i<tArr.length; i++) {
				
					tFactorCode = tRelaFactor + tArr[i][1];//从表字段
					tFieldType = tArr[i][3];//字段类型
					tValueType = tArr[i][4];//值类型

					var j = i+4;
					if (tFieldType=="1") {
						
						document.all(tFactorCode).value = tArr2[0][j];
						auotQuotShowCodeName(tValueType, tArr2[0][j], cObj, tFactorCode+"Name");
					} else {
						document.all(tFactorCode).value = tArr2[0][j];
					}
				}
			}
		}
	}
}

/**
 * 获取责任动态展示域
 */
function getDutyElementDomain(tArr, cFlag) {
	
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
			
			 if (tFieldType=="1") {
				
				tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=readonly name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" readonly style=\"width:20px\"><input class=readonly style=\"width:20px\" value = \"-- \"><input class=readonly name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\" readonly style=\"width:130px\"></td>";
				
			} else {
				if (tCalFactor =="PerPrem") {
					tInnerHTML0 += "<td class=title id=idPerPrem1 name=idPerPrem1 style=\"display: ''\">"+ tRelaFactorName + tFactorName +"</td><td class=input id=idPerPrem2 name=idPerPrem2 style=\"display: ''\"><input class=readonly name="+ tRelaFactor + tFactorCode + " value=\""+ tDefaultValue +"\"></td><td class=title id =hidden1 name=hidden1 style=\"display: 'none'\"></td><td class=input id =hidden2 name=hidden2 style=\"display: 'none'\"></td>";
				} else if (tCalFactor =="StandPerPrem") {
					tInnerHTML0 += "<td class=title></td><td class=input></td>";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=readonly name="+ tRelaFactor + tFactorCode + " value=\""+ tDefaultValue +"\"></td>";
				}
				
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
 * 选择保额后处理
 */
function dealAmntShow(cObj, tFieldValue) {

	if (tFieldValue=="01") {
		
		document.all("idFixedAmnt00").style.display = "";
		document.all("idFixedAmnt01").style.display = "";
		document.all("idSalaryMult00").style.display = "none";
		document.all("idSalaryMult01").style.display = "none";      
		document.all("idMaxAmnt00").style.display = "none";
		document.all("idMaxAmnt01").style.display = "none";
		document.all("idMinAmnt00").style.display = "none";
		document.all("idMinAmnt01").style.display = "none";
		document.all("idAmnt00").style.display = "none";
		document.all("idAmnt01").style.display = "none";
		document.all("idAmnt02").style.display = "";
		document.all("idAmnt03").style.display = "";
	} else if (tFieldValue=="02") {
		
		document.all("idFixedAmnt00").style.display = "none";
		document.all("idFixedAmnt01").style.display = "none";
		document.all("idSalaryMult00").style.display = "";
		document.all("idSalaryMult01").style.display = "";      
		document.all("idMaxAmnt00").style.display = "none";
		document.all("idMaxAmnt01").style.display = "none";
		document.all("idMinAmnt00").style.display = "none";
		document.all("idMinAmnt01").style.display = "none";
		document.all("idAmnt00").style.display = "none";
		document.all("idAmnt01").style.display = "none";
		document.all("idAmnt02").style.display = "";
		document.all("idAmnt03").style.display = "";
	} else if (tFieldValue=="03") {
		
		document.all("idFixedAmnt00").style.display = "none";
		document.all("idFixedAmnt01").style.display = "none";
		document.all("idSalaryMult00").style.display = "";
		document.all("idSalaryMult01").style.display = "";      
		document.all("idMaxAmnt00").style.display = "none";
		document.all("idMaxAmnt01").style.display = "none";
		document.all("idMinAmnt00").style.display = "";
		document.all("idMinAmnt01").style.display = "";
		document.all("idAmnt00").style.display = "none";
		document.all("idAmnt01").style.display = "none";
		document.all("idAmnt02").style.display = "none";
		document.all("idAmnt03").style.display = "none";
	} else if (tFieldValue=="04") {
		
		document.all("idFixedAmnt00").style.display = "none";
		document.all("idFixedAmnt01").style.display = "none";
		document.all("idSalaryMult00").style.display = "none";
		document.all("idSalaryMult01").style.display = "none";      
		document.all("idMaxAmnt00").style.display = "";
		document.all("idMaxAmnt01").style.display = "";
		document.all("idMinAmnt00").style.display = "";
		document.all("idMinAmnt01").style.display = "";
		document.all("idAmnt00").style.display = "none";
		document.all("idAmnt01").style.display = "none";
		document.all("idAmnt02").style.display = "none";
		document.all("idAmnt03").style.display = "none";
	}
}

/**
 * 点击主附共用配置时处理
 */
function setRelationFlag() {

	if (document.all("divDutyFactorRelation").style.display=="") {
		
		fm.RelaShareFlag.value = "0";
		dealRelationShow(fm, "0");
	} else {
		fm.RelaShareFlag.value = "1";
		dealRelationShow(fm, "1");
	}
}

/**
 * 根据主副关联标记展示附属域
 */
function dealRelationShow(cObj, tFieldValue) {
	
	if (tFieldValue=="1") {
		document.all("SetRelation").checked = true;
		document.all("SetRelation").disabled = true;
		document.all("trRelationRate").style.display = ""; 
		document.all("divDutyFactorRelation").style.display = "";
	} else {
		document.all("SetRelation").checked = false;
		document.all("SetRelation").disabled = true;
		document.all("trRelationRate").style.display = "none"; 
		document.all("divDutyFactorRelation").style.display = "none";
	}
}

/**
 * 查询变更类型
 */
function selectChangeType(Filed,FildName,ExceptPremType,AmntType) {
	
	var tExceptPremType = ExceptPremType.value;//期望保费类型
	var tAmntType = AmntType.value;// 保额类型     
	
	if (tExceptPremType==null || tExceptPremType=="") {
		alert("获取原期望保费类型失败!");
		return false;
	}
	
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	var tPlanType = PlanDetailInfoGrid.getRowColData(tSelNo, 4);
	
	var conditionChangeType = "";
	if (tAmntType=="04" && tExceptPremType == "01") {
		conditionChangeType = "1";
	} else if (tAmntType=="04" && tExceptPremType != "01") {
		conditionChangeType = "1 and code in (#0#,#2#,#3#) ";
	} else if (tAmntType!="04" && tExceptPremType == "01") {
		//modify by ZhangC 20150113 公共保额新增降低保费
		//因公共保额方案的保额类型只为固定保额,在此处添加下拉选项
		if (tPlanType=="01") {
			//公共保额保费计算方式为人均保费时，不允许提高、降低总保费
			var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
			var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
			var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
			var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotChangePremSql");
			tSQLInfo.setSqlId("LSQuotChangePremSql4");
			tSQLInfo.addSubPara(tOfferListNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);
			tSQLInfo.addSubPara(tRiskCode);
			tSQLInfo.addSubPara(tDutyCode);
			
			var tPremCalWayArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
			if (tPremCalWayArr[0][0] == "1") {//按人均保费收费
				conditionChangeType = "1 and code in (#0#,#1#) ";
			} else {
				conditionChangeType = "1 and code in (#0#,#1#,#2#,#4#) ";
			}
			
		} else {
			conditionChangeType = "1 and code in (#0#,#1#,#2#) ";
		}
	} else if (tAmntType!="04" && tExceptPremType != "01") {
		if (tPlanType=="01") {
			conditionChangeType = "1 and code in (#0#,#2#,#4#) ";
		} else {
			conditionChangeType = "1 and code in (#0#,#2#) ";
		}
	} 
	showCodeList('changetype',[Filed,FildName],[0,1],null,conditionChangeType,'1','1',null);
}
/**
 * 变更类型下拉后处理
 */
function afterCodeSelect(o, p) {
	
	if(o=='changetype') {
		clearChangInfo();
		var tChangeType = p.value;
		showChangeInfo(tChangeType);
	}
}

/**
 * 清空变更信息录入框
 */
function clearChangInfo() {
	
	fm.FixedAmntChange.value = "";
	fm.SalaryMultChange.value = "";
	fm.MinAmntChange.value = "";
	fm.MaxAmntChange.value = "";
	fm.ExceptPremChange.value = "";
	fm.FixedAmntChange1.value = "";
	fm.FixedPremChange1.value = "";
}
/**
 * 展示变更信息
 */
function showChangeInfo(cChangeType) {
	
	if(cChangeType=="1") {
		idAmntChange.style.display = '';
		id2.style.display = 'none';
		id3.style.display = 'none';
		fm.AmntTypeChange.value = fm.AmntType.value;
		fm.AmntTypeChangeName.value = fm.AmntTypeName.value;
		dealAmntTypeChange(fm,fm.AmntTypeChange.value);
		
	} else if(cChangeType=="2"){
		idAmntChange.style.display = 'none';
		id2.style.display = '';
		id3.style.display = 'none';
		fm.ExceptPremTypeChangeCode.value = fm.ExceptPremType.value;
		fm.ExceptPremTypeChange.value = fm.ExceptPremTypeName.value;
	} else if(cChangeType=="3"){
		idAmntChange.style.display = 'none';
		id2.style.display = 'none';
		id3.style.display = '';
		fm.ExceptPremTypeChangeCode1.value = "01";
		fm.ExceptPremTypeChange1.value = "期望保费";
	} else if(cChangeType=="4"){
		idAmntChange.style.display = 'none';
		id2.style.display = '';
		id3.style.display = 'none';
		fm.ExceptPremTypeChangeCode.value = fm.ExceptPremType.value;
		fm.ExceptPremTypeChange.value = fm.ExceptPremTypeName.value;
	} else{
		idAmntChange.style.display = 'none';
		id2.style.display = 'none';
		id3.style.display = 'none';
	}
}

/**
 * 点击Mul后变更信息赋值
 */
function afterMulShowChangeInfo(cObj,cChangeType,cAmntTypeChange){

	clearChangInfo();
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	cObj.ChangeType.value = cChangeType;
	cObj.ChangeTypeName.value = PlanDetailInfoGrid.getRowColData(tSelNo, 17);
	
	if(cChangeType=="1") {
		if (cAmntTypeChange=="01") {
			cObj.FixedAmntChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 20);
		} else if (cAmntTypeChange=="02") {
			cObj.SalaryMultChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 21);
		} else if (cAmntTypeChange=="03") {
			cObj.SalaryMultChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 21);
			cObj.MinAmntChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 23);
		} else if (cAmntTypeChange=="04") {
			cObj.MaxAmntChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 22);
			cObj.MinAmntChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 23);
		}
		
	} else if(cChangeType=="2"){
		
		cObj.ExceptPremChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 26);
	} else if(cChangeType=="3"){
		
		cObj.FixedAmntChange1.value = PlanDetailInfoGrid.getRowColData(tSelNo, 20);
		cObj.FixedPremChange1.value = PlanDetailInfoGrid.getRowColData(tSelNo, 26);
	} else if(cChangeType=="4"){
		
		cObj.ExceptPremChange.value = PlanDetailInfoGrid.getRowColData(tSelNo, 26);
	}
}
/**
 * 变更类型为降低保额时，根据保额类型展示界面
 */
function dealAmntTypeChange(cObj, tFieldValue) {

	if (tFieldValue=="01") {
		
		document.all("idFixedAmntChange00").style.display = "";
		document.all("idFixedAmntChange01").style.display = "";
		document.all("idSalaryMultChange00").style.display = "none";
		document.all("idSalaryMultChange01").style.display = "none";      
		document.all("idMaxAmntChange00").style.display = "none";
		document.all("idMaxAmntChange01").style.display = "none";
		document.all("idMinAmntChange00").style.display = "none";
		document.all("idMinAmntChange01").style.display = "none";
		document.all("idAmntChange00").style.display = "none";
		document.all("idAmntChange01").style.display = "none";
		document.all("idAmntChange02").style.display = "";
		document.all("idAmntChange03").style.display = "";
	} else if (tFieldValue=="02") {
		
		document.all("idFixedAmntChange00").style.display = "none";
		document.all("idFixedAmntChange01").style.display = "none";
		document.all("idSalaryMultChange00").style.display = "";
		document.all("idSalaryMultChange01").style.display = "";      
		document.all("idMaxAmntChange00").style.display = "none";
		document.all("idMaxAmntChange01").style.display = "none";
		document.all("idMinAmntChange00").style.display = "none";
		document.all("idMinAmntChange01").style.display = "none";
		document.all("idAmntChange00").style.display = "none";
		document.all("idAmntChange01").style.display = "none";
		document.all("idAmntChange02").style.display = "";
		document.all("idAmntChange03").style.display = "";
	} else if (tFieldValue=="03") {
		
		document.all("idFixedAmntChange00").style.display = "none";
		document.all("idFixedAmntChange01").style.display = "none";
		document.all("idSalaryMultChange00").style.display = "";
		document.all("idSalaryMultChange01").style.display = "";      
		document.all("idMaxAmntChange00").style.display = "none";
		document.all("idMaxAmntChange01").style.display = "none";
		document.all("idMinAmntChange00").style.display = "";
		document.all("idMinAmntChange01").style.display = "";
		document.all("idAmntChange00").style.display = "none";
		document.all("idAmntChange01").style.display = "none";
		document.all("idAmntChange02").style.display = "none";
		document.all("idAmntChange03").style.display = "none";
	} else if (tFieldValue=="04") {
		
		document.all("idFixedAmntChange00").style.display = "none";
		document.all("idFixedAmntChange01").style.display = "none";
		document.all("idSalaryMultChange00").style.display = "none";
		document.all("idSalaryMultChange01").style.display = "none";      
		document.all("idMaxAmntChange00").style.display = "";
		document.all("idMaxAmntChange01").style.display = "";
		document.all("idMinAmntChange00").style.display = "";
		document.all("idMinAmntChange01").style.display = "";
		document.all("idAmntChange00").style.display = "none";
		document.all("idAmntChange01").style.display = "none";
		document.all("idAmntChange02").style.display = "none";
		document.all("idAmntChange03").style.display = "none";
	}
}

/**
 * 提交时处理冗余数据
 */
function dealRedundant(cObj, cTranProdType) {

	if (cTranProdType=="00") {//普通险种
	
		var tAmntTypeChange = document.all("AmntTypeChange").value;
		if (tAmntTypeChange=="01") {//固定保额
		
			document.all("SalaryMultChange").value = "";
			document.all("MaxAmntChange").value = "";
			document.all("MinAmntChange").value = "";
		} else if (tAmntTypeChange=="02") {//月薪倍数
		
			document.all("FixedAmntChange").value = "";
			document.all("MaxAmntChange").value = "";
			document.all("MinAmntChange").value = "";
		} else if (tAmntTypeChange=="03") {//月薪倍数与最低保额取大者
			
			document.all("FixedAmntChange").value = "";
			document.all("MaxAmntChange").value = "";
		} else if (tAmntTypeChange=="04") {//非固定保额
			
			document.all("FixedAmntChange").value = "";
			document.all("SalaryMultChange").value = "";
		}
		
	} else if (cTranProdType=="01") {//建工险险种
		
		var tAmntTypeChange = document.all("AmntTypeChange").value;
		if (tAmntTypeChange=="01") {//固定保额
		
			document.all("SalaryMultChange").value = "";
			document.all("MaxAmntChange").value = "";
			document.all("MinAmntChange").value = "";
		} else if (tAmntTypeChange=="02") {//月薪倍数
		
			document.all("FixedAmntChange").value = "";
			document.all("MaxAmntChange").value = "";
			document.all("MinAmntChange").value = "";
		} else if (tAmntTypeChange=="03") {//月薪倍数与最低保额取大者
			
			document.all("FixedAmntChange").value = "";
			document.all("MaxAmntChange").value = "";
		} else if (tAmntTypeChange=="04") {//非固定保额
			
			document.all("FixedAmntChange").value = "";
			document.all("SalaryMultChange").value = "";
		}
	} else if (cTranProdType=="02") {//账户型险种
	
		document.all("AmntTypeChange").value = "";
		document.all("FixedAmntChange").value = "";
		document.all("SalaryMultChange").value = "";
		document.all("MaxAmntChange").value = "";
		document.all("MinAmntChange").value = "";
	} else if (cTranProdType=="03") {//个人险种
	
		var tAmntTypeChange = document.all("AmntTypeChange").value;
		if (tAmntTypeChange=="01") {//固定保额
		
			document.all("SalaryMultChange").value = "";
			document.all("MaxAmntChange").value = "";
			document.all("MinAmntChange").value = "";
		} else if (tAmntTypeChange=="02") {//月薪倍数
		
			document.all("FixedAmntChange").value = "";
			document.all("MaxAmntChange").value = "";
			document.all("MinAmntChange").value = "";
		} else if (tAmntTypeChange=="03") {//月薪倍数与最低保额取大者
			
			document.all("FixedAmntChange").value = "";
			document.all("MaxAmntChange").value = "";
		} else if (tAmntTypeChange=="04") {//非固定保额
			
			document.all("FixedAmntChange").value = "";
			document.all("SalaryMultChange").value = "";
		}
	}
}

/**
 * 提交前校验
 */
function beforeSubmit(cObj) {
	
	var tChangeType = document.all("ChangeType").value;
	
	if (tChangeType==null || tChangeType=="") {
		alert("变更类型不能为空!");
		return false;
	}
	if (tChangeType == "1") {//降低保额
		
		var tAmntTypeChange = document.all("AmntTypeChange").value;
		if (tAmntTypeChange==null || tAmntTypeChange=="") {
			alert("保额类型不能为空!");
			return false;
		}
		if (tAmntTypeChange=="01") {//固定保额
		
			var tFixedAmntChange = document.all("FixedAmntChange").value;
			if (tFixedAmntChange==null || tFixedAmntChange=="") {
				alert("变更固定保额不能为空！");
				return false;
			}
			
			if (!isNumeric(tFixedAmntChange )) {
				alert("变更固定保额不是有效的数字！");
				return false;
			}
			if (!checkDecimalFormat(tFixedAmntChange, 12, 2)) {
				alert("变更固定保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
			var tFixedAmnt = document.all("FixedAmnt").value;
			if (Number(tFixedAmntChange)>=Number(tFixedAmnt)){
				alert("变更固定保额应小于原固定保额！");
				return false;
			}
		} else if (tAmntTypeChange=="02") {//月薪倍数
			
			var tSalaryMultChange = document.all("SalaryMultChange").value;
			if (tSalaryMultChange==null || tSalaryMultChange=="") {
				alert("变更月薪倍数不能为空！");
				return false;
			}
			if (!isNumeric(tSalaryMultChange )) {
				alert("变更月薪倍数不是有效的数字！");
				return false;
			}
			if (!checkDecimalFormat(tSalaryMultChange, 5, 2)) {
				alert("变更月薪倍数整数位不应超过4位,小数位不应超过2位！");
				return false;
			}
		} else if (tAmntTypeChange=="03") {//月薪倍数与最低保额取大者
			
			var tSalaryMultChange = document.all("SalaryMultChange").value;
			if (tSalaryMultChange==null || tSalaryMultChange=="") {
				alert("变更月薪倍数不能为空！");
				return false;
			}
			if (!isNumeric(tSalaryMultChange )) {
				alert("变更月薪倍数不是有效的数字！");
				return false;
			}
			if (!checkDecimalFormat(tSalaryMultChange, 5, 2)) {
				alert("变更月薪倍数整数位不应超过4位,小数位不应超过2位！");
				return false;
			}
			
			var tMinAmntChange = document.all("MinAmntChange").value;
			if (tMinAmntChange==null || tMinAmntChange=="") {
				alert("变更最低保额不能为空！");
				return false;
			}
			if (!isNumeric(tMinAmntChange )) {
				alert("变更最低保额不是有效的数字！");
				return false;
			}
			if (!checkDecimalFormat(tMinAmntChange, 12, 2)) {
				alert("变更最低保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
		} else if (tAmntTypeChange=="04") {//非固定保额
			
			var tMaxAmntChange = document.all("MaxAmntChange").value;
			if (tMaxAmntChange==null || tMaxAmntChange=="") {
				alert("变更最高保额不能为空！");
				return false;
			}
			if (!isNumeric(tMaxAmntChange )) {
				alert("变更最高保额不是有效的数字！");
				return false;
			}
			if (!checkDecimalFormat(tMaxAmntChange, 12, 2)) {
				alert("变更最高保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
			
			var tMinAmntChange = document.all("MinAmntChange").value;
			if (tMinAmntChange==null || tMinAmntChange=="") {
				alert("变更最低保额不能为空！");
				return false;
			}
			if (!isNumeric(tMinAmntChange )) {
				alert("变更最低保额不是有效的数字！");
				return false;
			}
			if (!checkDecimalFormat(tMinAmntChange, 12, 2)) {
				alert("变更最低保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
			
			if (Number(tMinAmntChange)>Number(tMaxAmntChange)) {
				alert("变更最低保额不应大于变更最高保额！");
				return false;
			}
		}
	} else if (tChangeType == "2") {//提高保费
		
		var tExceptPremTypeChangeCode = cObj.ExceptPremTypeChangeCode.value;
		if (tExceptPremTypeChangeCode==null || tExceptPremTypeChangeCode=="") {
			alert("期望保费类型不能为空！");
			return false;
		}
		
		var tExceptPremChange = cObj.ExceptPremChange.value;
		if (tExceptPremChange==null || tExceptPremChange=="") {
			alert("变更后期望保费/期望费率/费率折扣不能为空！");
			return false;
		}
		if (!isNumeric(tExceptPremChange )) {
			alert("变更后期望保费/期望费率/费率折扣不是有效的数字！");
			return false;
		}
		if (tExceptPremTypeChangeCode=="01") {//保费,12,2
		
			if (!checkDecimalFormat(tExceptPremChange, 12, 2)) {
				
				alert("当期望保费类型为期望保费时，期望保费/期望费率/费率折扣整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
			
			var tExceptPrem = document.all("ExceptPrem").value;
			if (Number(tExceptPremChange)<=Number(tExceptPrem)){
				alert("变更后期望保费应大于原期望保费！");
				return false;
			}
			
		} else if (tExceptPremTypeChangeCode=="02") {//费率
			
			if (!checkDecimalFormat(tExceptPremChange, 4, 8)) {
				
				alert("当期望保费类型为期望费率时，期望保费/期望费率/费率折扣整数位不应超过4位,小数位不应超过8位！");
				return false;
			}
			var tExceptPrem = document.all("ExceptPrem").value;
			if (Number(tExceptPremChange)<=Number(tExceptPrem)){
				alert("变更后期望费率应大于原期望费率！");
				return false;
			}
		} else if (tExceptPremTypeChangeCode=="03") {//折扣
			
			if (!checkDecimalFormat(tExceptPremChange, 2, 2)) {
				
				alert("当期望保费类型为费率折扣时，期望保费/期望费率/费率折扣整数位不应超过2位,小数位不应超过2位！");
				return false;
			}
			var tExceptPrem = document.all("ExceptPrem").value;
			if (Number(tExceptPremChange)<=Number(tExceptPrem)){
				alert("变更后费率折扣应大于原费率折扣！");
				return false;
			}
		}
	} else if (tChangeType == "3") {//非固定保额转固定保额
		
		var tExceptPremTypeChangeCode1 = cObj.ExceptPremTypeChangeCode1.value;
		if (tExceptPremTypeChangeCode1==null || tExceptPremTypeChangeCode1=="") {
			alert("期望保费类型不能为空！");
			return false;
		}
		
		//固定保额
		var tFixedAmntChange1 = document.all("FixedAmntChange1").value;
		if (tFixedAmntChange1==null || tFixedAmntChange1=="") {
			alert("变更后固定保额不能为空！");
			return false;
		}
		
		if (!isNumeric(tFixedAmntChange1 )) {
			alert("变更后固定保额不是有效的数字！");
			return false;
		}
		if (!checkDecimalFormat(tFixedAmntChange1, 12, 2)) {
			alert("变更后固定保额整数位不应超过12位,小数位不应超过2位！");
			return false;
		}
//		var tMinAmnt = document.all("MinAmnt").value;
//		if (Number(tMinAmnt)>Number(tFixedAmntChange1)){
//			alert("变更后固定保额应大于等于原最低保额！");
//			return false;
//		}
//		var tMaxAmnt = document.all("MaxAmnt").value;
//		if (Number(tFixedAmntChange1)>Number(tMaxAmnt)){
//			alert("变更后固定保额应小于等于原最高保额！");
//			return false;
//		}
		//期望保费
		var tFixedPremChange1 = document.all("FixedPremChange1").value;
		if (tFixedPremChange1==null || tFixedPremChange1=="") {
			alert("变更后期望保费不能为空！");
			return false;
		}
		
		if (!isNumeric(tFixedPremChange1 )) {
			alert("变更后期望保费不是有效的数字！");
			return false;
		}
		if (!checkDecimalFormat(tFixedPremChange1, 12, 2)) {
			alert("变更后期望保费整数位不应超过12位,小数位不应超过2位！");
			return false;
		}
	} else if (tChangeType == "4") {//降低保费
		
		var tExceptPremTypeChangeCode = cObj.ExceptPremTypeChangeCode.value;
		if (tExceptPremTypeChangeCode==null || tExceptPremTypeChangeCode=="") {
			alert("期望保费类型不能为空！");
			return false;
		}
		
		var tExceptPremChange = cObj.ExceptPremChange.value;
		if (tExceptPremChange==null || tExceptPremChange=="") {
			alert("变更后期望保费/期望费率/费率折扣不能为空！");
			return false;
		}
		if (!isNumeric(tExceptPremChange )) {
			alert("变更后期望保费/期望费率/费率折扣不是有效的数字！");
			return false;
		}
		if (tExceptPremTypeChangeCode=="01") {//保费,12,2
		
			if (!checkDecimalFormat(tExceptPremChange, 12, 2)) {
				
				alert("当期望保费类型为期望保费时，期望保费/期望费率/费率折扣整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
			
			var tExceptPrem = document.all("ExceptPrem").value;
			if (Number(tExceptPremChange)>Number(tExceptPrem)){
				alert("变更后期望保费应小于等于原期望保费！");
				return false;
			}
			if (Number(tExceptPremChange)<(Number(tExceptPrem)/2)){
				alert("变更后期望保费应大于等于原期望保费的50%！");
				return false;
			}
			
		} else if (tExceptPremTypeChangeCode=="02") {//费率
			
			if (!checkDecimalFormat(tExceptPremChange, 4, 8)) {
				
				alert("当期望保费类型为期望费率时，期望保费/期望费率/费率折扣整数位不应超过4位,小数位不应超过8位！");
				return false;
			}
			var tExceptPrem = document.all("ExceptPrem").value;
			if (Number(tExceptPremChange)>Number(tExceptPrem)){
				alert("变更后期望费率应小于等于原期望费率！");
				return false;
			}
			if (Number(tExceptPremChange)<(Number(tExceptPrem)/2)){
				alert("变更后期望费率应大于等于原期望费率的50%！");
				return false;
			}
		} else if (tExceptPremTypeChangeCode=="03") {//折扣
			
			if (!checkDecimalFormat(tExceptPremChange, 2, 2)) {
				
				alert("当期望保费类型为费率折扣时，期望保费/期望费率/费率折扣整数位不应超过2位,小数位不应超过2位！");
				return false;
			}
			var tExceptPrem = document.all("ExceptPrem").value;
			if (Number(tExceptPremChange)>Number(tExceptPrem)){
				alert("变更后费率折扣应小于等于原费率折扣！");
				return false;
			}
			if (Number(tExceptPremChange)<(Number(tExceptPrem)/2)){
				alert("变更后费率折扣应大于等于原费率折扣的50%！");
				return false;
			}
		}
	}
	return true;
}

/**
 * 保存
 */
function saveClick() {
	
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	
	dealRedundant(fm, tTranProdType);
	
	if (!beforeSubmit(fm)) {
		return false;
	}
	
	fm.Operate.value = "SAVE";
	fm.action = "./LSQuotChangePremSave.jsp?Operate=SAVE&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo +"&RiskCode="+ tRiskCode +"&DutyCode="+ tDutyCode;
	submitForm(fm);

}

/**
 * 试算
 */
function receivPremCal() {

	mOperate = "TRYCALPREM";
	
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	
	fm.action = "./LSQuotReceivPremSave.jsp?Operate=TRYCALPREM&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo+"&OfferListNo="+ tOfferListNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode +"&RiskCode="+ tRiskCode+"&DutyCode="+ tDutyCode;
	submitForm(fm); 
}

/**
 * 保存应收保费
 */
function saveReceivPrem() {
	
	mOperate = "SAVEPREM";
	
	var tReceivPrem = fm.ReceivPrem.value;
	if (tReceivPrem =="" || tReceivPrem == null) {
		alert("应收保费不能为空！");
		return false;
	} else {
		if (!isNumeric(tReceivPrem)) {
			alert("应收保费不是有效的数字！");
			return false;
		}
		
		if (!checkDecimalFormat(tReceivPrem, 12, 2)) {
			alert("应收保费整数位不应超过12位,小数位不应超过2位！");
			return false;
		}
	}
	
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	
	fm.action = "./LSQuotReceivPremSave.jsp?Operate=SAVEPREM&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo+"&OfferListNo="+ tOfferListNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode +"&RiskCode="+ tRiskCode+"&DutyCode="+ tDutyCode;
	submitForm(fm); 

}

function afterSubmitTryCal(FlagStr, content) {
	
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
		
		if (mOperate=="TRYCALPREM") {
			fm.ReceivPrem.value = content;
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
			queryPlanDetailInfo();
			document.all("divOfferPlanDetail").style.display = "none";
			document.all("divReceivPrem").style.display = "none";
		}
	}	
}
