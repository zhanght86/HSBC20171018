<script type="text/JavaScript">
function initForm()
{
	initBOM();
	initQueryGrpGrid();
	
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
			iArray[1][0] = "规则名称"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
			//iArray[1][4] = "ibrmsresulttype";

			iArray[2] = new Array();
			iArray[2][0] = "业务模块"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "规则级别"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "生效日期"; //列名
			iArray[4][1] = "80px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 8; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "失效日期"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 8; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "创建者"; //列名
			iArray[6][1] = "80px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[7] = new Array();
			iArray[7][0] = "状态"; //列名
			iArray[7][1] = "100px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[7][4] = "ibrmsstate";
			
			iArray[8] = new Array();
			iArray[8][0] = "规则描述"; //列名
			iArray[8][1] = "180px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[8][4] = "ibrmsvalid";
		
		    iArray[9] = new Array();
			iArray[9][0] = "id"; //列名
			iArray[9][1] = "50px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 3; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[10] = new Array();
			iArray[10][0] = "来源表名"; //列名
			iArray[10][1] = "100px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 3; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
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
function initBOM() {
	try {
		fm.BOM.value = "";
		//var bomsql = "select * from lrbom";
		var mySql1=new SqlClass();
		mySql1.setResourceName("ibrms.RuleSearchSql"); //指定使用的properties文件名
		mySql1.setSqlId("RuleSearchSql1");//指定使用的Sql的id
		mySql1.addSubPara("");//指定传入的参数
		
		
	  var bomsql =mySql1.getString();	
		
		var tem = easyQueryVer3(bomsql, 1, 1, 1);
		if (tem == false) {
			fm.BOM.CodeData = '';
			alert("页面初始化BOM的时候出错，请找管理员！");
		} else {
			fm.BOM.CodeData = tem;
		}

	} catch (ex) {

	}
}
</script>
