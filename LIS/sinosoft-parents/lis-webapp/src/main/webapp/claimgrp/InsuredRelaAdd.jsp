<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="InsuredRelaAddInit.jsp"%>
<SCRIPT src="InsuredRelaAdd.js"></SCRIPT>
<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
loggerDebug("InsuredRelaAdd","ɨ��¼��");
%>
<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
<SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
<%
}
%>
</head>
<body  onload="initForm();">
<form name=fm id=fm target="fraSubmit">
	<!-- ��ͬ��Ϣ���� GroupPolSave.jsp-->
	<DIV id=DivLCContButton STYLE="display:''">
		<table id="table1">
			<tr>
				<td class="common">
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
				</td>
				<td class="titleImg">�����ͬ��Ϣ</td>
			</tr>
		</table>
	</DIV>
	<Div id="divGroupPol1" style="display: ''" class="maxbox">
		<table border="0" class=common>
			<tr class=common>
				<!--TD class=title>
					����Ͷ��������
				</td>
				<td class=input>
					<input class="common" name=ProposalGrpContNo readonly TABINDEX="-1"  MAXLENGTH="40">
				</TD-->
				<td class="title">Ͷ��������</td>
				<td class="input"><input class="common wid" name=PrtNo id=PrtNo elementtype=nacessary TABINDEX="-1" MAXLENGTH="14" verify="Ͷ��������|notnull&len<=14" readonly=true></td>
			    <td class="title">�ʱ�����</td>
				<td class="input"><input class="common wid" name="ReportNo" id="ReportNo" readonly=true></td>
			    <TD  class= title>��������</TD>
                <TD  class= input><Input class="codeno wid" name=SaleChnl id=SaleChnl elementtype=nacessary verify="��������|notnull" readonly=true><!--<input name=SaleChnlName class=codename readonly=true></TD>-->
			</tr>
			<tr class=common>
				<TD  class= title>�н����</TD>
                <TD  class= input><Input class="code8 wid" name=AgentCom id=AgentCom readonly=true);"></TD>
			  	<td class=title>Ͷ����������</td>
				<td class=input><!--<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=PolApplyDate verify="Ͷ����������|notnull&DATE verifyorder="1"" readonly=true>-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDate'});" onblur="checkapplydate();"  verify="Ͷ����������|notnull&DATE verifyorder="1"" readonly=true dateFormat="short" name=PolApplyDate id="PolApplyDate"><span class="icon"><a onClick="laydate({elem: '#PolApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td>
                <td class=title>������Ч����</td>
				<td class=input><!--<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=CValiDate verify="������Ч����|notnull&DATE verifyorder="1"" readonly=true>-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#CValiDate'});" onblur="checkapplydate();"  verify="������Ч����|notnull&DATE verifyorder="1"" readonly=true dateFormat="short" name=CValiDate id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td>
			</tr> 
			<tr class=common>
                <td CLASS="title">�������</td>
		        <td CLASS="input"><Input name=ManageCom class='codeno wid' id="ManageCom" readonly=true><!--<input name=ManageComName class=codename readonly=true>--></td>
	            <td class="title">�����շ�����</td>
				<td class="input"><input class="common wid" name="PayDate" id="PayDate" readonly></td>
			</tr>
       <!-- </table>
        <hr class="line">
        <table class="common">-->
  		    <tr class="common">
			    <td class="title">ҵ��Ա����</td>
			    <td class="input"><input NAME="AgentCode" id="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code wid" elementtype=nacessary readonly></td>
			    <td class="title">ҵ��Ա����</td>
			    <td CLASS="input"><input NAME="AgentName" id="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary  verifyorder="1" readonly=true></td>
			    <td CLASS="title">��������</td>
			    <td CLASS="input"><input NAME="AgentManageCom" id="AgentManageCom" readonly verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno wid" readonly=true verify="�������|code:station&amp;notnull"><!--<input name="AppntManageComName" elementtype=nacessary CLASS="codename" readonly>--></td>
		    </tr>
		    <tr class="common">
			    <td CLASS="title">�����ֲ�</td>
			    <td CLASS="input"><input NAME="BranchAttr" id="BranchAttr"  verifyorder="1" VALUE CLASS="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="ҵ��ԱӪҵ����Ӫҵ��|notnull"><input NAME="AgentGroup" type="hidden" value=""></td>
                <td class="title">�Ǽ�ҵ��Ա</td>
                <td class="input"><input class="codeno wid" readonly name="starAgent" id="starAgent"><!--<input class="codename" name="starAgentName" readonly>--></td>
                <td class="title">��ҵ��Ա���빴ѡ</td>
                <td class="title"><input type="checkbox" name="multiagentflag" value="true" onclick="haveMultiAgent();"></td>
            </tr>
        </table>
        <div id="DivMultiAgent" style="display:'none'">
            <table>
                <tr>
                    <td class=common>
                        <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
                    </td>
                    <td class= titleImg>����ҵ��Ա��Ϣ</td>
                </tr>
            </table>
            <div id="divMultiAgent1" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td style="text-align:left" colSpan="1">
                            <span id="spanMultiAgentGrid"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
	</Div>
    
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol2);"></td>
			<td class=titleImg>Ͷ����λ���ϣ��ͻ��� <input class="wid" class=common name=GrpNo id=GrpNo readonly=true>��</td>
		</tr>
	</table>
	<Div id="divGroupPol2" style="display: ''">
		<table class=common>
			<tr>
				<td class=title>��λ����</td>
				<td class=input><input class="wid" class=common name=GrpName id=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="��λ����|notnull&len<=60" readonly=true></td>
				<td class=title>�ʲ��ܶ�(Ԫ)</td>
				<td class=input><input class="wid" class=common name=Asset id=Asset verify="�ʲ��ܶ�|num&len<=17" readonly=true></td>
				<td class=title>��λ����</td>
				<td class=input><input class="wid" class=codeno name=GrpNature id=GrpNature verify="��λ����|notnull&code:grpNature&len<=10" readonly=true><!--<input class=codename name=GrpNatureName readonly=true elementtype=nacessary>--></td>
			</tr>
			<tr class=common>
				<td class=title>��ҵ���</td>
				<td class=input><input class="wid" class=codeno name=BusinessType id=BusinessType verify="��ҵ���|notnull&code:BusinessType&len<=20" readonly=true><!--<input class=codename name=BusinessTypeName readonly=true elementtype=nacessary>--></td>
				<td class=title>Ա������</td>
				<td class=input><input class="wid" class=common name=Peoples id=Peoples  elementtype=nacessary verify="��λ������|notnull&int" readonly=true></td>
				<td class=title>��λ����</td>
				<td class=input><input class="wid" class=common name=Fax id=Fax readonly=true></td>
			</tr>
			<tr>
				<td class=title>�籣�Ǽ�֤����</td>
				<td class=input><input class="wid" class=common name=BankAccNo1 id=BankAccNo1 readonly=true></td>
                <td class=title>��λ��ַ����</td>
				<td class=input><input class="wid" class="code" name="GrpAddressNo" id="GrpAddressNo" readonly=true></td>
                <td class=title>��λ��ַ</td>
				<td class=input><input class="wid" class="common3" name=GrpAddress id=GrpAddress elementtype=nacessary verify="��λ��ַ|notnull&len<=60" readonly=true></td>
			</tr>
			
			<tr class=common>
				
				<td class=title>��������</td>
				<td class=input><input class="wid" class=common name=GrpZipCode id=GrpZipCode maxlength=6 elementtype=nacessary verify="��������|notnull&zipcode" readonly=true></td>
                <td class=title>������ϵ��һ</td><td class=input></td>
                <td class=title>��  ��</td>
				<td class=input><input class="wid" class=common name=LinkMan1 id=LinkMan1 elementtype=nacessary verify="������ϵ��һ����|notnull&len<=10" readonly=true></td>
			</tr>
			
			<tr class=common>
				
				<td class=title>��ϵ�绰</td>
				<td class=input><input class="wid" class=common name=Phone1 id=Phone1 elementtype=nacessary verify="������ϵ��һ��ϵ�绰|notnull&len<=30" readonly=true></td>
				<td class=title>E-MAIL</td>
				<td class=input><input class="wid" class=common name=E_Mail1 id=E_Mail1 verify="������ϵ��һE-MAIL|len<=60&Email" readonly=true></td>
                <td class=title>��  ��</td>
				<td class=input colspan=3><input class="wid" class=common3 name=Department1 id=Department1 verify="������ϵ��һ����|len<=30" readonly=true></td>
			</tr>
			<tr class=common>
				
			</tr>
			<tr class=common>
				<td class=title>������ϵ�˶�</td><td class=input></td>
                <td class=title>��  ��</td>
				<td class=input><input class="wid" class=common name=LinkMan2 id=LinkMan2 verify="������ϵ�˶�����|len<=10" readonly=true></td>
				<td class=title>��ϵ�绰</td>
				<td class=input><input class="wid" class=common name=Phone2 id=Phone2 verify="������ϵ�˶���ϵ�绰|NUM&len<=30" readonly=true></td>
			</tr>
			<tr class=common>
				
				<td class=title>E-MAIL</td>
				<td class=input><input class="wid" class=common name=E_Mail2 id=E_Mail2 verify="������ϵ�˶�E-MAIL|len<=60&Email" readonly=true></td>
                <td class=title>��  ��</td>
				<td class=input><input class="wid" class=common3 name=Department2 id=Department2 verify="������ϵ�˶�����|len<=30" readonly=true></td>
                <td class=title>���ʽ</td>
				<td class=input><input class="wid" class=codeno name=GetFlag id=GetFlag readonly=true><!--<input class=codename name=GetFlagName readonly=true >--></td>
			</tr>
			
			<tr class=common>
				
				<td class=title>��������</td>
				<td class=input><input class="wid" class=codeno name=BankCode id=BankCode verify="��������|code:bank&len<=24" readonly=true><!--<input class=codename name=BankCodeName readonly=true>--></td>
				<td class=title>��  ��</td>
				<td class=input><input class="wid" class=common name=BankAccNo id=BankAccNo verify="�ʺ�|len<=40" readonly=true></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title>��ע</td>
			</tr>
			<tr class=common>
				<td colspan="6" style="padding-left:16px"><textarea name="Remark" cols="224" rows="4" class="common" readonly=true></textarea></td>
			</tr>
			<input type=hidden name=EmployeeRate verify="��Ա�Ը�����|num&len<=5" readonly=true>
			<input type=hidden name=FamilyRate verify="�����Ը�����|num&len<=80" readonly=true>
		</table>
	</Div>
	
	<table>
		<tr>
			<td class=common>
				<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol3);">
			</td>
			<td class=titleImg>���屣��������Ϣ</td>
		</tr>
	</table>
	<Div id="divGroupPol3" style="display: ''">
		<Div id="divGroupPol5" style="display: 'none'" class="maxbox1">
			<table class=common>
				<tr class=common>
					<td class=title>�����ͬ����</td>
					<td class=input><input class="readonly wid" readonly name=GrpContNo id=GrpContNo ></td>
                    <td class=title></td><td class=input></td>
                    <td class=title></td><td class=input></td>
				</tr>
			</table>
		</Div>
		<!--¼����ݽ��ѱ��� -->
		<Table class=common>
			<tr>
				<td text-align: left colSpan=1>
					<span id="spanRiskGrid"></span>
				</td>
			</tr>
		</Table>
		<div id="divGroupPol5" style="display: 'none'">
    		<table>
    			<tr>
    				<td class=common>
    					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol4);">
    				</td>
    				<td class=titleImg>������Ϣ</td>
    			</tr>
    		</table>
	    </div>
		<Div id="divGroupPol4" style="display: 'none'" class="maxbox1">
			<table class=common>
				<tr class=common>
					<td class=title>���ֱ���</td>
						<td class=input>
							<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode readonly=true ondblclick="return showCodeList('RiskGrp',[this,RiskCodeName],[0,1]);" onclick="return showCodeList('RiskGrp',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskGrp',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>
						</td>
						<td class=title>�����ڼ�</td>
						<td class=input>
							<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PayIntv id=PayIntv  readonly=true ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');" onclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');"><input class=codename name=PayIntvName id=PayIntvName readonly=true elementtype=nacessary>
						</td><td class=title></td><td class=input></td>
				</tr>
			</table>				
		</Div>
	</DIV>		
    
	<Div id="divnormalbtn" style="display: ''">
			<input class=cssButton VALUE="���������˲���"  TYPE=button onclick="grpFillList()">
			<input class=cssButton VALUE="���ռƻ��鿴"  TYPE=button onclick="grpRiskPlanInfo()">			
	</Div>
		
	<Div id="divquerybtn" style="display: ''">
		<input VALUE="�����������ѯ"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
		<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
	</Div>
	<input type=hidden name="ContNo" id="ContNo">
	<input type=hidden name="AgentType" id="AgentType">
	<input type=hidden name="ScanType" id="ScanType">
	<input type=hidden name="ProposalGrpContNo" id="ProposalGrpContNo">
</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
