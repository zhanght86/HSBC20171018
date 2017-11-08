<%
//程序名称：EdorNoticeInit.jsp
//程序功能：保全复核
//创建日期：2005-05-08 15:20:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

//页面初始化
function initForm()
{
    try
    {
        initInpBox();
        initEdorItemGrid();
        initQuery();
        InitApproveContent();
    }
    catch (ex)
    {
        alert("在 EdorNoticeInit.jsp --> initForm 函数中发生异常:初始化界面错误！");
    }
}

//初始化页面元素
function initInpBox()
{
    try
    {
    	  document.all('ApproveFlag').value     = "1";
    	  document.all('edornoticeideaName').value     = "保全审核通知书";
    	  document.all("divPayPassNotice").style.display = "";
        document.all('EdorAcceptNo').value     = NullToEmpty("<%= tEdorAcceptNo %>");
        document.all('MissionID').value        = NullToEmpty("<%= tMissionID %>");
        document.all('SubMissionID').value     = NullToEmpty("<%= tSubMissionID %>");
    }
    catch (ex)
    {
        alert("在 EdorNoticeInit.jsp --> initInpBox 函数中发生异常:初始化界面错误！");
    }
}

// 保全项目列表的初始化
function initEdorItemGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";                  //列宽
        iArray[0][2] = 30;                      //列最大值
        iArray[0][3] = 0;                       //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "申请批单号";
        iArray[1][1] = "0px";
        iArray[1][2] = 0;
        iArray[1][3] = 3;

        iArray[2] = new Array();
        iArray[2][0] = "批改类型";
        iArray[2][1] = "100px";
        iArray[2][2] = 100;
        iArray[2][3] = 0;

        iArray[3] = new Array();
        iArray[3][0] = "保单号";
        iArray[3][1] = "110px";
        iArray[3][2] = 150;
        iArray[3][3] = 0;

        iArray[4] = new Array();
        iArray[4][0] = "客户号";
        iArray[4][1] = "70px";
        iArray[4][2] = 100;
        iArray[4][3] = 0;

        iArray[5] = new Array();
        iArray[5][0] = "险种号";
        iArray[5][1] = "110px";
        iArray[5][2] = 150;
        iArray[5][3] = 0;

        iArray[6] = new Array();
        iArray[6][0] = "柜面受理日期";
        iArray[6][1] = "100px";
        iArray[6][2] = 150;
        iArray[6][3] = 0;


        iArray[7] = new Array();
        iArray[7][0] = "生效日期";
        iArray[7][1] = "0px";
        iArray[7][2] = 0;
        iArray[7][3] = 3;
        iArray[7][21] = 3;

        iArray[8] = new Array();
        iArray[8][0] = "补退费金额";
        iArray[8][1] = "0px";
        iArray[8][2] = 100;
        iArray[8][3] = 0;
        iArray[8][21] = 3;

        iArray[9] = new Array();
        iArray[9][0] = "补退费利息";
        iArray[9][1] = "0px";
        iArray[9][2] = 100;
        iArray[9][3] = 0;
        iArray[9][21] = 3;

        iArray[10] = new Array();
        iArray[10][0] = "批改状态";
        iArray[10][1] = "70px";
        iArray[10][2] = 100;
        iArray[10][3] = 0;

        iArray[11] = new Array();
        iArray[11][0] = "批改状态编码";
        iArray[11][1] = "0px";
        iArray[11][2] = 0;
        iArray[11][3] = 3;

        iArray[12] = new Array();
        iArray[12][0] = "生成日期";
        iArray[12][1] = "0px";
        iArray[12][2] = 0;
        iArray[12][3] = 3;
        iArray[12][21] = 3;

        iArray[13] = new Array();
        iArray[13][0] = "生成时间";
        iArray[13][1] = "0px";
        iArray[13][2] = 0;
        iArray[13][3] = 3;
        iArray[13][21] = 3;

        iArray[14] = new Array();
        iArray[14][0] = "批改类型编码";
        iArray[14][1] = "0px";
        iArray[14][2] = 0;
        iArray[14][3] = 3;


        EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
        //这些属性必须在loadMulLine前
        EdorItemGrid.mulLineCount = 1;
        EdorItemGrid.displayTitle = 1;
        EdorItemGrid.locked = 1;
        EdorItemGrid.canSel = 1;
        EdorItemGrid.hiddenPlus = 1;
        EdorItemGrid.hiddenSubtraction=1;
        EdorItemGrid.selBoxEventFuncName = "getEdorItemInfo";
        EdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 EdorNoticeInit.jsp --> initEdorItemGrid 函数中发生异常:初始化界面错误！");
    }
}

</script>
