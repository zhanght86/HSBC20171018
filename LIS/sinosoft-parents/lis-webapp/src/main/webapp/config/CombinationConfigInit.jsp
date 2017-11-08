<%
//程序名称：GrpHealthFactoryQueryInit.jsp
//程序功能：
//创建日期：2004-08-30
//创建人  ：sunxy
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{

  try
  {
  	document.all('ContPlanCode').value = "";
  	document.all('ContPlanName').value = "";
  	document.all('RiskCode').value = "";
  	document.all('PlanSql').value = "";
  	document.all('ManageCom').value = tManageCom;
	// 保单查询条件
  }
  catch(ex)
  {
    alert("在ContPlanInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在ContPlanInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initContPlanCodeGrid();
    initContPlanDutyGrid();
    initContPlanGrid();
    if(this.LoadFlag=="4"||this.LoadFlag=="16")
    {
     divRiskPlanSave.style.display="none";
    }
    easyQueryClick();
  }
  catch(re)
  {
    alert("ContPlanInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var ContPlanGrid;




// 要约信息列表的初始化
function initContPlanGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种名称";    	        //列名
      iArray[1][1]="200px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=150;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="责任编码";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 3;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10]="DutyCode";


      iArray[4]=new Array();
      iArray[4][0]="险种责任";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[5]=new Array();
      iArray[5][0]="计算要素";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;
      iArray[5][10]="FactorCode";            			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="要素名称";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="要素说明";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="要素值";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=150;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="特别说明";         		//列名
      iArray[9][1]="200px";            		//列宽
      iArray[9][2]=150;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="险种版本";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=10;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="集体保单险种号码";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=10;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="主险编码";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=10;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="类型";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=10;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="计算方法";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=10;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      ContPlanGrid = new MulLineEnter( "document" , "ContPlanGrid" );
      //这些属性必须在loadMulLine前
      ContPlanGrid.mulLineCount = 5;
      ContPlanGrid.displayTitle = 1;
      ContPlanGrid.hiddenPlus = 1;
      ContPlanGrid.hiddenSubtraction = 0;
      ContPlanGrid.canChk=0;
      ContPlanGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

//责任信息初始化
function initContPlanDutyGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	        //列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=150;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][10]="RiskCode";

      iArray[3]=new Array();
      iArray[3][0]="责任类型";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="类型名称";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ContPlanDutyGrid = new MulLineEnter( "document" , "ContPlanDutyGrid" );
      //这些属性必须在loadMulLine前
      ContPlanDutyGrid.mulLineCount = 5;
      ContPlanDutyGrid.displayTitle = 1;
      ContPlanDutyGrid.hiddenPlus = 1;
      ContPlanDutyGrid.hiddenSubtraction = 1;
      ContPlanDutyGrid.canChk=1;
      ContPlanDutyGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

//保险计划初始化
function initContPlanCodeGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="计划编码";    	        //列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[2]=new Array();
      iArray[2][0]="计划名称";         		//列名
      iArray[2][1]="260px";            		//列宽
      iArray[2][2]=150;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][10]="RiskCode";

      iArray[3]=new Array();
      iArray[3][0]="分类说明";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="可保人数";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]= 60;            			//列最大值
      iArray[4][3]= 0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="特别约定";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]= 60;            			//列最大值
      iArray[5][3]= 0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="组合层级1";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]= 60;            			//列最大值
      iArray[6][3]= 0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="组合层级2";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]= 60;            			//列最大值
      iArray[7][3]= 0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="组合层级3";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]= 60;            			//列最大值
      iArray[8][3]= 0;              			//是否允许输入,1表示允许，0表示不允许                        

      iArray[9]=new Array();
      iArray[9][0]="管理机构";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]= 60;            			//列最大值
      iArray[9][3]= 0;              			//是否允许输入,1表示允许，0表示不允许                        

      ContPlanCodeGrid = new MulLineEnter( "document" , "ContPlanCodeGrid" );
      //这些属性必须在loadMulLine前
      ContPlanCodeGrid.mulLineCount = 5;
      ContPlanCodeGrid.displayTitle = 1;
      ContPlanCodeGrid.hiddenPlus = 1;
      ContPlanCodeGrid.hiddenSubtraction = 1;
      ContPlanCodeGrid.canSel=1;
      ContPlanCodeGrid.selBoxEventFuncName = "ShowContPlan"; 
      ContPlanCodeGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}
</script>
