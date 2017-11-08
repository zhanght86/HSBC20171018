<%@include file="/i18n/language.jsp"%>
<%
	//程序名称：RIAccRDInit.jsp
	//程序功能：分出责任定义
	//创建日期：2011/6/16
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">
	function initInpBox() {
	}

	function initForm() {
		try {
			initInpBox();
		} catch (re) {
			myAlert("RIAccRDInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}
</script>
