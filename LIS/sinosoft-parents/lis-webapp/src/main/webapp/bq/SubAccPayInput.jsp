<html> 
<% 
//�������ƣ�
//�����ܣ���ȫ
//�������ڣ�2005-10-28 09:18����
//������  ��wenhuan
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 

  <!--SCRIPT src="./PEdor.js"></SCRIPT-->
  <SCRIPT src="./SubAccPay.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="SubAccPayInit.jsp"%>

<title>��ȫ���˻�������Ϣ¼��</title> 
</head>
<body  onload="initForm();" >
  <form action="./SubAccPaySubmit.jsp" method=post name=fm id=fm target="fraSubmit"> 
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
  </TABLE>
  </div>
  <!--���������б���Ϣ-->  
 <Div  id= "divPolInfo" style= "display: ''">
 	 <table>
 	 	<tr>
 	 		<td class=common>
 	 			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MoneyDetail);">
 	 		</td>
 	 		<td class= titleImg>
 	 			δ¼����˻�������Ϣ����ϸ
 	 		</td>
 	 	</tr>
 	</table>
 	<Div id = "MoneyDetail" style = "display : ''">
 		<table>
 			<tr>
 				<td>
 					<span id = "spanMoneyDetailGrid">
 					</span>
 				</td>
 			</tr>
 		</table>
 		<div id = "divMoneyButton" align="center">
 			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage11.firstPage();"> 
			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage11.previousPage();"> 					
			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage11.nextPage();"> 
			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage11.lastPage();"> 
		</div>		
 	</Div>           
  
<Div  id= "divPolInfo" style= "display: ''">
 	 <table>
 	 	<tr>
 	 		<td class=common>
 	 			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,SubAccDetail);">
 	 		</td>
 	 		<td class= titleImg>
 	 			���˻�������ϸ
 	 		</td>
 	 	</tr>
 	</table>
 	<Div id = "SubAccDetail" style = "display : ''">
 		<table>
 			<tr>
 				<td>
 					<span id = "spanSubAccGrid">
 					</span>
 				</td>
 			</tr>
 		</table>
 		<div id = "divMoneyButton2" align="center">
 			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage22.firstPage();"> 
			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage22.previousPage();"> 					
			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage22.nextPage();"> 
			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage22.lastPage();"> 
		</div>		
 	</Div> 
 <DIV id = "divGetInfo" align="center" class=maxbox1>	
 	<TABLE class=common>
      <tr class=common>
         <td class=title> �˷ѷ�ʽ </td>
         <TD  class= input><input class="codeno" name=PayMode id=PayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('edorgetpayform',[this,PayModeName],[0,1],null,null,null,null,'207');" onkeyup="showCodeListKey('edorgetpayform', [this,PayModeName],[0,1],null,null,null,null,'207');"><input class="codename" name=PayModeName id=PayModeName readonly></TD>
         <td class=title>��ȡ��</td>
         <td class= input><Input type="input" class="common wid" name=Drawer id=Drawer></td>
         <td class=title>��ȡ�����֤��</td>
         <td class= input><Input type="input" class="common wid" name=DrawerIDNo id=DrawerIDNo></td>
       </tr>
      <tr class=common>
         <td class="title">��������</td>
         <td class="input"><Input type="text" class="codeno" name="BankCode" id=BankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('bank',[this,BankName],[0,1],null,null,null,0,317)" onkeyup="showCodeListKey('bank',[this,BankName],[0,1],null,null,null,0,317)"><input type="text" class="codename" name="BankName" id=BankName readonly></td>
         <td class="title">�����˻�</td>
         <td class="input"><input type="text" class="coolConfirmBox" name="BankAccNo" id=BankAccNo></td>
         <td class="title">�˻���</td>
         <td class="input"><Input type="text" class="common wid" name="AccName" id=AccName></td>
      </tr>
   </table>
   <Input class= cssButton type=Button value="��Ӽ�¼" onclick="addSubAcc()">	
   <Input class= cssButton type=Button value="�޸ļ�¼" onclick="updateSubAcc()">	
   <Input class= cssButton type=Button value="ɾ����¼" onclick="deleteSubAcc()">	
   <Input class= cssButton type=Button value="��������" onclick="showPage(this,divDiskApp)">	
   <Input class= cssButton type=Button value="�� ��" onclick="returnParent()">	
<DIV>
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">
	 <input type=hidden id="ContNo" name="ContNo">
	 <input type=hidden id="PEdorState" name="PEdorState">
</form>
  
<form action="./SubAccLoadDetail.jsp" method=post name=fm2 target="fraSubmit" enctype="multipart/form-data"> 
  	 <div id="divDiskApp" style="display: 'none'" align="center">
       <table>
         <TR class= common>
     	 	 <TD class=common>
						<Input type="file" name=FileName id=FileName>
       		 	<Input class= cssButton type=Button value=" ��  �� " onclick="diskInput();">
     	 	 </TD>
     	  </TR>
     </table>
     <br>
      <center>
      		<a href="./SubAcc.xls">���ط��ʻ���������ģ��</a><br> 
      </center>
  </div>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
 <script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";	
</script>
</html>
