<%
//程序名称：LmRiskAppQueryInit.jsp
//程序功能：
//创建日期：2005-10-31
//创建人  ：万泽辉


%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">



// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在LmRiskAppGridInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}
//险种信息查询初始化
function initLmRiskAppForm()
{
  try
  {
    initSelBox();
		initLmRiskAppGrid();
  }
  catch(re)
  {
    alert("initLmRiskAppForm函数中发生异常:初始化界面错误!");
  }
}
//银行利率查询初始化
function initLdBankRateForm(){
  try
  {
    initSelBox();
		initLdBankRateGrid();
  }
  catch(re)
  {
    alert("initLdBankRateForm函数中发生异常:初始化界面错误!");
  }
}

var LmRiskAppGrid;
// 险种信息列表的初始化
function initLmRiskAppGrid()
{
 
   var iArray = new Array();
    
    try
    {
    
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="险种编码";             //列名
    iArray[1][1]="100px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="险种名称";             //列名
    iArray[2][1]="180px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="险种版本";             //列名
    iArray[3][1]="80px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="预定利率";             //列名
    iArray[4][1]="80px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="险种类型";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="销售时间";             //列名
    iArray[6][1]="100px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=8;                    //是否允许输入,1表示允许，0表示不允许

    LmRiskAppGrid = new MulLineEnter( "fm" , "LmRiskAppGrid" ); 
    //这些属性必须在loadMulLine前
    LmRiskAppGrid.mulLineCount = 0;   
    LmRiskAppGrid.displayTitle = 1;
    LmRiskAppGrid.locked = 1;
    LmRiskAppGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
    LmRiskAppGrid.hiddenPlus=1;
    LmRiskAppGrid.hiddenSubtraction=1;
    LmRiskAppGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

var LdBankRateGrid;
//银行利率信息列表的初始化
function initLdBankRateGrid()
{
 
   var iArray = new Array();
    
    try
    {
    
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="期间标记";             //列名
    iArray[1][1]="100px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="利率类型";             //列名
    iArray[2][1]="180px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="存/贷";             //列名
    iArray[3][1]="80px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="公布日期";             //列名
    iArray[4][1]="80px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="止期";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="利率";             //列名
    iArray[6][1]="100px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    LdBankRateGrid = new MulLineEnter( "fm" , "LdBankRateGrid" ); 
    //这些属性必须在loadMulLine前
    LdBankRateGrid.mulLineCount = 0;   
    LdBankRateGrid.displayTitle = 1;
    LdBankRateGrid.locked = 1;
    LdBankRateGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
    LdBankRateGrid.hiddenPlus=1;
    LdBankRateGrid.hiddenSubtraction=1;
    LdBankRateGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
