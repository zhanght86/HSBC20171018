<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * @direction: 团单保全人工核保申请任务主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ include file="../common/jsp/AccessCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>团单保全人工核保</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
    <SCRIPT language="JavaScript" src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="WFGrpEdorManuUW.js"></script>
    <%@ include file="WFGrpEdorManuUWInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- 共享工作队列查询 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllSearch)"></td>
                <td class="titleImg">请输入查询条件</td>
            </tr>
        </table>
        <!-- 共享队列查询录入 -->
        <div class="maxbox1">
        <div id="divEdorAllSearch" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title5">保全受理号</td>
                    <td class="input5"><input type="text" class="common wid" name="EdorAcceptNo1" id="EdorAcceptNo1"></td>
                    <td class="title5">号码类型</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="OtherNoType1" id="OtherNoType1" verify="号码类型|Code:EdorNoType" onclick="showCodeList('gedornotype',[this,EdorNoTypeName1],[0,1])" ondblclick="showCodeList('gedornotype',[this,EdorNoTypeName1],[0,1])" onkeyup="showCodeListKey('EdorNoType',[this,EdorNoTypeName1],[0,1])"><input type="text" class="codename" name="EdorNoTypeName1" id="EdorNoTypeName1" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title5">团体保单号</td>
                    <td class="input5"><input type="text" class="common wid" name="OtherNo1" id="OtherNo1"></td>
                    <td class="title5">申请人</td>
                    <td class="input5"><input type="text" class="common wid" name="EdorAppName1" id="EdorAppName1"></td>
                </tr>
                <tr class="common">
                    <!--
                    <td class="title">申请方式</td>
                    <td class="input"><input type="text" class="codeno" name="AppType1" ondblclick="showCodeList('EdorAppType',[this,AppTypeName1],[0,1])" onkeyup="showCodeListKey('EdorAppType',[this,AppTypeName1],[0,1])"><input type="text" class="codename" name="AppTypeName1" readonly></td>
                    -->
                    <td class="title5">核保状态</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="UWState1" id="UWState1" verify="核保状态|Code:UWActivityStatus" onclick="showCodeList('UWActivityStatus',[this,UWStateName1],[0,1])" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName1],[0,1])" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName1],[0,1])"><input type="text" class="codename" name="UWStateName1" id="UWStateName1" readonly>
                    <td class="title5">录入日期</td>
                    <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate1'});" verify="录入日期|DATE" dateFormat="short" name=MakeDate1 id="MakeDate1"><span class="icon"><a onClick="laydate({elem: '#MakeDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                </tr>
                <tr class="common">
                    <td class="title5">管理机构</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="ManageCom1" id="ManageCom1" verify="管理机构|Code:Station" onclick="showCodeList('Station',[this,ManageComName1],[0,1])" ondblclick="showCodeList('Station',[this,ManageComName1],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName1],[0,1])"><input type="text" class="codename" name="ManageComName1" id="ManageComName1" readonly></td>
                    <td class="title5">&nbsp;</td>
                    <td class="input5">&nbsp;</td>
                </tr>
            </table>
            <!-- 查询数据操作按钮 -->
            <!-- <input type="button" class="cssButton" value="查  询" onclick="queryAllGrid()"> -->
        </div>
    </div>
    <a href="javascript:void(0)" class=button onclick="queryAllGrid();">查  询</a>
        <!-- 共享队列折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllList)"></td>
                <td class="titleImg">共享工作池</td>
           </tr>
        </table>
        <!-- 共享队列结果展现 -->
        <div id="divEdorAllList" style="display:''" align=center>
            <table class="common">
                <tr class="common">
                    <td><span id="spanAllGrid"></span></td>
                </tr>
            </table>
            <!-- 共享队列结果翻页 -->
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageAllGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageAllGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageAllGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageAllGrid.lastPage()">
        </div>
        <!-- 申请数据操作按钮 -->
        <a href="javascript:void(0)" class=button onclick="applyTheMission();">申  请</a>
        <!-- <input type="button" class="cssButton" value="申  请" onclick="applyTheMission()"> -->
        <!-- 共享工作队列查询 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfSearch)"></td>
                <td class="titleImg">请输入查询条件</td>
            </tr>
        </table>
        <!-- 我的任务查询录入 -->
        <div class="maxbox">
        <div id="divEdorSelfSearch" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="common wid" name="EdorAcceptNo2" id="EdorAcceptNo2"></td>
                    <td class="title5">号码类型</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="OtherNoType2" id="OtherNoType2" verify="号码类型|Code:EdorNoType" onclick="showCodeList('gedornotype',[this,EdorNoTypeName2],[0,1])" ondblclick="showCodeList('gedornotype',[this,EdorNoTypeName2],[0,1])" onkeyup="showCodeListKey('EdorNoType',[this,EdorNoTypeName2],[0,1])"><input type="text" class="codename" name="EdorNoTypeName2" id="EdorNoTypeName2" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title5">客户/保单号</td>
                    <td class="input5"><input type="text" class="common wid" name="OtherNo2" id="OtherNo2"></td>
                    <td class="title5">申请人</td>
                    <td class="input5"><input type="text" class="common wid" name="EdorAppName2" id="EdorAppName2"></td>
                </tr>
                <tr class="common">
                    <!--
                    <td class="title">申请方式</td>
                    <td class="input"><input type="text" class="codeno" name="AppType2" ondblclick="showCodeList('EdorAppType',[this,AppTypeName],[0,1])" onkeyup="showCodeListKey('EdorAppType',[this,AppTypeName],[0,1])"><input type="text" class="codename" name="AppTypeName2" readonly></td>
                    -->
                    <td class="title5">核保状态</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="UWState2" id="UWState2" verify="核保状态|Code:UWActivityStatus" onclick="showCodeList('UWActivityStatus',[this,UWStateName2],[0,1])" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName2],[0,1])" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName2],[0,1])"><input type="text" class="codename" name="UWStateName2" id="UWStateName2" readonly>
                    <td class="title5">录入日期</td>
                    <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate2'});" verify="录入日期|DATE" dateFormat="short" name=MakeDate2 id="MakeDate2"><span class="icon"><a onClick="laydate({elem: '#MakeDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                </tr>
                <tr class="common">
                    <td class="title5">管理机构</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="ManageCom2" id="ManageCom2" verify="管理机构|Code:Station" onclick="showCodeList('Station',[this,ManageComName2],[0,1])" ondblclick="showCodeList('Station',[this,ManageComName2],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName2],[0,1])"><input type="text" class="codename" name="ManageComName2" id="ManageComName2" readonly></td>
                    <td class="title5">&nbsp;</td>
                    <td class="input5">&nbsp;</td>
                </tr>
            </table>
            <!-- 查询数据操作按钮 -->
            <!-- <input type="button" class="cssButton" value="查  询" onclick="querySelfGrid()"> -->
        </div>
    </div>
    <a href="javascript:void(0)" class=button onclick="querySelfGrid();">查  询</a>
        <!-- 我的任务折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfList)"></td>
                <td class="titleImg">我的任务</td>
           </tr>
        </table>
        <!-- 我的任务结果展现 -->
        <div id="divEdorSelfList" style="display:''" align=center>
            <table class="common">
                <tr class="common">
                    <td><span id="spanSelfGrid"></span></td>
                </tr>
            </table>
            <!-- 我的任务结果翻页 -->
            <div style="display:none">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageSelfGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageSelfGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageSelfGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageSelfGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="MissionID" id="MissionID">
        <input type="hidden" name="SubMissionID" id="SubMissionID">
        <input type="hidden" name="ActivityID" id="ActivityID">
        <input type="hidden" name="ActivityStatus" id="ActivityStatus">
        <input type="hidden" name="LoginOperator"  id="LoginOperator" >
        <input type="hidden" name="LoginManageCom" id="LoginManageCom">
        <!-- 提交数据操作按钮 -->
        <a href="javascript:void(0)" class=button onclick="gotoManuUWDeal();">人工核保</a>
        <a href="javascript:void(0)" class=button onclick="showNotePad();">记事本查看</a>
        <!-- <input type="button" class="cssButton" value="人工核保" onclick="gotoManuUWDeal()"> -->
        <!--
        <input type="button" class="cssButton" value=" 保全明细查询 " onclick="showEdorDetail()">
        -->
        <!-- <input type="button" class="cssButton" value="记事本查看" onclick="showNotePad()"> -->
    </form>
    <br>
    <br>
    <br>
    <br>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
