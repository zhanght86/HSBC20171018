<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-26
 * @direction: 影像迁移日志查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        String sOperator = tGlobalInput.Operator;
        tGlobalInput = null;
    %>

    <script language="JavaScript">

        var TaskLogGrid;      //全局变量, 迁移批次信息
        var MoveErrorGrid;    //全局变量, 批次错误信息

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
                initTaskLogGrid();
                initMoveErrorGrid();
                //queryTaskLogGrid();
            }
            catch (ex)
            {
                alert("在 ImgMoveLogInfoInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 隐藏域的初始化
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
            }
            catch (ex)
            {
                alert("在 ImgMoveLogInfoInit.jsp --> initHiddenArea 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("OldManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("NewManageCom")[0].value = "86";
                showOneCodeName("Station", "OldManageCom", "OldManageComName");
                showOneCodeName("Station", "NewManageCom", "NewManageComName");
            }
            catch (ex)
            {
                alert("在 ImgMoveLogInfoInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 迁移批次信息 MultiLine 的初始化
         */
        function initTaskLogGrid()
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
                iArray[1][0] = "迁移批次号";
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "迁出机构";
                iArray[2][1] = "90px";
                iArray[2][2] = 100;
                iArray[2][3] = 2;
                iArray[2][4] = "Station";

                iArray[3] = new Array();
                iArray[3][0] = "迁入机构";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "Station";

                iArray[4] = new Array();
                iArray[4][0] = "扫描起期";
                iArray[4][1] = "90px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                iArray[4][21] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "扫描止期";
                iArray[5][1] = "90px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
                iArray[5][21] = 3;

                iArray[6] = new Array();
                iArray[6][0] = "需迁移总数";
                iArray[6][1] = "90px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "成功迁移数";
                iArray[7][1] = "90px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "任务性质";
                iArray[8][1] = "60px";
                iArray[8][2] = 60;
                iArray[8][3] = 0;
                iArray[8][21] = 3;
            }
            catch (ex)
            {
                alert("在 ImgMoveLogInfoInit.jsp --> initTaskLogGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                TaskLogGrid = new MulLineEnter("fm", "TaskLogGrid");
                TaskLogGrid.mulLineCount = 3;
                TaskLogGrid.displayTitle = 1;
                TaskLogGrid.locked = 1;
                TaskLogGrid.hiddenPlus = 1;
                TaskLogGrid.hiddenSubtraction = 1;
                TaskLogGrid.canChk = 0;
                TaskLogGrid.canSel = 1;
                TaskLogGrid.chkBoxEventFuncName = "";
                TaskLogGrid.selBoxEventFuncName = "queryMoveErrorGrid";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                TaskLogGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 ImgMoveLogInfoInit.jsp --> initTaskLogGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 批次错误信息 MultiLine 的初始化
         */
        function initMoveErrorGrid()
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
                iArray[1][0] = "迁移批次号";
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "影像扫描编号";
                iArray[2][1] = "90px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "影像扫描日期";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;
                iArray[3][21] = 3;

                iArray[4] = new Array();
                iArray[4][0] = "影像文件路径";
                iArray[4][1] = "370px";
                iArray[4][2] = 500;
                iArray[4][3] = 0;
            }
            catch (ex)
            {
                alert("在 ImgMoveLogInfoInit.jsp --> initMoveErrorGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                MoveErrorGrid = new MulLineEnter("fm", "MoveErrorGrid");
                MoveErrorGrid.mulLineCount = 3;
                MoveErrorGrid.displayTitle = 1;
                MoveErrorGrid.locked = 1;
                MoveErrorGrid.hiddenPlus = 1;
                MoveErrorGrid.hiddenSubtraction = 1;
                MoveErrorGrid.canChk = 0;
                MoveErrorGrid.canSel = 1;
                MoveErrorGrid.chkBoxEventFuncName = "";
                MoveErrorGrid.selBoxEventFuncName = "initDocID";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                MoveErrorGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 ImgMoveLogInfoInit.jsp --> initMoveErrorGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

