<%
//�������ƣ�AgentQueryInput.jsp
//�����ܣ�
//�������ڣ�2003-04-8
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./BankQueryInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./BankQueryInit.jsp"%>
  <title>���в�ѯ </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <!--ҵ��Ա��ѯ���� -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            </td>
            <td class= titleImg>
                ���в�ѯ����
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
     <table  class= common>
      <TR  class= common> 
        <TD  class= title> ���б��� </TD>
        <TD  class= input>   <Input name=BankCode class= common >  </TD>        
        <TD  class= title> ��������  </TD>
        <TD  class= input>   <Input name=BankName class= common >  </TD>
      </TR>   
     </table>
                   
	   <table> 
		    <tr>
		    <td><INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();"> </td>
		    <td><INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();"> 	</td>
        </tr> 
	   </table> 
  </Div>      
    <hr>      				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBankGrid);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divBankGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
    		<tr>
    			<td>
			      <INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
