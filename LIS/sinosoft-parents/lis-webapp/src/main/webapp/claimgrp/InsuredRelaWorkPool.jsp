<%
//�������ƣ�InsuredRelaWorkPool.jsp
//�����ܣ������������˲���
//�������ڣ�2009-04-13 10:49:57
%>

<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="InsuredRelaWorkPool.js"></SCRIPT>
  <%@include file="InsuredRelaWorkPoolInit.jsp"%>
  
  <title>Ͷ����ɨ�����ѯ </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <!-- ��ѯ���� -->  
    <div class="maxbox1">      		         
    <table  class= common>
      	<TR  class= common>
            <TD  class= title5>Ͷ������</TD>
            <TD  class= input5><Input class="wid" class= common name=PrtNo id=PrtNo > </TD>
            <TD  class= title5>�����ͬ��</TD>
            <TD  class= input5><Input class="wid" class= common name=GrpContNo id=GrpContNo > </TD>
            <!--<TD  class= title>��ͬ��</TD>
            <TD  class= input><Input class= common name=ContNo ></TD>  -->
    	</TR>
      	<!-- <TR  class= common>
            <TD  class= title>��֤��</TD>
            <TD  class= input><Input class= common name=CertifyNo > </TD>
    	</TR> -->
    </table></div>
    <!--<INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">-->
<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
  	
  	<!-- ������Ϣ���� -->  
  	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>�ŵ���Ϣ</td>
    	</tr>
    </table> 
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
  			</tr>
    	</table>
        <Div  id= "divPage" align=center style= "display: '' ">
          <INPUT CLASS=cssButton90 VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <INPUT CLASS=cssButton93 VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
        </Div> 	
  	</div>
  	<!--<hr>
    <table>
    	<tr>
    		<td class= titleImg><INPUT VALUE="������" class= cssButton TYPE=button onclick="addNameClick();"></td>
    	</tr>
    </table> -->
    
    <input type=hidden id="ManageCom" name="ManageCom">
	<input type=hidden id="Operator" name="Operator">
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
