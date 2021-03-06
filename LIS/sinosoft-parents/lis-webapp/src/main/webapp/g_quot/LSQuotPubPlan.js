/***************************************************************
 * <p>ProName：LSQuotPubBasic.js</p>
 * <p>Title：基础信息公用方法</p>
 * <p>Description：</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
/**
 * 方案维护其他信息处理
 */
function initOtherInfo(cObj, cTranProdType) {

	//其他信息部分处理
	if (cTranProdType=="01" || cTranProdType=="03" || cTranProdType=="00") {//产品类型为普通险种、建工险、个人险种时,该部分不进行展示
		document.getElementById("divInfo5").style.display = "none";
	} else if (cTranProdType=="02") {//如果是账户型险种,该处直接展示
		
		document.getElementById("productButton").style.display = "";
		document.getElementById("divInfo5").style.display = "";
	}
}

/**
 * 第二步公共初始化处理
 */
function pubInitQuotStep2(cObj, cQuotType, cTranProdType, cTranPremMode, cEnginObj) {
	document.getElementById("PlanCode").value = "";
	document.getElementById("PlanDesc").value = "";
	document.getElementById("SysPlanCode").value = "";
	if (cQuotType==tETQuotType) {//一般询价
		if (cTranPremMode==null || cTranPremMode=="") {
			
		} else {
		
			cObj.PremMode.value = cTranPremMode;
			auotQuotShowCodeName('premmode', cTranPremMode, cObj, 'PremModeName');
			//企业负担占比,待处理
			if (cTranPremMode=="1") {//企业负担
				document.getElementById("EnterpriseRate").readOnly = true;
				document.getElementById("EnterpriseRate").value = "1";
			}
		}
		
		if (cTranProdType=="00") {//普通险种
			//隐藏工程信息
			document.getElementById("divEngin").style.display = "none";
			
			//方案类型处理
			document.getElementById("tdPlan5").style.display = "";
			document.getElementById("tdPlan6").style.display = "";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//方案标识处理
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//保费计算方式处理
			document.getElementById("tdPlan10").style.display = "none";
			document.getElementById("tdPlan11").style.display = "none";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//方案人数(建工险)
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("PlanPeople").value = "";
			
			//补充的空白行
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			
			//职业类别处理
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			document.getElementById("spanOccupMid").style.display = "";
			document.getElementById("spanOccupCode").style.display = "";
			
			//年龄
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//人数,参加社保占比,男女占比
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//退休占比
			document.getElementById("RetireRate").value = "0";
			
			//薪资
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//备注
			document.getElementById("OtherDesc").value = "";
			
			initOtherInfo(cObj, cTranProdType);
		}	else if (cTranProdType=="02") {//账户型
			//隐藏工程信息
			document.getElementById("divEngin").style.display = "none";
			
			//方案类型处理
			document.getElementById("tdPlan5").style.display = "";
			document.getElementById("tdPlan6").style.display = "";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//方案标识处理
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//保费计算方式处理
			document.getElementById("tdPlan10").style.display = "none";
			document.getElementById("tdPlan11").style.display = "none";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//方案人数(建工险)
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("PlanPeople").value = "";
			
			//补充的空白行
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			
			//职业类别处理
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//年龄
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//人数,参加社保占比,男女占比
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//退休占比
			document.getElementById("RetireRate").value = "0";
			
			//薪资
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//备注
			document.getElementById("OtherDesc").value = "";

			initOtherInfo(cObj, cTranProdType);
		} else {//建工险
			//隐藏工程信息
			document.getElementById("divEngin").style.display = "";
			
			//方案类型处理
			document.getElementById("tdPlan5").style.display = "none";
			document.getElementById("tdPlan6").style.display = "none";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//方案标识处理
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//保费计算方式处理
			document.getElementById("tdPlan10").style.display = "";
			document.getElementById("tdPlan11").style.display = "";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//方案人数(建工险)
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("PlanPeople").value = "";
			
			//补充的空白行
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			
			//职业类别处理
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//年龄
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//人数,参加社保占比,男女占比
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//退休占比
			document.getElementById("RetireRate").value = "";
			
			//薪资
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//备注
			document.getElementById("OtherDesc").value = "";

			initOtherInfo(cObj, cTranProdType);
		}
	} else {//项目型询价
		if (cTranProdType=="00" || cTranProdType=="03") {
			
			//方案类型
			document.getElementById("tdPlan5").style.display = "";
			document.getElementById("tdPlan6").style.display = "";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//方案标识
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//保费计算方式
			document.getElementById("tdPlan10").style.display = "none";
			document.getElementById("tdPlan11").style.display = "none";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//方案人数,面积,造价
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("tdPlan14").style.display = "none";
			document.getElementById("tdPlan15").style.display = "none";
			document.getElementById("tdPlan16").style.display = "none";
			document.getElementById("tdPlan17").style.display = "none";
			document.getElementById("PlanPeople").style.display = "";
			document.getElementById("EnginCost").style.display = "";
			document.getElementById("EnginArea").style.display = "";
			
			//空白行处理
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
		
			document.getElementById("trPlan1").style.display = "none";
			
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("trPlan6").style.display = "none";
			document.getElementById("trPlan7").style.display = "none";
			document.getElementById("trEnginCondition").style.display = "none";
			document.getElementById("spanOccupMid").style.display = "";
			document.getElementById("spanOccupCode").style.display = "";
			
			document.getElementById("InsuPeriod").value = "";
			document.getElementsByName("InsuPeriodFlag").value = "";
			document.getElementById("InsuPeriodFlagName").value = "";
			
			//职业类别处理
			
			document.getElementById("OccupTypeRadio1").checked = true;
			
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//年龄
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//人数,参加社保占比,男女占比
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "0.5";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//退休占比
			document.getElementById("RetireRate").value = "";
			
			//薪资
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//工程信息
			document.getElementById("EnginType").value = "";
			document.getElementById("EnginDays").value = "";
			document.getElementById("EnginTypeName").value = "";
			
			document.getElementById("EnginDesc").value = "";
			document.getElementById("EnginCondition").value = "";
			
			//备注
			document.getElementById("OtherDesc").value = "";
		}	else {//建工险
  		//方案类型
			document.getElementById("tdPlan5").style.display = "none";
			document.getElementById("tdPlan6").style.display = "none";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//方案标识
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//保费计算方式
			document.getElementById("tdPlan10").style.display = "";
			document.getElementById("tdPlan11").style.display = "";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//方案人数,面积,造价
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("tdPlan14").style.display = "none";
			document.getElementById("tdPlan15").style.display = "none";
			document.getElementById("tdPlan16").style.display = "none";
			document.getElementById("tdPlan17").style.display = "none";
			document.getElementById("PlanPeople").style.display = "";
			document.getElementById("EnginCost").style.display = "";
			document.getElementById("EnginArea").style.display = "";
			
			document.getElementById("tdEngin1").style.display = "none";
			document.getElementById("tdEngin2").style.display = "none";
			document.getElementById("tdEngin3").style.display = "none";
			document.getElementById("tdEngin4").style.display = "none";
			document.getElementById("tdEngin5").style.display = "";
			document.getElementById("tdEngin6").style.display = "";
			
			
			document.getElementById("InsuPeriod").value = "";
			document.getElementsByName("InsuPeriodFlag").value = "";
			document.getElementById("InsuPeriodFlagName").value = "";
			
			//空白行处理
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("trPlan6").style.display = "";
			document.getElementById("trPlan7").style.display = "";
			document.getElementById("trEnginCondition").style.display = "none";
			
			//职业类别处理
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			
			document.getElementById("OccupTypeFlag").value = "";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//年龄
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//人数,参加社保占比,男女占比
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//退休占比
			document.getElementById("RetireRate").value = "";
			
			//薪资
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//工程信息
			document.getElementById("EnginType").value = "";
			document.getElementById("EnginDays").value = "";
			document.getElementById("EnginTypeName").value = "";
			
			document.getElementById("EnginDesc").value = "";
			document.getElementById("EnginCondition").value = "";
			
			//备注
			document.getElementById("OtherDesc").value = "";
			
			document.getElementById("divEnginFactor").innerHTML = showEnginFactorDiv(tQuotNo, tQuotBatNo, '-1', '-1', '0');//放入一个根本不存在的方案编码
		}
	}
}

/**
 * 选择方案后公共处理
 */
function pubShowPlanInfo(cObj, cQuotType, cTranProdType) {
	
	if (cQuotType==tETQuotType) {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
		
		cObj.SysPlanCode.value = PlanInfoGrid.getRowColData(tSelNo, 1);
		cObj.PlanCode.value = PlanInfoGrid.getRowColData(tSelNo, 2);
		cObj.PlanDesc.value = PlanInfoGrid.getRowColData(tSelNo, 3);
		var tPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		cObj.PlanType.value = tPlanType;
		cObj.PlanTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 5);
		
		cObj.PlanFlag.value = PlanInfoGrid.getRowColData(tSelNo, 6);
		cObj.PlanFlagName.value = PlanInfoGrid.getRowColData(tSelNo, 7);
		var tPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		cObj.PremCalType.value = tPremCalType;
		cObj.PremCalTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 9);
		
		var tOccupTypeFlag = PlanInfoGrid.getRowColData(tSelNo, 10);
		cObj.OccupTypeFlag.value = tOccupTypeFlag;//特殊处理
		//需特殊处理
		if (tOccupTypeFlag=="1") {
			
			cObj.OccupType.value = PlanInfoGrid.getRowColData(tSelNo, 12);
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
			auotQuotShowCodeName('occuptype', PlanInfoGrid.getRowColData(tSelNo, 12), cObj, 'OccupTypeName');
		} else if (tOccupTypeFlag=="2") {
			
			var tDoubOccupType = PlanInfoGrid.getRowColData(tSelNo, 12);
			var tDoubArr = tDoubOccupType.split("-");
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = tDoubArr[0];
			cObj.MaxOccupType.value = tDoubArr[1];
			cObj.OccupRate.value = PlanInfoGrid.getRowColData(tSelNo, 32);
			auotQuotShowCodeName('occuptype', tDoubArr[0], cObj, 'MinOccupTypeName');
			auotQuotShowCodeName('occuptype', tDoubArr[1], cObj, 'MaxOccupTypeName');
		} else {
			
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
		}
		
		cObj.OccupMidType.value = PlanInfoGrid.getRowColData(tSelNo, 14);
		cObj.OccupMidTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 15);
		cObj.OccupCode.value = PlanInfoGrid.getRowColData(tSelNo, 16);
		cObj.OccupCodeName.value = PlanInfoGrid.getRowColData(tSelNo, 17);
		
		cObj.MinAge.value = PlanInfoGrid.getRowColData(tSelNo, 18);
		cObj.MaxAge.value = PlanInfoGrid.getRowColData(tSelNo, 19);
		cObj.AvgAge.value = PlanInfoGrid.getRowColData(tSelNo, 20);
		
		cObj.NumPeople.value = PlanInfoGrid.getRowColData(tSelNo, 21);
		cObj.PlanPeople.value = PlanInfoGrid.getRowColData(tSelNo, 21);
		cObj.SocialInsuRate.value = PlanInfoGrid.getRowColData(tSelNo, 22);
  	
		var tSexRate = PlanInfoGrid.getRowColData(tSelNo, 23);//男女比例需特殊处理
		if (tSexRate==null || tSexRate=="") {
  	
		} else {
			
			tSexArr = tSexRate.split(":");
			cObj.MaleRate.value = tSexArr[0];
			cObj.FemaleRate.value = tSexArr[1];
		}
		
		cObj.RetireRate.value = PlanInfoGrid.getRowColData(tSelNo, 24);
		cObj.PremMode.value = PlanInfoGrid.getRowColData(tSelNo, 25);
		cObj.PremModeName.value = PlanInfoGrid.getRowColData(tSelNo, 26);
		cObj.EnterpriseRate.value = PlanInfoGrid.getRowColData(tSelNo, 27);
		cObj.MinSalary.value = PlanInfoGrid.getRowColData(tSelNo, 28);
		cObj.MaxSalary.value = PlanInfoGrid.getRowColData(tSelNo, 29);
		cObj.AvgSalary.value = PlanInfoGrid.getRowColData(tSelNo, 30);
		cObj.OtherDesc.value = PlanInfoGrid.getRowColData(tSelNo, 31);
		
		pubShowInfoControl(cObj, cQuotType, cTranProdType, tPlanType, tPremCalType, tOccupTypeFlag);
	} else {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
		var tSysPlanCode = PlanInfoGrid.getRowColData(tSelNo, 1);
		cObj.SysPlanCode.value = tSysPlanCode;
		var tPlanCode = PlanInfoGrid.getRowColData(tSelNo, 2);
		cObj.PlanCode.value = tPlanCode;
		cObj.PlanDesc.value = PlanInfoGrid.getRowColData(tSelNo, 3);
		var tPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		cObj.PlanType.value = tPlanType;
		cObj.PlanTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 5);
		
		cObj.PlanFlag.value = PlanInfoGrid.getRowColData(tSelNo, 6);
		cObj.PlanFlagName.value = PlanInfoGrid.getRowColData(tSelNo, 7);
		var tPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		cObj.PremCalType.value = tPremCalType;
		cObj.PremCalTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 9);
		
		cObj.InsuPeriod.value = PlanInfoGrid.getRowColData(tSelNo, 10);
		cObj.InsuPeriodFlag.value = PlanInfoGrid.getRowColData(tSelNo, 11);
		cObj.InsuPeriodFlagName.value = PlanInfoGrid.getRowColData(tSelNo, 12);
		
		var tOccupTypeFlag = PlanInfoGrid.getRowColData(tSelNo, 13);
		cObj.OccupTypeFlag.value = tOccupTypeFlag;//特殊处理
		//需特殊处理
		if (tOccupTypeFlag=="1") {
			
			cObj.OccupType.value = PlanInfoGrid.getRowColData(tSelNo, 15);
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
			auotQuotShowCodeName('occuptype', PlanInfoGrid.getRowColData(tSelNo, 15), cObj, 'OccupTypeName');
		} else if (tOccupTypeFlag=="2") {
			
			var tDoubOccupType = PlanInfoGrid.getRowColData(tSelNo, 15);
			var tDoubArr = tDoubOccupType.split("-");
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = tDoubArr[0];
			cObj.MaxOccupType.value = tDoubArr[1];
			cObj.OccupRate.value = PlanInfoGrid.getRowColData(tSelNo, 35);
			auotQuotShowCodeName('occuptype', tDoubArr[0], cObj, 'MinOccupTypeName');
			auotQuotShowCodeName('occuptype', tDoubArr[1], cObj, 'MaxOccupTypeName');
		} else {
			
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
		}
		
		cObj.OccupMidType.value = PlanInfoGrid.getRowColData(tSelNo, 17);
		cObj.OccupMidTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 18);
		cObj.OccupCode.value = PlanInfoGrid.getRowColData(tSelNo, 19);
		cObj.OccupCodeName.value = PlanInfoGrid.getRowColData(tSelNo, 20);
		
		cObj.MinAge.value = PlanInfoGrid.getRowColData(tSelNo, 21);
		cObj.MaxAge.value = PlanInfoGrid.getRowColData(tSelNo, 22);
		cObj.AvgAge.value = PlanInfoGrid.getRowColData(tSelNo, 23);
		
		var tNumPeople = PlanInfoGrid.getRowColData(tSelNo, 24);
		cObj.NumPeople.value = tNumPeople;
		cObj.PlanPeople.value = PlanInfoGrid.getRowColData(tSelNo, 24);
		cObj.SocialInsuRate.value = PlanInfoGrid.getRowColData(tSelNo, 25);
  	
		var tSexRate = PlanInfoGrid.getRowColData(tSelNo, 26);//男女比例需特殊处理
		
		if (tSexRate==null || tSexRate=="") {
			
			cObj.MaleRate.value = "";
			cObj.FemaleRate.value = "";
		} else {
			
			tSexArr = tSexRate.split(":");
			cObj.MaleRate.value = tSexArr[0];
			cObj.FemaleRate.value = tSexArr[1];
		}
		
		cObj.RetireRate.value = PlanInfoGrid.getRowColData(tSelNo, 27);
		cObj.PremMode.value = PlanInfoGrid.getRowColData(tSelNo, 28);
		cObj.PremModeName.value = PlanInfoGrid.getRowColData(tSelNo, 29);
		cObj.EnterpriseRate.value = PlanInfoGrid.getRowColData(tSelNo, 30);
		cObj.MinSalary.value = PlanInfoGrid.getRowColData(tSelNo, 31);
		cObj.MaxSalary.value = PlanInfoGrid.getRowColData(tSelNo, 32);
		cObj.AvgSalary.value = PlanInfoGrid.getRowColData(tSelNo, 33);
		cObj.OtherDesc.value = PlanInfoGrid.getRowColData(tSelNo, 34);
		
		if (cTranProdType=="01") {//项目询价建工险额外处理工程信息
			
			cObj.PlanPeople.value = tNumPeople;
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql26");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);
			
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				
				cObj.MaxEnginCost.value = tArr[0][0];
				cObj.MaxEnginArea.value = tArr[0][1];
				cObj.EnginType.value = tArr[0][2];
				cObj.EnginTypeName.value = tArr[0][3];
				cObj.EnginDesc.value = tArr[0][4];
				cObj.EnginCondition.value = tArr[0][5];
				cObj.EnginDays.value = tArr[0][6];
				cObj.EnginArea.value = tArr[0][7];
				cObj.EnginCost.value = tArr[0][8];
			}
			
			document.getElementById("divEnginFactor").innerHTML = showEnginFactorDiv(tQuotNo, tQuotBatNo, tSysPlanCode, tPlanCode, '0');
			pubShowConditionCheck(cObj);
		}
		
		pubShowInfoControl(cObj, cQuotType, cTranProdType, tPlanType, tPremCalType, tOccupTypeFlag);
	}
}

/**
 * 方案信息处下拉后处理
 */
function pubPlanAfterCodeSelect(cObj, cQuotType, cCodeType, FieldValue) {
	
	if (cQuotType==tETQuotType) {//一般询价
		
		if	(cCodeType=="plantype") {//选择方案后处理
			
			if (FieldValue=="00") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else if (FieldValue=="01") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="02") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="03") {
				
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cCodeType=="engincaltype") {
			
			if (FieldValue=='1') {
				
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else {
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		}
	} else {//项目询价
		
		if	(cCodeType=="plantype") {//选择方案后处理
			
			if (FieldValue=="00") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else if (FieldValue=="01") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="02") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="03") {
				
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cCodeType=="engincaltype") {
			
			if (FieldValue=='1') {
				
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "";
				document.getElementById("tdEngin6").style.display = "";
				
			} else if (FieldValue=='2') {
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "";
				document.getElementById("tdPlan15").style.display = "";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "";
				document.getElementById("tdEngin2").style.display = "";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
				
			} else {
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "";
				document.getElementById("tdPlan17").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "";
				document.getElementById("tdEngin4").style.display = "";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
			}
		}
	}
}

/**
 * 选择单多职业类别
 */
function pubChooseOccupFlag(cObj, cQuotFlag) {

	if (cQuotFlag=='1') {
		
		cObj.OccupTypeFlag.value = '1';
		document.getElementById("OccupTypeRadio1").checked = true;
		document.getElementById("OccupTypeRadio2").checked = false;
		document.getElementById("trOccupType1").style.display = "";
		document.getElementById("trOccupType2").style.display = "none";
	} else if (cQuotFlag=='2') {
		
		cObj.OccupTypeFlag.value = '2';
		document.getElementById("OccupTypeRadio2").checked = true;
		document.getElementById("OccupTypeRadio1").checked = false;
		document.getElementById("trOccupType1").style.display = "none";
		document.getElementById("trOccupType2").style.display = "";
	} else {
		
		cObj.OccupTypeFlag.value = '';
		document.getElementById("trOccupType1").style.display = "none";
		document.getElementById("trOccupType2").style.display = "none";
	}
}

/**
 * 选择方案信息后,公共展示控制
 */
function pubShowInfoControl(cObj, cQuotType, cTranProdType, cPlanType, cPremCalType, cOccupTypeFlag) {

	if (cQuotType==tETQuotType) {
		
		if (cTranProdType=="00") {//普通险种
			
			if (cPlanType=="00") {//普通方案
			
				document.getElementById("trPlan1").style.display = "";
				
				if (cOccupTypeFlag=="1") {
					
					document.getElementById("trOccupType1").style.display = "";
					document.getElementById("trOccupType2").style.display = "none";
				} else {
					document.getElementById("trOccupType1").style.display = "none";
					document.getElementById("trOccupType2").style.display = "";
				}
				
				pubChooseOccupFlag(cObj, cOccupTypeFlag);
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
			} else {//公共保额
			
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cTranProdType=="01") {//建工险险种
		
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "0";
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
				
			if (cPremCalType=="1") {
				
				document.getElementById("tdPlan10").style.display = "";
				document.getElementById("tdPlan11").style.display = "";
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else {
				
				document.getElementById("tdPlan10").style.display = "";
				document.getElementById("tdPlan11").style.display = "";
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} 
		} else if (cTranProdType=="02") {
			
			if (cPlanType=="02") {//个人账户
			
				document.getElementById("trPlan1").style.display = "";
				
				if (cOccupTypeFlag=="1") {
					
					document.getElementById("trOccupType1").style.display = "";
					document.getElementById("trOccupType2").style.display = "none";
				} else {
					document.getElementById("trOccupType1").style.display = "none";
					document.getElementById("trOccupType2").style.display = "";
				}
				pubChooseOccupFlag(cObj, cOccupTypeFlag);
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
			} else {//团体账户
			
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
			}
		}
	} else {
		
		if (cTranProdType=="00" || cTranProdType=="03") {//普通险种
			
			if (cPlanType=="00") {//普通方案
			
				document.getElementById("trPlan1").style.display = "";
				
				if (cOccupTypeFlag=="1") {
					
					document.getElementById("trOccupType1").style.display = "";
					document.getElementById("trOccupType2").style.display = "none";
				} else {
					document.getElementById("trOccupType1").style.display = "none";
					document.getElementById("trOccupType2").style.display = "";
				}
				
				pubChooseOccupFlag(cObj, cOccupTypeFlag);
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else {//公共保额
			
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cTranProdType=="01") {//建工险险种
		
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "0";
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			
			if (cPremCalType=="1") {//按人数
			
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				document.getElementById("tdPlan3").style.display = "none";
				document.getElementById("tdPlan4").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "";
				document.getElementById("tdEngin6").style.display = "";
				
			} else if (cPremCalType=="2") {//按造价
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "";
				document.getElementById("tdPlan15").style.display = "";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				document.getElementById("tdPlan3").style.display = "none";
				document.getElementById("tdPlan4").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "";
				document.getElementById("tdEngin2").style.display = "";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
				
			} else {//按建筑面积
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "";
				document.getElementById("tdPlan17").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				document.getElementById("tdPlan3").style.display = "none";
				document.getElementById("tdPlan4").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "";
				document.getElementById("tdEngin4").style.display = "";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
			}
		}
	}
	
}

/**
 * 提交时,处理多余的数据
 * 
 */
function dealRedundant(cObj, cQuotType, cTranProdType) {
	
	
	/*
	一般询价
		1.普通险种
		a) 普通方案:清空建工险的保费计算方式字段,根据职业类型清空另一个职业数据
		b) 公共保额:清空界面上所有其他隐藏数据
		2.建工险
		a) 普通方案:根据选择的保费计算方式判断清空隐藏域及其他所有隐藏域
		3.账户型
		a) 个人账户:清空建工险的保费计算方式字段,根据职业类型清空另一个职业数据
		b) 团体账户:清空界面上所有其他隐藏数据
	项目询价
		1.普通险种
		a) 普通方案:清空建工险的保费计算方式字段,根据职业类型清空另一个职业数据
		b) 公共保额:清空界面上所有其他隐藏数据
		2.建工险
		a) 普通方案:根据选择的保费计算方式判断清空隐藏域及其他所有隐藏域
	*/
	if (cQuotType==tETQuotType) {//一般询价
		
		if (cTranProdType=="00") {//普通险种
			
			document.getElementById("PremCalType").value = "";
			document.getElementById("PlanPeople").value = "";
			
			var tPlanType = document.getElementById("PlanType").value;
			var tOccupTypeFlag = document.getElementById("OccupTypeFlag").value;
			
			if (tPlanType=="00") {//普通方案
			
				if (tOccupTypeFlag=="1") {//单职业
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				} else if (tOccupTypeFlag=="2") {//多职业
					
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
				} else {//如果没有职业类别,那么都置空
				
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				}
			} else {//公共保额
				
				document.getElementById("PlanFlag").value = "";
				document.getElementById("OccupTypeFlag").value = "";
				document.getElementById("OccupType").value = "";
				document.getElementById("OccupTypeName").value = "";
				document.getElementById("OccupMidType").value = "";
				document.getElementById("OccupMidTypeName").value = "";
				document.getElementById("OccupCode").value = "";
				document.getElementById("OccupCodeName").value = "";
				document.getElementById("MinOccupType").value = "";
				document.getElementById("MinOccupTypeName").value = "";
				document.getElementById("MaxOccupType").value = "";
				document.getElementById("MaxOccupTypeName").value = "";
				document.getElementById("OccupRate").value = "";
				document.getElementById("MinAge").value = "";
				document.getElementById("MaxAge").value = "";
				document.getElementById("AvgAge").value = "";
				document.getElementById("NumPeople").value = "";
				document.getElementById("SocialInsuRate").value = "";
				document.getElementById("MaleRate").value = "";
				document.getElementById("FemaleRate").value = "";
				document.getElementById("RetireRate").value = "";
				document.getElementById("PremMode").value = "";
				document.getElementById("PremModeName").value = "";
				document.getElementById("EnterpriseRate").value = "";
				document.getElementById("MinSalary").value = "";
				document.getElementById("MaxSalary").value = "";
				document.getElementById("AvgSalary").value = "";
			}
		} else if (cTranProdType=="01") {//建工险
			
			var tPremCalType = cObj.PremCalType.value;
			
			if (tPremCalType=="1") {//按人数
				
			} else {//不是按人数的建工险,清空按人数所录入的人数
				
				document.getElementById("PlanPeople").value = "";
			}
			document.getElementById("PlanFlag").value = "";
			document.getElementById("OccupTypeFlag").value = "";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			document.getElementById("RetireRate").value = "";
			document.getElementById("PremMode").value = "";
			document.getElementById("PremModeName").value = "";
			document.getElementById("EnterpriseRate").value = "";
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			setNumPeople(cObj, tPremCalType);
		} else {//账户型
		
			document.getElementById("PremCalType").value = "";
			document.getElementById("PlanPeople").value = "";
			
			var tPlanType = document.getElementById("PlanType").value;
			var tOccupTypeFlag = document.getElementById("OccupTypeFlag").value;
			
			if (tPlanType=="02") {//个人账户
			
				if (tOccupTypeFlag=="1") {//单职业
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				} else if (tOccupTypeFlag=="2") {//多职业
					
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
				} else {//如果没有职业类别,那么都置空
				
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				}
			} else {//团体账户
				
				document.getElementById("PlanFlag").value = "";
				document.getElementById("OccupTypeFlag").value = "";
				document.getElementById("OccupType").value = "";
				document.getElementById("OccupTypeName").value = "";
				document.getElementById("OccupMidType").value = "";
				document.getElementById("OccupMidTypeName").value = "";
				document.getElementById("OccupCode").value = "";
				document.getElementById("OccupCodeName").value = "";
				document.getElementById("MinOccupType").value = "";
				document.getElementById("MinOccupTypeName").value = "";
				document.getElementById("MaxOccupType").value = "";
				document.getElementById("MaxOccupTypeName").value = "";
				document.getElementById("OccupRate").value = "";
				document.getElementById("MinAge").value = "";
				document.getElementById("MaxAge").value = "";
				document.getElementById("AvgAge").value = "";
				document.getElementById("NumPeople").value = "";
				document.getElementById("SocialInsuRate").value = "";
				document.getElementById("MaleRate").value = "";
				document.getElementById("FemaleRate").value = "";
				document.getElementById("RetireRate").value = "";
				document.getElementById("PremMode").value = "";
				document.getElementById("PremModeName").value = "";
				document.getElementById("EnterpriseRate").value = "";
				document.getElementById("MinSalary").value = "";
				document.getElementById("MaxSalary").value = "";
				document.getElementById("AvgSalary").value = "";
			}
		}
	} else {//项目询价
		
		if (cTranProdType=="00" || cTranProdType=="03") {//普通险种
			
			document.getElementById("PremCalType").value = "";
			document.getElementById("PlanPeople").value = "";
			document.getElementById("EnginCost").value = "";
			document.getElementById("EnginArea").value = "";
			document.getElementById("EnginType").value = "";
			document.getElementById("EnginDays").value = "";
			document.getElementById("EnginDesc").value = "";
			document.getElementById("EnginCondition").value = "";
			
			var tPlanType = document.getElementById("PlanType").value;
			var tOccupTypeFlag = document.getElementById("OccupTypeFlag").value;
			
			if (tPlanType=="00") {//普通方案

				if (tOccupTypeFlag=="1") {//单职业
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				} else if (tOccupTypeFlag=="2") {//多职业
					
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
				} else {//如果没有职业类别,那么都置空
				
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				}
			} else {//公共保额
				
				document.getElementById("PlanFlag").value = "";
				document.getElementById("OccupTypeFlag").value = "";
				document.getElementById("OccupType").value = "";
				document.getElementById("OccupTypeName").value = "";
				document.getElementById("OccupMidType").value = "";
				document.getElementById("OccupMidTypeName").value = "";
				document.getElementById("OccupCode").value = "";
				document.getElementById("OccupCodeName").value = "";
				document.getElementById("MinOccupType").value = "";
				document.getElementById("MinOccupTypeName").value = "";
				document.getElementById("MaxOccupType").value = "";
				document.getElementById("MaxOccupTypeName").value = "";
				document.getElementById("OccupRate").value = "";
				document.getElementById("MinAge").value = "";
				document.getElementById("MaxAge").value = "";
				document.getElementById("AvgAge").value = "";
				document.getElementById("NumPeople").value = "";
				document.getElementById("SocialInsuRate").value = "";
				document.getElementById("MaleRate").value = "";
				document.getElementById("FemaleRate").value = "";
				document.getElementById("RetireRate").value = "";
				document.getElementById("PremMode").value = "";
				document.getElementById("PremModeName").value = "";
				document.getElementById("EnterpriseRate").value = "";
				document.getElementById("MinSalary").value = "";
				document.getElementById("MaxSalary").value = "";
				document.getElementById("AvgSalary").value = "";
			}
		} else if (cTranProdType=="01") {//建工险
			
			var tPremCalType = document.getElementById("PremCalType").value;
			
			if (tPremCalType=="1") {//按人数,清空面积及造价
				
				document.getElementById("EnginCost").value = "";
				document.getElementById("EnginArea").value = "";
				document.getElementById("MaxEnginCost").value = "";
				document.getElementById("MaxEnginArea").value = "";
			} else if (tPremCalType=="2") {//按造价
				
				document.getElementById("PlanPeople").value = "";
				document.getElementById("EnginArea").value = "";
				document.getElementById("MaxEnginArea").value = "";
			} else {//按面积
				
				document.getElementById("PlanPeople").value = "";
				document.getElementById("EnginCost").value = "";
				document.getElementById("MaxEnginCost").value = "";
			}
			document.getElementById("PlanFlag").value = "";
			document.getElementById("OccupTypeFlag").value = "";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			document.getElementById("RetireRate").value = "";
			document.getElementById("PremMode").value = "";
			document.getElementById("PremModeName").value = "";
			document.getElementById("EnterpriseRate").value = "";
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			if (document.getElementById("trEnginCondition").style.display=="none") {
				
				cObj.EnginCondition.value = "";
			}
			
			setNumPeople(cObj, tPremCalType);
		}
	}
}

/**
 * 根据产品类型对隐藏区域中的人数赋值(建工险)
 */
function setNumPeople(cObj, cPremCalType) {

	if (cPremCalType=="1") {
		
		cObj.NumPeople.value = cObj.PlanPeople.value;
	} else {
		
		cObj.NumPeople.value = "";
		cObj.PlanPeople.value = "";
	}
}

/**
 * 工程明细处理
 */
function showEnginFactorDiv(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cFlag) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql24");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var tElementCode;//因子编码
	var tElementName;//因子名称
	var tControlFlag;//是否有录入框
	var tIsSelected;//借用quotno来判断该因子值是否在询价特约表中
	var tOElementValue;//原始值
	var tNElementValue;//询价值
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>工程明细<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];//用来判断是否被选中
		tOElementValue = tArr[i][4];//原始值
		tNElementValue = tArr[i][5];//询价值
		
		var tDisableFlag = "";
		if (cFlag=="1") {
			tDisableFlag = "disabled";
		}
		
		tInnerHTML1 += "<input type=checkbox name="+ tElementCode + " onclick=\"showConditionCheck();\" "+ tDisableFlag +" ";
		if (tIsSelected=='0') {//询价表中没有保存该因子

			tInnerHTML1 += ">"+ tElementName;
		} else {//询价中保存了该因子

			tInnerHTML1 += " checked>"+ tElementName;
		}
		var tFlag1 = tControlFlag.substring(0,1);
		var tFlag2 = tControlFlag.substring(1,2);
		tInnerHTML1 += "<input type=hidden name="+ tElementCode +"Flag value="+ tFlag2 +">";
		
		if (tFlag1=='1') {//存在录入框

			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tFlag1 +"><input style='width:100px' class=common name="+ tElementCode +"Value value="+ tNElementValue +" "+ tDisableFlag +">";	
		} else {
			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";	
		}
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

/**
 * 是否展示工程状况说明录入域
 */
function pubShowConditionCheck(cObj) {
	
	var tArr = document.getElementById("divEnginFactor").getElementsByTagName("input");
	
	var tFlag = false;//默认不展示工程状况说明
	for (var i=0; i<tArr.length; i++) {
		
		if (tFlag==false) {
			var tField = tArr[i];
			
			if (tField.type=="checkbox") {//只有该字段为checkbox时,才进行处理
				
				if (tField.checked) {//选中时,进行取标志值,取到标志值为1时,展示状况说明
					
					var tCheckValue = eval(cObj.name+"."+ tField.name +"Flag.value");
					if (tCheckValue=="1") {
						tFlag = true;
					}	
				}
			}
		}
	}
	
	if (tFlag==true) {
		document.getElementById("trEnginCondition").style.display = "";
	} else {
		document.getElementById("trEnginCondition").style.display = "none";
	}
}

/**
 * 提交前的校验-工程明细
 */
function checkEnginFactor(cObj) {
	
	var arrAll;
	arrAll = document.getElementById("divEnginFactor").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		if (obj.type == "checkbox") {

			if(obj.checked) {
				//如果被选中了,取选中的值的隐藏域看是否需要录入

				var tNeedInput = eval(cObj.name+".Hidden"+ obj.name +".value");
      
				if (tNeedInput=='1') {//需要赋值
					
					var tInputValue = eval(cObj.name+"."+ obj.name +"Value.value");
					
					if (tInputValue==null || tInputValue=='') {
						alert("选中的信息需录入具体信息！");
						return false;
					} else {
						
						if (tInputValue.length>30) {
							alert("录入的具体信息不应超过30字长！");
							return false;
						}
					}
				}
				tConfigCount++;
			}
		}
	}
	if (tConfigCount=="0") {
		alert("请选择工程明细！");
		return false;
	}

	return true;
}

/**
 * 工程信息提交前校验
 */
function beforeSaveEngin(cObj) {
	
	if (isEmpty(cObj.EnginName)) {
		alert("工程名称不能为空！");
		return false;
	}
	
	var tEnginName = cObj.EnginName.value;
	if (tEnginName.length>150) {
		alert("工程名称不能超过150字长！");
		return false;
	}
	
	if (isEmpty(cObj.EnginType)) {
		alert("工程类型不能为空！");
		return false;
	}
	
	//校验工程面积
	var tEnginArea = cObj.EnginArea.value;
	if (tEnginArea==null || tEnginArea=="") {
	
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

	//校验工程造价
	var tEnginCost = cObj.EnginCost.value;
	if (tEnginCost==null || tEnginCost=="") {
	
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
	
	if (!checkEnginFactor(cObj)) {
		return false;
	}
	
	if (isEmpty(cObj.EnginPlace)) {
		alert("工程地点不能为空！");
		return false;
	}
	
	var tEnginPlace = cObj.EnginPlace.value;
	if (tEnginPlace.length>150) {
		alert("工程地点不能超过150字长！");
		return false;
	}
	
	var tEnginStartDate = cObj.EnginStartDate.value;
	var tEnginEndDate = cObj.EnginEndDate.value;
	
	if (tEnginStartDate!=null && tEnginStartDate!=""&&tEnginEndDate!=null && tEnginEndDate!="") {
		if (tEnginStartDate>tEnginEndDate) {
			alert("工程起期不能晚于工程止期！");
			return false;
		}
	}
	
	if (isEmpty(cObj.EnginDesc)) {
		alert("工程概述不能为空！");
		return false;
	}
	
	var tEnginDesc = cObj.EnginDesc.value;
	if (tEnginDesc.length>1000) {
		alert("工程概述不能超过1000字长！");
		return false;
	}

	if (document.getElementById("trEnginCondition").style.display=="") {
		
		if (isEmpty(cObj.EnginCondition)) {
			alert("当工程明细勾选了道路、桥梁、隧道、水电站等工程信息时，工程状况说明不能为空！");
			return false;
		}
		
		var tEnginCondition = cObj.EnginCondition.value;
		if (tEnginCondition.length>1000) {
			alert("工程状况说明不能超过1000字长！");
			return false;
		}
	}
	
	var tRemark = cObj.Remark.value;
	if (tRemark.length>1000) {
		alert("备注不能超过1000字长！");
		return false;
	}
	
	var tInsurerName = cObj.InsurerName.value;
	if (tInsurerName.length>150) {
		alert("承包方名称不能超过150字长！");
		return false;
	}
	
	if (isEmpty(cObj.ContractorName)) {
		alert("施工方名称不能为空！");
		return false;
	}
	
	var tContractorName = cObj.ContractorName.value;
	if (tContractorName.length>150) {
		alert("施工方名称不能超过150字长！");
		return false;
	}
	
	if (isEmpty(cObj.ContractorType)) {
		alert("施工方资质不能为空！");
		return false;
	}
	
	return true;
}

/**
 * 提交前数据校验
 */
function pubBeforeSubmit(cObj, cQuotType, cTranProdType) {

	if (isEmpty(cObj.PlanDesc)) {
		alert("方案描述不能为空！");
		return false;
	}

	if (cQuotType==tETQuotType) {//一般询价
	
		if (cTranProdType=="00") {//普通险种
			
			var tPlanType = cObj.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("方案类型不能为空！");
				return false;
			}
			
			
			if (tPlanType=="00") {//普通方案
				
				//方案校验
				var tPlanFlag = cObj.PlanFlag.value;
				if (tPlanFlag==null || tPlanFlag=="") {
					alert("方案标识不能为空！");
					return false;
				}
				
				//职业校验
				var tOccupTypeFlag = cObj.OccupTypeFlag.value;
				if (tOccupTypeFlag=="1") {//单职业类别
					
					var tOccupType = cObj.OccupType.value;
					var tOccupMidType = cObj.OccupMidType.value;
					var tOccupCode = cObj.OccupCode.value;
					
					if (tOccupType==null || tOccupType=="") {
						alert("职业类别不能为空！");
						return false;
					}
					
					if (tOccupMidType==null || tOccupMidType=="") {
						alert("职业中分类不能为空！");
						return false;
					}
					
					if (tOccupCode==null || tOccupCode=="") {
						alert("工种不能为空！");
						return false;
					}
				} else {
					
					var tMinOccupType = cObj.MinOccupType.value;
					var tMaxOccupType = cObj.MaxOccupType.value;
					var tOccupRate = cObj.OccupRate.value;
					
					if (tMinOccupType==null || tMinOccupType=="") {
						alert("最低职业类别不能为空！");
						return false;
					}
					
					if (tMaxOccupType==null || tMaxOccupType=="") {
						alert("最高职业类别不能为空！");
						return false;
					}
					
					if (Number(tMinOccupType)>=Number(tMaxOccupType)) {
						alert("最低职业类别应小于最高职业类别！");
						return false;
					}
					if (tOccupRate==null || tOccupRate=="") {
						alert("职业比例不能为空！");
						return false;
					}
					
				}
				
				//年龄
				var tMinAge = cObj.MinAge.value;
				var tMaxAge = cObj.MaxAge.value;
				var tAvgAge = cObj.AvgAge.value;
				if (tAvgAge==null || tAvgAge=="") {
					alert("平均年龄不能为空！");
					return false;
				}
				
				if (tMinAge==null || tMinAge=="") {
				
				} else {
					
					if (Number(tMinAge)>Number(tAvgAge)) {
						alert("最低年龄应不大于平均年龄！");
						return false;
					}
				}
				
				if (tMaxAge==null || tMaxAge=="") {
				
				} else {
					
					if (Number(tMaxAge)<Number(tAvgAge)) {
						alert("最高年龄应不小于平均年龄！");
						return false;
					}
				}
				
				//人数
				var tNumPeople = cObj.NumPeople.value;
				if (tNumPeople==null || tNumPeople=="") {
					alert("人数不能为空！");
					return false;
				}
				
				//参加社保占比
				var tSocialInsuRate = cObj.SocialInsuRate.value;
				if (tSocialInsuRate==null || tSocialInsuRate=="") {
					alert("参加社保占比不能为空！");
					return false;
				}
				
				if (!checkDecimalFormat(tSocialInsuRate, 1, 2)) {
					alert("参加社保占比小数位不应超过2位！");
					return false;
				}
				
				//男女比例
				var tMaleRate = cObj.MaleRate.value;
				var tFemaleRate = cObj.FemaleRate.value;
				if (tMaleRate==null || tMaleRate=="" || tFemaleRate==null || tFemaleRate=="") {
					alert("男女比例不能为空");
					return false
				}
				
				if (Number(tMaleRate)==0 && Number(tFemaleRate)==0) {
					alert("男女比例不能同时为0");
					return false;
				}
				
				//退休占比
				var tRetireRate = cObj.RetireRate.value;
				if (tRetireRate==null || tRetireRate=="") {
					alert("退休占比不能为空！");
					return false;
				}
				
				if (!checkDecimalFormat(tRetireRate, 1, 2)) {
					alert("退休占比小数位不应超过2位！");
					return false;
				}
				
				//保费分摊方式
				var tPremMode = cObj.PremMode.value;
				if (tPremMode==null || tPremMode=="") {
					alert("保费分摊方式不能为空！");
					return false;
				}
				//企业负担占比
				var tEnterpriseRate = cObj.EnterpriseRate.value;
				if (tPremMode=="1") {//企业负担
					
					if (Number(tEnterpriseRate)==1) {
						
					} else {
						alert("企业负担时，企业负担占比应为1！");
						return false;
					}
				} else if (tPremMode=="2") {//个人负担
				
					if (Number(tEnterpriseRate)==0) {
						
					} else {
						alert("个人负担时，企业负担占比应为0！");
						return false;
					}
				} else {
					
					if (tEnterpriseRate==null || tEnterpriseRate=="") {
						alert("企业个人共同负担时，企业负担占比不能为空！");
						return false;
					}
					
					if (!checkDecimalFormat(tEnterpriseRate, 1, 2)) {
						alert("企业负担占比小数位不应超过2位！");
						return false;
					}
				}
				
				//月薪
				var tMinSalary = cObj.MinSalary.value;
				var tMaxSalary = cObj.MaxSalary.value;
				var tAvgSalary = cObj.AvgSalary.value;
				
				if (tMinSalary==null || tMinSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMinSalary, 8, 2)) {//校验最低月薪整数位与小数位长度
						alert("最低月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}
				
				if (tMaxSalary==null || tMaxSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMaxSalary, 8, 2)) {//校验最高月薪整数位与小数位长度
						alert("最高月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}
				
				if (tAvgSalary==null || tAvgSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tAvgSalary, 8, 2)) {//校验平均月薪整数位与小数位长度
						alert("平均月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}

				if ((tMinSalary!=null&&tMinSalary!="") && (tMaxSalary!=null&&tMaxSalary!="")) {//最低与最高月薪大小校验
					
					if (Number(tMinSalary)>Number(tMaxSalary)) {
						alert("最低月薪应不大于最高月薪！");
						return false;
					}
				}
				
				if ((tMinSalary!=null&&tMinSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//最低与平均月薪大小校验
					
					if (Number(tMinSalary)>Number(tAvgSalary)) {
						alert("最低月薪应不大于平均月薪！");
						return false;
					}
				}
				
				if ((tMaxSalary!=null&&tMaxSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//平均与最高月薪大小校验
					
					if (Number(tAvgSalary)>Number(tMaxSalary)) {
						alert("平均月薪应不大于最高月薪！");
						return false;
					}
				}
			} else {//公共保额
				
				//暂无校验
			}
		} else if (cTranProdType=="01") {//建工险
			
			var tPremCalType = cObj.PremCalType.value;
			if (tPremCalType==null || tPremCalType=="") {
				alert("保费计算方式不能为空！");
				return false;
			}
			
			if (tPremCalType=="1") {
				
				var tPlanPeople = cObj.PlanPeople.value;
				if (tPlanPeople==null || tPlanPeople=="") {
					alert("按人数计算时，方案人数不能为空！");
					return false;
				}
			}
		} else {
			
			var tPlanType = cObj.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("方案类型不能为空！");
				return false;
			}
			
			if (tPlanType=="02") {
			
				//职业校验
				var tOccupTypeFlag = cObj.OccupTypeFlag.value;
				if (tOccupTypeFlag=="1") {//单职业类别
					
					var tOccupType = cObj.OccupType.value;
					
					if (tOccupType==null || tOccupType=="") {
						alert("职业类别不能为空！");
						return false;
					}
				} else {
					
					var tMinOccupType = cObj.MinOccupType.value;
					var tMaxOccupType = cObj.MaxOccupType.value;
					var tOccupRate = cObj.OccupRate.value;
					
					if (tMinOccupType==null || tMinOccupType=="") {
						alert("最低职业类别不能为空！");
						return false;
					}
					
					if (tMaxOccupType==null || tMaxOccupType=="") {
						alert("最高职业类别不能为空！");
						return false;
					}
					
					if (Number(tMinOccupType)>=Number(tMaxOccupType)) {
						alert("最低职业类别应小于最高职业类别！");
						return false;
					}
					if (tOccupRate==null || tOccupRate=="") {
						alert("职业比例不能为空！");
						return false;
					}
				}
				
				//年龄
				var tMinAge = cObj.MinAge.value;
				var tMaxAge = cObj.MaxAge.value;
				var tAvgAge = cObj.AvgAge.value;
				if (tAvgAge==null || tAvgAge=="") {
					alert("平均年龄不能为空！");
					return false;
				}
				
				if (tMinAge==null || tMinAge=="") {
				
				} else {
					
					if (Number(tMinAge)>Number(tAvgAge)) {
						alert("最低年龄应不大于平均年龄！");
						return false;
					}
				}
				
				if (tMaxAge==null || tMaxAge=="") {
				
				} else {
					
					if (Number(tMaxAge)<Number(tAvgAge)) {
						alert("最高年龄应不小于平均年龄！");
						return false;
					}
				}
				
				//人数
				var tNumPeople = cObj.NumPeople.value;
				if (tNumPeople==null || tNumPeople=="") {
					alert("人数不能为空！");
					return false;
				}
				
				//参加社保占比
				var tSocialInsuRate = cObj.SocialInsuRate.value;
				if (tSocialInsuRate==null || tSocialInsuRate=="") {
					alert("参加社保占比不能为空！");
					return false;
				}
				
				if (!checkDecimalFormat(tSocialInsuRate, 1, 2)) {
					alert("参加社保占比小数位不应超过2位！");
					return false;
				}
				
				//男女比例
				var tMaleRate = cObj.MaleRate.value;
				var tFemaleRate = cObj.FemaleRate.value;
				if (tMaleRate==null || tMaleRate=="" || tFemaleRate==null || tFemaleRate=="") {
					alert("男女比例不能为空");
					return false
				}
				
				if (Number(tMaleRate)==0 && Number(tFemaleRate)==0) {
					alert("男女比例不能同时为0");
					return false;
				}
				
				//退休占比
				var tRetireRate = cObj.RetireRate.value;
				if (tRetireRate==null || tRetireRate=="") {
					alert("退休占比不能为空！");
					return false;
				}
				
				if (!checkDecimalFormat(tRetireRate, 1, 2)) {
					alert("参加社保占比小数位不应超过2位！");
					return false;
				}
				
				//保费分摊方式
				var tPremMode = cObj.PremMode.value;
				if (tPremMode==null || tPremMode=="") {
					alert("保费分摊方式不能为空！");
					return false;
				}
				//企业负担占比
				var tEnterpriseRate = cObj.EnterpriseRate.value;
				if (tPremMode=="1") {//企业负担
					
					if (Number(tEnterpriseRate)==1) {
						
					} else {
						alert("企业负担时，企业负担占比应为1！");
						return false;
					}
				} else if (tPremMode=="2") {//个人负担
				
					if (Number(tEnterpriseRate)==0) {
						
					} else {
						alert("个人负担时，企业负担占比应为0！");
						return false;
					}
				} else {
					
					if (tEnterpriseRate==null || tEnterpriseRate=="") {
						alert("企业个人共同负担时，企业负担占比不能为空！");
						return false;
					}
					
					if (!checkDecimalFormat(tEnterpriseRate, 1, 2)) {
						alert("企业负担占比小数位不应超过2位！");
						return false;
					}
				}
				
				//月薪
				var tMinSalary = cObj.MinSalary.value;
				var tMaxSalary = cObj.MaxSalary.value;
				var tAvgSalary = cObj.AvgSalary.value;
				
				if (tMinSalary==null || tMinSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMinSalary, 8, 2)) {//校验最低月薪整数位与小数位长度
						alert("最低月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}
				
				if (tMaxSalary==null || tMaxSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMaxSalary, 8, 2)) {//校验最高月薪整数位与小数位长度
						alert("最高月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}
				
				if (tAvgSalary==null || tAvgSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tAvgSalary, 8, 2)) {//校验平均月薪整数位与小数位长度
						alert("平均月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}
      	
				if ((tMinSalary!=null&&tMinSalary!="") && (tMaxSalary!=null&&tMaxSalary!="")) {//最低与最高月薪大小校验
					
					if (Number(tMinSalary)>Number(tMaxSalary)) {
						alert("最低月薪应不大于最高月薪！");
						return false;
					}
				}
				
				if ((tMinSalary!=null&&tMinSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//最低与平均月薪大小校验
					
					if (Number(tMinSalary)>Number(tAvgSalary)) {
						alert("最低月薪应不大于平均月薪！");
						return false;
					}
				}
				
				if ((tMaxSalary!=null&&tMaxSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//平均与最高月薪大小校验
					
					if (Number(tAvgSalary)>Number(tMaxSalary)) {
						alert("平均月薪应不大于最高月薪！");
						return false;
					}
				}
			} else {//团体账户
			
				//无校验
			}
		}
	} else {
		
		if (cTranProdType=="00" || cTranProdType=="03") {//普通险种
			
			var tPlanType = cObj.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("方案类型不能为空！");
				return false;
			}
			
			if (tPlanType=="00") {//普通方案
				
				//方案校验
				var tPlanFlag = cObj.PlanFlag.value;
				if (tPlanFlag==null || tPlanFlag=="") {
					alert("方案标识不能为空！");
					return false;
				}
				
				var tInsuPeriod = cObj.InsuPeriod.value;
				var tInsuPeriodFlag = cObj.InsuPeriodFlag.value;
				if (tInsuPeriod==null || tInsuPeriod=="" || tInsuPeriodFlag==null || tInsuPeriodFlag=="") {
					alert("保险期限不能为空！");
					return false;
				}
				
				//职业校验
				var tOccupTypeFlag = cObj.OccupTypeFlag.value;
				if (tOccupTypeFlag=="1") {//单职业类别
					
					var tOccupType = cObj.OccupType.value;
					var tOccupMidType = cObj.OccupMidType.value;
					var tOccupCode = cObj.OccupCode.value;
					
					if (tOccupType==null || tOccupType=="") {
						alert("职业类别不能为空！");
						return false;
					}
					
					if (tOccupMidType==null || tOccupMidType=="") {
						alert("职业中分类不能为空！");
						return false;
					}
					
					if (tOccupCode==null || tOccupCode=="") {
						alert("工种不能为空！");
						return false;
					}
				} else {
					
					var tMinOccupType = cObj.MinOccupType.value;
					var tMaxOccupType = cObj.MaxOccupType.value;
					var tOccupRate = cObj.OccupRate.value;
					
					if (tMinOccupType==null || tMinOccupType=="") {
						alert("最低职业类别不能为空！");
						return false;
					}
					
					if (tMaxOccupType==null || tMaxOccupType=="") {
						alert("最高职业类别不能为空！");
						return false;
					}
					
					if (Number(tMinOccupType)>=Number(tMaxOccupType)) {
						alert("最低职业类别应小于最高职业类别！");
						return false;
					}
					
					if (tOccupRate==null || tOccupRate=="") {
						alert("职业比例不能为空！");
						return false;
					}
				}
				
				//年龄
				var tMinAge = cObj.MinAge.value;
				var tMaxAge = cObj.MaxAge.value;
				var tAvgAge = cObj.AvgAge.value;
				if (tAvgAge==null || tAvgAge=="") {
					alert("平均年龄不能为空！");
					return false;
				}
				
				if (tMinAge==null || tMinAge=="") {
				
				} else {
					
					if (Number(tMinAge)>Number(tAvgAge)) {
						alert("最低年龄应不大于平均年龄！");
						return false;
					}
				}
				
				if (tMaxAge==null || tMaxAge=="") {
				
				} else {
					
					if (Number(tMaxAge)<Number(tAvgAge)) {
						alert("最高年龄应不小于平均年龄！");
						return false;
					}
				}
				
				//人数
				var tNumPeople = cObj.NumPeople.value;
				/*
				if (tNumPeople==null || tNumPeople=="") {
					alert("人数不能为空！");
					return false;
				}*/
				
				
				//参加社保占比
				var tSocialInsuRate = cObj.SocialInsuRate.value;
				if (tSocialInsuRate==null || tSocialInsuRate=="") {
					alert("参加社保占比不能为空！");
					return false;
				} else {
					
					if (!checkDecimalFormat(tSocialInsuRate, 1, 2)) {
						alert("参加社保占比小数位不应超过2位！");
						return false;
					}
				}

				//男女比例
				var tMaleRate = cObj.MaleRate.value;
				var tFemaleRate = cObj.FemaleRate.value;
				if (tMaleRate==null || tMaleRate=="" || tFemaleRate==null || tFemaleRate=="") {
					alert("男女比例不能空！");
					return false;
				} else if (tMaleRate!=null && tMaleRate!="" && tFemaleRate!=null && tFemaleRate!="") {
					
					if (Number(tMaleRate)==0 && Number(tFemaleRate)==0) {
						alert("男女比例不能同时为0！");
						return false;
					}
				} 
				
				//退休占比
				var tRetireRate = cObj.RetireRate.value;
				if (tRetireRate==null || tRetireRate=="") {
					
				} else {
					
					if (!checkDecimalFormat(tRetireRate, 1, 2)) {
						alert("参加社保占比小数位不应超过2位！");
						return false;
					}
				}

				//保费分摊方式
				var tPremMode = cObj.PremMode.value;
				if (tPremMode==null || tPremMode=="") {

				} else {  
					
					//企业负担占比
					var tEnterpriseRate = cObj.EnterpriseRate.value;
					if (tPremMode=="1") {//企业负担
						
						if (Number(tEnterpriseRate)==1) {
							
						} else {
							alert("企业负担时，企业负担占比应为1！");
							return false;
						}
					} else if (tPremMode=="2") {//个人负担
					
						if (Number(tEnterpriseRate)==0) {
							
						} else {
							alert("个人负担时，企业负担占比应为0！");
							return false;
						}
					} else {
						
						if (tEnterpriseRate==null || tEnterpriseRate=="") {
							alert("企业个人共同负担时，企业负担占比不能为空！");
							return false;
						}
						
						if (!checkDecimalFormat(tEnterpriseRate, 1, 2)) {
							alert("企业负担占比小数位不应超过2位！");
							return false;
						}
					}
				}
				
				//月薪
				var tMinSalary = cObj.MinSalary.value;
				var tMaxSalary = cObj.MaxSalary.value;
				var tAvgSalary = cObj.AvgSalary.value;
				
				if (tMinSalary==null || tMinSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMinSalary, 8, 2)) {//校验最低月薪整数位与小数位长度
						alert("最低月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}
				
				if (tMaxSalary==null || tMaxSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMaxSalary, 8, 2)) {//校验最高月薪整数位与小数位长度
						alert("最高月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}
				
				if (tAvgSalary==null || tAvgSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tAvgSalary, 8, 2)) {//校验平均月薪整数位与小数位长度
						alert("平均月薪整数位不应超过8位,小数位不应超过2位！");
						return false;
					}
				}

				if ((tMinSalary!=null&&tMinSalary!="") && (tMaxSalary!=null&&tMaxSalary!="")) {//最低与最高月薪大小校验
					
					if (Number(tMinSalary)>Number(tMaxSalary)) {
						alert("最低月薪应不大于最高月薪！");
						return false;
					}
				}
				
				if ((tMinSalary!=null&&tMinSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//最低与平均月薪大小校验
					
					if (Number(tMinSalary)>Number(tAvgSalary)) {
						alert("最低月薪应不大于平均月薪！");
						return false;
					}
				}
				
				if ((tMaxSalary!=null&&tMaxSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//平均与最高月薪大小校验
					
					if (Number(tAvgSalary)>Number(tMaxSalary)) {
						alert("平均月薪应不大于最高月薪！");
						return false;
					}
				}
			} else {//公共保额
				
				//暂无校验
			}
		} else if (cTranProdType=="01") {//建工险
			
			var tPremCalType = cObj.PremCalType.value;
			if (tPremCalType==null || tPremCalType=="") {
				alert("保费计算方式不能为空！");
				return false;
			}
			
			if (tPremCalType=="1") {//按人数
				
				var tPlanPeople = cObj.PlanPeople.value;
				if (tPlanPeople==null || tPlanPeople=="") {
					alert("按人数计算时，方案人数不能为空！");
					return false;
				}
			} else if (tPremCalType=="2") {//按工程造价
				
				var tEnginCost = cObj.EnginCost.value;
				if (tEnginCost==null || tEnginCost=="") {
					alert("按工程造价计算时，最低工程造价不能为空！");
					return false;
				}
				
				//工程造价,应为大于0的有效数字
				if (!isNumeric(tEnginCost) || Number(tEnginCost)<0) {
					alert("最低工程造价应为大于0的有效数字！");
					return false;
				}
				
				if (!checkDecimalFormat(tEnginCost, 18, 2)) {
					alert("最低工程造价整数位不应超过18位,小数位不应超过2位！");
					return false;
				}
				
				var tMaxEnginCost = cObj.MaxEnginCost.value;
				if (tMaxEnginCost==null || tMaxEnginCost=="") {
					alert("按工程造价计算时，最高工程造价不能为空！");
					return false;
				}
				
				//工程造价,应为大于0的有效数字
				if (!isNumeric(tMaxEnginCost) || Number(tMaxEnginCost)<0) {
					alert("最高工程造价应为大于0的有效数字！");
					return false;
				}
				
				if (!checkDecimalFormat(tMaxEnginCost, 18, 2)) {
					alert("最高工程造价整数位不应超过18位,小数位不应超过2位！");
					return false;
				}
				if (Number(tEnginCost) > Number(tMaxEnginCost)) {
					alert("最低工程造价应小于等于最高工程造价！");
					return false;
				}
				
			} else {//按建筑面积
				
				var tEnginArea = cObj.EnginArea.value;
				if (tEnginArea==null || tEnginArea=="") {
					alert("按建筑面积计算时，最低工程面积不能为空！");
					return false;
				}
				
				//工程面积,应为大于0的有效数字
				if (!isNumeric(tEnginArea) || Number(tEnginArea)<0) {
					alert("最低工程面积应为大于0的有效数字！");
					return false;
				}
				
				if (!checkDecimalFormat(tEnginArea, 18, 2)) {
					alert("最低工程面积整数位不应超过18位,小数位不应超过2位！");
					return false;
				}
				
				var tMaxEnginArea = cObj.MaxEnginArea.value;
				if (tMaxEnginArea==null || tMaxEnginArea=="") {
					alert("按建筑面积计算时，最高工程面积不能为空！");
					return false;
				}
				
				//工程面积,应为大于0的有效数字
				if (!isNumeric(tMaxEnginArea) || Number(tMaxEnginArea)<0) {
					alert("最高工程面积应为大于0的有效数字！");
					return false;
				}
				
				if (!checkDecimalFormat(tMaxEnginArea, 18, 2)) {
					alert("最高工程面积整数位不应超过18位,小数位不应超过2位！");
					return false;
				}
				if (Number(tEnginArea) > Number(tMaxEnginArea)) {
					alert("最低工程面积应小于等于最高工程面积！");
					return false;
				}
			}
			
			var tInsuPeriod = cObj.InsuPeriod.value;
			var tInsuPeriodFlag = cObj.InsuPeriodFlag.value;
			if (tInsuPeriod==null || tInsuPeriod=="" || tInsuPeriodFlag==null || tInsuPeriodFlag=="") {
				alert("保险期限不能为空！");
				return false;
			}
			
			if (isEmpty(cObj.EnginType)) {
				alert("工程类型不能为空！");
				return false;
			}
			
			if (isEmpty(cObj.EnginDays)) {
				alert("施工天数不能为空！");
				return false;
			}
			
			if (isEmpty(cObj.EnginDesc)) {
				alert("工程概述不能为空！");
				return false;
			}
			
			var tEnginDesc = cObj.EnginDesc.value;
			if (tEnginDesc.length>1000) {
				alert("工程概述不能超过1000字长！");
				return false;
			}
  		
			if (document.getElementById("trEnginCondition").style.display=="") {
				
				if (isEmpty(cObj.EnginCondition)) {
					alert("当工程明细勾选了道路、桥梁、隧道、水电站等工程信息时，工程状况说明不能为空！");
					return false;
				}
		  		
				var tEnginCondition = cObj.EnginCondition.value;
				if (tEnginCondition.length>1000) {
					alert("工程状况说明不能超过1000字长！");
					return false;
				}
			}
			
			if (!checkEnginFactor(cObj)) {
				return false;
			}
			
			if (document.getElementById("trEnginCondition").style.display=="") {
				
				if (isEmpty(cObj.EnginCondition)) {
					alert("当工程明细勾选了道路、桥梁、隧道、水电站等工程信息时，工程状况说明不能为空！");
					return false;
				}
				
				var tEnginCondition = cObj.EnginCondition.value;
				if (tEnginCondition.length>1000) {
					alert("工程状况说明不能超过1000字长！");
					return false;
				}
			}
		}
	}
	
	return true;
}

/**
 * 方案修改前校验
 */
function pubBeforeModifyPlan(cObj, cTranProdType) {

	//不允许方案/保费计算方法间进行修改
	if (cTranProdType=="00" || cTranProdType=="03" || cTranProdType=="02") {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPlanType = cObj.PlanType.value;
		var tMulPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		
		if (tPlanType!=tMulPlanType) {
			alert("修改方案时，不能变更方案类型！");
			return false;
		}
	} else if (cTranProdType=="01") {
		
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPremCalType = cObj.PremCalType.value;
		var tMulPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		if (tPremCalType!=tMulPremCalType) {
			alert("修改方案时，不能变更保费计算方式！");
			return false;
		}
	}
	
	//普通险种、个人险种，在修改方案时，职业类型由单变多时，如果有非费用折扣的期望保费类型，职业类型不许修改
	if (tTranProdType=="00" || tTranProdType=="03") {
		
		var tSelNo = PlanInfoGrid.getSelNo()-1;
		var tOccupTypeFlagOld = PlanInfoGrid.getRowColData(tSelNo,10);
		if (tOccupTypeFlagOld=="1") {
			
			var tOccupTypeFlagNew = cObj.OccupTypeFlag.value;
			if (tOccupTypeFlagOld != tOccupTypeFlagNew) {
				
				var tSysPlanCode =  PlanInfoGrid.getRowColData(tSelNo,1);
				var tPlanCode =  PlanInfoGrid.getRowColData(tSelNo,2);
				
//				tSQLInfo = new SqlClass();
//				tSQLInfo.setResourceName("g_quot.LSQuotSql");
//				tSQLInfo.setSqlId("LSQuotSql39");
//				tSQLInfo.addSubPara(tQuotNo);
//				tSQLInfo.addSubPara(tQuotBatNo);
//				tSQLInfo.addSubPara(tSysPlanCode);
//				tSQLInfo.addSubPara(tPlanCode);
//				
//				var tArrNum = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//				if (tArrNum[0][0]>0) {
//					alert("方案明细中含有非费率折扣的期望保费类型，职业类型不可修改！"); 
//					return false;
//				}
			}
		}
	}

	return true;
}
