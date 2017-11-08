<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2005-12-14, 2006-03-21, 2006-07-13, 2006-08-19, 2006-10-25
 * @direction: 保全人工核保投(被)保人既往保全查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var EdorGrid;        //全局变量, 既往保全批单信息
        var PrintGrid;       //全局变量, 既往保全批单核保通知书信息
        var EdorItemGrid;    //全局变量, 既往保全批改项目信息
        var PremGrid;        //全局变量, 既往保全批改项目加费信息
        var SpecGrid;        //全局变量, 既往保全批改项目特别约定信息

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initEdorGrid();
                initPrintGrid();
                initEdorItemGrid();
                initPremGrid();
                initSpecGrid();
                queryCustomerInfo();
                queryCustomerEdor();
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("CustomerNo")[0].value = "<%=request.getParameter("CustomerNo")%>";
                document.getElementsByName("CurEdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 既往保全批单信息查询 MultiLine 的初始化
         */
        function initEdorGrid()
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
                iArray[1][1] = "110px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "保单号";
                iArray[2][1] = "110px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "投保人姓名";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "柜面受理日期";
                iArray[4][1] = "90px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "生效日期";
                iArray[5][1] = "90px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "核保结论";
                iArray[6][1] = "100px";
                iArray[6][2] = 120;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "批改状态";
                iArray[7][1] = "100px";
                iArray[7][2] = 120;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "保全受理号";
                iArray[8][1] = "0px";
                iArray[8][2] = 0;
                iArray[8][3] = 3;
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initEdorGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                EdorGrid = new MulLineEnter("fm", "EdorGrid");
                EdorGrid.mulLineCount = 0;
                EdorGrid.displayTitle = 1;
                EdorGrid.locked = 1;
                EdorGrid.hiddenPlus = 1;
                EdorGrid.hiddenSubtraction = 1;
                EdorGrid.canChk = 0;
                EdorGrid.canSel = 1;
                EdorGrid.chkBoxEventFuncName = "";
                EdorGrid.selBoxEventFuncName = "queryUWPrintEdorItem";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                EdorGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> EdorGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 既往保全批单核保通知书查询 MultiLine 的初始化
         */
        function initPrintGrid()
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
                iArray[1][0] = "打印流水号";
                iArray[1][1] = "110px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "保单号";
                iArray[2][1] = "110px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "通知书类型";
                iArray[3][1] = "160px";
                iArray[3][2] = 200;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "管理机构代码";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "管理机构名称";
                iArray[5][1] = "130px";
                iArray[5][2] = 150;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "打印发起者";
                iArray[6][1] = "0px";
                iArray[6][2] = 0;
                iArray[6][3] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "生成日期";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initPrintGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                PrintGrid = new MulLineEnter("fm", "PrintGrid");
                PrintGrid.mulLineCount = 0;
                PrintGrid.displayTitle = 1;
                PrintGrid.locked = 1;
                PrintGrid.hiddenPlus = 1;
                PrintGrid.hiddenSubtraction = 1;
                PrintGrid.canChk = 0;
                PrintGrid.canSel = 1;
                PrintGrid.chkBoxEventFuncName = "";
                PrintGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                PrintGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initPrintGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 既往保全批改项目信息查询 MultiLine 的初始化
         */
        function initEdorItemGrid()
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
                iArray[1][1] = "105px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "保单号";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "保全项目";
                iArray[3][1] = "80px";
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
                iArray[6][1] = "65px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "生效日期";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "核保结论";
                iArray[8][1] = "65px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "批改状态";
                iArray[9][1] = "0px";
                iArray[9][2] = 0;
                iArray[9][3] = 3;
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initEdorItemGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
                EdorItemGrid.mulLineCount = 0;
                EdorItemGrid.displayTitle = 1;
                EdorItemGrid.locked = 1;
                EdorItemGrid.hiddenPlus = 1;
                EdorItemGrid.hiddenSubtraction = 1;
                EdorItemGrid.canChk = 0;
                EdorItemGrid.canSel = 1;
                EdorItemGrid.chkBoxEventFuncName = "";
                EdorItemGrid.selBoxEventFuncName = "queryEdorPremSpec";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                EdorItemGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initEdorItemGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 既往保全批改项目加费信息查询 MultiLine 的初始化
         */
        function initPremGrid()
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
                iArray[1][0] = "险种号";
                iArray[1][1] = "85px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "责任编码";
                iArray[2][1] = "60px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "加费类型";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "加费评点";
                iArray[4][1] = "60px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "第二被保险人加费评点";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "加费对象";
                iArray[6][1] = "55px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "加费金额";
                iArray[7][1] = "55px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "加费起期";
                iArray[8][1] = "55px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "交至日期";
                iArray[9][1] = "55px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "加费止期";
                iArray[10][1] = "55px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][21] = 3;
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initPremGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                PremGrid = new MulLineEnter("fm", "PremGrid");
                PremGrid.mulLineCount = 1;
                PremGrid.displayTitle = 1;
                PremGrid.locked = 1;
                PremGrid.hiddenPlus = 1;
                PremGrid.hiddenSubtraction = 1;
                PremGrid.canChk = 0;
                PremGrid.canSel = 0;
                PremGrid.chkBoxEventFuncName = "";
                PremGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                PremGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initPremGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 既往保全批改项目特别约定信息查询 MultiLine 的初始化
         */
        function initSpecGrid()
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
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "保单号";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "险种号";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "特约内容";
                iArray[4][1] = "250px";
                iArray[4][2] = 300;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "生成日期";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initSpecGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                SpecGrid = new MulLineEnter("fm", "SpecGrid");
                SpecGrid.mulLineCount = 1;
                SpecGrid.displayTitle = 1;
                SpecGrid.locked = 1;
                SpecGrid.hiddenPlus = 1;
                SpecGrid.hiddenSubtraction = 1;
                SpecGrid.canChk = 0;
                SpecGrid.canSel = 0;
                SpecGrid.chkBoxEventFuncName = "";
                SpecGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                SpecGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorAgoQueryInit.jsp --> initSpecGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

