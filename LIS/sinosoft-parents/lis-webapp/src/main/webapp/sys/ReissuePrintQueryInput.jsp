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
 * @date     : 2006-03-01
 * @direction: ������ӡ��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>������ӡ��ѯ</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "ReissuePrintQueryMain.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <script language="JavaScript" src="../common/laydate/laydate.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="ReissuePrintQuery.js"></script>
    <%@ include file="ReissuePrintQueryInit.jsp" %>
</head>
<body topmargin="0" onLoad="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- ������Ϣ��ʾ��� -->
       <Br>
        <table class="common">
            <tr class="common">
                <td class="title5">��ȫ�����</td>
                <td class="input5"><input type="text" class="readonly wid" name="EdorAcceptNo" id="EdorAcceptNo" readonly></td>
                <td class="title5">��������</td>
                <td class="input5"><input type="text" class="readonly wid" name="ContNo" id="ContNo" readonly></td>
            </tr>
        </table>
        <!-- ������ӡ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onClick="showPage(this,divReissuePrintList)"></td>
                <td class="titleImg">������ӡ��Ϣ</td>
           </tr>
        </table>
        <!-- ������ӡ���չ�� -->
        <div id="divReissuePrintList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanReissuePrintGrid"></span></td>
                </tr>
            </table>
            <!-- ����״̬�����ҳ -->
            <div align="center" style= "display:">
                <input type="button" class="cssButton90" value="��  ҳ" onClick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onClick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onClick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onClick="turnPage.lastPage()">
            </div>
        </div>
        <br>
        <!-- �ύ���ݲ�����ť -->
        <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	<br/><br/><br/><br/>
</body>
</html>
