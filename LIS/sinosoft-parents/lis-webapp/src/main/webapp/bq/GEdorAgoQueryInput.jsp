<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * @direction: ���屣ȫ�˹��˱�Ͷ����λ������ȫ��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>Ͷ����λ������ȫ��ѯ</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű�  -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="GEdorAgoQuery.js"></script>
    <%@ include file="GEdorAgoQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- �ͻ�����������ʾ -->
        <div class="maxbox1">
        <table class="common">
            <tr class="common">
                <td class="title">����ͻ���</td>
                <td class="input"><input type="text" class="readonly wid" name="CustomerNo" id="CustomerNo" readonly></td>
                <td class="title">��λ����</td>
                <td class="input"><input type="text" class="readonly wid" name="GrpName" id="GrpName" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table></div>
            <br>
            <!-- ������Ŀ�۵�չ�� -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorItemGrid)"></td>
                    <td class="titleImg">������ȫ������Ŀ��Ϣ</td>
                </tr>
            </table>
            <!-- ������Ŀ���չ�� -->
            <div id="divEdorItemGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanEdorItemGrid"></span></td>
                    </tr>
                </table>
                <!-- ������Ŀ�����ҳ -->
                <div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageEdorItemGrid.firstPage()">
                    <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageEdorItemGrid.previousPage()">
                    <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageEdorItemGrid.nextPage()">
                    <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageEdorItemGrid.lastPage()">
                </div>
                <!-- ��ѯ���ݲ�����ť -->
                <input type="button" class="cssButton" value="   ������ѯ   " onclick="EndorseDetail()">
                <input type="button" class="cssButton" value=" �����嵥��ѯ " onclick="NamesBill()">
                <input type="button" class="cssButton" value=" ɨ��Ӱ���ѯ " onclick="showImage()">
                <br><br>
            </div>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="EdorAcceptNo" id=EdorAcceptNo>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="GrpContNo" id=GrpContNo>
        <input type="hidden" name="EdorValiDate" id=EdorValiDate>
        <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
        <!-- �ر�ҳ�������ť -->
        <!--<input type="button" class="cssButton" value="   ��    ��   " onclick="returnParent()">-->
        <br>
<a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	
<br/><br/><br/><br/>
</body>
</html>
