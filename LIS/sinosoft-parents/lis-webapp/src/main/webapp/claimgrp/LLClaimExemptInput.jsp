<%
//�������ƣ�LLClaimExemptinput.jsp
//�����ܣ����⴦��
//�������ڣ�2005-07-19
//������  ��yuejw
//���¼�¼��
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI"); 
	  String tClmNo = request.getParameter("ClaimNo");	//�ⰸ��
%>
    <title>���⴦�� </title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/Verifyinput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="./LLClaimExempt.js"></SCRIPT>
    <%@include file="LLClaimExemptInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimExemptGrid);"></TD>
            <TD class= titleImg> ��ȡ������Ϣ </TD>
        </TR>
    </Table>
   <Div id= "divLLClaimExemptGrid" style= "display: ''" align = center>
        <Table  class= common>
        	<TR>
        		  <TD><input TYPE=button class=cssButton name="exemptquery" value="��ȡ������Ϣ" onclick="LLExemptQueryClick();"></TD>
			</TR>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimExemptGrid" ></span> </TD>
            </TR>
        </Table>
<!--
        <Table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="lastPage();"></td>
            </tr>
        </Table>
-->        
    </Div> 
    <hr>
    <Div id= "divLLClaimExempt" style= "display: 'none'">
        
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimExemptInfo);"></TD>
            <TD class= titleImg>�����¼��ϸ��Ϣ </TD>
        </TR>
    </Table>
   	<Div id= "divLLClaimExemptInfo" style= "display: ''">
		<TABLE class=common>
			<tr class=common>
				<td class=title> �߽ɱ�� </td>
				<!--<td class= input><input class="readonly" readonly  name="UrgePayFlag"></td>-->
                <TD  class= input><input class=codeno disabled name="UrgePayFlag" CodeData="0|3^Y|�߽�^N|���߽�" ondblclick="return showCodeListEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200)"onkeyup="return showCodeListKeyEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200);"><input class=codename name=UrgePayFlagName readonly></TD>
				<td class=title> �Ƿ���˻���� </td>
				<td class= input><input class="readonly" readonly  name="NeedAcc"></td>
				<td class=title> �ѽ��Ѵ��� </td>
				<td class= input><input class="readonly" readonly  name="PayTimes"></td>                
			</tr>
			<tr class=common>
				<td class=title> ���ѷ������ </td>
				<td class= input><input class="readonly" readonly  name="Rate"></td>
				<td class=title> ������ </td>
				<td class= input><input class="readonly" readonly  name="PayStartDate"></td>
				<td class=title> �ս����� </td>
				<td class= input><input class="readonly" readonly  name="PayEndDate"></td>                
			</tr>
			<tr class=common>
				<td class=title> ÿ�ڱ��� </td>
				<td class= input><input class="readonly" readonly  name="StandPrem"></td>
				<td class=title> ʵ�ʱ��� </td>
				<td class= input><input class="readonly" readonly  name="Prem"></td>
				<td class=title> �ۼƱ��� </td>
				<td class= input><input class="readonly" readonly  name="SumPrem"></td>                
			</tr>	
			<tr class=common>
				<td class=title> �������� </td>
				<td class= input><input class="readonly" readonly  name="PaytoDate"></td>
				<td class=title> ���Ѽ�� </td>
				<!--<td class= input><input class="readonly" readonly  name="PayIntv"></td>-->
                <TD  class= input><input class=codeno disabled name="PayIntv" CodeData="0|3^-1|�����ڽɷ�^0|����^1|�½�^1|����^6|�����^12|���^36|�����" ondblclick="return showCodeListEx('PayIntv', [this,PayIntvName],[0,1],'','','','',200)"onkeyup="return showCodeListKeyEx('PayIntv', [this,],PayIntvName[0,1],'','','','',200);"><input class=codename name=PayIntvName readonly ></TD>			
				<td class=title> ����������� </td>
				<td class= input><input class="readonly" readonly  name="SuppRiskScore"></td>                
			</tr>
			<tr class=common>			
		        <TD  class= title>�⽻��־</TD>
                <!--<TD  class= input><input class=common name="FreeFlag"></TD>-->
                <TD  class= input><input class=codeno name="FreeFlag" CodeData="0|3^0|���⽻^1|�⽻" ondblclick="return showCodeListEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100);"onfocus="FreeFlagClick();"><input class=codename name=FreeFlagName readonly ></TD>
                <TD  class= title>״̬</TD>
                <!--<TD  class= input><input class=common name="State"></TD>-->               
                <TD  class= input><input class=codeno disabled name="State" CodeData="0|3^0|�б�ʱ�ı�����^1|�б�ʱ�ļӷ���^2|������Ŀ�ӷ���^3|ǰ���β�ͨ�����µļӷ�" ondblclick="return showCodeListEx('State', [this,StateName],[0,1],'','','','',200)"onkeyup="return showCodeListKeyEx('State', [this,StateName],[0,1],'','','','',200);"><input class=codename name=StateName readonly ></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>   
                
		</Table>
	</Div>		
	<Div id= "divLLClaimExemptFreeInfo" style= "display: 'none'">
		<Table class=common>
			<tr class=common>
	            <TD class= title>����ԭ��</TD>
				<td class=input><Input class=codeno readonly name=ExemptReason ondblclick="return showCodeList('llexemptreason',[this,ExemptReasonname],[0,1]);" onkeyup="return showCodeListKey('llexemptreason',[this,ExemptReasonname],[0,1]);"><Input class=codename name=ExemptReasonname readonly ></td>
		        <TD class= title>�⽻����</TD>
	            <TD class= input><input class="coolDatePicker" dateFormat="short" name="FreeStartDate"  ></TD>
	            <TD class= title>�⽻ֹ��</TD>
	            <TD class= input><input class="coolDatePicker" dateFormat="short" name="FreeEndDate"  ></TD>
	        </tr>
            <tr class=common>
                <td class=title>��������</td>
                <td class= input><input class="readonly" readonly  name="ExemptPeriod"></td>
                <td class=title>�����ܱ���</td>
                <td class= input><input class="readonly" readonly  name="ExemptSumAmnnt"></td>                                
                <TD class= title></TD>
                <TD class= input></TD>
            </tr>
                        
        </Table>
        <Table class= common>
            <tr class= common>           	               
                <td class= title> �������� </td>
            </tr> 
            <tr class= common>                  
                <td class= input><textarea name="ExemptDesc" cols="100" rows="3" witdh=25% class="common" ></textarea></td>
            </tr>
		</Table>
	</Div>			
	<hr>
	<Table>
		<TR>
			<TD><input class=cssButton value="��  ��" type=button onclick="LLExemptSaveClick();"></TD>
			<TD><input class=cssButton value="��  ��" type=button onclick="top.close();"></TD>
		</TR>
	</Table> 

    </Div>
    
    <!--���ر�����-->
	<input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->  
	<input type=hidden id="PolNo" name="PolNo"><!--�������ֺ���-->  
	<input type=hidden id="DutyCode" name="DutyCode"><!--���α���-->  
	<input type=hidden id="PayPlanCode" name="PayPlanCode"><!--���Ѽƻ�����-->  
	<input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->
	<input type=hidden id="ManageCom" name="ManageCom"><!--����-->  
	
	<!--<TD class= title>�⽻����</TD>--����---->
	<TD class= input><input type=hidden class=codeno name="FreeRate" CodeData="0|3^0|ȫ��^1|���⽻" ondblclick="return showCodeListEx('FreeRate', [this,FreeRateName],[0,1])"onkeyup="return showCodeListKeyEx('FreeRate', [this,],FreeRateName[0,1]);"><input type=hidden class=codename name=FreeRateName readonly ></TD>            					
	
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>			
</body>
</html>
