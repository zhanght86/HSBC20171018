<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RITempContShowInit.jsp
	//程序功能：临分审核查询
	//创建日期：2011-11-09
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">
	//输入框的初始化（单记录部分）
	function initInpBox() {

		//针对险种或针对责任转换标志 
		fm.DeTailFlag.value = "2"; //1-到险种 2-到责任 
	}

	// 下拉列表的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("在ReInsureInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			//initInpBox();
			//initSelBox();
			initIndTempToalListGrid();
			initIndTempListGrid();
			//显示个险临分申请记录
			//QueryPolInfo();

		} catch (re) {
			myAlert("LRTempInsuManInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
		//ReInsureAudit();
		//QueryReInsureAudit();
	}
	//保单基本信息
	function initIndTempToalListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列為順序號，列名無意義，而且不顯示）
			iArray[0][1] = "30px"; //列寬
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[1] = new Array();
			iArray[1][0] = "总单投保单号码"; //列名
			iArray[1][1] = "0px"; //列寬
			iArray[1][2] = 100; //列最大值
			iArray[1][3] = 3; //是否允許輸入,1表示允許，0表示不允許

			iArray[2] = new Array();
			iArray[2][0] = "投保单号码"; //列名
			iArray[2][1] = "100px"; //列寬
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[3] = new Array();
			iArray[3][0] = "保单状态"; //列名
			iArray[3][1] = "60px"; //列寬
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[4] = new Array();
			iArray[4][0] = "险种号"; //列名
			iArray[4][1] = "60px"; //列寬
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[5] = new Array();
			iArray[5][0] = "被保人"; //列名
			iArray[5][1] = "60px"; //列寬
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[6] = new Array();
			iArray[6][0] = "保費"; //列名
			iArray[6][1] = "60px"; //列寬
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[7] = new Array();
			iArray[7][0] = "保額"; //列名
			iArray[7][1] = "60px"; //列寬
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[8] = new Array();
			iArray[8][0] = "风险保额"; //列名
			iArray[8][1] = "60px"; //列寬
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[9] = new Array();
			iArray[9][0] = "任务类型"; //列名
			iArray[9][1] = "60px"; //列寬
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[10] = new Array();
			iArray[10][0] = "保单状态保单状态号码"; //列名
			iArray[10][1] = "0px"; //列寬
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 3; //是否允許輸入,1表示允許，0表示不允許

			iArray[11] = new Array();
			iArray[11][0] = "序列号"; //列名
			iArray[11][1] = "0px"; //列寬
			iArray[11][2] = 100; //列最大值
			iArray[11][3] = 3; //是否允許輸入,1表示允許，0表示不允許

			IndTempToalListGrid = new MulLineEnter("fm", "IndTempToalListGrid");
			IndTempToalListGrid.mulLineCount = 0;
			IndTempToalListGrid.displayTitle = 1;
			IndTempToalListGrid.locked = 1;
			IndTempToalListGrid.hiddenPlus = 1;
			IndTempToalListGrid.canSel = 1;
			IndTempToalListGrid.canChk = 0;
			IndTempToalListGrid.selBoxEventFuncName = "listSelect";
			IndTempToalListGrid.hiddenSubtraction = 1;
			IndTempToalListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
	//个人保单信息
	function initIndTempListGrid() {
		var iArray = new Array();
		try {

			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[1] = new Array();
			iArray[1][0] = "保单号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "30px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[2] = new Array();
			iArray[2][0] = "保单险种号"; //列名
			iArray[2][1] = "50px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[3] = new Array();
			iArray[3][0] = "被保险人"; //列名
			iArray[3][1] = "50px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[4] = new Array();
			iArray[4][0] = "险种代码"; //列名
			iArray[4][1] = "50px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[5] = new Array();
			iArray[5][0] = "责任编码"; //列名
			iArray[5][1] = "50px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[6] = new Array();
			iArray[6][0] = "保費"; //列名
			iArray[6][1] = "50px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[7] = new Array();
			iArray[7][0] = "保額"; //列名
			iArray[7][1] = "50px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[8] = new Array();
			iArray[8][0] = "风险保额"; //列名
			iArray[8][1] = "60px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 3; //是否允许输入,1表示允许，0表示不允许

			iArray[9] = new Array();
			iArray[9][0] = "核保结论"; //列名
			iArray[9][1] = "30px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[10] = new Array();
			iArray[10][0] = "核保结编码"; //列名
			iArray[10][1] = "60px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 3; //是否允许输入,1表示允许，0表示不允许

			iArray[11] = new Array();
			iArray[11][0] = "临分合同"; //列名
			iArray[11][1] = "100px"; //列寬
			iArray[11][2] = 100; //列最大值
			iArray[11][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			iArray[12] = new Array();
			iArray[12][0] = "临分方案"; //列名
			iArray[12][1] = "100px"; //列寬
			iArray[12][2] = 100; //列最大值
			iArray[12][3] = 0; //是否允許輸入,1表示允許，0表示不允許

			IndTempListGrid = new MulLineEnter("fm", "IndTempListGrid");
			//这些属性必须在loadMulLine前
			IndTempListGrid.mulLineCount = 0;
			IndTempListGrid.displayTitle = 1;
			IndTempListGrid.locked = 1;
			IndTempListGrid.hiddenPlus = 1;
			IndTempListGrid.canSel = 0;
			IndTempListGrid.canChk = 0;
			//IndTempListGrid.selBoxEventFuncName = "listSelect"; 
			IndTempListGrid.hiddenSubtraction = 1;
			IndTempListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
