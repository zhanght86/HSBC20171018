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
 * @date     : 2006-07-05, 2006-12-05
 * @direction: 团单保全人工核保分单层主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ include file="../common/jsp/AccessCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>团体保全个人核保</title>
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
    <script language="JavaScript" src="GEdorManuUWInsured.js"></script>
    <%@ include file="GEdorManuUWInsuredInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 个单查询折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divPEdorSearchInput)"></td>
                <td class="titleImg">被保人查询</td>
            </tr>
        </table>
        <!-- 个人保单查询录入 -->
        <div id="divPEdorSearchInput" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">分单号</td>
                    <td class="input"><input type="text" class="common wid" name="ContNo_Srh" id=ContNo_Srh></td>
                    <td class="title">被保人号</td>
                    <td class="input"><input type="text" class="common wid" name="InsuredNo_Srh" id=ContNo_Srh></td>
                    <td class="title">员工序号</td>
                    <td class="input"><input type="text" class="common wid" name="CustomerSeqNo_Srh" id=ContNo_Srh></td>
                </tr>
                    <td class="title">被保人姓名</td>
                    <td class="input"><input type="text" class="common wid" name="InsuredName_Srh" id=ContNo_Srh></td>
                    <td class="title">性别</td>
                    <td class="input"><input type="text" class="codeno" name="Sex_Srh" id=Sex_Srh style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('Sex',[this,SexName_Srh],[0,1])" onkeyup="showCodeListKey('Sex',[this,SexName_Srh],[0,1])"><input type="text" class="codename" name="SexName_Srh" id=SexName_Srh readonly></td>
                    <td class="title">出生日期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="Birthday_Srh" onClick="laydate({elem: '#Birthday_Srh'});" id="Birthday_Srh"><span class="icon"><a onClick="laydate({elem: '#Birthday_Srh'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>
                </tr>
                <tr class="common">
                    <td class="title">证件类型</td>
                    <td class="input"><input class="codeno" name="IDType_Srh" id=IDType_Srh style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('IDType',[this,IDTypeName_Srh],[0,1])" onkeyup="showCodeListKey('IDType',[this,IDTypeName_Srh],[0,1])"><input class="codename" name="IDTypeName_Srh" id=IDTypeName_Srh readonly></td>
                    <td class="title">证件号码</td>
                    <td class="input"><input type="text" class="common wid" name="IDNo_Srh" id=IDNo_Srh></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
            <!-- 查询数据操作按钮 -->
            <input type="button" class="cssButton" value=" 查 询 " onclick="queryInsuredGrid()">
        </div>
        <!-- 个单查询折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divInsuredList)"></td>
                <td class="titleImg">被保人查询结果</td>
           </tr>
        </table>
        <!-- 个单查询结果展现 -->
        <div id="divInsuredList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanInsuredGrid"></span></td>
                </tr>
            </table>
            <!-- 个单查询结果翻页 -->
            <div id="divTurnPageInsuredGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageInsuredGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageInsuredGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageInsuredGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageInsuredGrid.lastPage()">
            </div>
        </div>
        <!-- 个单信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divContInfo)"></td>
                <td class="titleImg">被保人信息</td>
           </tr>
        </table>
        <!-- 个单信息列结果展现 -->
        <div id="divContInfo" style="display:''">

                <div id="divContInfotr" style="display:'none'"> <!-- 这两行信息多余,暂时隐藏掉 -->
                    <table class="common">
                <tr class="common">
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                    <td class="title">团体保单号</td>
                    <td class="input"><input type="text" class="readonly wid" name="GrpContNo" id=EdorAcceptNo readonly></td>
                    <td class="title">个人保单号</td>
                    <td class="input"><input type="text" class="readonly wid" name="ContNo" id=EdorAcceptNo readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">团体客户号</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntNo" id=AppntNo readonly></td>
                    <td class="title">投保单位</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" id=AppntNo readonly></td>
                    <td class="title">投保人性别</td>
                    <td class="input"><input type="text" class="codeno" name="AppntSex" id=AppntSex readonly><input type="text" class="codename" name="AppntSexName" id=AppntSexName readonly></td>
                </tr>
               </table>
              </div>
               <table class="common">
                <tr class="common">
                    <td class="title">被保人客户号</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredNo" id=InsuredNo readonly></td>
                    <td class="title">被保人姓名</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" id=InsuredName readonly></td>
                    <td class="title">被保人性别</td>
                    <td class="input"><input type="text" class="codeno" name="InsuredSex" id=InsuredSex readonly><input type="text" class="codename" name="InsuredSexName" id=InsuredSexName readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">出生日期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="InsuredBirthday" id=InsuredBirthday readonly onClick="laydate({elem: '#InsuredBirthday'});" id="InsuredBirthday"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>
                    <td class="title">证件类型</td>
                    <td class="input"><input type="text" class="codeno" name="InsuredIDType" id=InsuredIDType readonly><input type="text" class="codename" name="InsuredIDTypeName" id=InsuredIDTypeName readonly></td>
                    <td class="title">证件号码</td>
                    <td class="input"><input type="text" class="readonly" name="InsuredIDNo" readonly></td>
                </tr>
            </table>
        </div>
        <!-- 被保人信息查询按钮集合 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divInsuerdInfo)"></td>
                <td class="titleImg">被保人附属信息查询</td>
           </tr>
        </table>
        <div id="divInsuerdInfo" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="input">
                          <input type="button" class="cssButton" value="  个人保单明细信息  " onclick="showPolDetail()">
                        <input type="button" class="cssButton" value="   被保人健康告知   " onclick="queryHealthApprize()">
                        <input type="button" class="cssButton" value="   被保人体检资料   " onclick="queryBodyInspect()">
                        <input type="button" class="cssButton" value="   被保人保额累计   " onclick="queryAccumulateAmnt()">
                        <br><br>
                        <input type="button" class="cssButton" value="  被保人已承保保单  " onclick="queryAlreadyProposal()">
                        <input type="button" class="cssButton" value="  被保人未承保保单  " onclick="queryUnProposalCont()">
                        <input type="button" class="cssButton" value="   被保人既往保全   " onclick="queryLastEdorTrack()">
                        <input type="button" class="cssButton" value="   被保人既往理赔   " onclick="queryLastClaimTrack()">
                    </td>
                </tr>
            </table>
        </div>
        <!-- 险种信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divPolList)"></td>
                <td class="titleImg">个人保单险种信息</td>
           </tr>
        </table>
        <!-- 险种信息结果展现 -->
        <div id="divPolList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanPolGrid"></span></td>
                </tr>
            </table>
            <!-- 险种信息结果翻页 -->
            <div id="divTurnPagePolGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPagePolGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPagePolGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPagePolGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPagePolGrid.lastPage()">
            </div>
        </div>
        <!-- 核保操作折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divEdorOperations)"></td>
                <td class="titleImg">保全核保操作</td>
           </tr>
        </table>
        <!-- 核保操作按钮集合 -->
        <div id="divEdorOperations" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="input">
                        <!--
                        <input type="button" class="cssButton" value="  被保人健康告知查询  " onclick="queryHealthApprize()">
                        <input type="button" class="cssButton" value="  被保人体检资料查询  " onclick="queryBodyInspect()">
                        <input type="button" class="cssButton" value="  被保人保额累计查询  " onclick="queryAccumulateAmnt()">
                        <br><br>
                        <input type="button" class="cssButton" value=" 被保人已承保保单查询 " onclick="queryAlreadyProposal()">
                        <input type="button" class="cssButton" value="被保人未承保投保单查询" onclick="queryUnProposalCont()">
                        <input type="button" class="cssButton" value="被保人既往保全信息查询" onclick="queryLastEdorTrack()">
                        <input type="button" class="cssButton" value="被保人既往理赔信息查询" onclick="queryLastClaimTrack()">
                        <br><br>
                        <input type="button" class="cssButton" value="   个人单加费录入   " onclick="showAddinPremInput()">
                        <input type="button" class="cssButton" value="   个人单特约录入   " onclick="showSpecStipulation()">
                        <br><br>
                        <input type="button" class="cssButton" value=" 批量核保通过所有分单 " onclick="batchUWPassAll()">
                        -->
                        <input type="button" class="cssButton" value="       体检录入     " onclick="showHealth()">
                        <input type="button" class="cssButton" value="       生调录入     " onclick="showRReport()">
                        <input type="button" class="cssButton" value="       加费录入     " onclick="showAdd()">
                        <input type="button" class="cssButton" value="       特约录入     " onclick="showSpec()">
                    </td>
                </tr>
            </table>
        </div>
        <!-- 个单核保折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divContUWInput)"></td>
                <td class="titleImg">个人险种核保结论</td>
            </tr>
        </table>
        <!-- 个单核保结论录入 -->
        <div id="divContUWInput" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">核保结论</td>
                    <td class="input"><input type="text" class="codeno" name="UWState" id=UWState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="团单分单核保结论|NotNull&Code:gedorpoluw" ondblClick="showCodeList('gedorpoluw',[this,UWStateName],[0,1],null,null,null,0,207)" onkeyup="showCodeListKey('gedorpoluw',[this,UWStateName],[0,1],null,null,null,0,207)"><input type="text" class="codename" name="UWStateName" id=UWStateName readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="title">核保意见</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="input" colspan="6"><textarea class="common wid" name="UWIdea" id=UWIdea cols="108" rows="3" verify="团单分单核保意见|Len<255"></textarea></td>
                </tr>
            </table>
            <!-- 提交数据操作按钮 -->
            <input type="button" class="cssButton" value=" 确 定 " onclick="saveContUW()">
            <input type="button" class="cssButton" value=" 重 置 " onclick="resetContUW()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <br><br>
        </div>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="MissionID" id=MissionID>
        <input type="hidden" name="SubMissionID" id=SubMissionID>
        <input type="hidden" name="ActivityID" id=ActivityID>
        <input type="hidden" name="ActivityStatus" id=ActivityStatus>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="PrtNo" id=PrtNo >
        <input type="hidden" name="ProposalContNo" id=ProposalContNo>
        <input type="hidden" name="PolNo" id=PolNo>
        <input type="hidden" name="PayToDate" id=PayToDate>
        <input type="hidden" name="UWType" id=UWType>
        <input type="hidden" name="ActionFlag" id=ActionFlag>
        <input type="hidden" name="LoginOperator" id=LoginOperator>
        <input type="hidden" name="LoginManageCom" id=LoginManageCom>
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	
<br/><br/><br/><br/>
</body>
</html>
