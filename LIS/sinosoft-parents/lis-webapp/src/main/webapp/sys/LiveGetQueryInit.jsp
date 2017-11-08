<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2005-12-02, 2006-02-22, 2006-05-12, 2006-11-14
 * @direction: 综合查询生存领取查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var LiveGetGrid;      //全局变量, 生存领取队列
        var BankProxyGrid;    //全局变量, 代收代付队列

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initLiveGetGrid();
                initBankProxyGrid();
                queryBankInfo();
                queryLiveGetGrid();
            }
            catch (ex)
            {
                alert("在 LiveGetQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
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
                document.getElementsByName("PolNo")[0].value = "<%=request.getParameter("PolNo")%>";
                document.getElementsByName("RiskCode")[0].value = "<%=request.getParameter("RiskCode")%>";
            }
            catch (ex)
            {
                alert("在 LiveGetQueryInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 生存领取队列 MultiLine 的初始化
         */
        function initLiveGetGrid()
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
                iArray[1][0] = "实付号码";
                iArray[1][1] = "105px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "责任编码";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "给付责任编码";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "给付责任类型";
                iArray[4][1] = "110px";
                iArray[4][2] = 150;
                iArray[4][3] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "给付金额";
                iArray[5][1] = "60px";
                iArray[5][2] = 100;
                iArray[5][3] = 7;
                iArray[5][21] = 3;
                iArray[5][23] = "0";

                iArray[6] = new Array();
                iArray[6][0] = "应付日期";
                iArray[6][1] = "60px";
                iArray[6][2] = 100;
                iArray[6][3] = 8;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "业务核销日期";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 8;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "财务到账日期";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 8;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "本次领至日期";
                iArray[9][1] = "60px";
                iArray[9][2] = 100;
                iArray[9][3] = 8;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "是否生调";
                iArray[10][1] = "45px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][21] = 2;
                
                iArray[11]=new Array();
								iArray[11][0]="币种";
								iArray[11][1]="60px";
								iArray[11][2]=100;
								iArray[11][3]=2;
								iArray[11][4]="currency";
            }
            catch (ex)
            {
                alert("在 LiveGetQueryInit.jsp --> initLiveGetGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                LiveGetGrid = new MulLineEnter("fm", "LiveGetGrid");
                LiveGetGrid.mulLineCount = 0;
                LiveGetGrid.displayTitle = 1;
                LiveGetGrid.locked = 1;
                LiveGetGrid.hiddenPlus = 1;
                LiveGetGrid.hiddenSubtraction = 1;
                LiveGetGrid.canChk = 0;
                LiveGetGrid.canSel = 1;
                LiveGetGrid.chkBoxEventFuncName = "";
                LiveGetGrid.selBoxEventFuncName = "queryBankProxyGrid";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                LiveGetGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 LiveGetQueryInit.jsp --> initLiveGetGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 代收代付队列 MultiLine 的初始化
         */
        function initBankProxyGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 1表示允许; 0表示不允许。

                iArray[1] = new Array();
                iArray[1][0] = "领取通知书号";
                iArray[1][1] = "110px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "开户银行";
                iArray[2][1] = "70px";
                iArray[2][2] = 200;
                iArray[2][3] = 2;
                iArray[2][4] = "Bank";

                iArray[3] = new Array();
                iArray[3][0] = "银行账号";
                iArray[3][1] = "120px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "账户名";
                iArray[4][1] = "70px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "划款日期";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 8;
                iArray[5][21] = 3;

                iArray[6] = new Array();
                iArray[6][0] = "划款结果";
                iArray[6][1] = "65px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 2;

                iArray[7] = new Array();
                iArray[7][0] = "划款不成功原因";
                iArray[7][1] = "130px";
                iArray[7][2] = 150;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("在 LiveGetQueryInit.jsp --> initBankProxyGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                BankProxyGrid = new MulLineEnter("fm", "BankProxyGrid");
                BankProxyGrid.mulLineCount = 0;
                BankProxyGrid.displayTitle = 1;
                BankProxyGrid.locked = 1;
                BankProxyGrid.hiddenPlus = 1;
                BankProxyGrid.hiddenSubtraction = 1;
                BankProxyGrid.canChk = 0;
                BankProxyGrid.canSel = 0;
                BankProxyGrid.chkBoxEventFuncName = "";
                BankProxyGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                BankProxyGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 LiveGetQueryInit.jsp --> initBankProxyGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

