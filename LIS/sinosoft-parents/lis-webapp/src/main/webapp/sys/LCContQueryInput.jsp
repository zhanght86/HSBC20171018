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
 * @date     : 2006-02-20
 * @direction: ����״̬��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>����״̬��ѯ</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "LCContQueryMain.jsp";
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
    <script language="JavaScript" src="LCContQuery.js"></script>
    <%@ include file="LCContQueryInit.jsp" %>
</head>
<body topmargin="0" onLoad="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- ������Ϣ��ʾ��� -->
       <br>
        <table class="common">
            <tr class="common">
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id="ContNo" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
        <!-- ����״̬�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onClick="showPage(this,divContStateList)"></td>
                <td class="titleImg">����״̬��Ϣ</td>
           </tr>
        </table>
        <!-- ����״̬���չ�� -->
        <div id="divContStateList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanContStateGrid"></span></td>
                </tr>
            </table>
            <!-- ����״̬�����ҳ -->
            <div align="center" style= "display:none">
                <input type="button" class="cssButton" value="��  ҳ" onClick="turnPage.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPage.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPage.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onClick="turnPage.lastPage()">
            </div>
        </div>
        <br>
        <!-- �ύ���ݲ�����ť -->
        <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
    </form>
    <br><br><br><br><br>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
