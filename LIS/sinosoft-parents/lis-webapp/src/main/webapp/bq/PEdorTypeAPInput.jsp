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
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-11-13, 2006-03-11, 2006-08-25
 * @direction: ��ȫ�����Ե����롢��ֹ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��ĩ����ʽ���</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeAP.jsp";
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
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorTypeAP.js"></script>
    <%@ include file="PEdorTypeAPInit.jsp" %>
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
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</td>
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
                <td class="titleImg">������Ϣ</td>
            </tr>
        </table>
        <!-- ������Ϣ��� -->
        <div id= "divPolInfo" style= "display:''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanPolGrid"></span></td>
                </tr>
            </table>
            <table class="common">
                <!--tr class="common">
                    <td class="title">���ֺ�</td>
                    <td class="input"><input type="text" class="readonly wid" name="PolNo" readonly></td>
                    <td class="title">���ִ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" readonly></td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">Ͷ����</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" readonly></td>
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" readonly></td>
                    <td class="title">Ͷ������</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredAppAge" readonly></td>
                </tr-->
                <tr class="common">
                    <td class="title">ԭ�Ե�״̬</td>
                    <td class="input"><input type="hidden" name="AutoPayFlag_Old" id=AutoPayFlag_Old><input type="text" class="readonly wid" name="AutoPayFlagName_Old" id=AutoPayFlagName_Old readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
            
        </div>
        <!-- �潻��Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divEdorAPInput)"></td>
                <td class="titleImg">�����Ե���</td>
            </tr>
        </table>
        <!-- �潻��Ϣ������ -->
        <div id="divEdorAPInput" class=maxbox1 style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">�����Ե��־</td>
                    <td class="input"><input type="text" class="codeno" name="AutoPayFlag" id=AutoPayFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="�Ե��־|NotNull&Code:AutoPayType" ondblclick="showCodeList('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)" onkeyup="showCodeListKey('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)"><input type="text" class="codename" name="AutoPayFlagName" id=AutoPayFlagName readonly="true"></td>
                       <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
        </div>
        <br>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="PolNo" id=PolNo>
        <input type="hidden" name="InsuredNo" id=InsuredNo>
        <!-- �ύ���ݲ�����ť -->
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" �� �� " onClick="saveEdorTypeAP()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <input type="button" class="cssButton" value="���±��鿴" onClick="showNotePad()">
        </div>
    </form>
	<br /><br /><br /><br />
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
