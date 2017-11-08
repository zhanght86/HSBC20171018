<script type="text/JavaScript">
function initForm()
{
	}
function initQueryGrpGrid() {

	var iArray = new Array();
	try {
		iArray[0] = new Array();
		iArray[0][0] = "序号";
		iArray[0][1] = "30px"; 
		iArray[0][2] = 10; 
		iArray[0][3] = 0;

		iArray[1] = new Array();
		iArray[1][0] = "机构"; //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[1][1] = "80px"; //列宽
		iArray[1][2] = 10; //列最大值
		iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
		//iArray[1][4] = "ibrmsresulttype";

		iArray[2] = new Array();
		iArray[2][0] = "业务模块"; //列名
		iArray[2][1] = "80px"; //列宽
		iArray[2][2] = 100; //列最大值
		iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[2][4] = "ibrmsbusiness";

		iArray[3] = new Array();
		iArray[3][0] = "校验保单总数"; //列名
		iArray[3][1] = "80px"; //列宽
		iArray[3][2] = 100; //列最大值
		iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		iArray[3][4] = "ibrmstemplatelevel";

		iArray[4] = new Array();
		iArray[4][0] = "通过总数"; //列名
		iArray[4][1] = "80px"; //列宽
		iArray[4][2] = 100; //列最大值
		iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		
		iArray[5] = new Array();
		iArray[5][0] = "校验通过率"; //列名
		iArray[5][1] = "80px"; //列宽
		iArray[5][2] = 100; //列最大值
		iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		
  QueryGrpGrid = new MulLineEnter( "document" , "QueryGrpGrid" );
 //这些属性必须在loadMulLine前
  QueryGrpGrid.mulLineCount = 5;
  QueryGrpGrid.displayTitle = 1;
  QueryGrpGrid.hiddenPlus = 1;
  QueryGrpGrid.hiddenSubtraction = 1;
  QueryGrpGrid.canSel= 1;
  QueryGrpGrid.canChk =0;
  QueryGrpGrid.loadMulLine(iArray);
		
		
	} catch (ex) {
		alert(ex);
	}
}

</script>
