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

  <SCRIPT src="PDPrtProblemQueryInput.js"></SCRIPT>  
  <%@include file="PDPrtProblemQueryInit.jsp"%>
    
  <title>�����״̬��ѯ</title>
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
				<TD class= title>����</TD>
				<TD class= input><Input class=codeNo name=RiskCode ondblclick="return showCodeList('pdrisk',[this,RiskName],[0,1]);" 
										onkeyup="return showCodeListKey('pdrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName>		
				<TD class= title>�����</TD>
				<TD class= input><Input class="common" name=issueCreater ></TD>
				<TD class= title>������</TD>
				<TD class= input><Input class="common" name=issueDealPer ></TD>
			</TR>			
			<TR class=common>
				<TD class= title>�������������</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=StartDate> </TD>				
				<TD class= title>��</TD>
				<TD class= input><Input class="multiDatePicker" dateFormat="short" name=EndDate> </TD>
				<TD class= title></TD>
				<TD class= input></TD>
			</TR>
	</table>        	
	
    <INPUT VALUE="��ѯ" class= CssButton TYPE=button onclick="easyQueryClick();"> 
	
    <table>
    	<tr>
          <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ">
    	  </td>
    	  <td class= titleImg>��ѯ���</td>
    	</tr>
    </table>
  	<Div  id= "divRiskInfo" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanRiskStateInfoGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��ҳ" class= CssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= CssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= CssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class= CssButton TYPE=button onclick="getLastPage();"> 	
  	</div>
  	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>