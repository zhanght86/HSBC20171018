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
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupContQueryInit.jsp"%>
</head>
<body onload="initForm('<%=tGrpContNo%>');">
	<form method=post name=fm id="fm" target="fraSubmit">
	  <!-- �ŵ��¸��˵���ѯ���� -->
		<div id="divSearch">
			<table class=common border=0 width=100%>
				<tr>
					<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
					</td>
					<td class=titleImg align=center>�������ѯ������</td>
				</tr>
			</table>
			<div class="maxbox1">
			<div  id= "divFCDay" style= "display: ''"> 
			<table class=common align=center>
				<TR class=common>
					<TD class=title>��ͬ���� </TD>
					<TD class=input><Input class="common wid" name=QContNo id="QContNo" TABINDEX="-1"  MAXLENGTH="40"></TD>
					<TD class=title>����������</TD>
					<TD class=input><Input class="common wid" name=QInsuredName id="QInsuredName"></TD>
					<TD class=title>������� </TD>
					<TD class=input>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name=QManageCom id="QManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<!--Input class="code" name=GUWState CodeData= "0|^1|�ܱ�^4|ͨ�ڳб�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����" ondblclick= "showCodeListEx('cond',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('cond',[this,''],[0,1]);"-->
					</TD>
				</TR>
			</table>
			</div>
			</div>
			<a href="javascript:void(0)" class=button onclick="easyQueryClick('<%=tGrpContNo%>');">��  ѯ</a>
			<!-- <INPUT VALUE="��  ѯ" class="cssButton" TYPE=button onclick="easyQueryClick('<%=tGrpContNo%>');"> -->
			<INPUT type="hidden" name="Operator" id="Operator" value="">
			<INPUT type="hidden" name="blank" id="blank" value="">
			<TD class=input><INPUT type=checkbox name=SearchFlag id="SearchFlag" checked=true></TD>
			<TD class=title>����ѯδͨ���Զ��˱������ֱ���</TD>
		
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
			<hr class="line">
			<table>
		    	<tr>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
		    		</td>
		    		<td class= titleImg>
		    			 �����˱���Ϣ 
		    		</td>
		    	</tr>
		    </table>
			<Div  id= "divLCUWSub1" style= "display: ''">
		    	<table  class= common>
		        	<tr  class= common>
		    	  		<td text-align: left colSpan=1 >
							<span id="spanUWErrGrid" >
							</span> 
						</td>
					</tr>
				</table>
		      <!-- <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="turnPage1.firstPage();"> 
		      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="turnPage1.previousPage();"> 					
		      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="turnPage1.nextPage();"> 
		      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="turnPage1.lastPage();">  -->
			</Div>	
			<div align=left>
				<a href="javascript:void(0)" class=button onclick="goBack();">��  ��</a>
				<a href="javascript:void(0)" class=button onclick="manuchk(3);">�����˱�ͨ��</a>
			</div>
			<!-- <INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="goBack();">
			<INPUT VALUE="�����˱�ͨ��" class="cssButton" TYPE=button onclick="manuchk(3);"> -->
		</Div>

		<DIV id="DivLCAppntIndButton" STYLE="display:none">
			<!-- ���˵�Ͷ������Ϣ���� -->
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCAppntInd);"></td>
					<td class=titleImg>Ͷ����λ��Ϣ</td>
				</tr>
			</table>
		</DIV>
		
		<DIV id="DivLCAppntInd" STYLE="display:none">
			<div class="maxbox1">
			<table class=common>
				<TR class=common>
					<TD class=title>Ͷ����λ</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly name=GrpName id="GrpName">
					</TD>
					<TD class=title>�绰</TD>
					<TD class=input>
						<Input name=Phone id="Phone" CLASS="readonly wid" readonly>
					</TD>
					<TD class=title>��λ��ַ</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly name="PostalAddress" id="PostalAddress">
					</TD>
				</TR>
				<TR class=common>
					
					<TD class=title>��������</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly name="ZipCode" id="ZipCode">
					</TD>
					<td CLASS="title">ҵ��Ա����</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCode" id="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</TR>
			</table>
			</div>
		</DIV>
		
		<!--���˵���ͬ��Ϣ-->
		<DIV id="DivLCContButton" STYLE="display:none">
			<table id="table1">
				<tr>
					<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
					<td class="titleImg">������ͬ��Ϣ</td>
				</tr>
			</table>
		</DIV>
		
		<div id="DivLCCont" STYLE="display:none">
			<div class="maxbox1">
			<table class="common" id="table2">
				<tr CLASS="common">
					<td CLASS="title">����</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredName" id="InsuredName" CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">�Ա�</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredSex" id="InsuredSex"  CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">��������</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredBirthday" id="InsuredBirthday" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">֤������</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredIDType" id="InsuredIDType" CLASS="readonly wid"  MAXLENGTH="2" readonly>
					</td>
					<td CLASS="title">֤����</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredIDno" id="InsuredIDno" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
					<td CLASS="title">ְҵ���</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="OccupationType" id="OccupationType" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</tr>
			</table>
			</div>
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
			<div id="divContUWResult" style="display : 'none'">
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
		    <Input type="hidden" class=Common name=ProposalContNo id="ProposalContNo" value="">
			<INPUT type="hidden" class=Common name=UWGrade id="UWGrade" value="">
			<INPUT type="hidden" class=Common name=AppGrade id="AppGrade" value="">
			<INPUT type="hidden" class=Common name=PrtNo id="PrtNo" value="">
			<INPUT type="hidden" class=Common name=PolNo id="PolNo" value="">
			<INPUT type="hidden" class=Common name=ContNo id="ContNo" value="">
			<INPUT type="hidden" class=Common name=GrpContNo id="GrpContNo" value="">
			<INPUT type="hidden" class=Common name=AppntNo id="AppntNo" value="">
			<INPUT type="hidden" class=Common name=ProposalNo id="ProposalNo" value="">
			<INPUT type="hidden" class=Common name=RiskCode id="RiskCode" value="">
			<INPUT type="hidden" class=Common name=RiskVersion id="RiskVersion" value="">
			<INPUT type="hidden" class=Common name=InsuredNo id="InsuredNo" value="">
			<INPUT type="hidden" class=Common name=Mult id="Mult" value="">
			<INPUT type="hidden" class=Common name=Prem id="Prem" value="">
			<INPUT type="hidden" class=Common name=Amnt id="Amnt" value="">
			<INPUT type="hidden" class=Common name=InsuredNo id="InsuredNo" value="">
				
				
			<hr class="line"></hr>
			<div>
				<a href="javascript:void(0)" class=button onclick="showPolDetail();">����������ϸ��Ϣ</a>
				<a href="javascript:void(0)" class=button onclick="queryProposal();">�������ѳб�������ѯ</a>
				<a href="javascript:void(0)" class=button onclick="queryNotProposal();">������δ�б�������ѯ</a>
				<a href="javascript:void(0)" class=button onclick="showNewPolUWSub();">�����Զ��˱���Ϣ</a>
			</div>
			<!-- <INPUT VALUE="����������ϸ��Ϣ" Class="cssButton" TYPE=button onclick="showPolDetail();"> -->
			<!--input value="�����˱����ۼ���Ϣ" class=cssButton type=button onclick="amntAccumulate();" -->
			<!-- <INPUT VALUE="�������ѳб�������ѯ" class="cssButton"TYPE=button onclick="queryProposal();">
        	<INPUT VALUE="������δ�б�������ѯ" class="cssButton"TYPE=button onclick="queryNotProposal();">
		   	<INPUT VALUE="�����Զ��˱���Ϣ" Class="cssButton" TYPE=button onclick="showNewPolUWSub();"> -->
		   	<!--INPUT VALUE="�����˱��켣" Class="cssButton" TYPE=button onclick="showOldUWSub();"-->
		   	<br>
		   	<div>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="queryHealthImpart();">������֪��ѯ</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showBeforeHealthQ();">������ϲ�ѯ</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showHealth();">�������¼��</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showAdd();">�����ӷѳб�¼��</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showSpec();">������Լ�б�¼��</a>
		   	</div>
			<!-- <input value="������֪��ѯ"  type="hidden" class=cssButton type=button onclick="queryHealthImpart();">
			<input value="������ϲ�ѯ" type="hidden" class=cssButton type=button onclick="showBeforeHealthQ();" > -->         	    
	     <!--INPUT VALUE="�������ϲ�ѯ" class=cssButton TYPE=button onclick="RReportQuery();"-->	
	     	<!-- <input value="�������¼��" type="hidden" class=cssButton type=button onclick="showHealth();">
	     	<INPUT VALUE="�����ӷѳб�¼��" type="hidden"  Class="cssButton" type=button onclick="showAdd();">
			<INPUT VALUE="������Լ�б�¼��" type="hidden" Class="cssButton" type=button onclick="showSpec();"> -->
		   <!--input value="��������¼��" class=cssButton type=button onclick="showRReport();"-->
				<!--INPUT VALUE="�����Զ��˱���Ϣ" Class="cssButton" TYPE=button onclick="showNewUWSub();"-->			
		</Div>
		<Div id = "divLCPolButton" style="display: none"></Div>
		<!-- ���˵��˱����� -->
		<div id="divUWResult" style="display : none">
			<table class=common border=0 width=100%>
				<tr>
					<td class=common>
    					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
					</td>
					<td class=titleImg align=center>���˵��˱����ۣ�</td>
				</tr>
			</table>
			<div  id= "divFCDay1" style= "display: ''"> 
			<table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title5>
						���˵��˱�����
					</TD>
					<TD class=input5>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=UWState id="UWState" CodeData="0|^1|�ܱ�^4|ͨ�ڳб�^9|��׼�б�" onclick="showCodeListEx('condition',[this,''],[0,1]);" ondblclick="showCodeListEx('condition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">
					</TD>
					<td class=title5></td>
					<td class=input5></td>
				</TR>
				<tr>
					<TD class=title5>���˵��˱����</TD>
					<TD class=input5><textarea name="UWIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></TD>
					<td class=title5></td>
					<td class=input5></td>
				</tr>
			</table>
			</div>
			<a href="javascript:void(0)" class=button onclick="manuchk(1);">�����˱�ȷ��</a>
			<a href="javascript:void(0)" class=button onclick="InitClick();">��  ��</a>
			<!-- <INPUT VALUE="�����˱�ȷ��" Class="cssButton" TYPE=button onclick="manuchk(1);"> -->
			<INPUT TYPE="hidden" NAME="UWDelay" id="UWDelay" VALUE="">
			<INPUT TYPE="hidden" NAME="tempgrpcontno" id="tempgrpcontno" VALUE="">
			<INPUT TYPE="hidden" NAME="tempriskcode" id="tempriskcode" VALUE="">
			<INPUT TYPE="hidden" NAME="PolNoHide"  id="PolNoHide" VALUE="">
			<!-- <INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="InitClick();"> -->
		</div>
		
			
			
	
		<div id="divChangeResult" style="display: 'none'"></div>
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
