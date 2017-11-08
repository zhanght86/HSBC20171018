<%
//**************************************************************************************************
//Name��LLClaimPrepayInput.jsp
//Function����Ԥ������������
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
	String tClmNo=request.getParameter("ClmNo"); //�ⰸ��
	String tCustomerNo=request.getParameter("CustomerNo"); //�������      
	String tActivityID=request.getParameter("ActivityID"); //�ID
	String tMissionID =request.getParameter("MissionID");  //����������ID
	String tSubMissionID =request.getParameter("SubMissionID");  //������������ID	
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPrepay.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimPrepayInit.jsp"%>
    <title> Ԥ������</title>
</head>
<body  onload="initForm();" >
<form  method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimDetailGrid);"></td>
            <td class= titleImg> ����������Ϣ�б� </td>
        </tr>
    </table>
    <Div id= "divLLClaimDetailGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanLLClaimDetailGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>   
   
    </div>    

	<hr>
	    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLPrepayDetailInfo);"></td>
            <td class= titleImg> �����Ԥ�����Ĵ��� </td>
        </tr>
    </table>
   <Div id= "divLLPrepayDetailInfo" style= "display: ''" align = center>

        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLPrepayDetailGrid" ></span> </TD>
            </TR>
        </Table>
        <Table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick=" turnPage2.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick=" turnPage2.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick=" turnPage2.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick=" turnPage2.lastPage();"></td>
            </tr>
        </Table>
    </Div>    
    <table >
        <TR class= common>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayAdd" disabled VALUE="��  ��" onclick="LLPrepayDetailAdd();"></TD>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayCancel" disabled VALUE="ȡ  ��" onclick="LLPrepayDetailCancel();"></TD>
        </TR>
    </Table>      
   <Div id= "divLLPrepayDetail" style= "display: 'none'" align = center>
		<table class= common>
		    <TR class= common>
	            <TD class= title> ����Ԥ����� </TD>
	            <TD class= input><Input class= common name="PrepaySum" ></TD>		        
	            <TD class= title>֧����ʽ</TD>
	            <td class= input><Input class=codeno readonly=true name="CasePayMode" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);"><input class=codename name="CasePayModeName" readonly=true></TD>
	            <TD class= title></TD>
	            <TD class= input></TD>
		    </TR>
		     <TR class= common>
            	<TD> <Input TYPE=button class=cssButton VALUE="��  ��" onclick="LLPrepayDetailSave();"></TD>
            </TR>
		</table>
   </Div>      
	<hr>
	<Table>
        <TR>
            <TD> <Input TYPE=button class=cssButton name="Bnf" VALUE="�����˷���" onclick="showBnf();"></TD>        	
            <TD> <Input TYPE=button class=cssButton name="PrepayCofirm" VALUE="Ԥ��ȷ��" onclick="LLClaimPrepayCofirm();"></TD>
            <TD> <Input TYPE=button class=cssButton VALUE="��   ��" onclick="Return();"></TD>  
            <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
            <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>                  
        </TR>
    </Table>
	
    <!--//�������������ر���-->
    <Input type=hidden name="Operator" >    <!--//��ǰ������-->
    <Input type=hidden name="ComCode" >     <!--//��ǰ����-->
    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->
    <Input type=hidden name="PrepayNo" >     <!--//Ԥ�����κ�,ÿ�ν���Ԥ��ҳ���ʼ��ʱ���ɣ��ڴ��������һֱ����-->
    <!--//������Ӧ�� ����������Ϣ�б������ѡť�ĺ���ʱ���������-->
	<input type=hidden id="ClmNo"   name= "ClmNo">
	<input type=hidden id="CaseNo" name= "CaseNo">    
	<input type=hidden id="PolNo"   name= "PolNo">
	<input type=hidden id="GetDutyKind" name= "GetDutyKind">
	<input type=hidden id="GetDutyCode" name= "GetDutyCode">
	<input type=hidden id="CaseRelaNo" name= "CaseRelaNo">
	<input type=hidden id="GrpContNo"   name= "GrpContNo">
	<input type=hidden id="GrpPolNo" name= "GrpPolNo">    
	<input type=hidden id="ContNo"   name= "ContNo">
	<input type=hidden id="KindCode" name= "KindCode">
	<input type=hidden id="RiskCode" name= "RiskCode">
	<input type=hidden id="RiskVer" name= "RiskVer">    
	<input type=hidden id="DutyCode" name= "DutyCode">    
	
    <!--//������ҳ���������,���ڡ�Ԥ��ȷ�ϡ�ʱ��ѯ�ڵ�����,Ϊ�����½ڵ�׼������-->  
    
	<input type=hidden id="tRptNo"   name= "tRptNo">
	<input type=hidden id="tRptorState"   name= "tRptorState">
	<input type=hidden id="tCustomerNo"   name= "tCustomerNo">
	<input type=hidden id="tCustomerName"   name= "tCustomerName">
	<input type=hidden id="tCustomerSex"   name= "tCustomerSex">
	<input type=hidden id="tAccDate"   name= "tAccDate">
	<input type=hidden id="tRgtClass"   name= "tRgtClass">
	<input type=hidden id="tRgtObj"   name= "tRgtObj">
	<input type=hidden id="tRgtObjNo"   name= "tRgtObjNo">
	<input type=hidden id="tPopedom"   name= "tPopedom">
	<input type=hidden id="tPrepayFlag"   name= "tPrepayFlag">
	<input type=hidden id="tAuditer"   name= "tAuditer">	
	<input type=hidden id="tComeWhere"   name= "tComeWhere">
	<input type=hidden id="tMngCom"   name= "tMngCom">
	<Input type=hidden id="tComFlag" name= "tComFlag">     <!--//Ȩ�޿缶��־-->
	<input type=hidden id="tMissionID"   name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>	
