<%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
 String CurrentDate = PubFun.getCurrentDate();
%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="MoveData.js"></SCRIPT>
  <%@include file="MoveDataInit.jsp"%>
  <title>����ӿ�</title>
</head>
<body  onload="initForm();" >
  <form action="./MoveDataCommit.jsp" method=post name=fm id=fm target="fraTitle">    
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
  					<span id="spanMoveDataGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table>
  	</div>
  	
    <Div id= "divPage" align=center style= "display: 'none' ">
    	<center>
    		<INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();"> 
    		<INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();"> 					
    		<INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"> 
    		<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();">
		</center>
    </Div>  
   

    <!--INPUT VALUE="test" class= cssButton TYPE=button onclick="testtt()"-->
    <!--<INPUT VALUE= "��������" class= cssButton TYPE=button onclick="SubmitForm()">--> <br> 
    <a href="javascript:void(0);" class="button" onClick="SubmitForm();">��������</a>
    <INPUT VALUE="" TYPE=hidden name=sBatchNo>     
    <INPUT value=<%= tGI.ManageCom %> type=hidden name=cManageCom>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
