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
 * @date     : 2006-12-06, 2006-12-18
 * @direction: 影像迁移初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        String sOperator = tGlobalInput.Operator;
        tGlobalInput = null;
    %>

    <script language="JavaScript">

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
            }
            catch (ex)
            {
                alert("在 ImgMoveInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 隐藏域的初始化
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
            }
            catch (ex)
            {
                alert("在 ImgMoveInit.jsp --> initHiddenArea 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("OldManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("NewManageCom")[0].value = "86";
                showOneCodeName("Station", "OldManageCom", "OldManageComName");
                showOneCodeName("Station", "NewManageCom", "NewManageComName");
            }
            catch (ex)
            {
                alert("在 ImgMoveInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

