<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�DeleteSendToBankInput.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head > 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="DeleteSendToBankInput.js"></SCRIPT> 
  <%@include file="DeleteSendToBankInit.jsp"%>
  
  <title>�����������ļ� </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./DeleteSendToBankSave.jsp" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <!-- ������Ϣ���� -->
    
  <table class= common border=0 width=100%>
        <tr>
         <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
         </td>
		<td class= titleImg align= center>�������ѯ������</td>
	   </tr>
	</table>
	<div class="maxbox1" id="maxbox">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            ӡˢ��
          </TD>
          <TD  class= input>
            <Input NAME=PrtNo class="common wid">
          </TD>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>  
        </TR>
        <tr  class= common>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td> 
          <td class=title>
          </td>
        </TR>
    </table>
    </div> 
    <INPUT VALUE="��    ѯ" TYPE=button class=cssButton onClick="easyQueryClick();" name=butQuery >  
    <br>
    <!-- �����������ļ� fraSubmit-->  
     
    <!-- �ݽ�����Ϣ���б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBank1);">
    		</td>
    		<td class= titleImg>
    			 ������������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div id= "divBank1" style= "display: ''">
      	<table  class= common>
          	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table> 

  	
    <Div id= "divPage" align=center style= "display: none ">
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
    </Div>
    <table class= common border=0 width=100% >
    	<tr>
			<td class= titleImg >������;���ݲ���ȡ��</td>
  		</tr>
  	</table>
    </Div>
    <br><br><hr class="line"><br><br>
    
    <DIV id= "divBank2"  style= "display: none">      		  								
    <table  class= common align=center>     
    	<TR CLASS=common>
        <TD CLASS=title5>
          �ݽ��Ѻ� 
        </TD>
        <TD CLASS=input5 COLSPAN=1>
          <Input class="common wid" NAME=TempFeeNo id="TempFeeNo" VALUE="" >
        </TD>
        <TD CLASS=title5>
          ������ʽ 
        </TD>
        <TD CLASS=input5 COLSPAN=1>
          <Input class="common wid" NAME=Action id="Action" VALUE="" >
        </TD>
      </TR>
    </table>
    <br>
    </DIV>
    <table align=left>
      <tr>
        <td> 
    <INPUT VALUE="ȡ����������" class= cssButton TYPE=button onClick="submitForm()" name=butSave >
        </td>
        <td>
    <INPUT VALUE="ȡ����������" class= cssButton TYPE=button onClick="submitForm2()" name=butSave2>
        </td>
      </tr>
    </table>
    <INPUT VALUE="" TYPE=hidden name=serialNo>
 
  </form>
   <br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>

