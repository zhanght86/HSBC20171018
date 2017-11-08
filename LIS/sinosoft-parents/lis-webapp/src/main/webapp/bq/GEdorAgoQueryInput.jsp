<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * @direction: 团体保全人工核保投保单位既往保全查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>投保单位既往保全查询</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本  -->
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
    <script language="JavaScript" src="GEdorAgoQuery.js"></script>
    <%@ include file="GEdorAgoQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- 客户号码姓名显示 -->
        <div class="maxbox1">
        <table class="common">
            <tr class="common">
                <td class="title">团体客户号</td>
                <td class="input"><input type="text" class="readonly wid" name="CustomerNo" id="CustomerNo" readonly></td>
                <td class="title">单位名称</td>
                <td class="input"><input type="text" class="readonly wid" name="GrpName" id="GrpName" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table></div>
            <br>
            <!-- 既往项目折叠展开 -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorItemGrid)"></td>
                    <td class="titleImg">既往保全批改项目信息</td>
                </tr>
            </table>
            <!-- 既往项目结果展现 -->
            <div id="divEdorItemGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanEdorItemGrid"></span></td>
                    </tr>
                </table>
                <!-- 既往项目结果翻页 -->
                <div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="首  页" onclick="turnPageEdorItemGrid.firstPage()">
                    <input type="button" class="cssButton91" value="上一页" onclick="turnPageEdorItemGrid.previousPage()">
                    <input type="button" class="cssButton92" value="下一页" onclick="turnPageEdorItemGrid.nextPage()">
                    <input type="button" class="cssButton93" value="尾  页" onclick="turnPageEdorItemGrid.lastPage()">
                </div>
                <!-- 查询数据操作按钮 -->
                <input type="button" class="cssButton" value="   批单查询   " onclick="EndorseDetail()">
                <input type="button" class="cssButton" value=" 人名清单查询 " onclick="NamesBill()">
                <input type="button" class="cssButton" value=" 扫描影像查询 " onclick="showImage()">
                <br><br>
            </div>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="EdorAcceptNo" id=EdorAcceptNo>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="GrpContNo" id=GrpContNo>
        <input type="hidden" name="EdorValiDate" id=EdorValiDate>
        <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
        <!-- 关闭页面操作按钮 -->
        <!--<input type="button" class="cssButton" value="   返    回   " onclick="returnParent()">-->
        <br>
<a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	
<br/><br/><br/><br/>
</body>
</html>
