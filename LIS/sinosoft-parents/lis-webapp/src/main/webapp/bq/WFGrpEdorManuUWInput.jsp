<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * @direction: �ŵ���ȫ�˹��˱��������������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ include file="../common/jsp/AccessCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�ŵ���ȫ�˹��˱�</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <SCRIPT language="JavaScript" src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="WFGrpEdorManuUW.js"></script>
    <%@ include file="WFGrpEdorManuUWInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- ���������в�ѯ -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllSearch)"></td>
                <td class="titleImg">�������ѯ����</td>
            </tr>
        </table>
        <!-- ������в�ѯ¼�� -->
        <div class="maxbox1">
        <div id="divEdorAllSearch" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title5">��ȫ�����</td>
                    <td class="input5"><input type="text" class="common wid" name="EdorAcceptNo1" id="EdorAcceptNo1"></td>
                    <td class="title5">��������</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="OtherNoType1" id="OtherNoType1" verify="��������|Code:EdorNoType" onclick="showCodeList('gedornotype',[this,EdorNoTypeName1],[0,1])" ondblclick="showCodeList('gedornotype',[this,EdorNoTypeName1],[0,1])" onkeyup="showCodeListKey('EdorNoType',[this,EdorNoTypeName1],[0,1])"><input type="text" class="codename" name="EdorNoTypeName1" id="EdorNoTypeName1" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title5">���屣����</td>
                    <td class="input5"><input type="text" class="common wid" name="OtherNo1" id="OtherNo1"></td>
                    <td class="title5">������</td>
                    <td class="input5"><input type="text" class="common wid" name="EdorAppName1" id="EdorAppName1"></td>
                </tr>
                <tr class="common">
                    <!--
                    <td class="title">���뷽ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="AppType1" ondblclick="showCodeList('EdorAppType',[this,AppTypeName1],[0,1])" onkeyup="showCodeListKey('EdorAppType',[this,AppTypeName1],[0,1])"><input type="text" class="codename" name="AppTypeName1" readonly></td>
                    -->
                    <td class="title5">�˱�״̬</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="UWState1" id="UWState1" verify="�˱�״̬|Code:UWActivityStatus" onclick="showCodeList('UWActivityStatus',[this,UWStateName1],[0,1])" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName1],[0,1])" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName1],[0,1])"><input type="text" class="codename" name="UWStateName1" id="UWStateName1" readonly>
                    <td class="title5">¼������</td>
                    <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate1'});" verify="¼������|DATE" dateFormat="short" name=MakeDate1 id="MakeDate1"><span class="icon"><a onClick="laydate({elem: '#MakeDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                </tr>
                <tr class="common">
                    <td class="title5">�������</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="ManageCom1" id="ManageCom1" verify="�������|Code:Station" onclick="showCodeList('Station',[this,ManageComName1],[0,1])" ondblclick="showCodeList('Station',[this,ManageComName1],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName1],[0,1])"><input type="text" class="codename" name="ManageComName1" id="ManageComName1" readonly></td>
                    <td class="title5">&nbsp;</td>
                    <td class="input5">&nbsp;</td>
                </tr>
            </table>
            <!-- ��ѯ���ݲ�����ť -->
            <!-- <input type="button" class="cssButton" value="��  ѯ" onclick="queryAllGrid()"> -->
        </div>
    </div>
    <a href="javascript:void(0)" class=button onclick="queryAllGrid();">��  ѯ</a>
        <!-- ��������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllList)"></td>
                <td class="titleImg">��������</td>
           </tr>
        </table>
        <!-- ������н��չ�� -->
        <div id="divEdorAllList" style="display:''" align=center>
            <table class="common">
                <tr class="common">
                    <td><span id="spanAllGrid"></span></td>
                </tr>
            </table>
            <!-- ������н����ҳ -->
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageAllGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageAllGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageAllGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageAllGrid.lastPage()">
        </div>
        <!-- �������ݲ�����ť -->
        <a href="javascript:void(0)" class=button onclick="applyTheMission();">��  ��</a>
        <!-- <input type="button" class="cssButton" value="��  ��" onclick="applyTheMission()"> -->
        <!-- ���������в�ѯ -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfSearch)"></td>
                <td class="titleImg">�������ѯ����</td>
            </tr>
        </table>
        <!-- �ҵ������ѯ¼�� -->
        <div class="maxbox">
        <div id="divEdorSelfSearch" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="common wid" name="EdorAcceptNo2" id="EdorAcceptNo2"></td>
                    <td class="title5">��������</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="OtherNoType2" id="OtherNoType2" verify="��������|Code:EdorNoType" onclick="showCodeList('gedornotype',[this,EdorNoTypeName2],[0,1])" ondblclick="showCodeList('gedornotype',[this,EdorNoTypeName2],[0,1])" onkeyup="showCodeListKey('EdorNoType',[this,EdorNoTypeName2],[0,1])"><input type="text" class="codename" name="EdorNoTypeName2" id="EdorNoTypeName2" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title5">�ͻ�/������</td>
                    <td class="input5"><input type="text" class="common wid" name="OtherNo2" id="OtherNo2"></td>
                    <td class="title5">������</td>
                    <td class="input5"><input type="text" class="common wid" name="EdorAppName2" id="EdorAppName2"></td>
                </tr>
                <tr class="common">
                    <!--
                    <td class="title">���뷽ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="AppType2" ondblclick="showCodeList('EdorAppType',[this,AppTypeName],[0,1])" onkeyup="showCodeListKey('EdorAppType',[this,AppTypeName],[0,1])"><input type="text" class="codename" name="AppTypeName2" readonly></td>
                    -->
                    <td class="title5">�˱�״̬</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="UWState2" id="UWState2" verify="�˱�״̬|Code:UWActivityStatus" onclick="showCodeList('UWActivityStatus',[this,UWStateName2],[0,1])" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName2],[0,1])" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName2],[0,1])"><input type="text" class="codename" name="UWStateName2" id="UWStateName2" readonly>
                    <td class="title5">¼������</td>
                    <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate2'});" verify="¼������|DATE" dateFormat="short" name=MakeDate2 id="MakeDate2"><span class="icon"><a onClick="laydate({elem: '#MakeDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                </tr>
                <tr class="common">
                    <td class="title5">�������</td>
                    <td class="input5"><input  style="background:url(../common/images/select--bg_03.png)         no-repeat right center" type="text" class="codeno" name="ManageCom2" id="ManageCom2" verify="�������|Code:Station" onclick="showCodeList('Station',[this,ManageComName2],[0,1])" ondblclick="showCodeList('Station',[this,ManageComName2],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName2],[0,1])"><input type="text" class="codename" name="ManageComName2" id="ManageComName2" readonly></td>
                    <td class="title5">&nbsp;</td>
                    <td class="input5">&nbsp;</td>
                </tr>
            </table>
            <!-- ��ѯ���ݲ�����ť -->
            <!-- <input type="button" class="cssButton" value="��  ѯ" onclick="querySelfGrid()"> -->
        </div>
    </div>
    <a href="javascript:void(0)" class=button onclick="querySelfGrid();">��  ѯ</a>
        <!-- �ҵ������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfList)"></td>
                <td class="titleImg">�ҵ�����</td>
           </tr>
        </table>
        <!-- �ҵ�������չ�� -->
        <div id="divEdorSelfList" style="display:''" align=center>
            <table class="common">
                <tr class="common">
                    <td><span id="spanSelfGrid"></span></td>
                </tr>
            </table>
            <!-- �ҵ���������ҳ -->
            <div style="display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageSelfGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageSelfGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageSelfGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageSelfGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="MissionID" id="MissionID">
        <input type="hidden" name="SubMissionID" id="SubMissionID">
        <input type="hidden" name="ActivityID" id="ActivityID">
        <input type="hidden" name="ActivityStatus" id="ActivityStatus">
        <input type="hidden" name="LoginOperator"  id="LoginOperator" >
        <input type="hidden" name="LoginManageCom" id="LoginManageCom">
        <!-- �ύ���ݲ�����ť -->
        <a href="javascript:void(0)" class=button onclick="gotoManuUWDeal();">�˹��˱�</a>
        <a href="javascript:void(0)" class=button onclick="showNotePad();">���±��鿴</a>
        <!-- <input type="button" class="cssButton" value="�˹��˱�" onclick="gotoManuUWDeal()"> -->
        <!--
        <input type="button" class="cssButton" value=" ��ȫ��ϸ��ѯ " onclick="showEdorDetail()">
        -->
        <!-- <input type="button" class="cssButton" value="���±��鿴" onclick="showNotePad()"> -->
    </form>
    <br>
    <br>
    <br>
    <br>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
