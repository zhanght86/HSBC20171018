<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRiskParamsDefInput.jsp
 //程序功能：缴费字段控制定义
 //创建日期：2009-3-13
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
	 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="buttonshow.jsp"%> 
 <SCRIPT src="PDRiskParamsDef.js"></SCRIPT>

 <%@include file="PDRiskParamsDefInit.jsp"%>
 <%
 	String type;
 	if(((String)request.getParameter("type")).equals("1"))
 	type = ""+"缴费"+"";
   else 
   	type = ""+"给付"+"";
%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskParamsDefSave.jsp" method=post name=fm target="fraSubmit">


<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>险种<%=type%>编码</td>
		<td class=input5>
			<input class=common name="PayPlanCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >已保存<%=type%>控制字段</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">

<table>
  <tr>
    <td class="titleImg" ><%=type%>字段控制定义</td>
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
<br><br>
<div align=left id=savebuttonid><input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
</div>

<!-- <input id=definedparams value="字段参数定义" type=button  onclick="button127( )" class="cssButton" type="button" > -->
<br><br>
<input type=hidden name="operator">
<input type=hidden name="DutyCode">
<input type=hidden name="tableName" value="PD_LMDutyCtrl">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
