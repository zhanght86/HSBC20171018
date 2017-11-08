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
 * @version  : 1.00, 1.01
 * @date     : 2006-12-06, 2006-12-18
 * @direction: 影像迁移主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>影像迁移</title>
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
    <script language="JavaScript" src="ImgMove.js"></script>
    <%@ include file="ImgMoveInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 查询条件折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divQueryInput)"></td>
                <td class="titleImg">输入迁移条件</td>
           </tr>
        </table>
        <div id="divQueryInput" class=maxbox1 style="display:''">
            <!-- 查询条件录入表格 -->
            <table class="common">
                <tr class="common">
                    <td class="title">迁出机构</td>
                    <td class="input"><input type="text" class="codeno" name="OldManageCom" id=OldManageCom verify="迁出机构|NotNull" readonly><input type="text" class="codename" name="OldManageComName" readonly></td>
                    <!-- 以后如果需要扩展到机构对机构传送,则换成下面2个注释掉的就可以 -->
                    <!-- <td class="input"><input type = "text" class = "codeno" name = "OldManageCom" ondblclick="return showCodeList('station',[this,OldManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,OldManageComName],[0,1]);"><input class=codename name=OldManageComName readonly></TD> -->
                    <td class="title">迁入机构</td>
                    <td class="input"><input type="text" class="codeno" name="NewManageCom" verify="迁入机构|NotNull" readonly><input type="text" class="codename" name="NewManageComName" readonly></td>
                    <!-- <td class="input"><input type = "text" class = "codeno" name = "NewManageCom" ondblclick="return showCodeList('station',[this,NewManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,NewManageComName],[0,1]);"><input class=codename name=NewManageComName readonly></TD> -->
                </tr>
                <tr class="common">
                    <td class="title">扫描起期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="StartDate" dateFormat="short" verify="扫描起期|Date" onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                    <td class="title">扫描止期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="EndDate" dateFormat="short" verify="扫描止期|Date" onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                </tr>
            </table>
        </div>
        <br>
        <!-- 提交数据操作按钮 -->
        <input type="button" class="cssButton" value=" 迁 移 " onClick="moveImage()">
        <input type="button" class="cssButton" value=" 重 置 " onClick="resetForm()">
        <br>
        <br>
        <table>
            <tr>
                <td class="common"></td>
                <td class="common" style="color:red">为保证传送成功,防止因生成MD5后对影像目录的增删改操作造成MD5与文件不一致,建议在迁移前通知分公司生成最新MD5.</td>
           </tr>
        </table>        
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="LoginManageCom">
        <input type="hidden" name="LoginOperator">
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
