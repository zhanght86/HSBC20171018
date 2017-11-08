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
 * @date     : 2006-03-01
 * @direction: 补发打印查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var ReissuePrintGrid;    //全局变量, 补发打印队列

        //总函数，初始化整个页面
        function initForm()
        {
            try
            {
                initInputBox();
                initReissuePrintGrid();
                queryReissuePrintGrid();
            }
            catch (ex)
            {
                alert("在 ReissuePrintQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        //输入框的初始化
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = NullToEmpty("<%=request.getParameter("EdorAcceptNo")%>");
                document.getElementsByName("ContNo")[0].value = NullToEmpty("<%=request.getParameter("ContNo")%>");
            }
            catch (ex)
            {
                alert("在 ReissuePrintQueryInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        //补发打印队列查询 MultiLine 的初始化
        function initReissuePrintGrid()
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
                iArray[1][0] = "保全受理号";
                iArray[1][1] = "100px";
                iArray[1][2] = 120;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "批单号";
                iArray[2][1] = "100px";
                iArray[2][2] = 120;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "补退费金额";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 7;
                iArray[3][23] = "0";

                iArray[4] = new Array();
                iArray[4][0] = "受理日期";
                iArray[4][1] = "65px";
                iArray[4][2] = 100;
                iArray[4][3] = 8;

                iArray[5] = new Array();
                iArray[5][0] = "复核日期";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 8;

                iArray[6] = new Array();
                iArray[6][0] = "生效日期";
                iArray[6][1] = "65px";
                iArray[6][2] = 100;
                iArray[6][3] = 8;

                iArray[7] = new Array();
                iArray[7][0] = "打印日期";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 8;

                iArray[8] = new Array();
                iArray[8][0] = "打印时间";
                iArray[8][1] = "65px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "打印操作员";
                iArray[9][1] = "65px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                
                iArray[10]=new Array();
								iArray[10][0]="币种";
								iArray[10][1]="60px";
								iArray[10][2]=100;
								iArray[10][3]=2;
								iArray[10][4]="currency";
            }
            catch (ex)
            {
                alert("在 ReissuePrintQueryInit.jsp --> initReissuePrintGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                ReissuePrintGrid = new MulLineEnter("fm", "ReissuePrintGrid");
                ReissuePrintGrid.mulLineCount = 0;
                ReissuePrintGrid.displayTitle = 1;
                ReissuePrintGrid.locked = 1;
                ReissuePrintGrid.hiddenPlus = 1;
                ReissuePrintGrid.hiddenSubtraction = 1;
                ReissuePrintGrid.canChk = 0;
                ReissuePrintGrid.canSel = 0;
                ReissuePrintGrid.chkBoxEventFuncName = "";
                ReissuePrintGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                ReissuePrintGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 ReissuePrintQueryInit.jsp --> initReissuePrintGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

