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
 * @date     : 2005-12-19
 * @direction: 年金、满期金给付主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>年金、满期金给付</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeAG.jsp";
        }
    </script>
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
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdorTypeAG.js"></script>
    <%@ include file="PEdorTypeAGInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 保全受理通用保单信息 -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">保全受理号</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">批改类型</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">保单号</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo></td>
            </tr>
            <tr class="common">
                <td class="title">柜面受理日期</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

				</td>
                <td class="title">生效日期</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorValiDate" readonly onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

				</td>
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
        <div id="divPolInfo" style="display:''" class=maxbox1>
            <table class="common">
                <tr class="common">
                    <td class="title">险种代码</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" id=RiskCode readonly></td>
                    <td class="title">险种名称</td>
                    <td class="input" colspan="3"><input type="text" class="readonly3 wid" name="RiskName" id=RiskName readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">生效日期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="CValiDate" readonly onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</td>
                    <td class="title">交至日期</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="PayToDate" readonly onClick="laydate({elem: '#PayToDate'});" id="PayToDate"><span class="icon"><a onClick="laydate({elem: '#PayToDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</td>
                    <td class="title">总保费</td>
                    <td class="input"><input type="text" class="readonly wid" name="Prem" id=Prem readonly></td>
                </tr>
            </table>
        </div>
        <!-- 客户信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divCustomerInfo)"></td>
                <td class="titleImg">客户基本信息</td>
            </tr>
        </table>
        <!-- 客户信息展现表格 -->
        <div id="divCustomerInfo" style="display:''" class=maxbox1>
            <div id="divAppntInfo" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td class="title">投保人</td>
                        <td class="input"><input type="text" class="readonly wid" name="AppntName" id=AppntName readonly></td>
                        <td class="title">证件类型</td>
                        <td class="input"><input type="text" class="codeno" name="AppntIDType" id=AppntIDType readonly><input type="text" class="codename" name="AppntIDTypeName" id=AppntIDTypeName readonly></td>
                        <td class="title">证件号码</td>
                        <td class="input"><input type="text" class="readonly wid" name="AppntIDNo" id=AppntIDNo readonly></td>
                    </tr>
                </table>
            </div>
            <table class="common">
                <tr class="common">
                    <td class="title">被保人</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" id=InsuredName readonly></td>
                    <td class="title">证件类型</td>
                    <td class="input"><input type="text" class="codeno" name="InsuredIDType" id=InsuredName readonly><input type="text" class="codename" name="InsuredIDTypeName" id=InsuredIDTypeName readonly></td>
                    <td class="title">证件号码</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredIDNo" id=InsuredName readonly></td>
                </tr>
            </table>
        </div>
        <!-- 年金信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divAGInfo)"></td>
                <td class="titleImg">年金、期满金</td>
            </tr>
        </table>
        <!-- 年金信息输入表格 -->
        <div id="divAGInfo" style="display:''"  class=maxbox1>
            <table class="common">
                <tr class="common">
                    <td class="title">应领金额</td>
                    <td class="input"><input type="text" class="readonly wid" name="MoneyAdd" id=MoneyAdd readonly></td>
                    <td class="title">续期领取形式</td>
                    <td class="input"><input type="text" class="codeno" name="GoonGetMethod" id=GoonGetMethod verify="续期领取形式|Code:GetLocation" ondblclick="showCodeList('GetLocation',[this,GoonGetMethodName],[0,1])" onkeyup="showCodeListKey('GetLocation',[this,GoonGetMethodName],[0,1])"><input type="text" class="codename" name="GoonGetMethodName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
            <!-- 银行帐户录入表格 -->
            <div id="BankInfo" style="display:'none'">
                <table class="common">
                    <tr class="common">
                        <td class="title">开户银行</td>
                        <td class="input"><input type="text" class="codeno" name="GetBankCode" id=GetBankCode verify="开户银行|Code:Bank" ondblclick="showCodeList('Bank',[this,BankName],[0,1])" onkeyup="showCodeListKey('Bank',[this,BankName],[0,1])"><input type="text" class="codename" name="BankName" readonly></td>
                        <td class="title">银行帐户</td>
                        <td class="input"><input type="text" class="coolConfirmBox" name="GetBankAccNo" id=GetBankAccNo></td>
                        <td class="title">户    名</td>
                        <td class="input"><input type="text" class="common wid" name="GetAccName" id=GetAccName></td>
                    </tr>
                </table>
            </div>
        </div>
        <!-- 领取项目折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divGetProject)"></td>
                <td class="titleImg">领取项目信息</td>
            </tr>
        </table>
        <!-- 领取项目结果表格 -->
        <div id="divGetProject" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanPolGrid"></span></td>
                </tr>
            </table>
        </div>
        <div id="BonusInfo" style="display:'none'">
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divGetBonusProject)"></td>
                    <td class="titleImg">红利领取项目</td>
                </tr>
            </table>
            <!-- 红利领取项目结果表格 -->
            <div id="divGetBonusProject" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanBonusGrid"></span></td>
                    </tr>
                </table>
            </div>
        </div>
        <br>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="PolNo" id=EdorNo>
        <input type="hidden" name="InsuredNo" id=EdorNo>
        <input type="hidden" name="AppObj" id=EdorNo>
        <!-- 提交数据操作按钮 -->
        <div id="divEdorquery" style="display:''">
            <input type="button" class="cssButton" value=" 保 存 " onClick="saveEdorTypeAG()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <input type="button" class="cssButton" value="记事本查看" onClick="showNotePad()">
        </div>
        <!-- 公用的补退费界面 -->
        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
        <br><br>
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	<br/><br/><br/><br/>
</body>
</html>
