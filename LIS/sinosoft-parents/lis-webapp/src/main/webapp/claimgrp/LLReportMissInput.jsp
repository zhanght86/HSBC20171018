<%
//**************************************************************************************************
//Name��LLReportMissInput.jsp
//Function����������������Ϣ
//Author��zl
//Date: 2005-6-9 15:31
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
    <SCRIPT src="LLReportMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLReportMissInit.jsp"%>
    <title>�����Ǽ� </title>
</head>
<body  onload="initForm();" >
    <form action="./LLReportMissSave.jsp" method=post name=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <!--hr>
    <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
        <table class= common>
            <TR class= common>    
                <TD class= title> ������ </TD>
                <TD class= input> <Input class= common name=RptNo ></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class= common name=RptorName ></TD>
                <TD class= title> �����˵绰 </TD>       
                <TD class= input> <Input class= common name=RptorPhone ></TD>
            </TR>
            <TR class= common>                  
                <TD class= title> ������ͨѶ��ַ </TD>
                <TD class= input> <Input class= common name=RptorAddress ></TD>          
                <TD class= title> ���������¹��˹�ϵ </TD>
                <TD class= input> <Input class=codeno name=Relation ondblclick="return showCodeList('Relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('Relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
                <TD class= title> ������ʽ </TD>
                <TD class= input> <Input class=codeno name="RptMode" ondblclick="return showCodeList('llrptcasetype',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptcasetype',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" readonly=true></TD>            
            </TR>
         
        </table> 
    </DIV>
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();"> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table>      
    <Div  id= "divLLReport" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLReportGrid" ></span> </TD>
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
    
   <br>
    <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();"-->

    <DIV id=DivLCContInfo STYLE="display:''"> 
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLReportGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>      
        </table>
    </Div>
    <Div  id= "divSelfLLReportGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLReportGrid" ></span></td>
            </tr>
        </table>
        <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();">                     
        <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();">     
    </div>
    <br>
    <hr>
    <table  class= common >
        <tr  class=common>    
            <td><INPUT class=cssButton VALUE="��������" TYPE=button onclick="newReport();"></td> 
        </tr>
    </table>            
    <%
    //�������������ر���
    %>  
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
