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
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimScanInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimScanInputInit.jsp"%>
    <title>��֤��ɨ </title>
</head>
<body  onload="initForm();" >
<form action="./LLClaimScanInputSave.jsp"  method=post name=fm id=fm target="fraSubmit">
	
    <!--��֤��ɨ��¼�벿�� -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> �����뵥֤��ɨ�����ݣ� </TD>
        </TR>
    </Table>    

    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title> ��֤���� </TD>
                <TD class= input> <Input class="wid" class= common name="BussNo" id="BussNo" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD class= title> ҵ������ </TD>
                <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="BussType" id="BussType" ondblclick="return showCodeList('BussTypeDetail',[this,BussTypeName],[0,1]);" onclick="return showCodeList('BussTypeDetail',[this,BussTypeName],[0,1]);" onkeyup="return showCodeListKey('SubTypeDetail',[this,BussTypeName],[0,1]);"><input class=codename name=BussTypeName id=BussTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD class= title> ��֤���� </TD>
                <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="SubType" id="SubType" ondblclick="return showCodeList('SubTypeDetail',[this,SubTypeName],[0,1],null,fm.BussType.value,'busstype','1','200');" onclick="return showCodeList('SubTypeDetail',[this,SubTypeName],[0,1],null,fm.BussType.value,'busstype','1','200');" onkeyup="return showCodeListKey('SubTypeDetail',[this,SubTypeName],[0,1],null,fm.BussType.value,'busstype','1','200');" ><input class=codename name=SubTypeName id=SubTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
               
        </table>
        <table class= common>           
           <TR class= common>     
                <TD class=title5> �������� <font size=1 color='#ff0000'><b>*</b></font></TD>
           </TR>
           <TR class= common>    
             <TD colspan="6" style="padding-left:16px">
               <textarea   name="IssueDesc" verify="��������|len<800" verifyorder="1" cols="199" rows="4" class="common" ></textarea>
             </TD>
           </TR>   
        </table>

    <Div  id= "divSearch2" style= "display:none">
    	<table class= common>
            <TR class= common>
                <TD class= title5> ҵ��������� </TD>
                <TD class= input5> <Input class="wid" class= common name="BussNoType" id="BussNoType" value="23" ></TD>
                <TD class= title5> ����״̬ </TD>
                <TD class= input5> <Input class="wid" class= common name="Status" id="Status"  value="0" ></TD>
           </TR>
           </TR>   
        </table>    	 
    </DIV>       </DIV> 
<!--<Input TYPE=button class=cssButton VALUE="��  ��" onclick="submitForm();">-->
<a href="javascript:void(0);" class="button" onClick="submitForm();">��    ��</a>
    <!--//�������������ر���-->
    <Input type=hidden id="Operator" name="Operator" >
    <Input type=hidden id="ComCode" name="ComCode" >  
    
	<input type=hidden id="tMissionID" 	 name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">
	
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
