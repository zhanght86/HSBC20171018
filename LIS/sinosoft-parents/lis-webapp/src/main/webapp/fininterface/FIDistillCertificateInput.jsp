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
  <title>财务接口提取数据</title>
</head>
<body  onload="initForm();initElementtype();" >
<form method=post name=fm id=fm action= "./FIDistillCertificateSave.jsp" target="fraSubmit">
   <table class= common border=0 width=100%>
     <tr>
      <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,mis);"></td>
	  <td class= titleImg align= center>请输入日期：</td>
     </tr>
   </table>
   <Div id="mis" style="display: ''"><div class="maxbox1">
  
   <table  class= common align=center>
      	 
      <TR  class= common>
          <TD  class= title5>
            起始日期
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#Bdate'});" verify="有效开始日期|DATE" dateFormat="short" name=Bdate id="Bdate"><span class="icon"><a onClick="laydate({elem: '#Bdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            终止日期
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#Edate'});" verify="有效开始日期|DATE" dateFormat="short" name=Edate id="Edate"><span class="icon"><a onClick="laydate({elem: '#Edate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>		    
    </table></Div> </div>
    <!--<INPUT  class=cssButton VALUE="批次查询" TYPE=Button onclick="QueryTask();">-->
    <a href="javascript:void(0);" class="button" onClick="QueryTask();">批次查询</a><br><br>
   
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
  <center><INPUT VALUE="首  页" class =  cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class =  cssButton93 TYPE=button onclick="getLastPage();"> </center>
   <!--<INPUT  class=cssButton VALUE="提取凭证" TYPE=Button onclick="PutCertificate();">-->
   <a href="javascript:void(0);" class="button" onClick="PutCertificate();">提取凭证</a><br>
		
</form>
</body>
</html>
