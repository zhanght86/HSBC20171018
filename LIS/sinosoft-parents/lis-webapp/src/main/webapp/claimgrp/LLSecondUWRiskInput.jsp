<%
//�������ƣ�LLSecondUWRiskInput.jsp
//�����ܣ����ֺ˱���Ϣ����-----���ⲿ��
//�������ڣ�2005-01-06 11:10:36
//������  ��HYQ
//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<html>
<head>
	<%
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
		String tContNo=request.getParameter("ContNo");
		String tInsuredNo=request.getParameter("InsuredNo");
		String tSendFlag=request.getParameter("SendFlag");
		String tCaseNo=request.getParameter("CaseNo");
		String tBatNo=request.getParameter("BatNo");
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="./LLSecondUWRisk.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="./LLSecondUWRiskInit.jsp"%>
	<title>������Ϣ </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" >
	<DIV id=DivLCPol style="display:''">
		<table class= common >
			<tr>
				<td class= titleImg align= center>������Ϣ��</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1><span id="spanLLRiskGrid" ></span></td>
			</tr>
		</table>
	</DIV>
	<hr>
	<table>
		<tr>
			<td>        
				<input value="�ӷѳб�¼��"  class=cssButton type=button name= "AddFee"  onclick="showAdd();">
				<input value="��Լ�б�¼��"  class=cssButton type=button onclick="showSpec();">
			</td>
		</tr>
	</table>

	<div id = "divUWResult" style = "display: ''">
	<!-- �˱����� -->
		<table  class= common>
			<TR  class= common>
				<TD class= title>���ֺ˱�����</td>
				<td class=input><Input class=codeno readonly name=UWState ondblclick="return showCodeList('uwstate',[this,UWStateName],[0,1]);" onkeyup="return showCodeListKey('uwstate',[this,UWStateName],[0,1]);"><Input class=codename name=UWStateName readonly ></td>
				<TD class= title></td>
				<td class=input></td>
				<TD class= title></td>
				<td class=input></td>
			</TR>
		</table>
		<table class=common>
			<tr>
				<TD >�˱����</TD>
			</tr>
			<tr>
				<TD  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
			</tr>
		</table>
		<INPUT VALUE="ȷ  ��" class=cssButton TYPE=button onclick="uwSaveClick();">
		<INPUT VALUE="ȡ  ��" class=cssButton TYPE=button onclick="cancelClick();">
		<INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="top.close();">
	</div>
	<!--��������-->
	<Input name="Operator"    id="Operator" type=hidden><!-- //��¼����Ա -->
	<Input name="ComCode"    id="ComCode" type=hidden><!-- //��¼��½���� -->
	<Input name="ManageCom"    id="ManageCom" type=hidden><!-- //��¼������� -->
	
	<Input name="SendFlag"    id="SendFlag" type=hidden><!-- ��;δ֪ -->
	<Input name="CaseNo"   id="CaseNo" type=hidden><!--�ⰸ�� -->
	<Input name="BatNo"   id="BatNo" type=hidden><!-- ���κ� -->
	<Input name="ContNo"  id="ContNo" type=hidden><!-- ��ͬ�� -->
	<Input name="InsuredNo"   id="InsuredNo" type=hidden><!--�����˿ͻ���  -->
	<Input name="PolNo"   id="PolNo" type=hidden><!--��������  -->
	
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
