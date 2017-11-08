<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：LRRelaTempPolInit.jsp
	//程序功能：关联责任保单
	//创建日期：2007-09-29 11:10:36
	//创建人  ：zhangbin
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
	String tRIContNo = request.getParameter("RIContNo");
	String tRIPreceptNo = request.getParameter("RIPreceptNo");
	String tCalFeeType = request.getParameter("CalFeeType");
	String tCalFeeTerm = request.getParameter("CalFeeTerm");
	String tOperateNo = request.getParameter("OperateNo");
	String tCodeType = request.getParameter("CodeType");
	String tSerialNo = request.getParameter("SerialNo");
	String RIPolno = request.getParameter("RIPolno");
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">


// 输入框的初始化（单记录部分）
function initInpBox(){
	fm.RIContNo.value ='<%=tRIContNo%>';
	fm.RIPreceptNo.value ='<%=tRIPreceptNo%>';
	fm.CalFeeType.value ='<%=tCalFeeType%>';
	fm.CalFeeTerm.value ='<%=tCalFeeTerm%>';
	fm.OperateNo.value ='<%=tOperateNo%>';
	fm.OperateType.value ='<%=tCodeType%>';
	fm.SerialNo.value ='<%=tSerialNo%>';
	fm.RIPolno.value ='<%=RIPolno%>';
		//divGrpTempInsuListTitle.style.display='';

		//针对险种或针对责任转换标志 
		fm.DeTailFlag.value = "2"; //1-到险种 2-到责任 

	}

	// 下拉框的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("在ReInsureInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			//initSelBox();
			initIndTempListGrid();
			initIndRelaListGrid();

			QueryPagePolInfo(); //显示临分保单信息
		} catch (re) {
			myAlert("TempReInAnswerInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}

	function initIndTempListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[1] = new Array();
			iArray[1][0] = "保单号"; //列名
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 100; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[2] = new Array();
			iArray[2][0] = "被保险人"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[3] = new Array();
			iArray[3][0] = "险种代码"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[4] = new Array();
			iArray[4][0] = "责任编码"; //列名
			iArray[4][1] = "60px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[5] = new Array();
			iArray[5][0] = "保費"; //列名
			iArray[5][1] = "60px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[6] = new Array();
			iArray[6][0] = "保額"; //列名
			iArray[6][1] = "60px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[7] = new Array();
			iArray[7][0] = "风险保额"; //列名
			iArray[7][1] = "60px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[8] = new Array();
			iArray[8][0] = "投保单号"; //列名
			iArray[8][1] = "100px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[9] = new Array();
			iArray[9][0] = "核保结论"; //列名
			iArray[9][1] = "100px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[10] = new Array();
			iArray[10][0] = "被保险人code"; //列名
			iArray[10][1] = "0px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 3; //是否允许输入,1表示允许，0表示不允许

			IndTempListGrid = new MulLineEnter("fm", "IndTempListGrid");
			//这些属性必须在loadMulLine前
			IndTempListGrid.mulLineCount = 0;
			IndTempListGrid.displayTitle = 1;
			IndTempListGrid.locked = 1;
			IndTempListGrid.hiddenPlus = 1;
			IndTempListGrid.canSel = 0;
			IndTempListGrid.canChk = 1;
			IndTempListGrid.hiddenSubtraction = 1;
			IndTempListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	function initIndRelaListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[1] = new Array();
			iArray[1][0] = "保单号"; //列名
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 100; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[2] = new Array();
			iArray[2][0] = "被保险人"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[3] = new Array();
			iArray[3][0] = "险种代码"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[4] = new Array();
			iArray[4][0] = "责任编码"; //列名
			iArray[4][1] = "60px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[5] = new Array();
			iArray[5][0] = "保費"; //列名
			iArray[5][1] = "60px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[6] = new Array();
			iArray[6][0] = "保額"; //列名
			iArray[6][1] = "60px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[7] = new Array();
			iArray[7][0] = "风险保额"; //列名
			iArray[7][1] = "60px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[8] = new Array();
			iArray[8][0] = "投保单号"; //列名
			iArray[8][1] = "100px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[9] = new Array();
			iArray[9][0] = "核保结论"; //列名
			iArray[9][1] = "100px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[10] = new Array();
			iArray[10][0] = "被保险人code"; //列名
			iArray[10][1] = "80px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 3; //是否允许输入,1表示允许，0表示不允许

			iArray[11] = new Array();
			iArray[11][0] = "再保方案编号";//列名
			iArray[11][1] = "60px"; //列宽
			iArray[11][2] = 100; //列最大值
			iArray[11][3] = 0; //是否允许输入,1表示允许，0表示不允许

			IndRelaListGrid = new MulLineEnter("fm", "IndRelaListGrid");
			//这些属性必须在loadMulLine前
			IndRelaListGrid.mulLineCount = 0;
			IndRelaListGrid.displayTitle = 1;
			IndRelaListGrid.locked = 1;
			IndRelaListGrid.hiddenPlus = 1;
			IndRelaListGrid.canSel = 0;
			IndRelaListGrid.canChk = 1;
			IndRelaListGrid.hiddenSubtraction = 1;
			IndRelaListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	function initRelaListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[1] = new Array();
			iArray[1][0] = "印刷号码"; //列名
			iArray[1][1] = "100px"; //列宽
			iArray[1][2] = 100; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[2] = new Array();
			iArray[2][0] = "集体投保单号码"; //列名
			iArray[2][1] = "100px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[3] = new Array();
			iArray[3][0] = "保障计划"; //列名
			iArray[3][1] = "60px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[4] = new Array();
			iArray[4][0] = "个人合同号码"; //列名
			iArray[4][1] = "100px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[5] = new Array();
			iArray[5][0] = "投保单险种号码"; //列名
			iArray[5][1] = "100px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[6] = new Array();
			iArray[6][0] = "被保人姓名"; //列名
			iArray[6][1] = "60px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[7] = new Array();
			iArray[7][0] = "被保人号码"; //列名
			iArray[7][1] = "100px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[8] = new Array();
			iArray[8][0] = "险种代码"; //列名
			iArray[8][1] = "70px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[9] = new Array();
			iArray[9][0] = "责任代码"; //列名
			iArray[9][1] = "70px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[10] = new Array();
			iArray[10][0] = "关联方式"; //列名
			iArray[10][1] = "120px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[11] = new Array();
			iArray[11][0] = "临分核保结论"; //列名
			iArray[11][1] = "100px"; //列宽
			iArray[11][2] = 100; //列最大值
			iArray[11][3] = 3; //是否允许输入,1表示允许，0表示不允许

			iArray[12] = new Array();
			iArray[12][0] = "关联方式编号"; //列名
			iArray[12][1] = "100px"; //列宽
			iArray[12][2] = 100; //列最大值
			iArray[12][3] = 3; //是否允许输入,1表示允许，0表示不允许

			RelaListGrid = new MulLineEnter("fm", "RelaListGrid");
			//这些属性必须在loadMulLine前
			RelaListGrid.mulLineCount = 0;
			RelaListGrid.displayTitle = 1;
			RelaListGrid.locked = 1;
			RelaListGrid.hiddenPlus = 1;
			RelaListGrid.canSel = 0;
			RelaListGrid.canChk = 1;
			RelaListGrid.hiddenSubtraction = 1;
			RelaListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
