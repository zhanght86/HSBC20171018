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
 * @date     : 2006-03-01
 * @direction: 补发打印查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>补发打印查询</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "ReissuePrintQueryMain.jsp";
        }
    </script>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <script language="JavaScript" src="../common/laydate/laydate.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="ReissuePrintQuery.js"></script>
    <%@ include file="ReissuePrintQueryInit.jsp" %>
</head>
<body topmargin="0" onLoad="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- 保单信息显示表格 -->
       <Br>
        <table class="common">
            <tr class="common">
                <td class="title5">保全受理号</td>
                <td class="input5"><input type="text" class="readonly wid" name="EdorAcceptNo" id="EdorAcceptNo" readonly></td>
                <td class="title5">保单号码</td>
                <td class="input5"><input type="text" class="readonly wid" name="ContNo" id="ContNo" readonly></td>
            </tr>
        </table>
        <!-- 补发打印折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onClick="showPage(this,divReissuePrintList)"></td>
                <td class="titleImg">补发打印信息</td>
           </tr>
        </table>
        <!-- 补发打印结果展现 -->
        <div id="divReissuePrintList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanReissuePrintGrid"></span></td>
                </tr>
            </table>
            <!-- 保单状态结果翻页 -->
            <div align="center" style= "display:">
                <input type="button" class="cssButton90" value="首  页" onClick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onClick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onClick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onClick="turnPage.lastPage()">
            </div>
        </div>
        <br>
        <!-- 提交数据操作按钮 -->
        <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	<br/><br/><br/><br/>
</body>
</html>
