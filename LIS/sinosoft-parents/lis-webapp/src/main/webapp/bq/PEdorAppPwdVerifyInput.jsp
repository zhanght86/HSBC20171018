<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

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
 * @date     : 2006-11-06
 * @direction: 保全申请添加批改项目校验保单密码主框架
 * @comment  : 猪的生日 :)
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保单密码校验</title>
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
    <script language="JavaScript" src="PEdorAppPwdVerify.js"></script>
    <%@ include file="PEdorAppPwdVerifyInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 号码信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorOtherInfo)"></td>
                <td class="titleImg">保全申请信息</td>
            </tr>
        </table>
        <!-- 号码信息展现表格 -->
        <div id="divEdorOtherInfo" style="display:''" class=maxbox1>
            <table class="common">
                <tr class="common">
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                    <td class="title">申请号码</td>
                    <td class="input"><input type="text" class="readonly wid" name="OtherNo" id=OtherNo readonly></td>
                    <td class="title">号码类型</td>
                    <td class="input"><input class="codeno" name="OtherNoType" readonly><input class="codename" name="OtherNoTypeName" id=OtherNoTypeName readonly></td>
                </tr>              
                <tr class="common">
                    <td class="title">柜面受理日期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</td>
                    <td class="title">申请方式</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" id=AppType readonly><input type="text" class="codename" name="AppTypeName" id=AppTypeName readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </div>
        <!-- 保单密码折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divContPwdGrid)"></td>
                <td class="titleImg">输入保单密码</td>
            </tr>
        </table>
        <!-- 保单密码展现表格 -->
        <div id="divContPwdGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanContPwdGrid"></span></td>
                </tr>
            </table>
            <!-- 保单密码结果翻页 -->
            <div id="divTurnPageContPwdGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageContPwdGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageContPwdGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageContPwdGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageContPwdGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- 提交数据操作按钮 -->
        <input type="button" class="cssButton" value=" 确 定 " onclick="verifyContPwd()">
        <input type="button" class="cssButton" value=" 取 消 " onclick="verifyPwdFail()">
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	<br/><br/><br/><br/>
</body>
</html>
