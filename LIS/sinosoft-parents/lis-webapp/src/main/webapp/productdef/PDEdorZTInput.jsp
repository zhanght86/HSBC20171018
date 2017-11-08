<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDEdorZTInput.jsp
 //程序功能：保全退保描述
 //创建日期：2009-3-16
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
 <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDEdorZT.js"></SCRIPT>
 <%@include file="PDEdorZTInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDEdorZTSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>险种编码</td>
		<td class=input5>
			<input class="common" name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>险种名称</td>
		<td class=input5>
			<input class="common" name="RiskCodeName" readonly="readonly" >
		</td>
	</tr>
	<tr class=common>
		<td class=title5>保全项目编码</td>
		<td class=input5>
			<input class="common" name="EdorType" readonly="readonly" >
		</td>
		<td class=title5>保全项目名称</td>
		<td class=input5>
			<input class="common" name="EdorTypeName" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >描述表内容</td>
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
<input id=savabutton1 value="新  增" onclick="save()" class="cssButton" type="button" >
<input id=savabutton2 value="修  改" onclick="update()" class="cssButton" type="button" >
<input id=savabutton3 value="删  除" onclick="del()" class="cssButton" type="button" >    
<br><br>                                                                                   
<input value="责任退保及算法定义" type=button  onclick="button278( )" class="cssButton" type="button" >
<br><br>
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMEdorZT">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
