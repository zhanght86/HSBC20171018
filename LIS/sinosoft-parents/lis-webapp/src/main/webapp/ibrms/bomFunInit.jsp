<%
	//程序名称：bomFunInit.jsp
	//程序功能：对BOM和词条的初始化
	//创建日期：2008-8-13
	//创建人  ：
	//更新记录：
%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<%
	//添加页面控件的初始化。
%>

<script language="JavaScript">
		var tResourceName1 = 'ibrms.bomFunInitSql';
		
	function initInpBox() {
		try {	
			// 父BOM
			//alert('1');
			document.all('fBOM').value = "";
			//document.all('PreCheck').value = "";
			
			var eName = document.all("eName").value;
			
			var sqlid1="bomFunInitSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName(tResourceName1); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara('1');
	  
	  
			if(eName==''){
				//var fbomsql = "select name,cnname from (select * from lrbom where name<>' ')";
			}else{
				//var fbomsql = "select name,cnname from (select * from lrbom where name<>'"+eName+"')";
				mySql1.addSubPara(eName);//指定传入的参数
			}			
				var strSql=mySql1.getString();	
			var tem = easyQueryVer3(strSql,1,1,1);
			if(tem==false){
				document.all('fBOM').CodeData = '';
			}else{
				document.all('fBOM').CodeData = tem;
			}
			
			
			
		} catch (ex) {

		}
	}

	function initSelBox() {
		try {
		
		} catch (ex) {
			alert("在bomFunInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm(operate) {
		try {
			initInpBox();
			initSelBox();
			if (operate == '1'){
			}
			else{
			initQueryGrpGrid();
			initItemGrid();
			initValid();
			}
		} catch (re) {
			alert("bomFunInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
		}
	}

	function initItemGrid() {

		var iArray = new Array();

		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "词条英文名"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "100px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "BOM"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[2][1] = "100px"; //列宽
			iArray[2][2] = 10; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[3] = new Array();
			iArray[3][0] = "词条中文名"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[4] = new Array();
			iArray[4][0] = "连接词"; //列名
			iArray[4][1] = "70px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][4] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
			iArray[5] = new Array();
			iArray[5][0] = "层次型数据"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[5][4] = "IbrmsIsHierarchical";
			
			iArray[6] = new Array();
			iArray[6][0] = "基础词条"; //列名
			iArray[6][1] = "70px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[6][4] = "IbrmsIsBase";
			
			iArray[7] = new Array();
			iArray[7][0] = "基础数据取值类型"; //列名
			iArray[7][1] = "100px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[7][4] = "IbrmsSourceType";
			
			iArray[8] = new Array();
			iArray[8][0] = "基础数据取值"; //列名
			iArray[8][1] = "120px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			
			iArray[9] = new Array();
			iArray[9][0] = "词条数据类型"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[9][1] = "80px"; //列宽
			iArray[9][2] = 10; //列最大值
			iArray[9][3] = 2;
			iArray[9][4] = "IbrmsCommandType";

			iArray[10] = new Array();
			iArray[10][0] = "词条预校验"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[10][1] = "70px"; //列宽
			iArray[10][2] = 10; //列最大值
			iArray[10][3] = 2;
			iArray[10][4] = "IbrmsPreCheck";
			iArray[10][15] = "IbrmsPreCheck";
			iArray[10][17] = "9"; //依赖于9列的值

			iArray[11] = new Array();
			iArray[11][0] = "有效性"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[11][1] = "50px"; //列宽
			iArray[11][2] = 10; //列最大值
			iArray[11][3] = 2;
			iArray[11][4] = "IbrmsValid";

			iArray[12] = new Array();
			iArray[12][0] = "词条描述"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[12][1] = "180px"; //列宽
			iArray[12][2] = 10; //列最大值
			iArray[12][3] = 0;

			ItemGrid = new MulLineEnter("document", "ItemGrid");
			
			//这些属性必须在loadMulLine前
			ItemGrid.mulLineCount = 5;
			ItemGrid.displayTitle = 1;
   	    	ItemGrid.canChk = 0;
			ItemGrid.canSel = 1;
			ItemGrid.locked = 1; //是否锁定：1为锁定 0为不锁定
			ItemGrid.hiddenPlus = 1; //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
			ItemGrid.hiddenSubtraction = 1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
			ItemGrid.recordNo = 0; //设置序号起始基数为10，如果要分页显示数据有用

			ItemGrid.loadMulLine(iArray);
		} catch (ex) {
			alert(ex);
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
			iArray[1][0] = "BOM英文名"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[2] = new Array();
			iArray[2][0] = "BOM中文名"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[3] = new Array();
			iArray[3][0] = "父BOM"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[4] = new Array();
			iArray[4][0] = "本BOM字段"; //列名
			iArray[4][1] = "80px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[5] = new Array();
			iArray[5][0] = "父BOM字段"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[6] = new Array();
			iArray[6][0] = "BOM层次"; //列名
			iArray[6][1] = "60px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[7] = new Array();
			iArray[7][0] = "业务模块"; //列名
			iArray[7][1] = "60px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[7][4] = "IbrmsBusiness";

			iArray[8] = new Array();
			iArray[8][0] = "BOM描述信息"; //列名
			iArray[8][1] = "200px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[9] = new Array();
			iArray[9][0] = "BOM对应类"; //列名
			iArray[9][1] = "240px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			iArray[10] = new Array();
			iArray[10][0] = "有效性"; //列名
			iArray[10][1] = "50px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[10][4] = "IbrmsValid";

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
			QueryGrpGrid.selBoxEventFuncName ="itemQuery";

			QueryGrpGrid.loadMulLine(iArray);
		} catch (ex) {
			alert(ex);
		}
	}

function initValid(){
	try {
		fm.Valid.value = "1";
		fm.Valid.readOnly = "true";
		fm.ValidName.value = "有效";
		fm.ValidName.readOnly = "true";
	} catch (e) {
		alert("状态信息初始化错误！");
	}	
}	
</script>
