<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

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
 * @date     : 2006-12-26
 * @direction: Ӱ��Ǩ����־��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>Ӱ��Ǩ����־��ѯ</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <script language="JavaScript" src="../common/Calendar/Calendar.js"></script>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="ImgMoveLogInfo.js"></script>
    <%@ include file="ImgMoveLogInfoInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" method="post" target="fraSubmit">
        <!-- ��ѯ�����۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divQueryInput)"></td>
                <td class="titleImg">�����ѯ����</td>
           </tr>
        </table>
        <div id="divQueryInput" style="display:''">
            <!-- ��ѯ����¼���� -->
            <table class="common">
                <tr class="common">
                    <td class="title">Ǩ������</td>
                    <td class="input">
                    <!-- �Ժ������Ҫ��չ�������Ի�������,�򻻳�����2��ע�͵��ľͿ��� -->
                    <!-- <input type = "text" class = "codeno" name = "OldManageCom" ondblclick="return showCodeList('station',[this,OldManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,OldManageComName],[0,1]);"><input class=codename name=OldManageComName readonly></TD> -->
                    <input type="text" class="codeno" name="OldManageCom"><input type="text" class="codename" name="OldManageComName"></td>
                    <td class="title">Ǩ�����</td>
                    <td class="input">
                    <!-- <input type = "text" class = "codeno" name = "NewManageCom" ondblclick="return showCodeList('station',[this,NewManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,NewManageComName],[0,1]);"><input class=codename name=NewManageComName readonly></TD> -->
                    <input type="text" class="codeno" name="NewManageCom" readonly><input type="text" class="codename" name="NewManageComName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">ɨ������</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="StartDate" dateFormat="short"></td>
                    <td class="title">ɨ��ֹ��</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="EndDate" dateFormat="short"></td>
                </tr>
            </table>
            <!-- �ύ���ݲ�����ť -->
            <input type="button" class="cssButton" value=" �� ѯ " onClick="queryTaskLogGrid()">
        </div>
        <!-- Ǩ��������Ϣչ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divTaskLogGrid)"></td>
                <td class="titleImg">Ǩ��������Ϣ</td>
            </tr>
        </table>
        <!-- ������Ϣչ�ֱ�� -->
        <div id="divTaskLogGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanTaskLogGrid"></span></td>
                </tr>
            </table>
            <!-- ������Ϣ�����ҳ -->
            <div id="divTurnPageTaskLogGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageTaskLogGrid.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageTaskLogGrid.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageTaskLogGrid.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageTaskLogGrid.lastPage()">
            </div>
        </div>
        <!-- ���δ�����Ϣչ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divMoveErrorGrid)"></td>
                <td class="titleImg">���δ�����Ϣ</td>
            </tr>
        </table>
        <!-- ������Ϣչ�ֱ�� -->
        <div id="divMoveErrorGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanMoveErrorGrid"></span></td>
                </tr>
            </table>
            <!-- ������Ϣ�����ҳ -->
            <div id="divTurnPageMoveErrorGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageMoveErrorGrid.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageMoveErrorGrid.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageMoveErrorGrid.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageMoveErrorGrid.lastPage()">
            </div>
            <div id="divTurnPageResend" style="display:'none'">
                <!-- �ύ���ݲ�����ť -->
                <input type="button" class="cssButton" value=" �� �� " onClick="resend()">
            </div> 
        </div>       
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="LoginManageCom">
        <input type="hidden" name="LoginOperator">
        <input type="hidden" name="StartDate">
        <input type="hidden" name="EndDate">
        <input type="hidden" name="DocID" value="">
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
