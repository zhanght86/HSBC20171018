<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIContQueryAsComInit.jsp
	//程序功能：合同查询
	//创建日期：2011-7-10
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">
	function initForm() {
		try {
			initMul10Grid();
			initMul11Grid();
		} catch (re) {
			myAlert("RIContQueryAsComInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
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
			iArray[1][0] = "再保公司编号";
			iArray[1][1] = "45px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "再保公司名称";
			iArray[2][1] = "90px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "再保合同编号";
			iArray[3][1] = "45px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "再保合同名称";
			iArray[4][1] = "90px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			Mul10Grid = new MulLineEnter("fm", "Mul10Grid");

			Mul10Grid.mulLineCount = 0;
			Mul10Grid.displayTitle = 1;
			Mul10Grid.canSel = 1;
			Mul10Grid.hiddenPlus = 1;
			Mul10Grid.hiddenSubtraction = 1;
		    Mul10Grid.selBoxEventFuncName = "queryPrecept";
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
            iArray[1][0] = "再保合同编号";
            iArray[1][1] = "30px";
            iArray[1][2] = 100;
            iArray[1][3] = 0;

            iArray[2] = new Array();
            iArray[2][0] = "再保合同名称";
            iArray[2][1] = "70px";
            iArray[2][2] = 100;
            iArray[2][3] = 0;

            iArray[3] = new Array();
            iArray[3][0] = "再保方案编号";
            iArray[3][1] = "30px";
            iArray[3][2] = 100;
            iArray[3][3] = 0;

            iArray[4] = new Array();
            iArray[4][0] = "再保方案名称";
            iArray[4][1] = "70px";
            iArray[4][2] = 100;
            iArray[4][3] = 0;

            iArray[5] = new Array();
            iArray[5][0] = "区域下限";
            iArray[5][1] = "25px";
            iArray[5][2] = 100;
            iArray[5][3] = 0;

            iArray[6] = new Array();
            iArray[6][0] = "区域上限";
            iArray[6][1] = "25px";
            iArray[6][2] = 100;
            iArray[6][3] = 0;

            iArray[7] = new Array();
            iArray[7][0] = "分保比例";
            iArray[7][1] = "20px";
            iArray[7][2] = 100;
            iArray[7][3] = 0;

            iArray[8] = new Array();
            iArray[8][0] = "分保费率表";
            iArray[8][1] = "35px";
            iArray[8][2] = 100;
            iArray[8][3] = 0;
            
            iArray[9] = new Array();
            iArray[9][0] = "佣金费率表";
            iArray[9][1] = "35px";
            iArray[9][2] = 100;
            iArray[9][3] = 0;

            Mul11Grid = new MulLineEnter("fm", "Mul11Grid");

            Mul11Grid.mulLineCount = 0;
            Mul11Grid.displayTitle = 1;
            Mul11Grid.canSel = 0;
            Mul11Grid.hiddenPlus = 1;
            Mul11Grid.hiddenSubtraction = 1;
            
            Mul11Grid.loadMulLine(iArray);

        } catch (ex) {
            myAlert(ex);
        }
    }
</script>
