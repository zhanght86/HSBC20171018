<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

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
 * @date     : 2006-12-26
 * @direction: 影像迁移日志查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>影像迁移日志查询</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
    <script language="JavaScript" src="../common/Calendar/Calendar.js"></script>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="ImgMoveLogInfo.js"></script>
    <%@ include file="ImgMoveLogInfoInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" method="post" target="fraSubmit">
        <!-- 查询条件折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divQueryInput)"></td>
                <td class="titleImg">输入查询条件</td>
           </tr>
        </table>
        <div id="divQueryInput" style="display:''">
            <!-- 查询条件录入表格 -->
            <table class="common">
                <tr class="common">
                    <td class="title">迁出机构</td>
                    <td class="input">
                    <!-- 以后如果需要扩展到机构对机构传送,则换成下面2个注释掉的就可以 -->
                    <!-- <input type = "text" class = "codeno" name = "OldManageCom" ondblclick="return showCodeList('station',[this,OldManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,OldManageComName],[0,1]);"><input class=codename name=OldManageComName readonly></TD> -->
                    <input type="text" class="codeno" name="OldManageCom"><input type="text" class="codename" name="OldManageComName"></td>
                    <td class="title">迁入机构</td>
                    <td class="input">
                    <!-- <input type = "text" class = "codeno" name = "NewManageCom" ondblclick="return showCodeList('station',[this,NewManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,NewManageComName],[0,1]);"><input class=codename name=NewManageComName readonly></TD> -->
                    <input type="text" class="codeno" name="NewManageCom" readonly><input type="text" class="codename" name="NewManageComName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">扫描起期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="StartDate" dateFormat="short"></td>
                    <td class="title">扫描止期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="EndDate" dateFormat="short"></td>
                </tr>
            </table>
            <!-- 提交数据操作按钮 -->
            <input type="button" class="cssButton" value=" 查 询 " onClick="queryTaskLogGrid()">
        </div>
        <!-- 迁移批次信息展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divTaskLogGrid)"></td>
                <td class="titleImg">迁移批次信息</td>
            </tr>
        </table>
        <!-- 批次信息展现表格 -->
        <div id="divTaskLogGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanTaskLogGrid"></span></td>
                </tr>
            </table>
            <!-- 批次信息结果翻页 -->
            <div id="divTurnPageTaskLogGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="首  页" onclick="turnPageTaskLogGrid.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPageTaskLogGrid.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPageTaskLogGrid.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPageTaskLogGrid.lastPage()">
            </div>
        </div>
        <!-- 批次错误信息展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divMoveErrorGrid)"></td>
                <td class="titleImg">批次错误信息</td>
            </tr>
        </table>
        <!-- 错误信息展现表格 -->
        <div id="divMoveErrorGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanMoveErrorGrid"></span></td>
                </tr>
            </table>
            <!-- 错误信息结果翻页 -->
            <div id="divTurnPageMoveErrorGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="首  页" onclick="turnPageMoveErrorGrid.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPageMoveErrorGrid.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPageMoveErrorGrid.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPageMoveErrorGrid.lastPage()">
            </div>
            <div id="divTurnPageResend" style="display:'none'">
                <!-- 提交数据操作按钮 -->
                <input type="button" class="cssButton" value=" 重 传 " onClick="resend()">
            </div> 
        </div>       
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="LoginManageCom">
        <input type="hidden" name="LoginOperator">
        <input type="hidden" name="StartDate">
        <input type="hidden" name="EndDate">
        <input type="hidden" name="DocID" value="">
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
