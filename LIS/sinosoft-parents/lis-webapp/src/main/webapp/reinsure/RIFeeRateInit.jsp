<%@include file="/i18n/language.jsp"%>

<%
	//Creator :张斌
	//Date :2006-08-21
%>

<script type="text/javascript">
	function initInpBox() {
		try {
			fm.FeeTableNo.value = "";
			fm.FeeTableName.value = "";
			fm.FeeTableType.value = "";
			fm.ReMark.value = "";
			fm.State.value = "02";
			fm.stateName.value = "未生效";
			fm.FeeTableType.value = "";
			fm.FeeTableTypeName.value = "";
			fm.UseType.value = "";
			fm.UseTypeName.value = "";

		} catch (ex) {
			myAlert("进行初始化是出现错误！");
		}
	};

	// 下拉列表的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("在LRContInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			initTableClumnDefGrid();
			verifyFeeRateTableImp();
		} catch (re) {
			myAlert("ReContManageInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}

	//再保合同签订人列表初始化
	function initTableClumnDefGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 10;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "费率表字段";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 1;
			iArray[1][9] = "费率表字段名|notnull";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "界面表字段名";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 2;
			iArray[2][4] = "ripolrecordcols";
			iArray[2][5] = "2"; //将引用代码分别放在第1、2
			iArray[2][6] = "0|1";
			iArray[2][9] = "界面表字段|notnull";
			iArray[2][19] = 1;

			iArray[3] = new Array();
			iArray[3][0] = "费率表字段类型名";
			iArray[3][1] = "80px";
			iArray[3][2] = 100;
			iArray[3][3] = 2;
			iArray[3][4] = "rifeecoltype";
			iArray[3][5] = "3|4"; //将引用代码分别放在第1、2
			iArray[3][6] = "1|0";
			iArray[3][19] = 1;

			iArray[4] = new Array();
			iArray[4][0] = "费率表字段类型";
			iArray[4][1] = "80px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;
			iArray[4][9] = "界面表字段|notnull";
			iArray[4][19] = 1;

			iArray[5] = new Array();
			iArray[5][0] = "固定值映射字段名"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 200; //列最大值
			iArray[5][3] = 2; //是否允许输入,1表示允许，0表示不允许   
			iArray[5][4] = "riimpcols";
			iArray[5][5] = "5|6"; //将引用代码分别放在第1、2
			iArray[5][6] = "0|1";
			iArray[5][19] = 1;

			iArray[6] = new Array();
			iArray[6][0] = "固定值映射字段";
			iArray[6][1] = "0px";
			iArray[6][2] = 200;
			iArray[6][3] = 3;
			iArray[6][19] = 1;

			iArray[7] = new Array();
			iArray[7][0] = "区间下限映射字段名"; //列名
			iArray[7][1] = "80px"; //列宽
			iArray[7][2] = 60; //列最大值
			iArray[7][3] = 2; //2表示是代码选择
			iArray[7][4] = "riimpcols";
			iArray[7][5] = "7|8"; //将引用代码分别放在第1、2
			iArray[7][6] = "0|1";
			iArray[7][19] = 1;

			iArray[8] = new Array();
			iArray[8][0] = "区间下限字段映射";
			iArray[8][1] = "0px";
			iArray[8][2] = 200;
			iArray[8][3] = 3;
			iArray[8][19] = 1;

			iArray[9] = new Array();
			iArray[9][0] = "区间上限字段映射名"; //列名
			iArray[9][1] = "90px"; //列宽
			iArray[9][2] = 60; //列最大值
			iArray[9][3] = 2; //是否允许输入,1表示允许，0表示不允许
			iArray[9][4] = "riimpcols";
			iArray[9][5] = "9|10"; //将引用代码分别放在第1、2
			iArray[9][6] = "0|1";
			iArray[9][19] = 1;

			iArray[10] = new Array();
			iArray[10][0] = "区间上限字段映射";
			iArray[10][1] = "0px";
			iArray[10][2] = 200;
			iArray[10][3] = 3;
			iArray[10][19] = 1;

			TableClumnDefGrid = new MulLineEnter("fm", "TableClumnDefGrid");
			TableClumnDefGrid.mulLineCount = 1;
			TableClumnDefGrid.displayTitle = 1;
			//  TableClumnDefGrid.locked = 0;
			TableClumnDefGrid.canSel = 0;
			//  TableClumnDefGrid.hiddenPlus=0; 
			//  TableClumnDefGrid.hiddenSubtraction=1;
			TableClumnDefGrid.loadMulLine(iArray);
		}

		catch (ex) {
			myAlert("初始化时出错:" + ex);
		}
	}
</script>
