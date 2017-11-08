<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRAccRDInput.jsp
//function :
//Creator :zhangbin
//date :2007-3-14
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./RICataRiskLevelInput.js"></SCRIPT> 
<%@include file="./RICataRiskLevelInit.jsp"%>
</head>
<body  onload="initElementtype();initForm();" >
<form action="" method=post name=fm target="fraSubmit">
<br>
	<Table class= common>  			
 	 		<TR class= common>
 	 			<TD class= title5>团险巨灾数据导入：</TD>     
		    <TD>
		      <Input type="file" name=FileName class=common >
		      <INPUT VALUE="导  入" class=cssButton TYPE=button onclick="RICataRiskImp();">
		    </TD>
		   
		  </TR>
	</Table>
<br><br>
	<table>
		<tr>
		    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
		    	OnClick= "showPage(this,divLLReport1);"></td>
			<td class= titleImg>巨灾风险级别分类</td>
		</tr>
	</table>
	<br>
	<Div id= "divLLReport1" style= "display:none;">
		<Table class= common>
			<TR class= common>
	   			<TD class= title5>年度</TD>
	   			<TD class= input5 >
	   				<Input class="code" name=CalYear verify="年度|NOTNULL&len=4&num"  
	   					ondblclick="showCodeList('bjhyear',[this],[0])" 
	   					onkeyup="showCodeListKey('bjhyear',[this],[0])" elementtype=nacessary>
	   			</TD>    
	   	</Table>
	</Div>  	
   	<INPUT  class=cssButton VALUE="风险级别分类统计" TYPE=Button onclick="catastropheQuery();">

   	<br><hr>
    <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divIncomeType);"></td>
    		<td class= titleImg>巨灾风险级别分类汇总</td></tr>
    </table>
   	<Div id= "divIncomeType" style= "display: ''">
		<Div  id= "divIncomeType1" style= "display: ''">
		    <table  class= common>
		        <tr  class= common>
		        	<td style="text-align:left;" colSpan=1>
		        		<span id="spanCatastropheGrid" ></span>
		      		</td>
		    	</tr>
		  	</table>
		</div>
	</Div>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
