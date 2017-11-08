<%@include file="/i18n/language.jsp"%>

<%
	//Creator :张斌
	//Date :2007-2-7
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String CurrentDate = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	String Operator = tG.Operator;
%>

<script type="text/javascript">
	function initInpBox() {
		try {
			fm.all("BFFlag").value = "01";
			fm.all("BFFlagName").value = "业务标记";
		} catch (ex) {
			myAlert("进行初始化是出现错误！！！！");
		}
	}

	// 下拉列表的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("2在LRGetBsnsDataInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			initRIDataGrid();
			initRIDataDetailGrid();
			initRIDataUCoDetailGrid();
		} catch (re) {
			myAlert("3在LRGetBsnsDataInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}

	// 接口业务数据
	function initRIDataGrid() {
		var iArray = new Array();
		try {
			var i=0;
			
			iArray[i] = new Array();
			iArray[i][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[i][1] = "30px"; //列宽
			iArray[i][2] = 40; //列最大值
			iArray[i][3] = 0; //是否允许输入,1表示允许，0表示不允许
			i++;   			     

			iArray[i] = new Array();
			iArray[i][0] = "EventNo";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 3;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "保单号";
			iArray[i][1] = "80px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "业务类型";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "业务明细";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "险种编码";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "责任编码";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "被保人客户号";
			iArray[i][1] = "100px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "被保人姓名";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "被保人性别";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "当前年龄";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "吸烟标志";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "职业类别";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "保单生效日";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "保单年度";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "核保類型";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 3;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "保障计划";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 3;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "再保/共保";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "再保合同编号";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "再保方案编号";
			iArray[i][1] = "100px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "分出责任编号";
			iArray[i][1] = "100px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="标准保费";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="EM加费金额";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="EM加费评点";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="$/M加费金额";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="$/M加费评点";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="%加费金额";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="%加费评点";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "当前风险保额";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "当前币种";
			iArray[i][1] = "50px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "风险保额变化量";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "转换汇率";
			iArray[i][1] = "45px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "转换后风险保额";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "累计币种";
			iArray[i][1] = "50px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "累计风险保额";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "理赔额";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "业务日期";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "计算日期";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "锁定状态";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			RIDataGrid = new MulLineEnter("fm", "RIDataGrid");
			//这些属性必须在loadMulLine前
			RIDataGrid.mulLineCount = 1;
			RIDataGrid.displayTitle = 1;
			RIDataGrid.locked = 0;
			RIDataGrid.canSel = 1; // 1 显示 ；0 隐藏（缺省值）
			RIDataGrid.selBoxEventFuncName = "queryDetail"; //响应的函数名，不加扩号   
			RIDataGrid.hiddenPlus = 1;
			RIDataGrid.hiddenSubtraction = 1;
			RIDataGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	// 分保详细信息
	function initRIDataDetailGrid() {
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
			iArray[2][0] = "险种编码";
			iArray[2][1] = "80px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "责任编码";
			iArray[3][1] = "80px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "分保公司编码";
			iArray[4][1] = "80px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "分保公司名称";
			iArray[5][1] = "100px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "分保区域";
			iArray[6][1] = "80px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "分保比例";
			iArray[7][1] = "80px";
			iArray[7][2] = 85;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "分保保额";
			iArray[8][1] = "80px";
			iArray[8][2] = 85;
			iArray[8][3] = 0;

			iArray[9] = new Array();
			iArray[9][0] = "分保保额变量";
			iArray[9][1] = "80px";
			iArray[9][2] = 85;
			iArray[9][3] = 0;
			
			iArray[10] = new Array();
			iArray[10][0] = "分保费率";
			iArray[10][1] = "80px";
			iArray[10][2] = 85;
			iArray[10][3] = 0;
			
			iArray[11] = new Array();
			iArray[11][0] = "分保保费";
			iArray[11][1] = "80px";
			iArray[11][2] = 85;
			iArray[11][3] = 0;
			
			iArray[12] = new Array();
			iArray[12][0] = "4亿保费标记";
			iArray[12][1] = "80px";
			iArray[12][2] = 85;
			iArray[12][3] = 0;

			iArray[13] = new Array();
			iArray[13][0] = "分保佣金率";
			iArray[13][1] = "80px";
			iArray[13][2] = 85;
			iArray[13][3] = 0;

			iArray[14] = new Array();
			iArray[14][0] = "分保佣金1";
			iArray[14][1] = "80px";
			iArray[14][2] = 85;
			iArray[14][3] = 0;

			iArray[15] = new Array();
			iArray[15][0] = "分保佣金2";
			iArray[15][1] = "80px";
			iArray[15][2] = 85;
			iArray[15][3] = 0;

			iArray[16] = new Array();
			iArray[16][0] = "分保佣金汇总";
			iArray[16][1] = "80px";
			iArray[16][2] = 85;
			iArray[16][3] = 0;

			iArray[17] = new Array();
			iArray[17][0] = "理赔摊回金额";
			iArray[17][1] = "80px";
			iArray[17][2] = 85;
			iArray[17][3] = 0;

			iArray[18] = new Array();
			iArray[18][0] = "分保业务日期";
			iArray[18][1] = "80px";
			iArray[18][2] = 85;
			iArray[18][3] = 0;
			
			iArray[19] = new Array();
			iArray[19][0] = "锁定状态";
			iArray[19][1] = "60px";
			iArray[19][2] = 85;
			iArray[19][3] = 0;

			RIDataDetailGrid = new MulLineEnter("fm", "RIDataDetailGrid");
			//这些属性必须在loadMulLine前
			RIDataDetailGrid.mulLineCount = 0;
			RIDataDetailGrid.displayTitle = 1;
			RIDataDetailGrid.locked = 0;
			RIDataDetailGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
			//RIDataGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号   
			RIDataDetailGrid.hiddenPlus = 1;
			RIDataDetailGrid.hiddenSubtraction = 1;
			RIDataDetailGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
	
	// 万能共保分保详细信息
	function initRIDataUCoDetailGrid() {
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
			iArray[2][0] = "险种编码";
			iArray[2][1] = "80px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "责任编码";
			iArray[3][1] = "80px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "分保公司编码";
			iArray[4][1] = "80px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "分保公司名称";
			iArray[5][1] = "100px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "分保区域";
			iArray[6][1] = "80px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "分保比例";
			iArray[7][1] = "80px";
			iArray[7][2] = 85;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "分保保额";
			iArray[8][1] = "80px";
			iArray[8][2] = 85;
			iArray[8][3] = 0;

			iArray[9] = new Array();
			iArray[9][0] = "分保保额变量";
			iArray[9][1] = "80px";
			iArray[9][2] = 85;
			iArray[9][3] = 0;
			
			iArray[10] = new Array();
			iArray[10][0] = "分保费率";
			iArray[10][1] = "80px";
			iArray[10][2] = 85;
			iArray[10][3] = 0;
			
			iArray[11] = new Array();
			iArray[11][0] = "分保保费";
			iArray[11][1] = "80px";
			iArray[11][2] = 85;
			iArray[11][3] = 0;

			iArray[12] = new Array();
			iArray[12][0] = "分保佣金率";
			iArray[12][1] = "80px";
			iArray[12][2] = 85;
			iArray[12][3] = 0;

			iArray[13] = new Array();
			iArray[13][0] = "分保佣金1";
			iArray[13][1] = "80px";
			iArray[13][2] = 85;
			iArray[13][3] = 0;

			iArray[14] = new Array();
			iArray[14][0] = "分保佣金2";
			iArray[14][1] = "80px";
			iArray[14][2] = 85;
			iArray[14][3] = 0;
			
			iArray[15] = new Array();
			iArray[15][0] = "分保佣金3";
			iArray[15][1] = "80px";
			iArray[15][2] = 85;
			iArray[15][3] = 0;
			
			iArray[16] = new Array();
			iArray[16][0] = "分保佣金4";
			iArray[16][1] = "80px";
			iArray[16][2] = 85;
			iArray[16][3] = 0;

			iArray[17] = new Array();
			iArray[17][0] = "分保佣金汇总";
			iArray[17][1] = "80px";
			iArray[17][2] = 85;
			iArray[17][3] = 0;

			iArray[18] = new Array();
			iArray[18][0] = "理赔摊回金额";
			iArray[18][1] = "80px";
			iArray[18][2] = 85;
			iArray[18][3] = 0;

			iArray[19] = new Array();
			iArray[19][0] = "分保业务日期";
			iArray[19][1] = "80px";
			iArray[19][2] = 85;
			iArray[19][3] = 0;
			
			iArray[20] = new Array();
			iArray[20][0] = "锁定状态";
			iArray[20][1] = "60px";
			iArray[20][2] = 85;
			iArray[20][3] = 0;

			RIDataUCoDetailGrid = new MulLineEnter("fm", "RIDataUCoDetailGrid");
			//这些属性必须在loadMulLine前
			RIDataUCoDetailGrid.mulLineCount = 0;
			RIDataUCoDetailGrid.displayTitle = 1;
			RIDataUCoDetailGrid.locked = 0;
			RIDataUCoDetailGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
			//RIDataGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号   
			RIDataUCoDetailGrid.hiddenPlus = 1;
			RIDataUCoDetailGrid.hiddenSubtraction = 1;
			RIDataUCoDetailGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
</script>