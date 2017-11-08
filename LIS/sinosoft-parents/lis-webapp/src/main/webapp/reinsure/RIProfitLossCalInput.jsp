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

<SCRIPT src = "./RIProfitLossCalInput.js"></SCRIPT> 
<%@include file="./RIProfitLossCalInit.jsp"%>
</head>
<body  onload="initElementtype();initForm();" >
<form action="" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
    	OnClick= "showPage(this,divLLReport1);"></td>
	<td class= titleImg>盈余佣金计算</td></tr>
</table>
<Div id= "divLLReport1" style= "display: ''">
	<Table class= common>
		<TR class= common>
   			<TD class= title5>年度</TD>
   			<TD class= input5 >
   				<Input class="code" name=CalYear verify="年度|NOTNULL&len=4&num"  
   					ondblclick="showCodeList('riprofityear',[this],[0])" 
   					onkeyup="showCodeListKey('riprofityear',[this],[0])" elementtype=nacessary>
   			</TD>
   			<TD  class=title5>分保公司</TD>
			<TD  class= input5 > 
		      	<Input class="codeno" name="RIcomCode" 
				    ondblClick="showCodeList('riincompany',[this,RIcomName],[0,1],null,null,null,1);"
				    onkeyup="showCodeListKey('riincompany',[this,RIcomName],[0,1],null,null,null,1);" verify="分保公司|NOTNULL" ><Input 
				    class=codename name='RIcomName'  elementtype=nacessary >
			</TD>
   			<TD  class=title >合同名称</TD>
	        <TD  class=input > 
	        	<Input class="codeno" name="ContNo" 
			      ondblClick="showCodeList('ricontno',[this,ContName],[0,1],null,null,null,1,260);"
			      onkeyup="showCodeListKey('ricontno',[this,ContName],[0,1],null,null,null,1,260);" verify="合同名称|NOTNULL"><Input 
			      class= codename name='ContName'  elementtype=nacessary >
	        </TD> 
		</TR>      
		<TR>
			<TD class= title5>计算结果</TD>
			<TD class= input5>
				<Input class= common name= CalResult id="CalResultID" readonly="readonly" > 
			</TD>
			<TD class= title5>
				<div id="divSerialId" style="display:none;">序号</div>
			</TD>
			<TD class= input5>
				<Input class= common name= SerialNo id="SerialId" readonly="readonly" style="display:none;"> 
			</TD>
      		<TD class= title5></TD>
      		<TD class= input5>
      		</TD>
		</TR>
   	</Table>
</Div>  	
   	<INPUT  class=cssButton VALUE="初始化参数" TYPE=Button onclick="initParameter();">
   	<INPUT  class=cssButton VALUE="&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;" TYPE=Button onclick="queryClick();">
   	<br><hr>
    <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divIncomeType);"></td>
    		<td class= titleImg>计算参数</td></tr>
    </table>
   	<Div id= "divIncomeType" style= "display: ''">
		<Div  id= "divIncomeType1" style= "display: ''">
		    <table  class= common>
		        <tr  class= common>
		        	<td style="text-align:left;" colSpan=1>
		        		<span id="spanIncomeGrid" ></span>
		      		</td>
		    	</tr>
		  	</table>
		</div>
	</Div>
		
	<Div  id= "divPayoutType1" style= "display:none;">
		<table>
		  	<tr>
		  	    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
		  	    	OnClick= "showPage(this,divPayoutType);"></td>
		  		<td class= titleImg>支出</td>
		  	</tr>
	  	</table>
	</Div>
	<Div  id= "divPayoutType" style= "display:none;">
		<table  class= common>
			<tr  class= common>
		        <td style="text-align:left;" colSpan=1>
		      		<span id="spanPayoutGrid" ></span>
		    	</td>
			</tr>
		</table>
	</Div>
	<br>
	<INPUT  class=cssButton VALUE="计  算" TYPE=Button onclick="submitForm();">
	<br><br><hr>

	<input type="hidden" name="OperateType" >
	<input type="hidden" name="DeTailType" >
  
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
