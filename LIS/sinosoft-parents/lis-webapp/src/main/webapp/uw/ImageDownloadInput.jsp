<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ page import="com.sinosoft.lis.pubfun.GlobalInput" %>
<%
    //�������ƣ�ImageQuery.jsp
//�����ܣ�Ӱ�����ϲ�ѯ
//�������ڣ�2005-07-07 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
    String tPrtNo = "";
    tPrtNo = request.getParameter("PrtNo");
%>
<html>
<%
    //�����¸���
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var PrtNo = "<%=tPrtNo%>";
    var ttContNo = "<%=request.getParameter("ContNo")%>";
    var operator = "<%=tGI.Operator%>";   //��¼����Ա
    var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����

</script>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <script src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="ImageDownload.js"></SCRIPT>
    <link href="../common/css/Project.css" rel=stylesheet type=text/css>
    <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
    <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@ include file="ImageDownloadInit.jsp" %>
</head>

<body>
<form id="fm" name="fm" action="ImageDownloadSave.jsp" method="post" target="iframe1">
<div class="maxbox1">
    <table class="common" id="table2">
        <tr CLASS="common">
            <td CLASS="title" nowrap>Ͷ��������
            </td>
            <td CLASS="input" COLSPAN="1">
                <input id="PrtNo" NAME="PrtNo" VALUE="<%=tPrtNo%>" CLASS="readonly wid" readonly="readonly" TABINDEX="-1"
                       MAXLENGTH="14">
            </td>

            <td class=title>
                Ӱ�����
            </td>
            <td class=input>
                <Input class="codeno" id="subtype" name="subtype"
                style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
                       ondblclick="return showCodeList('imagetype',[this,subtypname],[0,1]);"
                       onkeyup="return showCodeListKey('Imagetype',[this,subtypname],[0,1]);"><Input class="codename"
                                                                                                  id="subtypname"  name="subtypname"
                                                                                                     readonly>
            </td>
            <td class=title>
                ɨ�����κ�
            </td>
            <td class=input>
                <Input class="common wid" id="ScanNo" name="ScanNo">
            </td>
        </tr>
    </table>
    </div>
    <INPUT class=cssButton VALUE="��   ѯ" TYPE=button onclick="imageQuery();">
</form>
<iframe width="100%" height="400" class="common" frameborder="0" name="iframe1" id="iframeid1">

</iframe>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>

</html>
