<script type="text/JavaScript"><!--
var turnPage=new turnPageClass();
var turnPage2=new turnPageClass();
function initForm()
{
	initMainGrid();
	initDetailGrid();
	}
function initMainGrid() {

	var iArray = new Array();
	try {
		iArray[0] = new Array();
		iArray[0][0] = "序号";
		iArray[0][1] = "30px"; 
		iArray[0][2] = 10; 
		iArray[0][3] = 0;

		iArray[1] = new Array();
		iArray[1][0] = "批次号";
		iArray[1][1] = "100px"; 
		iArray[1][2] = 10; 
		iArray[1][3] = 0;
		
		iArray[2] = new Array();
		iArray[2][0] = "业务模块"; //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[2][1] = "50px"; //列宽
		iArray[2][2] = 10; //列最大值
		iArray[2][3] = 2; //是否允许输入,1表示允许，0表示不允许
		iArray[2][4] = "ibrmsbusiness";

		iArray[3] = new Array();
		iArray[3][0] = "保单号"; //列名
		iArray[3][1] = "50px"; //列宽
		iArray[3][2] = 100; //列最大值
		iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

		iArray[4] = new Array();
		iArray[4][0] = "消耗时间(单位:S)"; //列名
		iArray[4][1] = "100px"; //列宽
		iArray[4][2] = 100; //列最大值
		iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

		iArray[5] = new Array();
		iArray[5][0] = "结果标志"; //列名
		iArray[5][1] = "50px"; //列宽
		iArray[5][2] = 100; //列最大值
		iArray[5][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[5][4] = "";
		
		iArray[6] = new Array();
		iArray[6][0] = "操作员"; //列名
		iArray[6][1] = "50px"; //列宽
		iArray[6][2] = 100; //列最大值
		iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		
		iArray[7] = new Array();
		iArray[7][0] = "执行日期"; //列名
		iArray[7][1] = "80px"; //列宽
		iArray[7][2] = 100; //列最大值
		iArray[7][3] = 8; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

		iArray[8] = new Array();
		iArray[8][0] = "执行时间"; //列名
		iArray[8][1] = "50px"; //列宽
		iArray[8][2] = 100; //列最大值
		iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

		iArray[9] = new Array();
		iArray[9][0] = "处理类机构"; //列名
		iArray[9][1] = "80px"; //列宽
		iArray[9][2] = 100; //列最大值
		iArray[9][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[9][4] = "";

		/*iArray[9] = new Array();
		iArray[9][0] = "结果标志"; //列名
		iArray[9][1] = "80px"; //列宽
		iArray[9][2] = 100; //列最大值
		iArray[9][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		//iArray[9][4] = "ibrmsvalid";
		*/
		
		MainGrid = new MulLineEnter( "document" , "MainGrid" );
 //这些属性必须在loadMulLine前
  MainGrid.mulLineCount = 5;
  MainGrid.displayTitle = 1;
  MainGrid.hiddenPlus = 1;
  MainGrid.hiddenSubtraction = 1;
  MainGrid.canSel= 1;
  MainGrid.canChk =0;
  MainGrid.selBoxEventFuncName="queryDetails";
  MainGrid.loadMulLine(iArray);
	} catch (ex) {
		alert(ex);
	}
}
function initDetailGrid()
{
	var iArray = new Array();
	try {
		iArray[0] = new Array();
		iArray[0][0] = "序号";
		iArray[0][1] = "30px"; 
		iArray[0][2] = 10; 
		iArray[0][3] = 0;

		iArray[1] = new Array();
		iArray[1][0] = "批次号";
		iArray[1][1] = "80px"; 
		iArray[1][2] = 10; 
		iArray[1][3] = 0;
		
		iArray[2] = new Array();
		iArray[2][0] = "执行顺序号"; //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[2][1] = "80px"; //列宽
		iArray[2][2] = 10; //列最大值
		iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许
		//iArray[1][4] = "ibrmsresulttype";

		iArray[3] = new Array();
		iArray[3][0] = "规则模板标识"; //列名
		iArray[3][1] = "80px"; //列宽
		iArray[3][2] = 100; //列最大值
		iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[3][4] = "ibrmsbusiness";

		iArray[4] = new Array();
		iArray[4][0] = "版本号"; //列名
		iArray[4][1] = "30px"; //列宽
		iArray[4][2] = 100; //列最大值
		iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[4][4] = "ibrmstemplatelevel";

		iArray[5] = new Array();
		iArray[5][0] = "规则标识"; //列名
		iArray[5][1] = "40px"; //列宽
		iArray[5][2] = 100; //列最大值
		iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		
		iArray[6] = new Array();
		iArray[6][0] = "自核结果"; //列名
		iArray[6][1] = "80px"; //列宽
		iArray[6][2] = 100; //列最大值
		iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		
		iArray[7] = new Array();
		iArray[7][0] = "人工核保级别"; //列名
		iArray[7][1] = "80px"; //列宽
		iArray[7][2] = 100; //列最大值
		iArray[7][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[7][4] = "";
		
		iArray[8] = new Array();
		iArray[8][0] = "执行异常标志"; //列名
		iArray[8][1] = "80px"; //列宽
		iArray[8][2] = 100; //列最大值
		iArray[8][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[8][4] = "";

		
		DetailGrid = new MulLineEnter( "document" , "DetailGrid" );
 //这些属性必须在loadMulLine前
  DetailGrid.mulLineCount = 5;
  DetailGrid.displayTitle = 1;
  DetailGrid.hiddenPlus = 1;
  DetailGrid.hiddenSubtraction = 1;
  DetailGrid.canSel= 1;
  DetailGrid.canChk =0;
  DetailGrid.loadMulLine(iArray);
	} catch (ex) {
		alert(ex);
	}
}

--></script>
