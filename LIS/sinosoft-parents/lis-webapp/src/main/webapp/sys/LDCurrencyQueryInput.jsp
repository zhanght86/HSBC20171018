<%
//�������ƣ�LDCurrencyQueryInput.jsp
//�����ܣ�
//�������ڣ�2009-10-12 19:31:48
//������  ��ZhanPeng���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
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

  <SCRIPT src="./LDCurrencyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LDCurrencyQueryInit.jsp"%>
  <title> </title>
</head>
<body  onload="initForm();" >
  <form action="./OLDCodeQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class="maxbox1">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>
      ���ִ���
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CurrCode id=CurrCode >
    </TD>
    <TD  class= title5>
      ��������
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CurrName id=CurrName >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      ��Ч��־
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=Validation id=Validation >
    </TD>
    <TD  class= title5>
      �������
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CodeAlias id=CodeAlias >
    </TD>
  </TR>
</table>
</div>
          <!--<INPUT VALUE="��  ѯ" TYPE=button class=cssButton onclick="return easyQueryClick();"> 
          <INPUT VALUE="��  ��" TYPE=button class=cssButton onclick="returnParent();"> -->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
          		
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			�����ֲ�ѯ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
     	    <INPUT class = cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();">
     		<INPUT class = cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
     		<INPUT class = cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
     		<INPUT class = cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">	</center>				
  	</div>
     <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>			
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
