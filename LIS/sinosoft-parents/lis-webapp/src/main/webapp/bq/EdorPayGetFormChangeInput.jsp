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
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2006-03-16, 2006-05-16, 2006-06-28, 2006-10-24, 2006-11-08
 * @direction: 保全收付费方式变更主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保全收付费方式变更</title>
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
    <script language="JavaScript" src="EdorPayGetFormChange.js"></script>
    <%@ include file="EdorPayGetFormChangeInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- 查询条件折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSearchLayer)"></td>
                <td class="titleImg">请输入查询条件</td>
            </tr>
        </table>
        <!-- 查询条件录入表格 -->
        <div id="divSearchLayer" style="display:''">
        <div class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="title5">保全受理号</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="EdorAcceptNo_srh" id="EdorAcceptNo_srh" onkeypress="queryOnKeyPress()"></td>
                    <td class="title5">领取通知书号</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="GetNoticeNo_srh" id="GetNoticeNo_srh" onkeypress="queryOnKeyPress()"></td>
                </tr>
                <tr class="common">
                    <td class="title5">领取人</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="Drawer_srh" id="Drawer_srh" onkeypress="queryOnKeyPress()"></td>
                    <td class="title5">领取人证件号</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="DrawerIDNo_srh" id="DrawerIDNo_srh" onkeypress="queryOnKeyPress()"></td>
                </tr>
            </table>
            <!-- 查询数据操作按钮 -->
            
            </div>
        </div>
			<!--<input type="button" class="cssButton" value=" 查 询 " onclick="queryEdorInfoGrid()">-->
            <a href="javascript:void(0);" class="button" onClick="queryEdorInfoGrid();">查    询</a>

        <!-- 保全信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorInfoList)"></td>
                <td class="titleImg">保全批改信息</td>
           </tr>
        </table>
        <!-- 保全信息结果展现 -->
        <div id="divEdorInfoList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorInfoGrid"></span></td>
                </tr>
            </table></div></div>
            <!-- 保全信息结果翻页 -->
            <div id="divTurnPageEdorInfoGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageEdorInfoGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageEdorInfoGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageEdorInfoGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageEdorInfoGrid.lastPage()">
            
        </div>

        <!-- 方式变更录入表格 -->
        
        	        <!-- 方式变更折叠展开 -->
        		<table>
        		    <tr>
        		        <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPayGetFormInput)"></td>
        		        <td class="titleImg">收付费方式变更</td>
        		    </tr>
        		</table>
                <div id="divPayGetFormInput" style="display:''">
                <div class="maxbox">
            <table class="common">
                <tr class="common">
                    <!--
                    <td class="title">变更类型</td>
                    <td class="input"><input type="text" class="codeno" name="ChangeType" verify="变更类型|NotNull" CodeData="0|^1|收费^2|付费" ondblClick="showCodeListEx('nothis',[this,ChangeTypeName],[0,1])" onkeyup="showCodeListKeyEx('nothis',[this,ChangeTypeName],[0,1])"><input type="text" class="codename" name="ChangeTypeName" readonly></td>
                    -->
                    <td class="title5">新的方式</td>
                    <td class="input5"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="FormType" id="FormType" verify="收付费方式|NotNull&Code:EdorGetPayForm"  ondblclick="showCodeList('EdorGetPayForm',[this,FormTypeName],[0,1])" onMouseDown="showCodeList('EdorGetPayForm',[this,FormTypeName],[0,1])" onkeyup="showCodeListKey('EdorGetPayForm',[this,FormTypeName],[0,1]);"><input type="text" class="codename" name="FormTypeName" id="FormTypeName" readonly></td>
 <td class="title5">开户银行</td>
                    <td class="input5"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="BankCode" id="BankCode" disabled verify="开户银行|Code:Bank" ondblclick="showCodeList('Bank',[this,BankCodeName],[0,1])" onMouseDown="showCodeList('Bank',[this,BankCodeName],[0,1])" onkeyup="showCodeListKey('Bank',[this,BankCodeName],[0,1])"><input type="text" class="CodeName" name="BankCodeName" id="BankCodeName" readonly disabled></td>
                </tr>
                <tr class="common" id="divBankInfo" style="display:''">
                   
                    <td class="title5">银行帐号</td>
                    <td class="input5"><input type="text" class="wid" class="coolConfirmBox" name="BankAccNo" id="BankAccNo" disabled></td>
                    <td class="title5">帐户名</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="AccName" id="AccName" disabled></td>
                </tr>
                <tr class="common">
                    <td class="title5">补退费领取人</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="PayGetName" id="PayGetName"></td>
                    <td class="title5">领取人身份证</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="PersonID" id="PersonID"></td>
                </tr>
            </table>
        </div>
        </div>
        
        <!--<input type="button" class="cssButton" value=" 修 改 " onClick="savePayGetForm()">-->
        <a href="javascript:void(0);" class="button" onClick="savePayGetForm();">修    改</a>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="EdorAcceptNo" id="EdorAcceptNo">
        <input type="hidden" name="GetNoticeNo" id="GetNoticeNo">
        <input type="hidden" name="ActuGetNo" id="ActuGetNo">
        <input type="hidden" name="ChangeType" id="ChangeType">
        <input type="hidden" name="PrtSeq" id="PrtSeq">
        <input type="hidden" name="CurrentManageCom" id="CurrentManageCom">
        <!-- 提交数据操作按钮 -->
        
       
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
    <br><br><br><br>
</body>
</html>
