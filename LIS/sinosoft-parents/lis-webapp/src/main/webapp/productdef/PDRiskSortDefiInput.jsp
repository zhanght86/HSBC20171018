<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRiskSortDefiInput.jsp
 //程序功能：险种分类定义
 //创建日期：2009-3-12
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
 <%@include file="buttonshow.jsp"%> 
 <SCRIPT src="PDRiskSortDefi.js"></SCRIPT>
 <%@include file="PDRiskSortDefiInit.jsp"%>
</SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskSortDefiSave.jsp" method=post name=fm target="fraSubmit">

<table class=common>
	<TR class=common>
		<TD class=title5>险种代码</TD>
		<TD class=input5>
			<input class=common name=RiskCode readonly="readonly">
		</TD>
		<TD class=title5>申请日期</TD>
		<td class=input5>
            <input class="coolDatePicker" dateFormat="short" id="RequDate"  name="RequDate" onClick="laydate
({elem:'#RequDate'});" > <span class="icon"><a onClick="laydate({elem: '#RequDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
		</td>
	</TR>
</Table>		
<table>
  <tr>
    <td class="titleImg" >险种分类属性定义</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<input id=savabutton value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRiskSort">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
