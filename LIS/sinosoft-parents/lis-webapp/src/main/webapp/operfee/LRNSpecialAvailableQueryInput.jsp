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
 * @version  : 1.00, 1.01
 * @date     : 2005-12-03, 2006-02-15
 * @direction: �������⸴Ч��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�������⸴Ч</title>
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
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="LRNSpecialAvailableQueryInput.js"></script>
    <%@ include file="LRNSpecialAvailableQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="_blank">
        <!-- �ظ���ѯ�۵�չ�� -->
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
                </td>
                <td class="titleImg">�������ѯ����</td>
            </tr>
        </table>
        <div class="maxbox1">
        <div  id= "divFCDay" style= "display: ''"> 
        <table class="common">
            <tr class="common">
                <td class="title5">��ͬ����</td>
                <td class="input5"><input type="text" class="common wid" name="ContNo" id="ContNo" maxlength="24"></td>
                <td class="title5">ʧЧԭ��</td>
                <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  type="text" class="codeno" name="InvalidReason" id="InvalidReason" verify="ʧЧԭ��|Code:contavailablereason" onclick="return showCodeList('contavailablereason',[this,InvalidReasonName],[0,1],null,null,null,0,450)" ondblclick="return showCodeList('contavailablereason',[this,InvalidReasonName],[0,1],null,null,null,0,450)" onkeyup="clearEmptyCode(this,InvalidReasonName); return showCodeListKey('contavailablereason',[this,InvalidReasonName],[0,1],null,null,null,0,450);"><input type="text" class="codename" name="InvalidReasonName" id="InvalidReasonName" readonly></td>
            </tr>
            <tr class="common">
                <td class="title5">ͳ������</td>
                <td class="input5"><Input  type="text" class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|Date" dateFormat="short" name=StartDate id="StartDate" onblur="formatDate(this)"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title5">�� �� Ա</td>
                <td class="input5"><input type="text" class="common wid" name="Operator" id="Operator" maxlength="40"></td>
            </tr>
            <tr class="common">
                <td class="title5">�������</td>
                <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  type="text" class="codeno" name="ManageCom" id="ManageCom" verify="�������|Code:ComCode" onclick="return showCodeList('ComCode',[this,ManageComName],[0,1],null,null,null,0,450)" ondblclick="return showCodeList('ComCode',[this,ManageComName],[0,1],null,null,null,0,450)" onkeyup="clearEmptyCode(this,ManageComName); return showCodeListKey('ComCode',[this,ManageComName],[0,1],null,null,null,0,450);"><input type="text" class="codename" name="ManageComName" id="ManageComName" readonly></td>
                <td class="title5">ͳ��ֹ��</td>
                <td class="input5"><Input type="text" class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|Date" dateFormat="short" name=EndDate id="EndDate" onblur="formatDate(this)"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
            </tr>
        </table>
    </div>
    </div>
        <!-- �ύ���ݲ�����ť -->
        <a href="javascript:void(0)" class=button onclick="queryRevalidateTrack();">��  ѯ</a>
        <a href="javascript:void(0)" class=button onclick="printRevalidateTrack();">��  ӡ</a>
        <!-- <input type="button" class="cssButton" value=" �� ѯ " onclick="queryRevalidateTrack()">
        <input type="button" class="cssButton" value=" �� ӡ " onclick="printRevalidateTrack()"> -->
        <!-- ��Ч��ѯ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divQueryList)"></td>
                <td class="titleImg">��Ч��ͬ��Ϣ</td>
           </tr>
        </table>
        <!-- ��Ч��ѯ���չ�� -->
        <div id="divQueryList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanQueryGrid"></span></td>
                </tr>
            </table>
            <!-- ��Ч��ѯ�����ҳ -->
            <div align="center" style= "display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPage.lastPage()">
            </div>
        </div>
    </form>
    <br>
    <br>
    <br>
    <br>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
