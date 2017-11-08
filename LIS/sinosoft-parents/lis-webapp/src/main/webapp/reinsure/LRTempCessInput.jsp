<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：LRTempCessInput.jsp
//程序功能：再保审核功能
//创建日期：2007-3-30 15:22
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="LRTempCessInput.js"></SCRIPT>
<%@include file="LRTempCessInit.jsp"%>
<title>再保回复</title>

</head>
<body  onload="initForm();" >
<form name=fm method="post" target="fraSubmit" action="./CessTempConclusionSave.jsp">
  <%@include file="../common/jsp/InputButton.jsp"%>
  <table>
  	<tr>
      	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchCondition);">
  	   	</td>
  		  <td class= titleImg>
查询条件
  	   	</td>
  	</tr>
	</table>
	<Div  id= "divSearchCondition" style= "display: ''">
		<Table class= common>
	 		<TR class= common>
	 			<TD class= title5>被保险人编码</TD>
	 			<TD class= input5>
	 				<input class=common name="InsuredNo">
	 			</TD>
	 			<TD class= title5>被保人姓名</TD>
	 			<TD class= input5>
	 				<input class=common name="InsuredName">
	 			</TD>
	 			<TD class= title5>险种编码</TD>
	 			<TD class="input5">
	 				<input class=common name="RiskCode">
	 			</TD>
	 		</TR>
	 		<TR>
	 			<TD class= title5>保单状态</TD>
	      <TD class= input5 >
	      	<input class=codeno readonly="readonly" name="AppFlag" CodeData="0|^0|未签单|^1|已签单|" 
	        ondblclick="return showCodeListEx('audittype', [this,AppFlagName],[0,1],null,null,null,1);" 
	        onkeyup="return showCodeListKeyEx('audittype', [this,AppFlagName],[0,1],null,null,null,1);" ><input 
	        class=codename name=AppFlagName readonly="readonly" elementtype=nacessary>
	      </TD>
	      <TD class= title5>临分结论</TD>
	      <TD class= input5>
	      	<input class=codeno readonly="readonly" name="TempContClusion" CodeData="0|^0|已下临分结论|^1|未下临分结论|" 
	        ondblclick="return showCodeListEx('audittype', [this,TempContClusionName],[0,1],null,null,null,1);" 
	        onkeyup="return showCodeListKeyEx('audittype', [this,TempContClusionName],[0,1],null,null,null,1);" ><input 
	        class=codename name=TempContClusionName readonly="readonly" elementtype=nacessary>
	      </TD>
	      <TD class= title5></TD>
	      <TD class= input5></TD>
	    </TR> 
	  </Table>
	  <INPUT VALUE="查询" class= cssButton TYPE=button onclick="QueryRiskInfo();">&nbsp;&nbsp;
	  <INPUT VALUE="重置" class= cssButton TYPE=button onclick="resetForm();">
	</Div>
	<table>
	  <tr>
	    <td class=common>
		  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);">
	    </td>
	  	<td class= titleImg> 
责任保单信息
	    </td>
	  </tr>
	</table>
	<Div  id= "divRiskInfo" style= "display: ''"> 
	  <table  class= common>
	    	<tr  class= common>
	    		<td style="text-align:left;" colSpan=1> 
					  <span id="spanRiskInfoGrid"> 
					  </span> 
			    </td>
		  </tr>
		</table>   
		<Div id= "div1" align="center" style= "display: '' ">
			<INPUT VALUE="首页" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
		  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
		  <INPUT VALUE="尾页" class=cssButton TYPE=button onclick="turnPage.lastPage();">  
		</Div>
	</div>
	<br>
	<INPUT VALUE="再保回复" class= cssButton TYPE=button onclick="ReinsureAnswer();">
	<br><br>
	<table>
	  <tr>
	    <td class=common>
		  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPrecept);">
	    </td>
	  	<td class= titleImg> 
再保方案
	    </td>
	  </tr>
	</table>
	<Div id="divPrecept" style= "display: ''"> 
	  <table  class= common>
	    	<tr  class= common>
	    		<td style="text-align:left;" colSpan=1> 
					  <span id="spanPreceptGrid"> 
					  </span> 
			    </td>
		  </tr>
		</table>
	</div>
	<br> <hr>
	<INPUT VALUE="临分结论" class= cssButton TYPE=button onclick="TempCessConcls();">&nbsp;&nbsp;
	
  <input type="hidden" name="ProposalNo">
  <input type="hidden" name="PrtNo">
</form>  


<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>