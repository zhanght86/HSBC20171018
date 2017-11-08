<%
/***************************************************************
 * <p>ProName：LDWorkFlowInit.jsp</p>
 * <p>Title：工作流驱动</p>
 * <p>Description：工作流驱动</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-11-09
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
	} catch (re) {
		alert("初始化界面错误！");
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
		
		DivDocCode.style.display="none";
		id1.style.display="none";
		id2.style.display="none";
		id3.style.display="none";
		id4.style.display="none";
		id5.style.display="none";
		id6.style.display="none";
		id7.style.display="none";
		id8.style.display="none";
		
		fm.ScanManageCom.value = "";
		fm.ScanManageComName.value = "";
		fm.BussType.value = "";
		fm.BussTypeName.value = "";
		fm.SubType.value = "";
		fm.SubTypeName.value = "";
		fm.PropType.value = "";
		fm.PropTypeName.value = "";
		fm.DocCode.value = "";
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
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
