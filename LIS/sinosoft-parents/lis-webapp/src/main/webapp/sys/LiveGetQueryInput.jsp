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
 * @date     : 2005-12-02, 2006-02-22, 2006-05-12, 2006-11-14
 * @direction: �ۺϲ�ѯ������ȡ��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>������ȡ��ѯ</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
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
    <script language="JavaScript" src="LiveGetQuery.js"></script>
    <%@ include file="LiveGetQueryInit.jsp" %>
</head>
<body topmargin="0" onLoad="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divPolInfo)"></td>
                <td class="titleImg">������Ϣ</td>
           </tr>
        </table>
        <!-- ������Ϣ��ʾ��� -->
        <div id="divPolInfo" class="maxbox" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="readonly wid" name="ContNo" id="ContNo" readonly></td>
                    <td class="title">���ֺ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="PolNo" id="PolNo" readonly></td>
                    <td class="title">���ֱ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="RiskCode" id="RiskCode" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">Ͷ��������</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" id="AppntName" readonly></td>
                    <td class="title">����������</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" id="InsuredName" readonly></td>
                    <td class="title">��ȡ��ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="GetForm" id="GetForm" readonly><input type="text" class="codename" name="GetFormName" readonly></td>
                </tr>
                <tr class="common">
                  <td class="title">��������</td>
                  <td class="input"><input type="text" class="codeno" name="GetBank" id="GetBank" readonly><input type="text" class="codename" name="GetBankName" readonly></td>
                  <td class="title">�����ʺ�</td>
                  <td class="input"><input type="text" class="readonly wid" name="GetBankAccNo" id="GetBankAccNo" readonly></td>
                  <td class="title">�ʻ���</td>
                  <td class="input"><input type="text" class="readonly wid" name="GetAccName" id="GetAccName" readonly></td>
                </tr>
            </table>
        </div>
        <!-- ��ȡ��Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divLiveGetList)"></td>
                <td class="titleImg">��ȡ��Ϣ</td>
           </tr>
        </table>
        <!-- ��ȡ��Ϣչ�ֱ�� -->
        <div id="divLiveGetList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanLiveGetGrid"></span></td>
                </tr>
            </table>
            <!-- ��ȡ��Ϣ�����ҳ -->
            <div id="divTurnPageLiveGetGrid" align="center" style="display:none">
                <input type="button" class="cssButton" value="��  ҳ" onClick="turnPageLiveGetGrid.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPageLiveGetGrid.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPageLiveGetGrid.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onClick="turnPageLiveGetGrid.lastPage()">
            </div>
        </div>
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBankProxyList)"></td>
                <td class="titleImg">������Ϣ</td>
           </tr>
        </table>
        <!-- ������Ϣչ�ֱ�� -->
        <div id="divBankProxyList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanBankProxyGrid"></span></td>
                </tr>
            </table>
            <!-- ������Ϣ�����ҳ -->
            <div id="divTurnPageBankProxyGrid" align="center" style="display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onClick="turnPageBankProxyGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onClick="turnPageBankProxyGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onClick="turnPageBankProxyGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onClick="turnPageBankProxyGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- �ύ���ݲ�����ť -->
        <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
