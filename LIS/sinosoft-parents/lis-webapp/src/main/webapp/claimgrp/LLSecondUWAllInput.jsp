<%
	//�������ƣ�LLSecondUWAllInput.jsp.
	//�����ܣ������˹��˱���ȡ����
	//�������ڣ�2005-1-28 11:10:36
	//������  ��zhangxing
	//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<head >
	<%	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	//    String tFlag = request.getParameter("type");
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="LLSecondUWAll.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LLSecondUWAllInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" > 
	<table class= common border=0 width=100%>
		<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''">
		<table  class= common>
			<TR  class= common>
				<TD  class= title>�ⰸ�� </TD>
				<TD  class= input><Input class= common name=QCaseNo ></TD>
				<TD  class= title>�������˺���</TD>
				<TD  class= input><Input class= common name=QInsuredNo ></TD>
				<TD  class= title>������������</TD>
				<TD  class= input><Input class= common name=QInsuredName ></TD>
			</TR>
			<TR  class= common>
				<TD  class= title>�ⰸ��ر�־ </TD>
                <TD  class= input><input class=codeno readonly name=QClaimRelFlag CodeData="0|3^0|��� ^1|�����" ondblclick="return showCodeListEx('QClaimRelFlag', [this,QClaimRelFlagName],[0,1],'','','','',100);" onkeyup="return showCodeListKeyEx('QClaimRelFlag', [this,QClaimRelFlagName],[0,1],'','','','',100);"><input class=codename name=QClaimRelFlagName readonly ></TD>				
				<TD  class= title>����</TD>
				<TD  class= input><Input class="common" name=QMngCom ></TD>
				<TD  class= title> </TD>
				<TD  class= input></TD>
			</TR>
		</table>
	</DIV>
	<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryClick();">
	<INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="cancleClick();">

	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWBatchGrid);"></td>
			<td class= titleImg>�����</td>
		</tr>  	
	</table>
	<Div  id= "DivLLCUWBatchGrid" style= "display: ''" align = center>
		<table  class= common >
			<tr  class=common>
				<td text-align: left colSpan=1 ><span id="spanLLCUWBatchGrid" ></span> </td>
			</tr>
		</table>
		<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();">    
	</Div>
	<hr>
	<INPUT class=cssButton VALUE="��������" TYPE=button onclick="applyClick();">
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivSelfLLCUWBatchGrid);"></td>
			<td class= titleImg>���˱��������</td>
		</tr>  	
	</table>
	<Div  id= "DivSelfLLCUWBatchGrid" style= "display: ''" align = center>
		<table  class= common >
			<tr  class=common>
				<td text-align: left colSpan=1 ><span id="spanSelfLLCUWBatchGrid" ></span> </td>
			</tr>
		</table>
		<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();">    
	</Div>
	<!--��������������¼���ر���Ϣ-->
	<table>
		<Input type=hidden id="" name="Operator" ><!--//��¼����Ա-->
		<Input type=hidden id="" name="ComCode" ><!--//��¼��½����-->
		<Input type=hidden id="" name="ManageCom" ><!-- //��¼�������-->
		
		<input type=hidden id="MissionID" 	 name= "MissionID">
		<input type=hidden id="SubMissionID" name= "SubMissionID">
		<input type=hidden id="ActivityID" name= "ActivityID">
	
	</table>
</form>
</body>
</html>
