<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01
 * @date     : 2006-10-10, 2006-11-22
 * @direction: ��ȫ�����˱�������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�����˱��</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeBC.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorTypeBC.js"></script>
    <%@ include file="PEdorTypeBCInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- ��ȫ����ͨ����Ϣ -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">��ȫ�����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
            </tr>
            <tr class="common">
                <td class="title">������������</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">��Ч����</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorValiDate" readonly onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
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
        <div id= "divPolInfo" class=maxbox1 style= "display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">���ִ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" id=RiskCode readonly></td>
                    <td class="title">��������</td>
                    <td class="input" colspan="3"><input type="text" class="common3" name="RiskName" id=RiskName readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">��Ч����</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="CValiDate" readonly onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="PayToDate" readonly onClick="laydate({elem: '#PayToDate'});" id="PayToDate"><span class="icon"><a onClick="laydate({elem: '#PayToDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                    <td class="title">�ܱ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="Prem" id=Prem readonly></td>
                </tr>
            </table>
        </div>
        <!-- �ͻ���Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divCustomerLayer)"></td>
                <td class="titleImg">�����ͻ���Ϣ</td>
            </tr>
        </table>
        <!-- �ͻ���Ϣչ�ֱ�� -->
        <div id="divCustomerLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanCustomerGrid"></span></td>
                </tr>
            </table>
            <!-- �ͻ���Ϣ�����ҳ -->
            <div id="divTurnPageCustomerGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageCustomerGrid.lastPage()">
            </div>
        </div>
        <!-- ԭ�������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divOldBnfGridLayer)"></td>
                <td class="titleImg">ԭ��������Ϣ</td>
            </tr>
        </table>
        <!-- ԭ������չ�ֱ�� -->
        <div id="divOldBnfGridLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanOldBnfGrid"></span></td>
                </tr>
            </table>
            <!-- ԭ�����˽����ҳ -->
            <div id="divTurnPageOldBnfGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageOldBnfGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageOldBnfGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageOldBnfGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageOldBnfGrid.lastPage()">
            </div>
        </div>
        <!-- ���������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divNewBnfGridLayer)"></td>
                <td class="titleImg">����������Ϣ:<font color=red>���֤��Ϊ���֤,�Ա𡢳������ڲ�����д</font></td>
            </tr>
        </table>
        <!-- ��������¼���� -->
        <div id="divNewBnfGridLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanNewBnfGrid"></span></td>
                </tr>
            </table>
            <!-- �������˽����ҳ -->
            <div id="divTurnPageNewBnfGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageNewBnfGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageNewBnfGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageNewBnfGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageNewBnfGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="EdorNo">
        <input type="hidden" name="PolNo">
        <input type="hidden" name="InsuredNo">
        <input type="hidden" name="AppObj">
        <!-- �ύ���ݲ�����ť -->
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" �� �� " onClick="saveEdorTypeBC()">
            <input type="button" class="cssButton" value=" �� �� " onClick="resetNewBnfGrid()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <input type="button" class="cssButton" value="���±��鿴" onClick="showNotePad()">
        </div>
    </form>
	<br /><br /><br /><br />
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
