<%
/***************************************************************
 * <p>ProName:EdorWTInput.jsp</p>
 * <p>Title:  犹豫期退保</p>
 * <p>Description:犹豫期退保</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>


<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
 		initButton();
		initInpBox();
		initParam();
		QueryReson();

	} catch (re) {
		alert("初始化界面错误!");
	}
}


/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
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
		
		if(tActivityID=='1800401002'){
			divButton01.style.display='';
			divButton02.style.display='none';
		}else {
			divButton01.style.display='none';
			divButton02.style.display='';
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
