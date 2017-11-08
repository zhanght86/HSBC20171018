<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIDataRevertInit.jsp
	//程序功能：数据回滚
	//创建日期：2011-07-30
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">
	function initForm() {
		try {

			fm.AccumulateDefNO.value = "";
			fm.AccumulateDefName.value = "";
			fm.InsuredNo.value = "";
			fm.StartDate.value = "";
			initMul11Grid();
		} catch (re) {
			myAlert("RIDataRevertInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}
	function initMul11Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 40; //列最大值
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许   			     

			iArray[1] = new Array();
			iArray[1][0] = "保单号";
			iArray[1][1] = "80px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "业务类型";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "业务明细";
			iArray[3][1] = "0px";
			iArray[3][2] = 100;
			iArray[3][3] = 3;

			iArray[4] = new Array();
			iArray[4][0] = "被保人客户号";
			iArray[4][1] = "100px";
			iArray[4][2] = 85;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "被保人姓名";
			iArray[5][1] = "80px";
			iArray[5][2] = 85;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "再保合同编号";
			iArray[6][1] = "120px";
			iArray[6][2] = 85;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "再保方案编号";
			iArray[7][1] = "100px";
			iArray[7][2] = 85;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "累计风险编号";
			iArray[8][1] = "100px";
			iArray[8][2] = 85;
			iArray[8][3] = 0;

			iArray[9] = new Array();
			iArray[9][0] = "当前风险保额";
			iArray[9][1] = "80px";
			iArray[9][2] = 85;
			iArray[9][3] = 0;

			iArray[10] = new Array();
			iArray[10][0] = "风险保额变化量";
			iArray[10][1] = "80px";
			iArray[10][2] = 85;
			iArray[10][3] = 0;

			iArray[11] = new Array();
			iArray[11][0] = "转换后风险保额";
			iArray[11][1] = "80px";
			iArray[11][2] = 85;
			iArray[11][3] = 0;

			iArray[12] = new Array();
			iArray[12][0] = "累计风险保额";
			iArray[12][1] = "80px";
			iArray[12][2] = 85;
			iArray[12][3] = 0;

			iArray[13] = new Array();
			iArray[13][0] = "理赔额";
			iArray[13][1] = "80px";
			iArray[13][2] = 85;
			iArray[13][3] = 0;

			iArray[14] = new Array();
			iArray[14][0] = "业务日期";
			iArray[14][1] = "80px";
			iArray[14][2] = 85;
			iArray[14][3] = 0;

			Mul11Grid = new MulLineEnter("fm", "Mul11Grid");
			Mul11Grid.mulLineCount = 0;
			Mul11Grid.displayTitle = 1;
			Mul11Grid.canSel = 0;
			Mul11Grid.canChk = 0;
			Mul11Grid.hiddenPlus = 1;
			Mul11Grid.hiddenSubtraction = 1;
			Mul11Grid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
