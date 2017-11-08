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
 * @date     : 2005-12-03, 2006-02-15
 * @direction: 保单特殊复效查询初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var QueryGrid;    //全局变量, 失效保单查询

        //总函数，初始化整个页面
        function initForm()
        {
            try
            {
                initQueryGrid();
            }
            catch (ex)
            {
                alert("在 LRNSpecialAvailableQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        //复效合同信息查询 MultiLine 的初始化
        function initQueryGrid()
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
                iArray[1][0] = "合同号";
                iArray[1][1] = "100px";
                iArray[1][2] = 120;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "复效次数";
                iArray[2][1] = "45px";
                iArray[2][2] = 80;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "失效原因";
                iArray[3][1] = "85px";
                iArray[3][2] = 120;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "管理机构";
                iArray[4][1] = "115px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "备注";
                iArray[5][1] = "90px";
                iArray[5][2] = 150;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "操作员";
                iArray[6][1] = "60px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "复效日期";
                iArray[7][1] = "60px";
                iArray[7][2] = 80;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "复效时间";
                iArray[8][1] = "60px";
                iArray[8][2] = 80;
                iArray[8][3] = 0;

            }
            catch (ex)
            {
                alert("在 LRNSpecialAvailableQueryInit.jsp --> initQueryGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                QueryGrid = new MulLineEnter("fm", "QueryGrid");
                QueryGrid.mulLineCount = 5;
                QueryGrid.displayTitle = 1;
                QueryGrid.locked = 1;
                QueryGrid.hiddenPlus = 1;
                QueryGrid.hiddenSubtraction = 1;
                QueryGrid.canChk = 0;
                QueryGrid.canSel = 0;
                QueryGrid.chkBoxEventFuncName = "";
                QueryGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                QueryGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 LRNSpecialAvailableQueryInit.jsp --> initQueryGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

