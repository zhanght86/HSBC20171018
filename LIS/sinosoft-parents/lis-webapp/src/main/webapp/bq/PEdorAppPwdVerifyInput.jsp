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
 * @date     : 2006-11-06
 * @direction: ��ȫ�������������ĿУ�鱣�����������
 * @comment  : ������� :)
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��������У��</title>
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
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorAppPwdVerify.js"></script>
    <%@ include file="PEdorAppPwdVerifyInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorOtherInfo)"></td>
                <td class="titleImg">��ȫ������Ϣ</td>
            </tr>
        </table>
        <!-- ������Ϣչ�ֱ�� -->
        <div id="divEdorOtherInfo" style="display:''" class=maxbox1>
            <table class="common">
                <tr class="common">
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                    <td class="title">�������</td>
                    <td class="input"><input type="text" class="readonly wid" name="OtherNo" id=OtherNo readonly></td>
                    <td class="title">��������</td>
                    <td class="input"><input class="codeno" name="OtherNoType" readonly><input class="codename" name="OtherNoTypeName" id=OtherNoTypeName readonly></td>
                </tr>              
                <tr class="common">
                    <td class="title">������������</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</td>
                    <td class="title">���뷽ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" id=AppType readonly><input type="text" class="codename" name="AppTypeName" id=AppTypeName readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </div>
        <!-- ���������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divContPwdGrid)"></td>
                <td class="titleImg">���뱣������</td>
            </tr>
        </table>
        <!-- ��������չ�ֱ�� -->
        <div id="divContPwdGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanContPwdGrid"></span></td>
                </tr>
            </table>
            <!-- ������������ҳ -->
            <div id="divTurnPageContPwdGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageContPwdGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageContPwdGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageContPwdGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageContPwdGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- �ύ���ݲ�����ť -->
        <input type="button" class="cssButton" value=" ȷ �� " onclick="verifyContPwd()">
        <input type="button" class="cssButton" value=" ȡ �� " onclick="verifyPwdFail()">
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	<br/><br/><br/><br/>
</body>
</html>
