<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-11-03, 2005-12-03, 2006-02-08
 * @direction: 代收代付查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var ProxyGrid;    //全局变量, 代收代付队列

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initProxyGrid();
                queryProxyGrid();
            }
            catch (ex)
            {
                alert("在 ProxyIncomePayQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("OtherNo")[0].value = "<%=request.getParameter("OtherNo")%>";
            }
            catch (ex)
            {
                alert("在 ProxyIncomePayQueryInit.jsp --> InitInpBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 代收代付队列 MultiLine 的初始化
         */
        function initProxyGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 10;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 1表示允许; 0表示不允许。

                iArray[1] = new Array();
                iArray[1][0] = "保全受理号";
                iArray[1][1] = "90px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "保全项目";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 2;
                iArray[2][4] = "PEdorType";

                iArray[3] = new Array();
                iArray[3][0] = "补退费金额";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 7;
                iArray[3][21] = 3;
                iArray[3][23] = "0";

                iArray[4] = new Array();
                iArray[4][0] = "操作日期";
                iArray[4][1] = "60px";
                iArray[4][2] = 100;
                iArray[4][3] = 8;
                iArray[4][21] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "开户银行";
                iArray[5][1] = "60px";
                iArray[5][2] = 100;
                iArray[5][3] = 2;
                iArray[5][4] = "Bank";

                iArray[6] = new Array();
                iArray[6][0] = "账号";
                iArray[6][1] = "110px";
                iArray[6][2] = 150;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "账户名";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "划款日期";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 8;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "划款结果";
                iArray[9][1] = "50px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 2;

                iArray[10] = new Array();
                iArray[10][0] = "划款不成功原因";
                iArray[10][1] = "80px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                
                iArray[11]=new Array();
								iArray[11][0]="币种";
								iArray[11][1]="60px";
								iArray[11][2]=100;
								iArray[11][3]=2;
								iArray[11][4]="currency";
            }
            catch (ex)
            {
                alert("在 ProxyIncomePayQueryInit.jsp --> initProxyGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                ProxyGrid = new MulLineEnter("fm", "ProxyGrid");
                ProxyGrid.mulLineCount = 1;
                ProxyGrid.displayTitle = 1;
                ProxyGrid.locked = 1;
                ProxyGrid.hiddenPlus = 1;
                ProxyGrid.hiddenSubtraction = 1;
                ProxyGrid.canChk = 0;
                ProxyGrid.canSel = 0;
                ProxyGrid.chkBoxEventFuncName = ""
                ProxyGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                ProxyGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 ProxyIncomePayQueryInit.jsp --> initProxyGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

