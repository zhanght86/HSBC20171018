<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ProposalFee.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalFeeInit.jsp"%>
  <title>�ݽ�����Ϣ </title>
</head>      
<body  onload="initForm();" >
  <form action="./ProposalFeeSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- ������Ϣ���� -->
    <table class=common>
    <TR  class= common>
      <TD  class= title>
        ��������
      </TD>
      <TD  class= input>
        <Input class="readonly wid" readonly name=PolNo id="PolNo" >
        <Input class="readonly" readonly name=polType id="polType" type=hidden>
      </TD>
	  <TD  class= title></TD>
      <TD  class= input></TD>
	  <TD  class= title></TD>
      <TD  class= input></TD>
    </TR>
    </table>
    
    <!-- �ݽ�����Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJTempFee1);">
    		</td>
    		<td class= titleImg>
    			 �ݽ�����Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLJTempFee1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanFeeGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
	
  <INPUT VALUE="����" CLASS=cssButton TYPE=button onclick="submitForm();"> 					
  <INPUT VALUE="����" CLASS=cssButton TYPE=button onclick="top.close();"> 					

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
