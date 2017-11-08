/***************************************************************
 * <p>ProName：LLClaimCustomerInput.jsp</p>
 * <p>Title：立案登记通用</p>
 * <p>Description：立案登记通用</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/

var tResultFlag=false;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var tSQLInfo = new SqlClass();
var tPageNo = 1;//记录查询页码
var tSelNo = 1;//记录查询序号
var tQueryFlag = false;
var tQueryResultFlag = false;

//校验受理信息
function checkAcceptInfo() {
	
	if(!notNull(fm.RgtClass,"保单类型")){return false;};
	var tRgtClass = fm.RgtClass.value;
	//团单下投保人信息必录，否则隐藏
	if (tRgtClass=="01") {
		if (fm.AppntNo.value==null || fm.AppntNo.value=="") {
			alert("未选择投保人，请输入【投保人名称】进行模糊查询");
			fm.AppntName.focus();
			fm.AppntName.style.background="#ffb900";
			return false;
		}
	}
	if(!notNull(fm.AppntName,"投保人名称")){return false;};
	if(!notNull(fm.AppDate,"批次申请日期")){return false;};
	var tAppDate = fm.AppDate.value;
	if (dateDiff(tAppDate,mCurrentDate,'D')<0) {	
		
		alert("批次申请日期不能晚于当前日期！");
		return false;
	}	
	if(!notNull(fm.PageNo,"交接流转号")){return false;};
	
	//校验受理机构
	if (!checkAcceptCom()) {
		return false;
	}	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql20");
	tSQLInfo.addSubPara(fm.PageNo.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length!=1) {
		alert("您录入的交接流转号不存在，请重新录入！");
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
		
		//个单查询
		if (tContType!=null && tContType=="1") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql17");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(mManageCom);
			tSQLInfo.addSubPara(tAcceptCom);
		} else if(tContType=="2") {//团单查询
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(tAcceptCom);
//			tSQLInfo.addSubPara(tAppntName);
//			tSQLInfo.addSubPara(mManageCom);			
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
			fm.HospitalCode.value = "";
			fm.HospitalName.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.HospitalCode.value = tArr[0][0];
				fm.HospitalName.value = tArr[0][1];
				
				
				fm.AccSite.value = tArr[0][2];
			} else {
				var tCodeCondition = "1 and hospitalname like #%"+ tHospitalName +"%# and hosstate=#1# ";
				showCodeList('hospital', [fm.HospitalCode,fm.HospitalName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	if (tObjectName=="AccResult1Name") {
		var tAccResultName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql11");
		tSQLInfo.addSubPara(tAccResultName);
		tSQLInfo.addSubPara("1");
		tSQLInfo.addSubPara("");
		
		if (fm.AccResult1Name.value==null || fm.AccResult1Name.value=="") {
			alert("主要诊断不可为空");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
			fm.AccResult2.value = "";
			fm.AccResult2Name.value = "";
			return false;
		} else {
			fm.AccResult2.value="";
			fm.AccResult2Name.value="";
			if (tArr.length==1) {
				fm.AccResult1.value = tArr[0][0];
				fm.AccResult1Name.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and icdname like #%"+ tAccResultName +"%# and icdlevel=#1#  ";
				showCodeList('diseasecode', [fm.AccResult1,fm.AccResult1Name], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	if (tObjectName=="AccResult2Name") {
		var tAccResultName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql11");		
		tSQLInfo.addSubPara(tAccResultName);
		tSQLInfo.addSubPara("2");
		tSQLInfo.addSubPara(fm.AccResult1.value);
		
		if (fm.AccResult1.value==null || fm.AccResult1.value=="") {
			alert("未选择主要诊断，请输入【主要诊断名称】进行模糊查询");
			fm.AccResult1Name.focus();
			fm.AccResult1Name.style.background="#ffb900";
			return false;
		}
		if (fm.AccResult2Name.value==null || fm.AccResult2Name.value=="") {
			alert("诊断详情不可为空");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AccResult2.value = tArr[0][0];
				fm.AccResult2Name.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and icdname like #%"+ tAccResultName +"%# and upicdcode=#"+ fm.AccResult1.value +"# and icdlevel=#2#";
				showCodeList('diseasecode', [fm.AccResult2,fm.AccResult2Name], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	} else if (tObjectName=="BankCodeName") {
		
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
				var tCodeCondition = "1 and headbankname like #%"+ tBankName +"%# ";
				showCodeList('headbank', [fm.BankCode,fm.BankCodeName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}	
	
}

/**
 * 客户风险等级展示
 *//*
function showRiskLevel(){
	
	var tAppntNo = fm.AppntNo.value;		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql27");
	tSQLInfo.addSubPara(tAppntNo);
	var tResult = easyExecSql(tSQLInfo.getString());
	if(tResult==null||tResult==""){
		fm.RiskLevel.value = "";
	}else {
		fm.RiskLevel.value = tResult;
	}
}*/
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

}
function queryAcceptInfo() {
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql1");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
					
		fm.GrpRgtNo.value = tArr[0][0];		
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
		fm.AppDate.value = tArr[0][5];
		fm.AcceptDate.value = tArr[0][6];
		fm.PageNo.value = tArr[0][7];
		fm.AcceptOperator.value = tArr[0][8];
		fm.AcceptCom.value = tArr[0][9];
		fm.SelfAcceptCom.value = tArr[0][9];
		fm.AcceptComName.value = tArr[0][10];
		fm.AcceptOperatorName.value = tArr[0][11];
		fm.AppntType.value = tArr[0][12];
		
	}
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
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("请先保存理赔受理信息");
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
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql2");
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	
	//个单的情况下需重置查询
	var tContType = fm.RgtClass.value;
	if (tContType!=null && tContType=="02") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql16");
		tSQLInfo.addSubPara(fm.AppntNo.value);
		
	}	
	
	var tEmployeNo = fm.EmployeNo.value ;
	if(tEmployeNo!=null && tEmployeNo!="" && tEmployeNo.indexOf("where",0)>=0){
		fm.EmployeNo.value = "";
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
	if (fm.CustomerNo.value !=null && fm.CustomerNo.value!="") {
		tSQLInfo.addSubPara(fm.CustomerNo.value);
	} else {
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.SelfAcceptCom.value);		
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length==0) {
		tQueryResultFlag = false;		
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
	return true;
}
//展示客户列表--存在同一投保人下同名同生日或同名同员工号或同一证件号码
function showCustomerList() {
	
	var strUrl="./LLClaimQueryMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&AppntNo="+fm.AppntNo.value+"&CustomerName="+fm.CustomerName.value+"&Birthday="+fm.Birthday.value+"&EmpNo="+fm.EmployeNo.value+"&IDNo="+fm.IDNo.value;
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
 * 获得查询到的被保人银行账户信息
 */
function afterQueryAccInfo(tQueryResult) {
	
	fm.BankCode.value = tQueryResult[7];
	fm.BankCodeName.value = tQueryResult[8];
	fm.Province.value = tQueryResult[3];
	fm.ProvinceName.value = tQueryResult[4];		
	fm.City.value = tQueryResult[5];	
	fm.CityName.value = tQueryResult[6];	
	fm.AccName.value = tQueryResult[9];
	fm.BankAccNo.value = tQueryResult[10];

	self.focus();
}

/**
 * 查询已维护的客户信息列表
 */
function queryCustomerList() {
	if(mMode=="2"){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql22");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2);
		
	}else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql5");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		
		turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2);	
	}
}
/**
 * 展示选中的客户信息
 */
function showSelectCustomer() {
	var i = CustomerListGrid.getSelNo()-1;
	var tRegisterNo = CustomerListGrid.getRowColData(i,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询！");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(i,2);
	
	fm.CustomerNo.value = tCustomerNo;
	fm.RegisterNo.value = tRegisterNo;
	fm.SelNo.value = CustomerListGrid.getSelNo();//add 2011-11-29
	fm.PageIndex.value = turnPage1.pageIndex;//add 2011-11-29
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql6");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(mGrpRegisterNo);
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("出险人信息不存在!");
		return false;
	}else {
		//先清空原有的数据
		initCustomerInfo();
		disableCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];	
		fm.Birthday.value = tArr[0][2];
		fm.EmployeNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];
		fm.AppAmnt.value = tArr[0][9];
		fm.BillCount.value = tArr[0][10];
		fm.ScanCount.value = tArr[0][11];
		fm.IsUrgent.value = tArr[0][12];
		fm.IsUrgentName.value = tArr[0][13];
		fm.IsOpenBillFlag.value = tArr[0][14];
		fm.IsOpenBillFlagName.value = tArr[0][15];
		fm.IsBackBill.value = tArr[0][16];
		fm.IsBackBillName.value = tArr[0][17];
		fm.BankCode.value = tArr[0][18];
		fm.BankCodeName.value = tArr[0][19];
		fm.Province.value = tArr[0][20];
		fm.ProvinceName.value = tArr[0][21];
		fm.City.value = tArr[0][22];	
		fm.CityName.value = tArr[0][23];	
		fm.BankAccNo.value = tArr[0][24];	
		fm.AccName.value = tArr[0][25];	
		fm.CustomerAppDate.value = tArr[0][26];
		fm.Remark.value = tArr[0][27];
		fm.RegisterNo.value = tArr[0][28];
		fm.SelfAppntNo.value = tArr[0][29];
		fm.SelfAppntName.value = tArr[0][30];
		fm.TelPhone.value = tArr[0][31];
	}
	queryCustomerCaseList();
}
/**
 * 校验客户信息--是否满足出险条件
 */
function checkCustomerInfo() {			
	
	if (fm.CustomerNo.value==null || fm.CustomerNo.value=="") {
		
		if(!notNull(fm.CustomerName,"[出险人姓名]")){return false;};
		if(!notNull(fm.Birthday,"[出险人生日]")){return false;};
		if(!notNull(fm.IDNo,"[证件号码]")){return false;};
		if(!notNull(fm.IDType,"[证件类型]")){return false;};
		if(!notNull(fm.Gender,"[性别]")){return false;};
	}
	if( fm.TelPhone.value!=null ||  fm.TelPhone.value!=""){
		var tTelPhone = fm.TelPhone.value;
	}
	var tRgtClass = fm.RgtClass.value;
	//团单下投保人信息必录，否则隐藏
	
	queryCustomer();
	if(!tQueryResultFlag){
		alert("不存在这样的出险人，请检查");
		return false;
	}		
	fm.TelPhone.value = tTelPhone;
	if(!notNull(fm.AppAmnt,"[申请金额]")){return false;};
	if(!notNull(fm.BillCount,"[发票数]")){return false;};
	if(!notNull(fm.IsUrgent,"[是否加急]")){return false;};
	if(!notNull(fm.IsOpenBillFlag,"[是否开据分割单]")){return false;};
	if(!notNull(fm.IsBackBill,"[是否退回发票]")){return false;};
	
	if (fm.IsOpenBillFlag.value == "1" && fm.IsBackBill.value == "1") {
		alert("[是否开据分割单]与[是否退回发票]不可同时为'是'！");
		return false;
	}
	
	//校验该出险人是否已经在本次出险中--修改操作时跳过
	if (fm.Operate.value=="CUSTOMERINSERT") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql3");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		tSQLInfo.addSubPara(fm.CustomerNo.value);	
		
		var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistsArr!=null && tExistsArr.length>0 ) {
			
			alert("您选择的客户已经存在于本次赔案中,请选择其他客户！");
			return false;
		}		
	}
	//校验被保人状态是否有效即是否存在有效保单
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql4");
	tSQLInfo.addSubPara(fm.CustomerNo.value);	
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length==0 ) {
		
		alert("您选择的客户不存在有效保单，请检查！");
		return false;
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
	
	if(fm.BankCode.value!="" || fm.Province.value!=""
			|| fm.City.value!="" || fm.BankAccNo.value!=""
				|| fm.AccName.value!=""){
	
		if(!notNull(fm.BankCode,"账户信息不可部分录入，[开户行所在省]")){return false;};
		if(!notNull(fm.Province,"账户信息不可部分录入，[开户行所在省]")){return false;};
		if(!notNull(fm.City,"账户信息不可部分录入，[开户行所在市]")){return false;};
		if(!notNull(fm.BankAccNo,"账户信息不可部分录入，[账号]")){return false;};
		if(!notNull(fm.AccName,"账户信息不可部分录入，[账户名]")){return false;};			
	}
	
	var tCustomerAppDate = fm.CustomerAppDate.value;	
	if (tCustomerAppDate!=null && tCustomerAppDate!="") {
		
		if (dateDiff(tCustomerAppDate,mCurrentDate,'D')<0) {	
			
			alert("[客户申请日期]不能晚于当前日期！");
			return false;
		}		
	}
	
	return true;
}
/**
 * 立案完毕前校验
 */
function checkBeforeOver() {
	
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
 * 默认选中刚操作的记录
 */
function setSelRow(ObjGrid,tTurnPage){			
	
 	var tPageIndex = fm.PageIndex.value;
 	
	if (tPageIndex!=null && tPageIndex!="") {
		for (var i=0; i<tPageIndex; i++) {
			tTurnPage.nextPage();
		}
	}
	var tSelNo =fm.SelNo.value;
	if(tSelNo==null || tSelNo ==""){
		tSelNo = 1;
	}	
	ObjGrid.radioSel(tSelNo);
	
}

/**
 * 选择城市前必须先选择省份
 */
function checkProvince() {
	
	if(fm.Province.value == "") {
		
		alert("请先选择省份");
		fm.City.value = "";
		fm.CityName.value = "";
	}
}
//====================事件===================//
/**
 * 校验事件信息
 */
function checkCaseInfo() {
	
	if(!notNull(fm.AccReason,"出险原因")){return false;};
	if(!notNull(fm.AccDate,"出险日期")){return false;};
	if(!notNull(fm.ClaimPay,"索赔金额")){return false;};
	if(!notNull(fm.HospitalCode,"就诊医院")){return false;};
	if(!notNull(fm.HospitalName,"医院名称")){return false;};
	if(!notNull(fm.AccResult1,"主要诊断")){return false;};
	if(!notNull(fm.TreatResult,"治疗结果")){return false;};
	if(!notNull(fm.CaseSource,"事件来源")){return false;};
	/*
	if(!notNull(fm.AccProvinceName,"出险地点[省]")){return false;};
	if(!notNull(fm.AccCityName,"出险地点[市]")){return false;};
	if(!notNull(fm.AccCountyName,"出险地点[区/县]")){return false;};*/
	if(!notNull(fm.AccSite,"出险地点")){return false;};	
	
	var tGrpRegisterNo = fm.GrpRgtNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	var tRegisterNo = fm.RegisterNo.value;
	var tAccDate = fm.AccDate.value;
	var tAppntNo = fm.AppntNo.value;
	var tHospitalCode = fm.HospitalCode.value;
	var tHospitalName = fm.HospitalName.value;
	var tAccidentType = fm.AccReason.value;
	
	//出险人信息校验
	if (tCustomerNo==null || tCustomerNo=="") {
		
		alert("请先选中一条客户信息！");
		return false;
	}
	//校验出险人是否存在
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql7");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(tGrpRegisterNo);
	
	var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistArr==null || tExistArr.length<=0) {
		
		alert("该出险人不在此次立案下，请先添加!");
		return false;
	}
	
/*	//被保人所有保单已做完生存给付（趸领） 不允许理赔
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql8");
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length<=0) {
		
		alert("该出险人已经做了生存给付，不允许做新增!");
		return false;
	}*/
/*
	//退保万能险不允许理赔，退保普通险是可以理赔的
	var tPGSQL = "select count(1) from LPEdorItem where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and edortype='CT'";
	var tEdorState = easyExecSql(tPGSQL);
	
	var tCSQL = "select count(*) from lmriskapp where "
	         + "riskcode in (select riskcode From lcpol where "
	         + "grpcontno = '"+ tGrpContNo +"' and insuredno = '"+ tCustomerNo +"' and contno='"+ tContNo +"') "
	         + "and RiskPeriod = 'L'";
	var tCount = easyExecSql(tCSQL);
	
	if (tEdorState>0 && tCount>0 ) {
		alert("该出险人有未确认的保全或已经退保，请确认后再做新增！");
		return false;
	}	
*/					

/*
		var tSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+ tAccidentDate +"'";
				tSQLBQ = tSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+ tCustomerNo +"' and a.contno='"+ tContNo +"'";
				tSQLBQ = tSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";
		if (tAppntNo!=null) {
			
				tSQLBQ = tSQLBQ+" and b.AppntNo='"+ tAppntNo +"'";
		}
		if (tGrpContNo!=null) {
				tSQLBQ = tSQLBQ+" and b.GrpContNo='"+ tGrpContNo +"'";
		}
		var tArrBQ = easyExecSql(tSQLBQ);
		if ( tArrBQ != null ) {

				alert("严重警告：该被保险人做过可能更改保额的保全！");   
		}
*/
	//出险日期校验
	if (dateDiff(tAccDate,mCurrentDate,'D')<0) {	
		
		alert("出险日期不能晚于当前日期！");
		return false;
	}
	if (fm.Operate.value=="CASEUPDATE") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql23");
		tSQLInfo.addSubPara(tRegisterNo);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		var Result = easyExecSql(tSQLInfo.getString());
		if(Result != null && Result != ""){
		
		var len = Result.length;
		
			for(var i=0;i<len;i++){
				var tStateDate=Result[i][0];
				if(dateDiff(tAccDate,tStateDate,'D')< 0 ){
						alert("出险日期不能晚于账单起期！");
						return false;
				}
			}
		}		
	}

	//身故日期校验
	var tDeathDate = fm.DeathDate.value;
	if (tDeathDate!=null && tDeathDate!="") {
		
		if (dateDiff(tDeathDate,mCurrentDate,'D')<0) {
			
			alert("身故日期不能晚于当前日期！");
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";			
			return false;
		}
		if (dateDiff(tDeathDate,tAccDate,'D')>0) {		
			
			alert("身故日期不能早于出险日期！");
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDeathDate,'D')>180) {
				
				if (confirm("身故日期与出险日期的间隔大于180天是否继续？")) {
									
				} else {
					
					return false;
				}
			}
		}
	}
	//伤残/全残日期校验
	var tDianoseDate = fm.DeformityDate.value;
	if (tDianoseDate!=null && tDianoseDate!="") {
		
		if (dateDiff(tDianoseDate,mCurrentDate,'D')<0) {		
			
			alert("伤残/全残日期不能晚于当前日期！");
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";							
			return false;
		}
		if (dateDiff(tDianoseDate,tAccDate,'D')>0) {
			
			alert("伤残/全残日期不能早于出险日期！");
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";				
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDianoseDate,'D')>180) {
				
				if (confirm("伤残日期与出险日期的间隔大于180天是否继续？")) {
									
				} else {
					
					return false;
				}
			}
		}				
		var tDeadCheck = fm.ClaimType[0].checked;//校验出险类型为身故的多选框是否勾选
		if (!tDeadCheck && tDeathDate!="") {
			
			alert("您录入了【身故日期】，请勾选理赔类型--【身故】！");
			return false;
		}
		var tMaim = fm.ClaimType[2].checked;//校验出险类型为伤残的多选框是否勾选
		if (!tMaim && tDianoseDate!="") {
			
			alert("您录入了【伤残/全残日期】，请勾选理赔类型--【伤残】！");
			return false;
		}		

	}	
	var tAllGetClaimType = "";
	//校验出险类型
	
	var tClaimType = new Array;	
	var tDeadFalg = fm.ClaimType[0].checked;//校验是否勾选身故多选框
	var tMedicalFlag = fm.ClaimType[3].checked;//是否勾选出险类型--医疗
	var tMaimFalg = fm.ClaimType[2].checked;//是否勾选出险类型--伤残
		
	//出险类型
	for (var j=0;j<fm.ClaimType.length;j++) {
  	
		if (fm.ClaimType[j].checked == true) {
      	
			tClaimType[j] = fm.ClaimType[j].value;
			tAllGetClaimType = tAllGetClaimType + "'" + tAccidentType + "" + tClaimType[j] + "',";
		}
	}
	if (tClaimType==null || tClaimType=="") {

		alert("请选择出险类型！");
		return false;
	}
	tAllGetClaimType = tAllGetClaimType.substr(0,tAllGetClaimType.length-1);
//  var tRowNum = RiskDutyGrid.mulLineCount;
  /*
	for (var i=0;i<tRowNum;i++) {
	
		var tRiskCode = RiskDutyGrid.getRowColData(i,2);
		var tDutyCode = RiskDutyGrid.getRowColData(i,4);
		var tDutyName = RiskDutyGrid.getRowColData(i,5);
		
		var tGetDutyKindSQL = "select 1 from lmdutygetrela a, lmdutygetclm b where a.getdutycode = b.getdutycode  and a.dutycode = '"+ tDutyCode +"'  and b.getdutykind in ("+ tAllGetClaimType +")";
		var tGetDutyKindResult = easyExecSql(tGetDutyKindSQL);
		if(tGetDutyKindResult==null){
			
			alert("您录入的“"+ tDutyName +"”,不存在与它相对应的出险原因与出险类型！");
			return false;
		}	
	}
	*/
	if (tDeadFalg) {
		
		if (tDeathDate==null || tDeathDate=="") {
			
			alert("出险类型为身故，请录入【身故日期】！");
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;
		}
	}
	if (tMaimFalg) {
		
		if (tDianoseDate==null || tDianoseDate=="") {
			
			alert("出险类型为伤残，请录入【伤残/全残日期】！");			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";				
			return false;
		}
	}	
	
	//校验治疗医院与医院编码是否匹配
		
	if (tHospitalCode!=null && tHospitalCode!="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql9");
		tSQLInfo.addSubPara(tHospitalCode);
		tSQLInfo.addSubPara(tHospitalName);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tValidArr==null || tValidArr.length<=0) {
			
			alert("医院名称与医院编码不对应，请检查！");
			return false;
		}
	}
	
	if (fm.Operate.value=="CASEUPDATE") {
		
		//校验索赔金额是否大于该事件下所关联的账单的原始金额之和
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql25");
		tSQLInfo.addSubPara(fm.RegisterNo.value);
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		var tOriaSum = 0;
		if (tValidArr==null || tValidArr.length<=0) {
			
			tOriaSum=0;		
		} else {
			tOriaSum = tValidArr[0][0];
		}
		var tAppMoney = fm.ClaimPay.value;
		if (tAppMoney-tOriaSum<0) {
			alert("索赔金额不得小于该事件下账单原始金额之和");		
			return false;
		}		
	}
	
	return true;	
}
/**
 * 查询已维护的客户的事件信息列表
 */
function queryCustomerCaseList() {
	
	initCaseInfo();
	initOnEventDutyListGrid();
	initOffEventDutyListGrid();
	
	if (fm.RegisterNo.value=="" || fm.RegisterNo.value==null || fm.CustomerNo.value=="" || fm.CustomerNo.value==null) {
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql12");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),EventlistGrid,2);	
}
/**
 * 查询选中的客户的事件明细信息
 */
function showSelectCase() {

	var i = EventlistGrid.getSelNo()-1;
	var tRegisterNo = EventlistGrid.getRowColData(i,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询！");
		return;
	}
	var tCaseNo = EventlistGrid.getRowColData(i,2);
	var tCustomerNo = EventlistGrid.getRowColData(i,3);
	
	fm.CustomerNo.value = tCustomerNo;
	fm.RegisterNo.value = tRegisterNo;
	fm.CaseNo.value = tCaseNo;
	fm.SelNo.value = EventlistGrid.getSelNo();//add 2011-11-29
	fm.PageIndex.value = turnPage2.pageIndex;//add 2011-11-29	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql13");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("出险人事件信息不存在!");
		return false;
	}else {
		//先清空原有的数据
		initCaseInfo();

		fm.AccReason.value = tArr[0][0];
		fm.AccReasonName.value = tArr[0][1];
		fm.AccDate.value = tArr[0][2];
		fm.ClaimPay.value = tArr[0][3];
		fm.HospitalCode.value = tArr[0][4];
		fm.HospitalName.value = tArr[0][5];
		fm.AccResult1.value = tArr[0][6];
		fm.AccResult1Name.value = tArr[0][7];
		fm.AccResult2.value = tArr[0][8];
		fm.AccResult2Name.value = tArr[0][9];
		fm.DeformityDate.value = tArr[0][10];
		fm.DeathDate.value = tArr[0][11];
		fm.TreatResult.value = tArr[0][12];
		fm.TreatResultName.value = tArr[0][13];
		fm.CaseSource.value = tArr[0][14];
		fm.CaseSourceName.value = tArr[0][15];
		fm.LRCaseNo.value = tArr[0][16];
		/*fm.AccProvinceName.value = tArr[0][17];
		fm.AccProvince.value = tArr[0][18];	
		fm.AccCityName.value = tArr[0][19];	
		fm.AccCity.value = tArr[0][20];	
		fm.AccCountyName.value = tArr[0][21];
		fm.AccCounty.value = tArr[0][22];*/
		fm.AccSite.value = tArr[0][17];
		fm.AccDesc.value = tArr[0][18];
		fm.CaseRemark.value = tArr[0][19];
		fm.CaseNo.value = tArr[0][20]; 			
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql14");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);	
	
	var tClaimType = easyExecSql(tSQLInfo.getString());
	
	if (tClaimType == null || tClaimType.length==0) {
		alert("出险人理赔类型信息不存在，请检查该出险人是否有效!");
		return false;
	} else {
		
		for (var i=0;i<fm.ClaimType.length;i++) {
		  	
			fm.ClaimType[i].checked = false;
		}
		if (tClaimType!= '' && tClaimType !=null) {
		  	
			for (var j=0;j<fm.ClaimType.length;j++){
		     	
				for (var l=0;l<tClaimType.length;l++){
								
					var tClaim = tClaimType[l].toString();
					tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
					if(fm.ClaimType[j].value == tClaim){				        		
						fm.ClaimType[j].checked = true;
					}
				}
			}
		}
	}
	queryCaseDutyInfo();
}
/**
 * 查询事件责任信息
 */
function queryCaseDutyInfo() {
	
	initOnEventDutyListGrid();
		
	turnPage3.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql15");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara("1");
	turnPage3.queryModal(tSQLInfo.getString(),OnEventDutyListGrid,2,1);	
	
	initOffEventDutyListGrid();
	
	turnPage4.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql15");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara("0");
	turnPage4.queryModal(tSQLInfo.getString(),OffEventDutyListGrid,2,1);		
	
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
 * 记录操作页码与序号
 */
function initSelNo() {
	
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
			
//			fm.AccCity.value = "";
//			fm.AccCityName.value = "";
//			fm.AccCountyName.value = "";
//			fm.AccCounty.value = "";
			fm.AccSite.value = "";						
		} else if (CodeName == "city") {
			
//			fm.AccCountyName.value = "";
//			fm.AccCounty.value = "";
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
				
				/*	fm.AccProvince.value = "";
				fm.AccProvinceName.value = "";
				fm.AccCity.value = "";
				fm.AccCityName.value = "";
				fm.AccCounty.value = "";
				fm.AccCountyName.value = "";*/
				fm.AccSite.value = "";							
				return false;
			} else {
				
				if (tArr.length==1) {
					fm.HospitalCode.value = tArr[0][0];
					fm.HospitalName.value = tArr[0][1];
					
				/*	fm.AccProvince.value = tArr[0][2];
					fm.AccProvinceName.value = tArr[0][3];
					fm.AccCity.value = tArr[0][4];
					fm.AccCityName.value = tArr[0][5];
					fm.AccCounty.value = tArr[0][6];
					fm.AccCountyName.value = tArr[0][7];*/
					fm.AccSite.value = tArr[0][8];
				}		
			}
		}
	} catch (ex){
		
		alert(ex);
	}
}

function initAreaData() {
	
	//fm.City.value = "";
	//fm.CityName.value = "";
}

function initSelectCustomerInfo() {
	
		initCustomerInfo();
		initCaseInfo();
		initEventlistGrid();
		initOnEventDutyListGrid();
		initOffEventDutyListGrid();
		queryCustomerList();			
}

function initSelectCaseInfo() {
	
	initCaseInfo();	
	initOnEventDutyListGrid();
	initOffEventDutyListGrid();
	queryCustomerCaseList();
}
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "个人案件号^|客户编码^|姓名^|性别编码^|性别^|出生日期^|"
							+"证件类型编码^|证件类型名称^|证件号码^|员工号^|申请金额^|发票数";
	 
	tSQLInfo = new SqlClass();
	if(mMode=="2"){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql22");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		
	}else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql5");
		tSQLInfo.addSubPara(mGrpRegisterNo);	
	}
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
//校验受理机构
function checkAcceptCom() {

	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql24");
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

//查询功能
/**
 * 保单查询
 */
function showInsuredLCPol() {	
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人");
		return false;
	}	
	var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1&RgtNo="+fm.RegisterNo.value;
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
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
 * 影像件查询
 */
function queryEasyscan() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRgtNo =CustomerListGrid.getRowColData(tSelNo,1);
	window.open("../g_easyscan/ScanPagesQueryMainInput.jsp?BussType=G_CM&BussNo="+tRgtNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * BPO校验结果查询
 */
function BPOCheck() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	
	var strUrl="./LLClaimBPOcheckMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=1200;
	var tHeight=500;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"问题件",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * 社保新消息查询
 */
function querySecurity() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	
	var strUrl="./LLClaimQuerySocialInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"问题件",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 账户查询
 */
function queryAccInfo() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}	
	var strUrl="./LLClaimQueryAccInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&Mode=0" ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"问题件",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 受益人信息
 */
function benefitInfo() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);

	var strUrl="./LLClaimBenefitMain.jsp?GrpRgtNo="+mGrpRegisterNo+"&RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo;
	
	window.open(strUrl,"受益人",'width=1000,height=480,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}