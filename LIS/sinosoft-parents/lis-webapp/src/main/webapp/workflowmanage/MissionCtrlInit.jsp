<%
/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

%>

<!--用户校验类-->

<script language="JavaScript">  

function initInpBox()
{

    try
    {
    }
    catch(ex)
    {
        alert("在InvAssOrderbuildDetailInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initTICollectGrid();
    }
    catch(re)
    {
        alert("InvAssOrderbuildDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
} //成交后汇总表
function initTICollectGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i] = new Array();
        iArray[i][0] = "序号";
        iArray[i][1] = "30px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "业务类型CODE";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "业务类型";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "活动名称CODE";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "活动名称";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "业务号";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "生成时间";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "规定作业用时";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "执行人";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "MissionID";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "SubMissionID";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        TICollectGrid = new MulLineEnter("fm", "TICollectGrid");
        //这些属性必须在loadMulLine前
        TICollectGrid.mulLineCount = 5;
        TICollectGrid.displayTitle = 1;
        TICollectGrid.hiddenPlus = 1;
        TICollectGrid.hiddenSubtraction = 1;
        TICollectGrid.canSel = 1;
        TICollectGrid.selBoxEventFuncName = "showData";
        TICollectGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
