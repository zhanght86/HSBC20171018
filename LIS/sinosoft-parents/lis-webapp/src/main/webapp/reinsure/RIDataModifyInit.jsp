<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIDataModifyInit.jsp
	//程序功能：业务数据调整
	//创建日期：2011-07-30
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">
	function initForm() {
		try {
			initMul11Grid();
			initMul13Grid();
		} catch (re) {
			myAlert("RIDataModifyInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}

	function initMul11Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;
			
			iArray[1] = new Array();
            iArray[1][0] = "事件编号";
            iArray[1][1] = "60px";
            iArray[1][2] = 100;
            iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "合同编号";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "被保险人编号";
			iArray[3][1] = "90px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "被保险人姓名";
			iArray[4][1] = "90px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "分出责任";
			iArray[5][1] = "60px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "险种";
			iArray[6][1] = "30px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "责任项";
			iArray[7][1] = "30px";
			iArray[7][2] = 100;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "业务类型";
			iArray[8][1] = "60px";
			iArray[8][2] = 100;
			iArray[8][3] = 0;

			Mul11Grid = new MulLineEnter("fm", "Mul11Grid");

			Mul11Grid.mulLineCount = 0;
			Mul11Grid.displayTitle = 1;
			Mul11Grid.canSel = 1;
			Mul11Grid.canChk = 0;
			Mul11Grid.hiddenPlus = 1;
			Mul11Grid.hiddenSubtraction = 1;

			Mul11Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
	function initMul13Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "字段代码";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ridatamodifycol";
			iArray[1][5] = "1|2"; //将引用代码分别放在第1、2
			iArray[1][6] = "0|1";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "字段名称";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "原始数值";
			iArray[3][1] = "60px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "修改数值";
			iArray[4][1] = "60px";
			iArray[4][2] = 100;
			iArray[4][3] = 1;

			Mul13Grid = new MulLineEnter("fm", "Mul13Grid");
			Mul13Grid.mulLineCount = 0;
			Mul13Grid.displayTitle = 1;
			Mul13Grid.canSel = 0;
			Mul13Grid.canChk = 0;
			Mul13Grid.hiddenPlus = 0;
			Mul13Grid.hiddenSubtraction = 0;
			Mul13Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
