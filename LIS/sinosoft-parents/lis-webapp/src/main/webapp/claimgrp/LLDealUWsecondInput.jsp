<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
	<%
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput)session.getValue("GI");	  
		String tCaseNo = request.getParameter("CaseNo");
		String tInsuredNo =request.getParameter("InsuredNo");
	%>
	<title>���˽���</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> 
    <script src="./LLDealUWsecond.js"></script> 
    <%@include file="LLDealUWsecondInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>
	<table>
		<TR>
			<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);"></TD>
			<TD class= titleImg>���������Ѿ����κ˱��ĺ�ͬ���б�</TD>
		</TR>
	</table>
	<Div  id= "divCont" align= center style= "display: ''">
		<table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLLContGrid" ></span></TD>
			</TR>
		</table>
		<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();"> 	
	</Div>
    <hr>
    
    <table>
		<TR>
			<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContInfo);"></TD>
			<TD class= titleImg>ѡ�еĺ�ͬ���Ķ��κ˱���Ϣ</TD>
		</TR>
	</table>
    <Div  id= "divContInfo" align= center style= "display: ''">
		<table  class= common>
			<TR class= common><TD class= title> Ͷ���齡����֪��ѯ�ʺ� </TD></TR> 
           	<TR class= common>       
               <TD class= input> <textarea name="HealthImpartNo1" cols="100" rows="1" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>	
           	
           	<TR class= common><TD class= title> ��콡����֪��ѯ�ʺ� </TD></TR> 
           	<TR class= common>       
               <TD class= input> <textarea name="HealthImpartNo2" cols="100" rows="1" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>	
           	
           	<TR class= common><TD class= title> ��Ӧδ��֪��� </TD></TR> 
           	<TR class= common>       
               <TD class= input> <textarea name="NoImpartDesc" cols="100" rows="2" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>	
           	
           	<TR class= common> <TD class= title> ������Ŀǰ����״������</TD></TR> 
           	<TR class= common>       
               <TD class= input> <textarea name="Remark1" cols="100" rows="3" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>		
		</table>    	
	
    	<!---#######�������˽���###########--->

		<table class=common>
			<tr class=common>
				<TD class= title>��ͬ�˱�����</td>
				<td class=input><Input class=codeno disabled name="uwContState" ondblclick="return showCodeList('contuwstate',[this,uwContStatename],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,uwContStatename],[0,1]);"><Input class=codename name=uwContStatename readonly ></td>
				<td class=title> </td>
				<td class= input></td>
				<td class=title></td>
				<td class= input></td> 
			</TR>
		</table>
		<table  class= common>
			<TR class= common>
               <TD class= title> ��ͬ�˱���� </TD>
           	</TR> 
           	<TR class= common>       
               <TD class= input> <textarea name="uwContIdea" cols="100" rows="3" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>		
		</table>
    </Div> 	
    <hr>
	<table>
		<TR>
			<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);"></TD>
			<TD class= titleImg>ѡ�еĺ�ͬ���µ����ֵ�</TD>
		</TR>
	</table>
	
	<Div  id= "divPol" align= center style= "display: ''">
		<table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLLPolGrid" ></span></TD>
			</TR>
		</table>
		<!--
		<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="tturnPage.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="tturnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="tturnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="tturnPage.lastPage();"> 	
		-->

		<table class=common>
			<tr class=common>
				<TD class= title>���ֺ˱�����</td>
				<td class=input><Input class=codeno disabled name=UWRiskState ondblclick="return showCodeList('uwstate',[this,UWRiskStateName],[0,1]);" onkeyup="return showCodeListKey('uwstate',[this,UWRiskStateName],[0,1]);"><Input class=codename name=UWRiskStateName readonly ></td>				
				<td class=title> </td>
				<td class= input></td>
				<td class=title></td>
				<td class= input></td> 
			</TR>
		</table>
		<table  class= common>
			<TR class= common>
               <TD class= title>���ֺ˱���� </TD>
           	</TR> 
           	<TR class= common>       
               <TD class= input> <textarea name="UWRiskIdea" cols="100" rows="3" witdh=25% class="common" readonly ></textarea></TD>
           	</TR>		
		</table>
    </Div> 	
	
	<hr>
	<INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="turnback();"> 	
    <!---���ر��������ڽ�����ҳ���������-------->
	 <INPUT  type= "hidden" id="CaseNo" name="CaseNo"  >
 	 <INPUT  type= "hidden" id="InsuredNo" name="InsuredNo"  >
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
