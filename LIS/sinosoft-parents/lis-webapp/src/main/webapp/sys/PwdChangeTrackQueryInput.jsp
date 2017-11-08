<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-03-20
 * @direction: 保全保单密码修改轨迹查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保单密码修改轨迹查询</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PwdChangeTrackQueryMain.jsp";
        }
    </script>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PwdChangeTrackQuery.js"></script>
    <%@ include file="PwdChangeTrackQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 保单信息显示表格 -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">保单号码</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
        <!-- 修改轨迹折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPwdTrackGrid)"></td>
                <td class="titleImg">密码修改轨迹</td>
           </tr>
        </table>
        <!-- 修改轨迹结果展现 -->
        <div id="divPwdTrackGrid" style="display: ">
            <table class="common">
                <tr class="common">
                    <td><span id="spanPwdTrackGrid"></span></td>
                </tr>
            </table>
            <!-- 修改轨迹结果翻页 -->
            <div id="divTurnPagePwdTrackGrid" align="center" style="display:none;">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPagePwdTrackGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPagePwdTrackGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPagePwdTrackGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPagePwdTrackGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- 提交数据操作按钮 -->
        <input type="button" class="cssButton" value=" 返 回 " onclick="returnParent()">
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
