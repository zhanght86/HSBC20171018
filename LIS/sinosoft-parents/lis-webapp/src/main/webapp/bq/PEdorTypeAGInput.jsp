<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2005-12-19
 * @direction: ������ڽ���������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>������ڽ����</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeAG.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
	<link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű�  -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorTypeAG.js"></script>
    <%@ include file="PEdorTypeAGInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- ��ȫ����ͨ�ñ�����Ϣ -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">��ȫ�����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo></td>
            </tr>
            <tr class="common">
                <td class="title">������������</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

				</td>
                <td class="title">��Ч����</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorValiDate" readonly onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

				</td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divPolInfo)"></td>
                <td class="titleImg">����������Ϣ</td>
            </tr>
        </table>
        <!-- ����������Ϣ��� -->
        <div id="divPolInfo" style="display:''" class=maxbox1>
            <table class="common">
                <tr class="common">
                    <td class="title">���ִ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" id=RiskCode readonly></td>
                    <td class="title">��������</td>
                    <td class="input" colspan="3"><input type="text" class="readonly3 wid" name="RiskName" id=RiskName readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">��Ч����</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="CValiDate" readonly onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="PayToDate" readonly onClick="laydate({elem: '#PayToDate'});" id="PayToDate"><span class="icon"><a onClick="laydate({elem: '#PayToDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</td>
                    <td class="title">�ܱ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="Prem" id=Prem readonly></td>
                </tr>
            </table>
        </div>
        <!-- �ͻ���Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divCustomerInfo)"></td>
                <td class="titleImg">�ͻ�������Ϣ</td>
            </tr>
        </table>
        <!-- �ͻ���Ϣչ�ֱ�� -->
        <div id="divCustomerInfo" style="display:''" class=maxbox1>
            <div id="divAppntInfo" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td class="title">Ͷ����</td>
                        <td class="input"><input type="text" class="readonly wid" name="AppntName" id=AppntName readonly></td>
                        <td class="title">֤������</td>
                        <td class="input"><input type="text" class="codeno" name="AppntIDType" id=AppntIDType readonly><input type="text" class="codename" name="AppntIDTypeName" id=AppntIDTypeName readonly></td>
                        <td class="title">֤������</td>
                        <td class="input"><input type="text" class="readonly wid" name="AppntIDNo" id=AppntIDNo readonly></td>
                    </tr>
                </table>
            </div>
            <table class="common">
                <tr class="common">
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" id=InsuredName readonly></td>
                    <td class="title">֤������</td>
                    <td class="input"><input type="text" class="codeno" name="InsuredIDType" id=InsuredName readonly><input type="text" class="codename" name="InsuredIDTypeName" id=InsuredIDTypeName readonly></td>
                    <td class="title">֤������</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredIDNo" id=InsuredName readonly></td>
                </tr>
            </table>
        </div>
        <!-- �����Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divAGInfo)"></td>
                <td class="titleImg">���������</td>
            </tr>
        </table>
        <!-- �����Ϣ������ -->
        <div id="divAGInfo" style="display:''"  class=maxbox1>
            <table class="common">
                <tr class="common">
                    <td class="title">Ӧ����</td>
                    <td class="input"><input type="text" class="readonly wid" name="MoneyAdd" id=MoneyAdd readonly></td>
                    <td class="title">������ȡ��ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="GoonGetMethod" id=GoonGetMethod verify="������ȡ��ʽ|Code:GetLocation" ondblclick="showCodeList('GetLocation',[this,GoonGetMethodName],[0,1])" onkeyup="showCodeListKey('GetLocation',[this,GoonGetMethodName],[0,1])"><input type="text" class="codename" name="GoonGetMethodName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
            <!-- �����ʻ�¼���� -->
            <div id="BankInfo" style="display:'none'">
                <table class="common">
                    <tr class="common">
                        <td class="title">��������</td>
                        <td class="input"><input type="text" class="codeno" name="GetBankCode" id=GetBankCode verify="��������|Code:Bank" ondblclick="showCodeList('Bank',[this,BankName],[0,1])" onkeyup="showCodeListKey('Bank',[this,BankName],[0,1])"><input type="text" class="codename" name="BankName" readonly></td>
                        <td class="title">�����ʻ�</td>
                        <td class="input"><input type="text" class="coolConfirmBox" name="GetBankAccNo" id=GetBankAccNo></td>
                        <td class="title">��    ��</td>
                        <td class="input"><input type="text" class="common wid" name="GetAccName" id=GetAccName></td>
                    </tr>
                </table>
            </div>
        </div>
        <!-- ��ȡ��Ŀ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divGetProject)"></td>
                <td class="titleImg">��ȡ��Ŀ��Ϣ</td>
            </tr>
        </table>
        <!-- ��ȡ��Ŀ������ -->
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
                    <td class="titleImg">������ȡ��Ŀ</td>
                </tr>
            </table>
            <!-- ������ȡ��Ŀ������ -->
            <div id="divGetBonusProject" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanBonusGrid"></span></td>
                    </tr>
                </table>
            </div>
        </div>
        <br>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="PolNo" id=EdorNo>
        <input type="hidden" name="InsuredNo" id=EdorNo>
        <input type="hidden" name="AppObj" id=EdorNo>
        <!-- �ύ���ݲ�����ť -->
        <div id="divEdorquery" style="display:''">
            <input type="button" class="cssButton" value=" �� �� " onClick="saveEdorTypeAG()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <input type="button" class="cssButton" value="���±��鿴" onClick="showNotePad()">
        </div>
        <!-- ���õĲ��˷ѽ��� -->
        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
        <br><br>
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	<br/><br/><br/><br/>
</body>
</html>
