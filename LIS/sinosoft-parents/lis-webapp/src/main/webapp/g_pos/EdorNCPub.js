/***************************************************************
 * <p>ProName：EdorNCPub.js</p>
 * <p>Title：方案信息公用方法</p>
 * <p>Description：</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/

/**
 * 第二步公共初始化处理
 */
function pubInitContStep2(cObj , cContPlanType, cTranPremMode) {
	
	document.all("PlanCode").value = "";
	document.all("PlanDesc").value = "";
	document.all("SysPlanCode").value = "";
		
	if (cTranPremMode==null || cTranPremMode=="") {
		
	} else {
		
		cObj.PremMode.value = cTranPremMode;
		auotContShowCodeName('premmode', cTranPremMode, cObj, 'PremModeName');
		//企业负担占比,待处理
		if (cTranPremMode=="1") {//企业负担
			document.all("EnterpriseRate").readOnly = true;
			document.all("EnterpriseRate").value = "1";
		}
	}
	
	if (cContPlanType=="00" || cContPlanType=="") {//普通险种 默认先都展示普通类型
		//方案类型处理
		document.all("tdPlan5").style.display = "";
		document.all("tdPlan6").style.display = "";
		document.all("PlanType").value = "";
		document.all("PlanTypeName").value = "";
		
		//方案标识处理
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		//document.all("PlanFlag").value = "";
		//document.all("PlanFlagName").value = "";
		
		//保费计算方式处理
		document.all("tdPlan10").style.display = "none";
		document.all("tdPlan11").style.display = "none";
		document.all("PremCalType").value = "";
		document.all("PremCalTypeName").value = "";
		
		//方案人数(建工险)
		document.all("tdPlan12").style.display = "none";
		document.all("tdPlan13").style.display = "none";
		document.all("PlanPeople").value = "";
		
		//补充的空白行
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
		document.all("tdPlan3").style.display = "none";
		document.all("tdPlan4").style.display = "none";
		
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		
		//职业类别处理
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "1";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("OccupRate").value = "";
		document.all("spanOccupMid").style.display = "";
		document.all("spanOccupCode").style.display = "";
		
		//年龄
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		
		//人数,参加社保占比,男女占比
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		
		//退休占比
		document.all("RetireRate").value = "0";
		
		//薪资
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		
		//备注
		document.all("OtherDesc").value = "";
		
	}	else if (cContPlanType=="02") {//账户型
		
		//方案类型处理
		document.all("tdPlan5").style.display = "";
		document.all("tdPlan6").style.display = "";
		document.all("PlanType").value = "";
		document.all("PlanTypeName").value = "";
		
		//方案标识处理
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		//document.all("PlanFlag").value = "";
		//document.all("PlanFlagName").value = "";
		
		//保费计算方式处理
		document.all("tdPlan10").style.display = "none";
		document.all("tdPlan11").style.display = "none";
		document.all("PremCalType").value = "";
		document.all("PremCalTypeName").value = "";
		
		//方案人数(建工险)
		document.all("tdPlan12").style.display = "none";
		document.all("tdPlan13").style.display = "none";
		document.all("PlanPeople").value = "";
		
		//补充的空白行
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
		document.all("tdPlan3").style.display = "none";
		document.all("tdPlan4").style.display = "none";
		
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		
		//职业类别处理
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "1";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("OccupRate").value = "";
		
		//年龄
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		
		//人数,参加社保占比,男女占比
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		
		//退休占比
		document.all("RetireRate").value = "0";
		
		//薪资
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		
		//备注
		document.all("OtherDesc").value = "";
		
	} else if (cContPlanType=="01"){//建工险
		
		//方案类型处理
		document.all("tdPlan5").style.display = "none";
		document.all("tdPlan6").style.display = "none";
		document.all("PlanType").value = "";
		document.all("PlanTypeName").value = "";
		
		//方案标识处理
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		document.all("PlanFlag").value = "";
		document.all("PlanFlagName").value = "";
		
		//保费计算方式处理
		document.all("tdPlan10").style.display = "";
		document.all("tdPlan11").style.display = "";
		document.all("PremCalType").value = "";
		document.all("PremCalTypeName").value = "";
		
		//方案人数(建工险)
		document.all("tdPlan12").style.display = "none";
		document.all("tdPlan13").style.display = "none";
		document.all("PlanPeople").value = "";
		
		//补充的空白行
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
		document.all("tdPlan3").style.display = "none";
		document.all("tdPlan4").style.display = "none";
		
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		
		//职业类别处理
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "1";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("OccupRate").value = "";
		
		//年龄
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		
		//人数,参加社保占比,男女占比
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		
		//退休占比
		document.all("RetireRate").value = "";
		
		//薪资
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		
		//备注
		document.all("OtherDesc").value = "";
	} 
}

/**
 * 提交时,处理多余的数据
 * 
 */
function dealRedundant(cObj, cContPlanType) {
	/*
		1.普通险种
		a) 普通方案:清空建工险的保费计算方式字段,根据职业类型清空另一个职业数据
		b) 公共保额:清空界面上所有其他隐藏数据
		2.建工险
		a) 普通方案:根据选择的保费计算方式判断清空隐藏域及其他所有隐藏域
		3.账户型
		a) 个人账户:清空建工险的保费计算方式字段,根据职业类型清空另一个职业数据
		b) 团体账户:清空界面上所有其他隐藏数据
	*/
		
	if (cContPlanType=="00") {//普通险种
		
		document.all("PremCalType").value = "";
		document.all("PlanPeople").value = "";
		
		var tPlanType = document.all("PlanType").value;
		var tOccupTypeFlag = document.all("OccupTypeFlag").value;
		
		if (tPlanType=="00") {//普通方案
		
			if (tOccupTypeFlag=="1") {//单职业
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			} else if (tOccupTypeFlag=="2") {//多职业
				
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
			} else {//如果没有职业类别,那么都置空
			
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			}
		} else {//公共保额
			
			document.all("PlanFlag").value = "";
			document.all("OccupTypeFlag").value = "";
			document.all("OccupType").value = "";
			document.all("OccupTypeName").value = "";
			document.all("OccupMidType").value = "";
			document.all("OccupMidTypeName").value = "";
			document.all("OccupCode").value = "";
			document.all("OccupCodeName").value = "";
			document.all("MinOccupType").value = "";
			document.all("MinOccupTypeName").value = "";
			document.all("MaxOccupType").value = "";
			document.all("MaxOccupTypeName").value = "";
			document.all("MinAge").value = "";
			document.all("MaxAge").value = "";
			document.all("AvgAge").value = "";
			document.all("NumPeople").value = "";
			document.all("SocialInsuRate").value = "";
			document.all("MaleRate").value = "";
			document.all("FemaleRate").value = "";
			document.all("RetireRate").value = "";
			document.all("PremMode").value = "";
			document.all("PremModeName").value = "";
			document.all("EnterpriseRate").value = "";
			document.all("MinSalary").value = "";
			document.all("MaxSalary").value = "";
			document.all("AvgSalary").value = "";
			document.all("OccupRate").value = "";
		}
	} else if (cContPlanType=="01") {//建工险
		
		var tPremCalType = cObj.PremCalType.value;
		
		if (tPremCalType=="1") {//按人数
			
		} else {//不是按人数的建工险,清空按人数所录入的人数
			
			document.all("PlanPeople").value = "";
		}
		document.all("PlanFlag").value = "";
		document.all("OccupTypeFlag").value = "";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		document.all("RetireRate").value = "";
		document.all("PremMode").value = "";
		document.all("PremModeName").value = "";
		document.all("EnterpriseRate").value = "";
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		document.all("OccupRate").value = "";
		setNumPeople(cObj, tPremCalType);
	} else {//账户型
	
		document.all("PremCalType").value = "";
		document.all("PlanPeople").value = "";
		
		var tPlanType = document.all("PlanType").value;
		var tOccupTypeFlag = document.all("OccupTypeFlag").value;
		
		if (tPlanType=="02") {//个人账户
		
			if (tOccupTypeFlag=="1") {//单职业
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			} else if (tOccupTypeFlag=="2") {//多职业
				
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
			} else {//如果没有职业类别,那么都置空
			
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			}
		} else {//团体账户
			
			document.all("PlanFlag").value = "";
			document.all("OccupTypeFlag").value = "";
			document.all("OccupType").value = "";
			document.all("OccupTypeName").value = "";
			document.all("OccupMidType").value = "";
			document.all("OccupMidTypeName").value = "";
			document.all("OccupCode").value = "";
			document.all("OccupCodeName").value = "";
			document.all("MinOccupType").value = "";
			document.all("MinOccupTypeName").value = "";
			document.all("MaxOccupType").value = "";
			document.all("MaxOccupTypeName").value = "";
			document.all("MinAge").value = "";
			document.all("MaxAge").value = "";
			document.all("AvgAge").value = "";
			document.all("NumPeople").value = "";
			document.all("SocialInsuRate").value = "";
			document.all("MaleRate").value = "";
			document.all("FemaleRate").value = "";
			document.all("RetireRate").value = "";
			document.all("PremMode").value = "";
			document.all("PremModeName").value = "";
			document.all("EnterpriseRate").value = "";
			document.all("MinSalary").value = "";
			document.all("MaxSalary").value = "";
			document.all("AvgSalary").value = "";
			document.all("OccupRate").value = "";
		}
	}
}


/**
 * 提交前数据校验
 */
function pubBeforeSubmit(cObj, cContPlanType) {

	if (isEmpty(cObj.PlanDesc)) {
		alert("方案描述不能为空！");
		return false;
	}
	if (cContPlanType=="00") {//普通险种
		
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
			var tOccupRate = cObj.OccupRate.value;
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
				
				if (tMinOccupType==null || tMinOccupType=="") {
					alert("最低职业类别不能为空！");
					return false;
				}
				
				if (tMaxOccupType==null || tMaxOccupType=="") {
					alert("最高职业类别不能为空！");
					return false;
				}
				
				if (Number(tMinOccupType)>Number(tMaxOccupType)) {
					alert("最低职业类别不能高于最高职业类别！");
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
		} else {//公共保额
			
			//暂无校验
		}
	} else if (cContPlanType=="01") {//建工险
		
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
				
				if (Number(tMinOccupType)>Number(tMaxOccupType)) {
					alert("最低职业类别不能高于最高职业类别！");
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
				return flase;
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
	return true;
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
 * 选择方案后公共处理
 */
function pubShowPlanInfo(cObj, cContPlanType) {
	
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
		auotContShowCodeName('occuptype', PlanInfoGrid.getRowColData(tSelNo, 12), cObj, 'OccupTypeName');
	} else if (tOccupTypeFlag=="2") {
		
		var tDoubOccupType = PlanInfoGrid.getRowColData(tSelNo, 12);
		var tDoubArr = tDoubOccupType.split("-");
		cObj.OccupType.value = "";
		cObj.OccupTypeName.value = "";
		cObj.MinOccupType.value = tDoubArr[0];
		cObj.MaxOccupType.value = tDoubArr[1];
		cObj.OccupRate.value = PlanInfoGrid.getRowColData(tSelNo, 32);
		auotContShowCodeName('occuptype', tDoubArr[0], cObj, 'MinOccupTypeName');
		auotContShowCodeName('occuptype', tDoubArr[1], cObj, 'MaxOccupTypeName');
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
	
	pubShowInfoControl(cObj, cContPlanType, tPlanType, tPremCalType, tOccupTypeFlag);

}


/**
 * 选择方案信息后,公共展示控制
 */
function pubShowInfoControl(cObj, cContPlanType, cPlanType, cPremCalType, cOccupTypeFlag) {
		
	if (cContPlanType=="00") {//普通险种
		
		if (cPlanType=="00") {//普通方案
		
			document.all("trPlan1").style.display = "";
			
			if (cOccupTypeFlag=="1") {
				
				document.all("trOccupType1").style.display = "";
				document.all("trOccupType2").style.display = "none";
			} else {
				document.all("trOccupType1").style.display = "none";
				document.all("trOccupType2").style.display = "";
			}
			
			pubChooseOccupFlag(cObj, cOccupTypeFlag);
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "";
			document.all("tdPlan9").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else {//公共保额
		
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	} else if (cContPlanType=="01") {//建工险险种
	
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "0";
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
			
		if (cPremCalType=="1") {
			
			document.all("tdPlan12").style.display = "";
			document.all("tdPlan13").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else {
			
			document.all("tdPlan12").style.display = "none";
			document.all("tdPlan13").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	} else if (cContPlanType=="02") {
		
		if (cPlanType=="02") {//个人账户
		
			document.all("trPlan1").style.display = "";
			
			if (cOccupTypeFlag=="1") {
				
				document.all("trOccupType1").style.display = "";
				document.all("trOccupType2").style.display = "none";
			} else {
				document.all("trOccupType1").style.display = "none";
				document.all("trOccupType2").style.display = "";
			}
			pubChooseOccupFlag(cObj, cOccupTypeFlag);
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		} else {//团体账户
		
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	}
}
/**
 * 选择单多职业类别
 */
function pubChooseOccupFlag(cObj, cQuotFlag) {

	if (cQuotFlag=='1') {
		
		cObj.OccupTypeFlag.value = '1';
		document.all("OccupTypeRadio1").checked = true;
		document.all("trOccupType1").style.display = "";
		document.all("trOccupType2").style.display = "none";
	} else if (cQuotFlag=='2') {
		
		cObj.OccupTypeFlag.value = '2';
		document.all("OccupTypeRadio2").checked = true;
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "";
	} else {
		
		cObj.OccupTypeFlag.value = '';
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
	}
}
/**
 * 方案信息处下拉后处理
 */
function pubPlanAfterCodeSelect(cObj, cCodeType, FieldValue) {
	
	if	(cCodeType=="quotplantype") {//选择方案后处理
		
		if (FieldValue=="00") {
			
			document.all("OccupTypeRadio1").checked = true;
			document.all("OccupTypeFlag").value = "1";
			document.all("trPlan1").style.display = "";
			document.all("trOccupType1").style.display = "";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "";
			document.all("tdPlan9").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else if (FieldValue=="01") {
			
			document.all("OccupTypeRadio1").checked = true;
			document.all("OccupTypeFlag").value = "1";
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		} else if (FieldValue=="02") {
			
			document.all("OccupTypeRadio1").checked = true;
			document.all("OccupTypeFlag").value = "1";
			document.all("trPlan1").style.display = "";
			document.all("trOccupType1").style.display = "";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		} else if (FieldValue=="03") {
			
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	} else if (cCodeType=="engincaltype") {
		
		if (FieldValue=='1') {
			
			document.all("tdPlan12").style.display = "";
			document.all("tdPlan13").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else {
			
			document.all("tdPlan12").style.display = "none";
			document.all("tdPlan13").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	}
}
/**
 * 方案修改前校验
 */
function pubBeforeModifyPlan(cObj, cContPlanType) {

	//不允许方案/保费计算方法间进行修改
	if (cContPlanType=="00" || cContPlanType=="03" || cContPlanType=="02") {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPlanType = cObj.PlanType.value;
		var tMulPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		
		if (tPlanType!=tMulPlanType) {
			alert("修改方案时，不能变更方案类型！");
			return false;
		}
	} else if (cContPlanType=="01") {
		
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPremCalType = cObj.PremCalType.value;
		var tMulPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		if (tPremCalType!=tMulPremCalType) {
			alert("修改方案时，不能变更保费计算方式！");
			return false;
		}
	}
	return true;
}


/**
 * 提交前的校验-工程明细
 */
function checkEnginFactor(cObj) {
	
	var arrAll;
	arrAll = document.all("divEnginFactor").getElementsByTagName("input");

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
