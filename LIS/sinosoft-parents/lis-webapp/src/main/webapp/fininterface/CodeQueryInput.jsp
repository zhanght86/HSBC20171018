<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CodeQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:48
//������  ��CrtHtml���򴴽�
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
  <SCRIPT src="./CodeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./CodeQueryInit.jsp"%>
  <title>���ô���� </title>
</head>
<body  onload="initForm();" >
  <form action="./CodeQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid1);">
      </td>
      <td class= titleImg>
        ���������ѯ������ 
      </td>
    	</tr>   
    </table>
  <Div  id= "divCodeGrid1" style= "display: ''">    
  <div class="maxbox1">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>
      ��������
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CodeType >
    </TD>
    <TD  class= title5>
      ����
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=Code >
    </TD>
  </TR>
</table>
 </Div></div>
          <!--<INPUT VALUE="��ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton"> -->
           	<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>				
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
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <!--<div align="left">
        <INPUT VALUE="����" TYPE=button onclick="returnParent();" class="cssButton"></div>-->
        <div align="center">
        <INPUT VALUE="��ҳ" TYPE=button onclick="getFirstPage();" class="cssButton90"> 
      <INPUT VALUE="��һҳ" TYPE=button onclick="getPreviousPage();" class="cssButton91"> 					
      <INPUT VALUE="��һҳ" TYPE=button onclick="getNextPage();" class="cssButton92"> 
      <INPUT VALUE="βҳ" TYPE=button onclick="getLastPage();" class="cssButton93"> 	</div>				
  	</div>
    <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
