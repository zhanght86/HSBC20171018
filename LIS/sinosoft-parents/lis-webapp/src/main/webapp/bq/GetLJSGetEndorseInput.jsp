<html> 
<% 
//�������ƣ�
//�����ܣ���ȫ
//�������ڣ�2005-10-28 09:18����
//������  ��wenhuan
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>  
  
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <!--SCRIPT src="./GetLJSGetEndorse.js"></SCRIPT-->
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="GetLJSGetEndorseInit.jsp"%>

<title>��ȫ������ϸ</title> 
</head>
<body  onload="initForm();" >
  <form action="./GetLJSGetEndorseSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <div class=maxbox1>
 <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > ������</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorNo id=EdorNo >
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
      <TD class = title > ���屣���� </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
      </TD>   
    </TR>
    <!--TR class=common>
    	<TD class =title>��������</TD>
    	<TD class = input>    		
    		<Input class= "readonly" readonly name=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<Input class= "readonly" readonly name=EdorValiDate ></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR-->
  </TABLE>
  </div>
  <!--���������б���Ϣ-->  
 <Div  id= "divPolInfo" style= "display: ''">
 	 <table>
 	 	<tr>
 	 		<td class=common>
 	 			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Customer);">
 	 		</td>
 	 		<td class= titleImg>
 	 			������ϸ
 	 		</td>
 	 	</tr>
 	</table>
 	<Div id = "MoneyDetail" style = "display : ''" align=center>
 		<table>
 			<tr>
 				<td>
 					<span id = "spanMoneyDetailGrid">
 					</span>
 				</td>
 			</tr>
 		</table>
 		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage11.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage11.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage11.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage11.lastPage();"> 		
 	</Div>           
    
    <Input class= cssButton type=Button value="�� ��" onclick="returnParent()">	

	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" value= 2 name="ContType">
	 <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">
	 <input type=hidden id="ContNo" name="ContNo">
	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
 <script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";	
</script>
</html>
