<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�
//������  ��Nicholas
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <SCRIPT src="./PEdor.js"></SCRIPT>

  <SCRIPT src="./PEdorTypeAG.js"></SCRIPT>

  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@include file="PEdorTypeAGInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title > ��ȫ�����</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>

      <TD class = title > ������ </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
    <TR class=common>
    	<TD class =title>��������</TD>
    	<TD class = input>
    		<Input class= "readonly wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<Input class= "readonly wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
  </TABLE>
  </div>
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
   </tr>
   </table>

    <Div  id= "divPol" class="maxbox1" style= "display:  ">
      	<table  class= common>
      		<tr class common>
      			<td class = title>Ͷ��������</td>
      			<td class = input>
      				<input class="readonly wid" readonly name=AppntName id=AppntName>
      			</td>
      			<td class = title>֤������</td>
      			<td class = input>
      				<Input class=codeno  "readonly" readonly name=AppntIDType id=AppntIDType >
					<input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true>
      			</td>
		       <TD  class= title > ֤������</TD>
		       <TD  class= input >
               <input class="readonly wid" readonly name=AppntIDNo id=AppntIDNo >
               </TD>
					</tr>
       		<tr  class= common>
		       <TD  class= title > ����������</TD>
		       <TD  class= input >
               <input class="readonly wid" readonly name=InsuredName id=InsuredName >
               </TD>
           <TD  class= title > ֤������</TD>
		       <TD  class= input >
               <Input class=codeno  "readonly" readonly name=InsuredIDType id=InsuredIDType >
			   <input class=codename name=InsuredIDTypeName id=InsuredIDTypeName readonly=true>
               </TD>

		       <TD  class= title > ֤������</TD>
		       <TD  class= input >
               <input class="readonly wid" readonly name=InsuredIDNo id=InsuredIDNo >
               </TD>
         </tr>

    	</table>
    </Div>

<table>
	   	<tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo);">
	      </td>
	      <td class= titleImg>
	        ��������������Ϣ
	      </td>
	   	</tr>
</table>
	<Div  id= "divAGInfo" class=maxbox1 style= "display:  ">
	  <table  class= common>
	  	<TR class = common >
	      <TD  class= title5>
	          Ӧ����
	      </TD>
	      <TD  class= input5 >
				 <Input class= "readonly wid"  readonly name=MoneyAdd id=MoneyAdd >
	  	  </TD>
	    	<TD  class= title5 >
	      ������ȡ��ʽ
	      </TD>
	      <TD  class= input5 width="">
	        	<input class="codeno" name=GoonGetMethod id=GoonGetMethod readonly>
				<input class="codename" name=GoonGetMethodName id=GoonGetMethodName readonly>
				</TD>
                    <td class="title5">&nbsp;</td>
                    <td class="input5">&nbsp;</td>
	    </TR>
	  </table>

	<Div  id= "BankInfo" style= "display: none">
	  <table  class= common>
	  	<TR class = common >
	      <TD  class= title width="">
	      ��������
	      </TD>
	      <TD  class= input width="">
	      	   	<Input class="codeno" name=GetBankCode id=GetBankCode readonly>
				<input class=codename name=BankName id=BankName readonly>
	      </TD>
	      <TD  class= title width="">
	      �����ʻ�</font>
	      </TD>
	      <TD  class= input width="">
					<Input class="common wid" name=GetBankAccNo id=GetBankAccNo type="text" readonly>
  			</TD>
	  	   <TD  class= title width="">
	      ��   ��
	      </TD>
	      <TD  class= input width="">
				 <Input class= "common wid"  name="GetAccName" id=GetAccName readonly>
	  	  </TD>
	  	</TR>
	  </table>
	</Div>

</Div>

<table>
	   	<tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGetProject);">
	      </td>
	      <td class= titleImg>
	        ��ȡ��Ŀ
	      </td>
	   	</tr>
</table>

   <Div id="divGetProject" style="display: ">
   	<table  class= common>
        	<tr  class= common>
          		<td style="text-align: left" colSpan=1>
        			<span id="spanPolGrid" >
        			</span>
        	  	</td>
        	</tr>
        </table>
   	     <table align='center'>
    </table>
   </Div>
<br>
<Div id= "divEdorquery" style="display:  ">
    <!--Input  class= cssButton type=Button hidden value="��  ��" onclick="edorTypeAGSave()"-->
    <Input  class= cssButton type=Button value="��  ��" onclick="returnParent()">
    <!--Input  class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();"-->
</Div>


	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" name="ContType">
	 <input type=hidden id="EdorNo" name="EdorNo">
	 <input type=hidden id="PolNo" name="PolNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";

</script>
</html>
