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
 * @date     : 2005-11-21, 2006-02-16, 2006-10-14
 * @direction: 个单保全生调结果录入初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>
    <%
        String sCurrentDate = PubFun.getCurrentDate();
    %>

    <script language="JavaScript">

        var InquiryTrackGrid;    //全局变量, 生调履历表格

        //总函数，初始化页面
        function initForm()
        {
            try
            {
                initInputBox();
                initInquiryTrackGrid();
            }
            catch (ex)
            {
                alert("在 EdorLiveInquiryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        //输入框的初始化
        function initInputBox()
        {
            try
            {
                document.getElementsByName("CurrentDate")[0].value = "<%=sCurrentDate%>";
            }
            catch (ex)
            {
                alert("在 EdorLiveInquiryInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        //生调轨迹队列 MultiLine 的初始化
        function initInquiryTrackGrid()
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
                iArray[1][0] = "客户号码";
                iArray[1][1] = "70px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "生调日期";
                iArray[2][1] = "70px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;
                iArray[2][21] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "生调结果";
                iArray[3][1] = "70px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;
                iArray[3][21] = 2;

                iArray[4] = new Array();
                iArray[4][0] = "死亡日期";
                iArray[4][1] = "70px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                iArray[4][21] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "死亡原因";
                iArray[5][1] = "90px";
                iArray[5][2] = 150;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "生调员姓名";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "备注";
                iArray[7][1] = "110px";
                iArray[7][2] = 150;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("在 EdorLiveInquiryInit.jsp --> initInquiryTrackGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                InquiryTrackGrid = new MulLineEnter("fm", "InquiryTrackGrid");
                InquiryTrackGrid.mulLineCount = 3;
                InquiryTrackGrid.displayTitle = 1;
                InquiryTrackGrid.locked = 1;
                InquiryTrackGrid.hiddenPlus = 1;
                InquiryTrackGrid.hiddenSubtraction = 1;
                InquiryTrackGrid.canChk = 0;
                InquiryTrackGrid.canSel = 0;
                InquiryTrackGrid.chkBoxEventFuncName = "";
                InquiryTrackGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                InquiryTrackGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 EdorLiveInquiryInit.jsp --> initInquiryTrackGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

