<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01
 * @date     : 2006-10-10, 2006-11-22
 * @direction: 保全受益人变更主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>受益人变更</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeBC.jsp";
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
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdorTypeBC.js"></script>
    <%@ include file="PEdorTypeBCInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 保全受理通用信息 -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">保全受理号</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">批改类型</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">保单号</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
            </tr>
            <tr class="common">
                <td class="title">柜面受理日期</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">生效日期</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorValiDate" readonly onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
        <!-- 险种信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divPolInfo)"></td>
                <td class="titleImg">保单险种信息</td>
            </tr>
        </table>
        <!-- 保单险种信息表格 -->
        <div id= "divPolInfo" class=maxbox1 style= "display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">险种代码</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" id=RiskCode readonly></td>
                    <td class="title">险种名称</td>
                    <td class="input" colspan="3"><input type="text" class="common3" name="RiskName" id=RiskName readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">生效日期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="CValiDate" readonly onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                    <td class="title">交至日期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="PayToDate" readonly onClick="laydate({elem: '#PayToDate'});" id="PayToDate"><span class="icon"><a onClick="laydate({elem: '#PayToDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                    <td class="title">总保费</td>
                    <td class="input"><input type="text" class="readonly wid" name="Prem" id=Prem readonly></td>
                </tr>
            </table>
        </div>
        <!-- 客户信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divCustomerLayer)"></td>
                <td class="titleImg">保单客户信息</td>
            </tr>
        </table>
        <!-- 客户信息展现表格 -->
        <div id="divCustomerLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanCustomerGrid"></span></td>
                </tr>
            </table>
            <!-- 客户信息结果翻页 -->
            <div id="divTurnPageCustomerGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageCustomerGrid.lastPage()">
            </div>
        </div>
        <!-- 原受益人折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divOldBnfGridLayer)"></td>
                <td class="titleImg">原受益人信息</td>
            </tr>
        </table>
        <!-- 原受益人展现表格 -->
        <div id="divOldBnfGridLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanOldBnfGrid"></span></td>
                </tr>
            </table>
            <!-- 原受益人结果翻页 -->
            <div id="divTurnPageOldBnfGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageOldBnfGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageOldBnfGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageOldBnfGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageOldBnfGrid.lastPage()">
            </div>
        </div>
        <!-- 新受益人折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divNewBnfGridLayer)"></td>
                <td class="titleImg">新受益人信息:<font color=red>如果证件为身份证,性别、出生日期不用填写</font></td>
            </tr>
        </table>
        <!-- 新受益人录入表格 -->
        <div id="divNewBnfGridLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanNewBnfGrid"></span></td>
                </tr>
            </table>
            <!-- 新受益人结果翻页 -->
            <div id="divTurnPageNewBnfGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageNewBnfGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageNewBnfGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageNewBnfGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageNewBnfGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="EdorNo">
        <input type="hidden" name="PolNo">
        <input type="hidden" name="InsuredNo">
        <input type="hidden" name="AppObj">
        <!-- 提交数据操作按钮 -->
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" 保 存 " onClick="saveEdorTypeBC()">
            <input type="button" class="cssButton" value=" 重 置 " onClick="resetNewBnfGrid()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <input type="button" class="cssButton" value="记事本查看" onClick="showNotePad()">
        </div>
    </form>
	<br /><br /><br /><br />
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
