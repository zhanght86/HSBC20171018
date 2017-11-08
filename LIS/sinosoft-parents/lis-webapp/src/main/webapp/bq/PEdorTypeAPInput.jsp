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
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-11-13, 2006-03-11, 2006-08-25
 * @direction: 保全保费自垫申请、终止主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>宽末处理方式变更</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeAP.jsp";
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
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdorTypeAP.js"></script>
    <%@ include file="PEdorTypeAPInit.jsp" %>
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
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</td>
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
                <td class="titleImg">保单信息</td>
            </tr>
        </table>
        <!-- 保单信息表格 -->
        <div id= "divPolInfo" style= "display:''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanPolGrid"></span></td>
                </tr>
            </table>
            <table class="common">
                <!--tr class="common">
                    <td class="title">险种号</td>
                    <td class="input"><input type="text" class="readonly wid" name="PolNo" readonly></td>
                    <td class="title">险种代码</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" readonly></td>
                    <td class="title">险种名称</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">投保人</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" readonly></td>
                    <td class="title">被保人</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" readonly></td>
                    <td class="title">投保年龄</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredAppAge" readonly></td>
                </tr-->
                <tr class="common">
                    <td class="title">原自垫状态</td>
                    <td class="input"><input type="hidden" name="AutoPayFlag_Old" id=AutoPayFlag_Old><input type="text" class="readonly wid" name="AutoPayFlagName_Old" id=AutoPayFlagName_Old readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
            
        </div>
        <!-- 垫交信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divEdorAPInput)"></td>
                <td class="titleImg">保费自垫变更</td>
            </tr>
        </table>
        <!-- 垫交信息输入表格 -->
        <div id="divEdorAPInput" class=maxbox1 style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">保费自垫标志</td>
                    <td class="input"><input type="text" class="codeno" name="AutoPayFlag" id=AutoPayFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="自垫标志|NotNull&Code:AutoPayType" ondblclick="showCodeList('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)" onkeyup="showCodeListKey('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)"><input type="text" class="codename" name="AutoPayFlagName" id=AutoPayFlagName readonly="true"></td>
                       <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
        </div>
        <br>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="PolNo" id=PolNo>
        <input type="hidden" name="InsuredNo" id=InsuredNo>
        <!-- 提交数据操作按钮 -->
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" 保 存 " onClick="saveEdorTypeAP()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <input type="button" class="cssButton" value="记事本查看" onClick="showNotePad()">
        </div>
    </form>
	<br /><br /><br /><br />
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
