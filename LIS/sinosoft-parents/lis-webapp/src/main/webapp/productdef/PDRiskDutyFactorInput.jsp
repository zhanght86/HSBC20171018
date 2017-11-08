<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRiskDutyFactorInput.jsp
 //程序功能：责任录入要素定义
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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDRiskDutyFactor.js"></SCRIPT>
 <%@include file="PDRiskDutyFactorInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskDutyFactorSave.jsp" method=post name=fm target="fraSubmit">

<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input type='hidden' name="RiskCode" readonly="readonly" >
			<input class=common  name="RiskCode1" value = '<%=request.getParameter("riskcode")%>'>
		</td>
		<td class=title5>险种缴费编码</td>
		<td class=input5>
			<input class=common name="PayPlanCode" readonly="readonly" >
		</td>
	</tr>
</table>

<table>
  <tr>
    <td class="titleImg" >险种责任录入要素定义</td>
  </tr>
</table>
<table  class= common style="display: ''">
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanLMDutyParamGrid" >
     </span> 
      </td>
   </tr>
</table>
<table style="margin-left:600px;display: ''"><input value="保存" type=button  onclick="save2(1)" class="cssButton" type="button" >
<input value="修改" type=button  onclick="save2(2)" class="cssButton" type="button" >
<input value="删除" type=button  onclick="save2(3)" class="cssButton" type="button" ></table>
<div style="display:none;">
<table  class= common style="display:none;">
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<br>
<table style="margin-left:600px;display:none">
<input value="保存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删除" type=button  onclick="del()" class="cssButton" type="button" >
</table>
</div>

<table style="display:none;">
  <tr>
    <td class="titleImg" >已保存险种责任录入要素</td>
  </tr>
</table>
<table  class= common style="display:none;">
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();" style="display:none;"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();" style="display:none;">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();" style="display:none;"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();" style="display:none;">
</tr>
</BR>
<br>
<div id=calFactorSub style="display:none;">
<table>
  <tr>
    <td class="titleImg" >要素配置信息</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline12Grid" >
     </span> 
      </td>
   </tr>
</table>
<table style="margin-left:600px;"> <input value="保存" type=button  onclick="saveCalFactorSub()" class="cssButton" type="button" >
<input value="修改" type=button  onclick="updateCalFactorSub()" class="cssButton" type="button" >
<input value="删除" type=button  onclick="delCalFactorSub()" class="cssButton" type="button" ></table>

</div>

<br>


<br>

<input type=hidden name="operator">
<input type=hidden name="DutyCode">
<input type=hidden name="tableName" value="PD_LMRiskDutyFactor">
<input type=hidden name=IsReadOnly>
<input type=hidden name=submitFlag>
<input type=hidden name="tableName1" value="ldcode">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
