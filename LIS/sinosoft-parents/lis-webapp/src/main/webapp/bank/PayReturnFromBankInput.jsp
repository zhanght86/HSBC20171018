<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�PayReturnFromBankInput.jsp
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

  <SCRIPT src="PayReturnFromBankInput.js"></SCRIPT>
  <%@include file="PayReturnFromBankInit.jsp"%>
  
  <title>���д��� </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./PayReturnFromBankSave.jsp" method=post name=fm  id=fmtarget="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- ���д��� fraSubmit-->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>��ѡ�����κţ�</td>
  		</tr>
  	</table>   
        
    <!-- ���κ���Ϣ���б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBank1);">
    		</td>
    		<td class= titleImg>
    			 ���κ���Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divBank1" style= "display: ''">
      	<table  class= common>
          	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table>
  	</div>
  	
    <Div id= "divPage" align=center style= "display: 'none' ">
    <INPUT CLASS="cssButton90" VALUE="��ҳ" TYPE=button onClick="turnPage.firstPage();"> 
    <INPUT CLASS="cssButton91" VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
    <INPUT CLASS="cssButton92" VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
    <INPUT CLASS="cssButton93" VALUE="βҳ" TYPE=button onClick="turnPage.lastPage();">
    </Div>

    
    <br>
    
    <table  class= common>
    <TR  class= common>
      <TD  class= title5>
        ���������з��ص��ܽ�
      </TD>
      <TD  class= input5>
        <Input class= common name=TotalMoney verify="�ܽ��|notnull&num" > 
      </TD>           
    </TR>
    </table>
    
   <!-- <INPUT VALUE="�������ش���" class= cssButton TYPE=button onClick="submitForm()">-->
   <a href="javascript:void(0);" class="button"onClick="submitForm()">�������ش���</a>
    <INPUT VALUE="" TYPE=hidden name=serialNo>
           										
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
