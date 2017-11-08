<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDCalFactorQueryInput.jsp
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
 
 <SCRIPT src="PDCalFactorQuery.js"></SCRIPT>
 <%@include file="PDCalFactorQueryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDCalFactorQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >产品算法要素查询条件：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>要素类别</td>
		<td class=input5>
			<input class="codeno" name="FactorType" readonly="readonly" ondblclick="return showCodeList('pdfactortype',[this,FactorTypeName],[0,1]);" onkeyup="return showCodeListKey('pdfactortype',[this,FactorTypeName],[0,1]);" ><input class="codename" name="FactorTypeName" >
		</td>
		<td class=title5>性质</td>
		<td class=input5>
			<input class="codeno" name="Kind" readonly="readonly" ondblclick="return showCodeList('pdproperty',[this,PropertyName],[0,1]);" onkeyup="return showCodeListKey('pdproperty',[this,PropertyName],[0,1]);" ><input class="codename" name="PropertyName" >
		</td>	
	</tr>
	<tr class=common>
		<td class=title5>模块</td>
		<td class=input5>
			<input class="codeno" name="Module" readonly="readonly" ondblclick="return showCodeList('pdmoduletype',[this,ModuleName],[0,1]);" onkeyup="return showCodeListKey('pdmoduletype',[this,ModuleName],[0,1]);" ><input class="codename" name="ModuleName" >
		</td>
		<td class=title5>要素代码</td>
		<td class=input5>
			<input class=common name="FactorCode" >
		</td>
	</tr>
	<tr class=common>
		<td class=title5 style="display: none">要素值数据类型</td>
		<td class=input5 style="display: none">
			<input class=common name="FactorValType">
		</td>
		<td class=title5>要素含义说明</td>
		<td class=input5>
			<input class=common name="FactorDesc">
		</td>
	</tr>
</table>
<input value="查询" type=button  onclick="query()" class="cssButton" type="button" >
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
<input type=hidden name="tableName" value="">
<input type=hidden name="selectTable">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
