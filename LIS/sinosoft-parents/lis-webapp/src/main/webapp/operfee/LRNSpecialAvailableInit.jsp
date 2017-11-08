
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
	 * @date     : 2005-11-29, 2005-12-03, 2006-02-15, 2006-02-28, 2006-03-23
	 * @direction: 保单特殊复效录入初始化
	 ******************************************************************************/
%>

<!-- 调用 JSP Init 初始化页面 : 开始 -->
<%@ page import="com.sinosoft.lis.pubfun.*"%>

<%
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI");
	String sManageCom = tGlobalInput.ManageCom;
	tGlobalInput = null;
%>

<script language="JavaScript">
        var AvailableGrid;    //全局变量, 失效保单队列

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initAvailableGrid();
            }
            catch (ex)
            {
                alert("在 LRNSpecialAvailableInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("ManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("在 LRNSpecialAvailableInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 失效合同队列息列表 MultiLine 的初始化
         */
        function initAvailableGrid()
        {
            var iArray = new Array();                          //总数组, 返回给 MultiLine 表格
            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

                iArray[1] = new Array();
                iArray[1][0] = "险种代码";
                iArray[1][1] = "65px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "险种名称";
                iArray[2][1] = "120px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "被保人客户号";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "被保人姓名";
                iArray[4][1] = "65px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "生效日期";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "保单状态";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                
                iArray[7] = new Array();
                iArray[7][0] = "失效原因";
                iArray[7][1] = "120px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("在 LRNSpecialAvailableInit.jsp --> initAvailableGrid 函数中发生异常: 初始化数组错误！");
            }
            try
            {
                AvailableGrid = new MulLineEnter("fm", "AvailableGrid");
                AvailableGrid.mulLineCount = 5;
                AvailableGrid.displayTitle = 1;
                AvailableGrid.locked = 1;
                AvailableGrid.hiddenPlus = 1;
                AvailableGrid.hiddenSubtraction = 1;
                AvailableGrid.canChk = 0;
                AvailableGrid.canSel = 0;
                AvailableGrid.chkBoxEventFuncName = "";
                AvailableGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                AvailableGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 LRNSpecialAvailableInit.jsp --> initAvailableGrid 函数中发生异常: 初始化界面错误！");
            }
        }
</script>

<!-- 调用 JSP Init 初始化页面 : 结束 -->

