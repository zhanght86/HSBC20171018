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
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./BankQueryInit.jsp"%>
  <title>���в�ѯ </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id="fm" target="fraSubmit">
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
    <div class="maxbox1">
  <Div  id= "divLAAgent" style= "display: ''">
     <table  class= common>
      <TR  class= common> 
        <TD  class= title> ���б��� </TD>
        <TD  class= input>   <Input name=BankCode id="BankCode" class="common wid">  </TD>        
        <TD  class= title> ��������  </TD>
        <TD  class= input>   <Input name=BankName id="BankName" class="common wid">  </TD>
        <td class= title></td>
        <td class= input></td>
      </TR>   
     </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
  
		    <!-- <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();">
		    <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();">  -->     				
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
      <div style= "display: none">
      <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 			      
      <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"> 
      </div>						
  	</div>
    <a href="javascript:void(0)" class=button onclick="returnParent();">��  ��</a>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
  <br>
  <br>
  <br>
  <br>
</body>
</html>
