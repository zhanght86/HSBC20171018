<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
String CValiDate=request.getParameter("CValiDate");
%>
<head>
<script>
var GrpContNo = "<%=GrpContNo%>";
var LoadFlag  = "<%=LoadFlag%>";

var scantype  = "<%=request.getParameter("scantype")%>";
var MissionID = "<%=request.getParameter("MissionID")%>";
var ManageCom = "<%=request.getParameter("ManageCom")%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";
var CValiDate="<%=request.getParameter("CValiDate")%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="ContPlan.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPlanInit.jsp"%>
<title>���屣�ռƻ����� </title>
</head>
<body onload="initForm();">
	<form method=post name=fm target="fraSubmit" action="ContPlanSave.jsp">
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>��ͬ��Ϣ</td>
			</tr>
		</table>
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>�������κ�</TD>
				<TD  class= input>
					<Input class=readonly readonly name=GrpContNo value="<%=GrpContNo%>">
					<Input type=hidden name=ProposalGrpContNo>
					<input type=hidden name=mOperate>
					<input type=hidden name=CValiDate value="<%=CValiDate%>">
					<input type=hidden name=mFlagStr>
				</TD>
				<TD  class= title>�������</TD>
				<TD  class= input>
					<Input class=readonly readonly name=ManageCom>
				</TD>
				<TD  class= title>Ͷ���˿ͻ���</TD>
				<TD  class= input>
					<Input class= readonly readonly name=AppntNo>
				</TD>
			</TR>
			<TR  class= common>
				
				<TD  class= title>���㵥λ����</TD>
				<TD  class= input>
					<Input class= readonly readonly name=GrpName>
				</TD>
			</TR>
		</table>
		<table>
			<tr class=common>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
				</td>
				<td class= titleImg>�Ѵ��ڱ��ռƻ�</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanCodeGrid" ></span>
				</td>
			</tr>
		</table>
		<table>
			<tr class=common>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
				</td>
				<td class= titleImg>���ռƻ�����</td>
			</tr>
		</table>
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>�ƻ�����</TD>
				<TD  class= input>
					<Input class=common name=ContPlanCode maxlength=6 onchange="ChangePlan();"><font color=red>*</font>
				</TD>
				<TD  class= title>���ռƻ�����</TD>
				<TD  class= input>
					<input class=common name=ContPlanName><font color=red>*</font>
				</TD>
				<TD  class= title>Ͷ������</TD>
				<TD  class= input>
					<Input class=common name=Peoples3><font color=red>*</font>
				</TD>
					<Input type=hidden name=PlanSql>
			</TR>
			<TR>
				<TD  class= title>�ƻ����</TD>
				<TD  class= input>
					<Input class=codeno name=RiskPlan1 CodeData='0|^1|�����ײ�' value=1 ondblclick="return showCodeListEx('RiskPlan1',[this,RiskPlan1Name],[0,1]);" onkeyup="return showCodeListKeyEx('RiskPlan1',[this,RiskPlan1Name],[0,1]);"><Input class=codename name="RiskPlan1Name" readonly=true value="�����ײ�">
				</TD>

				<TD id=divriskcodename class= title>���ִ���</TD>
				<TD id=divriskcode  class= input>
					<Input class=codeno name=RiskCode ondblclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo');"><Input class=codename name="RiskCodeName" readonly=true><font color=red>*</font>
					<input type=hidden name=RiskFlag>
				</TD>
				<TD  class= title> Ͷ������</TD>
                <TD class=input>
						<Input class=code name=InsuMult verify="Ͷ������|notnull"  value='1' elemettype=naccesary ondblclick="showCodeList('InsuMult',[this],[0,1],null,null,null,1);" onkeyup="showCodeListKey('InsuMult',[this],[0,1],null,null,null,1);"><font color=red>*</font>
					</TD>
				<TD id=divmainriskname style="display:none" class=title>���ձ���</TD>
				<TD id=divmainrisk style="display:none" class=input>
					<Input class=codeno maxlength=6 name=MainRiskCode ondblclick="return showCodeList('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,fm.GrpContNo.value,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,fm.GrpContNo.value,'b.GrpContNo');"><Input class=codename name="MainRiskCodeName" readonly=true>
				</TD>
				<TD id=divplankind1name style="display:none" class=title>�ײͲ㼶1</TD>
				<TD id=divplankind1 style="display:none" class=input>
					<Input class=code maxlength=6 name=PlanKind1 ondblclick="return showCodeList('PlanKind1',[this],[0],null,'','',1);" onkeyup="return showCodeListKey('PlanKind1',[this],[0],null,'','');">
				</TD>
					
				<TD id=divplankind2name style="display:none" class=title>�ײͲ㼶2</TD>
				<TD id=divplankind2 style="display:none" class=input>
					<Input class=code maxlength=6 name=PlanKind2 ondblclick="initPlanKind2(this);" onkeyup="initPlanKind2(this);">
				</TD>
				</TR>
				<TR>				
				<TD id=divplankind3name style="display:none" class=title>�ײͲ㼶3</TD>
				<TD id=divplankind3 style="display:none" class=input>
					<Input class=code maxlength=6 name=PlanKind3 ondblclick="initPlanKind3(this);" onkeyup="initPlanKind3(this);">
				</TD>							
				<TD id=divcontplanname style="display:none" class=title>�����ײ�</TD>
				<TD id=divcontplan style="display:none" class=input>
					<Input class=codeno maxlength=6 name=RiskPlan ondblclick="initRiskPlan(this,RiskPlanName);" onkeyup="initRiskPlan(this,RiskPlanName);"><Input class=codename name="RiskPlanName" readonly=true>
				</TD>
			</TR>
		</table>
		<Input VALUE="�������β�ѯ" class=cssButton TYPE=button onclick="QueryDutyClick();">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanDutyGrid" ></span>
				</td>
			</tr>
		</table>
		<Input VALUE="���屣�ռƻ�Ҫ��" class=cssButton  TYPE=button onclick="AddContClick();">
		<!--table class=common>
			<TR  class= common>
				<TD>
					<Input VALUE="������ֵ����ռƻ�" class=cssButton  TYPE=button onclick="AddContClick();">
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title>���ռƻ�����</TD>
				<TD  class= input>
					<Input class="code" name=ContPlanType ondblclick="showCodeList('contplantype',[this]);" onkeyup="showCodeListKey('contplantype',[this]);">
				</TD>
			</TR>
		</table-->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>���ռƻ���ϸ��Ϣ</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanGrid" ></span>
				</td>
			</tr>
		</table>
    	<hr>
		<hr/>
		<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<INPUT VALUE="���ռƻ���ѯ" class="cssButton" style= "display: 'none'" TYPE=button onclick="">
		<INPUT VALUE="���ռƻ�����" class="cssButton" TYPE=button onclick="submitForm();">
		<INPUT VALUE="���ռƻ��޸�" class="cssButton" TYPE=button onclick="UptContClick();">
		<INPUT VALUE="���ռƻ�ɾ��" class="cssButton" TYPE=button onclick="DelContClick();">
		</Div>
		<Div  id= "divRiskPlanPSave" style= "display: 'none'" align= right> 
		<INPUT VALUE="���ռƻ�����" class="cssButton" TYPE=button onclick="submitForm();">
		<INPUT VALUE="���ռƻ�ɾ��" class="cssButton" TYPE=button onclick="DelContClick();">
		</Div>		
		<hr/>
		<Div  id= "divRiskPlanRela" style= "display: ''" align= left> 
		<INPUT VALUE="��һ��" class="cssButton"  TYPE=button onclick="returnparent();">
		<!--INPUT VALUE="���ռƻ�ҪԼ¼��" class="cssButton"  TYPE=button onclick="nextstep();"-->
		</Div>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
