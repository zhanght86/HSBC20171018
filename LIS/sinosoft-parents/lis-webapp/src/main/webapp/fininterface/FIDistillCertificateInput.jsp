<%@page contentType="text/html;charset=GBK" %>
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
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="FIDistillCertificateInput.js"></SCRIPT>
  <%@include file="FIDistillCertificateInit.jsp"%>
  <title>����ӿ���ȡ����</title>
</head>
<body  onload="initForm();initElementtype();" >
<form method=post name=fm id=fm action= "./FIDistillCertificateSave.jsp" target="fraSubmit">
   <table class= common border=0 width=100%>
     <tr>
      <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,mis);"></td>
	  <td class= titleImg align= center>���������ڣ�</td>
     </tr>
   </table>
   <Div id="mis" style="display: ''"><div class="maxbox1">
  
   <table  class= common align=center>
      	 
      <TR  class= common>
          <TD  class= title5>
            ��ʼ����
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#Bdate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Bdate id="Bdate"><span class="icon"><a onClick="laydate({elem: '#Bdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ��ֹ����
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#Edate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Edate id="Edate"><span class="icon"><a onClick="laydate({elem: '#Edate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>		    
    </table></Div> </div>
    <!--<INPUT  class=cssButton VALUE="���β�ѯ" TYPE=Button onclick="QueryTask();">-->
    <a href="javascript:void(0);" class="button" onClick="QueryTask();">���β�ѯ</a><br><br>
   
    <Div  id= "Task" style= "display: ''" >
      	<table  class= common align="left">
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanResultGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        
    	<p></p>
      				
    
     <p></p>
  
  <p></p>
  <center><INPUT VALUE="��  ҳ" class =  cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class =  cssButton93 TYPE=button onclick="getLastPage();"> </center>
   <!--<INPUT  class=cssButton VALUE="��ȡƾ֤" TYPE=Button onclick="PutCertificate();">-->
   <a href="javascript:void(0);" class="button" onClick="PutCertificate();">��ȡƾ֤</a><br>
		
</form>
</body>
</html>
