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
 * @date     : 2007-03-20
 * @direction: 保全保单密码修改轨迹查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var PwdTrackGrid;    //全局变量, 修改轨迹队列

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initPwdTrackGrid();
                queryPwdTrackGrid();
            }
            catch (ex)
            {
                alert("在 PwdChangeTrackQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("ContNo")[0].value = "<%=request.getParameter("ContNo")%>";
            }
            catch (ex)
            {
                alert("在 PwdChangeTrackQueryInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 修改轨迹队列查询 MultiLine 的初始化
         */
        function initPwdTrackGrid()
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
                iArray[1][0] = "保单号";
                iArray[1][1] = "120px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "投保人客户号";
                iArray[2][1] = "80px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "投保人姓名";
                iArray[3][1] = "80px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "被保人客户号";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "被保人姓名";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "修改日期";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "修改时间";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "操作员";
                iArray[8][1] = "80px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
            }
            catch (ex)
            {
                alert("在 PwdChangeTrackQueryInit.jsp --> initPwdTrackGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                PwdTrackGrid = new MulLineEnter("fm", "PwdTrackGrid");
                PwdTrackGrid.mulLineCount = 0;
                PwdTrackGrid.displayTitle = 1;
                PwdTrackGrid.locked = 1;
                PwdTrackGrid.hiddenPlus = 1;
                PwdTrackGrid.hiddenSubtraction = 1;
                PwdTrackGrid.canChk = 0;
                PwdTrackGrid.canSel = 0;
                PwdTrackGrid.chkBoxEventFuncName = "";
                PwdTrackGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                PwdTrackGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 PwdChangeTrackQueryInit.jsp --> initPwdTrackGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

