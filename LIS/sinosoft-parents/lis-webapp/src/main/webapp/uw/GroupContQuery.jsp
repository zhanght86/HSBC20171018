<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�GroupContQuery.jsp.
//�����ܣ��ŵ��¸����˹��˱�
//�������ڣ�2003-12-29 11:10:36
//������ ��sxy  zhangrong
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupContQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupContQueryInit.jsp"%>
</head>
<body onload="initForm('<%=tGrpContNo%>');">
	<form method=post id="fm" name=fm target="fraSubmit">
	  <!-- �ŵ��¸��˵���ѯ���� -->
		<div id="divSearch">
			<table class=common border=0 width=100%>
				<tr>
					<td class=titleImg align=center>�������ѯ������</td>
				</tr>
			</table>
			<div class="maxbox1">
			<table class=common align=center>
				<TR class=common>
					<TD class=title>��ͬ���� </TD>
					<TD class=input><Input class="common wid" id="QContNo" name=QContNo TABINDEX="-1"  MAXLENGTH="40"></TD>
					<TD class=title>����������</TD>
					<TD class=input><Input class="common wid" id="QInsuredName" name=QInsuredName></TD>
					<TD class=title>������� </TD>
					<TD class=input>
						<Input class="code wid" id="QManageCom" name=QManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<!--Input class="code" name=GUWState CodeData= "0|^1|�ܱ�^4|ͨ�ڳб�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����" ondblclick= "showCodeListEx('cond',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('cond',[this,''],[0,1]);"-->
					</TD>
				</TR>
			</table>
			</div>
			<INPUT VALUE="��  ѯ" class="cssButton" TYPE=button onclick="easyQueryClick('<%=tGrpContNo%>');">
			<INPUT type="hidden" id="Operator" name="Operator" value="">
			<INPUT type="hidden" id="blank" name="blank" value="">
			<INPUT type="hidden" id="isContPlan" name="isContPlan" value="">
		
		</div>
		<Div id="divLCPol1" style="display: ''" >
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCPol1);"></td>
					<td class=titleImg>������ѯ���</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPolGrid"></span></td>
				</tr>
				<tr>
					<td align=center>
			<INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();">
			<INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
					</td>
				</tr>					
			</table>
		


			<P>
			<INPUT VALUE="��  ��" style="float: left" class="cssButton" TYPE=button onclick="goBack();">
			<!--<INPUT VALUE="�����˱�ͨ��" class="cssButton" TYPE=button onclick="manuchk(3);">-->
			</P>
		</div>

		<DIV id=DivLCAppntIndButton STYLE="display:none">
			<!-- ���˵�Ͷ������Ϣ���� -->
			<table>
				<tr>
					<td class="common"><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCAppntInd);"></td>
					<td class=titleImg>Ͷ����λ��Ϣ</td>
				</tr>
			</table>
		</DIV>
		<DIV id=DivLCAppntInd class="maxbox1" STYLE="display:none">
			<table class=common>
				<TR class=common>
					<TD class=title>Ͷ����λ</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly id="GrpName" name=GrpName>
					</TD>
					<TD class=title>�绰</TD>
					<TD class=input>
						<Input id="Phone" name=Phone CLASS="readonly wid" readonly>
					</TD>
					<TD class=title>��λ��ַ</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly id="PostalAddress" name="PostalAddress">
					</TD>
				</TR>
				<TR class=common>
					
					<TD class=title>��������</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly id="ZipCode" name="ZipCode">
					</TD>
					<td CLASS="title">ҵ��Ա����</td>
					<td CLASS="input" COLSPAN="1">
						<input id="AgentCode" NAME="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</TR>
			</table>
		</DIV>
		
		<!--���˵���ͬ��Ϣ-->
		<DIV id=DivLCContButton  STYLE="display:none">
			<table id="table1">
				<tr>
					<td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
					<td class="titleImg">������ͬ��Ϣ</td>
				</tr>
			</table>
		</DIV>
		
		<div id="DivLCCont" class="maxbox1" STYLE="display:none">
			<table class="common" id="table2">
				<tr CLASS="common">
					<td CLASS="title">����</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredName" NAME="InsuredName" CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">�Ա�</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredSex" NAME="InsuredSex"  CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">��������</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredBirthday" NAME="InsuredBirthday" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">֤������</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredIDType" NAME="InsuredIDType" CLASS="readonly wid"  MAXLENGTH="2" readonly>
					</td>
					<td CLASS="title">֤����</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredIDno" NAME="InsuredIDno" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
					<td CLASS="title">ְҵ���</td>
					<td CLASS="input" COLSPAN="1">
						<input id="OccupationType" NAME="OccupationType" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</tr>
			</table>
		</div>
<!--
		<div id="DivLCCont" STYLE="display:'none'">
			<table class="common" id="table2">
				<tr CLASS="common">
					<td CLASS="title">������ͬͶ��������</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="ProposalContNo" CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="40">
					</td>
					<td CLASS="title">ӡˢ����</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="PrtNo" VALUE CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="20">
					</td>
					<td CLASS="title">�������</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="ManageCom" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">��������</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="SaleChnl" CLASS="readonly" readonly MAXLENGTH="2">
					</td>
					<td CLASS="title">�����˱���</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCode" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
					<td CLASS="title">���������</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentGroup" CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="12">
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">���ϴ����˱���</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCode1" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
					<td CLASS="title">�������</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCom" CLASS="readonly" readonly>
					</td>
					<td CLASS="title">����Ӫҵ����</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentType" CLASS="readonly" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">��ע</td>
					<td CLASS="input" COLSPAN="5">
						<input NAME="Remark" CLASS="readonly" readonly MAXLENGTH="255">
					</td>
				</tr>
			</table>
		</div>
-->		

			<%-- <hr class="line"/> -->--%>
			
			<div id="divContUWResult" style="display : none">
			<!--
			<INPUT VALUE="����������ϸ��Ϣ" Class="cssButton" TYPE=button onclick="showPolDetail();">
			<INPUT VALUE="��������Ͷ����Ϣ" Class="cssButton" TYPE=button onclick="showApp();">
			<INPUT VALUE="���������˱���¼" Class="cssButton" TYPE=button onclick="showOldUWSub();">
		
			<input value="�����˱����ۼ���Ϣ" class=cssButton type=button onclick="amntAccumulate();" > 
			<input value="������֪��ѯ" class=cssButton type=button onclick="queryHealthImpart();">
			 <input value="������ϲ�ѯ" class=cssButton type=button onclick="showBeforeHealthQ();" >         	    	
	     <input value="�������¼��" class=cssButton type=button onclick="showHealth();">
		   <input value="��������¼��" class=cssButton type=button onclick="showRReport();">
		   	<INPUT VALUE="�����Զ��˱���Ϣ" Class="cssButton" TYPE=button onclick="showNewPolUWSub();">			
			
-->	    
		</Div>

		<!-- ���˵�����ѯ������֣��б� -->
		<Div id="divMain" style="display: none"></div>
		<!--������-->
		<Div id="divLCPol2" style="display: none">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" ></td>
					<td class=titleImg>���ֲ�ѯ���</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPolAddGrid" onclick="getPolGridCho();"></span></td>
				</tr>
				<tr>
					<td align=center>
						<INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();">
						<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();">
						<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();">
						<INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
					</td>
				</tr>	
			</table>
		</div>
		
				<!----��Ʒ��϶���--------------->
			
		<Div id="divLCPlan" style="display: none">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" ></td>
					<td class=titleImg>��Ʒ��ѯ���</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPlanAddGrid"></span></td>
				</tr>
				<tr>
					<td align=center>
						<INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();">
						<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();">
						<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();">
						<INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
					</td>
				</tr>	
	  </table>
	  </Div>
<!--
			<table class=common border=0 width=100%>
				<tr>
					<td class=titleImg align=center>������Ϣ��</td>
				</tr>
			</table>
-->
<!--
			<table class=common align=center>
				<TR class=common>
					<TD class=title>Ͷ��������</TD>
					<TD class=input><Input class="readonly" readonly name=ProposalNo></TD>
					<TD class=title>���ֱ���</TD>
					<TD class=input><Input class="readonly" readonly name=RiskCode></TD>
					<TD class=title>���ְ汾</TD>
					<TD class=input><Input class="readonly" readonly name=RiskVersion></TD>
				</TR>
				<TR class=common>
					<TD class=title>�����˿ͻ���</TD>
					<TD class=input><Input class="readonly" readonly name=InsuredNo></TD>
					<TD class=title>����������</TD>
					<!--TD class=input><Input class="readonly" readonly name=InsuredName></TD>
					<TD class=title>�������Ա�</TD>
					<!--TD class=input><Input class="readonly" readonly name=InsuredSex></TD>
				</TR>
				<TR class=common>
					<TD class=title>����</TD>
					<TD class=input><Input class="readonly" readonly name=Mult></TD>
					<TD class=title>����</TD>
					<TD class=input><Input class="readonly" readonly name=Prem></TD>
					<TD class=title>����</TD>
					<TD class=input><Input class="readonly" readonly name=Amnt></TD>
				</TR>
				<TR class=common>
					<TD class=input>
						<INPUT type="hidden" class=Common name=UWGrade value="">
						<INPUT type="hidden" class=Common name=AppGrade value="">
						<INPUT type="hidden" class=Common name=PolNo value="">
						<INPUT type="hidden" class=Common name=ContNo value="">
						<INPUT type="hidden" class=Common name=GrpContNo value="">
						<INPUT type="hidden" class=Common name=AppntNo value="">
					</TD>
					</TR>
				</table>
-->
				    <Input type="hidden" class=Common id="ProposalContNo" name=ProposalContNo value="">
						<INPUT type="hidden" class=Common id="UWGrade" name=UWGrade value="">
						<INPUT type="hidden" class=Common id="AppGrade" name=AppGrade value="">
						<INPUT type="hidden" class=Common id="PrtNo" name=PrtNo value="">
						<INPUT type="hidden" class=Common id="PolNo" name=PolNo value="">
						<INPUT type="hidden" class=Common id="ContNo" name=ContNo value="">
						<INPUT type="hidden" class=Common id="GrpContNo" name=GrpContNo value="">
						<INPUT type="hidden" class=Common id="AppntNo" name=AppntNo value="">
						<INPUT type="hidden" class=Common id="ProposalNo" name=ProposalNo value="">
						<INPUT type="hidden" class=Common id="RiskCode"��name=RiskCode value="">
						<INPUT type="hidden" class=Common id="RiskVersion" name=RiskVersion value="">
						<INPUT type="hidden" class=Common id="InsuredNo" name=InsuredNo value="">
						<INPUT type="hidden" class=Common id="Mult" name=Mult value="">
						<INPUT type="hidden" class=Common id="Prem" name=Prem value="">
						<INPUT type="hidden" class=Common id="Amnt" name=Amnt value="">
						<INPUT type="hidden" class=Common id="ContPlanCode"��name=ContPlanCode value="">
						<INPUT type="hidden" class=Common id="PlanType" name=PlanType value="">
						

				
				<div id='divOperateButtion'  style="display: none">
				<hr class="line">
		
				<INPUT VALUE="  ����������ϸ��Ϣ  " Class="cssButton" id="uwButton1" name="uwButton1" TYPE=button onclick="showPolDetail();">
			  <input value=" �����˱����ۼ���Ϣ " class=cssButton id="uwButton2" name="uwButton2" type=button onclick="amntAccumulate();" >
			  <INPUT VALUE=" �����Զ��˱���Ϣ " Class="cssButton" id="uwButton5" name="uwButton5" TYPE=button onclick="showNewPolUWSub();">
			  <INPUT VALUE="   �����˱��켣   " Class="cssButton" id="uwButton6" name="uwButton6" TYPE=button onclick="showOldUWSub();">
			  <br> 
				<INPUT VALUE="�������ѳб�������ѯ" class="cssButton" id="uwButton3" name="uwButton3" TYPE=button onclick="queryProposal();">
        <INPUT VALUE="������δ�б�������ѯ" class="cssButton" id="uwButton4" name="uwButton4" TYPE=button onclick="queryNotProposal();">
		   	<INPUT VALUE ="�����˼�����ȫ��ѯ" Class="cssButton" id="uwButton14" name="uwButton14" TYPE=button onclick="queryEdor();">
		   <INPUT VALUE = "�����˼��������ѯ" Class="cssButton" id="uwButton15" name="uwButton15" TYPE=button onclick="queryClaim();">
		   	<br>
			<input value="    ������֪��ѯ    " class=cssButton id="uwButton7" name="uwButton7" type=button onclick="queryHealthImpart();">
			 <input value="   ������ϲ�ѯ     " class=cssButton id="uwButton8" name="uwButton8" type=button onclick="showBeforeHealthQ();" >         	    
	     <!--INPUT VALUE="�������ϲ�ѯ" class=cssButton TYPE=button onclick="RReportQuery();"-->	
	     <input value="   �������¼��   " class=cssButton id="uwButton9" name="uwButton9" type=button onclick="showHealth();">
		   <input value="   ��������¼��   " class=cssButton id="uwButton10" name="uwButton10" type=button onclick="showRReport();">
		   
				<!--INPUT VALUE="�����Զ��˱���Ϣ" Class="cssButton" TYPE=button onclick="showNewUWSub();"-->
				
							
	
			<hr class="line"/>			
		</Div>
		
		
		<Div id = divLCPolButton style="display: none">
		</Div>
		<!-- ���˵��˱����� -->
		<div id="divUWResult" style="display : none">
			<table class=common border=0 width=100%>
				<tr>
					<td class=titleImg align=center>���˵��˱����ۣ�</td>
				</tr>
			</table>
			<table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title>
						���˵��˱�����
						<Input class="code" id="UWState" name=UWState readonly='true' CodeData="0|^1|�ܱ�^j|�ӷѳб�^t|��Լ�б�^x|�޶�б�^9|��׼�б�" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('condition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">
					</TD>
				</TR>
				<tr>
				  <td>
	        <hr class="line"/>
					<INPUT VALUE="�����ӷѳб�¼��" Class="cssButton" id="AddPremInput" name="AddPremInput" type=button onclick="showAdd();">
					<INPUT VALUE="������Լ�б�¼��" Class="cssButton" id="AddSpecInput" name="AddSpecInput" type=button onclick="showSpec();">
	        <hr class="line"/>
	        <td>
        </tr>
				<tr>
					<TD class=title>���˵��˱����</TD>
				</tr>

				<tr>
					<TD class=input><textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
				</tr>
			</table>
			
			<INPUT VALUE="�����˱�ȷ��" Class="cssButton" TYPE=button onclick="manuchk(1);">
			<INPUT TYPE="hidden" id="UWDelay" NAME="UWDelay" VALUE="">
			<INPUT TYPE="hidden" id="PolNoHide" NAME="PolNoHide" VALUE="">
			<INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="InitClick();">
		</Div>
		
			
			
	
		<div id="divChangeResult" style="display: none"></div>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
