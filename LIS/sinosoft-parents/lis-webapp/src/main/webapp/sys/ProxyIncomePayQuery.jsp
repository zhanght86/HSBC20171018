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
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2005-11-03, 2005-12-03, 2006-02-08, 2006-11-14
 * @direction: ���մ�����ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>���մ�����ѯ</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "ProxyIncomePayQueryMain.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű�  -->
    <script language="JavaScript" src="../common/laydate/laydate.js"></script>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="ProxyIncomePayQuery.js"></script>
    <%@ include file="ProxyIncomePayQueryInit.jsp" %>
</head>
<body topmargin="0" onLoad="initForm()" ondragstart="return false">
    <form name="fm" id="fm">
        <!-- ��ѯ�����۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divInputQueryLayer)"></td>
                <td class="titleImg">������Ϣ</td>
            </tr>
        </table>
        <!-- ��ѯ����¼���� -->
        <div id="divInputQueryLayer" class="maxbox1" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="common wid" name="OtherNo" id="OtherNo" maxlength="24"></td>
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="common wid" name="EdorAcceptNo" id="EdorAcceptNo" maxlength="24"></td>
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="common wid" name="EdorNo" id="EdorNo" maxlength="24"></td>
                </tr>
            </table>
        </div>
        <input type="button" class="cssButton" value=" �� ѯ " onClick="queryProxyGrid()">
        <br><br>
        <!-- ��ѯ����۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divQueryResult)"></td>
                <td class="titleImg">��ѯ���</td>
           </tr>
        </table>
        <!-- ��ѯ���չ�ֱ�� -->
        <div id="divQueryResult" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanProxyGrid"></span></td>
                </tr>
            </table>
            <!-- ��ѯ�����ҳ��ť -->
            <div id="divTurnPageProxyGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onClick="turnPageProxyGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onClick="turnPageProxyGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onClick="turnPageProxyGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onClick="turnPageProxyGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- �ύ���ݲ�����ť -->
        <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
        <!-- ͨ��������Ϣ�б� -->
        <span id="spanCode" style="display:none; position:absolute; slategray"></span>
    </form>
</body>
</html>
