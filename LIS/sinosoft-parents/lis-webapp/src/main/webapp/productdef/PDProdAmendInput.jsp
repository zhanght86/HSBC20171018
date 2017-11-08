<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDProdAmendInput.jsp
 //程序功能：产品修改
 //创建日期：2009-3-18
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
 <SCRIPT src="PDProdAmend.js"></SCRIPT>
 <%@include file="PDProdAmendInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDProdAmendSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >已上线产品查询：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>
产品险种代码
		</td>
		<td class=input5>
			<input class="code" name="RiskCode" ondblclick="return showCodeList('pdriskdeployed',[this],[0]);" onkeyup="return showCodeListKey('pdriskdeployed',[this],[0]);" >
		</td>
		<td class=title5>
申请日期
		</td>
		<TD class=input5><input class="coolDatePicker" dateFormat="short"
			name="RequDate" readonly id="RequDate"
			onClick="laydate({elem:'#RequDate'});"><span class="icon"><a
			onClick="laydate({elem: '#RequDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			</tr>
			<tr class=common>
		<td class=title5>
上线日期
		</td>
		<TD class=input5><input class="coolDatePicker" dateFormat="short"
			name="DeplDate" readonly id="DeplDate"
			onClick="laydate({elem:'#DeplDate'});"><span class="icon"><a
			onClick="laydate({elem: '#DeplDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			<td class=title5></td>
			<TD class=input5></TD>
			<td class=title5></td>
			<TD class=input5></TD>
	</tr>
</table>

<input value="查询" type=button  onclick="query()" class="cssButton" type="button" >
<br><br>
已上线产品：<BR>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<input value="记事本" onclick="button402( )" class="cssButton" type="hidden" >
<input value="申请" type=button  onclick="IssueInput( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
