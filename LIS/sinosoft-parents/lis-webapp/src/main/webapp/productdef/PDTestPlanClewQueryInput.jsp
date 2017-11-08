<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDTestPointClewQueryInput.jsp
 //程序功能：测试要点提示查询界面
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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDTestPlanClewQueryInput.js"></SCRIPT>
 <%@include file="PDTestPlanClewQueryInputInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDTestPlanClewQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >测试方案查询条件：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<TD class=title5>测试计划分类</TD>
		<TD class=input5>
			<input class="codeno" id='plankind' name="planKind" ondblclick="return showCodeList('planKind',[this,planKindName],[0,1]);" onkeyup="return showCodeListKey('planKind',[this,planKindName],[0,1]);" readonly="readonly" verify="测试计划分类|notnull"><input class='codename' name='planKindName' elementtype=nacessary verify="测试计划分类|notnull" readonly="readonly" >
		</TD>	
		<TD class=title5 id='riskKindTitle'>险种分类</TD>
		<TD class=input5 id='riskKindContent'>
			<input class="codeno" name="riskkind" ondblclick="return showCodeList('riskkind',[this,riskKindName],[0,1]);" onkeyup="return showCodeListKey('riskkind',[this,riskKindName],[0,1]);" verify="险种分类|notnull" readonly="readonly"><input class='codename' name = 'riskKindName' readonly="readonly">
		</TD>
	</tr>
	<tr class=common>
		<td class=title5>测试要素</td>
		<td class=common>
		<input name=ClewContent >
		</td>
	</tr>
</table>
<input value="查询" type=button  onclick="queryMulline9Grid()" class="cssButton" type="button" >
<input value="返回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >查询结果</td>
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
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>

<input type=hidden name="operator">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
/span>
</body>
</html>
