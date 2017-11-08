<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDCalFactorInput.jsp
 //程序功能：算法要素库
 //创建日期：2009-3-17
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
 
 <SCRIPT src="PDCalFactor.js"></SCRIPT>
 <%@include file="PDCalFactorInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDCalFactorSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="保存" type=button name="btnSave" onclick="save()" class="cssButton" >
	<input value="修改" type=button  onclick="update()" class="cssButton" >
	<input value="查询" type=button  onclick="query()" class="cssButton" >
	<input value="删除" type=button  onclick="del()" class="cssButton" >
</div>
<table>
  <tr>
    <td class="titleImg" >产品算法要素</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>要素类别</td>
		<td class=input5>
			<input class="codeno" name="FactorType" readonly="readonly" verify="要素类别|NotNull" ondblclick="return showCodeList('pdfactortype',[this,FactorTypeName],[0,1]);" onkeyup="return showCodeListKey('pdfactortype',[this,FactorTypeName],[0,1]);" ><input class="codename" name="FactorTypeName" elementtype=nacessary>
		</td>
		<td class=title5>性质</td>
		<td class=input5>
			<input class="codeno" name="Kind" verify="性质|NotNull" readonly="readonly" ondblclick="return showCodeList('pdproperty',[this,PropertyName],[0,1]);" onkeyup="return showCodeListKey('pdproperty',[this,PropertyName],[0,1]);" ><input class="codename" name="PropertyName" elementtype=nacessary>
		</td>	
	</tr>
	<tr class=common>
		<td class=title5>模块</td>
		<td class=input5>
			<input class="codeno" name="Module" readonly="readonly" verify="模块|NotNull" ondblclick="return showCodeList('pdmoduletype',[this,ModuleName],[0,1]);" onkeyup="return showCodeListKey('pdmoduletype',[this,ModuleName],[0,1]);" ><input class="codename" name="ModuleName" elementtype=nacessary>
		</td>
		<td class=title5>要素代码</td>
		<td class=input5>
			<input class=common name="FactorCode" verify="要素类别|NotNull&Len<7" elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>要素值数据类型</td>
		<td class=input5>
			<input class=common name="FactorValType">
		</td>
		<td class=title5>要素含义说明</td>
		<td class=input5>
			<input class=common name="FactorDesc">
		</td>
	</tr>
</table>
<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_CalFactor_Lib">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
