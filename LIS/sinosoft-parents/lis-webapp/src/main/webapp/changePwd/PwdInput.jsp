<%@page contentType='text/html;charset=GBK' %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script src="../common/javascript/Common.js"></script> 
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<script type="text/javascript" src="../common/javascript/encrypt/jQuery.dPassword.min.js"></script>
<script src="../common/javascript/encrypt/Encrypt.min.js"></script>
<Script src="PwdInput.js"></script>

<link rel='stylesheet' type='text/css' href='../common/css/Project.css'>
<link rel='stylesheet' type='text/css' href='../common/css/Project3.css'>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
</head>
<script>

</script>

<body>

  <form name="fm"  action="./changPwd.jsp" method=post name=fm id=fm target="fraSubmit">
	
	<div class=maxbox1>
	<TABLE>

      <TR  class= common> 
          <TD  class= title>
            请输入原密码：
          </TD>  
          <TD  class= input>
            <input type="password" name=oldPwd id=oldPwd class="wid common" maxlength= 48>
          </TD>
          <TD  class= title>
            请输入新密码：
          </TD>  
          <TD  class= input>
            <input type="password" name=newPwd id=newPwd class="wid common" maxlength= 48>
          </TD>
          <TD  class= title>
            请确认新密码：
          </TD>  
          <TD  class= input>
            <input type="password" name=confirmPwd id=confirmPwd  class="wid common" maxlength= 48>
          </TD>
     </TR>
     <TR>
   
     </TR>
   </TABLE>

     <INPUT VALUE="确定" class= cssButton TYPE=button onClick="return submitForm();">  
     <INPUT VALUE="重置" class= cssButton TYPE=button onclick="resetForm()">  
    </div>

 </form>

</body>
</html>
