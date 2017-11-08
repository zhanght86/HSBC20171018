<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-07-05, 2006-12-05
 * @direction: 团单保全人工核保分单层初始化
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

        var InsuredGrid;    //全局变量, 被保人队列
        var PolGrid;        //全局变量, 分单险种队列

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
                initInsuredGrid();
                initPolGrid();
                queryInsuredGrid();
            }
            catch (ex)
            {
                alert("在 GEdorManuUWInsuredInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 隐藏域的初始化
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("MissionID")[0].value = "<%=request.getParameter("MissionID")%>";
                document.getElementsByName("SubMissionID")[0].value = "<%=request.getParameter("SubMissionID")%>";
                document.getElementsByName("ActivityStatus")[0].value = "<%=request.getParameter("ActivityStatus")%>";
                document.getElementsByName("ActivityID")[0].value = "0000008006";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("在 GEdorManuUWInsuredInit.jsp --> initHiddenArea 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
                document.getElementsByName("GrpContNo")[0].value = "<%=request.getParameter("GrpContNo")%>";
            }
            catch (ex)
            {
                alert("在 GEdorManuUWInsuredInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 被保人队列查询 MultiLine 的初始化
         */
        function initInsuredGrid()
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
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "批改类型";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "分单号";
                iArray[3][1] = "90px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "被保人号";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "被保人姓名";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "性别";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 2;

                iArray[7] = new Array();
                iArray[7][0] = "出生日期";
                iArray[7][1] = "70px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "证件类型";
                iArray[8][1] = "90px";
                iArray[8][2] = 150;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "证件号码";
                iArray[9][1] = "120px";
                iArray[9][2] = 150;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "核保结论";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 2;
                iArray[10][4] = "gedorcontuwstate";
                iArray[10][18] = 92;
            }
            catch (ex)
            {
                alert("在 GEdorManuUWInsuredInit.jsp --> initInsuredGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                InsuredGrid = new MulLineEnter("fm", "InsuredGrid");
                InsuredGrid.mulLineCount = 1;
                InsuredGrid.displayTitle = 1;
                InsuredGrid.locked = 1;
                InsuredGrid.hiddenPlus = 1;
                InsuredGrid.hiddenSubtraction = 1;
                InsuredGrid.canChk = 0;
                InsuredGrid.canSel = 1;
                InsuredGrid.chkBoxEventFuncName = "";
                InsuredGrid.selBoxEventFuncName = "queryInsuredAndPol";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                InsuredGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 GEdorManuUWInsuredInit.jsp --> initInsuredGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 分单险种队列查询 MultiLine 的初始化
         */
        function initPolGrid()
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
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "批改类型";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "个单险种号";
                iArray[3][1] = "0px";
                iArray[3][2] = 0;
                iArray[3][3] = 3;

                iArray[4] = new Array();
                iArray[4][0] = "险种代码";
                iArray[4][1] = "70px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "险种名称";
                iArray[5][1] = "160px";
                iArray[5][2] = 200;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "基本保额";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "投保份数";
                iArray[7][1] = "70px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "标准保费";
                iArray[8][1] = "80px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "健康加费";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "职业加费";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][21] = 3;

                iArray[11] = new Array();
                iArray[11][0] = "核保结论";
                iArray[11][1] = "70px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][4] = "gedorpoluwstate";
                iArray[11][18] = 92;
            }
            catch (ex)
            {
                alert("在 GEdorManuUWInsuredInit.jsp --> initPolGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                PolGrid = new MulLineEnter("fm", "PolGrid");
                PolGrid.mulLineCount = 1;
                PolGrid.displayTitle = 1;
                PolGrid.locked = 1;
                PolGrid.hiddenPlus = 1;
                PolGrid.hiddenSubtraction = 1;
                PolGrid.canChk = 0;
                PolGrid.canSel = 1;
                PolGrid.chkBoxEventFuncName = "";
                PolGrid.selBoxEventFuncName = "initPolInfo";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                PolGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 GEdorManuUWInsuredInit.jsp --> initPolGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

