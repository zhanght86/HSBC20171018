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

  <SCRIPT src="PDPrtRiskModifyQueryInput.js"></SCRIPT> 
    
  <title>产品修改查询</title>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();" >
  <form  method=post name=fm target="">
    <table class= common border=0 width=100%>
    	<tr>
		   <td class= titleImg align= center>请输入查询条件：</td>
	    </tr>
    </table>
	<table  class=common align=center>
			<TR class=common>
				<TD class= title>险种编码</TD>
				<TD class= input><Input class=codeNo name=RiskCode ondblclick="return showCodeList('pdrisk',[this,RiskName],[0,1]);" 
										onkeyup="return showCodeListKey('pdrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName readonly="readonly"><font color=red>*</font>
				<TD class= title>修改申请日期从</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=StartDate> </TD>				
				<TD class= title>到</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=EndDate> </TD>
			</TR>
	</table>        	
	<hr>
    <INPUT VALUE="查询并打印" class= CssButton TYPE=button onclick="submitForm();"> 	    
  	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>