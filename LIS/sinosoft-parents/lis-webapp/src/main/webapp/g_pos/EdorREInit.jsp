<%
/***************************************************************
 * <p>ProName:EdorREInput.jsp</p>
 * <p>Title:  保单复效</p>
 * <p>Description:保单复效</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-24
 ****************************************************************/
%>


<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initButton();
		initInpBox();
		
	} catch (re) {
		alert("初始化界面错误!");
	}
}


/**
 * 初始化参数
 */
function initParam() {
	
	try {
		tContPlanType = getContPlanType(tGrpContNo);
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
		if("01"==tContPlanType){
			fm.REtype.value="32";
			queryInfoClick();
			queryEdorValDate();
		} else if("00"==tContPlanType){
			fm.REtype.value="31";
			queryClick();
		}
		auotContShowCodeName("surrender",fm.REtype.value,fm,"REtypeName");
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	try {
		if(tActivityID=='1800401002'){
			divButton01.style.display=''
		}else {
			divButton01.style.display='none'
		}
		if("01"==tContPlanType){
			tdGetMoneyName.style.display='none';
			tdGetMoney.style.display='none';
			tdDaysName.style.display='';
			tdDays.style.display='';
			tdInterestName.style.display='none';
			tdInterest.style.display='none';
			tdValDateName.style.display='';
			tdValDate.style.display='';
		} else if("00"==tContPlanType){
			tdGetMoneyName.style.display='';
			tdGetMoney.style.display='';
			tdDaysName.style.display='none';
			tdDays.style.display='none';
			tdInterestName.style.display='';
			tdInterest.style.display='';
			tdValDateName.style.display='none';
			tdValDate.style.display='none';
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
