/***************************************************************
 * <p>ProName：LLClaimSpecialCaseInput.js</p>
 * <p>Title：特殊案件录入</p>
 * <p>Description：特殊案件录入</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tQueryFlag = false;
var tQueryResultFlag = false;
var tSQLInfo;

//=======================初始化页面========================
/**
 * 查询受理信息
 */
function queryAcceptInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql1");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
					
		fm.GrpRgtNo.value = tArr[0][0];		
		fm.RgtClass.value = tArr[0][1];
		fm.RgtClassName.value = tArr[0][2];
		
		if (fm.RgtClass.value=="1") {
			$("#appntinfo").hide();
			$("#appntvalueinfo").hide();
//			$("#risklevel").hide();
//			$("#risklevelname").hide();
			fm.AppntNo.value = "";
			fm.AppntName.value = "";	
//			fm.RiskLevel.value = "";
		} else if (fm.RgtClass.value=="2") {
			$("#appntinfo").show();
			$("#appntvalueinfo").show();
//			$("#risklevel").show();
//			$("#risklevelname").show();
			fm.AppntNo.value = tArr[0][3];
			fm.AppntName.value = tArr[0][4];
//			if (tArr[0][13]==null || tArr[0][13]==""){
//				fm.RiskLevel.value = "";
//			}else{
//				fm.RiskLevel.value = tArr[0][13];
//			}
		}
		
		fm.AppDate.value = tArr[0][5];
		fm.AcceptDate.value = tArr[0][6];
		fm.PageNo.value = tArr[0][7];
		fm.AcceptOperator.value = tArr[0][8];
		fm.AcceptCom.value = tArr[0][9];
		fm.SelfAcceptCom.value = tArr[0][9];
		fm.AcceptComName.value = tArr[0][10];
		
		fm.CaseType.value = tArr[0][11];
		fm.CaseTypeName.value = tArr[0][12];
	} else {
		alert("赔案受理信息查询失败！");
		return false;		
	}	
}
/**
 * 查询已维护的客户信息列表
 * 特殊立案一次只允许申请一个出险人
 * 若历史案件号录入，则查询历史赔案出险人信息
 */
function queryCustomerInfo() {
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql4");
	
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("出险人信息不存在!");
		return false;
	}else {
		//先清空原有的数据
		initCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];	
		fm.Birthday.value = tArr[0][2];
		fm.EmployeNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];
		
		fm.ApplyRemarks.value = tArr[0][9];
		fm.RgtNo.value = tArr[0][10];
		
		if (fm.CaseType.value == "12") {
			//fm.ErrorStation.style.display="";
			//fm.ErrorStationName.style.display="";
			document.getElementById("ErrorStation").style.display="";
			document.getElementById("ErrorStationInfo").style.display="";
			fm.ErrorStation.value = tArr[0][11];
			fm.ErrorStationName.value = tArr[0][12];			
		} else {
			//fm.ErrorStation.style.display="none";
			//fm.ErrorStationName.style.display="none";
			document.getElementById("ErrorStation").style.display="none";
			document.getElementById("ErrorStationInfo").style.display="none";
			fm.ErrorStation.value = "";
			fm.ErrorStationName.value = "";						
		}		
		
		fm.HistoryRgtNo.value = tArr[0][13];
		
		if(fm.HistoryRgtNo.value==null || fm.HistoryRgtNo.value=="") {
			fm.ClaimFlag.value = "0";
			fm.ClaimFlagName.value = "否";
			$("#HistoryRgtNo").hide();
			$("#HistoryRgtNoInfo").hide();	
		} else {
			fm.ClaimFlag.value = "1";
			fm.ClaimFlagName.value = "是";			
			$("#HistoryRgtNo").show();
			$("#HistoryRgtNoInfo").show();
		}
		fm.SelfAppntNo.value = tArr[0][14];
		fm.SelfAppntName.value = tArr[0][15];
		fm.TelPhone.value = tArr[0][16];
	}
}

//=======================处理受理信息========================

//按键
function QueryOnKeyDown(tObject) {

	var keycode = event.keyCode;
	//回车的ascii码是13
	if(keycode!="13" && keycode!="9") {
		return;
	}	
	var tObjectName = tObject.name;
	var tObjectValue = tObject.value;
	if (tObjectName=="AppntName") {
		
		var tContType = fm.RgtClass.value;
		if (tContType==null || tContType=="") {
			alert("请先选择保单类型！");
			fm.RgtClass.className = "warnno";
			fm.RgtClass.focus();
			return false;
		}		
				
		var tAcceptCom = fm.AcceptCom.value;
		if (tAcceptCom==null || tAcceptCom=="") {
			alert("请先选择受理机构！");
			fm.AcceptCom.className = "warnno";
			fm.AcceptCom.focus();
			return false;
		} else {
			
			if (!checkAcceptCom()) {
				return false;
			}
		}
		
		var tAppntName = tObjectValue;		
		
		if (tAppntName==null || tAppntName=="") {
			alert("请录入投保人名称！");
			tObject.focus();
			return false;
		}		
		
		//个单时查询个单投保人表
		if (fm.RgtClass.value=="1") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
			tSQLInfo.setSqlId("LLClaimSpecialCaseSql6");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(mManageCom);
			tSQLInfo.addSubPara(tAcceptCom);
		} else if (fm.RgtClass.value=="2") {
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
			tSQLInfo.setSqlId("LLClaimSpecialCaseSql");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(tAcceptCom);
						
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
			fm.AppntType.value = "";				
			fm.AppntNo.value = "";
			fm.AppntName.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AppntType.value = tArr[0][0];				
				fm.AppntNo.value = tArr[0][1];
				fm.AppntName.value = tArr[0][2];
//				showRiskLevel();
			} else {
				showAppntList();			
			}
		}		
	}
if (tObjectName=="BankCodeName") {
		
		var tBankName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimOutCaseSql");
		tSQLInfo.setSqlId("LLClaimOutCaseSql10");
		tSQLInfo.addSubPara(tBankName);
		tSQLInfo.addSubPara(mManageCom);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
			fm.BankCode.value = "";
			fm.BankCodeName.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.BankCode.value = tArr[0][0];
				fm.BankCodeName.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and headbankname like #%"+ tBankName +"%# and managecom like #"+mManageCom+"%#";
				showCodeList('headbank', [fm.BankCode,fm.BankCodeName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}	
	
}
/**
 * 打开查询的系统团体投保人选择页面
 */
function showAppntList() {
	
	var strUrl="./LLClaimQueryAppntMain.jsp?AppntName="+fm.AppntName.value+"&ContType="+fm.RgtClass.value+"&AcceptCom="+fm.AcceptCom.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"用户信息查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * 获得查询的系统投保人
 */
function afterQueryAppnt(tQueryResult) {
	
	fm.all('AppntType').value = tQueryResult[0];
	fm.all('AppntNo').value = tQueryResult[1];
	fm.all('AppntName').value = tQueryResult[2];
	self.focus();
//	showRiskLevel();
}
/**
 * 申请交接流转号
 */
function applyPageNo() {
	
	var strUrl="./LLClaimHandAppMain.jsp";
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"交接流转号查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 返回交接流转号
 */
function afterAppPageNo(tSelectResult) {
	
	fm.all('PageNo').value = "";
	fm.all('PageNo').value = tSelectResult[0];
}
/**
 * 查询投保人下所有的个人赔案信息
 */
function queryPastCaseInfo() {
	
	if(fm.ClaimFlag.value=="0") {
		fm.HistoryRgtNo.value = "";
		return;
	}
	var tHistoryRgtNo = fm.HistoryRgtNo.value;
	if (tHistoryRgtNo==null || tHistoryRgtNo=="") {
		alert("请先录入历史赔案号");
		return false;
	}
	if (fm.GrpRgtNo.value=="01") {
		var tAppntNo = fm.AppntNo.value;
		if (tAppntNo==null || tAppntNo=="") {
			alert("请先录入投保人信息");
			return false;
		}		
	}	
	/* 暂时不做，回头修改
	var strUrl="./LLClaimQueryPastCaseMain.jsp?AppntNo="+fm.AppntNo.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"投保人历史赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	*/
	//根据获得的历史赔案号查询出险人明细，并填充页面
	if (!queryOldCustomerInfo()) {
		return false;
	}
}
/**
 * 根据案件号查询历史赔案的出险人信息
 */
function queryOldCustomerInfo() {
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql7");
	
	tSQLInfo.addSubPara(fm.HistoryRgtNo.value);	
	
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("该历史赔案下出险人不存在，请检查!");
		return false;
	}else {
		//先清空原有的数据
		initCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];	
		fm.Birthday.value = tArr[0][2];
		fm.EmployeNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];
		fm.SelfAppntNo.value = tArr[0][9];
		fm.SelfAppntName.value = tArr[0][10];
	}
}
/**
 * 保存
 */
function saveSpecialCase() {
	
	if(!checkSaveInfo()) {
		return false;
	}		
	fm.Operate.value="CASEINSERT";
	submitForm();	
}

function checkSaveInfo() {
	
	//校验批次信息
	if(!notNull(fm.RgtClass,"保单类型")){return false;};
	var tRgtClass = fm.RgtClass.value;
	//团单下投保人信息必录，否则隐藏
	if (tRgtClass=="01") {
		if(!notNull(fm.AppntNo,"投保人名称编码")){return false;};
		if(!notNull(fm.AppntName,"投保人名称")){return false;};
	}
	if(!notNull(fm.AppDate,"批次申请日期")){return false;};
	if(!notNull(fm.AcceptCom,"受理机构")){return false;};
	if(!notNull(fm.PageNo,"交接流转号")){return false;};
	
	//校验受理机构
	if (!checkAcceptCom()) {
		return false;
	}	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql9");
	tSQLInfo.addSubPara(fm.PageNo.value);			
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length!=1) {
		alert("您录入的交接流转号不存在，请重新录入！");
		return false;
	}	
	if(!notNull(fm.CaseType,"案件类型")){return false;};
	if(!notNull(fm.ClaimFlag,"是否已申请理赔")){return false;};
	if (fm.ClaimFlag.vlaue=="1") {
		if(!notNull(fm.HistoryRgtNo,"原案件号")){return false;};
	}	
	//校验出险人信息
	var tRgtClass = fm.RgtClass.value;
	
	if (fm.CustomerNo.value==null || fm.CustomerNo.value=="") {
		
		if(!notNull(fm.CustomerName,"[出险人姓名]")){return false;};
		if(!notNull(fm.Birthday,"[出险人生日]")){return false;};
		if(!notNull(fm.IDNo,"[证件号码]")){return false;};
		if(!notNull(fm.IDType,"[证件类型]")){return false;};
		if(!notNull(fm.Gender,"[性别]")){return false;};
	}
	var tTelphone = fm.TelPhone.value;
	//alert(fm.TelPhone.value);
	queryCustomer();
	if(!tQueryResultFlag){
		alert("不存在这样的出险人，请检查");
		return false;
	}
	fm.TelPhone.value = tTelphone;
	if (fm.CaseType.value == "12") {
		if(!notNull(fm.ErrorStation,"差错责任岗")){return false;};
	}
	if(!notNull(fm.ApplyRemarks,"申请原因")){return false;};
	
	//校验被保人状态是否有效即是否存在有效保单
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql3");
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length==0 ) {
		
		alert("您选择的客户不存在有效保单，请检查！");
		return false;
	}
	if(fm.ClaimFlag.value=="1"){
		if(!notNull(fm.HistoryRgtNo,"原案件号")){return false;};            
		}
//校验该出险人承保的保单是否已经在本次出险中---针对于换人情况
	
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
//	tSQLInfo.setSqlId("LLClaimCommonQuerySql3");
//	tSQLInfo.addSubPara(mGrpRegisterNo);
//	tSQLInfo.addSubPara(fm.CustomerNo.value);	
	
//	var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	if (tExistsArr1!=null && tExistsArr1.length>0 ) {
		
//		alert("您选择的客户所承包的保单已经存在于本次赔案中,请选择其他客户！");
//		return false;
//	}	

/*
//校验出险人是否存在未确认的保全
	
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
//	tSQLInfo.setSqlId("LLClaimCommonQuerySql3");
//	tSQLInfo.addSubPara(mGrpRegisterNo);
//	tSQLInfo.addSubPara(fm.CustomerNo.value);		
//  var tSQL = "select 1 from LPEdorItem where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and edorstate!='0'";
//	var arr = easyExecSql(tSQL);
//	if (arr!=null && arr.length>0) {
		
//			alert("该出险人有未确认保全，请保全确认后再理赔！");
//			return false;
//	}	
	
	//add by lixf----特殊校验，对于保全扫描录入的情况，未录入完毕确认前，其中业务表中是有数据的，但是保全明细表中无数据
	//故以上校验无法校验出该被保险人是否存在保全,且只有保全新增才会出现这种情况，故此校验必须在上面的校验之后------------start	
  var tCheckSQL = "select 1 from lccont where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and state is null ";
	var tCheckArr = easyExecSql(tCheckSQL);
	if (tCheckArr!=null && tCheckArr.length>0) {
		
			alert("该出险人有未确认的扫描保全，请保全确认后再理赔！");
			return false;
	}	
	//---------------------------end
*/
	
/*	
	//2.万能险校验是否死亡
	var tWanSql = "select 1 from lcgrppol a ,lmriskapp b where a.riskcode=b.riskcode and b.risktype3='4' and a.grpcontno='"+ tGrpContNo +"'" ;
	var tIsWan = easyExecSql(tWanSql);
	if(tIsWan!=null && tIsWan.length!=0){
			
		var tDeadSql = " select 1 from lcinsured where insuredno='"+ tCustomerNo +"' and insuredstat='9' ";
		var tIsDead = easyExecSql(tDeadSql);
		if(tIsDead!=null && tIsDead.length!=0){
			alert("出险人为万能险，出险人不能选择已经死亡的被保险人");
			resetInfo();
			return false;
		}
	}
*/	
/*
	//3.校验是否因减人、换人而锁定
	var tIsLockSql = " select insuredstat from lcinsured where insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' ";
	var tIsLock = easyExecSql(tIsLockSql);
	if (tIsLock!=null && tIsLock[0][0]=="8") {//insuredstat=8代表离职，此时才会判断是否锁定
		
		var tIsExist = "select State from LLRegisterLock where GrpContNo='"+ tGrpContNo +"' and ContNo='"+ tContNo +"' and InsuredNo='"+ tCustomerNo +"' order by applydate, applytime desc";
		var tUnlock = easyExecSql(tIsExist);
		if (tUnlock==null || tUnlock.length<=0) {
			alert("此人已做减人处理或被替换处理，已被锁定！");
			resetInfo();
			return false;	
		}else if(tUnlock[0][0] == "1" || tUnlock[0][0] == "3") {
			alert("此人已做减人处理或被替换处理，已被锁定！");
			resetInfo();
	  	return false;
		}		
	}
*/
	return true;		
}

/**
 * 根据录入信息选择系统被保人信息，并填充页面
 * 被保险人检索条件：姓名+出生日期、姓名+员工号、证件号码，三种条件任选一种
 */
function selectUser(tType) {
	
	var keycode = event.keyCode;
	//回车的ascii码是13
	if(keycode!="13" && keycode!="9") {
		return;
	} else {
		tQueryFlag = false;
	}
	if (fm.RgtClass.value=="2") {
		if (fm.AppntNo.value==null || fm.AppntNo.value=="") {
			alert("请先选择投保人");
			fm.AppntName.focus();
			fm.AppntName.style.background = "#ffb900";
			return false;
		}		
	}	
	
	//校验受理机构
	if (!checkAcceptCom()) {
		return false;
	}	
	
	var tCustomerName = fm.CustomerName.value;
	var tBirthday = fm.Birthday.value;
	var tEmployeNo = fm.EmployeNo.value;
	var tIDNo = fm.IDNo.value;
	if (tCustomerName!=null && tCustomerName!="") {

		if (tBirthday!=null && tBirthday!="") {
			//queryCustomer(1);
		} else {
			
			if (tEmployeNo!=null && tEmployeNo!="") {
				//queryCustomer(2);
			} else {
				
				if (tIDNo!=null && tIDNo!="") {
						//queryCustomer(3);
				} else {
					return;
				}				
			}
		}	
	} else {
			if (tIDNo!=null && tIDNo!="") {
					//queryCustomer(3);
			} else {
				alert("被保险人检索条件：姓名+出生日期、姓名+员工号、证件号码，三种条件任选至少满足一种!");
				return false;
			}		
	}
	queryCustomer();
	if (!tQueryResultFlag) {

		if(confirm("系统中不存在该出险人，是否进行未受理客户录入?")) {
			
			noAcceptInfo();
		} else {
			return false;
		}
	}
}
function queryCustomer(tFlag) {	
	
	tQueryFlag = true;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql2");
	
	//个单的情况下需重置查询
	var tContType = fm.RgtClass.value;
	if (tContType!=null && tContType=="02") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
		tSQLInfo.setSqlId("LLClaimSpecialCaseSql8");
	}	
		
	if (tFlag==1) {

		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	} else if (tFlag==2) {
			
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara(fm.EmployeNo.value);
		tSQLInfo.addSubPara("");				
	} else if (tFlag==3) {
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara("");			
		tSQLInfo.addSubPara(fm.IDNo.value);		
	} else {
		
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);					
		tSQLInfo.addSubPara(fm.EmployeNo.value);			
		tSQLInfo.addSubPara(fm.IDNo.value);			
	}
	tSQLInfo.addSubPara(fm.AppntNo.value);
	if (fm.CustomerNo.value !=null && fm.CustomerNo.value!="") {
		tSQLInfo.addSubPara(fm.CustomerNo.value);
	} else {
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.SelfAcceptCom.value);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length==0) {
		
		return false;
	} else {
		
		tQueryResultFlag = true;
		if (tArr.length==1) {
			
			fm.CustomerNo.value = tArr[0][0];
			fm.CustomerName.value = tArr[0][1];
			fm.Birthday.value = tArr[0][2];
			fm.EmployeNo.value = tArr[0][3];
			fm.IDNo.value = tArr[0][6];
			fm.IDType.value = tArr[0][4];
			fm.IDTypeName.value = tArr[0][5];			
			fm.Gender.value = tArr[0][7];
			fm.GenderName.value = tArr[0][8];
			fm.SelfAppntNo.value = tArr[0][9];
			fm.SelfAppntName.value = tArr[0][10];
			fm.TelPhone.value = tArr[0][11];
			return true;
		} else {
			var tOpenFlag = false;
			for (var i=0;i<tArr.length;i++) {
													
				if (tArr[i][0]!=tArr[0][0] || tArr[i][9]!=tArr[0][9]) {
					tOpenFlag = true;
					break;
				}
			}
			if (tOpenFlag) {
				showCustomerList();
			} else {
				
				fm.CustomerNo.value = tArr[0][0];
				fm.CustomerName.value = tArr[0][1];
				fm.Birthday.value = tArr[0][2];
				fm.EmployeNo.value = tArr[0][3];
				fm.IDNo.value = tArr[0][6];
				fm.IDType.value = tArr[0][4];
				fm.IDTypeName.value = tArr[0][5];			
				fm.Gender.value = tArr[0][7];
				fm.GenderName.value = tArr[0][8];
				fm.SelfAppntNo.value = tArr[0][9];
				fm.SelfAppntName.value = tArr[0][10];
				fm.TelPhone.value = tArr[0][11];
			}	
		}
	}	
}
//展示客户列表--存在同一投保人下同名同生日或同名同员工号或同一证件号码
function showCustomerList() {
	
	var strUrl="./LLClaimQueryMain.jsp?AppntNo="+fm.AppntNo.value+"&CustomerName="+fm.CustomerName.value+"&Birthday="+fm.Birthday.value+"&EmpNo="+fm.EmployeNo.value+"&IDNo="+fm.IDNo.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"用户信息查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * 获得查询的系统被保人
 */
function afterQueryCustomer(tQueryResult) {
	
	fm.all('SelfAppntNo').value = tQueryResult[0];
	fm.all('SelfAppntName').value = tQueryResult[1];
	fm.CustomerNo.value = tQueryResult[2];
	fm.CustomerName.value = tQueryResult[3];
	fm.Gender.value = tQueryResult[4];
	fm.GenderName.value = tQueryResult[5];		
	fm.Birthday.value = tQueryResult[6];	
	fm.EmployeNo.value = tQueryResult[7];
	fm.IDType.value = tQueryResult[8];
	fm.IDTypeName.value = tQueryResult[9];	
	fm.IDNo.value = tQueryResult[10];	
	fm.TelPhone.value = tQueryResult[14];

	self.focus();
}
/**
 * 立案登记
 */
function caseAccept() {
	
	//mode :页面类型，0-可操作；1-查看；2-特殊立案
	
	var strUrl="./LLClaimSpecialCaseMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&Mode=2";
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"立案登记",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}

/**
 * 立案完毕
 */
function caseOver() {
	
	if (fm.GrpRgtNo.value == null || fm.GrpRgtNo.value == "") {
		alert("请先保存受理信息！");
		return false;
	}
	//通融、申诉案件且未申请理赔的必须进行立案登记
	var tCaseType = fm.CaseType.value;
	var tClaimFlag = fm.ClaimFlag.value;
	if (tCaseType!=null && (tCaseType=="13" || tCaseType=="11") && tClaimFlag!=null && tClaimFlag=="0") {
		
		if(!checkCaseOver()) {
			return false;
		}		
	}		
	fm.Operate.value="CASEOVER";
	submitForm();		
}

/**
 * 立案完毕前校验
 */
function checkCaseOver() {
	
	//校验是否录入了事件信息，以校验是否进行了立案登记
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql5");
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length==0 ) {
		
		alert("请先进行立案登记！");
		return false;
	}
	//校验是否存在出险人
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql18");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistsArr==null || tExistsArr.length<=0 ) {
		
		alert("请为该理赔批次下至少录入一个已受理出险人！");
		return false;
	} else {
		
		for (var i=0;i<tExistsArr.length;i++) {
			
			var tRgtNo = tExistsArr[i][0];
			var tCustomerNo = tExistsArr[i][1];
			var tCustomerName = tExistsArr[i][2];
			//校验是已受理出险人是否录入了事件信息
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql19");
			tSQLInfo.addSubPara(tRgtNo);
			tSQLInfo.addSubPara(tCustomerNo);
			var tCaseArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tCaseArr==null || tCaseArr.length<=0) {
				alert("出险人'"+tCustomerName+"'未录入事件信息，请检查！");
				return false;				
			}
			
			//校验个人事件是否存在账单生成的且未修改事件的
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql21");
			tSQLInfo.addSubPara(tRgtNo);
			tSQLInfo.addSubPara(tCustomerNo);
			var tCaseArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tCaseArr!=null && tCaseArr.length>0) {
				alert("出险人'"+tCustomerName+"'存在账单生成的事件，请修改该事件信息以补全事件信息，请检查！");
				return false;				
			}			
		}
	}
	return true;		
}

/**
 * 返回
 */
function goBack() {
	
	if (mMode==1) {
		top.close();
	} else {
		window.location.href="./LLClaimSpecialAppInput.jsp";
	}
}


/**
 * 提交数据
 */
function submitForm() {
	
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
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, tGrpRgtNo, tRgtNo) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		if (fm.Operate.value=="CASEINSERT") {
			
			mGrpRegisterNo = tGrpRgtNo;
			window.location.href="LLClaimSpecialCaseInput.jsp?GrpRgtNo="+tGrpRgtNo;
		} else if (fm.Operate.value=="CASEOVER") {
			goBack();
		}		
	}	
}
//====================系统===================//
/**
 * 校验非空要素，并聚焦
 */
function notNull(tObject,tName) {

	if (tObject!=null && tObject.value=="") {
		alert(tName+"不可为空，请录入！");

		tObject.focus();
		tObject.style.background="#ffb900";
		return false;
	} else if (tObject==null) {
		return false;
	}
	return true;
}
/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == 'casetype') {
		
		//案件类型选择
		if(fm.CaseType.value == "11" || fm.CaseType.value=="12"){
			
			fm.ClaimFlag.value="1";
			fm.ClaimFlagName.value="是";
			
			$("#HistoryRgtNo").show();
			$("#HistoryRgtNoInfo").show();
							
			document.getElementById("ClaimFlag").setAttribute('readOnly',true);
			document.getElementById("ClaimFlagName").setAttribute('readOnly',true);
			

			if (fm.CaseType.value == "12") {
				
				$("#ErrorStation").show();
				$("#ErrorStationInfo").show();
			} else {
				
				fm.ErrorStation.value="";
				fm.ErrorStationName.value = "";
				$("#ErrorStation").hide();
				$("#ErrorStationInfo").hide();
			}			
			document.getElementById("HistoryRgtNo").style.display="";
			document.getElementById("HistoryRgtNoInfo").style.display="";
		} else {
			fm.ClaimFlag.value="";
			fm.ClaimFlagName.value="";													
			
			$("#ErrorStation").hide();
			$("#ErrorStationInfo").hide();			
			document.getElementById("HistoryRgtNo").style.display="none";
			document.getElementById("HistoryRgtNoInfo").style.display="none";
		}
	} else if(cCodeName == 'trueflag') {
		
		//案件类型选择
		if(fm.ClaimFlag.value == "0"){
			
			if (fm.CaseType.value=="12") {
				
				fm.ClaimFlag.value = "1";
				fm.ClaimFlagName.value = "是";
				alert("差错案件必须存在历史赔案！");
				return false;
			}
			$("#HistoryRgtNo").hide();
			$("#HistoryRgtNoInfo").hide();	
			//fm.HistoryRgtNo.value="";
		} else {
			$("#HistoryRgtNo").show();
			$("#HistoryRgtNoInfo").show();
		}
	} else if (cCodeName == "conttype"){
			
			var tRgtClass = fm.RgtClass.value;
			if (tRgtClass=="02") {
				
				fm.AppntNo.value = "";
				fm.AppntName.value = "";
//				fm.RiskLevel.value = "";
				$("#appntinfo").hide();
				$("#appntvalueinfo").hide();
//				$("#risklevel").hide();
//				$("#risklevelname").hide();
			} else {
				$("#appntinfo").show();
				$("#appntvalueinfo").show();	
//				$("#risklevel").show();
//				$("#risklevelname").show();
			}
		}	
				
}
//展示提示信息
function showWarnInfo() {
		
	alert('请录入[投保人名称]后回车操作(支持模糊查询)！');
	fm.AppntName.focus();
	fm.AppntName.style.background = "#ffb900";
}
//校验受理机构
function checkAcceptCom() {

	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql10");
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tArr = null;
	tArr = easyExecSql(tSQLInfo.getString());
	//判断是否查询成功
	if (tArr==null || tArr.length==0) {
		alert("未查询到登录机构的机构级别!");
		return false;
	} else {
		tComGrade = tArr[0][0];
	}
	if (tComGrade==null || tComGrade=="01" || tComGrade=="02") {
		
		alert("受理机构不能是总公司或分公司，请下拉选择！");
		fm.AcceptCom.focus();
		fm.AcceptCom.className = "warnno";
		return false;
	}
	return true;
}


/**
 * 客户风险等级展示
 */
//function showRiskLevel(){
//	
//	var tAppntNo = fm.AppntNo.value;		
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
//	tSQLInfo.setSqlId("LLClaimCommonQuerySql27");
//	tSQLInfo.addSubPara(tAppntNo);
//	var tResult = easyExecSql(tSQLInfo.getString());
//	if(tResult==null||tResult==""){
//		fm.RiskLevel.value = "";
//	}else {
//		fm.RiskLevel.value = tResult;
//	}
//}