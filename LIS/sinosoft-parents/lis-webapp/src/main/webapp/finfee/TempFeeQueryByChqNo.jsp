<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<html>
<head>
<title>�ݽ�����Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" type="text/javascript" ></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./TempFeeQueryByChqNo.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="TempFeeQueryByChqNoInit.jsp"%>


</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm id=fm action=./TempFeeQueryResult.jsp target=fraSubmit method=post>
<script>
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
     <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
        </td>
        <td class= titleImg>
          �������ѯ������         
        </td>
      </tr>
    </table>
	<div class=maxbox1>
     <table  class= common >
      	<TR  class= common>
      	  <TD  class= title>
             �շѻ���
          </TD>
          <TD  class= input>
			<Input class=codeno name=ManageCom id=ManageCom verify="�շѻ���|code:comcode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);" >
			<input class=codename name=ManageComName id=ManageComName readonly=true>
		  </TD>
		  <!--<TD  class= title>
             �շѻ���
          </TD>          
          <TD  class= input>
							<Input class="readonly" name=ManageCom readonly=true >				 
		  </TD> -->
		  <!--<TD  class= title>
            �շѻ���
          </TD>
		  <TD class=input>
			     <Input class=codeno name=ManageCom verify="�շѻ���|notnull&code:station" ondblclick="return showCodeList('Station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
		  </TD>-->
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
				    <Input class=codeno name=PolicyCom id=PolicyCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this,PolicyComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1]);" >
					<input class=codename name=PolicyComName id=PolicyComName readonly=true>
          </TD>
          <!--<TD  class= title>
             �������
          </TD>          
          <TD  class= input>
							<Input class="readonly" name=PolicyCom readonly=true >				 
		  </TD>-->
		  <!--<TD  class= title>
            �������
          </TD>
		  <TD class=input>
			     <Input class=codeno name=PolicyCom verify="�������|notnull&code:station" ondblclick="return showCodeList('Station',[this,PolicyComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1]);"><input class=codename name=PolicyComName readonly=true>
		  </TD>-->
          <TD class=title>
            ���ѷ�ʽ
          </TD>
          <TD class=input>
            <Input class=code name=PayMode id=PayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('paymodequery',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('paymodequery',[this],null,null,null,null,1);">
          </TD>


          <!--TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= code name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">
          </TD-->
       </TR>
      	<TR  class= common>
          <TD  class= title>
          ���ѿ�ʼ����
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          ���ѽ�������
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          ����Ա
          </TD>
          <TD  class= input>
            <Input class="common wid" name=Operator id=Operator >
          </TD>
       </TR>
       <TR  class= common>
          <TD  class= title>
          ��С���
          </TD>
          <TD  class= input>
            <Input class="common wid" name=MinMoney id=MinMoney >
          </TD>

          <TD  class= title>
            �����
          </TD>
          <TD class= input>
            <Input class="common wid" name=MaxMoney id=MaxMoney >
          </TD>
          <TD class = title>
            ��������
          </TD>
          <TD class=input>
            <Input class="common wid" name=PrtNo id=PrtNo>
          </TD>
       </TR>

       <TR  class= common>
          <TD  class= title>
          ���˿�ʼ����
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EnterAccStartDate onClick="laydate({elem: '#EnterAccStartDate'});" id="EnterAccStartDate"><span class="icon"><a onClick="laydate({elem: '#EnterAccStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          ���˽�������
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EnterAccEndDate onClick="laydate({elem: '#EnterAccEndDate'});" id="EnterAccEndDate"><span class="icon"><a onClick="laydate({elem: '#EnterAccEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          ���վݺ�
          </TD>
          <TD  class= input>
            <Input class="common wid" name=TempFeeNo id=TempFeeNo >
          </TD>
        </TR>

       <TR  class= common>
          <TD  class= title>
             ��������
          </TD>
          <TD  class= input>
             <Input class=codeno name=PayType id=PayType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('paytype',[this,PayTypeName],[0,1]);" onkeyup="return showCodeListKey('paytype',[this,PayTypeName],[0,1]);">
			 <input class=codename name=PayTypeName id=PayTypeName readonly=true>
          </TD>
          <TD  class= title>
          	�ݽ���״̬
          </TD>
          <TD class= input>
            <Input class= code name=TempFeeStatus1 id=TempFeeStatus1  CodeData="0|^0|����^1|δ����^2|����^3|δ����^4|ȫ��" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" onkeyup="showCodeListKeyEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);">
          </TD>
          <TD class = title>
          </TD>
          <TD class=input>
          </TD>
       </TR>

      </table>

      <div id=divBankCode style="display:none">
        <table class=common align=center>
        <TR class=common>
          <TD class=title>
            ��������
          </TD>
    			<TD class=input>
					<Input class=codeno name=BankCode9 id=BankCode9 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('HeadOffice',[this,BankCode9Name],[0,1],null,'3','Branchtype',1);" onkeyup="return showCodeListKey('HeadOffice',[this,BankCode9Name],[0,1],[0,1],null,'3','Branchtype','1');">
					<input class=codename name=BankCode9Name id=BankCode9Name readonly=true>
								
    			</TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
         </table>
        </TR>

      </div>

        <div id=divBankCode7 style="display:none">
        	<table  class= common align=center>
        	<TR class=common>
            <TD class=title>
            ��������
            </TD>
    			  <TD class=input>
					<Input class=codeno name=BankCode7 id=BankCode7 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this,BankCode7Name],[0,1]);" onkeyup="return showCodeListKey('finbank',[this,BankCode7Name],[0,1]);">
					<input class=codename name=BankCode7Name id=BankCode7Name readonly=true>
    			  </TD>
    			<TD class=title>

          </TD>
          <TD class=input>

          </TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
        	</TR>
          </table>
        </div>

        <div id=InBankCode style='display:none'>
        	<table  class= common align=center>
        	<TR class=common>
            <TD class=title>
            ��������
            </TD>
    			  <TD class=input>
					<Input class=codeno name=InBankCode id=InBankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comtobank',[this,InBankCodeName],[0,1]);" onkeyup="return showCodeListKey('comtobank',[this,InBankCodeName],[0,1]);">
					<input class=codename name=InBankCodeName id=InBankCodeName readonly=true>
    			  </TD>
    			<TD class=title>

          </TD>
          <TD class=input>

          </TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
        	</TR>
          </table>
        </div>

      <div id=divChequeNo style='display:none'>
      <table class=common align=center>
        <TR class=common>
          <TD class=title>
            ֧Ʊ����
          </TD>
          <TD class=input>
            <Input class="common wid" name=ChequeNo id=ChequeNo>
          </TD>
          <TD class=title>
            ֧Ʊ����
          </TD>
          <TD class=input>
            <Input class="common wid" name=ChequeDate id=ChequeDate>
          </TD>

        </TR>

      </table>
      </div>

      <table align=right>
        <TR >

          <TD >
            <INPUT VALUE="��  ѯ" Class=cssButton TYPE=button onclick="easyQueryClick();">
          </TD>

       </TR>
      </Table>
   <BR><BR>
     <INPUT VALUE="����" Class=cssButton TYPE=button onclick="returnParent();">


    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursorhand;" OnClick= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 �ݽ�����Ϣ
    		</TD>
    	</TR>
    </Table>
 <Div  id= "divTempFee1" style= "display ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align left colSpan=1>
            <span id="spanTempQueryByChqNoGrid" ></span>
  	</TD>
      </TR>
    </Table>

	<center>
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
    </center>
    <table class=common>
          <TD  class= title>
          	���ͳ��
          </TD>
          <TD  class= input>
          	<Input class="readonly wid" name=SumMoney id=SumMoney readonly=true>
          </TD>
          <TD  class= title>

          </TD>
          <TD  class= input>

          </TD>
          <TD  class= title>

          </TD>
          <TD  class= input>

          </TD>

      <tr>
        <td>
          <INPUT CLASS=cssButton VALUE="��  ӡ" TYPE=button onclick="easyQueryPrint(2,'TempQueryByChqNoGrid','turnPage');">
        </td>
      </tr>
      <tr>
        <TD >
            <INPUT VALUE="   �����շ���ϸ   " Class=cssButton TYPE=button onclick="SFDetailExecel();">
        </TD>
        <!--TD >
            <INPUT VALUE="��������Լ�б���ϸ" Class=cssButton TYPE=button onclick="NCDetailExecel();">
        </TD-->
      </tr>

      <!------------------------------------------------Beg-->
	  <!--��Ӹ��嵥�򱨱��˵��-->
	  <!--�޸��ˣ�chenrong-->
	  <!--�޸����ڣ�2006-02-14-->
      <tr>
         <td colspan=6 class=common>
         	 <Table>
		        <TR>
		            <TD class=common>
		            	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivShow);">
		            </TD>
		            <TD class= titleImg>��;˵��:</TD>
		        </TR>
		    </Table>
		    <Div id= "DivShow" class=maxbox1 style= "display: ''">
		        <Table class=common>
		          <TR class=common>
		          	�г���ѯʱ����ڰ����ѷ�ʽ���ֵĽ��ѷ�ʽ��ѯ
		          </TR>
		        </Table>
	        </Div>
		  </td>
      </tr>
      <!--------------------------------------------End-->

    </table>

    <input type=hidden id="fmAction" name="fmAction">
 </Div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br /><br /><br /><br />
</Form>
</body>
</html>

