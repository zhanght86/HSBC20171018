<%
//程序名称：WFEdorCancelInit.jsp
//程序功能：保全工作流-保全撤销
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>


<script language="JavaScript">

function initForm()
{
  try
  {
    initAllGrid();
    initSelfGrid();
    querySelfGrid();
  }
  catch(re)
  {
    alert("WFGrpEdorCancelInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAllGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";
      iArray[1][1]="120px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="团体保单号";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="号码类型";
      iArray[3][1]="0px";
      iArray[3][2]=100;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="投保单位";
      iArray[4][1]="150px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="申请方式";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="管理机构";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=0;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="录入员";
      iArray[7][1]="70px";
      iArray[7][2]=200;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="录入日期";
      iArray[8][1]="70px";
      iArray[8][2]=100;
      iArray[8][3]=0;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="工作流任务号";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="工作流子任务号";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="工作流活动Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;

      AllGrid = new MulLineEnter( "document" , "AllGrid" );
      //这些属性必须在loadMulLine前
      AllGrid.mulLineCount = 5;
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.hiddenPlus = 1;
      AllGrid.hiddenSubtraction = 1;
      AllGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

function initSelfGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";
      iArray[1][1]="120px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="团体保单号";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="号码类型";
      iArray[3][1]="0px";
      iArray[3][2]=100;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="投保单位";
      iArray[4][1]="150px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="申请方式";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="管理机构";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=0;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="录入员";
      iArray[7][1]="70px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="录入日期";
      iArray[8][1]="70px";
      iArray[8][2]=100;
      iArray[8][3]=0;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="工作流任务号";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="工作流子任务号";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="工作流活动Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;

      SelfGrid = new MulLineEnter( "document" , "SelfGrid" );
      //这些属性必须在loadMulLine前
      SelfGrid.mulLineCount = 5;
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;
      SelfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>