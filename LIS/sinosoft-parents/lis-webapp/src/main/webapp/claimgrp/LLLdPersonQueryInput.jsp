<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>�ͻ���Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLLdPersonQuery.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLLdPersonQueryInit.jsp"%>
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
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanLDPerson);"></TD>
            <TD class= titleImg> ��ѯ���� </TD>
        </TR>
    </Table>
    <span id= "spanLDPerson" style= "display: ''">
        <table class= common align=center>
            <TR class= common>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= common name=CustomerNo >  </TD>
                <TD class= title> ���� </TD>
                <TD class= input> <Input class= common name=Name >  </TD>
                <TD class= title> ��������</TD>
                <TD class= input> <Input class="coolDatePicker" dateFormat="short" name=Birthday >             </TD>
            </TR>             
            <TR  class= common>
                <TD  class= title> �Ա�  </TD>
                <TD  class= input> <Input name=Sex class=codeno ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input class=codename name="SexName" readonly=true></TD>       
                <TD  class= title> ֤������ </TD>
                <TD  class= input><Input name=IDType class=codeno ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);"><input class=codename name="IDTypeName" readonly=true></TD>
                <TD  class= title> ֤������ </TD>
                <TD  class= input> <Input class= common name=IDNo > </TD>
            </TR>   
            <TR  class= common>
                <TD  class= title> �������� </TD>
                <TD  class= input> <Input class= common name=ContNo > </TD>
                <TD  class= title> ���屣������ </TD>
                <TD  class= input> <Input class= common name=GrpContNo > </TD>
            </TR>   
        </Table>
    </span>  
    <table> 
        <tr>    
            <td> <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();">  </td>
            <td> <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();">  </td>
            <!--td> <INPUT class=cssButton VALUE="����(���رմ���)" TYPE=button onclick="returnParent2();">  </td-->
            <!--
            <td>  <INPUT class=cssButton VALUE="�ͻ���Ϣ" TYPE=button onclick="returnParent();">  </td>
            <td> <INPUT class=cssButton VALUE="Ͷ������Ϣ" TYPE=button onclick="ProposalClick();"> </td>
            <td>  <INPUT class=cssButton VALUE="������Ϣ" TYPE=button onclick="PolClick();"> </td>
            <td>  <INPUT class=cssButton VALUE="����������Ϣ" TYPE=button onclick="DesPolClick();"> </td>
            -->
        </tr> 
    </table> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></TD>
            <TD class= titleImg> �ͻ���Ϣ </TD>
        </TR>
    </Table>      
    <Div  id= "divLDPerson1" style= "display: ''" align= center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanPersonGrid" ></span> </TD>
            </TR>
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

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
