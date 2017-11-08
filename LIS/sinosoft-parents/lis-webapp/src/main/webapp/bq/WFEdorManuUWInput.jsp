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
 * @date     : 2005-12-12
 * @direction: 保全人工核保主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ include file="../common/jsp/AccessCheck.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String sOperator = tGlobalInput.Operator;
    String sManageCom = tGlobalInput.ManageCom;
    String sComCode = tGlobalInput.ComCode;
    tGlobalInput = null;
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保全人工核保</title>
    <!-- 获取登陆信息 -->
    <script language="JavaScript">
        var operator = "<%=sOperator%>";
        var manageCom = "<%=sManageCom%>";
        var comcode = "<%=sComCode%>";
        var curDay = "<%=PubFun.getCurrentDate()%>"; 
    </script>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本  -->
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="WFEdorManuUW.js"></script>
    <%@ include file="WFEdorManuUWInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" action="WFEdorManuUWSave.jsp" method="post" target="fraSubmit">
    <div id="ManuUWInputPool"></div>
        <!-- ======================================================================= -->
        <!-- 共享工作队列查询 -->
    <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllSearch)"></td>
                <td class="titleImg">请输入查询条件</td>
            </tr>
        </table>
        <!-- 共享队列查询录入 -->
       <!--  <div id="divEdorAllSearch" style="display:''">
            <table class="common" >
                <tr class="common">
                    <td class="title">保单号</td>
                    <td class="input"><input type="text" class="common" name="OtherNo"></td>
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="common" name="EdorAcceptNo"></td>
                	<td class="title">保全申请人</td>
                    <td class="input"><input type="text" class="common" name="EdorAppName" ></td>
                </tr>
                <tr class="common">
                	<td class="title">业务类别</td>
                    <td class="input"><Input class= "codeno" CodeData="" name=EdorType  ondblclick="showCodeListEx('edorcode', [this,EdorTypeName], [0,1]);" onkeyup="showCodeListKeyEx('edorcode', [this,EdorTypeName], [0,1]);"><input class=codename name=EdorTypeName ></TD>
                    <td class="title">任务状态</td>
                    <td class="input"><input type="text" class="codeno" name="UWState"  value= "1"  verify="核保状态|Code:UWActivityStatus" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName],[0,1],null,' 1 and code<#5#','1')" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName],[0,1],null,' 1 and code<#5#','1')"><input type="text" class="codename" name="UWStateName" value ="未人工核保" ></td>
                    <td class="title">管理机构</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom" verify="管理机构|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName],[0,1])"><input class="codename" name="ManageComName" ></td>
                </tr>
                <tr class="common">
                    <td class="title">投保人</td>
                    <td class="input"><input type="text" class="common" name="AppntName"></td>
                    <td class="title">被保险人</td>
                    <td class="input"><input type="text" class="common" name="InsuredName"></td>
                    <!--
                    <td class="title">申请方式</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" ondblclick="showCodeList('EdorAppType',[this, AppTypeName],[0,1])" onkeyup="showCodeListKey('EdorAppType', [this,AppTypeName],[0,1])"><input type="text" class="codename" name="AppTypeName" readonly></td>
                    -->
                    
                    <!--  <td class="title">VIP客户</td>
                    <td class="input"><input type="text" class="common" name="VIP"></td>
                </tr>                
                <tr class="common">
                    <td class="title">转核日期</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="theCurrentDate"></td>
                    <td class="title">最后回复日期</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="BackDate"></td>
                	<td class="title">最后回复时间</td>
                    <td class="input"><input type="text" class="common" name="BackTime" value="00:00:00"></td>
                </tr>
                <tr class="common">
                	<td class="title">星级业务员</td>
                    <td class="input"><input type="text" class="common" name="StarAgent" ></td>
                </tr>
            </table>
            <!-- 查询数据操作按钮 -->
            
            <!-- <input type="button" class="cssButton" value=" 查 询 " onclick="easyQueryClickAll()">
            <TD  class= title> 总单量</TD>
      		<TD  class= input> <Input class="readonly" readonly name=PolSum  ></TD>
        </div>
        <!-- 共享队列折叠展开 -->
        <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllList)"></td>
                <td class="titleImg">共享工作池</td>
           </tr>
        </table>
        <!-- 共享队列结果展现 -->
        <!-- <div id="divEdorAllList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanAllGrid"></span></td>
                </tr>
            </table>
            <!-- 共享队列结果翻页 -->
           <!--  <div id="divTurnPage1" align="center" style="display:''">
                <input type="button" class="cssButton" value="首  页" onclick="turnPage1.firstPage();HighlightAllRow();">
                <input type="button" class="cssButton" value="上一页" onclick="turnPage1.previousPage();HighlightAllRow();">
                <input type="button" class="cssButton" value="下一页" onclick="turnPage1.nextPage();HighlightAllRow();">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPage1.lastPage();HighlightAllRow();">
            </div>
        </div>
        <br>
        <!-- 申请数据操作按钮
        <input type="button" class="cssButton" value=" 申 请 " onclick="applyMission()">  -->
        <!-- <br>
        <!-- ======================================================================= -->
        <!-- 我的任务队列查询 -->
        <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfSearch)"></td>
                <td class="titleImg">请输入查询条件</td>
            </tr>
        </table>
        <!-- 我的任务查询录入 -->
        <!-- <div id="divEdorSelfSearch" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">保单号</td>
                    <td class="input"><input type="text" class="common" name="OtherNo2"></td>
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="common" name="EdorAcceptNo2"></td>
                	<td class="title">保全申请人</td>
                    <td class="input"><input type="text" class="common" name="EdorAppName2" ></td>
                </tr>
                <tr class="common">
                	<td class="title">业务类别</td>
                   <td class="input"><Input class= "codeno" CodeData="" name=EdorType2  ondblclick="showCodeListEx('edorcode', [this,EdorTypeName2], [0,1]);" onkeyup="showCodeListKeyEx('edorcode', [this,EdorTypeName2], [0,1]);"><input class=codename name=EdorTypeName2 ></TD>
                    <td class="title">任务状态</td>
                    <td class="input"><input type="text" class="codeno" name="UWState2" value ="1"  verify="核保状态|Code:UWActivityStatus" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName2],[0,1],null,' 1 and code<#5#','1')" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName2],[0,1],null,' 1 and code<#5#','1')"><input type="text" class="codename" name="UWStateName2" value ="未人工核保" ></td>
                    <td class="title">管理机构</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom2" verify="管理机构|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName2],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName2],[0,1])"><input class="codename" name="ManageComName2" ></td>
                </tr>
                <tr class="common">
                    <td class="title">投保人</td>
                    <td class="input"><input type="text" class="common" name="AppntName2"></td>
                    <td class="title">被保险人</td>
                    <td class="input"><input type="text" class="common" name="InsuredName2"></td>
                    <!--
                    <td class="title">申请方式</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" ondblclick="showCodeList('EdorAppType',[this, AppTypeName],[0,1])" onkeyup="showCodeListKey('EdorAppType', [this,AppTypeName],[0,1])"><input type="text" class="codename" name="AppTypeName" readonly></td>
                    -->
                    
                    <!--<td class="title">VIP客户</td>
                    <td class="input"><input type="text" class="common" name="VIP2"></td>
                </tr>                
                <tr class="common">
                    <td class="title">转核日期</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="theCurrentDate2"></td>
                    <td class="title">最后回复日期</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="BackDate2"></td>
                	<td class="title">最后回复时间</td>
                    <td class="input"><input type="text" class="common" name="BackTime2" value="00:00:00"></td>
                </tr>
                <tr class="common">
                	<td class="title">星级业务员</td>
                    <td class="input"><input type="text" class="common" name="StarAgent2" ></td>
                </tr>
            </table>
            <!-- 查询数据操作按钮 -->
           <!--  <input type="button" class="cssButton" value=" 查 询 " onclick="easyQueryClickSelf()">
        </div>
        <!-- 我的任务折叠展开 -->
        <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfList)"></td>
                <td class="titleImg">我的任务</td>
           </tr>
        </table>
        <!-- 我的任务结果展现 -->
         <!--<div id="divEdorSelfList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanSelfGrid"></span></td>
                </tr>
            </table>
            <!-- 我的任务结果翻页 -->
            <!-- <div id="divTurnPage2" align="center" style="display:''">
                <input type="button" class="cssButton" value="首  页" onclick="turnPage2.firstPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="上一页" onclick="turnPage2.previousPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="下一页" onclick="turnPage2.nextPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPage2.lastPage();HighlightSelfRow();">
            </div>
        </div>
        <br> -->
        <!-- ======================================================================= -->
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" value="3" name="OtherNoType" >
        <input type="hidden" name="Operator" value="<%=sOperator%>">
        <input type="hidden" name="MissionID">
        <input type="hidden" name="SubMissionID">
        <input type="hidden" name="ActivityID" >
        <input type="hidden" name="fmtransact">
        <input type="hidden" name="customerNo"> 
        <!-- 提交数据操作按钮 -->
       <!-- <div id="hidden" align="center" style="display:'none'">
        <table>
        <input type="button" class="cssButton" value=" 人工核保 " onclick="GoToBusiDeal()">
        <input type="button" class="cssButton" value=" 保全明细查询 " onclick="EdorDetailQuery()">
        <input type="button" class="cssButton" name="NotePadButton" value=" 记事本查看 " onclick="showNotePad()">
        </table>
        </div>--> 
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
