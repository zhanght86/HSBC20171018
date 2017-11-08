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
 * @version  : 1.00, 1.01
 * @date     : 2005-12-03, 2006-02-15
 * @direction: 保单特殊复效查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保单特殊复效</title>
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
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="LRNSpecialAvailableQueryInput.js"></script>
    <%@ include file="LRNSpecialAvailableQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="_blank">
        <!-- 特复查询折叠展开 -->
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
                </td>
                <td class="titleImg">请输入查询条件</td>
            </tr>
        </table>
        <div class="maxbox1">
        <div  id= "divFCDay" style= "display: ''"> 
        <table class="common">
            <tr class="common">
                <td class="title5">合同号码</td>
                <td class="input5"><input type="text" class="common wid" name="ContNo" id="ContNo" maxlength="24"></td>
                <td class="title5">失效原因</td>
                <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  type="text" class="codeno" name="InvalidReason" id="InvalidReason" verify="失效原因|Code:contavailablereason" onclick="return showCodeList('contavailablereason',[this,InvalidReasonName],[0,1],null,null,null,0,450)" ondblclick="return showCodeList('contavailablereason',[this,InvalidReasonName],[0,1],null,null,null,0,450)" onkeyup="clearEmptyCode(this,InvalidReasonName); return showCodeListKey('contavailablereason',[this,InvalidReasonName],[0,1],null,null,null,0,450);"><input type="text" class="codename" name="InvalidReasonName" id="InvalidReasonName" readonly></td>
            </tr>
            <tr class="common">
                <td class="title5">统计起期</td>
                <td class="input5"><Input  type="text" class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|Date" dateFormat="short" name=StartDate id="StartDate" onblur="formatDate(this)"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title5">操 作 员</td>
                <td class="input5"><input type="text" class="common wid" name="Operator" id="Operator" maxlength="40"></td>
            </tr>
            <tr class="common">
                <td class="title5">管理机构</td>
                <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  type="text" class="codeno" name="ManageCom" id="ManageCom" verify="管理机构|Code:ComCode" onclick="return showCodeList('ComCode',[this,ManageComName],[0,1],null,null,null,0,450)" ondblclick="return showCodeList('ComCode',[this,ManageComName],[0,1],null,null,null,0,450)" onkeyup="clearEmptyCode(this,ManageComName); return showCodeListKey('ComCode',[this,ManageComName],[0,1],null,null,null,0,450);"><input type="text" class="codename" name="ManageComName" id="ManageComName" readonly></td>
                <td class="title5">统计止期</td>
                <td class="input5"><Input type="text" class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|Date" dateFormat="short" name=EndDate id="EndDate" onblur="formatDate(this)"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
            </tr>
        </table>
    </div>
    </div>
        <!-- 提交数据操作按钮 -->
        <a href="javascript:void(0)" class=button onclick="queryRevalidateTrack();">查  询</a>
        <a href="javascript:void(0)" class=button onclick="printRevalidateTrack();">打  印</a>
        <!-- <input type="button" class="cssButton" value=" 查 询 " onclick="queryRevalidateTrack()">
        <input type="button" class="cssButton" value=" 打 印 " onclick="printRevalidateTrack()"> -->
        <!-- 复效查询折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divQueryList)"></td>
                <td class="titleImg">复效合同信息</td>
           </tr>
        </table>
        <!-- 复效查询结果展现 -->
        <div id="divQueryList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanQueryGrid"></span></td>
                </tr>
            </table>
            <!-- 复效查询结果翻页 -->
            <div align="center" style= "display:none">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPage.lastPage()">
            </div>
        </div>
    </form>
    <br>
    <br>
    <br>
    <br>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
