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
 * 动态因子展示-保障等级划分
 */
function showPlanDiv(cFlag) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql15");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var tElementCode;//因子编码
	var tElementName;//因子名称
	var tControlFlag;//是否有录入框
	var tIsSelected;//借用quotno来判断该因子值是否在询价特约表中
	var tOElementValue;//原始值
	var tNElementValue;//询价值
	
	var tDisabledFlag = "";
	if (cFlag=="1") {
		tDisabledFlag = "disabled";
	}
	
	//var tInnerHTML0 = "<table class=common><tr class=common style='display:none'><td class=input></td><td class=input></td><td class=input></td><td class=input></td></tr>";
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>保障层级划分标准<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];//用来判断是否被选中
		tOElementValue = tArr[i][4];//原始值
		tNElementValue = tArr[i][5];//询价值
		
		tInnerHTML1 += "<input type=checkbox id="+ tElementCode + " name="+ tElementCode + " "+ tDisabledFlag;
		
		if (tControlFlag=='1') {//存在录入框
			
			if (tIsSelected=='0') {//询价表中没有保存该因子
			
				tInnerHTML1 += " onclick=\"showHiddenInput('"+ tElementCode +"')\">"+ tElementName;
				tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tControlFlag +"><input style='width:100px' class=common style=\"display: 'none'\" id="+ tElementCode +"Value name="+ tElementCode +"Value value=\""+ tNElementValue +"\" "+ tDisabledFlag +">";	
			} else {//询价中保存了该因子
				
				tInnerHTML1 += " onclick=\"showHiddenInput('"+ tElementCode +"')\" checked>"+ tElementName;
				tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tControlFlag +"><input style='width:100px' class=common id="+ tElementCode +"Value name="+ tElementCode +"Value value=\""+ tNElementValue +"\" "+ tDisabledFlag +">";	
			}
		} else {
			
			if (tIsSelected=='0') {//询价表中没有保存该因子
    	
				tInnerHTML1 += ">"+ tElementName;
			} else {//询价中保存了该因子
    	
				tInnerHTML1 += " checked>"+ tElementName;
			}
			
			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";	
		}
	}
	
	tInnerHTML1 += "</td></tr>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

function showHiddenInput(o) {
	
	if (document.getElementById(o).checked == true) {
		
	   document.getElementById(o+"Value").style.display = "";
	} else {
		document.getElementById(o+"Value").style.display = "none";
	}
}

/**
 * 提交前的校验-等级划分
 */
function checkElements() {
	
	var arrAll;
	arrAll = document.getElementById("divPlanDiv").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		if (obj.type == "checkbox") {

			if(obj.checked) {
				//如果被选中了,取选中的值的隐藏域看是否需要录入

				var tNeedInput = eval("fm2.Hidden"+ obj.name +".value");
      
				if (tNeedInput=='1') {//需要赋值
					
					var tInputValue = eval("fm2."+ obj.name +"Value.value");
					
					if (tInputValue==null || tInputValue=='') {
						alert("\"保障层级划分标准\"已勾选\"其他\"，请录入具体信息！");
						return false;
					} else {
						
						if (tInputValue.length>30) {
							alert("\"保障层级划分标准\"的\"其他\"，所填信息不应超过30字长！");
							return false;
						}
					}
				}
				tConfigCount++;
			}
		}
	}
	if (tConfigCount=="0") {
		alert("请选择保障层级划分标准！");
		return false;
	}

	return true;
}

/**
 * 动态因子展示-销售渠道
 */
function showSaleChnlDiv(cFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrChnl = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var tElementCode;//因子编码
	var tElementName;//因子名称
	var tIsSelected;//是否选中
	var tControlFlag;
	
	var tDisabledFlag = "";
	if (cFlag=="1") {
		tDisabledFlag = "disabled";
	}
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>渠道类型<span style='color: red'>*</span>";
	if (cFlag!="1") {
		tInnerHTML1 +="<input type=checkbox name=CheckAll id=\"CheckAll\" onclick=\"checkAll();\">全选";
	}
	tInnerHTML1 +="</td><td class=input colspan=5>";
	
	for (var i=0; i<tArrChnl.length; i++) {
	
		tElementCode = tArrChnl[i][0];
		tElementName = tArrChnl[i][1];
		tIsSelected = tArrChnl[i][2];
		tControlFlag = tArrChnl[i][3];
		
		tInnerHTML1 += "<input type=checkbox name="+ tElementCode+" "+ tDisabledFlag +" onclick=\"showIfAgencyName();\"";
		if (tIsSelected=='0') {//询价表中没有保存该因子
			tInnerHTML1 += ">"+ tElementName;
		} else {//询价中保存了该因子

			tInnerHTML1 += " checked>"+ tElementName;
		}
		var tFlag1 = tControlFlag.substring(0,1);
		tInnerHTML1 += "<input type=hidden name="+ tElementCode +"Flag value="+ tFlag1 +">";
		
	}
	tInnerHTML1 += "</td></tr>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

function checkAll() {
	
	var arrAll;
	arrAll = document.getElementById("divSaleChnlDiv").getElementsByTagName("input");
	var tConfigCount = 0;
	var tFlag = document.getElementById("CheckAll").checked;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		
		if (obj.type == "checkbox") {

			if (obj.name!="CheckAll") {
				
				if (tFlag) {
					if(obj.checked) {
						
					} else {
						
						obj.checked = true;
					}
				} else {
					if(obj.checked) {
						obj.checked = false;
					} else {
						
					}
				}
			}
		}
	}
	showIfAgencyName();
}
/**
 * 是否展示中介名称
 */
function pubShowAgencyInfoCheck(cObj) {
	
	var tArr = document.all("divSaleChnlDiv").getElementsByTagName("input");
	
	var tFlag = false;//默认不展示中介名称
	for (var i=0; i<tArr.length; i++) {
		
		if (tFlag==false) {
			var tField = tArr[i];
			
			if (tField.type=="checkbox") {//只有该字段为checkbox时,才进行处理
				
				if (tField.name!="CheckAll") {
					
					if (tField.checked) {//选中时,进行取标志值,取到标志值为1时,展示状况说明
						
						var tCheckValue = eval(cObj.name+"."+ tField.name +"Flag.value");
						if (tCheckValue=="1") {
							tFlag = true;
						}	
					}
				}
			}
		}
	}
	
	if (tFlag==true) {
		document.all("divAgencyInfo").style.display = "";
		showAgencyInfo();
	} else {
		document.all("divAgencyInfo").style.display = "none";
	}
}

/**
 * 校验销售渠道是否选中
 */
function checkSaleChnl() {
	
	var arrAll;
	arrAll = document.getElementById("divSaleChnlDiv").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		
		if (obj.type == "checkbox") {

			if (obj.name!="CheckAll") {
				
				if(obj.checked) {
				      
					tConfigCount++;
				}
			}
		}
	}
	if (tConfigCount=="0") {
		alert("请选择销售渠道！");
		return false;
	}

	return true;
}

/**
 * 动态因子展示-缴费方式
 */
function showPayIntvDiv(cFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrPayIntv = easyExecSql(tSQLInfo.getString());
	
	var tElementCode;//因子编码
	var tElementName;//因子名称
	var tIsSelected;//是否选中
	var tPayIntv;//缴费方式
	
	var tDisabledFlag = "";
	if (cFlag=="1") {
		tDisabledFlag = "disabled";
	}
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>缴费方式<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArrPayIntv.length; i++) {
	
		tElementCode = tArrPayIntv[i][0];
		tElementName = tArrPayIntv[i][1];
		tIsSelected = tArrPayIntv[i][2];
		tPayIntv = tArrPayIntv[i][3];
		
		tInnerHTML1 += "<input type=checkbox name="+ tElementCode +" "+ tDisabledFlag;
		if (tIsSelected=='0') {//询价表中没有保存该因子
			tInnerHTML1 += ">"+ tElementName;
		} else {//询价中保存了该因子

			tInnerHTML1 += " checked>"+ tElementName;
		}
	}

	tInnerHTML1 += "</td></tr>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

/**
 * 校验缴费方式是否选中
 */
function checkPayIntv() {
	
	var arrAll;
	arrAll = document.getElementById("divPayIntvDiv").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		if (obj.type == "checkbox") {

			if(obj.checked) {
      
				tConfigCount++;
			}
		}
	}
	if (tConfigCount=="0") {
		alert("请选择缴费方式！");
		return false;
	}

	return true;
}

/**
 * 获取询价非录入环节工作流数量
 */
function getNotInputCount(cQuotNo, cQuotBatNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql29");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	
	var tCountArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCountArr==null || tCountArr[0][0]=="") {
		return "0";
	}
	
	return tCountArr[0][0];
}
