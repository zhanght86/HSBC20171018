<%
//**************************************************************************************************
//Name��LLClaimPrtQueryMissInput.jsp
//Function���ⰸ��ѯ���
//Author��zl
//Date: 2005-8-22 10:17
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPrtQueryMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimPrtQueryMissInit.jsp"%>
    <title> �ⰸ��ӡ��ѯ </title>
</head>
<body  onload="initForm();" >
    <form method=post name=fm target="fraSubmit">
    <!-- ����ز��� -->
    <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> �ⰸ�� </TD>
                <TD class= input> <Input class= common name=RptNo ></TD>
                <TD class= title> �ⰸ״̬ </TD>
                <TD class= input> <Input class=codeno name=ClmState ondblclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);" onkeyup="return showCodeListKey('llclaimstate',[this,ClmStateName],[0,1]);"><input class=codename name=ClmStateName readonly=true></TD>
                <TD class= title> �����¹����� </TD>
                <TD class= input> <input class="coolDatePicker" dateFormat="short" name="AccidentDate" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> �����˱��� </TD>
                <TD class= input> <Input class= common name=CustomerNo ></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class= common name=CustomerName ></TD>
                <!--TD class= title> �������Ա� </TD>
                <TD class= input> <Input class=codeno name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD-->
                <TD class= title> �������� </TD>
                <TD class= input> <input class="coolDatePicker" dateFormat="short" name="AccidentDate2" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> �������� </TD>
                <TD class= input> <input class="coolDatePicker" dateFormat="short" name="RgtDate" ></TD>
                <TD class= title> �᰸���� </TD>
                <TD class= input> <input class="coolDatePicker" dateFormat="short" name="EndDate" ></TD>
                <TD class= title> ��ͬ�� </TD>
                <TD class= input> <input class=common name="ContNo" ></TD>
            </TR>
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQuery);"></TD>
            <TD class= titleImg> �ⰸ�б� </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimQuery" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimQueryGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
    <hr>
    <table>
        <tr>
            <td><INPUT class=cssButton VALUE="�ⰸ��ѯ" TYPE=button onclick="showDetail();"></td>
            <td><INPUT class=cssButton VALUE="�ⰸ��ӡ" TYPE=button onclick="showPrint();"></td>
        </tr>
    </table>
    
    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
