<%
//程序名称：GrpHealthFactoryQueryInit.jsp
//程序功能：
//创建日期：2004-08-30
//创建人  ：sunxy
//更新记录：  更新人 mqhu 更新日期 2005-4-1    更新原因/内容
%>
<!--用户校验类-->
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox(){
  try{
	// 保单查询条件
  }
  catch(ex)
  {
    alert("PayRuleInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox(){
  try{
  }
  catch(ex)
  {
    alert("PayRuleInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm(){
  try{
   
    
   
    if(this.LoadFlag=="4"||this.LoadFlag=="16"||this.LoadFlag=="14")
    {
       divRiskPlanSave.style.display="none";
    }
  
   //GrpPerPolDefine();
   
    //GrpPerPolDefineOld();
    initPayRuleOldGrid();
    initPayRuleGrid();
    initPayRuleNewGrid();
    if(LoadFlag=="13" || LoadFlag=="2")
	  {
	    fm.GrpContNo.value=GrpContNo;
	    GrpPerPolDefine();
	    GrpPerPolDefineOld();
	    queryrelate();
	  }
	 if(LoadFlag=="4" || LoadFlag=="16"){
	    fm.GrpContNo.value=GrpContNo;
	    GrpPerPolDefine();
	    GrpPerPolDefineOld();
	    queryrelate();
	    divRiskPlanSave.style.display="none";
	  }
  }
  catch(re)
  {
    alert("PayRuleInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var ContPlanGrid;
function initPayRuleOldGrid(){
	var iArray = new Array();
	try{
	iArray[0]=new Array();
	iArray[0][0]="序号";
	iArray[0][1]="30px";
	iArray[0][2]=10;
	iArray[0][3]=0;
	//alert("not here");
        iArray[1]= new Array();
	iArray[1][0]="缴费规则名称";
	iArray[1][1]="100px";
	iArray[1][2]=10;
	iArray[1][3]=0;
	
	iArray[2]=new Array();
	iArray[2][0]="已有的缴费规则说明";
	iArray[2][1]="300px";
	iArray[2][2]=10;
	iArray[2][3]=0;
	
	     PayRuleOldGrid = new MulLineEnter( "fm" , "PayRuleOldGrid" );
      //这些属性必须在loadMulLine前
       PayRuleOldGrid.mulLineCount = 0;
      PayRuleOldGrid.displayTitle = 1;
      PayRuleOldGrid.hiddenPlus = 1;
      PayRuleOldGrid.hiddenSubtraction = 1;
      PayRuleOldGrid.canSel=1;
      PayRuleOldGrid.selBoxEventFuncName = "ShowPayRule"; 
      PayRuleOldGrid.loadMulLine(iArray);
	}
	    catch(ex) {
      alert(ex);
    }
    }


// 要约信息列表的初始化
function initPayRuleNewGrid(tImpGrpContNo) {
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[1] = new Array();
      iArray[1][0] = "险种编码";    	        //列名
      iArray[1][1] = "60px";            		//列宽
      iArray[1][2] = 100;            			//列最大值
      iArray[1][3] = 2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[1][10] = "RiskRuleCode";
      iArray[1][11] = tImpGrpContNo;
      iArray[1][12] = "1|9";	//multine上的列位
      iArray[1][13] = "0|2";	//查询字段的位置
      iArray[1][19] = 1;

      iArray[2] = new Array();
      iArray[2][0] = "要素类别";    	        //列名
      iArray[2][1] = "60px";            		//列宽
      iArray[2][2] = 100;            			//列最大值
      iArray[2][3] = 2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[2][4] = "RiskRuleFactoryType";
      iArray[2][5] = "2|8";
      iArray[2][6] = "0|2";
      iArray[2][15] = "RiskCode";
      iArray[2][17] = "1";
      iArray[2][19] = 1;

      iArray[3] = new Array();
      iArray[3][0] = "要素目标编码";         		//列名
      iArray[3][1] = "80px";            		//列宽
      iArray[3][2] = 60;            			//列最大值
      iArray[3][3] = 2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4] = "RiskRuleFactoryNo";
      iArray[3][9] = "要素目标编码|len<=6";
      iArray[3][15] = "RiskCode";
      iArray[3][17] = "8";

      iArray[4] = new Array();
      iArray[4][0] = "要素计算编码";         		//列名
      iArray[4][1] = "80px";            		//列宽
      iArray[4][2] = 60;            			//列最大值
      iArray[4][3] = 2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][4] = "RiskRuleFactory";
      iArray[4][5] = "4|5|6|7";
      iArray[4][6] = "0|1|2|3";
      iArray[4][9] = "要素计算编码|len<=4";
      iArray[4][15] = "RiskCode";
      iArray[4][17] = "8";

      iArray[5] = new Array();
      iArray[5][0] = "要素内容";         		//列名
      iArray[5][1] = "300px";            		//列宽
      iArray[5][2] = 200;            			//列最大值
      iArray[5][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6] = new Array();
      iArray[6][0] = "要素值";         		//列名
      iArray[6][1] = "80px";            		//列宽
      iArray[6][2] = 150;            			//列最大值
      iArray[6][3] = 1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7] = new Array();
      iArray[7][0] = "要素名称";         		//列名
      iArray[7][1] = "0px";            		//列宽
      iArray[7][2] = 150;            			//列最大值
      iArray[7][3] = 3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8] = new Array();
      iArray[8][0] = "查询条件";         		//列名
      iArray[8][1] = "0px";            		//列宽
      iArray[8][2] = 150;            			//列最大值
      iArray[8][3] = 3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9] = new Array();
      iArray[9][0] = "集体保单险种号码";         		//列名
      iArray[9][1] = "0px";            		//列宽
      iArray[9][2] = 150;            			//列最大值
      iArray[9][3] = 3;              			//是否允许输入,1表示允许，0表示不允许

      PayRuleNewGrid = new MulLineEnter( "fm" , "PayRuleNewGrid" );
      //这些属性必须在loadMulLine前
      PayRuleNewGrid.mulLineCount = 1;
      PayRuleNewGrid.displayTitle = 1;
      PayRuleNewGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}
function initPayRuleGrid(tImpGrpContNo) {
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;           			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1] = new Array();
      iArray[1][0] = "规则代码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1] = "40px";            		//列宽
      iArray[1][2] = 10;            			//列最大值
      iArray[1][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

       iArray[2] = new Array();
      iArray[2][0] = "规则说明";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[2][1] = "60px";            		//列宽
      iArray[2][2] = 10;            			//列最大值
      iArray[2][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3] = new Array();
      iArray[3][0] = "缴费类型";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[3][1] = "40px";            		//列宽
      iArray[3][2] = 10;            			//列最大值
      iArray[3][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4] = new Array();
      iArray[4][0] = "规则内容";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[4][1] = "250px";            		//列宽
      iArray[4][2] = 10;            			//列最大值
      iArray[4][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5] = new Array();
      iArray[5][0] = "所含要素的值";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[5][1] = "50px";            		//列宽
      iArray[5][2] = 10;            			//列最大值
      iArray[5][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      


      PayRuleGrid = new MulLineEnter( "fm" , "PayRuleGrid" );
      //这些属性必须在loadMulLine前
      PayRuleGrid.mulLineCount = 1;
      PayRuleGrid.displayTitle = 1;
      PayRuleGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert("payrulegrid error");
    }
}
</script>
