<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDProdAudiInput.jsp
 //程序功能：产品审核
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
 <SCRIPT src="SpeedProgressConvert.js"></SCRIPT>
 <SCRIPT src="PDRiskProcess.js"></SCRIPT>
 <SCRIPT src="PDProdAudi.js"></SCRIPT>
 <%@include file="PDProdAudiInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDProdAudiSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >请输入待审核险种代码：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class=common name="RiskCode" ondblclick="return showCodeList('pdriskaudit',[this,RiskName],[0,1]);" onkeyup="return showCodeListKey('pdriskaudit',[this,RiskName],[0,1]);" >
			<input type="hidden" name="RiskName" readonly="readonly" >
		</td>
		<td class=title5>申请日期
		</td>
		<td class=input5>
            <input class="coolDatePicker" dateFormat="short" id="RequDate"  name="RequDate" onClick="laydate
({elem:'#RequDate'});" > <span class="icon"><a onClick="laydate({elem: '#RequDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>

		</td>
	</tr>
</table>
<input value="查  询" type=button  onclick="queryAudit()" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >待审核的产品：</td>
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
<input value="开始审核" type=button  onclick="beginAudit( )" class="cssButton" type="button" >
<br><br>
<Div  id= "divQuery" style= "display: none">
<table>
  <tr>
    <td class="titleImg" >定义进度：</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
	<td style="text-align:left;" colSpan=1>
   <span id="RiskStateGrid"></span> 
	</td>
   </tr>
</table>
</Div>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
