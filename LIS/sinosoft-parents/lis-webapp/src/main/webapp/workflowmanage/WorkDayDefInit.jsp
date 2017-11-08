<%/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */%><!--用户校验类-->
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("PMInverstRuleInit_st.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}
function initSelBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("PMInverstRuleInit_st.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}
function initForm()
{
    try
    {
        initWorkDayGrid();
        initSaveWorkDayGrid();
    }
    catch(re)
    {
        alert("PMInverstRuleInit_st.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
} //全部日期信息
function initWorkDayGrid()
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
        iArray[i][0] = "工作日类型";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "序列号";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;
        iArray[i] = new Array();
        iArray[i][0] = "年份";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "日期";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "星期";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "上午工作开始时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "上午工作结束时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "下午工作开始时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "下午工作结束时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "工作时间";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "是否工作日";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        iArray[i++][4] = "yesorno";
        iArray[i] = new Array();
        iArray[i][0] = "其他属性 ";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        WorkDayGrid = new MulLineEnter("fm", "WorkDayGrid");
        //这些属性必须在loadMulLine前
        WorkDayGrid.mulLineCount = 5;
        WorkDayGrid.displayTitle = 1;
        WorkDayGrid.hiddenPlus = 1
        WorkDayGrid.hiddenSubtraction = 1;
        WorkDayGrid.canChk = 1;
        WorkDayGrid.selBoxEventFuncName = "";
        WorkDayGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
} //选中
function initSaveWorkDayGrid()
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
        iArray[i][0] = "工作日类型";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "序列号";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;
        iArray[i] = new Array();
        iArray[i][0] = "年份";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "日期";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "星期";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "上午工作开始时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "上午工作结束时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "下午工作开始时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "下午工作结束时间";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "工作时间";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "是否工作日";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        iArray[i++][4] = "yesorno";
        iArray[i] = new Array();
        iArray[i][0] = "其他属性 ";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        SaveWorkDayGrid = new MulLineEnter("fm", "SaveWorkDayGrid");
        //这些属性必须在loadMulLine前
        SaveWorkDayGrid.mulLineCount = 0;
        SaveWorkDayGrid.displayTitle = 1;
        SaveWorkDayGrid.hiddenPlus = 1;
        SaveWorkDayGrid.hiddenSubtraction = 1;
        SaveWorkDayGrid.canChk = 1;
        SaveWorkDayGrid.selBoxEventFuncName = "";
        SaveWorkDayGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
