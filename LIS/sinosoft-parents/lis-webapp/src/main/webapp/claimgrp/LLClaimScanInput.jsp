<%
//**************************************************************************************************
//Name��LLClaimScanInput.jsp
//Function����֤�Ĳ�ɨ
//Author��zw
//Date: 2005-8-26 16:00
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
    <SCRIPT src="LLClaimScanInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimScanInputInit.jsp"%>
    <title>��֤��ɨ </title>
</head>
<body  onload="initForm();" >
<form action="./LLClaimScanInputSave.jsp"  method=post name=fm target="fraSubmit">
	
    <!--��֤��ɨ��¼�벿�� -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> �����뵥֤��ɨ�����ݣ� </TD>
        </TR>
    </Table>    

    <Div  id= "divSearch" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> �ⰸ���� </TD>
                <TD class= input> <Input class= common name="BussNo" ></TD>
                <TD class= title> ��֤�������� </TD>
                <TD class= input> <Input class=codeno name="SubType" ondblclick="return showCodeList('SubTypeDetail',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('SubTypeDetail',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
           			<TD class= title> </TD>
                <TD class= input> </TD>
           </TR>
           </TR>   
        </table>
        <table class= common>           
           <TR class= common>     
                <TD class= title> �������� </TD>
                <TD class= common> 
                <textarea name="IssueDesc" verify="��������|len<800" verifyorder="1" cols="110" rows="5" class="common" >
                </textarea>		
                </TD>
           </TR>   
        </table>
    </DIV>
    <Div  id= "divSearch" style= "display:'none'">
    	<table class= common>
            <TR class= common>
                <TD class= title> ҵ��������� </TD>
                <TD class= input> <Input class= common name="BussNoType" value="23" ></TD>
                <TD class= title> ҵ������ </TD>
                <TD class= input> <Input class= common name="BussType"  value="LP" ></TD>
                <TD class= title> ����״̬ </TD>
                <TD class= input> <Input class= common name="Status"  value="0" ></TD>
           </TR>
           </TR>   
        </table>    	 
    </DIV>    
    
    <hr>
    <Table>
        <TR>
            <TD> <Input TYPE=button class=cssButton VALUE="��  ��" onclick="submitForm();"></TD>
        </TR>
    </Table>
    
    <!--//�������������ر���-->
    <Input type=hidden id="Operator" name="Operator" >
    <Input type=hidden id="ComCode" name="ComCode" >  
    
	<input type=hidden id="tMissionID" 	 name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">
	
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
