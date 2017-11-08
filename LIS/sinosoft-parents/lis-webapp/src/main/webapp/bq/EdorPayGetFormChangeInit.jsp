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
 * @date     : 2006-03-16, 2006-06-28, 2006-11-08
 * @direction: 保全收付费方式变更初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>

    <script language="JavaScript">

        var ChgTrackGrid;    //全局变量, 变更轨迹信息
        var EdorInfoGrid;    //全局变量, 保全批改信息

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                //initChgTrackGrid();
                initEdorInfoGrid();
            }
            catch (ex)
            {
                alert("在 EdorPayGetFormChangeInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 隐藏域的初始化
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("CurrentManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("在 EdorPayGetFormChangeInit.jsp --> initHiddenArea 函数中发生异常: 初始化界面错误！");
            }
        }


        /**
         * 变更轨迹队列 MultiLine 的初始化
         */
        function initChgTrackGrid()
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
                iArray[1][0] = "变更类型";
                iArray[1][1] = "50px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "原方式";
                iArray[2][1] = "55px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "新方式";
                iArray[3][1] = "55px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "新开户行";
                iArray[4][1] = "60px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                

                iArray[5] = new Array();
                iArray[5][0] = "新帐号";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "新帐户名";
                iArray[6][1] = "55px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "新领取人";
                iArray[7][1] = "55px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "新身份证号";
                iArray[8][1] = "90px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "变更日期";
                iArray[9][1] = "60px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;
            }
            catch (ex)
            {
                alert("在 EdorPayGetFormChangeInit.jsp --> initChgTrackGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                ChgTrackGrid = new MulLineEnter("document", "ChgTrackGrid");
                ChgTrackGrid.mulLineCount = 0;
                ChgTrackGrid.displayTitle = 1;
                ChgTrackGrid.locked = 1;
                ChgTrackGrid.hiddenPlus = 1;
                ChgTrackGrid.hiddenSubtraction = 1;
                //ChgTrackGrid.canChk = 0;
                //ChgTrackGrid.canSel = 0;
                //ChgTrackGrid.chkBoxEventFuncName = "";
                //ChgTrackGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                ChgTrackGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorPayGetFormChangeInit.jsp --> initChgTrackGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 批改信息队列 MultiLine 的初始化
         */
        function initEdorInfoGrid()
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
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "保单号";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "管理机构";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;
               

                iArray[4] = new Array();
                iArray[4][0] = "保全项目";
                iArray[4][1] = "55px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
               

                iArray[5] = new Array();
                iArray[5][0] = "补退费金额";
                iArray[5][1] = "60px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
                

                iArray[6] = new Array();
                iArray[6][0] = "补退费利息";
                iArray[6][1] = "0px";
                iArray[6][2] = 0;
                iArray[6][3] = 3;
    

                iArray[7] = new Array();
                iArray[7][0] = "交费/到帐日";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                

                iArray[8] = new Array();
                iArray[8][0] = "生效日期";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
   

                iArray[9] = new Array();
                iArray[9][0] = "补退费通知书号";
                iArray[9][1] = "100px";
                iArray[9][2] = 0;
                iArray[9][3] = 0;
                
                iArray[10] = new Array();
                iArray[10][0] = "交费方式";
                iArray[10][1] = "40px";
                iArray[10][2] = 0;
                iArray[10][3] = 0;
                
                iArray[11] = new Array();
                iArray[11][0] = "领取人";
                iArray[11][1] = "60px";
                iArray[11][2] = 0;
                iArray[11][3] = 0;
                
                iArray[12] = new Array();
                iArray[12][0] = "领取人身份证";
                iArray[12][1] = "120px";
                iArray[12][2] = 0;
                iArray[12][3] = 0;
                
                iArray[13] = new Array();
                iArray[13][0] = "银行编码";
                iArray[13][1] = "0px";
                iArray[13][2] = 0;
                iArray[13][3] = 3;
                
                iArray[14] = new Array();
                iArray[14][0] = "银行账号";
                iArray[14][1] = "0px";
                iArray[14][2] = 0;
                iArray[14][3] = 3;
                
                iArray[15] = new Array();
                iArray[15][0] = "账户名";
                iArray[15][1] = "0px";
                iArray[15][2] = 0;
                iArray[15][3] = 3;
                
                iArray[16] = new Array();
                iArray[16][0] = "实付/实收号";
                iArray[16][1] = "0px";
                iArray[16][2] = 0;
                iArray[16][3] = 3;
            }
            catch (ex)
            {
                alert("在 EdorPayGetFormChangeInit.jsp --> initEdorInfoGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                EdorInfoGrid = new MulLineEnter("document", "EdorInfoGrid");
                EdorInfoGrid.mulLineCount =5;
                EdorInfoGrid.displayTitle = 1;
                EdorInfoGrid.locked = 1;
                EdorInfoGrid.hiddenPlus = 1;
                EdorInfoGrid.hiddenSubtraction = 1;
                EdorInfoGrid.canChk = 0;
                EdorInfoGrid.canSel = 1;
                //EdorInfoGrid.chkBoxEventFuncName = "";
                EdorInfoGrid.selBoxEventFuncName = "ShowSubAccInfo";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                EdorInfoGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorPayGetFormChangeInit.jsp --> initEdorInfoGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

