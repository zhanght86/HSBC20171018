<html>
<%
//�������ƣ�WFEdorApproveInput.jsp
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //��¼����Ա
    var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����
    var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFEdorApprove.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WFEdorApproveInit.jsp"%>

  <title>��ȫ����</title>

</head>

<body  onload="initForm();" >
  <form action="./WFEdorApproveSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<div id="EdorApproveInputPool"></div>
   <!--<table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
        <table  class= common >
            <TR  class= common>
                <td class=title> ��ȫ����� </td>
                <td class= input><Input class="common" name=EdorAcceptNo></td>
                <TD class= title> �������� </TD>
                <td class= input><Input class="codeno" name=OtherNoType ondblclick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);"onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName readonly=true ></td>
                <TD class= title> �ͻ�/������ </TD>
                <td class= input><Input class="common" name=OtherNo></td>
            </TR>
            <TR  class= common>
                <td class=title> ������ </td>
                <td class=input><Input type="input" class="common" name=EdorAppName></td>
                <td class=title> ���뷽ʽ </td>
                <td class= input ><Input class="codeno" name=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);"onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName readonly=true></td>
                <TD class= title> ¼������ </TD>
                <TD class= input><Input class= "multiDatePicker" dateFormat="short" name=MakeDate ></TD>
            </TR>
            <TR  class= common>
                <TD class=title> ������� </TD>
                <TD class=input><Input class="codeno" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true></TD>
                <TD class= title>��͸��˼���</TD>
				<TD class=input><input type="text" class="codeno" name="EdorPopedom" verify="����Ȩ�޼���|Code:EdorPopedom" ondblclick="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onkeyup="showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1])"><input type="text" class="codename" name="EdorPopedomName" readonly>
                <TD class= title>  </TD>
                <TD class= title>  </TD>
                <TD class= title>  </TD>
            </TR>
        </table>
    </div>

    <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClickAll();">

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divAllGrid);">
            </td>
            <td class= titleImg>
                 ��������
            </td>
        </tr>
    </table>
    <div id="divAllGrid" style="display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAllGrid"></span></td>
            </tr>
        </table>
        <div id="divTurnPageAllGrid" align="center" style="display:'none'">
            <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageAllGrid.firstPage();HighlightAllRow();">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageAllGrid.previousPage();HighlightAllRow();">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageAllGrid.nextPage();HighlightAllRow();">
            <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageAllGrid.lastPage();HighlightAllRow();">
        </div>
    </div>

    <br>
        <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="applyMission();">
    <br>

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
            </td>
            <td class= titleImg>
                 �ҵ�����
            </td>
        </tr>
    </table>
    <div  id="divSelfGrid" style="display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanSelfGrid"></span></td>
            </tr>
        </table>
        <div id="divTurnPageSelfGrid" align="center" style="display:'none'">
            <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageSelfGrid.firstPage();HighlightSelfRow();">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.previousPage();HighlightSelfRow();">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.nextPage();HighlightSelfRow();">
            <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageSelfGrid.lastPage();HighlightSelfRow();">
        </div>
    </div>  -->
    <br>
    <!--<INPUT class= cssButton TYPE=button VALUE=" ��ȫ���� " onclick="GoToBusiDeal()">
    <INPUT class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">��ȫ����</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">���±��鿴</a>

        <!-- �����򲿷� -->
        <input type="hidden" name="MissionID">
        <input type="hidden" name="SubMissionID">
        <input type="hidden" name="ActivityID">
        <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
