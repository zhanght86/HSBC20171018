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
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2005-11-03, 2005-12-03, 2006-02-08, 2006-11-14
 * @direction: 代收代付查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>代收代付查询</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "ProxyIncomePayQueryMain.jsp";
        }
    </script>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本  -->
    <script language="JavaScript" src="../common/laydate/laydate.js"></script>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="ProxyIncomePayQuery.js"></script>
    <%@ include file="ProxyIncomePayQueryInit.jsp" %>
</head>
<body topmargin="0" onLoad="initForm()" ondragstart="return false">
    <form name="fm" id="fm">
        <!-- 查询条件折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divInputQueryLayer)"></td>
                <td class="titleImg">保单信息</td>
            </tr>
        </table>
        <!-- 查询条件录入表格 -->
        <div id="divInputQueryLayer" class="maxbox1" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">保单号</td>
                    <td class="input"><input type="text" class="common wid" name="OtherNo" id="OtherNo" maxlength="24"></td>
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="common wid" name="EdorAcceptNo" id="EdorAcceptNo" maxlength="24"></td>
                    <td class="title">批单号</td>
                    <td class="input"><input type="text" class="common wid" name="EdorNo" id="EdorNo" maxlength="24"></td>
                </tr>
            </table>
        </div>
        <input type="button" class="cssButton" value=" 查 询 " onClick="queryProxyGrid()">
        <br><br>
        <!-- 查询结果折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divQueryResult)"></td>
                <td class="titleImg">查询结果</td>
           </tr>
        </table>
        <!-- 查询结果展现表格 -->
        <div id="divQueryResult" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanProxyGrid"></span></td>
                </tr>
            </table>
            <!-- 查询结果翻页按钮 -->
            <div id="divTurnPageProxyGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="首  页" onClick="turnPageProxyGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onClick="turnPageProxyGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onClick="turnPageProxyGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onClick="turnPageProxyGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- 提交数据操作按钮 -->
        <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
        <!-- 通用下拉信息列表 -->
        <span id="spanCode" style="display:none; position:absolute; slategray"></span>
    </form>
</body>
</html>
