<%
//**************************************************************************************************
//Name��LLClaimPrepayMissInput.jsp
//Function������˹������й���������Ϣ��׼�����ڡ�Ԥ��������
//Author��yuejw
//Date: 2005-7-5 16:00
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
<script>	
	var operator = "<%=tGlobalInput.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGlobalInput.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGlobalInput.ComCode%>"; //��¼��½����
</script>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <SCRIPT src="LLClaimPrepayMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimPrepayMissInit.jsp"%>
    <title> Ԥ������</title>
</head>
<body  onload="initForm();initElementtype();" >
<form  method=post name=fm id=fm target="fraSubmit">
<div id="ClaimPrepayPool"></div>
<div>
	<Input type=hidden id="Operator" name="Operator" >
    <Input type=hidden id="ComCode" name="ComCode" >      
    <Input type=hidden id="tRptNo" name= "tRptNo">     <!--//�ⰸ��-->
    <Input type=hidden id="tRptorState" name= "tRptorState">     <!--//����״̬--> 
    <Input type=hidden id="tCustomerNo" name= "tCustomerNo">     <!--//�����˱���--> 
    <Input type=hidden id="tCustomerName" name= "tCustomerName">    <!--// ����������--> 
    <Input type=hidden id="tCustomerSex" name= "tCustomerSex">    <!--// �������Ա�"t-->
    <Input type=hidden id="tAccDate" name= "tAccDate">     <!--//�¹�����" -->
    <Input type=hidden id="tRgtClass" name= "tRgtClass">     <!--//��������" -->
    <Input type=hidden id="tRgtObj" name= "tRgtObj">     <!--//��������" -->
    <Input type=hidden id="tRgtObjNo" name= "tRgtObjNo">     <!--//��������" -->
    <Input type=hidden id="tPopedom" name= "tPopedom">     <!--//����ʦȨ��"-->
    <Input type=hidden id="tPrepayFlag" name= "tPrepayFlag">     <!--//Ԥ����־"-->
    <Input type=hidden id="tComeWhere" name= "tComeWhere">     <!--//����"-->
    <Input type=hidden id="tAuditer" name= "tAuditer">     <!--//��˲�����"-->
    <Input type=hidden id="tMngCom" name= "tMngCom">     <!--//����"-->
    <Input type=hidden id="tComFlag" name= "tComFlag">     <!--//Ȩ�޿缶��־-->
    <Input type=hidden name=CurDate  id="CurDate">    
	<input type=hidden id="tMissionID" 	 name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">	
</div>
</form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <!-- ����ز��� -->
	<!--
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> �������ѯ������ </TD>
        </TR>
    </Table>   -->

<!-- lzf     <Div  id= "divSearch" style= "display: 'none'">
        <table class= common>
            <TR class= common>
                <TD class= title> �ⰸ�� </TD>
                <TD class= input> <Input class= common name="RptNo" ></TD>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= common name="CustomerNo" ></TD>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= common name="CustomerName" ></TD>
            </TR>
        </table>
    </DIV>
    <!-- 
    <Table>
        <TR>
            <TD> <Input TYPE=button class=cssButton VALUE="��  ѯ" onclick="LLClaimAuditGridQuery();"></TD>
        </TR>
    </Table>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimAudit);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table>  -->
<!-- lzf   <Div id= "divLLClaimAudit" style= "display: 'none'" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimAuditGrid" ></span> </TD>
            </TR>
        </Table>
        <Table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </Table>
    </Div> 
    <hr>
    <Table>
        <TR>
        	<!-- <TD><INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyLLClaimAudit();"></TD> -->
<!-- lzf       </TR>
    </Table>
    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimPrepayGrid);"></td>
            <td class= titleImg> �������� </td>
        </tr>
    </table>
    <Div id= "divLLClaimPrepayGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
  				<td text-align: left colSpan=1 ><span id="spanLLClaimPrepayGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightSelfByRow()"></td>
            </tr>
        </table>        
    </div>    
    <!--//�������������ر���-->
<!--     <Input type=hidden id="Operator" name="Operator" >
    <Input type=hidden id="ComCode" name="ComCode" >  
    
    <Input type=hidden id="tRptNo" name= "tRptNo">     <!--//�ⰸ��-->
<!--     <Input type=hidden id="tRptorState" name= "tRptorState">     <!--//����״̬--> 
<!--     <Input type=hidden id="tCustomerNo" name= "tCustomerNo">     <!--//�����˱���--> 
 <!--    <Input type=hidden id="tCustomerName" name= "tCustomerName">    <!--// ����������--> 
<!--     <Input type=hidden id="tCustomerSex" name= "tCustomerSex">    <!--// �������Ա�"t-->
<!--     <Input type=hidden id="tAccDate" name= "tAccDate">     <!--//�¹�����" -->
<!--     <Input type=hidden id="tRgtClass" name= "tRgtClass">     <!--//��������" -->
<!--     <Input type=hidden id="tRgtObj" name= "tRgtObj">     <!--//��������" -->
<!--     <Input type=hidden id="tRgtObjNo" name= "tRgtObjNo">     <!--//��������" -->
<!--     <Input type=hidden id="tPopedom" name= "tPopedom">     <!--//����ʦȨ��"-->
<!--     <Input type=hidden id="tPrepayFlag" name= "tPrepayFlag">     <!--//Ԥ����־"-->
<!--     <Input type=hidden id="tComeWhere" name= "tComeWhere">     <!--//����"-->
<!--     <Input type=hidden id="tAuditer" name= "tAuditer">     <!--//��˲�����"-->
<!--     <Input type=hidden id="tMngCom" name= "tMngCom">     <!--//����"-->
<!--     <Input type=hidden id="tComFlag" name= "tComFlag">     <!--//Ȩ�޿缶��־-->
<!--     <Input type=hidden name=CurDate  id="CurDate">
    
	<input type=hidden id="tMissionID" 	 name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">
	
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  -->
</body>
</html>
