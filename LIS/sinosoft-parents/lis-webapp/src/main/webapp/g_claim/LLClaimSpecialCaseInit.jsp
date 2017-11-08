<%
/***************************************************************
 * <p>ProName：LLClaimSpecialCaseInit.js</p>
 * <p>Title：特殊立案录入初始化</p>
 * <p>Description：特殊立案录入初始化</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面

 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();

		if (fm.GrpRgtNo.value!=null && fm.GrpRgtNo.value!="" && fm.GrpRgtNo.value!="null") {
                 
			queryAcceptInfo();
			queryCustomerInfo();
			disableCustomerInfo();
		}else {
			fm.GrpRgtNo.value="";
			validCustomerInfo();
		}
		
		initButton();					
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
			
		fm.GrpRgtNo.value = mGrpRegisterNo;
		fm.RgtClass.value = "2";
		fm.RgtClassName.value = "团单";
		fm.AppntNo.value = "";
		fm.AppDate.value = mCurrentDate;
		fm.AcceptDate.value = mCurrentDate;
		fm.AcceptOperator.value = mOperator;
		fm.AcceptCom.value = mManageCom;
		
		fm.PageNo.value = "";
		fm.CaseType.value = "";
		fm.CaseTypeName.value = "";
		fm.ClaimFlag.value = "";
		fm.ClaimFlagName.value = "";
		fm.HistoryRgtNo.value = "";
		
		initCustomerInfo();
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}
/**
 * 初始化出险人信息
 */
function initCustomerInfo() {
	
		fm.CustomerNo.value = "";
		fm.CustomerName.value = "";
		fm.Birthday.value = "";
		fm.EmployeNo.value = "";
		fm.IDNo.value = "";
		fm.IDType.value = "";
		fm.IDTypeName.value = "";
		fm.Gender.value = "";
		fm.GenderName.value = "";
		fm.RgtNo.value = "";
		fm.ErrorStation.value = "";
		fm.ErrorStationName.value = "";
		fm.ApplyRemarks.value = "";
		fm.SelfAppntNo.value = "";
		fm.TelPhone.value = "";
}

/**
 * 初始化客户不可用
 */
function disableCustomerInfo() {
		
	/*fm.CustomerName.disabled = true;	
		fm.Birthday.disabled = true;
		fm.EmployeNo.disabled = true;
		fm.IDNo.disabled = true;
		fm.IDType.disabled = true;
		fm.IDTypeName.disabled = true;
		fm.Gender.disabled = true;
		fm.GenderName.disabled = true;*/		
}
/**
 * 初始化客户可用
 */
function validCustomerInfo() {
	
		fm.CustomerName.disabled = false;	
		fm.Birthday.disabled = false;
		fm.EmployeNo.disabled = false;
		fm.IDNo.disabled = false;
		fm.IDType.disabled = false;
		fm.IDTypeName.disabled = false;
		fm.Gender.disabled = false;
		fm.GenderName.disabled = false;		
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
			document.getElementById("ErrorStation").style.display="none";
			document.getElementById("ErrorStationInfo").style.display="none";
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
		//通融、申诉案件且未申请理赔的必须进行立案登记
		var tCaseType = fm.CaseType.value;
		var tClaimFlag = fm.ClaimFlag.value;
		if (tCaseType!=null && (tCaseType=="13"||tCaseType=="11") && tClaimFlag!=null && tClaimFlag=="0") {
			fm.AcceptCase.disabled = false;
		//	fm.HistoryRgtNo.style.display="none";	
			document.getElementById("HistoryRgtNo").style.display="none";
			document.getElementById("HistoryRgtNoInfo").style.display="none";
			
		} else {
			fm.AcceptCase.disabled = true;
		//	fm.HistoryRgtNo.style.display="";	
			document.getElementById("HistoryRgtNo").style.display="none";
			document.getElementById("HistoryRgtNoInfo").style.display="none";
			if(tClaimFlag=="1"){
				document.getElementById("HistoryRgtNo").style.display="";
				document.getElementById("HistoryRgtNoInfo").style.display="";
			}
		}
		
		if (mMode==1) {
			
			fm.PageNoApply.style.display = "none";
			fm.Report.style.display = "none";
			fm.AcceptCase.style.display = "none";
			fm.RecordOver.style.display = "none";
		}
		
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}

/**
 * 把null的字符串转为空

 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}


</script>
