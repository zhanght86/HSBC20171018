<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDProdAudiDetailInput.jsp
 //程序功能：产品审核明细
 //创建日期：2009-3-18
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
 session.setAttribute("LoadFlagButton","0"); 
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
 
 <SCRIPT src="PDProdAudiDetail.js"></SCRIPT>
 <%@include file="PDProdAudiDetailInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDProdAudiDetailSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >险种缴费责任</td>
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
<table>
  <tr>
    <td class="titleImg" >险种给付责任</td>
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
</BR></BR>
<input value="查看产品基础定义" type=button  onclick="button391( )" class="cssButton" type="button" >
<input value="查看契约定义" type=button  onclick="button392( )" class="cssButton" type="button" >
<input value="查看保全定义" type=button  onclick="button393( )" class="cssButton" type="button" >
<input value="查看理赔定义" type=button  onclick="button394( )" class="cssButton" type="button" >
<input value="查看保监会报表定义" onclick="button395( )" class="cssButton" type="hidden" >
<input value="查看销售定义"  onclick="button396( )" class="cssButton" type="hidden" >
<!--<input value="查看保监会信息" onclick="button395()" class="cssButton" type="button">-->
<br><br>
<input value="记事本" onclick="button397( )" class="cssButton" type="hidden" >
<input value="问题件查询" type=button  onclick="IssueQuery( )" class="cssButton" type="button" >
<input value="问题件录入" type=button  onclick="IssueInput( )" class="cssButton" type="button" >
<br><br>
<input value="审核完毕" type=button  onclick="btnAuditDone( )" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
<input type=hidden name="RiskCode">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name=PdFlag>
<input type=hidden name=RequDate>
<input type=hidden name=IsReadOnly>
<input type=hidden name=RiskName>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
