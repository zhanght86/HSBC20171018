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
<%@page import = "com.sinosoft.lis.claim.*"%>
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
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="./LLClaimExempt.js"></SCRIPT>
    <%@include file="LLClaimExemptInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimExemptGrid);"></TD>
            <TD class= titleImg> ��ȡ������Ϣ </TD>
        </TR>
    </Table>
   <Div id= "divLLClaimExemptGrid" style= "display: ''">
        <Table  class= common>
        	<!--<TR>
        		  <TD><input TYPE=button class=cssButton name="exemptquery" value="��ȡ������Ϣ" onclick="LLExemptQueryClick();"></TD>
			</TR>-->
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
    <br>
    <a href="javascript:void(0);" name="exemptquery" id="exemptquery" class="button" onClick="LLExemptQueryClick();">��ȡ������Ϣ</a>
    <Div id= "divLLClaimExempt" style= "display: 'none'">
        
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimExemptInfo);"></TD>
            <TD class= titleImg>�����¼��ϸ��Ϣ </TD>
        </TR>
    </Table>
   	<Div id= "divLLClaimExemptInfo" style= "display: ''" class="maxbox">
		<TABLE class=common>
			<tr class=common>
				<td class=title> �߽ɱ�� </td>
				<!--<td class= input><input class="readonly" readonly  name="UrgePayFlag"></td>-->
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="UrgePayFlag" id="UrgePayFlag" CodeData="0|3^Y|�߽�^N|���߽�" ondblclick="return showCodeListEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200)"  onclick="return showCodeListEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200)" onkeyup="return showCodeListKeyEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200);"><input class=codename name=UrgePayFlagName id=UrgePayFlagName readonly></TD>
				<td class=title> �Ƿ���˻���� </td>
				<td class= input><input class="readonly wid" readonly  name="NeedAcc" id="NeedAcc"></td>
				<td class=title> �ѽ��Ѵ��� </td>
				<td class= input><input class="readonly wid" readonly  name="PayTimes" id="PayTimes"></td>                
			</tr>
			<tr class=common>
				<td class=title> ���ѷ������ </td>
				<td class= input><input class="readonly wid" readonly  name="Rate" id="Rate"></td>
				<td class=title> ������ </td>
				<td class= input><input class="readonly wid" readonly  name="PayStartDate" id="PayStartDate"></td>
				<td class=title> �ս����� </td>
				<td class= input><input class="readonly wid" readonly  name="PayEndDate" id="PayEndDate"></td>                
			</tr>
			<tr class=common>
				<td class=title> ÿ�ڱ��� </td>
				<td class= input><input class="readonly wid" readonly  name="StandPrem" id="StandPrem"></td>
				<td class=title> ʵ�ʱ��� </td>
				<td class= input><input class="readonly wid" readonly  name="Prem" id="Prem"></td>
				<td class=title> �ۼƱ��� </td>
				<td class= input><input class="readonly wid" readonly  name="SumPrem" id="SumPrem"></td>                
			</tr>	
			<tr class=common>
				<td class=title> �������� </td>
				<td class= input><input class="readonly wid" readonly  name="PaytoDate" id="PaytoDate"></td>
				<td class=title> ���Ѽ�� </td>
				<!--<td class= input><input class="readonly" readonly  name="PayIntv"></td>-->
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="PayIntv" id="PayIntv" CodeData="0|3^-1|�����ڽɷ�^0|����^1|�½�^1|����^6|�����^12|���^36|�����" ondblclick="return showCodeListEx('PayIntv', [this,PayIntvName],[0,1],'','','','',200)"  onclick="return showCodeListEx('PayIntv', [this,PayIntvName],[0,1],'','','','',200)" onkeyup="return showCodeListKeyEx('PayIntv', [this,],PayIntvName[0,1],'','','','',200);"><input class=codename name=PayIntvName id=PayIntvName readonly ></TD>			
				<td class=title> ����������� </td>
				<td class= input><input class="readonly wid" readonly  name="SuppRiskScore" id="SuppRiskScore"></td>                
			</tr>
			<tr class=common>			
		        <TD  class= title>�⽻��־</TD>
                <!--<TD  class= input><input class=common name="FreeFlag"></TD>-->
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="FreeFlag" id="FreeFlag" CodeData="0|3^0|���⽻^1|�⽻" ondblclick="return showCodeListEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100)"  onclick="return showCodeListEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100)" onkeyup="return showCodeListKeyEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100);"onfocus="FreeFlagClick();"><input class=codename name=FreeFlagName id=FreeFlagName readonly ></TD>
                <TD  class= title>״̬</TD>
                <!--<TD  class= input><input class=common name="State"></TD>-->               
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="State" id="State" CodeData="0|3^0|�б�ʱ�ı�����^1|�б�ʱ�ļӷ���^2|������Ŀ�ӷ���^3|ǰ���β�ͨ�����µļӷ�" ondblclick="return showCodeListEx('State', [this,StateName],[0,1],'','','','',200)"  onclick="return showCodeListEx('State', [this,StateName],[0,1],'','','','',200)" onkeyup="return showCodeListKeyEx('State', [this,StateName],[0,1],'','','','',200);"><input class=codename name=StateName id=StateName readonly ></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>   
                
		</Table>
	</Div>		
	<Div id= "divLLClaimExemptFreeInfo" style= "display: 'none'">
		<Table class=common>
			<tr class=common>
	            <TD class= title>����ԭ��</TD>
				<td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ExemptReason id=ExemptReason ondblclick="return showCodeList('llexemptreason',[this,ExemptReasonname],[0,1]);" onclick="return showCodeList('llexemptreason',[this,ExemptReasonname],[0,1]);" onkeyup="return showCodeListKey('llexemptreason',[this,ExemptReasonname],[0,1]);"><Input class=codename name=ExemptReasonname id=ExemptReasonname readonly ></td>
		        <TD class= title>�⽻����</TD>
	            <TD class= input><!--<input class="coolDatePicker" dateFormat="short" name="FreeStartDate"  >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#FreeStartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=FreeStartDate id="FreeStartDate"><span class="icon"><a onClick="laydate({elem: '#FreeStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
	            <TD class= title>�⽻ֹ��</TD>
	            <TD class= input><!--<input class="coolDatePicker" dateFormat="short" name="FreeEndDate"  >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#FreeEndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=FreeEndDate id="FreeEndDate"><span class="icon"><a onClick="laydate({elem: '#FreeEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
	        </tr>
            <tr class=common>
                <td class=title>��������</td>
                <td class= input><input class="readonly wid" readonly  name="ExemptPeriod" id="ExemptPeriod"></td>
                <td class=title>�����ܱ���</td>
                <td class= input><input class="readonly wid" readonly  name="ExemptSumAmnnt" id="ExemptSumAmnnt"></td>                                
                <TD class= title></TD>
                <TD class= input></TD>
            </tr>
                        
        </Table>
        <Table class= common>
            <tr class= common>           	               
                <td class= title> �������� </td>
            </tr> 
            <tr class= common>                  
                <td colspan="6" style="padding-left:16px"><textarea name="ExemptDesc" cols="224" rows="4" witdh=25% class="common" ></textarea></td>
            </tr>
		</Table>
	</Div>			
	<!--<Table>
		<TR>
			<TD><input class=cssButton value="��  ��" type=button onclick="LLExemptSaveClick();"></TD>
			<TD><input class=cssButton value="��  ��" type=button onclick="top.close();"></TD>
		</TR>
	</Table> -->

    </Div>
    <br>
    <a href="javascript:void(0);" class="button" onClick="LLExemptSaveClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">��    ��</a>
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
