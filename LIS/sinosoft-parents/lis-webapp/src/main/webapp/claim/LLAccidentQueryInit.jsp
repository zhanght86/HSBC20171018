<%
//程序名称：LLAccidentQueryInit.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">

var tAccDate = "<%=tAccDate%>";
var tCustomerNo = "<%=tCustomerNo%>";

function initInpBox()
{
    try
    {


    }
    catch(ex)
    {
        alert("在LLAccidentQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initSelBox()
{
    try
    {

    }
    catch(ex)
    {
       alert("在LLAccidentQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();
        initLLAccidentGrid();
        easyQueryClick();
    }
    catch(re)
    {
        alert("LLAccidentQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
// 保单信息列表的初始化
function initLLAccidentGrid()
  {
      var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                //列宽
      iArray[0][2]=30;                  //列最大值
      iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="事件号";             //列名
      iArray[1][1]="70px";                //列宽
      iArray[1][2]=70;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="事故日期";             //列名
      iArray[2][1]="70px";                //列宽
      iArray[2][2]=70;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="事故描述";             //列名
      iArray[3][1]="160px";                //列宽
      iArray[3][2]=160;                  //列最大值
      iArray[3][3]=0;


      iArray[4]=new Array();
      iArray[4][0]="事故地点";             //列名
      iArray[4][1]="60px";                //列宽
      iArray[4][2]=60;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许      
            
      iArray[5]=new Array();
      iArray[5][0]="出险原因编码";             //列名，既事故类型代码
      iArray[5][1]="0px";                //列宽
      iArray[5][2]=0;                  //列最大值
      iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

//*******************modify by niuzj,2005-11-03,界面上显示事故类型名称***************

      iArray[6]=new Array();
      iArray[6][0]="出险原因";         //列名
      iArray[6][1]="60px";                //列宽
      iArray[6][2]=60;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许
//************************而把事故类型代码在最后隐藏传递*****************************


      iArray[7]=new Array();
      iArray[7][0]="意外细节编码";             //列名
      iArray[7][1]="0px";                //列宽
      iArray[7][2]=0;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="意外细节";             //列名
      iArray[8][1]="160px";                //列宽
      iArray[8][2]=160;                  //列最大值
      iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="出险结果1编码";             //列名
      iArray[9][1]="0px";                //列宽
      iArray[9][2]=0;                  //列最大值
      iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="出险结果1";             //列名
      iArray[10][1]="120px";                //列宽
      iArray[10][2]=120;                  //列最大值
      iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="出险结果2编码";             //列名
      iArray[11][1]="0px";                //列宽
      iArray[11][2]=0;                  //列最大值
      iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="出险结果2";             //列名
      iArray[12][1]="120px";                //列宽
      iArray[12][2]=120;                  //列最大值
      iArray[12][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="操作员";             //列名
      iArray[13][1]="60px";                //列宽
      iArray[13][2]=60;                  //列最大值
      iArray[13][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="管理机构";             //列名
      iArray[14][1]="60px";                //列宽
      iArray[14][2]=60;                  //列最大值
      iArray[14][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[15]=new Array();
      iArray[15][0]="备注";             //列名
      iArray[15][1]="0px";                //列宽
      iArray[15][2]=0;                  //列最大值
      iArray[15][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      

      LLAccidentGrid = new MulLineEnter( "fm" , "LLAccidentGrid" );
      //这些属性必须在loadMulLine前
      LLAccidentGrid.mulLineCount = 10;
      LLAccidentGrid.displayTitle = 1;
      LLAccidentGrid.locked = 0;
//      LLAccidentGrid.canChk = 1;
      LLAccidentGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLAccidentGrid.hiddenPlus=1;
      LLAccidentGrid.hiddenSubtraction=1;
      LLAccidentGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
