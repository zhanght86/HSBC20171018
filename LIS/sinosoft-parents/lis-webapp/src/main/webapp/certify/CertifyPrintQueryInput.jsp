<%
//�������ƣ�CertifyPrintQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-10-14 10:20:49
//������  ��kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./CertifyPrintQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./CertifyPrintQueryInit.jsp"%>
  <title>��֤ӡˢ�� </title>
</head>
<body  onload="initForm();" >
  <form action="./CertifyPrintQuerySubmit.jsp" method=post name=fm target="fraSubmit">
      <table class="common">
	    <tr class="common">
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class=titleImg>�������ѯ������</td>
		</tr>
	  </table>
	  <div class=maxbox>
      <table class="common">
        <tr class="common">
          <td class="title5">ӡˢ���κ�</td>
          <td class="input5"><input class="wid common" name="PrtNo"></td>
          
          <td class="title5">��֤����</td>
          <td class="input5"><input class="code" name="CertifyCode" id=CertifyCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('CertifyCode', [this]);" onkeyup="return showCodeList('CertifyCode', [this]);"></td>
          
        <tr class="common">
          <td class="title5">�����</td>
          <td class="input5"><input class="wid common" name="MaxMoney" id=MaxMoney></td>
          
          <td class="title5">�������</td>
          <td class="input5"><input class="wid common" name="MaxDate" id=MaxDate></td></tr>
          
        <tr class="common">
          <td class="title5">��֤�۸�</td>
          <td class="input5"><input class="wid common" name="CertifyPrice" id=CertifyPrice></td>
          
          <td class="title5">�������</td>
          <td class="input5"><input class="wid common" name="ManageCom" id=ManageCom></td></tr>
          
        <tr class="common">
          <td class="title5">��ʼ��</td>
          <td class="input5"><input class="wid common" name="StartNo" id=StartNo></td>
          
          <td class="title5">��ֹ��</td>
          <td class="input5"><input class="wid common" name="EndNo" id=EndNo></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">���̱���</td>
          <td class="input5"><input class="wid common" name=ComCode id=ComCode ></td>
          
          <td class="title5">�绰</td>
          <td class="input5"><input class="wid common" name=Phone id=Phone ></td></tr>

        <tr class="common">
          <td class="title5">��������Ա</td>
          <td class="input5"><input class="wid common" name="OperatorInput" id=OperatorInput></td>
          
          <td class="title5">��ϵ��</td>
          <td class="input5"><input class="wid common" name=LinkMan id=LinkMan ></td></tr>
          
        <tr class="common">
          <td class="title5">��������</td>
          <td class="input5"><input class="wid common" name="InputDate" id=InputDate></td>

          <td class="title5">������������</td>
          <td class="input5"><input class="wid common" name=InputMakeDate id=InputMakeDate ></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">�ᵥ����Ա</td>
          <td class="input5"><input class="wid common" name=OperatorGet id=OperatorGet ></td>
          
          <td class="title5">�ᵥ��</td>
          <td class="input5"><input class="wid common" name=GetMan id=GetMan ></td></tr>
          
        <tr class="common">
          <td class="title5">�ᵥ����</td>
          <td class="input5"><input class="wid common" name=GetDate id=GetDate ></td>
          
          <td class="title5">�ᵥ��������</td>
          <td class="input5"><input class="wid common" name=GetMakeDate id=GetMakeDate ></td></tr>
          
      </table>

    </table>
          <INPUT VALUE="��ѯ" TYPE=button class="cssButton" onclick="submitForm();return false;"> 
          <INPUT VALUE="����" TYPE=button class="cssButton" onclick="returnParent();"> 
    </div>		  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCardPrintGrid);">
    		</td>
    		<td class= titleImg>
    			 ��֤ӡˢ����
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCertifyPrintGrid" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanCertifyPrintGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE=" ��ҳ " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <INPUT VALUE=" βҳ " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
  	
  	<input type=hidden name="sql_where" id=sql_where>
  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
