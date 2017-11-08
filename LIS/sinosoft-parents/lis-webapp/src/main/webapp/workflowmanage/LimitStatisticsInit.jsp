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
    } // 下拉框的初始化
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
            initInpBox();
            initSelBox();
            initMonitorGrid();
        }
        catch(re)
        {
            alert("PMInverstRuleInit_st.jsp-->InitForm函数中发生异常:初始化界面错误!");
        }
    } //全部日期信息
    function initMonitorGrid()
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
            iArray[i][0] = "活动CODE";
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
            iArray[i][0] = "开始时间";
            iArray[i][1] = "100px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            iArray[i] = new Array();
            iArray[i][0] = "结束时间";
            iArray[i][1] = "140px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            
            iArray[i] = new Array();
            iArray[i][0] = "实际用时";
            iArray[i][1] = "70px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            
            iArray[i] = new Array();
            iArray[i][0] = "标准用时";
            iArray[i][1] = "70px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;   
            
            iArray[i] = new Array();
            iArray[i][0] = "时间利用率";
            iArray[i][1] = "70px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;                       

            iArray[i] = new Array();
            iArray[i][0] = "操作人";
            iArray[i][1] = "80px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            MonitorGrid = new MulLineEnter("fm", "MonitorGrid");
            //这些属性必须在loadMulLine前
            MonitorGrid.mulLineCount = 5;
            MonitorGrid.displayTitle = 1;
            MonitorGrid.hiddenPlus = 1;
            MonitorGrid.hiddenSubtraction = 1;
            MonitorGrid.canChk = 0;
            MonitorGrid.selBoxEventFuncName = "";
            MonitorGrid.loadMulLine(iArray);
        }
        catch(ex)
        {
            alert(ex);
        }
    }


</script>
