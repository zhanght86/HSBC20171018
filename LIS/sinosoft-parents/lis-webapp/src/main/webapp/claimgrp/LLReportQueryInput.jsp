<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
<html>
<head>
<title>������Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLReportQuery.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLReportQueryInit.jsp"%>
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
%>
</head>
<body  onload="initForm();">
<!--��¼������-->
<br>
<form name=fm target=fraSubmit method=post>
	  <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanLLReport);"></TD>
            <TD class= titleImg> ��ѯ���� </TD>
        </TR>
    </Table>
    <span id= "spanLLReport" style= "display: ''">
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
<!--
            <TR class= common>            
                <TD class= title> ���յص� </TD>     
                <TD class= input> <Input class= common name=AccidentSite ></TD>        
                <TD class= title> ������������ </TD>
                <TD class= input>  <input class="readonly" readonly name="RptDate" ></TD>
                <TD class= title> ��Ͻ���� </TD>
                <TD class= input> <Input class= "readonly" readonly name=MngCom></TD>         
            </TR>
            <TR class= common>                  
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class="readonly" readonly name=Operator ></TD>        
                <TD class= title> ����״̬ </TD>
                <TD class= input> <Input class= "readonly" readonly name=CaseState></TD>
            </TR>
-->            
        </table> 
    </span>  
    <table> 
        <tr>    
            <td> <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();">  </td>
            <td> <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();">  </td>
        </tr> 
    </table> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport);"></TD>
            <TD class= titleImg> ������Ϣ </TD>
        </TR>
    </Table>      
    <Div  id= "divLLReport" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLReportGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>    
    </Div>          

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
