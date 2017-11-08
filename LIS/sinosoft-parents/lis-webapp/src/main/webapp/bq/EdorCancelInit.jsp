<%
//程序名称：EdorCancelInit.jsp
//程序功能：保全撤销
//创建日期：2005-05-08 09:20:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

    var AppItemGrid;     //全局变量, 批单项目队列

//页面初始化
function initForm()
{
  try
  {
    initParam();
    initInputBox();
    initQuery();
    //<!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : BGN -->
    initAppItemGrid();
    queryAppItemGrid();
    //<!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : END -->
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//把null的字符串转为空
function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

//接收从工作流保全撤销页面传递过来的参数
function initParam()
{
   document.all('EdorAcceptNo').value     = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value        = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value     = nullToEmpty("<%= tSubMissionID %>");
   document.all('ActivityID').value     = nullToEmpty("<%= tActivityID %>");

}

//初始化页面元素
function initInputBox()
{
    try
    {
        document.getElementsByName("DelFlag")[0].value = "EdorApp";    //XinYQ added on 2006-02-09 : 默认撤销全部保全申请
    }
    catch(ex)
    {
        alert("在 PEdorInputInit.jsp --> initInputBox 函数中发生异常:初始化界面错误！ ");
    }
}

// 保全批单列表的初始化
//function initEdorMainGrid()
//{
//    var iArray = new Array();
//
//  try
//  {
//      iArray[0]=new Array();
//      iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
//      iArray[0][1]="30px";                    //列宽
//      iArray[0][2]=30;                        //列最大值
//      iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[1]=new Array();
//      iArray[1][0]="申请批单号";              //列名
//      iArray[1][1]="120px";                   //列宽
//      iArray[1][2]=100;                       //列最大值
//      iArray[1][3]=0;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[2]=new Array();
//      iArray[2][0]="保单号";                  //列名
//      iArray[2][1]="120px";                   //列宽
//      iArray[2][2]=100;                       //列最大值
//      iArray[2][3]=0;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[3]=new Array();
//      iArray[3][0]="主险交费对应日";          //列名
//      iArray[3][1]="100px";                   //列宽
//      iArray[3][2]=100;                       //列最大值
//      iArray[3][3]=0;                         //是否允许输入,1表示允许，0表示不允许,2表示代码引用
//
//      iArray[4]=new Array();
//      iArray[4][0]="柜面受理日期";                //列名
//      iArray[4][1]="0px";                     //列宽
//      iArray[4][2]=100;                       //列最大值
//      iArray[4][3]=3;                         //是否允许输入,1表示允许，0表示不允许,2表示代码引用
//
//      iArray[5]=new Array();
//      iArray[5][0]="生效日期";                //列名
//      iArray[5][1]="0px";                     //列宽
//      iArray[5][2]=100;                       //列最大值
//      iArray[5][3]=3;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[6]=new Array();
//      iArray[6][0]="变动保费";                //列名
//      iArray[6][1]="0px";                     //列宽
//      iArray[6][2]=100;                       //列最大值
//      iArray[6][3]=3;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[7]=new Array();
//      iArray[7][0]="变动保额";                //列名
//      iArray[7][1]="0px";                     //列宽
//      iArray[7][2]=100;                       //列最大值
//      iArray[7][3]=3;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[8]=new Array();
//      iArray[8][0]="补退费金额合计";           //列名
//      iArray[8][1]="100px";                   //列宽
//      iArray[8][2]=100;                       //列最大值
//      iArray[8][3]=0;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[9]=new Array();
//      iArray[9][0]="补退费利息";              //列名
//      iArray[9][1]="80px";                    //列宽
//      iArray[9][2]=100;                       //列最大值
//      iArray[9][3]=0;                         //是否允许输入,1表示允许，0表示不允许
//
//      iArray[10]=new Array();
//      iArray[10][0]="批改状态";
//      iArray[10][1]="80px";
//      iArray[10][2]=100;
//      iArray[10][3]=0;
//
//      iArray[11]=new Array();
//      iArray[11][0]="批改状态编码";
//      iArray[11][1]="0px";
//      iArray[11][2]=100;
//      iArray[11][3]=3;
//
//      iArray[12]=new Array();
//      iArray[12][0]="核保状态";               //列名
//      iArray[12][1]="80px";                   //列宽
//      iArray[12][2]=80;                       //列最大值
//      iArray[12][3]=0;                        //是否允许输入,1表示允许，0表示不允许
//
//      iArray[13]=new Array();
//      iArray[13][0]="核保状态编码";           //列名
//      iArray[13][1]="0px";                    //列宽
//      iArray[13][2]=80;                       //列最大值
//      iArray[13][3]=3;                        //是否允许输入,1表示允许，0表示不允许
//
//      EdorMainGrid = new MulLineEnter( "fm" , "EdorMainGrid" );
//      //这些属性必须在loadMulLine前
//      EdorMainGrid.mulLineCount = 0;
//      EdorMainGrid.displayTitle = 1;
//      EdorMainGrid.locked = 1;
//      EdorMainGrid.canSel = 1;
//      EdorMainGrid.hiddenPlus = 1;
//      EdorMainGrid.hiddenSubtraction=1;
//      EdorMainGrid.loadMulLine(iArray);
//
//      EdorMainGrid. selBoxEventFuncName = "showEdorItemList";
//
//      //这些操作必须在loadMulLine后面
//      //EdorMainGrid.setRowColData(1,1,"asdf");
//  }
//  catch(ex)
//  {
//      alert(ex);
//  }
//}
//
//// 保全项目列表的初始化
//function initEdorItemGrid()
//{
//    var iArray = new Array();
//
//  try
//  {
//        iArray[0]=new Array();
//        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
//        iArray[0][1]="30px";                  //列宽
//        iArray[0][2]=30;                      //列最大值
//        iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[1]=new Array();
//        iArray[1][0]="申请批单号";            //列名
//        iArray[1][1]="0px";                   //列宽
//        iArray[1][2]=100;                     //列最大值
//        iArray[1][3]=3;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[2]=new Array();
//        iArray[2][0]="批改类型";              //列名
//        iArray[2][1]="120px";                 //列宽
//        iArray[2][2]=100;                     //列最大值
//        iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[3]=new Array();
//        iArray[3][0]="保单号";                //列名
//        iArray[3][1]="110px";                 //列宽
//        iArray[3][2]=100;                     //列最大值
//        iArray[3][3]=0;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[4]=new Array();
//        iArray[4][0]="客户号";                //列名
//        iArray[4][1]="80px";                  //列宽
//        iArray[4][2]=80;                      //列最大值
//        iArray[4][3]=0;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[5]=new Array();
//        iArray[5][0]="险种号";                //列名
//        iArray[5][1]="120px";                 //列宽
//        iArray[5][2]=80;                      //列最大值
//        iArray[5][3]=0;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[6]=new Array();
//        iArray[6][0]="柜面受理日期";              //列名
//        iArray[6][1]="0px";                   //列宽
//        iArray[6][2]=100;                     //列最大值
//        iArray[6][3]=3;                       //是否允许输入,1表示允许，0表示不允许,2表示代码引用
//
//        iArray[7]=new Array();
//        iArray[7][0]="生效日期";              //列名
//        iArray[7][1]="0px";                   //列宽
//        iArray[7][2]=100;                     //列最大值
//        iArray[7][3]=3;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[8]=new Array();
//        iArray[8][0]="变动保费";              //列名
//        iArray[8][1]="0px";                   //列宽
//        iArray[8][2]=100;                     //列最大值
//        iArray[8][3]=3;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[9]=new Array();
//        iArray[9][0]="变动保额";              //列名
//        iArray[9][1]="0px";                   //列宽
//        iArray[9][2]=100;                     //列最大值
//        iArray[9][3]=3;                       //是否允许输入,1表示允许，0表示不允许
//
//        iArray[10]=new Array();
//        iArray[10][0]="补退费金额合计";       //列名
//        iArray[10][1]="80px";                 //列宽
//        iArray[10][2]=100;                    //列最大值
//        iArray[10][3]=0;                      //是否允许输入,1表示允许，0表示不允许
//
//        iArray[11]=new Array();
//        iArray[11][0]="补退费利息";           //列名
//        iArray[11][1]="60px";                 //列宽
//        iArray[11][2]=100;                    //列最大值
//        iArray[11][3]=0;                      //是否允许输入,1表示允许，0表示不允许
//
//        iArray[12]=new Array();
//        iArray[12][0]="批改状态";
//        iArray[12][1]="60px";
//        iArray[12][2]=100;
//        iArray[12][3]=0;
//
//        iArray[13]=new Array();
//        iArray[13][0]="批改状态编码";
//        iArray[13][1]="0px";
//        iArray[13][2]=100;
//        iArray[13][3]=3;
//
//        iArray[14]=new Array();
//        iArray[14][0]="核保状态";             //列名
//        iArray[14][1]="60px";                 //列宽
//        iArray[14][2]=80;                     //列最大值
//        iArray[14][3]=0;                      //是否允许输入,1表示允许，0表示不允许
//
//        iArray[15]=new Array();
//        iArray[15][0]="核保状态编码";         //列名
//        iArray[15][1]="0px";                  //列宽
//        iArray[15][2]=80;                     //列最大值
//        iArray[15][3]=3;                      //是否允许输入,1表示允许，0表示不允许
//
//        iArray[16]=new Array();
//        iArray[16][0]="生成日期";
//        iArray[16][1]="0px";
//        iArray[16][2]=100;
//        iArray[16][3]=3;
//
//        iArray[17]=new Array();
//        iArray[17][0]="生成时间";
//        iArray[17][1]="0px";
//        iArray[17][2]=100;
//        iArray[17][3]=3;
//
//        iArray[18]=new Array();
//        iArray[18][0]="保全项目代码";
//        iArray[18][1]="0px";
//        iArray[18][2]=100;
//        iArray[18][3]=3;
//
//        EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" );
//        //这些属性必须在loadMulLine前
//        EdorItemGrid.mulLineCount = 0;
//        EdorItemGrid.displayTitle = 1;
//        EdorItemGrid.locked = 1;
//        EdorItemGrid.canSel = 1;
//        EdorItemGrid.hiddenPlus = 1;
//        EdorItemGrid.hiddenSubtraction=1;
//        EdorItemGrid.loadMulLine(iArray);
//        EdorItemGrid.selBoxEventFuncName = "getEdorItemInfo";
//  }
//  catch(ex)
//  {
//      alert(ex);
//  }
//}

        //<!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : BGN -->

        //批单项目队列查询 MultiLine 的初始化
        function initAppItemGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

                iArray[1] = new Array();
                iArray[1][0] = "批单号";
                iArray[1][1] = "90px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "保单号";
                iArray[2][1] = "95px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "客户号";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "险种号";
                iArray[4][1] = "105px";
                iArray[4][2] = 120;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "保全项目";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "柜面受理日期";
                iArray[6][1] = "55px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "补退费金额";
                iArray[7][1] = "55px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "批改状态";
                iArray[8][1] = "55px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "核保状态";
                iArray[9][1] = "55px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
            }
            catch (ex)
            {
                alert("在 EdorCancelInit.jsp --> initAppItemGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                AppItemGrid = new MulLineEnter("document", "AppItemGrid");
                AppItemGrid.mulLineCount = 5;
                AppItemGrid.displayTitle = 1;
                AppItemGrid.locked = 1;
                AppItemGrid.hiddenPlus = 1;
                AppItemGrid.hiddenSubtraction = 1;
                AppItemGrid.canChk = 0;
                AppItemGrid.canSel = 1;
                AppItemGrid.chkBoxEventFuncName = ""
                AppItemGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                AppItemGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorCancelInit.jsp --> initAppItemGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    //<!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : END -->

</script>
