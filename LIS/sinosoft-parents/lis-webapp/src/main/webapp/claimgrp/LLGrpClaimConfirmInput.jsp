<%
//**************************************************************************************************
//Name��LLGrpClaimConfirmInput.jsp
//Function�����װ�������ҳ��
//Author��pd
//Date: 2005-11-16
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
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLGrpClaimConfirm.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLGrpClaimConfirmInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <!-- <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table> -->

    <Div  id= "divSearch" style= "display: none" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> �ⰸ�� </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> �ͻ����� </TD>
                <TD class= input5><Input class="wid" class=common name=CustomerNo id=CustomerNo ></TD></TR>
                <TR class= common>
                <TD class= title5> �ͻ����� </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
            </TR>

        </table>
    </DIV>
    <!-- <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="querySelfGrid();">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimSimpleGrid);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table> -->
    <Div id= "divSelfLLClaimSimpleGrid" style= "display: none">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimSimpleGrid" ></span></td>
            </tr>
        </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>        
    </div>
    
    <!-- <hr>
    
    <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyClaim2();"> -->
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimSimpleGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>    
   <Div id= "divLLClaimSimpleGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanLLClaimSimpleGrid" ></span></td>
            </tr>
        </table>
          
    </div> 
     

    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <input type=hidden id="MissionID"    name= "MissionID">
    <input type=hidden id="SubMissionID" name= "SubMissionID">
    <input type=hidden id="ActivityID" name= "ActivityID">    
    <Input type=hidden name=CurDate  id="CurDate">
        
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
