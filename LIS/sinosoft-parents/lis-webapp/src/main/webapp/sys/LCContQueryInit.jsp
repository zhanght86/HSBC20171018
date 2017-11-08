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
 * @date     : 2006-02-20
 * @direction: 保单状态查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var ContStateGrid;    //全局变量, 保单状态队列

        //总函数，初始化整个页面
        function initForm()
        {
            try
            {
                initInputBox();
                initContStateGrid();
                queryContStateGrid();
            }
            catch (ex)
            {
                alert("在 LCContQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        //输入框的初始化
        function initInputBox()
        {
            try
            {
                document.getElementsByName("ContNo")[0].value = "<%=request.getParameter("ContNo")%>";
            }
            catch (ex)
            {
                alert("在 LCContQueryInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        //保单状态队列查询 MultiLine 的初始化
        function initContStateGrid()
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
                iArray[1][1] = "110px";
                iArray[1][2] = 120;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "险种代码";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "险种名称";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "状态类型";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "保单状态";
                iArray[5][1] = "50px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "状态原因";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "状态起始日期";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 8;

                iArray[8] = new Array();
                iArray[8][0] = "状态结束日期";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 8;

                iArray[9] = new Array();
                iArray[9][0] = "备注";
                iArray[9][1] = "100px";
                iArray[9][2] = 120;
                iArray[9][3] = 0;
            }
            catch (ex)
            {
                alert("在 LCContQueryInit.jsp --> initContStateGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                ContStateGrid = new MulLineEnter("fm", "ContStateGrid");
                ContStateGrid.mulLineCount = 0;
                ContStateGrid.displayTitle = 1;
                ContStateGrid.locked = 1;
                ContStateGrid.hiddenPlus = 1;
                ContStateGrid.hiddenSubtraction = 1;
                ContStateGrid.canChk = 0;
                ContStateGrid.canSel = 0;
                ContStateGrid.chkBoxEventFuncName = "";
                ContStateGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                ContStateGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 LCContQueryInit.jsp --> initContStateGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

