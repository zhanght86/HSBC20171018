<%
//程序名称：PEdorPopedomConfigInit.jsp
//程序功能：保全权限配置
//创建日期：2006-01-09 15:13:22
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
//
%>

<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initSelfGrid();
    //popedomQuery();  //初始化权限列表
  }
  catch(re)
  {
    alert("PEdorPopedomConfigInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{

  try
  {
     fm.ManageCom.value = "86"; //暂不提供分管理机构配置权限功能，默认为86
  }
  catch(ex)
  {
    alert("PEdorPopedomConfigInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
      iArray[0][2]=30;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全项目代码";
      iArray[1][1]="120px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="保全项目名称";
      iArray[2][1]="200px";
      iArray[2][2]=200;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="申请权限";
      iArray[3][1]="120px";
      iArray[3][2]=200;
      iArray[3][3]=0;
      iArray[3][9]="申请权限|NOTNULL";
      iArray[3][10]="ApplyFlagList";
      iArray[3][11]="0|^0|无^1|有";
      iArray[3][18]=200;

      iArray[4]=new Array();
      iArray[4][0]="审批权限";
      iArray[4][1]="120px";
      iArray[4][2]=200;
      iArray[4][3]=0;
      iArray[4][9]="审批权限|NOTNULL";
      iArray[4][10]="ApproveFlagList";
      iArray[4][11]="0|^0|无^1|有";
      iArray[4][18]=200;
      
      iArray[5]=new Array();
      iArray[5][0]="保全项目类别";
      iArray[5][1]="0px";
      iArray[5][2]=0;
      iArray[5][3]=3;
      
      iArray[6]=new Array();
      iArray[6][0]="最大支付金额";
      iArray[6][1]="120px";
      iArray[6][2]=200;
      iArray[6][3]=0;
      iArray[6][9]="最大支付金额|NOTNULL";
      iArray[6][10]="LimitGetMoney";
      iArray[6][11]="0|^0|0^2000|1^5000|2^20000|3^50000|4^100000|5^200000|6";
      iArray[6][18]=200;
      //iArray[6][14]="0";

      SelfGrid = new MulLineEnter("fm", "SelfGrid");
      //这些属性必须在loadMulLine前
      SelfGrid.mulLineCount = 3;
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;
      SelfGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
