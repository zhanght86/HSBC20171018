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

  <SCRIPT src="ProductCallBackInput.js"></SCRIPT> 
  <%@include file="ProductCallBackInit.jsp"%>
  
  <title>��Ʒ�޸Ĳ�ѯ</title>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>
		   <td class= titleImg align= center>�������ѯ������</td>
	    </tr>
    </table>
	<table  class=common align=center>
			<TR class=common>
				<TD class= title>���ֱ���</TD>
				<TD class= input><Input class=codeNo name=RiskCode ondblclick="return showCodeList('pdrisk',[this,RiskName],[0,1]);" 
										onkeyup="return showCodeListKey('pdrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName readonly="readonly"><font color=red>*</font>
				<TD class= title>�޸��������ڴ�</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=StartDate> </TD>				
				<TD class= title>��</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=EndDate> </TD>
			</TR>
	</table>        	
	<hr>
    <INPUT VALUE="��ѯ" class= CssButton TYPE=button onclick="queryMulline10();"> 	    
  	
  	
  	<table  class= common>
	   <tr  class= common>
	      <td style="text-align:left;" colSpan=1>
	     <span id="spanMulline10Grid" >
	     </span> 
	      </td>
	   </tr>
	</table>
	
	<INPUT CLASS=cssbutton VALUE="��ҳ" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
	<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
	<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
	<INPUT CLASS=cssbutton VALUE="βҳ" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
	<table class = common>
		<tr class = common>
			<td align = 'right'>
				<input value="�޸���Ϣ��ѯ" type=button  onclick="modifyInfoQuery( )" class="cssButton" type="button" > 
				<input value="����" type=button  onclick="productBack( )" class="cssButton" type="button" > 
				<input value="��Ʒ��Ϣ��ѯ" type=button  onclick="queryRiskInfo( )" class="cssButton" type="button" > 
			</td>
		<tr>
	</table>
	
	<table class=common id= 'modifyInfoID' style = 'display:none'>
		<tr>
		   <td class= titleImg align= center>�޸���Ϣ��ѯ��</td>
	    </tr>
		<tr  class= common>
	      <td style="text-align:left;" colSpan=1>
	    	 <span id="spanMulline9Grid" >
	     	 </span> 
	      </td>
	    </tr>
	</table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>