<%
//�������ƣ�TempFeeInputPrint.jsp
//�����ܣ����շ�Ʊ�ݴ�ӡ
//�������ڣ�2005-12-21 14:55
//������  ����Ρ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/InputButton.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="TempFeeInputPrint.js"></SCRIPT>
  <%@include file="TempFeeInputPrintInit.jsp"%>
  <title>���շ�Ʊ�ݴ�ӡ</title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  var comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraTitle">
    <!-- ��ѯ��Ϣ���� -->
    <table class= common border=0 width=100%>
      <tr>
	  		<td class= titleImg align= center>�������ѯ������</td>
	    </tr>
	  </table>

    <table  class= common align=center>
      <TR  class= common>
        <TD  class= title>��ӡƱ������</TD>
        <TD  class= input>
           <Input class="codeno" name=InvoiceType  CodeData="0|^0|֧Ʊ/��Ʊ����֪ͨ��^1|�ͻ�Ԥ���վ�^2|Ԥ�����ڱ����վ�" ondblClick="showCodeListEx('InvoiceType',[this,InvoiceTypeName],[0,1]);" onKeyUp="showCodeListKeyEx('InvoiceType',[this,InvoiceTypeName],[0,1]);"  ><input class=codename name=InvoiceTypeName >
        </TD>
      </TR>
      <TR  class= common>
        <TD  class= title>�ͻ�����</TD>
        <TD  class= input>
        	<Input NAME=AccName class=common>
          <Input NAME=AccNameHidden type=hidden class=common>
        </TD>
        <TD  class= title>�ɷ�����</TD>
        <TD  class= input>
          <Input class="coolDatePicker" dateFormat="short" name=MakeDate>
          <Input NAME=MakeDateHidden type=hidden class=common>
        </TD>
        <TD  class= title>�ɷѽ��</TD>
        <TD  class= input>
        	<Input NAME=PayMoney class=common>
          <Input NAME=PayMoneyHidden type=hidden class=common>
        </TD>
      </TR>
    </table>

   <!--֧Ʊ��Ʊ����֪ͨ��-->
    <Div id="InvoiceType0" style="display:none">
      <table class= common align=center>
      	<TR  class= common>
          <TD  class= title>�շѻ���</TD>
          <TD  class= input>
			        <Input class=codeno verify="�շѻ���|NOTNULL" name=ManageCom1 onDblClick="return showCodeList('station',[this,ManageCom1Name],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageCom1Name],[0,1]);"><input class=codename name=ManageCom1Name readonly=true elementtype=nacessary>
              <Input NAME=ManageCom1Hidden type=hidden class=common>           
          </TD>
          <TD  class= title>֧Ʊ����</TD>
          <TD  class= input>
            	<Input NAME=ChequeNo class=common elementtype=nacessary>
              <Input NAME=ChequeNoHidden type=hidden class=common>
          </TD>
          <TD  class= title></TD>
          <TD  class= input></TD>
        </TR>
      </table>

      <table class= common align=center>
        <TR  class= common align=center>
        	<TD  class= common>
        		<Input NAME=OtherNoHidden type=hidden class=common>
            <Input NAME=AccNoHidden type=hidden class=common>
            <Input NAME=AgentCodeHidden type=hidden class=common>
            <Input NAME=AgentNameHidden type=hidden class=common>
            <Input NAME=BankCodeHidden type=hidden class=common>
          </TD>
        </TR>
      </table>
    </Div>

    <!--�ͻ�Ԥ���վ�-->
    <Div id="InvoiceType1" style="display:none">
      <table class= common align=center>
        <TR  class= common>
          <TD  class= title>�շѻ���</TD>
          <TD  class= input>
			        <Input class=codeno name=ManageCom2 onDblClick="return showCodeList('station',[this,ManageCom2Name],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageCom2Name],[0,1]);"><input class=codename name=ManageCom2Name readonly=true elementtype=nacessary>
              <Input NAME=ManageCom2Hidden type=hidden class=common>
          </TD>
          <TD  class= title>Ԥ���</TD>
          <TD  class= input>
          	<Input NAME=TempfeeNo class=common>
          	<Input NAME=CustomerNoHidden type=hidden class=common>
            <Input NAME=CustomerNameHidden type=hidden class=common>
            <Input NAME=PayModeHidden type=hidden class=common>
            <Input NAME=TempfeeNoHidden type=hidden class=common>
            <Input NAME=PayDateHidden type=hidden class=common>
            <Input NAME=CollectorHidden type=hidden class=common>
          </TD>
          <TD  class= title>�ͻ����</TD>
          <TD  class= input>
          	<Input NAME=CustomerNo class=common elementtype=nacessary>
          	<Input NAME=ActuGetNoHidden type=hidden class=common>
          </TD>
        </TR>
      </table>
    </Div>


    <!--Ԥ�����ڱ����վ�-->
    <Div id="InvoiceType2" style="display:none">
      <table class= common align=center>
        <TR  class= common>
          <TD  class= title>�շѻ���</TD>
          <TD  class= input>
			        <Input class=codeno name=ManageCom3 onDblClick="return showCodeList('station',[this,ManageCom3Name],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageCom3Name],[0,1]);"><input class=codename name=ManageCom3Name readonly=true elementtype=nacessary>
              <Input NAME=ManageCom3Hidden type=hidden class=common>
          </TD>
          <TD  class= title>������</TD>
          <TD  class= input>
          	<Input NAME=ContNo class=common elementtype=nacessary>
            <Input NAME=AppntNameHidden type=hidden class=common>
            <Input NAME=PaytoDateHidden type=hidden class=common>
            <Input NAME=ContNoHidden type=hidden class=common>
            <Input NAME=TempfeeNo2Hidden type=hidden class=common>                                                  
            <Input NAME=OperatorHidden type=hidden class=common>
            <Input NAME=PayerHidden type=hidden class=common>
            <Input NAME=IDNoHidden type=hidden class=common>
          </TD>
          <TD  class= title></TD>
          <TD  class= input></TD>
        </TR>
      </table>
    </Div>

    <div>
      <TR class = common>
        <TD  class= title><INPUT VALUE="��  ѯ" TYPE=button class=cssButton onClick="TempfeeQuery();">
      </TR>
   </div>
    <!-- ���շ���Ϣ���б� -->
    <table>
    	<tr>
    		<td class= titleImg>
    			 ���շ���Ϣ
    		</td>
    	</tr>
    </table>
 
    <!--֧Ʊ��Ʊ���շ�-->
   	<Div  id= "divChequeGrid" align = center style= "display: none">
     	<table  class= common>
     		<tr  class= common>
   	  		<td text-align: left colSpan=1>
  					<span id="spanChequeGrid" >
  					</span>
 			  	</td>
  			</tr>
    	</table>
        <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();">
        <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();">
        <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();">
        <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
  	</Div>

    <!--�ͻ�Ԥ���վ�-->
   	<Div  id= "divCustomertGrid" align = center style= "display: none">
     	<table  class= common>
     		<tr  class= common>
   	  		<td text-align: left colSpan=1>
  					<span id="spanCustomertGrid" >
  					</span>
 			  	</td>
  			</tr>
    	</table>
        <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();">
        <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();">
        <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();">
        <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
  	</Div>

    <!--Ԥ�����ڱ����վ�-->
   	<Div  id= "divRNPremGrid" align = center style= "display: none">
     	<table  class= common>
     		<tr  class= common>
   	  		<td text-align: left colSpan=1>
  					<span id="spanRNPremGrid" >
  					</span>
 			  	</td>
  			</tr>
    	</table>
        <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();">
        <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();">
        <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();">
        <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
  	</Div>

  	<p>
    <table class= common border=0 width=100%>
      <tr>
      	<INPUT VALUE="�� ӡ" class= cssButton TYPE=button onClick="printInvoice();">
		  </tr>
	  </table>
    <div>
    	<INPUT VALUE="" TYPE=hidden name=serialNo>
      <INPUT VALUE="" TYPE=hidden name=prtseq>
    </div>
  </form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </body>
</html>
