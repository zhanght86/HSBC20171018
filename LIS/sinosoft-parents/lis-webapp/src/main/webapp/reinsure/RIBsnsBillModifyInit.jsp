<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIBsnsBillModifyInit.jsp
	//程序功能：账单修改
	//创建日期：2011-08-18
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">
	function initForm() {
		try {
			initMul10Grid();
			initMul11Grid();
			initMul12Grid();
			initMul13Grid();
		} catch (re) {
			myAlert("RIBsnsBillModifyInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}

	function initMul10Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "账单编号";
			iArray[1][1] = "40px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "再保合同";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "再保公司";
			iArray[3][1] = "60px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "借贷形式";
			iArray[4][1] = "40px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "起始日期";
			iArray[5][1] = "60px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "终止日期";
			iArray[6][1] = "60px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "账单状态";
			iArray[7][1] = "40px";
			iArray[7][2] = 100;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "批次号";
			iArray[8][1] = "0px";
			iArray[8][2] = 100;
			iArray[8][3] = 3;
			
			iArray[9] = new Array();
			iArray[9][0] = "幣種";
			iArray[9][1] = "40px";
			iArray[9][2] = 100;
			iArray[9][3] = 0;
			
			iArray[10] = new Array();
			iArray[10][0] = "DCType";
			iArray[10][1] = "0px";
			iArray[10][2] = 100;
			iArray[10][3] = 3;

			Mul10Grid = new MulLineEnter("fm", "Mul10Grid");

			Mul10Grid.mulLineCount = 0;
			Mul10Grid.displayTitle = 1;
			Mul10Grid.canSel = 1;
			Mul10Grid.canChk = 0;
			Mul10Grid.hiddenPlus = 1;
			Mul10Grid.hiddenSubtraction = 1;
			Mul10Grid.selBoxEventFuncName = "queryBillDetail";

			Mul10Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
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
			iArray[1][0] = "费用项名称";
			iArray[1][1] = "75px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ribillfeecode";
			iArray[1][5]="2|1|3|7"; 	 			//将引用代码分别放在第1、2
			iArray[1][6]="0|1|2|4";	
			iArray[1][15]="billno";
			iArray[1][17]="6";
			iArray[1][19]=1;
			
			iArray[2] = new Array();
			iArray[2][0] = "费用项编码";
			iArray[2][1] = "0px";
			iArray[2][2] = 100;
			iArray[2][3] = 3;
			
			iArray[3] = new Array();
			iArray[3][0] = "录入类型";
			iArray[3][1] = "30px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "费用项值";
			iArray[4][1] = "60px";
			iArray[4][2] = 100;
			iArray[4][3] = 1;
			
			iArray[5] = new Array();
			iArray[5][0] = "费用项值备份";
			iArray[5][1] = "0px";
			iArray[5][2] = 100;
			iArray[5][3] = 3;
			
			iArray[6] = new Array();
			iArray[6][0] = "账单编号";
			iArray[6][1] = "0px";
			iArray[6][2] = 100;
			iArray[6][3] = 3;
			
			iArray[7] = new Array();
			iArray[7][0] = "inputtype";
			iArray[7][1] = "0px";
			iArray[7][2] = 100;
			iArray[7][3] = 3;
			
			Mul11Grid = new MulLineEnter("fm", "Mul11Grid");

			Mul11Grid.mulLineCount = 0;
			Mul11Grid.displayTitle = 1;
			Mul11Grid.canSel = 0;
			Mul11Grid.canChk = 0;
			Mul11Grid.hiddenPlus = 0;
			Mul11Grid.hiddenSubtraction = 0;
			Mul11Grid.addEventFuncName="addbillno";
			Mul11Grid.addEventFuncParm = "1";
			
			Mul11Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
	function initMul12Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "费用项名称";
			iArray[1][1] = "75px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ribillfeecode";
			iArray[1][5]="2|1|3|8|10"; 	 			//将引用代码分别放在第1、2
			iArray[1][6]="0|1|2|3|4";	
			iArray[1][15]="billno";
			iArray[1][17]="9"; 
			iArray[1][19]=1;
			
			iArray[2] = new Array();
			iArray[2][0] = "费用项编码";
			iArray[2][1] = "0px";
			iArray[2][2] = 100;
			iArray[2][3] = 3;

			iArray[3] = new Array();
			iArray[3][0] = "录入类型";
			iArray[3][1] = "30px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "借";
			iArray[4][1] = "30px";
			iArray[4][2] = 100;
			iArray[4][3] = 1;

			iArray[5] = new Array();
			iArray[5][0] = "贷";
			iArray[5][1] = "30px";
			iArray[5][2] = 100;
			iArray[5][3] = 1;

			iArray[6] = new Array();
			iArray[6][0] = "借备份";
			iArray[6][1] = "0px";
			iArray[6][2] = 100;
			iArray[6][3] = 3;
			
			iArray[7] = new Array();
			iArray[7][0] = "贷备份";
			iArray[7][1] = "0px";
			iArray[7][2] = 100;
			iArray[7][3] = 3;
			
			iArray[8] = new Array();
			iArray[8][0] = "借贷类型";
			iArray[8][1] = "0px";
			iArray[8][2] = 100;
			iArray[8][3] = 3;
			
			iArray[9] = new Array();
			iArray[9][0] = "账单编号";
			iArray[9][1] = "0px";
			iArray[9][2] = 100;
			iArray[9][3] = 3;

			iArray[10] = new Array();
			iArray[10][0] = "inputtype";
			iArray[10][1] = "0px";
			iArray[10][2] = 100;
			iArray[10][3] = 3;
			
			Mul12Grid = new MulLineEnter("fm", "Mul12Grid");

			Mul12Grid.mulLineCount = 0;
			Mul12Grid.displayTitle = 1;
			Mul12Grid.canSel = 0;
			Mul12Grid.canChk = 0;
			Mul12Grid.hiddenPlus = 0;
			Mul12Grid.hiddenSubtraction = 0;
			Mul12Grid.addEventFuncName="addbillno";
			Mul12Grid.addEventFuncParm = "2";
			
			Mul12Grid.loadMulLine(iArray);

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
			iArray[1][0] = "信息项名称";
			iArray[1][1] = "75px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ribillinfocode";
			iArray[1][5]="2|1|3|7"; 	 			//将引用代码分别放在第1、2
			iArray[1][6]="0|1|2|3";	
			iArray[1][15]="billno";
			iArray[1][17]='6'; 
			iArray[1][19]=1;

			iArray[2] = new Array();
			iArray[2][0] = "信息项编码";
			iArray[2][1] = "0px";
			iArray[2][2] = 100;
			iArray[2][3] = 3;
		
			iArray[3] = new Array();
			iArray[3][0] = "录入类型";
			iArray[3][1] = "30px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;
			
			iArray[4] = new Array();
			iArray[4][0] = "信息项值";
			iArray[4][1] = "60px";
			iArray[4][2] = 100;
			iArray[4][3] = 1;
			
			iArray[5] = new Array();
			iArray[5][0] = "信息项值备份";
			iArray[5][1] = "0px";
			iArray[5][2] = 100;
			iArray[5][3] = 3;
			
			iArray[6] = new Array();
			iArray[6][0] = "账单编号";
			iArray[6][1] = "0px";
			iArray[6][2] = 100;
			iArray[6][3] = 3;
			
			iArray[7] = new Array();
			iArray[7][0] = "inputtype";
			iArray[7][1] = "0px";
			iArray[7][2] = 100;
			iArray[7][3] = 3;

			Mul13Grid = new MulLineEnter("fm", "Mul13Grid");

			Mul13Grid.mulLineCount = 0;
			Mul13Grid.displayTitle = 1;
			Mul13Grid.canSel = 0;
			Mul13Grid.canChk = 0;
			Mul13Grid.hiddenPlus = 0;
			Mul13Grid.hiddenSubtraction = 0;
			Mul13Grid.addEventFuncName="addbillno";
			Mul13Grid.addEventFuncParm = "3";
			
			Mul13Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
