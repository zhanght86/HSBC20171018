<%
	//�������ƣ�LLSecondUWAllInput.jsp.
	//�����ܣ������˹��˱���ȡ����
	//�������ڣ�2005-1-28 11:10:36
	//������  ��zhangxing
	//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<head >
	<%	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	//    String tFlag = request.getParameter("type");
	%>
 <script>	
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
     <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
 
	<SCRIPT src="LLSecondUWAll.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<%@include file="LLSecondUWAllInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
<form method=post name=fm id="fm" target="fraSubmit" > 
  <div id ="SecondUWAllPool"></div> 
<!--  	<table class= common border=0 width=100%>
		<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''">
		<table  class= common>
			<TR  class= common>
				<TD  class= title>���κ� </TD>
				<TD  class= input><Input class= common name=BatNo ></TD>
				<TD  class= title>������ </TD>
				<TD  class= input><Input class= common name=QCaseNo ></TD>
				<TD  class= title>ת������ </TD>
				<TD  class= input><Input class= "multiDatePicker" dateFormat="short" name=theCurrentDate ></TD>
			</tr>
			<tr  class= common>
				<TD  class= title>�����˺���</TD>
				<TD  class= input><Input class= common name=QInsuredNo ></TD>
				<TD  class= title>������</TD>
				<TD  class= input><Input class= common name=QInsuredName ></TD>
				<td class="title">�������</td>
                <td class="input"><input type="text" class="codeno" name="ManageCom" verify="�������|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName],[0,1])"><input class="codename" name="ManageComName" readonly></td>
			</TR>
			<TR  class= common>
				<TD  class= title>�ⰸ��ر�־ </TD>
				<TD  class= input><input class=codeno readonly name="QClaimRelFlag" CodeData="0|3^0|���^1|�����" ondblclick="return showCodeListEx('QClaimRelFlag', [this,QClaimRelFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('QClaimRelFlag', [this,],QClaimRelFlagName[0,1]);"><input class=codename name=QClaimRelFlagName readonly ></TD>
				<TD  class= title></TD>
				<TD  class= input></TD>				
				<TD  class= title> </TD>
				<TD  class= input></TD>
			</TR>
		</table>
	</DIV>
	<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryClick();">
	<INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="cancleClick();">
    <hr>
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWBatchGrid);"></td>
			<td class= titleImg>��������</td>
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
		<table class= common border=0 width=100%>
		<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''">
		<table  class= common>
			<TR  class= common>
				<TD  class= title>���κ� </TD>
				<TD  class= input><Input class= common name=BatNo2 ></TD>
				<TD  class= title>������ </TD>
				<TD  class= input><Input class= common name=QCaseNo2 ></TD>
				<TD  class= title>ת������ </TD>
				<TD  class= input><Input class= "multiDatePicker" dateFormat="short" name=theCurrentDate2 ></TD>
			</tr>
			<tr  class= common>
				<TD  class= title>�����˺���</TD>
				<TD  class= input><Input class= common name=QInsuredNo2 ></TD>
				<TD  class= title>������</TD>
				<TD  class= input><Input class= common name=QInsuredName2 ></TD>
				<td class="title">�������</td>
                <td class="input"><input type="text" class="codeno" name="ManageCom2" verify="�������|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName],[0,1])"><input class="codename" name="ManageComName" readonly></td>
			</TR>
			<TR  class= common>
				<TD  class= title>�ⰸ��ر�־ </TD>
				<TD  class= input><input class=codeno readonly name="QClaimRelFlag2" CodeData="0|3^0|���^1|�����" ondblclick="return showCodeListEx('QClaimRelFlag', [this,QClaimRelFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('QClaimRelFlag', [this,],QClaimRelFlagName[0,1]);"><input class=codename name=QClaimRelFlagName readonly ></TD>
				<TD  class= title></TD>
				<TD  class= input></TD>				
				<TD  class= title> </TD>
				<TD  class= input></TD>
			</TR>
		</table>
	</DIV>
	<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="initSelfLLCUWBatchGridQuery();">
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivSelfLLCUWBatchGrid);"></td>
			<td class= titleImg>���˹�����</td>
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
	-->
	<!--��������������¼���ر���Ϣ-->
	<table>
		<Input type=hidden id="" name="Operator" ><!--//��¼����Ա-->
		<Input type=hidden id="" name="ComCode" ><!--//��¼��½����-->
		<Input type=hidden id="" name="ManageCom" ><!--//��¼��½����-->
	 	
		<input type=hidden id="MissionID" 	 name= "MissionID">
		<input type=hidden id="SubMissionID" name= "SubMissionID">
		<input type=hidden id="ActivityID" name= "ActivityID">
		<input type=hidden id="SBatNo" name= "SBatNo">
		<input type=hidden id="SCaseNo" name= "SCaseNo">
		
	</table>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
