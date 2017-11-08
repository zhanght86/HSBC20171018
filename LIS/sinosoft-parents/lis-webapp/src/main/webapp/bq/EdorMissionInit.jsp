<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorMissionInit.jsp
//程序功能：保全操作轨迹查询
//创建日期：2005-11-24 19:10:36
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
    //添加页面控件的初始化。
    GlobalInput globalInput = (GlobalInput)session.getValue("GI");

    if(globalInput == null) {
        out.println("session has expired");
        return;
    }

    String strOperator = globalInput.Operator;
%>

<script language="JavaScript">

function initForm()
{
  try
  {
        initInpBox();
        initEdorMissionGrid();        
        queryEdorMission();

  }
  catch(re)
  {
    alert("在EdorAppUWQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{

  try
  {

  }
  catch(ex)
  {
    alert("在EdorAppUWQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

//批单信息初始化
function initEdorMissionGrid()
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
          iArray[1][0]="操作岗位";
          iArray[1][1]="180px";
          iArray[1][2]=200;
          iArray[1][3]=0;

          iArray[2]=new Array();
          iArray[2][0]="操作员";
          iArray[2][1]="100px";
          iArray[2][2]=150;
          iArray[2][3]=0;

          iArray[3]=new Array();
          iArray[3][0]="任务流转时间";
          iArray[3][1]="120px";
          iArray[3][2]=150;
          iArray[3][3]=0;
          iArray[3][21]=3;

          iArray[4]=new Array();
          iArray[4][0]="任务申请时间";
          iArray[4][1]="120px";
          iArray[4][2]=150;
          iArray[4][3]=0;
          iArray[4][21]=3;

          iArray[5]=new Array();
          iArray[5][0]="处理完成时间";
          iArray[5][1]="120px";
          iArray[5][2]=150;
          iArray[5][3]=0;
          iArray[5][21]=3;

          iArray[6]=new Array();
          iArray[6][0]="工作流节点";
          iArray[6][1]="0px";
          iArray[6][2]=100;
          iArray[6][3]=3;

          iArray[7]=new Array();
          iArray[7][0]="生成日期";
          iArray[7][1]="0px";
          iArray[7][2]=0;
          iArray[7][3]=3;

          iArray[8]=new Array();
          iArray[8][0]="生成时间";
          iArray[8][1]="0px";
          iArray[8][2]=0;
          iArray[8][3]=3;

          EdorMissionGrid = new MulLineEnter("fm", "EdorMissionGrid");
          //这些属性必须在loadMulLine前
          EdorMissionGrid.mulLineCount = 3;
          EdorMissionGrid.displayTitle = 1;
          EdorMissionGrid.locked = 1;
          EdorMissionGrid.canSel = 0;
          EdorMissionGrid.hiddenPlus = 1;
          EdorMissionGrid.hiddenSubtraction = 1;
          EdorMissionGrid.loadMulLine(iArray);
          //这些操作必须在loadMulLine后面
          //EdorMissionGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
