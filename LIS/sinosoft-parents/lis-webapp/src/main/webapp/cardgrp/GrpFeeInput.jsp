<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�GrpFeeInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:42
//������ ��CrtHtml���򴴽�
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<%
String RiskName = request.getParameter("RiskName");
String GrpPolNo = request.getParameter("GrpPolNo");
String LoadFlag = request.getParameter("LoadFlag");
%>
<script>
	var ProposalGrpContNo = "<%=request.getParameter("ProposalGrpContNo")%>";
	var LoadFlag ="<%=request.getParameter("LoadFlag")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="GrpFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GrpFeeInit.jsp"%>
</head>
<body onload="initForm();">
	<form method=post name=fm target="fraSubmit" action="GrpFeeSave.jsp">
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;">
				</td>
				<td class=titleImg align=center>��ͬ������Ϣ</td>
			</tr>
		</table>
		<table class=common>
			<TR class=common>
				<TD class=title>
					�����ͬ��
				</TD>
				<TD class=input>
					<Input class="readonly" readonly name=ProposalGrpContNo value="">
					<input type=hidden name=GrpContNo value="">
					<input type=hidden name=mOperate>
				</TD>
				<td class=title>
					<input type=button class="cssButton" value="������Ϣ��ѯ" onclick="RiskQueryClick();">
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr class=common>
				<TD class=title>
					���ֱ���
				</TD>
				<TD class=input>
					<Input class=codeno name=RiskCode ondblclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo');"><input class=codename name=RiskCodeName readonly=true>
					<input type=hidden name=RiskFlag>
				</TD>
			
				<TD id=divmainriskname style="display:none" class=title>
					���ձ���
				</TD>
				<TD id=divmainrisk style="display:none" class=input>
					<Input class=codeno maxlength=6 name=MainRiskCode ondblclick="getriskcode();return showCodeList('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,acodeSql,'b.GrpContNo',1);" onkeyup="getriskcode();return showCodeListKey('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,acodeSql,'b.GrpContNo',1);"><Input class=codename name="MainRiskCodeName" readonly=true>
				</TD>
				<td>
					<Input VALUE="�������β�ѯ" class=cssButton TYPE=button onclick="QueryDutyClick();">
				</td>
			</TR>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanDutyGrid" ></span>
				</td>
			</tr>
		</table>
		<Input VALUE="��������Ҫ��" class=cssButton  TYPE=button onclick="RiskAddClick();">
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContPlanGrid);">
				</td>
				<td class= titleImg>�����ر���Ϣ</td>
			</tr>
		</table>
		<div id="divContPlanGrid" style="display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanGrid" ></span>
				</td>
			</tr>
		</table>
		</Div>
<!--
		<br>
		<input type=button class="cssButton" value="��  ѯ" onclick="easyQueryClick();">
-->
		
	
		<!--table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGrpFee);">
				</td>
				<td class=titleImg>
					���ֹ������ϸ
				</td>
			</tr>
		</table>
		<Div id="divGrpFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpFeeGrid">
						</span>
					</td>
				</tr>
			</table>
		</div>
		<br-->
		 <hr/>
		<Div  id= "divRiskSave" style= "display: ''" align= right> 
			<input type=button class="cssButton" value="������Ϣ����" onclick="submitForm();">
			<input type=button class="cssButton" value="������Ϣ����" onclick="UptContClick();">
			<input type=button class="cssButton" value="������Ϣɾ��" onclick="DelContClick();">
		</Div>
		<hr/>
		<Div  id= "divRiskRela" style= "display: ''" align= left>
			<input type=button class="cssButton" value="��һ��" onclick="returnparent();">
			<Input VALUE="����Ѷ���" class=cssButton  TYPE=button onclick="GrpRiskFeeInfo();">
			<!--Input VALUE="�ɷѶ���" class=cssButton  TYPE=button onclick="PayRuleInfo();"-->
		</Div>

	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
<script>
  var acodeSql ="";
function getriskcode()
{
 acodeSql ="#"+fm.GrpContNo.value+"# and a.riskcode in (select riskcode from lmriskrela where relariskcode =#"+fm.RiskCode.value+"#)";
}
</script>
</html>
