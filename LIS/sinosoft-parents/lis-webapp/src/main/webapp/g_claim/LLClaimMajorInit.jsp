<%
/***************************************************************
 * <p>ProName：LLClaimMajorInit.jsp</p>
 * <p>Title：重大案件上报/录入</p>
 * <p>Description：重大案件上报/录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
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
		initButton();
		
		initPageInfo();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		
		fm.RptNo.value = mRptNo;
		fm.CustomerNo.value = mCustomerNo;
		fm.InputOperator.value = mOperator;
		fm.InputDate.value = mCurrentDate;
		fm.InputCom.value = mManageCom;
		fm.InputComName.value = "";
		fm.InputRemarks.value = "";					
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件

 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
		//判断重大案件上报是否审批
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql2");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		var tArr = easyExecSql(tSQLInfo.getString());
		//判断是否查询成功
		if (tArr!=null && tArr.length>0) {
			fm.printCase.style.display = "";
		} else {
			fm.printCase.style.display = "none";
		}
		if (mMode!=null && mMode=="3") {
			fm.reportMajor.style.display = "none";			
		} else if(mMode=="0") {			
			fm.reportMajor.style.display = "";			
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
