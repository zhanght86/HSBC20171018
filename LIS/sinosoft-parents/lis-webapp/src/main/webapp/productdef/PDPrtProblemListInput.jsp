<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/Calendar/Calendar.js"></script>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="PDPrtProblemListInput.js"></SCRIPT> 
    
  <title>��Ʒ���������ͳ��</title>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();" >
  <form  method=post name=fm target="">
    <table class= common border=0 width=100%>
    	<tr>
		   <td class= titleImg align= center>�������ѯ������</td>
	    </tr>
    </table>
	<table  class=common align=center>
			<TR class=common>
				<TD class= title>��������ʱ���</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=StartDate> </TD>				
				<TD class= title>��</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=EndDate> </TD>
				<TD class= title>��������</TD>
				<TD class= input><Input class=codeno name=TestType CodeData="0|^0|�²�Ʒ����^1|�޸Ĳ���" ondblclick="return showCodeListEx('TestType', [this,TestTypeName],[0,1])"onkeyup="return showCodeListKeyEx('TestType', [this,TestTypeName],[0,1])"><Input class=codename name=TestTypeName readonly="readonly"></TD>
			</TR>
	</table>        	
	<hr>
    <INPUT VALUE="��ѯ����ӡ" class= CssButton TYPE=button onclick="submitForm();"> 	    
  	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>