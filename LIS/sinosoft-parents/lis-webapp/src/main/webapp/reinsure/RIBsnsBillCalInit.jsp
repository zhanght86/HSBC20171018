<%@include file="/i18n/language.jsp"%>

<%
	//Creator :张斌
	//Date :2006-10-24
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<script type="text/javascript">
	function initInpBox() {
		try {
			fm.RIComCode.value = '';
			fm.RIComName.value = '';
			fm.BillType.value = '';
			fm.BillTypeName.value = '';
			fm.StartDate.value = '';
			fm.EndDate.value = '';
		} catch (ex) {
			myAlert("进行初始化时出现错误！！！！");
		}
	}

	// 下拉列表的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("2在CertifyDescInit.jspInitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
		} catch (re) {
			myAlert("3CertifyDescInit.jspInitForm函数中发生异常:初始化界面错误!");
		}
	}
</script>
