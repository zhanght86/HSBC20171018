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
 * @version  : 1.00, 1.01
 * @date     : 2006-12-06, 2006-12-18
 * @direction: Ӱ��Ǩ�������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>Ӱ��Ǩ��</title>
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
    <script language="JavaScript" src="ImgMove.js"></script>
    <%@ include file="ImgMoveInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- ��ѯ�����۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divQueryInput)"></td>
                <td class="titleImg">����Ǩ������</td>
           </tr>
        </table>
        <div id="divQueryInput" class=maxbox1 style="display:''">
            <!-- ��ѯ����¼���� -->
            <table class="common">
                <tr class="common">
                    <td class="title">Ǩ������</td>
                    <td class="input"><input type="text" class="codeno" name="OldManageCom" id=OldManageCom verify="Ǩ������|NotNull" readonly><input type="text" class="codename" name="OldManageComName" readonly></td>
                    <!-- �Ժ������Ҫ��չ�������Ի�������,�򻻳�����2��ע�͵��ľͿ��� -->
                    <!-- <td class="input"><input type = "text" class = "codeno" name = "OldManageCom" ondblclick="return showCodeList('station',[this,OldManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,OldManageComName],[0,1]);"><input class=codename name=OldManageComName readonly></TD> -->
                    <td class="title">Ǩ�����</td>
                    <td class="input"><input type="text" class="codeno" name="NewManageCom" verify="Ǩ�����|NotNull" readonly><input type="text" class="codename" name="NewManageComName" readonly></td>
                    <!-- <td class="input"><input type = "text" class = "codeno" name = "NewManageCom" ondblclick="return showCodeList('station',[this,NewManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,NewManageComName],[0,1]);"><input class=codename name=NewManageComName readonly></TD> -->
                </tr>
                <tr class="common">
                    <td class="title">ɨ������</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="StartDate" dateFormat="short" verify="ɨ������|Date" onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                    <td class="title">ɨ��ֹ��</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="EndDate" dateFormat="short" verify="ɨ��ֹ��|Date" onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                </tr>
            </table>
        </div>
        <br>
        <!-- �ύ���ݲ�����ť -->
        <input type="button" class="cssButton" value=" Ǩ �� " onClick="moveImage()">
        <input type="button" class="cssButton" value=" �� �� " onClick="resetForm()">
        <br>
        <br>
        <table>
            <tr>
                <td class="common"></td>
                <td class="common" style="color:red">Ϊ��֤���ͳɹ�,��ֹ������MD5���Ӱ��Ŀ¼����ɾ�Ĳ������MD5���ļ���һ��,������Ǩ��ǰ֪ͨ�ֹ�˾��������MD5.</td>
           </tr>
        </table>        
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="LoginManageCom">
        <input type="hidden" name="LoginOperator">
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
