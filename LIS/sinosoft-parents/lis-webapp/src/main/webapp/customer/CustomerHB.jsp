<%
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css> 
  <SCRIPT src="CustomerHB.js"></SCRIPT>
  <%@include file="CustomerHBInit.jsp"%>
  <title>�ͻ��ϲ�</title>
</head>

<body  onload="initForm(); initElementtype();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
  <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
	        <TD  class= title5>�ͻ��˻�����</TD>
	        <TD  class= input5>
				    <Input class= "common wid" name=InsuAccNo id="InsuAccNo">
	        </TD>
	        <TD  class= title5> �ͻ�����</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name=CustomerNo id="CustomerNo">
	        </TD>	        
        </TR>
        <TR  class= common>
	        <TD  class= title5> �ͻ�����</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name=CustomerName id="CustomerName">
	        </TD>	        
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
  <!-- <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();"> -->
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 �ͻ��˻���Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCustomerGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none">
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onclick="getLastPage();"> 	
      </div>
    </Div> 				
  	</div>
  	<p>
  	
  	</p>
  	<table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
        </td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
  <div class="maxbox1">
    <Div  id= "divFCDay1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
	        <TD  class= title5>�ͻ��˻�����</TD>
	        <TD  class= input5>
				<Input class= "common wid" name="InsuAccNo1" id="InsuAccNo1">
	        </TD>
	        <TD  class= title5> �ͻ�����</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name="CustomerNo1" id="CustomerNo1" >
	        </TD>	        
        </TR>
        
  <TR  class= common>
	        <TD  class= title5> �ͻ�����</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name="CustomerName1" id="CustomerName1" >
	        </TD>	        
        </TR>
        
       
    </table>
  </Div>
  </div>
    <br>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick1();">��  ѯ</a>
  <!-- <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick1();"> -->
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
        </td>
        <td class= titleImg>
           ����ͻ��˻���Ϣ
        </td>
      </tr>
    </table>
  	<Div  id= "divLCPol2" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCustomer1Grid" >
  					</span> 
  			  	</td>
  			  </tr>
    	  </table>
        <div style= "display: none">
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onclick="getLastPage();"> 	
        </div>
    </Div>
  	<p> 
    <a href="javascript:void(0)" class=button name="signbutton" id="signbutton" onclick="autochk();">��  ��</a> 
		<!-- <INPUT VALUE="��  ��" class=cssButton TYPE=button name="signbutton" id="signbutton" onclick = "autochk();">  -->
  	
  	<Input class=common name=currentDate id="currentDate" type=hidden>	 
  	<Input class=common name=PolicyCom id="PolicyCom" type=hidden>	 
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
