<%
	//程序名称：CommandFunInit.jsp
	//程序功能：对运算符的初始化
	//创建日期：2008-9-17
	//创建人  ：
	//更新记录：
%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<%
	//添加页面控件的初始化。
%>

<script language="JavaScript">
	function initInpBox() {
		try {	
			
		} catch (ex) {

		}
	}

	function initSelBox() {
		try {
		
		} catch (ex) {
			alert("在CommandFunInit.jspInitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
		//	alert('1');
			initInpBox();
	//		alert('2');
			initSelBox();
	//		alert('3');
			initQueryGrpGrid();			
	//		alert('4');
		} catch (re) {
			alert("CommandFunInit.jspInitForm函数中发生异常:初始化界面错误!");
		}
	}

	
	function initQueryGrpGrid() {

		var iArray = new Array();

		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "运算符名称"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[2] = new Array();
			iArray[2][0] = "界面展示"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[3] = new Array();
			iArray[3][0] = "技术实现"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[4] = new Array();
			iArray[4][0] = "有效性"; //列名
			iArray[4][1] = "80px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "运算数据类型"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "运算结果类型"; //列名
			iArray[6][1] = "80px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[6][4] = "IbrmsResulttype";

			iArray[7] = new Array();
			iArray[7][0] = "连接参数的类型"; //列名
			iArray[7][1] = "100px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[7][4] = "IbrmsParatype";

			iArray[8] = new Array();
			iArray[8][0] = "参数个数"; //列名
			iArray[8][1] = "60px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 1; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
			iArray[9] = new Array();
			iArray[9][0] = "描述信息"; //列名
			iArray[9][1] = "180px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[10] = new Array();
			iArray[10][0] = "运算符类型"; //列名
			iArray[10][1] = "80px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[10][4] = "IbrmsCommType"; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
		
			QueryGrpGrid = new MulLineEnter("document", "QueryGrpGrid");

			//这些属性必须在loadMulLine前
			QueryGrpGrid.mulLineCount = 5;
			QueryGrpGrid.displayTitle = 1;
			QueryGrpGrid.canChk = 0;
			QueryGrpGrid.canSel = 1;
			QueryGrpGrid.locked = 1; //是否锁定：1为锁定 0为不锁定
			QueryGrpGrid.hiddenPlus = 1; //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
			QueryGrpGrid.hiddenSubtraction = 1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
			QueryGrpGrid.recordNo = 0; //设置序号起始基数为10，如果要分页显示数据有用
		
			QueryGrpGrid.loadMulLine(iArray);
		} catch (ex) {
			alert(ex);
		}
	}
</script>
