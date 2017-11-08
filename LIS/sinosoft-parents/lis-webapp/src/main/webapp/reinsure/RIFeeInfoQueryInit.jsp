<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：ClientConjoinQueryInit.jsp
	//程序功能：
	//创建日期：2002-08-19
	//创建人  ：Kevin
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
%>

<script type="text/javascript">
	var sqlresourcename = "reinsure.RIFeeInfoQuerySql";
	function initInpBox() {
		try {
		} catch (ex) {
			myAlert("在FeeInfoQueryInit.jsp-->"+"InitInpBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
		} catch (re) {
			myAlert("FeeInfoQueryInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}
	function initFeeGrid() {
		try {
			var feeno = fm.QureyFeeCode.value;
			var iArray = new Array();
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIFeeInfoQuerySql3");// 指定使用的Sql的id
			mySql.addSubPara(feeno);// 指定传入的参数，多个参数顺序添加
			var strSQL = mySql.getString();
			var result = easyExecSql(strSQL, 1, 0, 1);

			var i = 0;
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许
			var n = 0;
			for (i = 0; i < result.length; i++) {
				colname = result[i][0];
				n = i + 1;
				iArray[n] = new Array();
				iArray[n][0] = colname; //列名（此列为顺序号，列名无意义，而且不显示）
				iArray[n][1] = "50px"; //列宽
				iArray[n][2] = 10; //列最大值
				iArray[n][3] = 0; //是否允许输入,1表示允许，0表示不允许		
			}
			n = n + 1;
			iArray[n] = new Array();
			iArray[n][0] = "费率数值"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[n][1] = "50px"; //列宽
			iArray[n][2] = 10; //列最大值
			iArray[n][3] = 0; //是否允许输入,1表示允许，0表示不允许	

			FeeGrid = new MulLineEnter("fm", "FeeGrid"); //这些属性必须在loadMulLine前
			FeeGrid.mulLineCount = 0;
			FeeGrid.displayTitle = 1;
			FeeGrid.hiddenPlus = 1;
			FeeGrid.hiddenSubtraction = 1;
			FeeGrid.canSel = 1;
			FeeGrid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
