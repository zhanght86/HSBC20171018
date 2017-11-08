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
 * @date     : 2005-12-02, 2006-02-22, 2006-05-12, 2006-11-14
 * @direction: 综合查询生存领取查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>生存领取查询</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
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
    <script language="JavaScript" src="LiveGetQuery.js"></script>
    <%@ include file="LiveGetQueryInit.jsp" %>
</head>
<body topmargin="0" onLoad="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- 保单信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divPolInfo)"></td>
                <td class="titleImg">保单信息</td>
           </tr>
        </table>
        <!-- 保单信息显示表格 -->
        <div id="divPolInfo" class="maxbox" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">保单号码</td>
                    <td class="input"><input type="text" class="readonly wid" name="ContNo" id="ContNo" readonly></td>
                    <td class="title">险种号码</td>
                    <td class="input"><input type="text" class="readonly wid" name="PolNo" id="PolNo" readonly></td>
                    <td class="title">险种编码</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" id="RiskCode" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">投保人姓名</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" id="AppntName" readonly></td>
                    <td class="title">被保人姓名</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" id="InsuredName" readonly></td>
                    <td class="title">领取形式</td>
                    <td class="input"><input type="text" class="codeno" name="GetForm" id="GetForm" readonly><input type="text" class="codename" name="GetFormName" readonly></td>
                </tr>
                <tr class="common">
                  <td class="title">开户银行</td>
                  <td class="input"><input type="text" class="codeno" name="GetBank" id="GetBank" readonly><input type="text" class="codename" name="GetBankName" readonly></td>
                  <td class="title">银行帐号</td>
                  <td class="input"><input type="text" class="readonly wid" name="GetBankAccNo" id="GetBankAccNo" readonly></td>
                  <td class="title">帐户名</td>
                  <td class="input"><input type="text" class="readonly wid" name="GetAccName" id="GetAccName" readonly></td>
                </tr>
            </table>
        </div>
        <!-- 领取信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divLiveGetList)"></td>
                <td class="titleImg">领取信息</td>
           </tr>
        </table>
        <!-- 领取信息展现表格 -->
        <div id="divLiveGetList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanLiveGetGrid"></span></td>
                </tr>
            </table>
            <!-- 领取信息结果翻页 -->
            <div id="divTurnPageLiveGetGrid" align="center" style="display:none">
                <input type="button" class="cssButton" value="首  页" onClick="turnPageLiveGetGrid.firstPage()">
                <input type="button" class="cssButton" value="上一页" onClick="turnPageLiveGetGrid.previousPage()">
                <input type="button" class="cssButton" value="下一页" onClick="turnPageLiveGetGrid.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onClick="turnPageLiveGetGrid.lastPage()">
            </div>
        </div>
        <!-- 代付信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBankProxyList)"></td>
                <td class="titleImg">代付信息</td>
           </tr>
        </table>
        <!-- 代付信息展现表格 -->
        <div id="divBankProxyList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanBankProxyGrid"></span></td>
                </tr>
            </table>
            <!-- 代付信息结果翻页 -->
            <div id="divTurnPageBankProxyGrid" align="center" style="display:none">
                <input type="button" class="cssButton90" value="首  页" onClick="turnPageBankProxyGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onClick="turnPageBankProxyGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onClick="turnPageBankProxyGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onClick="turnPageBankProxyGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- 提交数据操作按钮 -->
        <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
