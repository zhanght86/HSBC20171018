<html>
<%
//�������ƣ�ClientMergeInput.jsp
//�����ܣ�
//�������ڣ�2002-08-19
//������  ��kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="ClientMergeInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ClientMergeInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./ClientMergeSave.jsp" method=post name=fm target="fraSubmit">

	

    <table>
      <tr>
        <td><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></td>
        <td class= titleImg>�ͻ��ϲ���ѯ����</td></tr>
    </table>

    <div  id= "divLDPerson" style= "display: ''">
      <table class="common">
        <tr class="common">
          <td class="title">�ͻ�����</td>
          <td class="input"><Input class="common" name=Name ></td>
          
          <td class="title">�ͻ��Ա�</td>
          <td class="input"><Input class="codeno" name=AppntSex  verify="Ͷ�����Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName readonly=true ></td>
          
          <td class="title">�ͻ���������</td>
          <td class="input"><input class="coolDatePicker" dateFormat="short" name="Birthday"></td></tr>
          	
        <tr class="common">
        	<td class="title">֤������</td>
          <td class="input"><Input class="codeno" name="AppntIDType" verify="Ͷ����֤������|code:IDType" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName readonly=true></td>	
          
          <td class="title">֤������</td>
          <td class="input"><Input class="common" name=IDNo ></td>
     
          <td class="title"></td>
          <td class="input"><Input class="readonly" readonly name= ></td></tr>
         </table>
<input class=cssButton type="button" align=lift value=" ��  ѯ " onclick="queryClick()">
 
          
      <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 ԭ�ͻ���Ϣ 
	    	</td>
	    </tr>
	    </table>    
	<div id= "divOPolGrid" style= "display: ''" align=center >
        <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         
    </div>      
      
       
          
       

    
    </div>
    
    <!-- �ͻ��б� -->
    <table>
   	  <tr>
        <td class="common"><IMG  src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divClientList);"></td>
    	<td class= titleImg>��ͬ�ͻ���Ϣ�б�</td></tr>
    </table>
    
	<div id="divClientList" style="display: ''">
      <table  class= common>
        <tr  class= common>
          <td text-align: left colSpan=1><span id="spanClientList" ></span></td></tr>
	  </table>
	  <div id= "divCustomerUnionInfo" style= "display: ''" >
	 <table class= common>
    	<TR  class= common>
            	
          <TD class= title> ԭ�ͻ����� </td>
          <TD class= input><input class= readonly  name=CustomerNo_OLD readonly ></td>
           <TD class = title>  </td>
          <TD class= input><input class=readonly  name= readonly ></td>
          <TD class= title> �ϲ���ͻ����� </td>
          <TD class= input><input class= readonly  name=CustomerNo_NEW readonly ></td>
        </tr>
        <tr>
          <td class="title">�ϲ�����</td>
          <td class="input">
			      <input type="radio" name="exchangeRadio"  value="1"  OnClick="exchangeCustomerNo();" >���� 
			      <input type="radio" name="exchangeRadio"  value="-1" OnClick="exchangeCustomerNo();">����          
		      </td>
        </tr>
      </table>		
	</div>
	  <input class=cssButton type="button" value="�ͻ��ϲ�" onclick="ClientMerge()">
	</div>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
