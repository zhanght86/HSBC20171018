/***************************************************************
 * <p>ProName：LLClaimReportInput.js</p>
 * <p>Title：报案录入界面</p>
 * <p>Description：报案录入界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();

var tQueryFlag = false;
var tQueryResultFlag = false;
var tMajorFlag;//标识是2-分公司回复还是3-总公司回复

var tPageNo = 1;//记录查询页码
var tIndexNo = 1;//记录查询序号

var submit = 0;//提交次数

/**
 * 初始化页面展示信息
 */
function initMajorInfo() {
		
		fm.RptNo.value = mRptNo;
		fm.InputOperator.value = "";
		fm.InputDate.value = "";
		fm.InputCom.value = "";
		fm.InputComName.value = "";
		fm.InputRemarks.value = "";
		
		var tArr;
		
		//查询是否存在已录入的上报信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql9");
		tSQLInfo.addSubPara(mRptNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//判断是否查询成功
		if (tArr==null || tArr.length==0) {
			alert("该报案未进行重大案件上报，请确认！");
			return;
		} else {
			fm.InputOperator.value = tArr[0][0];
			fm.InputDate.value = tArr[0][1];
			fm.InputCom.value = tArr[0][2];
			fm.InputComName.value = tArr[0][3];
			fm.InputRemarks.value = tArr[0][4];					
		}
		
		//校验录入机构级别
		var tComGrade;
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql16");
		tSQLInfo.addSubPara(mManageCom);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//判断是否查询成功
		if (tArr==null || tArr.length==0) {
			alert("未查询到登录机构的机构级别!");
			return false;
		} else {
			tComGrade = tArr[0][0];
		}		
		if (tComGrade!=null && tComGrade!="01" && tComGrade!="02") {
			return false;
		}
		
		//查询是否存在已录入的分公司回复信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql10");
		tSQLInfo.addSubPara(mRptNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//判断是否查询成功
		if (tArr==null || tArr.length==0) {

			if (tComGrade=="02") {
				$("#divReplyInfo").css("display","");
				$("#divReplyInfo1").css("display","none");
				fm.RepInputOperator.value = mOperator;
				fm.RepInputDate.value = mCurrentDate;
				fm.RepInputCom.value = mManageCom;
				fm.RepInputComName.value = "";
				fm.RepInputRemarks.value = "";
				tMajorFlag=2;				
			}
		} else {
			
			$("#divReplyInfo").css("display","");
			fm.RepInputOperator.value = tArr[0][0];
			fm.RepInputDate.value = tArr[0][1];
			fm.RepInputCom.value = tArr[0][2];
			fm.RepInputComName.value = tArr[0][3];
			fm.RepInputRemarks.value = tArr[0][4];
			tMajorFlag=3;		
		}
		
		//查询是否存在已录入的总公司回复信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql11");
		tSQLInfo.addSubPara(mRptNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//判断是否查询成功
		if (tArr==null || tArr.length==0) {
			
			if (tComGrade=="01") {
				
				$("#divReplyInfo1").css("display","");
				fm.RepInputOperator1.value = mOperator;
				fm.RepInputDate1.value = mCurrentDate;
				fm.RepInputCom1.value = mManageCom;
				fm.RepInputComName1.value = "";
				fm.RepInputRemarks1.value = "";	
				tMajorFlag=3;					
			}
		} else {
						
			$("#divReplyInfo1").css("display","");						
			fm.RepInputOperator1.value = tArr[0][0];
			fm.RepInputDate1.value = tArr[0][1];
			fm.RepInputCom1.value = tArr[0][2];
			fm.RepInputComName1.value = tArr[0][3];
			fm.RepInputRemarks1.value = tArr[0][4];
			tMajorFlag=3;		
		}			
}

/**
 * 报案保存
 */
function saveReport() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	var tRgtClass = fm.RgtClass.value;
	if (tRgtClass=="01") {
		if(!notNull(fm.AppntNo,"投保人名称")){return false;};
	}
	//校验报案机构
	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql16");
	tSQLInfo.addSubPara(fm.RptCom.value);
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
		
		alert("报案机构不能是总公司或分公司，请下拉选择！");
		fm.RptCom.focus();
		fm.RptCom.className = "warnno";
		return false;
	}	
	
	
	var tRptDate = fm.RptDate.value;
	if (dateDiff(tRptDate,mCurrentDate,'D')<0) {	
		
		alert("报案日期不能晚于当前日期！");
		return false;
	}
	
	fm.Operate.value="REPORTINSERT";
	submitForm();
}

/**
 * 报案删除
 */
function deleteReport() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		alert("请先保存!");
		return false;
	}
	if (confirm("确定删除该报案！")) {
		
		fm.Operate.value="REPORTDELETE";
		submitForm();		
	}	
}


/**
 * 查询报案信息
 */
function queryReportInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql");
	tSQLInfo.addSubPara(mRptNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
					
		fm.RptNo.value = tArr[0][0];
		fm.RgtClass.value = tArr[0][1];
		fm.RgtClassName.value = tArr[0][2];
		if (fm.RgtClass.value=="1") {
			$("#appntinfo").hide();
			$("#appntvalueinfo").hide();
			fm.AppntNo.value = "";
			fm.AppntName.value = "";						
		} else if (fm.RgtClass.value=="2") {
			$("#appntinfo").show();
			$("#appntvalueinfo").show();
			fm.AppntNo.value = tArr[0][3];
			fm.AppntName.value = tArr[0][4];					
		}	
		fm.RptName.value = tArr[0][5];
		fm.RptPhone.value = tArr[0][6];
		fm.RptMode.value = tArr[0][7];
		fm.RptModeName.value = tArr[0][8];
		fm.Relation.value = tArr[0][9];
		fm.RelationName.value = tArr[0][10];
		fm.RptDate.value = tArr[0][11];
		fm.RgtFlag.value = tArr[0][12];
		fm.RgtFlagName.value = tArr[0][13];
		fm.RptInputOperator.value = tArr[0][14];
		fm.RptInputDate.value = tArr[0][15];
		fm.RptCom.value = tArr[0][16];
		fm.SelfRptCom.value = tArr[0][16];
		fm.RptComName.value = tArr[0][17];
		fm.RptConfOperator.value = tArr[0][18];
		fm.RptConfDate.value = tArr[0][19];
	} else {
		return false;		
	}
}
/**
 * 查询报案出险人信息
 */
function queryReportCustomerInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql1");
	tSQLInfo.addSubPara(fm.RptNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2,1);
	

}
/**
 * 查询选中的出险人信息，报案默认选中第一条
 * 报案只允许存在一个出险人
 */
function showSelectCustomerInfo() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		return false;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	fm.CustomerNo.value = tCustomerNo;
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql2");
	tSQLInfo.addSubPara(mRptNo);
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
		
		initReportCustomerInfo();
		disableCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];		
		fm.Birthday.value = tArr[0][2];
		fm.EmpNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];		
		fm.CustomerRemarks.value = tArr[0][9];
		fm.SelfAppntNo.value = tArr[0][10];
		fm.SelfAppntName.value = tArr[0][11];
		fm.SubRptNo.value = tArr[0][12];
		queryReportCaseInfo();
			
	} else {
		return false;		
	}	
}
/*=================================客户操作===========================*/
/**
 * 新增客户
 */
function addCustomer() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("请先保存报案信息");
		return false;;
	}	
	
	fm.Operate.value="CUSTOMERINSERT";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * 校验客户信息--是否满足出险条件
 */
function checkCustomerInfo() {			
	
	//校验报案下只能有1个出险人
	if (fm.Operate.value=="CUSTOMERINSERT") {
						
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql3");
		tSQLInfo.addSubPara(fm.RptNo.value);
		
		var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistsArr!=null && tExistsArr[0][0]==1 ) {
			
			alert("一次报案下只能有1个出险人！");
			return false;
		}		
	}
	
	if (fm.CustomerNo.value==null || fm.CustomerNo.value=="") {
		
		if(!notNull(fm.CustomerName,"[出险人姓名]")){return false;};
		if(!notNull(fm.Birthday,"[出险人生日]")){return false;};
		if(!notNull(fm.IDNo,"[证件号码]")){return false;};
		if(!notNull(fm.IDType,"[证件类型]")){return false;};
		if(!notNull(fm.Gender,"[性别]")){return false;};
	}
	
	//校验根据录入的出险人信息是否能够获得被保人
	queryCustomer();
	if (!tQueryResultFlag) {

		alert("系统中不存在该出险人，请重新录入！");
		return false;
	}
	
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
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("请先保存报案信息");
		return false;;
	}
		
	var tCustomerName = fm.CustomerName.value;
	var tBirthday = fm.Birthday.value;
	var tEmployeNo = fm.EmpNo.value;
	var tIDNo = fm.IDNo.value;
	if (tCustomerName!=null && tCustomerName!="") {

		if (tBirthday!=null && tBirthday!="") {
			
			if (dateDiff(tBirthday,mCurrentDate,'D')<0) {	
				
				alert("出生日期不能晚于当前日期！");
				return false;
			}
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

		alert("系统中不存在该出险人，请重新录入！");
		return false;
	}
}
/**
 * 根据录入信息选择系统被保人信息，并填充页面
 * 被保险人检索条件：姓名+出生日期、姓名+员工号、证件号码，三种条件任选一种
 * tFlag 定义查询优先级，暂时不用--等待确认
 */
function queryCustomer(tFlag) {
		
	tQueryResultFlag = false;
	tQueryFlag = true;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql4");
	tSQLInfo.addSubPara(fm.RptNo.value);
	
	//个单的情况下需重置查询
	var tContType = fm.RgtClass.value;
	if (tContType!=null && tContType=="02") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql15");
		tSQLInfo.addSubPara(fm.AppntNo.value);
	}	
				
	if (tFlag==1) {

		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	} else if (tFlag==2) {
			
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara(fm.EmpNo.value);
		tSQLInfo.addSubPara("");				
	} else if (tFlag==3) {
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara("");			
		tSQLInfo.addSubPara(fm.IDNo.value);		
	} else {
		
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);					
		tSQLInfo.addSubPara(fm.EmpNo.value);			
		tSQLInfo.addSubPara(fm.IDNo.value);			
	}
	if (fm.CustomerNo.value !=null && fm.CustomerNo.value!="") {
		tSQLInfo.addSubPara(fm.CustomerNo.value);
	} else {
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.SelfRptCom.value);	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length==0) {
		
		return false;
	} else {
		
		tQueryResultFlag = true;
		if (tArr.length==1) {
			
			fm.CustomerNo.value = tArr[0][0];
			fm.CustomerName.value = tArr[0][1];
			fm.Birthday.value = tArr[0][2];
			fm.EmpNo.value = tArr[0][3];
			fm.IDNo.value = tArr[0][6];
			fm.IDType.value = tArr[0][4];
			fm.IDTypeName.value = tArr[0][5];			
			fm.Gender.value = tArr[0][7];
			fm.GenderName.value = tArr[0][8];
			fm.SelfAppntNo.value = tArr[0][9];
			fm.SelfAppntName.value = tArr[0][10];
		} else {
			alert("根据录入信息可查询到多个被保人，请手动选择!");
			showCustomerList();			
		}
	}	
}
//展示客户列表--存在同一投保人下同名同生日或同名同员工号或同一证件号码
function showCustomerList() {
	
	var strUrl="./LLClaimQueryMain.jsp?AppntNo="+fm.AppntNo.value+"&CustomerName="+fm.CustomerName.value+"&Birthday="+fm.Birthday.value+"&EmpNo="+fm.EmpNo.value+"&IDNo="+fm.IDNo.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"用户信息查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}
/**
 * 修改客户
 */
function modifyCustomer() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("请先保存报案信息");
		return false;
	}
		
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条该出险人信息");
		return false;
	}
	var tCustomerNoSel = CustomerListGrid.getRowColData(tSelNo,1);
	var tCustomerNo = fm.CustomerNo.value;
	if (tCustomerNo!=null && tCustomerNo!="") {
		if (tCustomerNoSel!=tCustomerNo) {
			alert("【修改】操作不可替换出险人");
			return false;
		}
	}
	fm.CustomerNo.value = CustomerListGrid.getRowColData(tSelNo,1);		
	fm.Operate.value="CUSTOMERUPDATE";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();	
}

/**
 * delete客户
 */
function deleteCustomer() {
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("请先保存报案信息");
		return false;;
	}
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条该出险人信息");
		return false;
	}
	
  if (confirm("出险人删除操作将会删除该出险人下的所有相关的信息！确定删除吗?")) {  
		
		fm.CustomerNo.value = CustomerListGrid.getRowColData(tSelNo,1);
		
		fm.Operate.value = "CUSTOMERDELETE";
		submitForm();	
	}	
}
/*======================事件操作========================*/
/**
 * 查询报案出险人事件信息
 */
function queryReportCaseInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql5");
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),AccidentListGrid,2);

}
/**
 * 展示选中的报案出险人事件信息
 */
function showSelReportCaseInfo() {
	
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条出险人信息");
		return false;
	}
	var tAccNo = AccidentListGrid.getRowColData(tSelNo,1);
	if (tAccNo==null || tAccNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	var tCustomerNo = AccidentListGrid.getRowColData(tSelNo,2);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	
	fm.AccNo.value = tAccNo;
	fm.CustomerNo.value = tCustomerNo;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql6");
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tAccNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
		
		initCustomerCaseInfo();		
		fm.AccNo.value = tArr[0][0];
		fm.AccReason.value = tArr[0][1];
		fm.AccReasonName.value = tArr[0][2];
		fm.AccDate.value = tArr[0][3];
		fm.HospitalCode.value = tArr[0][4];
		fm.HospitalName.value = tArr[0][5];
		fm.ClaimPay.value = tArr[0][6];
		fm.AccGrade.value = tArr[0][7];
		fm.AccGradeName.value = tArr[0][8];
		fm.AccSite.value = tArr[0][9];
		fm.AccDesc.value = tArr[0][10];
		fm.CaseRemark.value = tArr[0][11];
		
		var tClaimType = tArr[0][12];
		var tClaimTypeArr = tClaimType.split(",");
		for (var i=0;i<fm.ClaimType.length;i++) {
		  	
			fm.ClaimType[i].checked = false;
		}
		if (tClaimTypeArr!= '' && tClaimTypeArr !=null) {
		  	
			for (var j=0;j<fm.ClaimType.length;j++){
		     	
				for (var l=0;l<tClaimTypeArr.length;l++){
								
					var tClaim = tClaimTypeArr[l].toString();
					if(fm.ClaimType[j].value == tClaim.substr(1,3)){				        		
						fm.ClaimType[j].checked = true;
					}
				}
			}
		} else {
			
			for (var i=0;i<fm.ClaimType.length;i++) {
			  
			  if (fm.ClaimType[i].value==tClaimType.substr(1,3)) {
			  	fm.ClaimType[i].checked = true;
			  }				
			}			
		}
	} else {
		return false;		
	}
	queryCaseDutyInfo();
}
/**
 * 查询事件责任信息
 */
function queryCaseDutyInfo() {
	
	turnPage3.pageLineNum = 100;	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql12");
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(fm.AccNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	turnPage3.queryModal(tSQLInfo.getString(),GrpClaimDutyGrid,2,1);		
	
}
/**
 * 新增事件
 */
function newCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条出险人信息");
		return false;
	}
	
	fm.Operate.value="CASEINSERT";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * 校验事件
 */
function checkCaseInfo() {
	
	var tCustomerNo = fm.CustomerNo.value;
	//出险人信息校验
	if (tCustomerNo==null || tCustomerNo=="") {
		
		alert("请先选中一条客户信息！");
		return false;
	}	
	
	if(!notNull(fm.AccReason,"出险原因")){return false;};
	if(!notNull(fm.AccDate,"出险日期")){return false;};	
	if(!notNull(fm.HospitalCode,"就诊医院")){return false;};
	if(!notNull(fm.HospitalName,"医院名称")){return false;};
	if(!notNull(fm.AccSite,"出险地点")){return false;};
	if(!notNull(fm.AccDesc,"事故描述")){return false;};
	
	var tRptNo = fm.RptNo.value;
	var tAccDate = fm.AccDate.value;
	var tHospitalCode = fm.HospitalCode.value;
	var tHospitalName = fm.HospitalName.value;
	
	//校验出险人是否存在
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql7");
	tSQLInfo.addSubPara(tRptNo);
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistArr==null || tExistArr.length<=0) {
		
		alert("该出险人不在此次立案下，请先添加!");
		return false;
	}	
	//出险日期校验
	if (dateDiff(tAccDate,mCurrentDate,'D')<0) {	
		
		alert("出险日期不能晚于当前日期！");
		return false;
	}
	//校验出险类型
	var tClaimType = new Array;
		
	//出险类型
	for (var j=0;j<fm.ClaimType.length;j++) {
  	
		if (fm.ClaimType[j].checked == true) {
      	
			tClaimType[j] = fm.ClaimType[j].value;			
		}
	}
	if (tClaimType==null || tClaimType=="") {

		alert("请选择出险类型！");
		return false;
	}

	
	//校验治疗医院与医院编码是否匹配
		
	if (tHospitalCode!=null && tHospitalCode!="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql8");
		tSQLInfo.addSubPara(tHospitalCode);
		tSQLInfo.addSubPara(tHospitalName);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tValidArr==null || tValidArr.length<=0) {
			
			alert("医院名称与医院编码不对应，请检查！");
			return false;
		}
	}
	return true;
}

/**
 * 修改事件
 */
function modifyCase() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条该出险人的事件信息");
		return false;
	}
	tPageNo = turnPage2.pageIndex;
	tIndexNo = tSelNo+1;
	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;	
	
	fm.Operate.value="CASEUPDATE";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();	
}

/**
 * 删除事件
 */
function deleteCase() {
	
		var tSelNo = AccidentListGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("请选择一条事件信息");
			return false;
		}	
	
  if (confirm("事件删除操作将会删除该出险人该事件下的所有相关的信息！确定删除吗?")) {  
					
		fm.Operate.value = "CASEDELETE";
		submitForm();	
	}	
}

/**
 * 保单查询
 */
function showInsuredLCPol() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人");
		return false;
	}	
	var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=2";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"保单查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 保全查询
 */
function showInsuredLCEdor() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人");
		return false;
	}	
	var strUrl="./LLClaimQueryEdorMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"保全查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 既往赔案查询
 */
function showOldCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人");
		return false;
	}	
	var strUrl="./LLClaimLastCaseMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}

/**
 * 索赔资料
 */
function ClaimData() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("请先保存报案信息！");
			return false;		
	}	
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("请先保存出险人信息");
		return false;
	}	
	
	var strUrl="./LLClaimDataMain.jsp?RptNo="+fm.RptNo.value+"&CustomerNo="+fm.CustomerNo.value+"&Mode="+mType;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"用户信息查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}

/**
 * 前置调查
 */
function ShowPresurveyData() {
		var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tGrpRgtNo="";
	var tRgtNo =fm.RptNo.value;
	var tInsuredNo = CustomerListGrid.getRowColData(tSelNo,1);
	var tVistFlag = "02";//02 表示 前置调查
	var tManageCom =fm.RptCom.value;//受理机构
	var tAppntNo=fm.AppntNo.value;//投保人编码	
   if(tInsuredNo == "" || tInsuredNo == null) {
    
    alert("出险人编码为空，请核查信息！");
		return false;
  	}
		
		var strUrl="LLClaimPreinvestInputMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&custNo="+tInsuredNo+"&surveyType=02&initPhase=01&AppntNo="+tAppntNo+"&ManageCom="+tManageCom+"&Mode=0";

		window.open(strUrl,"发起调查",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 报案回访
 */
function ReturnVisit() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("请先保存报案信息！");
			return false;		
	}
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("请先保存出险人信息");
		return false;
	}	
		
	var strUrl="./LLClaimVisitMain.jsp?RptNo="+fm.RptNo.value+"&CustomerNo="+fm.CustomerNo.value+"&Mode="+mType;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"用户信息查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}

/**
 * 重大案件上报
 */
function ShowReportBig() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("请先保存报案信息！");
			return false;		
	}	
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("请先保存出险人信息");
		return false;
	}
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息！");
		return false;
	}
	
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("请先查询出险人信息！");
		return false;
	}
	
	fm.CustomerNo.value = tCustomerNo;
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql17");
	tSQLInfo.addSubPara(tRptNo);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length<=0 || tValidArr[0][0]=="0") {
		
		alert("该报案不是重大案件！");
		return false;
	}		
	
	var strUrl="./LLClaimMajorMain.jsp?RptNo="+fm.RptNo.value+"&CustomerNo="+fm.CustomerNo.value+"&Mode="+mType;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"重大案件上报",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}
/**
 * 清空出险人信息
 */
function initInfo() {
	
	initReportCustomerInfo();
	initCustomerCaseInfo();
	initAccidentListGrid();
	initGrpClaimDutyGrid();
	validCustomerInfo();
	queryReportCustomerInfo();
		
}
/**
 * 报案确认
 */
function confirmReport() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("请先保存报案信息！");
			return false;		
	}	
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("请先保存出险人信息");
		return false;
	}	
	
	//校验是否录入了事件
	var tCaseCount = AccidentListGrid.mulLineCount;
	if (tCaseCount<=0) {
		alert("请出险人未录入事件信息");
		return false;
	}	
	
  if (confirm("是否报案确认?")) {  
		
		var tRptNo = fm.RptNo.value;
		if (tRptNo==null || tRptNo=="") {
			alert("未获得报案号!");
			return false;
		}
		fm.Operate.value = "REPORTCONFIRM";
		submitForm();	
	}		
	
}

/**
 * 返回
 */
function goToBack1() {
	window.location.href="./LLClaimMajorAppInput.jsp";
}

/**
 * 返回
 */
function goToBack() {
	window.location.href="./LLClaimReportAppInput.jsp";
}

/**
 * 重大案件回复确认
 */
function majorApprove() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息！");
		return false;
	}
	
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("请先查询出险人信息！");
		return false;
	}
	
	fm.CustomerNo.value = tCustomerNo;
	
  if (confirm("重大案件回复是否审批确认?")) {  
				
		fm.MajorFlag.value = tMajorFlag;
		if (tMajorFlag==2) {
			if(!notNull(fm.RepInputRemarks,"分公司机构意见")){return false;};
		} else if (tMajorFlag==3) {
			if(!notNull(fm.RepInputRemarks1,"总公司机构意见")){return false;};
		}
		fm.Operate.value = "MAJORCONFIRM";
		submitForm();	
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
function afterSubmit(FlagStr, content, tRptNo) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
//		alert("111");
////		alert(content);
//		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
//		showDialogWindow(urlStr, 1);
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
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

		if (fm.Operate.value=="REPORTCONFIRM") {
			window.location.href="./LLClaimReportAppInput.jsp";
		} else if (fm.Operate.value=="MAJORCONFIRM") {
			window.location.href="./LLClaimMajorAppInput.jsp";
		} else {

			if ((fm.Operate.value).indexOf("CASE")>=0) {
				initCustomerCaseInfo();				
				queryReportCaseInfo();
				//修改后仍选择当前行
				if (fm.Operate.value=="CASEUPDATE") {
					
					for (var i=0;i<fm.PageIndex.value;i++) {
						turnPage2.nextPage();
					}
					AccidentListGrid.radioSel(fm.SelNo.value);
					showSelReportCaseInfo();
				}
				initGrpClaimDutyGrid();
				queryCaseDutyInfo();
				
			} else {
				if (fm.Operate.value=="REPORTINSERT") {
					
					if (tRptNo!=null && tRptNo!="") {
						mRptNo = tRptNo;
						window.location.href="LLClaimReportInput.jsp?Type=2&RptNo="+ tRptNo;
					}										
				}
				initForm();
			}
		}		
	}
	submit = 0;
}
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
//查询投保人信息，支持左右模糊查询
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
		
		var tRptCom = fm.RptCom.value;
		if (tRptCom==null || tRptCom=="") {
			alert("请先选择报案机构！");
			fm.RptCom.className = "warnno";
			fm.RptCom.focus();
			return false;
		} else {
			
			//校验报案机构
			var tComGrade;
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
			tSQLInfo.setSqlId("LLClaimReportSql16");
			tSQLInfo.addSubPara(fm.RptCom.value);
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
				
				alert("报案机构不能是总公司或分公司，请下拉选择！");
				fm.RptCom.focus();
				fm.RptCom.className = "warnno";
				return false;
			}
		}
		
		var tAppntName = tObjectValue;
		
		if (tAppntName==null || tAppntName=="") {
			alert("请录入投保人名称！");
			tObject.focus();
			return false;
		}
		
		
		//个单查询
		if (tContType!=null && tContType=="1") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
			tSQLInfo.setSqlId("LLClaimReportSql14");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(mManageCom);
			tSQLInfo.addSubPara(tRptCom);
		} else if(tContType=="2") {//团单查询
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
			tSQLInfo.setSqlId("LLClaimReportSql13");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(tRptCom);			
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AppntType.value = tArr[0][0];
				fm.AppntNo.value = tArr[0][1];
				fm.AppntName.value = tArr[0][2];
			} else {
				showAppntList();			
			}
		}		
	}else if (tObjectName=="HospitalName") {
		
		var tHospitalName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql10");
		tSQLInfo.addSubPara(tHospitalName);
		tSQLInfo.addSubPara("");		
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
			fm.AccResult1.value = "";
			fm.AccResult1Name.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.HospitalCode.value = tArr[0][0];
				fm.HospitalName.value = tArr[0][1];
				
				fm.AccSite.value = tArr[0][2];				
			} else {
				var tCodeCondition = "1 and hospitalname like #%"+ tHospitalName +"%# ";
				showCodeList('hospital', [fm.HospitalCode,fm.HospitalName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	
}
/**
 * 打开查询的系统团体投保人选择页面
 */
function showAppntList() {
	
	var strUrl="./LLClaimQueryAppntMain.jsp?AppntName="+fm.AppntName.value+"&ContType="+fm.RgtClass.value+"&AcceptCom="+fm.RptCom.value;
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
	fm.EmpNo.value = tQueryResult[7];
	fm.IDType.value = tQueryResult[8];
	fm.IDTypeName.value = tQueryResult[9];	
	fm.IDNo.value = tQueryResult[10];			

	self.focus();
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
//选则下拉框后调用
function afterCodeSelect (CodeName) {
	
	try{
		if(CodeName == "conttype"){
			
			var tRgtClass = fm.RgtClass.value;
			if (tRgtClass=="02") {
				
				fm.AppntNo.value = "";
				fm.AppntName.value = "";
				$("#appntinfo").hide();
				$("#appntvalueinfo").hide();
			} else {
				$("#appntinfo").show();
				$("#appntvalueinfo").show();				
			}
		} else if (CodeName == "province") {
			
			fm.AccCity.value = "";
			fm.AccCityName.value = "";
			fm.AccCountyName.value = "";
			fm.AccCounty.value = "";
			fm.AccSite.value = "";						
		} else if (CodeName == "city") {
			
			fm.AccCountyName.value = "";
			fm.AccCounty.value = "";
			fm.AccSite.value = "";						
		} else if (CodeName == "hospital") {
			
			var tHospitalCode = fm.HospitalCode.value;
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql10");
			tSQLInfo.addSubPara("");
			tSQLInfo.addSubPara(tHospitalCode);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null || tArr.length==0) {
				
				fm.HospitalCode.value = "";
				fm.HospitalName.value = "";
				
				fm.AccProvince.value = "";
				fm.AccProvinceName.value = "";
				fm.AccCity.value = "";
				fm.AccCityName.value = "";
				fm.AccCounty.value = "";
				fm.AccCountyName.value = "";
				fm.AccSite.value = "";							
				return false;
			} else {
				
				if (tArr.length==1) {
					fm.HospitalCode.value = tArr[0][0];
					fm.HospitalName.value = tArr[0][1];
					
					fm.AccProvince.value = tArr[0][2];
					fm.AccProvinceName.value = tArr[0][3];
					fm.AccCity.value = tArr[0][4];
					fm.AccCityName.value = tArr[0][5];
					fm.AccCounty.value = tArr[0][6];
					fm.AccCountyName.value = tArr[0][7];
					fm.AccSite.value = tArr[0][8];
				}		
			}
		}
	} catch (ex){
		
		alert(ex);
	}
}
function initAreaData(tFlag) {
	
	if (tFlag==1) {
		fm.AccCity.value = "";
		fm.AccCityName.value = "";
		fm.AccCountyName.value = "";
		fm.AccCounty.value = "";		
	} else if (tFlag==2) {
		fm.AccCountyName.value = "";
		fm.AccCounty.value = "";		
	}
}
//展示提示信息
function showWarnInfo() {
		
	alert('请录入[投保人名称]进行模糊查询！');
	fm.AppntName.focus();
	fm.AppntName.style.background = "#ffb900";
}
/**
 * 正则校验录入金额
 */
function checkMoney(tObject) {

	var tValue = tObject.value;
	if(((!/^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(tValue)) && (!tValue==""))){
		
		alert("请输入正确的金额！") ;
		tObject.className = "warn" ;
		tObject.focus();
		tObject.value="";		
	}
}