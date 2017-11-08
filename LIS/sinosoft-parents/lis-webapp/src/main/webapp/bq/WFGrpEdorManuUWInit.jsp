<%
/*******************************************************************************
 * @direction: 团单保全人工核保申请任务初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sOperator = tGlobalInput.Operator;
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>


    <script language="JavaScript">

        var AllGrid;     //全局变量, 共享工作队列
        var SelfGrid;    //全局变量, 我的任务队列

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initAllGrid();
                initSelfGrid();
                querySelfGrid();
            }
            catch (ex)
            {
                alert("在 WFGrpEdorManuUWApplyInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 隐藏域的初始化
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("ActivityID")[0].value = "0000008005";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("在 WFGrpEdorManuUWApplyInit.jsp --> initHiddenArea 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 共享工作队列查询 MultiLine 的初始化
         */
        function initAllGrid()
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
                iArray[1][0] = "工作流任务号";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "工作流子任务号";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "保全受理号";
                iArray[3][1] = "110px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "客户/保单号";
                iArray[4][1] = "110px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "号码类型";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "申请人";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "管理机构";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][4] = "Station";
                iArray[7][18] = 235;

                iArray[8] = new Array();
                iArray[8][0] = "当前活动状态";
                iArray[8][1] = "0px";
                iArray[8][2] = 0;
                iArray[8][3] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "核保状态";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "录入员";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "录入日期";
                iArray[11][1] = "70px";
                iArray[11][2] = 100;
                iArray[11][3] = 0;
                iArray[11][21] = 3;
            }
            catch (ex)
            {
                alert("在 WFGrpEdorManuUWApplyInit.jsp --> initAllGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                AllGrid = new MulLineEnter("document", "AllGrid");
                AllGrid.mulLineCount = 5;
                AllGrid.displayTitle = 1;
                AllGrid.locked = 1;
                AllGrid.hiddenPlus = 1;
                AllGrid.hiddenSubtraction = 1;
                AllGrid.canChk = 0;
                AllGrid.canSel = 1;
                AllGrid.chkBoxEventFuncName = "";
                AllGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                AllGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 WFGrpEdorManuUWApplyInit.jsp --> initAllGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 我的任务队列查询 MultiLine 的初始化
         */
        function initSelfGrid()
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
                iArray[1][0] = "工作流任务号";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "工作流子任务号";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "保全受理号";
                iArray[3][1] = "110px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "客户/保单号";
                iArray[4][1] = "110px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "号码类型";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "申请人";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "管理机构";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][4] = "Station";
                iArray[7][18] = 235;

                iArray[8] = new Array();
                iArray[8][0] = "当前活动状态";
                iArray[8][1] = "0px";
                iArray[8][2] = 0;
                iArray[8][3] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "核保状态";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "录入员";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "录入日期";
                iArray[11][1] = "70px";
                iArray[11][2] = 100;
                iArray[11][3] = 0;
                iArray[11][21] = 3;
            }
            catch (ex)
            {
                alert("在 WFGrpEdorManuUWApplyInit.jsp --> initSelfGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                SelfGrid = new MulLineEnter("document", "SelfGrid");
                SelfGrid.mulLineCount = 0;
                SelfGrid.displayTitle = 1;
                SelfGrid.locked = 1;
                SelfGrid.hiddenPlus = 1;
                SelfGrid.hiddenSubtraction = 1;
                SelfGrid.canChk = 0;
                SelfGrid.canSel = 1;
                SelfGrid.chkBoxEventFuncName = "";
                SelfGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                SelfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 WFGrpEdorManuUWApplyInit.jsp --> initSelfGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

