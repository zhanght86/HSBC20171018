<%
//�������ƣ�OLDCodeQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:48
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

  <SCRIPT src="./OLDCodeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./OLDCodeQueryInit.jsp"%>
  <title>���ô���� </title>
</head>
<body  onload="initForm();" >
  <form action="./OLDCodeQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>
      ��������
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=CodeType >
    </TD>
    <TD  class= title5>
      ����
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=Code >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ��������
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=CodeName >
    </TD>
    <TD  class= title5>
      �������
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=CodeAlias >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      ��������
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=ComCode >
    </TD>
    <TD  class= title5>
      ������־
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=OtherSign >
    </TD>
  </TR>
</table>

          <INPUT VALUE="��ѯ" class="cssButton" TYPE=button onclick="submitForm();return false;"> 
          <INPUT VALUE="����" class="cssButton" TYPE=button onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ���ô������
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT class = cssButton90 VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class = cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class = cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class = cssButton93 VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">	  			
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
