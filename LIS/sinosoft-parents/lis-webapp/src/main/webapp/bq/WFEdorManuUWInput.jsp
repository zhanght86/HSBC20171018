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
 * @version  : 1.00
 * @date     : 2005-12-12
 * @direction: ��ȫ�˹��˱������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ include file="../common/jsp/AccessCheck.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String sOperator = tGlobalInput.Operator;
    String sManageCom = tGlobalInput.ManageCom;
    String sComCode = tGlobalInput.ComCode;
    tGlobalInput = null;
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��ȫ�˹��˱�</title>
    <!-- ��ȡ��½��Ϣ -->
    <script language="JavaScript">
        var operator = "<%=sOperator%>";
        var manageCom = "<%=sManageCom%>";
        var comcode = "<%=sComCode%>";
        var curDay = "<%=PubFun.getCurrentDate()%>"; 
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű�  -->
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="WFEdorManuUW.js"></script>
    <%@ include file="WFEdorManuUWInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" action="WFEdorManuUWSave.jsp" method="post" target="fraSubmit">
    <div id="ManuUWInputPool"></div>
        <!-- ======================================================================= -->
        <!-- ���������в�ѯ -->
    <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllSearch)"></td>
                <td class="titleImg">�������ѯ����</td>
            </tr>
        </table>
        <!-- ������в�ѯ¼�� -->
       <!--  <div id="divEdorAllSearch" style="display:''">
            <table class="common" >
                <tr class="common">
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="common" name="OtherNo"></td>
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="common" name="EdorAcceptNo"></td>
                	<td class="title">��ȫ������</td>
                    <td class="input"><input type="text" class="common" name="EdorAppName" ></td>
                </tr>
                <tr class="common">
                	<td class="title">ҵ�����</td>
                    <td class="input"><Input class= "codeno" CodeData="" name=EdorType  ondblclick="showCodeListEx('edorcode', [this,EdorTypeName], [0,1]);" onkeyup="showCodeListKeyEx('edorcode', [this,EdorTypeName], [0,1]);"><input class=codename name=EdorTypeName ></TD>
                    <td class="title">����״̬</td>
                    <td class="input"><input type="text" class="codeno" name="UWState"  value= "1"  verify="�˱�״̬|Code:UWActivityStatus" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName],[0,1],null,' 1 and code<#5#','1')" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName],[0,1],null,' 1 and code<#5#','1')"><input type="text" class="codename" name="UWStateName" value ="δ�˹��˱�" ></td>
                    <td class="title">�������</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom" verify="�������|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName],[0,1])"><input class="codename" name="ManageComName" ></td>
                </tr>
                <tr class="common">
                    <td class="title">Ͷ����</td>
                    <td class="input"><input type="text" class="common" name="AppntName"></td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="common" name="InsuredName"></td>
                    <!--
                    <td class="title">���뷽ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" ondblclick="showCodeList('EdorAppType',[this, AppTypeName],[0,1])" onkeyup="showCodeListKey('EdorAppType', [this,AppTypeName],[0,1])"><input type="text" class="codename" name="AppTypeName" readonly></td>
                    -->
                    
                    <!--  <td class="title">VIP�ͻ�</td>
                    <td class="input"><input type="text" class="common" name="VIP"></td>
                </tr>                
                <tr class="common">
                    <td class="title">ת������</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="theCurrentDate"></td>
                    <td class="title">���ظ�����</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="BackDate"></td>
                	<td class="title">���ظ�ʱ��</td>
                    <td class="input"><input type="text" class="common" name="BackTime" value="00:00:00"></td>
                </tr>
                <tr class="common">
                	<td class="title">�Ǽ�ҵ��Ա</td>
                    <td class="input"><input type="text" class="common" name="StarAgent" ></td>
                </tr>
            </table>
            <!-- ��ѯ���ݲ�����ť -->
            
            <!-- <input type="button" class="cssButton" value=" �� ѯ " onclick="easyQueryClickAll()">
            <TD  class= title> �ܵ���</TD>
      		<TD  class= input> <Input class="readonly" readonly name=PolSum  ></TD>
        </div>
        <!-- ��������۵�չ�� -->
        <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllList)"></td>
                <td class="titleImg">��������</td>
           </tr>
        </table>
        <!-- ������н��չ�� -->
        <!-- <div id="divEdorAllList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanAllGrid"></span></td>
                </tr>
            </table>
            <!-- ������н����ҳ -->
           <!--  <div id="divTurnPage1" align="center" style="display:''">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPage1.firstPage();HighlightAllRow();">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage1.previousPage();HighlightAllRow();">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage1.nextPage();HighlightAllRow();">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPage1.lastPage();HighlightAllRow();">
            </div>
        </div>
        <br>
        <!-- �������ݲ�����ť
        <input type="button" class="cssButton" value=" �� �� " onclick="applyMission()">  -->
        <!-- <br>
        <!-- ======================================================================= -->
        <!-- �ҵ�������в�ѯ -->
        <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfSearch)"></td>
                <td class="titleImg">�������ѯ����</td>
            </tr>
        </table>
        <!-- �ҵ������ѯ¼�� -->
        <!-- <div id="divEdorSelfSearch" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="common" name="OtherNo2"></td>
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="common" name="EdorAcceptNo2"></td>
                	<td class="title">��ȫ������</td>
                    <td class="input"><input type="text" class="common" name="EdorAppName2" ></td>
                </tr>
                <tr class="common">
                	<td class="title">ҵ�����</td>
                   <td class="input"><Input class= "codeno" CodeData="" name=EdorType2  ondblclick="showCodeListEx('edorcode', [this,EdorTypeName2], [0,1]);" onkeyup="showCodeListKeyEx('edorcode', [this,EdorTypeName2], [0,1]);"><input class=codename name=EdorTypeName2 ></TD>
                    <td class="title">����״̬</td>
                    <td class="input"><input type="text" class="codeno" name="UWState2" value ="1"  verify="�˱�״̬|Code:UWActivityStatus" ondblClick="showCodeList('UWActivityStatus',[this,UWStateName2],[0,1],null,' 1 and code<#5#','1')" onkeyup="showCodeListKey('UWActivityStatus',[this,UWStateName2],[0,1],null,' 1 and code<#5#','1')"><input type="text" class="codename" name="UWStateName2" value ="δ�˹��˱�" ></td>
                    <td class="title">�������</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom2" verify="�������|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName2],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName2],[0,1])"><input class="codename" name="ManageComName2" ></td>
                </tr>
                <tr class="common">
                    <td class="title">Ͷ����</td>
                    <td class="input"><input type="text" class="common" name="AppntName2"></td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="common" name="InsuredName2"></td>
                    <!--
                    <td class="title">���뷽ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" ondblclick="showCodeList('EdorAppType',[this, AppTypeName],[0,1])" onkeyup="showCodeListKey('EdorAppType', [this,AppTypeName],[0,1])"><input type="text" class="codename" name="AppTypeName" readonly></td>
                    -->
                    
                    <!--<td class="title">VIP�ͻ�</td>
                    <td class="input"><input type="text" class="common" name="VIP2"></td>
                </tr>                
                <tr class="common">
                    <td class="title">ת������</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="theCurrentDate2"></td>
                    <td class="title">���ظ�����</td>
                    <td class="input"><input type="text" class= "multiDatePicker" dateFormat="short" name="BackDate2"></td>
                	<td class="title">���ظ�ʱ��</td>
                    <td class="input"><input type="text" class="common" name="BackTime2" value="00:00:00"></td>
                </tr>
                <tr class="common">
                	<td class="title">�Ǽ�ҵ��Ա</td>
                    <td class="input"><input type="text" class="common" name="StarAgent2" ></td>
                </tr>
            </table>
            <!-- ��ѯ���ݲ�����ť -->
           <!--  <input type="button" class="cssButton" value=" �� ѯ " onclick="easyQueryClickSelf()">
        </div>
        <!-- �ҵ������۵�չ�� -->
        <!-- <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorSelfList)"></td>
                <td class="titleImg">�ҵ�����</td>
           </tr>
        </table>
        <!-- �ҵ�������չ�� -->
         <!--<div id="divEdorSelfList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanSelfGrid"></span></td>
                </tr>
            </table>
            <!-- �ҵ���������ҳ -->
            <!-- <div id="divTurnPage2" align="center" style="display:''">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPage2.firstPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage2.previousPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage2.nextPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPage2.lastPage();HighlightSelfRow();">
            </div>
        </div>
        <br> -->
        <!-- ======================================================================= -->
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" value="3" name="OtherNoType" >
        <input type="hidden" name="Operator" value="<%=sOperator%>">
        <input type="hidden" name="MissionID">
        <input type="hidden" name="SubMissionID">
        <input type="hidden" name="ActivityID" >
        <input type="hidden" name="fmtransact">
        <input type="hidden" name="customerNo"> 
        <!-- �ύ���ݲ�����ť -->
       <!-- <div id="hidden" align="center" style="display:'none'">
        <table>
        <input type="button" class="cssButton" value=" �˹��˱� " onclick="GoToBusiDeal()">
        <input type="button" class="cssButton" value=" ��ȫ��ϸ��ѯ " onclick="EdorDetailQuery()">
        <input type="button" class="cssButton" name="NotePadButton" value=" ���±��鿴 " onclick="showNotePad()">
        </table>
        </div>--> 
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
